<template>
  <Meta v-if="state.isGetData" :webMenuInfo="state.webMenuInfo" :key="props.menuPath"/>
  <NavbarDefault :sticky="true"/>
  <Header
      :image="state.webMenuInfo.image"
      :title="state.webMenuInfo.title"
      :description="state.webMenuInfo.description"
      :minHeight="props.minHeight"
  />
  <slot></slot>
  <DefaultFooter/>
</template>

<script setup>
import NavbarDefault from "./navbars/NavbarDefault.vue";
import DefaultFooter from "./footers/FooterDefault.vue";
import Header from "./Header.vue";
import Meta from "./Meta.vue";
import {onMounted, reactive, watch} from "vue";
import {getWebMenuByPath} from "../api/common";
import {useRoute} from "vue-router";

const props = defineProps({
  menuPath: {
    type: String,
    required: true,
  },
  minHeight: {
    type: String,
    default: " min-height-400",
  },
});

const state = reactive({
  webMenuInfo: {},
  isGetData: false
});

onMounted(() => {
  getWebMenuByPathClick(props.menuPath);
});

const getWebMenuByPathClick = (menuPath) => {
  getWebMenuByPath(menuPath).then(res => {
    state.webMenuInfo = res;
    state.isGetData = true;
  });
}
</script>