// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 考核周期列表
export function pageAssessCycle(data) {
    return http({
        method: 'post',
        url: api + `/civilization/assessCycle/page`,
        data
    })
}

// 考核周期详情
export function infoAssessCycle(id) {
    return http({
        method: 'post',
        url: api + `/civilization/assessCycle/${id}`
    })
}

// 考核周期新增
export function addAssessCycle(data) {
    return http({
        method: 'post',
        url: api + `/civilization/assessCycle/save`,
        data
    })
}

// 考核周期修改
export function editAssessCycle(data) {
    return http({
        method: 'post',
        url: api + `/civilization/assessCycle/update`,
        data
    })
}

// 考核周期删除
export function deleteAssessCycle(id) {
    return http({
        method: 'post',
        url: api + `/civilization/assessCycle/delete/${id}`
    })
}

// 查询最后期数
export function getLastNumber(){
    return http({
        method: 'get',
        url: api + `/civilization/assessCycle/number`
    }) 
}

// 同步周期
export function synchCycle() {
    return http({
        method: 'get',
        url: api + `/civilization/wmb/synchCycle`
    }) 
}