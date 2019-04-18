// pages/red_packet/red_packet.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    commodityAttr: [],
    attrValueList: [],
    show_share: false,
    maskHidden: false,
    indexchase: true,
    remind: true,
    show : 1,
  },
  //页面加载完成函数 
  onReady: function () {

  },
  //下拉刷新
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (e) {
    // console.log(app);
    var backgroundColor = app.d.h_color;
    wx.setNavigationBarColor({
      frontColor: '#ffffff',//
      backgroundColor: backgroundColor,//页面标题为路由参数
    });
    wx.setNavigationBarTitle({
      title: '发红包',
      success: function () {
      },
    });
    console.log(app)
    var that = this;
    wx.request({
      // 显示头像，获取比值
      url: app.d.ceshiUrl + '&action=red_packet&m=index',
      method: 'post',
      data: {
        openid: app.globalData.userInfo.openid,
      },
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        if (res.data.status == 2) {
          that.setData({
            remind: true
          });
          wx.showToast({
            title: res.data.err,
            icon: 'none',
            duration: 2000,
          });
          setTimeout(function () {
            wx.switchTab({
              url: '../user/user'
            })
          }, 2000);
        }
        if (res.data.status == 1) {
          that.setData({
            re: app.globalData.userInfo.avatarUrl,
            bizhi: res.data.bizhi,
            backgroundColor: backgroundColor
          });
          setTimeout(function () {
            that.setData({
              remind:false
            });
          }, 1500);
        }
        console.log(res);
        if (res.data.status == 0) {
          wx.showToast({
            title:'红包发太多了',
            duration: 2000
          });
          setTimeout(function () {
            wx.redirectTo({
              url: "../red_packet/red_envelopes?openid=" + app.globalData.userInfo.openid,
            })
          }, 2000)
          
        };
        wx.setNavigationBarColor({
          frontColor: '#ffffff',//
          backgroundColor: app.d.h_color //页面标题为路由参数
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
//
n1:function(e){
  var that = this;
  var status = that.data.show;//状态
  var type = e.target.dataset.type;

    if(status == 1){
          var bizhi = that.data.bizhi.bizhi;
          var money = that.data.money,
            num = that.data.num;
          if (type == 'money' && e.detail.value != '') {
            money = e.detail.value;
            that.setData({
              money: money,
            });
            if (money != '' && num == '') {
              that.setData({
                num: Math.ceil(money / bizhi * 100),
              });
            }
          } else if (type == 'num' && e.detail.value != '') {
            console.log(2);
            var num = e.detail.value;
            this.setData({
              num: num,
            });
            if (num != '' && money == '') {

              that.setData({
                money: num * bizhi / 100,
              });
            }
          } else if (type == 'remarks') {

            this.setData({
              remarks: e.detail.value,
            });
          }
    }else{
      if (type == 'money') {
        this.setData({
          money: e.detail.value,
        });
      } else if (type == 'num') {
        this.setData({
          num: e.detail.value,
        });
      } else if (type == 'remarks') {
        this.setData({
          remarks: e.detail.value,
        });
      }
    }



  
},
  tapName:function(e){//普通红包和拼团红包的点击事件
    var that = this;
    var status = e.currentTarget.dataset.status;//状态
    if (status == 1){
      that.setData({
       show : 2,
      });
    }
    if (status == 2) {
      that.setData({
        show: 1,
      });
    }
  },

  // 弹窗
  set_share: function (e) {

    var taht = this;
    var money = e.detail.value.money ? e.detail.value.money:'';
    var num = e.detail.value.num ? e.detail.value.num:'';
    var remarks = e.detail.value.remarks ? e.detail.value.remarks :'恭喜发财 大吉大利';
    var bizhi = e.detail.value.bizhi ? e.detail.value.bizhi : '';
    var show = e.detail.value.show ? e.detail.value.show :1;
    var num1 = Math.ceil(money /bizhi * 100);//最小红包
    var num2 = Math.floor(money * 10 );//最大红包数
    var show_share = taht.data.show_share;
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    });
    if (money==''){
      wx.showToast({
        title: '请填写红包金额',
        icon: 'none',
        duration: 2000,
      });
      return false;
    };
    if (money == 0) {
      wx.showToast({
        title: '红包金额要大于0',
        icon: 'none',
        duration: 2000,
      });
      return false;
    };
    if(num==''){
      wx.showToast({
        title: '请填写发红包的数量',
        icon: 'none',
        duration: 2000,
      });
      return false;
    }
    this.setData({
      remarks: remarks
    })
    if(show == 2){
      wx.request({
        url: app.d.ceshiUrl + '&action=red_packet&m=red_packet_send',
        method: 'post',
        data: {
          money: money, // 金额
          num: num, //数量
          remarks: remarks, //备注
          show: show,
          openid: app.globalData.userInfo.openid,//openid
        },
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res) {
          taht.setData({
            ids: res.data.re, // 返回id
          });
        },
      });
    }else{
      if (num2 >= num && num >= num1) {
        console.log(444)
        wx.request({
          url: app.d.ceshiUrl + '&action=red_packet&m=red_packet_send',
          method: 'post',
          data: {
            money: money, // 金额
            num: num, //数量
            remarks: remarks, //备注
            show: show,
            openid: app.globalData.userInfo.openid,//openid
          },
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            taht.setData({
              ids: res.data.re, // 返回id
            });
          },
        });
      } else if (num < num1) {
        wx.showToast({
          title: '根据你的金额，红包数量不小于' + num1 + '个哦',
          icon: 'none',
          duration: 2000,
        });
        return false;
      } else if (num > num2) {
        wx.showToast({
          title: '根据你的金额，红包数量不大于' + num2 + '个哦',
          icon: 'none',
          duration: 2000,
        });
        return false;
      }
    }
    

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
      url: '&action=getcode&m=product_share',
      data: {
        product_title: that.data.remarks,
        scene: 'productId=' + that.data.ids ,
        path: 'pages/red_packet/red_record?id=' + that.data.ids ,
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
    console.log(that);
    console.log(1122);
    var ids = that.data.ids;
    var title = that.data.remarks;
      return {
        title: title,
        imageUrl: 'https://open.laiketui.com/a15a744a5ca77d41baa9d4f272f45dfd/LKT/images/red_packet_share.png', 
        path: 'pages/red_packet/red_record?id=' + that.data.ids,
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
    console.log(2);
    this.setData({
      money: '',
      num: '',
      remarks: '',
    });
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
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
      var that = this;
    //背景颜色设置
   


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