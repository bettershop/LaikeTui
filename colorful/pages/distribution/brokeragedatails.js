// pages/distribution/brokeragedatails.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    datails:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 设置标题
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bf_color, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })

    this.getDatailsData(options.id)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

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
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

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
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  getDatailsData:function(id){
    var vm = this
    wx.request({
      url: app.d.ceshiUrl + '&action=distribution&m=show',
      method: 'post',
      data: {
        id:id
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var data = res.data[0]
        
        vm.setData({
          datails: data
        })
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