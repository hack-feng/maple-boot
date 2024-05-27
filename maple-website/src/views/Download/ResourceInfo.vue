<script setup>
import {onMounted, onUpdated, ref} from "vue";
import { useRoute } from 'vue-router'

import { getResourceById, downResource, collectResource } from "@/api/download"

import { MdPreview, MdCatalog } from 'md-editor-v3';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';

// Sections components
import BaseLayout from "@/layouts/sections/components/BaseLayout.vue";
import { isDesktop } from "@/assets/js/useWindowsWidth";
import {useMeta} from "vue-meta";


// set nav color on mobile && desktop
const route = useRoute();
const id = 'preview-only';
const scrollElement = document.documentElement;
const textDark = isDesktop();

//获取参数
const resourceInfo = ref({});

const getResourceByIdClick = () => {
  getResourceById(route.params.id).then(res => {
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

const collectResourceClick = () => {
  const param = ref({
    id: resourceInfo.value.id,
    isCollect: resourceInfo.value.isCollect ? !resourceInfo.value.isCollect : true
  });
  collectResource(param.value).then(res => {
    resourceInfo.value.isCollect = param.value.isCollect
  });
}

// hook
onMounted(() => {
  getResourceByIdClick();
});

onUpdated(() => {
  useMeta({
    title: resourceInfo.value.resourceName ? resourceInfo.value.resourceName + '- 笑小枫' : '资源详情 - 笑小枫',
    meta: [
      {name: 'keywords', content: '笑小枫,java,SpringBoot,程序员'},
      {
        name: 'description',
        content: resourceInfo.value.resourceDesc && resourceInfo.value.resourceDesc !== ''
            ? resourceInfo.value.resourceDesc : '笑小枫拥有海量的资源，包括教程、案例、工具、代码库等，为您的学习和实践提供了全面的支持。'
      }
    ]
  })
})

</script>
<template>
  <BaseLayout
      :breadcrumb="[
        { label: '小枫资源库', route: '/download' },
        { label: '资源详情', route: '/resource/' + resourceInfo.id },
    ]"
  >
    <div class="col-lg-10 mx-auto">
      <div class="text-center">
        <h4>
          {{ resourceInfo.resourceName ? resourceInfo.resourceName : '资源不存在了呢' }}
        </h4>

        <p>
          {{ resourceInfo.resourceDesc }}
        </p>

        <el-button type="success" :plain="!resourceInfo.isDownload" v-show="resourceInfo.id" v-on:click="downResourceClick(resourceInfo.id, resourceInfo.downUrl)">
          <i class="fa fa-arrow-circle-o-down"></i>
          <span v-if="resourceInfo.isDownload">点击下载(已下载)</span>
          <span v-else-if="resourceInfo.needPoints !== 0">点击下载({{ resourceInfo.needPoints }}🍁)</span>
          <span v-else>免费下载</span>
        </el-button>
        
        <el-button type="warning" :plain="!resourceInfo.isCollect" plain v-show="resourceInfo.id" v-on:click="collectResourceClick()">
          <i class="fa fa-star-o"></i>
          <span v-if="!resourceInfo.isCollect">我要收藏</span>
          <span v-else-if="resourceInfo.isCollect">已收藏</span>
        </el-button>

      </div>
      <div class="row" v-if="resourceInfo.originalContent">
        <div :class="textDark ? 'col-lg-10' : 'col-lg-12'">
          <div class="card">
            <div class="row mt-2 mx-md-2">
              <MdPreview :editorId="id" :modelValue="resourceInfo.originalContent" previewTheme="smart-blue" codeTheme="github"/>
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

<style>
.md-editor-preview-wrapper {
  padding: 10px 0px;
}
</style>
