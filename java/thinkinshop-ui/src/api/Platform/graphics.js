import request from '../https'

// 添加/编辑图片上传配置
export const addImageConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取商户信息
export const getImageConfigInfo = params => {
    return request({
        method: 'post',
        params
    })
}