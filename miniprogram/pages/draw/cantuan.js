var app = getApp();
Page({
  data:{
    hour: '',
    minute: '',
    miao: '',
    remind: '加载中',
    paytype: 'buynow',
    commodityAttr: [],
    attrValueList: [],
    buynum: 1,
  },
  onLoad:function(options){
    console.log(options);
    this.login();
    if (options.referee_openid != ''){
      app.globalData.userInfo['referee_openid'] = options.referee_openid;
    }else{
      app.globalData.userInfo['referee_openid'] = '';
    }
    this.setData({
      options: options,
      bgcolor: app.d.bgcolor,
      order_id: options.orderId, // 订单id
      choujiangid: options.choujiangid ? options.choujiangid : '', // 抽奖活动id
      type1: options.type1 ? options.type1 : '',//判断是抽奖还是其他活动
      role: options.role ? options.role : '', // 角色
      size: options.size ? options.size : '',
    })
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor //页面标题为路由参数
    });
    this.getdraw(options.orderId);
  },

  //下拉事件
  onPullDownRefresh: function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
  },
  onShow: function () {
    var id = this.id;
    var that = this;
    that.setData({
      includeGroup: that.data.commodityAttr,
    });
    that.distachAttrValue(that.data.commodityAttr);
    // 只有一个属性组合的时候默认选中      
    if (that.data.commodityAttr.length == 1) {
      for (var i = 0; i < that.data.commodityAttr[0].attrValueList.length; i++) {
        that.data.attrValueList[i].selectedValue = that.data.commodityAttr[0].attrValueList[i].attrValue;
      }
      var sizeid = that.data.commodityAttr[0].priceId;
      this.setData({
        attrValueList: that.data.attrValueList,
        sizeid: sizeid
      });
      that.setimg();
    }
  },
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
  },
  // 进入抽奖团详情
  getdraw: function (order_id){
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=draw&m=getdraw',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid, // 本人微信id
        referee_openid: app.globalData.userInfo.referee_openid, // 推荐人微信id
        order_id: order_id, // 订单id
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var num = res.data.arr.num;
        that.setTimeData(res.data.arr.time)
        for (var i = 0; i < num; i++) {
          if (res.data.user[i]) {
            res.data.user[i] = res.data.user[i]
          } else {
            res.data.user[i] = {}
          }
        }
        if (res.data.arr.drawid != 0){
          var type1 = 1;
        }
        that.setData({
          list: res.data.arr,
          user: res.data.user,
          commodityAttr: res.data.commodityAttr,
          type1: type1,
        });
        that.onShow();
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  // 还剩多少时间
  setTimeData:function(time){
    var that = this;
    var inter = setInterval(function(){
      var t = --time;
      var d = Math.floor(t / 24 / 60 / 60);
      var h = Math.floor(t % 86400 / 3600); 
      var m = Math.floor(t % 86400 % 3600 / 60);
      var s = t%60;
      if (d < 10) {
        d = "0" + d;
      } else if (d > 10){
        d = d;
      }else{
        d = 0;
      }
      if(h<10) h = "0"+h;
      if(m<10) m = "0"+m;
      if(s<10) s = "0"+s;
      var timeStr = h+':'+m+':'+s
      if (time <= 0) {
        clearInterval(inter);
        that.setData({
          hour: '00',
          minute: '00',
          miao: '00'
        })
      }
      if(d > 0){
        that.setData({
          day: d,
          hour: h,
          minute: m,
          miao: s
        })
      }else{
        that.setData({
          hour: h,
          minute: m,
          miao: s
        })
      }
      
    }, 1000)
  },
  // 分享
  onShareAppMessage:function(options){
    var that = this;
    var path = '/pages/draw/cantuan?orderId=' + that.data.order_id + '&referee_openid=' + app.globalData.userInfo.openid + '&type1=1&choujiangid=' + that.data.list.draw_id + '&role=' + that.data.list.drawid;
    console.log(path)
    return {
      title: '【好消息】' + that.data.list.p_price+'元就能拼到 '+ that.data.list.product_title,
        path: path,
        success:function(res){
          console.log(res)
        }
      }
  },
  // 弹窗
  setModalStatus: function (e) {
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    //定义点击的类型
    var type = e.target.dataset.type ? e.target.dataset.type : false;
    //控制两种不同显示方式 
    if (type) {
      this.setData({
        xefl: false,
      })
    } else {
      this.setData({
        xefl: true,
      })
      type = this.data.paytype;
    }
    this.animation = animation
    animation.translateY(300).step();
    this.setData({
      paytype: type,
      animationData: animation.export()
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
    }.bind(this), 200)
  },
  /* 获取数据 */
  distachAttrValue: function (commodityAttr) {
    /** 
      将后台返回的数据组合成类似 
      { 
        attrKey:'型号', 
        attrValueList:['1','2','3'] 
      } 
    */
    // 把数据对象的数据（视图使用），写到局部内  
    var attrValueList = this.data.attrValueList;
    // 遍历获取的数据  
    for (var i = 0; i < commodityAttr.length; i++) {
      for (var j = 0; j < commodityAttr[i].attrValueList.length; j++) {
        var attrIndex = this.getAttrIndex(commodityAttr[i].attrValueList[j].attrKey, attrValueList);
        // 如果还没有属性索引为-1，此时新增属性并设置属性值数组的第一个值；索引大于等于0，表示已存在的属性名的位置  
        if (attrIndex >= 0) {
          // 如果属性值数组中没有该值，push新值；否则不处理  
          if (!this.isValueExist(commodityAttr[i].attrValueList[j].attrValue, attrValueList[attrIndex].attrValues)) {
            attrValueList[attrIndex].attrValues.push(commodityAttr[i].attrValueList[j].attrValue);
          }
        } else {
          attrValueList.push({
            attrKey: commodityAttr[i].attrValueList[j].attrKey,
            attrValues: [commodityAttr[i].attrValueList[j].attrValue]
          });
        }
      }
    }
    for (var i = 0; i < attrValueList.length; i++) {
      for (var j = 0; j < attrValueList[i].attrValues.length; j++) {
        if (attrValueList[i].attrValueStatus) {
          attrValueList[i].attrValueStatus[j] = true;
        } else {
          attrValueList[i].attrValueStatus = [];
          attrValueList[i].attrValueStatus[j] = true;
        }
      }
    }
    this.setData({
      attrValueList: attrValueList
    });
  },
  getAttrIndex: function (attrName, attrValueList) {
    // 判断数组中的attrKey是否有该属性值  
    for (var i = 0; i < attrValueList.length; i++) {
      if (attrName == attrValueList[i].attrKey) {
        break;
      }
    }
    return i < attrValueList.length ? i : -1;
  },
  isValueExist: function (value, valueArr) {
    // 判断是否已有属性值  
    for (var i = 0; i < valueArr.length; i++) {
      if (valueArr[i] == value) {
        break;
      }
    }
    return i < valueArr.length;
  },
  /* 选择属性值事件 */
  selectAttrValue: function (e) {
    /* 
    点选属性值，联动判断其他属性值是否可选 
    { 
      attrKey:'型号', 
      attrValueList:['1','2','3'], 
      selectedValue:'1', 
      attrValueStatus:[true,true,true] 
    } 
    */
    var attrValueList = this.data.attrValueList;
    var index = e.currentTarget.dataset.index;//属性索引  
    var key = e.currentTarget.dataset.key; // 属性类型
    var value = e.currentTarget.dataset.value; // 属性名称
    if (e.currentTarget.dataset.status || index == this.data.firstIndex) {
      if (e.currentTarget.dataset.selectedvalue == e.currentTarget.dataset.value) {
        // 取消选中  
        this.disSelectValue(attrValueList, index, key, value);
      } else {
        // 选中  
        this.selectValue(attrValueList, index, key, value);
      }

    }
  },
  /* 选中 */
  selectValue: function (attrValueList, index, key, value, unselectStatus) {
    var that = this;
    attrValueList[index].selectedValue = value;
    for (var m = 0; m < attrValueList.length; m++) {
      if (attrValueList[m].selectedValue) {

      } else {
        attrValueList[m].selectedValue = '';
      }
    }
    wx.request({
      url: app.d.ceshiUrl + '&action=product&m=select_size',
      method: 'post',
      data: {
        attrValueList: attrValueList,
        index: index,
        key: key,
        value: value,
        pid: that.data.list.p_id,
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        //设置购物车刷新
        var data = res.data;
        if (data.status == 1) {
          that.setData({
            attrValueList: res.data.attrValueList,
          });
          that.setimg();
        } else {
          wx.showToast({
            icon: 'loading',
            title: data.err,
            duration: 2000
          });
        }
      },
      fail: function () {
        wx.showtestToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  /* 取消选中 */
  disSelectValue: function (attrValueList, index, key, value) {
    var commodityAttr = this.data.commodityAttr;
    attrValueList[index].selectedValue = '';
    var rew = 0;
    // 判断属性是否可选  
    for (var i = 0; i < attrValueList.length; i++) {
      for (var j = 0; j < attrValueList[i].attrValues.length; j++) {
        if (attrValueList[i].selectedValue != '') {
          rew = rew + 1;
        }
        attrValueList[i].attrValueStatus[j] = true;
      }
    }
    if (rew == 0) {
      var num = this.data.kucun;
      var itemData = this.data.itemData;
      itemData.num = num;
      this.setData({
        itemData: itemData,
      });
    }
    this.setData({
      includeGroup: commodityAttr,
      attrValueList: attrValueList
    });

    for (var i = 0; i < attrValueList.length; i++) {
      if (attrValueList[i].selectedValue) {
        this.selectValue(attrValueList, i, attrValueList[i].attrKey, attrValueList[i].selectedValue, true);
      }
    }
  },
  /* 点击确定 */
  submit: function (e) {
    var that = this;
    var value = [];
    for (var i = 0; i < this.data.attrValueList.length; i++) {
      if (!this.data.attrValueList[i].selectedValue) {
        break;
      }
      value.push(that.data.attrValueList[i].selectedValue);
    }
    if (i < that.data.attrValueList.length) {
      wx.showToast({
        title: '请完善属性！',
        icon: 'loading',
        duration: 1000
      })
    } else {
      var type = e.target.dataset.type;
      var sizeid = that.data.sizeid;
      that.addShopCart(e, sizeid);
    }
  },
  setimg: function (e) {
    //设置数据 全部选好后替换图片和价格属性
    var that = this;
    var value = [];
    for (var i = 0; i < this.data.attrValueList.length; i++) {
      if (!this.data.attrValueList[i].selectedValue) {
        break;
      }
      value.push(that.data.attrValueList[i].selectedValue);
    }
    if (i < that.data.attrValueList.length) {

    } else {
      //设置显示数据
      for (var i = 0; i < that.data.commodityAttr.length; i++) {
        var name = that.data.commodityAttr[i].attrValueList[0].attrValue;
        var size = that.data.commodityAttr[i].attrValueList[1].attrValue;
        var color = that.data.commodityAttr[i].attrValueList[2].attrValue;
        if (name == value[0] && size == value[1] && color == value[2]) {
          var list = that.data.list;
          list.img = that.data.commodityAttr[i].img;
          list.stock = that.data.commodityAttr[i].stock;
          var choujiangid = that.data.choujiangid;
          var sizeid = that.data.commodityAttr[i].priceId;
          that.setData({
            value: value,
            list: list,
            sizeid: sizeid,
            choujiangid: choujiangid,
          });
        }
      }
    }
  },
  addShopCart: function (e, sizeid) {
    //添加到购物车
    var that = this;
    //抽奖弹框
    var productId = that.data.list.p_id;
    var sizeid = that.data.sizeid;
    var choujiangid = that.data.list.draw_id;
    var role = that.options.role;
    if (role == '' || role == 'undefined') {
      role = '';
    }
    wx.redirectTo({
      url: '../order/pay?productId=' + productId + '&sizeid=' + sizeid + '&choujiangid=' + choujiangid + '&type1=' + 11 + '&role=' + role,
    });
    return;
    
  },
  preventTouchMove: function (e) {

  },
  material: function (res) {
    wx.getUserInfo({
      success: function (res) {
        var userInfo = res.userInfo;
        var nickName = userInfo.nickName;
        var avatarUrl = userInfo.avatarUrl;
        var gender = userInfo.gender; //性别 0：未知、1：男、2：女
        wx.request({
          url: app.d.ceshiUrl + '&action=user&m=material',
          method: 'post',
          data: {
            openid: app.globalData.userInfo.openid,
            nickName: nickName,
            avatarUrl: avatarUrl,
            gender: gender
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            wx.showToast({
              title: res.data.info,
              success: 2000
            });
          }
        })
      }
    })
  },
  //获取用户信息新接口
  agreeGetUser: function (e) {
    let that = this
    if (e.detail.errMsg == 'getUserInfo:ok') {
      //获取成功设置状态
      app.globalData.userlogin = true;
      wx.setStorageSync('userlogin', true);
      //设置用户信息本地存储
      try {
        wx.setStorageSync('userInfo', e.detail.userInfo);
      } catch (e) {
        wx.showToast({
          title: '系统提示:网络错误！',
          icon: 'warn',
          duration: 1500,
        })
      }
      wx.showLoading({
        title: '加载中...',
        duration: 1500,
      })
      that.setData({
        userlogin: false
      })
      that.getOP(e.detail.userInfo)
    } else {
      wx.showToast({
        title: '没有授权，不能进入小程序个人中心！',
        icon: 'none',
        duration: 2000
      })
      //没有授权需要弹框 
      that.setData({
        userlogin: true
      });
    }
  },
  login: function () {
    var that = this;
    //取出本地存储用户信息，解决需要每次进入小程序弹框获取用户信息
    var userInfo = wx.getStorageSync('userInfo');
    wx.login({
      success: res => {
        app.globalData.code = res.code
        wx.setStorageSync('code', res.code)
      },
      fail: function () {
        wx.showToast({
          title: '系统提示:网络错误！',
          icon: 'warn',
          duration: 1500,
        })
      }
    })

    wx.getSetting({
      success: (res) => {
        //没有授权需要弹框 
        if (!res.authSetting['scope.userInfo']) {
          that.setData({
            userlogin: true
          });
        } else {
          //判断用户已经授权。不需要弹框
          if (app.globalData.userlogin) {
            that.setData({
              userlogin: false
            })
            app.globalData.userlogin = true;
            wx.setStorageSync('userlogin', true);
          } else {
            that.setData({
              userlogin: true
            });
          }
        }
      },
      fail: function () {
        wx.showToast({
          title: '系统提示:网络错误！',
          icon: 'warn',
          duration: 1500,
        })
      }
    })
  },
  getOP: function (res) {
    //提交用户信息 获取用户id
    let that = this
    let userInfo = res;
    var user = app.globalData.userInfo;
    app.globalData.userInfo['avatarUrl'] = userInfo.avatarUrl; // 头像
    app.globalData.userInfo['nickName'] = userInfo.nickName; // 昵称
    app.globalData.userInfo['gender'] = userInfo.gender; //  性别

    wx.setStorageSync('userInfo', app.globalData.userInfo);
    //写入缓存
    var nickName = userInfo.nickName;
    var avatarUrl = userInfo.avatarUrl;
    var gender = userInfo.gender; //性别 0：未知、1：男、2：女
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=material',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
        nickName: nickName,
        avatarUrl: avatarUrl,
        gender: gender
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        wx.showToast({
          title: '授权成功!',
          success: 2000
        });
        that.onLoad(that.data.options);
      }
    })
  },
})