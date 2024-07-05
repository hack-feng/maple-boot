<script setup>
import {onMounted, ref, getCurrentInstance, watch, onUpdated} from "vue";
import { useRoute } from 'vue-router'
import { getArticleById, collectArticle, likeArticle } from "@/api/website"
import {formatDateYYYYMMDD} from '@/utils/maple'

import { MdPreview, MdCatalog } from 'md-editor-v3';
// preview.css相比style.css少了编辑器那部分样式
import 'md-editor-v3/lib/preview.css';

// Sections components
import BaseLayout from "@/layouts/sections/components/BaseLayout.vue";
import { isDesktop } from "@/assets/js/useWindowsWidth";
import { useMeta } from 'vue-meta'

import Comment from '@/components/Comment.vue'


//获取参数
const route = useRoute()
const articleInfo = ref({
  articleContent:{}, 
  categoryVo: {
    name: undefined
  }
});
const commentData = ref([]);
const drawer = ref(false)
const articleContent = ref({});
const categoryVo = ref({name: undefined});
const nextArticle = ref({});
const preArticle = ref({});
const aboutArticleList = ref([]);
const textDark = isDesktop();

const id = 'preview-only';
const scrollElement = document.documentElement;

const getArticleByIdClick = () => {
  getArticleById(route.params.id).then(res => {
    articleInfo.value = res;
    articleContent.value = res.contentModel ? res.contentModel : {};
    categoryVo.value = res.categoryModel ? res.categoryModel : {};
    nextArticle.value = res.nextTitle;
    preArticle.value = res.preTitle;
    aboutArticleList.value = res.aboutArticle;
  });
}

const likeArticleClick = () => {
  const param = ref({
    id: articleInfo.value.id,
    isLike: articleInfo.value.isLike ? !articleInfo.value.isLike : true
  });
  likeArticle(param.value).then(res => {
    articleInfo.value.isLike = param.value.isLike
    if(param.value.isLike === true) {
      articleInfo.value.likeNum = articleInfo.value.likeNum ? articleInfo.value.likeNum + 1 : 1
    } else {
      articleInfo.value.likeNum = articleInfo.value.likeNum - 1
    }
  });
}

const collectArticleClick = () => {
  const param = ref({
    id: articleInfo.value.id,
    isCollect: articleInfo.value.isCollect ? !articleInfo.value.isCollect : true
  });
  collectArticle(param.value).then(res => {
    articleInfo.value.isCollect = param.value.isCollect
    if(param.value.isCollect === true) {
      articleInfo.value.collectNum = articleInfo.value.collectNum ? articleInfo.value.collectNum + 1:1
    } else {
      articleInfo.value.collectNum = articleInfo.value.collectNum - 1
    }
    
  });
}

// hook
onMounted(() => {
  getArticleByIdClick();
});

onUpdated(() => {
  useMeta({
    title: articleInfo.value.title ? articleInfo.value.title + '_【' + categoryVo.value.name + '】' + '- 笑小枫' : '文章详情 - 笑小枫' ,
    meta: [
      { name: 'keywords', content: articleInfo.value.keywords ? articleInfo.value.keywords : '笑小枫,java,SpringBoot,程序员' },
      { name: 'description', content: articleInfo.value.description && articleInfo.value.resourceDesc !== '' 
            ? articleInfo.value.description : articleInfo.value.title }
    ]
  })
})

</script>
<template>
  <BaseLayout
      :breadcrumb="[
        { label: '小枫博客录', route: '/blog' },
        { label: '文章目录', route: '/category/'+articleInfo.categoryId },
        { label: '文章详情' },
    ]"
  >
    <div class="mx-auto" :class="textDark ? 'col-lg-10' : 'col-lg-12'">
      <div class="text-center">
        <h3>
          {{ articleInfo.title ? articleInfo.title : '文章不存在了呢'}}
        </h3>
        <div class="row mx-7 mt-lg-4">
          <div class="col-auto" v-if="articleInfo.keywords">
            <span>文章标签： </span>
            <span v-for="keyword in articleInfo.keywords.split(',')">
              <el-tag class="mx-2" type="success">{{ keyword }}</el-tag>
            </span>
          </div>
          <div class="col-auto">
            <span>阅读： </span>
            <span class="me-1" style="color:#669900;">{{ articleInfo.readNum }}</span>
          </div>
          <div class="col-auto">
            <span>收藏： </span>
            <span class="me-1" style="color:#669900;">{{ articleInfo.collectNum }}</span>
          </div>
          <div class="col-auto">
            <span>文章最新于<span style="color:#ff9900;">{{ formatDateYYYYMMDD(articleInfo.updateTime) }}</span>更新</span>
          </div>
        </div>
        <el-divider></el-divider>
      </div>
      <div class="row">
        <div class="col-lg-1" v-show="textDark">
          <div class="sticky-100">
            <div class="mx-auto" >
              <el-badge :value="articleInfo.likeNum > 0 ? articleInfo.likeNum : ''" class="item">
                <el-button class="button-operation" :class="articleInfo.isLike ? 'active' : ''" circle @click="likeArticleClick">
                  <span class="iconfont icon-icon" style="font-size: 2rem;"></span>
                </el-button>
              </el-badge>
              <el-badge :value="articleInfo.commentNum > 0 ? articleInfo.commentNum : ''" class="item">
                <el-button class="button-operation" circle @click="drawer = true">
                  <span class="iconfont icon-pinglun" style="font-size: 2rem;"></span>
                </el-button>
              </el-badge>
              <el-badge :value="articleInfo.collectNum > 0 ? articleInfo.collectNum : ''" class="item">
                <el-button class="button-operation" :class="articleInfo.isCollect ? 'active' : ''" circle @click="collectArticleClick">
                  <span class="iconfont icon-shoucang" style="font-size: 2rem;"></span>
                </el-button>
              </el-badge>
            </div>
          </div>
        </div>
        <el-drawer v-model="drawer" title="评论信息">
          <Comment :dataInfo="articleInfo" :commentType="1"></Comment>
        </el-drawer>
        <div :class="textDark ? 'col-lg-9' : 'col-lg-12'">
          <div class="card">
            <div class="row mt-2 mx-md-2">
              <MdPreview :editorId="id" :modelValue="articleContent === null ? '如有疑问，可以联系笑小枫![image-20240107092237524](https://image.xiaoxiaofeng.site/blog/image/image-20240107092237524.png?xiaoxiaofeng)' : articleContent.originalContent" previewTheme="smart-blue" codeTheme="github"/>
            </div>
          </div>
        </div>
        <div class="col-lg-2" v-show="textDark">
          <div class="card sticky-100" style="max-height: calc(100vh - 120px);">
            <h6 class="mx-md-2">🎨 笑小枫的目录</h6>
            <MdCatalog :editorId="id" :scrollElement="scrollElement" style="overflow:auto;"/>
          </div>
        </div>
      </div>
      <div class="row mt-2 mx-md-2 mb-4">
        <div class="col-lg-5">
          <i class="fas fa-arrow-left text-sm ms-1" v-if="preArticle != null">
            上一篇 <a class="text-info icon-move-right" :href="'/article/' + preArticle.id">{{ preArticle.title}}</a>
          </i>
        </div>
        <div class="col-lg-5">
          <i class="fas fa-arrow-right text-sm ms-1" v-if="nextArticle != null">
            下一篇 <a class="text-info icon-move-right" :href="'/article/' + nextArticle.id">{{ nextArticle.title}}</a>
          </i>
        </div>
      </div>

      <div class="card card-body blur shadow-blur mt-2">
        <div class="row mb-2">
          <div class="col-lg-6">
            <h3 class="mt-2 mb-2">相关文章</h3>
          </div>
          <hr class="hr-1"/>
        </div>
        <section>
          <div class="container">
            <div class="row" v-for="search in aboutArticleList">
              <div class="col-12 mx-auto" v-show="search.type === 1">
                <div class="row">
                  <div class="col-lg-12 position-relative mx-auto">
                    <a :href="'/category/'+search.id">
                      <div class="d-flex">
                        <h6 class="mb-0 more-omit-2" v-html="search.title">
                        </h6>
                        <el-tag class="mx-2">目录</el-tag>
                      </div>
                      <p class="mb-0 more-omit-3 mt-2" v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
                      </p>
                    </a>
                  </div>
                </div>
                <hr class="hr-3"/>
              </div>
  
              <div class="col-12 mx-auto" v-show="search.type === 2">
                <div class="row">
                  <div class="col-lg-12 position-relative mx-auto">
                    <a :href="'/article/'+search.id">
                      <div class="d-flex">
                        <h6 class="mb-0 more-omit-2" v-html="search.title">
                        </h6>
                        <el-tag class="mx-2" type="success">文章</el-tag>
                      </div>
                      <p class="mb-0 more-omit-3 mt-2" v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
                      </p>
                    </a>
                  </div>
                </div>
                <hr class="hr-3"/>
              </div>
  
              <div class="col-12 mx-auto" v-show="search.type === 3">
                <div class="row">
                  <div class="col-lg-12 position-relative mx-auto">
                    <a :href="'/resource/'+search.id">
                      <div class="d-flex">
                        <h6 class="mb-0 more-omit-2" v-html="search.title">
                        </h6>
                        <el-tag class="mx-2" type="warning">资源</el-tag>
                      </div>
                      <p class="mb-0 more-omit-3 mt-2" v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
                      </p>
                    </a>
                  </div>
                </div>
                <hr class="hr-3"/>
              </div>
  
              <div class="col-12 mx-auto" v-show="search.type === 4">
                <div class="row">
                  <div class="col-lg-12 position-relative mx-auto">
                    <a :href="search.url" target="_blank">
                      <div class="d-flex">
                        <h6 class="mb-0 more-omit-2" v-html="search.title">
                        </h6>
                        <el-tag class="mx-2" type="danger">链接</el-tag>
                      </div>
                      <p class="mb-0 more-omit-3 mt-2" v-html="search.description  == null || search.description == '' ? '连点介绍都没有，还是直接去看详情吧~' : search.description">
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
    </div>
  </BaseLayout>
</template>

<style>
.md-editor-catalog-active>span {
    color: #135ce0;
}

.md-editor-preview-wrapper {
  padding: 10px 0px;
}
.button-operation {
  width: 25px;
  border-radius: 50%;
  padding: 25px !important;
  font-size: 1.7rem;
  background-color: #fff8ef;
  text-align: center;
  margin-bottom: 20px;
  color: rgb(188, 125, 125);
}
.button-operation.active{
  background-color: #c6e2ff;
  color: #409eff;
}
.el-button.el-button{
    margin-left: 10px !important;
}

.el-badge__content.is-fixed {
    right: calc(5px + var(--el-badge-size)/ 2);
    transform: translateY(-25%) translateX(100%);
}
</style>
