import request from '@/router/axios';

export const getJvm = () => {
  return request({
    url: '/api/monitor/data/jvm',
    method: 'get'
  })
}
export const getCallapi = () => {
  return request({
    url: '/api/monitor/data/callapi',
    method: 'get'
  })
}
export const getApiAbnormaAmount = () => {
  return request({
    url: '/api/monitor/data/apiAbnormaAmount',
    method: 'get'
  })
}
