<template>
  <section class="pt-3 pb-4" id="count-stats">
    <div class="container">
      <div class="row">
        <div class="col-lg-9 z-index-2 border-radius-xl mx-auto py-3">
          <div class="row">
            <div class="col-md-4 position-relative">
              <DefaultCounterCard
                  link="/blog"
                  color="success"
                  title="文章数量"
                  description="笑小枫汇聚了众多技术文章，涵盖了编程、软件开发、计算机技术等各个方面，旨在为您提供丰富的技术知识和实践经验。"
                  :count="homeInfo.articleNum"
                  :duration="3000"
                  divider="vertical"
              />
            </div>
            <div class="col-md-4 position-relative">
              <DefaultCounterCard
                  link="/download"
                  color="success"
                  title="资源数量"
                  description="笑小枫拥有海量的资源，包括教程、案例、工具、代码库等，为您的学习和实践提供了全面的支持。"
                  :count="homeInfo.resourceNum"
                  :duration="3000"
                  divider="vertical"
              />
            </div>
            <div class="col-md-4">
              <DefaultCounterCard
                  link="/links"
                  color="success"
                  title="链接地址"
                  description="笑小枫的链接地址丰富多样，无论是寻找学习资源、解决技术难题，还是探索行业趋势，都能为您提供实用、高效的帮助。"
                  :count="homeInfo.linksNum"
                  :duration="3000"
                  divider="vertical"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
// 使用
import DefaultCounterCard from "../../../examples/cards/counterCards/DefaultCounterCard.vue";

defineProps({
  homeInfo: {
    type: Object,
    required: true,
  },
});
</script>
