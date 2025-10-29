import request from '@/router/axios';

export const getList = (current, size, bhDeptId, params) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/page',
    method: 'get',
    params: {
      ...params,
      current,
      size,
      bhDeptId
    }
  })
}
export const getUserList = (params) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getList',
    method: 'get',
    params: {
      ...params,
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/submit',
    method: 'post',
    data: row
  })
}

export const getBhDeptTree = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhdept/getBhDeptTree',
    method: 'get',
    params: {
      id
    }
  })
}


// 踢下线
export const changeOnline = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/changeOnline',
    method: 'post',
    params: {
      ids,
    }
  })
}
export const changePassword = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/changePassword',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const changeEnable = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/changeEnable',
    method: 'post',
    params: {
      id,
    }
  })
}
// 部门下人员
export const getUserSyn = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhusersync/getUserSyn',
    method: 'get',
    params: {
      ids
    }
  })
}

export const getUser = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getUser',
    method: 'get',
    params: {
      ids
    }
  })
}

export const getUsers = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getUsers',
    method: 'post',
    data: row
  })
}

// 保存人员
export const syn = (account) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/syn',
    method: 'post',
    data: account
  })
}
export const syncUser = () => {
  return request({
    url: '/api/squirrel-szbh/bhusersync/syncUser',
    method: 'get',
  })
}
// 授权树
export const getAppCategoryTree = (name, treeType) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/getAppCategoryTree',
    method: 'get',
    params: {
      name, treeType
    }
  })
}
export const getUserAppCategoryTree = (name, treeType) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/getUserAppCategoryTree',
    method: 'get',
    params: {
      name, treeType
    }
  })
}
// 授权树数据回显
export const getSaveAppList = (id, deptId) => {
  return request({
    url: '/api/szbh-back/bhuserapplicationmanagement/list',
    method: 'get',
    params: {
      id, deptId
    }
  })
}
export const getDeptList = (id) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getDeptList',
    method: 'get',
    params: {
      id
    }
  })
}
// 批量授权
export const getMajorDeptList = (ids) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getMajorDeptList',
    method: 'get',
    params: {
      ids
    }
  })
}
// 保存授权
export const submit = (row) => {
  return request({
    url: '/api/szbh-back/bhuserapplicationmanagement/submit',
    method: 'post',
    data: row
  })
}
// 自动同步详情
export const getUserSynDetail = (type) => {
  return request({
    url: '/api/szbh-back/syscronconfig/detail',
    method: 'get',
    params: {
      type
    }
  })
}
// 自动同步保存
export const userSynSubmit = (row) => {
  return request({
    url: '/api/szbh-back/syscronconfig/submit',
    method: 'post',
    data: row
  })
}

// 发短信
export const sendMsg = (userIds) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/sendMsg',
    method: 'get',
    params: {
      userIds
    }
  })
}