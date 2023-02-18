/**
 * 小程序配置文件
 * 这个配置文件不起作用，大家可以不用理会
 */

// 此处主机域名配置是无效的，不需要配置
var host = "https://open.laiketui.com"

var config = {

    host,

    loginUrl: `https://${host}/login`,

    requestUrl: `https://${host}/testRequest`,

    openIdUrl: `https://${host}/openid`,

    tunnelUrl: `https://${host}/tunnel`,

    paymentUrl: `https://${host}/payment`,

    templateMessageUrl: `https://${host}/templateMessage`,

    uploadFileUrl: `https://${host}/upload`,

    downloadExampleUrl: `https://${host}/static/weapp.jpg`
};

module.exports = config
