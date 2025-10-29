import { http } from '../../network/request'
import { api } from '../../config'

// 督察点位类型列表
export function listCheckPointsType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/page`,
        data
    })
}

// 查询所有类型列表：用于左侧列表
export function getAllTypeList(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/list`,
        data
    })
} 

// 督察点位类型详情
export function infoCheckPointsType(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/${id}`
    })
}

// 督察点位类型新增
export function addCheckPointsType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/save`,
        data
    })
}

// 督察点位类型修改
export function editCheckPointsType(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/update`,
        data
    })
}

// 督察点位类型删除
export function deleteCheckPointsType(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsType/delete/${id}`
    })
}

// 点位:检查内容分页
export function getPointsContentList(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkItem/page`,
        data
    })
}

// 点位:检查内容详情
export function getPointsContentInfo(id) {
    return http({
        method: 'get',
        url: api + `/civilization/checkItem/${id}`
    })
}

// 点位:检查内容删除
export function delPointsContent(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkItem/delete/${id}`
    })
}

// 点位:编辑
export function editPointsContent(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkItem/update`,
        data
    })
}

// 点位:新增
export function addPointsContent(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkItem/save`,
        data
    })
}

// 同步点位类型数据
export function synchPointsType() {
    return http({
        method: 'get',
        url: api + `/civilization/wmb/synchCheckPointsType`
    })
}

// 同步点位类型检查项数据
export function synchCheckItem() {
    return http({
        method: 'get',
        url: api + `/civilization/wmb/synchCheckItem`
    })
}