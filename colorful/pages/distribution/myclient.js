// pages/distribution/myclient.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    total: 1,
    r01: [],
    isopen: true,
    load: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    // 设置标题
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor, //
      backgroundColor: app.d.bf_color, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    this.getMyClientData()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    this.setData({
      num: 0,
      r01: [],
      total: 1,
      isopen: true
    })
    this.getMyClientData()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {
    this.getMyClientData()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  /**
   * get my client
   */
  getMyClientData: function() {
    var that = this;
    if (!that.data.isopen) {
      return
    }
    if (that.data.load) {
      that.data.load = false
      wx.request({
        url: app.d.ceshiUrl + '&action=distribution&m=membership',
        method: 'post',
        data: {
          openid: app.globalData.userInfo.openid,
          page: that.data.total
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function(res) {
          var data = res.data
          if (data.status) {
            that.setData({
              num: 0,
              r01: 1
            });
          } else {
            var total = data.total

            var r01 = that.data.r01
            r01 = r01.concat(data.r01)
            that.setData({
              num: data.num,
              r01: r01
            });

            that.data.load = true

            if (that.data.total === total) {
              that.data.isopen = false
            } else {
              that.data.total++
            }
          }
        },
        error: function(e) {
          wx.showToast({
            title: '网络异常！',
            duration: 2000
          });
        }
      })
    }
  },
  setNav: function(event) {
    var openid = event.target.dataset.openid
    var title = event.target.dataset.title
    var num = event.target.dataset.num
    if (num) {
      wx.navigateTo({
        url: './myclientdef?title=' + title + '&openid=' + openid,
      })
    }

  }
})