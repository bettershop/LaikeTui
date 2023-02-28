import request from '../https'
// 获取运费列表
export const getFreightInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除运费
export const delFreight = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/修改运费
export const addFreight = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取地区列表
export const getRegion = params => {
    return request({
        method: 'post',
        params
    })
}

// 设置默认开关
export const freightSetDefault = params => {
    return request({
        method: 'post',
        params
    })
}