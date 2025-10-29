// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 文明校园列表
export function listCivilizationSchool(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationSchool/page`,
        data
    })
}

// 文明校园详情
export function infoCivilizationSchool(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationSchool/${id}`
    })
}

// 文明校园新增
export function addCivilizationSchool(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationSchool/save`,
        data
    })
}

// 文明校园修改
export function editCivilizationSchool(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationSchool/update`,
        data
    })
}

// 文明校园删除
export function deleteCivilizationSchool(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationSchool/delete/${id}`
    })
}

