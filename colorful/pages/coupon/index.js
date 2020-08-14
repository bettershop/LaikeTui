var t = getApp();

require("../../utils/common.js");

Page({
    data: {
        currentTab: 0,
        remind: "加载中",
        rtype: !0,
        pop: null
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500);
    },
    onReady: function() {
        this.pop = this.selectComponent("#pop");
    },
    onLoad: function(e) {
        wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), "receive" == e.type ? (this.requestaward(), this.setData({
            rtype: !0,
            bgcolor: t.d.bgcolor
        })) : (this.setData({
            rtype: !1,
            currentTab: parseInt(e.currentTab),
            bgcolor: t.d.bgcolor
        }), this.requestaward(), this.mycoupon());
    },
    bindChange: function(t) {
        var e = t.detail.current;
        this.setData({
            currentTab: e
        });
    },
    swichNav: function(t) {
        var e = this;
        if (e.data.currentTab === t.target.dataset.current) return !1;
        var a = t.target.dataset.current;
        e.setData({
            currentTab: parseInt(a),
            isStatus: t.target.dataset.otype
        }), 0 == a ? this.requestaward() : 1 == a && this.mycoupon();
    },
    requestaward: function() {
        var e = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=Coupon&m=index",
            method: "post",
            data: {
                openid: t.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (2 == t.data.status) wx.navigateBack({
                    delta: 1
                }); else {
                    var a = t.data.list;
                    e.setData({
                        list: a,
                        remind: ""
                    });
                }
            },
            error: function(t) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    mycoupon: function() {
        var e = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=Coupon&m=mycoupon",
            method: "post",
            data: {
                openid: t.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                var a = t.data.list;
                e.setData({
                    mylist: a
                });
            },
            error: function(t) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    receive: function(e) {
        if (t.userlogin(1)) this.pop.clickPup(this); else if ("领取" == e.currentTarget.dataset.point) {
            var a = this;
            wx.request({
                url: t.d.ceshiUrl + "&action=Coupon&m=receive",
                method: "post",
                data: {
                    openid: t.globalData.userInfo.openid,
                    id: e.target.dataset.id
                },
                header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function(t) {
                    1 == t.data.status ? (a.requestaward(), wx.showToast({
                        title: t.data.info,
                        duration: 2e3
                    })) : wx.showToast({
                        title: t.data.info,
                        duration: 2e3
                    });
                },
                error: function(t) {
                    wx.showToast({
                        title: "网络异常！",
                        duration: 2e3
                    });
                }
            });
        }
    },
    getvou: function(e) {
        wx.request({
            url: t.d.ceshiUrl + "&action=Coupon&m=immediate_use",
            method: "post",
            data: {
                id: e.target.dataset.id,
                openid: t.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                1 == t.data.status ? wx.switchTab({
                    url: "../index/indexs"
                }) : wx.redirectTo({
                    url: "../coupon/index?currentTab=1"
                });
            },
            error: function(t) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    jumpss: function () {
        wx.switchTab({
          url: "../index/index"
        });
      },
});