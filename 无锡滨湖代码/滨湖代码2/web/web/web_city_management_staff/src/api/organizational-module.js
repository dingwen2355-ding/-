import request from '@/utils/request.js'

// 组织体系--新增弹框内容列表
export function getUnitInfoList (data) {
  return request({
    url: '/apis/communication/unitInfo?type=' + data,
    method: 'get'
  })
}
// 组织体系--获取页面当前显示的内容
export function getData (data) {
  return request({
    url: '/apis/Command/communicationOrderByDepartment?eventId=' + data,
    method: 'get'
  })
}
// 决策辅助--决策建议  信息内容获取
export function getDecisionSuggest (data) {
  return request({
    url: '/apis/decisionSuggest/queryInfo?eventId=' + data,
    method: 'get'
  })
}
// 决策辅助--提交(修改后的建议)
export function getUpdateDecisionSuggest (data) {
  let url = '/apis/decisionSuggest/updateSuggest'
  return request({
    url: url,
    data: data,
    method: 'post'
  })
}
