import request from '@/utils/request'

// 获取资源列表
export function getPageResource(data) {
    return request({
        url: '/resource/getPageResource',
        method: 'post',
        data: data
    })
}

// 获取目录详情
export function getResourceById(resourceId) {
    return request({
        url: '/resource/getResourceById/' + resourceId,
        method: 'get'
    })
}

// 下载文件
export function downResource(data) {
    return request({
        url: '/resource/downResource',
        method: 'post',
        data: data,
        responseType: "blob"
    })
}

// 获取资源列表
export function collectResource(data) {
    return request({
        url: '/resource/collectResource',
        method: 'post',
        data: data
    })
}


  

