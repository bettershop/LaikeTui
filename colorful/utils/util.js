function formatTime(date) {
  var year = date.getFullYear()
  var month = date.getMonth() + 1
  var day = date.getDate()
  var hour = date.getHours()
  var minute = date.getMinutes()
  var second = date.getSeconds()
  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

function getUesrBg(that){
  wx.request({
    url: that.d.ceshiUrl + '&action=app&m=cart',
    method: 'post',
    data: {
      openid: that.globalData.userInfo.openid,

    },
    header: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    success: function (res) {
      var num = res.data.cart.toString()
      if(num >0){
        wx.setTabBarBadge({
          index: 2,
          text: num,
        })
      }
      


    },
    fail: function (e) {

    },
  });
}

function getUesrBgplus(that,app,is) {
    wx.request({
      url: app.d.ceshiUrl + '&action=app&m=cart',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,

      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if(is){
          that.setData({
            cart: res.data.cart
          })
          return null
        }
        
        if (res.data.cart){
          wx.setTabBarBadge({
            index: 2,
            text: res.data.cart,
          })
        } else {
          wx.removeTabBarBadge({
            index: 2
          })
        }


      },
      fail: function (e) {

      },
    });
}

function getUri(){
  //如果不想安装后台服务，请使用注释这一行做简单数据体验
  //return 'http://xiaochengxu.houjiemeishi.com/zhibo/LKT/index.php?module=api&software_name=3&edition=1.0'
  return 'http://localhost/open/app/LKT/index.php?module=api&software_name=3&edition=1.0'
}

module.exports = {
  formatTime: formatTime,
  getUesrBg: getUesrBg,
  getUesrBgplus: getUesrBgplus,
  getUri:getUri
}

function checkStringEmpty(data){
  if(null == data || "" == data){
    return false;
  }
  return true;
}
