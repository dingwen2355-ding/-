// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 设备通道列表
export function pageDeviceChannel(data) {
    return http({
        method: 'post',
        url: api + `/civilization/deviceChannel/page`,
        data
    })
}

// 设备通道删除
export function deleteDevice(id) {
    return http({
        method: 'post',
        url: api + `/civilization/deviceChannel/delete/${id}`
    })
}

// 同步设备通道
export function synchChannel() {
    return http({
        method: 'post',
        url: api + `/sync/video/synchChannel`
    })
}

// 根据划分的组织查询视频列表
export function getListByRegionCode(data) {
    return http({
        method: 'post',
        url: api + `/civilization/deviceChannel/getListByRegionCode`,
        data
    })
}
