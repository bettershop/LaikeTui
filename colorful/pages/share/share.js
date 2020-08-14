var a = getApp();

Page({
    data: {
        user: ""
    },
    onLoad: function(t) {
        var e = this;
        e.setData({
            wx_name: a.globalData.userInfo.nickName,
            headimgurl: a.globalData.userInfo.avatarUrl
        }), wx.request({
            url: a.d.ceshiUrl + "&action=user&m=share",
            method: "post",
            data: {
                n: e.options.n,
                id: e.options.id,
                openid: a.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(a) {
                a.data.status;
                e.setData({
                    text: a.data.text,
                    wishing: a.data.wishing
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
    close: function() {
        wx.navigateBack({
            delta: 2
        });
    }
});