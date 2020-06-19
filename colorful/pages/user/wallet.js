var app = getApp();
Page({
  data: {
    winWidth: 0,
    winHeight: 0,
    currentTab: 0,
    angle: 0,
    remind: '加载中',
    detailed_commission:0
  },
  //页面加载完成函数 
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  onLoad: function (options) {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bf_color, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    this.setData({
      bgcolor: app.d.bf_color
    });
    this.requestMyData();
  },
  /** 
     * 滑动切换tab 
     */
  bindChange: function (e) {
    var that = this;
    that.setData({ 
      currentTab: e.detail.current 
    });
  },
  /** 
   * 点击tab切换 
   */
  swichNav: function (e) {
    var that = this;
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },
  // 请求我的数据
  requestMyData: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=details',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {       
        var status = res.data.status;
        if (status == 1) {
          var user = res.data.user;
          that.setData({
            user: user,
            list_1: res.data.list_1,
            list_2: res.data.list_2,
            list_3: res.data.list_3,
            detailed_commission: res.data.detailed_commission ? res.data.detailed_commission:0
          });
        } else {
          wx.showToast({
            title: '非法操作！',
            duration: 2000
          });
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
})