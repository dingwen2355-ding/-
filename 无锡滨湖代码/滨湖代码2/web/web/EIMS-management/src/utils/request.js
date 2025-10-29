import axios from 'axios'
import { Message } from 'element-ui'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  // timeout: 100000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  request => {
    // do something before request is sent
    let qurl = request.url
    if (qurl.indexOf('?') === -1) {
      qurl += '?'
    } else {
      qurl += '&'
    }
    qurl += ('r=' + (new Date().getTime()))
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
    if (response.status !== 200) {
      Message.error(JSON.stringify(response.data))
    } else if (response.status === 401) {
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
      // Authorization
    }
    return Promise.reject(error)
  }
)

export default service
