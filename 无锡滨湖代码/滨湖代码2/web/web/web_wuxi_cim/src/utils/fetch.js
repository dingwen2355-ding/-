import axios from 'axios'
import router from '@/routers/index.router.js'
import store from '@/services/store.provider.js'
import { Message } from 'element-ui'

axios.defaults.crossDomain = true
axios.defaults.withCredentials = true

/**
 * 拼接请求路径
 * @param {string} url 请求路径
 * @returns 完整的URL
 */
function spliceURL(url) {
  if (url.indexOf('http') === 0) {
    return url
  }
  return url
}

/**
 * 数据类型转换
 * @param {string} url 请求路径
 * @param {object} data 请求参数
 * @returns 原数据 || FormData类型的数据
 */
function convertDataType(url, data) {
  // onemap（post请求参数全部转为FormData类型）
  if (url.indexOf('/onemap/admin/rest') >= 0) {
    let formData = new FormData()
    for (let key in data) {
      let val = data[key]
      if (Object.prototype.toString.call(val) === '[object Object]') {
        val = JSON.stringify(val)
      }
      formData.append(key, val)
    }
    return formData
  }
  return data
}

/**
 * 请求参数添加Token
 * @param {all} data
 * @returns 请求参数
 */
function addToken(data) {
  let token = store.state.login.account.token
  if (!!token) {
    switch (Object.prototype.toString.call(data)) {
      case '[object Object]':
        if (!('token' in data)) {
          data.token = token
        }
        break
      case '[object FormData]':
        if (!data.get('token')) {
          data.append('token', token)
        }
        break
      case '[object Undefined]':
        data = {
          token: token
        }
        break
    }
  }
  return data
}

export function get(url, data, needToken = true) {
  url = `${url}${url.indexOf('?') === -1 ? '?' : '&'}n=${encodeURIComponent(Math.random())}`
  if (needToken) data = addToken(data)

  return axios.get(spliceURL(url), {
    params: data
  })
}

export function getCookie(url) {
  const config = {
    method: 'get',
    url: url,
  }
  return axios(config)
}

export function post(url, data, needToken = true) {
  if (needToken) data = addToken(data)

  let _data = convertDataType(url, data)
  let config = {
    url: spliceURL(url),
    method: 'post',
    data: _data,
  }
  if (Object.prototype.toString.call(_data) === '[object FormData]') {
    config.headers = {
      'Content-Type': 'multipart/form-data'
    }
  }
  return axios(config)
}

export function put(url, data, needToken = true) {
  if (needToken) data = addToken(data)

  let config = {
    url: spliceURL(url),
    method: 'put',
    data
  }
  if (Object.prototype.toString.call(data) === '[object FormData]') {
    config.headers = {
      'Content-Type': 'multipart/form-data'
    }
  }
  return axios(config)
}
export function generalPutEvent(url, data, needToken = true) {
  if (needToken) data = addToken(data)

  return axios({
    url: url,
    method: 'put',
    transformRequest: [
      function (data) {
        let ret = ''
        for (let it in data) {
          ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        return ret
      },
    ],
    data
  })
}

export function deletes(url, data, needToken = true) {
  if (needToken) data = addToken(data)

  return axios({
    url: spliceURL(url),
    method: 'delete',
    params: data
  })
}

export function postForm(url, data = {}, needToken = true) {
  if (typeof data != 'object') return
  if (needToken) data = addToken(data)

  return axios({
    url: spliceURL(url),
    method: 'post',
    withCredentials: false,
    headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
    transformRequest: [
      function (data) {
        let ret = ''
        for (let it in data) {
          ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        return ret
      },
    ],
    data
  })
}

export function postJson(url, data, needToken = true) {
  if (typeof data !== 'object') return
  if (needToken) data = addToken(data)

  return axios({
    url: spliceURL(url),
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'kbn-version': '8.3.3',
    },
    // responseType: 'json',
    data: JSON.stringify(data)
  })
}

export function generalPostEvent(url, data, needToken = true) {
  if (needToken) data = addToken(data)

  return axios({
    url: url,
    method: 'post',
    transformRequest: [
      function (data) {
        let ret = ''
        for (let it in data) {
          ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
        }
        return ret
      },
    ],
    data
  })
}

// // 请求拦截器
// axios.interceptors.request.use()

// 返回状态判断(添加响应拦截器)
axios.interceptors.response.use(
  (res) => {
    if (res.hasOwnProperty('data')) {

      if (res.data.hasOwnProperty('code')) {
        if (res.data.code == 200) return res.data
        else return res
      }

      if (res.data.hasOwnProperty('state')) {
        switch (res.data.state.code) {
          case 10000:
            return res.data.body
          case 10001:
            return res.data.body
          case 10003:
            store.dispatch('SET_ACCOUNT_ACTION', {
              token: '',
              uploadtoken: '',
              jtToken: '',
              userInfo: {}
            })
            // router.push({
            //   path: '/home'
            // })
            return Promise.reject()
        }
      }
    }

    if (res.status === 200) return res.data
    return Promise.reject(res)
  },
  (error) => {
    if(error.response&&error.response.status===401){
      Message.error('license校验失败，请更新license');
      router.push({
        path: '/license'
      })
    }
    return Promise.reject(error)
  }
)

export default axios