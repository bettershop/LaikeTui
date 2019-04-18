// pages/return_goods/index.js
var app = getApp();
function initSubMenuDisplay() {
  return ['hidden', 'hidden', 'hidden'];
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isStatus: 'whole',
    currentTab: 0,  
    orderList0: [],
    orderList1: [],
    remind: '加载中',
    showModalStatus: false,
    address: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    this.setData({
      currentTab: parseInt(options.currentTab),
      bgcolor: app.d.bgcolor
    });
    this.loadList();
  },
  //页面加载完成函数 remind: '加载中',
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
  },
  //拒绝提示
  jujue: function (e) {
    var that = this;
    console.log(e);
    var id = e.currentTarget.dataset.index;
    var orderList0 = that.data.orderList0;
    var r_content = orderList0[id].r_content;
    
    wx.showToast({
      title: '拒绝理由:'+r_content,
      icon:'none',
      duration: 4000
    });
  },
  go_kd: function (e) {
    var that = this;
    console.log(e);
    var id = e.currentTarget.dataset.index;
    var orderList0 = that.data.orderList0;
    var r_content = orderList0[id].r_content;
    wx.showToast({
      title: r_content,
      icon: 'none',
      duration: 4000
    });

  },
  kdtj: function (e) {
    var that = this;
    var kdcode = e.detail.value.kdcode,
      kdname = e.detail.value.kdname,
      lxdh = e.detail.value.lxdh,
      lxr = e.detail.value.lxr,
      formId = e.detail.formId;
    if (kdcode.length > 8 && kdname.length > 2 && lxdh.length == 11 && lxr.length > 1 ){
        console.log('OK');
        wx.request({
          url: app.d.ceshiUrl + '&action=order&m=back_send',
          method: 'post',
          data: {
            kdcode: kdcode,
            kdname : kdname,
            lxdh : lxdh,
            lxr : lxr,
            uid: app.globalData.userInfo.openid,
            oid:that.data.id,
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            console.log(res)
            var status = res.data.status;
            if (status){
              that.setData({
                showModalStatus: false
              });
              wx.showToast({
                title: '提交成功！',
                duration: 2000
              });
              that.loadList();
            }else{
              wx.showToast({
                title: '提交失败！',
                icon:'none',
                duration: 2000
              });
              that.setData({
                showModalStatus: false
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
    }else{
      wx.showToast({
        title: '输入信息有误！',
        icon: 'none',
        duration: 2000
      });
    }

  },
  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  loadList: function(){
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=order&m=ReturnDataList',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
        order_type: that.data.isStatus,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        var list = res.data.list;
        // var h = res.data.h;
        // that.setData({
        //   h: h,
        // });
        switch (that.data.currentTab) {
          case 0:
            that.setData({
              orderList0: list,
            });
            break;
          case 1:
            that.setData({
              orderList1: list,
            });
            break;
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
  bindChange: function (e) {
    var that = this;
    that.setData({ currentTab: e.detail.current });
  },
  swichNav: function (e) {
    var that = this;
    if (that.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      var current = e.target.dataset.current;
      that.setData({
        currentTab: parseInt(current),
        isStatus: e.target.dataset.otype,
      });
      that.loadList();
    };
  },
  // 弹窗
  setModalStatus: function (e) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=order&m=see_send',
      method: 'post',
      data: {},
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status){
          var express = res.data.express;
          var exp = [];
          var j = 0;
          for (j = 0; j < express.length; j++) {
            exp[j] = express[j].kuaidi_name;
          }
          //返回退回信息
          that.setData({
            address: res.data.address,
            name: res.data.name,
            phone: res.data.phone,
            express: express,
            exp: exp,
          });

        }else{
          wx.showToast({
            title: '获取错误！',
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

    var id = e.currentTarget.dataset.id;
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    //定义点击的类型
    this.animation = animation
    animation.translateY(300).step();
    this.setData({
      animationData: animation.export(),
      id: id
    })
    if (e.currentTarget.dataset.status == 1) {
      this.setData({
        showModalStatus: true
      });
    }
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation
      })
      if (e.currentTarget.dataset.status == 0) {
        this.setData({
          showModalStatus: false
        });
      }
    }.bind(this), 200);
  },
})