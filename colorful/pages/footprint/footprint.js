var t = getApp();

Page({
    data: {
        remind: "加载中"
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500), this.footprint();
    },
    onLoad: function(o) {
        wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), this.setData({
            bgcolor: t.d.bf_color
        }), this.footprint();
    },
    onReady: function() {
        var t = this;
        setTimeout(function() {
            t.setData({
                remind: ""
            });
        }, 1e3);
    },
    alldel: function() {
        var o = this;
        wx.showModal({
            title: "提示",
            content: "确定要清除全部商品吗？",
            success: function(n) {
                n.confirm ? wx.request({
                    url: t.d.ceshiUrl + "&action=footprint&m=alldel",
                    method: "post",
                    data: {
                        openid: t.globalData.userInfo.openid
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(t) {
                        1 == t.data.status ? (wx.showToast({
                            title: "清理成功！",
                            duration: 2e3
                        }), o.setData({
                            arr: []
                        })) : wx.showToast({
                            title: "清理失败!",
                            duration: 2e3
                        });
                    },
                    error: function(t) {
                        wx.showToast({
                            title: "网络异常！",
                            duration: 2e3
                        });
                    }
                }) : n.cancel && console.log("用户点击取消");
            }
        });
    },
    footprint: function() {
        var o = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=footprint&m=index",
            method: "post",
            data: {
                openid: t.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                1 == t.data.status ? o.setData({
                    arr: t.data.arr
                }) : wx.showToast({
                    title: "暂时还没有记录!",
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
    }
});