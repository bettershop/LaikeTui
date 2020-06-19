var t = require("../../wxSearch/wxSearch.js"), e = getApp();

Page({
    data: {
        color: "",
        listHeight: "",
        cont: 1,
        remind: "加载中",
        curNav: 1,
        curIndex: 0,
        isFocus: !1
    },
    onPullDownRefresh: function() {
        wx.showNavigationBarLoading(), setTimeout(function() {
            wx.hideNavigationBarLoading(), wx.stopPullDownRefresh();
        }, 1500), this.search();
    },
    search: function(t) {
        var a = this;
        wx.request({
            url: e.d.ceshiUrl + "&action=search&m=index",
            method: "post",
            data: {
                keyword: "百货"
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(t) {
                a.setData({
                    cateItems: t.data.List,
                    hotwords: t.data.hot,
                    curNav: t.data.List[0].cate_id
                }), a.onReady();
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    onShow: function() {
        this.data.cont;
    },
    onLoad: function(t) {
        this.setData({
            color: e.d.bgcolor
        }), wx.setNavigationBarColor({
            frontColor: e.d.frontColor,
            backgroundColor: e.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        }), this.search();
    },
    switchRightTab: function(t) {
        var e = t.target.dataset.id, a = parseInt(t.target.dataset.index);
        this.setData({
            curNav: e,
            curIndex: a
        });
    },
    search_confirmType: function(e) {
        console.log("-----");
        var a = this, i = e.detail.value;
        this.setData({
            value: e.detail.value
        }), "" != i && (t.wxSearchAddHisKey(a), wx.navigateTo({
            url: "../listdetail/listdetail?keyword=" + i
        }));
    },
    wxSearchFn: function(e) {
        console.log(e);
        var a = this;
        t.wxSearchAddHisKey(a), setTimeout(function() {
            var t = a.data.wxSearchData.value;
            t && wx.navigateTo({
                url: "../listdetail/listdetail?keyword=" + t
            });
        }, 500);
    },
    wxSearchInput: function(e) {
        console.log(e);
        var a = this;
        console.log(a), t.wxSearchInput(e, a);
    },
    wxSerchFocus: function(e) {
        var a = this;
        a.setData({
            isFocus: !1
        }), t.wxSearchFocus(a);
    },
    wxSearchBlur: function(e) {
        var a = this;
        t.wxSearchBlur(e, a);
    },
    wxSearchKeyTap: function(e) {
        var a = this;
        t.wxSearchKeyTap(e, a);
    },
    wxSearchDeleteKey: function(e) {
        var a = this;
        t.wxSearchDeleteKey(e, a);
    },
    wxSearchDeleteAll: function(e) {
        var a = this;
        t.wxSearchDeleteAll(a);
    },
    wxSearchTap: function(e) {
        var a = this;
        t.wxSearchHiddenPancel(a);
    },
    onReady: function() {
        var e = this;
        setTimeout(function() {
            e.setData({
                remind: ""
            });
        }, 1e3), t.init(e, 43, this.data.hotwords), t.initMindKeys([ "laiketui.com", "微信小程序开发", "微信开发", "微信小程序" ]);
        wx.getSystemInfo({
            success: function(t) {
                var a = t.windowWidth / 1200;
                e.setData({
                    right_titleHeight: Number(60 * a),
                    right_contentHeight: Number(200 * a),
                    left_titleHeight: Number(140 * a)
                });
            }
        });
    }
});