function t(t, a, e) {
    t.animation = wx.createAnimation({
        transformOrigin: "50% 50%",
        duration: 400,
        timingFunction: "linear",
        delay: 0
    }), t.animation.translateY(a + "vh").step(), t.setData({
        animation: t.animation.export(),
        show: e
    });
}

var a = getApp(), e = 0, i = 200, o = [ 0, 0, 0 ], n = [], s = [], d = [];

Page({
    data: {
        sheng: n,
        shi: s,
        xian: d,
        value: [ 0, 0, 0 ],
        cartId: 0,
        arr: [],
        show: !1
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
            cartId: t.cartId
        }), this.AddressManagement();
    },
    aotuAddress: function() {
        var t = this;
        console.log(t.data.arr), wx.chooseAddress({
            success: function(a) {
                var e = a.userName, i = a.telNumber, o = a.detailInfo;
                t.setData({
                    sheng: a.provinceName,
                    shi: a.cityName,
                    xian: a.countyName,
                    province: a.provinceName,
                    city: a.cityName,
                    county: a.countyName,
                    user_name: e,
                    mobile: i,
                    address: o
                });
            }
        });
    },
    AddressManagement: function() {
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
                    var e = a.data.sheng, i = a.data.shi, o = a.data.xian;
                    t.setData({
                        sheng: e,
                        shi: i,
                        xian: o
                    });
                } else wx.showToast({
                    title: "非法操作！",
                    duration: 2e3
                });
            }
        });
    },
    translate: function(a) {
        i = 200, this.data.show = !0, e = 0;
        var o = this.data.province ? this.data.province : this.data.sheng[0].G_CName, n = this.data.city ? this.data.city : this.data.shi[0].G_CName, s = this.data.county ? this.data.county : this.data.xian[0].G_CName;
        console.log(o, n, s), this.setData({
            province: o,
            city: n,
            county: s
        }), t(this, i, this.data.show);
    },
    hiddenFloatView: function(t) {
        i = 200, this.setData({
            show: !1
        }), e = 1;
    },
    bindChange: function(t) {
        var a = t.detail.value;
        console.log(a), console.log(o), o[0] != a[0] ? (a[1] = 0, a[2] = 0, o[1] = 0, o[2] = 0, 
        this.getCityArr(a[0]), this.getCountyInfo(a[0], a[1])) : o[1] != a[1] && (a[2] = 0, 
        o[2] = 0, this.getCountyInfo(a[0], a[1])), o = a, this.Preservation(o);
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
        var i = this;
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
                    i.setData({
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
                    var a = t.data.province, i = t.data.city, o = t.data.county;
                    e.setData({
                        province: a,
                        city: i,
                        county: o
                    });
                } else wx.showToast({
                    title: "非法操作！",
                    duration: 2e3
                });
            }
        });
    },
    SaveAddress: function(t) {
        if (0 == t.detail.value.user_name.length) wx.showToast({
            title: "联系人不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else if (0 == t.detail.value.mobile.length) wx.showToast({
            title: "电话不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else if (0 == t.detail.value.province.length) wx.showToast({
            title: "城市不得为空!",
            icon: "loading",
            duration: 1500
        }), setTimeout(function() {
            wx.hideToast();
        }, 2e3); else {
            this.data.cartId;
            t.detail.value.mobile.match(/^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/) ? wx.request({
                url: a.d.ceshiUrl + "&action=user&m=SaveAddress",
                data: {
                    openid: a.globalData.userInfo.openid,
                    user_name: t.detail.value.user_name,
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
                    }) : (a.d.islogin = !0, wx.showToast({
                        title: t.data.info,
                        icon: "success",
                        duration: 1e3
                    }), setTimeout(function() {
                        wx.navigateBack({
                            delta: 1
                        });
                    }, 1500));
                },
                fail: function() {
                    wx.showToast({
                        title: "网络异常！",
                        duration: 2e3
                    });
                }
            }) : wx.showToast({
                title: "手机号码格式错误,请重新输入!",
                icon: "none",
                duration: 1e3
            });
        }
    }
});