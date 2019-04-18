var app = getApp();
var zi = 0;
var cont_time=0;//首页tab点击
Page({
  data: {
    inforList: [],//公告
    banner: [],
    indicatorDots: true, // 是否显示面板指示点
    autoplay: true, // 是否自动切换
    interval: 5000, // 自动切换时间间隔
    duration: 1000, // 滑动动画时长
    circular: true, // 是否采用衔接滑动
    scrollLeft: 0, //tab标题的滚动条位置
    current: 0,//当前选中的Tab项
    current1:0,
    page: 1,
    index: 1,
    cont:1,
    tabid:0,
    loading:false,
    remind: '加载中',
    showModal: false,
    plug: [],
    timestamp:0,
    searchView:false,
    images:{},
    zjList: {},
    zjList_box:false,
  },
  //下拉事件
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1800);
    this.onLoad();
  },
  current1Change:function(e){
    this.setData({
      current1: e.detail.current,
    })
  },
  imgW: function (e) {
    var $width = e.detail.width,    //获取图片真实宽度
      $height = e.detail.height,
      ratio = $width / $height;    //图片的真实宽高比例
    var viewWidth = 718,           //设置图片显示宽度，左右留有16rpx边距
      viewHeight = 718 / ratio;    //计算的高度值
    var image = this.data.images;
    //将图片的datadata-index作为image对象的key,然后存储图片的宽高值
    image[e.target.dataset.index] = {
      width: viewWidth,
      height: viewHeight
    }
    this.setData({
      images: image
    })
  },
  getMore: function (e) {
    var that = this;
    var page = that.data.page;
    var index = that.data.tabid;
    var current = that.data.current;
    wx.request({
      url: app.d.ceshiUrl + '&action=Index&m=get_more',
      method: 'post',
      data: {
        page: page,
        index: index,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var prolist = res.data.prolist;

        wx.hideNavigationBarLoading() //完成停止加载
        wx.stopPullDownRefresh() //停止下拉刷新
        that.setData({
          loading: false,
        });

        if (prolist == '' || res.data.status == 0) {
          wx.showToast({
            title: '已为您全部加载完毕!',
            icon:'none',
            duration: 2000
          });
          return false;
        }else{
          var twoList = that.data.twoList;
          for (var i = 0; i < prolist.length; i++) {
            twoList[current].twodata.push(prolist[i])
          }
          that.setData({
            page: page + 1,
            twoList: twoList
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
  inputConfirm: function (e) {
    var that = this, value = e.detail.value;
      this.setData({
        value: e.detail.value,
      });

      if (value != '') {
        wx.navigateTo({
          url: "../listdetail/listdetail?keyword=" + value
        })
      }
  },
  searchView:function(e){
    console.log(e);
     this.setData({
       searchView:!this.data.searchView,
     });
  },
  inputBlur: function (e) {
    this.setData({
      value: e.detail.value,
    });
    this.searchView();
  },
  search_cancel: function (e) {
    var formId = e.detail.formId;
    var value = e.detail.value.search_value;
    console.log(this.data.value,e);
    if(value.length > 0){
      wx.navigateTo({
        url: "../listdetail/listdetail?keyword=" + value
      })
    }else{
      wx.showToast({
        title: '请输入关键词！',
        icon:'none',
        duration: 2000
      });
    }
    if (formId != 'the formId is a mock one') {
      var page = 'pages/index/index'
      app.request.wxRequest({
        url: '&action=product&m=save_formid',
        data: { from_id: formId, userid: app.globalData.userInfo.openid },
        method: 'post',
        success: function (res) {

        }
      })
    }
  },
  search: function () {
    var that = this, value = this.data.value;
    if (value != '') {
      wx.navigateTo({
        url: "../listdetail/listdetail?keyword=" + value
      })
    }
  },
  loadProductDetail: function () {
    var that = this;
    var openid = app.globalData.userInfo.openid ? app.globalData.userInfo.openid:false;
    if (openid) {
      wx.request({
        url: app.d.ceshiUrl + '&action=Index&m=index',
        method: 'post',
        data: {
          user_id: app.globalData.userInfo.user_id
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          app.userlogin(1);
          var banner = res.data.banner; // 轮播图
          var twoList = res.data.twoList;     //产品显示
          var bgcolor = res.data.bgcolor;     //产品显示
          var plug = res.data.plug;     //抽奖产品
          var title = res.data.title;
          app.d.bgcolor = bgcolor;
          var arr = Object.keys(twoList[0].distributor);
          var banner_num = Object.keys(banner); // 轮播图
          console.log(banner_num, 123333, banner.length)
          var notice = res.data.notice;
          that.setData({
            distributor: arr,
            inforList: notice,
            banner: banner,
            banner_num: banner_num,
            twoList: twoList,
            bgcolor: bgcolor,
            plug: plug,
            mch_name: title,
            logo: res.data.logo,
            djname: res.data.djname,
            zjList:res.data.list
          });

          wx.setNavigationBarColor({
            frontColor: app.d.frontColor,
            backgroundColor: app.d.bgcolor //页面标题为路由参数
          });
          
          wx.setNavigationBarTitle({
            title: title,
            success: function () {
            },
          });

          that.setData({
            remind: ''
          });
          
          console.log(res.data.list.length)
          if (res.data.list.length){
            setTimeout(function () {
              that.listnsg();
            }, 2000);
          }

        },
        fail: function (e) {
          wx.showToast({
            title: '网络异常！',
            duration: 2000
          });
        },
      })
    }else{
      setTimeout(function () {
        that.loadProductDetail();
      }, 1000);
    }
  },
  onHide: function () {
    clearTimeout();
  },
  listnsg:function(){
    var zjList = this.data.zjList;
    var that = this;
    //随机
    // var max = 10000, min = 1000;
    // parseInt(Math.random() * (max - min + 1) + min, 10);
    // var time = Math.floor(Math.random() * (max - min + 1) + min);
    var time = 1500;
    if (zjList[zi].type == 2){
      time = 6000;
    }else{
      time = 1500;
    }
    setTimeout(function () {
      that.setData({
        zjList_box: false
      })
      setTimeout(function () {
        that.listnsg();
      }, 2000);
    }, time);
    that.setData({
      headimgurl: zjList[zi].headimgurl,
      user_name: zjList[zi].user_name,
      pname: zjList[zi].name,
      zjList_box: true
    })
    zi++;
    if (zi == zjList.length){
      zi = 0;
    }
  },
  //上拉事件
  onReachBottom: function () {
    var that = this;
    that.setData({
      loading: true,
    });
    that.getMore();
    /*
    setTimeout(function () {
      that.getMore();
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
      that.setData({
        loading: false,
      });
    }, 1800);
    that.setData({
      loading: true,
    });
    */
  },
  obm: function () {
    var that = this;
    var timestamp = Date.parse(new Date());
    console.log(timestamp, that.data.timestamp)
    if (timestamp - that.data.timestamp > 2000){
      that.setData({
        timestamp: timestamp,
      });
      /*
      setTimeout(function () {
        that.getMore();
        wx.hideNavigationBarLoading() //完成停止加载
        wx.stopPullDownRefresh() //停止下拉刷新
        that.setData({
          loading: false,
        });
      }, 1800);
      that.setData({
        loading: true,
      });
      */
    }

  },
  /**
   * Tab的点击切换事件
   */
  tabItemClick: function (e) {
    //防止点击过快带来的闪屏问题
    var timestamp = Date.parse(new Date());
    timestamp = timestamp / 1000;
    var data = e.currentTarget.dataset;
    var that=this;
    if (cont_time){
      if (timestamp - cont_time >= 1){
        that.nextpic(data);
        cont_time = timestamp;
      }else{
        cont_time = timestamp;
      }
    }else{
      that.nextpic(data);
      cont_time = timestamp;
    } 
    that.checkCor(e); 
  },
  nextpic: function (data) {
    this.setData({
      current: data.pos,
      tabid: data.tabid
    });
  },
  //设置点击tab大于第七个是自动跳到后面
  checkCor: function (e) {
    if (this.data.current > 4) {
      this.setData({
        scrollLeft: 800
      })
    } else {
      this.setData({
        scrollLeft: 0
      })
    }
  },
  /**
   * 内容区域swiper的切换事件
   */
  contentChange: function (e) {
    var that = this;
    var id = e.detail.current;
    var tabid = that.data.twoList[id].id;
    this.setData({
      current: id,
      tabid: tabid,
      page: 1,
    })
    this.checkCor();
  },
  onShow: function () {
    this.login();
    var indexchase = app.d.indexchase;
    var that = this;
    if (indexchase){
      that.onLoad();
      app.d.indexchase = false;
    }
    
  },
  onReady: function () {

  },
  onLoad: function (e) {
    var that = this;
    //签到活动弹窗,勿删
    setTimeout(function(){
      that.setData({
        sign_image: app.globalData.userInfo.sign_image, // 签到图片
        sign_status: app.globalData.userInfo.sign_status // 是否签名
      })
      //如果用户今日已签到则不再显示
      if (app.globalData.userInfo.sign_status == 1){
        that.setData({
          showModal: true
        })
      }else{
        that.setData({
          showModal: false
        })
      }
    },5000);
    that.loadProductDetail();
    
  },

  preventTouchMove: function () {

  },
  
  go: function () {
    this.setData({
      showModal: false
    })
  },
  navigate_sign: function (){
    wx.navigateTo({
      url: '../sign_in/sign_in',
    })
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
        that.onLoad();
      }
    })
  },

  onShareAppMessage: function (res) {
    var that = this;
    var id = that.data.productId;
    var type1 = that.data.type1;
    var uname = app.globalData.userInfo.nickName ? app.globalData.userInfo.nickName : '' ;
    var title = uname + '邀请你来' + that.data.mch_name;
    var user_id = app.globalData.userInfo.user_id;
    if (res.from === 'button') {
      // 来自页面内转发按钮
    }
    return {
      title: title,
      imageUrl: that.data.logo,
      path: 'pages/index/index?userid='+ user_id,
      success: function (res) {
        console.log('转发成功');
      },
      fail: function (res) {
        console.log('转发失败')
      }
    }
  },
});