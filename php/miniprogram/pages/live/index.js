// pages/live/index.js
var app = getApp()
Page({

  data: {
  },

  onLoad: function (options) {
    let roomId = 3 // 房间号
    let customParams = {}
    wx.navigateTo({
      url: `plugin-private://wx2b03c6e691cd7370/pages/live-player-plugin?room_id=${roomId}&custom_params=${encodeURIComponent(JSON.stringify(customParams))}`
    })
  },

  onReady: function () {
  },


  onShow: function () {
  },

  onHide: function () {
  },


  onUnload: function () {
  },

  onPullDownRefresh: function () {
  },

  onReachBottom: function () {
  },

  onShareAppMessage: function () {
  }
})