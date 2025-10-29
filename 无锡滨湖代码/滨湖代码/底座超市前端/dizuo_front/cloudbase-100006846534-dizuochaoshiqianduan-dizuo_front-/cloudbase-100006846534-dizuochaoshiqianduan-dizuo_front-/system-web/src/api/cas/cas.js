import request from '@/utils/request'

// 应用类别
// 查询应用类别列表
export function addApplicationClassification() {
    return request({
        url: '/api/addApplicationClassification',
        method: 'get',
    })
}
export function addApplicationDept() {
    return request({
        url: 'api/addApplicationDept',
        method: 'get',
    })
}
