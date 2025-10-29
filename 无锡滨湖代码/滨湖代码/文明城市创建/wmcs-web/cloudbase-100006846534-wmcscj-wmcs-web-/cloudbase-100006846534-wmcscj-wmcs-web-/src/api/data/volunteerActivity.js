// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 志愿活动列表
export function listVolunteerActivity(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerActivity/page`,
        data
    })
}

// 志愿活动详情
export function infoVolunteerActivity(id) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerActivity/${id}`
    })
}

// 志愿活动新增
export function addVolunteerActivity(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerActivity/save`,
        data
    })
}

// 志愿活动修改
export function editVolunteerActivity(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerActivity/update`,
        data
    })
}

// 志愿活动删除
export function deleteVolunteerActivity(id) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteerActivity/delete/${id}`
    })
}

