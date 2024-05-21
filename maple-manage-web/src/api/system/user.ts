import request from '/@/utils/request';

/**
 * 用户api接口集合
 */
export function useUserApi() {
    return {
        getUserInfo: () => {
            return request({
                url: '/sso/getInfo',
                method: 'get',
            });
        },
        // 查询用户列表
        getPageList(data: object) {
            return request({
                url: '/user/getPageList',
                method: 'post',
                data: data
            })
        },
        // 查询用户详细
        getUserById(id: number) {
            return request({
                url: '/user/' + id,
                method: 'get'
            })
        },
        // 新增用户
        createUser(data: object) {
            return request({
                url: '/user/createUser',
                method: 'post',
                data: data
            })
        },
        updateUser(data: object) {
            return request({
                url: '/user/updateUser',
                method: 'post',
                data: data
            })
        },
        // 删除用户
        deleteUser(id: number) {
            return request({
                url: '/user/' + id,
                method: 'delete'
            })
        },
        // 用户密码修改
        updatePwd(password: string, oldPassword: string) {
            const data = {
                password,
                oldPassword
            }
            return request({
                url: '/user/updatePwd',
                method: 'put',
                data: data
            })
        },
    }
}
