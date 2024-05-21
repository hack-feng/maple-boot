import request from '/@/utils/request';

/**
 * 系统管理-参数配置api接口集合
 */
export function useConfigApi() {
  return {
    // 分页获取系统管理-参数配置
    getPageList(data: object) {
      return request({
        url: '/system/config/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询系统管理-参数配置详细
    getConfigById(id: number) {
      return request({
        url: '/system/config/' + id,
        method: 'get'
      })
    },
    // 新增系统管理-参数配置
    createConfig(data: object) {
      return request({
        url: '/system/config/createConfig',
        method: 'post',
        data: data
      })
    },
    // 修改系统管理-参数配置
    updateConfig(data: object) {
      return request({
        url: '/system/config/updateConfig',
        method: 'post',
        data: data
      })
    },
    // 删除系统管理-参数配置
    deleteConfig(id: number) {
      return request({
        url: '/system/config/' + id,
        method: 'delete'
      })
    },
  }
}