<template>
  <div class="container">
    <div class="row" v-for="blogArticle in props.articleList">
      <div class="col-12 mx-auto"  v-show="blogArticle.dataType === 1">
        <div class="row">
          <div class="col-lg-3 position-relative mx-auto">
            <img
                :src="!blogArticle.img ? defaultImage : blogArticle.img"
                alt="图片找不到了"
                class="shadow border-radius-lg img-130"
                loading="lazy"
            />
          </div>
          <div class="col-lg-9 position-relative mx-auto">
            <a :href="'/article/'+blogArticle.id">
              <div style="height:85%">
                <h6 class="mb-0 more-omit-2">
                  【{{ blogArticle.categoryModel.name === null ? "笑小枫新作" : blogArticle.categoryModel.name }}】 - {{ blogArticle.title }}
                  <el-tag class="mx-2" type="success">文章</el-tag>
                </h6>
                <p class="mb-0 more-omit-3 mt-2 text-sm">
                  {{ blogArticle.description  === null || blogArticle.description === "" ? "连点介绍都没有，还是直接去看详情吧~" : blogArticle.description}}
                  <br/>
                </p>
              </div>
            </a>
            <div class="row">
              <div class="col-auto">
                <span>{{ formatDateYYYYMMDD(blogArticle.createTime) }}发布</span>
              </div>
              <div class="col-auto">
                <span>阅读 </span>
                <span class="me-1">{{ blogArticle.readNum }}</span>
              </div>
              <div class="col-auto">
                <span>收藏 </span>
                <span class="me-1">{{ blogArticle.collectNum }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-12 mx-auto"  v-show="blogArticle.dataType === 2">
        <div class="row">
          <div class="col-lg-3 position-relative mx-auto">
            <img
                :src="!blogArticle.img ? defaultImage : blogArticle.img"
                alt="图片找不到了~"
                class="shadow border-radius-lg img-130"
                loading="lazy"
            />
          </div>
          <div class="col-lg-9 position-relative mx-auto">
            <a :href="'/resource/'+blogArticle.id">
              <div style="height:85%">
                <h6 class="mb-0 more-omit-2">
                  【{{ blogArticle.categoryModel.name === null ? "笑小枫新作" : blogArticle.categoryModel.name }}】 - {{ blogArticle.title }}
                  <el-tag class="mx-2" type="warning">资源</el-tag>
                </h6>
                <p class="mb-0 more-omit-3 mt-2 text-sm">
                  {{ blogArticle.description  === null || blogArticle.description === "" ? "哎呀，没有介绍哎~要不先下载试试" : blogArticle.description}}
                  <br/>
                </p>
              </div>
            </a>
            <div>
              <div class="row">
                <div class="col-auto">
                  <span>{{ formatDateYYYYMMDD(blogArticle.createTime) }}发布</span>
                </div>
                <div class="col-auto">
                  <span>下载 </span>
                  <span class="me-1">{{ blogArticle.downloadNum }}</span>
                </div>
                <div class="col-auto">
                  <span>收藏 </span>
                  <span class="me-1">{{ blogArticle.collectNum }}</span>
                </div>
                <div class="col-auto">
                  <a class="text-success icon-move-left" style="cursor: pointer;">
                    <span class="iconfont icon-xiazai"/>
                    <span v-if="blogArticle.isDownload">点击下载(已下载)</span>
                    <span v-else>立即下载</span>
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-12 mx-auto"  v-show="blogArticle.dataType === 3">
        <div class="row">
          <div class="col-lg-3 position-relative mx-auto">
            <img
                :src="!blogArticle.img ? defaultImage : blogArticle.img"
                alt="图片去找笑小枫啦"
                class="shadow border-radius-lg img-130"
                loading="lazy"
            />
          </div>
          <div class="col-lg-9 position-relative mx-auto">
            <a v-on:click="jumpWebsite(blogArticle.id, blogArticle.title, blogArticle.originalUrl)">
              <div style="height:85%">
                <h6 class="mb-0 more-omit-2">
                  {{ blogArticle.title }}
                  <el-tag class="mx-2" type="danger">链接</el-tag>
                </h6>
                <p class="mb-0 more-omit-3 mt-2 text-sm">
                  {{ blogArticle.description  === null || blogArticle.description === "" ? "连点介绍都没有，还是直接去看看吧~" : blogArticle.description}}
                  <br/>
                </p>
              </div>
            </a>
            <div>
              <div class="row">
                <div class="col-auto">
                  <span>{{ formatDateYYYYMMDD(blogArticle.createTime) }}发布</span>
                </div>
                <div class="col-auto">
                  <span>点击次数 </span>
                  <span class="me-1">{{ blogArticle.readNum }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <hr class="hr-3"/>
    </div>
  </div>
</template>

<script setup>

import {formatDateYYYYMMDD} from '@/utils/maple'
import { getArticleById } from "@/api/website"

import defaultImage from "@/assets/img/defaultImage.jpg";
import {ElMessageBox} from "element-plus";

// 定义父组件传过来的值
const props = defineProps({
  articleList: {
    type: Array,
    default: () => [],
  }
});

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

<style scoped>

</style>