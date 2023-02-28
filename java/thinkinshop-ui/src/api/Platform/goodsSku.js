import request from '../https'

// 获取属性名称下拉
export const getSkuAttributeList = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商品属性信息
export const getSkuInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/修改商品属性
export const addSku = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/修改商品名称
export const addSkuName = params => {
    return request({
        method: 'post',
        params
    })
}

// 批量删除商品属性
export const delSku = params => {
    return request({
        method: 'post',
        params
    })
}

// 商品属性生效开关
export const setSkuSwitch = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商品属性属性值列表
export const getSkuList = params => {
    return request({
        method: 'post',
        params
    })
}