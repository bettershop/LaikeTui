import request from '../https'

// 获取店铺列表
export const getMchInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除店铺
export const delMchInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 编辑店铺
export const modifyMchInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取店铺审核列表
export const getMchExamineInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 店铺审核通过/拒绝
export const examineMch = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商品审核列表
export const getGoodsExamineInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商品详情信息
export const getGoodsDetailInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 商品审核
export const goodsExamine = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取店铺提现审核列表
export const getWithdrawalExamineInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 店铺提现审核
export const withdrawalExamine = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取提现记录列表
export const getWithdrawalInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商城配置信息
export const getStoreConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/修改商城配置
export const setStoreConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}