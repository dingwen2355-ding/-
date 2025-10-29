// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 事件类别列表
export function pageEventType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/eventType/list`,
        data
    })
}

// 事件类别详情
export function infoEventType(id) {
    return http({
        method: 'post',
        url: api + `/civilization/eventType/${id}`
    })
}

// 事件类别新增
export function addEventType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/eventType/save`,
        data
    })
}

// 事件类别修改
export function editEventType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/eventType/update`,
        data
    })
}

// 事件类别删除
export function deleteEventType(id) {
    return http({
        method: 'post',
        url: api + `/civilization/eventType/delete/${id}`
    })
}

