// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../network/request'
import { api } from '../config'

// 事件类别列表
export function homeindex() {
    return http({
        method: 'get',
        url: api + `/civilization/index/data/count`,
    })
}

//
export function shijianleixing() {
    return http({
        method: 'get',
        url: api + `/civilization/eventIndex/eventData`,
    })
}
export function eventList() {
    return http({
        method: 'get',
        url: api + `/civilization/eventIndex/eventList`,
    })
}
export function quotaInfo() {
    return http({
        method: 'get',
        url: api + `/civilization/eventIndex/quotaInfo`,
    })
}
export function pointsTypeList(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/page`,
        data: data
    })
}