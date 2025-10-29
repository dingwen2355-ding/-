import request from '@/router/axios';

export const getList = (current,size,params) => {
  return request({
    url: '/api/szbh-back/bhent/page',
    method: 'get',
      params: {
        current,size,
      ...params
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/szbh-back/bhent/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/szbh-back/bhent/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/szbh-back/bhent/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/szbh-back/bhent/submit',
    method: 'post',
    data: row
  })
}