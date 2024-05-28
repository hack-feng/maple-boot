import request from '/@/utils/request';

/**
 * 网站用户api接口集合
 */
export function useWebUserApi() {
  return {
    // 分页获取网站用户
    getPageList(data: object) {
      return request({
        url: '/manageWebUser/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询网站用户详细
    getWebUserById(id: number) {
      return request({
        url: '/manageWebUser/' + id,
        method: 'get'
      })
    },
    // 新增网站用户
    createWebUser(data: object) {
      return request({
        url: '/manageWebUser/createWebUser',
        method: 'post',
        data: data
      })
    },
    // 修改网站用户
    updateWebUser(data: object) {
      return request({
        url: '/manageWebUser/updateWebUser',
        method: 'post',
        data: data
      })
    },
    // 删除网站用户
    deleteWebUser(id: number) {
      return request({
        url: '/manageWebUser/' + id,
        method: 'delete'
      })
    },
  }
}