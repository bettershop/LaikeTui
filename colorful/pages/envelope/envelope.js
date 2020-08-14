var e = getApp(), t = require("../../wxParse/wxParse.js");

Page({
    data: {
        images: {}
    },
    imageLoad: function(e) {
        var t = 718 / (e.detail.width / e.detail.height), a = this.data.images;
        a[e.target.dataset.index] = {
            width: 718,
            height: t
        }, this.setData({
            images: a
        });
    },
    onLoad: function(t) {
        wx.setNavigationBarColor({
            frontColor: e.d.frontColor,
            backgroundColor: e.d.bgcolor
        });
        var a = this, o = t.id;
        "" != t.p_openid ? e.globalData.userInfo.p_openid = t.p_openid : e.globalData.userInfo.p_openid = "", 
        a.setData({
            id: o
        }), a.y_detail(o), a.storage();
    },
    onShow: function() {},
    y_detail: function(a) {
        var o = this;
        wx.request({
            url: e.d.ceshiUrl + "&action=envelope&m=index",
            method: "post",
            data: {
                id: a
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (1 == e.data.status) {
                    var a = e.data.article, i = a[0].content;
                    t.wxParse("content", "html", i, o, 3), o.setData({
                        article: a[0]
                    }), wx.setNavigationBarTitle({
                        title: a[0].Article_title
                    });
                } else wx.showToast({
                    title: e.data.err,
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
    onShareAppMessage: function(t) {
        var a = this, o = a.data.article.Article_id, i = a.data.article.Article_title;
        return "button" === t.from && console.log(t.target), {
            title: i,
            path: "/pages/envelope/envelope?id=" + o + "&p_openid=" + e.globalData.userInfo.openid,
            success: function(t) {
                wx.request({
                    url: e.d.ceshiUrl + "&action=envelope&m=share",
                    method: "post",
                    data: {
                        id: o,
                        openid: e.globalData.userInfo.openid
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(e) {
                        wx.showToast({
                            title: e.data.err,
                            duration: 2e3
                        }), 1 == e.data.info && wx.navigateTo({
                            url: "../share/index?id=" + o + "&n=1"
                        });
                    },
                    fail: function(e) {
                        wx.showToast({
                            title: "网络异常！err:getsessionkeys",
                            duration: 2e3
                        });
                    }
                });
            },
            fail: function(e) {}
        };
    },
    storage: function() {
        wx.request({
            url: e.d.ceshiUrl + "&action=app&m=user",
            method: "post",
            data: {
                nickName: e.globalData.userInfo.nickName,
                headimgurl: e.globalData.userInfo.avatarUrl,
                sex: e.globalData.userInfo.gender,
                openid: e.globalData.userInfo.openid,
                p_openid: e.globalData.userInfo.p_openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                console.log(e);
            },
            fail: function(e) {}
        });
    }
});