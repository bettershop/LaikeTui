import request from '../https'

// 获取商户营业报表
export const mchTurnoverReport = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商户新增用户报表
export const mchTurnoverNewUserReport = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商户订单报表
export const mchTurnoverOrderReport = params => {
    return request({
        method: 'post',
        params
    })
}