<script setup>
import { onMounted} from "vue";
import {useMeta} from "vue-meta";

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";

// image
import image from "@/assets/img/bg/b5.jpg";
import LinksIndex from "./Sections/LinksIndex.vue";

import Typed from "typed.js";

onMounted(() => {
  
  useMeta({
    title: '小枫链接集 - 笑小枫',
    meta: [
      { name: 'keywords', content: '笑小枫,java链接,链接地址,程序员,java链接' },
      { name: 'description', content: '笑小枫的链接地址丰富多样，无论是寻找学习资源、解决技术难题，还是探索行业趋势，都能为您提供实用、高效的帮助。' }
    ]
  });
  

  if (document.getElementById("typed")) {
    // eslint-disable-next-line no-unused-vars
    var typed = new Typed("#typed", {
      stringsElement: "#typed-strings",
      typeSpeed: 120,
      backSpeed: 90,
      backDelay: 1000,
      startDelay: 500,
      loop: true,
    });
  }
});

</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
        class="page-header min-height-400"
        :style="{ backgroundImage: `url(${image})` }"
        loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-3"></span>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-8 text-center mx-auto my-auto">
            <h3 class="text-white">
              在这里你可以找到 <span class="text-white" id="typed"></span>
            </h3>
            <div id="typed-strings">
              <h2>学习地址</h2>
              <h2>工具链接</h2>
              <h2>优秀网站</h2>
              <h2>总之你想要的一切</h2>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Header>
  <LinksIndex />
  <DefaultFooter />
</template>
