<template>
  <BaseFrame :menuPath="menuPathData" :key="menuPathData">
    <DownloadIndex />
  </BaseFrame>
</template>

<script setup>
import { ref } from "vue";
import BaseFrame from "../../examples/BaseFrame.vue";
import DownloadIndex from "./Sections/DownloadIndex.vue";

const menuPathData = ref("download");
</script>

