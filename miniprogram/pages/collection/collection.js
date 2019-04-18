var app = getApp();
Page({
  data: {
    remind: '加载中',
  },
  onLoad: function (options) {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })

  },
  //下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    this.collection();
  },
  //页面加载完成函数
  onReady: function () {
    var that = this;
    // setTimeout(function () {
    //   that.setData({
        
    //   });
    // }, 1000);
  },
  onShow: function () {
    // 页面显示
    this.collection();
  },
  alldel: function () {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定要清除全部商品吗？',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: app.d.ceshiUrl + '&action=addFavorites&m=alldel',
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
                  list: []
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
  collection: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=addFavorites&m=collection',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status
        if (status == 1){
          that.setData({
            list: res.data.list,
            bgcolor: app.d.bf_color,
            remind: ''
          });
        }else{
          wx.showToast({
            title: '暂时还没有收藏!',
            duration: 2000,
            remind: ''
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
  // 取消收藏
  removeFavorites: function (e) {
    console.log(e)
    var that = this;
    var id = e.currentTarget.dataset.favId;
    wx.showModal({
      title: '提示',
      content: '你确认移除吗',
      success: function (res) {
        res.confirm && wx.request({
          url: app.d.ceshiUrl + '&action=addFavorites&m=removeFavorites',
          method: 'post',
          data: {
            id: id,
            openid: app.globalData.userInfo.openid,
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            var status = res.data.status;
            if (status == 1) {
              wx.showToast({
                title: res.data.succ,
                duration: 2000
              });
              that.collection();
            }else{
              wx.showToast({
                title: res.data.err,
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
    });
  }
});