var request = require('request.js');
App({
  d: {
    appId:"", // 小程序appid
    appKey:"", // 小程序密钥
    purchase: 0,//设置购物车刷新
    indexchase: false,//设置首页刷新
    frontColor:'#ffffff',
    one:false,
    bf_color:'#FF6347',
    h_color:'#FF63477',
    order:{},
    ceshiUrl: 'http://192.168.0.104/test/LKT/index.php?module=api&software_name=3&edition=1.0', 
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
    // console.log(this.globalData.userInfo.nickName, this.globalData.userInfo.openid)
    if (this.globalData.userInfo.nickName == '' || this.globalData.userInfo.nickName == '简单的奇迹' || !this.globalData.userInfo.nickName || this.globalData.userInfo.openid == '' || !this.globalData.userInfo.openid) {
      if (page) {
        wx.navigateTo({
          url: '../login/login'
        })
      } else {
        wx.navigateTo({
          url: 'pages/login/login'
        })
      }
    }else{
      console.log(this.globalData.userInfo)
    }
  },
  onShow: function (options) {
      console.log(options)
      var referee_openid = options.query.userid ? options.query.userid:'';
      this.globalData.referee_openid = referee_openid;
      this.getUserInfo();
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    //模拟加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  getUserInfo: function (cb, stype){
    var that = this;

    if (this.d.one){
      this.d.one = false;
      setTimeout(function () {
        that.getUserInfo(cb, stype);
      }, 1500);
    }else{
      this.d.one = true;
      //调用登录接口  已更新登入接口  
      wx.login({
        success: function (res) {
          var code = res.code;
          that.globalData.code = res.code;
          //取出本地存储用户信息，解决需要每次进入小程序弹框获取用户信息
          var userinfo = wx.getStorageSync('userInfo');
          if (userinfo.length > 1) {
            that.globalData.userInfo = userinfo;
          }
          that.getUserSessionKey(code, cb);
        }
      });
    }
    //添加控制在同一秒执行同一个方法两次
  },
  // 获取用户会话密钥
  getUserSessionKey: function (code, cb, stype){
    var that = this;
    wx.request({
      url: that.d.ceshiUrl + '&action=app&m=index',
      method:'post',
      data: {
        code: code
      },
      header: {
        'Content-Type':  'application/x-www-form-urlencoded'
      },
      success: function (res) {   
        var data = res.data;
        var bgcolor = res.data.bgcolor;
        that.d.bgcolor = bgcolor;
        if(data.status==0){
          wx.showToast({
            title: data.err,
            duration: 2000
          });
          return false;
        }
        //设置openid 和 session_key
        that.globalData.userInfo = data;
        //存储用户信息
        wx.request({
          url: that.d.ceshiUrl + '&action=app&m=user',
          method: 'post',
          data: {
            nickName: that.globalData.userInfo.nickName,
            headimgurl: that.globalData.userInfo.avatarUrl,
            sex: that.globalData.userInfo.gender,
            openid: that.globalData.userInfo.openid,
            p_openid: that.globalData.referee_openid,
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
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
            that.globalData.userInfo['avatarUrl'] = res.data.avatarUrl;
            //修改缓存写入
            wx.setStorageSync('userInfo', that.globalData.userInfo);
            if (cb) {
              if (stype) {
                cb.getPhoneNumber(stype)
              } else {
                cb.loadProductDetail();
              }
            }
          },
          fail: function (e) {
           
          },
        });
      },
      fail:function(e){
        wx.showToast({
          title: '网络异常！err:getsessionkeys',
          duration: 2000
        });
      },
    });
  },
  
  getOrBindTelPhone:function(returnUrl){
    var user = this.globalData.userInfo;
    if(!user.tel){
      wx.navigateTo({
        url: 'pages/binding/binding'
      });
    }
  },
  globalData:{
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