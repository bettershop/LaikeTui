var t = getApp(), a = require("../../utils/util.js");

Page({
    data: {
        ismask: !1,
        productId: 0,
        pop: null,
        spcounts: 0,
        spprice: "0",
        page: 1,
        list: []
    },
    onLoad: function(t) {
        this.loadProductDetail();
    },
    loadProductDetail: function() {
        var a = this;
        console.log(a);
        var i = t.globalData.userInfo.openid ? t.globalData.userInfo.openid : "";
        wx.request({
            url: t.d.ceshiUrl + "&action=activities&m=pro",
            method: "post",
            data: {
                id: a.options.id,
                openid: i,
                page: this.data.page
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                var i = a.data.list, e = t.data.title;
                i = i.concat(t.data.list), a.setData({
                    list: i,
                    title: e,
                    page: ++a.data.page
                }), wx.setNavigationBarTitle({
                    title: e,
                    success: function() {}
                });
            },
            fail: function(t) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    onReady: function() {
        this.pop = this.selectComponent("#pop");
    },
    onShow: function() {},
    onHide: function() {},
    onUnload: function() {},
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500);
    },
    onReachBottom: function() {
        this.loadProductDetail();
    },
    onShareAppMessage: function() {},
    maskopen: function(a) {
        if (t.userlogin(1)) this.pop.clickPup(this); else {
            var i = a.target.dataset.index, e = this.data.list[i].pro_id;
            this.setShoppingCart(e);
        }
    },
    setShoppingCart: function(a) {
        var i = this;
        if (t.userlogin(1)) this.pop.clickPup(this); else {
            var e = t.globalData.userInfo.openid;
            wx.request({
                url: t.d.ceshiUrl + "&action=product&m=index",
                method: "post",
                data: {
                    pro_id: a,
                    openid: e || "",
                    type1: this.data.type1,
                    choujiangid: this.data.choujiangid,
                    role: this.options.role ? this.options.role : "",
                    size: this.data.size,
                    userid: this.data.userid
                },
                header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function(t) {
                    var e = t.data;
                    if (0 !== e.status) {
                        e.number = 1;
                        var s = !0, o = !1, n = void 0;
                        try {
                            for (var r, d = e.attrList[Symbol.iterator](); !(s = (r = d.next()).done); s = !0) {
                                var u = r.value, l = !0, c = !1, f = void 0;
                                try {
                                    for (var h, p = u.attr[Symbol.iterator](); !(l = (h = p.next()).done); l = !0) {
                                        h.value.select = !0;
                                        break;
                                    }
                                } catch (t) {
                                    c = !0, f = t;
                                } finally {
                                    try {
                                        !l && p.return && p.return();
                                    } finally {
                                        if (c) throw f;
                                    }
                                }
                            }
                        } catch (t) {
                            o = !0, n = t;
                        } finally {
                            try {
                                !s && d.return && d.return();
                            } finally {
                                if (o) throw n;
                            }
                        }
                        i.setData({
                            deffs: e,
                            spid: a,
                            sizeid: e.skuBeanList[0].cid,
                            spcounts: e.skuBeanList[0].count,
                            spprice: e.skuBeanList[0].price,
                            imgurls: e.skuBeanList[0].imgurl
                        }), i.setData({
                            ismask: !0
                        });
                    } else wx.showToast({
                        title: e.err,
                        icon: "none",
                        duration: 2e3
                    });
                },
                error: function(t) {
                    wx.showToast({
                        title: "网络异常！",
                        duration: 2e3
                    });
                }
            });
        }
    },
    maskclose: function() {
        this.setData({
            ismask: !1
        });
    },
    setNumberLess: function() {
        var t = this.data.deffs.number;
        1 !== t ? (this.data.deffs.number = t - 1, this.setData({
            deffs: this.data.deffs
        })) : wx.showToast({
            title: "不能再少了",
            icon: "none",
            duration: 1e3
        });
    },
    setNumberplus: function() {
        var t = this.data.deffs.number;
        t >= parseInt(this.data.spcounts) ? wx.showToast({
            title: "不能再多了",
            icon: "none",
            duration: 1e3
        }) : (this.data.deffs.number = t + 1, this.setData({
            deffs: this.data.deffs
        }));
    },
    jumpgo: function(t) {
        var a = t.currentTarget.dataset.id;
        wx.navigateTo({
            url: a
        });
    },
    setSizeid: function(t) {
        var a = t.target.dataset, i = a.index, e = a.name, s = a.id, o = a.sid, n = this.data.deffs.attrList[i].attr, r = this.data.deffs.skuBeanList, d = !0, u = !1, l = void 0;
        try {
            for (var c, f = n[Symbol.iterator](); !(d = (c = f.next()).done); d = !0) {
                var h = c.value;
                h.attributeValue != e ? h.select = !1 : h.select = !0;
            }
        } catch (t) {
            u = !0, l = t;
        } finally {
            try {
                !d && f.return && f.return();
            } finally {
                if (u) throw l;
            }
        }
        var p = !0, w = !1, v = void 0;
        try {
            for (var m, g = r[Symbol.iterator](); !(p = (m = g.next()).done); p = !0) {
                var y = m.value, x = !0, b = !1, D = void 0;
                try {
                    for (var T, k = y.attributes[Symbol.iterator](); !(x = (T = k.next()).done); x = !0) {
                        var L = T.value;
                        L.attributeId == o && L.attributeValId == s && this.setData({
                            sizeid: y.cid,
                            spcounts: y.count,
                            spprice: y.price,
                            imgurls: y.imgurl
                        });
                    }
                } catch (t) {
                    b = !0, D = t;
                } finally {
                    try {
                        !x && k.return && k.return();
                    } finally {
                        if (b) throw D;
                    }
                }
            }
        } catch (t) {
            w = !0, v = t;
        } finally {
            try {
                !p && g.return && g.return();
            } finally {
                if (w) throw v;
            }
        }
        this.data.deffs.attrList[i].attr = n, this.setData({
            deffs: this.data.deffs
        });
    },
    submit: function(t) {
        this.addShopCart();
    },
    addShopCart: function() {
        var i = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=product&m=add_cart",
            method: "post",
            data: {
                uid: t.globalData.userInfo.openid,
                pid: this.data.spid,
                num: this.data.deffs.number,
                sizeid: this.data.sizeid,
                pro_type: "addcart"
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                var s = e.data;
                1 == s.status ? (wx.showToast({
                    title: "加入购物车成功",
                    icon: "success",
                    duration: 2e3
                }), i.maskclose(), a.getUesrBgplus(i, t, !1)) : wx.showToast({
                    icon: "loading",
                    title: s.err,
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