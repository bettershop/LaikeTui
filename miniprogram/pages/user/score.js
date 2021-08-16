// pages/user/score.js
var WxParse = require('../../wxParse/wxParse.js');
var app = getApp()
Page({
  data: {
    winWidth: 0,
    winHeight: 0,
    currentTab: 0,
    score: null,
    addscore: [],
    fuscore: [],
    remind: '加载中',
  },
  //页面加载完成函数 
  onReady: function () {
    var that = this;
    that.setData({
      remind: ''
    });
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
    wx.showNavigationBarLoading() 
    wx.hideNavigationBarLoading() 
    wx.stopPullDownRefresh() 
  },

  bindChange: function (e) {
    console.log(e)
    var that = this;
    that.setData({ currentTab: e.detail.current });
  },

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

  getRequest: function () {
    var that = this
    wx.request({
      url: app.d.laikeUrl + '&index.php?module=api&action=pi&p=sign&c=Home&m=integral',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: { 
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var content = res.data.rule;
        WxParse.wxParse('content', 'html', content, that, 5);
        that.setData({
          score: res.data.score, // 积分
          logo: res.data.logo,
          sign: res.data.sign, // 获取记录
          consumption: res.data.consumption, // 使用记录
          switch: res.data.switch//转账按钮（0 关闭  1.开启）
        })
        console.log(res.data.sign);

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