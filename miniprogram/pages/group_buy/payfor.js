var app = getApp();

function initSubMenuDisplay() {
  return ['hidden', 'hidden', 'hidden'];
}
Page({
  data:{
    pays: [],
    subMenuDisplay: initSubMenuDisplay(),
    itemData:{},
    userId:0,
    paytype:'wxPay',//支付方式
    remark:'',
    cartId:'group',
    addrId:0,//收货地址//测试--
    btnDisabled:false,
    productData:[],
    address:{},
    total:0,
    vprice:0,
    vid:0,
    addemt:1,//加入次数
    vou:[],
    checked: false,
    pass_num: 4,
    paytype: 'wxPay',//支付方式
    title:'请输入支付密码',
    remind: '加载中',
    user_money: 0,
    yuan: 0,
    d_yuan: 0,
    coupon_money: 0,
    pagefrom: null,
    
  },
  //获取插件
  get_plug: function (e) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=app&m=get_plug',
      method: 'post',
      data: {
        userid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var plug_ins = res.data;
        that.setData({
          plug_coupon: plug_ins.coupon,
          plug_wallet: plug_ins.wallet,
          plug_integral: plug_ins.integral,
          pays: plug_ins.pays
        });
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
    
  },
  onShow: function () {
    // 页面显示
    var vm = this;
    var windowWidth = wx.getSystemInfoSync().windowWidth;// 屏幕宽度
    vm.onLoad(vm.data.options);
  },
  onLoad:function(options){
    var that = this;
    that.get_plug();
    if (!that.data.options){
      that.setData({
        options: options,
      });
    }

    var uid = app.globalData.userInfo.openid; // 微信id
    var wallet = app.globalData.userInfo.wallet; // 钱包状态
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor //设置页面参数
    })
   
    app.request.wxRequest({
      url: '&action=groupbuy&m=payfor',
      data: { uid: uid, sizeid: options.sizeid, groupid: options.groupid, pagefrom: options.pagefrom, oid: options.oid},
      method: 'post',
      success: function (res) {
        
        that.user_name = res.user_name
      if (res.isself != 0) {
        wx.showModal({
          title: '温馨提示!',
          content: '您已加入了该团',
          showCancel: false,
          success: function (res) {
            wx.navigateBack({
              delta: 1
            })
          },
        })
      }
        if(options.pagefrom == 'kaituan'){
          var coupon_money = that.coupon_money = res.proattr.member_price * options.num;
        } else if (options.pagefrom == 'cantuan'){
          var coupon_money = that.coupon_money = res.proattr.group_price * options.num;
          that.oid = options.oid
        }
         that.setData({
           coupon_money: coupon_money, //支付总额
           buymsg: res.buymsg,
           is_add: res.is_add, //是否有地址
           user_money: res.money, //钱包余额
           proattr: res.proattr, //产品属性
           groupres: res.groupres, //拼团信息        
         })        
    
         that.setData({
           groupid: options.groupid,
           sizeid: options.sizeid, 
           bgcolor: app.d.bgcolor, // 背景颜色
           userId: uid, // 微信id
           pro_num: options.num, //产品数量
           pro_id: options.pro_id, //产品数量
           pagefrom: options.pagefrom, //页面来源
           pro_name: options.pro_name, //产品名称
           wallet: wallet // 钱包状态
         });
  }
    })
  },
  
  //页面加载完成函数
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1500);
  },

  // 选择支付方式
  switchChange: function (e) {
    console.log(e)
    var that = this;
    var check = e.currentTarget.dataset.check;
    var index = e.currentTarget.dataset.index;
    var value = e.detail.value;
    var pays = that.data.pays[index];
    var coupon_money = that.data.coupon_money;
    var user_money = that.data.user_money;
    if (value) {
      pays.checked = true;
    } else {
      pays.checked = false;
    }
    var pay_type = that.data.pays;
    var i = 0;
    var one_pay = '';
    console.log(pay_type)
    for (var j = 0; j < pay_type.length; j++) {
      if (pay_type[j].checked) {
        i += 1;
        one_pay = pay_type[j].value;
      }
    }
    if (i == 0) {
      that.setData({
        paytype: false,
      });
    } else if (i == 1) {
      //单选支付方式时 设置支付方式
      //如果是之前点击了钱包支付 需要还原金额
      var yuan = that.data.yuan;
      if (yuan) {
        that.setData({
          paytype: one_pay,
          coupon_money: yuan,
          d_yuan: false,
        });
      } else {
        that.setData({
          paytype: one_pay,
        });
      }

      if (one_pay == 'wallet_Pay') {
        if (user_money < coupon_money) {
          wx.showToast({
            title: '余额不足，请更换支付方式或选择组合支付！',
            icon: 'none',
            duration: 2000,
          });

          for (var j = 0; j < pay_type.length; j++) {
            if (pay_type[j].value == 'wxPay') {
              pay_type[j].checked = true;
            } else {
              pay_type[j].checked = false;
            }
          }

          that.setData({
            paytype: false,
            pays: pay_type
          });
        }
      }
    } else if (i == 2) {
      console.log('两种支付方式')
      //两种支付方式时当支付金额大于用户余额 则抵扣全部余额 并验证 //否则 直接选择默认钱包支付
      if (Number(user_money) >= Number(coupon_money)) {
        for (var j = 0; j < pay_type.length; j++) {
          if (pay_type[j].value == check) {
            pay_type[j].checked = true;
          } else {
            pay_type[j].checked = false;
          }
        }
        //设置支付方式和点击默认
        that.setData({
          paytype: check,
          pays: pay_type
        });

      } else {
        // 组合支付
        wx.showModal({
          title: '组合支付',
          content: '是否使用余额抵用？',
          success: function (res) {
            if (res.confirm) {
              //组合支付 替换数据
              var price = Number(coupon_money) - Number(user_money); //防止出现小数点后多余2位以上  .toFixed(2)
              that.setData({
                paytype: 'wxPay',
                yuan: coupon_money,
                coupon_money: price.toFixed(2),
                d_yuan: user_money,
              });
            } else if (res.cancel) {
              //设置支付方式和点击默认
              for (var j = 0; j < pay_type.length; j++) {
                if (pay_type[j].value == 'wxPay') {
                  pay_type[j].checked = true;
                } else {
                  pay_type[j].checked = false;
                }
              }

              that.setData({
                paytype: false,
                pays: pay_type
              });
              //安全提示
              wx.showToast({
                title: '已取消',
                icon:'none',
                duration: 2000,
              });
            }
          }
        })
      }
    }
  },

  // 备注
  remarkInput:function(e){
    this.setData({
      remark: e.detail.value,
    })
  },

  // 提交订单支付
  createProductOrderByWX:function(e){
    var that = this;
    
    if (parseInt(that.data.proattr.have) >= parseInt(that.data.groupres.groupnum)){
        wx.showToast({
          title: '抱歉，最多只能同时拼' + that.data.groupres.groupnum +'个团！',
          icon: 'none',
          duration: 2000
        })
    }else{
      that.setData({
        form_id: e.detail.formId
      })
      var paytype = that.data.paytype;
      if (paytype) {
        that.setData({
          paytype: paytype,
        });
      } else {
        wx.showToast({
          title: '已为您选择默认支付方式！',
          icon: 'none',
          duration: 1500,
        });
        //当都没有选中时 循环找到默认的支付方式 在设置支付方式数据
        var pays = that.data.pays, j = 0;
        for (j = 0; j < pays.length; j++) {
          if (pays[j].checked) {
            that.setData({
              pays: pays,
              paytype: pays[j].value,
            });
          }
        }
        
      }

      var address = e.detail.value.address;
      if (address) {
        wx.request({
          url: app.d.ceshiUrl + '&action=groupbuy&m=isgrouppacked',
          method: 'post',
          data: {
            oid: that.oid, // 微信支付  
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            console.log(res.data.hasnum,that.data.groupres.man_num)
            if (Number(res.data.hasnum) >= Number(that.data.groupres.man_num)){
              wx.showModal({
                content: "此团已满，请选择其他团或重新开团!",
                showCancel: false,
                confirmText: "确定",
                success: function () {
                  wx.redirectTo({
                    url: "../group_buy/detail?gid=" + that.data.pro_id + "&sum=1&group_id=" + that.data.groupres.status
                  })
                }
              })         
        }else{
        // 收货地址存在
        if (paytype == 'wallet_Pay') {
          wx.showModal({
            title: '余额支付',
            content: '是否使用余额支付？',
            success: function (res) {
              if (res.confirm) {
                //组合支付 替换数据
                that.wallet_pay()
              }
            }
          })
          } else {
           that.wxpay();
          }
         }
        }
      })
      } else {
        // 没有收货地址
        wx.showToast({
          title: "请添加收货地址!",
          duration: 3000
        });
      }  
    }
  },

  isgroupfull: function(){
    var that = this
    wx.request({
      url: app.d.ceshiUrl + '&action=groupbuy&m=isgrouppacked',
      method: 'post',
      data: {
        oid: that.oid, // 微信支付  
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          hasnum: res.data.hasnum
        })
        
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  // 发起钱包支付
  wallet_pay: function () {
    var that = this;
    var coupon_money = that.data.coupon_money;
      var user_money = that.data.user_money; 
    if (user_money > coupon_money) {
      wx.request({
        url: app.d.ceshiUrl + '&action=product&m=wallet_pay',
        method: 'post',
        data: {
          uid: app.globalData.userInfo.openid, // 微信支付
          total: coupon_money, // 付款金额
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          if (that.data.pagefrom == 'kaituan') {
            that.createGroupOrder(1,'')
          } else if (that.data.pagefrom == 'cantuan') {
            that.canGroupOrder(1,'')
          }
        },
        fail: function () {
          wx.showToast({
            title: '网络异常！',
            duration: 2000
          });
        }
      });
    } else {
      wx.showToast({
        title: '余额不足，请更换支付方式！',
        icon: 'none',
        duration: 2000,
      });
    }
  },
  
  createGroupOrder: function (status,trade_no) {
    var that = this

    app.request.wxRequest({
      url: '&action=groupbuy&m=createGroup',
      data: { 
        uid: app.globalData.userInfo.openid,
        fromid: that.data.form_id, 
        pro_id: that.data.pro_id,
        man_num: that.data.groupres.man_num, //拼团人数
        time_over: that.data.groupres.time_over, //结束时间
        sizeid: that.data.sizeid, //商品规格id
        groupid: that.data.groupres.status,//拼团id
        ptgoods_name: that.data.pro_name,//商品名称
        d_price: that.data.proattr.member_price, //单价
        price: that.coupon_money,//付款金额
        name: that.data.buymsg.name,//收件人姓名
        sheng: that.data.buymsg.sheng,//省
        shi: that.data.buymsg.city,//市
        quyu: that.data.buymsg.quyu,//县
        address: that.data.buymsg.address_xq,//收货人地址
        tel: that.data.buymsg.tel,//收货人电话
        lack: that.data.proattr.num,//是否缺货
        num: that.data.pro_num,//购买数量
        paytype: that.data.paytype, //支付方式
        status: status, //拼团状态
        trade_no: trade_no //微信交易单号
         },
      method: 'post',
      success: function (res) {
        if (res.code == 1 && status == 1){
          wx.showModal({
            content: "成功开团！",
            showCancel: false,
            confirmText: "确定",
            success:function (){
              wx.redirectTo({
                url: '../group_buy/cantuan?id=' + res.gcode + '&groupid=' + that.data.groupres.status + '&pro_id=' + 
                that.data.pro_id + '&man_num=' + that.data.groupres.man_num
              })
            }
          })   
          that.openGroupNotice(res.order, that.user_name, that.data.groupres.time_over, that.data.proattr.member_price, that.coupon_money, app.globalData.userInfo.openid, that.data.form_id, that.data.pro_name, 'pages/order/detail?orderId=' + res.id);
        }else{
          wx.showModal({
            content: "参团失败！支付金额已退还到您钱包账户",
            showCancel: false,
            confirmText: "确定",
          })
        }
      }
    })
  },

  canGroupOrder: function (status, trade_no) {
    var that = this
    app.request.wxRequest({
      url: '&action=groupbuy&m=canOrder',
      data: {
        uid: app.globalData.userInfo.openid,
        fromid: that.data.form_id,
        oid: that.oid, //拼团号
        pro_id: that.data.pro_id,
        man_num: that.data.groupres.man_num, //拼团人数
        sizeid: that.data.sizeid, //商品规格id
        groupid: that.data.groupres.status,//拼团id
        ptgoods_name: that.data.proattr.pro_name,//商品名称
        d_price: that.data.proattr.group_price, //单价
        price: that.coupon_money,//付款金额
        name: that.data.buymsg.name,//收件人姓名
        sheng: that.data.buymsg.sheng,//省
        shi: that.data.buymsg.city,//市
        quyu: that.data.buymsg.quyu,//县
        address: that.data.buymsg.address_xq,//收货人地址
        tel: that.data.buymsg.tel,//收货人电话
        lack: that.data.proattr.num,//是否缺货
        num: that.data.pro_num,//购买数量
        paytype: that.data.paytype, //支付方式
        status: status, //拼团状态
        trade_no: trade_no //微信交易单号
      },
      method: 'post',
      success: function (res) {
        console.log(res)
      if (status == 1){
        if (res.code == 1) {
          wx.showModal({
            content: "成功入团！",
            showCancel: false,
            confirmText: "确定",
            success: function () {
              wx.redirectTo({
                url: '../group_buy/cantuan?id=' + res.gcode + '&groupid=' + that.data.groupres.status + '&pro_id=' +
                that.data.pro_id + '&man_num=' + that.data.groupres.man_num
              })
            }
          })
          var man_num = that.data.groupres.man_num - 1 - res.ptnumber;
          that.canGroupNotice(res.order, res.endtime, that.data.proattr.group_price, that.coupon_money, app.globalData.userInfo.openid, that.data.form_id, man_num, that.data.proattr.pro_name, 'pages/order/detail?orderId=' + res.id)
        } else if (res.code == 2){
          
          wx.showModal({
            content: "恭喜您,拼团成功！",
            showCancel: false,
            confirmText: "确定",
            success: function () {
              wx.redirectTo({
                url: '../group_buy/cantuan?id=' + res.gcode + '&groupid=' + that.data.groupres.status + '&pro_id=' +
                that.data.pro_id + '&man_num=' + that.data.groupres.man_num
              })
            }
          })
        } else if (res.code == 3) {         
          wx.showModal({
            content: "很抱歉,此团已满！支付金额已退还到您钱包账户",
            showCancel: false,
            confirmText: "确定",
          })
        } else if (res.code == 4) {
          wx.showModal({
            content: "参团失败！支付金额已退还到您钱包账户",
            showCancel: false,
            confirmText: "确定",
          })
        }
      }
      }
    })
    
  },
  // 调起微信支付
  wxpay: function(){
    var that = this;
    var cmoney = that.data.coupon_money;
      wx.request({
        url: app.d.ceshiUrl + '&action=pay&m=pay',
        data: {
          cmoney: cmoney, // 付款金额
          openid: app.globalData.userInfo.openid, // 微信id
          type: 'PT',
        },
        method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
        header: {
          'Content-Type':  'application/x-www-form-urlencoded'
        }, // 设置请求的 header
        success: function (res) {
          if (res.data) {
            var dingdanhao = res.data.out_trade_no;
            that.up_out_trade_no(1, dingdanhao)
            wx.requestPayment({
              timeStamp: res.data.timeStamp,
              nonceStr: res.data.nonceStr,
              package: res.data.package,
              signType: 'MD5',
              paySign: res.data.paySign,
              success: function (res) {
                console.log(res)
                that.verification(dingdanhao)
              },
              fail: function (res) {
                // wx.request({
                //   url: 'http://192.168.0.104/589fec3364886bd29ccb4536c3c9290a/LKT/notify_url.php?trade_no=' + dingdanhao,
                //   method: 'post',
                //   data: {
                //     trade_no: dingdanhao //微信交易单号
                //   },
                //   header: {
                //     'Content-Type': 'application/x-www-form-urlencoded'
                //   },
                //   success: function (res) {
                //     console.log(res)
                //     that.verification(dingdanhao)
                //   }
                // })
                wx.showModal({
                  content: "取消支付！",
                  showCancel: false,
                  confirmText: "确定",
                  success: function (res) {
                    
                  }
                })
              }
            })
          }
        },
        fail: function() {
          // 失败后
          wx.showToast({
            title: '网络异常！err:wxpay',
            duration: 2000
          });
        }
      })
  },
  
  //发送数据到客户微信上
  openGroupNotice: function (order_sn, member, endtime, price, sum, user_id, form_id, f_pname,path) {
    
    app.request.wxRequest({
      url: '&action=groupbuy&m=Send_open',
      method: 'post',
      data: {
        page: path,
        order_sn: order_sn,
        price: price,
        member: member,
        endtime: endtime,
        sum: sum,
        user_id: user_id,
        form_id: form_id,
        f_pname: f_pname
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log('信息发送成功')
      }
    })
  },

  canGroupNotice: function (order_sn, endtime, price, sum, user_id, form_id, man_num, f_pname,path) {

    app.request.wxRequest({
      url: '&action=groupbuy&m=Send_can',
      method: 'post',
      data: {
        page: path,
        order_sn: order_sn,
        price: price,
        man_num: man_num,
        endtime: endtime,
        sum: sum,
        user_id: user_id,
        form_id: form_id,
        f_pname: f_pname
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log('信息发送成功')
      }
    })
  },
  up_out_trade_no: function (status,trade_no) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=groupbuy&m=up_out_trade_no',
      method: 'post',
      data: {
        uid: app.globalData.userInfo.openid,
        fromid: that.data.form_id,
        oid: that.oid, //拼团号
        pro_id: that.data.pro_id,
        time_over: that.data.groupres.time_over, //结束时间
        man_num: that.data.groupres.man_num, //拼团人数
        sizeid: that.data.sizeid, //商品规格id
        groupid: that.data.groupres.status,//拼团id
        ptgoods_name: that.data.proattr.pro_name,//商品名称
        d_price: that.data.proattr.group_price, //单价
        price: that.coupon_money,//付款金额
        name: that.data.buymsg.name,//收件人姓名
        sheng: that.data.buymsg.sheng,//省
        shi: that.data.buymsg.city,//市
        quyu: that.data.buymsg.quyu,//县
        address: that.data.buymsg.address_xq,//收货人地址
        tel: that.data.buymsg.tel,//收货人电话
        lack: that.data.proattr.num,//是否缺货
        num: that.data.pro_num,//购买数量
        paytype: that.data.paytype, //支付方式
        status: status, //拼团状态
        trade_no: trade_no, //微信交易单号
        pagefrom:that.data.pagefrom
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log(res)
      }
    })
  },
  verification: function (trade_no) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=groupbuy&m=verification',
      method: 'post',
      data: {
        trade_no: trade_no //微信交易单号
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log(res)
        if (res.data.status) {
          wx.showModal({
            content: "恭喜您,拼团成功！",
            showCancel: false,
            confirmText: "确定",
            success: function () {
              wx.redirectTo({
                url: '../group_buy/cantuan?id=' + res.data.data.ptcode + '&groupid=' + that.data.groupres.status + '&pro_id=' +
                  that.data.pro_id + '&man_num=' + that.data.groupres.man_num
              })
            }
          })
        } else {
          wx.showModal({
            content: "参团失败！支付金额已退还到您钱包账户",
            showCancel: false,
            confirmText: "确定",
          })
        }
      }
    })
  }

})