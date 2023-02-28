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

function getUesrBg(that) {
  wx.request({
    url: that.d.laikeUrl + '&action=app&m=cart',
    method: 'post',
    data: {
      openid: that.globalData.userInfo.openid,

    },
    header: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    success: function (res) {
      var num = res.data.cart.toString()
      if (num > 0) {
        wx.setTabBarBadge({
          index: 3,
          text: num,
        })
      }
    },
    fail: function (e) {
    },
  });
}

function getUesrBgplus(that, app, is) {
  wx.request({
    url: app.d.laikeUrl + '&action=app&m=cart',
    method: 'post',
    data: {
      openid: app.globalData.userInfo.openid,
    },
    header: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    success: function (res) {
      if (is) {
        that.setData({
          cart: res.data.cart
        })
        return null
      }

      if (res.data.cart) {
        wx.setTabBarBadge({
          index: 3,
          text: res.data.cart,
        })
      } else {
        wx.removeTabBarBadge({
          index: 3
        })
      }
    },
    fail: function (e) {
    },
  });
}

/*
* 假设你访问的后台登录路径是：https://www.laiketui.com/app/LKT/index.php?module=Login
* 那么你在这里填的路径应该是：https://www.laiketui.com/app/LKT/index.php?module=api&software_name=3&edition=1.0

* 假设你访问的后台登录路径是：https://www.baidu.com/LKT/index.php?module=Login
* 那么你在这里填的路径应该是：https://www.baidu.com/LKT/index.php?module=api&software_name=3&edition=1.0

* &software_name=3&edition=1.0 这条路径必需带上，不带会发生登录不了的情况
*/
function getUri() {
  return 'http://localhost/open/app/LKT/index.php?module=api&software_name=3&edition=1.0'
}

module.exports = {
  formatTime: formatTime,
  getUesrBg: getUesrBg,
  getUesrBgplus: getUesrBgplus,
  getUri: getUri
}

function checkStringEmpty(data) {
  if (null == data || "" == data) {
    return false;
  }
  return true;
}
