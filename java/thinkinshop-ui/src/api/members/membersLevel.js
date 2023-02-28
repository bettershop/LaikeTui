import request from '../https'

// 获取等级列表
export const getUserGradeInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加等级
export const addUserGrade = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除等级
export const delUserGrade = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取赠送商品下拉数据
export const getGiveGoodsList = params => {
    return request({
        method: 'post',
        params
    })
}
