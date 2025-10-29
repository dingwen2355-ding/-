// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 月度考核
export function getMonthAssess(data) {
    return http({
        method: 'post',
        url: api + `/civilization/assess/month`,
        data
    })
}

// 季度考核
export function getEvaluatingAssess(data) {
    return http({
        method: 'post',
        url: api + `/civilization/assess/evaluating`,
        data
    })
}
// 年度考核
export function getYearAssess(data) {
    return http({
        method: 'post',
        url: api + `/civilization/assess/year`,
        data
    })
}