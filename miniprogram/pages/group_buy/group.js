var app = getApp();
Page({
  data: {
    pop: null,
    current: 0,
    list: [],
    ptype: '',
    title: '',
    page: 1,
    catId: 0,
    cont: 1,
    remind: '加载中',//进来加载
    brandId: 0,
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
    titlee: '',
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    var that = this;
    var select = that.data.select;
    var sort = that.data.sort;
    wx.request({
      url: app.d.laikeUrl + '&action=pi&p=pintuan&c=groupbuy&m=grouphome',
      method: 'post',
      data: {
        cid: 1,
        select: select,
        sort: sort,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if (res.data.code == 1) {
          that.setData({
            list: res.data.list,
            remind: false
          })
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  /*  tab   */
  choosesort1: function (e) {
    var that = this;
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
  //上拉事件
  onReachBottom: function () {
    var that = this;
    if (that.data.list.length > 0) {
      that.getMore();
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
      that.setData({
        loading: true,
      });
    }
  },
  //排序
  sort: function () {
    var that = this;
    var select = that.data.select;
    var sort = that.data.sort;
    var page = that.data.page;
    wx.request({
      url: app.d.laikeUrl + '&action=pi&p=pintuan&c=groupbuy&m=grouphome',
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
        if (res.data.code == 1) {
          that.setData({
            list: res.data.list,
          })
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

  onReady: function () {
    this.pop = this.selectComponent("#pop")
  },

  getMore: function (e) {
    var that = this;
    var page = that.data.page + 1;
    var select = that.data.select;
    var sort = that.data.sort;
    wx.request({
      url: app.d.laikeUrl + '&action=pi&p=pintuan&c=groupbuy&m=grouphome',
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
        var prolist = res.data.list;
        if (prolist == '' || res.data.status == 0) {
          that.setData({
            period: true
          });
          return false;
        }
        if (res.data.code == 1) {
          //成功返回设置数据
          that.setData({
            page: page,
            list: that.data.list.concat(prolist),
            remind: false
          });
        } else {
          that.setData({
            period: true
          });
        }
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
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor 
    })
    //页面初始化 options为页面跳转所带来的参数
    var that = this;
    var select = that.data.select;
    var sort = that.data.sort;
    var page = that.data.page;
    
    wx.request({
      url: app.d.laikeUrl + '&action=pi&p=pintuan&c=groupbuy&m=grouphome',
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
        if (res.data.code == 1) {
          var list = res.data.list;
          var prolist = that.data.list;
          if (prolist.length > 1) {
            that.setData({
              page: page,
              list: list,
              remind: false
            });

          } else {
            that.setData({
              list: list,
              remind: false
            })

          }
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
  //详情页跳转
  lookdetail: function (e) {
    var lookid = e.currentTarget.dataset;
    wx.navigateTo({
      url: "../group_buy/detail?gid=" + lookid.id
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
  },
  onUnload: function () {
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
  goTop: function (e) {  
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
  jumpgo: function (event) {
    let url = event.currentTarget.dataset.id
    wx.navigateTo({
      url: url
    })
  }
})
