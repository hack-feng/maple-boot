import request from '/@/utils/request';

/**
 * 网站用户api接口集合
 */
export function useWebConfigApi() {
  return {
    // 分页获取网站用户
    getPageList(data: object) {
      return request({
        url: '/manageConfig/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询网站用户详细
    getWebConfigById(id: number) {
      return request({
        url: '/manageConfig/' + id,
        method: 'get'
      })
    },
    // 新增网站用户
    createWebConfig(data: object) {
      return request({
        url: '/manageConfig/createWebConfig',
        method: 'post',
        data: data
      })
    },
    // 修改网站用户
    updateWebConfig(data: object) {
      return request({
        url: '/manageConfig/updateWebConfig',
        method: 'post',
        data: data
      })
    },
    // 删除网站用户
    deleteWebConfig(id: number) {
      return request({
        url: '/manageConfig/' + id,
        method: 'delete'
      })
    },
  }
}