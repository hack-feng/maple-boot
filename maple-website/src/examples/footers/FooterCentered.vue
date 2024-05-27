<script setup>
defineProps({
  socials: {
    type: Array,
    icon: String,
    link: String,
    default: () => [
      {
        icon: '<i class="iconfont icon-shang" style="color: #ef990b; font-size: 2rem;"></i>',
        link: "/article/1038"
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
        link: "https://github.com/creativetimofficial"
      },
      {
        icon: '<i class="iconfont icon-shejiaotubiao-46" style="color: #49c0fb; font-size: 2rem;"></i>',
        link: "https://www.zhihu.com/people/xiaofengJava"
      },
    ],
  },
  links: {
    type: Array,
    name: String,
    href: String,
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
        href: "/aboutUs"
      }
    ],
  },
});
</script>
<template>
  <footer class="footer py-5">
    <div class="container">
      <div class="row">
        <div class="col-lg-8 mb-4 mx-auto text-center">
          <a
            v-for="{ name, href } of links"
            :key="name"
            :href="href"
            class="text-secondary me-xl-5 me-3 mb-sm-0 mb-2"
          >
            {{ name }}
          </a>
        </div>
        <div class="col-lg-8 mx-auto text-center mb-4 mt-2">
          <a
            v-for="{ icon, link } of socials"
            :key="link"
            :href="link"
            target="_blank"
            class="text-secondary me-xl-4 me-4"
            v-html="icon"
          >
          </a>
        </div>
      </div>
      <div class="row">
        <div class="col-8 mx-auto text-center mt-1">
          <p class="mb-0 text-secondary">
            {{ new Date().getFullYear() }} © 笑小枫 - <a href="https://www.xiaoxiaofeng.com" target="_blank">鲁ICP备2021024782号</a>
          </p>
        </div>
      </div>
    </div>
  </footer>
</template>
