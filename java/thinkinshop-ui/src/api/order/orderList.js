import request from '../https'

// 获取订单列表
export const orderList = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除订单
export const delOrder = params => {
    return request({
        method: 'post',
        params
    })
}

// 订单详情
export const orderDetailsInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 编辑订单页面详情
export const editOrderView = params => {
    return request({
        method: 'post',
        params
    })
}

// 编辑订单
export const saveEditOrder = params => {
    return request({
        method: 'post',
        params
    })
}

// 发货界面信息
export const deliveryView = params => {
    return request({
        method: 'post',
        params
    })
}

// 发货提交
export const deliverySave = params => {
    return request({
        method: 'post',
        params
    })
}

// 打印订单
export const orderPrint = params => {
    return request({
        method: 'post',
        params
    })
}

//代客下单
export const helpOrder = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商品列表
export const getGoodsConfigureList = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取订单统计
export const orderCount = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取代客下单结算数据
export const Settlement = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取物流信息
export const kuaidishow = params => {
    return request({
        method: 'post',
        params
    })
}
