import request from '@/utils/request'

// 获取授权登录二维码
export function getUnlimitedQrCode() {
  return request({
    url: '/sso/getUnlimitedQrCode',
    method: 'post'
  })
}

// 获取授权登录二维码
export function checkAppletAuth(data) {
  return request({
    url: '/sso/checkAppletAuth',
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getUserInfo() {
  return request({
    url: '/webUser/getUserInfo',
    method: 'post'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/sso/logout',
    method: 'get'
  })
}
