import request from '@/router/axios';

export const getUserData = () => {
  return request({
    url: '/api/szbh-back/loginrecord/getUserData',
    method: 'get',
  })
}
export const getDeptRank = () => {
  return request({
    url: '/api/szbh-back/loginrecord/getDeptRank',
    method: 'get',
  })
}
export const getAccessStatistics = () => {
  return request({
    url: '/api/szbh-back/loginrecord/getAccessStatistics',
    method: 'get',
  })
}


export const getEcharsList = (params) => {
    return request({
      url: '/api/szbh-back/loginrecord/getEcharsList',
      method: 'get',
      params: {
        ...params,
      }
    })
}
//   应用登录统计
export const getYOYRecord = () => {
    return request({
      url: '/api/szbh-back/appuserecord/getYOYRecord',
      method: 'get',
    })
}
export const getAppClassificationStatistics = () => {
    return request({
      url: '/api/szbh-back/appuserecord/getAppClassificationStatistics',
      method: 'get',
    })
}
export const getAppRecordRank = () => {
  return request({
    url: '/api/szbh-back/headPage/getAppRecordRank',
    method: 'get',
  })
}
export const getRecordEchars = (params) => {
    return request({
      url: '/api/szbh-back/appuserecord/getRecordEchars',
      method: 'get',
      params: {
        ...params,
      }
    })
}
// 短信统计
export const getSMSStatistics = () => {
  return request({
    url: '/api/squirrel-szbh/bhsmssend/getSMSStatistics',
    method: 'get',
  })
}
export const getRecentlyEchars = () => {
    return request({
      url: '/api/squirrel-szbh/bhsmssend/getRecentlyEchars',
      method: 'get',
    })
}
export const getAllSendEchars = () => {
    return request({
      url: '/api/squirrel-szbh/bhsmssend/getAllSendEchars',
      method: 'get',
    })
}
export const getSmsEchars = (params) => {
    return request({
      url: '/api/squirrel-szbh/bhsmssend/getSmsEchars',
      method: 'get',
      params: {
        ...params,
      }
    })
}