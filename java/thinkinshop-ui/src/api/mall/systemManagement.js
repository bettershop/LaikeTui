import request from '../https'
import qs from 'qs'
// 获取系统基础配置
export const getSystemIndex = params => {
  return request({
    method: 'post',
    params
  })
}

// 添加/修改系统基础配置信息
export const addSystemConfig = data => {
    return request({
      method: 'post',
      data: qs.stringify(data)
    })
}

// 获取协议列表
export const getAgreementIndex = params => {
    return request({
      method: 'post',
      params
    })
}

// 添加、编辑协议
export const addAgreement = params => {
  return request({
    method: 'post',
    params
  })
}

// 删除协议
export const delAgreement = params => {
  return request({
    method: 'post',
    params
  })
}

// 常见问题修改
export const updateCommonProblem = params => {
    return request({
      method: 'post',
      params
    })
}

// 新手指南修改
export const updateBeginnerGuide = params => {
    return request({
      method: 'post',
      params
    })
}

// 售后服务修改
export const updateRefundService = params => {
    return request({
      method: 'post',
      params
    })
}

// 关于我们修改
export const updateAboutMe = (params,data) => {
    return request({
      method: 'post',
      data: qs.stringify(data),
      params
    })
}