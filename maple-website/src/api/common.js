import request from '@/utils/request'


// 获取菜单信息
export function getHeaderMenu() {
    return request({
        url: '/common/getMenuTreeList',
        method: 'post',
        data: {
            menuType: "H",
            status: 1,
            parentId: 1
        }
    })
}

// 获取菜单信息
export function getFooterMenu() {
    return request({
        url: '/common/getMenuTreeList',
        method: 'post',
        data: {
            menuType: "F",
            status: 1,
            parentId: 2
        }
    })
}

// 根据菜单ID获取菜单详情
export function getWebMenuByPath(path) {
    return request({
        url: '/common/getWebMenuByPath/' + path,
        method: 'get',
    })
}

// 获取系统配置信息
export function getAllConfigList() {
    return request({
        url: '/common/getAllConfigList',
        method: 'post'
    })
}

// 根据字典type获取字典数据
export function getDictByCode(dictCode){
    return request({
        url: '/common/getDictByCode/' + dictCode,
        method: 'get'
    })
}

