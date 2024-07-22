import request from '@/utils/request'

// 获取首页数据
export function getHomeData() {
    return request({
        url: '/getHomeData',
        method: 'get'
    })
}

export function search(data) {
    return request({
        url: '/search',
        method: 'post',
        data: data
    })
}

// 获取博客目录
export function getPageCategory(data) {
    return request({
        url: '/webCategory/getPageCategory',
        method: 'post',
        data: data
    })
}

// 获取博客目录
export function getBlogCategory(data) {
  return request({
    url: '/webCategory/getPageCategory',
    method: 'post',
    data: data
  })
}

// 获取目录详情
export function getCategoryById(categoryId) {
    return request({
        url: '/webCategory/' + categoryId,
        method: 'get'
    })
}

// 分页获取文章列表
export function getPageArticle(data) {
    return request({
      url: '/webArticle/getPageArticle',
      method: 'post',
      data: data
    })
}

// 获取文章详情
export function getArticleById(articleId) {
    return request({
        url: '/webArticle/' + articleId,
        method: 'get'
    })
}

// 收藏
export function collectArticle(data) {
    return request({
        url: '/operation/collectTitle',
        method: 'post',
        data: data
    })
}

// 点赞
export function likeArticle(data) {
    return request({
        url: '/operation/likeTitle',
        method: 'post',
        data: data
    })
}

// 下载文件
export function downResource(data) {
    return request({
        url: '/operation/downResource',
        method: 'post',
        data: data,
        responseType: "blob"
    })
}


  

