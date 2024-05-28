import request from '/@/utils/request';

/**
 * 网站资源api接口集合
 */
export function useWebResourceApi() {
  return {
    // 分页获取网站资源
    getPageList(data: object) {
      return request({
        url: '/manageResource/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询网站资源详细
    getWebResourceById(id: number) {
      return request({
        url: '/manageResource/' + id,
        method: 'get'
      })
    },
    // 新增网站资源
    createWebResource(data: object) {
      return request({
        url: '/manageResource/createWebResource',
        method: 'post',
        data: data
      })
    },
    // 修改网站资源
    updateWebResource(data: object) {
      return request({
        url: '/manageResource/updateWebResource',
        method: 'post',
        data: data
      })
    },
    // 删除网站资源
    deleteWebResource(id: number) {
      return request({
        url: '/manageResource/' + id,
        method: 'delete'
      })
    },
  }
}