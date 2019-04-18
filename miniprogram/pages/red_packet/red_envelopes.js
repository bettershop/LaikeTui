var app = getApp();
var common = require("../../utils/common.js");
Page({
  data: {
    currentTab: 0,
    remind: '加载中',
    rtype: true,
    checked:1
  },

  //下拉刷新
  onPullDownRefresh: function () {
    var that = this;
    console.log(that);
    console.log(77878);
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    var currentTab = that.data.currentTab;
    if (currentTab == 0){
      that.send();//发送
    } else if (currentTab == 1){
      that.mycoupon();//收到
    }else{
      that.detail();//钱包明细
    }
    
  },
  //页面加载完成函数
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
    var currentTab = that.data.currentTab;
    if (currentTab == 0) {
      that.send();//发送
    } else if (currentTab == 1) {
      that.mycoupon();//收到
    } else {
      that.detail();//钱包明细
    }
  },
  onLoad: function (options) {
    var backgroundColor = app.d.h_color;
    wx.setNavigationBarColor({
      frontColor: '#ffffff',//
      backgroundColor: backgroundColor,//页面标题为路由参数
    });
    wx.setNavigationBarTitle({
      title: '我的记录',
      success: function () {
      },
    });

    var that = this;
    wx.request({
      //进入领红包页面
      // 显示头像，获取比值
      url: app.d.ceshiUrl + '&action=red_packet&m=send',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          r01: res.data.r_1,//红包详情
          money: res.data.money,//发出红包金额
          num: res.data.num,//发出红包总数量
          total:res.data.total,//红包总金额
          re01: res.data.re01,//系统参数
          type1:1,//做分类处理（1.我发出的  2.我收到的  3.钱包明细）
        });
        wx.setNavigationBarColor({
          frontColor: '#ffffff',//
          backgroundColor: app.d.h_color //页面标题为路由参数
        });
      },
      fail: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      },
    })
  },
  // 跳转页面1111111
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
        this.send();
      } else if (current == 1) {
        this.mycoupon();
      } else if (current == 2) {
        this.detail();
      }
    };
  },
  //发送红包
  send: function () {
    var that = this;
    checked: 1
    wx.request({
      url: app.d.ceshiUrl + '&action=red_packet&m=send',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          r01: res.data.r_1,//红包详情
          money: res.data.money,//发出红包金额
          num: res.data.num,//发出红包总数量
          total: res.data.total,//红包总金额

          type1: 1,//做分类处理（1.我发出的  2.我收到的  3.钱包明细）
          // type2: res.data.type2,//做分类处理（1.失效  2.3天失效  3.时间还长）
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
  // 收到红包
  mycoupon: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=red_packet&m=received',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          r01: res.data.r_1,//红包详情
          money: res.data.money,//发出红包金额
          num: res.data.num,//发出红包总数量
          type1: 2,//做分类处理（1.我发出的  2.我收到的  3.钱包明细）
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
// 钱包明细
  detail: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=red_packet&m=detailed',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
        typye:1,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          r: res.data.r,//红包详情
          time: res.data.time,//当前时间
          type1: 3,//做分类处理（1.我发出的  2.我收到的  3.钱包明细）
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
  checkBtn: function (e) {
    var that = this
    var checked = e.currentTarget.dataset.key
    that.setData({
      checked: checked,
      page: 0,
    })
    wx.request({
      url: app.d.ceshiUrl + '&action=red_packet&m=detailed',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
        typye:checked,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          r: res.data.r,//红包详情
          time: res.data.time,//当前时间
          type1: 3,//做分类处理（1.我发出的  2.我收到的  3.钱包明细）
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
})