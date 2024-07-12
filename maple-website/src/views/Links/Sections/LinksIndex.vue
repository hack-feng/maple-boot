<script setup>
import {computed, onMounted, ref} from "vue";
import { getPageArticle, getArticleById } from "@/api/website"
import { getDictByCode } from "@/api/common"

import setNavPills from "@/assets/js/nav-pills.js";
import {ElMessageBox} from "element-plus";

onMounted(() => {
  setNavPills();
  getPageArticleClick();
  getDictByCodeClick();
});

const linksType = ref([]);

const linksList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const disabled = computed(() => loading.value || noMore.value);

let linksParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  query: {
    dataType: 3,
    categoryId: undefined,
    title: undefined,
  }
});

const handleInfiniteScroll = () => {
  if (linksParam.value.page.current * linksParam.value.page.size <= linksParam.value.page.total) {
    linksParam.value.page.current = linksParam.value.page.current + 1;
    loading.value = true;
    getPageArticleClick();
  } else {
    noMore.value = true;
  }
};

const searchClick = (typeValue) => {
  loading.value = true;
  linksParam.value.page.current = 1;
  if(typeValue !== undefined){
    linksParam.value.query.categoryId = typeValue;
  }
  linksList.value = [];
  getPageArticleClick();
}

const getPageArticleClick = () => {
  getPageArticle(linksParam.value).then(res => {
    linksParam.value.page.total = res.total;
    linksList.value.push(...res.records);
    loading.value = false;
  });
}

const activeName = ref('')

const handleClick = (tab, event) => {
  searchClick(tab.value)
}

const getDictByCodeClick = () => {
  getDictByCode("web_friendly_link_type").then(res => {
    linksType.value = res.map(p => ({
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
    getArticleById(id).then(res => {
      window.open(originalUrl, '_blank')
    })
  }).catch(() => {

  })
}

</script>
<template>
  <div class="card card-body blur shadow-blur mx-md-4 mt-n6">
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-6 mt-2">
            <h3>链接列表</h3>
          </div>
        </div>
    
        <div class="row mt-4">
          <div class="col-lg-5">
            <div class="nav-wrapper position-relative end-0">
              <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
                <el-tab-pane label="全部" name=""/>
                <div v-for="typeItem in linksType">
                  <el-tab-pane :label="typeItem.label" :name="typeItem.value" />
                </div>
              </el-tabs>
              
              <ul class="nav nav-pills nav-fill p-1">
                <li class="nav-item" v-show="linksType !== {}">
                  <a
                      class="nav-link mb-0 px-0 py-1"
                      data-bs-toggle="tab"
                      href="#"
                      v-on:click="searchClick('')"
                      aria-controls=""
                      aria-selected="true"
                  >
                    全部
                  </a>
                </li>
                <li class="nav-item" v-for="typeItem in linksType">
                  <a
                    class="nav-link mb-0 px-0 py-1"
                    data-bs-toggle="tab"
                    href="#"
                    v-on:click="searchClick(typeItem.value)"
                    :aria-controls="typeItem.value"
                    aria-selected="true"
                  >
                    {{typeItem.label}}
                  </a>
                </li>
              </ul>
            </div>
          </div>
            
          <div class="col-lg-5 mx-auto">
            <div class="input-group input-group-dynamic">
              <input
                type="text"
                class="form-control form-control-md"
                v-model="linksParam.query.title"
                placeholder="请输入关键字搜索"
              />
            </div>
            
          </div>
          <div class="col-lg-2">
            <button class="btn bg-gradient-success" v-on:click="searchClick(undefined)">
              <i class="fas fa-search"></i> 搜索
            </button>
          </div>
        </div>
      </div>
    </section>
    <section class="py-2">
      <div v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
        <div class="container">
          <div class="row">
            <div class="col-lg-3 col-sm-6"  v-for="link in linksList">
              <div class="card card-plain" style="height:62%">
                <div class="px-1">
                  <div class="row">
                    <h6 class="mt-2 mb-1 more-omit-1">
                    {{ link.title }} 
                    <el-tag :type="linksType[link.dataClass].elTagType">{{linksType[link.dataClass].label}}</el-tag>
                    </h6>
                  </div>
                  <p class="mb-0 more-omit-2 text-sm">
                  {{ link.description }}
                  </p>
                </div>
              </div>
              <div class="mb-0">
                <el-button type="success" size="small" plain v-on:click="jumpWebsite(link.id, link.title, link.originalUrl)"><i class="fa fa-paper-plane-o">立即前往</i></el-button>
                <hr class="hr-3"/>
              </div>
            </div>
          </div>
        </div>
        <p class="text-center" v-if="loading">加载中...</p>
        <p class="text-center" v-if="noMore">已经到底啦</p>
      </div>
    </section>
  </div>


  
</template>

