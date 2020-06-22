//获取应用实例  
var app = getApp();
var common = require("../../utils/common.js");
Page({
  data: {
    winWidth: 0,
    winHeight: 0,
    // tab切换  
    currentTab: 0,
    isStatus: 'payment', //10待付款，20待发货，30待收货 40、50已完成
    page: 0,
    refundpage: 0,
    ordert0: [],
    ordert1: [],
    ordert2: [],
    ordert3: [],
    ordert4: [],
    remind: '加载中',
    groupid: '',
    man_num: '',
    selectPerson: true,
    activeSortingName: "全部订单",
    selectArea: false,
    otype: 'pay',
    pxopen: false,
    districtChioceIcon: "/images/icon-go-black.png",
    sortingChioceIcon: "icon-xiangyou",
    sortingList: [{
        key: 1,
        value: "全部订单",
        otype: 'pay'
      },
      {
        key: 3,
        value: "拼团",
        otype: 'pay6'
      }
    ],
  },
  //提醒发货
  add_fix: function(e) {
    console.log(1)
    wx.showToast({
      title: '已为您通知卖家提醒发货',
      icon: 'none',
      duration: 2000,
    });
  },
  selectSorting: function(e) {
    var index = e.currentTarget.dataset.index;
    var otype = e.currentTarget.dataset.otype;
    this.setData({
      otype: otype,
      sortingChioceIcon: "icon-xiangyou",
      chioceSorting: false,
      activeSortingIndex: index,
      activeSortingName: this.data.sortingList[index].value,
      productList: [],
      pageIndex: 1,
      loadOver: false,
      isLoading: true
    })
    this.loadOrderList();
  },
  //点击选择类型
  clickPerson: function() {
    if (this.data.chioceSorting) {
      this.setData({
        sortingChioceIcon: "icon-xiangyou",
        chioceDistrict: false,
        chioceSorting: false,
        chioceFilter: false,
      });
    } else {
      this.setData({
        sortingChioceIcon: "icon-xiangxia",
        chioceDistrict: false,
        chioceSorting: true,
        chioceFilter: false,
      });
    }

  },
  //下拉刷新
  onPullDownRefresh: function() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.loadOrderList();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  //页面加载完成函数
  onReady: function() {
    var that = this;
    that.setData({
      remind: ''
    });
  },
  onLoad: function(options) {
    if (options.otype) {
      var otype = options.otype;
      this.setData({
        otype: otype,
      });
      var isStatus = options.order_type1 ? options.order_type1 : '';
    } else {
      var otype = this.data.otype;
      var isStatus = '';
    }
    var sortingList = this.data.sortingList;
    for (var j = 0; j < sortingList.length; j++) {
      if (sortingList[j].otype == otype) {
        this.setData({
          activeSortingName: sortingList[j].value,
        });
      }
    }
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    this.initSystemInfo();

    this.setData({
      currentTab: parseInt(options.currentTab),
      isStatus: isStatus,
      bgcolor: '#FF6347'
    });
    this.loadOrderList();
  },
  onShow: function() {
    this.loadOrderList();
  },
  // 获取订单
  loadOrderList: function() {
    var that = this;
    var isStatus1 = that.options.order_type1
    wx.request({
      url: app.d.ceshiUrl + '&action=order&m=index',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
        order_type: that.data.isStatus,
        otype: that.data.otype,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function(res) {
        var status = res.data.status;
        var order = res.data.order;
        switch (that.data.currentTab) {
          case 0:
            that.setData({
              ordert0: order,
            });
            break;
          case 5:
            that.setData({
              ordert0: order,
            });
            break;
          case 6:
            that.setData({
              ordert0: order,
            });
            break;
          case 1:
            that.setData({
              ordert1: order,
            });
            break;
          case 2:
            that.setData({
              ordert2: order,
            });
            break;
          case 3:
            that.setData({
              ordert3: order,
            });
            break;
          case 4:
            that.setData({
              ordert4: order,
            });
            break;
        }
        //console.log(that.data.ordert0)
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
  // 确认收货
  recOrder: function(e) {
    var that = this;
    var sNo = e.currentTarget.dataset.sno;
    wx.showModal({
      title: '提示',
      content: '你确定已收到宝贝吗？',
      success: function(res) {
        res.confirm && wx.request({
          url: app.d.ceshiUrl + '&action=order&m=ok_Order',
          method: 'post',
          data: {
            sNo: sNo,
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function(res) {
            var status = res.data.status;
            if (status == 1) {
              wx.showToast({
                title: '操作成功！',
                duration: 2000
              });
              that.loadOrderList();
            } else {
              wx.showToast({
                title: res.data.err,
                duration: 2000
              });
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
    });
  },
  // 取消订单
  removeOrder: function(e) {
    var that = this;
    var orderId = e.currentTarget.dataset.orderId;
    wx.showModal({
      title: '提示',
      content: '你确定要取消订单吗？',
      success: function(res) {
        res.confirm && wx.request({
          url: app.d.ceshiUrl + '&action=order&m=removeOrder',
          method: 'post',
          data: {
            openid: app.globalData.userInfo.openid,
            id: orderId,
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function(res) {
            var status = res.data.status;
            if (status == 1) {
              wx.showToast({
                title: res.data.err,
                success: 2000
              });
              that.loadOrderList();
            } else {
              wx.showToast({
                title: res.data.err,
                duration: 2000
              });
            }
          },
          fail: function() {
            wx.showToast({
              title: '网络异常！',
              duration: 2000
            });
          }
        });
      }
    });
  },
  initSystemInfo: function() {
    var that = this;
    wx.getSystemInfo({
      success: function(res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        });
      }
    });
  },

  bindChange: function(e) {
    console.log('1111')
    var that = this;
    //设置移动请求  防止空数据出现
    var currentTab = e.detail.current;
    if (currentTab == 0) {
      console.log('全部');
      that.setData({
        currentTab: parseInt(currentTab),
        isStatus: '',
      });
      that.loadOrderList();
    } else if (currentTab == 1) {
      that.setData({
        currentTab: parseInt(currentTab),
        isStatus: 'payment',
      });
      that.loadOrderList();
    } else if (currentTab == 2) {
      that.setData({
        currentTab: parseInt(currentTab),
        isStatus: 'send',
      });
      that.loadOrderList();
    } else if (currentTab == 3) {
      that.setData({
        currentTab: parseInt(currentTab),
        isStatus: 'receipt',
      });
      that.loadOrderList();
    } else if (currentTab == 4) {
      that.setData({
        currentTab: parseInt(currentTab),
        isStatus: 'evaluate',
      });
      that.loadOrderList();
    }
    that.setData({
      currentTab: currentTab
    });
  },
  // 跳转页面
  swichNav: function(e) {
    var that = this;
    if (that.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      var current = e.target.dataset.current;
      that.setData({
        currentTab: parseInt(current),
        isStatus: e.target.dataset.otype,
      });
      that.loadOrderList();
    };
  },
  // 微信支付
  payOrderByWechat: function(e) {
    var that = this;
    var order_id = e.detail.target.dataset.orderId; // 订单id
    var order_sn = e.detail.target.dataset.ordersn; // 订单号
    var price = e.detail.target.dataset.price; // 实付金额
    var user_id = app.globalData.userInfo.openid; // 微信id
    var form_id = e.detail.formId;
    var f_pname = e.detail.target.dataset.pname // 拼接的商品名称
    var coupon_id = e.detail.target.dataset.coupon_id // 优惠券id
    // 调起微信支付
    wx.request({
      url: app.d.ceshiUrl + '&action=pay&m=pay',
      data: {
        cmoney: price, // 实付金额
        openid: user_id, // 微信id
        type: 1,
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function(res) {
        if (app.globalData.userInfo.referee_openid && app.globalData.userInfo.openid && app.globalData.userInfo.referee_openid != 'undefined') {
          // console.log(66411)
          var referee_openid = app.globalData.userInfo.referee_openid;
          var openid = app.globalData.userInfo.openid
          that.refereeopenid(referee_openid, openid); //储存推荐人
        }

        if (res.data) {
          var dingdanhao = res.data.out_trade_no;
          wx.requestPayment({
            timeStamp: res.data.timeStamp,
            nonceStr: res.data.nonceStr,
            package: res.data.package,
            signType: 'MD5',
            paySign: res.data.paySign,
            success: function(res) {
              //成功后修改订单状态
              wx.request({
                url: app.d.ceshiUrl + '&action=product&m=up_order',
                method: 'post',
                data: {
                  coupon_id: coupon_id, // 付款金额
                  coupon_money: price, // 付款金额
                  order_id: order_sn, // 订单号
                  user_id: user_id, // 微信id
                },
                header: {
                  'Content-Type': 'application/x-www-form-urlencoded'
                },
                success: function(res) {
                  var orderId = res.data.sNo; // 订单号
                  var oid = res.data.id; // 订单id
                  //发送信息到客户
                  that.notice(oid, order_sn, price, user_id, form_id, f_pname);
                  wx.showModal({
                    content: "支付成功！",
                    showCancel: false,
                    confirmText: "确定",
                    success: function(res) {
                      wx.redirectTo({
                        url: '../order/detail?orderId=' + oid
                      });
                    }

                  });
                  that.detailed(order.order_sn); //分销
                }
              })
            },
            fail: function(res) {
              wx.showModal({
                content: "取消支付！",
                showCancel: false,
                confirmText: "确定",
              })
            }
          })
        }

      },
      fail: function() {
        wx.showToast({
          title: '网络异常！err:wxpay',
          duration: 2000
        });
      }
    })
  },
  //发送数据到客户微信上
  notice: function(order_id, order_sn, price, user_id, form_id, f_pname) {
    wx.request({
      url: app.d.ceshiUrl + '&action=getcode&m=Send_Prompt',
      method: 'post',
      data: {
        page: 'pages/order/detail?orderId=' + order_id,
        order_sn: order_sn,
        price: price,
        user_id: user_id,
        form_id: form_id,
        f_pname: f_pname
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function(res) {
        console.log(res)
      }
    })
  },
  escOrder: function(e) {
    var that = this,
      id = e.target.dataset.orderId;
    var sNo = e.target.dataset.sno;
    wx.showModal({
      title: '提示',
      content: '小二正在努力为您发货,确定要取消吗？',
      success: function(res) {
        if (res.confirm) {
          console.log('用户点击确定' + "../return_goods/return_goods?id=" + id + "&type=1&oid=" + sNo);
          wx.redirectTo({
            url: "../return_goods/return_goods?id=" + id + "&type=1&oid=" + sNo
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }

      }
    });
  },
  //储存推荐人
  refereeopenid: function(referee_openid, openid) {
    wx.request({
      url: app.d.ceshiUrl + '&action=app&m=referee_openid',
      method: 'post',
      data: {
        openid: openid,
        referee_openid: referee_openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function(res) {},
      error: function(e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000,
        });
      },
    });
  },
  detailed: function(sNo, openid) { //分销
    wx.request({
      url: app.d.ceshiUrl + '&action=distribution&m=detailed_commission',
      method: 'post',
      data: {
        userid: app.globalData.userInfo.openid,
        order_id: sNo,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
  },
  commission: function(sNo) { //分销返佣
    wx.request({
      url: app.d.ceshiUrl + '&action=distribution&m=commission',
      method: 'post',
      data: {
        order_id: sNo,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
    })
  },

})