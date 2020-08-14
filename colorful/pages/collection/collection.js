var o = getApp();

Page({
    data: {
        remind: "加载中"
    },
    onLoad: function(t) {
        wx.setNavigationBarColor({
            frontColor: o.d.frontColor,
            backgroundColor: o.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        });
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500), this.collection();
    },
    onReady: function() {},
    onShow: function() {
        this.collection();
    },
    alldel: function() {
        var t = this;
        wx.showModal({
            title: "提示",
            content: "确定要清除全部商品吗？",
            success: function(e) {
                e.confirm ? wx.request({
                    url: o.d.ceshiUrl + "&action=addFavorites&m=alldel",
                    method: "post",
                    data: {
                        openid: o.globalData.userInfo.openid
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(o) {
                        1 == o.data.status ? (wx.showToast({
                            title: "清理成功！",
                            duration: 2e3
                        }), t.setData({
                            list: []
                        })) : wx.showToast({
                            title: "清理失败!",
                            duration: 2e3
                        });
                    },
                    error: function(o) {
                        wx.showToast({
                            title: "网络异常！",
                            duration: 2e3
                        });
                    }
                }) : e.cancel && console.log("用户点击取消");
            }
        });
    },
    collection: function() {
        var t = this;
        wx.request({
            url: o.d.ceshiUrl + "&action=addFavorites&m=collection",
            method: "post",
            data: {
                openid: o.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                1 == e.data.status ? t.setData({
                    list: e.data.list,
                    bgcolor: o.d.bf_color,
                    remind: ""
                }) : wx.showToast({
                    title: "暂时还没有收藏!",
                    duration: 2e3,
                    remind: ""
                });
            },
            error: function(o) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    removeFavorites: function(t) {
        console.log(t);
        var e = this, a = t.currentTarget.dataset.favId;
        wx.showModal({
            title: "提示",
            content: "你确认移除吗",
            success: function(t) {
                t.confirm && wx.request({
                    url: o.d.ceshiUrl + "&action=addFavorites&m=removeFavorites",
                    method: "post",
                    data: {
                        id: a,
                        openid: o.globalData.userInfo.openid
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(o) {
                        1 == o.data.status ? (wx.showToast({
                            title: o.data.succ,
                            duration: 2e3
                        }), e.collection()) : wx.showToast({
                            title: o.data.err,
                            duration: 2e3
                        });
                    },
                    error: function(o) {
                        wx.showToast({
                            title: "网络异常！",
                            duration: 2e3
                        });
                    }
                });
            }
        });
    }
});