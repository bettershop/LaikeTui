var request = require('request.js');
var util = require('./utils/util.js');
App({
  d: {
    appId: "", // 小程序appid
    appKey: "", // 小程序密钥
    purchase: 0, //设置购物车刷新
    indexchase: false, //设置首页刷新
    frontColor: '#ffffff',
    one: false,
    bf_color: '#FF6347',
    h_color: '#FF63477',
    order: {},
    ceshiUrl: util.getUri(),
    titlee: '',
    bgcolor: '',

  },
  onLaunch: function (options) {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs);
    this.request = request;

  },
  //控制授权登入
  userlogin: function (page) {
    if (this.globalData.userInfo.openid == '' || !this.globalData.userInfo.openid) {
      if (page) {
        return true
      } else {
        wx.navigateTo({
          url: 'pages/login/login'
        })
      }
    } else {
      console.log(this.globalData.userInfo)
    }
  },

  onShow: function (options) {
    var referee_openid = options.query.userid ? options.query.userid : '';
    this.globalData.referee_openid = referee_openid;
  },

  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },

  getUserInfo: function (cb, stype, uesr, callback) {
    var that = this;
    if (this.d.one) {
      this.d.one = false;
      that.getUserInfo(cb, stype);
    } else {
      this.d.one = true;
      //调用登录接口  已更新登入接口  
      wx.login({
        success: function (res) {
          var code = res.code;
          that.globalData.code = res.code;
          that.getUserSessionKey(code, cb, uesr, callback);
        }
      });

    }
    //添加控制在同一秒执行同一个方法两次
  },

  getHomeData: function () {
    var that = this;
    wx.login({
      success: function (res) {
        that.globalData.code = res.code;
        var userinfo = wx.getStorageSync('userInfo');
        if (userinfo.nickName) {
          that.globalData.userInfo = userinfo;
        }
      }
    });
  },
  // 获取用户会话密钥
  getUserSessionKey: function (code, cb, stype, callback) {
    var that = this;
    wx.request({
      url: that.d.ceshiUrl + '&action=app&m=index',
      method: 'post',
      data: {
        code: code,
        nickName: stype.nickName,
        avatarUrl: stype.avatarUrl,
        gender: stype.gender,
        referee_openid: this.globalData.userInfo.referee_openid || ''
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var data = res.data;
        if (data.status == 0) {
          wx.showToast({
            title: data.err,
            duration: 2000
          });
          return false;
        }

        that.d.ceshiUrl = that.d.ceshiUrl + '&token=' + res.data.access_token; // 线上密钥
        that.d.localhost = that.d.localhost + '&token=' + res.data.access_token; // 本地密钥 
        that.globalData.userInfo['plug_ins'] = res.data.plug_ins; // 插件状态
        that.globalData.userInfo['coupon'] = res.data.coupon; // 优惠券状态
        that.globalData.userInfo['wallet'] = res.data.wallet; //  钱包状态
        that.globalData.userInfo['sign'] = res.data.sign; //  签到状态
        that.globalData.userInfo['sign_status'] = res.data.sign_status; // 是否签名
        that.globalData.userInfo['sign_image'] = res.data.sign_image; // 签到图片
        that.globalData.userInfo['user_id'] = res.data.user_id; // user_id
        that.globalData.userInfo['nickName'] = res.data.nickName;
        that.globalData.userInfo['avatarUrl'] = res.data.avatarUrl; //头像
        that.globalData.userInfo['session_key'] = res.data.user.session_key;
        that.globalData.userInfo['openid'] = res.data.user.openid;
        that.globalData.userInfo['nickName'] = res.data.user.nickName;
        that.globalData.userInfo['gender'] = res.data.user.gender;
        that.globalData.userInfo['company'] = res.data.user.company;
        that.globalData.userInfo['bgcolor'] = res.data.user.bgcolor;

        //修改缓存写入
        wx.setStorageSync('userInfo', that.globalData.userInfo);
        callback()
      },
      fail: function (e) {
        wx.showToast({
          title: '网络异常！err:getsessionkeys',
          duration: 2000
        });
      },
    });
  },

  getOrBindTelPhone: function (returnUrl) {
    var user = this.globalData.userInfo;
    if (!user.tel) {
      wx.navigateTo({
        url: 'pages/binding/binding'
      });
    }
  },
  globalData: {
    userInfo: {},
    userlogin: wx.getStorageSync('userlogin'),
  },
  redirect: function (url, param) {
    wx.navigateTo({
      url: '/pages/' + url + '?' + param
    })
  },
  showModal: function (that) {
    var animation = wx.createAnimation({
      duration: 200
    })
    animation.opacity(0).rotateX(-100).step();
    that.setData({
      animationData: animation.export()
    })
    setTimeout(function () {
      animation.opacity(1).rotateX(0).step();
      that.setData({
        animationData: animation
      });
    }.bind(that), 200)
  },
  showToast: function (that, title) {
    var toast = {};
    toast.toastTitle = title;
    that.setData({
      toast: toast
    })
    var animation = wx.createAnimation({
      duration: 100
    })
    animation.opacity(0).rotateY(-100).step();
    toast.toastStatus = true;
    toast.toastAnimationData = animation.export()
    that.setData({
      toast: toast
    })
    setTimeout(function () {
      animation.opacity(1).rotateY(0).step();
      toast.toastAnimationData = animation
      that.setData({
        toast: toast
      });
    }.bind(that), 100)
    // 定时器关闭 
    setTimeout(function () {
      toast.toastStatus = false
      that.setData({
        toast: toast
      });
    }.bind(that), 2000);
  }
});