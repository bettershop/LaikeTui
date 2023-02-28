import request from '../https'

// 功能导览首页
export const index = params => {
    return request({
        method: 'post',
        params
    })
}

// 编辑商城导览列表
export const functionList = params => {
    return request({
        method: 'post',
        params
    })
}

// 是否显示开关
export const isDisplaySwitch = params => {
    return request({
        method: 'post',
        params
    })
}

// 菜单上下移动
export const move = params => {
    return request({
        method: 'post',
        params
    })
}

// 菜单置顶
export const sortTop = params => {
    return request({
        method: 'post',
        params
    })
}