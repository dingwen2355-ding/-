import request from '@/router/axios';
import {getToken} from '@/util/auth';
import website from '@/config/website';
import {Base64} from 'js-base64';

export const getList = (current, size, params) => {
  return request({
    url: '/api/blade-develop/datasource/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/blade-develop/datasource/detail',
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/blade-develop/datasource/remove',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/blade-develop/datasource/submit',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/blade-develop/datasource/submit',
    method: 'post',
    data: row
  })
}

export const headers = () => {
  let obj={}
  obj[website.tokenHeader]= 'bearer ' + getToken()
  obj['Authorization'] = `Basic ${Base64.encode(`${website.clientId}:${website.clientSecret}`)}`;
  return obj
}