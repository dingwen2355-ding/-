import request from '@/router/axios';

export const getList = (current, size,treeId, params) => {
  return request({
    url: '/api/szbh-back/loginrecord/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,treeId
    }
  })
}

export const getDetail = (id) => {
    return request({
      url: '/api/szbh-back/loginrecord/detail',
      method: 'get',
      params: {
        id
      }
    })
}
  
export const getDatAPage = (current,size,userAccount) => {
  return request({
    url: '/api/szbh-back/loginrecord/getDatAPage',
    method: 'get',
    params: {
      current,size,userAccount
    }
  })
} 