
var Arr = []; //存储option
var array = []; //存储id
$(function() {
    /*先将数据存入数组*/
    $("#hh option").each(function(index, el) {
        var zindex = $(this).val();
        Arr[index] = $(this).text();
        array[index] =  $(this).val();
    });
    $(document).bind('click', function(e) {
        var e = e || window.event; //浏览器兼容性
        var elem = e.target || e.srcElement;
        while (elem) { //循环判断至跟节点，防止点击的是div子元素
            if (elem.id && (elem.id == 'hh' || elem.id == "makeInput")) {
                return;
            }
            elem = elem.parentNode;
        }
        $('#hh').css('display', 'none');
    });
});

function choose(a) {
    $(a).prev("input").val($(a).find("option:selected").text());
    $("#hh").css({
        "display": "none"
    });
}

function setDemo(a, event) {
    $("#makeInput").val("");
    $("#hh").css({
        "display": ""
    });
    var select = $("#hh");
    select.html("");
    setContent(a, event);
}

function setContent(a, event) {
    var select = $("#hh");
    select.html("");

    for (i = 0; i < Arr.length; i++) {
        //若找到包含txt的内容的，则添option
        if (Arr[i].indexOf(a.value) >= 0) {
            var option = $("<option value='" + array[i] + "'></option>").text(Arr[i]);
            select.append(option);
        }
    }
    if (event.keyCode == 40) {
        //按向下的键之后跳转到列表中
        //焦点转移并且选中第一个值
        $("#hh").focus();
        $("#hh").find("option:first").attr("selected", "true");
        return false;
    }
}

function getfocus(this_, event) {
    if (event.keyCode == 13) { //回车键获取内容
        $(this_).prev("input").val($(this_).find("option:selected").text());
        $("#hh").css({
            "display": "none"
        });
    } else if (event.keyCode == 38) { //向上键回到输入框
        var option = $(this_).find("option:selected");
        if (option.prev().val() == undefined) {
            $('#makeInput').focus();
        }
    }
    return false;
}