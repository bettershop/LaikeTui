
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
<link href="style/lib/icheck/icheck.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<link href="style/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css" />
{literal}
<style>
.keep{
    position: fixed;
    top:0;
    left: 0;
    right: 0;
    bottom: 0;
    width: 100%;
    height: 100%;
    filter:alpha(opacity=70);
    -moz-opacity:0.7;
    opacity: 0.7;
    background-color: #000;
    z-index: 3000;
}
.pop_model{
    position: fixed;
    left:20%;
    top: 10%;
    background-color: #fff;
    width: 65%;
    box-shadow: 2px 2px 2px #000;
    z-index: 3001;
}
.php_title{
    height: 2rem;
    background-color: #eee;
    color: #333333;
    line-height: 2rem;
    padding-left: 1rem;
    border-bottom: 1px solid #e1e1e1;
}
.content {
    margin:  0 auto;
    padding-top: 10px;
}
</style>
{/literal}
<title>添加活动</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe6ca;</i> 插件管理 <span class="c-gray en">&gt;</span> 满减设置 </nav>
<div class="pd-20">
    <form name="form1" action="index.php?module=subtraction" class="form form-horizontal" method="post" enctype="multipart/form-data">
        {*<div class="row cl">*}
            {*<label class="form-label col-4"><span class="c-red">*</span>满额包邮：</label>*}
            {*<div class="formControls col-4 skin-minimal" >*}
                {*<div class="radio-box">*}
                    {*<input name="status" type="radio" value="1" {if $status == 1}checked="checked{/if} />*}
                    {*<label for="sex-1">开启</label>*}
                {*</div>*}
                {*<div class="radio-box">*}
                    {*<input name="status" type="radio" value="2" {if $status != 1}checked="checked{/if}/>*}
                    {*<label for="sex-2">关闭</label>*}
                {*</div>*}
                {*<div>开启满包邮, 订单总金额超过多少可以包邮</div>*}

            {*</div>*}
            {*<div class="col-4"> </div>*}
        {*</div>*}
        {*<div class="row cl">*}
            {*<label class="form-label col-4">单笔订单满：</label>*}
            {*<div class="formControls col-4">*}
                {*<input type="number" class="input-text" placeholder="" id="" value="{$man_money}" name="man_money">*}
                {*<div>如果开启满额包邮，设置0为全场包邮</div>*}
            {*</div>*}
        {*</div>*}
        {*<div class='row cl' id='information' style='width: 80%;text-align: center;margin-top: 30px;display: none;'>*}
            {*<input type="hidden" class="input-text" value="{$region}" name="region" id="region">*}
            {*<text id="text"> {$region} </text>*}
        {*</div>*}
        {*<div class="row cl">*}
            {*<label class="form-label col-4"></label>*}
            {*<div class="formControls col-4">*}
                {*<button class = "btn radius" type = "button" onclick="choice()" > 添加不参与包邮的地区</button >*}
            {*</div>*}
        {*</div>*}
        <div class="row cl"  >
            <label class="form-label col-4">满额减：</label>
            <div class="formControls col-4" id="num">
                <input type="hidden" class="input-text" value="{$num}" name="num" id="num1">
                {if $subtraction == array()}
                    <div id="num_1">
                        <text>单笔订单满</text>
                        <input type="number" class="input-text" value="" name="man[]">
                        <text>元 立减</text>
                        <input type="number" class="input-text" value="" name="jian[]">
                        <text>元</text>
                    </div>
                {else}
                    {foreach from=$subtraction item=item name=f1 key=k}
                        {if $smarty.foreach.f1.first}
                            <div id="num_{$k+1}">
                                {foreach from=$item item=item1 name=f2 key=k1}
                                    <text>单笔订单满</text>
                                    <input type="number" class="input-text" value="{$k1}" name="man[]">
                                    <text>元 立减</text>
                                    <input type="number" class="input-text" value="{$item1}" name="jian[]">
                                    <text>元</text>
                                {/foreach}
                            </div>
                        {else}
                            <div id="num_{$k+1}" style='margin-top: 15px;'>
                                {foreach from=$item item=item1 name=f2 key=k1}
                                    <text>单笔订单满</text>
                                    <input type="number" class="input-text" value="{$k1}" name="man[]">
                                    <text>元 立减</text>
                                    <input type="number" class="input-text" value="{$item1}" name="jian[]">
                                    <text>元</text>
                                    <span class="btn radius" onclick="del('{$k+1}')">删除</span>
                                {/foreach}
                            </div>
                        {/if}
                    {/foreach}
                {/if}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"></label>
            <div class="formControls col-4">
                <button class = "btn radius" type = "button" onclick="add()" > 添加优惠项</button >
                <div>两项都填写才能生效</div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-10 col-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保 存 </button>
            </div>
        </div>
    </form>
    <div class="row keep" style="display: none"></div>
    <div class="pop_model" style="display: none">
        <div class="row php_title">
            <span>选择区域</span>
            <span style="float: right;margin-right: 1rem;cursor: pointer" title="关闭" onclick="select_hid()">X</span>
        </div>
        <div class="row cl content">
            <div class="formControls col-xs-8 skin-minimal" id="province">

            </div>
        </div>
        <div class="row cl" style="margin: 20px auto;">
            <div class="col-8 col-offset-4">
                <button class = "btn btn-primary radius" type = "button" onclick="save_address()" ><i class = "Hui-iconfont" > &#xe632;</i> 提 交</button >
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="modpub/js/check.js" > </script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type='text/javascript' src='modpub/js/calendar.js'> </script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/icheck/jquery.icheck.min.js"></script>
<script type="text/javascript" src="style/lib/Validform/5.3.2/Validform.min.js"></script>
<script type="text/javascript" src="style/lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.all.min.js"> </script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="style/js/H-ui.js"></script>
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

<!-- 新增编辑器引入文件 -->
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css" />
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>
{literal}
<script>
var num = $("#num1").val();
console.log(num)
// var num = 1;
var region = $("#region").val();
if(region){
    document.getElementById("information").style.display = ''; // 显示
}
function choice(){
    var region = document.getElementById("region").value;
    var region_arr = region.split(',');

    var rew = '';
    $.get("index.php?module=subtraction&action=province",function(res){
        var res = JSON.parse( res );
        if(res.status == 1){
            var list = res.list;

            for (var k in list) {
                var g = true;
                for (var j in region_arr) {
                    if(region_arr[j] == list[k]['G_CName']) {
                        rew += "<div class='radio-box' style='width: 32%;'>" +
                            "<input name='list' type='checkbox'  id='sex-" + list[k]['GroupID'] + "' value='" + list[k]['GroupID'] + "' checked >" +
                            "<label for='sex-" + list[k]['GroupID'] + "'>" + list[k]['G_CName'] + "</label>" +
                            "</div>";
                        g = false;
                    }
                }
                if(g){
                    rew += "<div class='radio-box' style='width: 32%;'>" +
                        "<input name='list' type='checkbox'  id='sex-" + list[k]['GroupID'] + "' value='" + list[k]['GroupID'] + "' >" +
                        "<label for='sex-" + list[k]['GroupID'] + "'>" + list[k]['G_CName'] + "</label>" +
                        "</div>";
                }
            }

            document.getElementById("province").innerHTML=rew;
            $('.keep').show();
            $('.pop_model').show();
        }
    });
}
// 关闭
function select_hid(){
    $('.keep').hide();
    $('.pop_model').hide();
}
// 保存
function save_address(){
    var region = document.getElementById("region").value;

    var obj = document.getElementsByName("list");
    var check_val = [];
    for(k in obj){
        if(obj[k].checked)
            check_val.push(obj[k].value);
    }
    $.post("index.php?module=subtraction&action=province",{check_val:check_val},function(result){
        var result = JSON.parse( result );
        if(result.status == 1){
            var name = result.name;

            // 给隐藏域赋值
            $("#region").val(name);
            $("#text").html(name,region);

            document.getElementById("information").style.display = ''; // 显示
        }
    });
    select_hid();
}
// 添加优惠项
function add() {
    num++;
    var rew = '';
    rew = "<div style='margin-top: 15px;' id='num_"+num+"'>" +
        "<text style='margin-right: 4px;'>单笔订单满</text>" +
        "<input type='number' class='input-text' value='' name='man[]'>" +
        "<text style='margin:0px 4px;'>元 立减</text>" +
        "<input type='number' class='input-text' value='' name='jian[]'>" +
        "<text style='margin:0px 4px;'>元</text>" +
        "<span class='btn radius' onclick='del("+num+")'>删除</span>";
        "</div>"
    $("#num").append(rew);
}
// 删除
function del(obj) {
    $("#num_" + obj).remove();

    // window.location.reload();
}
</script>
{/literal}
</body>
</html>