import request from '/@/utils/request';

/**
 * 用户中心-菜单权限api接口集合
 */
export function useMenuApi() {
    return {
        // 获取树形组件数据用户中心-菜单权限
        getRouters() {
            return request({
                url: '/system/menu/getRouters',
                method: 'get'
            })
        },
        // 获取树形组件数据用户中心-菜单权限
        getTreeList(data: object) {
            return request({
                url: '/system/menu/getTreeList',
                method: 'post',
                data: data
            })
        },
        // 查询用户中心-菜单权限详细
        getMenuById(id: number) {
            return request({
                url: '/system/menu/' + id,
                method: 'get'
            })
        },
        // 新增用户中心-菜单权限
        createMenu(data: object) {
            return request({
                url: '/system/menu/createMenu',
                method: 'post',
                data: data
            })
        },
        // 修改用户中心-菜单权限
        updateMenu(data: object) {
            return request({
                url: '/system/menu/updateMenu',
                method: 'post',
                data: data
            })
        },
        // 删除用户中心-菜单权限
        deleteMenu(id: number) {
            return request({
                url: '/system/menu/' + id,
                method: 'delete'
            })
        },
    }
}