var app = getApp();
var WxParse = require('../../wxParse/wxParse.js');
Page({
  data: {
    images: {},
    titstu:false,
    maskHidden: false,
    show_share: false,
  },
  onLoad: function (options) {
    console.log(options)
    wx.setNavigationBarColor({
      frontColor: app.d.frontColor,
      backgroundColor: app.d.bgcolor, 
      animation: {
        duration: 400,
        timingFunc: 'easeIn'
      }
    })
    this.setData({
      bgcolor: app.d.bgcolor
    });
    var that = this;
    var id = options.id;
    if (options.p_openid != '') {
      app.globalData.userInfo['p_openid'] = options.p_openid;
    } else {
      app.globalData.userInfo['p_openid'] = '';
    }
    that.setData({
      id: id,
    });
    that.y_detail(id);
  },
  onPullDownRefresh: function () {
    
  },
  // 获取文章详情
  y_detail: function (id) {
    var that = this;
    wx.request({
      url: app.d.laikeUrl + '&action=envelope&m=index',
      method: 'post',
      data: {
        id: id,
      },
      header: { 
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          var article = res.data.article;
          var content = article['0'].content;
          WxParse.wxParse('content', 'html', content, that, 3);
          that.setData({
            article: article['0'],
            title:article['0'].Article_title,
          });
          
          if (article['0'].Article_title !='关于我们'){
            that.setData({
              titstu:true,
            });
          }
          //设置动态标题
          wx.setNavigationBarTitle({
            title: article['0'].Article_title
          });
        } else {
          //返回错误提示
          wx.showToast({
            title: res.data.err,
            duration: 2000,
          });
        }
      },
      error: function (e) {
        wx.showToast({
          title: '网络异常！',
          duration: 2000,
        });
      },
    });
  },
 
  onShareAppMessage: function (res) {
    var that = this;
    var id = that.data.article.Article_id;
    var Article_title = that.data.article.Article_title;
    if (res.from === 'button') {
      console.log(res.target)
    }
    return {
      title: Article_title,
      imageUrl:that.data.article.Article_imgurl,
      path: '/pages/index/index?id?p_openid=' + app.globalData.userInfo.openid,
      success: function (res) {
        wx.request({
          url: app.d.laikeUrl + '&action=envelope&m=share',
          method: 'post',
          data: {
            id: id,
            openid: app.globalData.userInfo.openid
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            wx.showToast({
              title: res.data.err,
              duration: 2000
            });
            if (res.data.info == 1) {
              wx.navigateTo({
                url: '../share/index?id=' + id + '&n=1',
              });
            }
          },
          fail: function (e) {
            wx.showToast({
              title: '网络异常！err:getsessionkeys',
              duration: 2000
            });
          },
        });
      },
      fail: function (res) {
        
      }
    }
  },
  storage: function () {
    var that = this;
    wx.request({
      url: app.d.laikeUrl + '&action=app&m=user',
      method: 'post',
      data: {
        nickName: app.globalData.userInfo.nickName,
        headimgurl: app.globalData.userInfo.avatarUrl,
        sex: app.globalData.userInfo.gender,
        openid: app.globalData.userInfo.openid,
        p_openid: app.globalData.userInfo.p_openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
       
      },
      fail: function (e) {
        
      },
    });
  },
  //分享朋友圈 查看保存图片
  user_share: function () {
    var that = this;
    wx.showToast({
      title: '图片生成中',
      icon: 'loading',
      duration: 1500,
    });
    
    app.request.wxRequest({
      url: '&action=getcode&m=product_share',
      data: {
        scene: 'id=' + that.data.id + '&p_openid=' + app.globalData.userInfo.openid,
        path: 'pages/article/article',
        id: app.globalData.userInfo.user_id,
        pid: that.data.id,
        type: 1
      },
      method: 'post',
      success: function (res) {
        that.setData({
          maskHidden: true,
          imagePath: res.url,
        });
      }
    })

    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    that.animation = animation;
    animation.translateY(300).step();
    that.setData({
      animationData: animation.export()
    })
    setTimeout(function () {
      animation.translateY(0).step()
      that.setData({
        animationData: animation,
        show_share: false
      })
    }.bind(that), 200)
  },
  close_share: function (e) {
    var that = this;
    that.setData({
      maskHidden: false
    })
  },
  //点击保存到相册
  baocun: function () {
    var that = this;
    wx.getSetting({
      success(res) {
        console.log(res)
        if (!res.authSetting['scope.writePhotosAlbum']) {
          wx.authorize({
            scope: 'scope.writePhotosAlbum',
            success() {
              console.log('授权成功')
            }
          })
        } else {
          console.log('error')
        }
      }
    })

    wx.downloadFile({
      url: that.data.imagePath,
      success: function (res) {
        var tempFilePath = res.tempFilePath;
        console.log(tempFilePath);
        wx.saveImageToPhotosAlbum({
          filePath: tempFilePath,
          success(res) {
            wx.showModal({
              content: '图片已保存到相册，赶紧晒一下吧~',
              showCancel: false,
              confirmText: '好的',
              confirmColor: '#333',
              success: function (res) {
                if (res.confirm) {
                  that.setData({
                    maskHidden: false
                  })
                }
              }, fail: function (res) {
                
              }
            })
          }
        })

      }
    })

  },
  //图片预览
  previewImage: function (e) {
    var current = e.target.dataset.src;
    
    var arr = [current];
    
    wx.previewImage({
      current: current, 
      urls: arr, 
    })
  },
  preventTouchMove: function (e) {

  },
})