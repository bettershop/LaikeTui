import request from '../https'


// 获取页面数据
export const index = params => {
  return request({
    method: 'post',
    params
  })
}
// 保存/编辑
export const save = params => {
  return request({
    method: 'post',
    params
  })
}
// 删除
export const del = params => {
  return request({
    method: 'post',
    params
  })
}
