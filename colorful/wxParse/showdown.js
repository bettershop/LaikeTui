function e(e) {
    var r = {
        omitExtraWLInCodeBlocks: {
            defaultValue: !1,
            describe: "Omit the default extra whiteline added to code blocks",
            type: "boolean"
        },
        noHeaderId: {
            defaultValue: !1,
            describe: "Turn on/off generated header id",
            type: "boolean"
        },
        prefixHeaderId: {
            defaultValue: !1,
            describe: "Specify a prefix to generated header ids",
            type: "string"
        },
        headerLevelStart: {
            defaultValue: !1,
            describe: "The header blocks level start",
            type: "integer"
        },
        parseImgDimensions: {
            defaultValue: !1,
            describe: "Turn on/off image dimension parsing",
            type: "boolean"
        },
        simplifiedAutoLink: {
            defaultValue: !1,
            describe: "Turn on/off GFM autolink style",
            type: "boolean"
        },
        literalMidWordUnderscores: {
            defaultValue: !1,
            describe: "Parse midword underscores as literal underscores",
            type: "boolean"
        },
        strikethrough: {
            defaultValue: !1,
            describe: "Turn on/off strikethrough support",
            type: "boolean"
        },
        tables: {
            defaultValue: !1,
            describe: "Turn on/off tables support",
            type: "boolean"
        },
        tablesHeaderId: {
            defaultValue: !1,
            describe: "Add an id to table headers",
            type: "boolean"
        },
        ghCodeBlocks: {
            defaultValue: !0,
            describe: "Turn on/off GFM fenced code blocks support",
            type: "boolean"
        },
        tasklists: {
            defaultValue: !1,
            describe: "Turn on/off GFM tasklist support",
            type: "boolean"
        },
        smoothLivePreview: {
            defaultValue: !1,
            describe: "Prevents weird effects in live previews due to incomplete input",
            type: "boolean"
        },
        smartIndentationFix: {
            defaultValue: !1,
            description: "Tries to smartly fix identation in es6 strings",
            type: "boolean"
        }
    };
    if (!1 === e) return JSON.parse(JSON.stringify(r));
    var t = {};
    for (var n in r) r.hasOwnProperty(n) && (t[n] = r[n].defaultValue);
    return t;
}

function r(e, r) {
    var t = r ? "Error in " + r + " extension->" : "Error in unnamed extension", a = {
        valid: !0,
        error: ""
    };
    s.helper.isArray(e) || (e = [ e ]);
    for (var o = 0; o < e.length; ++o) {
        var i = t + " sub-extension " + o + ": ", l = e[o];
        if ("object" !== (void 0 === l ? "undefined" : n(l))) return a.valid = !1, a.error = i + "must be an object, but " + (void 0 === l ? "undefined" : n(l)) + " given", 
        a;
        if (!s.helper.isString(l.type)) return a.valid = !1, a.error = i + 'property "type" must be a string, but ' + n(l.type) + " given", 
        a;
        var c = l.type = l.type.toLowerCase();
        if ("language" === c && (c = l.type = "lang"), "html" === c && (c = l.type = "output"), 
        "lang" !== c && "output" !== c && "listener" !== c) return a.valid = !1, a.error = i + "type " + c + ' is not recognized. Valid values: "lang/language", "output/html" or "listener"', 
        a;
        if ("listener" === c) {
            if (s.helper.isUndefined(l.listeners)) return a.valid = !1, a.error = i + '. Extensions of type "listener" must have a property called "listeners"', 
            a;
        } else if (s.helper.isUndefined(l.filter) && s.helper.isUndefined(l.regex)) return a.valid = !1, 
        a.error = i + c + ' extensions must define either a "regex" property or a "filter" method', 
        a;
        if (l.listeners) {
            if ("object" !== n(l.listeners)) return a.valid = !1, a.error = i + '"listeners" property must be an object but ' + n(l.listeners) + " given", 
            a;
            for (var u in l.listeners) if (l.listeners.hasOwnProperty(u) && "function" != typeof l.listeners[u]) return a.valid = !1, 
            a.error = i + '"listeners" property must be an hash of [event name]: [callback]. listeners.' + u + " must be a function but " + n(l.listeners[u]) + " given", 
            a;
        }
        if (l.filter) {
            if ("function" != typeof l.filter) return a.valid = !1, a.error = i + '"filter" must be a function, but ' + n(l.filter) + " given", 
            a;
        } else if (l.regex) {
            if (s.helper.isString(l.regex) && (l.regex = new RegExp(l.regex, "g")), !l.regex instanceof RegExp) return a.valid = !1, 
            a.error = i + '"regex" property must either be a string or a RegExp object, but ' + n(l.regex) + " given", 
            a;
            if (s.helper.isUndefined(l.replace)) return a.valid = !1, a.error = i + '"regex" extensions must implement a replace string or function', 
            a;
        }
    }
    return a;
}

function t(e, r) {
    return "~E" + r.charCodeAt(0) + "E";
}

var n = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function(e) {
    return typeof e;
} : function(e) {
    return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e;
}, s = {}, a = {}, o = {}, i = e(!0), l = {
    github: {
        omitExtraWLInCodeBlocks: !0,
        prefixHeaderId: "user-content-",
        simplifiedAutoLink: !0,
        literalMidWordUnderscores: !0,
        strikethrough: !0,
        tables: !0,
        tablesHeaderId: !0,
        ghCodeBlocks: !0,
        tasklists: !0
    },
    vanilla: e(!0)
};

s.helper = {}, s.extensions = {}, s.setOption = function(e, r) {
    return i[e] = r, this;
}, s.getOption = function(e) {
    return i[e];
}, s.getOptions = function() {
    return i;
}, s.resetOptions = function() {
    i = e(!0);
}, s.setFlavor = function(e) {
    if (l.hasOwnProperty(e)) {
        var r = l[e];
        for (var t in r) r.hasOwnProperty(t) && (i[t] = r[t]);
    }
}, s.getDefaultOptions = function(r) {
    return e(r);
}, s.subParser = function(e, r) {
    if (s.helper.isString(e)) {
        if (void 0 === r) {
            if (a.hasOwnProperty(e)) return a[e];
            throw Error("SubParser named " + e + " not registered!");
        }
        a[e] = r;
    }
}, s.extension = function(e, t) {
    if (!s.helper.isString(e)) throw Error("Extension 'name' must be a string");
    if (e = s.helper.stdExtName(e), s.helper.isUndefined(t)) {
        if (!o.hasOwnProperty(e)) throw Error("Extension named " + e + " is not registered!");
        return o[e];
    }
    "function" == typeof t && (t = t()), s.helper.isArray(t) || (t = [ t ]);
    var n = r(t, e);
    if (!n.valid) throw Error(n.error);
    o[e] = t;
}, s.getAllExtensions = function() {
    return o;
}, s.removeExtension = function(e) {
    delete o[e];
}, s.resetExtensions = function() {
    o = {};
}, s.validateExtension = function(e) {
    var t = r(e, null);
    return !!t.valid || (console.warn(t.error), !1);
}, s.hasOwnProperty("helper") || (s.helper = {}), s.helper.isString = function(e) {
    return "string" == typeof e || e instanceof String;
}, s.helper.isFunction = function(e) {
    var r = {};
    return e && "[object Function]" === r.toString.call(e);
}, s.helper.forEach = function(e, r) {
    if ("function" == typeof e.forEach) e.forEach(r); else for (var t = 0; t < e.length; t++) r(e[t], t, e);
}, s.helper.isArray = function(e) {
    return e.constructor === Array;
}, s.helper.isUndefined = function(e) {
    return void 0 === e;
}, s.helper.stdExtName = function(e) {
    return e.replace(/[_-]||\s/g, "").toLowerCase();
}, s.helper.escapeCharactersCallback = t, s.helper.escapeCharacters = function(e, r, n) {
    var s = "([" + r.replace(/([\[\]\\])/g, "\\$1") + "])";
    n && (s = "\\\\" + s);
    var a = new RegExp(s, "g");
    return e = e.replace(a, t);
};

var c = function(e, r, t, n) {
    var s, a, o, i, l, c = n || "", u = c.indexOf("g") > -1, p = new RegExp(r + "|" + t, "g" + c.replace(/g/g, "")), h = new RegExp(r, c.replace(/g/g, "")), d = [];
    do {
        for (s = 0; o = p.exec(e); ) if (h.test(o[0])) s++ || (i = (a = p.lastIndex) - o[0].length); else if (s && !--s) {
            l = o.index + o[0].length;
            var f = {
                left: {
                    start: i,
                    end: a
                },
                match: {
                    start: a,
                    end: o.index
                },
                right: {
                    start: o.index,
                    end: l
                },
                wholeMatch: {
                    start: i,
                    end: l
                }
            };
            if (d.push(f), !u) return d;
        }
    } while (s && (p.lastIndex = a));
    return d;
};

s.helper.matchRecursiveRegExp = function(e, r, t, n) {
    for (var s = c(e, r, t, n), a = [], o = 0; o < s.length; ++o) a.push([ e.slice(s[o].wholeMatch.start, s[o].wholeMatch.end), e.slice(s[o].match.start, s[o].match.end), e.slice(s[o].left.start, s[o].left.end), e.slice(s[o].right.start, s[o].right.end) ]);
    return a;
}, s.helper.replaceRecursiveRegExp = function(e, r, t, n, a) {
    if (!s.helper.isFunction(r)) {
        var o = r;
        r = function() {
            return o;
        };
    }
    var i = c(e, t, n, a), l = e, u = i.length;
    if (u > 0) {
        var p = [];
        0 !== i[0].wholeMatch.start && p.push(e.slice(0, i[0].wholeMatch.start));
        for (var h = 0; h < u; ++h) p.push(r(e.slice(i[h].wholeMatch.start, i[h].wholeMatch.end), e.slice(i[h].match.start, i[h].match.end), e.slice(i[h].left.start, i[h].left.end), e.slice(i[h].right.start, i[h].right.end))), 
        h < u - 1 && p.push(e.slice(i[h].wholeMatch.end, i[h + 1].wholeMatch.start));
        i[u - 1].wholeMatch.end < e.length && p.push(e.slice(i[u - 1].wholeMatch.end)), 
        l = p.join("");
    }
    return l;
}, s.helper.isUndefined(console) && (console = {
    warn: function(e) {
        alert(e);
    },
    log: function(e) {
        alert(e);
    },
    error: function(e) {
        throw e;
    }
}), s.Converter = function(e) {
    function t(e, t) {
        if (t = t || null, s.helper.isString(e)) {
            if (e = s.helper.stdExtName(e), t = e, s.extensions[e]) return console.warn("DEPRECATION WARNING: " + e + " is an old extension that uses a deprecated loading method.Please inform the developer that the extension should be updated!"), 
            void a(s.extensions[e], e);
            if (s.helper.isUndefined(o[e])) throw Error('Extension "' + e + '" could not be loaded. It was either not found or is not a valid extension.');
            e = o[e];
        }
        "function" == typeof e && (e = e()), s.helper.isArray(e) || (e = [ e ]);
        var n = r(e, t);
        if (!n.valid) throw Error(n.error);
        for (var i = 0; i < e.length; ++i) {
            switch (e[i].type) {
              case "lang":
                h.push(e[i]);
                break;

              case "output":
                d.push(e[i]);
            }
            if (e[i].hasOwnProperty(f)) for (var l in e[i].listeners) e[i].listeners.hasOwnProperty(l) && c(l, e[i].listeners[l]);
        }
    }
    function a(e, t) {
        "function" == typeof e && (e = e(new s.Converter())), s.helper.isArray(e) || (e = [ e ]);
        var n = r(e, t);
        if (!n.valid) throw Error(n.error);
        for (var a = 0; a < e.length; ++a) switch (e[a].type) {
          case "lang":
            h.push(e[a]);
            break;

          case "output":
            d.push(e[a]);
            break;

          default:
            throw Error("Extension loader error: Type unrecognized!!!");
        }
    }
    function c(e, r) {
        if (!s.helper.isString(e)) throw Error("Invalid argument in converter.listen() method: name must be a string, but " + (void 0 === e ? "undefined" : n(e)) + " given");
        if ("function" != typeof r) throw Error("Invalid argument in converter.listen() method: callback must be a function, but " + (void 0 === r ? "undefined" : n(r)) + " given");
        f.hasOwnProperty(e) || (f[e] = []), f[e].push(r);
    }
    function u(e) {
        var r = e.match(/^\s*/)[0].length, t = new RegExp("^\\s{0," + r + "}", "gm");
        return e.replace(t, "");
    }
    var p = {}, h = [], d = [], f = {};
    !function() {
        e = e || {};
        for (var r in i) i.hasOwnProperty(r) && (p[r] = i[r]);
        if ("object" !== (void 0 === e ? "undefined" : n(e))) throw Error("Converter expects the passed parameter to be an object, but " + (void 0 === e ? "undefined" : n(e)) + " was passed instead.");
        for (var a in e) e.hasOwnProperty(a) && (p[a] = e[a]);
        p.extensions && s.helper.forEach(p.extensions, t);
    }(), this._dispatch = function(e, r, t, n) {
        if (f.hasOwnProperty(e)) for (var s = 0; s < f[e].length; ++s) {
            var a = f[e][s](e, r, this, t, n);
            a && void 0 !== a && (r = a);
        }
        return r;
    }, this.listen = function(e, r) {
        return c(e, r), this;
    }, this.makeHtml = function(e) {
        if (!e) return e;
        var r = {
            gHtmlBlocks: [],
            gHtmlMdBlocks: [],
            gHtmlSpans: [],
            gUrls: {},
            gTitles: {},
            gDimensions: {},
            gListLevel: 0,
            hashLinkCounts: {},
            langExtensions: h,
            outputModifiers: d,
            converter: this,
            ghCodeBlocks: []
        };
        return e = e.replace(/~/g, "~T"), e = e.replace(/\$/g, "~D"), e = e.replace(/\r\n/g, "\n"), 
        e = e.replace(/\r/g, "\n"), p.smartIndentationFix && (e = u(e)), e = e, e = s.subParser("detab")(e, p, r), 
        e = s.subParser("stripBlankLines")(e, p, r), s.helper.forEach(h, function(t) {
            e = s.subParser("runExtension")(t, e, p, r);
        }), e = s.subParser("hashPreCodeTags")(e, p, r), e = s.subParser("githubCodeBlocks")(e, p, r), 
        e = s.subParser("hashHTMLBlocks")(e, p, r), e = s.subParser("hashHTMLSpans")(e, p, r), 
        e = s.subParser("stripLinkDefinitions")(e, p, r), e = s.subParser("blockGamut")(e, p, r), 
        e = s.subParser("unhashHTMLSpans")(e, p, r), e = s.subParser("unescapeSpecialChars")(e, p, r), 
        e = e.replace(/~D/g, "$$"), e = e.replace(/~T/g, "~"), s.helper.forEach(d, function(t) {
            e = s.subParser("runExtension")(t, e, p, r);
        }), e;
    }, this.setOption = function(e, r) {
        p[e] = r;
    }, this.getOption = function(e) {
        return p[e];
    }, this.getOptions = function() {
        return p;
    }, this.addExtension = function(e, r) {
        t(e, r = r || null);
    }, this.useExtension = function(e) {
        t(e);
    }, this.setFlavor = function(e) {
        if (l.hasOwnProperty(e)) {
            var r = l[e];
            for (var t in r) r.hasOwnProperty(t) && (p[t] = r[t]);
        }
    }, this.removeExtension = function(e) {
        s.helper.isArray(e) || (e = [ e ]);
        for (var r = 0; r < e.length; ++r) {
            for (var t = e[r], n = 0; n < h.length; ++n) h[n] === t && h[n].splice(n, 1);
            for (;0 < d.length; ++n) d[0] === t && d[0].splice(n, 1);
        }
    }, this.getAllExtensions = function() {
        return {
            language: h,
            output: d
        };
    };
}, s.subParser("anchors", function(e, r, t) {
    var n = function(e, r, n, a, o, i, l, c) {
        s.helper.isUndefined(c) && (c = ""), e = r;
        var u = n, p = a.toLowerCase(), h = o, d = c;
        if (!h) if (p || (p = u.toLowerCase().replace(/ ?\n/g, " ")), h = "#" + p, s.helper.isUndefined(t.gUrls[p])) {
            if (!(e.search(/\(\s*\)$/m) > -1)) return e;
            h = "";
        } else h = t.gUrls[p], s.helper.isUndefined(t.gTitles[p]) || (d = t.gTitles[p]);
        var f = '<a href="' + (h = s.helper.escapeCharacters(h, "*_", !1)) + '"';
        return "" !== d && null !== d && (d = d.replace(/"/g, "&quot;"), f += ' title="' + (d = s.helper.escapeCharacters(d, "*_", !1)) + '"'), 
        f += ">" + u + "</a>";
    };
    return e = (e = t.converter._dispatch("anchors.before", e, r, t)).replace(/(\[((?:\[[^\]]*]|[^\[\]])*)][ ]?(?:\n[ ]*)?\[(.*?)])()()()()/g, n), 
    e = e.replace(/(\[((?:\[[^\]]*]|[^\[\]])*)]\([ \t]*()<?(.*?(?:\(.*?\).*?)?)>?[ \t]*((['"])(.*?)\6[ \t]*)?\))/g, n), 
    e = e.replace(/(\[([^\[\]]+)])()()()()()/g, n), e = t.converter._dispatch("anchors.after", e, r, t);
}), s.subParser("autoLinks", function(e, r, t) {
    function n(e, r) {
        var t = r;
        return /^www\./i.test(r) && (r = r.replace(/^www\./i, "http://www.")), '<a href="' + r + '">' + t + "</a>";
    }
    function a(e, r) {
        var t = s.subParser("unescapeSpecialChars")(r);
        return s.subParser("encodeEmailAddress")(t);
    }
    var o = /\b(((https?|ftp|dict):\/\/|www\.)[^'">\s]+\.[^'">\s]+)(?=\s|$)(?!["<>])/gi, i = /<(((https?|ftp|dict):\/\/|www\.)[^'">\s]+)>/gi, l = /(?:^|[ \n\t])([A-Za-z0-9!#$%&'*+-/=?^_`\{|}~\.]+@[-a-z0-9]+(\.[-a-z0-9]+)*\.[a-z]+)(?:$|[ \n\t])/gi, c = /<(?:mailto:)?([-.\w]+@[-a-z0-9]+(\.[-a-z0-9]+)*\.[a-z]+)>/gi;
    return e = (e = t.converter._dispatch("autoLinks.before", e, r, t)).replace(i, n), 
    e = e.replace(c, a), r.simplifiedAutoLink && (e = (e = e.replace(o, n)).replace(l, a)), 
    e = t.converter._dispatch("autoLinks.after", e, r, t);
}), s.subParser("blockGamut", function(e, r, t) {
    e = t.converter._dispatch("blockGamut.before", e, r, t), e = s.subParser("blockQuotes")(e, r, t), 
    e = s.subParser("headers")(e, r, t);
    var n = s.subParser("hashBlock")("<hr />", r, t);
    return e = e.replace(/^[ ]{0,2}([ ]?\*[ ]?){3,}[ \t]*$/gm, n), e = e.replace(/^[ ]{0,2}([ ]?\-[ ]?){3,}[ \t]*$/gm, n), 
    e = e.replace(/^[ ]{0,2}([ ]?_[ ]?){3,}[ \t]*$/gm, n), e = s.subParser("lists")(e, r, t), 
    e = s.subParser("codeBlocks")(e, r, t), e = s.subParser("tables")(e, r, t), e = s.subParser("hashHTMLBlocks")(e, r, t), 
    e = s.subParser("paragraphs")(e, r, t), e = t.converter._dispatch("blockGamut.after", e, r, t);
}), s.subParser("blockQuotes", function(e, r, t) {
    return e = t.converter._dispatch("blockQuotes.before", e, r, t), e = e.replace(/((^[ \t]{0,3}>[ \t]?.+\n(.+\n)*\n*)+)/gm, function(e, n) {
        var a = n;
        return a = a.replace(/^[ \t]*>[ \t]?/gm, "~0"), a = a.replace(/~0/g, ""), a = a.replace(/^[ \t]+$/gm, ""), 
        a = s.subParser("githubCodeBlocks")(a, r, t), a = s.subParser("blockGamut")(a, r, t), 
        a = a.replace(/(^|\n)/g, "$1  "), a = a.replace(/(\s*<pre>[^\r]+?<\/pre>)/gm, function(e, r) {
            var t = r;
            return t = t.replace(/^  /gm, "~0"), t = t.replace(/~0/g, "");
        }), s.subParser("hashBlock")("<blockquote>\n" + a + "\n</blockquote>", r, t);
    }), e = t.converter._dispatch("blockQuotes.after", e, r, t);
}), s.subParser("codeBlocks", function(e, r, t) {
    e = t.converter._dispatch("codeBlocks.before", e, r, t);
    var n = /(?:\n\n|^)((?:(?:[ ]{4}|\t).*\n+)+)(\n*[ ]{0,3}[^ \t\n]|(?=~0))/g;
    return e = (e += "~0").replace(n, function(e, n, a) {
        var o = n, i = a, l = "\n";
        return o = s.subParser("outdent")(o), o = s.subParser("encodeCode")(o), o = s.subParser("detab")(o), 
        o = o.replace(/^\n+/g, ""), o = o.replace(/\n+$/g, ""), r.omitExtraWLInCodeBlocks && (l = ""), 
        o = "<pre><code>" + o + l + "</code></pre>", s.subParser("hashBlock")(o, r, t) + i;
    }), e = e.replace(/~0/, ""), e = t.converter._dispatch("codeBlocks.after", e, r, t);
}), s.subParser("codeSpans", function(e, r, t) {
    return void 0 === (e = t.converter._dispatch("codeSpans.before", e, r, t)) && (e = ""), 
    e = e.replace(/(^|[^\\])(`+)([^\r]*?[^`])\2(?!`)/gm, function(e, r, t, n) {
        var a = n;
        return a = a.replace(/^([ \t]*)/g, ""), a = a.replace(/[ \t]*$/g, ""), a = s.subParser("encodeCode")(a), 
        r + "<code>" + a + "</code>";
    }), e = t.converter._dispatch("codeSpans.after", e, r, t);
}), s.subParser("detab", function(e) {
    return e = e.replace(/\t(?=\t)/g, "    "), e = e.replace(/\t/g, "~A~B"), e = e.replace(/~B(.+?)~A/g, function(e, r) {
        for (var t = r, n = 4 - t.length % 4, s = 0; s < n; s++) t += " ";
        return t;
    }), e = e.replace(/~A/g, "    "), e = e.replace(/~B/g, "");
}), s.subParser("encodeAmpsAndAngles", function(e) {
    return e = e.replace(/&(?!#?[xX]?(?:[0-9a-fA-F]+|\w+);)/g, "&amp;"), e = e.replace(/<(?![a-z\/?\$!])/gi, "&lt;");
}), s.subParser("encodeBackslashEscapes", function(e) {
    return e = e.replace(/\\(\\)/g, s.helper.escapeCharactersCallback), e = e.replace(/\\([`*_{}\[\]()>#+-.!])/g, s.helper.escapeCharactersCallback);
}), s.subParser("encodeCode", function(e) {
    return e = e.replace(/&/g, "&amp;"), e = e.replace(/</g, "&lt;"), e = e.replace(/>/g, "&gt;"), 
    e = s.helper.escapeCharacters(e, "*_{}[]\\", !1);
}), s.subParser("encodeEmailAddress", function(e) {
    var r = [ function(e) {
        return "&#" + e.charCodeAt(0) + ";";
    }, function(e) {
        return "&#x" + e.charCodeAt(0).toString(16) + ";";
    }, function(e) {
        return e;
    } ];
    return e = "mailto:" + e, e = e.replace(/./g, function(e) {
        if ("@" === e) e = r[Math.floor(2 * Math.random())](e); else if (":" !== e) {
            var t = Math.random();
            e = t > .9 ? r[2](e) : t > .45 ? r[1](e) : r[0](e);
        }
        return e;
    }), e = '<a href="' + e + '">' + e + "</a>", e = e.replace(/">.+:/g, '">');
}), s.subParser("escapeSpecialCharsWithinTagAttributes", function(e) {
    var r = /(<[a-z\/!$]("[^"]*"|'[^']*'|[^'">])*>|<!(--.*?--\s*)+>)/gi;
    return e = e.replace(r, function(e) {
        var r = e.replace(/(.)<\/?code>(?=.)/g, "$1`");
        return r = s.helper.escapeCharacters(r, "\\`*_", !1);
    });
}), s.subParser("githubCodeBlocks", function(e, r, t) {
    return r.ghCodeBlocks ? (e = t.converter._dispatch("githubCodeBlocks.before", e, r, t), 
    e += "~0", e = e.replace(/(?:^|\n)```(.*)\n([\s\S]*?)\n```/g, function(e, n, a) {
        var o = r.omitExtraWLInCodeBlocks ? "" : "\n";
        return a = s.subParser("encodeCode")(a), a = s.subParser("detab")(a), a = a.replace(/^\n+/g, ""), 
        a = a.replace(/\n+$/g, ""), a = "<pre><code" + (n ? ' class="' + n + " language-" + n + '"' : "") + ">" + a + o + "</code></pre>", 
        a = s.subParser("hashBlock")(a, r, t), "\n\n~G" + (t.ghCodeBlocks.push({
            text: e,
            codeblock: a
        }) - 1) + "G\n\n";
    }), e = e.replace(/~0/, ""), t.converter._dispatch("githubCodeBlocks.after", e, r, t)) : e;
}), s.subParser("hashBlock", function(e, r, t) {
    return e = e.replace(/(^\n+|\n+$)/g, ""), "\n\n~K" + (t.gHtmlBlocks.push(e) - 1) + "K\n\n";
}), s.subParser("hashElement", function(e, r, t) {
    return function(e, r) {
        var n = r;
        return n = n.replace(/\n\n/g, "\n"), n = n.replace(/^\n/, ""), n = n.replace(/\n+$/g, ""), 
        n = "\n\n~K" + (t.gHtmlBlocks.push(n) - 1) + "K\n\n";
    };
}), s.subParser("hashHTMLBlocks", function(e, r, t) {
    for (var n = [ "pre", "div", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote", "table", "dl", "ol", "ul", "script", "noscript", "form", "fieldset", "iframe", "math", "style", "section", "header", "footer", "nav", "article", "aside", "address", "audio", "canvas", "figure", "hgroup", "output", "video", "p" ], a = 0; a < n.length; ++a) e = s.helper.replaceRecursiveRegExp(e, function(e, r, n, s) {
        var a = e;
        return -1 !== n.search(/\bmarkdown\b/) && (a = n + t.converter.makeHtml(r) + s), 
        "\n\n~K" + (t.gHtmlBlocks.push(a) - 1) + "K\n\n";
    }, "^(?: |\\t){0,3}<" + n[a] + "\\b[^>]*>", "</" + n[a] + ">", "gim");
    return e = e.replace(/(\n[ ]{0,3}(<(hr)\b([^<>])*?\/?>)[ \t]*(?=\n{2,}))/g, s.subParser("hashElement")(e, r, t)), 
    e = e.replace(/(<!--[\s\S]*?-->)/g, s.subParser("hashElement")(e, r, t)), e = e.replace(/(?:\n\n)([ ]{0,3}(?:<([?%])[^\r]*?\2>)[ \t]*(?=\n{2,}))/g, s.subParser("hashElement")(e, r, t));
}), s.subParser("hashHTMLSpans", function(e, r, t) {
    for (var n = s.helper.matchRecursiveRegExp(e, "<code\\b[^>]*>", "</code>", "gi"), a = 0; a < n.length; ++a) e = e.replace(n[a][0], "~L" + (t.gHtmlSpans.push(n[a][0]) - 1) + "L");
    return e;
}), s.subParser("unhashHTMLSpans", function(e, r, t) {
    for (var n = 0; n < t.gHtmlSpans.length; ++n) e = e.replace("~L" + n + "L", t.gHtmlSpans[n]);
    return e;
}), s.subParser("hashPreCodeTags", function(e, r, t) {
    return e = s.helper.replaceRecursiveRegExp(e, function(e, r, n, a) {
        var o = n + s.subParser("encodeCode")(r) + a;
        return "\n\n~G" + (t.ghCodeBlocks.push({
            text: e,
            codeblock: o
        }) - 1) + "G\n\n";
    }, "^(?: |\\t){0,3}<pre\\b[^>]*>\\s*<code\\b[^>]*>", "^(?: |\\t){0,3}</code>\\s*</pre>", "gim");
}), s.subParser("headers", function(e, r, t) {
    function n(e) {
        var r, n = e.replace(/[^\w]/g, "").toLowerCase();
        return t.hashLinkCounts[n] ? r = n + "-" + t.hashLinkCounts[n]++ : (r = n, t.hashLinkCounts[n] = 1), 
        !0 === a && (a = "section"), s.helper.isString(a) ? a + r : r;
    }
    e = t.converter._dispatch("headers.before", e, r, t);
    var a = r.prefixHeaderId, o = isNaN(parseInt(r.headerLevelStart)) ? 1 : parseInt(r.headerLevelStart), i = r.smoothLivePreview ? /^(.+)[ \t]*\n={2,}[ \t]*\n+/gm : /^(.+)[ \t]*\n=+[ \t]*\n+/gm, l = r.smoothLivePreview ? /^(.+)[ \t]*\n-{2,}[ \t]*\n+/gm : /^(.+)[ \t]*\n-+[ \t]*\n+/gm;
    return e = e.replace(i, function(e, a) {
        var i = s.subParser("spanGamut")(a, r, t), l = r.noHeaderId ? "" : ' id="' + n(a) + '"', c = o, u = "<h" + c + l + ">" + i + "</h" + c + ">";
        return s.subParser("hashBlock")(u, r, t);
    }), e = e.replace(l, function(e, a) {
        var i = s.subParser("spanGamut")(a, r, t), l = r.noHeaderId ? "" : ' id="' + n(a) + '"', c = o + 1, u = "<h" + c + l + ">" + i + "</h" + c + ">";
        return s.subParser("hashBlock")(u, r, t);
    }), e = e.replace(/^(#{1,6})[ \t]*(.+?)[ \t]*#*\n+/gm, function(e, a, i) {
        var l = s.subParser("spanGamut")(i, r, t), c = r.noHeaderId ? "" : ' id="' + n(i) + '"', u = o - 1 + a.length, p = "<h" + u + c + ">" + l + "</h" + u + ">";
        return s.subParser("hashBlock")(p, r, t);
    }), e = t.converter._dispatch("headers.after", e, r, t);
}), s.subParser("images", function(e, r, t) {
    function n(e, r, n, a, o, i, l, c) {
        var u = t.gUrls, p = t.gTitles, h = t.gDimensions;
        if (n = n.toLowerCase(), c || (c = ""), "" === a || null === a) {
            if ("" !== n && null !== n || (n = r.toLowerCase().replace(/ ?\n/g, " ")), a = "#" + n, 
            s.helper.isUndefined(u[n])) return e;
            a = u[n], s.helper.isUndefined(p[n]) || (c = p[n]), s.helper.isUndefined(h[n]) || (o = h[n].width, 
            i = h[n].height);
        }
        r = r.replace(/"/g, "&quot;"), r = s.helper.escapeCharacters(r, "*_", !1);
        var d = '<img src="' + (a = s.helper.escapeCharacters(a, "*_", !1)) + '" alt="' + r + '"';
        return c && (c = c.replace(/"/g, "&quot;"), d += ' title="' + (c = s.helper.escapeCharacters(c, "*_", !1)) + '"'), 
        o && i && (d += ' width="' + (o = "*" === o ? "auto" : o) + '"', d += ' height="' + (i = "*" === i ? "auto" : i) + '"'), 
        d += " />";
    }
    var a = /!\[(.*?)]\s?\([ \t]*()<?(\S+?)>?(?: =([*\d]+[A-Za-z%]{0,4})x([*\d]+[A-Za-z%]{0,4}))?[ \t]*(?:(['"])(.*?)\6[ \t]*)?\)/g, o = /!\[([^\]]*?)] ?(?:\n *)?\[(.*?)]()()()()()/g;
    return e = (e = t.converter._dispatch("images.before", e, r, t)).replace(o, n), 
    e = e.replace(a, n), e = t.converter._dispatch("images.after", e, r, t);
}), s.subParser("italicsAndBold", function(e, r, t) {
    return e = t.converter._dispatch("italicsAndBold.before", e, r, t), e = r.literalMidWordUnderscores ? (e = (e = (e = e.replace(/(^|\s|>|\b)__(?=\S)([\s\S]+?)__(?=\b|<|\s|$)/gm, "$1<strong>$2</strong>")).replace(/(^|\s|>|\b)_(?=\S)([\s\S]+?)_(?=\b|<|\s|$)/gm, "$1<em>$2</em>")).replace(/(\*\*)(?=\S)([^\r]*?\S[*]*)\1/g, "<strong>$2</strong>")).replace(/(\*)(?=\S)([^\r]*?\S)\1/g, "<em>$2</em>") : (e = e.replace(/(\*\*|__)(?=\S)([^\r]*?\S[*_]*)\1/g, "<strong>$2</strong>")).replace(/(\*|_)(?=\S)([^\r]*?\S)\1/g, "<em>$2</em>"), 
    e = t.converter._dispatch("italicsAndBold.after", e, r, t);
}), s.subParser("lists", function(e, r, t) {
    function n(e, n) {
        t.gListLevel++, e = e.replace(/\n{2,}$/, "\n"), e += "~0";
        var a = /(\n)?(^[ \t]*)([*+-]|\d+[.])[ \t]+((\[(x|X| )?])?[ \t]*[^\r]+?(\n{1,2}))(?=\n*(~0|\2([*+-]|\d+[.])[ \t]+))/gm, o = /\n[ \t]*\n(?!~0)/.test(e);
        return e = e.replace(a, function(e, n, a, i, l, c, u) {
            u = u && "" !== u.trim();
            var p = s.subParser("outdent")(l, r, t), h = "";
            return c && r.tasklists && (h = ' class="task-list-item" style="list-style-type: none;"', 
            p = p.replace(/^[ \t]*\[(x|X| )?]/m, function() {
                var e = '<input type="checkbox" disabled style="margin: 0px 0.35em 0.25em -1.6em; vertical-align: middle;"';
                return u && (e += " checked"), e += ">";
            })), n || p.search(/\n{2,}/) > -1 ? (p = s.subParser("githubCodeBlocks")(p, r, t), 
            p = s.subParser("blockGamut")(p, r, t)) : (p = (p = s.subParser("lists")(p, r, t)).replace(/\n$/, ""), 
            p = o ? s.subParser("paragraphs")(p, r, t) : s.subParser("spanGamut")(p, r, t)), 
            p = "\n<li" + h + ">" + p + "</li>\n";
        }), e = e.replace(/~0/g, ""), t.gListLevel--, n && (e = e.replace(/\s+$/, "")), 
        e;
    }
    function a(e, r, t) {
        var s = "ul" === r ? /^ {0,2}\d+\.[ \t]/gm : /^ {0,2}[*+-][ \t]/gm, a = [], o = "";
        if (-1 !== e.search(s)) {
            !function e(a) {
                var i = a.search(s);
                -1 !== i ? (o += "\n\n<" + r + ">" + n(a.slice(0, i), !!t) + "</" + r + ">\n\n", 
                s = "ul" === (r = "ul" === r ? "ol" : "ul") ? /^ {0,2}\d+\.[ \t]/gm : /^ {0,2}[*+-][ \t]/gm, 
                e(a.slice(i))) : o += "\n\n<" + r + ">" + n(a, !!t) + "</" + r + ">\n\n";
            }(e);
            for (var i = 0; i < a.length; ++i) ;
        } else o = "\n\n<" + r + ">" + n(e, !!t) + "</" + r + ">\n\n";
        return o;
    }
    e = t.converter._dispatch("lists.before", e, r, t), e += "~0";
    var o = /^(([ ]{0,3}([*+-]|\d+[.])[ \t]+)[^\r]+?(~0|\n{2,}(?=\S)(?![ \t]*(?:[*+-]|\d+[.])[ \t]+)))/gm;
    return t.gListLevel ? e = e.replace(o, function(e, r, t) {
        return a(r, t.search(/[*+-]/g) > -1 ? "ul" : "ol", !0);
    }) : (o = /(\n\n|^\n?)(([ ]{0,3}([*+-]|\d+[.])[ \t]+)[^\r]+?(~0|\n{2,}(?=\S)(?![ \t]*(?:[*+-]|\d+[.])[ \t]+)))/gm, 
    e = e.replace(o, function(e, r, t, n) {
        return a(t, n.search(/[*+-]/g) > -1 ? "ul" : "ol");
    })), e = e.replace(/~0/, ""), e = t.converter._dispatch("lists.after", e, r, t);
}), s.subParser("outdent", function(e) {
    return e = e.replace(/^(\t|[ ]{1,4})/gm, "~0"), e = e.replace(/~0/g, "");
}), s.subParser("paragraphs", function(e, r, t) {
    for (var n = (e = (e = (e = t.converter._dispatch("paragraphs.before", e, r, t)).replace(/^\n+/g, "")).replace(/\n+$/g, "")).split(/\n{2,}/g), a = [], o = n.length, i = 0; i < o; i++) {
        var l = n[i];
        l.search(/~(K|G)(\d+)\1/g) >= 0 ? a.push(l) : (l = (l = s.subParser("spanGamut")(l, r, t)).replace(/^([ \t]*)/g, "<p>"), 
        l += "</p>", a.push(l));
    }
    for (o = a.length, i = 0; i < o; i++) {
        for (var c = "", u = a[i], p = !1; u.search(/~(K|G)(\d+)\1/) >= 0; ) {
            var h = RegExp.$1, d = RegExp.$2;
            c = (c = "K" === h ? t.gHtmlBlocks[d] : p ? s.subParser("encodeCode")(t.ghCodeBlocks[d].text) : t.ghCodeBlocks[d].codeblock).replace(/\$/g, "$$$$"), 
            u = u.replace(/(\n\n)?~(K|G)\d+\2(\n\n)?/, c), /^<pre\b[^>]*>\s*<code\b[^>]*>/.test(u) && (p = !0);
        }
        a[i] = u;
    }
    return e = a.join("\n\n"), e = e.replace(/^\n+/g, ""), e = e.replace(/\n+$/g, ""), 
    t.converter._dispatch("paragraphs.after", e, r, t);
}), s.subParser("runExtension", function(e, r, t, n) {
    if (e.filter) r = e.filter(r, n.converter, t); else if (e.regex) {
        var s = e.regex;
        !s instanceof RegExp && (s = new RegExp(s, "g")), r = r.replace(s, e.replace);
    }
    return r;
}), s.subParser("spanGamut", function(e, r, t) {
    return e = t.converter._dispatch("spanGamut.before", e, r, t), e = s.subParser("codeSpans")(e, r, t), 
    e = s.subParser("escapeSpecialCharsWithinTagAttributes")(e, r, t), e = s.subParser("encodeBackslashEscapes")(e, r, t), 
    e = s.subParser("images")(e, r, t), e = s.subParser("anchors")(e, r, t), e = s.subParser("autoLinks")(e, r, t), 
    e = s.subParser("encodeAmpsAndAngles")(e, r, t), e = s.subParser("italicsAndBold")(e, r, t), 
    e = s.subParser("strikethrough")(e, r, t), e = e.replace(/  +\n/g, " <br />\n"), 
    e = t.converter._dispatch("spanGamut.after", e, r, t);
}), s.subParser("strikethrough", function(e, r, t) {
    return r.strikethrough && (e = (e = t.converter._dispatch("strikethrough.before", e, r, t)).replace(/(?:~T){2}([\s\S]+?)(?:~T){2}/g, "<del>$1</del>"), 
    e = t.converter._dispatch("strikethrough.after", e, r, t)), e;
}), s.subParser("stripBlankLines", function(e) {
    return e.replace(/^[ \t]+$/gm, "");
}), s.subParser("stripLinkDefinitions", function(e, r, t) {
    var n = /^ {0,3}\[(.+)]:[ \t]*\n?[ \t]*<?(\S+?)>?(?: =([*\d]+[A-Za-z%]{0,4})x([*\d]+[A-Za-z%]{0,4}))?[ \t]*\n?[ \t]*(?:(\n*)["|'(](.+?)["|')][ \t]*)?(?:\n+|(?=~0))/gm;
    return e += "~0", e = e.replace(n, function(e, n, a, o, i, l, c) {
        return n = n.toLowerCase(), t.gUrls[n] = s.subParser("encodeAmpsAndAngles")(a), 
        l ? l + c : (c && (t.gTitles[n] = c.replace(/"|'/g, "&quot;")), r.parseImgDimensions && o && i && (t.gDimensions[n] = {
            width: o,
            height: i
        }), "");
    }), e = e.replace(/~0/, "");
}), s.subParser("tables", function(e, r, t) {
    function n(e) {
        return /^:[ \t]*--*$/.test(e) ? ' style="text-align:left;"' : /^--*[ \t]*:[ \t]*$/.test(e) ? ' style="text-align:right;"' : /^:[ \t]*--*[ \t]*:$/.test(e) ? ' style="text-align:center;"' : "";
    }
    function a(e, n) {
        var a = "";
        return e = e.trim(), r.tableHeaderId && (a = ' id="' + e.replace(/ /g, "_").toLowerCase() + '"'), 
        e = s.subParser("spanGamut")(e, r, t), "<th" + a + n + ">" + e + "</th>\n";
    }
    function o(e, n) {
        return "<td" + n + ">" + s.subParser("spanGamut")(e, r, t) + "</td>\n";
    }
    function i(e, r) {
        for (var t = "<table>\n<thead>\n<tr>\n", n = e.length, s = 0; s < n; ++s) t += e[s];
        for (t += "</tr>\n</thead>\n<tbody>\n", s = 0; s < r.length; ++s) {
            t += "<tr>\n";
            for (var a = 0; a < n; ++a) t += r[s][a];
            t += "</tr>\n";
        }
        return t += "</tbody>\n</table>\n";
    }
    if (!r.tables) return e;
    var l = /^[ \t]{0,3}\|?.+\|.+\n[ \t]{0,3}\|?[ \t]*:?[ \t]*(?:-|=){2,}[ \t]*:?[ \t]*\|[ \t]*:?[ \t]*(?:-|=){2,}[\s\S]+?(?:\n\n|~0)/gm;
    return e = t.converter._dispatch("tables.before", e, r, t), e = e.replace(l, function(e) {
        var r, t = e.split("\n");
        for (r = 0; r < t.length; ++r) /^[ \t]{0,3}\|/.test(t[r]) && (t[r] = t[r].replace(/^[ \t]{0,3}\|/, "")), 
        /\|[ \t]*$/.test(t[r]) && (t[r] = t[r].replace(/\|[ \t]*$/, ""));
        var l = t[0].split("|").map(function(e) {
            return e.trim();
        }), c = t[1].split("|").map(function(e) {
            return e.trim();
        }), u = [], p = [], h = [], d = [];
        for (t.shift(), t.shift(), r = 0; r < t.length; ++r) "" !== t[r].trim() && u.push(t[r].split("|").map(function(e) {
            return e.trim();
        }));
        if (l.length < c.length) return e;
        for (r = 0; r < c.length; ++r) h.push(n(c[r]));
        for (r = 0; r < l.length; ++r) s.helper.isUndefined(h[r]) && (h[r] = ""), p.push(a(l[r], h[r]));
        for (r = 0; r < u.length; ++r) {
            for (var f = [], g = 0; g < p.length; ++g) s.helper.isUndefined(u[r][g]), f.push(o(u[r][g], h[g]));
            d.push(f);
        }
        return i(p, d);
    }), e = t.converter._dispatch("tables.after", e, r, t);
}), s.subParser("unescapeSpecialChars", function(e) {
    return e = e.replace(/~E(\d+)E/g, function(e, r) {
        var t = parseInt(r);
        return String.fromCharCode(t);
    });
}), module.exports = s;