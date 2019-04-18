// pages/user/transfer1.js
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
    console.log(options),
      console.log(11111111)
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
    var that = this;
    var user_id = options.user_id;
    var colo = options.user_id;
    var num = colo.substring(4)
    var cor = num.replace(/\b(0+)/gi, "")
    if (num == 0) {
      user_id="user" + 0
    } else {
      user_id = "user" + cor
    }

   
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=selectuser',
      method: 'post',
      data: {
        user_id: user_id,
        openid: app.globalData.userInfo.openid
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          var wx_name = res.data.user.wx_name;
          var headimgurl = res.data.user.headimgurl;
          var money = res.data.user.money;
          that.setData({
            wx_name: wx_name,
            headimgurl: headimgurl,
            money: money,
            transfer_multiple: res.data.user.transfer_multiple,
          });

        } else {
          wx.showToast({
            title: res.data.err,
            icon: 'loading',
            duration: 1500
          });
          setTimeout(function () {
            wx.redirectTo({
              url: "../user/transfer",
            })
          }, 2000);
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
  //转账给好友
  withdrawals1: function (e) {
    console.log(this);
    var that = this;
    console.log(6666)
    var money = Number(e.detail.value.money);
    var user_id = that.options.user_id;
    var mon = Number(that.data.money);//账户余额
    console.log(mon);
    console.log(money);
    if(money>mon){
        wx.showToast({
          title: '余额不足',
          duration: 2000
        });
    }
    if(money < 0 || money == ''){
      wx.showToast({
        title: '正确填写转账金额',
        duration: 2000
      });
    }
    if(mon>=money && money>0){
      wx.request({
        url: app.d.ceshiUrl + '&action=user&m=transfer',
        method: 'post',
        data: {
          user_id: user_id,
          openid: app.globalData.userInfo.openid,
          money: money
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var status = res.data.status;
          if (status == 1) {
            wx.showToast({
              title: res.data.err,
              icon: 'loading',
              duration: 1500
            });

            setTimeout(function () {
              wx.redirectTo({
                url: "../user/wallet",
              })
            }, 2000);

          } else {
            wx.showToast({
              title: res.data.err,
              icon: 'none',
              duration: 1500
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

  }



})