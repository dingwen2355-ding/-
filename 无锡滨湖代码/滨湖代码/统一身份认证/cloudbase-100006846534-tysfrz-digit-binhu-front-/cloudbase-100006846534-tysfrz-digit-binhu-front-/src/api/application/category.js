import request from '@/router/axios';

export const getList = (type,params) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/list',
    method: 'get',
    params: {
      type,
      ...params
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/submit',
    method: 'post',
    data: row
  })
}

export const changeEnable = (id) => {
    return request({
      url: '/api/squirrel-szbh/bhapplicationcategory/changeEnable',
      method: 'post',
      params: {
        id,
      }
    })
}
  
export const getAppCategoryTree = (params) => {
    return request({
      url: '/api/squirrel-szbh/bhapplicationcategory/getAppCategoryTree',
      method: 'get',
      params: {
        ...params
      }
    })
}

// 管理员操作日志
export const getLogs = (current, size,params) => {
  return request({
    url: '/api/blade-log/log/operation/list',
    method: 'get',
    params: {
      current, size,...params
    }
  })
}
// 管理员登录日志
export const getLoginList = (current, size,params) => {
  return request({
    url: '/api/blade-log/log/operation/getLoginList',
    method: 'get',
    params: {
      current, size,...params
    }
  })
}