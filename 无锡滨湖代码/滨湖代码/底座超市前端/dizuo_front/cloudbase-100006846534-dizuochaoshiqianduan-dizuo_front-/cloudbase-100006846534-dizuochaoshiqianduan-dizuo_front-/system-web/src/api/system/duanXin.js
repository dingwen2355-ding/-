import request from '@/utils/request'

// 查询列表
export function list(query) {
  return request({
    url: '/system/duanXinSwitch/list',
    method: 'get',
    params: query
  })
}

// 开关状态修改
export function changeStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: '/system/duanXinSwitch/changeStatus',
    method: 'post',
    data: data
  })
}

