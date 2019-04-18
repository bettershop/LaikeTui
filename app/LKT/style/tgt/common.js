window.Modernizr = function (a, b, c) {
    function w(a) {
        j.cssText = a
    }
    function x(a, b) {
        return w(m.join(a + ";") + (b || ""))
    }
    function y(a, b) {
        return typeof a === b
    }
    function z(a, b) {
        return!!~("" + a).indexOf(b)
    }
    
    function A(a, b, d) {
        for (var e in a) {
            var f = b[a[e]];
            if (f !== c)
                return d === !1 ? a[e] : y(f, "function") ? f.bind(d || b) : f
        }
        return!1
    }
    var d = "2.6.2", e = {}, f = !0, g = b.documentElement, h = "modernizr", i = b.createElement(h), j = i.style, k, l = {}.toString, m = " -webkit- -moz- -o- -ms- ".split(" "), n = {}, o = {}, p = {}, q = [], r = q.slice, s, t = function (a, c, d, e) {
        var f, i, j, k, l = b.createElement("div"), m = b.body, n = m || b.createElement("body");
        if (parseInt(d, 10))
            while (d--)
                j = b.createElement("div"), j.id = e ? e[d] : h + (d + 1), l.appendChild(j);
        return f = ["&#173;", '<style id="s', h, '">', a, "</style>"].join(""), l.id = h, (m ? l : n).innerHTML += f, n.appendChild(l), m || (n.style.background = "", n.style.overflow = "hidden", k = g.style.overflow, g.style.overflow = "hidden", g.appendChild(n)), i = c(l, a), m ? l.parentNode.removeChild(l) : (n.parentNode.removeChild(n), g.style.overflow = k), !!i
    }, u = {}.hasOwnProperty, v;
    !y(u, "undefined") && !y(u.call, "undefined") ? v = function (a, b) {
        return u.call(a, b)
    } : v = function (a, b) {
        return b in a && y(a.constructor.prototype[b], "undefined")
    }, Function.prototype.bind || (Function.prototype.bind = function (b) {
        var c = this;
        if (typeof c != "function")
            throw new TypeError;
        var d = r.call(arguments, 1), e = function () {
            if (this instanceof e) {
                var a = function () {
                };
                a.prototype = c.prototype;
                var f = new a, g = c.apply(f, d.concat(r.call(arguments)));
                return Object(g) === g ? g : f
            }
            return c.apply(b, d.concat(r.call(arguments)))
        };
        return e
    }), n.touch = function () {
        var c;
        return"ontouchstart"in a || a.DocumentTouch && b instanceof DocumentTouch ? c = !0 : t(["@media (", m.join("touch-enabled),("), h, ")", "{#modernizr{top:9px;position:absolute}}"].join(""), function (a) {
            c = a.offsetTop === 9
        }), c
    };
    for (var B in n)
        v(n, B) && (s = B.toLowerCase(), e[s] = n[B](), q.push((e[s] ? "" : "no-") + s));
    return e.addTest = function (a, b) {
        if (typeof a == "object")
            for (var d in a)
                v(a, d) && e.addTest(d, a[d]);
        else {
            a = a.toLowerCase();
            if (e[a] !== c)
                return e;
            b = typeof b == "function" ? b() : b, typeof f != "undefined" && f && (g.className += " " + (b ? "" : "no-") + a), e[a] = b
        }
        return e
    }, w(""), i = k = null, e._version = d, e._prefixes = m, e.testStyles = t, g.className = g.className.replace(/(^|\s)no-js(\s|$)/, "$1$2") + (f ? " js " + q.join(" ") : ""), e
}(this, this.document);
Modernizr.addTest('android', function () {
    return!!navigator.userAgent.match(/Android/i)
});
Modernizr.addTest('chrome', function () {
    return!!navigator.userAgent.match(/Chrome/i)
});
Modernizr.addTest('firefox', function () {
    return!!navigator.userAgent.match(/Firefox/i)
});
Modernizr.addTest('iemobile', function () {
    return!!navigator.userAgent.match(/IEMobile/i)
});
Modernizr.addTest('ie', function () {
    return!!navigator.userAgent.match(/MSIE/i)
});
Modernizr.addTest('ie10', function () {
    return!!navigator.userAgent.match(/MSIE 10/i)
});
Modernizr.addTest('ie11', function () {
    return!!navigator.userAgent.match(/Trident.*rv:11\./)
});
Modernizr.addTest('ios', function () {
    return!!navigator.userAgent.match(/iPhone|iPad|iPod/i)
});
 
jQuery.fn.extend({slimScroll: function (h) {
        var a = $.extend({width: "auto", height: "250px", size: "7px", color: "#000", position: "right", distance: "1px", start: "top", opacity: 0.4, alwaysVisible: !1, disableFadeOut: !1, railVisible: !1, railColor: "#333", railOpacity: 0.2, railDraggable: !0, railClass: "slimScrollRail", barClass: "slimScrollBar", wrapperClass: "slimScrollDiv", allowPageScroll: !1, wheelStep: 20, touchScrollStep: 200, borderRadius: "7px", railBorderRadius: "7px"}, h);
        this.each(function () {
            function r(d) {
                if (s) {
                    d = d || window.event;
                    var c = 0;
                    d.wheelDelta && (c = -d.wheelDelta / 120);
                    d.detail && (c = d.detail / 3);
                    $(d.target || d.srcTarget || d.srcElement).closest("." + a.wrapperClass).is(b.parent()) && m(c, !0);
                    d.preventDefault && !k && d.preventDefault();
                    k || (d.returnValue = !1)
                }
            }
            function m(d, f, h) {
                k = !1;
                var e = d, g = b.outerHeight() - c.outerHeight();
                f && (e = parseInt(c.css("top")) + d * parseInt(a.wheelStep) / 100 * c.outerHeight(), e = Math.min(Math.max(e, 0), g), e = 0 < d ? Math.ceil(e) : Math.floor(e), c.css({top: e + "px"}));
                l = parseInt(c.css("top")) / (b.outerHeight() - c.outerHeight());
                e = l * (b[0].scrollHeight - b.outerHeight());
                h && (e = d, d = e / b[0].scrollHeight * b.outerHeight(), d = Math.min(Math.max(d, 0), g), c.css({top: d + "px"}));
                b.scrollTop(e);
                b.trigger("slimscrolling", ~~e);
                v();
                p()
            }
            function C() {
                window.addEventListener ? (this.addEventListener("DOMMouseScroll", r, !1), this.addEventListener("mousewheel", r, !1), this.addEventListener("MozMousePixelScroll", r, !1)) : document.attachEvent("onmousewheel", r)
            }
            function w() {
                u = Math.max(b.outerHeight() / b[0].scrollHeight * b.outerHeight(), D);
                c.css({height: u + "px"});
                var a = u == b.outerHeight() ? "none" : "block";
                c.css({display: a})
            }
            function v() {
                w();
                clearTimeout(A);
                l == ~~l ? (k = a.allowPageScroll, B != l && b.trigger("slimscroll", 0 == ~~l ? "top" : "bottom")) : k = !1;
                B = l;
                u >= b.outerHeight() ? k = !0 : (c.stop(!0, !0).fadeIn("fast"), a.railVisible && g.stop(!0, !0).fadeIn("fast"))
            }
            function p() {
                a.alwaysVisible || (A = setTimeout(function () {
                    a.disableFadeOut && s || (x || y) || (c.fadeOut("slow"), g.fadeOut("slow"))
                }, 1E3))
            }
            var s, x, y, A, z, u, l, B, D = 30, k = !1, b = $(this);
            if (b.parent().hasClass(a.wrapperClass)) {
                var n = b.scrollTop(), c = b.parent().find("." + a.barClass), g = b.parent().find("." + a.railClass);
                w();
                if ($.isPlainObject(h)) {
                    if ("height"in h && "auto" == h.height) {
                        b.parent().css("height", "auto");
                        b.css("height", "auto");
                        var q = b.parent().parent().height();
                        b.parent().css("height", q);
                        b.css("height", q)
                    }
                    if ("scrollTo"in h)
                        n = parseInt(a.scrollTo);
                    else if ("scrollBy"in h)
                        n += parseInt(a.scrollBy);
                    else if ("destroy"in h) {
                        c.remove();
                        g.remove();
                        b.unwrap();
                        return
                    }
                    m(n, !1, !0)
                }
            } else {
                a.height = "auto" == a.height ? b.parent().height() : a.height;
                n = $("<div></div>").addClass(a.wrapperClass).css({position: "relative", overflow: "hidden", width: a.width, height: a.height});
                b.css({overflow: "hidden", width: a.width, height: a.height});
                var g = $("<div></div>").addClass(a.railClass).css({width: a.size, height: "100%", position: "absolute", top: 0, display: a.alwaysVisible && a.railVisible ? "block" : "none", "border-radius": a.railBorderRadius, background: a.railColor, opacity: a.railOpacity, zIndex: 90}), c = $("<div></div>").addClass(a.barClass).css({background: a.color, width: a.size, position: "absolute", top: 0, opacity: a.opacity, display: a.alwaysVisible ? "block" : "none", "border-radius": a.borderRadius, BorderRadius: a.borderRadius, MozBorderRadius: a.borderRadius, WebkitBorderRadius: a.borderRadius, zIndex: 99}), q = "right" == a.position ? {right: a.distance} : {left: a.distance};
                g.css(q);
                c.css(q);
                b.wrap(n);
                b.parent().append(c);
                b.parent().append(g);
                a.railDraggable && c.bind("mousedown", function (a) {
                    var b = $(document);
                    y = !0;
                    t = parseFloat(c.css("top"));
                    pageY = a.pageY;
                    b.bind("mousemove.slimscroll", function (a) {
                        currTop = t + a.pageY - pageY;
                        c.css("top", currTop);
                        m(0, c.position().top, !1)
                    });
                    b.bind("mouseup.slimscroll", function (a) {
                        y = !1;
                        p();
                        b.unbind(".slimscroll")
                    });
                    return!1
                }).bind("selectstart.slimscroll", function (a) {
                    a.stopPropagation();
                    a.preventDefault();
                    return!1
                });
                g.hover(function () {
                    v()
                }, function () {
                    p()
                });
                c.hover(function () {
                    x = !0
                }, function () {
                    x = !1
                });
                b.hover(function () {
                    s = !0;
                    v();
                    p()
                }, function () {
                    s = !1;
                    p()
                });
                b.bind("touchstart", function (a, b) {
                    a.originalEvent.touches.length && (z = a.originalEvent.touches[0].pageY)
                });
                b.bind("touchmove", function (b) {
                    k || b.originalEvent.preventDefault();
                    b.originalEvent.touches.length && (m((z - b.originalEvent.touches[0].pageY) / a.touchScrollStep, !0), z = b.originalEvent.touches[0].pageY)
                });
                w();
                "bottom" === a.start ? (c.css({top: b.outerHeight() - c.outerHeight()}), m(0, !0)) : "top" !== a.start && (m($(a.start).position().top, null, !0), a.alwaysVisible || c.hide());
                C()
            }
        });
        return this
    }});
jQuery.fn.extend({slimscroll: jQuery.fn.slimScroll});

require(['jquery'], function ($) {
    $("section.hidden-bsection").show(),
        $("#loading_done").add("div.pageload-overlay").removeClass("show").hide();
    var vbox = $(".ie11 .vbox").add(".ie .vbox");
    vbox.length > 0 && (vbox.each(function () {
        $(this).height($(this).parent().height());
    }),
        $(window).resize(function () {
        var b = $(window).height();
        vbox.each(function () {
            $(this).height(b - $(this).offset().top-20)
        })
    })),$(".no-touch .slim-scroll").each(function () {
        var scroller = $(this), data = scroller.data();
        var time;
        scroller.slimScroll(data), $(window).resize(function () {
            clearTimeout(time), time = setTimeout(function () {
                scroller.slimScroll(data)
            }, 500);
        })
    });
});

$(document).on("click", ".nav-primary a", function (e) {
        var active, nav = $(e.target);

        nav.is("a") || (nav =nav.closest("a"));
        $(".nav-vertical").length || (active = nav.parent().siblings(".active"));
        active && active.find("> a").toggleClass("active") && active.toggleClass("active").find("> ul:visible").slideUp(200);
        nav.hasClass("active") && nav.next().slideUp(200) || nav.next().slideDown(200);
        nav.toggleClass("active").parent().toggleClass("active"),
        nav.next().is("ul") && e.preventDefault();
    });
  