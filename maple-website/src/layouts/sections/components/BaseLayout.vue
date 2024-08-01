<script setup>
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import FooterDefault from "@/examples/footers/FooterDefault.vue";
import Breadcrumbs from "@/examples/Breadcrumbs.vue";

defineProps({
  breadcrumb: {
    type: Array,
    required: true,
  },
  title: {
    type: String,
    required: true,
  },
});
</script>
<template>
  <el-affix>
    <DefaultNavbar light />
  </el-affix>
  <div class="container mt-5">
    <div class="row">
      <div class="col-lg-12 mx-auto">
        <div class="mb-4 w-100 w-md-50 w-lg-25">
          <Breadcrumbs :routes="breadcrumb" />
          <h3>{{ title }}</h3>
        </div>

        <slot />
      </div>
    </div>
  </div>
  <FooterDefault />
</template>
