<script setup>
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";

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
    <DefaultNavbar light />
    <el-empty description="由于笑小枫网站全新升级，页面找不到了呢，现已支持全文搜索，可以在顶部搜索框搜索哟~" />
    <a class="d-block text-center" href="/">
        <button class="btn btn-outline-success btn-sm false false text-nowrap mb-0" >前往首页</button>
    </a>
</template>