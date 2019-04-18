// pages/set/set.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
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

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },
  agreeGetUser: function (e) {
    let that = this;
    wx.getSetting({
      success: function (res) {
        if (res.authSetting['scope.userInfo']) {
          wx.getUserInfo({
            success: function (res) {
              console.log(res.userInfo)
              console.log('用户已经授权过');
            }
          })
        }else{
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
      //获取成功设置状态
      //设置用户信息本地存储
      console.log(e.detail.userInfo)
      wx.setStorageSync('userInfo', e.detail.userInfo);
      var userInfo = e.detail.userInfo;
      var nickName = userInfo.nickName;
      var avatarUrl = userInfo.avatarUrl;
      var gender = userInfo.gender; //性别 0：未知、1：男、2：女
      wx.request({
        url: app.d.ceshiUrl + '&action=user&m=material',
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
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 清理缓存
   */
  clearStorage: function () {
    wx.clearStorageSync();
    wx.clearStorage();
    wx.showToast({
      title: '清理成功！',
      duration: 2000
    });
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击更新资料
   */
  update: function () {
    wx.getUserInfo({
      success: function (res) {
        console.log(res)
        var userInfo = res.userInfo;
        var nickName = userInfo.nickName;
        var avatarUrl = userInfo.avatarUrl;
        var gender = userInfo.gender; //性别 0：未知、1：男、2：女
        wx.request({
          url: app.d.ceshiUrl + '&action=user&m=material',
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