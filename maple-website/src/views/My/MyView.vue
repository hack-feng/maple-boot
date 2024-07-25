<template>
  <Meta v-if="state.isGetData" :webMenuInfo="state.webMenuInfo" :key="state.webMenuInfo.path"/>
  <el-affix>
    <NavbarDefault light />
  </el-affix>
  <div class="container mt-5">
    <div class="row">
      <div class="col-lg-10 mx-auto mt-4">
        <MyIndex />
        <slot />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, reactive } from "vue";
import NavbarDefault from "@/examples/navbars/NavbarDefault.vue";
import Meta from "@/examples/Meta.vue";
import MyIndex from "./Sections/MyIndex.vue";
import { getWebMenuByPath } from "../../api/common";

const state = reactive({
  webMenuInfo: {},
  isGetData: false
});

onMounted(() => {
  getWebMenuByPathClick("my");
});

const getWebMenuByPathClick = (menuPath) => {
  getWebMenuByPath(menuPath).then(res => {
    state.webMenuInfo = res;
    state.isGetData = true;
  });
}
</script>
