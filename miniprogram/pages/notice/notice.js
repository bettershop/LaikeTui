var app = getApp()
Page({
  data: {
    notice: [],
    radioindex: '',
    pro_id: 0,
    num: 0,
    flag: false,
    cartId: 0,
    numbers: ""
  },
  // 下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() 
    this.DataonLoad();
    wx.hideNavigationBarLoading() 
    wx.stopPullDownRefresh() 
  },
  onShow: function () {
    this.DataonLoad();
  },
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '公告', 
    });
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, 
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    this.setData({
      bgcolor: app.d.bgcolor
    });
    var that = this;
    wx.request({
      url: app.d.laikeUrl + '&action=notice&m=index',
      data: {
        openid: app.globalData.userInfo.openid
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var notice = res.data.notice;
        if (notice == '') {
          var notice = []
        }
        that.setData({
          notice: notice
        })
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },

  // 数据加载
  DataonLoad: function () {
    var that = this;
    wx.request({
      url: app.d.laikeUrl + '&action=notice&m=index',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      method: 'POST', 
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var notice = res.data.notice;
        if (notice == '') {
          var notice = []
        }
        that.setData({
          notice: notice

        })
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },

})