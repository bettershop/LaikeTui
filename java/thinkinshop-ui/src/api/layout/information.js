import request from '../https'

// 修改用户信息
export const updateAdminInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 订单通知
export const noticeList = params => {
    return request({
        method: 'post',
        params
    })
}

// 一键已读
export const noticeRead = params => {
    return request({
        method: 'post',
        params
    })
}
