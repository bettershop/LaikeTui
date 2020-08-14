function e(e, t, n) {
    return r("appid=" + o.d.appId + "&nonceStr=" + e + "&package=" + t + "&signType=MD5&timeStamp=" + n + "&key=" + o.d.appKey).toUpperCase();
}

function t() {
    return Date.parse(new Date()) + "";
}

function n() {
    return Math.random().toString(36).substring(3, 8);
}

function r(e) {
    return a.hexMD5(e);
}

var o = getApp(), a = require("MD5Encode.js");

module.exports = {
    isStringEmpty: function(e) {
        return null == e || "" == e;
    },
    sentHttpRequestToServer: function(e, t, n, r, a, s) {
        wx.request({
            url: o.d.hostUrl + e,
            data: t,
            method: n,
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: r,
            fail: a,
            complete: s
        });
    },
    mapToJson: function(e) {
        if (null == e) return null;
        var t = "{";
        for (var n in e) t = t + n + ":" + e[n] + ",";
        return "," == t.charAt(t.length - 1) && (t = t.substring(0, t.length - 1)), t += "}";
    },
    toastSuccess: function() {
        wx.showToast({
            title: "成功",
            icon: "success",
            duration: 2e3
        });
    },
    doWechatPay: function(r, o, a, s) {
        var u = n(), i = t(), c = "prepay_id=" + r, p = {
            timeStamp: i,
            nonceStr: u,
            package: c,
            signType: "MD5",
            paySign: e(u, c, i),
            success: o,
            fail: a,
            complete: s
        };
        console.log(p), wx.requestPayment(p);
    }
};