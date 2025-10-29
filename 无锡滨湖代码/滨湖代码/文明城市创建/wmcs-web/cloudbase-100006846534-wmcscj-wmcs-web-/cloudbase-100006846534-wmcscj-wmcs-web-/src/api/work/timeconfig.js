// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 时间配置列表
export function listTimeConfig(data) {
    return http({
        method: 'post',
        url: api + `/civilization/timeConfig/page`,
        data
    })
}

// 时间配置详情
export function infoTimeConfig(id) {
    return http({
        method: 'post',
        url: api + `/civilization/timeConfig/${id}`
    })
}

// 时间配置新增
export function addTimeConfig(data) {
    return http({
        method: 'post',
        url: api + `/civilization/timeConfig/save`,
        data
    })
}

// 时间配置修改
export function editTimeConfig(data) {
    return http({
        method: 'post',
        url: api + `/civilization/timeConfig/update`,
        data
    })
}

// 时间配置删除
export function deleteTimeConfig(id) {
    return http({
        method: 'post',
        url: api + `/civilization/timeConfig/delete/${id}`
    })
}

