
// pages/group/info.js
var app = getApp();
Page({
  data:{
    num:1,
    commodityAttr: [],
    attrValueList: [],
    sum: null, 
    hour: '',
    minute: '',
    miao: '',
    remind: true,
    xefl: true,//点击选择规格显示状态
    bgcolor: '',
    price: '',
    image: '',
    man_num:null //团满人数
  },
  onLoad:function(options){
    var self = this
    self.id = options.id;
    self.groupid = options.groupid;
    self.pro_id = options.pro_id;
    
    self.setData({
      man_num: options.man_num,
      options: options
    })
    this.loadProductDetail(options);
  },
  onShow: function () {
    var id = this.id;
    var self = this;
    self.onLoad(self.data.options);
  },
  loadProductDetail: function () {
    var self = this, options = this.data.options;
    var openid = app.globalData.userInfo.openid;
    console.log(app.globalData.userInfo,'openid')
    if (openid) {
      app.request.wxRequest({
        url: '&action=groupbuy&m=cangroup',
        data: { oid: options.id, groupid: options.groupid, gid: options.pro_id, userid: app.globalData.userInfo.openid },
        method: 'post',
        success: function (res) {
          
          if (res.isplug == 0) {
            wx.showModal({
              title: '温馨提示!',
              content: '拼团活动已结束',
              showCancel: false,
              success: function (res) {
                wx.switchTab({
                  url: '../index/index',
                })
              },
            })
            return false;
          }

          setTimeout(function () {
            self.setData({
              remind: false
            });
          }, 1500);
          if (res.intro) {
            WxParse.wxParse('goodsIntro', 'html', res.intro, self, 5);
          }
          self.goodsInfo = res.groupmsg;
          self.setTimeData(res.groupmsg.leftTime)
          var groupMember = [];
          for (var i = options.man_num - 1; i >= 0; i--) {
            if (res.groupMember[i]) {
              groupMember[i] = res.groupMember[i]
            } else {
              groupMember[i] = {}
            }
          }
          self.setData({
            groupMember, groupMember,
            groupInfo: self.goodsInfo,
            image: self.goodsInfo.img,
            price: self.goodsInfo.p_price,
            yprice: self.goodsInfo.yprice,
            skuBeanList: res.skuBeanList,
            attrList: res.attrList,
            prostatus: res.prostatus
          })
          
          self.one();
        }
      });
      self.setData({
         bgcolor: app.d.bgcolor,
      })
      wx.setNavigationBarColor({
        frontColor: app.d.frontColor,//
        backgroundColor: app.d.bgcolor //页面标题为路由参数
      });
    } else {
      //不存在openid  先获取 在回调  传递that
      setTimeout(function () {
        that.loadProductDetail();
      }, 1000);
    }
  },
  //首次进去选中
  one: function () {
    var that = this;
    var attrListIn = this.data.attrList;
    var skuBeanListIn = this.data.skuBeanList;
    var select_list = skuBeanListIn[0];
    for (var i = 0; i < attrListIn.length; i++) {
      for (var j = 0; j < attrListIn[i].attr.length; j++) {
        for (var b = 0; b < select_list.attributes.length; b++) {
          if (select_list.attributes[b].attributeId == attrListIn[i].attr[j].attributeId && select_list.attributes[b].attributeValId == attrListIn[i].attr[j].id) {
            attrListIn[i].attr[j].select = true;
          }
        }
      }
    }

    var groupInfo = that.data.groupInfo;
    groupInfo.img = select_list.imgurl;
    groupInfo.p_price = select_list.price;
    groupInfo.p_num = select_list.count;
    var sizeid = select_list.cid;

    that.setData({
      groupInfo: groupInfo,
      sizeid: sizeid,
      value: select_list.name
    });
    this.onData();
  },
  //下拉事件
  onPullDownRefresh: function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
  },

  onReady: function () {

  },

  setTimeData:function(time){
    var self = this;
    var inter = setInterval(function(){
        var t = --time;
        var h =  Math.floor(t/60/60);
        var m = Math.floor((t-h*60*60)/60);
        var s = t%60;
        if(h<10) h = "0"+h;
        if(m<10) m = "0"+m;
        if(s<10) s = "0"+s;
        var timeStr = h+':'+m+':'+s    
      self.setData({
        hour: h,
        minute: m,
        miao: s
      })
    }, 1000)
    if (time <= 0) {
      clearInterval(inter);
      self.setData({
        hour: '00',
        minute: '00',
        miao: '00'
      })
    }
  },
  onShareAppMessage:function(options){
    var self = this;
    var path = '/pages/group_buy/cantuan?id=' + self.id + '&groupid=' + self.groupid + '&man_num=' + this.data.man_num + '&pro_id=' + self.goodsInfo.ptgoods_id;
    console.log(path)
    return {
      title: '【快来拼】' + self.data.price + '元就能拼到 ' +self.goodsInfo.p_name,
        path: path,
        success:function(res){
          console.log(path)
          console.log(res)
        }
      }
  },
  goToHome:function(){
    wx.switchTab({
      url:'/pages/index/index'
    })
  },
  showGoodsDetail:function(e){
    var id = e.currentTarget.dataset.id;
    app.redirect('group_buy/detail', 'gid=' + id + '&sum=' + this.data.groupInfo.sum + '&group_id=' + this.groupid)
  },
  goToBuy:function(){
    var that = this;
    var obj = '';
    var value = [];
    if (that.data.prostatus == 1) {
      wx.showToast({
        title: '此商品已下架!',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    if (that.data.num > that.goodsInfo.productnum) {
      wx.showToast({
        title: '抱歉，一次最多只能购买' + that.goodsInfo.productnum + '件产品！',
        icon: 'none',
        duration: 2000
      })
  } else {
    if (that.data.sizeid.length < 1) {
      wx.showToast({
        title: '请完善属性！',
        icon: 'loading',
        duration: 1000
      })
    } else {
      var sizeid = that.data.sizeid;    
      obj += '&pro_name=' + that.goodsInfo.ptgoods_name + '&num=' + that.data.num + '&pro_id=' + that.goodsInfo.ptgoods_id + '&sizeid=' + sizeid + '&groupid=' + that.groupid + '&oid=' + that.id + '&pagefrom=cantuan';
      app.redirect('group_buy/payfor', obj);
    }
  }
  },

  /**
   * Sku核心算法
   * 根据所有出当前类别之外的选择 判断按钮的enable ？ false or true
   */
  onData: function () {

    var attrListIn = this.data.attrList;

    for (var i = 0; i < attrListIn.length; i++) {
      var attrListBig = attrListIn[i];

      //当前类别之外的选择列表
      var attrsOtherSelect = [];
      for (var j = 0; j < attrListIn.length; j++) {
        var attrListSmall = attrListIn[j];
        if (attrListSmall.id != attrListBig.id) {
          for (var k = 0; k < attrListSmall.attr.length; k++) {
            var attrListSmallAttr = attrListSmall.attr[k];
            if (attrListSmallAttr.enable && attrListSmallAttr.select) {
              attrsOtherSelect.push(attrListSmallAttr);
            }
          }
        }
      }

      var enableIds = [];

      var skuBeanListIn = this.data.skuBeanList;

      for (var z = 0; z < skuBeanListIn.length; z++) {
        var ism = true;
        var skuBean = skuBeanListIn[z];

        for (var j = 0; j < attrsOtherSelect.length; j++) {
          var enable = false;
          for (var k = 0; k < skuBean.attributes.length; k++) {

            var goodAttrBean = skuBean.attributes[k];
            if (attrsOtherSelect[j].attributeId == goodAttrBean.attributeId
              && attrsOtherSelect[j].id == goodAttrBean.attributeValId) {
              enable = true;
              break;
            }
          }
          ism = enable && ism;
        }

        if (ism) {
          for (var o = 0; o < skuBean.attributes.length; o++) {
            var goodAttrBean = skuBean.attributes[o];

            if (attrListBig.id == goodAttrBean.attributeId) {
              enableIds.push(goodAttrBean.attributeValId);
            }
          }
        }
      }



      var integers = enableIds;
      for (var s = 0; s < attrListBig.attr.length; s++) {
        var attrItem = attrListBig.attr[s];

        attrItem.enable = integers.indexOf(attrItem.id) != -1;

      }
    }

    // 重新赋值
    this.setData({
      attrList: attrListIn,
      skuBeanList: skuBeanListIn
    })
  },

  /**
   * 规格属性点击事件
   */
  onChangeShowState: function (event) {
    var that = this;
    var listItem = this.data.attrList;
    var items = listItem[event.currentTarget.dataset.idx];
    var item = items.attr[event.currentTarget.dataset.index];

    if (!item.enable) {
      return;
    }

    var select = !item.select;

    for (var i = 0; i < items.attr.length; i++) {
      items.attr[i].select = false;
    }

    item.select = select;

    // 获取点击属性列表
    var canGetInfo = [];
    for (var skuIndex = 0; skuIndex < listItem.length; skuIndex++) {
      for (var skuIndexIn = 0; skuIndexIn < listItem[skuIndex].attr.length; skuIndexIn++) {
        if (listItem[skuIndex].attr[skuIndexIn].enable && listItem[skuIndex].attr[skuIndexIn].select) {
          canGetInfo.push(listItem[skuIndex].attr[skuIndexIn]);
        }
      }
    }

    console.log(canGetInfo, "目前点击的属性");

    var canGetInfoLog = "";

    var skuBeanList = this.data.skuBeanList;

    var haveSkuBean = [];
    // 对应库存清单扫描
    for (var skuBeanIndex = 0; skuBeanIndex < skuBeanList.length; skuBeanIndex++) {
      var iListCount = 0;
      for (var skuBeanIndexIn = 0; skuBeanIndexIn < skuBeanList[skuBeanIndex].attributes.length; skuBeanIndexIn++) {
        if (canGetInfo.length == skuBeanList[skuBeanIndex].attributes.length) {
          if (skuBeanList[skuBeanIndex].attributes[skuBeanIndexIn].attributeValId == canGetInfo[skuBeanIndexIn].id) {
            iListCount++;
          }
        } else {
          canGetInfoLog = "库存清单不存在此属性" + " ";

        }
      }
      if (iListCount == skuBeanList[skuBeanIndex].attributes.length) {
        haveSkuBean.push(skuBeanList[skuBeanIndex]);
      }
    }



    for (var iox = 0; iox < canGetInfo.length; iox++) {
      canGetInfoLog += canGetInfo[iox].attributeValue + " ";
    }

    if (haveSkuBean.length != 0) {

      var groupInfo = that.data.groupInfo;
      groupInfo.img = haveSkuBean[0].imgurl;
      groupInfo.p_price = haveSkuBean[0].price;
      groupInfo.p_num = haveSkuBean[0].count;
      var sizeid = haveSkuBean[0].cid;
      console.log(sizeid)
      that.setData({
        groupInfo: groupInfo,
        sizeid: sizeid,
        value: canGetInfoLog
      });
    } else {
      console.log('没选完')
      that.setData({
        sizeid: '',
        value: ''
      });
    }

    // 重新赋值
    this.setData({
      attrList: listItem,
      infoText: canGetInfoLog,
    })

    // 重新sku运算
    this.onData();
  },
  minus:function(){
    var num = this.data.num > 1 ? --this.data.num : 1
    this.setData({
      num : num
    })
  },
  plus:function(){
    var num = ++this.data.num
    this.setData({
      num : num
    })
  },
  getUserformid: function (e) {
    var formid = e.detail.formId;
    this.sendFormid(formid)
    this.setModalStatus(e);
  },
  getformidToo: function (e) {
    var formid = e.detail.formId;
    this.sendFormid(formid)
    this.goToBuy()
  },
  sendFormid: function (fromid, page) {
    app.request.wxRequest({
      url: '&action=groupbuy&m=getFormid',
      data: { from_id: fromid, userid: app.globalData.userInfo.openid },
      method: 'post',
      success: function () {

      }
    })
  },
  // 弹窗
  setModalStatus: function (e) {
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    //定义点击的类型
    this.animation = animation
    animation.translateY(300).step();
    this.setData({
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
        if (app.globalData.code){
          app.globalData.code = res.code
        }

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

  toproDetail: function () {
    var self = this
    wx.navigateTo({
      url: "../group_buy/detail?gid=" + self.pro_id + "&sum=" + self.goodsInfo.sum + "&group_id=" + self.groupid
    })
  },
  goIndex: function () {
    wx.switchTab({
      url: "../index/index"
    })
  }
})