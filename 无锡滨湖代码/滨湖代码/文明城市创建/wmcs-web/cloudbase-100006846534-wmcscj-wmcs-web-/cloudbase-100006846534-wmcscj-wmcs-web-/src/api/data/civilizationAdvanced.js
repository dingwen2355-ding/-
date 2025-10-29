// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 先进典型人物列表
export function listCivilizationAdvanced(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationAdvanced/page`,
        data
    })
}

// 先进典型人物详情
export function infoCivilizationAdvanced(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationAdvanced/${id}`
    })
}

// 先进典型人物新增
export function addCivilizationAdvanced(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationAdvanced/save`,
        data
    })
}

// 先进典型人物修改
export function editCivilizationAdvanced(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationAdvanced/update`,
        data
    })
}

// 先进典型人物删除
export function deleteCivilizationAdvanced(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationAdvanced/delete/${id}`
    })
}


// 先进典型人物复制上报全国级
export function copyCivilizationAdvanced(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationAdvanced/copy`,
        data
    })
}
