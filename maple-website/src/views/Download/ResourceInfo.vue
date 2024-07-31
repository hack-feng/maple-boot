<template>
  <Meta v-if="state.isGetData" :webMenuInfo="resourceInfo" :key="route.params.id"/>
  <BaseLayout
      :breadcrumb="[
        { label: '小枫资源库', route: '/download' },
        { label: '资源详情'},
    ]"
  >
    <div class="col-lg-10 mx-auto">


      <div class="card alignment-container">
        <div class="row mt-2 mx-md-2 mb-4">
          <el-space class="col-lg-3">
            <a class="d-block blur-shadow-image">
              <img
                  :src="resourceInfo.img === null ? defaultImage : resourceInfo.img"
                  :alt="resourceInfo.title"
                  class="img-fluid border-radius-lg img-160" />
            </a>
          </el-space>
          <div class="col-lg-9">
            <div class="position-relative mx-3">
              <div class="row mb-1">
                <div class="d-flex justify-content-between">
                  <h4 class="font-weight-bolder mt-3">
                    {{ resourceInfo.title ? resourceInfo.title : '资源不存在了呢' }}
                  </h4>
                </div>
              </div>
              <div class="row mb-2">
                <div class="col-auto">
                  <span>下载 </span>
                  <span class="h6 me-1">{{ resourceInfo.downloadNum }}</span>
                </div>
                <div class="col-auto">
                  <span>收藏 </span>
                  <span class="h6 me-1">{{ resourceInfo.collectNum }}</span>
                </div>
              </div>
              <p class="pe-5">
                {{ resourceInfo.description }}
              </p>

              <el-button type="success" :plain="!resourceInfo.isDownload" v-show="resourceInfo.id" v-on:click="downResourceClick(resourceInfo.id, resourceInfo.originalUrl)">
                <span class="iconfont icon-xiazai"/>
                <span v-if="resourceInfo.isDownload">点击下载(已下载)</span>
                <span v-else>立即下载</span>
              </el-button>

              <el-button type="warning" :plain="!resourceInfo.isCollect" plain v-show="resourceInfo.id" v-on:click="collectArticleClick()">
                <span class="iconfont icon-shoucang"/>
                <span v-if="!resourceInfo.isCollect">我要收藏</span>
                <span v-else-if="resourceInfo.isCollect">已收藏</span>
              </el-button>
            </div>
          </div>
        </div>
      </div>
      
      <div class="row relevanceContent" v-if="resourceInfo.relevanceContent" >
        <el-divider>
          <h4>使用教程</h4>
        </el-divider>
        <div :class="textDark ? 'col-lg-10' : 'col-lg-12'">
          <div class="card">
            <div class="row mt-2 mx-md-2">
              <MdPreview :editorId="id" :modelValue="resourceInfo.relevanceContent" previewTheme="smart-blue" codeTheme="github"/>
            </div>
          </div>
        </div>
        <div class="col-lg-2" v-show="textDark">
          <div class="card" style="position: sticky; top:80px;">
            <h6 class="mx-md-2">🎨 笑小枫的目录</h6>
            <MdCatalog :editorId="id" :scrollElement="scrollElement"/>
          </div>
        </div>
      </div>
    </div>
  </BaseLayout>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import { useRoute } from 'vue-router'
import { getArticleById, downResource, collectArticle } from "@/api/website"
import { MdPreview, MdCatalog } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import defaultImage from "@/assets/img/defaultImage.jpg";
import BaseLayout from "@/layouts/sections/components/BaseLayout.vue";
import Meta from "@/examples/Meta.vue";
import { isDesktop } from "@/assets/js/useWindowsWidth";


// set nav color on mobile && desktop
const route = useRoute();
const id = 'preview-only';
const scrollElement = document.documentElement;
const textDark = isDesktop();
const state = reactive({
  isGetData: false
});

//获取参数
const resourceInfo = ref({});

const getArticleByIdClick = () => {
  getArticleById(route.params.id).then(res => {
    resourceInfo.value = res;
  });
}


const downResourceClick = async (resourceId, downUrl) => {
  await downResource({"id": resourceId}).then(res => {
    if(downUrl !== null && res != undefined) {
      window.open(downUrl, "_blank");
    }
  });
}

const collectArticleClick = () => {
  const param = ref({
    id: resourceInfo.value.id,
    isCollect: resourceInfo.value.isCollect ? !resourceInfo.value.isCollect : true
  });
  collectArticle(param.value).then(res => {
    resourceInfo.value.isCollect = param.value.isCollect
  });
}

// hook
onMounted(() => {
  getArticleByIdClick();
});

</script>

<style>
.md-editor-preview-wrapper {
  padding: 10px 0px;
}

.relevanceContent {
  margin-top: 80px !important;
  margin-bottom: 20px;
}
</style>
