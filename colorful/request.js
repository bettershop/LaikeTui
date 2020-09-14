var e = require("./utils/util.js"), t = new Object();

t.wxRequest = function(t) {
    wx.request({
        url: e.getUri() + t.url,
        data: t.data,
        method: t.method,
        header: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        success: function(e) {
            "function" == typeof t.success && t.success(e.data);
        }
    });
}, module.exports = t;