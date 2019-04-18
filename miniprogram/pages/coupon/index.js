//获取应用实例  
var app = getApp();
var common = require("../../utils/common.js");
Page({
  data: {
    currentTab: 0,
    remind: '加载中',
    rtype: true,
  },
  //下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  //页面加载完成函数
  onReady: function () {
    var that = this;
    // setTimeout(function () {
    //   that.setData({
        
    //   });
    // }, 1000);
  },
  onLoad: function (options) {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    var rtype = options.type;
    if (rtype == 'receive'){
      this.requestaward();
      this.setData({
        rtype: true,
        bgcolor: app.d.bgcolor
      });
    }else{
      this.setData({
        rtype: false,
        currentTab: parseInt(options.currentTab),
        bgcolor: app.d.bgcolor
      });
      this.requestaward();
      this.mycoupon();
    }

  },
  bindChange: function (e) {
    var cur = e.detail.current;
    var that = this;
    that.setData({ 
      currentTab: cur,
     });
  },
  // 跳转页面
  swichNav: function (e) {
    var that = this;
    if (that.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      var current = e.target.dataset.current;
      that.setData({
        currentTab: parseInt(current),
        isStatus: e.target.dataset.otype,
      });
      if (current == 0) {
        this.requestaward();
      } else if (current == 1) {
        this.mycoupon();
      }
    };
  },
  // 请求活动
  requestaward: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=Coupon&m=index',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var list = res.data.list;
        that.setData({
          list: list,
          remind: ''
        });
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  // 我的优惠券
  mycoupon: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=Coupon&m=mycoupon',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var list = res.data.list;
        that.setData({
          mylist: list,
        });
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  // 点击领取
  receive: function (e) {
    if (e.currentTarget.dataset.point == '领取'){
      var that = this;
      wx.request({
        url: app.d.ceshiUrl + '&action=Coupon&m=receive',
        method: 'post',
        data: {
          openid: app.globalData.userInfo.openid,
          id: e.target.dataset.id
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var status = res.data.status;
          if (status == 1) {
            that.requestaward();
            wx.showToast({
              title: res.data.info,
              duration: 2000
            });
          } else {
            wx.showToast({
              title: res.data.info,
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
    }
  },
  // 点击使用
  getvou: function (e) {
    // if (e.currentTarget.dataset.point == '立即使用') {
      var that = this;
      wx.request({
        url: app.d.ceshiUrl + '&action=Coupon&m=immediate_use',
        method: 'post',
        data: {
          id: e.target.dataset.id,
          openid: app.globalData.userInfo.openid,
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          if(res.data.status == 1){
            wx.switchTab({
              url: '../index/index'
            })
          }else{
            wx.redirectTo({
              url: '../coupon/index?currentTab=1'
            })
          }
        },
        error: function (e) {
          wx.showToast({
            title: '网络异常！',
            duration: 2000
          });
        }
      });
    // }
  }
})