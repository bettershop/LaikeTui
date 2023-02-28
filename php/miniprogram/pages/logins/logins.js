var app = getApp();

const defaultAvatarUrl = '../../images/heads.png'

Page({

  data: {
    click: false, //是否显示弹窗内容
    option: false, //显示弹窗或关闭弹窗的操作动画
    logoimg: '',
    loadtitle:'',
    avatarUrl: defaultAvatarUrl,
    navatarUrl: '',
    nickname:'',
    thvm:{}
  },
  myCatchTouch: function () {
    return;
  },
  // 用户点击显示弹窗
  setCloses:function(){
    wx.showToast({
      title: '没有授权，不能进入小程序个人中心！',
      icon: 'none',
      duration: 2000
    })
    this.clickPup()
  },
  clickPup: function (vm) {
    this.setData({
      logoimg: app.globalData.logoimg,
      loadtitle: app.globalData.title,
      thvm: vm ? vm:''
    })
    let _that = this;
    if (!_that.data.click) {
      _that.setData({
        click: true,
      })
    }

    if (_that.data.option) {
      _that.setData({
        option: false,
      })

      // 关闭显示弹窗动画的内容，不设置的话会出现：点击任何地方都会出现弹窗，就不是指定位置点击出现弹窗了
      setTimeout(() => {
        _that.setData({
          click: false,
        })
      }, 500)


    } else {
      _that.setData({
        option: true
      })
    }
  },
  
  onChooseAvatar(e) {
    const { avatarUrl } = e.detail 
    this.setData({
      avatarUrl,
    })
    let that = this
    wx.uploadFile({
      url: app.d.laikeUrl+'&action=user&m=upload', 
      filePath: that.data.avatarUrl,
      name: 'file',	
      success(res) {
        that.data.navatarUrl = res.data;
        console.log(res.data);
      }
    })

  },

  getUserProfile(e) {
    let that = this
    let thatplus = this.data.thvm
    

    let index = app.d.laikeUrl .lastIndexOf("/")
    var url =app.d.laikeUrl .substring(0,index);
    var userInfo = {avatarUrl:'',nickName:'',gender:0};

    if(that.data.avatarUrl.length > 0){
      userInfo.avatarUrl = url+"/images/"+that.data.navatarUrl; 
    }
    userInfo.nickName = e.detail.value.nickname;
    if (userInfo.nickName.length == 0) {
      wx.showToast({
        title: '昵称不能为空!',
        icon: 'loading',
        duration: 1500
      })
      return
    } 

    this.getOP(userInfo)
    console.log(userInfo);
    wx.request({
          url: app.d.laikeUrl + '&action=user&m=material',
          method: 'post',
          data: {
            openid: app.globalData.userInfo.openid,
            nickName: userInfo.nickName,
            avatarUrl: userInfo.avatarUrl,
            gender: userInfo.gender
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            app.globalData.userlogin = true;
            wx.setStorageSync('userlogin', true);
            wx.setStorageSync('userInfo', userInfo);
            thatplus.onLoad()

          }
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
          url: app.d.laikeUrl + '&action=user&m=material',
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
    let that = this;
    wx.showLoading({
      title: '正在登入',
      success: 2000
    });

    console.log(e.detail.userInfo)
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

      that.setData({
        userlogin: false
      })

      that.getOP(e.detail.userInfo)
    } else {
      wx.showToast({
        title: '没有授权，不能执行该操作！',
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

    let that = this
    let thatplus = this.data.thvm
    app.getUserInfo('', '', res, function (){
      let userInfo = res;
      var user = app.globalData.userInfo;

      app.globalData.userInfo['avatarUrl'] = userInfo.avatarUrl; 
      app.globalData.userInfo['nickName'] = userInfo.nickName; 
      app.globalData.userInfo['gender'] = userInfo.gender; 
      wx.setStorageSync('userInfo', app.globalData.userInfo);

      var nickName = userInfo.nickName;
      var avatarUrl = userInfo.avatarUrl;
      var gender = userInfo.gender; 

      wx.request({
        url: app.d.laikeUrl + '&action=user&m=material',
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

          setTimeout(function () {
            that.clickPup()
            thatplus.onLoad()
          }, 1800);

        }
      })

    })

    

  }
})