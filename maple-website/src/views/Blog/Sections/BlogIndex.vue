<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import {useMeta} from "vue-meta";

// example components
import TransparentBlogCard from "@/examples/cards/blogCards/TransparentBlogCard.vue";
import BackgroundBlogCard from "@/examples/cards/blogCards/BackgroundBlogCard.vue";

import {formatDateYYYYMMDD} from '@/utils/maple'
import {getBlogCategory, getPageTitle} from "@/api/blog"
import { isDesktop } from "@/assets/js/useWindowsWidth";

//Maple Blog components
import post4 from "@/assets/img/examples/blog2.jpg";
// image
import defaultImage from "@/assets/img/defaultImage.jpg";


onMounted(() => {
  getBlogCategoryClick();
  getPageTitleClick();
  getHotTitleClick();

  useMeta({
    title: '小枫博客录 - 笑小枫',
    meta: [
      { name: 'keywords', content: '笑小枫,java,SpringBoot,程序员,博客' },
      { name: 'description', content: '笑小枫汇聚了众多技术文章，涵盖了编程、软件开发、计算机技术等各个方面，旨在为您提供丰富的技术知识和实践经验。' }
    ]
  });
});

const textDark = isDesktop();
const blogArticleList = ref([]);
const hotBlogArticleList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const disabled = computed(() => loading.value || noMore.value);

let data = reactive({
  blogCategoryList: {}
})

let articleParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  model: {
    isNew: true,
    description: undefined
  }
});

const getBlogCategoryClick = () => {
  let categoryParam = {
    page: {
      current: 1,
      size: 3
    },
    model: {
      isTop: true
    }
  }

  getBlogCategory(categoryParam).then(res => {
    data.blogCategoryList = res.records
  });
};

const handleInfiniteScroll = () => {
  if (articleParam.value.page.current * articleParam.value.page.size <= articleParam.value.page.total) {
    articleParam.value.page.current = articleParam.value.page.current + 1;
    loading.value = true;
    getPageTitleClick(false);
  } else {
    noMore.value = true;
  }
};

const getPageTitleClick = (isRefreshList) => {
  if(isRefreshList === true) {
    articleParam.value.page.current = 1
    blogArticleList.value = [];
  }
  console.log(isRefreshList)
  getPageTitle(articleParam.value).then(res => {
    articleParam.value.page.total = res.total;
    let list = res.records;
    for (let i = 0; i < list.length; i++) {
      let article = list[i];
      blogArticleList.value.push(article);
    }
    loading.value = false;
  });
}

const getHotTitleClick = () => {
  let hotArticleParam = {
    page: {
      current: 1,
      size: 10
    },
    model: {
      isHot: true
    }
  }
  getPageTitle(hotArticleParam).then(res => {
    hotBlogArticleList.value = res.records;
  });
}


</script>
<template>
  <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6 mb-4">
    <section class="py-1">
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <h3 class="mb-4">博客分类</h3>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-3 col-sm-6" v-for="blogCategory in data.blogCategoryList">
            <TransparentBlogCard
                :image="blogCategory.icon === null ? defaultImage : blogCategory.icon"
                :title="blogCategory.name"
                :description="blogCategory.description"
                :action="{label:'前往阅读', color: 'success', route: '/category/'+blogCategory.id }"
            />
          </div>
          <div class="col-lg-3 col-md-12 col-12">
            <BackgroundBlogCard
                :image="post4"
                :action="{label:'点击前往', color: 'white', route: '/categoryList' }"
                title="查看更多"
                description="笑小枫致力于为广大技术爱好者提供一个丰富多样的知识分享平台。
            在这里，我们为您精选了一系列优质技术文章，涵盖多个领域，以满足不同读者的需求。
            为了让您更轻松地找到感兴趣的内容，我们对文章进行了细致的分类。"
            />
          </div>
        </div>
      </div>
    </section>
  </div>

  <div class="col-12 mx-auto row">
    <div class="col-1" v-show="textDark">
      
    </div>
    
    <div class="mx-auto" :class="textDark ? 'col-8' : 'col-12'">
      <div class="card card-body blur shadow-blur">
        <div class="container">
          <div class="row mt-2 mb-2">
            <div class="col-lg-2">
              <h3>博客列表</h3>
            </div>
            <div class="col-lg-6 mx-auto">
              <div class="input-group input-group-dynamic">
                <input
                  type="text"
                  class="form-control form-control-md"
                  v-model="articleParam.model.description"
                  placeholder="请输入关键字搜索"
                />
              </div>
            </div>
            <div class="col-lg-2">
              <button class="btn bg-gradient-success" v-on:click="getPageTitleClick(true)">
                <i class="fas fa-search small"></i> 搜索
              </button>
            </div>
          </div>
        </div>
      </div>
    
      <div v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
        <div class="card card-body blur shadow-blur">
          <section>
            <div class="container">
              <div class="row" v-for="blogArticle in blogArticleList">
                <div class="col-12 mx-auto">
                  <div class="row">
                    <div class="col-lg-3 position-relative mx-auto">
                      <img
                          :src="blogArticle.img === null ? defaultImage : blogArticle.img"
                          alt="图片去找笑小枫啦"
                          class="shadow border-radius-lg img-130"
                          loading="lazy"
                      />
                    </div>
                    <div class="col-lg-9 position-relative mx-auto">
                      <a :href="'/article/'+blogArticle.id">
                        <div style="height:85%">
                          <h6 class="mb-0 more-omit-2">
                            【{{ blogArticle.blogCategoryVo.name === null ? "笑小枫新作" : blogArticle.blogCategoryVo.name }}】 - {{ blogArticle.title }}
                          </h6>
                          <p class="mb-0 more-omit-3 mt-2 text-sm">
                            {{ blogArticle.description  === null || blogArticle.description === "" ? "连点介绍都没有，还是直接去看详情吧~" : blogArticle.description}}
                            <br/>
                          </p>
                        </div>
                      </a>
                      <div>
                        <div class="row">
                          <div class="col-auto">
                            <span>{{ formatDateYYYYMMDD(blogArticle.createTime) }}发布</span>
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
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="hr-3"/>
              </div>
            </div>
          </section>
        </div>
        <p class="text-center" v-if="loading">加载中...</p>
        <p class="text-center" v-if="noMore">已经到底啦</p>
      </div>
    </div>
    <div class="col-3" v-show="textDark">
      <div class="sticky-10">
        <section>
          <div class="card card-body blur shadow-blur">
            <h6><i class="fas fa-fire-alt text-warning"></i> 热门文章</h6>
            <div class="more-omit-1" v-for="(hotArticle, index) in hotBlogArticleList" :key="index">
              <hr class="hr-2"/>
              <a :href="'/article/'+ hotArticle.id">
                <span class="text-sm">
                  <span :class="index < 3 ? 'text-danger' : ''">{{index + 1}}.</span> {{ hotArticle.title}}
                </span>
              </a>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>
