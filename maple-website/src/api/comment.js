import request from '@/utils/request'

// 分页获取评论列表
export function getPageComment(data) {
  return request({
    url: '/webUserComment/getPageList',
    method: 'post',
    data: data
  })
}

// 用户评论
export function createComment(data) {
  return request({
    url: '/webUserComment/createWebUserComment',
    method: 'post',
    data: data
  })
}

// 删除评论
export function deleteComment(commentId) {
  return request({
    url: '/webUserComment/' + commentId,
    method: 'delete'
  })
}

// 点赞评论
export function likeComment(data) {
  return request({
    url: '/webUserComment/likeComment',
    method: 'post',
    data: data
  })
}