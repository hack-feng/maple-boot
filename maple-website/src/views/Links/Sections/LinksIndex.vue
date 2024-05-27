<script setup>
import {computed, onMounted, ref} from "vue";

import {getPageLink, addLinkNum} from "@/api/links"

import setNavPills from "@/assets/js/nav-pills.js";

onMounted(() => {
  setNavPills();
  getPageLinkClick();
});

const linksType = ref([
  {"item": "全部", "value": ""}, 
  {"item": "学习地址", "value": "4"}, 
  {"item": "工具链接", "value": "2"}, 
  {"item": "优秀网站", "value": "3"},
  {"item": "友情链接", "value": "1"}
]);


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
  model: {
    linkType: undefined,
    name: undefined,
  }
});

const handleInfiniteScroll = () => {
  if (linksParam.value.page.current * linksParam.value.page.size <= linksParam.value.page.total) {
    linksParam.value.page.current = linksParam.value.page.current + 1;
    loading.value = true;
    getPageLinkClick();
  } else {
    noMore.value = true;
  }
};

const searchClick = (typeValue) => {
  loading.value = true;
  linksParam.value.page.current = 1;
  if(typeValue !== undefined){
    linksParam.value.model.linkType = typeValue;
  }
  linksList.value = [];
  getPageLinkClick();
}

const getPageLinkClick = () => {
  getPageLink(linksParam.value).then(res => {
    linksParam.value.page.total = res.total;
    linksList.value.push(...res.records);
    loading.value = false;
  });
}

const addLinkNumClick = async (linkId) => {
  await addLinkNum(linkId);
}

function toLinkTypeText (typeValue) {
  return typeValue;
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
              <ul class="nav nav-pills nav-fill p-1">
                <li class="nav-item" v-for="typeItem in linksType">
                  <a
                    class="nav-link mb-0 px-0 py-1"
                    :class="typeItem.value === '' ? 'active' : ''"
                    data-bs-toggle="tab"
                    href="#"
                    v-on:click="searchClick(typeItem.value)"
                    :aria-controls="typeItem.value"
                    aria-selected="true"
                  >
                    {{typeItem.item}}
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
                v-model="linksParam.model.name"
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
                    {{ link.name }} 
                    <el-tag v-if="link.linkType == 1">友情链接</el-tag>
                    <el-tag type="warning" v-if="link.linkType == 2">工具链接</el-tag>
                    <el-tag type="warning" v-if="link.linkType == 3">优秀网站</el-tag>
                    <el-tag type="success" v-if="link.linkType == 4">学习地址</el-tag>
                    </h6>
                  </div>
                  <p class="mb-0 more-omit-2 text-sm">
                  {{ link.description }}
                  </p>
                </div>
              </div>
              <div class="mb-0">
                <a :href="link.url" target="_blank">
                  <el-button type="success" size="small" plain><i class="fa fa-paper-plane-o">立即前往</i></el-button>
                </a>
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

