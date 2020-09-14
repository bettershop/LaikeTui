var t = getApp(), e = require("../../utils/util.js");

Page({
    data: {
        click: !1,
        option: !1,
        logoimg: "",
        loadtitle: "",
        thvm: {}
    },
    myCatchTouch: function() {},
    setCloses: function() {
        this.clickPup();
    },
    clickPup: function(e) {
        this.setData({
            logoimg: t.globalData.logoimg,
            loadtitle: t.globalData.title,
            thvm: e || ""
        });
        var a = this;
        a.data.click || a.setData({
            click: !0
        }), a.data.option ? (a.setData({
            option: !1
        }), setTimeout(function() {
            a.setData({
                click: !1
            });
        }, 500)) : a.setData({
            option: !0
        });
    },
    getUserInfo: function(t) {
        wx.showLoading({
            title: "正在登录",
            mask: !0
        });
    },
    material: function(e) {
        wx.getUserInfo({
            success: function(e) {
                var a = e.userInfo, o = a.nickName, n = a.avatarUrl, s = a.gender;
                wx.request({
                    url: t.d.ceshiUrl + "&action=user&m=material",
                    method: "post",
                    data: {
                        openid: t.globalData.userInfo.openid,
                        nickName: o,
                        avatarUrl: n,
                        gender: s
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(t) {
                        wx.showToast({
                            title: t.data.info,
                            success: 2e3
                        });
                    }
                });
            }
        });
    },
    agreeGetUser: function(e) {
        var a = this;
        if (wx.showLoading({
            title: "正在登入",
            success: 2e3
        }), console.log(e.detail.userInfo), "getUserInfo:ok" == e.detail.errMsg) {
            t.globalData.userlogin = !0, wx.setStorageSync("userlogin", !0);
            try {
                wx.setStorageSync("userInfo", e.detail.userInfo);
            } catch (e) {
                wx.showToast({
                    title: "系统提示:网络错误！",
                    icon: "warn",
                    duration: 1500
                });
            }
            a.setData({
                userlogin: !1
            }), a.getOP(e.detail.userInfo);
        } else wx.showToast({
            title: "没有授权，不能执行该操作！",
            icon: "none",
            duration: 2e3
        }), a.setData({
            userlogin: !0
        });
    },
    login: function() {
        var e = this;
        wx.getStorageSync("userInfo");
        wx.login({
            success: function(e) {
                t.globalData.code = e.code, wx.setStorageSync("code", e.code);
            },
            fail: function() {
                wx.showToast({
                    title: "系统提示:网络错误！",
                    icon: "warn",
                    duration: 1500
                });
            }
        }), wx.getSetting({
            success: function(a) {
                a.authSetting["scope.userInfo"] && t.globalData.userlogin ? (e.setData({
                    userlogin: !1
                }), t.globalData.userlogin = !0, wx.setStorageSync("userlogin", !0)) : e.setData({
                    userlogin: !0
                });
            },
            fail: function() {
                wx.showToast({
                    title: "系统提示:网络错误！",
                    icon: "warn",
                    duration: 1500
                });
            }
        });
    },
    getOP: function(a) {
        var o = this, n = this.data.thvm;
        t.getUserInfo("", "", a, function() {
            var s = a;
            t.globalData.userInfo;
            t.globalData.userInfo.avatarUrl = s.avatarUrl, t.globalData.userInfo.nickName = s.nickName, 
            t.globalData.userInfo.gender = s.gender, wx.setStorageSync("userInfo", t.globalData.userInfo);
            var i = s.nickName, r = s.avatarUrl, l = s.gender;
            wx.request({
                url: t.d.ceshiUrl + "&action=user&m=material",
                method: "post",
                data: {
                    openid: t.globalData.userInfo.openid,
                    nickName: i,
                    avatarUrl: r,
                    gender: l
                },
                header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function(a) {
                    wx.showToast({
                        title: "授权成功!",
                        success: 2e3
                    }), setTimeout(function() {
                        o.clickPup(), n.onLoad(), e.getUesrBgplus(this, t, !1);
                    }, 1800);
                }
            });
        });
    }
});