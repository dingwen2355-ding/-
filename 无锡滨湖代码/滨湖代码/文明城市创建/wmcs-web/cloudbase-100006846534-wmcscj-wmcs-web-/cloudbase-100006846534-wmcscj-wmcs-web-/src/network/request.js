import axios from 'axios'
import { Message } from 'element-ui'

import Cookies from 'js-cookie'
import { timeout, codeForReload, tokenRecTime, needRefreshToken } from '@/config'
import { api } from '@/config/index'
const Base64 = require('js-base64').Base64
const moment = require('moment')

export const t_http = axios.create({ timeout })
t_http.interceptors.response.use(
    response => {
        const res = response
        // 默认0为正确
        if (res.data.code == 200) return res.data
        window.queue_messageArr.push(res.data.msg || 'Error')
        throw new Error('接口调用失败')
    },
    error => {
        console.log('err' + error)
        window.queue_messageArr.push(error.msg || '网络超时,请稍后再试')
        return Promise.reject(error)
    }
)

export const exportTable = axios.create({
    timeout: timeout,
    responseType: 'blob',
    baseURL: api
})

// 为每个请求带上token
exportTable.interceptors.request.use(config => {
    let token = Cookies.get('token')
    if (token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
        config.headers['h-request-token'] = token;
    }
    return config
}, error => {
    return Promise.reject(error)
})

// const JSONbig = require('json-bigint');
export const http = axios.create({ timeout })

// 为每个请求带上token
http.interceptors.request.use(config => {
    if (needRefreshToken) {
        // 每次请求前先看是否需要刷token:
        // 1 refreshToken 没有过期
        // 2 当前时间大于token截至时间前5分钟: 配置: 刷新完token后继续重新获取token继续请求
        return new Promise((res, rej) => {
            let nextTime = Cookies.get('tokenOverTime') || moment().format('YYYY-MM-DD HH:mm:ss')
            let refreshTime = moment(nextTime).add(0 - tokenRecTime, 'seconds').format('YYYY-MM-DD HH:mm:ss')
            if (moment().format('YYYY-MM-DD HH:mm:ss') >= refreshTime) {
                // 需要刷新token
                console.log('需要刷新token');
                window.queue_refushTokenArr.push(() => {
                    let token_new = Cookies.get('token')
                    if (token_new) config.headers['h-request-token'] = token_new
                    res(config)
                })
            } else {
                let token_new = Cookies.get('token')
                if (token_new) config.headers['h-request-token'] = token_new
                res(config)
            }
        })
    } else {
        let token = Cookies.get('token')
        if (token) { // 判断是否存在token，如果存在的话，则每个http header都加上token
            config.headers['Authorization'] = 'Bearer ' + token
        }
        return config
    }
}, error => {
    return Promise.reject(error)
})

// 为返回的值做统一处理
http.interceptors.response.use(
    response => {
        const res = response
        // 默认0为正确
        if (res.data.code == 200) return res.data
        window.queue_messageArr.push(res.data.msg || 'Error')
        // Message({
        //     message: res.data.msg || 'Error',
        //     type: 'error',
        //     duration: 1 * 1000
        // })
        // 需要退出登录的全局拦截
        codeForReload.forEach(item => {
            if (item == res.data.code) {
                // 取消登录状态，刷新页面，回到登录页面
                Cookies.remove('token')
                Cookies.remove('tokenExpiresTime')
                localStorage.removeItem('role')
                localStorage.removeItem('menu')
                sessionStorage.removeItem('userinfo')
                location.reload()
                return
            }
        })
        throw new Error('接口调用失败')
        // 自动登录
        return res
    },
    error => {
        console.log('err' + error) // for debug
        window.queue_messageArr.push(error.msg || '网络超时,请稍后再试')
        // Message({
        //     message: error.message,
        //     type: 'error',
        //     duration: 2 * 1000
        // })
        return Promise.reject(error)
    }
)

const excelErrInfo = '导出失败'
// 为导出做统一处理:普通报表 + 报表中心的下载(目前2处自己实现,没有封装)
exportTable.interceptors.response.use(
    response => {
        return new Promise((res, rej) => {
            let fileReader = new FileReader();
            fileReader.onload = function(e) {
                try {
                    let resu = e.target.result.split(',')[1]
                    resu = Base64.decode(resu)
                    let jsonData = JSON.parse(resu)
                    if (typeof jsonData == 'object') {
                        if (jsonData['code'] && jsonData['code'] != 0) {
                            Message({ type: 'error', message: jsonData['message'], duration: 1000 })
                        }
                    }
                } catch (e) {
                    res(response)
                }
            }
            fileReader.readAsDataURL(response.data);
        })
    },
    error => {
        Message({
            message: excelErrInfo,
            type: 'error',
            duration: 2 * 1000
        })
        return Promise.reject(error)
    }
)

// 文件请求类
export const downFile = axios.create({
    timeout: timeout * 2,
    responseType: 'blob'
})
downFile.interceptors.request.use(config => {
    let token = Cookies.get('token')
    if (token) { config.headers['Authorization'] = token }
    return config
}, error => {
    return Promise.reject(error)
})
downFile.interceptors.response.use(
    response => {
        return new Promise((res, rej) => {
            let fileReader = new FileReader();
            fileReader.onload = function(e) {
                try {
                    let resu = e.target.result.split(',')[1]
                    resu = Base64.decode(resu)
                    let jsonData = JSON.parse(resu);
                    if (typeof jsonData == 'object') {
                        if (jsonData['code'] && jsonData['code'] != 0) {
                            Message({ type: 'error', message: jsonData['msg'], duration: 1000 })
                        }
                    }
                } catch (e) {
                    // 获取文件名
                    let fileName = response.headers['content-disposition'] || ''
                    let arr = fileName.split(';')
                    arr.forEach(i => {
                        if (i.indexOf('filename=') > -1) {
                            fileName = i.replace('filename=', '')
                        }
                    })
                    // 后台给的名字时encode过的,需要decode一下
                    response.fileName = decodeURI(fileName)
                    res(response)
                }
            }
            fileReader.readAsDataURL(response.data);
        })
    },
    error => {
        console.log('文件获取失败', error);
        Message({
            message: '获取文件失败',
            type: 'error',
            duration: 2 * 1000
        })
        return Promise.reject(error)
    }
)

