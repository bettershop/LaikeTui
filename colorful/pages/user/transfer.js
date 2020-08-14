var o = getApp();

Page({
    data: {
        inp_money: 0,
        iv: "",
        encryptedData: "",
        islogin: !1,
        remind: "加载中",
        bank_name: ""
    },
    onReady: function() {
        var o = this;
        setTimeout(function() {
            o.setData({
                remind: ""
            });
        }, 1e3);
    },
    onLoad: function(e) {
        wx.setNavigationBarColor({
            frontColor: o.d.frontColor,
            backgroundColor: o.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), wx.checkSession({
            success: function(e) {
                console.log(e), console.log("session_key 未过期" + o.globalData.userInfo.session_key), 
                o.globalData.userInfo.session_key = o.globalData.userInfo.session_key;
            },
            fail: function() {
                wx.login({
                    success: function(e) {
                        var a = e.code;
                        that.globalData.code = e.code;
                        var n = wx.getStorageSync("userInfo");
                        that.globalData.userInfo = n, o.getUserSessionKey(a, cb);
                    }
                });
            }
        }), this.setData({
            bgcolor: o.d.bf_color
        });
    },
    withdrawals: function(o) {
        var e = o.detail.value.user_id;
        wx.redirectTo({
            url: "../user/transfer1?user_id=" + e
        });
    }
});