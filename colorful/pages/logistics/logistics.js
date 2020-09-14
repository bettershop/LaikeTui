var t = getApp();

Page({
    data: {
        wuliu: [ "已接收", "抵达深圳", "抵达广州" ],
        remind: "加载中"
    },
    onLoad: function(o) {
        console.log(o);
        var a = this;
        wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        });
        var e = o.orderId, n = o.details ? o.details : "", i = o.type ? o.type : "", r = o.courier_num, s = o.express_id;
        wx.request({
            url: t.d.ceshiUrl + "&action=order&m=logistics",
            method: "post",
            data: {
                id: e,
                details: n,
                type: i,
                courier_num: r,
                express_id: s
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                1 == t.data.status ? ("ok" == t.data.res_1.message ? a.setData({
                    wuliu: t.data.res_1.data,
                    res: t.data
                }) : a.setData({
                    res: !1
                }), console.log(t.data.res_1.data)) : wx.showToast({
                    title: t.data.err,
                    duration: 2e3
                });
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    onReady: function() {
        var t = this;
        setTimeout(function() {
            t.setData({
                remind: ""
            });
        }, 1e3);
    },
    copyText: function(t) {
        var o = t.currentTarget.dataset.text;
        wx.setClipboardData({
            data: o,
            success: function() {
                wx.showToast({
                    title: "已复制"
                });
            }
        });
    },
    onShow: function() {},
    onHide: function() {},
    onUnload: function() {},
    onPullDownRefresh: function() {},
    onReachBottom: function() {}
});