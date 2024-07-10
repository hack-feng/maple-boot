<script setup>
import { onMounted, reactive } from "vue";
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";
import Meta from "@/examples/Meta.vue";

// image
import LinksIndex from "./Sections/LinksIndex.vue";

import Typed from "typed.js";

import { getWebMenuByPath } from "../../api/common";

const state = reactive({
  webMenuInfo: {},
  isGetData: false
});

onMounted(() => {
  getWebMenuByPathClick("links");
});

const getWebMenuByPathClick = (menuPath) => {
  getWebMenuByPath(menuPath).then(res => {
    state.webMenuInfo = res;
    state.isGetData = true;
  });
}

onMounted(() => {
  
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
  <Meta v-if="state.isGetData" :webMenuInfo="state.webMenuInfo" :key="state.webMenuInfo.path"/>
  <div class="container position-sticky z-index-sticky top-0  opacity-8">
    <div class="row">
      <div class="col-12">
        <DefaultNavbar :sticky="true"/>
      </div>
    </div>
  </div>
  <Header>
    <div
        class="page-header min-height-400"
        :style="{ backgroundImage: `url(${state.webMenuInfo.image})` }"
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
