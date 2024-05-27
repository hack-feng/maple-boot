import request from '@/utils/request'

// 分页获取评论列表
export function getPageComment(data) {
  return request({
    url: '/comment/getPageComment',
    method: 'post',
    data: data
  })
}

// 用户评论
export function createComment(data) {
  return request({
    url: '/comment/createComment',
    method: 'post',
    data: data
  })
}

// 用户评论
export function deleteComment(commentId) {
  return request({
    url: '/comment/deleteComment/' + commentId,
    method: 'delete'
  })
}

// 用户评论
export function likeComment(data) {
  return request({
    url: '/comment/likeComment',
    method: 'post',
    data: data
  })
}
  