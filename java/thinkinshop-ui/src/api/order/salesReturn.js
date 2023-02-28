import request from '../https'

// 获取退货列表
export const getRefundList = params => {
    return request({
        method: 'post',
        params
    })
}

// 退货通过拒绝
export const examine = params => {
    return request({
        method: 'post',
        params
    })
}