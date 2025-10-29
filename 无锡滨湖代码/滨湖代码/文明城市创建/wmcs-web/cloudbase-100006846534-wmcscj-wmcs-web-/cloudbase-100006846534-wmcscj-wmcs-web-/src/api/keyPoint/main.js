import { http } from '../../network/request'
import { api } from '../../config'

// 重点问题列表
export function pageMainEvent(data) {
    return http({
        method: 'post',
        // url: api + `/civilization/weight/page`,
        data
    })
}

// 重点点位列表
export function pageMainPoint(data) {
    return http({
        method: 'post',
        // url: api + `/civilization/weight/page`,
        data
    })
}
