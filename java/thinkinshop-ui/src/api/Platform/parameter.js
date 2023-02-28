import request from '../https'

// 添加/编辑小程序配置参数
export const addThridParmate = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取小程序发布列表
export const getThridParmate = params => {
    return request({
        method: 'post',
        params
    })
}
