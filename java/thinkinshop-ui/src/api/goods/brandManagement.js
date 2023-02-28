import request from '../https'

// 保存/编辑品牌信息
export const addBrand = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取品牌信息
export const getBrandInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 品牌置顶
export const brandByTop = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除品牌
export const delBrand = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取国家列表
export const getCountry = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取分类信息
export const getClassInfo = params => {
    return request({
        method: 'post',
        params
    })
}