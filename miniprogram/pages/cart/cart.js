var app = getApp();
// pages/cart/cart.js
Page({
  data:{
    page:1,
    minusStatuses: ['disabled', 'disabled', 'normal', 'normal', 'disabled'],
    total: 0.0,
    carts: [],
    cont: 1,
    upstatus:false,
    remind: '加载中',
  },
  //页面加载完成函数 remind: '加载中',
  onReady: function () {
    var that = this;
    // setTimeout(function () {
    //   that.setData({
        
    //   });
    // }, 1000);
  },
  //下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      // complete
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    this.loadProductData();
    this.sum();
  },
bindMinus: function(e) {
    var that = this;
    var index = parseInt(e.currentTarget.dataset.index);
    var num = that.data.carts[index].num;
    
    // 如果只有1件了，就不允许再减了
    if (num > 0) {
      num --;
    }
    var cart_id = e.currentTarget.dataset.cartid;
    if(num < 1){
      wx.showToast({
        title: '数量不能小于1!',
        duration: 2000
      });
    }else{
      wx.request({
        url: app.d.ceshiUrl + '&action=product&m=up_cart',
        method:'post',
        data: {
          user_id: that.data.user_id,
          num:num,
          cart_id:cart_id
        },
        header: {
          'Content-Type':  'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var status = res.data.status;
          if(status==1){
            // 只有大于一件的时候，才能normal状态，否则disable状态
            var minusStatus = num <= 1 ? 'disabled' : 'normal';
            // 购物车数据
            var carts = that.data.carts;
            carts[index].num = num;
            // 按钮可用状态
            var minusStatuses = that.data.minusStatuses;
            minusStatuses[index] = minusStatus;
            // 将数值与状态写回
            that.setData({
              minusStatuses: minusStatuses
            });
            that.sum();
          }
        },
        fail: function() {
          // fail
          wx.showToast({
            title: '网络异常！',
            duration: 2000
          });
        }
      });
    }
},
//跳转首页
go_index: function () {
  wx.switchTab({
    url: '../index/index'
  })
},
//清空购物车
delall: function () {
  var that = this;
  wx.showModal({
    title: '提示',
    content: '你确认清空全部吗?',
    success: function (res) {
      if (res.confirm){
            wx.request({
              url: app.d.ceshiUrl + '&action=product&m=delAll_cart',
              method: 'post',
              data: {
                user_id: that.data.user_id,
              },
              header: {
                'Content-Type': 'application/x-www-form-urlencoded'
              },
              success: function (res) {
                var data = res.data;
                if (data.status == 1) {
                  wx.showToast({
                    title: '操作成功！',
                    duration: 2500
                  });
                  that.loadProductData();
                } else {
                  wx.showToast({
                    title: '操作失败！',
                    duration: 2000
                  });
                }
              },
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
},
//编辑完成
ok: function () {
  var that = this;
  that.setData({
    upstatus: false
  });
},
//清空购物车
updata: function () {
  var that = this;
  that.setData({
    upstatus:true
  });
},

//删除
delarr: function () {
  var that = this;
  // 初始化toastStr字符串
  var toastStr = '';
  // 遍历取出已勾选的cid
  for (var i = 0; i < this.data.carts.length; i++) {
    if (this.data.carts[i].selected) {
      toastStr += this.data.carts[i].id;
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
  that.removeShopCard(toastStr);
},

//移至收藏
shouc: function () {
  var that = this;
  // 初始化toastStr字符串
  var toastStr = '';
  // 遍历取出已勾选的cid
  for (var i = 0; i < this.data.carts.length; i++) {
    if (this.data.carts[i].selected) {
      toastStr += this.data.carts[i].id;
      toastStr += ',';
    }
  }
  if (toastStr == '') {
    wx.showToast({
      title: '请选择要收藏的商品！',
      duration: 2000
    });
    return false;
  }
  //ajax请求
  wx.request({
    url: app.d.ceshiUrl + '&action=product&m=to_Collection',
    method: 'post',
    data: {
      user_id: that.data.user_id,
      carts: toastStr,
    },
    header: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    success: function (res) {
      var data = res.data;
      if (data.status == 1) {
        wx.showToast({
          title: '操作成功！',
          duration: 2000
        });
        that.loadProductData();
      } else {
        wx.showToast({
          title: '操作失败！',
          duration: 2000
        });
      }
    },
  });
},

bindPlus: function(e) {
    var that = this;
    var index = parseInt(e.currentTarget.dataset.index);
    var num = that.data.carts[index].num;
    // 自增
    num ++;
    var pnum = that.data.carts[index].pnum;
    var cart_id = e.currentTarget.dataset.cartid;
    console.log(pnum)
    if (pnum > num){
      wx.request({
        url: app.d.ceshiUrl + '&action=product&m=up_cart',
        method: 'post',
        data: {
          user_id: that.data.user_id,
          num: num,
          cart_id: cart_id
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var status = res.data.status;
          if (status == 1) {
            // 只有大于一件的时候，才能normal状态，否则disable状态
            var minusStatus = num <= 1 ? 'disabled' : 'normal';
            // 购物车数据
            var carts = that.data.carts;
            carts[index].num = num;
            // 按钮可用状态
            var minusStatuses = that.data.minusStatuses;
            minusStatuses[index] = minusStatus;
            // 将数值与状态写回
            that.setData({
              minusStatuses: minusStatuses
            });
            that.sum();
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
        title: '库存不足！',
        icon:'none',
        duration: 2000
      });
    }

}, 

bindCheckbox: function(e) {
  /*绑定点击事件，将checkbox样式改变为选中与非选中*/
  //拿到下标值，以在carts作遍历指示用
  var index = parseInt(e.currentTarget.dataset.index);
  //原始的icon状态
  var selected = this.data.carts[index].selected;
  var carts = this.data.carts;
  // 对勾选状态取反
  carts[index].selected = !selected;
  // 写回经点击修改后的数组
  this.setData({
    carts: carts
  });
  this.sum();
},

bindSelectAll: function() {
   // 环境中目前已选状态
   var selectedAllStatus = this.data.selectedAllStatus;
   // 取反操作
   selectedAllStatus = !selectedAllStatus;
   // 购物车数据，关键是处理selected值
   var carts = this.data.carts;
   // 遍历
   for (var i = 0; i < carts.length; i++) {
     carts[i].selected = selectedAllStatus;
   }
   this.setData({
     selectedAllStatus: selectedAllStatus,
     carts: carts
   });
   this.sum()
 },

bindCheckout: function() {
   // 初始化toastStr字符串
   var toastStr = '';
   // 遍历取出已勾选的cid
   for (var i = 0; i < this.data.carts.length; i++) {
     if (this.data.carts[i].selected) {
       toastStr += this.data.carts[i].id;
       toastStr += ',';
     }
   }
   if (toastStr==''){
     wx.showToast({
       title: '请选择要结算的商品！',
       duration: 2000
     });
     return false;
   }
   //存回data
   wx.navigateTo({
     url: '../order/pay?cartId=' + toastStr,
   })
 },

 bindToastChange: function() {
   this.setData({
     toastHidden: true
   });
 },

sum: function() {
    var that = this;
    var carts = that.data.carts;

    // 计算总金额
    var total = 0;
    var selected = 0;
    for (var i = 0; i < carts.length; i++) {
      if (carts[i].selected) {
        total += carts[i].num * carts[i].price;
        selected = ++selected;
      }
    }
   //判断全选
    if (carts.length == selected && selected != 0){
      that.setData({
        selectedAllStatus: true,
      });
    }else{
      that.setData({
        selectedAllStatus: false,
      });
    }
    // 写回经点击修改后的数组  .toFixed(2)取小数点2位
    this.setData({
      carts: carts,
      total: '¥ ' + total.toFixed(2)
    });
  },

onLoad:function(options){
    this.setData({
      bgcolor: app.d.bgcolor,
      user_id: app.globalData.userInfo.openid
    });
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor //页面标题为路由参数
    });
    this.loadProductData();
    this.sum();
},
onShow: function () {
  // app.userlogin(true);
  var cont = this.data.cont;
  var carts = this.data.carts;
  var purchase = app.d.purchase;
  var that = this;
  if (cont > 1 && purchase == 1) {
    that.loadProductData();
  } else {
    that.setData({
        carts: carts,
        cont: cont+1
      });
  }
},
  removeShopCard: function (carts){
    var that = this;
    wx.showModal({
      title: '提示',
      content: '你确认移除吗',
      success: function(res) {
        res.confirm && wx.request({
          url: app.d.ceshiUrl + '&action=product&m=delcart',
          method:'post',
          data: {
            carts: carts,
          },
          header: {
            'Content-Type':  'application/x-www-form-urlencoded'
          },
          success: function (res) {
            //--init data
            var data = res.data;
            if(data.status == 1){
              that.loadProductData();
              // that.setData({
              //   upstatus: false
              // });
            }else{
              wx.showToast({
                title: '操作失败！',
                duration: 2000
              });
            }
          },
        });
      },
      fail: function() {
        // fail
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },

  // 数据加载
  loadProductData:function(){
    var that = this;
      wx.request({
        url: app.d.ceshiUrl + '&action=product&m=Shopping',
        method: 'post',
        data: {
          user_id: app.globalData.userInfo.openid
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var cart = res.data.cart;
          that.setData({
            carts: cart,
            selectedAllStatus: false,
            total: '￥0.00',
            remind: ''
          });
        },
      }); 
  },
  bindManual:function (e) {
    var num = e.detail.value;
    // var index = e.target.dataset.index;
    var carid = e.target.dataset.cartid;
    var carts = this.data.carts;
    var that = this;
    var index = parseInt(e.currentTarget.dataset.index);
    var cat_num = that.data.carts[index].num;
    var cart_id = e.currentTarget.dataset.cartid;
    var pnum = that.data.carts[index].pnum;
    console.log(num, cat_num, pnum)
    if (Number(num) > 0) {
      if (Number(num) <= Number(pnum)) {
        wx.request({
          url: app.d.ceshiUrl + '&action=product&m=up_cart',
          method: 'post',
          data: {
            user_id: that.data.user_id,
            num: num,
            cart_id: carid
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            var status = res.data.status;
            if (status == 1) {
              // 只有大于一件的时候，才能normal状态，否则disable状态
              var minusStatus = num <= 1 ? 'disabled' : 'normal';
              // 购物车数据
              var carts = that.data.carts;
              carts[index].num = num;
              // 按钮可用状态
              var minusStatuses = that.data.minusStatuses;
              minusStatuses[index] = minusStatus;
              // 将数值与状态写回
              that.setData({
                minusStatuses: minusStatuses
              });
              that.sum();
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
          title: '库存不足,请重新输入！',
          icon: 'none',
          duration: 2000
        });
        var carts = that.data.carts;
        that.setData({
          carts: carts
        });
        that.sum();
      }

    } else {
          wx.showToast({
            title: '数量不能小于1,请重新输入！',
            icon:'none',
            duration: 2000
          });
          var carts = that.data.carts;
          that.setData({
            carts: carts
          });
          that.sum();
      // wx.request({
      //   url: app.d.ceshiUrl + '&action=product&m=up_cart',
      //   method: 'post',
      //   data: {
      //     user_id: that.data.user_id,
      //     num: num,
      //     cart_id: carid
      //   },
      //   header: {
      //     'Content-Type': 'application/x-www-form-urlencoded'
      //   },
      //   success: function (res) {
      //     var status = res.data.status;
      //     if (status == 1) {
      //       // 只有大于一件的时候，才能normal状态，否则disable状态
      //       var minusStatus = num <= 1 ? 'disabled' : 'normal';
      //       // 购物车数据
      //       var carts = that.data.carts;
      //       carts[index].num = num;
      //       // 按钮可用状态
      //       var minusStatuses = that.data.minusStatuses;
      //       minusStatuses[index] = minusStatus;
      //       // 将数值与状态写回
      //       that.setData({
      //         minusStatuses: minusStatuses
      //       });
      //       that.sum();
      //     }
      //   },
      //   fail: function () {
      //     // fail
      //     wx.showToast({
      //       title: '网络异常！',
      //       duration: 2000
      //     });
      //   }
      // });
    };
  },
})