var e = getApp();

Page({
    data: {},
    onLoad: function(e) {},
    onReady: function() {},
    onShow: function() {},
    onHide: function() {},
    onUnload: function() {},
    getUserInfo: function(e) {
        wx.showLoading({
            title: "正在登录",
            mask: !0
        });
    },
    material: function(a) {
        wx.getUserInfo({
            success: function(a) {
                var t = a.userInfo, o = t.nickName, n = t.avatarUrl, s = t.gender;
                wx.request({
                    url: e.d.ceshiUrl + "&action=user&m=material",
                    method: "post",
                    data: {
                        openid: e.globalData.userInfo.openid,
                        nickName: o,
                        avatarUrl: n,
                        gender: s
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(e) {
                        wx.showToast({
                            title: e.data.info,
                            success: 2e3
                        });
                    }
                });
            }
        });
    },
    agreeGetUser: function(a) {
        var t = this;
        if (wx.showLoading({
            title: "正在登入",
            success: 2e3
        }), console.log(a.detail.userInfo), "getUserInfo:ok" == a.detail.errMsg) {
            e.globalData.userlogin = !0, wx.setStorageSync("userlogin", !0);
            try {
                wx.setStorageSync("userInfo", a.detail.userInfo);
            } catch (a) {
                wx.showToast({
                    title: "系统提示:网络错误！",
                    icon: "warn",
                    duration: 1500
                });
            }
            t.setData({
                userlogin: !1
            }), t.getOP(a.detail.userInfo);
        } else wx.showToast({
            title: "没有授权，不能执行该操作！",
            icon: "none",
            duration: 2e3
        }), t.setData({
            userlogin: !0
        });
    },
    login: function() {
        var a = this;
        wx.getStorageSync("userInfo");
        wx.login({
            success: function(a) {
                e.globalData.code = a.code, wx.setStorageSync("code", a.code);
            },
            fail: function() {
                wx.showToast({
                    title: "系统提示:网络错误！",
                    icon: "warn",
                    duration: 1500
                });
            }
        }), wx.getSetting({
            success: function(t) {
                t.authSetting["scope.userInfo"] && e.globalData.userlogin ? (a.setData({
                    userlogin: !1
                }), e.globalData.userlogin = !0, wx.setStorageSync("userlogin", !0)) : a.setData({
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
        var t = a;
        e.globalData.userInfo;
        e.globalData.userInfo.avatarUrl = t.avatarUrl, e.globalData.userInfo.nickName = t.nickName, 
        e.globalData.userInfo.gender = t.gender, wx.setStorageSync("userInfo", e.globalData.userInfo);
        var o = t.nickName, n = t.avatarUrl, s = t.gender;
        wx.request({
            url: e.d.ceshiUrl + "&action=user&m=material",
            method: "post",
            data: {
                openid: e.globalData.userInfo.openid,
                nickName: o,
                avatarUrl: n,
                gender: s
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                wx.showToast({
                    title: "授权成功!",
                    success: 2e3
                }), setTimeout(function() {
                    wx.navigateBack({
                        delta: 1
                    });
                }, 1800);
            }
        });
    }
});