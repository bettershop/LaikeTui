var a = require("request.js"), t = require("./utils/util.js");

App({
    d: {
        appId: "",
        appKey: "",
        purchase: 0,
        indexchase: !1,
        frontColor: "#000000",
        one: !1,
        bf_color: "#ffffff",
        h_color: "#FF63477",
        order: {},
        ceshiUrl: t.getUri(),
        titlee: "",
        bgcolor: ""
    },
    onLaunch: function(t) {
        var e = wx.getStorageSync("logs") || [];
        e.unshift(Date.now()), wx.setStorageSync("logs", e), this.request = a;
    },
    userlogin: function(a) {
        if ("" != this.globalData.userInfo.openid && this.globalData.userInfo.openid) console.log(this.globalData.userInfo); else {
            if (a) return !0;
            wx.navigateTo({
                url: "pages/login/login"
            });
        }
    },
    onShow: function(a) {
        var t = a.query.userid ? a.query.userid : "";
        this.globalData.referee_openid = t;
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500);
    },
    getUserInfo: function(a, t, e, o) {
        var n = this;
        this.d.one ? (this.d.one = !1, setTimeout(function() {
            n.getUserInfo(a, t);
        }, 1500)) : (this.d.one = !0, wx.login({
            success: function(t) {
                var s = t.code;
                n.globalData.code = t.code, n.getUserSessionKey(s, a, e, o);
            }
        }));
    },
    getHomeData: function() {
        var a = this;
        wx.login({
            success: function(t) {
                a.globalData.code = t.code;
                var e = wx.getStorageSync("userInfo");
                e.nickName && (a.globalData.userInfo = e);
            }
        });
    },
    getUserSessionKey: function(a, t, e, o, n) {
        var s = this;
        wx.request({
            url: s.d.ceshiUrl + "&action=app&m=index",
            method: "post",
            data: {
                code: a,
                nickName: e.nickName,
                avatarUrl: e.avatarUrl,
                gender: e.gender,
                referee_openid: this.globalData.userInfo.referee_openid || ""
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(a) {
                var t = a.data;
                if (0 == t.status) return wx.showToast({
                    title: t.err,
                    duration: 2e3
                }), !1;
                s.d.ceshiUrl = s.d.ceshiUrl + "&token=" + a.data.access_token, s.d.localhost = s.d.localhost + "&token=" + a.data.access_token, 
                s.globalData.userInfo.plug_ins = a.data.plug_ins, s.globalData.userInfo.coupon = a.data.coupon, 
                s.globalData.userInfo.wallet = a.data.wallet, s.globalData.userInfo.sign = a.data.sign, 
                s.globalData.userInfo.sign_status = a.data.sign_status, s.globalData.userInfo.sign_image = a.data.sign_image, 
                s.globalData.userInfo.user_id = a.data.user_id, s.globalData.userInfo.nickName = a.data.nickName, 
                s.globalData.userInfo.avatarUrl = a.data.avatarUrl, s.globalData.userInfo.session_key = a.data.user.session_key, 
                s.globalData.userInfo.openid = a.data.user.openid, s.globalData.userInfo.nickName = a.data.user.nickName, 
                s.globalData.userInfo.gender = a.data.user.gender, s.globalData.userInfo.company = a.data.user.company, 
                s.globalData.userInfo.bgcolor = a.data.user.bgcolor, wx.setStorageSync("userInfo", s.globalData.userInfo), 
                o();
            },
            fail: function(a) {
                wx.showToast({
                    title: "网络异常！err:getsessionkeys",
                    duration: 2e3
                });
            }
        });
    },
    getOrBindTelPhone: function(a) {
        this.globalData.userInfo.tel || wx.navigateTo({
            url: "pages/binding/binding"
        });
    },
    globalData: {
        userInfo: {},
        userlogin: wx.getStorageSync("userlogin")
    },
    redirect: function(a, t) {
        wx.navigateTo({
            url: "/pages/" + a + "?" + t
        });
    },
    showModal: function(a) {
        var t = wx.createAnimation({
            duration: 200
        });
        t.opacity(0).rotateX(-100).step(), a.setData({
            animationData: t.export()
        }), setTimeout(function() {
            t.opacity(1).rotateX(0).step(), a.setData({
                animationData: t
            });
        }.bind(a), 200);
    },
    showToast: function(a, t) {
        var e = {};
        e.toastTitle = t, a.setData({
            toast: e
        });
        var o = wx.createAnimation({
            duration: 100
        });
        o.opacity(0).rotateY(-100).step(), e.toastStatus = !0, e.toastAnimationData = o.export(), 
        a.setData({
            toast: e
        }), setTimeout(function() {
            o.opacity(1).rotateY(0).step(), e.toastAnimationData = o, a.setData({
                toast: e
            });
        }.bind(a), 100), setTimeout(function() {
            e.toastStatus = !1, a.setData({
                toast: e
            });
        }.bind(a), 2e3);
    }
});