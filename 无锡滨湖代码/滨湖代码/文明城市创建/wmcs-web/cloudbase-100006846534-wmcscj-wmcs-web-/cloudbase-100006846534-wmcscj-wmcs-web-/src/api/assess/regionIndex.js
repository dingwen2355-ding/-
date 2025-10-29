// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 区指数列表
export function pageRegionIndex(data) {
    return http({
        method: 'post',
        url: api + `/civilization/regionIndex/page`,
        data
    })
}

// 区指数新增
export function addRegionIndex(data) {
    return http({
        method: 'post',
        url: api + `/civilization/regionIndex/save`,
        data
    })
}

// 区指数修改
export function editRegionIndex(data) {
    return http({
        method: 'post',
        url: api + `/civilization/regionIndex/update`,
        data
    })
}

// 区指数删除
export function deleteRegionIndex(id) {
    return http({
        method: 'post',
        url: api + `/civilization/regionIndex/delete/${id}`
    })
}