var t = getApp(), a = require("../../utils/util.js"), e = require("../../wxParse/wxParse.js");

Page({
    data: {
        pop: null,
        bannerApp: !0,
        maskHidden: !1,
        winWidth: 0,
        winHeight: 0,
        currentTab: 0,
        productId: 0,
        itemData: {},
        wsc: "bxs",
        sc: "bxs",
        paytype: "buynow",
        sizeid: "",
        remind: !0,
        bannerItem: [],
        select: [],
        buynum: 1,
        value: !1,
        autoplay: !0,
        interval: 5e3,
        duration: 1e3,
        xefl: !0,
        firstIndex: -1,
        commodityAttr: [],
        attrValueList: [],
        show_share: !1
    },
    user_share: function() {
        var a = this;
        wx.showToast({
            title: "图片生成中",
            icon: "loading",
            duration: 1500
        }), t.request.wxRequest({
            url: "&action=getcode&m=product_share",
            data: {
                product_img_path: a.data.itemData.photo_d,
                product_title: a.data.title,
                price: a.data.itemData.price_yh,
                yprice: a.data.itemData.price,
                scene: "productId=" + a.data.productId + "&referee_openid=" + t.globalData.userInfo.user_id,
                path: "pages/product/detail",
                id: t.globalData.userInfo.user_id,
                pid: a.data.productId,
                head: t.globalData.userInfo.avatarUrl,
                name: t.globalData.userInfo.nickName,
                type: 3
            },
            method: "post",
            success: function(t) {
                a.setData({
                    maskHidden: !0,
                    imagePath: t.url
                });
            }
        });
        var e = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        });
        a.animation = e, e.translateY(300).step(), a.setData({
            animationData: e.export()
        }), setTimeout(function() {
            e.translateY(0).step(), a.setData({
                animationData: e,
                show_share: !1
            });
        }.bind(a), 200);
    },
    onReady: function() {
        this.pop = this.selectComponent("#pop");
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500), this.loadProductDetail();
    },
    onLoad: function(a) {
        console.log(a);
        var e = decodeURIComponent(a.scene), i = this;
        "undefined" != e && e.length > 1 && "" != e && (a = e), i.initNavHeight(), "" != a.referee_openid ? t.globalData.userInfo.referee_openid = a.referee_openid : t.globalData.userInfo.referee_openid = "", 
        i.setData({
            productId: a.productId,
            userid: !!a.userid && a.userid,
            choujiangid: a.choujiangid ? a.choujiangid : "",
            type1: a.type1 ? a.type1 : "",
            role: a.role ? a.role : "",
            size: a.size ? a.size : "",
            earn: !!a.earn && a.earn
        }), i.loadProductDetail();
    },
    onShow: function() {},
    getUserformid: function(t) {
        var a = t.detail.formId;
        this.sendFormid(a, "kt1"), this.setModalStatus(t);
    },
    sendFormid: function(a, e) {
        t.request.wxRequest({
            url: "&action=draw&m=getFormid",
            data: {
                from_id: a,
                userid: t.globalData.userInfo.openid,
                page: e
            },
            method: "post",
            success: function() {}
        });
    },
    loadProductDetail: function() {
        var i = this, s = (i.data.choujiangid, t.globalData.userInfo.openid);
        console.log(t.globalData.userInfo, "openid");
        var o = t.d.bgcolor;
        wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: o,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), console.log(i.data.userid), wx.request({
            url: t.d.ceshiUrl + "&action=activities&m=index",
            method: "post",
            data: {
                pro_id: i.data.productId,
                openid: s,
                type1: i.data.type1,
                choujiangid: i.data.choujiangid,
                role: i.options.role ? i.options.role : "",
                size: i.data.size,
                userid: i.data.userid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(s) {
                var n = s.data.status, r = s.data.type;
                if (1 == n) {
                    var d = s.data.pro, u = d.content;
                    e.wxParse("content", "html", u, i, 5), i.setData({
                        bgcolor: o,
                        itemData: d,
                        kucun: d.num,
                        bannerItem: d.img_arr,
                        share: s.data.share,
                        title: d.name,
                        is_zhekou: d.is_zhekou,
                        comments: s.data.comments,
                        is_shou: s.data.type,
                        collection_id: s.data.collection_id,
                        choujiangid: i.data.choujiangid,
                        role: i.data.role ? i.data.role : "",
                        qj_price: s.data.qj_price,
                        qj_yprice: s.data.qj_yprice,
                        attrList: s.data.attrList,
                        skuBeanList: s.data.skuBeanList,
                        zhekou: "" != s.data.zhekou && s.data.zhekou
                    }), a.getUesrBgplus(i, t, !0), setTimeout(function() {
                        i.setData({
                            remind: !1
                        });
                    }, 1e3), i.one();
                } else 3 == n ? (wx.showToast({
                    title: s.data.err,
                    duration: 2e3
                }), wx.redirectTo({
                    url: "../../pages/draw/draw"
                })) : (setTimeout(function() {
                    a.getUesrBgplus(i, t, !0), a.getUesrBgplus(i, t, !1), wx.switchTab({
                        url: "../index/indexs"
                    });
                }, 2e3), wx.showToast({
                    title: s.data.err,
                    duration: 2e3
                }));
                r ? i.setData({
                    wsc: "bxs",
                    sc: "xs"
                }) : i.setData({
                    wsc: "xs",
                    sc: "bxs"
                });
            },
            error: function(t) {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    setModalStatus: function(t) {
        var a = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        }), e = !!t.target.dataset.type && t.target.dataset.type;
        e ? this.setData({
            xefl: !1
        }) : (this.setData({
            xefl: !0
        }), e = this.data.paytype), this.animation = a, a.translateY(300).step(), this.setData({
            paytype: e,
            animationData: a.export()
        }), 1 == t.currentTarget.dataset.status && this.setData({
            showModalStatus: !0
        }), setTimeout(function() {
            a.translateY(0).step(), this.setData({
                animationData: a
            }), 0 == t.currentTarget.dataset.status && this.setData({
                showModalStatus: !1
            });
        }.bind(this), 200);
    },
    changeNum: function(t) {
        var a = this, e = a.data.itemData.num;
        0 == t.target.dataset.alphaBeta ? this.data.buynum <= 1 ? wx.showToast({
            title: "不能再少了",
            icon: "none",
            duration: 1e3
        }) : this.setData({
            buynum: this.data.buynum - 1
        }) : a.data.buynum < e ? this.setData({
            buynum: this.data.buynum + 1
        }) : wx.showToast({
            title: "不能再多了",
            icon: "none",
            duration: 1e3
        });
    },
    one: function() {
        for (var t = this.data.attrList, a = this.data.skuBeanList, e = a[0], i = 0; i < t.length; i++) for (var s = 0; s < t[i].attr.length; s++) for (var o = 0; o < e.attributes.length; o++) e.attributes[o].attributeId == t[i].attr[s].attributeId && e.attributes[o].attributeValId == t[i].attr[s].id && (t[i].attr[s].select = !0);
        var n = this.data.itemData;
        n.photo_x = e.imgurl, n.price_yh = e.price, n.num = e.count;
        var r = e.cid;
        this.setData({
            attrList: t,
            skuBeanList: a,
            itemData: n,
            sizeid: r,
            value: e.name
        }), this.onData();
    },
    t_index: function() {
        wx.switchTab({
            url: "../index/indexs"
        });
    },
    go_cart: function() {
        a.getUesrBgplus(this, t, !1), wx.switchTab({
            url: "../cart/cart"
        });
    },
    onData: function() {
        for (var t = this.data.attrList, a = 0; a < t.length; a++) {
            for (var e = t[a], i = [], s = 0; s < t.length; s++) {
                var o = t[s];
                if (o.id != e.id) for (p = 0; p < o.attr.length; p++) {
                    var n = o.attr[p];
                    n.enable && n.select && i.push(n);
                }
            }
            for (var r = [], d = this.data.skuBeanList, u = 0; u < d.length; u++) {
                for (var c = !0, l = d[u], s = 0; s < i.length; s++) {
                    for (var h = !1, p = 0; p < l.attributes.length; p++) {
                        g = l.attributes[p];
                        if (i[s].attributeId == g.attributeId && i[s].id == g.attributeValId) {
                            h = !0;
                            break;
                        }
                    }
                    c = h && c;
                }
                if (c) for (var f = 0; f < l.attributes.length; f++) {
                    var g = l.attributes[f];
                    e.id == g.attributeId && r.push(g.attributeValId);
                }
            }
            for (var m = r, w = 0; w < e.attr.length; w++) {
                var x = e.attr[w];
                x.enable = -1 != m.indexOf(x.id);
            }
        }
        this.setData({
            attrList: t,
            skuBeanList: d
        });
    },
    onChangeShowState: function(t) {
        var a = this, e = this.data.attrList, i = e[t.currentTarget.dataset.idx], s = i.attr[t.currentTarget.dataset.index];
        if (s.enable) {
            for (var o = !s.select, n = 0; n < i.attr.length; n++) i.attr[n].select = !1;
            s.select = o;
            for (var r = [], d = 0; d < e.length; d++) for (var u = 0; u < e[d].attr.length; u++) e[d].attr[u].enable && e[d].attr[u].select && r.push(e[d].attr[u]);
            for (var c = "", l = this.data.skuBeanList, h = [], p = 0; p < l.length; p++) {
                for (var f = 0, g = 0; g < l[p].attributes.length; g++) r.length == l[p].attributes.length ? l[p].attributes[g].attributeValId == r[g].id && f++ : c = "库存清单不存在此属性 ";
                f == l[p].attributes.length && h.push(l[p]);
            }
            for (var m = 0; m < r.length; m++) c += r[m].attributeValue + " ";
            if (0 != h.length) {
                var w = a.data.itemData;
                w.photo_x = h[0].imgurl, w.price_yh = h[0].price, w.num = h[0].count;
                var x = a.data.choujiangid, b = h[0].cid;
                a.setData({
                    itemData: w,
                    sizeid: b,
                    choujiangid: x,
                    value: c
                });
            } else a.setData({
                sizeid: "",
                value: ""
            });
            this.setData({
                attrList: e,
                infoText: c
            }), this.onData();
        }
    },
    submit: function(t) {
        var a = this;
        if ("" == (e = a.data.sizeid) || e.length < 1) wx.showToast({
            title: "请完善属性",
            icon: "loading",
            duration: 1e3
        }); else {
            t.target.dataset.type;
            var e = e;
            a.addShopCart(t, e);
        }
    },
    addShopCart: function(e, i) {
        var s = this, o = e.target.dataset.type;
        e.currentTarget.dataset.type;
        wx.request({
            url: t.d.ceshiUrl + "&action=product&m=add_cart",
            method: "post",
            data: {
                uid: t.globalData.userInfo.openid,
                pid: s.data.productId,
                num: s.data.buynum,
                sizeid: i,
                pro_type: o
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(i) {
                t.d.purchase = 1;
                var o = i.data;
                if (1 == o.status) {
                    if ("buynow" == e.currentTarget.dataset.type) return void wx.redirectTo({
                        url: "../order/pay?cartId=" + o.cart_id + "&pid=" + s.data.productId + "&num=" + s.data.buynum + "&type=1"
                    });
                    wx.showToast({
                        title: "加入购物车成功",
                        icon: "success",
                        duration: 2e3
                    }), a.getUesrBgplus(s, t, !0), a.getUesrBgplus(s, t, !1), s.setData({
                        showModalStatus: !1
                    });
                } else wx.showToast({
                    icon: "loading",
                    title: o.err,
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
    },
    bindChange: function(t) {
        this.setData({
            currentTab: t.detail.current
        });
    },
    initNavHeight: function() {
        var t = this;
        wx.getSystemInfo({
            success: function(a) {
                t.setData({
                    winWidth: a.windowWidth,
                    winHeight: a.windowHeight
                });
            }
        });
    },
    bannerClosed: function() {
        this.setData({
            bannerApp: !1
        });
    },
    swichNav: function(t) {
        var a = this;
        if (a.data.currentTab === t.target.dataset.current) return !1;
        a.setData({
            currentTab: t.target.dataset.current
        });
    },
    onShareAppMessage: function(a) {
        var e = this, i = e.data.productId, s = (e.data.type1, (t.globalData.userInfo.nickName ? t.globalData.userInfo.nickName + "超值推荐 " : "我发现一个好的东西 推荐给你们 ") + e.data.title), o = t.globalData.userInfo.user_id;
        return a.from, console.log("pages/product/detail?productId=" + i + "&referee_openid=" + o), 
        {
            title: s,
            imageUrl: e.data.bannerItem[0],
            path: "pages/product/detail?productId=" + i + "&referee_openid=" + o,
            success: function(t) {
                console.log("转发成功");
                var a = wx.createAnimation({
                    duration: 200,
                    timingFunction: "linear",
                    delay: 0
                });
                e.animation = a, a.translateY(300).step(), e.setData({
                    animationData: a.export()
                }), setTimeout(function() {
                    a.translateY(0).step(), e.setData({
                        animationData: a,
                        show_share: !1
                    });
                }.bind(e), 200);
            },
            fail: function(t) {
                console.log("转发失败");
            }
        };
    },
    addFavorites: function(a) {
        if (t.userlogin(1)) this.pop.clickPup(this); else {
            var e = this;
            wx.request({
                url: t.d.ceshiUrl + "&action=addFavorites&m=index",
                method: "post",
                data: {
                    openid: t.globalData.userInfo.openid,
                    pid: e.data.productId
                },
                header: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                success: function(t) {
                    var a = t.data;
                    1 == a.status ? (wx.showToast({
                        title: a.succ,
                        duration: 2e3
                    }), e.setData({
                        wsc: "bxs",
                        sc: "xs",
                        collection_id: a.id
                    }), e.data.itemData.isCollect = !1) : (wx.showToast({
                        title: a.err,
                        duration: 2e3
                    }), e.setData({
                        wsc: "bxs",
                        sc: "xs"
                    }));
                },
                fail: function() {
                    wx.showToast({
                        title: "网络异常！",
                        duration: 2e3
                    });
                }
            });
        }
    },
    delFavorites: function(a) {
        var e = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=addFavorites&m=removeFavorites",
            method: "post",
            data: {
                id: e.data.collection_id
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                var a = t.data;
                1 == a.status ? (wx.showToast({
                    title: a.succ,
                    duration: 2e3
                }), e.setData({
                    wsc: "xs",
                    sc: "bxs"
                })) : (wx.showToast({
                    title: a.err,
                    duration: 2e3
                }), e.setData({
                    wsc: "xs",
                    sc: "bxs"
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
    previewImage: function(t) {
        var a = t.target.dataset.src, e = [ a ];
        wx.previewImage({
            current: a,
            urls: e
        });
    },
    preventTouchMove: function(t) {},
    add_fromid: function(a) {
        if (t.userlogin(1)) this.pop.clickPup(this); else {
            var e = a.detail.formId, i = wx.createAnimation({
                duration: 200,
                timingFunction: "linear",
                delay: 0
            }), s = !!a.detail.target.dataset.type && a.detail.target.dataset.type;
            if (s ? this.setData({
                xefl: !1
            }) : (this.setData({
                xefl: !0
            }), s = this.data.paytype), this.animation = i, i.translateY(300).step(), this.setData({
                paytype: s,
                animationData: i.export()
            }), 1 == a.detail.target.dataset.status && this.setData({
                showModalStatus: !0
            }), setTimeout(function() {
                i.translateY(0).step(), this.setData({
                    animationData: i
                }), 0 == a.detail.target.dataset.status && this.setData({
                    showModalStatus: !1
                });
            }.bind(this), 200), "the formId is a mock one" != e) {
                t.request.wxRequest({
                    url: "&action=product&m=save_formid",
                    data: {
                        from_id: e,
                        userid: t.globalData.userInfo.openid
                    },
                    method: "post",
                    success: function(t) {}
                });
            }
        }
    },
    set_share: function(a) {
        if (t.userlogin(1)) this.pop.clickPup(this); else {
            var e = this, i = (e.data.show_share, wx.createAnimation({
                duration: 200,
                timingFunction: "linear",
                delay: 0
            }));
            e.animation = i, i.translateY(300).step(), e.setData({
                animationData: i.export()
            }), 1 == a.currentTarget.dataset.status && e.setData({
                show_share: !0
            }), setTimeout(function() {
                i.translateY(0).step(), e.setData({
                    animationData: i
                }), 0 == a.currentTarget.dataset.status && e.setData({
                    show_share: !1
                });
            }.bind(this), 200);
        }
    },
    baocun: function() {
        var t = this;
        wx.getSetting({
            success: function(t) {
                t.authSetting["scope.writePhotosAlbum"] || wx.authorize({
                    scope: "scope.writePhotosAlbum",
                    success: function() {
                        console.log("授权成功");
                    },
                    fail: function(t) {
                        wx.openSetting({
                            success: function(t) {
                                t.authSetting = {
                                    "scope.userInfo": !0,
                                    "scope.userLocation": !0,
                                    "scope.writePhotosAlbum": !0
                                };
                            }
                        });
                    }
                });
            }
        }), wx.downloadFile({
            url: t.data.imagePath,
            success: function(a) {
                var e = a.tempFilePath;
                wx.saveImageToPhotosAlbum({
                    filePath: e,
                    success: function(a) {
                        wx.showModal({
                            content: "图片已保存到相册，赶紧晒一下吧~",
                            showCancel: !1,
                            confirmText: "好的",
                            confirmColor: "#333",
                            success: function(a) {
                                a.confirm && (console.log("用户点击确定"), t.setData({
                                    maskHidden: !1
                                }));
                            },
                            fail: function(t) {}
                        });
                    }
                });
            }
        });
    },
    close_share: function(t) {
        this.setData({
            maskHidden: !1
        });
    }
});