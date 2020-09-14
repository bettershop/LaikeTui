var t = getApp(), a = require("../../utils/util.js");

Page({
    data: {
        page: 1,
        minusStatuses: [ "disabled", "disabled", "normal", "normal", "disabled" ],
        total: 0,
        carts: [],
        cont: 1,
        upstatus: !1,
        remind: "加载中"
    },
    onReady: function() {},
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500), this.loadProductData(), this.sum();
    },
    bindMinus: function(s) {
        var e = this, o = parseInt(s.currentTarget.dataset.index), r = e.data.carts[o].num, i = s.currentTarget.dataset.cartid;
        r > 1 && r--, r < 1 ? wx.showToast({
            title: "数量不能小于1!",
            icon: "none",
            duration: 2e3
        }) : wx.request({
            url: t.d.ceshiUrl + "&action=product&m=up_cart",
            method: "post",
            data: {
                user_id: e.data.user_id,
                num: r,
                cart_id: i
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(s) {
                if (1 == s.data.status) {
                    a.getUesrBgplus(e, t, !1);
                    var i = r <= 1 ? "disabled" : "normal";
                    e.data.carts[o].num = r;
                    var n = e.data.minusStatuses;
                    n[o] = i, e.setData({
                        minusStatuses: n
                    }), e.sum();
                }
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    go_index: function() {
        wx.switchTab({
            url: "../index/index"
        });
    },
    delall: function() {
        var s = this;
        wx.showModal({
            title: "提示",
            content: "你确认清空全部吗?",
            success: function(e) {
                e.confirm && wx.request({
                    url: t.d.ceshiUrl + "&action=product&m=delAll_cart",
                    method: "post",
                    data: {
                        user_id: s.data.user_id
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(e) {
                        1 == e.data.status ? (wx.showToast({
                            title: "操作成功！",
                            duration: 2500
                        }), s.loadProductData(), a.getUesrBgplus(s, t, !1)) : wx.showToast({
                            title: "操作失败！",
                            duration: 2e3
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
    },
    ok: function() {
        this.setData({
            upstatus: !1
        });
    },
    updata: function() {
        this.setData({
            upstatus: !0
        });
    },
    delarr: function() {
        for (var t = this, a = "", s = 0; s < this.data.carts.length; s++) this.data.carts[s].selected && (a += this.data.carts[s].id, 
        a += ",");
        if ("" == a) return wx.showToast({
            title: "请选择要删除的商品！",
            duration: 2e3
        }), !1;
        t.removeShopCard(a);
    },
    shouc: function() {
        for (var s = this, e = "", o = 0; o < this.data.carts.length; o++) this.data.carts[o].selected && (e += this.data.carts[o].id, 
        e += ",");
        if ("" == e) return wx.showToast({
            title: "请选择要收藏的商品！",
            duration: 2e3
        }), !1;
        wx.request({
            url: t.d.ceshiUrl + "&action=product&m=to_Collection",
            method: "post",
            data: {
                user_id: s.data.user_id,
                carts: e
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                a.getUesrBgplus(s, t, !1), 1 == e.data.status ? (wx.showToast({
                    title: "操作成功！",
                    duration: 2e3
                }), s.loadProductData()) : wx.showToast({
                    title: "操作失败！",
                    duration: 2e3
                });
            }
        });
    },
    bindPlus: function(s) {
        var e = this, o = parseInt(s.currentTarget.dataset.index), r = e.data.carts[o].num;
        r++;
        var i = e.data.carts[o].pnum, n = s.currentTarget.dataset.cartid;
        console.log(i), i > r ? wx.request({
            url: t.d.ceshiUrl + "&action=product&m=up_cart",
            method: "post",
            data: {
                user_id: e.data.user_id,
                num: r,
                cart_id: n
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(s) {
                if (1 == s.data.status) {
                    a.getUesrBgplus(e, t, !1);
                    var i = r <= 1 ? "disabled" : "normal";
                    e.data.carts[o].num = r;
                    var n = e.data.minusStatuses;
                    n[o] = i, e.setData({
                        minusStatuses: n
                    }), e.sum();
                }
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        }) : wx.showToast({
            title: "库存不足！",
            icon: "none",
            duration: 2e3
        });
    },
    bindCheckbox: function(t) {
        var a = parseInt(t.currentTarget.dataset.index), s = this.data.carts[a].selected, e = this.data.carts;
        e[a].selected = !s, this.setData({
            carts: e
        }), this.sum();
    },
    bindSelectAll: function() {
        var t = this.data.selectedAllStatus;
        t = !t;
        for (var a = this.data.carts, s = 0; s < a.length; s++) a[s].selected = t;
        this.setData({
            selectedAllStatus: t,
            carts: a
        }), this.sum();
    },
    bindCheckout: function() {
        for (var t = "", a = 0; a < this.data.carts.length; a++) this.data.carts[a].selected && (t += this.data.carts[a].id, 
        t += ",");
        if ("" == t) return wx.showToast({
            title: "请选择要结算的商品！",
            duration: 2e3
        }), !1;
        wx.navigateTo({
            url: "../order/pay?cartId=" + t
        });
    },
    bindToastChange: function() {
        this.setData({
            toastHidden: !0
        });
    },
    sum: function() {
        for (var t = this, a = t.data.carts, s = 0, e = 0, o = 0; o < a.length; o++) a[o].selected && (s += a[o].num * a[o].price, 
        e = ++e);
        a.length == e && 0 != e ? t.setData({
            selectedAllStatus: !0
        }) : t.setData({
            selectedAllStatus: !1
        }), this.setData({
            carts: a,
            total: "¥ " + s.toFixed(2)
        });
    },
    onLoad: function(a) {
        this.setData({
            bgcolor: t.d.bgcolor,
            user_id: t.globalData.userInfo.openid
        }), wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor
        }), this.loadProductData(), this.sum();
    },
    onShow: function() {
        var a = this.data.cont, s = this.data.carts, e = t.d.purchase, o = this;
        o.loadProductData(), a > 1 && 1 == e || o.setData({
            carts: s,
            cont: a + 1
        });
    },
    removeShopCard: function(s) {
        var e = this;
        wx.showModal({
            title: "提示",
            content: "你确认移除吗",
            success: function(o) {
                o.confirm && wx.request({
                    url: t.d.ceshiUrl + "&action=product&m=delcart",
                    method: "post",
                    data: {
                        carts: s
                    },
                    header: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    success: function(s) {
                        a.getUesrBgplus(e, t, !1), 1 == s.data.status ? e.loadProductData() : wx.showToast({
                            title: "操作失败！",
                            duration: 2e3
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
    },
    loadProductData: function() {
        var a = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=product&m=Shopping",
            method: "post",
            data: {
                user_id: t.globalData.userInfo.openid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                var s = t.data.cart;
                a.setData({
                    carts: s,
                    selectedAllStatus: !1,
                    total: "￥0.00",
                    remind: ""
                });
            }
        });
    },
    bindManual: function(a) {
        var s = a.detail.value, e = a.target.dataset.cartid, o = this.data.carts, r = this, i = parseInt(a.currentTarget.dataset.index), n = r.data.carts[i].num, d = (a.currentTarget.dataset.cartid, 
        r.data.carts[i].pnum);
        if (console.log(s, n, d), Number(s) > 0) if (Number(s) <= Number(d)) wx.request({
            url: t.d.ceshiUrl + "&action=product&m=up_cart",
            method: "post",
            data: {
                user_id: r.data.user_id,
                num: s,
                cart_id: e
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (1 == t.data.status) {
                    var a = s <= 1 ? "disabled" : "normal";
                    r.data.carts[i].num = s;
                    var e = r.data.minusStatuses;
                    e[i] = a, r.setData({
                        minusStatuses: e
                    }), r.sum();
                }
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        }); else {
            wx.showToast({
                title: "库存不足,请重新输入！",
                icon: "none",
                duration: 2e3
            });
            o = r.data.carts;
            r.setData({
                carts: o
            }), r.sum();
        } else {
            wx.showToast({
                title: "数量不能小于1,请重新输入！",
                icon: "none",
                duration: 2e3
            });
            o = r.data.carts;
            r.setData({
                carts: o
            }), r.sum();
        }
    }
});