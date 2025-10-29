// 搜索条件api: 本API是约定好所有需要后端请求的搜索条件API
import { http } from '../network/request'
import { api } from '../config'

export function synchDept() {
    return http({
        method: 'post',
        url: api + `/civilization/third/dept/synch`,
        data: {noFetchChild: 0}
    })
}

// 第三方部门列表
export function getThirdDeptList() {
    return http({
        method: 'post',
        url: api + `/civilization/third/dept/list`
    })
}

// 第三方部门删除
export function deleteDept(id) {
    return http({
        method: 'post',
        url: api + `/civilization/third/dept/delete/${id}`
    })
}
