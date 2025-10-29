// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 权重管理列表
export function pageWeight(data) {
    return http({
        method: 'post',
        url: api + `/civilization/weight/page`,
        data
    })
}

// 权重管理详情
export function infoWeight(id) {
    return http({
        method: 'post',
        url: api + `/civilization/weight/${id}`
    })
}

// 权重管理新增
export function addWeight(data) {
    return http({
        method: 'post',
        url: api + `/civilization/weight/save`,
        data
    })
}

// 权重管理修改
export function editWeight(data) {
    return http({
        method: 'post',
        url: api + `/civilization/weight/update`,
        data
    })
}

// 权重管理删除
export function deleteWeight(id) {
    return http({
        method: 'post',
        url: api + `/civilization/weight/delete/${id}`
    })
}


