import request from '/@/utils/request';

/**
 * 网站链接api接口集合
 */
export function useWebFriendlyLinkApi() {
  return {
    // 分页获取网站链接
    getPageList(data: object) {
      return request({
        url: '/manageFriendlyLink/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询网站链接详细
    getWebFriendlyLinkById(id: number) {
      return request({
        url: '/manageFriendlyLink/' + id,
        method: 'get'
      })
    },
    // 新增网站链接
    createWebFriendlyLink(data: object) {
      return request({
        url: '/manageFriendlyLink/createWebFriendlyLink',
        method: 'post',
        data: data
      })
    },
    // 修改网站链接
    updateWebFriendlyLink(data: object) {
      return request({
        url: '/manageFriendlyLink/updateWebFriendlyLink',
        method: 'post',
        data: data
      })
    },
    // 删除网站链接
    deleteWebFriendlyLink(id: number) {
      return request({
        url: '/manageFriendlyLink/' + id,
        method: 'delete'
      })
    },
  }
}