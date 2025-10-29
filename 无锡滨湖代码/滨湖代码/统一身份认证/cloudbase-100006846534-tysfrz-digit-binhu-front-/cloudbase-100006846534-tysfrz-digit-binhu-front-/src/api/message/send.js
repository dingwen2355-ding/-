import request from '@/router/axios';

export const getLoginUserPhone = () => {
  return request({
    url: '/api/szbh-back/smssendsetting/getLoginUserPhone',
    method: 'get',
  })
}
