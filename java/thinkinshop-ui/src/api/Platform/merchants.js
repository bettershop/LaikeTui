import request from '../https'

// 获取商户信息
export const getShopInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑商城
export const addStore = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除商城
export const delStore = params => {
    return request({
        method: 'post',
        params
    })
}

// 重置密码
export const resetAdminPwd = params => {
    return request({
        method: 'post',
        params
    })
}

// 是否启用开关
export const setStoreOpenSwitch = params => {
    return request({
        method: 'post',
        params
    })
}

// 设置默认商城
export const setStoreDefaultSwitch = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取权限列表
export const getUserRoleInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取权限菜单列表
export const getUserRoleMenuInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 赋予系统管理员商城
export const setUserAdmin = params => {
    return request({
        method: 'post',
        params
    })
}