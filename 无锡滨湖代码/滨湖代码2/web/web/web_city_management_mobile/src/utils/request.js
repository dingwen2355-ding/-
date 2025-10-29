import axios from 'axios'
import { Notify } from 'vant'
import qs from 'qs'
// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 100000 // request timeout
})

// let aes = new AES([])
//
// console.log(aes.decryption('f3880b77c6734e52a1350adff7f3756c'))

// request interceptor
service.interceptors.request.use(
  async (request) => {
    const url = location.href
    const hasCode = url.split('code=').length > 1
    if (hasCode) {
      const { code } = qs.parse(url.split('?')[1])
      await axios
        .get('/auth/loginByGWC?code=' + code)
        .then((res) => {
          let { result, data, remark } = res.data
          if (result === 0) {
            data = data[0]
            sessionStorage.setItem('token', data.token)
            sessionStorage.setItem('roleId', data.userMessage.roleId)
            sessionStorage.setItem('user', data.userMessage.trueName)
            sessionStorage.setItem('phone', data.userMessage.phone)
            sessionStorage.setItem('precinctTreeId', data.userMessage.precinctTreeId)
            sessionStorage.setItem('streetId', data.userMessage.streetId)
            sessionStorage.setItem('streetName', data.userMessage.streetName)
            sessionStorage.setItem('roleName', data.userMessage.roleName)
            if (data.userMessage.departmentName) {
              sessionStorage.setItem('departmentName', data.userMessage.departmentName)
              sessionStorage.setItem('departmentId', data.userMessage.departmentId)
            } else {
              sessionStorage.setItem('departmentName', '')
              sessionStorage.setItem('departmentId', '')
            }
            sessionStorage.setItem('userId', data.userMessage.userId)
            sessionStorage.setItem('userName', data.userMessage.userName)
          } else {
            Notify(remark)
            window.location.href = 'http://2.21.137.156:8085/login'
          }
        })
        .catch((error) => {
          // Notify('出错了')\
          window.location.href = 'http://2.21.137.156:8085/login'
        })
    }
    // do something before request is sent
    let qurl = request.url
    if (qurl.indexOf('?') === -1) {
      qurl += '?'
    } else {
      qurl += '&'
    }
    qurl += ('r=' + (new Date().getTime()))
    request.headers.Authorization = sessionStorage.getItem('token')
    // request.headers.Cauthorization = sessionStorage.getItem('token')
    request.url = qurl
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
      localStorage.clear()
      window.location.href = '/'
    }
    // 对响应数据做处理
    return response
  },
  error => {
    // 对响应错误做处理
    if (error.response && error.response.status === 401) {
      localStorage.clear()
      window.location.href = '/'
    }
    return Promise.reject(error)
  }
)

export default service
