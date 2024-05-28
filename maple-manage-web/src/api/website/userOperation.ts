import request from '/@/utils/request';

/**
 * 网站用户操作api接口集合
 */
export function useWebUserOperationApi() {
  return {
    // 分页获取网站用户操作
    getPageList(data: object) {
      return request({
        url: '/manageUserOperation/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询网站用户操作详细
    getWebUserOperationById(id: number) {
      return request({
        url: '/manageUserOperation/' + id,
        method: 'get'
      })
    },
    // 新增网站用户操作
    createWebUserOperation(data: object) {
      return request({
        url: '/manageUserOperation/createWebUserOperation',
        method: 'post',
        data: data
      })
    },
    // 修改网站用户操作
    updateWebUserOperation(data: object) {
      return request({
        url: '/manageUserOperation/updateWebUserOperation',
        method: 'post',
        data: data
      })
    },
    // 删除网站用户操作
    deleteWebUserOperation(id: number) {
      return request({
        url: '/manageUserOperation/' + id,
        method: 'delete'
      })
    },
  }
}