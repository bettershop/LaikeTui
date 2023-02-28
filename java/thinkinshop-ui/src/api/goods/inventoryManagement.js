import request from '../https'

// 获取库存列表
export const getStockInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商品库存详细信息
export const getStockDetailInfo = params => {
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