import request from '../https'

// 获取系统基本配置
export const getSystemIndex = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加修改系统基本配置
export const addSystemConfig = params => {
    return request({
        method: 'post',
        params
    })
}

// 列表api
export const index = params => {
  return request({
    method: 'post',
    params
  })
}
//保存/编辑
export const save = params => {
  return request({
    method: 'post',
    params
  })
}
