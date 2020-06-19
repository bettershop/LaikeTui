// pages/return_goods/return_goods.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    itemList: [],
    itemList_text:'退货退款',
    tapIndex:1
  },
  actionSheetTap: function () {
    var that = this;
    wx.showActionSheet({
      itemList: that.data.itemList,
      success: function (e) {

        var arrayType = that.data.arrayType, itemList = that.data.itemList;

        for (var i = 0; i < arrayType.length; i++) {
          if (itemList[e.tapIndex] == arrayType[i].text){
            that.setData({
              tapIndex: arrayType[i].id,
            })
         }
        }
        that.setData({
          itemList_text: itemList[e.tapIndex]
        })

      }
    })
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
    var otype = options.type ? options.type:false;
    console.log(options)
    this.setData({
      bgcolor: app.d.bf_color,
      id: options.id,
      oid:options.oid,
      otype: otype
    });
    this.loadate();
  },
  loadate: function (e) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=order&m=return_type',
      method: 'post',
      data: {
        id: that.data.id,
        oid: that.data.oid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          var arrayType = res.data.arrayType, itemList = [];
          for (var i = 0; i < arrayType.length; i++) {
            itemList.push(arrayType[i].text);
          }

          that.setData({
            itemList: itemList,
            arrayType: res.data.arrayType,
            itemList_text: res.data.itemList_text,
            tapIndex: res.data.tapIndex
          });
        } else {
          wx.showToast({
            title: res.data.err,
            duration: 2000
          });
        }
      },
    });
  },
  remarkInput: function (e) {
    this.setData({
      remark: e.detail.value,
    });
  },
  submitReturnData: function(e){
    var remark = e.detail.value.remark;
    var that = this;
    var formId = e.detail.formId;

    if (remark.length < 1) {
      wx.showToast({
        title: '退款原因不能为空!',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    

    if (formId != 'the formId is a mock one') {
      app.request.wxRequest({
        url: '&action=product&m=save_formid',
        data: { from_id: formId, userid: app.globalData.userInfo.openid },
        method: 'post',
        success: function (res) {
          console.log(res)
        }
      })
    }

    wx.request({
      url: app.d.ceshiUrl + '&action=order&m=ReturnData',
      method: 'post',
      data: {
        id: that.data.id,
        oid: that.data.oid,
        otype: that.data.otype,
        re_type: that.data.tapIndex,
        back_remark: remark,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {       
        var status = res.data.status;
        if (status == 1) {
          wx.showToast({
            title: res.data.succ,
            success: 2000
          });
          wx.redirectTo({
            url: '/pages/return_goods/index?currentTab=0&otype=whole',
          });
        } else {
          wx.showToast({
            title: res.data.err,
            duration: 2000
          });
        }
      },
    });
  }
})