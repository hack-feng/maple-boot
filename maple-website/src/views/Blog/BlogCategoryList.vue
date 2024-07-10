<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import { useRoute } from 'vue-router'

// Sections components
import TransparentBlogCard from "@/examples/cards/blogCards/TransparentBlogCard.vue";
import BaseLayout from "@/layouts/sections/components/BaseLayout.vue";
import {getBlogCategory} from "@/api/website"
import { useMeta } from 'vue-meta'

// nav-pills
import setNavPills from "@/assets/js/nav-pills.js";

//获取参数
const route = useRoute()


const blogCategoryList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const disabled = computed(() => loading.value || noMore.value);

let categoryParam = ref({
  page: {
    current: 1,
    size: 12,
    total: 100
  },
  query: {
    description: undefined
  }
});

const handleInfiniteScroll = () => {
  if (categoryParam.value.page.current * categoryParam.value.page.size <= categoryParam.value.page.total) {
    categoryParam.value.page.current = categoryParam.value.page.current + 1;
    loading.value = true;
    getBlogCategoryClick(false);
  } else {
    noMore.value = true;
  }
};

const getBlogCategoryClick = (isRefreshList) => {
  if(isRefreshList === true) {
      categoryParam.value.page.current = 1;
      blogCategoryList.value = [];
    }
  getBlogCategory(categoryParam.value).then(res => {
    categoryParam.value.page.total = res.total;
    let list = res.records;
    for (let i = 0; i < list.length; i++) {
      let article = list[i];
      blogCategoryList.value.push(article);
    }
    loading.value = false;
  });
};

// hook
onMounted(() => {
  setNavPills();
  getBlogCategoryClick();

  useMeta({
    title: '博客目录 - 笑小枫',
    meta: [
      { name: 'keywords', content: '笑小枫,java,SpringBoot,程序员,博客' },
      { name: 'description', content: '笑小枫汇聚了众多技术文章，涵盖了编程、软件开发、计算机技术等各个方面，旨在为您提供丰富的技术知识和实践经验。' }
    ]
  });
});
</script>
<template>
  <BaseLayout
      :breadcrumb="[
      { label: '小枫博客录', route: '/blog' },
      { label: '全部类目' },
    ]"
  >
    <div class="row mb-3">
      <div class="col-lg-2">
        <h3>博客列表</h3>
      </div>
      <div class="col-lg-6">
        <div class="input-group input-group-dynamic">
          <input
            type="text"
            class="form-control form-control-md"
            v-model="categoryParam.query.description"
            placeholder="请输入关键字搜索"
          />
        </div>
      </div>
      <div class="col-lg-2">
        <button class="btn bg-gradient-success" v-on:click="getBlogCategoryClick(true)">
          <i class="fas fa-search small"></i> 搜索
        </button>
      </div>
    </div>
    <div class="row" v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
      <div class="col-lg-3 col-sm-6" v-for="blogCategory in blogCategoryList">
        <TransparentBlogCard
            :image="blogCategory.icon"
            :title="blogCategory.name"
            :description="blogCategory.description"
            :action="{label:'前往阅读', color: 'success', route: '/category/'+blogCategory.id }"
        />
      </div>
    </div>
    <p class="text-center" v-if="loading">加载中...</p>
    <p class="text-center" v-if="noMore">已经到底啦</p>
  </BaseLayout>
</template>
