import request from '@/utils/request'


// 获取菜单信息
export function getHeaderMenu() {
    return request({
        url: '/common/getHeaderMenuTreeList',
        method: 'post',
        data: {
            menuType: "H",
            status: 1
        }
    })
}

// 获取系统配置信息
export function getAllConfigList() {
    return request({
        url: '/common/getAllConfigList',
        method: 'post'
    })
}

