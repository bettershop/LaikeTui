var app = getApp()
Page({
  data: {
    current: 0,
    shopList: [],
    ptype:'',
    title:'',
    page: 1,
    catId:0,
    brandId: 0,
    keyword:null,
    pagefrom:'index',  //判断页面来源
    goodslist: [], //全部商品
    remind: '加载中',
    heng: 'xs',//控制显示方式
    shu: 'bxs',
    xianshi: 'icon-yduipaibanleixingliebiao',
    imageurl1: "../../images/mo.png",//默认排序图
    daindex1: 0,
    imageurl2: "../../images/mo.png",
    daindex2: 0,
    loading: false,//显示加载
    period: false,//显示无数据
    select: 0,//选中
    sort: 0,// 1 asc 升序   0 desc 降序
    types: '刷新', // 点击更多、下拉上啦刷新
    bg:''
  },
  onPullDownRefresh: function () {
    console.log(111)
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  //上拉
  onReachBottom: function () {
    var that = this;
    if (that.data.keyword){
      if(that.data.type != 1){
        that.getkeywordgood(that.data.keyword, that.data.types);
      }else{
        that.setData({
          period: true
        });
      }
    }else{
      that.getMore();
    }
  },
  onLoad: function (options) {
    var objectId = options.class;
    var title = options.title;
    //页面初始化 options为页面跳转所带来的参数
    if (objectId || title) {
      wx.setNavigationBarColor({
        frontColor: app.d.frontColor,//
        backgroundColor: app.d.bgcolor //页面标题为路由参数
      })
      //更改头部标题
      wx.setNavigationBarTitle({
        title: options.title,
        success: function () {
        },
      });
      this.setData({
        bgcolor: app.d.bgcolor,
        objectId: objectId,
        title: title,
      })
      this.listdetail(objectId);
    } else if (options.keyword) {
      var keyword = options.keyword;
      this.setData({
        pagefrom: 'keyword'
      });
      this.getkeywordgood(keyword); //如果存在关键词，则调用getkeywordgood方法
    }
  },
  listdetail: function (objectId,types = 0){
    var that = this;
    var select = that.data.select;
    var sort = that.data.sort;
    if (types == 0){
      var page = that.data.page
    }else{
      var page = that.data.page + 1;
    }
    //ajax请求数据
    wx.request({
      url: app.d.ceshiUrl + '&action=search&m=listdetail',
      method: 'post',
      data: {
        cid: objectId,
        select: select,
        sort: sort,
        page: page
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if (res.data.status) {
          var shoplist = res.data.pro;// 商品信息
          if (types == 0) {
            that.setData({
              shopList: shoplist,
              bg: res.data.bg
            })
          } else {
            that.setData({
              page: page,
              shopList: that.data.shopList.concat(shoplist)
            })
          }
        } else {
          // wx.showToast({
          //   title: '没有更多数据！',
          //   icon: 'none',
          //   duration: 2000
          // });
          that.setData({
            period: true
          });
          return false;
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          icon: 'none',
          duration: 2000
        });
      }
    })
  },
  //点击加载更多
  getMore: function (e) {
    var that = this;
    var objectId = that.data.objectId;
    that.listdetail(objectId,1);
  },
  //详情页跳转
  lookdetail: function (e) {
    var lookid = e.currentTarget.dataset;
    wx.navigateTo({
      url: "../index/detail?id=" + lookid.id
    })
  },
  switchSlider: function (e) {
    this.setData({
      current: e.target.dataset.index
    })
  },
  changeSlider: function (e) {
    this.setData({
      current: e.detail.current
    })
  },

  getkeymore: function (e) {
    var that = this;
    that.getkeywordgood(that.data.keyword, that.data.types);
  },
  // 根据关键词进入列表
  getkeywordgood: function (keyword, types) {  
    var that = this;
    if (types == '刷新'){
      that.data.page = that.data.page + 1;
    }else{
      that.data.page = that.data.page;
    }
    that.setData({
      bgcolor: app.d.bgcolor,
      keyword: keyword,
      pagefrom: 'keyword',
    });
    wx.setNavigationBarTitle({
      title: keyword,
      success: function () {
      },
    });
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor //页面标题为路由参数
    });
    wx.request({
      url: app.d.ceshiUrl + '&action=search&m=search',
      method: 'post',
      data: {
        keyword: keyword,
        num: that.data.page,
        select: that.data.select,
        sort: that.data.sort,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if (res.data.code == 1) {
          var goods = res.data.list;
          var type = res.data.type;
          if(type == 0){
            that.data.objectId = res.data.cid;
            var page = that.data.page;
            var shoplist = [];
            for (var i = 0; i <= page * 2 - 1; i++) {
              if (i == goods.length) {
                break;
              }
              shoplist[i] = goods[i];
            }
            if (types){
              that.setData({
                type: type,
                goodslist: goods,
                shopList: that.data.shopList.concat(shoplist)
              });
            }else{
              that.setData({
                type: type,
                shopList: goods
              });
            }
          }else{
            that.setData({
              type: type,
              shopList: goods
            });
          }
        } else {
          that.setData({
            period: true
          });
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },
  onShow: function () {
    // 页面显示
  },
  
  // 最新
  default: function (e) {
    var that = this;
    this.setData({
      select: 0,
      sort: 1,
      imageurl1: "../../images/mo.png",
      imageurl2: "../../images/mo.png",
      page: 1,
      period: false,
    })
    that.sort();
  },
  // 销量
  choosesort1: function (e) {
    var that = this;
    if (this.data.daindex1 == 0) {
      this.setData({
        imageurl1: "../../images/xia.png",
        daindex1: 1,
        imageurl2: "../../images/mo.png",
      })
    } else {
      this.setData({
        imageurl1: "../../images/shang.png",
        daindex1: 0,
        imageurl2: "../../images/mo.png",
      })
    }
    this.setData({
      select: 1,
      sort: that.data.daindex1,
      page: 1,
      period: false,
    });
    that.sort();
  },
  // 价格
  choosesort2: function (e) {
    var that = this;
    if (this.data.daindex2 == 0) {
      this.setData({
        imageurl2: "../../images/shang.png",
        daindex2: 1,
        imageurl1: "../../images/mo.png",
      })
    } else {
      this.setData({
        imageurl2: "../../images/xia.png",
        imageurl1: "../../images/mo.png",
        daindex2: 0
      })
    }
    this.setData({
      select: 2,
      sort: that.data.daindex2,
      page: 1,
      period: false,
    });
    that.sort();
  },
  
  //排序
  sort: function () {
    //页面初始化 options为页面跳转所带来的参数
    var that = this;
    var objectId = that.data.objectId;
    if (that.data.type == 1 || that.data.type == 0){
      that.getkeywordgood(that.data.keyword);
    }else{
      that.listdetail(objectId);
    }
  },
  tabchage: function () {
    if (this.data.heng == 'xs') {
      this.setData({
        heng: 'bxs',
        shu: 'xs',
        xianshi: 'icon-yduipaibanleixingduicheng'
      })
    } else {
      this.setData({
        heng: 'xs',
        shu: 'bxs',
        xianshi: 'icon-yduipaibanleixingliebiao'
      })
    }
  },
  showModal: function () {
    // 显示遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
      showModalStatus: true
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)
  },
  hideModal: function () {
    // 隐藏遮罩层
    var animation = wx.createAnimation({
    duration: 200,
    timingFunction: "linear",
    delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
    animationData: animation.export(),
    })
    setTimeout(function () {
    animation.translateY(0).step()
    this.setData({
      animationData: animation.export(),
      showModalStatus: false
    })
    }.bind(this), 200)
  },

  //页面加载完成函数
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
  },
})