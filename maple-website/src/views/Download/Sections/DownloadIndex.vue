<script setup>
import { computed, onMounted, reactive, ref } from "vue";
import { getPageArticle, downResource } from "@/api/website";
import { getDictByCode } from "@/api/common";
import { isDesktop } from "@/assets/js/useWindowsWidth";
import setNavPills from "@/assets/js/nav-pills.js";
import defaultImage from "@/assets/img/defaultImage.jpg";
import wxImage from "@/assets/img/wx.jpg";

onMounted(() => {
  setNavPills();
  getPageResourceClick();
  getDictByCodeClick();
});

const resourceType = ref([]);

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
    dataType: 2,
    dataKind: '',
    categoryId: undefined,
    title: undefined,
  }
});

const handleClick = (tab, event) => {
  searchResourceClick(tab.props.name)
}

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
    resourceParam.value.query.dataKind = typeValue;
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
              <el-tabs v-model="resourceParam.query.dataKind" class="data-type-tabs" @tab-click="handleClick">
                <el-tab-pane label="全部" name=""/>
                <div v-for="typeItem in resourceType">
                  <el-tab-pane :label="typeItem.label" :name="typeItem.value" />
                </div>
              </el-tabs>
            </div>
          </div>
            
          <div class="col-lg-6 mx-auto">
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
              <span class="iconfont icon-sousuo"/> 搜索
            </button>
          </div>
        </div>
      </div>
    </section>
    <section class="py-2">
      <div class="mx-auto row" :class="textDark ? 'col-11' : 'col-12'">
        <div :class="textDark ? 'col-9' : 'col-12'">
          <div v-infinite-scroll="handleInfiniteScroll" :infinite-scroll-disabled="disabled">
            <div class="container">
              <div class="row" v-for="resource in resourceList">
                <div class="col-12 mx-auto">
                  <div class="row">
                    <div class="col-lg-3 position-relative mx-auto">
                      <img
                          :src="resource.img === null ? defaultImage : resource.img"
                          alt="图片去找笑小枫啦"
                          class="shadow border-radius-lg"
                          loading="lazy"
                          style="width: 100%; height: 120px;"
                      />
                    </div>
                    <div class="position-relative mx-auto" :class="textDark ? 'col-lg-8' : 'col-lg-9'">
                      <div style="height: 85%;">
                        <h6 class="mb-0 more-omit-3">
                          {{ resource.title }}
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
                          {{ resource.description === null ? '哎呀，没有介绍哎~要不先下载试试' :  resource.description }}
                          <br/>
                        </p>
                      </div>
                      <div>
                        <a v-on:click="downResourceClick(resource.id, resource.originalUrl)" class="text-success icon-move-left" style="cursor: pointer;">
                          <span class="iconfont icon-xiazai"/>
                          <span v-if="resource.isDownload">点击下载(已下载)</span>
                          <span v-else>立即下载</span>
                        </a>
                        <a :href="'/resource/'+resource.id"  class="text-success icon-move-left mx-4">
                          <span class="iconfont icon-yulan"/>
                          点击预览
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
                <hr class="hr-3"/>
              </div>
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
    </section>
  </div>
</template>

<style scoped>
.data-type-tabs {
  height: 3rem !important
}
.el-tab-pane {
  height: 0 !important;
}
</style>