// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 区划列表
export function pageRegion(data) {
    return http({
        method: 'post',
        url: api + `/system/region/list`,
        data
    })
}

// 区划详情
export function infoRegion(id) {
    return http({
        method: 'post',
        url: api + `/system/region/${id}`
    })
}

// 区划新增
export function addRegion(data) {
    return http({
        method: 'post',
        url: api + `/system/region/save`,
        data
    })
}

// 区划修改
export function editRegion(data) {
    return http({
        method: 'post',
        url: api + `/system/region/update`,
        data
    })
}

// 区划删除
export function deleteRegion(id) {
    return http({
        method: 'post',
        url: api + `/system/region/delete/${id}`
    })
}

