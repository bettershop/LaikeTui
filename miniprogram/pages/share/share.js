// pages/share/share.js
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    user: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      wx_name: app.globalData.userInfo.nickName,
      headimgurl: app.globalData.userInfo.avatarUrl
    })
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=share',
      method: 'post',
      data: {
        n: that.options.n,
        id: that.options.id,
        openid: app.globalData.userInfo.openid
      },
      header: { //请求头
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status
        if(status == 1){
          that.setData({
            text: res.data.text,
            wishing: res.data.wishing
          })
        }else{
          that.setData({
            text: res.data.text,
            wishing: res.data.wishing
          })
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000,
        });
      },
    });
  },
  // 点击关闭
  close: function(){
    wx.navigateBack({
      delta: 2
    })
  }
})