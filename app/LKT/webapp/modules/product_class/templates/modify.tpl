
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />

<script language="javascript"  src="modpub/js/check.js"> </script>
<script type="text/javascript" src="style/js/jquery.js"></script>
{literal}
<script type="text/javascript">
function check(f){
    if(Trim(f.pname.value)==""){
        alert("分类名称不能为空！");
        f.pname.value = '';
        return false;
    }
    if(Trim(f.sort.value)==""){
        alert("分类排序号不能为空！");
        f.sort.value = '';
        return false;
    }
    f.sort.value = Trim(f.sort.value);
    if(!/^(([1-9][0-9]*)|0)(\.[0-9]{1,2})?$/.test(f.sort.value)){
        alert("排序号必须为数字，且格式为 ####.## ！");
        f.sort.value = '';
        return false;
    }
    return true;
}

$(function(){  
    $(".pimg").click(function(){  
        var _this = $(this);//将当前的pimg元素作为_this传入函数  
        imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);  
    });  
});
function imgShow(outerdiv, innerdiv, bigimg, _this){  
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性  
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性  
        /*获取当前点击图片的真实大小，并显示弹出层及大图*/  
    $("<img/>").attr("src", src).load(function(){  
        var windowW = $(window).width();//获取当前窗口宽度  
        var windowH = $(window).height();//获取当前窗口高度  
        var realWidth = this.width;//获取图片真实宽度  
        var realHeight = this.height;//获取图片真实高度  
        var imgWidth, imgHeight;  
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放        
        if(realHeight>windowH*scale) {//判断图片高度  
            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放  
            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度  
            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度  
                imgWidth = windowW*scale;//再对宽度进行缩放  
            }  
        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度  
            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放  
                        imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度  
        } else {//如果图片真实高度和宽度都符合要求，高宽不变  
            imgWidth = realWidth;  
            imgHeight = realHeight;  
        }  
                $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放  
          
        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距  
        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距  
        $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性  
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg  
    });  
      
    $(outerdiv).click(function(){//再次点击淡出消失弹出层  
        $(this).fadeOut("fast");  
    });  
}
</script>
{/literal}
<title>修改分类</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 产品分类管理 <span class="c-gray en">&gt;</span> 修改产品分类 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=product_class';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <form name="form1" action="index.php?module=product_class&action=modify" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <input type="hidden" name="cid" value="{$cid_r}" />
        <input type="hidden" name="uploadImg" value="{$uploadImg}" />
        <input type="hidden" name="val" class="val" value="{$cid}" >
        <input type="hidden" name="level" class="level" value="{$level}" >

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>分类级别：</label>
            <div class="formControls col-1"> <span class="select-box">
                <select name="select_c" class="select" onchange="slevel()" id="select_c">
                    <option {if $level == 0}selected="true"{/if} value="0">顶级</option>
                    <option {if $level == 1}selected="true"{/if} value="1">一级</option>
                    <option {if $level == 2}selected="true"{/if} value="2">二级</option>
                    <option {if $level == 3}selected="true"{/if} value="3">三级</option>
                    <option {if $level == 4}selected="true"{/if} value="4">四级</option>
                    <option {if $level == 5}selected="true"{/if} value="5">五级</option>
                </select>
                </span>
            </div>
        </div>

        <div class="row cl slevel_box" style="display: none;">
            <label class="form-label col-4"><span class="c-red"></span>上级分类：</label>
            <div class="formControls col-1 slevel_1"> <span class="select-box">
                <select name="select_1" class="select" onchange="one()" id="select_1">
                    <!-- <option selected="true" value="0">请选择</option> -->
                    {$ctype}
                </select>
                </span>
            </div>
            <div class="formControls col-1 slevel_2"> <span class="select-box">
                <select name="select_2" class="select" onchange="two()" id="select_2">
                    <!-- <option selected="true" value="0">请选择</option> -->
                    {$ctype1}
                </select>
                </span>
            </div>
            <div class="formControls col-1 slevel_3"> <span class="select-box" >
                <select name="select_3" class="select" onchange="three()" id="select_3">
                    <!-- <option selected="true" value="0">请选择</option> -->
                    {$ctype2}
                </select>
                </span>
            </div>

            <div class="formControls col-1 slevel_4"> <span class="select-box" >
                <select name="select_4" class="select" onchange="four()" id="select_4">
                    <!-- <option selected="true" value="0">请选择</option> -->
                    {$ctype2}
                </select>
                </span>
            </div>

            <div class="formControls col-1 slevel_5"> <span class="select-box" >
                <select name="select_5" class="select" onchange="five()" id="select_5">
                    <!-- <option selected="true" value="0">请选择</option> -->
                    {$ctype2}
                </select>
                </span>
            </div>

        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>分类名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="pname" value="{$pname}" datatype="*6-18" style="width: 260px;">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>图片：</label>
            <div class="formControls col-xs-8 col-sm-6"> 
                {if $img != ''}
                    <img id="thumb_url" class="cimage" src='{$uploadImg}{$img}' style="height:100px;width:150px;">
                {else}
                    <img id="thumb_url" class="cimage" src='{$uploadImg}noimg.jpg' style="height:100px;width:150px;">
                {/if}
                <input type="hidden"  id="picurl" name="image" datatype="*" nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpic" value="{$img}" >
                <div>图片尺寸:60*60</div>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>分类展示图片：</label>
            <div class="formControls col-xs-8 col-sm-6"> 
                <img id="thumb_urlbg" class="cbg" src="{$uploadImg}{if $bg != ''}{$bg}{else}noimg.jpg{/if}" style="height:100px;width:150px">
                <input type="hidden"  id="picurlbg" name="bg" datatype="*" nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpicbg" value="{$bg}" >
                <div>展示图尺寸:220*145</div>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl" style="display: none;">
            <label class="form-label col-4"><span class="c-red"></span>排序号：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="sort" value="{$sort}" datatype="*6-18" style="width: 260px;">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <div class="col-8 col-offset-4">
                <input type="submit" name="Submit" value="提 交" class="btn btn-primary radius">
                <input type="reset" name="reset" value="重 写"  class="btn btn-primary radius">
            </div>
        </div>
    </form>
</div>

<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:999;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>

<!-- 新增编辑器引入文件 -->
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css" />
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>

<script>

var str_option = {$str_option};
var level = {$level};
{literal}

$(function(){  
        slevel();
        onlaod();
});

function onlaod() {
    console.log(str_option);
    var arr = [];
    var keds = [];
    for (var i in str_option) {
        arr.push(str_option[i]); //属性
        keds.push(i);
    }
    console.log(keds);
    console.log(arr);
    for (var i = 0; i < arr.length; i++) {
        // console.log('str_option',str_option);
        var tid = i+1;
        var obj = $("#select_"+tid);
        // console.log("#select_"+tid);
        var str = '';
        for (var j = 0; j < arr[i].length; j++) {
            if(keds[i]==arr[i][j].cid){
                str += '<option selected="true" value="'+arr[i][j].cid+'">'+arr[i][j].pname+'</option>';
            }else{
                str += '<option  value="'+arr[i][j].cid+'">'+arr[i][j].pname+'</option>';
            }
            
        }
        obj.append(str);
    }

}

function slevel() {
    var select_c = $("#select_c").val();
    
    console.log(select_c);
    if(select_c < 1){
       $(".slevel_box").hide(); 
    }else{
        $(".slevel_box").show();
        for (var i = 1; i <= 5; i++) {
           obj = $(".slevel_"+i);
           if(i <= select_c){
             obj.show();
           }else{
             obj.hide();
           }
        }
    }

}

KindEditor.ready(function(K) {
  var editor = K.editor({
      allowFileManager : true,       
      uploadJson : "index.php?module=system&action=uploadImg", //上传功能
      fileManagerJson : 'kindeditor/php/file_manager_json.php', //网络空间
    });
  //上传背景图片
  K('.cimage').click(function() {
    editor.loadPlugin('image', function() {
      editor.plugin.imageDialog({
        //showRemote : false, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K('#picurl').val(),
          clickFn : function(url, title, width, height, border, align) {
          K('#picurl').val(url);
          $('#thumb_url').attr("src",url);
          editor.hideDialog();
        }
      });
    });
  });
  K('.cbg').click(function() {
    editor.loadPlugin('image', function() {
      editor.plugin.imageDialog({
        //showRemote : false, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K('#picurlbg').val(),
          clickFn : function(url, title, width, height, border, align) {
          K('#picurlbg').val(url);
          $('#thumb_urlbg').attr("src",url);
          editor.hideDialog();
        }
      });
    });
  });
});
function one() {
    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var v = dropElement1.value;
    $("#select_2").empty();

    RemoveDropDownList(dropElement2);
    RemoveDropDownList(dropElement3);

    if(v != 0){
        $('.val').val(v);
        $('.level').val(1);
        $.ajax({
            type: "GET",
            url: location.href+'&action=ajax&v='+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_2").append(obj);
            }
        });
    }else{
        $('.val').val('');
        $('.level').val('');
    }
}
function two() {
    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var v = dropElement2.value;

    $("#select_3").empty();

    if(v != 0){
        $('.val').val(v);
        $('.level').val(2);
        $.ajax({
            type: "GET",
            url: location.href+'&action=ajax&v='+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_3").append(obj);
            }
        });
    }else{
        var dropElement1 = document.getElementById("select_1");
        var v1 = dropElement1.value;
        $('.val').val(v1);
        $('.level').val(1);
    }
}
function three() {

    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var dropElement4 = document.getElementById("select_4");
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement3.value;


    RemoveDropDownList(dropElement4);
    $("#select_4").empty();
    if(v != 0){
        $('.val').val(v);
        $('.level').val(2);
        $.ajax({
            type: "GET",
            url: location.href+'&action=ajax&v='+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_4").append(obj);
            }
        });
    }else{
        var dropElement2 = document.getElementById("select_2");
        var v1 = dropElement2.value;
        $('.val').val(v1);
        $('.level').val(2);
    }
}

function four() {
    var dropElement1 = document.getElementById("select_1");
    var dropElement2 = document.getElementById("select_2");
    var dropElement3 = document.getElementById("select_3");
    var dropElement4 = document.getElementById("select_4");
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement4.value;


    RemoveDropDownList(dropElement5);
    $("#select_5").empty();
    if(v != 0){
        $('.val').val(v);
        $('.level').val(4);
        $.ajax({
            type: "GET",
            url: location.href+'&action=ajax&v='+v,
            data: "",
            success: function(msg){
                obj = JSON.parse(msg);
                $("#select_5").append(obj);
            }
        });
    }else{
        var dropElement3 = document.getElementById("select_3");
        var v1 = dropElement3.value;
        $('.val').val(v1);
        $('.level').val(3);
    }
}

function five() {
    var dropElement5 = document.getElementById("select_5");
    var v = dropElement5.value;
    if(v != 0){
        $('.val').val(v);
        $('.level').val(5);
    }else{
        var dropElement4 = document.getElementById("select_4");
        var v1 = dropElement4.value;
        $('.val').val(v1);
        $('.level').val(4);
    }
}
function RemoveDropDownList(obj){
    if(obj){
        var len=obj.options.length;
        if(len>0){
            //alert(len);
            for(var i=len;i>=1;i--){
                obj.remove(i);
            }
        }
    }
}
</script>
{/literal}
</body>
</html>