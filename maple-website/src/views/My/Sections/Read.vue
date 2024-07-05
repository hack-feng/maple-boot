<script setup>
import {computed, onMounted, ref} from "vue";

import {formatDateYYYYMMDD} from '@/utils/maple'
import {getPageArticle} from "@/api/website"


onMounted(() => {
  getPageArticleClick();
});

const blogArticleList = ref([]);
const readLoading = ref(false);
const readNoMore = ref(false);

let articleParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  model: {
   isRead: true
  }
});

const handleInfiniteScroll = () => {
  if (articleParam.value.page.current * articleParam.value.page.size <= articleParam.value.page.total) {
    articleParam.value.page.current = articleParam.value.page.current + 1;
    readLoading.value = true;
    getPageArticleClick();
  } else {
    readNoMore.value = true;
  }
};

const getPageArticleClick = () => {
  getPageArticle(articleParam.value).then(res => {
    articleParam.value.page.total = res.total;
    let list = res.records;
    for (let i = 0; i < list.length; i++) {
      let article = list[i];
      blogArticleList.value.push(article);
    }
    readLoading.value = false;
  });
}


</script>
<template>
  <section>
    <div class="card card-body blur shadow-blur mx-3 mt-3">
      <div class="container">
        <div class="row" v-for="blogArticle in blogArticleList">
          <div class="col-12 mx-auto">
            <div class="row">
              <div class="col-lg-12 position-relative mx-auto">
                <h6 class="mb-0 more-omit-3">
                  {{ blogArticle.title }}
                </h6>
                <div class="row mb-1">
                  <div class="col-auto">
                    <span>于{{ formatDateYYYYMMDD(blogArticle.createTime) }}发布</span>
                  </div>
                  <div class="col-auto">
                    <span>阅读 </span>
                    <span class="h6 me-1">{{ blogArticle.readNum }}</span>
                  </div>
                  <div class="col-auto">
                    <span>收藏 </span>
                    <span class="h6 me-1">{{ blogArticle.collectNum }}</span>
                  </div>
                </div>
                <p class="mb-0 more-omit-3">
                  {{ blogArticle.description }}
                  <br/>
                </p>
                <a :href="'/article/'+blogArticle.id" class="text-success icon-move-right">
                  点击阅读
                  <i class="fas fa-arrow-right text-sm ms-1"></i>
                </a>
              </div>
            </div>
          </div>
          <hr class="hr-3"/>
        </div>
      </div>
    </div>
    <div  class="text-center">
      <el-button type="info" size="small" plain  v-if="!readNoMore" v-on:click="handleInfiniteScroll()">加载更多</el-button>
      <p class="text-center" v-if="readLoading">加载中...</p>
      <p class="text-center" v-if="readNoMore">已经到底啦</p>
    </div>
  </section>
</template>
