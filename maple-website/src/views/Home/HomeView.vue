<script setup>
import {onMounted, onUnmounted, reactive, ref} from "vue";

//example components
import NavbarDefault from "../../examples/navbars/NavbarDefault.vue";
import DefaultFooter from "../../examples/footers/FooterDefault.vue";
import Header from "../../examples/Header.vue";
import Meta from "../../examples/Meta.vue";
import MaterialSocialButton from "@/components/MaterialSocialButton.vue";
import DefaultCounterCard from "@/examples/cards/counterCards/DefaultCounterCard.vue";
import PresentationCounter from "./Sections/PresentationCounter.vue";
import PresentationExample from "./Sections/PresentationExample.vue";
import vueMkHeader from "@/assets/img/bg/b2.jpg";
import { getHomeData } from "../../api/website";
import { getWebMenuByPath } from "../../api/common";
import {ElMessage} from "element-plus";
import Typed from "typed.js";

//hooks
const body = document.getElementsByTagName("body")[0];
onMounted(() => {
  body.classList.add("presentation-page");
  body.classList.add("bg-gray-200");
  getWebMenuByPathClick("home");
  getHomeDataClick();


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

onUnmounted(() => {
  body.classList.remove("presentation-page");
  body.classList.remove("bg-gray-200");
});

const homeInfo = ref({});
const data = ref([]);
const state = reactive({
  webMenuInfo: {},
  isGetData: false
});

const getHomeDataClick = () => {
  getHomeData().then(res => {
    homeInfo.value = res;
    data.value = res.homeCategoryList;
  });
};

const getWebMenuByPathClick = (menuPath) => {
  getWebMenuByPath(menuPath).then(res => {
    state.webMenuInfo = res;
    state.isGetData = true;
  });
}

</script>

<template>
  <Meta v-if="state.isGetData" :webMenuInfo="state.webMenuInfo" :key="state.webMenuInfo.path"/>
  <div class="container position-sticky z-index-sticky top-0  opacity-8">
    <div class="row">
      <div class="col-12">
        <NavbarDefault :sticky="true"/>
      </div>
    </div>
  </div>
  <Header>
    <div
        class="page-header min-vh-75"
        :style="`background-image: url(${vueMkHeader})`"
        loading="lazy"
    >
      <div class="container">
        <div class="row">
          <div class="col-lg-7 text-center mx-auto position-relative">
            <h1
                class="text-white pt-3 mt-n5 me-2"
                :style="{ display: 'inline-block ' }"
            >
              笑小枫
            </h1>
            <p class="lead text-white px-5 mt-3" :style="{ fontWeight: '500' }">
              欢迎来到笑小枫，在繁忙的数字世界中，这里<span style="color: #d8ff00;" id="typed"></span>
            </p>
            <div id="typed-strings">
              <p>是一片世外桃源</p>
              <p>有聪明的程序猿</p>
              <p>还有可爱的程序媛</p>
              <p>遇见便是有缘</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Header>

  <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6">
    <section class="pt-3 pb-4" id="count-stats">
      <div class="container">
        <div class="row">
          <div class="col-lg-9 z-index-2 border-radius-xl mx-auto py-3">
            <div class="row">
              <div class="col-md-4 position-relative">
                <DefaultCounterCard
                    link="/blog"
                    color="success"
                    title="文章数量"
                    description="笑小枫汇聚了众多技术文章，涵盖了编程、软件开发、计算机技术等各个方面，旨在为您提供丰富的技术知识和实践经验。"
                    :count="homeInfo.articleNum"
                    :duration="3000"
                    divider="vertical"
                />
              </div>
              <div class="col-md-4 position-relative">
                <DefaultCounterCard
                    link="/download"
                    color="success"
                    title="资源数量"
                    description="笑小枫拥有海量的资源，包括教程、案例、工具、代码库等，为您的学习和实践提供了全面的支持。"
                    :count="homeInfo.resourceNum"
                    :duration="3000"
                    divider="vertical"
                />
              </div>
              <div class="col-md-4">
                <DefaultCounterCard
                    link="/links"
                    color="success"
                    title="链接地址"
                    description="笑小枫的链接地址丰富多样，无论是寻找学习资源、解决技术难题，还是探索行业趋势，都能为您提供实用、高效的帮助。"
                    :count="homeInfo.linksNum"
                    :duration="3000"
                    divider="vertical"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- <PresentationInformation /> -->
    <PresentationExample :data="data"/>
    <!-- <PresentationPages /> -->
    <!-- <BuiltByDevelopers /> -->


    <!-- <PresentationTestimonials /> -->

    <!-- <div
        class="container-fluid mt-sm-5 border-radius-xl"
        :style="{
        background: 'linear-gradient(195deg, rgb(66, 66, 74), rgb(25, 25, 25))'
      }"
    >
      <div
          class="page-header py-6 py-md-5 my-sm-3 mb-3 border-radius-xl"
          :style="{
          backgroundImage: `url(${wavesWhite})`
        }"
          loading="lazy"
      >
        <span class="mask bg-gradient-dark"></span>
        <div class="container">
          <div class="row">
            <div class="d-flex justify-content-center p-5">
              <div class="col-lg-8 ms-lg-5 text-center">
                <h3 class="text-white">
                  Do you love this awesome UI Kit from Vuejs & Bootstrap?
                </h3>
                <p class="text-white text-md">
                  Cause if you do, it can be yours for FREE. Hit the button
                  below to navigate to Creative Tim where you can <br/>
                  find the Design System in HTML. Start a new project or give an
                  old Bootstrap project a new look!
                </p>

                <a
                    href="https://www.xiaoxiaofeng.com/product/vue-material-kit"
                    class="btn btn-sm mb-0 bg-gradient-success px-5 py-3 mt-4"
                >Download Now</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="container">
      <div class="row">
        <div class="d-flex flex-column w-100 text-center p-5 mb-8">
          <h3>Available on these technologies</h3>
          <div class="d-flex justify-content-center mt-3 flex-wrap">
            <a
                href="https://www.xiaoxiaofeng.com/product/material-kit"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="Bootstrap 5 - Most popular front-end component library"
            >
              <img
                  :src="logoBootstrap"
                  alt="title"
                  loading="lazy"
                  :style="{ height: '90px' }"
              />
            </a>
            <a
                class="opacity-5 ms-3"
                href="#"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="Coming Soon"
            >
              <img
                  :src="logoTailwind"
                  alt="title"
                  loading="lazy"
                  :style="{ height: '90px' }"
              />
            </a>
            <a
                href="https://www.xiaoxiaofeng.com"
                class="mx-3"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="Vue.js - Is a Progressive JavaScript Framework"
            >
              <img
                  :src="logoVue"
                  alt="title"
                  loading="lazy"
                  :style="{ height: '90px' }"
              />
            </a>
            <a
                class="opacity-5"
                href="#"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="Coming Soon"
            >
              <img
                  :src="logoAngular"
                  alt="title"
                  loading="lazy"
                  :style="{ height: '90px' }"
              />
            </a>
            <a
                href="https://www.xiaoxiaofeng.com/product/material-kit-react-pro"
                class="mx-3"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="React – A JavaScript library for building user interfaces"
            >
              <img
                  :src="logoReact"
                  alt="title"
                  loading="lazy"
                  :style="{ height: '90px' }"
              />
            </a>
            <a
                class="opacity-5"
                href="#"
                data-bs-toggle="tooltip"
                data-bs-placement="bottom"
                title="Coming Soon"
            >
              <img
                  :src="logoSketch"
                  alt="title"
                  loading="lazy"
                  :style="{ height: '90px' }"
              />
            </a>
          </div>
        </div>
      </div>
    </div>
    <div class="py-5">
      <div class="container">
        <div class="row">
          <div class="col-lg-5 ms-auto">
            <h4 class="mb-1">Thank you for your support!</h4>
            <p class="lead mb-0">We deliver the best web products</p>
          </div>
          <div class="col-lg-5 me-lg-auto my-lg-auto text-lg-end mt-5">
            <MaterialSocialButton
                route="https://twitter.com/intent/tweet?text=Check%20Material%20Design%20System%20made%20by%20%40CreativeTim%20%23webdesign%20%23designsystem%20%23bootstrap5&url=https%3A%2F%2Fwww.xiaoxiaofeng.com%2Fproduct%2Fmaterial-design-system-pro"
                component="twitter"
                color="twitter"
                label="Tweet"
            />
            <MaterialSocialButton
                route="https://www.facebook.com/sharer/sharer.php?u=https://www.xiaoxiaofeng.com/product/material-design-system-pro"
                component="facebook-square"
                color="facebook"
                label="Share"
            />
            <MaterialSocialButton
                route=""
                component="pinterest"
                color="pinterest"
                label="Pin it"
            />
          </div>
        </div>
      </div>
    </div> -->
  </div>
  <DefaultFooter/>
</template>

<style>
a:hover {
  color: #7f6307 !important;
  text-decoration: none;
}
</style>
