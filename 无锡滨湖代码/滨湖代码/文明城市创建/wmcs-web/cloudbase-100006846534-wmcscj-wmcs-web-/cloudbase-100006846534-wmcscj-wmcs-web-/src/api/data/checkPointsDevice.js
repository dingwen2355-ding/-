// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 督察点位设备列表
export function listCheckPointsDevice(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsDevice/page`,
        data
    })
}

// 设置通道接入AI
export function saveAI(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsDevice/saveAi`,
        data
    })
}

// 修改通道接入AI
export function updateAI(data) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsDevice/updateAi`,
        data
    })
}

// 设置通道接入AI
export function deleteAi(id) {
    return http({
        method: 'post',
        url: api + `/civilization/checkPointsDevice/deleteAi/${id}`
    })
}