var WxSearch = require('../../wxSearch/wxSearch.js')
var app = getApp();
Page({
  data: {
    color:'',
    listHeight: '',
    cont: 1,
    remind: '加载中',
    curNav: 1,
    curIndex: 0,
    isFocus:false,
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() 
    this.search();
    wx.hideNavigationBarLoading() 
    wx.stopPullDownRefresh() 
  },
  search: function (e) {
    var that = this;
    wx.request({
      url: app.d.laikeUrl + '&action=search&m=index',
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
        that.setData({
          remind: ''
        });
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
    
  },
  onLoad: function (options) {
    var that = this;
    that.setData({
      color:app.d.bgcolor,
    })
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, 
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
    var that = this
    WxSearch.wxSearchAddHisKey(that);
    var value = that.data.wxSearchData.value;
    if (value){
        wx.navigateTo({
          url: "../listdetail/listdetail?keyword=" + value
        })
    }
  },
  wxSearchInput: function (e) {
    var that = this
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

  onReady: function () {
    var that = this;    
    WxSearch.init(that, 43, this.data.hotwords);
    WxSearch.initMindKeys(['laiketui.com', '微信小程序开发', '微信开发', '微信小程序']);
    var right_titleRpxHeight = 60;
    var right_titleHeight;
    var right_contentRpxHeight = 200;
    var right_contentHeight;
    var left_titleRpxHeight = 140;
    var left_titleHeight;
    wx.getSystemInfo({
      success: function (res) {
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
  