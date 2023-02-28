import request from '../https'

// 获取商品分类信息
export const getClassInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑类别
export const addClass = params => {
    return request({
        method: 'post',
        params
    })
}

// 类别置顶
export const classSortTop = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除当前类别
export const delClass = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取当前分类的所有上级分类
export const getClassLevelTopAllInfo = params => {
    return request({
        method: 'post',
        params
    })
}