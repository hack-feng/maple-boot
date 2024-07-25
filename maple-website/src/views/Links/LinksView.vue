<template>
  <BaseFrame :menuPath="menuPathData" :key="menuPathData">
    <LinksIndex />
  </BaseFrame>
</template>

<script setup>
import { ref } from "vue";
import BaseFrame from "../../examples/BaseFrame.vue";
import LinksIndex from "./Sections/LinksIndex.vue";

const menuPathData = ref("links");

</script>
