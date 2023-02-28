import request from '../../https'

// 售后地址列表api
export const index = params => {
    return request({
        method: 'post',
        params
    })
}
//设置默认地址
export const setDefault = params => {
  return request({
    method: 'post',
    params
  })
}
//删除地址
export const del = params => {
  return request({
    method: 'post',
    params
  })
}
//保存/编辑地址
export const save = params => {
  return request({
    method: 'post',
    params
  })
}
