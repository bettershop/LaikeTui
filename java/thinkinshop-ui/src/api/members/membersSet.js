import request from '../https'
import qs from 'qs'
// 获取会员配置信息
export const getUserConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除/修改配置
export const addUserRule = params => {
    return request({
        method: 'post',
        params
    })
}