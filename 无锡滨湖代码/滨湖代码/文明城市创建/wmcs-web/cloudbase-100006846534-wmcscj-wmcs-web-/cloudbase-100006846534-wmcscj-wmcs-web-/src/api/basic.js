// 全系统平台通用工具类API
import { downFile, http } from '../network/request'
import { api } from '../config'
import axios from 'axios'
import Cookies from 'js-cookie'

// 下载文件
export function downLoadFile(id, useToken) {
    // 非明确给false时,默认都是带token的
    if (useToken !== false) useToken = true
    if (useToken) {
        return downFile({
            method: 'get',
            url: api + `/file/downloadFile?id=${id}`
            // data: { id: id }
        })
    } else {
        return downFile({
            method: 'get',
            url: api + `/file/api/downloadFile?id=${id}`
            // data: { id: id }
        })
    }
}

// 下载文件:按相对路径获取
export function downLoadFileByUrl(url) {
    return downFile({
        method: 'get',
        url: api + `/file/download?file=${url}`
    })
}

// 下载附件
export function downloadZip(ids) {
    return downFile({
        method: 'get',
        url: api + `/file/downloadZip?ids=${ids}`
    })
}

// 手动上传文件:富文本用,不带token下载用的
export function upLoadFileByFile(file) {
    let formData = new FormData()
    formData.append('file', file)
    return axios.post(api + '/file/api/upload', formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': Cookies.get('token')
        }
    })
}

// 文件转base64:富文本上传转换
export function file2Base64(file) {
    return new Promise((res, rej) => {
        const reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = function() {
            // res(reader.result.split(',')[1])
            // 带data:image/jpeg;base64, 头的
            res(reader.result)
        }
        reader.onerror = function() { rej() }
    })
}

// 获取APP版本信息
export function getAppPubInfo() {
    return http({
        method: 'get',
        url: api + '/server-system/getNew'
    })
}

// 更新APP版本
export function publishAppVersion(data) {
    return http({
        method: 'post',
        url: api + '/server-system/sys/appConfig/save',
        data
    })
}

// 获取当前平台信息
export function getPlaInfo() {
    return http({
        method: 'get',
        url: api + '/system/baseConfig/info'
    })
}

// 修改平台信息
export function setPlaInfo(data) {
    return http({
        method: 'post',
        url: api + '/system/baseConfig/save',
        data
    })
}
