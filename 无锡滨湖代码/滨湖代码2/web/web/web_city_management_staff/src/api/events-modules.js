import request from '@/utils/request.js'

// 获取事件列表信息
export function getEventInfoList (data) {
  return request({
    url: '/apis/eventDeal/queryFlow?eventId=' + data,
    method: 'get'
  })
}

// 事件列表提交更新
export function getSubmitEventInfoList (data) {
  let url = '/apis/eventDeal/batchUpdateDeafFlow'
  return request({
    url: url,
    data: data,
    method: 'post'
  })
}

// 增加事件列表一条信息
export function addEventInfo (data) {
  let url = '/apis/eventDeal/insertEventDealFlow'
  return request({
    url: url,
    data: data,
    method: 'post'
  })
}
