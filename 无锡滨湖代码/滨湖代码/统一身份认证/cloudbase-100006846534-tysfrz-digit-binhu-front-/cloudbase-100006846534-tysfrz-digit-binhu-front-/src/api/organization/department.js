import request from '@/router/axios';

export const getList = (params,parentId) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/list',
    method: 'get',
    params: {
      ...params,
      parentId
    }
  })
}

export const getList2 = (params,parentId) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/list2',
    method: 'get',
    params: {
      ...params,
      parentId
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/submit',
    method: 'post',
    data: row
  })
}

export const changeEnable = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/changeEnable',
    method: 'post',
    params: {
      id
    }
  })
}

// 同步树
export const getBhDeptTreeSync = () => {
  return request({
    url: '/api/squirrel-szbh/bhdeptsync/getBhDeptTree',
    method: 'get',
  })
}

// 同步
export const syn = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/syn',
    method: 'post',
    params: {
      ids,
    }
  })
}
export const syncDept = () => {
  return request({
    url: '/api/squirrel-szbh/bhdeptsync/syncDept',
    method: 'get',
  })
}