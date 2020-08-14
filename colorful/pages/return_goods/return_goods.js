var t = getApp();

Page({
    data: {
        itemList: [],
        itemList_text: "退货退款",
        tapIndex: 1
    },
    actionSheetTap: function() {
        var t = this;
        wx.showActionSheet({
            itemList: t.data.itemList,
            success: function(a) {
                for (var e = t.data.arrayType, o = t.data.itemList, i = 0; i < e.length; i++) o[a.tapIndex] == e[i].text && t.setData({
                    tapIndex: e[i].id
                });
                t.setData({
                    itemList_text: o[a.tapIndex]
                });
            }
        });
    },
    onLoad: function(a) {
        wx.setNavigationBarColor({
            frontColor: t.d.frontColor,
            backgroundColor: t.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        });
        var e = !!a.type && a.type;
        console.log(a), this.setData({
            bgcolor: t.d.bf_color,
            id: a.id,
            oid: a.oid,
            otype: e
        }), this.loadate();
    },
    loadate: function(a) {
        var e = this;
        wx.request({
            url: t.d.ceshiUrl + "&action=order&m=return_type",
            method: "post",
            data: {
                id: e.data.id,
                oid: e.data.oid
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                if (1 == t.data.status) {
                    for (var a = t.data.arrayType, o = [], i = 0; i < a.length; i++) o.push(a[i].text);
                    e.setData({
                        itemList: o,
                        arrayType: t.data.arrayType,
                        itemList_text: t.data.itemList_text,
                        tapIndex: t.data.tapIndex
                    });
                } else wx.showToast({
                    title: t.data.err,
                    duration: 2e3
                });
            }
        });
    },
    remarkInput: function(t) {
        this.setData({
            remark: t.detail.value
        });
    },
    submitReturnData: function(a) {
        var e = a.detail.value.remark, o = this, i = a.detail.formId;
        e.length < 1 ? wx.showToast({
            title: "退款原因不能为空!",
            icon: "none",
            duration: 2e3
        }) : ("the formId is a mock one" != i && t.request.wxRequest({
            url: "&action=product&m=save_formid",
            data: {
                from_id: i,
                userid: t.globalData.userInfo.openid
            },
            method: "post",
            success: function(t) {
                console.log(t);
            }
        }), wx.request({
            url: t.d.ceshiUrl + "&action=order&m=ReturnData",
            method: "post",
            data: {
                id: o.data.id,
                oid: o.data.oid,
                otype: o.data.otype,
                re_type: o.data.tapIndex,
                back_remark: e
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                1 == t.data.status ? (wx.showToast({
                    title: t.data.succ,
                    success: 2e3
                }), wx.redirectTo({
                    url: "/pages/return_goods/index?currentTab=0&otype=whole"
                })) : wx.showToast({
                    title: t.data.err,
                    duration: 2e3
                });
            }
        }));
    }
});