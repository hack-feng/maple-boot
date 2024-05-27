<script setup>
import { onMounted, reactive, onUnmounted } from "vue";
import {useMeta} from "vue-meta";
import axios from 'axios'
import { useRouter } from "vue-router";
import { ElMessage } from 'element-plus';
import { getToken, setToken } from '@/utils/auth'

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";

//Maple Blog components
import MaterialButton from "@/components/MaterialButton.vue";

import {getUnlimitedQrCode, checkAppletAuth} from "@/api/login";

const router = useRouter()

let qrCodeResult = reactive({
  uniCode: -1,
  authToken: undefined,
  qrCode: undefined
})


onMounted(() => {
  
  useMeta({
    title: '登录 - 笑小枫',
    meta: [
      { name: 'keywords', content: '笑小枫,登录,程序员' },
      { name: 'description', content: '欢迎来到笑小枫，请先登录吧~' }
    ]
  });
  
  if(getToken() !== undefined && getToken() !== null) {
    if(router.options.history.state.back !== null && router.options.history.state.back !== 'login') {
      router.go(-1);
    } else {
      router.push("/");
    }
  }
  getUnlimitedQrCodeClick();
});

const getAuthResult = setInterval(() => {
  checkAppletAuthClick(1);
}, 10000);

onUnmounted(() => {
  clearInterval(getAuthResult);
})

const getUnlimitedQrCodeClick = () => {
  
  getUnlimitedQrCode().then(res => {
    qrCodeResult.uniCode = res.uniCode
    qrCodeResult.authToken = res.authToken
    axios({
      method: "POST",
      withCredentials: false,
      url: "/wx/wxa/getwxacodeunlimit?access_token=" + res.authToken,
      data: {"scene": res.uniCode, "page": "pages/authPage/index", "check_path": false},
      responseType: "blob"
    }).then(res => {
      qrCodeResult.qrCode = URL.createObjectURL(res.data);
    })
  });
};

const checkAppletAuthClick = (type) => {
  checkAppletAuth({"uniCode":qrCodeResult.uniCode}).then(res => {
    if(res.authResult) {
      ElMessage.success("登录成功");
      setToken(res.token);
      if(router.options.history.state.back != null) {
        router.go(-1);
      } else {
        router.push("/");
      }
    } else {
      if(type !== 1) {
        ElMessage.error('没有检测到授权信息哟')
      }
    }
  });
};

</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header align-items-start min-vh-100"
      :style="{
        backgroundImage:
          'url(https://image.xiaoxiaofeng.site/blog/image/photo-1497294815431-9365093b7331.jpg?xiaoxiaofeng)'
      }"
      loading="lazy"
    >
      
      <div class="container my-auto">
        <div class="row">
          <div class="col-lg-4 col-md-8 col-12 mx-auto">
            <div class="card z-index-0 fadeIn3 fadeInBottom">
              <div
                class="card-header p-0 position-relative"
              >
                <div
                  class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1"
                >
                  <h4
                    class="text-white font-weight-bolder text-center mt-2 mb-0"
                  >
                    微信扫码授权，请勿刷新页面
                  </h4>
                  <div class="row mt-3">
                    <div class="col-12 text-center px-1">
                      <span class="text-white text-lg">需要绑定「笑小枫」小程序授权登录</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="text-center">
                <img
                    :src="qrCodeResult.qrCode"
                    alt="加载失败，点击下方刷新"
                    loading="lazy"
                    :style="{ height: '255px', display: 'inline-block', 'margin-top': '20px' }"
                />

                <div class="text-center">
                  <MaterialButton
                    class="my-4 mb-2"
                    variant="gradient"
                    color="success"
                    fullWidth
                    @click.native.prevent="checkAppletAuthClick"
                    >我已扫码</MaterialButton
                  >
                </div>
                <p class="mt-4 text-sm text-center">
                  没有加载出来二维码
                  <a
                    @click.native.prevent="getUnlimitedQrCodeClick"
                    href="#"
                    class="text-success text-gradient font-weight-bold"
                    >点击刷新</a
                  >
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <footer class="footer position-absolute bottom-2 py-2 w-100">
        <div class="container">
          <div class="row align-items-center justify-content-lg-between">
            <div class="col-12 col-md-6 my-auto">
              <div
                class="copyright text-center text-sm text-white text-lg-start"
              >
                {{ new Date().getFullYear() }} © 笑小枫 - <a class="text-white" href="https://www.xiaoxiaofeng.com" target="_blank">鲁ICP备2021024782号</a>
              </div>
            </div>
          </div>
        </div>
      </footer>
    </div>
  </Header>
</template>
