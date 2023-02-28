import request from '../https'

// 获取秒杀标签
export const labelIndex = params => {
    return request({
        method: 'post',
        params
    })
}

// 秒杀标签显示开关
export const labelSwitch = params => {
    return request({
        method: 'post',
        params
    })
}

// 秒杀标签上下移动
export const sortMove = params => {
    return request({
        method: 'post',
        params
    })
}

// 秒杀标签置顶
export const labelTop = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除秒杀标签
export const delLabel = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑秒杀标签
export const addLabel = params => {
    return request({
        method: 'post',
        params
    })
}

// 秒杀商品列表数据
export const getProList = params => {
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

// 添加/编辑秒杀商品
export const addPro = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取秒杀记录列表
export const getSecRecord = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除秒杀记录
export const delSecRecord = params => {
    return request({
        method: 'post',
        params
    })
}

// 秒杀标签商品列表
export const secLabelGoodsList = params => {
    return request({
        method: 'post',
        params
    })
}

// 标签商品显示开关
export const labelGoodsSwitch = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除标签商品
export const delPro = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取秒杀设置
export const getSecConfig = params => {
    return request({
        method: 'post',
        params
    })
}

// 配置秒杀设置
export const setSecConfig = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商品规格信息
export const getProAttrList = params => {
    return request({
        method: 'post',
        params
    })
}

// 增加库存
export const addProStock = params => {
    return request({
        method: 'post',
        params
    })
}

// 秒杀订单列表
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

// 获取秒杀售后列表
export const getRefundList = params => {
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