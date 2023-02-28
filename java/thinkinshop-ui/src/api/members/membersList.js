import request from '../https'

// 获取来源
export const getDictionaryInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取会员列表
export const getUserInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 会员充值金额
export const userRechargeMoney = params => {
    return request({
        method: 'post',
        params
    })
}

// 修改等级
export const updateUserGradeById = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除用户
export const delUserById = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加用户
export const saveUser = params => {
    return request({
        method: 'post',
        params
    })
}

// 修改会员信息
export const updateUserById = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取会员等级下拉
export const goodsStatus = params => {
    return request({
        method: 'post',
        params
    })
}

// 会员添加默认地址
export const saveAddress = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取开通方式
export const method = params => {
    return request({
        method: 'post',
        params
    })
}