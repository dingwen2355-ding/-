// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 指标管理列表
export function pageIndicator(data) {
    return http({
        method: 'post',
        url: api + `/civilization/indicator/page`,
        data
    })
}

// 指标管理详情
export function infoIndicator(id) {
    return http({
        method: 'post',
        url: api + `/civilization/indicator/${id}`
    })
}

// 指标管理新增
export function addIndicator(data) {
    return http({
        method: 'post',
        url: api + `/civilization/indicator/save`,
        data
    })
}

// 指标管理修改
export function editIndicator(data) {
    return http({
        method: 'post',
        url: api + `/civilization/indicator/update`,
        data
    })
}

// 指标管理删除
export function deleteIndicator(id) {
    return http({
        method: 'post',
        url: api + `/civilization/indicator/delete/${id}`
    })
}

// 查询最后期数
export function getLastNumber(){
    return http({
        method: 'get',
        url: api + `/civilization/indicator/number`
    }) 
}

// 根据类型id查询最大、小数值
export function getValByTypeId(typeId, id){
    return http({
        method: 'post',
        url: api + `/civilization/indicator/getValByTypeId?typeId=${typeId}&id=${id}`
    })
}