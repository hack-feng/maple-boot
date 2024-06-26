<script setup>
import {onMounted, reactive} from "vue";
import { useRoute } from 'vue-router';

import Meta from "@/examples/Meta.vue";
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";
import BlogIndex from "./Sections/BlogIndex.vue";


import {getWebMenuByPath} from "../../api/common";

const route = useRoute();

const state = reactive({
  menuPath: route.params.menu,
  webMenuInfo: {},
  isGetData: false
});

onMounted(() => {
  getWebMenuByPath(state.menuPath).then(res => {
    state.webMenuInfo = res;
    state.isGetData = true;
  });
});

</script>
<template>
  <Meta v-if="state.isGetData" :webMenuInfo="state.webMenuInfo"/>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header min-height-400"
      :style="{ backgroundImage: `url(${state.webMenuInfo.image})` }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-3"></span>
    </div>
  </Header>
  <BlogIndex :menuPath="state.menuPath"/>
  <DefaultFooter />
</template>
