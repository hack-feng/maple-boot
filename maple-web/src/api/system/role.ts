import request from '/@/utils/request';

/**
 * 用户中心-角色信息api接口集合
 */
export function useRoleApi() {
    return {
        // 分页获取用户中心-角色信息
        getPageList(data: object) {
            return request({
                url: '/system/role/getPageList',
                method: 'post',
                data: data
            })
        },
        getRoleList() {
            return request({
                url: '/system/role/getRoleList',
                method: 'post',
            })
        },
        // 查询用户中心-角色信息详细
        getRoleById(id: number) {
            return request({
                url: '/system/role/' + id,
                method: 'get'
            })
        },
        // 新增用户中心-角色信息
        createRole(data: object) {
            return request({
                url: '/system/role/createRole',
                method: 'post',
                data: data
            })
        },
        // 修改用户中心-角色信息
        updateRole(data: object) {
            return request({
                url: '/system/role/updateRole',
                method: 'post',
                data: data
            })
        },
        // 删除用户中心-角色信息
        deleteRole(id: number) {
            return request({
                url: '/system/role/' + id,
                method: 'delete'
            })
        },
    }
}