var app = getApp();
function getNowFormatDate() {
  var date = new Date();
  var seperator1 = "-";
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  var strDate = date.getDate();
  if (month >= 1 && month <= 9) {
    month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
    strDate = "0" + strDate;
  }
  var currentdate = year + seperator1 + month + seperator1 + strDate;
  return currentdate;
};
Page({
  /**
   * 页面的初始数据
   */
  data: {
    inp_money: 0,
    iv: '',
    encryptedData: '',
    islogin: false,
    remind: '加载中',
    bank_name: '',
    binding:false,
    multiIndex: [0, 0, 0],
    date: '2016-09-01',
    time: '12:01',
    region: ['湖南省', '长沙市', '岳麓区'],
    customItem: '全部',
    items: [
      { name: '1', value: '男', checked: true },
      { name: '2', value: '女', checked: false },
    ],
    sex:1
  },
  //页面加载完成函数
  onReady: function () {
    // var that = this;
    // setTimeout(function () {
      
    // }, 1000);
  },
  radioChange: function (e) {
    var items = this.data.items;
    for (var i = 0; i < items.length; i++) {
      if (items[i].name == e.detail.value) {
        items[i].checked = true;
      }else{
        items[i].checked = false;
      }
    }
    this.setData({
      items: items,
      sex: e.detail.value
    })

  },
  bindPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },
  bindMultiPickerChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value
    })
  },
  bindDateChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },
  bindTimeChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      time: e.detail.value
    })
  },
  bindRegionChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      region: e.detail.value
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */

  onLoad: function () {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,//
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    wx.checkSession({
      success: function (e) {
        console.log(e)
        console.log('session_key 未过期' + app.globalData.userInfo.session_key)
        //session_key 未过期，并且在本生命周期一直有效
        app.globalData.userInfo['session_key'] = app.globalData.userInfo.session_key;
      },
      fail: function () {
        // session_key 已经失效，需要重新执行登录流程
        wx.login({
          success: function (res) {
            var code = res.code;
            that.globalData.code = res.code;
            //取出本地存储用户信息，解决需要每次进入小程序弹框获取用户信息
            var userinfo = wx.getStorageSync('userInfo');
            that.globalData.userInfo = userinfo;
            app.getUserSessionKey(code, cb);
          }
        }); //重新登录
      }
    });
    var date = getNowFormatDate();
    console.log(date)
    this.setData({
      bgcolor: app.d.bgcolor,
      date: date
    });
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=user&m=perfect_index',
      method: 'post',
      data: {
        user_id: app.globalData.userInfo.user_id
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log(res)
        var status = res.data.status;
        if (status == 1) {
          var data = res.data.data;

          var items = that.data.items;
          for (var i = 0; i < items.length; i++) {
            if (items[i].name == data.sex) {
              items[i].checked = true;
            } else {
              items[i].checked = false;
            }
          }
          if (data.province){
            var region = [data.province, data.city, data.county]; 
          }else{
            var region = that.data.region;
          }
          if (data.birthday){
            var date = data.birthday
          }else{
            var date = that.data.date;
          }
          

          that.setData({
            name: data.name,
            mobile: data.mobile,
            binding: res.data.binding,
            items: items,
            region: region,
            date: date,
            wx_id: data.wechat_id,
            sex: data.sex,
            remind: ''
          });
        } 
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  renewal: function (e) {
    var that = this;
    that.setData({
      binding: !that.data.binding
    })
  },
  // 获取手机号码
  getPhoneNumber: function (e) {
    var res_d = e;
    var iv = e.detail.iv;
    var encryptedData = e.detail.encryptedData;
    var that = this;
    if (e.detail.errMsg == 'getPhoneNumber:fail user deny') {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '未授权',
        success: function (res) { }
      })
    } else {
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: '同意授权',
        success: function (res) {
          wx.request({
            url: app.d.ceshiUrl + '&action=user&m=secret_key',
            method: 'post',
            data: {
              encryptedData: encryptedData, // 加密数据
              iv: iv, // 加密算法
              sessionId: app.globalData.userInfo.session_key
            },
            header: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            success: function (res) {
              var status = res.data.status;
              if (status == 1) {
                that.setData({
                  islogin: true,
                  mobile: res.data.info
                })
              } else {
                app.getUserInfo(that, res_d);
              }
            },
            error: function (e) {
              wx.showToast({
                title: '网络异常！',
                duration: 2000
              });
            }
          })
        }
      })
    }
  },
  // 获取信息
  perfect: function (res) {
    console.log(res)
    var that = this;
    var region = this.data.region;
  
    for (var i = 0; i < region.length; i++) {
      if (region[i] == '全部'){
        wx.showToast({
          title: '请完善地址!',
          icon: 'none',
          duration: 1500
        })
        return false;
        break;
      }
    }
    var sex = that.data.sex;
    // for (var i = 0; i < that.data.items.length; i++) {
    //   if (that.data.items[i].checked == 'true'){
    //     sex = that.data.items[i].name;
    //   }
    // }
    var province = region[0], city = region[1], county = region[2];
    if (res.detail.value.name.length == 0) {
      wx.showToast({
        title: '姓名不得为空!',
        icon: 'loading',
        duration: 1500
      })
      setTimeout(function () {
        wx.hideToast()
      }, 2000)
    }else {
     
      wx.request({
        url: app.d.ceshiUrl + '&action=user&m=perfect',
        method: 'post',
        data: {
          user_id: app.globalData.userInfo.user_id,
          name: res.detail.value.name,
          mobile: that.data.mobile,
          province: province,
          city: city,
          county: county,
          wx_id: res.detail.value.wx_id,
          sex: sex,
          date:that.data.date,
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          var status = res.data.status;
          if (status == 1) {
            wx.showToast({
              title: res.data.succ,
              icon: 'success',
              duration: 3000
            })
            
            // setTimeout(function () {
            //   wx.navigateBack({
            //     delta: 1
            //   })
            // }, 2500);
          } else {
            wx.showToast({
              title: res.data.err ? res.data.err : '非法操作！',
              icon: 'none',
              duration: 1500
            });
          }
            setTimeout(function () {
              wx.navigateBack({
                delta: 1
              })
            }, 2000);
        },
        error: function (e) {
          wx.showToast({
            title: '网络异常！',
            duration: 2000
          });
        }
      });
    }
  },
})