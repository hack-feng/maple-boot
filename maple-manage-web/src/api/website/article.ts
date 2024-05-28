import request from '/@/utils/request';

/**
 * 网站文章api接口集合
 */
export function useWebArticleApi() {
  return {
    // 分页获取网站文章
    getPageList(data: object) {
      return request({
        url: '/manageArticle/getPageList',
        method: 'post',
        data: data
      })
    },
    // 查询网站文章详细
    getWebArticleById(id: number) {
      return request({
        url: '/manageArticle/' + id,
        method: 'get'
      })
    },
    // 新增网站文章
    createWebArticle(data: object) {
      return request({
        url: '/manageArticle/createWebArticle',
        method: 'post',
        data: data
      })
    },
    // 修改网站文章
    updateWebArticle(data: object) {
      return request({
        url: '/manageArticle/updateWebArticle',
        method: 'post',
        data: data
      })
    },
    // 删除网站文章
    deleteWebArticle(id: number) {
      return request({
        url: '/manageArticle/' + id,
        method: 'delete'
      })
    },
  }
}