import request from '/@/utils/request';

/**
 * 用户中心-部门api接口集合
 */
export function useDeptApi() {
    return {
        // 获取用户中心-部门
        getTreeList(data: object) {
            return request({
                url: '/system/dept/getTreeList',
                method: 'post',
                data: data
            })
        },
        // 查询用户中心-部门详细
        getDeptById(id: number) {
            return request({
                url: '/system/dept/' + id,
                method: 'get'
            })
        },
        // 新增用户中心-部门
        createDept(data: object) {
            return request({
                url: '/system/dept/createDept',
                method: 'post',
                data: data
            })
        },
        // 修改用户中心-部门
        updateDept(data: object) {
            return request({
                url: '/system/dept/updateDept',
                method: 'post',
                data: data
            })
        },
        // 删除用户中心-部门
        deleteDept(id: number) {
            return request({
                url: '/system/dept/' + id,
                method: 'delete'
            })
        },
    }
}