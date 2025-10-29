import request from '@/utils/request'
export function getWeather (data) {
  return request({
    url: '/apis/queryWeather',
    method: 'get'
  })
}
export function getWarningWeather (data) {
  return request({
    url: '/apis/queryDisasterWarningData',
    method: 'get'
  })
}
