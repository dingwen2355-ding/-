// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'
import qs from 'qs';

// 设备组织分页列表
export function pageDeviceTree(data) {
    return http({
        method: 'post',
        url: api + `/civilization/device/page`,
        data
    })
}

// 设备组织列表
export function pageDevice(data) {
    return new Promise((res, rej) => {
        http({
            method: 'post',
            url: api + `/civilization/device/list`,
            data
        })
            .then(data => {
                let list = data.data.records
                let firstList = list.filter(i => { return !i.parentId })
                function myChildren(v) {
                    let childrenList = list.filter(i => { return i.parentId == v.id })
                    if (childrenList.length > 0) {
                        v.children = childrenList
                        childrenList.forEach(d => { myChildren(d) })
                    }
                }
                // 返回的是平铺数据,组装成层级结构
                firstList.forEach(k => {
                    myChildren(k)
                })
                data.data.records = firstList
                res(data)
            })
    })
}

// 设备组织列表，用于划分区域
export function pageDeviceList(data) {
    return http({
        method: 'post',
        url: api + `/civilization/device/getTreeByNoParent`,
        data
    })
}


// 设备组织删除
export function deleteDevice(id) {
    return http({
        method: 'post',
        url: api + `/civilization/device/delete/${id}`
    })
}

// 设置组织的区划
export function saveRegion(data) {
    return http({
        method: 'post',
        url: api + `/civilization/device/saveRegion`,
        data
    })
}

// 移除组织的所属区域
export function removeRegionCode(id) {
    return http({
        method: 'post',
        url: api + `/civilization/device/removeRegionCode/${id}`
    })
}

// 获取视频流
export function gethls(data) {
    return http({
        method: 'post',
        headers: { 'content-type': 'application/x-www-form-urlencoded' },
        url: api + `/civilization/video/hls`,
        data: qs.stringify(data) 
    })
}

// 同步设备组织
export function synchDevice() {
    return http({
        method: 'post',
        url: api + `/sync/video/synchDevice`
    })
}