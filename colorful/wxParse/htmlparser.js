function e(e) {
    for (var t = {}, r = e.split(","), s = 0; s < r.length; s++) t[r[s]] = !0;
    return t;
}

var t = /^<([-A-Za-z0-9_]+)((?:\s+[a-zA-Z_:][-a-zA-Z0-9_:.]*(?:\s*=\s*(?:(?:"[^"]*")|(?:'[^']*')|[^>\s]+))?)*)\s*(\/?)>/, r = /^<\/([-A-Za-z0-9_]+)[^>]*>/, s = /([a-zA-Z_:][-a-zA-Z0-9_:.]*)(?:\s*=\s*(?:(?:"((?:\\.|[^"])*)")|(?:'((?:\\.|[^'])*)')|([^>\s]+)))?/g, a = e("area,base,basefont,br,col,frame,hr,img,input,link,meta,param,embed,command,keygen,source,track,wbr"), n = e("a,address,code,article,applet,aside,audio,blockquote,button,canvas,center,dd,del,dir,div,dl,dt,fieldset,figcaption,figure,footer,form,frameset,h1,h2,h3,h4,h5,h6,header,hgroup,hr,iframe,ins,isindex,li,map,menu,noframes,noscript,object,ol,output,p,pre,section,script,table,tbody,td,tfoot,th,thead,tr,ul,video"), i = e("abbr,acronym,applet,b,basefont,bdo,big,br,button,cite,del,dfn,em,font,i,iframe,img,input,ins,kbd,label,map,object,q,s,samp,script,select,small,span,strike,strong,sub,sup,textarea,tt,u,var"), o = e("colgroup,dd,dt,li,options,p,td,tfoot,th,thead,tr"), l = e("checked,compact,declare,defer,disabled,ismap,multiple,nohref,noresize,noshade,nowrap,readonly,selected"), c = e("wxxxcode-style,script,style,view,scroll-view,block");

module.exports = function(e, d) {
    function f(e, t) {
        if (t) for (t = t.toLowerCase(), r = b.length - 1; r >= 0 && b[r] != t; r--) ; else var r = 0;
        if (r >= 0) {
            for (var s = b.length - 1; s >= r; s--) d.end && d.end(b[s]);
            b.length = r;
        }
    }
    var p, u, h, b = [], m = e;
    for (b.last = function() {
        return this[this.length - 1];
    }; e; ) {
        if (u = !0, b.last() && c[b.last()]) e = e.replace(new RegExp("([\\s\\S]*?)</" + b.last() + "[^>]*>"), function(e, t) {
            return t = t.replace(/<!--([\s\S]*?)-->|<!\[CDATA\[([\s\S]*?)]]>/g, "$1$2"), d.chars && d.chars(t), 
            "";
        }), f(0, b.last()); else if (0 == e.indexOf("\x3c!--") ? (p = e.indexOf("--\x3e")) >= 0 && (d.comment && d.comment(e.substring(4, p)), 
        e = e.substring(p + 3), u = !1) : 0 == e.indexOf("</") ? (h = e.match(r)) && (e = e.substring(h[0].length), 
        h[0].replace(r, f), u = !1) : 0 == e.indexOf("<") && (h = e.match(t)) && (e = e.substring(h[0].length), 
        h[0].replace(t, function(e, t, r, c) {
            if (t = t.toLowerCase(), n[t]) for (;b.last() && i[b.last()]; ) f(0, b.last());
            if (o[t] && b.last() == t && f(0, t), (c = a[t] || !!c) || b.push(t), d.start) {
                var p = [];
                r.replace(s, function(e, t) {
                    var r = arguments[2] ? arguments[2] : arguments[3] ? arguments[3] : arguments[4] ? arguments[4] : l[t] ? t : "";
                    p.push({
                        name: t,
                        value: r,
                        escaped: r.replace(/(^|[^\\])"/g, '$1\\"')
                    });
                }), d.start && d.start(t, p, c);
            }
        }), u = !1), u) {
            p = e.indexOf("<");
            for (var g = ""; 0 === p; ) g += "<", p = (e = e.substring(1)).indexOf("<");
            g += p < 0 ? e : e.substring(0, p), e = p < 0 ? "" : e.substring(p), d.chars && d.chars(g);
        }
        if (e == m) throw "Parse Error: " + e;
        m = e;
    }
    f();
};