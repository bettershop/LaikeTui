var t, e = getApp();

Page({
    data: {
        windowHeight: "auto",
        commentList: [],
        images: [],
        imageWidth: 0,
        remind: "加载中",
        addHide: 0
    },
    onReady: function() {},
    onLoad: function(a) {
        t = this, wx.getSystemInfo({
            success: function(e) {
                t.setData({
                    screenWidth: e.windowWidth,
                    screenHeight: e.windowHeight,
                    pixelRatio: e.pixelRatio,
                    imageWidth: e.windowWidth / 4 - 10
                });
            }
        }), wx.setNavigationBarColor({
            frontColor: e.d.frontColor,
            backgroundColor: e.d.bgcolor,
            animation: {
                duration: 400,
                timingFunc: "easeIn"
            }
        });
        var i = a.ordersn, o = e.globalData.userInfo.openid, n = a.pid, s = a.attribute_id;
        n = n || "", s = s || "", wx.request({
            url: e.d.ceshiUrl + "&action=product&m=comment",
            method: "post",
            data: {
                order_id: i,
                user_id: o,
                pid: n,
                attribute_id: s
            },
            header: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            success: function(e) {
                if (1 == e.data.status) {
                    var a = e.data.commentList;
                    if (a.length > 1) for (var o = 0, n = a.length; o < n; o++) a[o].commentType = "GOOD", 
                    a[o].images = [], a[o].id = o, a[o].addHide = a[o].images.length; else a[0].commentType = "GOOD", 
                    a[0].images = "", a[0].id = 0, a[0].addHide = a[0].images.length;
                    t.setData({
                        commentList: a,
                        bgcolor: "#09bb07",
                        orderId: i,
                        remind: ""
                    });
                } else wx.showToast({
                    title: "已经评论过了哦，亲!",
                    duration: 2e3
                }), setTimeout(function() {
                    wx.navigateBack({
                        delta: 2
                    });
                }, 2e3);
            },
            fail: function() {
                wx.showToast({
                    title: "网络异常！",
                    duration: 2e3
                });
            }
        });
    },
    onShow: function() {},
    chooseImage: function(t) {
        var e = t.target.id, a = this;
        wx.chooseImage({
            count: 3,
            sizeType: [ "compressed" ],
            sourceType: [ "album", "camera" ],
            success: function(t) {
                var i = t.tempFilePaths, o = a.data.commentList, n = o[e].images;
                if (n.length > 2) wx.showToast({
                    title: "亲！最多上传3张哦！",
                    icon: "none",
                    duration: 2e3
                }); else {
                    n = n ? n.concat(i) : i;
                    for (var s = 0, d = o.length; s < d; s++) s == e && (o[e].images = n, o[e].addHide = o[e].images.length);
                    a.setData({
                        commentList: o,
                        addHide: o[e].images.length
                    });
                }
            }
        });
    },
    delete: function(e) {
        var a = e.currentTarget.id, i = e.currentTarget.dataset.index, o = t.data.commentList, n = o[a].images;
        n.splice(i, 1), o[a].images = n, o[a].addHide = n.length, t.setData({
            commentList: o
        });
    },
    previewImage: function(e) {
        var a = e.currentTarget.id, i = t.data.commentList[a].images;
        wx.previewImage({
            urls: i
        });
    },
    setcon: function(t) {
        var e = this.data.commentList;
        e[parseInt(t.currentTarget.dataset.index)].content = t.detail.value, this.setData({
            commentList: e
        });
    },
    selectCommentType: function(t) {
        var e = this.data.commentList;
        e[parseInt(t.currentTarget.dataset.index)].commentType = t.currentTarget.dataset.type, 
        this.setData({
            commentList: e
        });
    },
    saveimg: function(e, a) {
        for (var i = t.data.commentList[a].images, o = {
            id: e
        }, n = 0, s = i.length; n < s; n++) t.upload_file("&action=product&m=t_comment&type=file", i[n], "imgFile", o);
    },
    submitComment: function(a) {
        for (var i = this, o = [], n = 0, s = i.data.commentList.length; n < s; n++) o.push({
            commodityId: i.data.commentList[n].commodityId,
            images: i.data.commentList[n].images,
            content: a.detail.value["content_" + n],
            score: i.data.commentList[n].commentType,
            size: i.data.commentList[n].size,
            attribute_id: i.data.commentList[n].sid,
            orderId: i.data.orderId,
            userid: e.globalData.userInfo.openid,
            pingid: i.data.commentList[n].id
        });
        wx.request({
            url: e.d.ceshiUrl + "&action=product&m=t_comment&type=json",
            method: "post",
            data: {
                comments: o
            },
            header: {
                "content-type": "application/json"
            },
            success: function(e) {
                if (1 == e.data.status) {
                    for (var a = e.data.arrid, i = 0, o = a.length; i < o; i++) t.saveimg(a[i], i);
                    wx.showToast({
                        title: e.data.succ,
                        duration: 2e3
                    }), setTimeout(function() {
                        wx.navigateBack({
                            delta: 1
                        });
                    }, 2e3);
                } else wx.showToast({
                    title: e.data.err,
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
    },
    upload_file: function(t, a, i, o, n, s) {
        wx.uploadFile({
            url: e.d.ceshiUrl + t,
            filePath: a,
            name: i,
            header: {
                "content-type": "multipart/form-data"
            },
            formData: o,
            success: function(t) {
                console.log(t);
            },
            fail: function(t) {
                console.log(t);
            }
        });
    }
});