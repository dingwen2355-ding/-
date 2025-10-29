import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const getAllList = (params) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/getList',
    method: 'get',
    params: {
      ...params,
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/submit',
    method: 'post',
    data: row
  })
}

export const changeEnable = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/changeEnable',
    method: 'post',
    params: {
      id,
    }
  })
}
export const changeGround = (id) => {
    return request({
      url: '/api/squirrel-szbh/bhapplicationmanagement/changeGround',
      method: 'post',
      params: {
        id,
      }
    })
  }
  
export const getSecret = () => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/getSecret',
    method: 'get',
  })
}