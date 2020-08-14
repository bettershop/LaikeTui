function e() {
    var e = new Date(), t = e.getFullYear(), a = e.getMonth() + 1, o = e.getDate();
    return a >= 1 && a <= 9 && (a = "0" + a), o >= 0 && o <= 9 && (o = "0" + o), t + "-" + a + "-" + o;
}

var t = getApp();

Page({
    data: {
        inp_money: 0,
        iv: "",
        encryptedData: "",
        islogin: !1,
        remind: "加载中",
        bank_name: "",
        binding: !1,
        multiIndex: [ 0, 0, 0 ],
        date: "2016-09-01",
        time: "12:01",
        region: [ "湖南省", "长沙市", "岳麓区" ],
        customItem: "全部",
        items: [ {
            name: "1",
            value: "男",
            checked: !0
        }, {
            name: "2",
            value: "女",
            checked: !1
        } ],
        sex: 1
    },
    onReady: function() {},
    radioChange: function(e) {
        for (var t = this.data.items, a = 0; a < t.length; a++) t[a].name == e.detail.value ? t[a].checked = !0 : t[a].checked = !1;
        this.setData({
            items: t,
            sex: e.detail.value
        });
    },
    bindPickerChange: function(e) {
        console.log("picker发送选择改变，携带值为", e.detail.value), this.setData({
            index: e.detail.value
        });
    },
    bindMultiPickerChange: function(e) {
        console.log("picker发送选择改变，携带值为", e.detail.value), this.setData({
            multiIndex: e.detail.value
        });
    },
    bindDateChange: function(e) {
        console.log("picker发送选择改变，携带值为", e.detail.value), this.setData({
            date: e.detail.value
        });
    },
    bindTimeChange: function(e) {
        console.log("picker发送选择改变，携带值为", e.detail.value), this.setData({
            time: e.detail.value
        });
    },
    bindRegionChange: function(e) {
        console.log("picker发送选择改变，携带值为", e.detail.value), this.setData({
            region: e.detail.value
        });
    },
    onLoad: function() {
        wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), wx.checkSession({
            success: function(e) {
                console.log(e), console.log("session_key 未过期" + t.globalData.userInfo.session_key), 
                t.globalData.userInfo.session_key = t.globalData.userInfo.session_key;
            },
            fail: function() {
                wx.login({
                    success: function(e) {
                        var a = e.code;
                        o.globalData.code = e.code;
                        var n = wx.getStorageSync("userInfo");
                        o.globalData.userInfo = n, t.getUserSessionKey(a, cb);
                    }
                });
            }
        });
        var a = e();
        console.log(a), this.setData({
            bgcolor: t.d.bgcolor,
            date: a
        });
        var o = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=user&m=perfect_index",
            method: "post",
            data: {
                user_id: t.globalData.userInfo.user_id
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (console.log(e), 1 == e.data.status) {
                    for (var t = e.data.data, a = o.data.items, n = 0; n < a.length; n++) a[n].name == t.sex ? a[n].checked = !0 : a[n].checked = !1;
                    if (t.province) i = [ t.province, t.city, t.county ]; else var i = o.data.region;
                    if (t.birthday) s = t.birthday; else var s = o.data.date;
                    o.setData({
                        name: t.name,
                        mobile: t.mobile,
                        binding: e.data.binding,
                        items: a,
                        region: i,
                        date: s,
                        wx_id: t.wechat_id,
                        sex: t.sex,
                        remind: ""
                    });
                }
            },
            error: function(e) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    renewal: function(e) {
        var t = this;
        t.setData({
            binding: !t.data.binding
        });
    },
    getPhoneNumber: function(e) {
        var a = e, o = e.detail.iv, n = e.detail.encryptedData, i = this;
        "getPhoneNumber:fail user deny" == e.detail.errMsg ? wx.showModal({
            title: "提示",
            showCancel: !1,
            content: "未授权",
            success: function(e) {}
        }) : wx.showModal({
            title: "提示",
            showCancel: !1,
            content: "同意授权",
            success: function(e) {
                wx.request({
                    url: t.d.ceshiUrl + "&action=user&m=secret_key",
                    method: "post",
                    data: {
                        encryptedData: n,
                        iv: o,
                        sessionId: t.globalData.userInfo.session_key
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(e) {
                        1 == e.data.status ? i.setData({
                            islogin: !0,
                            mobile: e.data.info
                        }) : t.getUserInfo(i, a);
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
    },
    perfect: function(e) {
        console.log(e);
        for (var a = this, o = this.data.region, n = 0; n < o.length; n++) if ("全部" == o[n]) return wx.showToast({
            title: "请完善地址!",
            icon: "none",
            duration: 1500
        }), !1;
        var i = a.data.sex, s = o[0], l = o[1], r = o[2];
        0 == e.detail.value.name.length ? (wx.showToast({
            title: "姓名不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3)) : wx.request({
            url: t.d.ceshiUrl + "&action=user&m=perfect",
            method: "post",
            data: {
                user_id: t.globalData.userInfo.user_id,
                name: e.detail.value.name,
                mobile: a.data.mobile,
                province: s,
                city: l,
                county: r,
                wx_id: e.detail.value.wx_id,
                sex: i,
                date: a.data.date
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                1 == e.data.status ? wx.showToast({
                    title: e.data.succ,
                    icon: "success",
                    duration: 3e3
                }) : wx.showToast({
                    title: e.data.err ? e.data.err : "非法操作！",
                    icon: "none",
                    duration: 1500
                }), setTimeout(function() {
                    wx.navigateBack({
                        delta: 1
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
    }
});