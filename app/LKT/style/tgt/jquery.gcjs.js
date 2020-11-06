jQuery.extend({
    isEmail: function(str) {
        return /^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$/.test($.trim(str));
    },
    isIDCard: function(obj) {
        var aCity = {11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙 江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖 北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西 藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国 外"};
        var iSum = 0;
        //var info = "";
        var strIDno = obj;
        var idCardLength = strIDno.length;
        if (!/^\d{17}(\d|x)$/i.test(strIDno) && !/^\d{15}$/i.test(strIDno))
            return false; //非法身份证号

        if (aCity[parseInt(strIDno.substr(0, 2))] == null)
            return false;// 非法地区

        // 15位身份证转换为18位
        if (idCardLength == 15)
        {
            sBirthday = "19" + strIDno.substr(6, 2) + "-" + Number(strIDno.substr(8, 2)) + "-" + Number(strIDno.substr(10, 2));
            var d = new Date(sBirthday.replace(/-/g, "/"))
            var dd = d.getFullYear().toString() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
            if (sBirthday != dd)
                return false; //非法生日
            strIDno = strIDno.substring(0, 6) + "19" + strIDno.substring(6, 15);
            strIDno = strIDno + GetVerifyBit(strIDno);
        }

        // 判断是否大于2078年，小于1900年
        var year = strIDno.substring(6, 10);
        if (year < 1900 || year > 2078)
            return false;//非法生日

        //18位身份证处理

        //在后面的运算中x相当于数字10,所以转换成a
        strIDno = strIDno.replace(/x$/i, "a");

        sBirthday = strIDno.substr(6, 4) + "-" + Number(strIDno.substr(10, 2)) + "-" + Number(strIDno.substr(12, 2));
        var d = new Date(sBirthday.replace(/-/g, "/"))
        if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate()))
            return false; //非法生日
        // 身份证编码规范验证
        for (var i = 17; i >= 0; i --)
            iSum += (Math.pow(2, i) % 11) * parseInt(strIDno.charAt(17 - i), 11);
        if (iSum % 11 != 1)
            return false;// 非法身份证号

        // 判断是否屏蔽身份证
        var words = new Array();
        words = new Array("11111119111111111", "12121219121212121");

        for (var k = 0; k < words.length; k++) {
            if (strIDno.indexOf(words[k]) != -1) {
                return false;
            }
        }

        return true;


    },
    isUrl: function(str) {
        return /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/.test($.trim(str));
    },
    isInt: function(str) {
        return  /^[-\+]?\d+$/.test($.trim(str));
    },
    isUserID: function(str) {
        return /^\s*[A-Za-z0-9_-]{6,20}\s*$/.test($.trim(str));
    },
    isMobile: function(str) {
        // 20171110
        // return $.trim(str) !== '' && /^[1|8][2|3|4|5|7|8][0-9]\d{4,8}$/.test($.trim(str));
        return $.trim(str) !== '' && /^1[3|4|5|7|8][0-9]{9}$/.test($.trim(str)) ;

    },
    isChinese: function(str) {
        return $.trim(str) != '' & !/[^\u4e00-\u9fa5]/.test($.trim(str));
    },
    isEnglish:function(str){
       return $.trim(str) != '' & !/[^a-zA-Z]/.test($.trim(str));
    },
    isPassword: function(str) {
        return  /^[^\u4e00-\u9fa5\s]{6,20}$/.test($.trim(str));
    },
    isFloat: function(str) {
        return /^(\+|-)?\d+($|\.\d+$)/.test($.trim(str));
    },
    isNumber: function(str) {
        return !$.isEmpty(str) && !isNaN(str);
    },
    isIP: function(str) {
        if (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test($.trim(str))) {
            if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256)
                return true;
        }
        return false;
    },
    isDate: function(str) {
        var r = $.trim(str).split("-");
        if (r == null)
            return false;
        var d = new Date(r[0], r[1] - 1, r[2]);
        return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d.getDate() == r[2]);
    },
    htmlEncode: function(str) {
        str = str.replace(/&/g, '&amp;');
        str = str.replace(/</g, '&lt;');
        str = str.replace(/>/g, '&gt;');
        str = str.replace(/(?:\t| |\v|\r)*\n/g, '<br />');
        str = str.replace(/  /g, '&nbsp; ');
        str = str.replace(/\t/g, '&nbsp; &nbsp; ');
        str = str.replace(/\x22/g, '&quot;');
        str = str.replace(/\x27/g, '&#39;');
        return $.trim(str);
    },
    htmlDecode: function(str) {
        str = str.replace(/&amp;/gi, '&');
        str = str.replace(/&nbsp;/gi, ' ');
        str = str.replace(/&quot;/gi, '"');
        str = str.replace(/&#39;/g, "'");
        str = str.replace(/&lt;/gi, '<');
        str = str.replace(/&gt;/gi, '>');
        str = str.replace(/<br[^>]*>(?:(\r\n)|\r|\n)?/gi, '\n');
        return $.trim(str);
    },
    preview: function(str) {
        var testwin = open("");
        testwin.document.open();
        testwin.document.write(str);
        testwin.document.close();
    },
    isEmpty: function(str) {
        return $.trim(str) == '' || str == undefined
    },
    arrayIndexOf: function(arr, substr, start) {
        var ta, rt, d = '\0';
        if (start != null) {
            ta = arr.slice(start);
            rt = start;
        } else {
            ta = arr;
            rt = 0;
        }
        var str = d + ta.join(d) + d, t = str.indexOf(d + substr + d);
        if (t == -1)
            return -1;
        rt += str.slice(0, t).replace(/[^\0]/g, '').length;
        return rt;
    },
    arrayLastIndexOf: function(arr, substr, start) {
        var ta, rt, d = '\0';
        if (start != null) {
            ta = arr.slice(start);
            rt = start;
        } else {
            ta = arr;
            rt = 0;
        }
        ta = ta.reverse();
        var str = d + ta.join(d) + d, t = str.indexOf(d + substr + d);
        if (t == -1)
            return -1;
        rt += str.slice(t).replace(/[^\0]/g, '').length - 2;
        return rt;
    },
    arrayReplace: function(arr, reg, rpby) {
        var ta = arr.slice(0), d = '\0';
        var str = ta.join(d);
        str = str.replace(reg, rpby);
        return str.split(d);
    },
    arraySearch: function(arr, reg) {
        var ta = arr.slice(0), d = '\0', str = d + ta.join(d) + d, regstr = reg.toString();
        reg = new RegExp(regstr.replace(/\/((.|\n)+)\/.*/g, '\\0$1\\0'), regstr.slice(regstr.lastIndexOf('/') + 1));
        t = str.search(reg);
        if (t == -1)
            return -1;
        return str.slice(0, t).replace(/[^\0]/g, '').length;
    },
    nullundefined: function(str) {
    },
    setClipboard: function(maintext) {
        if (window.clipboardData) {
            window.clipboardData.setData("Text", maintext);
            alert("复制成功！");
        }
        else if (window.netscape) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            } catch (e) {
                alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 'signed.applets.codebase_principal_support'设置为'true'");
                return;
            }
            var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
            if (!clip)
                return;
            var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
            if (!trans)
                return;
            trans.addDataFlavor('text/unicode');
            var str = new Object();
            var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
            var copytext = txt;
            str.data = copytext;
            trans.setTransferData("text/unicode", str, copytext.length * 2);
            var clipid = Components.interfaces.nsIClipboard;
            if (!clip)
                return false;
            clip.setData(trans, null, clipid.kGlobalClipboard);
            alert("复制成功！")

        }
    }
});
(function($) {
    $.fn.isInt = function() {
        return $.isInt($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isIDCard = function() {
        return $.isIDCard($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isEnglish = function() {
        return $.isEnglish($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isEmail = function() {
        return $.isEmail($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isDate = function() {
        return $.isDate($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isIP = function() {
        return $.isIP($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isChinese = function() {
        return $.isChinese($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isEmpty = function() {
        return $.isEmpty($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isUrl = function() {
        return $.isUrl($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isFloat = function() {
        return $.isFloat($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isNumber = function() {
        return $.isNumber($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isUserID = function() {
        return $.isUserID($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isPassword = function() {
        return $.isPassword($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.isMobile = function() {
        return $.isMobile($(this).val());
    }
})(jQuery);
(function($) {
    $.fn.len = function() {
        return $(this).val().length;
    }
})(jQuery);
(function($) {
    $.fn.checked = function() {

        return $(this).get(0).checked();
    }
})(jQuery);
(function($){
    
    $.fn.resizeImage = function(iwidth,iheight){
        
        var w = $(this).width() ;
        var h =  $(this).height() ;
        var _img = new Image();
        _img.src = $(this).attr("src");
        if(_img.width > _img.height)
        {
           h = (_img.height / _img.width) *w;
            w = (_img.width > iwidth) ? iwidth : _img.width;
           
        }
        else if(_img.width < _img.height)
        {
           w= (_img.width / _img.height) * h ;
           h= (_img.height > iheight) ? iheight : _img.height;
           
        }
        else
        {
          h = (_img.height > iheight) ? iheight : _img.height;
          w= (_img.width > iwidth) ? iwidth : _img.width;
        }
        $(this).css({width: w+ "px",height:h+"px"});
    }
       
    
})(jQuery);
(function($) {

    $.fn.breakWords = function(width) {
        var _this = $(this);
        var content = _this.html();

        if (content == undefined) {
            return;
        }
        if (content.length <= width) {
            _this.html(content);
        }
        else {
            var str = '';

            for (var i = 0; i < content.length; i++) {
                if (i % width != 0) {
                    str += content.substr(i, 1);

                }
                else {
                    if (i != 0) {
                        str += "<br/>" + content.substr(i, 1);
                    }
                }
            }

            _this.html(str);
        }

    }
})(jQuery);
(function($) {
    $.fn.limitWidth = function() {
        var _this = $(this);
        var elments = _this.getElementsByTagName("*");
        for (var i = 0; i < elments.length; i++) {
            if (parseInt(elments[i].getAttribute("width")) > width) {
                elments[i].setAttribute("width", width - 1);
            }
            if (elments[i].style.width) {
                if (parseInt(elments[i].style.width) > width) {
                    elments[i].style.width = width - 1;
                }
            }
        }
        return _this;
    }
})(jQuery);
(function($) {
    $.fn.limitLength = function(num, inputID,isInput) {

        var textarea = $(this);
         
        var input = null;
        if (inputID) {
            input = $("#" + inputID);
        }
        if (input) {
          isInput?input.val(num - textarea.len()):input.html(num - textarea.len());
        }
        function limit(textarea, num) {
            if (textarea.len() >= num) {
                textarea.val(textarea.val().substring(0, num));
            }
            else {
                if (input) {
                   isInput?input.val(num - textarea.len()):input.html(num - textarea.len());
                }
            }
        }
        textarea.keypress(function(e) {
            limit($(this), num);
        }).keyup(function(e) {
            limit($(this), num);
        }).mouseup(function(e) {
            limit($(this), num);
        });
        return textarea;
    }
})(jQuery);
(function($) {
    $.fn.nulldefined = function() {
    }
})(jQuery);
var GC = {
    addFavorite: function(url, text) //收藏夹
    {
        if (document.all) {
            window.external.addFavorite(url, text);
        }
        else if (window.sidebar) {
            window.sidebar.addPanel(text, url, "");
        }
    },
        
    holder: function(objid){
          
          var _this = $("#" + objid);
          var placeholder = _this.attr("holder");
          if($.trim(_this.val())==""){
                  _this.addClass("placeholder");
                  _this.val(placeholder);
          }
          _this.focus(function(){
              if($.trim(_this.val())==placeholder){
                  _this.removeClass("placeholder");
                  _this.val("");
              }
           }).blur(function(){ 
              if($.trim(_this.val())==""){
                  _this.addClass("placeholder");
                  _this.val(placeholder);
              }
           });
    },
        
    setHomePage: function(obj, vrl) //首页
    {
        if (confirm('确认要将' + vrl + '设置为首页?')) {
            try {
                obj.style.behavior = 'url(#default#homepage)';
                obj.setHomePage(vrl);
            }
            catch (e) {
                if (window.netscape) {
                    try {
                        netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                    }
                    catch (e) {
                        alert("此操作被浏览器拒绝！\n请在浏览器地址栏填写“about:config”并回车\n然后将[signed.applets.codebase_principal_support]设置为'true'");
                    }
                    var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
                    prefs.setCharPref('browser.startup.homepage', vrl);
                }
            }
        }
    },
    undefined: function(variable) {
        return typeof variable == 'undefined' ? true : false;
    },
    form: function(formid) {
        if (formid == null || formid == undefined)
        {
            return document.forms[0];
        }
        else {
            return document.forms[formid];
        }
    },
    openWindow : function(url){
        var a = document.createElement("a");
            a.setAttribute("href", url);
            a.setAttribute("target", "_blank");
            a.setAttribute("id", "openwin");
            document.body.appendChild(a);
            a.click();
    },
    getElementsByName: function(name) {
        var ret = [];
        var elements = document.getElementsByTagName("input");

        for (var i = 0; i < elements.length; i++) {
            if (elements[i].name == name) {
                ret.push(elements[i]);
            }
        }
        return ret;
    },
    checkAll: function(ischecked, formid) {

        if (formid) {
            $("#" + formid + " input[type='checkbox']").attr("checked", ischecked);
        } else {
            $("input[type='checkbox']").attr("checked", ischecked);
        }

    },
    selectedCount: function(formid) {
        var chks = null;
        var n = 0;
        if (formid) {
            chks = $("#" + formid + " input[type='checkbox']");
        } else {
            chks = $("input[type='checkbox']");
        }
        chks.each(function() {
            if ($(this).get(0).checked) {
                n++;
            }
        });
        return n;
    },
    selecteds: function(formid) {
        var ids = "";
        var chks = null;

        if (formid) {
            chks = $("#" + formid + " input[type='checkbox']");
        } else {
            chks = $("input[type='checkbox']");
        }
        chks.each(function() {
            if ($(this).get(0).checked) {
                if (ids != "") {
                    ids += ",";
                }
                ids += $(this).val();
            }
        });
        return ids;

    },
    allids: function(formid) {
        var ids = "";
        var chks = null;

        if (formid) {
            chks = $("#" + formid + " input[type='checkbox']");
        } else {
            chks = $("input[type='checkbox']");
        }
        chks.each(function() {

            if (ids != "") {
                ids += ",";
            }
            ids += $(this).val();

        });
        return ids;

    },
    uncheckAll: function(formid) {
        if (formid) {
            $("#" + formid + " input[type='checkbox']").attr("checked", false);
        } else {
            $("input[type='checkbox']").attr("checked", false);
        }


    }
}




;
(function($) {
    $.fn.extend({
        "placeholder": function(options) {
            options = $.extend({
                placeholderColor: '#aaa',
                isUseSpan: true, //是否使用插入span标签模拟placeholder的方式,默认false,默认使用value模拟
                onInput: true  //使用标签模拟(isUseSpan为true)时，是否绑定onInput事件取代focus/blur事件
            }, options);

            $(this).each(function() {
                var _this = this;
                var supportPlaceholder = 'placeholder' in document.createElement('input');
                if (!supportPlaceholder) {
                    var defaultValue = $(_this).attr('placeholder');
                    if (!defaultValue) {
                        return;
                    }

                    var defaultColor = $(_this).css('color');
                    if (!options.isUseSpan) {
                
                        $(_this).focus(function() {
                            var pattern = new RegExp("^" + defaultValue + "$|^$");
                            pattern.test($(_this).val()) && $(_this).val('').css('color', defaultColor);
                        }).blur(function() {
                            if ($(_this).val() == defaultValue) {
                                $(_this).css('color', defaultColor);
                            } else if ($(_this).val().length == 0) {
                                $(_this).val(defaultValue).css('color', options.placeholderColor)
                            }
                        }).trigger('blur');
                    } else {
                        var $imitate = $('<span class="wrap-placeholder" style="position:absolute; display:inline-block; overflow:hidden; color:' + options.placeholderColor + '; width:' + $(_this).outerWidth() + 'px; height:' + $(_this).outerHeight() + 'px;">' + defaultValue + '</span>');
                        $imitate.css({
                            'margin-left': $(_this).css('margin-left'),
                            'margin-top': $(_this).css('margin-top'),
                            'font-size': $(_this).css('font-size'),
                            'font-family': $(_this).css('font-family'),
                            'font-weight': $(_this).css('font-weight'),
                            'padding-left': parseInt($(_this).css('padding-left')) + 2 + 'px',
                            'line-height': _this.nodeName.toLowerCase() == 'textarea' ? $(_this).css('line-weight') : $(_this).outerHeight() + 'px',
                            'padding-top': _this.nodeName.toLowerCase() == 'textarea' ? parseInt($(_this).css('padding-top')) + 2 : 0,
                            "left":$(_this).position().left + "px",
                            "top":$(_this).position().top + "px" 
                    
                        });
                        $(_this).before($imitate.click(function() {
                            $(_this).trigger('focus');
                        }));

                        $(_this).val().length != 0 && $imitate.hide();

                        if (!options.onInput) {
                            //绑定oninput/onpropertychange事件
                            var inputChangeEvent = typeof(_this.oninput) == 'object' ? 'input' : 'propertychange';
                            $(_this).bind(inputChangeEvent, function() {
                                $imitate[0].style.display = $(_this).val().length != 0 ? 'none' : 'inline-block';
                            });
                        } else {
                            $(_this).focus(function() {
                                $imitate.hide();
                            }).blur(function() {
                                /^$/.test($(_this).val()) && $imitate.show();
                            });
                        }
                    }
                }
            });
            return this;
        }
    });
})(jQuery);




(function($) {
    $.fn.extend({
        "initSelect": function(options) {
            options = $.extend({
                debug: false,
                maxlength:10
            }, options);
           
            $(this).each(function() {
                 var _input =$(this);
                 var _div = $("#" + $(this).attr("id") + "_div");
                 var _ul =  $("#" + $(this).attr("id") + "_ul");
                 _div.mouseenter(function(){
                     _ul.show();
                 }).mouseleave(function(){ 
                     _ul.hide();
                 });
                 var txt = "";
                 var val = _input.val();
                 
                 $("li",_ul).each(function(){
                    
                    var item = $(this);
                    if(item.attr("val")==val){
                       $("span",$(".selected",_div)).html( item.attr("text").substr(0,options.maxlength));
                     }
                     
                     if( item.attr("haschild")=="true"){
               
                         var _cul =   $("#" + _input.attr("id") + "_" + item.attr("val")+"_ul");
                            item.mouseenter(function(){
                            _cul.show();
                        }).mouseleave(function(){ 
                            _cul.hide();
                        });
                     }
                 }).click(function(ev){
                         var txt1 = $(this).attr("text");
                         var val1 = $(this).attr("val");
                        
                        $("ul",_div).hide();
                        _input.val( val1 );
                        $("span",$(".selected",_div)).html(txt1.substr(0,options.maxlength));
                        ev.stopPropagation();
                     });;
                 
                 
                
            });
            return this;
        }
    });
})(jQuery);

