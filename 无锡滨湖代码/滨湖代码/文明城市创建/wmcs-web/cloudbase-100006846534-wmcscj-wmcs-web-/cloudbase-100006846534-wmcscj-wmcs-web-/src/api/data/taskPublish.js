import { http } from '../../network/request'
import { api } from '../../config'

// 任务发布
// 列表
export function listCheckTask(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkTask/page`,
        data
    })
}

// 详情
export function InfoCheckTask(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkTask/${id}`
    })
}

// 新增
export function addCheckTask(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkTask/save`,
        data
    })
}

// 修改
export function editCheckTask(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkTask/update`,
        data
    })
}

// 删除
export function deleteCheckTask(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkTask/delete/${id}`
    })
}
