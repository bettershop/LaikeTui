// pages/share/index.js
var app = getApp();
Page({
  data: {
    flag: false
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var id = options.id
    var n = options.n
    var that = this;
    console.log(app.globalData.userInfo)
    that.setData({
      n: n,
      id: id,
      headimgurl: app.globalData.userInfo.avatarUrl
    })
    wx.request({
      url: app.d.ceshiUrl + '&action=getcode&m=madeCode',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid
      },
      header: { //请求头
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          that.setData({
            text: res.data.text
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
  open: function (e) {
    var that = this;
    var id = that.data.id
    var n = that.data.n
    wx.navigateTo({
      url: '../share/share?id=' + id + '&n=' + n,
    });
  }
})