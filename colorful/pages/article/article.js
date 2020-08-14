var t = getApp(), e = require("../../wxParse/wxParse.js");

Page({
    data: {
        images: {},
        titstu: !1,
        maskHidden: !1,
        show_share: !1
    },
    onLoad: function(e) {
        console.log(e), wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), this.setData({
            bgcolor: t.d.bgcolor
        });
        var a = this, o = e.id;
        "" != e.p_openid ? t.globalData.userInfo.p_openid = e.p_openid : t.globalData.userInfo.p_openid = "", 
        a.setData({
            id: o
        }), a.y_detail(o), a.storage();
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500);
    },
    y_detail: function(a) {
        var o = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=envelope&m=index",
            method: "post",
            data: {
                id: a
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (1 == t.data.status) {
                    var a = t.data.article, n = a[0].content;
                    e.wxParse("content", "html", n, o, 3), o.setData({
                        article: a[0],
                        title: a[0].Article_title
                    }), "关于我们" != a[0].Article_title && o.setData({
                        titstu: !0
                    }), wx.setNavigationBarTitle({
                        title: a[0].Article_title
                    });
                } else wx.showToast({
                    title: t.data.err,
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
    },
    onShareAppMessage: function(e) {
        var a = this, o = a.data.article.Article_id, n = a.data.article.Article_title;
        return "button" === e.from && console.log(e.target), {
            title: n,
            imageUrl: a.data.article.Article_imgurl,
            path: "/pages/index/index?id?p_openid=" + t.globalData.userInfo.openid,
            success: function(e) {
                wx.request({
                    url: t.d.ceshiUrl + "&action=envelope&m=share",
                    method: "post",
                    data: {
                        id: o,
                        openid: t.globalData.userInfo.openid
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(t) {
                        wx.showToast({
                            title: t.data.err,
                            duration: 2e3
                        }), 1 == t.data.info && wx.navigateTo({
                            url: "../share/index?id=" + o + "&n=1"
                        });
                    },
                    fail: function(t) {
                        wx.showToast({
                            title: "网络异常！err:getsessionkeys",
                            duration: 2e3
                        });
                    }
                });
            },
            fail: function(t) {}
        };
    },
    storage: function() {
        wx.request({
            url: t.d.ceshiUrl + "&action=app&m=user",
            method: "post",
            data: {
                nickName: t.globalData.userInfo.nickName,
                headimgurl: t.globalData.userInfo.avatarUrl,
                sex: t.globalData.userInfo.gender,
                openid: t.globalData.userInfo.openid,
                p_openid: t.globalData.userInfo.p_openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {},
            fail: function(t) {}
        });
    },
    user_share: function() {
        var e = this;
        wx.showToast({
            title: "图片生成中",
            icon: "loading",
            duration: 1500
        }), console.log(t.globalData.userInfo), t.request.wxRequest({
            url: "&action=getcode&m=product_share",
            data: {
                scene: "id=" + e.data.id + "&p_openid=" + t.globalData.userInfo.openid,
                path: "pages/article/article",
                id: t.globalData.userInfo.user_id,
                pid: e.data.id,
                type: 1
            },
            method: "post",
            success: function(t) {
                console.log(t), e.setData({
                    maskHidden: !0,
                    imagePath: t.url
                });
            }
        });
        var a = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        });
        e.animation = a, a.translateY(300).step(), e.setData({
            animationData: a.export()
        }), setTimeout(function() {
            a.translateY(0).step(), e.setData({
                animationData: a,
                show_share: !1
            });
        }.bind(e), 200);
    },
    close_share: function(t) {
        this.setData({
            maskHidden: !1
        });
    },
    baocun: function() {
        var t = this;
        console.log("用户点击保存"), wx.getSetting({
            success: function(t) {
                console.log(t), t.authSetting["scope.writePhotosAlbum"] ? console.log("qqqqq") : wx.authorize({
                    scope: "scope.writePhotosAlbum",
                    success: function() {
                        console.log("授权成功");
                    }
                });
            }
        }), wx.downloadFile({
            url: t.data.imagePath,
            success: function(e) {
                var a = e.tempFilePath;
                console.log(a), wx.saveImageToPhotosAlbum({
                    filePath: a,
                    success: function(e) {
                        wx.showModal({
                            content: "图片已保存到相册，赶紧晒一下吧~",
                            showCancel: !1,
                            confirmText: "好的",
                            confirmColor: "#333",
                            success: function(e) {
                                e.confirm && (console.log("用户点击确定"), t.setData({
                                    maskHidden: !1
                                }));
                            },
                            fail: function(t) {
                                console.log(11111);
                            }
                        });
                    }
                });
            }
        });
    },
    previewImage: function(t) {
        var e = t.target.dataset.src, a = [ e ];
        wx.previewImage({
            current: e,
            urls: a
        });
    },
    preventTouchMove: function(t) {}
});