import request from '../https'
// 导出
export const exports = (params,fileName) => {
    return request({
        method: 'post',
        params: params,
    }).then(res => {
        console.log(res);
      let blob = new Blob([res.data], {type: 'application/actet-stream;charset=utf-8'})
        if ('download' in document.createElement('a')) { // 非IE下载
            const elink = document.createElement('a')
            elink.download = fileName + '-' + res.data.size + '.xls'
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click()
            URL.revokeObjectURL(elink.href) // 释放URL 对象
            document.body.removeChild(elink)
        } else { // IE10+下载
            navigator.msSaveBlob(blob, fileName + '-' + res.data.size + '.xls')
        }
    })
}

export const exportss = (params,fileName) => {
    return request({
        method: 'post',
        params: params,
    }).then(res => {
        console.log(res);
      let blob = new Blob([res.data], {type: 'application/actet-stream;charset=utf-8'})
        if ('download' in document.createElement('a')) { // 非IE下载
            const elink = document.createElement('a')
            elink.download = fileName + '-' + res.data.size + '.zip'
            elink.style.display = 'none'
            elink.href = URL.createObjectURL(blob)
            document.body.appendChild(elink)
            elink.click()
            URL.revokeObjectURL(elink.href) // 释放URL 对象
            document.body.removeChild(elink)
        } else { // IE10+下载
            navigator.msSaveBlob(blob, fileName + '-' + res.data.size + '.zip')
        }
    })
}
