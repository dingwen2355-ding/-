import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/szbh-back/bhnotice/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getDetail = (id) => {
    return request({
      url: '/api/szbh-back/bhnotice/detail',
      method: 'get',
      params: {
        id
      }
    })
  }
  
  export const remove = (ids) => {
    return request({
      url: '/api/szbh-back/bhnotice/remove',
      method: 'post',
      params: {
        ids,
      }
    })
  }
  
  export const submit = (row) => {
    return request({
      url: '/api/szbh-back/bhnotice/submit',
      method: 'post',
      data: row
    })
  }
  
  export const update = (row) => {
    return request({
      url: '/api/szbh-back/bhnotice/update',
      method: 'post',
      data: row
    })
  }
  