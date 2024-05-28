import request from '/@/utils/request';

/**
 * 网站用户评论api接口集合
 */
export function useWebUserCommentApi() {
  return {
    // 分页获取网站用户评论
    getPageList(data: object) {
      return request({
        url: '/manageUserComment/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询网站用户评论详细
    getWebUserCommentById(id: number) {
      return request({
        url: '/manageUserComment/' + id,
        method: 'get'
      })
    },
    // 新增网站用户评论
    createWebUserComment(data: object) {
      return request({
        url: '/manageUserComment/createWebUserComment',
        method: 'post',
        data: data
      })
    },
    // 修改网站用户评论
    updateWebUserComment(data: object) {
      return request({
        url: '/manageUserComment/updateWebUserComment',
        method: 'post',
        data: data
      })
    },
    // 删除网站用户评论
    deleteWebUserComment(id: number) {
      return request({
        url: '/manageUserComment/' + id,
        method: 'delete'
      })
    },
  }
}