function n(n, r) {
    var t = (65535 & n) + (65535 & r);
    return (n >> 16) + (r >> 16) + (t >> 16) << 16 | 65535 & t;
}

function r(n, r) {
    return n << r | n >>> 32 - r;
}

function t(t, e, u, o, c, f) {
    return n(r(n(n(e, t), n(o, f)), c), u);
}

function e(n, r, e, u, o, c, f) {
    return t(r & e | ~r & u, n, r, o, c, f);
}

function u(n, r, e, u, o, c, f) {
    return t(r & u | e & ~u, n, r, o, c, f);
}

function o(n, r, e, u, o, c, f) {
    return t(r ^ e ^ u, n, r, o, c, f);
}

function c(n, r, e, u, o, c, f) {
    return t(e ^ (r | ~u), n, r, o, c, f);
}

function f(r) {
    for (var t = 1732584193, f = -271733879, i = -1732584194, a = 271733878, h = 0; h < r.length; h += 16) {
        var l = t, g = f, v = i, d = a;
        f = c(f = c(f = c(f = c(f = o(f = o(f = o(f = o(f = u(f = u(f = u(f = u(f = e(f = e(f = e(f = e(f, i = e(i, a = e(a, t = e(t, f, i, a, r[h + 0], 7, -680876936), f, i, r[h + 1], 12, -389564586), t, f, r[h + 2], 17, 606105819), a, t, r[h + 3], 22, -1044525330), i = e(i, a = e(a, t = e(t, f, i, a, r[h + 4], 7, -176418897), f, i, r[h + 5], 12, 1200080426), t, f, r[h + 6], 17, -1473231341), a, t, r[h + 7], 22, -45705983), i = e(i, a = e(a, t = e(t, f, i, a, r[h + 8], 7, 1770035416), f, i, r[h + 9], 12, -1958414417), t, f, r[h + 10], 17, -42063), a, t, r[h + 11], 22, -1990404162), i = e(i, a = e(a, t = e(t, f, i, a, r[h + 12], 7, 1804603682), f, i, r[h + 13], 12, -40341101), t, f, r[h + 14], 17, -1502002290), a, t, r[h + 15], 22, 1236535329), i = u(i, a = u(a, t = u(t, f, i, a, r[h + 1], 5, -165796510), f, i, r[h + 6], 9, -1069501632), t, f, r[h + 11], 14, 643717713), a, t, r[h + 0], 20, -373897302), i = u(i, a = u(a, t = u(t, f, i, a, r[h + 5], 5, -701558691), f, i, r[h + 10], 9, 38016083), t, f, r[h + 15], 14, -660478335), a, t, r[h + 4], 20, -405537848), i = u(i, a = u(a, t = u(t, f, i, a, r[h + 9], 5, 568446438), f, i, r[h + 14], 9, -1019803690), t, f, r[h + 3], 14, -187363961), a, t, r[h + 8], 20, 1163531501), i = u(i, a = u(a, t = u(t, f, i, a, r[h + 13], 5, -1444681467), f, i, r[h + 2], 9, -51403784), t, f, r[h + 7], 14, 1735328473), a, t, r[h + 12], 20, -1926607734), i = o(i, a = o(a, t = o(t, f, i, a, r[h + 5], 4, -378558), f, i, r[h + 8], 11, -2022574463), t, f, r[h + 11], 16, 1839030562), a, t, r[h + 14], 23, -35309556), i = o(i, a = o(a, t = o(t, f, i, a, r[h + 1], 4, -1530992060), f, i, r[h + 4], 11, 1272893353), t, f, r[h + 7], 16, -155497632), a, t, r[h + 10], 23, -1094730640), i = o(i, a = o(a, t = o(t, f, i, a, r[h + 13], 4, 681279174), f, i, r[h + 0], 11, -358537222), t, f, r[h + 3], 16, -722521979), a, t, r[h + 6], 23, 76029189), i = o(i, a = o(a, t = o(t, f, i, a, r[h + 9], 4, -640364487), f, i, r[h + 12], 11, -421815835), t, f, r[h + 15], 16, 530742520), a, t, r[h + 2], 23, -995338651), i = c(i, a = c(a, t = c(t, f, i, a, r[h + 0], 6, -198630844), f, i, r[h + 7], 10, 1126891415), t, f, r[h + 14], 15, -1416354905), a, t, r[h + 5], 21, -57434055), i = c(i, a = c(a, t = c(t, f, i, a, r[h + 12], 6, 1700485571), f, i, r[h + 3], 10, -1894986606), t, f, r[h + 10], 15, -1051523), a, t, r[h + 1], 21, -2054922799), i = c(i, a = c(a, t = c(t, f, i, a, r[h + 8], 6, 1873313359), f, i, r[h + 15], 10, -30611744), t, f, r[h + 6], 15, -1560198380), a, t, r[h + 13], 21, 1309151649), i = c(i, a = c(a, t = c(t, f, i, a, r[h + 4], 6, -145523070), f, i, r[h + 11], 10, -1120210379), t, f, r[h + 2], 15, 718787259), a, t, r[h + 9], 21, -343485551), 
        t = n(t, l), f = n(f, g), i = n(i, v), a = n(a, d);
    }
    return [ t, f, i, a ];
}

function i(n) {
    for (var r = "", t = 0; t < 4 * n.length; t++) r += "0123456789abcdef".charAt(n[t >> 2] >> t % 4 * 8 + 4 & 15) + "0123456789abcdef".charAt(n[t >> 2] >> t % 4 * 8 & 15);
    return r;
}

function a(n) {
    for (var r = 1 + (n.length + 8 >> 6), t = new Array(16 * r), e = 0; e < 16 * r; e++) t[e] = 0;
    for (e = 0; e < n.length; e++) t[e >> 2] |= (255 & n.charCodeAt(e)) << e % 4 * 8;
    return t[e >> 2] |= 128 << e % 4 * 8, t[16 * r - 2] = 8 * n.length, t;
}

module.exports = {
    hexMD5: function(n) {
        return i(f(a(n)));
    }
};