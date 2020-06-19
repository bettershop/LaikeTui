var WxSearch = require('../../wxSearch/wxSearch.js')
var app = getApp();
Page({
  data: {
    color:'',
    // 左侧点击类样式
    listHeight: '',
    cont: 1,
    remind: '加载中',
    curNav: 1,
    curIndex: 0,
    isFocus:false,
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.search();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  search: function (e) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=search&m=index',
      method: 'post',
      data: {
        keyword:'百货'
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        that.setData({
          cateItems: res.data.List,
          hotwords: res.data.hot,
          curNav: res.data.List[0].cate_id
        });
        that.onReady();
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    });
  },
  onShow: function () {
    // app.userlogin(true);
    var cont = this.data.cont;
    var that = this;
    
  },
  onLoad: function (options) {

    var that = this;
    that.setData({
      color:app.d.bgcolor,
    })
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    }),
    this.search();
  },
  //事件处理函数  
  switchRightTab: function (e) {
    // 获取item项的id，和数组的下标值  
    let id = e.target.dataset.id,
      index = parseInt(e.target.dataset.index);
    // 把点击到的某一项，设为当前index  
    this.setData({
      curNav: id,
      curIndex: index
    })
  },
  search_confirmType: function (e) {
    console.log('-----');
    var that = this, value = e.detail.value;
    this.setData({
      value: e.detail.value,
    });

    if (value != '') {
      WxSearch.wxSearchAddHisKey(that);
      wx.navigateTo({
        url: "../listdetail/listdetail?keyword=" + value
      })
    }
  },
  wxSearchFn: function (e) {
    console.log(e)
    var that = this
    WxSearch.wxSearchAddHisKey(that);
    setTimeout(function () {
      var value = that.data.wxSearchData.value;
      if (value){
        wx.navigateTo({
          url: "../listdetail/listdetail?keyword=" + value
        })
      }
    }, 500);
  },
  wxSearchInput: function (e) {
    console.log(e)
    var that = this
    console.log(that)
    WxSearch.wxSearchInput(e, that);
  },
  wxSerchFocus: function (e) {
    var that = this;
    that.setData({
      isFocus: false
    });
    WxSearch.wxSearchFocus(that);
  },
  wxSearchBlur: function (e) {
    var that = this
    WxSearch.wxSearchBlur(e, that);
  },
  wxSearchKeyTap: function (e) {
    var that = this
    WxSearch.wxSearchKeyTap(e, that);
  },
  wxSearchDeleteKey: function (e) {
    var that = this
    WxSearch.wxSearchDeleteKey(e, that);
  },
  wxSearchDeleteAll: function (e) {
    var that = this;
    WxSearch.wxSearchDeleteAll(that);
  },
  wxSearchTap: function (e) {
    var that = this
    WxSearch.wxSearchHiddenPancel(that);
  },
  // 生命周期函数--监听页面初次渲染完成
  onReady: function () {
    var that = this;
    that.setData({
      remind: ''
    });
    //初始化的时候渲染wxSearchdata
    WxSearch.init(that, 43, this.data.hotwords);
    WxSearch.initMindKeys(['laiketui.com', '微信小程序开发', '微信开发', '微信小程序']);
    // 定义右侧标题的 rpx 高度 和 px 高度
    var right_titleRpxHeight = 60;
    var right_titleHeight;
    // 定义右侧单个商品的 rpx 高度 和 px 高度
    var right_contentRpxHeight = 200;
    var right_contentHeight;
    // 定义左侧单个tab的 rpx 高度 和 px 高度
    var left_titleRpxHeight = 140;
    var left_titleHeight;
    //  获取可视区屏幕高度
    wx.getSystemInfo({
      success: function (res) {
        // percent 为当前设备1rpx对应的px值
        var percent = res.windowWidth / 1200;
        that.setData({
          right_titleHeight: Number(right_titleRpxHeight * percent),
          right_contentHeight: Number(right_contentRpxHeight * percent),
          left_titleHeight: Number(left_titleRpxHeight * percent)
        })
      }
    })
  },
})
  