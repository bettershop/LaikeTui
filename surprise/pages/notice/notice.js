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
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      // complete
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    this.DataonLoad();
  },
  onShow: function () {
    this.DataonLoad();
  },
  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '公告', //修改页面标题
    });
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
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
      url: app.d.ceshiUrl + '&action=notice&m=index',
      data: {
        openid: app.globalData.userInfo.openid
      },
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {// 设置请求的 header
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
    // 页面初始化 options为页面跳转所带来的参数
    wx.request({
      url: app.d.ceshiUrl + '&action=notice&m=index',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {// 设置请求的 header
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