import request from '../https'

// 获取菜单分类列表
export const getMenuLeveInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑菜单
export const addMenuInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 菜单上下移动
export const moveMenuSort = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除菜单
export const delMenu = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加角色
export const addUserRoleMenu = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除角色
export const delUserRoleMenu = params => {
    return request({
        method: 'post',
        params
    })
}


// 菜单置顶
export const moveTopMenuSort = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取管理员角色信息
export const getRoleListInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取所有商城
export const getBindListInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 解绑/绑定角色
export const bindRole = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取角色拥有的一二级菜单
export const getRoleMenu = params => {
    return request({
        method: 'post',
        params
    })
}

// 验证商户是否绑定
export const verificationBind = params => {
    return request({
        method: 'post',
        params
    })
}
