import request from '@/router/axios';

export const detail = (current,size,id,params) => {
  return request({
    url: '/api/szbh-back/smssendsetting/detail',
    method: 'get',
      params: {
        current,size,id,
      ...params
    }
  })
}

export const sendSms = (current, size, params) => {
    return request({
      url: '/api/szbh-back/smssendsetting/page',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
}
  // 短信推送记录
export const getMsgList = (current, size, params) => {
    return request({
      url: '/api/squirrel-szbh/bhsmssend/getList',
      method: 'get',
      params: {
        ...params,
        current,
        size,
      }
    })
}
  
export const testSend = (msg,phone) => {
    return request({
      url: '/api/szbh-back/smssendsetting/testSend',
      method: 'post',
        data: {
            msg,phone
        }
    })
}
  
export const remove = (ids) => {
    return request({
      url: '/api/szbh-back/smssendsetting/remove',
      method: 'post',
      params: {
        ids,
      }
    })
  }
  
  export const add = (row) => {
    return request({
      url: '/api/szbh-back/smssendsetting/submit',
      method: 'post',
      data: row
    })
  }
  
  export const update = (row) => {
    return request({
      url: '/api/szbh-back/smssendsetting/submit',
      method: 'post',
      data: row
    })
  }