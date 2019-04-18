var app = getApp();
Page({
  data: {
    url:'#',
    banner: [],
    indicatorDots: true, // 是否显示面板指示点
    autoplay: true, // 是否自动切换
    interval: 5000, // 自动切换时间间隔
    duration: 1000, // 滑动动画时长
    circular: true, // 是否采用衔接滑动
    scrollLeft: 0, //tab标题的滚动条位置
    news_list: [],
    current: 0,//当前选中的Tab项
    proCat:[],
    page: 1,
    index: 1,
    cont:1,
    tabid:0,
    brand:[],
    remind: '加载中',
    // 滑动
    imgUrl: [],
    kbs:[],
    lastcat:[],
    course:[],
    showModal: false,
    is_sign: false,
    loading: false,
    signimg:'' //签到图片
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
  //上拉事件
  onReachBottom: function () {
    var that = this;
    setTimeout(function () {
      // that.getMore();
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
      that.setData({
        loading: false,
      });
      wx.showToast({
        title: '没有更多了！',
        icon:'none'
      })
    }, 1800);
    that.setData({
      loading: true,
    });

  },
  /**
   * 内容区域swiper的切换事件
   */
  contentChange: function (e) {
    this.setData({
      current: e.detail.current
    })
  },
  onShow: function () {
    var cont = this.data.cont;
    var that = this;
    if (cont > 1){
      that.onLoad();
    }else{
      that.setData({
        cont: cont+1
      })
    }
  },
  onReady: function () {
    var that = this;
    setTimeout(function () {
      that.setData({
        remind: ''
      });
    }, 1000);
  },
  onLoad: function (e) {
    var that = this;
    //背景颜色设置
    wx.request({
      url: app.d.ceshiUrl + '&action=Index&m=draw',
      method: 'post',
      data: {},
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var choujiang = res.data.r01;   //抽奖产品
        // var banner = res.data.banner;
        that.setData({
           choujiang: choujiang,
           banner: res.data.banner ? res.data.banner:'',
        });
        wx.setNavigationBarColor({
          frontColor: app.d.frontColor,//
          backgroundColor: app.d.bgcolor //页面标题为路由参数
        });
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
  
  go: function () {
    this.setData({
      showModal: false
    })
  },

  //算出当天的起始时间
  issetSign: function () {
    var that = this
    var time = new Date();
    var datetime = time.getTime();
    var Hours = time.getHours();
    var Minutes = time.getMinutes();
    var Seconds = time.getSeconds();
    var Milliseconds = time.getMilliseconds();
    var todaystart = datetime - Hours * 60 * 60 * 1000 - Minutes * 60 * 1000 - Seconds * 1000 - Milliseconds;
    return todaystart;
  },

  navigate: function (){
    wx.navigateTo({
      url: '../sign_in/sign_in',
    })
  }

});