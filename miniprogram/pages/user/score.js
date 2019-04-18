// pages/user/score.js
var WxParse = require('../../wxParse/wxParse.js');
var app = getApp()
Page({
  data: {
    winWidth: 0,
    winHeight: 0,
    // tab切换  
    currentTab: 0,
    score: null,
    addscore: [],
    fuscore: [],
    remind: '加载中',
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
  onLoad: function () {
    var that = this;
    that.setData({
      bgcolor: app.d.bf_color
    })
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bf_color,
    });
    wx.setNavigationBarTitle({
      title: '个人积分中心',
    })
    that.getRequest();
    /** 
     * 获取系统信息 
     */
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        });
      }

    });
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  /** 
   * 滑动切换tab 
   */
  bindChange: function (e) {
    console.log(e)
    var that = this;
    that.setData({ currentTab: e.detail.current });
  },
  /** 
   * 点击tab切换 
   */
  swichNav: function (e) {
    console.log(e)
    var that = this;
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },
  // 进入我的积分
  getRequest: function () {
     var that = this
     wx.request({
       url: app.d.ceshiUrl + '&action=sign&m=integral',
       method: 'post',
       data: {
         openid: app.globalData.userInfo.openid,
       },
       header: { //请求头
         'Content-Type': 'application/x-www-form-urlencoded'
       },
       success: function (res) {
         var content = res.data.rule;
         WxParse.wxParse('content', 'html', content, that, 5);
          that.setData({
            score: res.data.score, // 积分
            logo: res.data.logo, 
            // rule: res.data.rule,
            sign: res.data.sign, // 获取记录
            consumption: res.data.consumption, // 使用记录
            switch: res.data.switch//转账按钮（0 关闭  1.开启）
          })
         console.log(res.data.sign);
         console.log(333336666);
       },
       
       error: function (e) {
         wx.showToast({
           title: '网络异常！',
           duration: 2000,
         });
       },
     });
  }
})