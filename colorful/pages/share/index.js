var a = getApp();

Page({
    data: {
        flag: !1
    },
    onLoad: function(t) {
        var e = t.id, o = t.n, n = this;
        console.log(a.globalData.userInfo), n.setData({
            n: o,
            id: e,
            headimgurl: a.globalData.userInfo.avatarUrl
        }), wx.request({
            url: a.d.ceshiUrl + "&action=getcode&m=madeCode",
            method: "post",
            data: {
                openid: a.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(a) {
                1 == a.data.status && n.setData({
                    text: a.data.text
                });
            },
            error: function(a) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    open: function(a) {
        var t = this, e = t.data.id, o = t.data.n;
        wx.navigateTo({
            url: "../share/share?id=" + e + "&n=" + o
        });
    }
});