import request from '@/router/axios';

export const getBulletin = () => {
  return request({
    url: '/api/szbh-back/headPage/getBulletin',
    method: 'get'
  })
}
export const getAppRecordRank = () => {
  return request({
    url: '/api/szbh-back/headPage/getAppRecordRank',
    method: 'get'
  })
}
export const getYearActive = () => {
  return request({
    url: '/api/szbh-back/headPage/getYearActive',
    method: 'get'
  })
}
export const getUserInfo = () => {
  return request({
    url: '/api/szbh-back/headPage/getUserInfo',
    method: 'get'
  })
}