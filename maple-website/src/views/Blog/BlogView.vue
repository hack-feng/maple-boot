<template>
  <BaseFrame :menuPath="menuPathData" :key="menuPathData">
    <BlogIndex :menuPath="menuPathData" :key="menuPathData"/>
  </BaseFrame>
</template>

<script setup>
import { ref, watch } from "vue";
import { useRoute } from 'vue-router';
import BaseFrame from "@/examples/BaseFrame.vue";
import BlogIndex from "./Sections/BlogIndex.vue";

const route = useRoute();
const menuPathData = ref(route.params.menu);

watch(() => route.params.menu, () => {
  menuPathData.value = route.params.menu;
})

</script>
