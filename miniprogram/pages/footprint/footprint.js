var app = getApp()
Page({
  data: {
    remind: '加载中',
  },
  
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() 
    this.footprint();
    wx.hideNavigationBarLoading() 
    wx.stopPullDownRefresh() 
  },

  onLoad: function (options) {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, 
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
    that.setData({
      remind: ''
    });
  },
  alldel: function () {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定要清除全部商品吗？',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: app.d.laikeUrl + '&action=footprint&m=alldel',
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
          
        }
      }
    })
  },
  // 查看足迹
  footprint: function(){
    var that = this;
    wx.request({
      url: app.d.laikeUrl + '&action=footprint&m=index',
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