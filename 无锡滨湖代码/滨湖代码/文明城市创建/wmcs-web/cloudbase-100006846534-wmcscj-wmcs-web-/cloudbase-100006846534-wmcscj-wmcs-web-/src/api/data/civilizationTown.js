// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 文明村镇列表
export function listCivilizationTown(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationTown/page`,
        data
    })
}

// 文明村镇详情
export function infoCivilizationTown(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationTown/${id}`
    })
}

// 文明村镇新增
export function addCivilizationTown(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationTown/save`,
        data
    })
}

// 文明村镇修改
export function editCivilizationTown(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationTown/update`,
        data
    })
}

// 文明村镇删除
export function deleteCivilizationTown(id) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationTown/delete/${id}`
    })
}


// 文明村镇复制上报
export function copyCivilizationTown(data) {
    return http({
        method: 'post',
        url: api + `/civilization/civilizationTown/copy`,
        data
    })
}
