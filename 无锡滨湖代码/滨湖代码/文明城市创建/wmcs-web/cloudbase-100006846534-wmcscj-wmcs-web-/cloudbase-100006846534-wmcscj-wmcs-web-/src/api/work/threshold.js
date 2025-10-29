// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 阈值管理列表
export function listThreshold(data) {
    return http({
        method: 'post',
        url: api + `/civilization/threshold/page`,
        data
    })
}

// 阈值管理详情
export function infoThreshold(id) {
    return http({
        method: 'post',
        url: api + `/civilization/threshold/${id}`
    })
}

// 阈值管理新增
export function addThreshold(data) {
    return http({
        method: 'post',
        url: api + `/civilization/threshold/save`,
        data
    })
}

// 阈值管理修改
export function editThreshold(data) {
    return http({
        method: 'post',
        url: api + `/civilization/threshold/update`,
        data
    })
}

// 阈值管理删除
export function deleteThreshold(id) {
    return http({
        method: 'post',
        url: api + `/civilization/threshold/delete/${id}`
    })
}

