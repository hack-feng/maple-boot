<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import setNavPills from "@/assets/js/nav-pills.js";
import { getPageArticle, downResource } from "@/api/website"
import { getDictByCode } from "@/api/common";
import BlogArticleList from "@/views/Blog/Sections/BlogArticleList.vue"


onMounted(() => {
  setNavPills();
  getPageResourceClick();
  getDictByCodeClick();
});

const downloadLink = ref(null);
const resourceList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const resourceType = ref([]);


let resourceParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  query: {
    isDownload: true,
    dataType: 2,
    dataKind: '',
    title: undefined,
  }
});

const handleInfiniteScroll = () => {
  if (resourceParam.value.page.current * resourceParam.value.page.size <= resourceParam.value.page.total) {
    resourceParam.value.page.current = resourceParam.value.page.current + 1;
    loading.value = true;
    getPageResourceClick();
  } else {
    noMore.value = true;
  }
};

const handleClick = (tab, event) => {
  searchResourceClick(tab.props.name)
}

const searchResourceClick = (typeValue) => {
  loading.value = true;
  resourceParam.value.page.current = 1;
  if(typeValue !== undefined){
    resourceParam.value.query.dataKind = typeValue;
  }
  resourceList.value = [];
  getPageResourceClick();
}

const getPageResourceClick = () => {
  getPageArticle(resourceParam.value).then(res => {
    resourceParam.value.page.total = res.total;
    if(resourceParam.value.page.total === 0){
      noMore.value = true;
    }
    resourceList.value.push(...res.records);
    loading.value = false;
  });
}

const downResourceClick = (resourceId, downUrl) => {
  downResource({"id": resourceId}).then(res => {
    if(downUrl !== null && res != undefined) {
      window.open(downUrl, "_blank");
    }
  });
}

const getDictByCodeClick = () => {
  getDictByCode("web_resource_type").then(res => {
    resourceType.value = res.map(p => ({
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
              <el-tabs v-model="resourceParam.query.dataKind" class="data-type-tabs" @tab-click="handleClick">
                <el-tab-pane label="全部" name=""/>
                <div v-for="typeItem in resourceType">
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
                v-model="resourceParam.query.title"
                placeholder="请输入关键字搜索"
              />
            </div>
          </div>
          <div class="col-lg-2">
            <button class="btn bg-gradient-success" v-on:click="searchResourceClick(undefined)">
              <i class="fas fa-search"></i> 搜索
            </button>
          </div>
        </div>
      </div>
    </div>

    <div class="card card-body blur shadow-blur mx-3 mt-1">
      <div class="container">
        <BlogArticleList :articleList="resourceList"/>
      </div>
    </div>
    <div  class="text-center">
      <el-button type="info" size="small" plain  v-if="!noMore" v-on:click="handleInfiniteScroll()">加载更多</el-button>
      <p class="text-center" v-if="loading">加载中...</p>
      <p class="text-center" v-if="noMore">已经到底啦</p>
    </div>
  </section>
</template>

<style scoped>
.data-type-tabs {
  height: 3rem !important
}
.el-tab-pane {
  height: 0 !important;
}
</style>
