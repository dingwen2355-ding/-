import { http } from '../../network/request'
import { api } from '../../config'

// 开始节点接口
export function findstartnode(flowid) {
    return http({
        method: 'get',
        url: api + `/workflow/node/findstartnode/${flowid}`
    })
}

// 事件上报
export function eventReport(data) {
    return http({
        method: 'post',
        url: api + `/workflow/start/report`,
        data
    })
}

// 提交事件
export function submit(data) {
    return http({
        method: 'post',
        url: api + `/workflow/submit/submit`,
        data
    })
}

// 事件总览
export function all(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/page`,
        data
    })
}

// 我的上报
export function myReport(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/myReport`,
        data
    })
}

// 待分派
export function give(data){
    return http({
        method: 'post',
        url: api + `/workflow/search/give`,
        data
    })
}

// 待受理
export function allocate(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/allocate`,
        data
    })
}

// 已受理
export function allocated(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/allocated`,
        data
    })
}

// 待处置
export function handle(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/handle`,
        data
    })
}

// 已处置
export function handled(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/handled`,
        data
    })
}

// 待核查
export function check(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/check`,
        data
    })
}

// 已办结
export function finished(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/finished`,
        data
    })
}

// 转12345
export function citizen(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/citizen`,
        data
    })
}

// 延期列表
export function extension(data) {
    return http({
        method: 'post',
        url: api + `/workflow/extension/page`,
        data
    })
}
// 延期申请
export function extensionSave(data) {
    return http({
        method: 'post',
        url: api + `/workflow/extension/save`,
        data
    })
}

// 延期审核
export function extensionApprove(data) {
    return http({
        method: 'post',
        url: api + `/workflow/extension/examine`,
        data
    })
}
// 申诉列表
export function appeal(data) {
    return http({
        method: 'post',
        url: api + `/workflow/appeal/page`,
        data
    })
}
// 申诉申请
export function appealSave(data) {
    return http({
        method: 'post',
        url: api + `/workflow/appeal/save`,
        data
    })
}
// 申诉审核
export function appealApprove(data) {
    return http({
        method: 'post',
        url: api + `/workflow/appeal/examine`,
        data
    })
}
// 事件详情
export function detail(id, instanceid, scope, taskId) {
    return http({
        method: 'get',
        url: api + `/workflow/info/detail/${id}/${instanceid}?scope=${scope}&taskId=${taskId}`
    })
}

// 获取事件数据
export function edit(data) {
    return http({
        method: 'post',
        url: api + `/workflow/info/edit`,
        data
    })
}

// 获取处理人
export function getUsersByNodeId(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/getUsersByNodeId`,
        data
    })
}

// 获取处理部门
export function getDeptsByNodeId(data) {
    return http({
        method: 'post',
        url: api + `/workflow/search/getDeptsByNodeId`,
        data
    })
}


// 上传
export function uploadFileData(data) {
    return http({
        method: 'post',
        url: api + `/file/upload`,
        data
    })
}

// 消息通知
export function eventNoticeList(data) {
    return http({
        method: 'post',
        url: api + `/workflow/message/page`,
        data
    })
}

// 删除消息通知
export function delEventNotice(id) {
    return http({
        method: 'post',
        url: api + `/workflow/message/delete/${id}`
    })
}


// 事件审核
export function examine(id) {
    return http({
        method: 'post',
        url: api + `/workflow/submit/examine?id=${id}`
        // url: api + `/workflow/start/examine?id=${id}`
    })
}

// 事件派发
export function examineGive(data) {
    return http({
        method: 'post',
        url: api + `/workflow/start/give`,
        data
    })
}

// 废弃事件
export function deleteEvent(id) {
    return http({
        method: 'post',
        url: api + `/workflow/submit/delete/${id}`
    })
}

// 回退派发
export function refreshGive(id) {
    return http({
        method: 'post',
        url: api + `/workflow/submit/refreshGive/${id}`
    })
}

// 回退重做
export function refreshTodo(id) {
    return http({
        method: 'post',
        url: api + `/workflow/submit/refreshTodo/${id}`
    })
}