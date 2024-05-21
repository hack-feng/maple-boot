import axios, { AxiosInstance } from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Session } from '/@/utils/storage';
import qs from 'qs';

// 配置新建一个 axios 实例
const service: AxiosInstance = axios.create({
	baseURL: import.meta.env.VITE_API_URL,
	timeout: 50000,
	headers: { 'Content-Type': 'application/json' },
	paramsSerializer: {
		serialize(params) {
			return qs.stringify(params, { allowDots: true });
		},
	},
});

// 添加请求拦截器
service.interceptors.request.use(
	(config) => {
		// 在发送请求之前做些什么 token
		if (Session.get('token')) {
			config.headers!['Authorization'] = `${Session.get('token')}`;
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
		return config;
	},
	(error) => {
		// 对请求错误做些什么
		return Promise.reject(error);
	}
);

// 添加响应拦截器
service.interceptors.response.use((res) => {
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
	(error) => {
		// 对响应错误做点什么
		if (error.message.indexOf('timeout') != -1) {
			ElMessage.error('网络超时');
		} else if (error.message == 'Network Error') {
			ElMessage.error('网络连接错误');
		} else {
			handleResult(error.response.data);
		}
		return Promise.reject(error);
	}
);

function handleResult(data:any) {
	// 未设置状态码则默认成功状态
	const code = data.code || "0000";
	// 获取错误信息
	const msg = data.msg || "系统未知错误，请反馈给管理员";

	if (code === "1001" || code === "1002" || code === "1003") {
		if (document.getElementsByClassName('el-overlay-message-box').length > 0) return false
		ElMessageBox.confirm('当前状态未登录，您可以继续留在该页面，或者前往登录', '系统提示', {
				confirmButtonText: '前往登录',
				cancelButtonText: '暂不登录',
				type: 'warning'
			}
		).then(() => {
			Session.clear(); // 清除浏览器全部临时缓存
			window.location.href = '/'; // 去登录页
		}).catch(() => {
			Session.clear(); // 清除浏览器全部临时缓存
		})
	} else if (code !== "0000") {
		ElMessage.error(msg);
	} else {
		return data.data === undefined ? data : data.data;
	}
}

// 导出 axios 实例
export default service;
