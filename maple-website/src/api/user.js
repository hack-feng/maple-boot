import request from '@/utils/request'

// 修改用户
export function updateUser(data) {
    return request({
        url: '/user/updateUser',
        method: 'post',
        data: data
    })
}