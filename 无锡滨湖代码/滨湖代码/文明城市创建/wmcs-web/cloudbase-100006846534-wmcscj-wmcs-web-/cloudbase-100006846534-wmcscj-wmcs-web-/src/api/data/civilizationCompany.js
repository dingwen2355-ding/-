// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 文明单位列表
export function listCivilizationCompany(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationCompany/page`,
        data
    })
}

// 文明单位详情
export function infoCivilizationCompany(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationCompany/${id}`
    })
}

// 文明单位新增
export function addCivilizationCompany(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationCompany/save`,
        data
    })
}

// 文明单位修改
export function editCivilizationCompany(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationCompany/update`,
        data
    })
}

// 文明单位删除
export function deleteCivilizationCompany(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationCompany/delete/${id}`
    })
}

// 文明单位复制上报全国级
export function copyCivilizationCompany(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationCompany/copy`,
        data
    })
}
