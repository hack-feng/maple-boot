import axios from 'axios'
import { ElNotification, ElMessageBox, ElMessage } from 'element-plus'
import { getToken, removeToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import router from '@/router/index.js'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: import.meta.env.VITE_API_URL,
  // 超时
  timeout: 100000,
  headers: { 'Content-Type': 'application/json' },
  paramsSerializer: {
    serialize(params) {
      return qs.stringify(params, { allowDots: true });
    },
  },
})

// request拦截器
service.interceptors.request.use(config => {
  // 是否需要设置 token
  const isToken = (config.headers || {}).isToken === false
  if (getToken() && !isToken) {
    config.headers['Authorization'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
  }
  // get请求映射params参数
  if (config.method === 'get' && config.params) {
    let url = config.url + '?';
    for (const propName of Object.keys(config.params)) {
      const value = config.params[propName];
      var part = encodeURIComponent(propName) + "=";
      if (value !== null && typeof(value) !== "undefined") {
        if (typeof value === 'object') {
          for (const key of Object.keys(value)) {
            let params = propName + '[' + key + ']';
            var subPart = encodeURIComponent(params) + "=";
            url += subPart + encodeURIComponent(value[key]) + "&";
          }
        } else {
          url += part + encodeURIComponent(value) + "&";
        }
      }
    }
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }
  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// 响应拦截器
service.interceptors.response.use(res => {

    // 文件下载
    if (res.headers['content-disposition']) {
      let downLoadMark = res.headers['content-disposition'].split(';');
      if (downLoadMark[0] === 'attachment') {
        // 执行下载
        let fileName = downLoadMark[1].split('filename=')[1];
        if (fileName) {
          fileName = decodeURI(fileName);
          if (window.navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(new Blob([res.data]), fileName);
          } else {
            let url = window.URL.createObjectURL(new Blob([res.data]));
            let link = document.createElement('a');
            link.style.display = 'none';
            link.href = url;
            link.setAttribute('download', fileName);
            document.body.appendChild(link);
            link.click();
            return;
          }
        } else {
          return res;
        }
      }
    }

    // 处理下载时，发生错误的情况
    if (res.data.type === 'application/json') {
      const reader = new FileReader()
      reader.readAsText(res.data)
      reader.onload=function(result){
        return handleResult(JSON.parse(result.target.result))
      }
    } else {
      return handleResult(res.data);
    }
  },
  error => {
    console.log(error.response.data.type)
    if(error.response.data){
      // 处理下载时，发生错误的情况
      if (error.response.data.type === 'application/json') {
        const reader = new FileReader()
        reader.readAsText(error.response.data)
        reader.onload=function(result){
          handleResult(JSON.parse(result.target.result))
        }
      } else {
        handleResult(error.response.data);
      }
      return Promise.reject(error);
    }
  
    let { message } = error;
    if (message === "Network Error") {
      message = "后端接口连接异常";
    }
    else if (message.includes("timeout")) {
      message = "系统接口请求超时";
    }
    else if (message.includes("Request failed with status code")) {
      message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    ElMessage({
      message: message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

function handleResult(data) {
  // 未设置状态码则默认成功状态
  const code = data.code || "0000";
  // 获取错误信息
  const msg = errorCode[code] || data.msg || errorCode['default']

  if (code === "1001" || code === "1002" || code === "1003") {
    if (document.getElementsByClassName('el-overlay-message-box').length > 0) return false
    ElMessageBox.confirm('当前状态未登录，您可以继续留在该页面，或者前往登录', '系统提示', {
          confirmButtonText: '前往登录',
          cancelButtonText: '暂不登录',
          type: 'warning'
        }
    ).then(() => {
      removeToken();
      router.push({path: '/login'});
    }).catch(() => {
      removeToken();
    })
  } else if (code !== "0000") {
    ElNotification.error({
      title: msg
    })
  } else {
    return data.data === undefined ? data : data.data;
  }
}

export default service
