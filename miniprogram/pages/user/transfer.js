// pages/user/transfer.js
var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    inp_money: 0,
    iv: '',
    encryptedData: '',
    islogin: false,
    remind: '加载中',
    bank_name: ''
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
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    
    wx.checkSession({
      success: function (e) {
        console.log(e)
        console.log('session_key 未过期' + app.globalData.userInfo.session_key)
        //session_key 未过期，并且在本生命周期一直有效
        app.globalData.userInfo['session_key'] = app.globalData.userInfo.session_key;
       
      },
      fail: function () {
        // session_key 已经失效，需要重新执行登录流程
        wx.login({
          success: function (res) {
            var code = res.code;
            that.globalData.code = res.code;
            //取出本地存储用户信息，解决需要每次进入小程序弹框获取用户信息
            var userinfo = wx.getStorageSync('userInfo');
            that.globalData.userInfo = userinfo;
            app.getUserSessionKey(code, cb);
          }
        }); //重新登录
      }
    });
    this.setData({
      bgcolor: app.d.bf_color
    });
  },
//获取好友ID
  withdrawals: function (e) {
    // console.log(e);
    var that = this;
    // console.log(6666)
    var user_id = e.detail.value.user_id;

    wx.redirectTo({//跳转
      url: "../user/transfer1?user_id=" + user_id,
    })

  }
  
  

})