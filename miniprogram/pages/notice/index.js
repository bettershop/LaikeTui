var WxParse = require('../../wxParse/wxParse.js');
var app = getApp()
Page({
  data: {
    notice: [],
    radioindex: '',
    pro_id: 0,
    num: 0,
    flag: false,
    cartId: 0,
    numbers: "",
    bindData: {},
  },
  // 下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() 
    this.DataonLoad();
    wx.hideNavigationBarLoading() 
    wx.stopPullDownRefresh() 
  },

  onLoad: function (options) {
    wx.setNavigationBarTitle({
      title: '公告详情', 
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
        id: options.Id
      },
      method: 'POST', 
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var notice = res.data.notice;
        var detail = res.data.notice[0].detail;
        WxParse.wxParse('detail', 'html', detail, that, 5);//处理规则的富文本框
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
        id: options.Id
      },
      method: 'POST', 
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var notice = res.data.notice;
        var detail = res.data.notice[0].detail;
        WxParse.wxParse('detail', 'html', detail, that, 5);
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