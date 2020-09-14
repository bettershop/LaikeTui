function t(t, a, e) {
    t.animation = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 400,
        timingFunction: "ease",
        delay: 0
    }), t.animation.translateY(a + "vh").step(), t.setData({
        animation: t.animation.export(),
        show: e
    });
}

var a = getApp(), e = 0, n = !1, i = 200, o = [ 0, 0, 0 ], s = [], d = [], r = [];

Page({
    data: {
        sheng: s,
        shi: d,
        xian: r,
        value: [ 0, 0, 0 ]
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500), this.AddressManagement();
    },
    onLoad: function(t) {
        wx.setNavigationBarColor({
            frontColor: a.d.frontColor,
            backgroundColor: a.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), this.setData({
            bgcolor: a.d.bgcolor,
            id_arr: t.addr_id
        }), this.AddressManagement();
    },
    AddressManagement: function() {
        var t = this;
        wx.request({
            url: a.d.ceshiUrl + "&action=address&m=up_addsindex",
            data: {
                openid: a.globalData.userInfo.openid,
                id_arr: t.data.id_arr
            },
            method: "POST",
            header: {
                "content-type": "application/x-www-form-urlencoded"
            },
            success: function(a) {
                var e = a.data.adds, n = a.data.province, i = a.data.city, o = a.data.county;
                if ("" == e) e = [];
                t.setData({
                    address: e,
                    province: n,
                    city: i,
                    county: o
                });
            }
        });
    },
    AddressManagement1: function() {
        var t = this;
        wx.request({
            url: a.d.ceshiUrl + "&action=user&m=AddressManagement",
            data: {
                openid: a.globalData.userInfo.openid
            },
            method: "POST",
            header: {
                "content-type": "application/x-www-form-urlencoded"
            },
            success: function(a) {
                if (1 == a.data.status) {
                    var e = a.data.sheng, n = a.data.shi, i = a.data.xian;
                    t.setData({
                        sheng: e,
                        shi: n,
                        xian: i
                    });
                } else wx.showToast({
                    title: "非法操作！",
                    duration: 2e3
                });
            }
        });
    },
    translate: function(a) {
        0 == e ? (i = 0, n = !1, e = 1) : (i = 200, n = !0, e = 0), t(this, i, n), this.AddressManagement1();
    },
    hiddenFloatView: function(t) {
        i = 200, this.setData({
            show: !1
        }), e = 1;
    },
    bindChange: function(t) {
        var a = t.detail.value;
        o[0] != a[0] ? (this.getCityArr(a[0]), this.getCountyInfo(a[0], a[1])) : o[1] != a[1] && this.getCountyInfo(a[0], a[1]), 
        o = a, this.Preservation(o);
    },
    getCityArr: function(t) {
        var e = this;
        wx.request({
            url: a.d.ceshiUrl + "&action=user&m=getCityArr",
            data: {
                count: t
            },
            method: "POST",
            header: {
                "content-type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (1 == t.data.status) {
                    var a = t.data.shi;
                    e.setData({
                        shi: a
                    });
                } else wx.showToast({
                    title: "非法操作！",
                    duration: 2e3
                });
            }
        });
    },
    getCountyInfo: function(t, e) {
        var n = this;
        wx.request({
            url: a.d.ceshiUrl + "&action=user&m=getCountyInfo",
            data: {
                count: t,
                column: e
            },
            method: "POST",
            header: {
                "content-type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (1 == t.data.status) {
                    var a = t.data.xian;
                    n.setData({
                        xian: a
                    });
                } else wx.showToast({
                    title: "非法操作！",
                    duration: 2e3
                });
            }
        });
    },
    Preservation: function(t) {
        var e = this;
        wx.request({
            url: a.d.ceshiUrl + "&action=user&m=Preservation",
            data: {
                sheng: t[0],
                shi: t[1],
                xuan: t[2]
            },
            method: "POST",
            header: {
                "content-type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (1 == t.data.status) {
                    var a = t.data.province, n = t.data.city, i = t.data.county;
                    e.setData({
                        province: a,
                        city: n,
                        county: i
                    });
                } else wx.showToast({
                    title: "非法操作！",
                    duration: 2e3
                });
            }
        });
    },
    SaveAddress: function(t) {
        var e = this;
        0 == t.detail.value.user_name.length ? (wx.showToast({
            title: "联系人不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3)) : 0 == t.detail.value.mobile.length ? (wx.showToast({
            title: "电话不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3)) : 0 == t.detail.value.province.length ? (wx.showToast({
            title: "城市不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3)) : wx.request({
            url: a.d.ceshiUrl + "&action=address&m=up_adds",
            data: {
                openid: a.globalData.userInfo.openid,
                user_name: t.detail.value.user_name,
                id_arr: e.data.id_arr,
                mobile: t.detail.value.mobile,
                province: t.detail.value.province,
                city: t.detail.value.city,
                county: t.detail.value.county,
                address: t.detail.value.address
            },
            method: "POST",
            header: {
                "content-type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                0 == t.data.status ? wx.showToast({
                    title: t.data.info,
                    icon: "loading",
                    duration: 1500
                }) : wx.showModal({
                    title: "提示",
                    content: t.data.info,
                    success: function(t) {
                        wx.navigateBack({
                            delta: 1
                        });
                    }
                });
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    }
});