// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 督察员列表
export function listCheckUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/page`,
        data
    })
}

// 督察员详情
export function infoCheckUser(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/${id}`
    })
}

// 督察员新增
export function addCheckUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/save`,
        data
    })
}

// 督察员修改
export function editCheckUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/update`,
        data
    })
}

// 督察员删除
export function deleteCheckUser(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/delete/${id}`
    })
}

// 督察员锁定
export function stopCheckUser(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/stop/${id}`
    })
}

// 督察员开启
export function startCheckUser(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/start/${id}`
    })
}

//绑定用户
export function bindUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/bindUser`,
        data
    }) 
}

//解除绑定用户
export function unbindUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkUser/unbindUser`,
        data
    })   
}

//统计用户数量
export function countByStatus(){
    return http({
        method: 'get',
        url: api + `/civilization/checkUser/countByStatus`,
    })
}