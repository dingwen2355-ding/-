// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../../network/request'
import { api } from '../../config'

// 历史事件列表
export function listWorkWarn(data) {
    return http({
        method: 'post',
        url: api + `/civilization/workWarn/page`,
        data
    })
}

export function getImg(url) {
    return api + `/file/api/downloadAiFile?url=` + url
}

