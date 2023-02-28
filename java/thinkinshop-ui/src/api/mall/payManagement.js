import request from '../https'

// 获取支付类型列表
export const index = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取支付配置参数
export const paymentParmaInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 设置支付配置参数
export const setPaymentParma = params => {
    return request({
        method: 'post',
        params
    })
}

// 设置开启状态
export const setPaymentSwitch = params => {
    return request({
        method: 'post',
        params
    })
}