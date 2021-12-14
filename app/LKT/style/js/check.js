function LTrim(str) {
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(0)) != -1) {
        var j = 0,
            i = s.length;
        while (j < i && whitespace.indexOf(s.charAt(j)) != -1) {
            j++;
        }
        s = s.substring(j, i);
    }
    return s;
}
/*
==================================================================
RTrim(string):去除右边的空格
==================================================================
*/
function RTrim(str) {
    var whitespace = new String(" \t\n\r");
    var s = new String(str);
    if (whitespace.indexOf(s.charAt(s.length - 1)) != -1) {
        var i = s.length - 1;
        while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1) {
            i--;
        }
        s = s.substring(0, i + 1);
    }
    return s;
}
/*
==================================================================
Trim(string):去除前后空格
==================================================================
*/
function Trim(str) {
    return RTrim(LTrim(str));
}
/*
================================================================================
XMLEncode(string):对字符串进行XML编码
================================================================================
*/
function XMLEncode(str) {
    str = Trim(str);
    str = str.replace("&", "&amp;");
    str = str.replace("<", "&lt;");
    str = str.replace(">", "&gt;");
    str = str.replace("'", "&apos;");
    str = str.replace("\"", "&quot;");
    return str;
}
/*
================================================================================
验证类函数
================================================================================
*/
function IsEmpty(obj) {
    obj = document.getElementsByName(obj).item(0);
    if (Trim(obj.value) == "") {
        alert("字段不能为空。");
        if (obj.disabled == false && obj.readOnly == false) {
            obj.focus();
        }
    }
}
/*
IsInt(string,string,int or string):(测试字符串,+ or - or empty,empty or 0)
功能：判断是否为整数、正整数、负整数、正整数+0、负整数+0
*/
function IsInt(objStr, sign, zero) {
    var reg;
    var bolzero;
    if (Trim(objStr) == "") {
        return false;
    } else {
        objStr = objStr.toString();
    }
    if ((sign == null) || (Trim(sign) == "")) {
        sign = "+-";
    }
    if ((zero == null) || (Trim(zero) == "")) {
        bolzero = false;
    } else {
        zero = zero.toString();
        if (zero == "0") {
            bolzero = true;
        } else {
            alert("检查是否包含0参数，只可为(空、0)");
        }
    }
    switch (sign) {
        case "+-":
            //整数
            reg = /(^-?|^\+?)\d+$/;
            break;
        case "+":
            if (!bolzero) {
                //正整数
                reg = /^\+?[0-9]*[1-9][0-9]*$/;
            } else {
                //正整数+0
                //reg=/^\+?\d+$/;
                reg = /^\+?[0-9]*[0-9][0-9]*$/;
            }
            break;
        case "-":
            if (!bolzero) {
                //负整数
                reg = /^-[0-9]*[1-9][0-9]*$/;
            } else {
                //负整数+0
                //reg=/^-\d+$/;
                reg = /^-[0-9]*[0-9][0-9]*$/;
            }
            break;
        default:
            alert("检查符号参数，只可为(空、+、-)");
            return false;
            break;
    }
    var r = objStr.match(reg);
    if (r == null) {
        return false;
    } else {
        return true;
    }
}
/*
IsFloat(string,string,int or string):(测试字符串,+ or - or empty,empty or 0)
功能：判断是否为浮点数、正浮点数、负浮点数、正浮点数+0、负浮点数+0
*/
function IsFloat(objStr, sign, zero) {
    var reg;
    var bolzero;
    if (Trim(objStr) == "") {
        return false;
    } else {
        objStr = objStr.toString();
    }
    if ((sign == null) || (Trim(sign) == "")) {
        sign = "+-";
    }
    if ((zero == null) || (Trim(zero) == "")) {
        bolzero = false;
    } else {
        zero = zero.toString();
        if (zero == "0") {
            bolzero = true;
        } else {
            alert("检查是否包含0参数，只可为(空、0)");
        }
    }
    switch (sign) {
        case "+-":
            //浮点数
            reg = /^((-?|\+?)\d+)(\.\d+)?$/;
            break;
        case "+":
            if (!bolzero) {
                //正浮点数
                reg = /^\+?(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
            } else {
                //正浮点数+0
                reg = /^\+?\d+(\.\d+)?$/;
            }
            break;
        case "-":
            if (!bolzero) {
                //负浮点数
                reg = /^-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
            } else {
                //负浮点数+0
                reg = /^((-\d+(\.\d+)?)|(0+(\.0+)?))$/;
            }
            break;
        default:
            alert("检查符号参数，只可为(空、+、-)");
            return false;
            break;
    }
    var r = objStr.match(reg);
    if (r == null) {
        return false;
    } else {
        return true;
    }
}

function CheckInteger(Event) {
    if ((Event.keyCode > 7 && Event.keyCode < 10) || (Event.keyCode > 27 && Event.keyCode < 30) || Event.keyCode > 47 && Event.keyCode < 58) {
        Event.returnValue = Event.keyCode;
    } else {
        Event.cancelBubble = true;
        Event.returnValue = false;
    }
}

/*
*判断邮箱号码是否正确
*/
function CheckEmail(address) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(address)) {
        return (false)
    }
    return (true)
}

/*
*身份证号合法性验证，返回对象{'pass':true,'msg':'格式正确'}
*支持15位和18位身份证号
*支持地址编码、出生日期、校验位验证
*/
function IdCodeValid(code) {
    var city = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外 "
    };
    var row = {
        'pass': true,
        'msg': '验证成功'
    };
    if (!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[012])(0[1-9]|[12]\d|3[01])\d{3}(\d|[xX])$/.test(code)) {
        row = {
            'pass': false,
            'msg': '身份证号格式错误'
        };
    } else if (!city[code.substr(0, 2)]) {
        row = {
            'pass': false,
            'msg': '身份证号地址编码错误'
        };
    } else {
        //18位身份证需要验证最后一位校验位
        if (code.length == 18) {
            code = code.split('');
            //加权因子
            var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            //校验位
            var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++) {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            if (parity[sum % 11] != code[17].toUpperCase()) {
                row = {
                    'pass': false,
                    'msg': '身份证号校验位错误'
                };
            }
        }
    }
    return row;
}