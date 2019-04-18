var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    inp_money:0,
    iv:'',
    encryptedData:'',
    islogin: false,
    lai:'lai',
    remind: '加载中',
    bank_name:''
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
      bgcolor: app.d.bgcolor
    });
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
            money: user.money,
            min_amount: user.min_amount,
            max_amount: user.max_amount,
            multiple: user.multiple,
            unit: user.unit,
            Bank_name: user.Bank_name,
            Cardholder: user.Cardholder,
            Bank_card_number: user.Bank_card_number
          });
        } else {
          wx.showToast({
            title: '非法操作！',
            icon: 'none',  
            duration: 2000,
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
  // 获取手机号码
  getPhoneNumber: function (e) {
    var res_d = e;
    var iv = e.detail.iv;
    var encryptedData = e.detail.encryptedData;
    var that = this;
    if (e.detail.errMsg == 'getPhoneNumber:fail user deny') {
      console.log(111)
      
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '未授权',
        success: function (res) { }
      })
    } else {
      console.log(222)
      
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '同意授权',
        success: function (res) {
          wx.request({
            url: app.d.ceshiUrl + '&action=user&m=secret_key',
            method: 'post',
            data: {
              encryptedData: encryptedData, // 加密数据
              iv: iv, // 加密算法
              sessionId: app.globalData.userInfo.session_key
            },
            header: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (res) {
              var status = res.data.status;
              if (status == 1) {
                that.setData({
                  islogin: true,
                  mobile: res.data.info
                })
              } else {
                app.getUserInfo(that, res_d);
              }
            },
            error: function (e) {
              wx.showToast({
                title: '网络异常！',
                duration: 2000
              });
            }
          })
        }
      })
    }
  },  
  // 申请提现
  withdrawals: function (res) {
    if (res.detail.value.amoney.length == 0) {
      wx.showToast({
        title: '金额不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    } else if (res.detail.value.Bank_name.length == 0){
      wx.showToast({
        title: '银行名不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    } else if (res.detail.value.Cardholder.length == 0) {
      wx.showToast({
        title: '持卡人不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    } else if (res.detail.value.Bank_card_number.length == 0) {
      wx.showToast({
        title: '卡号不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    }else{
      var that = this;
      wx.request({
        url: app.d.ceshiUrl + '&action=user&m=withdrawals',
        method: 'post',
        data: {
          money: that.data.money,
          min_amount: that.data.min_amount,
          max_amount: that.data.max_amount,
          amoney: res.detail.value.amoney,
          Bank_name: res.detail.value.Bank_name,
          Cardholder: res.detail.value.Cardholder,
          Bank_card_number: res.detail.value.Bank_card_number,
          openid: app.globalData.userInfo.openid,
          mobile: that.data.mobile
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var status = res.data.status;
          if (status == 1) {
            wx.showToast({
              title: res.data.info,
              icon: 'success',
              duration: 3000
            })
            setTimeout(function () {
              wx.navigateBack({
                delta: 1
              })
            }, 2500);
          } else {
            wx.showToast({
              title: res.data.info,
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
  },
  verify_bank:function (e) {
    console.log(e);
    var that = this;
    var bnak_card_num = e.detail.value;
    console.log(bnak_card_num);
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=verify_bank',
      method: 'post',
      data: {
        Bank_card_number: bnak_card_num,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          var bank_name = res.data.bank_name;
          that.setData({
            Bank_name: bank_name
          });
          console.log(res);
        } else {
          wx.showToast({
            title: res.data.err,
            icon: 'loading',
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
})