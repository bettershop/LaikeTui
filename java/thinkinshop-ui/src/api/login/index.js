import http from '../http'

// 后台登录
export const login = data => {
    return http({
        method: 'post',
        params: {
            api: 'admin.saas.user.login',
            ...data
        }
    })
}