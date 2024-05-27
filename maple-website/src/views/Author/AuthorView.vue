<script setup>
import { onMounted } from "vue";
import {useMeta} from "vue-meta";

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";

// sections
import Profile from "./Sections/AuthorProfile.vue";
import Posts from "./Sections/AuthorPosts.vue";
import Contact from "./Sections/AuthorContact.vue";

// image
import image from "@/assets/img/city-profile.jpg";

onMounted(() => {
  useMeta({
    title: '关于笑小枫 - 笑小枫',
    meta: [
      { name: 'keywords', content: '笑小枫,java,SpringBoot,程序员,博客' },
      { name: 'description', content: '欢迎来到笑小枫，一个专注于Java后端技术的专业平台！我们致力于为广大Java开发者提供最新、最实用的技术分享和交流机会。' +
            '笑小枫自成立以来，一直秉承着“分享、学习、成长”的理念，为广大Java开发者提供了一个集学习、交流、成长于一体的专业社区。在这里，您可以找到关于Java后端技术的最新动态、深入的技术解析以及实用的开发技巧。' +
            '我们的内容涵盖了Java核心技术、Web开发、数据库管理、系统架构等多个方面。我们通过详细的教程、案例分析和专家解读等方式，帮助您深入了解Java后端技术的方方面面，提升您的开发技能和项目实战经验。' +
            '感谢您选择访问笑小枫！我们希望您在这里能够找到自己需要的技术资料，与我们一起分享和成长。如果您有任何问题或建议，欢迎随时联系我们。让我们共同探索Java后端技术的无限可能！' }
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
      <span class="mask bg-gradient-dark opacity-8"></span>
    </div>
  </Header>
  <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6 mb-4">
    <Profile />
    <Posts />
  </div>
  <Contact />
  <DefaultFooter />
</template>
