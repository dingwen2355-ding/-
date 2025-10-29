// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 历史事件列表
export function listWorkEvent(data) {
    return http({
        method: 'post',
        url: api + `/civilization/workEvent/page`,
        data
    })
}

// 历史事件详情
export function infoWorkEvent(id) {
    return http({
        method: 'post',
        url: api + `/civilization/workEvent/${id}`
    })
}

// 统计分析
export function totalEventCount(data) {
    return http({
        method: 'post',
        url: api + `/workflow/analysis/eventCount`,
        data
    })
}

export async function downloadFile(data) {
    return await http({
        method: 'get',
        url: api + `/civilization/workEvent/DownloadFile`,
        params: data
    })
}