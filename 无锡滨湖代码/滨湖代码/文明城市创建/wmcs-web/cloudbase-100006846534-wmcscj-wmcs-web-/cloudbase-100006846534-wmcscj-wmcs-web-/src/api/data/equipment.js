import { http } from '../../network/request'
import { api } from '../../config'

// 设备列表
export function listEquipment(data) {
    return http({
        method: 'post',
        url: api + `/system/device/page`,
        data
    })
}

// 设备详情
export function InfoEquipment(id) {
    return http({
        method: 'post',
        url: api + `/system/device/get/${id}`
    })
}

// 新增
export function addEquipment(data) {
    return http({
        method: 'post',
        url: api + `/system/device/save`,
        data
    })
}

// 修改
export function editEquipment(data) {
    return http({
        method: 'post',
        url: api + `/system/device/update`,
        data
    })
}

// 删除
export function deleteEquipment(id) {
    return http({
        method: 'post',
        url: api + `/system/device/delete/${id}`
    })
}

// 绑定
export function bindUser(data) {
    return http({
        method: 'post',
        url: api + `/system/device/bind`,
        data
    })
}

// 解除绑定
export function unbindUser(data) {
    return http({
        method: 'post',
        url: api + `/system/device/unbind`,
        data
    })
}
