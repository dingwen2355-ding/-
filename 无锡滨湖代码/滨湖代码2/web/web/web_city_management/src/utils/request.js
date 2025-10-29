import axios from 'axios'
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
    if(!sysConfig.superControl){
      let cauthorization = localStorage.getItem('cauthorization')
      if(!cauthorization){
        window.location.href = 'http://2.22.51.26:30002/oauth/authorize?client_id=CdZLrF07&client_secret=1914d35bc31ae09ae924a330a05e726888602004&redirect_uri=http://2.21.137.156:8080&response_type=code&scope=all'
      } else {
        request.headers.Cauthorization = cauthorization
      }
    }

    // do something before request is sent
    let qurl = request.url
    if (qurl.indexOf('?') === -1) {
      qurl += '?'
    } else {
      qurl += '&'
    }
    qurl += ('r=' + (new Date().getTime()))
    if (window.location.pathname === '/') {
      qurl += ('&eventId=' + store.getters.eventId.id)
    }
    request.url = encodeURI(qurl)
    request.headers.Authorization = '12CBD9B708D887A41AFAB97DAC46AAC6'
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
    if(!sysConfig.superControl){
      if (response.status === 401) {
        console.log('业务401拦截：',response)
        // localStorage.clear();
        // window.location.href = '/'
        localStorage.removeItem('cauthorization');
        localStorage.removeItem('userInfo');
        window.open('http://2.22.51.26:30002/logout?logoutUrl=', '_self');
        window.close();
        window.open('http://2.22.51.26:30002/oauth/authorize?client_id=CdZLrF07&client_secret=1914d35bc31ae09ae924a330a05e726888602004&redirect_uri=http://2.21.137.156:8080&response_type=code&scope=all')
      }
    }
    // 对响应数据做处理
    return response
  },
  error => {
    // 对响应错误做处理
    if (error.response && error.response.status === 401) {
      if(!sysConfig.superControl){
        console.log('http401拦截：',error.response)
        // localStorage.clear();
        // window.location.href = '/'
        localStorage.removeItem('cauthorization');
        localStorage.removeItem('userInfo');
        window.open('http://2.22.51.26:30002/logout?logoutUrl=', '_self');
        window.close();
        window.open('http://2.22.51.26:30002/oauth/authorize?client_id=CdZLrF07&client_secret=1914d35bc31ae09ae924a330a05e726888602004&redirect_uri=http://2.21.137.156:8080&response_type=code&scope=all')
      }
    }
    return Promise.reject(error)
  }
)

export default service
