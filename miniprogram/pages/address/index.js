var app = getApp()
Page({
  data: {
    address: [],
    radioindex: '',
    pro_id: 0,
    num: 0,
    flag:false,
    cartId: 0,
    numbers:""
  },
  // 下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      // complete
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    this.DataonLoad();
  },
  onShow: function () {
    this.DataonLoad();
  },

  //清空地址
  updelall: function (carts) {
    var that = this;
    console.log(carts)
    wx.showModal({
      title: '提示',
      content: '你确认移除吗?',
      success: function (res) {
        if (res.confirm) {
          console.log(11)
          wx.request({
            url: app.d.ceshiUrl + '&action=address&m=del_select',
            data: {
              openid: app.globalData.userInfo.openid,
              id_arr: carts
            },
            method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
            header: {// 设置请求的 header
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (res) {
              // success
              var status = res.data.status;
              if (status == 1) {
                wx.showToast({
                  title: res.data.succ,
                  duration: 2000
                });
                that.setData({
                  flag: false,
                });
                that.DataonLoad();
               
              } else {
                wx.showToast({
                  title: res.data.err,
                  duration: 2000
                });
              }
            },
          });
        }else{
          that.DataonLoad();
          that.setData({
            flag: false,
          })
        }
      },
      fail: function () {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
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
    this.setData({
      bgcolor: app.d.bgcolor
    });
    var that = this;
    // 页面初始化 options为页面跳转所带来的参数
    if (options.cartId){
      var cartId = options.cartId;
    }else{
      var cartId = 0;
    }
    
    wx.request({
      url: app.d.ceshiUrl + '&action=address&m=index',
      data: {
        openid: app.globalData.userInfo.openid
      },
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {// 设置请求的 header
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        // success
        var address = res.data.adds;
        if (address == '') {
          var address = []
        }
        that.setData({
          address: address,
          cartId: cartId,
        })
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },

  bindCheckbox: function (e) {
    var index = parseInt(e.currentTarget.dataset.index);
    console.log(index)
    var selected = this.data.address[index].selected;
    console.log(selected)
    var address = this.data.address;
    console.log(address)
    address[index].selected = !selected;
    console.log(address[index].selected)
    this.setData({
      address: address
    });
    this.sum();
  },

  bindSelectAll: function () {
    // 环境中目前已选状态
    var selectedAllStatus = this.data.selectedAllStatus;
    // 取反操作
    selectedAllStatus = !selectedAllStatus;
    // 购物车数据，关键是处理selected值
    var address = this.data.address;
    // 遍历
    for (var i = 0; i < address.length; i++) {
      address[i].selected = selectedAllStatus;
    }
    this.setData({
      selectedAllStatus: selectedAllStatus,
      address: address
    });
    this.sum()
  },

  // 地址管理
  delarr: function () {
    var that = this;
    // 初始化toastStr字符串
    var toastStr = '';
    // 遍历取出已勾选的cid
    for (var i = 0; i < this.data.address.length; i++) {
      if (this.data.address[i].selected) {
        toastStr += this.data.address[i].id;
        toastStr += ',';
      }
    }
    if (toastStr == '') {
      wx.showToast({
        title: '请选择要删除的商品！',
        duration: 2000
      });
      return false;
    }
    that.updelall(toastStr);
  },

  sum: function () {
    var that = this;
    var address = that.data.address;

    // 计算总金额
    var selected = 0;
    for (var i = 0; i < address.length; i++) {
      if (address[i].selected) {
        selected = ++selected;
      }
    }
    //判断全选
    if (address.length == selected && selected != 0) {
      that.setData({
        selectedAllStatus: true,
      });
    } else {
      that.setData({
        selectedAllStatus: false,
      });
    }
    // 写回经点击修改后的数组  .toFixed(2)取小数点2位
    this.setData({
      address: address
    });
  },

  // 切换
  upflag: function () {
    var that = this;
    that.setData({
      flag:true
    });
  },

  uptrue:function(){
    var that = this;
    that.setData({
      flag: false
    });
    that.DataonLoad()
  },

  // 设置默认
  setDefault: function (e) {
    var that = this;
    var addrId = e.currentTarget.dataset.id;
    console.log(that.data.flag)

      wx.showModal({
        content: "确认修改地址",
        success: function (t_res) {
          console.log(t_res.confirm)
          if (t_res.confirm) {
            wx.request({
              url: app.d.ceshiUrl + '&action=address&m=set_default',
              data: {
                openid: app.globalData.userInfo.openid,
                addr_id: addrId
              },
              method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
              header: {// 设置请求的 header
                'Content-Type': 'application/x-www-form-urlencoded'
              },
              success: function (res) {
                var status = res.data.status;
                var cartId = that.data.cartId;
                if (status == 1) {
                  if (cartId != '0' || cartId == 'group') {
                    wx.navigateBack({
                      delta: 1
                    })
                    return false;
                  }
                  wx.showToast({
                    title: res.data.err,
                    duration: 2000
                  });
                  that.DataonLoad();
                } else {
                  wx.showToast({
                    title: res.data.err,
                    duration: 2000
                  });
                }
              },
              fail: function () {
                // fail
                wx.showToast({
                  title: '网络异常！',
                  duration: 2000
                });
              }
            });
          } else {
            that.DataonLoad();
          }
        },
      });
  },
  // 删除地址
  delAddress: function (e) {
    var that = this;
    var addrId = e.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '你确认移除吗',
      success: function (res) {
        res.confirm && wx.request({
          url: app.d.ceshiUrl + '&action=address&m=del_adds',
          data: {
            openid: app.globalData.userInfo.openid,
            id_arr: addrId
          },
          method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
          header: {// 设置请求的 header
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            // success
            var status = res.data.status;
            if (status == 1) {
              that.DataonLoad();
            } else {
              wx.showToast({
                title: res.data.err,
                duration: 2000
              });
            }
          },
          fail: function () {
            // fail
            wx.showToast({
              title: '网络异常！',
              duration: 2000
            });
          }
        });
      }
    });

  },
  // 数据加载
  DataonLoad: function () {
    var that = this;
    // 页面初始化 options为页面跳转所带来的参数
    wx.request({
      url: app.d.ceshiUrl + '&action=address&m=index',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      header: {// 设置请求的 header
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var address = res.data.adds;
        that.numbers=res.data.adds;
        if (address == '') {
          var address = []
        }
        that.setData({
          address: address,
          selectedAllStatus: false
        })
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },
  //修改跳转
  upaddress: function (e) {
    var addrId = e.currentTarget.dataset.id
    wx.navigateTo({
      url: '../address/upaddress/upaddress?addr_id='+addrId,
    })
  },
})