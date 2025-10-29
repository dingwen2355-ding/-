import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/szbh-back/appuserecord/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const remove = (ids) => {
    return request({
      url: '/api/szbh-back/appuserecord/remove',
      method: 'post',
      params: {
        ids,
      }
    })
}
  
export const getDetail = (id) => {
    return request({
      url: '/api/szbh-back/appuserecord/detail',
      method: 'get',
      params: {
        id
      }
    })
  }