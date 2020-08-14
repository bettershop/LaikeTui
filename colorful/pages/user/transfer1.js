var e = getApp();

Page({
    data: {
        inp_money: 0,
        iv: "",
        encryptedData: "",
        islogin: !1,
        remind: "加载中",
        bank_name: ""
    },
    onReady: function() {
        var e = this;
        setTimeout(function() {
            e.setData({
                remind: ""
            });
        }, 1e3);
    },
    onLoad: function(o) {
        console.log(o), console.log(11111111), wx.setNavigationBarColor({
            frontColor: e.d.frontColor,
            backgroundColor: e.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), wx.checkSession({
            success: function(o) {
                console.log(o), console.log("session_key 未过期" + e.globalData.userInfo.session_key), 
                e.globalData.userInfo.session_key = e.globalData.userInfo.session_key;
            },
            fail: function() {
                wx.login({
                    success: function(o) {
                        var a = o.code;
                        t.globalData.code = o.code;
                        var n = wx.getStorageSync("userInfo");
                        t.globalData.userInfo = n, e.getUserSessionKey(a, cb);
                    }
                });
            }
        }), this.setData({
            bgcolor: e.d.bf_color
        });
        var t = this, a = o.user_id, n = o.user_id.substring(4), s = n.replace(/\b(0+)/gi, "");
        a = 0 == n ? "user0" : "user" + s, wx.request({
            url: e.d.ceshiUrl + "&action=user&m=selectuser",
            method: "post",
            data: {
                user_id: a,
                openid: e.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (1 == e.data.status) {
                    var o = e.data.user.wx_name, a = e.data.user.headimgurl, n = e.data.user.money;
                    t.setData({
                        wx_name: o,
                        headimgurl: a,
                        money: n,
                        transfer_multiple: e.data.user.transfer_multiple
                    });
                } else wx.showToast({
                    title: e.data.err,
                    icon: "loading",
                    duration: 1500
                }), setTimeout(function() {
                    wx.redirectTo({
                        url: "../user/transfer"
                    });
                }, 2e3);
            },
            error: function(e) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    withdrawals1: function(o) {
        console.log(this);
        var t = this;
        console.log(6666);
        var a = Number(o.detail.value.money), n = t.options.user_id, s = Number(t.data.money);
        console.log(s), console.log(a), a > s && wx.showToast({
            title: "余额不足",
            duration: 2e3
        }), (a < 0 || "" == a) && wx.showToast({
            title: "正确填写转账金额",
            duration: 2e3
        }), s >= a && a > 0 && wx.request({
            url: e.d.ceshiUrl + "&action=user&m=transfer",
            method: "post",
            data: {
                user_id: n,
                openid: e.globalData.userInfo.openid,
                money: a
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                1 == e.data.status ? (wx.showToast({
                    title: e.data.err,
                    icon: "loading",
                    duration: 1500
                }), setTimeout(function() {
                    wx.redirectTo({
                        url: "../user/wallet"
                    });
                }, 2e3)) : wx.showToast({
                    title: e.data.err,
                    icon: "none",
                    duration: 1500
                });
            },
            error: function(e) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    }
});