import request from '@/router/axios';

export const getUpSyn = (current, size, params) => {
  return request({
    url: '/api/szbh-back/syscronconfig/getUpSyn',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getDownPage = (current, size, params) => {
    return request({
      url: '/api/szbh-back/downstreamsync/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
}
  
export const getSynList = (current, size,type, params) => {
    return request({
      url: '/api/szbh-back/syncrecord/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,type
      }
    })
}
  // 下游同步
  export const submit = (row) => {
    return request({
      url: '/api/szbh-back/downstreamsync/submit',
      method: 'post',
      data: row
    })
}
export const getDownRecordPage = (current, size, id) => {
  return request({
    url: '/api/szbh-back/syncrecord/getDownpage',
    method: 'get',
    params: {
      id,
      current,
      size
    }
  })
} 
export const remove = (ids) => {
  return request({
    url: '/api/szbh-back/downstreamsync/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}
export const getDetail = (id) => {
  return request({
    url: '/api/szbh-back/downstreamsync/detail',
    method: 'get',
    params: {
      id
    }
  })
}