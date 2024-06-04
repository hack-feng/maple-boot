import request from '/@/utils/request';

/**
 * 网站类目api接口集合
 */
export function useWebCategoryApi() {
    return {
        // 获取树形组件数据网站类目
        getTreeList(data: object) {
            return request({
                url: '/manageCategory/getTreeList',
                method: 'post',
                data: data
            })
        },
        // 查询类目列表
        getCategoryList(data: object) {
            return request({
                url: '/manageCategory/getCategoryList',
                method: 'post',
                data: data
            })
        },
        // 查询网站类目详细
        getWebCategoryById(id: number) {
            return request({
                url: '/manageCategory/' + id,
                method: 'get'
            })
        },
        // 新增网站类目
        createWebCategory(data: object) {
            return request({
                url: '/manageCategory/createWebCategory',
                method: 'post',
                data: data
            })
        },
        // 修改网站类目
        updateWebCategory(data: object) {
            return request({
                url: '/manageCategory/updateWebCategory',
                method: 'post',
                data: data
            })
        },
        // 删除网站类目
        deleteWebCategory(id: number) {
            return request({
                url: '/manageCategory/' + id,
                method: 'delete'
            })
        },
    }
}