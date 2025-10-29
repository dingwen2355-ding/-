// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 志愿团队列表
export function listVolunteerTeam(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerTeam/page`,
        data
    })
}

// 志愿团队详情
export function infoVolunteerTeam(id) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerTeam/${id}`
    })
}

// 志愿团队新增
export function addVolunteerTeam(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerTeam/save`,
        data
    })
}

// 志愿团队修改
export function editVolunteerTeam(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerTeam/update`,
        data
    })
}

// 志愿团队删除
export function deleteVolunteerTeam(id) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerTeam/delete/${id}`
    })
}

// 志愿团队绑定志愿者
export function volunteerTeamAddUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerTeam/addUser`,
        data
    })
}

// 志愿团队解绑志愿者
export function volunteerTeamDelUser(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerTeam/deleteUser`,
        data
    })
}

