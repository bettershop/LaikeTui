// pages/set/set.js
var app = getApp()
Page({
  data: {
  },
  onLoad: function (options) {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    });
  },

  onReady: function () {
  },

  agreeGetUser: function (e) {
    let that = this;
    wx.getSetting({
      success: function (res) {
        if (res.authSetting['scope.userInfo']) {
          wx.getUserInfo({
            success: function (res) {
              
            }
          })
        } else {
          wx.openSetting({
            success: (res) => {
              wx.authorize({
                scope: 'scope.record',
                success() {
                }
              })
            }
          })
        }
      }
    })
    if (e.detail.errMsg == 'getUserInfo:ok') {
      wx.setStorageSync('userInfo', e.detail.userInfo);
      var userInfo = e.detail.userInfo;
      var nickName = userInfo.nickName;
      var avatarUrl = userInfo.avatarUrl;
      var gender = userInfo.gender; 
      wx.request({
        url: app.d.laikeUrl + '&action=user&m=material',
        method: 'post',
        data: {
          openid: app.globalData.userInfo.openid,
          nickName: nickName,
          avatarUrl: avatarUrl,
          gender: gender
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          wx.showToast({
            title: res.data.info,
            success: 2000
          });
        }
      })
    }
  },

  onShow: function () {
  },

  onHide: function () {
  },

  clearStorage: function () {
    wx.clearStorageSync();
    wx.clearStorage();
    wx.showToast({
      title: '清理成功！',
      duration: 2000
    });
  },
  onPullDownRefresh: function () {
  },
  onReachBottom: function () {
  },
  update: function () {
    wx.getUserInfo({
      success: function (res) {
        console.log(res)
        var userInfo = res.userInfo;
        var nickName = userInfo.nickName;
        var avatarUrl = userInfo.avatarUrl;
        var gender = userInfo.gender; //性别 0：未知、1：男、2：女
        wx.request({
          url: app.d.laikeUrl + '&action=user&m=material',
          method: 'post',
          data: {
            openid: app.globalData.userInfo.openid,
            nickName: nickName,
            avatarUrl: avatarUrl,
            gender: gender
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            console.log(nickName)
            wx.showToast({
              title: res.data.info,
              success: 2000
            });
          }
        })
      }
    })
  }
})