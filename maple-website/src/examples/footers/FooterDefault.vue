<script setup>
import logoDark from "@/assets/img/logos/logo.jpg";
import upyun from "@/assets/img/upyun.svg";
import { getSiteConfig } from '@/stores/website';
import { onMounted, reactive } from "vue";

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
  },
  menus: {
    type: Array,
    name: String,
    items: {
      type: Array,
      name: String,
      href: String
    },
    default: () => [
      {
        name: "笑小枫",
        href: "/"
      },
      {
        name: "小枫博客录",
        href: "/blog"
      },
      {
        name: "小枫资源库",
        href: "/download"
      },
      {
        name: "小枫链接集",
        href: "/links"
      },
      {
        name: "关于笑小枫",
        href: "/author"
      }
    ]
  }
});

const state = reactive({
  websiteName: '笑小枫',
  icp: ''
});

onMounted(() => {
  getSiteConfig("website_name").then(res => {
    state.websiteName = res;
  });
  getSiteConfig("ICP").then(res => {
    state.icp = res;
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
          v-for="{ name, href } of menus"
          :key="name"
        >
          <a class="nav-link" :href="href" target="_blank">
            <h6 class="text-sm">{{ name }}</h6>
          </a>
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
