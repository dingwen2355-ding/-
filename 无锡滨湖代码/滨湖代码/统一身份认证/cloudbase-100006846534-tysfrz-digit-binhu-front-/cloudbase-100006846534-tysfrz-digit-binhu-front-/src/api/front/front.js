import request from '@/router/axios';

export const getUserApp = (params) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/getUserApp',
    method: 'get',
    params
  })
}

export const getUserApplicationCategory = () => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationcategory/getUserApplicationCategory',
    method: 'get',
  })
}
export const getUserDept = () => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/getUserDept',
    method: 'get',
  })
}

export const applicationByClassificationAndDept = (params) => {
  return request({
    url: '/api/squirrel-szbh/bhapplicationmanagement/applicationByClassificationAndDept',
    method: 'get',
    params
  })
}

export const submit = (row) => {
  return request({
    url: '/api/szbh-back/authorityapplyfor/submit',
    method: 'post',
    data: row
  })
}
// 公告中心
export const getNoticeList = (params) => {
  return request({
    url: '/api/szbh-back/bhnotice/getNoticeList',
    method: 'get',
    params
  })
}
export const detail = (id) => {
  return request({
    url: '/api/szbh-back/bhnotice/detail',
    method: 'get',
    params: { id }
  })
}
//   获取是否是初始密码0是初始密码，未修改过，1不是初始密码
export const getPasswordType = () => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getPasswordType',
    method: 'get',
  })
}
// 修改密码
export const updatePassword = (params) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/updatePassword',
    method: 'get',
    params
  })
}
// 我的访问统计
export const getUserRecord = (type) => {
  return request({
    url: '/api/szbh-back/appuserecord/getUserRecord',
    method: 'get',
    params: {
      type
    }
  })
}
// 保存排序
export const submitOrder = (row) => {
  return request({
    url: '/api/szbh-back/bhuserapplicationmanagement/submitOrder',
    method: 'post',
    data: row
  })
}
// 权限申请
export const getUserApplyFor = (params) => {
  return request({
    url: '/api/szbh-back/authorityapplyfor/getUserApplyFor',
    method: 'get',
    params
  })
}
// 获得账号资料
export const getAccountDetail = () => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getAccountDetail',
    method: 'get',
  })
}
export const update = (row) => {
  return request({
    url: '/api/squirrel-szbh/bhuser/update',
    method: 'post',
    data: row
  })
}
export const getPasswordBit = () => {
  return request({
    url: '/api/squirrel-szbh/bhuser/getPasswordBit',
    method: 'get',
  })
}
//单位类型下拉
export const getDeptType = () => {
  return request({
    url: '/api/blade-system/dict/dictionary?code=applicant_dept_type',
    method: 'get',
  })
}
//用户信息
export const getUserInfo = () => {
  return request({
    url: '/api/szbh-back/authorityapplyfor/getUserInfo',
    method: 'get',
  })
}