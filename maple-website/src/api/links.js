import request from '@/utils/request'

// 分页获取链接信息
export function getPageLink(data) {
    return request({
        url: '/friendlyLink/getPageLink',
        method: 'post',
        data: data
    })
}

// 记录链接点击量
export function addLinkNum(linksId) {
    return request({
        url: '/friendlyLink/addLinkNum/' + linksId,
        method: 'get'
    })
}
