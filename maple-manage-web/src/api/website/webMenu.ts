import request from '/@/utils/request';

/**
 * 网站菜单api接口集合
 */
export function useWebMenuApi() {
    return {
        // 获取树形组件数据网站菜单
        getTreeList(data: object) {
            return request({
                url: '/manageWebMenu/getTreeList',
                method: 'post',
                data: data
            })
        },
        // 查询网站菜单详细
        getWebMenuById(id: number) {
            return request({
                url: '/manageWebMenu/' + id,
                method: 'get'
            })
        },
        // 新增网站菜单
        createWebMenu(data: object) {
            return request({
                url: '/manageWebMenu/createWebMenu',
                method: 'post',
                data: data
            })
        },
        // 修改网站菜单
        updateWebMenu(data: object) {
            return request({
                url: '/manageWebMenu/updateWebMenu',
                method: 'post',
                data: data
            })
        },
        // 删除网站菜单
        deleteWebMenu(id: number) {
            return request({
                url: '/manageWebMenu/' + id,
                method: 'delete'
            })
        },
    }
}