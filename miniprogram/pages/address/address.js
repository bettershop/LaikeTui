var app = getApp();
var t = 0;
var show = false;
var moveY = 200;
var index = [0, 0, 0];
var sheng = [];//省
var shi = [];//城市
var xian = [];//区县
Page({

  /**
   * 页面的初始数据
   */
  data: {
    sheng: sheng,
    shi: shi,
    xian: xian,
    value: [0, 0, 0],
    cartId: 0,
    arr:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    this.setData({
      bgcolor: app.d.bgcolor,
      cartId: options.cartId
    });
    this.AddressManagement();
  },
  aotuAddress: function () {
    var that = this;
    console.log(that.data.arr)
    wx.chooseAddress({
      success: function (res) {
        var user_name = res.userName;
        var mobile = res.telNumber;
        var address = res.detailInfo;
        that.setData({
          sheng: res.provinceName,
          shi: res.cityName,
          xian: res.countyName,
          province: res.provinceName,
          city: res.cityName,
          county: res.countyName,
          user_name: user_name,
          mobile: mobile,
          address: address,
        });
      }
    })
  },
  /**
   * 添加地址
   */
  AddressManagement: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=AddressManagement',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        if (res.data.status == 1) {
          var sheng = res.data.sheng;
          var shi = res.data.shi;
          var xian = res.data.xian;
          that.setData({
            sheng: sheng,
            shi: shi,
            xian: xian
          });
        } else {
          wx.showToast({
            title: '非法操作！',
            duration: 2000
          });
        }
      }
    })
  },
  // 点击选择城市
  translate: function (e) {
    if (t == 0) {
      moveY = 0;
      show = false;
      t = 1;
    } else {
      moveY = 200;
      show = true;
      t = 0;
    }
    var province = this.data.province ? this.data.province : this.data.sheng[0].G_CName, 
      city = this.data.city ? this.data.city : this.data.shi[0].G_CName, 
      county = this.data.county ? this.data.county : this.data.xian[0].G_CName;
    console.log(province ,city ,county);
    this.setData({
      province: province,
      city: city,
      county: county
    });
    animationEvents(this, moveY, show);
  },
  //隐藏弹窗浮层
  hiddenFloatView(e) {
    moveY = 200;
    show = true;
    t = 0;
    animationEvents(this, moveY, show);
  },
  //滑动事件
  bindChange: function (e) {
    var val = e.detail.value
    console.log(val);
    console.log(index);
    //判断滑动的是第几个column
    //若省份column做了滑动则定位到地级市和区县第一位
    if (index[0] != val[0]) {
      val[1] = 0;
      val[2] = 0;
      index[1] = 0;
      index[2] = 0;
      this.getCityArr(val[0]);//获取地级市数据
      this.getCountyInfo(val[0], val[1]);//获取区县数据
    } else {    //若省份column未做滑动，地级市做了滑动则定位区县第一位
      if (index[1] != val[1]) {
        val[2] = 0;
        index[2] = 0;
        this.getCountyInfo(val[0], val[1]);//获取区县数据
      }
    }
    index = val;
    this.Preservation(index);
  },
  // 根据省获取市
  getCityArr: function (count) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=getCityArr',
      data: {
        count: count,
      },
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        if (res.data.status == 1) {
          var shi = res.data.shi;
          that.setData({
            shi: shi
          });
        } else {
          wx.showToast({
            title: '非法操作！',
            duration: 2000
          });
        }
      }
    })
  },
  // 根据省市获取县
  getCountyInfo: function (count, column) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=getCountyInfo',
      data: {
        count: count,
        column: column,
      },
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        if (res.data.status == 1) {
          var xian = res.data.xian;
          that.setData({
            xian: xian
          });
        } else {
          wx.showToast({
            title: '非法操作！',
            duration: 2000
          });
        }
      }
    })
  },
  // 滑动事件结束
  Preservation: function (rew) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=Preservation',
      data: {
        sheng: rew[0],
        shi: rew[1],
        xuan: rew[2]
      },
      method: "POST",
      header: {
        'content-type': 'application/x-www-form-urlencoded' // 默认值
      },
      success: function (res) {
        if (res.data.status == 1) {
          var province = res.data.province;
          var city = res.data.city;
          var county = res.data.county;
          that.setData({
            province: province,
            city: city,
            county: county,
          });
        } else {
          wx.showToast({
            title: '非法操作！',
            duration: 2000
          });
        }
      }
    })
  },
  // 点击保存
  SaveAddress: function (e) {
    if (e.detail.value.user_name.length == 0) {
      wx.showToast({
        title: '联系人不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    } else if (e.detail.value.mobile.length == 0) {
      wx.showToast({
        title: '电话不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    } else if (e.detail.value.province.length == 0) {
      wx.showToast({
        title: '城市不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    } else {
      var cartId = this.data.cartId,
      mobile = e.detail.value.mobile;
      //预处理验证手机号码
      if (mobile.match(/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/)){
          wx.request({
            url: app.d.ceshiUrl + '&action=user&m=SaveAddress',
            data: {
              openid: app.globalData.userInfo.openid,
              user_name: e.detail.value.user_name,
              mobile: e.detail.value.mobile,
              province: e.detail.value.province,
              city: e.detail.value.city,
              county: e.detail.value.county,
              address: e.detail.value.address
            },
            method: "POST",
            header: {
              'content-type': 'application/x-www-form-urlencoded' // 默认值
            },
            success: function (res) {
              if (res.data.status == 0) {
                wx.showToast({
                  title: res.data.info,
                  icon: 'loading',
                  duration: 1500
                })
              } else {
                app.d.islogin = true;
                wx.showToast({
                  title: res.data.info,
                  icon: 'success',
                  duration: 1000
                });
                //成功后返回上一页面
                setTimeout(function () {
                  wx.navigateBack({
                    delta: 1
                  })
                }, 1500);
              } 
            },
            fail: function () {
              // fail
              wx.showToast({
                title: '网络异常！',
                duration: 2000
              });
            }
          })
      }else{
        wx.showToast({
          title: '手机号码格式错误,请重新输入!',
          icon: 'none',
          duration: 1000
        });
      }

    }
  }
})

//动画事件
function animationEvents(that, moveY, show) {
  that.animation = wx.createAnimation({
    transformOrigin: "50% 50%",
    duration: 400,
    timingFunction: "ease",
    delay: 0
  })
  that.animation.translateY(moveY + 'vh').step()
  that.setData({
    animation: that.animation.export(),
    show: show
  })
}