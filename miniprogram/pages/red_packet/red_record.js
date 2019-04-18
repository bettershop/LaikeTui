

var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    show_share: false,
    maskHidden: false,
    remind: true,
    showModalStatus: true,
    flag: false,
    fhb:false,
    text: '你来迟了，红包已领完了！'
  },
  home:function(){
    wx.switchTab({
      url: '../index/index'
    })
  },
  // 弹窗
  setModalStatus: function (e) {
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    this.animation = animation
    animation.rotateY(180).step();
    animation.rotateY(0).step();
    this.setData({
      animationData: animation.export()
    })
    this.audioCtx.play();
  },
  //下拉刷新
  onPullDownRefresh: function () {
    console.log(6666666666);
    var that = this;
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    that.Load_data();
  },
  //页面加载完成函数
  onReady: function () {
    this.audioCtx = wx.createAudioContext('myAudio');
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      options: options
    });
    that.Load_data();
  },
  Load_data: function () {
    wx.setNavigationBarTitle({
      title: '领红包',
      success: function () {
      },
    });
    var that = this;
    var openid = app.globalData.userInfo.openid ? app.globalData.userInfo.openid : false;
    if (openid) {
        var backgroundColor = app.d.h_color;
      console.log(that.data.options.id)
      if (that.data.options.id && that.data.options.id !='undefined'){
        wx.request({//进入领红包页面
          // 显示头像，获取比值
          url: app.d.ceshiUrl + '&action=red_packet&m=red_packet_show',
          method: 'post',
          data: {
            openid: openid,
            id: that.data.options.id,
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            that.setData({
              r01: res.data.r_1,
              r02: res.data.r_101,
              money: res.data.money,
              num: res.data.num,
              status: res.data.status,
              headimgurl: app.globalData.userInfo.avatarUrl,
              openid: openid
            });

            if (res.data.status == 1) {
              wx.showToast({
                title: '红包已失效',
                icon: 'none',
                duration: 2000
              });
              that.setData({
                text: '红包已失效'
              });
            } else {
              setTimeout(function () {
                that.setData({
                  remind: false
                });
              }, 1000);
            }

          },
          fail: function (e) {
            wx.showToast({
              title: '网络异常！',
              duration: 2000
            });
          },
        })
      }else{
        wx.showToast({
          title: '此红包无效',
          icon: 'none',
          duration: 2000
        });

        setTimeout(function () {
          wx.switchTab({
            url: '../index/index'
          })
        }, 2000);
      }

    } else {
      setTimeout(function () {
        that.Load_data();
      }, 1000);
    }
  },

  red_open: function(){//拆红包
    var that = this;
    if (that.data.start) {
      return false;
    }
    that.setData({
      start: true,
    })
    var money = that.data.money;
    if(money == ''){
        var animation = wx.createAnimation({
          duration: 200,
          timingFunction: "linear",
          delay: 0
        });
        that.animation = animation
        animation.rotateY(180).step();
        animation.rotateY(0).step();
        that.setData({
          animationData: animation.export()
        })
        that.audioCtx.play();
        var max = 1000, min = 1;
        parseInt(Math.random() * (max - min + 1) + min, 10);
        var time = Math.floor(Math.random() * (max - min + 1) + min);
      setTimeout(function () {
            wx.request({
              url: app.d.ceshiUrl + '&action=red_packet&m=red_packet_open',
              method: 'post',
              data: {
                openid: app.globalData.userInfo.openid,
                id: that.options.id,
              },
              header: {
                'Content-Type': 'application/x-www-form-urlencoded'
              },
              success: function (res) {
                var status = res.data.status;
                if (status == '1') {
                  setTimeout(function () {
                    that.Load_data();
                  }.bind(this), 600)
                } else if (status == '3') {
                  wx.showToast({
                    title: '不能重复领取红包',
                    icon: 'none',
                    duration: 2000,
                  });
                  return false
                } else {
                  wx.showToast({
                    title: '你来迟了，红包已领完',
                    icon: 'none',
                    duration: 2000,
                  });
                  return false
                }
              },
              fail: function (e) {
                wx.showToast({
                  title: '网络异常！',
                  duration: 2000
                });
              },
            })
      }, time);

  }else{
      wx.showToast({
        title: '不能重复领取红包',
        icon: 'none',
        duration: 2000,
      });
      return false;
  }
  },

  // 弹窗
  set_share: function (e) {
    var taht = this;
    var show_share = taht.data.show_share;
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
   
    //定义点击的类型
    taht.animation = animation
    animation.translateY(300).step();
    taht.setData({
      animationData: animation.export()
    })
    if (e.currentTarget.dataset.status == 1) {
      taht.setData({
        show_share: true
      });
    }
    setTimeout(function () {
      animation.translateY(0).step()
      taht.setData({
        animationData: animation
      })
      if (e.currentTarget.dataset.status == 0) {
        taht.setData({
          show_share: false
        });
      }
    }.bind(this), 200);
  },
  // 弹窗
  set_share1: function (e) {

    var taht = this;
    var show_share = taht.data.show_share;
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });

    //定义点击的类型
    taht.animation = animation
    animation.translateY(300).step();
    taht.setData({
      animationData: animation.export()
    })
    if (e.currentTarget.dataset.status == 1) {
      taht.setData({
        show_share: true
      });
    }
    setTimeout(function () {
      animation.translateY(0).step()
      taht.setData({
        animationData: animation
      })
      if (e.currentTarget.dataset.status == 0) {
        taht.setData({
          show_share: false
        });
      }
    }.bind(this), 200);
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
      url: '&action=red_packet&m=product_share',
      data: {
        product_title: that.data.r01[0].remarks,
        scene: 'productId=' + that.options.id,
        path: 'pages/red_packet/red_record?id=' +that.options.id,
        id: app.globalData.userInfo.user_id,
        head: app.globalData.userInfo.avatarUrl,
        type: 2
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
  //分享微信好友
  onShareAppMessage: function (res) {
    var that = this;
    var ids = that.options.id;
    var title = that.data.r01[0].remarks;
    return {
      title: title,
      imageUrl: 'https://open.laiketui.com/a15a744a5ca77d41baa9d4f272f45dfd/LKT/images/red_packet_share.png',
      // imageUrl: that.data.itemData.photo_x,
      path: 'pages/red_packet/red_record?id=' + ids,
      success: function (res) {
        console.log('转发成功');
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
      fail: function (res) {
        console.log('转发失败')
      }
    }
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


  //点击保存到相册
  baocun: function () {
    var that = this;
    console.log('用户点击保存');
    // console.log(that.data.itemData.photo_x);
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

  close_share: function (e) {
    var that = this;
    that.setData({
      maskHidden: false
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    wx.setNavigationBarColor({
      frontColor: '#ffffff',//
      backgroundColor: app.d.h_color //页面标题为路由参数
    });
    // var that = this;
    //   console.log(that);
    // wx.request({//进入领红包页面
    //   // 显示头像，获取比值
    //   url: app.d.ceshiUrl + '&action=red_packet&m=red_packet_show',
    //   method: 'post',
    //   data: {
    //     openid: app.globalData.userInfo.openid,
    //     id: that.options.id,
    //   },
    //   header: {
    //     'Content-Type': 'application/x-www-form-urlencoded'
    //   },
    //   success: function (res) {
    //     that.setData({
    //       r01: res.data.r_1,
    //       r02: res.data.r_101,
    //       money: res.data.money,
    //       num: res.data.num,
    //       fhb: res.data.fhb
    //     });

    //   },
    //   fail: function (e) {
    //     wx.showToast({
    //       title: '网络异常！',
    //       duration: 2000
    //     });
    //   },
    // })
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },



  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  // onShareAppMessage: function () {
  
  // }
})