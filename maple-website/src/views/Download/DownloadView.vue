<script setup>
import { onMounted } from "vue";
import {useMeta} from "vue-meta";

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";

// image
import image from "@/assets/img/bg/b1.jpg";
import DownloadIndex from "./Sections/DownloadIndex.vue";

onMounted(() => {
  useMeta({
    title: '小枫资源库 - 笑小枫',
    meta: [
      { name: 'keywords', content: '笑小枫,java资源,开发软件,程序员,资源下载' },
      { name: 'description', content: '笑小枫拥有海量的资源，包括教程、案例、工具、代码库等，为您的学习和实践提供了全面的支持。' }
    ]
  });
});

</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
        class="page-header min-height-400"
        :style="{ backgroundImage: `url(${image})` }"
        loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-2"></span>
    </div>
  </Header>
  <DownloadIndex />
  <DefaultFooter />
</template>
