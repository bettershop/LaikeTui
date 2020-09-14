var t = getApp();

Page({
    data: {
        isStatus: "whole",
        currentTab: 0,
        orderList0: [],
        orderList1: [],
        remind: "加载中",
        showModalStatus: !1,
        address: []
    },
    onLoad: function(a) {
        console.log(a), wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), this.setData({
            currentTab: parseInt(a.currentTab),
            bgcolor: t.d.bgcolor
        }), this.loadList();
    },
    onReady: function() {
        var t = this;
        setTimeout(function() {
            t.setData({
                remind: ""
            });
        }, 1e3);
    },
    jujue: function(t) {
        var a = this;
        console.log(t);
        var e = t.currentTarget.dataset.index, o = a.data.orderList0[e].r_content;
        wx.showToast({
            title: "拒绝理由:" + o,
            icon: "none",
            duration: 4e3
        });
    },
    go_kd: function(t) {
        var a = this;
        console.log(t);
        var e = t.currentTarget.dataset.index, o = a.data.orderList0[e].r_content;
        wx.showToast({
            title: o,
            icon: "none",
            duration: 4e3
        });
    },
    kdtj: function(a) {
        var e = this, o = a.detail.value.kdcode, n = a.detail.value.kdname, s = a.detail.value.lxdh, i = a.detail.value.lxr;
        a.detail.formId;
        o.length > 8 && n.length > 2 && 11 == s.length && i.length > 1 ? (console.log("OK"), 
        wx.request({
            url: t.d.ceshiUrl + "&action=order&m=back_send",
            method: "post",
            data: {
                kdcode: o,
                kdname: n,
                lxdh: s,
                lxr: i,
                uid: t.globalData.userInfo.openid,
                oid: e.data.id
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                console.log(t), t.data.status ? (e.setData({
                    showModalStatus: !1
                }), wx.showToast({
                    title: "提交成功！",
                    duration: 2e3
                }), e.loadList()) : (wx.showToast({
                    title: "提交失败！",
                    icon: "none",
                    duration: 2e3
                }), e.setData({
                    showModalStatus: !1
                }));
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        })) : wx.showToast({
            title: "输入信息有误！",
            icon: "none",
            duration: 2e3
        });
    },
    bindPickerChange: function(t) {
        console.log("picker发送选择改变，携带值为", t.detail.value), this.setData({
            index: t.detail.value
        });
    },
    loadList: function() {
        var a = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=order&m=ReturnDataList",
            method: "post",
            data: {
                openid: t.globalData.userInfo.openid,
                order_type: a.data.isStatus
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                t.data.status;
                var e = t.data.list;
                switch (a.data.currentTab) {
                  case 0:
                    a.setData({
                        orderList0: e
                    });
                    break;

                  case 1:
                    a.setData({
                        orderList1: e
                    });
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
    bindChange: function(t) {
        this.setData({
            currentTab: t.detail.current
        });
    },
    swichNav: function(t) {
        var a = this;
        if (a.data.currentTab === t.target.dataset.current) return !1;
        var e = t.target.dataset.current;
        a.setData({
            currentTab: parseInt(e),
            isStatus: t.target.dataset.otype
        }), a.loadList();
    },
    setModalStatus: function(a) {
        var e = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=order&m=see_send",
            method: "post",
            data: {},
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (t.data.status) {
                    var a = t.data.express, o = [], n = 0;
                    for (n = 0; n < a.length; n++) o[n] = a[n].kuaidi_name;
                    e.setData({
                        address: t.data.address,
                        name: t.data.name,
                        phone: t.data.phone,
                        express: a,
                        exp: o
                    });
                } else wx.showToast({
                    title: "获取错误！",
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
        var o = a.currentTarget.dataset.id, n = wx.createAnimation({
            duration: 200,
            timingFunction: "linear",
            delay: 0
        });
        this.animation = n, n.translateY(300).step(), this.setData({
            animationData: n.export(),
            id: o
        }), 1 == a.currentTarget.dataset.status && this.setData({
            showModalStatus: !0
        }), setTimeout(function() {
            n.translateY(0).step(), this.setData({
                animationData: n
            }), 0 == a.currentTarget.dataset.status && this.setData({
                showModalStatus: !1
            });
        }.bind(this), 200);
    }
});