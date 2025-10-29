import request from '@/router/axios';

export const getList = (current, size, type, params) => {
  return request({
    url: '/api/szbh-back/applicationauthority/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
      type,
    }
  })
}

export const getUserAppPage = (current, size, params) => {
  return request({
    url: '/api/szbh-back/applicationauthority/getUserAppPage',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const updateOrAdd = (row) => {
  return request({
    url: '/api/szbh-back/applicationauthority/submit',
    method: 'post',
    data: row
  })
}
export const getDetail = (id) => {
  return request({
    url: '/api/szbh-back/applicationauthority/detail',
    method: 'get',
    params: {
      id
    }
  })
}
export const remove = (ids) => {
  return request({
    url: '/api/szbh-back/applicationauthority/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}