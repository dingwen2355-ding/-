// 通用个人中心接口
import { http } from '../network/request'
import { api } from '../config'
import { logout } from './user'
// import md5 from 'js-md5'
import { Base64 } from 'js-base64'

// 登录日志
export function getLoginLogList(params) {
    return http({
        method: 'post',
        url: api + '/system/logininfor/page',
        data: params
    })
}

// 操作日志
export function getOperationLogList(params) {
    return http({
        method: 'post',
        url: api + '/system/operLog/page',
        data: params
    })
}

// 消息通知
export function getNoticeList(params) {
    return http({
        method: 'get',
        url: api + '/ems/v1/notice',
        params: params
    })
}

// 修改密码: 修改密码后强制退出
export function modifyPwd(params) {
    return new Promise((res, rej) => {
        http({
            method: 'post',
            url: api + '/system/updateMyPassword',
            data: {
                oldPassword: Base64.encode(params['oldPassword']),
                newPassword: Base64.encode(params['newPassword'])
            }
        })
        .then(data => {
            res(data)
        })
        .catch(e => {
            rej()
        })
    })
}

// ===============================================配置中心
// ========================账号管理
// 账号列表
export function getEmployeeList(data) {
    return http({
        method: 'post',
        url: api + '/server-system/sys/user/page',
        data
    })
}

// 创建账号
export function createAccount(params) {
    return http({
        method: 'post',
        url: api + '/ems/v1/emp/createEmp',
        data: params
    })
}

// 启/禁用账号
export function patchEmpStatus(data) {
    let url = '/server-system/sys/user/'
    if (data.useStatus === 0) url += 'enable'
    if (data.useStatus === 1) url += 'disable'
    return http({
        method: 'post',
        url: api + url,
        data
    })
}

// 重置密码
export function resetPassword(id) {
    return http({
        method: 'post',
        url: api + '/server-system/sys/user/resetPassword',
        data: { id: id }
    })
}

// =================================角色管理
// 支持分页所有角色
export function getRoleList(data) {
    return http({
        method: 'post',
        url: api + '/system/role/page',
        data
    })
}

// 创建角色
export function addRole(data) {
    return http({
        method: 'post',
        url: api + '/system/role/save',
        data
    })
}

// 角色拥有的资源列表
export function getRoleDeatil(roleId) {
    return http({
        method: 'get',
        url: api + `/system/roleMenu/list/${roleId}`
    })
}

// 修改角色
export function updateRole(params) {
    return http({
        method: 'post',
        url: api + '/system/role/update',
        data: params
    })
}

// 删除角色
export function delRole(id) {
    return http({
        method: 'post',
        url: api + `/system/role/delete/${id}`
    })
}

// =====================================================资源管理
// 资源列表
export function getResourceList(data) {
    return http({
        method: 'post',
        url: api + '/system/menu/list',
        data
    })
}

// 新增资源
export function createResource(data) {
    return http({
        method: 'post',
        url: api + '/system/menu/save',
        data
    })
}

// 修改资源
export function editResource(data) {
    return http({
        method: 'post',
        url: api + '/system/menu/update',
        data
    })
}

// 删除资源
export function delResource(id) {
    return http({
        method: 'post',
        url: api + `/system/menu/delete/${id}`
    })
}

// =====================================================字典配置
// 字典大类列表
export function getDirtList(data) {
    return http({
        method: 'post',
        url: api + '/system/dictType/page',
        data: data
    })
}

// 添加字典大类
export function apiAddDirtType(data) {
    return http({
        method: 'post',
        url: api + '/system/dictType/save',
        data: data
    })
}

// 删除字典大类
export function apiDelDirtType(id) {
    return http({
        method: 'post',
        url: api + `/system/dictType/delete/${id}`
    })
}

// 修改字典大类
export function apiChangeDirtType(data) {
    return http({
        method: 'post',
        url: api + '/system/dictType/update',
        data: data
    })
}

// 字典小类列表(按类型全部搜索)
export function apiDirtDataList(data) {
    return http({
        method: 'post',
        url: api + '/system/dictData/page',
        data: data
    })
}

// 所有字典
export function getAllDictType() {
    return http({
        method: 'get',
        url: api + '/system/dictData/all'
    })
}

// 删除字典小类(不用了)
export function apiDirtDataDel(id) {
    return http({
        method: 'post',
        url: api + `/system/dictData/delete/${id}`
    })
}

// 新增/修改/删除字典小类
export function apiChangeDirtList(list) {
    return http({
        method: 'post',
        url: api + '/system/dictData/set',
        data: list
    })
}
