<script setup>
import {onMounted, reactive, watch} from "vue";
import { useRoute } from 'vue-router';
import Meta from "@/examples/Meta.vue";
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";
import BlogIndex from "./Sections/BlogIndex.vue";
import { getWebMenuByPath } from "../../api/common";

const route = useRoute();

const state = reactive({
  webMenuInfo: {},
  isGetData: false
});

onMounted(() => {
  getWebMenuByPathClick(route.params.menu);
});

const getWebMenuByPathClick = (menuPath) => {
  getWebMenuByPath(menuPath).then(res => {
    state.webMenuInfo = res;
    state.isGetData = true;
  });
}

watch(() => route.params.menu, (newId, oldId) => {
  getWebMenuByPathClick(route.params.menu);
})

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
    </div>
  </Header>
  <BlogIndex :menuPath="state.webMenuInfo.path" :key="state.webMenuInfo.path"/>
  <DefaultFooter />
</template>
