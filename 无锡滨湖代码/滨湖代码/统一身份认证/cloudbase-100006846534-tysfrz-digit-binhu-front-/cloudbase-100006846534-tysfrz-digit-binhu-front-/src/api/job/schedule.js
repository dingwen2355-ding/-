import request from '@/router/axios';

export const getList = (current, size, params) => {
  return request({
    url: '/api/blade-system/job/schedule/list',
    method: 'get',
    params: {
      ...params,
      current,
      size,
    }
  })
}

export const getPostList = (tenantId) => {
  return request({
    url: '/api/blade-system/post/select',
    method: 'get',
    params: {
      tenantId
    }
  })
}

export const getDetail = (id) => {
  return request({
    url: '/api/blade-system/job/schedule/info/' + id,
    method: 'get',
    params: {
      id
    }
  })
}

export const remove = (ids) => {
  return request({
    url: '/api/blade-system/job/schedule/delete',
    method: 'post',
    params: {
      ids,
    }
  })
}

export const add = (row) => {
  return request({
    url: '/api/blade-system/job/schedule/save',
    method: 'post',
    data: row
  })
}

export const update = (row) => {
  return request({
    url: '/api/blade-system/job/schedule/update',
    method: 'post',
    data: row
  })
}


export const run = (jobIds) => {
  return request({
    url: '/api/blade-system/job/schedule/run',
    method: 'post',
    data: [jobIds]
  })
}

export const resume = (jobIds) => {
  return request({
    url: '/api/blade-system/job/schedule/resume',
    method: 'post',
    data: [jobIds]
  })
}

export const pause = (jobIds) => {
  return request({
    url: '/api/blade-system/job/schedule/pause',
    method: 'post',
    data: [jobIds]
  })
}
