import request from '../../https'

// 获取搜索配置
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
