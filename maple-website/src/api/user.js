import request from '@/utils/request'

// 修改用户
export function updateUser(data) {
    return request({
        url: '/webUser/updateUser',
        method: 'post',
        data: data
    })
}