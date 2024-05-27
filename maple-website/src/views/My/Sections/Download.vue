<script setup>
import {computed, onMounted, reactive, ref} from "vue";
import setNavPills from "@/assets/js/nav-pills.js";

import {getPageResource, getResourceById, downResource} from "@/api/download"


onMounted(() => {
  setNavPills();
  getPageResourceClick();
});

const resourceType = ref([
  {"item": "全部", "value": ""}, 
  {"item": "开发资源", "value": "3"}, 
  {"item": "简历模板", "value": "1"}, 
  {"item": "面试资源", "value": "2"}
]);

const downloadLink = ref(null);
const resourceList = ref([]);
const loading = ref(false);
const noMore = ref(false);


let resourceParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  model: {
    isDownload: true,
    resourceType: undefined,
    resourceName: undefined,
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

const searchResourceClick = (typeValue) => {
  loading.value = true;
  resourceParam.value.page.current = 1;
  if(typeValue !== undefined){
    resourceParam.value.model.resourceType = typeValue;
  }
  resourceList.value = [];
  getPageResourceClick();
}

const getPageResourceClick = () => {
  getPageResource(resourceParam.value).then(res => {
    resourceParam.value.page.total = res.total;
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

</script>
<template>
  <section>
    <div class="card card-body blur shadow-blur mx-md-3">
      <div class="container">
        <div class="row mt-2">
          <div class="col-lg-4">
            <div class="nav-wrapper position-relative end-0">
              <ul class="nav nav-pills nav-fill p-1">
                <li class="nav-item" v-for="typeItem in resourceType">
                  <a
                    class="nav-link mb-0 px-0 py-1"
                    :class="typeItem.value === '' ? 'active' : ''"
                    data-bs-toggle="tab"
                    href="#"
                    v-on:click="searchResourceClick(typeItem.value)"
                    :aria-controls="typeItem.value"
                    aria-selected="true"
                  >
                    {{typeItem.item}}
                  </a>
                </li>
              </ul>
            </div>
          </div>
            
          <div class="col-lg-6 mx-auto">
            <div class="input-group input-group-dynamic">
              <input
                type="text"
                class="form-control form-control-md"
                v-model="resourceParam.model.resourceName"
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
        <div class="row" v-for="resource in resourceList">
          <div class="col-12 mx-auto">
            <div class="row">
              <div class="col-lg-12 position-relative mx-auto">
                <h6 class="mb-0 more-omit-3">
                  {{ resource.resourceName }}
                </h6>
                <div class="row mb-1">
                  <div class="col-auto">
                    <span>下载次数 </span>
                    <span class="h6 me-1">{{ resource.downloadNum }}</span>
                  </div>
                  <div class="col-auto">
                    <span>收藏次数 </span>
                    <span class="h6 me-1">{{ resource.collectNum }}</span>
                  </div>
                </div>
                <p class="mb-0 more-omit-3">
                  {{ resource.resourceDesc }}
                  <br/>
                </p>
                <a href="#" v-on:click="downResourceClick(resource.id, resource.downUrl)" class="text-success icon-move-left">
                  <i class="fa fa-arrow-circle-o-down text-sm ms-1"></i>
                  <span v-if="resource.isDownload">点击下载(已下载)</span>
                  <span v-else>点击下载({{ resource.needPoints }}🍁)</span>
                </a>
                <a :href="'/resource/'+resource.id"  class="text-success icon-move-left mx-4">
                  <i class="fa fa-search text-sm ms-1"></i>
                  点击预览
                </a>
              </div>
            </div>
          </div>
          <hr class="hr-3"/>
        </div>
      </div>
    </div>
    <div  class="text-center">
      <el-button type="info" size="small" plain  v-if="!noMore" v-on:click="handleInfiniteScroll()">加载更多</el-button>
      <p class="text-center" v-if="loading">加载中...</p>
      <p class="text-center" v-if="noMore">已经到底啦</p>
    </div>
  </section>
</template>
