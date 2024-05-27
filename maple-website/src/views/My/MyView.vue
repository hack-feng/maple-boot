<script setup>
import { onMounted} from "vue";
import {useMeta} from "vue-meta";

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import CenteredFooter from "@/examples/footers/FooterCentered.vue";
import Breadcrumbs from "@/examples/Breadcrumbs.vue";
import MyIndex from "./Sections/MyIndex.vue";

useMeta({
  title: '个人中心 - 笑小枫',
  meta: [
    { name: 'keywords', content: '笑小枫,个人中心,程序员' },
    { name: 'description', content: '个人中心，尽情的DIY自己吧，这里任你疯狂澎湃，给予你最大的自由。' }
  ]
});

</script>
<template>

  <el-affix>
    <DefaultNavbar light />
  </el-affix>
  <div class="container mt-5">
    <div class="row">
      <div class="col-lg-10 mx-auto mt-4">
        <MyIndex />
        <slot />
      </div>
    </div>
  </div>
</template>
