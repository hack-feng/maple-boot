import request from '@/utils/request'

// 获取博客目录
export function getPageCategory(data) {
    return request({
        url: '/webCategory/getPageCategory',
        method: 'post',
        data: data
    })
}