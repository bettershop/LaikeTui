var e = getApp();

Page({
    data: {
        inp_money: 0,
        iv: "",
        encryptedData: "",
        islogin: !1,
        lai: "lai",
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
    onLoad: function(a) {
        wx.setNavigationBarColor({
            frontColor: e.d.frontColor,
            backgroundColor: e.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), wx.checkSession({
            success: function(a) {
                console.log(a), console.log("session_key 未过期" + e.globalData.userInfo.session_key), 
                e.globalData.userInfo.session_key = e.globalData.userInfo.session_key;
            },
            fail: function() {
                wx.login({
                    success: function(a) {
                        var t = a.code;
                        o.globalData.code = a.code;
                        var n = wx.getStorageSync("userInfo");
                        o.globalData.userInfo = n, e.getUserSessionKey(t, cb);
                    }
                });
            }
        }), this.setData({
            bgcolor: e.d.bgcolor
        });
        var o = this;
        wx.request({
            url: e.d.ceshiUrl + "&action=user&m=details",
            method: "post",
            data: {
                openid: e.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (1 == e.data.status) {
                    var a = e.data.user;
                    o.setData({
                        money: a.money,
                        min_amount: a.min_amount,
                        max_amount: a.max_amount,
                        multiple: a.multiple,
                        unit: a.unit,
                        Bank_name: a.Bank_name,
                        Cardholder: a.Cardholder,
                        Bank_card_number: a.Bank_card_number
                    });
                } else wx.showToast({
                    title: "非法操作！",
                    icon: "none",
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
    getPhoneNumber: function(a) {
        var o = a, t = a.detail.iv, n = a.detail.encryptedData, i = this;
        "getPhoneNumber:fail user deny" == a.detail.errMsg ? (console.log(111), wx.showModal({
            title: "提示",
            showCancel: !1,
            content: "未授权",
            success: function(e) {}
        })) : (console.log(222), wx.showModal({
            title: "提示",
            showCancel: !1,
            content: "同意授权",
            success: function(a) {
                wx.request({
                    url: e.d.ceshiUrl + "&action=user&m=secret_key",
                    method: "post",
                    data: {
                        encryptedData: n,
                        iv: t,
                        sessionId: e.globalData.userInfo.session_key
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(a) {
                        1 == a.data.status ? i.setData({
                            islogin: !0,
                            mobile: a.data.info
                        }) : e.getUserInfo(i, o);
                    },
                    error: function(e) {
                        wx.showToast({
                            title: "网络异常！",
                            duration: 2e3
                        });
                    }
                });
            }
        }));
    },
    withdrawals: function(a) {
        if (0 == a.detail.value.amoney.length) wx.showToast({
            title: "金额不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else if (0 == a.detail.value.Bank_name.length) wx.showToast({
            title: "银行名不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else if (0 == a.detail.value.Cardholder.length) wx.showToast({
            title: "持卡人不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else if (0 == a.detail.value.Bank_card_number.length) wx.showToast({
            title: "卡号不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else {
            var o = this;
            wx.request({
                url: e.d.ceshiUrl + "&action=user&m=withdrawals",
                method: "post",
                data: {
                    money: o.data.money,
                    min_amount: o.data.min_amount,
                    max_amount: o.data.max_amount,
                    amoney: a.detail.value.amoney,
                    Bank_name: a.detail.value.Bank_name,
                    Cardholder: a.detail.value.Cardholder,
                    Bank_card_number: a.detail.value.Bank_card_number,
                    openid: e.globalData.userInfo.openid,
                    mobile: o.data.mobile
                },
                header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function(e) {
                    1 == e.data.status ? (wx.showToast({
                        title: e.data.info,
                        icon: "success",
                        duration: 3e3
                    }), setTimeout(function() {
                        wx.navigateBack({
                            delta: 1
                        });
                    }, 2500)) : wx.showToast({
                        title: e.data.info,
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
    },
    verify_bank: function(a) {
        console.log(a);
        var o = this, t = a.detail.value;
        console.log(t), wx.request({
            url: e.d.ceshiUrl + "&action=user&m=verify_bank",
            method: "post",
            data: {
                Bank_card_number: t
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (1 == e.data.status) {
                    var a = e.data.bank_name;
                    o.setData({
                        Bank_name: a
                    }), console.log(e);
                } else wx.showToast({
                    title: e.data.err,
                    icon: "loading",
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