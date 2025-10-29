// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 获取月份列表
export function getMonthList(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationLevel/getMonthList`,
        data
    })
}

// 文明程度指数列表
export function pageCivilizationLevel(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationLevel/page`,
        data
    })
}

// 文明程度指数详情
export function infoCivilizationLevel(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationLevel/${id}`
    })
}

// 文明程度指数新增
export function addCivilizationLevel(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationLevel/save`,
        data
    })
}

// 文明程度指数修改
export function editCivilizationLevel(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationLevel/update`,
        data
    })
}

// 文明程度指数删除
export function deleteCivilizationLevel(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationLevel/delete/${id}`
    })
}

// 文明程度指数批量删除
export function deleteAllCivilizationLevel(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationLevel/deleteAll`,
        data
    })
}