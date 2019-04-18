const app = getApp();
var WxParse = require('../../wxParse/wxParse.js');
Page({
  data: {
    showModal: false,
    year: 0,
    month: 0,
    date: ['日', '一', '二', '三', '四', '五', '六'],
    dateArr: [],
    isToday: 0,
    isTodayWeek: false,
    todayIndex: 0,
    remind: '加载中',
    bgcolor:'',
  },
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
  },
  onLoad: function () {
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    });
    let now = new Date();
    let year = now.getFullYear(); // 获得年
    let month = now.getMonth() + 1; // 获得月份
    this.dateInit();
    this.setData({
      bgcolor: app.d.bgcolor,
      sign_status: app.globalData.userInfo.sign_status, // 是否签到
      year: year,
      month: month,
      isToday: '' + year + month + now.getDate()
    })
    
  },
  // 日期信息
  dateInit: function (setYear, setMonth) {
    // 全部时间的月份都是按0~11基准，显示月份才+1  
    let dateArr = []; // 需要遍历的日历数组数据  
    let arrLen = 0; // dateArr的数组长度  
    let now = setYear ? new Date(setYear, setMonth) : new Date();
    let year = setYear || now.getFullYear(); // 年
    let nextYear = 0;
    let month = setMonth || now.getMonth(); // 没有+1方便后面计算当月总天数  
    let nextMonth = (month + 1) > 11 ? 1 : (month + 1); // 月
    let startWeek = new Date('/' + year + '/' + (month + 1) + '/1').getDay(); // 目标月1号对应的星期  
    let dayNums = new Date(year, nextMonth, 0).getDate(); // 获取目标月有多少天  
    let obj = {};
    let num = 0;
    if (month + 1 > 11) {
      nextYear = year + 1;
      dayNums = new Date(nextYear, nextMonth, 0).getDate(); // 当前月的天数
    }
    arrLen = startWeek + dayNums;
    for (let i = 0; i < arrLen; i++) {
      if (i >= startWeek) {
        num = i - startWeek + 1;
        obj = {
          isToday: '' + year + (month + 1) + num,
          dateNum: num,
          weight: 5
        }
      } else {
        obj = {};
      }
      dateArr[i] = obj;
    }
    this.setData({
      dateArr: dateArr
    })
    this.sign(year, nextMonth)
    let nowDate = new Date();
    let nowYear = nowDate.getFullYear(); // 年
    let nowMonth = nowDate.getMonth() + 1; // 月
    let nowWeek = nowDate.getDay(); // 现在星期
    let getYear = setYear || nowYear; // 获得年
    let getMonth = setMonth >= 0 ? (setMonth + 1) : nowMonth; // 获得月份

    if (nowYear == getYear && nowMonth == getMonth) {
      this.setData({
        isTodayWeek: true,
        todayIndex: nowWeek
      })
    } else {
      this.setData({
        isTodayWeek: false,
        todayIndex: -1
      })
    }
  },
  // 上个月
  lastMonth: function () {
    //全部时间的月份都是按0~11基准，显示月份才+1  
    let year = this.data.month - 2 < 0 ? this.data.year - 1 : this.data.year;
    let month = this.data.month - 2 < 0 ? 11 : this.data.month - 2;
    this.setData({
      year: year,
      month: (month + 1)
    })
    this.dateInit(year, month);
  },
  // 下个月
  nextMonth: function () {
    //全部时间的月份都是按0~11基准，显示月份才+1  
    let year = this.data.month > 11 ? this.data.year + 1 : this.data.year;
    let month = this.data.month > 11 ? 0 : this.data.month;
    this.setData({
      year: year,
      month: (month + 1)
    })
    this.dateInit(year, month);
  },
  // 进入签到页面
  sign: function (year, month){
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=sign&m=sign',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
        year: year,
        month: month
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log(res)
        if (res.data.status == 1) {
          WxParse.wxParse('content', 'html', res.data.details, that, 5);
          var sign_time = res.data.sign_time;
          var dateArr = that.data.dateArr;
          for (var i = 0; i < dateArr.length;i++){
            dateArr[i]['sign_status'] = false; // 定义都没签到
            for (var j = 0; j < sign_time.length; j++){
              if (dateArr[i]['isToday'] === sign_time[j]){
                dateArr[i]['sign_status'] = true; // 已签到
              }
            }
          }
          var num = res.data.num;
          if (app.globalData.userInfo.sign_status == 0){
            num = num + 1;
          }
          that.setData({
            dateArr: dateArr, // 签到数组 
            imgurl: res.data.imgurl, // 签到图片
            num: num, // 连续签到天数
          });
        } else {
          WxParse.wxParse('content', 'html', res.data.details, that, 5);
          that.setData({
            num: res.data.num, // 连续签到天数
          });
          wx.showToast({
            title: res.data.err,
            icon:'none',
            duration: 2000
          });
        }
      },
      fail: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      },
    })
  },
  // 点击签到
  submit: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=sign&m=index',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if (res.data.status == 1){
          var sign_time = res.data.sign_time;
          var dateArr = that.data.dateArr;
          for (var i = 0; i < dateArr.length; i++) {
            dateArr[i]['sign_status'] = false; // 定义都没签到
            for (var j = 0; j < sign_time.length; j++) {
              if (dateArr[i]['isToday'] === sign_time[j]) {
                dateArr[i]['sign_status'] = true; // 已签到
              }
            }
          }
          that.setData({
            sign_score: res.data.sign_score, // 签到积分
            score: res.data.score, // 积分
            dateArr: dateArr, // 签到数组 
            imgurl: res.data.imgurl, // 签到图片
            showModal: true,
            sign_status: 0 // 是否签到
          });
          app.globalData.userInfo.sign_status = 0; // 修改签到状态(签到)
        }else{
          wx.showToast({
            title: res.data.err,
            icon: 'none',
            duration: 2000
          });
        }
      },
      fail: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      },
    })
  },

  preventTouchMove: function () {

  },
  guanbi: function () {
    this.setData({
      showModal: false
    });
    wx.reLaunch({
      url: '../index/index'
    })
  }
})  