<script setup>
import {computed, onMounted, reactive, ref} from "vue";

import {formatDateYYYYMMDD} from '@/utils/maple'
import { getPageArticle} from "@/api/website"
import { getDictByCode } from "@/api/common";
import BlogArticleList from "@/views/Blog/Sections/BlogArticleList.vue"


onMounted(() => {
  getDictByCodeClick();
  getPageArticleClick();
});

const blogArticleList = ref([]);
const collectLoading = ref(false);
const collectNoMore = ref(false);
const dataType = ref([]);

let articleParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  query: {
    isRead: true,
    dataType: "",
    title: undefined
  }
});


const handleInfiniteScroll = () => {
  if (articleParam.value.page.current * articleParam.value.page.size <= articleParam.value.page.total) {
    articleParam.value.page.current = articleParam.value.page.current + 1;
    collectLoading.value = true;
    getPageArticleClick();
  } else {
    collectNoMore.value = true;
  }
};

const handleClick = (tab, event) => {
  searchPageClick(tab.props.name)
}

const searchPageClick = (typeValue) => {
  collectLoading.value = true;
  articleParam.value.page.current = 1;
  if(typeValue !== undefined){
    articleParam.value.query.dataType = typeValue;
  }
  blogArticleList.value = [];
  getPageArticleClick();
}

const getPageArticleClick = () => {
  getPageArticle(articleParam.value).then(res => {
    articleParam.value.page.total = res.total;
    if(articleParam.value.page.total === 0){
      collectNoMore.value = true;
    }
    blogArticleList.value.push(...res.records);
    collectLoading.value = false;
  });
}

const getDictByCodeClick = () => {
  getDictByCode("web_data_type").then(res => {
    dataType.value = res.map(p => ({
      label: p.dictLabel,
      value: p.dictValue,
      elTagType: p.listClass,
      elTagClass: p.cssClass
    })).reduce((acc, item) => {
      acc[item.value] = item;
      return acc;
    }, {});
  });
}

</script>
<template>
  <section>
    <div class="card card-body blur shadow-blur mx-md-3">
      <div class="container">
        <div class="row mt-2">
          <div class="col-lg-6">
            <div class="nav-wrapper position-relative end-0">
              <el-tabs v-model="articleParam.query.dataType" class="data-type-tabs" @tab-click="handleClick">
                <el-tab-pane label="全部" name=""/>
                <div v-for="typeItem in dataType">
                  <el-tab-pane :label="typeItem.label" :name="typeItem.value" />
                </div>
              </el-tabs>
            </div>
          </div>
          <div class="col-lg-4 mx-auto">
            <div class="input-group input-group-dynamic">
              <input
                  type="text"
                  class="form-control form-control-md"
                  v-model="articleParam.query.title"
                  placeholder="请输入关键字搜索"
              />
            </div>
          </div>
          <div class="col-lg-2">
            <button class="btn bg-gradient-success" v-on:click="searchPageClick(undefined)">
              <i class="fas fa-search"></i> 搜索
            </button>
          </div>
        </div>
      </div>

      <div class="container">
        <BlogArticleList :articleList="blogArticleList"/>
      </div>
    </div>
    <div  class="text-center">
      <el-button type="info" size="small" plain  v-if="!collectNoMore" v-on:click="handleInfiniteScroll()">加载更多</el-button>
      <p class="text-center" v-if="collectLoading">加载中...</p>
      <p class="text-center" v-if="collectNoMore">已经到底啦</p>
    </div>
  </section>
</template>