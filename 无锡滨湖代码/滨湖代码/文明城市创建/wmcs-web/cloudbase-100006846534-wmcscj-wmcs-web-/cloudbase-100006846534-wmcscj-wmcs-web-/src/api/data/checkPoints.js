// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 督察点位列表
export function pageCheckPoints(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPoints/page`,
        data
    })
}

// 督察点位列表
export function listCheckPoints(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPoints/list`,
        data
    })
}

// 督察点位详情
export function infoCheckPoints(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPoints/${id}`
    })
}

// 督察点位新增
export function addCheckPoints(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPoints/save`,
        data
    })
}

// 督察点位修改
export function editCheckPoints(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPoints/update`,
        data
    })
}

// 督察点位删除
export function deleteCheckPoints(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPoints/delete/${id}`
    })
}

// 点位下的所有督察员
export function listByPointsId(id) {
    return http({
        method: 'get',
        url: api + `/civilization/checkUser/listByPointsId/${id}`
    })
}


// 同步点位数据
export function synchPoint() {
    return http({
        method: 'get',
        url: api + `/civilization/wmb/synchCheckPoints`
    })
}


