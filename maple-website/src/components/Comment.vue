<script setup>

import { onMounted, ref } from "vue";
import FormInstance from 'element-plus';
import { ElMessage } from 'element-plus'
import { getPageComment, createComment, deleteComment, likeComment } from "@/api/comment";
import { formatDateYMDHMS } from '@/utils/maple';
import { getUserInfo } from "@/api/login";

const propsData = defineProps({
  dataInfo: {
    type: Object,
    required: true,
  },
  commentType: {
    type: String,
    required: true,
  }
});

onMounted(() => {
  getUserInfoClick();
  getPageCommentClick();
});

const userInfo = ref({});
const commentInfo = ref({
  toUserId: propsData.dataInfo.createId,
    dataId: propsData.dataInfo.id,
    parentId: 0,
    commentType: propsData.commentType,
    content: '',
});
const replyCommentInfo = ref({
  replyName: '',
  parentId: '',
  content: '',
  toUserId: '',
  showItemId: '',
  dataId: propsData.dataInfo.id,
  commentType: propsData.commentType,
});
const replyPage = ref({});
const commentList = ref([]);
const commentItem = ref({});
const commentParam = ref({
  page: {
    current: 0,
    size: 10,
    total: 0
  },
  model: {
    dataId: propsData.dataInfo.id,
    commentType: propsData.commentType,
    topId: 0,
  }
});

const getUserInfoClick = () => {
  getUserInfo().then(res => {
    userInfo.value = res;
  });
}

const getPageCommentClick = () => {
  commentParam.value.page.current = commentParam.value.page.current + 1;
  getPageComment(commentParam.value).then(res => {
    commentParam.value.page.total = res.total;
    commentList.value.push(...res.records);
  });
}

const getReplyPageCommentClick = (item) => {
  const param = ref({
    page: {
      current: replyPage.value[item.id] ? replyPage.value[item.id] : 1,
      size: 10,
      total: 100
    },
    model: {
      dataId: propsData.dataInfo.id,
      commentType: propsData.commentType,
      topId: item.id,
      sortFlag: 1
    }
  });
  getPageComment(param.value).then(res => {
    replyPage.value[item.id] = param.value.page.current + 1;
    if(item.replyList) {
      item.replyList.push(...res.records);
    } else {
      item.replyList = [];
      item.replyList.push(...res.records);
    }
  });
}


/**
 * 提交文章评论
 */
const commitArticleComment = () => {
  createComment(commentInfo.value).then(res => {
    commentInfo.value.content = '';
    commentList.value.unshift(res);
    ElMessage.success("提交评论成功");
  });
}


/**
 * 提交回复评论
 */
const commitComment = (item) => {
  replyCommentInfo.value.content = replyCommentInfo.value.content.replace(replyCommentInfo.value.replyName, "");
  createComment(replyCommentInfo.value).then(res => {
    cancel();
    if(item.replyList) {
      item.replyList.push(res);
    } else {
      item.replyList = [];
      item.replyList.push(res);
    }
    ElMessage.success("提交回复成功");
  });
}

/**
 * 点击评论按钮显示输入框
 * item: 当前大评论
 * reply: 当前回复的评论
 */
const showCommentInput = (item, reply) => {
  replyCommentInfo.value.toUserId = item.userId
  replyCommentInfo.value.parentId = item.id
  replyCommentInfo.value.showItemId = item.id
  if (reply) {
    replyCommentInfo.value.content = "@" + reply.userName + " "
    replyCommentInfo.value.replyName = replyCommentInfo.value.content
    replyCommentInfo.value.toUserId = reply.userId
    replyCommentInfo.value.parentId = reply.id
  } else {
    replyCommentInfo.value.content = ''
    replyCommentInfo.value.replyName = replyCommentInfo.value.content
  }
}

/**
 * 点击取消按钮
 */
 const cancel = () => {
  replyCommentInfo.value.toUserId = ''
  replyCommentInfo.value.parentId = ''
  replyCommentInfo.value.showItemId = ''
}

const deleteClick = (index, item, reply) => {
  deleteComment(reply ? reply.id : item.id).then(res => {
    if (reply) {
      item.replyList.splice(index, 1)
    } else {
      commentList.value.splice(index, 1)
    }
    ElMessage.success("删除成功");
  });
}


/**
 * 点赞
 */
 const likeClick = (item) => {
  if (item.isLike === null) {
    item.isLike = true;
    item.likeNum++
  } else {
    if (item.isLike) {
      item.likeNum--
    } else {
      item.likeNum++
    }
    item.isLike = !item.isLike;
  }
  likeComment(item).then(res => {
    if(item.isLike) {
      ElMessage.success("点赞成功");
    } else {
      ElMessage.success("取消点赞成功");
    }
  });
}
</script>


<!--评论模块-->
<template>
  <div class="container">
    <transition name="fade" class="mb-4">
      <div class="input-wrapper">
        <el-input
                  maxlength=255
                  type="textarea"
                  v-model="commentInfo.content"
                  :rows="3"
                  show-word-limit
                  placeholder="写下你的评论">
        </el-input>
        <div class="btn-control mt-2">
          <el-button type="primary" @click="commitArticleComment()">发布</el-button>
        </div>
      </div>
    </transition>

    <div class="comment" v-for="(item, commentIndex) in commentList" :key="commentIndex">
      <div class="info">
        <img class="avatar" :src="item.headPortraitLink" width="36" height="36"/>
        <div class="right">
          <div class="name">{{item.userName}}</div>
          <div class="date">{{formatDateYMDHMS(item.createTime)}}</div>
        </div>
      </div>
      <div class="content">{{item.content}}</div>
      <div class="control">
        <span class="like" :class="{active: item.isLike}" @click="likeClick(item)">
          <i class="far fa-thumbs-up mx-1"></i>
          <span class="like-num">{{item.likeNum > 0 ? item.likeNum + '人赞' : '赞'}}</span>
        </span>
        <span class="comment-reply" @click="showCommentInput(item)">
          <i class="fa fa-commenting-o mx-1"></i>
          <span>回复</span>
        </span>
        <span class="comment-reply" 
              @click="deleteClick(commentIndex, item)" 
              v-if="userInfo.id === item.userId">
          <i class="fa fa-trash-o mx-1"></i>
          <span>删除</span>
        </span>
      </div>
      <div class="reply">
        <div class="item" v-for="(reply, replyIndex) in item.replyList" :key="replyIndex">
          <div class="reply-content">
            <span class="from-name mx-1">{{reply.userName}}</span>
            <span v-if="reply.topId !== reply.parentId">
              回复 
              <span class="to-name" >{{reply.toUserName}}</span>
            </span>
            <span>：{{reply.content}}</span>
          </div>
          <div class="reply-bottom">
            <span>{{formatDateYMDHMS(reply.createTime)}}</span>
            <span class="like" :class="{active: reply.isLike}" @click="likeClick(reply)">
              <i class="far fa-thumbs-up  mx-1"></i>
              <span class="like-num">{{reply.likeNum > 0 ? reply.likeNum + '人赞' : '赞'}}</span>
            </span>
            <span class="reply-text" @click="showCommentInput(item, reply)">
              <i class="fa fa-commenting-o mx-1"></i>
              <span>回复</span>
            </span>
            <span class="reply-text" 
                  @click="deleteClick(replyIndex, item, reply)" 
                  v-if="userInfo.id === reply.userId">
              <i class="fa fa-trash-o mx-1"></i>
              <span>删除</span>
            </span>
          </div>
        </div>
        <transition name="fade">
          <div class="input-wrapper" v-if="replyCommentInfo.showItemId == item.id">
            <el-input maxlength=255
                      v-model="replyCommentInfo.content"
                      type="textarea"
                      :rows="3"
                      show-word-limit
                      autofocus
                      placeholder="写下你的评论">
            </el-input>
            <div class="btn-control">
              <el-button @click="cancel">取消</el-button>
              <el-button type="primary" @click="commitComment(item)">确定</el-button>
            </div>
          </div>
        </transition>
      </div>
      <div class="reply-more" v-if="item.commentNum > 0 && ((replyPage[item.id] ? replyPage[item.id] * 10 - 10 : 0) < item.commentNum)" @click="getReplyPageCommentClick(item)">
        展开 {{ item.commentNum - (replyPage[item.id] ? replyPage[item.id] * 10 - 10 : 0) }} 条回复
      </div>
    </div>
    <div class="comment-more mx-auto" v-if="commentParam.page.current * commentParam.page.size <= commentParam.page.total" @click="getPageCommentClick()">
      <el-divider>
        查看更多评论信息
      </el-divider>
    </div>
  </div>
</template>


<style scoped lang="scss">

@import "../assets/scss/material-kit";

.container {
  padding: 0 10px;
  box-sizing: border-box;
  .comment {
    display: flex;
    flex-direction: column;
    padding: 10px;
    border-bottom: 1px solid $border-fourth;
    .info {
      display: flex;
      align-items: center;
      .avatar {
        border-radius: 50%;
      }
      .right {
        display: flex;
        flex-direction: column;
        margin-left: 10px;
        .name {
          font-size: 16px;
          color: $text-main;
          margin-bottom: 5px;
          font-weight: 500;
        }
        .date {
          font-size: 12px;
          color: $text-minor;
        }
      }
    }
    .content {
      font-size: 16px;
      color: $text-main;
      line-height: 20px;
      padding: 10px 0;
    }
    .control {
      display: flex;
      align-items: center;
      font-size: 14px;
      color: $text-minor;
      .like {
        display: flex;
        align-items: center;
        margin-right: 20px;
        cursor: pointer;
        &.active, &:hover {
          color: $color-main;
        }
        .iconfont {
          font-size: 14px;
          margin-right: 5px;
        }
      }
      .comment-reply {
        display: flex;
        align-items: center;
        cursor: pointer;
        margin-right: 20px;
        &:hover {
          color: $text-333;
        }
        .iconfont {
          font-size: 16px;
          margin-right: 5px;
        }
      }

    }
    .reply {
      margin: 10px 0;
      border-left: 2px solid $border-first;
      .item {
        margin: 0 10px;
        padding: 10px 0;
        border-bottom: 1px dashed $border-third;
        .reply-content {
          display: flex;
          align-items: center;
          font-size: 14px;
          color: $text-main;
          .from-name {
            color: $color-main;
          }
          .to-name {
            color: $color-main;
            margin-left: 5px;
            margin-right: 5px;
          }
        }
        .reply-bottom {
          display: flex;
          align-items: center;
          margin-top: 6px;
          font-size: 12px;
          color: $text-minor;
          .reply-text {
            display: flex;
            align-items: center;
            margin-left: 10px;
            cursor: pointer;
            &:hover {
              color: $text-333;
            }
            .icon-comment {
              margin-right: 5px;
            }
          }
          .like {
            display: flex;
            align-items: center;
            cursor: pointer;
            &.active, &:hover {
              color: $color-main;
            }
          }
        }
      }
      .write-reply {
        display: flex;
        align-items: center;
        font-size: 14px;
        color: $text-minor;
        padding: 10px;
        cursor: pointer;
        &:hover {
          color: $text-main;
        }
        .el-icon-edit {
          margin-right: 5px;
        }
      }
      .fade-enter-active, fade-leave-active {
        transition: opacity 0.5s;
      }
      .fade-enter, .fade-leave-to {
        opacity: 0;
      }
      .input-wrapper {
        padding: 10px;
        .gray-bg-input, .el-input__inner {
          /*background-color: #67C23A;*/
        }
        .btn-control {
          display: flex;
          justify-content: flex-end;
          align-items: center;
          padding-top: 10px;
          .cancel {
            font-size: 16px;
            color: $text-normal;
            margin-right: 20px;
            cursor: pointer;
            &:hover {
              color: $text-333;
            }
          }
          .confirm {
            font-size: 16px;
          }
        }
      }
    }
  }
  .comment-more {
    font-size: 14px;
    color: $color-main;
    cursor: pointer;
    &:hover {
      color: $color-warning;
    }
  }
  .reply-more {
    font-size: 14px;
    color: $color-main;
    cursor: pointer;
    &:hover {
      color: $text-333;
    }
  }
}
</style>