// pages/article/article.js
var app = getApp();
//引入这个插件，使html内容自动转换成wxml内容
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
      backgroundColor: app.d.bgcolor, //页面标题为路由参数
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
    that.storage();
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },
  // 获取文章详情
  y_detail: function (id) {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=envelope&m=index',
      method: 'post',
      data: {
        id: id,
      },
      header: { //请求头
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        var status = res.data.status;
        if (status == 1) {
          var article = res.data.article;
          var content = article['0'].content;
          //绑定页面数据，使用插件titstu
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
  // 转发分享
  onShareAppMessage: function (res) {
    var that = this;
    var id = that.data.article.Article_id;
    var Article_title = that.data.article.Article_title;
    if (res.from === 'button') {
      // 来自页面内转发按钮
      console.log(res.target)
    }
    return {
      title: Article_title,
      imageUrl:that.data.article.Article_imgurl,
      path: '/pages/index/index?id?p_openid=' + app.globalData.userInfo.openid,
      // path: '/pages/user/envelope?id=' + id + '&p_openid=' + app.globalData.userInfo.openid,
      success: function (res) {
        // 转发成功
        wx.request({
          url: app.d.ceshiUrl + '&action=envelope&m=share',
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
        // 转发失败
      }
    }
  },
  storage: function () {
    var that = this;
    wx.request({
      url: app.d.ceshiUrl + '&action=app&m=user',
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
    console.log(app.globalData.userInfo)
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
        console.log(res)
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
    console.log('用户点击保存');
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
          console.log('qqqqq')
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
                  console.log('用户点击确定');
                  /* 该隐藏的隐藏 */
                  that.setData({
                    maskHidden: false
                  })
                }
              }, fail: function (res) {
                console.log(11111)
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
    // 路径和 图片的数组
    var arr = [current];
    // 图片预览函数
    wx.previewImage({
      current: current, // 当前显示图片的http链接  
      urls: arr, // 需要预览的图片http链接列表  
    })
  },
  preventTouchMove: function (e) {

  },
})