<script setup>
import {computed, onMounted, onUpdated, ref} from "vue";
import { useRoute } from 'vue-router'

// Sections components
import BaseLayout from "@/layouts/sections/components/BaseLayout.vue";
import DefaultInfoCard from "@/examples/cards/infoCards/DefaultInfoCard.vue";
import CenteredBlogCard from "@/examples/cards/blogCards/CenteredBlogCard.vue";
import BlogArticleList from "./Sections/BlogArticleList.vue"
import {formatDateYYYYMMDD} from '@/utils/maple'
import {getCategoryById, getPageArticle} from "@/api/website"
import { isDesktop } from "@/assets/js/useWindowsWidth";

// image
import defaultImage from "@/assets/img/defaultImage.jpg";
import wxImage from "@/assets/img/wx.jpg";
import wxgzh from "@/assets/img/wxgzh.png";
import applet from "@/assets/img/applet.jpg";
import { useMeta } from 'vue-meta'


//获取参数
const route = useRoute()
const textDark = isDesktop();
const blogCategory = ref({});

const blogArticleList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const disabled = computed(() => loading.value || noMore.value);

let articleParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  model: {
    categoryId: route.params.id
  }
});


const getCategoryByIdClick = () => {
  getCategoryById(route.params.id).then(res => {
    blogCategory.value = res
  });
};

const handleInfiniteScroll = () => {
  if (articleParam.value.page.current * articleParam.value.page.size <= articleParam.value.page.total) {
    articleParam.value.page.current = articleParam.value.page.current + 1;
    loading.value = true;
    getPageArticleClick();
  } else {
    noMore.value = true;
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
    loading.value = false;
  });
}

// hook
onMounted(() => {
  getCategoryByIdClick();
  getPageArticleClick();
});

onUpdated(() => {
  useMeta({
    title: blogCategory.value.name === undefined ? '类目详情 - 笑小枫' : blogCategory.value.name + '- 笑小枫',
    meta: [
      { name: 'keywords', content: '笑小枫,java,SpringBoot,程序员' },
      { name: 'description', content: blogCategory.value.description }
    ]
  })
})

</script>
<template>
  <BaseLayout
      :breadcrumb="[
      { label: '小枫博客录', route: '/blog' },
      { label: '全部类目', route: '/categoryList' },
      { label: '类目详情', route: '/category/' + blogCategory.id }
    ]"
  >

    <div class="col-lg-12 mx-auto">
      <div class="card alignment-container">
        <div class="row mt-2 mx-md-2 mb-4">
          <el-space class="col-lg-3">
            <a class="d-block blur-shadow-image">
              <img 
              :src="blogCategory.icon === null ? defaultImage : blogCategory.icon" 
              :alt="blogCategory.name" 
              class="img-fluid border-radius-lg img-160" />
            </a>
          </el-space>
          <div class="col-lg-7">
            <div class="position-relative mx-3">
              <div class="row mb-1">
                <div class="d-flex justify-content-between">
                  <h4 class="font-weight-bolder mt-3">
                    {{ blogCategory.name }}
                    <button class="btn btn-outline-success btn-sm false false text-nowrap mb-0">收藏</button>
                  </h4>
                </div>
              </div>
              <div class="row mb-2">
                <div class="col-auto">
                  <span>阅读 </span>
                  <span class="h6 me-1">{{ blogCategory.readNum }}</span>
                </div>
                <div class="col-auto">
                  <span>收藏 </span>
                  <span class="h6 me-1">{{ blogCategory.collectNum }}</span>
                </div>
                <div class="col-auto">
                  <span>文章 </span>
                  <span class="h6 me-1">{{ blogCategory.titleNum }}</span>
                </div>
              </div>
              <p class="pe-5">
                {{ blogCategory.description }}
              </p>
            </div>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="card card-body blur mt-2">
          <div class="col-lg-6">
            <h3 class="mt-2 mb-2">文章列表</h3>
          </div>
        </div>

        <div class="col-2 mt-2" v-show="textDark">
          <div class="sticky-50">
            <section>
              <div class="card card-body blur shadow-blur">
                <h6><i class="fas fa-heart text-danger"></i> 温馨提示</h6>
                <span>
                  在使用过程中遇到问题<br/>
                  请添加笑小枫微信反馈
                </span>
                <img :src="wxImage"
                     alt="图片去找笑小枫啦"
                     class="shadow border-radius-lg mt-3"
                     loading="lazy"
                     style="width: 100%"
                />
              </div>
            </section>
          </div>
        </div>

        <div :class="textDark ? 'col-8' : 'col-12'">
          <div v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
            <div class="card card-body blur shadow-blur">
              <section>
                <BlogArticleList :articleList="blogArticleList"/>
              </section>
            </div>
            <p class="text-center" v-if="loading">加载中...</p>
            <p class="text-center" v-if="noMore">已经到底啦</p>
          </div>
        </div>
        <div class="col-2 mt-2" v-show="textDark">
          <div class="sticky-50">
            <section>
              <div class="card card-body blur shadow-blur">
                <h6><i class="fas fa-heart text-danger"></i> 干了这碗鸡汤</h6>
                <span>
                  没有播种，怎么收获<br>
                  没有辛苦，怎么成功<br>
                  没有挫折，怎么成长<br>
                  没有磨难，怎么荣耀<br>
                  做大做强，再创辉煌<br>
                </span>
                <h6><i class="fas fa-search text-danger mt-4"></i> 扫码关注</h6>
                <img :src="wxgzh"
                     alt="图片去找笑小枫啦"
                     class="shadow border-radius-lg mt-3"
                     loading="lazy"
                     style="width: 100%;"
                />
              </div>
            </section>
          </div>
        </div>
      </div>
    </div>
  </BaseLayout>

  
</template>
