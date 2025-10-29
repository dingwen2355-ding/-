// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 指标类型列表
export function pageIndicatorType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/indicatorType/page`,
        data
    })
}

// 指标类型详情
export function addIndicatorType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/indicatorType/save`,
        data
    })
}

// 指标类型详情
export function infoIndicatorType(id) {
    return http({
        method: 'post',
        url: api + `/civilization/indicatorType/${id}`
    })
}

// 指标类型修改
export function editIndicatorType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/indicatorType/update`,
        data
    })
}

// 指标类型删除
export function deleteIndicatorType(id) {
    return http({
        method: 'post',
        url: api + `/civilization/indicatorType/delete/${id}`
    })
}

// 查询最后期数
export function getLastNumber(){
    return http({
        method: 'get',
        url: api + `/civilization/indicatorType/number`
    }) 
}

