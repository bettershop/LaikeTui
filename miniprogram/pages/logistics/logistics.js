// pages/logistics/logistics.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    wuliu: ['已接收', '抵达深圳', '抵达广州'], 
    remind: '加载中'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    var that = this;

    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    });

    var orderId = options.orderId;
    var details = options.details ? options.details:'';
    var type = options.type ? options.type : '';
    wx.request({
      url: app.d.ceshiUrl + '&action=order&m=logistics',
      method: 'post',
      data: {
        id: orderId,
        details: details,
        type: type
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          if (res.data.res_1.message == 'ok'){
            that.setData({
              wuliu: res.data.res_1.data,
              res: res.data
            });
          }else{
            that.setData({
              res: false
            });
          }
          console.log(res.data.res_1.data)

        } else {
          wx.showToast({
            title: res.data.err,
            duration: 2000
          });
        }
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
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
  copyText: function (t) {
    var a = t.currentTarget.dataset.text;
    wx.setClipboardData({
      data: a,
      success: function () {
        wx.showToast({
          title: "已复制"
        })
      }
    })
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


})