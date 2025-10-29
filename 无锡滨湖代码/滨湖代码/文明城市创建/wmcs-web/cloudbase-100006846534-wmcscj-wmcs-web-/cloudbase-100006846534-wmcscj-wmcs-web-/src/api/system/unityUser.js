// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 部门列表
export function unifyDept(data) {
    return http({
        method: 'post',
        url: api + `/civilization/unify/dept/list`,
        data
    })
}

// 用户列表
export function unifyUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/unify/user/page`,
        data
    })
}

// 同步部门
export function synchDept() {
    return http({
        method: 'post',
        url: api + `/sync/unify/synchDept`
    })
}

// 同步用户
export function synchUser() {
    return http({
        method: 'post',
        url: api + `/sync/unify/synchUser`
    })
}