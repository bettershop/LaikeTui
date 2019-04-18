var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    remind: '加载中',
  },
  //下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    this.footprint();
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
    }),
      this.setData({
        bgcolor: app.d.bf_color
      });
    this.footprint();
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
  alldel: function () {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定要清除全部商品吗？',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: app.d.ceshiUrl + '&action=footprint&m=alldel',
            method: 'post',
            data: {
              openid: app.globalData.userInfo.openid,
            },
            header: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (res) {
              var status = res.data.status
              if (status == 1) {
                wx.showToast({
                  title: '清理成功！',
                  duration: 2000
                });
                that.setData({
                  arr: []
                });
              } else {
                wx.showToast({
                  title: '清理失败!',
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
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  // 查看足迹
  footprint: function(){
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=footprint&m=index',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status
        if (status == 1) {
          that.setData({
            arr: res.data.arr
          });
        } else {
          wx.showToast({
            title: '暂时还没有记录!',
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
  }
})