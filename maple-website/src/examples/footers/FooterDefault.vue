<script setup>
import logoDark from "@/assets/img/logos/logo.jpg";
import upyun from "@/assets/img/upyun.svg";
import { getSiteConfig } from '@/stores/website';
import { onMounted, reactive } from "vue";
import { getFooterMenu } from "../../api/common";

defineProps({
  brand: {
    type: Object,
    name: String,
    logo: String,
    route: "",
    default: () => ({
      name: "笑小枫",
      logo: logoDark,
      route: "/"
    })
  },
  socials: {
    type: Array,
    icon: String,
    link: String,
    default: () => [
      {
        icon: '<i class="iconfont icon-shang" style="color: #ef990b; font-size: 2rem;"></i>',
        link: "https://www.xiaoxiaofeng.com/article/1038"
      },
      {
        icon: '<i class="iconfont icon-csdn" style="color: red; font-size: 2rem;"></i>',
        link: "https://zhangfz.blog.csdn.net/"
      },
      {
        icon: '<i class="iconfont icon-github" style="color: #000000; font-size: 2rem;"></i>',
        link: "https://github.com/hack-feng"
      },
      {
        icon: '<i class="iconfont icon-weixin" style="color: #0abc64; font-size: 2rem;"></i>',
        link: "https://www.xiaoxiaofeng.com/author"
      },
      {
        icon: '<i class="iconfont icon-shejiaotubiao-46" style="color: #49c0fb; font-size: 2rem;"></i>',
        link: "https://www.zhihu.com/people/xiaofengJava"
      }
    ]
  }
});

const state = reactive({
  websiteName: '笑小枫',
  icp: '',
  footerList: [],
});

onMounted(() => {
  getSiteConfig("website_name").then(res => {
    state.websiteName = res;
  });
  getSiteConfig("ICP").then(res => {
    state.icp = res;
  });
  getFooterMenu().then(res => {
    state.footerList = res;
  });
})
</script>
<template>
  <footer class="footer pt-5">
    <div class="container">
      <div class="row">
        <div class="col-md-1 ms-auto">
          <a :href="brand.route">
            <img :src="brand.logo" class="mb-3 footer-logo" alt="main_logo" />
          </a>
        </div>
        <div
          class="col-md-2 col-sm-6 col-6"
          v-for="footer of state.footerList"
          :key="footer.title"
        >
          <RouterLink v-if="!footer.isLink"
                      :to="{ name: footer.uiTemplate ? footer.uiTemplate : footer.path, params: { menu: footer.path }  }"
                      class="dropdown-item border-radius-md"
                      tag="a" target="_blank"
          >
            <h6 class="text-sm">{{ footer.title }}</h6>
          </RouterLink>
          <ul class="flex-column ms-n3 nav" v-if="footer.children">
            <li class="nav-item" v-for="item of footer.children" :key="item.name">
              <RouterLink v-if="!footer.isLink"
                          :to="{ name: item.uiTemplate ? item.uiTemplate : item.path, params: { menu: item.path }  }"
                          class="dropdown-item border-radius-md"
                          tag="a" target="_blank"
              >
                {{ item.title }}
              </RouterLink>
            </li>
          </ul>
        </div>
        
        <div class="col-12">
          <div class="col-md-7 ms-auto">
            <ul class="d-flex flex-row ms-n3 nav">
              <li
                  class="nav-item"
                  v-for="{ icon, link } of socials"
                  :key="link"
              >
                <a
                    class="nav-link pe-1"
                    :href="link"
                    target="_blank"
                    v-html="icon"
                >
                </a>
              </li>
            </ul>
          </div>
          <div class="text-center">
            <p class="text-dark my-4 text-sm font-weight-normal">
              {{ new Date().getFullYear() }} © {{ state.websiteName }} - <a href="https://beian.miit.gov.cn/" target="_blank"> {{ state.icp }}</a>
              <br>
              本站点由 
              <a href="https://www.upyun.com/?utm_source=lianmeng&utm_medium=referral" target="_blank">
                <img :src="upyun" alt="云服务商"  style="width: 70px;">
              </a> 
              提供云服务
            </p>
          </div>
        </div>
      </div>
    </div>
  </footer>
</template>
