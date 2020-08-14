function e(e) {
    return e && e.__esModule ? e : {
        default: e
    };
}

function t(e) {
    var t = this, a = e.target.dataset.src, i = e.target.dataset.from;
    void 0 !== i && i.length > 0 && wx.previewImage({
        current: a,
        urls: t.data[i].imageUrls
    });
}

function a(e) {
    var t = this, a = e.target.dataset.from, r = e.target.dataset.idx;
    void 0 !== a && a.length > 0 && i(e, r, t, a);
}

function i(e, t, a, i) {
    var d = a.data[i];
    if (0 != d.images.length) {
        var n = d.images, s = r(e.detail.width, e.detail.height, a, i);
        n[t].width = s.imageWidth, n[t].height = s.imageheight, d.images = n;
        var o = {};
        o[i] = d, a.setData(o);
    }
}

function r(e, t, a, i) {
    var r = 0, d = 0, n = 0, s = 0, o = {};
    return wx.getSystemInfo({
        success: function(g) {
            var h = a.data[i].view.imagePadding;
            r = g.windowWidth - 2 * h, d = g.windowHeight, e > r ? (s = (n = r) * t / e, o.imageWidth = n, 
            o.imageheight = s) : (o.imageWidth = e, o.imageheight = t);
        }
    }), o;
}

var d = e(require("./showdown.js")), n = e(require("./html2json.js"));

module.exports = {
    wxParse: function() {
        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : "wxParseData", i = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "html", r = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : '<div class="color:red;">数据不能为空</div>', s = arguments[3], o = arguments[4], g = s, h = {};
        if ("html" == i) h = n.default.html2json(r, e); else if ("md" == i || "markdown" == i) {
            var m = new d.default.Converter().makeHtml(r);
            h = n.default.html2json(m, e);
        }
        h.view = {}, h.view.imagePadding = 0, void 0 !== o && (h.view.imagePadding = o);
        var l = {};
        l[e] = h, g.setData(l), g.wxParseImgLoad = a, g.wxParseImgTap = t;
    },
    wxParseTemArray: function(e, t, a, i) {
        for (var r = [], d = i.data, n = null, s = 0; s < a; s++) {
            var o = d[t + s].nodes;
            r.push(o);
        }
        e = e || "wxParseTemArray", (n = JSON.parse('{"' + e + '":""}'))[e] = r, i.setData(n);
    },
    emojisInit: function() {
        var e = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : "", t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "/wxParse/emojis/", a = arguments[2];
        n.default.emojisInit(e, t, a);
    }
};