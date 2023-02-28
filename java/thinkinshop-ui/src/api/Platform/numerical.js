import request from '../https'

// 获取字典列表
export const dictionaryList = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/修改字典表明细
export const addDictionaryTable = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取字典目录下拉
export const dictionaryDirectories = params => {
    return request({
        method: 'post',
        params
    })
}

// 获取字典目录编码
export const directoriesCode = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除字典明细
export const deleteDictionary = params => {
    return request({
        method: 'post',
        params
    })
}

// 字典表明细开关
export const switchDictionaryDetail = params => {
    return request({
        method: 'post',
        params
    })
}



// 数据名称管理
export const dataName = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加/编辑数据字典目录
export const addDictionaryInfo = params => {
    return request({
        method: 'post',
        params
    })
}

// 删除字典目录
export const delDictionary = params => {
    return request({
        method: 'post',
        params
    })
}

// 添加数据名称
export const addDataName = params => {
    return request({
        method: 'post',
        params
    })
}

// 字典表开关
export const switchDictionary = params => {
    return request({
        method: 'post',
        params
    })
}
