import request from '@/utils/request'

// 获取博客目录
export function getBlogCategory(data) {
  return request({
    url: '/webArticle/getCategory',
    method: 'post',
    data: data
  })
}

// 获取目录详情
export function getCategoryById(categoryId) {
    return request({
        url: '/webArticle/getCategoryById/' + categoryId,
        method: 'get'
    })
}

// 分页获取文章列表
export function getPageTitle(data) {
    return request({
      url: '/webArticle/getPageTitle',
      method: 'post',
      data: data
    })
}

// 获取文章详情
export function getArticleById(articleId) {
    return request({
        url: '/webArticle/getTitleById/' + articleId,
        method: 'get'
    })
}

// 收藏文章
export function collectArticle(data) {
    return request({
        url: '/operation/collectTitle',
        method: 'post',
        data: data
    })
}

// 点赞文章
export function likeArticle(data) {
    return request({
        url: '/operation/likeTitle',
        method: 'post',
        data: data
    })
}


  

