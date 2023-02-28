import request from '../https'

// 获取积分商品列表
export const integralGoodsList = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除积分商品
export const delIntegralGoods = params => {
    return request({
        method: 'post',
        params
    })
}

// 置顶积分商品
export const topIntegralGoods = params => {
    return request({
        method: 'post',
        params
    })
}

// 积分商品列表
export const getProList = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑积分商品
export const addIntegral = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加库存
export const addStock = params => {
    return request({
        method: 'post',
        params
    })
}

// 商品类别、品牌级联
export const get_class = params => {
    return request({
        method: 'post',
        params
    })
}


// 积分订单列表
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

// 关闭订单
export const closeOrder = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取积分售后列表
export const getRefundList = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取积分配置信息
export const getConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑配置信息
export const addConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}
