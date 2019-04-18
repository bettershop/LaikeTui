//获取应用实例  
var app = getApp();
//引入这个插件，使html内容自动转换成wxml内容
var WxParse = require('../../wxParse/wxParse.js');
Page({
  data: {
    bannerApp: true,
    maskHidden:false,
    winWidth: 0,
    winHeight: 0,
    currentTab: 0, //tab切换  
    productId: 0,
    itemData: {},
    wsc: 'bxs',
    sc: 'bxs',
    paytype: 'buynow',
    sizeid: '',
    remind: true,
    bannerItem: [],
    select:[],//选中
    buynum: 1,
    // 产品图片轮播
    value: false,
    autoplay: true,
    interval: 5000,
    duration: 1000,
    xefl:true,//点击选择规格显示状态
    // 属性选择
    firstIndex: -1,
    //数据结构：以一组一组来进行设定  
    commodityAttr: [],
    attrValueList: [],
    show_share:false,
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
        product_img_path: that.data.itemData.photo_d,
        product_title: that.data.title,
        price: that.data.itemData.price_yh,
        yprice: that.data.itemData.price,
        scene: 'productId=' + that.data.productId + '&userid=' + app.globalData.userInfo.user_id,
        path: 'pages/product/detail', 
        id: app.globalData.userInfo.user_id,
        pid: that.data.productId,
        head: app.globalData.userInfo.avatarUrl,
        type:3
       },
      method: 'post',
      success: function (res) {
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
  //页面加载完成函数
  onReady: function () {

  },
  // 下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      // complete
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    this.loadProductDetail();
  },
  // 传值
  onLoad: function (option) {
    console.log(option)
    var scene = decodeURIComponent(option.scene);
    var that = this;
    if (scene != 'undefined' && scene.length > 1 && scene != '') {
      option = scene;
    }
    that.initNavHeight();
    if (option.referee_openid != '') {
      app.globalData.userInfo['referee_openid'] = option.referee_openid;
    } else {
      app.globalData.userInfo['referee_openid'] = '';
    }
    that.setData({
      productId: option.productId,
      userid: option.userid ? option.userid:false,
      choujiangid: option.choujiangid ? option.choujiangid : '',
      type1: option.type1 ? option.type1 : '',//判断是抽奖还是其他活动
      role: option.role ? option.role : '',
      size: option.size ? option.size : '',
      earn: option.earn ? option.earn : false,
    });
    //显示数据
    that.loadProductDetail();
  },
  // 属性选择
  onShow: function () {
    var that = this;

  },
  //接受formid
  getUserformid: function (e) {
    var formid = e.detail.formId;
    this.sendFormid(formid, 'kt1');
    this.setModalStatus(e);
  },
  //接受formid
  sendFormid: function (fromid, page) {
    var that = this
    app.request.wxRequest({
      url: '&action=draw&m=getFormid',
      data: { from_id: fromid, userid: app.globalData.userInfo.openid, page: page },
      method: 'post',
      success: function () {

      }
    })
  },
  // 商品详情数据获取 
  loadProductDetail: function () {
    var that = this;
    var choujiangid = that.data.choujiangid;
    var openid = app.globalData.userInfo.openid;
    console.log(app.globalData.userInfo,'openid')
    if (openid) {
      var bgcolor = app.d.bgcolor;
      wx.setNavigationBarColor({
        frontColor: app.d.frontColor,
        backgroundColor: bgcolor, // 页面标题为路由参数
        animation: {
          duration: 400,
          timingFunc: 'easeIn'
        }
      });
      console.log(that.data.userid)
      wx.request({
        url: app.d.ceshiUrl + '&action=product&m=index',
        method: 'post',
        data: {
          pro_id: that.data.productId,
          openid: openid,
          type1: that.data.type1,//判断是抽奖还是其他活动
          choujiangid: that.data.choujiangid,
          role: that.options.role ? that.options.role : '',
          size: that.data.size,
          userid: that.data.userid
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          app.userlogin(1);
          var status = res.data.status;
          var is_shou = res.data.type;
          if (status == 1) {
            var pro = res.data.pro;
            var content = pro.content;
            WxParse.wxParse('content', 'html', content, that, 5);
            that.setData({
              bgcolor: bgcolor,
              itemData: pro,
              kucun:pro.num,
              bannerItem: pro.img_arr,
              share: res.data.share,
              title: pro.name,
              is_zhekou: pro.is_zhekou,
              comments: res.data.comments,
              is_shou: res.data.type,
              collection_id: res.data.collection_id,
              choujiangid: that.data.choujiangid,
              role: that.data.role ? that.data.role : '',
              qj_price: res.data.qj_price,
              qj_yprice: res.data.qj_yprice,
              attrList: res.data.attrList,
              skuBeanList: res.data.skuBeanList,
              zhekou: res.data.zhekou != '' ? res.data.zhekou:false,
            });
            
            setTimeout(function () {
              that.setData({
                remind: false
              });
            }, 1000);
            //默认选中
            that.one();

          } else if (status == 3) {
            wx.showToast({
              title: res.data.err,
              duration: 2000,
            });
            wx.redirectTo({
              url: '../../pages/draw/draw'
            });
          } else {
            setTimeout(function () {
              wx.switchTab({
                url: '../index/index'
              })
            }, 2000);
            wx.showToast({
              title: res.data.err,
              duration: 2000,
            });
          }
          //判断是否收藏
          if (is_shou) {
            that.setData({
              wsc: 'bxs',
              sc: 'xs',
            })
          } else {
            that.setData({
              wsc: 'xs',
              sc: 'bxs',
            })
          }
        },
        error: function (e) {
          wx.showToast({
            title: '网络异常！',
            duration: 2000,
          });
        },
      });
    } else {
      //不存在openid  先获取 在回调  传递that
      setTimeout(function () {
        that.loadProductDetail();
      }, 1000);
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
    if(type){
      this.setData({
        xefl: false,
      })
    }else{
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

  // 加减
  changeNum: function (e) {
    var that = this;
    var num = that.data.itemData.num;
    if (e.target.dataset.alphaBeta == 0) {
      if (this.data.buynum <= 1) {
        buynum: 1
      } else {
        this.setData({
          buynum: this.data.buynum - 1
        })
      };
    } else {
      if (that.data.buynum < num){
        this.setData({
          buynum: this.data.buynum + 1
        })
      }

    };
  },
  //首次进去选中
  one:function(){
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
  //跳转index
  t_index: function () {
    wx.switchTab({
      url: '../index/index'
    })
  },
  //跳转cart
  go_cart: function () {
    wx.switchTab({
      url: '../cart/cart'
    })
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

    // console.log(haveSkuBean, "存在于库存清单");

    for (var iox = 0; iox < canGetInfo.length; iox++) {
      canGetInfoLog += canGetInfo[iox].attributeValue + " ";
    }

    if (haveSkuBean.length != 0) {
      //选中设置
      var itemData = that.data.itemData;
      itemData.photo_x = haveSkuBean[0].imgurl;
      itemData.price_yh = haveSkuBean[0].price;
      itemData.num = haveSkuBean[0].count;
      var choujiangid = that.data.choujiangid;
      var sizeid = haveSkuBean[0].cid;
      // console.log(sizeid)
      that.setData({
        itemData: itemData,
        sizeid: sizeid,
        choujiangid: choujiangid,
        value: canGetInfoLog
      });
    } else {
      // console.log('没选完')
      that.setData({
        sizeid: '',
        value:''
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


  /* 点击确定 */
  submit: function (e) {
    var that = this;
    var sizeid = that.data.sizeid;

    if (sizeid == '' || sizeid.length < 1) {
      wx.showToast({
        title: '请完善属性',
        icon: 'loading',
        duration: 1000
      })
    } else {
      var type = e.target.dataset.type;
      var sizeid = sizeid;
      that.addShopCart(e, sizeid)
    }
  },
  addShopCart: function (e, sizeid) { 
    //添加到购物车
    var that = this;
    var pro_type = e.target.dataset.type;
    if (pro_type != 'canjiapintuan') {
      wx.request({
        url: app.d.ceshiUrl + '&action=product&m=add_cart',
        method: 'post',
        data: {
          uid: app.globalData.userInfo.openid,
          pid: that.data.productId,
          num: that.data.buynum,
          sizeid: sizeid,
          pro_type: pro_type,
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          //设置购物车刷新
          app.d.purchase = 1;
          var data = res.data;
          if (data.status == 1) {
            var ptype = e.currentTarget.dataset.type;
            if (ptype == 'buynow') {
              wx.redirectTo({
                url: '../order/pay?cartId=' + data.cart_id + '&pid=' + that.data.productId,
              });
              return;
            } else {
              wx.showToast({
                title: '加入购物车成功',
                icon: 'success',
                duration: 2000
              });
              that.setData({
                showModalStatus: false
              });
            }
          } else {
            wx.showToast({
              icon: 'loading',
              title: data.err,
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
    } else {
      //抽奖弹框
      var productId = that.data.productId;
      var sizeid = that.data.sizeid;
      var choujiangid = that.data.choujiangid;
      var role = that.options.role;
      if (role == '' || role == 'undefined'){
        role='';
      }
      wx.redirectTo({
        url: '../order/pay?productId=' + productId + '&sizeid=' + sizeid + '&choujiangid=' + choujiangid + '&type1=' + 11 + '&role=' + role,
      });
      return;
    }
  },
  bindChange: function (e) {//滑动切换tab 
    var that = this;
    that.setData({ currentTab: e.detail.current });
  },
  initNavHeight: function () {////获取系统信息
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          winWidth: res.windowWidth,
          winHeight: res.windowHeight
        });
      }
    });
  },
  bannerClosed: function () {
    this.setData({
      bannerApp: false,
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

  
  onShareAppMessage: function (res) {
    var that = this;
    var id = that.data.productId;
    var type1 = that.data.type1;
    var uname = app.globalData.userInfo.nickName ? app.globalData.userInfo.nickName + '超值推荐 ' :'我发现一个好的东西 推荐给你们 ';
    var title = uname+that.data.title;
    var referee_openid = app.globalData.userInfo.user_id;
    
    if (type1 == 1) {
      var drawid = that.data.choujiangid;
      if (res.from === 'button') {
        // 来自页面内转发按 
      }
      return {
        title: title,
        imageUrl: that.data.itemData.photo_x,
        path: 'pages/product/detail?productId=' + id + '&type1=1&choujiangid=' + drawid,
        success: function (res) {
          console.log('转发成功');
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
        fail: function (res) {
          console.log('转发失败')
        }
      }
    } else {
      if (res.from === 'button') {
        // 来自页面内转发按钮
      }
      return {
        title: title,
        imageUrl: that.data.bannerItem[0],
        path: 'pages/product/detail?productId=' + id + '&userid=' + referee_openid,
        success: function (res) {
          console.log('转发成功');
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
        fail: function (res) {
          console.log('转发失败')
        }
      }
    }
  },
  // 添加到收藏
  addFavorites: function (e) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=addFavorites&m=index',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
        pid: that.data.productId,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {

        var data = res.data;
        if (data.status == 1) {
          wx.showToast({
            title: data.succ,
            duration: 2000
          });
          that.setData({
            wsc: 'bxs',
            sc: 'xs',
            collection_id: data.id
          })
          //变成已收藏，但是目前小程序可能不能改变图片，只能改样式
          that.data.itemData.isCollect = false;
        } else {
          wx.showToast({
            title: data.err,
            duration: 2000
          });
          that.setData({
            wsc: 'bxs',
            sc: 'xs',
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
  // 取消收藏
  delFavorites: function (e) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=addFavorites&m=removeFavorites',
      method: 'post',
      data: {
        id: that.data.collection_id,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var data = res.data;
        if (data.status == 1) {
          wx.showToast({
            title: data.succ,
            duration: 2000
          });
          //变成未收藏，但是目前小程序可能不能改变图片，只能改样式
          that.setData({
            wsc: 'xs',
            sc: 'bxs',
          })
        } else {
          wx.showToast({
            title: data.err,
            duration: 2000
          });
          that.setData({
            wsc: 'xs',
            sc: 'bxs',
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
  //图片预览
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
  preventTouchMove: function (e) {

  },
  add_fromid: function (e) {
    var that = this;
    var formId = e.detail.formId;
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    //定义点击的类型
    var type = e.detail.target.dataset.type ? e.detail.target.dataset.type : false;
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
    if (e.detail.target.dataset.status == 1) {
      this.setData({
        showModalStatus: true
      });
    }
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation
      })
      if (e.detail.target.dataset.status == 0) {
        this.setData({
          showModalStatus: false
        });
      }
    }.bind(this), 200);
    if (formId != 'the formId is a mock one'){
      var page = 'pages/product/detail'
      app.request.wxRequest({
        url: '&action=product&m=save_formid',
        data: { from_id: formId, userid: app.globalData.userInfo.openid},
        method: 'post',
        success: function (res) {

        }
      })
    }

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

    wx.getSetting({
      success(res) {
        if (!res.authSetting['scope.writePhotosAlbum']) {

          wx.authorize({
            scope: 'scope.writePhotosAlbum',
            success() {
              console.log('授权成功')
            },
            fail: function (res) {

              wx.openSetting({
                success: (res) => {

                   res.authSetting = {
                     "scope.userInfo": true,
                     "scope.userLocation": true,
                     "scope.writePhotosAlbum": true
                   }

                }
              })
            }
          })
        }else{

        }
      }
    })

    wx.downloadFile({
      url: that.data.imagePath,
      success: function (res) {
        var tempFilePath = res.tempFilePath;

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
});
