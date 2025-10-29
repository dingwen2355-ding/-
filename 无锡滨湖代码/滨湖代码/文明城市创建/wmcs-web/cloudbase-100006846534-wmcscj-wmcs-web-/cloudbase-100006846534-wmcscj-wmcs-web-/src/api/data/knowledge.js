import { http } from '../../network/request'
import { api } from '../../config'

// 知识库列表
export function listKnowledge(data) {
    return http({
        method: 'post',
        url: api + `/civilization/knowledge/page`,
        data
    })
}

// 知识库详情
export function InfoKnowledge(id) {
    return http({
        method: 'post',
        url: api + `/civilization/knowledge/${id}`
    })
}

// 新增
export function addKnowledge(data) {
    return http({
        method: 'post',
        url: api + `/civilization/knowledge/save`,
        data
    })
}

// 修改
export function editKnowledge(data) {
    return http({
        method: 'post',
        url: api + `/civilization/knowledge/update`,
        data
    })
}

// 删除
export function deleteKnowledge(id) {
    return http({
        method: 'post',
        url: api + `/civilization/knowledge/delete/${id}`
    })
}

// 导入
export function batchSaveKnowledge(data) {
    return http({
        method: 'post',
        url: api + `/civilization/knowledge/saveBatch`,
        data
    })
}
