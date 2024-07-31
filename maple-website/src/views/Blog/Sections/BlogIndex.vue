<template>
  <div class="card card-body blur shadow-blur mx-3 mx-md-4 mt-n6 mb-4">
    <section class="py-1">
      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <h3 class="mb-4">精品分类</h3>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-3 col-sm-6" v-for="category in state.categoryList">
            <TransparentBlogCard
                :image="category.icon === null ? defaultImage : category.icon"
                :title="category.name"
                :description="category.description"
                :action="{label:'前往阅读', color: 'success', route: '/category/'+category.id }"
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
              <h3>最新资讯</h3>
            </div>
            <div class="col-lg-6 mx-auto">
              <div class="input-group input-group-dynamic">
                <input
                  type="text"
                  class="form-control form-control-md"
                  v-model="articleParam.query.description"
                  placeholder="请输入关键字搜索"
                />
              </div>
            </div>
            <div class="col-lg-2">
              <button class="btn bg-gradient-success" v-on:click="getPageArticleClick(true)">
                <span class="iconfont icon-sousuo"/> 搜索
              </button>
            </div>
          </div>
        </div>
      </div>
    
      <div v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
        <div class="card card-body blur shadow-blur">
          <section>
            <BlogArticleList :articleList="articleList"/>
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
            <div class="more-omit-1" v-for="(hotArticle, index) in hotArticleList" :key="index">
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

<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import TransparentBlogCard from "@/examples/cards/blogCards/TransparentBlogCard.vue";
import BackgroundBlogCard from "@/examples/cards/blogCards/BackgroundBlogCard.vue";
import BlogArticleList from "./BlogArticleList.vue";
import {ElMessageBox} from "element-plus";
import { getPageCategory, getPageArticle } from "@/api/website"
import { isDesktop } from "@/assets/js/useWindowsWidth";
import post4 from "@/assets/img/examples/blog2.jpg";
import defaultImage from "@/assets/img/defaultImage.jpg";


// 定义父组件传过来的值
const props = defineProps({
  menuPath: {
    type: String,
    default: () => "",
  }
});

onMounted(() => {
  getCategoryClick();
  getPageArticleClick(true);
  getHotTitleClick();
});

const textDark = isDesktop();
const articleList = ref([]);
const hotArticleList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const disabled = computed(() => loading.value || noMore.value);

const state = reactive({
  categoryList: {}
})

let articleParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  query: {
    isNew: true,
    menuPath: props.menuPath,
    description: undefined
  }
});

const getCategoryClick = () => {
  let categoryParam = {
    page: {
      current: 1,
      size: 3
    },
    query: {
      menuPath: props.menuPath,
      isTop: true
    }
  }
  getPageCategory(categoryParam).then(res => {
    state.categoryList = res.records
  });
};

const handleInfiniteScroll = () => {
  if (articleParam.value.page.current * articleParam.value.page.size <= articleParam.value.page.total) {
    articleParam.value.page.current = articleParam.value.page.current + 1;
    loading.value = true;
    getPageArticleClick(false);
  } else {
    noMore.value = true;
  }
};

const getPageArticleClick = (isRefreshList) => {
  if(isRefreshList === true) {
    articleParam.value.page.current = 1
    articleList.value = [];
  }
  getPageArticle(articleParam.value).then(res => {
    articleParam.value.page.total = res.total;
    articleList.value.push(...res.records);
    loading.value = false;
  });
}

const getHotTitleClick = () => {
  let hotArticleParam = {
    page: {
      current: 1,
      size: 10
    },
    query: {
      menuPath: props.menuPath,
      isHot: true
    }
  }
  getPageArticle(hotArticleParam).then(res => {
    hotArticleList.value = res.records;
  });
}
</script>