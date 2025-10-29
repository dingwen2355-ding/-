// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 文明家园列表
export function listCivilizationFamily(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationFamily/page`,
        data
    })
}

// 文明家园详情
export function infoCivilizationFamily(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationFamily/${id}`
    })
}

// 文明家园新增
export function addCivilizationFamily(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationFamily/save`,
        data
    })
}

// 文明家园修改
export function editCivilizationFamily(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationFamily/update`,
        data
    })
}

// 文明家园删除
export function deleteCivilizationFamily(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationFamily/delete/${id}`
    })
}

