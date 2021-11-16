// pages/user/transfer.js
var app = getApp()
Page({

  data: {
    inp_money: 0,
    iv: '',
    encryptedData: '',
    islogin: false,
    remind: '加载中',
    bank_name: ''
  },
  
  onReady: function () {
    var that = this;
    that.setData({
      remind: ''
    });
  },

  onLoad: function (options) {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, 
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    wx.checkSession({
      success: function (e) {
        console.log(e)
        console.log('session_key 未过期' + app.globalData.userInfo.session_key)
        app.globalData.userInfo['session_key'] = app.globalData.userInfo.session_key;

      },
      fail: function () {
        wx.login({
          success: function (res) {
            var code = res.code;
            that.globalData.code = res.code;
            var userinfo = wx.getStorageSync('userInfo');
            that.globalData.userInfo = userinfo;
            app.getUserSessionKey(code, cb);
          }
        }); 
      }
    });
    this.setData({
      bgcolor: app.d.bf_color
    });
  },
  //获取好友ID
  withdrawals: function (e) {
    var that = this;
    var user_id = e.detail.value.user_id;
    wx.redirectTo({//跳转
      url: "../user/transfer1?user_id=" + user_id,
    })
  }



})