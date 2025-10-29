import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/szbh-back/manageusergroup/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}
export const getGroupList = (params) => {
  return request({
    url: '/api/szbh-back/manageusergroup/getList',
    method: 'get',
    params: {
      ...params,
    }
  })
}
export const getDetail = (id) => {
  return request({
    url: '/api/szbh-back/manageusergroup/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/szbh-back/manageusergroup/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/szbh-back/manageusergroup/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/szbh-back/manageusergroup/submit',
    method: 'post',
    data: row
  })
}

export const getGroupUserPage = (current, size, id, params) => {
  return request({
    url: '/api/szbh-back/manageusergroup/getGroupUserPage',
    method: 'get',
    params: {
      ...params,
      current,
      size, id
    }
  })
}

export const removeUser = (ids, manageId) => {
  return request({
    url: '/api/szbh-back/manageusergroup/removeUser',
    method: 'post',
    params: {
      ids,
      manageId
    }
  })
}

// 保存添加用户
export const submit = (row) => {
  return request({
    url: '/api/szbh-back/manageuser/submit',
    method: 'post',
    data: row
  })
}
export const getUserList = (id) => {
  return request({
    url: '/api/szbh-back/manageusergroup/getUserList',
    method: 'get',
    params: {
      id
    }
  })
}