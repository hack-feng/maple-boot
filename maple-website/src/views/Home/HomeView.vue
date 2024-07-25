<template>
  <BaseFrame :menuPath="menuPathData" :minHeight="' min-vh-75'">
    <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6">
      <PresentationCounter :homeInfo="homeInfo"/>
      <PresentationExample :data="data"/>
    </div>
  </BaseFrame>
</template>

<script setup>
import {onMounted, ref} from "vue";
import BaseFrame from "../../examples/BaseFrame.vue";
import PresentationExample from "./Sections/PresentationExample.vue";
import PresentationCounter from "./Sections/PresentationCounter.vue";
import {getHomeData} from "../../api/website";

onMounted(() => {
  getHomeDataClick();
});

const homeInfo = ref({});
const data = ref([]);
const menuPathData = "home";

const getHomeDataClick = () => {
  getHomeData().then(res => {
    homeInfo.value = res;
    data.value = res.homeCategoryList;
  });
};
</script>

<style>
a:hover {
  color: #7f6307 !important;
  text-decoration: none;
}
</style>
