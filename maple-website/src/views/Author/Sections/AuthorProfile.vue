<script setup>
import { onMounted } from "vue";

//Maple Blog components
import MaterialAvatar from "@/components/MaterialAvatar.vue";
import MaterialButton from "@/components/MaterialButton.vue";

// image
import profilePic from "@/assets/img/logos/logo.jpg";

// material-input
import setMaterialInput from "@/assets/js/material-input";
onMounted(() => {
  setMaterialInput();
});
</script>
<template>
  <section class="py-sm-5 py-3 position-relative">
    <div class="container">
      <div class="row">
        <div class="col-12 mx-auto">
          <div class="mt-n8 mt-md-n9 text-center">
            <div class="blur-shadow-avatar">
              <MaterialAvatar
                size="xxl"
                class="shadow-xl position-relative z-index-2"
                :image="profilePic"
                alt="笑小枫"
              />
            </div>
          </div>
          <div class="row py-6">
            <div
              class="col-lg-7 col-md-7 z-index-2 position-relative px-md-2 px-sm-5 mx-auto"
            >
              <div
                class="d-flex justify-content-between align-items-center mb-2"
              >
                <h3 class="mb-0">关于笑小枫</h3>
                <div class="d-block">
                  <MaterialButton
                    class="text-nowrap mb-0"
                    variant="outline"
                    color="success"
                    size="sm"
                    >收藏</MaterialButton
                  >
                </div>
              </div>
              <div class="row mb-4">
                <div class="col-auto">
                  <span class="h6 me-1">300+</span>
                  <span>Blog</span>
                </div>
                <div class="col-auto">
                  <span class="h6 me-1">2w+</span>
                  <span>Fans</span>
                </div>
                <div class="col-auto">
                  <span class="h6 me-1">1.9k</span>
                  <span>Following</span>
                </div>
              </div>
              <p class="text-lg mb-0">
                欢迎来到笑小枫，一个专注于Java后端技术的专业平台！我们致力于为广大Java开发者提供最新、最实用的技术分享和交流机会。

                笑小枫自成立以来，一直秉承着“分享、学习、成长”的理念，为广大Java开发者提供了一个集学习、交流、成长于一体的专业社区。在这里，您可以找到关于Java后端技术的最新动态、深入的技术解析以及实用的开发技巧。

                我们的内容涵盖了Java核心技术、Web开发、数据库管理、系统架构等多个方面。我们通过详细的教程、案例分析和专家解读等方式，帮助您深入了解Java后端技术的方方面面，提升您的开发技能和项目实战经验。

                感谢您选择访问笑小枫！我们希望您在这里能够找到自己需要的技术资料，与我们一起分享和成长。如果您有任何问题或建议，欢迎随时联系我们。让我们共同探索Java后端技术的无限可能！
                <br /><br /><a
                  href="/"
                  class="text-success icon-move-right"
                  >立即前往
                  <i class="fas fa-arrow-right text-sm ms-1"></i>
                </a>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
