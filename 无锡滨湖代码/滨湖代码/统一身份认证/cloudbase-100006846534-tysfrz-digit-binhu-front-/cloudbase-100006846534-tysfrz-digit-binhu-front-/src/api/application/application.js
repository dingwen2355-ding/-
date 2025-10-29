import request from '@/router/axios';

export const getAppsApplicationList = (current, size, params) => {
  return request({
    url: '/api/szbh-back/applicationapplyfor/page',
    method: 'get',
    params: {
      current, size,
      ...params
    }
  })
}

export const getPermissionApplicationList = (current, size, params) => {
  return request({
    url: '/api/szbh-back/authorityapplyfor/page',
    method: 'get',
    params: {
      current, size,
      ...params
    }
  })
}

export const getAppsDetail = (id) => {
  return request({
    url: '/api/szbh-back/applicationapplyfor/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const getPermissionDetail = (id) => {
  return request({
    url: '/api/szbh-back/authorityapplyfor/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const updateApps = (row) => {
  return request({
    url: '/api/szbh-back/applicationapplyfor/submit',
    method: 'post',
    data: row
  })
}

export const updatePermission = (row) => {
  return request({
    url: '/api/szbh-back/authorityapplyfor/submit',
    method: 'post',
    data: row
  })
}

export const submitList = (data) => {
  return request({
    url: '/api/szbh-back/authorityapplyfor/submitList',
    method: 'post',
    data
  })
}