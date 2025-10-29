// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 部门列表
export function pageDept(data) {
    return http({
        method: 'post',
        url: api + `/system/dept/list`,
        data
    })
}

// 街道列表
export function pageDeptRegion(data) {
    return http({
        method: 'post',
        url: api + `/system/dept/list`,
        data
    })
}

// 部门详情
export function infoDept(id) {
    return http({
        method: 'post',
        url: api + `/system/dept/${id}`
    })
}

// 部门新增
export function addDept(data) {
    return http({
        method: 'post',
        url: api + `/system/dept/save`,
        data
    })
}

// 部门修改
export function editDept(data) {
    return http({
        method: 'post',
        url: api + `/system/dept/update`,
        data
    })
}

// 部门删除
export function deleteDept(id) {
    return http({
        method: 'post',
        url: api + `/system/dept/delete/${id}`
    })
}

// 部门下所有督察员
export function pageUserByDept(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/listByDeptId/${id}`
    })
}

// 更新部门的政务微信信息
export function syncDept() {
    return http({
        method: 'get',
        url: api + `/civilization/unify/dept/updateByWechat`
    })
}
