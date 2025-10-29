import axios from 'axios'
import { getToken } from '@/utils/auth'
import store from '../store/index'
// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 100000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  request => {
    // do something before request is sent
    let qurl = request.url
    if (qurl !== '/apis/communication/areaInfo?level=1') {
      if (qurl === '/apis/communication/queryPrecinctList?upPrecinctId=' + store.getters.precinctId) {
        if (qurl.indexOf('?') === -1) {
          qurl += '?'
        } else {
          qurl += '&'
        }
        qurl += ('r=' + (new Date().getTime()))
      } else {
        if (qurl.indexOf('?') === -1) {
          qurl += '?'
        } else {
          qurl += '&'
        }
        qurl += ('r=' + (new Date().getTime()))
        if (store.getters.precinctId) {
          qurl += ('&precinctId=' + store.getters.precinctId)
        }
        if (store.getters.precinctName) {
          qurl += ('&precinctName=' + store.getters.precinctName)
        }
      }
    } else {
      if (qurl.indexOf('?') === -1) {
        qurl += '?'
      } else {
        qurl += '&'
      }
      qurl += ('r=' + (new Date().getTime()))
    }
    if (request.url.indexOf('oid=1075') !== -1) {
    } else {
      // 链接中是否携带token
      request.headers.common.Authorization = GetQueryString('token') ? GetQueryString('token') : getToken()
    }
    request.url = encodeURI(qurl)
    request.headers.Authorization = '12CBD9B708D887A41AFAB97DAC46AAC6'
    request.headers.ZnvToken = '678C7B996793BA023649F822DB1DA2AD' // 标识自家系统调用后端接口
    return request
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    if (response.status === 401) {
      // sessionStorage.clear();
      // window.location.href = '/'
    }
    // 对响应数据做处理
    return response
  },
  error => {
    // 对响应错误做处理
    if (error.response && error.response.status === 401) {
      // sessionStorage.clear();
      // window.location.href = '/'
    }
    return Promise.reject(error)
  }
)

function GetQueryString (name) {
  var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
  var url = window.location.href
  var index = url.indexOf('?')
  var r = window.location.href.substr(index + 1).match(reg)
  if (r != null) return unescape(r[2])
  return null
}

export default service
