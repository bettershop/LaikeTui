var app = getApp();
//获取应用实例  
var common = require("../../utils/common.js");
// 定义一个总毫秒数，以一天为例  
var total_micro_second = 7200 * 1000 * 24; //这是两天倒计时  
function countdown(that) {
	// 渲染倒计时时钟  
	that.setData({
		clock: dateformat(total_micro_second) //格式化时间  
	});
	if(total_micro_second <= 0) {
		that.setData({
			clock: "已取消"
		});
		// timeout则跳出递归  
		return;
	}
	//settimeout实现倒计时效果  
	setTimeout(function() {
		// 放在最后--  
		total_micro_second -= 10;
		countdown(that);
	}, 10) //注意毫秒的步长受限于系统的时间频率，于是我们精确到0.01s即10ms  
}
// 时间格式化输出，如1天天23时时12分分12秒秒12 。每10ms都会调用一次  
function dateformat(micro_second) {
	// 总秒数  
	var second = Math.floor(micro_second / 1000);
	// 天数  
	var day = Math.floor(second / 3600 / 24);
	// 总小时  
	var hr = Math.floor(second / 3600);
	// 小时位  
	var hr2 = hr % 24;
	// 分钟位  
	var min = Math.floor((second - hr * 3600) / 60);
	// 秒位  
	var sec = (second - hr * 3600 - min * 60); // equal to => var sec = second % 60;  
	// 毫秒位，保留2位  
	var micro_sec = Math.floor((micro_second % 1000) / 10);
	return day + "天" + hr2 + "时" + min + "分" + sec + "秒";
}
Page({
	data: {
		clock: '',
		orderId: 0,
		orderData: {},
		proData: [],
		pass_num: 4,
		remind: '加载中',
		paytype: 'wxPay',
		showModalStatus: false,
		pays: [],
		yuan: 0, //临时储存先前的总价格
		d_yuan: 0, //抵扣余额显示
	},
  return_goods: function (e) {
    var that = this, id = e.target.dataset.orderId;
    var sNo = that.data.sNo;
    wx.redirectTo({
      url: "../return_goods/return_goods?id=" + id + "&type=1&oid=" + sNo
    })
  },
  // 选择支付方式
  switchChange: function (e) {
    var that = this;
    var check = e.currentTarget.dataset.check;
    var index = e.currentTarget.dataset.index;
    var value = e.detail.value;
    var checked_one = '';
    var pays = that.data.pays[index];
    var payment = that.data.z_price;
    var user_money = that.data.user_money;
    if (Number(payment) >= 0) {

      if (value) {
        pays.checked = true;
      } else {
        pays.checked = false;
      }
      var pay_type = that.data.pays;
      var i = 0
      for (var j = 0; j < pay_type.length; j++) {
        if (pay_type[j].checked) {
          i += 1;
          checked_one = pay_type[j].value;
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
          if (that.data.consumer_money) {
            yuan = Number(yuan) - Number(that.data.consumer_money);
          }
          that.setData({
            paytype: check,
            payment: yuan,
            d_yuan: false,
          });
        } else {
          that.setData({
            paytype: check,
          });
        }
        // 如果是余额支付
        if (checked_one == 'wallet_Pay') {
          console.log(payment, yuan)
          if (user_money >= payment) {
            // console.log(user_money, payment)
            if (user_money < payment) {
              wx.showToast({
                title: '余额不足，请更换支付方式或选择组合支付2！',
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
              //设置支付方式和点击默认
              that.setData({
                paytype: 'wxPay',
                pays: pay_type
              });
            }
          } else {
            wx.showToast({
              title: '余额不足，请更换支付方式或选择组合支付1！',
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
            //设置支付方式和点击默认
            that.setData({
              paytype: 'wxPay',
              pays: pay_type
            });
          }

        }
      } else if (i == 2) {
        //两种支付方式时当支付金额大于用户余额 则抵扣全部余额 并验证 //否则 直接选择默认钱包支付
        if (Number(user_money) >= Number(payment)) {
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
          if (Number(user_money) <= 0) {
            for (var j = 0; j < pay_type.length; j++) {
              if (pay_type[j].value == 'wxPay') {
                pay_type[j].checked = true;
              } else {
                pay_type[j].checked = false;
              }
            }
            //设置支付方式和点击默认
            that.setData({
              paytype: 'wxPay',
              pays: pay_type
            });
            //安全提示
            wx.showToast({
              title: '余额不足，已默认微信支付！',
              icon: 'none',
              duration: 2000,
            });
          } else {
            wx.showModal({
              title: '组合支付',
              content: '是否使用余额抵用？',
              success: function (res) {
                if (res.confirm) {
                  //组合支付 替换数据
                  var price = Number(payment) - Number(user_money); //防止出现小数点后多余2位以上  .toFixed(2)
                  that.setData({
                    paytype: 'wxPay',
                    yuan: payment,
                    payment: price.toFixed(2),
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
                    icon: 'none',
                    duration: 2000,
                  });
                }
              }
            })
          }

        }
      }

    } else {
      wx.showToast({
        title: '付款金额有误！',
        icon: 'none',
        duration: 2000,
      });
      that.setData({
        paytype: false,
      });
    }
  },

	onLoad: function(options) {
    console.log(options)
    this.get_plug();
		wx.setNavigationBarColor({
			frontColor: app.d.frontColor,
			backgroundColor: app.d.bgcolor, //页面标题为路由参数
			animation: {
				duration: 400,
				timingFunc: 'easeIn'
			}
		})
		this.setData({
			orderId: options.orderId,
      bgcolor: '#FF6347',
		})
		this.loadProductDetail();
	},
	onReady: function() {
		var that = this;
		setTimeout(function() {
			that.setData({
				remind: ''
			});
		}, 1000);
		countdown(that);
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
  
  
  
  getUserformid: function (e) {
    //储存id 并跳转
    var formid = e.detail.formId;
    var order_id = e.detail.target.dataset.orderid; //订单ID
    // var order_sn = e.detail.target.dataset.ordersn; //订单号
    this.sendFormid(formid, 'order');
    wx.navigateTo({
      url: '../draw/cantuan?orderId=' + order_id + '&&type1=11'
    })
  },
  sendFormid: function (fromid, page) {
    var that = this
    app.request.wxRequest({
      url: '&action=groupbuy&m=getFormid',
      data: { from_id: fromid, userid: app.globalData.userInfo.openid, page: page },
      method: 'post',
      success: function () {
      }
    })
  },
	// 订单详情
	loadProductDetail: function() {
		var that = this;
		var type1 = that.data.type1;
		wx.request({
			url: app.d.ceshiUrl + '&action=order&m=order_details',
			method: 'post',
			data: {
				order_id: that.data.orderId,
				type1: that.data.type1,
			},
			header: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			success: function(res) {
				var status = res.data.status;
        
				var type1 = res.data.type1;
				var dr = res.data.dr;
				var wx_id = res.data.wx_id;
				var title = res.data.title;
				var drawid = res.data.drawid;
				var p_id = res.data.p_id;
				var status_pid = res.data.pid;
        var list= res.data.list;
        var z_price = Number(res.data.z_price);
        var pro_price = 0;
        var freight = res.data.freight ? res.data.freight : 0;
        var red_packet = res.data.red_packet;
        for (var i = 0; i < list.length; i++) {
          pro_price = Number(pro_price) + Number(list[i].p_price) * Number(list[i].num); 
        }
				if(status == 1) {
					that.setData({
            pro_price: pro_price,
            freight: freight,
						id: res.data.id,
						sNo: res.data.sNo,
						z_price: z_price,
						add_time: res.data.add_time,
						rstatus: res.data.rstatus,
						list: res.data.list,
						address: res.data.address,
						mobile: res.data.mobile,
						name: res.data.name,
						lottery_status: res.data.lottery_status,
						type1: res.data.type1,
						dr: res.data.dr,
						title: res.data.title,
						drawid: res.data.drawid,
						role: res.data.role,
						p_id: res.data.p_id,
						coupon_money: res.data.coupon_money,
            consumer_money: res.data.consumer_money,
						user_money: res.data.user_money,
						coupon_activity_name: res.data.coupon_activity_name,
						status_pid: res.data.pid,
            otype: res.data.otype,
            ptcode: res.data.ptcode,
            man_num: res.data.man_num,
            groupid: res.data.pid,
            red_packet: red_packet,
            payment: z_price,
					});

					//支付倒计时
					var str = res.data.add_time; // 日期字符串
					str = str.replace(/-/g, '/'); // 将-替换成/，因为下面这个构造函数只支持/分隔的日期字符串
					var d_date = Date.parse(new Date(str)); // 构造一个日期型数据，值为传入的字符串
					var t_time = Date.parse(new Date());
					var t_res = t_time - d_date;
					var two_day = 7200 * 1000 * 24;
					var djs = two_day - t_res;
					total_micro_second = djs;
					//倒计时结束
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
						if(status == 1) {
							wx.showToast({
								title: res.data.err,
								success: 2000
							});
							wx.navigateBack({
								delta: 2
							})
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
	// 确认收货
	recOrder: function(e) {
		var that = this;
		var orderId = e.currentTarget.dataset.orderId;
		wx.showModal({
			title: '提示',
			content: '你确定已收到宝贝吗？',
			success: function(res) {
				res.confirm && wx.request({
					url: app.d.ceshiUrl + '&action=order&m=recOrder',
					method: 'post',
					data: {
						id: orderId,
					},
					header: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					success: function(res) {
						//--init data
						var status = res.data.status;
						if(status == 1) {
							wx.showToast({
								title: '操作成功！',
								duration: 2000
							});
							that.loadProductDetail();
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

	//触发支付
	payOrder: function(e) {
		var that = this;
		var order_id = e.detail.target.dataset.orderid; //订单ID
		var order_sn = e.detail.target.dataset.ordersn; //订单号
		var price = e.detail.target.dataset.price; //支付价格
		var pay = e.detail.target.dataset.pay; //支付方式
		var user_id = app.globalData.userInfo.openid; //openid
		var form_id = e.detail.formId; //消息ID
		this.setData({
			form_id: form_id,
		});
		console.log(pay)
		if(pay) {
			if(pay == "wxPay") {
				that.payOrderByWechat(order_id, order_sn, price);
			} else {
				wx.showModal({
					title: '余额支付',
					content: '是否使用余额支付？',
					success: function(res) {
						if(res.confirm) {
							//组合支付 替换数据
							that.wallet_pay(order_id, order_sn, price);
							console.log('用户点击确定');
						} else if(res.cancel) {
							console.log('用户点击取消')
						}
					}
				})
			}
		} else {
			wx.showToast({
				title: '已为您选择默认支付方式',
				icon: 'none',
				duration: 2000,
			});
			//当都没有选中时 循环找到默认的支付方式 在设置支付方式数据
			var pays = that.data.pays,
				j = 0;
			for(j = 0; j < pays.length; j++) {
				if(pays[j].value == 'wxPay') {
					pays[j].checked = true;
				} else {
					pays[j].checked = false;
				}
			}
			that.setData({
				pays: pays,
				paytype: 'wxPay',
			});
			that.payOrderByWechat(order_id, order_sn, price);
		}

	},

	// 微信支付
	payOrderByWechat: function(order_id, order_sn, price) {
    var that = this;
		var user_id = app.globalData.userInfo.openid;
		//调起微信支付
    console.log(price)
    // var cmoney = price.toFixed(2);
		wx.request({
			url: app.d.ceshiUrl + '&action=pay&m=pay',
			data: {
				order_id: order_sn,
        cmoney: price,
				openid: user_id,
				type: 1
			},
			method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
			header: {
				'Content-Type': 'application/x-www-form-urlencoded'
			}, // 设置请求的 header
			success: function(res) {
				if(res.data) {
          var dingdanhao = res.data.out_trade_no;

          that.up_out_trade_no(dingdanhao);
          that.setData({
            trade_no: dingdanhao
          })
					wx.requestPayment({
						timeStamp: res.data.timeStamp,
						nonceStr: res.data.nonceStr,
						package: res.data.package,
						signType: 'MD5',
						paySign: res.data.paySign,
						success: function(res) {
              wx.showModal({
                content: "支付成功！",
                showCancel: false,
                confirmText: "确定",
                success: function (res) {
                  setTimeout(function () {
                    wx.redirectTo({
                      url: '../order/detail?orderId=' + that.data.orderId
                    })
                  }, 1000);
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
	powerDrawer: function(e) {
		var currentStatu = e.currentTarget.dataset.statu;
		this.util(currentStatu);
	},
	util: function(currentStatu) {
		//创建动画
		var animation = wx.createAnimation({
			duration: 200, //时长  
			timingFunction: "linear", //线性  
			delay: 0 //不延迟  
		});

		// 这个赋给当前的动画  
		this.animation = animation;

		// 执行第一组动画：Y轴偏移150px
		animation.translateY(150).step();

		// 导出动画对象赋给数据对象储存  
		this.setData({
			animationData: animation.export()
		})

		// 设置定时器到指定时候后，执行第二组动画  
		setTimeout(function() {
			// 执行第二动画：Y轴不偏移  
			animation.translateY(0).step()
			// 给数据对象储存的第一动画，替为执行完第二动画的动画对象  
			this.setData({
				animationData: animation
			})

			//关闭
			if(currentStatu == "close") {
				this.setData({
					showModalStatus: false
				});
			}
		}.bind(this), 200)

		// 显示
		if(currentStatu == "open") {
			this.setData({
				showModalStatus: true
			});
		}
	},
	// 发起钱包支付
	wallet_pay: function() {
		var that = this;
		var total = that.data.z_price; // 付款金额
		/*
		1.支付成功
		2.支付失败：提示；清空密码；自动聚焦isFocus:true，拉起键盘再次输入
		*/
		wx.request({
			url: app.d.ceshiUrl + '&action=product&m=wallet_pay',
			method: 'post',
			data: {
				uid: app.globalData.userInfo.openid,
				total: total
			},
			header: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			success: function(res) {
				var status = res.data.status;
				var order_id = that.data.id;
				if(status) {
					//支付成功  修改订单
					that.up_order(that.data.order_sn);
				} else {
					wx.showToast({
						title: '余额不足，请更换支付方式！',
						icon: 'none',
						duration: 2000,
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
	},

	//修改订单
	up_order: function(coupon_money) {
		var that = this;
		wx.request({
			url: app.d.ceshiUrl + '&action=product&m=up_order',
			method: 'post',
			data: {
        coupon_id: that.data.coupon_id ? that.data.coupon_id:'', // 优惠券id
        consumer_money: that.data.consumer_money, // 使用积分
				coupon_money: that.data.z_price, // 付款金额
				order_id: that.data.sNo, // 订单号
				user_id: app.globalData.userInfo.openid, // 微信id
        pay: that.data.paytype
			},
			header: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			success: function(res) {
				var orderId = res.data.sNo; // 订单号
				var oid = res.data.id; // 订单id
				var f_pname = res.data.pname; // 拼接的商品名称
				var f_coupon_money = res.data.coupon_money; // 订单价格
				var time = res.data.time; // 当前时间
				var form_id = that.data.form_id;
				var user_id = app.globalData.userInfo.openid; // 微信id

				// 调用信息发送
				that.notice(orderId, oid, f_coupon_money, user_id, form_id, f_pname);
				wx.showModal({
					content: "支付成功！",
					showCancel: false,
					confirmText: "确定",
					success: function(res) {
						wx.redirectTo({
							url: '../order/detail?orderId=' + oid
						})
					}
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
				page: 'pages/order/detail?orderId=' + order_sn,
				order_sn: order_id,
				price: price,
				user_id: user_id,
				form_id: form_id,
				f_pname: f_pname
			},
			header: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			success: function(res) {
				console.log(form_id)
			}
		})
	},
  add_fromid: function (e) {
    var that = this;
    var formId = e.detail.formId;
    if (formId != 'the formId is a mock one') {
      var page = 'pages/product/detail'
      app.request.wxRequest({
        url: '&action=product&m=save_formid',
        data: { from_id: formId, userid: app.globalData.userInfo.openid },
        method: 'post',
        success: function (res) {
          console.log(res)
        }
      })
    }
    wx.switchTab({
      url: '../index/index'
    })
  },
  //跳转index
  t_index: function () {
    wx.switchTab({
      url: '../index/index'
    })
  },
  up_out_trade_no: function (out_trade_no) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=order&m=up_out_trade_no',
      method: 'post',
      data: {
        allow: that.data.allow, // 使用积分
        d_yuan: that.data.d_yuan,
        trade_no: out_trade_no,
        coupon_id: that.data.coupon_id ? that.data.coupon_id : '', // 优惠券id
        consumer_money: that.data.consumer_money, // 使用积分
        coupon_money: that.data.z_price, // 付款金额
        order_id: that.data.sNo, // 订单号
        user_id: app.globalData.userInfo.openid, // 微信id
        pay: that.data.paytype
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log(res)
      }
    })
  },
})