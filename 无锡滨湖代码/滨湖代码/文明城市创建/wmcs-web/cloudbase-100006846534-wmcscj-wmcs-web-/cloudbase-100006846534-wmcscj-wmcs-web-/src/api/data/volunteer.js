// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 志愿者列表
export function listVolunteer(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteer/page`,
        data
    })
}

// 志愿者详情
export function infoVolunteer(id) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteer/${id}`
    })
}

// 志愿者新增
export function addVolunteer(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteer/save`,
        data
    })
}

// 志愿者修改
export function editVolunteer(data) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteer/update`,
        data
    })
}

// 志愿者删除
export function deleteVolunteer(id) {
    return http({
        method: 'post',
        url: api + `/civilization/volunteer/delete/${id}`
    })
}

