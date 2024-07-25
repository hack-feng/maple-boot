<template>
  <BaseFrame :menuPath="menuPathData" :key="menuPathData">
    <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6 mb-4">
      <Profile />
      <Posts />
    </div>
    <Contact />
  </BaseFrame>
</template>

<script setup>
import { ref } from "vue";
import BaseFrame from "@/examples/BaseFrame.vue";
import Profile from "./Sections/AuthorProfile.vue";
import Posts from "./Sections/AuthorPosts.vue";
import Contact from "./Sections/AuthorContact.vue";

const menuPathData = ref("author");
</script>