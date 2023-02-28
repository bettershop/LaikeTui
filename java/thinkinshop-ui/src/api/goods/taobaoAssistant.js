import request from '../https'

// 获取淘宝抓取任务列表
export const getTaoBaoWorkeList = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取淘宝抓取任务明细列表
export const getTaoBaoWorkeListDetail = params => {
    return request({
        method: 'post',
        params
    })
}

// 批量删除淘宝抓取任务
export const delTaoBaoTask = params => {
    return request({
        method: 'post',
        params
    })
}

// 批量执行任务
export const executeTaoBaoById = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑任务
export const addTaoBaoTask = params => {
    return request({
        method: 'post',
        params
    })
}