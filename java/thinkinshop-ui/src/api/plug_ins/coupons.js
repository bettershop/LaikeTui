import request from '../https'

// 获取优惠券信息
export const getCouponCardInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 活动是否显示开关
export const activityisDisplay = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除优惠券活动
export const delCoupon = params => {
    return request({
        method: 'post',
        params
    })
}

// 优惠券活动领取记录
export const seeCouponLogger = params => {
    return request({
        method: 'post',
        params
    })
}

// 优惠券赠送记录
export const seeGiveCouponLogger = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取符合赠券的会员列表
export const getGiveUserInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 赠劵
export const receiveUserCoupon = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取优惠券配置信息
export const getCouponConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑优惠券配置
export const addCouponConfig = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加优惠劵
export const addCoupon = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取指定商品列表
export const getAssignGoods = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取指定分类列表
export const getAssignGoodsClass = params => {
    return request({
        method: 'post',
        params
    })
}