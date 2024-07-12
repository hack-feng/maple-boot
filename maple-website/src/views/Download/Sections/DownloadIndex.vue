<script setup>
import { computed, onMounted, reactive, ref } from "vue";
import { getPageArticle, downResource } from "@/api/website";
import { isDesktop } from "@/assets/js/useWindowsWidth";
import setNavPills from "@/assets/js/nav-pills.js";
import defaultImage from "@/assets/img/defaultImage.jpg";
import wxImage from "@/assets/img/wx.jpg";

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

const textDark = isDesktop();
const downloadLink = ref(null);
const resourceList = ref([]);
const loading = ref(false);
const noMore = ref(false);
const disabled = computed(() => loading.value || noMore.value);


let resourceParam = ref({
  page: {
    current: 1,
    size: 10,
    total: 100
  },
  query: {
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
    resourceParam.value.query.resourceType = typeValue;
  }
  resourceList.value = [];
  getPageResourceClick();
}

const getPageResourceClick = () => {
  getPageArticle(resourceParam.value).then(res => {
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
  <div class="card card-body blur shadow-blur mx-md-4 mt-n6">
    <section>
      <div class="container">
        <div class="row">
          <div class="col-lg-6 mt-2">
            <h3>资源列表</h3>
          </div>
        </div>
    
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
                v-model="resourceParam.query.resourceName"
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
    </section>
  </div>

  <div class="mx-auto row" :class="textDark ? 'col-10' : 'col-12'">
    <div :class="textDark ? 'col-9' : 'col-12'">
      <div v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
        <div class="card card-body blur shadow-blur mt-1">
          <section>
            <div class="container">
              <div class="row" v-for="resource in resourceList">
                <div class="col-12 mx-auto">
                  <div class="row">
                    <div class="col-lg-3 position-relative mx-auto">
                      <img
                          :src="resource.resourceImg === null ? defaultImage : resource.resourceImg"
                          alt="图片去找笑小枫啦"
                          class="shadow border-radius-lg"
                          loading="lazy"
                          style="width: 100%; height: 120px;"
                      />
                    </div>
                    <div class="position-relative mx-auto" :class="textDark ? 'col-lg-8' : 'col-lg-12'">
                      <div style="height: 85%;">
                        <h6 class="mb-0 more-omit-3">
                          {{ resource.resourceName }}
                        </h6>
                        <div class="row mt-2 mb-2 text-sm">
                          <div class="col-auto">
                            <span>下载次数 </span>
                            <span class="h6 me-1">{{ resource.downloadNum }}</span>
                          </div>
                          <div class="col-auto">
                            <span>收藏次数 </span>
                            <span class="h6 me-1">{{ resource.collectNum }}</span>
                          </div>
                        </div>
                        <p class="mb-0 more-omit-2 position-relative text-sm">
                          {{ resource.resourceDesc === null ? '哎呀，没有介绍哎~要不先下载试试' :  resource.resourceDesc }}
                          <br/>
                        </p>
                      </div>
                      <div>
                        <a v-on:click="downResourceClick(resource.id, resource.downUrl)" class="text-success icon-move-left" style="cursor: pointer;">
                            <i class="fa fa-arrow-circle-o-down text-sm ms-1"></i>
                            <span v-if="resource.isDownload">点击下载(已下载)</span>
                            <span v-else-if="resource.needPoints !== 0">点击下载({{ resource.needPoints }}🍁)</span>
                            <span v-else>免费下载</span>
                          </a>
                          <a :href="'/resource/'+resource.id"  class="text-success icon-move-left mx-4">
                            <i class="fa fa-search text-sm ms-1"></i>
                            点击预览
                          </a>
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
    <div class="col-3 mt-2" v-show="textDark">
      <div class="sticky-10">
        <section>
          <div class="card card-body blur shadow-blur">
            <h6><i class="fas fa-heart text-danger"></i> 温馨提示</h6>
            <span>
              如在下载过程中遇到问题<br/>
              或者需要获取枫叶🍁<br/>
              请添加笑小枫微信咨询<br/>
              
            </span>
            <img :src="wxImage"
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
</template>
