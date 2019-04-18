var app = getApp();
Page({
  data: {
    current: 0,
    shopList: [],
    ptype: '',
    title: '',
    page: 1,
    catId: 0,
    cont: 1,
    remind: '加载中',//进来加载
    brandId: 0,
    heng:'xs',//控制显示方式
    shu:'bxs',
    xianshi:'icon-yduipaibanleixingliebiao',
    imageurl1: "../../images/mo.png",//默认排序图
    daindex1: 0,
    imageurl2: "../../images/mo.png",
    daindex2: 0,
    loading: false,//显示加载
    period: false,//显示无数据
    select:0,//选中
    sort: 0,// 1 asc 升序   0 desc 降序
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    var that = this;
    var objectId = that.data.objectId;
    var select = that.data.select;
    var sort = that.data.sort;
    wx.request({
      url: app.d.ceshiUrl + '&action=product&m=new_product',
      method: 'post',
      data: {
        page: 1,
        cid: 1,
        select: select,
        sort: sort,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var shoplist = res.data.pro;
        that.setData({
          shopList: shoplist,
          page:2,
          period: false
        })
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },
  /*  tab   */
  choosesort1: function (e) {
    var that =this;
    if (this.data.daindex1 == 0) {
      this.setData({
        imageurl1: "../../images/shang.png",
        daindex1: 1,
        imageurl2: "../../images/mo.png",
      })
    } else {
      this.setData({
        imageurl1: "../../images/xia.png",
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
  tabchage: function () {
    if (this.data.heng == 'xs'){
      this.setData({
        heng: 'bxs',
        shu: 'xs',
        xianshi: 'icon-yduipaibanleixingduicheng' 
      })
    }else{
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
  //上拉事件
  onReachBottom: function () {
    var that = this;
    setTimeout(function () {
      that.getMore();
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
      that.setData({
        loading: false,
      });
    }, 1500);
    that.setData({
      loading: true,
    });

  },
  //排序
  sort:function (){
    //页面初始化 options为页面跳转所带来的参数
    var that = this;
    var select = that.data.select;
    var sort = that.data.sort;
    var page = that.data.page;
    //ajax请求数据
    wx.request({
      url: app.d.ceshiUrl + '&action=product&m=new_product',
      method: 'post',
      data: {
        cid: 1,
        select: select,
        sort: sort,
        page: page
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var shoplist = res.data.pro;
        that.setData({
          shopList: shoplist
        })
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },
  //页面加载完成函数
  onReady: function () {
    var that = this;
    app.userlogin(1);
  },
  // 点击加载更多
  getMore: function (e) {
    var that = this;
    var page = that.data.page;
    var objectId = that.data.objectId;
    var select = that.data.select;
    var sort = that.data.sort;
    wx.request({
      url: app.d.ceshiUrl + '&action=product&m=new_product',
      method: 'post',
      data: {
        page: page,
        cid: 1,
        select: select,
        sort: sort,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var prolist = res.data.pro;
        if (prolist == '' || res.data.status == 0) {
          // wx.showToast({
          //   title: '没有更多数据！',
          //   icon: 'warn',
          //   duration: 2000
          // });
          that.setData({
            period: true
          });
          return false;
        }

        //成功返回设置数据
        that.setData({
          remind: '',
          page: page+1,
          shopList: that.data.shopList.concat(prolist)
        });

      },
      fail: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },
  onShow: function () {
    var cont = this.data.cont;
    var that = this;
    if (cont > 1) {
      that.onLoad();
    } else {
      that.setData({
        cont: cont + 1
      })
    }
    
  },
  onLoad: function (options) {
    this.setData({
      bgcolor: app.d.bgcolor,
    });
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor //页面标题为路由参数
    })
    //页面初始化 options为页面跳转所带来的参数
    this.getMore();


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
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  },
  // 获取滚动条当前位置
  onPageScroll: function (e) {
    if (e.scrollTop > 300) {
      this.setData({
        floorstatus: true
      });
    } else {
      this.setData({
        floorstatus: false
      });
    }
  },

  //回到顶部
  goTop: function (e) {  // 一键回到顶部
    if (wx.pageScrollTo) {
      wx.pageScrollTo({
        scrollTop: 0
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },
})
