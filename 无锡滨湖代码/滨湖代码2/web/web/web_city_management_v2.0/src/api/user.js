import request from '@/utils/request'

export function login (data) {
  return request({
    // url: '/user/login',
    url: '/znv/login/verify',
    method: 'post',
    data
  })
}

export function logout () {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function getUser (userName) {
  return request({
    url: '/user/user?userName=' + userName,
    method: 'get'
  })
}

export function getModulesByToken (userName) {
  return request({
    url: '/user/getModulesByToken',
    method: 'get'
  })
}

export function getUserByToken () {
  return request({
    url: '/user/getUserByToken',
    method: 'get'
  })
}
