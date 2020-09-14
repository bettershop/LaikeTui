var a = getApp();

Page({
    data: {
        address: [],
        radioindex: "",
        pro_id: 0,
        num: 0,
        flag: !1,
        cartId: 0,
        numbers: ""
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500), this.DataonLoad();
    },
    onShow: function() {
        this.DataonLoad();
    },
    updelall: function(t) {
        var e = this;
        console.log(t), wx.showModal({
            title: "提示",
            content: "你确认移除吗?",
            success: function(o) {
                o.confirm ? (console.log(11), wx.request({
                    url: a.d.ceshiUrl + "&action=address&m=del_select",
                    data: {
                        openid: a.globalData.userInfo.openid,
                        id_arr: t
                    },
                    method: "POST",
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(a) {
                        1 == a.data.status ? (wx.showToast({
                            title: a.data.succ,
                            duration: 2e3
                        }), e.setData({
                            flag: !1
                        }), e.DataonLoad()) : wx.showToast({
                            title: a.data.err,
                            duration: 2e3
                        });
                    }
                })) : (e.DataonLoad(), e.setData({
                    flag: !1
                }));
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
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
            bgcolor: a.d.bgcolor
        });
        var e = this;
        if (t.cartId) o = t.cartId; else var o = 0;
        wx.request({
            url: a.d.ceshiUrl + "&action=address&m=index",
            data: {
                openid: a.globalData.userInfo.openid
            },
            method: "POST",
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(a) {
                if ("" == (t = a.data.adds)) var t = [];
                e.setData({
                    address: t,
                    cartId: o
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
    bindCheckbox: function(a) {
        var t = parseInt(a.currentTarget.dataset.index);
        console.log(t);
        var e = this.data.address[t].selected;
        console.log(e);
        var o = this.data.address;
        console.log(o), o[t].selected = !e, console.log(o[t].selected), this.setData({
            address: o
        }), this.sum();
    },
    bindSelectAll: function() {
        var a = this.data.selectedAllStatus;
        a = !a;
        for (var t = this.data.address, e = 0; e < t.length; e++) t[e].selected = a;
        this.setData({
            selectedAllStatus: a,
            address: t
        }), this.sum();
    },
    delarr: function() {
        for (var a = this, t = "", e = 0; e < this.data.address.length; e++) this.data.address[e].selected && (t += this.data.address[e].id, 
        t += ",");
        if ("" == t) return wx.showToast({
            title: "请选择要删除的商品！",
            duration: 2e3
        }), !1;
        a.updelall(t);
    },
    sum: function() {
        for (var a = this, t = a.data.address, e = 0, o = 0; o < t.length; o++) t[o].selected && (e = ++e);
        t.length == e && 0 != e ? a.setData({
            selectedAllStatus: !0
        }) : a.setData({
            selectedAllStatus: !1
        }), this.setData({
            address: t
        });
    },
    upflag: function() {
        this.setData({
            flag: !0
        });
    },
    uptrue: function() {
        var a = this;
        a.setData({
            flag: !1
        }), a.DataonLoad();
    },
    setDefault: function(t) {
        var e = this, o = t.currentTarget.dataset.id;
        console.log(e.data.flag), wx.showModal({
            content: "确认修改地址",
            success: function(t) {
                console.log(t.confirm), t.confirm ? wx.request({
                    url: a.d.ceshiUrl + "&action=address&m=set_default",
                    data: {
                        openid: a.globalData.userInfo.openid,
                        addr_id: o
                    },
                    method: "POST",
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(a) {
                        var t = a.data.status, o = e.data.cartId;
                        if (1 == t) {
                            if ("0" != o || "group" == o) return wx.navigateBack({
                                delta: 1
                            }), !1;
                            wx.showToast({
                                title: a.data.err,
                                duration: 2e3
                            }), e.DataonLoad();
                        } else wx.showToast({
                            title: a.data.err,
                            duration: 2e3
                        });
                    },
                    fail: function() {
                        wx.showToast({
                            title: "网络异常！",
                            duration: 2e3
                        });
                    }
                }) : e.DataonLoad();
            }
        });
    },
    delAddress: function(t) {
        var e = this, o = t.currentTarget.dataset.id;
        wx.showModal({
            title: "提示",
            content: "你确认移除吗",
            success: function(t) {
                t.confirm && wx.request({
                    url: a.d.ceshiUrl + "&action=address&m=del_adds",
                    data: {
                        openid: a.globalData.userInfo.openid,
                        id_arr: o
                    },
                    method: "POST",
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(a) {
                        1 == a.data.status ? e.DataonLoad() : wx.showToast({
                            title: a.data.err,
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
            }
        });
    },
    DataonLoad: function() {
        var t = this;
        wx.request({
            url: a.d.ceshiUrl + "&action=address&m=index",
            data: {
                openid: a.globalData.userInfo.openid
            },
            method: "POST",
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(a) {
                e = a.data.adds;
                if (t.numbers = a.data.adds, "" == e) var e = [];
                t.setData({
                    address: e,
                    selectedAllStatus: !1
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
    upaddress: function(a) {
        var t = a.currentTarget.dataset.id;
        wx.navigateTo({
            url: "../address/upaddress/upaddress?addr_id=" + t
        });
    }
});