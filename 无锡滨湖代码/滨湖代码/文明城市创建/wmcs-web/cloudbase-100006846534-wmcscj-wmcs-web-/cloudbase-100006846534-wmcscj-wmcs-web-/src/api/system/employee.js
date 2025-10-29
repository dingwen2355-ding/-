import { http } from '../../network/request'
import { api } from '../../config'

// 员工列表
export function pageEmployee(data) {
    return http({
        method: 'post',
        url: api + `/system/user/page`,
        data
    })
}

// 员工详情
export function infoEmployee(id) {
    return http({
        method: 'post',
        url: api + `/system/user/${id}`
    })
}

// 员工新增
export function addEmployee(data) {
    return http({
        method: 'post',
        url: api + `/system/user/save`,
        data
    })
}

// 员工修改
export function editEmployee(data) {
    return http({
        method: 'post',
        url: api + `/system/user/update`,
        data
    })
}

// 员工删除
export function deleteEmployee(id) {
    return http({
        method: 'post',
        url: api + `/system/user/delete/${id}`
    })
}

