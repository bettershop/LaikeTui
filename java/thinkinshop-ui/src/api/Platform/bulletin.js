import request from '../https'

// 获取系统公告信息
export const getSysNoticeInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑系统公告
export const addSysNoticeInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除系统公告
export const delSysNoticeInfo = params => {
    return request({
        method: 'post',
        params
    })
}