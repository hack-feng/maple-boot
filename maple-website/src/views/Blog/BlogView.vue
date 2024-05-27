<script setup>
// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";

// sections
import DefaultFooter from "@/examples/footers/FooterDefault.vue";
import BlogIndex from "./Sections/BlogIndex.vue";

// image
import image from "@/assets/img/bg/b3.jpg";

</script>
<template>
  <DefaultNavbar transparent />
  <Header>
    <div
      class="page-header min-height-400"
      :style="{ backgroundImage: `url(${image})` }"
      loading="lazy"
    >
      <span class="mask bg-gradient-dark opacity-3"></span>
    </div>
  </Header>
  <BlogIndex />
  <DefaultFooter />
</template>
