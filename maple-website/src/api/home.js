import request from '@/utils/request'

// 获取博客目录
export function getHomeData() {
    return request({
        url: '/home/getHomeData',
        method: 'get'
    })
}

// 全文搜索
export function search(data) {
    return request({
        url: '/home/search',
        method: 'post',
        data: data
    })
}



  

