function t(t) {
    return (t = t.toString())[1] ? t : "0" + t;
}

module.exports = {
    formatTime: function(e) {
        var a = e.getFullYear(), n = e.getMonth() + 1, o = e.getDate(), r = e.getHours(), i = e.getMinutes(), u = e.getSeconds();
        return [ a, n, o ].map(t).join("/") + " " + [ r, i, u ].map(t).join(":");
    },
    getUesrBg: function(t) {
        wx.request({
            url: t.d.ceshiUrl + "&action=app&m=cart",
            method: "post",
            data: {
                openid: t.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                var e = t.data.cart.toString();
                e > 0 && wx.setTabBarBadge({
                    index: 2,
                    text: e
                });
            },
            fail: function(t) {}
        });
    },
    getUesrBgplus: function(t, e, a) {
        wx.request({
            url: e.d.ceshiUrl + "&action=app&m=cart",
            method: "post",
            data: {
                openid: e.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (a) return t.setData({
                    cart: e.data.cart
                }), null;
                e.data.cart ? wx.setTabBarBadge({
                    index: 2,
                    text: e.data.cart
                }) : wx.removeTabBarBadge({
                    index: 2
                });
            },
            fail: function(t) {}
        });
    },
    getUri: function() {
        return "http://localhost/open/app/LKT/index.php?module=api&software_name=3&edition=1.0";
    }
};