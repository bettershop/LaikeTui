var app = getApp();
var that;
Page({
  data: {
    windowHeight: 'auto',
    commentList: [],
    images: [],
    imageWidth: 0,
    remind: '加载中',
    addHide: 0,
  },

  onReady: function () {
  },
  onLoad: function (options) {
    that = this;
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          screenWidth: res.windowWidth,
          screenHeight: res.windowHeight,
          pixelRatio: res.pixelRatio,
          imageWidth: res.windowWidth / 4 - 10
        });
      }
    });
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, 
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    var orderId = options.ordersn;
    var user_id = app.globalData.userInfo.openid; 
    var pid = options.pid;
    var attribute_id = options.attribute_id; 
    if (pid) {
      pid = pid;
    } else {
      pid = '';
    }
    if (attribute_id) {
      attribute_id = attribute_id;
    } else {
      attribute_id = '';
    }
    wx.request({
      url: app.d.laikeUrl + '&action=product&m=comment',
      method: 'post',
      data: {
        order_id: orderId, 
        user_id: user_id, 
        pid: pid, 
        attribute_id: attribute_id, 
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          var commentList = res.data.commentList;
          if (commentList.length > 1) {
            for (var i = 0, len = commentList.length; i < len; i++) {
              commentList[i].commentType = 'GOOD';
              commentList[i].images = [];
              commentList[i].id = i;
              commentList[i].addHide = commentList[i].images.length;
            }
          } else {
            commentList[0].commentType = 'GOOD';
            commentList[0].images = '';
            commentList[0].id = 0;
            commentList[0].addHide = commentList[0].images.length;
          }
          that.setData({
            commentList: commentList,
            bgcolor: '#09bb07',
            orderId: orderId,
            remind: ''
          });
        } else {
          wx.showToast({
            title: '已经评论过了哦，亲!',
            duration: 2000
          });
          wx.navigateBack({
            delta: 2
          });
        }
      },
      fail: function () {
        wx.showToast({
          title: '网络异常！',
          duration: 2000
        });
      }
    })
  },
  onShow: function () {
  },
  chooseImage: function (e) {
    var id = e.target.id;
    var that = this;
    wx.chooseImage({
      count: 3, 
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        var commentList = that.data.commentList;
        var images = commentList[id].images;
        if (images.length > 2) {
          wx.showToast({
            title: '亲！最多上传3张哦！',
            icon: 'none',
            duration: 2000
          });
        } else {
          if (images) {
            images = images.concat(tempFilePaths);
          } else {
            images = tempFilePaths;
          }
          
          for (var i = 0, len = commentList.length; i < len; i++) {
            if (i == id) {
              commentList[id].images = images;
              commentList[id].addHide = commentList[id].images.length;
            }
          }

          that.setData({
            commentList: commentList,
            addHide: commentList[id].images.length
          });
        }
      }
    })
  },
  //删除图片
  delete: function (e) {
    var id = e.currentTarget.id;
    var index = e.currentTarget.dataset.index;
    var commentList = that.data.commentList;
    var images = commentList[id].images;
    images.splice(index, 1);
    commentList[id].images = images;
    commentList[id].addHide = images.length;
    that.setData({
      commentList: commentList,
    });
  },
  previewImage: function (e) {
    var id = e.currentTarget.id;
    var commentList = that.data.commentList;
    var images = commentList[id].images
    wx.previewImage({
      urls: images
    });
  },
  //保存评论内容
  setcon: function (e) {
    var commentList = this.data.commentList;
    var index = parseInt(e.currentTarget.dataset.index);
    commentList[index].content = e.detail.value;
    this.setData({
      commentList: commentList
    });
  },
  selectCommentType: function (e) {
    var commentList = this.data.commentList;
    var index = parseInt(e.currentTarget.dataset.index);
    commentList[index].commentType = e.currentTarget.dataset.type;
    this.setData({
      'commentList': commentList
    });
  },
  //保存图片
  saveimg: function (id, key) {
    var commentList = that.data.commentList;
    var images = commentList[key].images;
    var formData = { 'id': id };
    for (var i = 0, len = images.length; i < len; i++) {
      that.upload_file('&action=product&m=t_comment&type=file', images[i], 'imgFile', formData);
    }
  },
  submitComment: function (e) {
    var vm = this;
    var commentList = [];
    for (var i = 0, len = vm.data.commentList.length; i < len; i++) {
      commentList.push({
        commodityId: vm.data.commentList[i].commodityId, // 商品id
        images: vm.data.commentList[i].images, // 商品id
        content: e.detail.value['content_' + i], // 评论内容
        score: vm.data.commentList[i].commentType, // 评论类型(好评、中评、差评)
        size: vm.data.commentList[i].size, // 属性名称
        attribute_id: vm.data.commentList[i].sid, // 属性名称
        orderId: vm.data.orderId, // 订单号
        userid: app.globalData.userInfo.openid, // 微信id 
        pingid: vm.data.commentList[i].id // 第几条数据
      });
    }
    wx.request({
      url: app.d.laikeUrl + '&action=product&m=t_comment&type=json',
      method: 'post',
      data: {
        comments: commentList
      },
      header: {
        'content-type': 'application/json' 
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          var arrid = res.data.arrid;
          for (var i = 0, len = arrid.length; i < len; i++) {
            that.saveimg(arrid[i], i);
          }
          wx.showToast({
            title: res.data.succ,
            duration: 2000
          });
          wx.navigateBack({
            delta: 1
          });
        } else {
          wx.showToast({
            title: res.data.err,
            duration: 2000
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
  //上传文件
  upload_file: function (url, filePath, name, formData, success, fail) {
    wx.uploadFile({
      url: app.d.laikeUrl + url,
      filePath: filePath,
      name: name,
      header: {
        'content-type': 'multipart/form-data'
      }, // 设置请求的 header
      formData: formData, // HTTP 请求中其他额外的 form data
      success: function (res) {
        console.log(res);
      },
      fail: function (res) {
        console.log(res);
      }
    })
  },
})