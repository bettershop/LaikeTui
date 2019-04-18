// pages/user/recharge.js
var app = getApp()
Page({
  data: {
    fmoney: 0,
    remind: '加载中',
  },
  //页面加载完成函数 remind: '加载中',
  onReady: function () {
    // var that = this;
    // setTimeout(function () {
    //   that.setData({
    //     remind: ''
    //   });
    // }, 1000);
  },
  bindblur: function (e) {
    var money = e.detail.value;
    if (money < 0 || isNaN(money)){
      wx.showToast({
        title: '输入金额非法！',
        duration: 2000
      });
    }else{
      this.setData({
        fmoney: money
      });
    }
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
    this.setData({
      bgcolor: app.d.bgcolor
    });
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=recharge&m=index',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        //最小充值金额
        that.setData({
          min_cz: res.data.min_cz
        });
        //--init data        
        var status = res.data.status;
        if (status == 1) {
          var user = res.data.user;
          that.setData({
            money: user.money
          });
          setTimeout(function () {
            that.setData({
              remind: ''
            });
          }, 1000);
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
  recharge: function(e) {
    var money = e.detail.value.money;
    var min_cz = e.detail.value.min_cz;
    var re = money - min_cz;
    console.log(money, min_cz, re)
    if (Number(money) < Number(min_cz) || Number(re) < 0 || isNaN(money)) {
      wx.showToast({
        title: '请填正确金额!',
        icon: 'loading',
        duration: 1000
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    }else{
      var that = this;
      wx.request({
        url: app.d.ceshiUrl + '&action=recharge&m=recharge',
        method: 'post',
        data: {
          openid: app.globalData.userInfo.openid,
          cmoney: e.detail.value.money
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {  
          if (res.data.state){
            var dingdanhao = res.data.out_trade_no;
            wx.requestPayment({
              timeStamp: res.data.timeStamp,
              nonceStr: res.data.nonceStr,
              package: res.data.package,
              signType: 'MD5',
              paySign: res.data.paySign,
              success: function(res){
                wx.request({
                  url: app.d.ceshiUrl + '&action=recharge&m=cz',
                  method: 'post',
                  data: {
                    openid: app.globalData.userInfo.openid,
                    cmoney: e.detail.value.money
                  },
                  header: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                  },
                  success: function (res) {
                    wx.showModal({
                      content: "充值成功！",
                      showCancel: false,
                      confirmText: "确定",
                      success: function (res) {
                        wx.navigateBack({
                          delta: 2
                        })
                      }
                    })
                  }
                });
              },
              fail: function(res){
                wx.showModal({
                  content: "取消充值！",
                  showCancel: false,
                  confirmText: "确定"
                })
              }
            })
          }else{
            wx.showModal({
              content: res.data.text,
              showCancel: false,
              confirmText: "确定"
            })
          }
        },
        fail: function(){
          wx.showModal({
            content: "充值失败！",
            showCancel: false,
            confirmText: "确定"
          })
        }
      })
    }
  }
})