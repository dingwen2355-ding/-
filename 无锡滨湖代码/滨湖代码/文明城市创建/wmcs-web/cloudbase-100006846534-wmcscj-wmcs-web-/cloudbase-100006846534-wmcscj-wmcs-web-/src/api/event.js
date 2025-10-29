// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../network/request'
import { api } from '../config'

// 工单总览列表
export function listWorkRecord(data) {
    return http({
        method: 'post',
        url: api + `/civilization/workRecord/page`,
        data
    })
}

// 告警列表
export function listWorkWarn(data) {
    return http({
        method: 'post',
        url: api + `/civilization/workWarn/page`,
        data
    })
}

// 现象工单列表
export function listAppearance() {
    return http({
        method: 'get',
        url: api + `/civilization/workWarn/groupList`
    })
}
