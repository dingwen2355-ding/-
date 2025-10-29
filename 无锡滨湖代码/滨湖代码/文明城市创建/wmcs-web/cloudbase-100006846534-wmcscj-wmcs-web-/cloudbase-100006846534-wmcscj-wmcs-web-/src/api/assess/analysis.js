// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 专题监测列表
export function specialList(data) {
    return http({
        method: 'post',
        url: api + `/civilization/analysis/specialList`,
        data
    })
}

// 专题监测总得分
export function specialSroceTotal(data) {
    return http({
        method: 'post',
        url: api + `/civilization/analysis/specialSroceTotal`,
        data
    })
}

// 重点问题列表
export function pageMainEvent(data) {
    return http({
        method: 'post',
        url: api + `/civilization/analysis/mainEventList`,
        data
    })
}

// 重点点位列表
export function pageMainPoint(data) {
    return http({
        method: 'post',
        url: api + `/civilization/analysis/mainPointList`,
        data
    })
}

// 问题集中列表
export function keyPointsByJz(data) {
    return http({
        method: 'post',
        url: api + `/civilization/analysis/keyPointsByJz`,
        data
    })
}

// 问题高发列表
export function keyPointsByGf(data) {
    return http({
        method: 'post',
        url: api + `/civilization/analysis/keyPointsByGf`,
        data
    })
}

// 问题顽症列表
export function keyPointsByWz(data) {
    return http({
        method: 'post',
        url: api + `/civilization/analysis/keyPointsByWz`,
        data
    })
}