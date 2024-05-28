import request from '/@/utils/request';

/**
 * 网站类目api接口集合
 */
export function useWebArticleApi() {
    return {
        // 分页获取网站类目
        getPageList(data: object) {
            return request({
                url: '/manageArticle/getPageList',
                method: 'post',
                data: data
            })
        },
        // 查询网站类目详细
        getWebArticleById(id: number) {
            return request({
                url: '/manageArticle/' + id,
                method: 'get'
            })
        },
        // 新增网站类目
        createWebArticle(data: object) {
            return request({
                url: '/manageArticle/createWebArticle',
                method: 'post',
                data: data
            })
        },
        // 修改网站类目
        updateWebArticle(data: object) {
            return request({
                url: '/manageArticle/updateWebArticle',
                method: 'post',
                data: data
            })
        },
        // 删除网站类目
        deleteWebArticle(id: number) {
            return request({
                url: '/manageArticle/' + id,
                method: 'delete'
            })
        },
    }
}