var WxParse = require('../../wxParse/wxParse.js');
var app = getApp();
Page({
  data: {
    scrollTop:0,
    maskHidden: false,
    num:1,
    sizeid: '',
    productId:'',
    commodityAttr: [],
    attrValueList: [],
    groupid: null,
    is_over: false,
    day: '',
    hour: '',
    minute: '',
    secStr: '',
    buytype: null,
    comnum: {},
    remind: '加载中',
    paytype: 'group',
    xefl: true,//点击选择规格显示状态
    bgcolor: '',
    gprice: '',
    control: {},
    show_share: false,
  },

  onLoad: function (options) {
    var scene = decodeURIComponent(options.scene);

    if (scene != 'undefined' && scene.length > 1 && scene != '') {
      options = scene;
    }
    var self = this;
    var gid = this.gid = options.gid; 
    var group_id = options.group_id;   
    self.setData({
      groupid: group_id, //所属拼团
      sum: options.sum,
      bgcolor: app.d.bgcolor,
      fdata: 'gid=' + gid + '&sum=' + options.sum + '&group_id=' + group_id
    })
    
    var systemInfo = wx.getSystemInfoSync()
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor //页面标题为路由参数
    });
    if (options.pagefrom){
      console.log('监听成功')
    }
    app.request.wxRequest({
      url:'&action=groupbuy&m=getgoodsdetail',
      data: { gid: gid, group_id: group_id, userid:app.globalData.userInfo.openid},
      method:'post',
      success:function(res){
        console.log(res);
        WxParse.wxParse('content', 'html', res.detail.content, self, 5);
        
        if (res.isplug === '0'){
          wx.showModal({
            title: '温馨提示!',
            content: '拼团功能未开启',
            showCancel: false,
            success: function (res) {
              wx.switchTab({
                url: '../index/index',
              })
            },
          })
          return false;
        }         
        var endtime = parseInt(res.control.endtime + '000')
        self.countDown(endtime)
        self.goodsInfo = res;
        var groupList = res.groupList;
        if(groupList.length>0){  
          for (var i = 0; i < groupList.length; i++) {
            var t = --groupList[i].leftTime;
            var h =  Math.floor(t/60/60);
            var m = Math.floor((t-h*60*60)/60);
            var s = t%60;
            if(h<10) h = "0"+h;
            if(m<10) m = "0"+m;
            if(s<10) s = "0"+s;
            groupList[i].leftTimeStr = h+':'+m+':'+s
            if (groupList[i].leftTime <= 0){
              groupList[i].leftTimeStr = '00:00:00'
            }
          }
          self.setTimeData(groupList);
        }
        
        self.setData({
          windowHeight:systemInfo.windowHeight,
          itemData:res.detail,
          gprice: res.detail.group_price,
          productId: gid,
          attrList: res.attrList,
          skuBeanList: res.skuBeanList,
          comments: res.comments,
          comnum: res.comnum,
          control: res.control,
          remind: '',
          share: res.share,
          pro_status: res.detail.status
        })
        
        var timestamp = Date.parse(new Date())/1000;
        if (timestamp > parseInt(res.control.endtime)) {
           self.setData({
             is_over: true
           })
        }
        self.one();
      }
    })
    
  },
  //首次进去选中
  one: function () {
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
    var itemData = this.data.itemData;
    itemData.photo_x = select_list.imgurl;
    itemData.price_yh = select_list.price;
    itemData.num = select_list.count;
    var sizeid = select_list.cid;

    // 重新赋值
    this.setData({
      attrList: attrListIn,
      skuBeanList: skuBeanListIn,
      itemData: itemData,
      sizeid: sizeid,
      value: select_list.name
    })
    this.onData();
  },
  previewImage: function (e) {
    var current = e.target.dataset.src;
    // 路径和 图片的数组
    var arr = [current];
    // 图片预览函数
    wx.previewImage({
      current: current, // 当前显示图片的http链接  
      urls: arr, // 需要预览的图片http链接列表  
    })
  },
  //分享朋友圈 查看保存图片
  user_share: function () {
    var that = this;
    wx.showToast({
      title: '图片生成中',
      icon: 'loading',
      duration: 1500,
    });
    app.request.wxRequest({
      url: '&action=getcode&m=product_share',
      data: {
        product_img_path: that.data.itemData.image,
        product_title: that.data.itemData.pro_name,
        price: that.data.itemData.member_price,
        yprice: that.data.itemData.market_price,
        scene: that.data.fdata + '&userid=' + app.globalData.userInfo.user_id,
        path: 'pages/group_buy/detail',
        id: app.globalData.userInfo.user_id,
        pid: that.data.itemData.product_id,
        head: app.globalData.userInfo.avatarUrl,
        type: 3
      },
      method: 'post',
      success: function (res) {
        console.log(res)
        that.setData({
          maskHidden: true,
          imagePath: res.url,
        });
      }
    })

    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    that.animation = animation;
    animation.translateY(300).step();
    that.setData({
      animationData: animation.export()
    })
    setTimeout(function () {
      animation.translateY(0).step()
      that.setData({
        animationData: animation,
        show_share: false
      })
    }.bind(that), 200)
  },
  //倒计时
  countDown: function (endtime) {
    var that = this
    //var endTime = new Date(endtime.replace(/-/g, '/')).getTime();
    var nowTime = new Date().getTime();
    var total_second = endtime - nowTime;
     that.dateformat(total_second)
     
    var stop = setTimeout(function () {
      that.countDown(endtime);
    }, 1000)
    if (total_second <= 0) {
      that.setData({
        day: 0,
        hour: 0,
        minute: 0,
        secStr: 0
      });
      clearTimeout(stop);
    }
  },
  // 时间格式化输出，如11:03 25:19 每1s都会调用一次
  dateformat: function (micro_second) {
    // 总秒数
    var second = Math.floor(micro_second / 1000);
    // 天数
    var day = Math.floor(second / 3600 / 24);

    // 小时
    var hr = Math.floor(second / 3600 % 24);
    var hrStr = hr.toString();
    if (hrStr.length == 1) hrStr = '0' + hrStr;

    // 分钟
    var min = Math.floor(second / 60 % 60);
    var minStr = min.toString();
    if (minStr.length == 1) minStr = '0' + minStr;

    // 秒
    var sec = Math.floor(second % 60);
    var secStr = sec.toString();
    if (secStr.length == 1) secStr = '0' + secStr;

    /*if (day < 1) {
      return "剩 " + hrStr + ":" + minStr + ":" + secStr;
    } else {
      return "剩 " + day + " 天 " + hrStr + ":" + minStr + ":" + secStr;
    }*/
    this.setData({
      day: day,
      hour: hrStr,
      minute: minStr,
      secStr: secStr
    })
  },

  swichNav: function (e) {//点击tab切换
    var that = this;
    if (that.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current
      })
    }
  },

  powerDrawer: function () {
      var that = this
      app.redirect('group_buy/comment', 'pid=' + that.data.productId + '&good=' + that.data.comnum.good + '&notbad=' + that.data.comnum.notbad + '&bad=' + that.data.comnum.bad);
  },
  
  onShareAppMessage: function (res) {
    if (res.from === 'button') {
      // 来自页面内转发按钮
      console.log(res.target)
    }
    this.setData({
      show_share: false
    });
    return {
      title: this.data.itemData.pro_name,
      path: "/pages/group_buy/detail?gid=" + this.gid + '&sum=' + this.data.sum + '&group_id=' + this.data.groupid + '&pagefrom=share&userid=' + app.globalData.userInfo.user_id,
      imageUrl: this.data.itemData.images[0],
      success:function(res){
        console.log(res)
      }
    }
  },


  setTimeData:function(data){
    var self = this;
    var groupList = data
    setInterval(function(){
      for (var i = 0; i < groupList.length; i++) {
        var t = --groupList[i].leftTime;
        var h =  Math.floor(t/60/60);
        var m = Math.floor((t-h*60*60)/60);
        var s = t%60;
        if(h<10) h = "0"+h;
        if(m<10) m = "0"+m;
        if(s<10) s = "0"+s;
        groupList[i].leftTimeStr = h+':'+m+':'+s
        if (groupList[i].leftTime <= 0) {
          groupList[i].leftTimeStr = '00:00:00';
        }
      }
      self.setData({
        groupList:groupList
      })
    }, 1000)
  },
  joinGroup:function(e){
    var id = e.currentTarget.dataset.id;
    app.redirect('group_buy/cantuan', 'id=' + id + '&groupid=' + this.data.groupid + '&man_num=' + this.data.itemData.man_num + '&pro_id=' + this.gid + '&sum=' + this.data.sum);
  },
  goHome:function(){
    wx.switchTab({
      url:'/pages/index/index'
    })
  },
  
  goToBuy:function(){
    var that = this;
    var obj = '';
    var value = [];
    if (that.data.pro_status == 1) {
      wx.showToast({
        title: '此商品已下架!',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
  if (that.data.num > that.data.itemData.num){
    wx.showToast({
      title: '抱歉,此属性的产品库存不足!',
      icon: 'none',
      duration: 2000
    })
  }else{
    if(that.data.num > that.data.control.productnum){
      wx.showToast({
        title: '抱歉，一次最多只能购买' + that.data.control.productnum + '件产品！',
        icon: 'none',
        duration: 2000
      })
    }else{
      console.log(that.data.sizeid)
      if (that.data.sizeid.length < 1) {
        wx.showToast({
          title: '请完善属性！',
          icon: 'loading',
          duration: 1000
        })
      } else {
        var sizeid = that.data.sizeid;
        that.setData({
          showModalStatus: false
        });
        obj += '&pro_name=' + that.goodsInfo.detail.pro_name + '&num=' + that.data.num + '&pro_id=' + that.goodsInfo.detail.product_id + '&sizeid=' + sizeid + '&groupid=' + that.data.groupid + '&pagefrom=kaituan&oid=321';
        app.redirect('group_buy/payfor', obj);
      }
    }
   }
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
  getUserformid: function(e){  
    var formid = e.detail.formId;
    this.sendFormid(formid,'kt1')
    this.setModalStatus(e);
  },
  getformidToo: function (e) {
    var that = this
    var formid = e.detail.formId;
    var paytype = e.currentTarget.dataset.type; 
    
  if (paytype == 'group') {
    that.sendFormid(formid, 'kt2')
    that.goToBuy()
    }else{
      if (that.data.sizeid.length < 1) {
        wx.showToast({
          title: '请完善属性！',
          icon: 'loading',
          duration: 1000
        })
      } else {
        that.addShopCart()
      }
    }
  },

  toIndex: function () {
    wx.switchTab({
      url: '../index/index'
    })
  },

  addShopCart: function () {
    //添加到购物车
    var that = this;
    if (that.data.pro_status == 1) {
      wx.showToast({
        title: '此商品已下架!',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
      wx.request({
        url: app.d.ceshiUrl + '&action=product&m=add_cart',
        method: 'post',
        data: {
          uid: app.globalData.userInfo.openid,
          pid: that.goodsInfo.detail.product_id,
          num: that.data.num,
          sizeid: that.data.sizeid,
          pro_type: 'buynow',
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          //设置购物车刷新
          app.d.purchase = 1;
          var data = res.data;
          
          if (data.status == 1) {      
              wx.redirectTo({
                url: '../order/pay?cartId=' + data.cart_id + '&pid=' + that.data.productId,
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
    var paytype = e.currentTarget.dataset.type;
    this.animation = animation
    animation.translateY(300).step();
    this.setData({
      paytype: paytype,
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

  sendFormid:function(fromid,page){
      var that = this
      app.request.wxRequest({
        url: '&action=groupbuy&m=getFormid',
        data: { from_id: fromid, userid: app.globalData.userInfo.openid, page: page},
        method: 'post',
        success:function(){
           
        }
      })
  },
  scrolltolower:function(){

  },
  

  // 属性选择
  onShow: function () {
    
  },


  /**
   * Sku核心算法
   * 根据所有出当前类别之外的选择 判断按钮的enable ？ false or true
   */
  onData: function () {
    var attrListIn = this.data.attrList;
    // console.log(this.data.attrList, "待扫描 列表清单");
    // console.log(this.data.skuBeanList, "待扫描 库存清单");
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
              console.log(attrsOtherSelect[j].attributeId, goodAttrBean.attributeId, attrsOtherSelect[j].id, goodAttrBean.attributeValId)
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
    console.log(haveSkuBean, "存在于库存清单");

    for (var iox = 0; iox < canGetInfo.length; iox++) {
      canGetInfoLog += canGetInfo[iox].attributeValue + " ";
    }

    if (haveSkuBean.length != 0) {
      //选中
      var itemData = that.data.itemData;
      itemData.image = haveSkuBean[0].imgurl;
      console.log(that.data.paytype, haveSkuBean[0].member_price);
      if (that.data.paytype == 'group'){
        itemData.member_price = haveSkuBean[0].member_price;
      }else{
        itemData.market_price = haveSkuBean[0].price;
      }
      // itemData.member_price = haveSkuBean[0].price;
      itemData.num = haveSkuBean[0].count;

      var sizeid = haveSkuBean[0].cid;
      console.log(sizeid)
      that.setData({
        itemData: itemData,
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



  // 弹窗
  set_share: function (e) {
    var taht = this;
    var show_share = taht.data.show_share;
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    //定义点击的类型
    taht.animation = animation
    animation.translateY(300).step();
    taht.setData({
      animationData: animation.export()
    })
    if (e.currentTarget.dataset.status == 1) {
      taht.setData({
        show_share: true
      });
    }
    setTimeout(function () {
      animation.translateY(0).step()
      taht.setData({
        animationData: animation
      })
      if (e.currentTarget.dataset.status == 0) {
        taht.setData({
          show_share: false
        });
      }
    }.bind(this), 200);
  },
  //点击保存到相册
  baocun: function () {
    var that = this;
    console.log('用户点击保存');
    console.log(that.data.itemData.photo_x);
    wx.getSetting({
      success(res) {
        console.log(res)
        if (!res.authSetting['scope.writePhotosAlbum']) {
          wx.authorize({
            scope: 'scope.writePhotosAlbum',
            success() {
              console.log('授权成功')
            }
          })
        } else {
          console.log('qqqqq')
        }
      }
    })

    wx.downloadFile({
      url: that.data.imagePath,
      success: function (res) {
        var tempFilePath = res.tempFilePath;
        console.log(tempFilePath);
        wx.saveImageToPhotosAlbum({
          filePath: tempFilePath,
          success(res) {
            wx.showModal({
              content: '图片已保存到相册，赶紧晒一下吧~',
              showCancel: false,
              confirmText: '好的',
              confirmColor: '#333',
              success: function (res) {
                if (res.confirm) {
                  console.log('用户点击确定');
                  /* 该隐藏的隐藏 */
                  that.setData({
                    maskHidden: false
                  })
                }
              }, fail: function (res) {
                console.log(11111)
              }
            })
          }
        })

      }
    })

  },

  close_share: function (e) {
    var that = this;
    that.setData({
      maskHidden: false
    })
  }
  
})