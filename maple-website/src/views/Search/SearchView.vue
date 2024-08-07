<template>
  <BaseLayout
      :breadcrumb="[
        { label: '首页', route: '/' },
        { label: '搜索详情' },
    ]"
  >

    <div class="mx-auto" :class="textDark ? 'col-8' : 'col-12'">
      <div class="card card-body blur shadow-blur">
        <div class="container">
          <div class="row">
            <div class="col-lg-6">
              <h3 class="mt-2 mb-2">搜索"<span class="text-danger">{{ searchResultParam }}</span>"的结果</h3>
            </div>
            <div class="col-lg-6 row">
              <div class="col-lg-8 mx-auto">
                <div class="input-group input-group-dynamic">
                  <input
                      type="text"
                      class="form-control form-control-md"
                      v-model="searchParam.query.title"
                      placeholder="请输入关键字搜索"
                  />
                </div>
              </div>
              <div class="col-lg-4">
                <button class="btn bg-gradient-success" v-on:click="searchClick()">
                  <span class="iconfont icon-sousuo"/>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
        <div class="card card-body blur shadow-blur">
          <section>
            <div class="container">
              <div class="row" v-for="search in searchList">
                <div class="col-12 mx-auto" v-show="search.type === 0">
                  <div class="row">
                    <div class="col-lg-12 position-relative mx-auto">
                      <a :href="'/category/'+search.id">
                        <div class="d-flex">
                          <h6 class="mb-0 more-omit-2" v-html="search.title">
                          </h6>
                          <el-tag class="mx-2">目录</el-tag>
                        </div>
                        <p class="mb-0 more-omit-3 mt-2"
                           v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
                        </p>
                      </a>
                    </div>
                  </div>
                  <hr class="hr-3"/>
                </div>

                <div class="col-12 mx-auto" v-show="search.type === 1">
                  <div class="row">
                    <div class="col-lg-12 position-relative mx-auto">
                      <a :href="'/article/'+search.id">
                        <div class="d-flex">
                          <h6 class="mb-0 more-omit-2" v-html="search.title">
                          </h6>
                          <el-tag class="mx-2" type="success">文章</el-tag>
                        </div>
                        <p class="mb-0 more-omit-3 mt-2"
                           v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
                        </p>
                      </a>
                    </div>
                  </div>
                  <hr class="hr-3"/>
                </div>

                <div class="col-12 mx-auto" v-show="search.type === 2">
                  <div class="row">
                    <div class="col-lg-12 position-relative mx-auto">
                      <a :href="'/resource/'+search.id">
                        <div class="d-flex">
                          <h6 class="mb-0 more-omit-2" v-html="search.title">
                          </h6>
                          <el-tag class="mx-2" type="warning">资源</el-tag>
                        </div>
                        <p class="mb-0 more-omit-3 mt-2"
                           v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
                        </p>
                      </a>
                    </div>
                  </div>
                  <hr class="hr-3"/>
                </div>

                <div class="col-12 mx-auto" v-show="search.type === 3">
                  <div class="row">
                    <div class="col-lg-12 position-relative mx-auto">
                      <a v-on:click="jumpWebsite(search.id, search.title, search.originalUrl)">
                        <div class="d-flex">
                          <h6 class="mb-0 more-omit-2" v-html="search.title">
                          </h6>
                          <el-tag class="mx-2" type="danger">链接</el-tag>
                        </div>
                        <p class="mb-0 more-omit-3 mt-2"
                           v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
                        </p>
                      </a>
                    </div>
                  </div>
                  <hr class="hr-3"/>
                </div>

              </div>
            </div>
          </section>
        </div>
        <p class="text-center" v-if="loading">加载中...</p>
        <p class="text-center" v-if="noMore">没有更多的内容了</p>
      </div>
    </div>
  </BaseLayout>
</template>

<script setup>
import {computed, onMounted, ref} from "vue";
import { useRoute } from 'vue-router'
import { search } from "@/api/website"
import { isDesktop } from "@/assets/js/useWindowsWidth";
import { ElMessage, ElMessageBox } from "element-plus";
import { getArticleById } from "../../api/website";
import BaseLayout from "@/layouts/sections/components/BaseLayout.vue";


//获取参数
const route = useRoute()
const searchList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const disabled = computed(() => loading.value || noMore.value);

const textDark = isDesktop();
const searchResultParam = ref(route.query.title);


let searchParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  query: {
    title: route.query.title
  }
});

const handleInfiniteScroll = () => {
  if (searchParam.value.page.current * searchParam.value.page.size <= searchParam.value.page.total) {
    searchParam.value.page.current = searchParam.value.page.current + 1;
    loading.value = true;
    searchDataClick();
  } else {
    noMore.value = true;
  }
};

const searchClick = () => {
  loading.value = true;
  searchParam.value.page.current = 1;
  searchList.value = [];
  searchDataClick();
}


const searchDataClick = () => {
  if (!searchParam.value.query.title) {
    ElMessage.error('请输入搜索关键字');
    return;
  }
  search(searchParam.value).then(res => {
    searchResultParam.value = searchParam.value.query.title;
    searchParam.value.page.total = res.total;
    let list = res.records;
    for (let article of list) {
      searchList.value.push(article);
    }
    loading.value = false;
  });
}

const jumpWebsite = (id, name, originalUrl) => {
  ElMessageBox.confirm(
      '您即将离开本站，前往：<span style="color: teal">' + originalUrl + '</span>',
      '请注意您的账号和财产安全',
      {
        dangerouslyUseHTMLString: true,
        confirmButtonText: '继续前往',
        cancelButtonText: '取消前往',
        type: 'warning',
      }
  ).then(() => {
    getArticleById(id).then(() => {
      window.open(originalUrl, '_blank')
    })
  }).catch(() => {

  })
}

// hook
onMounted(() => {
  searchDataClick();
});

</script>
