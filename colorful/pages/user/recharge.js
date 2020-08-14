var e = getApp();

Page({
    data: {
        fmoney: 0,
        remind: "加载中"
    },
    onReady: function() {},
    bindblur: function(e) {
        var t = e.detail.value;
        t < 0 || isNaN(t) ? wx.showToast({
            title: "输入金额非法！",
            duration: 2e3
        }) : this.setData({
            fmoney: t
        });
    },
    onLoad: function(t) {
        wx.setNavigationBarColor({
            frontColor: e.d.frontColor,
            backgroundColor: e.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), this.setData({
            bgcolor: e.d.bgcolor
        });
        var a = this;
        wx.request({
            url: e.d.ceshiUrl + "&action=recharge&m=index",
            method: "post",
            data: {
                openid: e.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (a.setData({
                    min_cz: e.data.min_cz
                }), 1 == e.data.status) {
                    var t = e.data.user;
                    a.setData({
                        money: t.money
                    }), setTimeout(function() {
                        a.setData({
                            remind: ""
                        });
                    }, 1e3);
                } else wx.showToast({
                    title: "非法操作！",
                    duration: 2e3
                });
            },
            error: function(e) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    recharge: function(t) {
        var a = t.detail.value.money, o = t.detail.value.min_cz, n = a - o;
        if (console.log(a, o, n), Number(a) < Number(o) || Number(n) < 0 || isNaN(a)) wx.showToast({
            title: "请填正确金额!",
            icon: "loading",
            duration: 1e3
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else {
            wx.request({
                url: e.d.ceshiUrl + "&action=recharge&m=recharge",
                method: "post",
                data: {
                    openid: e.globalData.userInfo.openid,
                    cmoney: t.detail.value.money
                },
                header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function(a) {
                    if (a.data.state) {
                        a.data.out_trade_no;
                        wx.requestPayment({
                            timeStamp: a.data.timeStamp,
                            nonceStr: a.data.nonceStr,
                            package: a.data.package,
                            signType: "MD5",
                            paySign: a.data.paySign,
                            success: function(a) {
                                wx.request({
                                    url: e.d.ceshiUrl + "&action=recharge&m=cz",
                                    method: "post",
                                    data: {
                                        openid: e.globalData.userInfo.openid,
                                        cmoney: t.detail.value.money
                                    },
                                    header: {
                                        "Content-Type": "application/x-www-form-urlencoded"
                                    },
                                    success: function(e) {
                                        wx.showModal({
                                            content: "充值成功！",
                                            showCancel: !1,
                                            confirmText: "确定",
                                            success: function(e) {
                                                wx.navigateBack({
                                                    delta: 2
                                                });
                                            }
                                        });
                                    }
                                });
                            },
                            fail: function(e) {
                                wx.showModal({
                                    content: "取消充值！",
                                    showCancel: !1,
                                    confirmText: "确定"
                                });
                            }
                        });
                    } else wx.showModal({
                        content: a.data.text,
                        showCancel: !1,
                        confirmText: "确定"
                    });
                },
                fail: function() {
                    wx.showModal({
                        content: "充值失败！",
                        showCancel: !1,
                        confirmText: "确定"
                    });
                }
            });
        }
    }
});