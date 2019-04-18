<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
<meta http-equiv="Cache-Control" content="no-siteapp"/>

<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css"/>
<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css"/>
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css"/>

<script language="javascript" src="modpub/js/check.js"></script>
{literal}
<style type="text/css">
	form[name=form1] input{
		margin: 0px;
	}
	.inputC+label{
		width: 50px;
		height: 20px;
		line-height: 20px;
		border: none;
	}
	.inputC:checked +label::before{
		display: inline-block;
	}
	   
</style>
<script type="text/javascript">
function check(f) {
    if (Trim(f.product_title.value) == "") {
        alert("产品名称不能为空！");
        f.product_title.value = '';
        return false;
    }
    if (Trim(f.keyword.value) == "") {
        alert("关键词不能为空！");
        f.keyword.value = '';
        return false;
    }
    if (Trim(f.sort.value) == "") {
        alert("排序不能为空！");
        f.sort.value = '';
        return false;
    }
    f.sort.value = Trim(f.sort.value);
    if (!/^(([1-9][0-9]*)|0)(\.[0-9]{1,2})?$/.test(f.sort.value)) {
        alert("排序号必须为数字，且格式为 ####.## ！");
        f.sort.value = '';
        return false;
    }
    return true;
}
</script>
{/literal}

{literal}
<style type="text/css">
.input-text, .scinput_s{
    width: 300px;
}
.wrap {
    width:60px;
    height:30px;
    background-color:#ccc;
    border-radius:16px;
    position:relative;
    transition:0.3s;
    margin-left: 10px;
}
.circle {
    width:29px;
    height:29px;
    background-color:#fff;
    border-radius:50%;
    position:absolute;
    left:0px;
    transition:0.3s;
    box-shadow:0px 1px 5px rgba(0,0,0,.5);
}
.circle:hover {
    transform:scale(1.2);
    box-shadow:0px 1px 8px rgba(0,0,0,.5);
}
.circle1 {
    width:29px;
    height:29px;
    background-color:#fff;
    border-radius:50%;
    position:absolute;
    left:0px;
    transition:0.3s;
    box-shadow:0px 1px 5px rgba(0,0,0,.5);
}
.circle1:hover {
    transform:scale(1.2);
    box-shadow:0px 1px 8px rgba(0,0,0,.5);
}
.wrap_box{
    display: none;
}
.ra1{
	position: relative;
	width: 70px;
	margin-right: 20px;
	border: 1px solid #eee;
	border-radius: 5px;
	height: 30px;
	line-height: 30px;
	float: left;
}
.ra1 label{
	height: 30px;
	text-align: center;
	line-height: 30px;
	margin: 0 auto;
	width: 90%;
}
.ra1 input{
	float: left;
	position: absolute;
	height: 30px;
	line-height: 30px;
}
.inputC:checked +label::before{
	top: 8px;
}
</style>
{/literal}

<title>添加产品</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 产品列表管理 <span class="c-gray en">&gt;</span> 发布产品 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=product';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>

<div class="pd-20">
    <form name="form1" action="index.php?module=product&action=add" class="form form-horizontal" method="post" enctype="multipart/form-data" onsubmit="return check(this);">
        <input type="hidden" name="attribute" class="attribute" id="attribute" value='{$attribute}'/>
        <input type="hidden" name="uploadImg" value="{$uploadImg}"/>
        <input type="hidden" name="attribute_num" class="attribute_num" id="attribute_num" value='{$attribute_num}'/>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>产品编号：</label>
            <div class="formControls col-4" style="width: 16.8%;">
                <input type="text" class="input-text" value="{$product_number}" placeholder="" id="" name="product_number">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>产品标题：</label>
            <div class="formControls col-4" style="width: 16.8%;">
                <input type="text" class="input-text" value="{$product_title}" placeholder="" id="" name="product_title">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>副标题：</label>
            <div class="formControls" style="display: inline-block;">
                <input type="text" class="input-text" value="{$subtitle}" placeholder="" id="" name="subtitle">
                
            </div>
            <text style="line-height:30px;position: relative;">*简洁表达产品，用来显示在首页产品，避免截取时不能表达是什么产品。</text>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>商品条形码：</label>
            <div class="formControls col-4" style="width: 16.8%;">
                <input type="text" class="input-text" value="{$scan}" placeholder="" id="" name="scan">
                
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>产品类别：</label>
            <div class="formControls col-2"> <span class="select-box">
                <select name="product_class" class="select">
                    <option selected="selected" value="0">请选择类别</option>
                    {$ctype}
                </select>
                </span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>产品品牌：</label>
            <div class="formControls col-2">
                <span class="select-box">
                    <select name="brand_class" class="select">
                        {*<option selected="selected" value="0">请选择品牌</option>*}
                        {foreach from=$brand item=item name=f1}
                            <option value="{$item->brand_id}">{$item->brand_name}</option>
                        {/foreach}
                    </select>
                </span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>关键词：</label>
            <div class="formControls col-4" style="width: 16.8%;">
                <input type="text" class="input-text" value="{$keyword}" placeholder="" id="" name="keyword">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>重量：</label>
            <div class="formControls col-4" style="width: 26.8%;">
                <input type="text" class="input-text" value="{$weight}" placeholder="" id="" name="weight">
            </div>
            <text style="line-height:30px;">克</text>
        </div>
        <div class="row cl" id="attribute_add">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>产品规格：</label>
            <div class="formControls col-xs-8 col-sm-10" style="border: solid 1px #eee;" id="add-tr">
                <div style="width:30%;float:left;padding-left: 5px;margin-right:10px;" id="attribute_attribute">
                    {if $rew != ''}
                        {$rew}
                    {else}
                        <div style="margin: 5px auto;" class="attribute_1 option" id="cattribute_1">
                            <input type="text" name="attribute_name" id="attribute_name_1" placeholder="属性名称" value=""
                                   class="input-text attribute_name" style=" width:50%" onblur="leave();"/>
                            -
                            <input type="text" name="attribute_value" id="attribute_value_1" placeholder="值" value=""
                                   class="input-text" style=" width:45%" onblur="leave();"/>
                        </div>
                    {/if}
                </div>
                <die style=" width:45%;float:left;" id="parameter">
                    <div style="margin: 5px auto;">
                        <input type="text" name="cost_price" id="" placeholder="成本价" value="" class="input-text"
                               style=" width:18%">
                        元
                        <input type="text" name="original_price" id="" placeholder="原价" value="" class="input-text"
                               style=" width:18%">
                        元
                        <input type="text" name="present_price" id="" placeholder="现价" value="" class="input-text"
                               style=" width:18%">
                        元
                        <input type="number" name="num" id="" placeholder="数量" value="" class="input-text"
                               style=" width:18%">
                        <select name="unit" id="unit" class="input-text" style="width:48px;vertical-align: middle;">
                            <option value="盒">
                                盒
                            </option>
                            <option value="篓">
                                篓
                            </option>
                            <option value="箱">
                                箱
                            </option>
                            <option value="盒">
                                盒
                            </option>
                            <option value="个">
                                个
                            </option>
                            <option value="套">
                                套
                            </option>
                            <option value="包">
                                包
                            </option>
                            <option value="支">
                                支
                            </option>
                            <option value="条">
                                条
                            </option>
                            <option value="根">
                                根
                            </option>
                            <option value="本">
                                本
                            </option>
                            <option value="瓶">
                                瓶
                            </option>
                            <option value="块">
                                块
                            </option>
                            <option value="片">
                                片
                            </option>
                            <option value="把">
                                把
                            </option>
                            <option value="组">
                                组
                            </option>
                            <option value="双">
                                双
                            </option>
                            <option value="台">
                                台
                            </option>
                            <option value="件">
                                件
                            </option>
                        </select>
                    </div>
                </die>
                <die style="float:left;margin-top: 5px;margin-left: -1px;">
                    <input type="hidden" id="picurl1" class="td-input" name="img[]" datatype="*" nullmsg="请选择图片"/>
                    <img id="thumb_url1" src=''>
                    <span class="btn btn-success" id="image1" type="button">选择图片</span>
                </die>
                <die style="float:right;margin-top: 5px;">
                    <span class="btn btn-primary radius" id="save" onclick="Preservation();">保存</span>
                    <span class="btn btn-secondary radius" id="empt" onclick="empty();">清空</span>
                </die>
            </div>
        </div>
        <div class='row cl' id='table' {if $attribute_key != '' && $attribute_val != ''}style='padding-left: 104px;'{else} style='padding-left: 104px;display:none;'{/if}>
            <table class='table table-border table-bordered table-bg'>
                <thead id="thead">
                {if $attribute_key != ''}
                    <tr id='attribute_1' >
                        {foreach from=$attribute_key item=item name=f1}
                            <th style='text-align: center;'>{$item}</th>
                        {/foreach}
                        <th style='text-align: center;'>操作</th>
                    </tr>
                {/if}
                </thead>
                <tbody id="tbody">
                {if $attribute_val != ''}
                    {foreach from=$attribute_val item=item1 key=k name=f2}
                        <tr class='num' id='tr_{$k+1}'>
                            {foreach from=$item1 item=item2 key=k1 name=f3}
                                {if $smarty.foreach.f3.last}
                                    <td style='width: 138px;'>
                                        <die style='margin-top: 5px;'>
                                            <input type='hidden' id='picurl_{$k+1}' class='td-input' name='img[]' datatype='*' nullmsg='请选择图片'/>
                                            <img id='thumb_url_{$k+1}' src='{$item2}' style='height:31px;width:50px;'>
                                            <span class='btn btn-success' id='image_{$k+1}' type='button' onclick='image_click({$k+1})'>选择图片</span>
                                        </die>
                                    </td>
                                {else}
                                    {if $smarty.foreach.f3.index == ($smarty.foreach.f3.total-2)}
                                        <td style='width: 50px;text-align: center;'>
                                            <input type='text' class='input-text' value='{$item2}' id='num_{$k+1}_{$k1+1}' readOnly='readOnly' style='background-color: #eeeeee;width: 50px;' ondblclick='double({$k+1},{$k1+1})' onblur="leave1({$k+1},{$k1+1},'{$attribute_key[$k1]}')">
                                        </td>
                                    {else}
                                        {if $attribute_key[$k1] == '成本价' || $attribute_key[$k1] == '原价' || $attribute_key[$k1] == '现价' || $attribute_key[$k1] == '数量'}
                                            <td style="text-align: center;width: 80px;">
                                                <input type='number' class='input-text' value='{$item2}' id='num_{$k+1}_{$k1+1}' readOnly='readOnly' style='background-color: #eeeeee;' ondblclick='double({$k+1},{$k1+1})' onblur="leave1({$k+1},{$k1+1},'{$attribute_key[$k1]}')">
                                            </td>
                                        {else}
                                            <td style="text-align: center;">
                                                <input type='text' class='input-text' value='{$item2}' id='num_{$k+1}_{$k1+1}' readOnly='readOnly' style='width: 100px;background-color: #eeeeee;' ondblclick='double({$k+1},{$k1+1})' onblur="leave1({$k+1},{$k1+1},'{$attribute_key[$k1]}')">
                                            </td>
                                        {/if}

                                    {/if}
                                {/if}
                            {/foreach}
                            <td style='width: 30px;'><span class='btn btn-secondary radius' id='empt' onclick='tr_del("{$k+1}")' >删除</span></td>
                        </tr>
                    {/foreach}
                {/if}
                </tbody>
            </table>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>显示类型：</label>
            <div class="formControls col-xs-8 col-sm-8 skin-minimal">
                <div class="ra1">
                    <input name="s_type[]" type="checkbox" id="sex-1" class="inputC" value="1" {if in_array(1,$s_type)}checked="checked"{/if}>
                    <label for="sex-1">新品</label>
                </div>
                <div class="ra1">
                    <input type="checkbox" id="sex-2" name="s_type[]" class="inputC" value="2" {if in_array(2,$s_type)}checked="checked"{/if}>
                    <label for="sex-2">热销</label>
                </div>
                <div class="ra1">
                    <input type="checkbox" id="sex-3" name="s_type[]" class="inputC" value="3" {if in_array(3,$s_type)}checked="checked"{/if}>
                    <label for="sex-3">推荐</label>
                </div>
            </div>
        </div>


        <div class="row cl">
            <label class="form-label col-2">拟定销量：</label>
            <div class="formControls col-2">
                <input type="number" class="input-text" value="{$volume}" name="volume">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">排序号：</label>
            <div class="formControls col-2">
                <input type="number" class="input-text" value="{$sort}" placeholder="" id="" name="sort">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>产品主图：</label>
            <div class="formControls col-xs-8 col-sm-10">
                {if $image}
                    <img id="thumb_url" src='{$image}' style="height:100px;width:150px">
                    <input type="hidden" name="oldpic" value="{$image}">
                {else}
                    <img id="thumb_url" src='../LKT/images/nopic.jpg' style="height:100px;width:150px">
                    <input type="hidden" name="oldpic" value="">
                {/if}
                <input type="hidden" id="picurl" name="image" datatype="*" nullmsg="请选择图片"/>
                <button class="btn btn-success" id="image" type="button">选择图片</button>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">产品展示图：</label>
            <div class="formControls col-10" style="width: 40%;">
                <div class="uploader-thum-container">
                    <input name="imgurls[]" id="imgurls" multiple='multiple' type="file" style="width:210px;" accept="upload_image/x-png,image/gif,image/jpeg"/>注:最多五张,一定为.jpg格式
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>运费设置：</label>
            <div class="formControls col-2"> <span class="select-box">
                <select name="freight" class="select">
                    <option selected="selected" value="0">默认模板</option>
                    {foreach from=$freight item=item1 name=f2}
                        <option value="{$item1->id}">{$item1->name}</option>
                    {/foreach}
                </select>
                </span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">详细内容：</label>
            <div class="formControls col-xs-8 col-sm-10">
                <script id="editor" type="text/plain" name="content" style="width:100%;height:400px;">{$content}</script>
            </div>
        </div>
        <div class="row cl">
            <div class="col-8 col-offset-4">
                <input type="submit" name="Submit" value="提 交" class="btn btn-primary radius">
                <input type="reset" name="reset" value="重 写"  class="btn btn-primary radius">
            </div>
        </div>
    </form>
</div>
<script type = "text/javascript" src = "style/js/jquery.js" ></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<!-- 新增编辑器引入文件 -->
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css"/>
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>
{literal}
<script>
$(function() {
    $("#imgurls").change(function() {
        var files = this.files;
        if (files && files.length > 5) {
            alert("超过5张");
            this.value = "" //删除选择
            // $(this).focus(); //打开选择窗口
        }
    })
})
$(function(){
    var ue = UE.getEditor('editor');
});
KindEditor.ready(function (K) {
    var editor = K.editor({
        allowFileManager: true,
        uploadJson: "index.php?module=system&action=uploadImg", //上传功能
        fileManagerJson: 'kindeditor/php/file_manager_json.php', //网络空间
    });
    //上传背景图片
    K('#image').click(function () {
        editor.loadPlugin('image', function () {
            editor.plugin.imageDialog({
                showRemote : false, //网络图片不开启
                //showLocal : false, //不开启本地图片上传
                imageUrl: K('#picurl').val(),
                clickFn: function (url, title, width, height, border, align) {
                    K('#picurl').val(url);
                    $('#thumb_url').attr("src", url);
                    editor.hideDialog();
                }
            });
        });
    });
    //上传背景图片
    K('#image1').click(function () {
        editor.loadPlugin('image', function () {
            editor.plugin.imageDialog({
                showRemote: false, //网络图片不开启
                //showLocal : false, //不开启本地图片上传
                imageUrl: K('#picurl1').val(),
                clickFn: function (url, title, width, height, border, align) {
                    K('#picurl1').val(url);
                    $('#thumb_url1').attr("src", url);
                    document.getElementById("thumb_url1").style.height='31px';

                    //                    document.getElementById("thumb_url1").style.display='block';
                    editor.hideDialog();
                }
            });
        });
    });
});
var num = 1;
var attribute = [];
var rnum = 0;
if(document.getElementById('attribute_num').value != ''){
    num_num = $("#add-tr #attribute_attribute").find(".input-text");
    num = num_num.length/2;
}

if(document.getElementById('attribute').value != ''){
    attribute1 = JSON.parse(document.getElementById('attribute').value);
    for (var h in attribute1) {
        attribute[h] = JSON.stringify(attribute1[h])
    }
    rnum=attribute.length;
}
// 离开事件
function leave() {
    var m = $("#add-tr #attribute_attribute").find(".input-text");
    var min = m.length - 2;
    var max = m.length - 1;
    var m_min = m.length - 4;
    var m_max = m.length - 3;

    if(m_min >= 0 && m[m_min].value == '' && m[m_max].value == ''){
        var parent=document.getElementById("attribute_attribute");
        var child=document.getElementById("cattribute_" + num);
        num--;
        parent.removeChild(child);
    }else{
        for (var i = 0; i < m.length; i++) {
            if (i % 2 == 0) {   //奇数
                if (m[i].value == m[min].value && i != min) {
                    $('#attribute_name_' + num).val("");
                    alert("属性名称重复");
                    break;
                }
            }
        }
        if (m[min].value != '' && m[max].value != '') {
            num++;
            var rew = "<div style='margin: 5px auto;' class='attribute_" + num + " option' id='cattribute_" + num + "'>" +
                "<input type='text' name='attribute_name' id='attribute_name_" + num + "' placeholder='属性名称' value='' class='input-text attribute_name' style=' width:50%' onblur='leave();' />" +
                " - " +
                "<input type='text' name='attribute_value' id='attribute_value_" + num + "' placeholder='值' value='' class='input-text' style=' width:45%' onblur='leave();' />" +
                "</div>";
            $("#add-tr #attribute_attribute").append(rew);
        }
    }
}

// 清除
function empty() {
    var num_1 = $('.num').length;

    var m = $("#add-tr #attribute_attribute").find(".input-text");
    var n = $("#add-tr #parameter").find(".input-text");

    $(".attribute_1:gt(0)").remove();

    if (num_1 == 0) {
        m.each(function () {
            $(this).val("");
        })
        n.each(function () {
            $(this).val("");
        })
    } else {
        for (var i = 0; i < num; i++) {
            m.each(function () {
                $("#attribute_value_" + i).val("");
            })
        }
        n.each(function () {
            $(this).val("");
        })
    }
}

// 保存
function Preservation() {
    var m = $("#add-tr #attribute_attribute").find(".input-text");
    var n = $("#add-tr #parameter").find(".input-text");
    var image = $("#add-tr #thumb_url1").attr("src");
    var min = m.length - 2;
    var max = m.length - 1;
    var res = [];
    var list = [];

    for (var h in attribute) { // 属性对象
        list1 = JSON.parse(attribute[h]); // 字符串转化为 对象
        var list2 = [];
        for (var h1 in list1) {
            var list3 = {};
            list3[h1] = list1[h1];
            list2.push(list3); //属性
        }
        for (var h_1 = 0;h_1 < 6;h_1++) { // 删除数组后6个元素
            list2.pop()
        }
        var srtj = '{';
        for (var i = 0; i < list2.length; i++) {
            for (var h2 in list2[i]) {
                srtj += '"' + h2 + '":"' + list2[i][h2] + '",';
            }
        }
        srtj = srtj.substring(0, srtj.length - 1);
        srtj += '}';

        list[h] = srtj;
    }

    // 最后2个input框，一个为空，一个不为空
    if (m[min].value == '' && m[max].value != '' || m[max].value == '' && m[min].value != '') {
        alert("您有未填属性");
        return false;
    }
    for (var i = 0; i < m.length; i++) { // 循环判断是否有未填写
        if (m[i].value == '' && i != min && i != max) {
            alert("您有未填属性");
            return false;
        } else {
            if (m[i].value != '') {
                res[i] = m[i].value;
            }
        }
    }
    var All = {};
    var All1 = {};
    var number = $('.option').length; // 获取属性个数

    for (var i = 0; i < number - 1; i++) {
        var key = $('.option').eq(i).find('input[name="attribute_name"]').val(); // 属性名称
        var value = $('.option').eq(i).find('input[name="attribute_value"]').val(); // 属性值
        All[key] = value;
        All1[key] = value;
    }
    for (var j = 0; j < n.length; j++) {
        if (n[j].value == '') {
            alert("您有未填参数");
            return false;
        } else {
            if (j == 0) {
                if(isNaN(n[j].value)){
                    alert("成本价请填写数字");
                    return false;
                }else{
                    if (Number(n[j].value) > 0) {
                        All['成本价'] = n[j].value;
                    } else {
                        alert("成本价不能低于0");
                        return false;
                    }
                }
            } else if (j == 1) {
                if(isNaN(n[j].value)){
                    alert("原价请填写数字");
                    return false;
                }else{
                    if (Number(n[j].value) >= Number(n[0].value)) {
                        All['原价'] = n[j].value;
                    } else {
                        alert("原价不能低于成本价");
                        return false;
                    }
                }
            } else if (j == 2) {
                if(isNaN(n[j].value)){
                    alert("原价请填写数字");
                    return false;
                }else{
                    if (Number(n[j].value) > 0) {
                        if (n[j].value < n[0].value) {
                            alert("现价低于成本价");
                        }
                        All['现价'] = n[j].value;
                    } else {
                        alert("现价不能低于0");
                        return false;
                    }
                }
            } else if (j == 3) {
                if (Number(n[j].value) > 0) {
                    All['数量'] = n[j].value;
                } else {
                    alert("数量不能低于0");
                    return false;
                }
            } else {
                All['单位'] = n[j].value;
            }
        }
    }
    if (image == '') {
        alert("请上传图片");
        return false;
    } else {
        All['图片'] = image;
    }
    var All2 = JSON.stringify(All1); // 从一个对象中解析出字符串
    if (in_array(All2, list)) {
        alert("属性重复，请核对属性");
        return false;
    }
    var attribute_1 = JSON.stringify(All); // 从一个对象中解析出字符串
    rnum++;
    var num_1 = $('.num').length + 1;

    attribute[attribute.length] = attribute_1; // 吧当前数组添加到全局数据里

    var attribute_value = '{';
    for (var i = 0; i < attribute.length; i++) {
        attribute_value += '"' + i + '":' + attribute[i] + ',';
    }

    attribute_value = attribute_value.substring(0, attribute_value.length - 1);
    attribute_value += '}';

    $(".attribute").val(attribute_value);
    var for_num = 0;
    if (num_1 == 1) {
        var rew = "<tr id='attribute_" + rnum + "'>";
        for (var k in All) {
            rew += "<th style='text-align: center;'>" + k + "</th>";
        }
        rew += "<th style='text-align: center;'>操作</th>" +
            "</tr>";
        rew1 = "<tr class='num' id='tr_" + rnum + "'>";
        for (var k in All) {
            for_num++;
            if (k == '图片') {
                rew1 += "<td style='width: 138px;'>" +
                    "<die style='margin-top: 5px;'>" +
                    "<input type='hidden' id='picurl_" + rnum + "' class='td-input' name='img[]' datatype='*' nullmsg='请选择图片'/>\n" +
                    "<img id='thumb_url_" + rnum + "' src='" + All[k] + "' style='height:31px;width:50px;'>" +
                    "<span class='btn btn-success' id='image_" + rnum + "' type='button' style='margin-left: 5px;' onclick='image_click(" + rnum + ")'>选择图片</span>" +
                    "</die>"+
                    "</td>";
            } else if(k == '单位'){
                rew1 += "<td style='width: 50px;text-align: center;'>" +
                    "<input type='text' class='input-text' value='"+All[k]+"' id='num_"+rnum+"_"+for_num+"' readOnly='readOnly' style='width: 50px;background-color: #eeeeee;' ondblclick='double("+rnum+","+for_num+")' onblur='leave1("+rnum+","+for_num+",\""+k+"\");'>" +
                    "</td>";
            }else if(k == '成本价' || k == '原价' || k == '现价' || k == '数量'){
                rew1 += "<td style='text-align: center;width: 80px;'>" +
                    "<input type='number' class='input-text' value='"+All[k]+"' id='num_"+rnum+"_"+for_num+"' readOnly='readOnly' style='background-color: #eeeeee;' ondblclick='double("+rnum+","+for_num+")' onblur='leave1("+rnum+","+for_num+",\""+k+"\");'>" +
                    "</td>";
            }else{
                rew1 += "<td style='text-align: center;'>" +
                    "<input type='text' class='input-text' value='"+All[k]+"' id='num_"+rnum+"_"+for_num+"' readOnly='readOnly' style='width: 100px;background-color: #eeeeee;' ondblclick='double("+rnum+","+for_num+")' onblur='leave1("+rnum+","+for_num+",\""+k+"\");'>" +
                    "</td>";
            }
        }
        rew1 += "<td style='width: 30px;'><span class='btn btn-secondary radius' id='empt' onclick='tr_del(" + rnum + ")' >删除</span></td>" +
            "</tr>";

        $("#thead").prepend(rew);
        $("#tbody").prepend(rew1);
        for (var num1 = 1; num1 <= num; num1++) {
            document.getElementById('attribute_name_' + num1).style.backgroundColor = "#eeeeee"; // 修改属性名称样式
        }
        document.getElementById('unit').style.backgroundColor = "#eeeeee"; // 修改下拉框样式
        $("#unit").attr("disabled", "disabled"); // 修改下拉框不能选择
        $('.option').find('input[name="attribute_name"]').attr('readOnly', true); // 修改下拉框属性名称不能选择
        document.getElementById("cattribute_" + num).style.display = 'none'; // 显示表格
        document.getElementById("table").style.display = ''; // 显示表格
    } else {
        rew2 = "<tr class='num' id='tr_" + rnum + "'>";
        for (var k in All) {
            for_num++;
            if (k == '图片') {
                rew2 += "<td style='width: 138px;'>" +
                    "<die style='margin-top: 5px;'>" +
                    "<input type='hidden' id='picurl_" + rnum + "' class='td-input' name='img[]' datatype='*' nullmsg='请选择图片'/>" +
                    "<img id='thumb_url_" + rnum + "' src='" + All[k] + "' style='height:31px;width:50px;'>" +
                    "<span class='btn btn-success' id='image_" + rnum + "' type='button' style='margin-left: 5px;' onclick='image_click(" + rnum + ")'>选择图片</span>" +
                    "</die>"+
                    "</td>";
            } else if(k == '单位'){
                rew2 += "<td style='width: 50px;'>" +
                    "<input type='text' class='input-text' value='"+All[k]+"' id='num_"+rnum+"_"+for_num+"' readOnly='readOnly' style='width: 50px;background-color: #eeeeee;' ondblclick='double("+rnum+","+for_num+")' onblur='leave1("+rnum+","+for_num+",\""+k+"\");'>" +
                    "</td>";
            }else if(k == '成本价' || k == '原价' || k == '现价' || k == '数量'){
                rew2 += "<td style='text-align: center;width: 80px;'>" +
                    "<input type='number' class='input-text' value='"+All[k]+"' id='num_"+rnum+"_"+for_num+"' readOnly='readOnly' style='background-color: #eeeeee;' ondblclick='double("+rnum+","+for_num+")' onblur='leave1("+rnum+","+for_num+",\""+k+"\");'>" +
                    "</td>";
            }else{
                rew2 += "<td style='text-align: center;'>" +
                    "<input type='text' class='input-text' value='"+All[k]+"' id='num_"+rnum+"_"+for_num+"' readOnly='readOnly' style='width: 100px;background-color: #eeeeee;' ondblclick='double("+rnum+","+for_num+")' onblur='leave1("+rnum+","+for_num+",\""+k+"\");'>" +
                    "</td>";
            }
        }
        rew2 += "<td style='width: 30px;text-align: center;'><span class='btn btn-secondary radius' id='empt' onclick='tr_del(" + rnum + ")'>删除</span></td>" +
            "</tr>";
        $("#tbody").append(rew2);
    }
}

// 删除
function tr_del(obj) {
    var obj1 = obj - 1;
    $("#tr_" + obj).remove();

    delete attribute[obj1];

    var attribute_value = '{';
    for (var i = 0; i < attribute.length; i++) {
        if(attribute[i] == 'undefined' || attribute[i] == '' || !attribute[i]){
            attribute_value += '';
        }else{
            attribute_value += '"' + i + '":' + attribute[i] + ',';
        }
    }

    attribute_value = attribute_value.substring(0, attribute_value.length - 1);
    attribute_value += '}';

    $(".attribute").val(attribute_value);

    var num_1 = $('.num').length;
    if (num_1 == 0) {
        rnum = 0;
        attribute = [];
        $(".attribute").val('');
        $('.option').find('input[name="attribute_name"]').attr('readOnly', false);
        for (var num1 = 1; num1 <= num; num1++) {
            document.getElementById('attribute_name_' + num1).style.backgroundColor = "#ffffff"; // 修改属性名称样式
        }
        document.getElementById('unit').style.backgroundColor = "#ffffff"; // 修改下拉框样式
        $("#unit").attr("disabled", ""); // 修改下拉框不能选择
        document.getElementById("cattribute_" + num).style.display = '';
        document.getElementById("table").style.display = 'none';
        var o = document.getElementById("thead");
        var a = document.getElementById("attribute_1");
        o.removeChild(a);
    }
}

function in_array(search, array) {
    for (var i in array) {
        if (array[i] == search) {
            return true;
        }
    }
    return false;
}

$(function () {
    var ue = UE.getEditor('editor');
});
if(document.getElementById('is_distribution').value == 0){
    $('.circle').css('left', '0px'),
        $('.circle').css('background-color', '#fff'),
        $('.circle').parent(".wrap").css('background-color', '#ccc');
    $(".wrap_box").hide();
}else{
    $('.circle').css('left', '30px'),
        $('.circle').css('background-color', '#fff'),
        $('.circle').parent(".wrap").css('background-color', '#5eb95e');
    $(".wrap_box").show();
}
if(document.getElementById('is_zhekou').value == 0){
    $('.circle1').css('left', '0px'),
        $('.circle1').css('background-color', '#fff'),
        $('.circle1').parent(".wrap").css('background-color', '#ccc');
}else{
    $('.circle1').css('left', '30px'),
        $('.circle1').css('background-color', '#fff'),
        $('.circle1').parent(".wrap").css('background-color', '#5eb95e');
}
$('.circle').click(function() {
    var left = $(this).css('left');
    // var status = $(".status");
    left = parseInt(left);
    var status = $(this).parents(".status_box").children(".status");
    if (left == 0) {
        $(this).css('left', '30px'),
            $(this).css('background-color', '#fff'),
            $(this).parent(".wrap").css('background-color', '#5eb95e');
        $(".wrap_box").show();
        status.val(1);
    } else {
        $(this).css('left', '0px'),
            $(this).css('background-color', '#fff'),
            $(this).parent(".wrap").css('background-color', '#ccc');
        $(".wrap_box").hide();
        status.val(0);
    }
})

$('.circle1').click(function() {
    var left = $(this).css('left');
    // var status = $(".status");
    left = parseInt(left);

    var status = $(this).parents(".status_box").children(".status");
    if (left == 0) {
        $(this).css('left', '30px'),
            $(this).css('background-color', '#fff'),
            $(this).parent(".wrap").css('background-color', '#5eb95e');
        // $(".wrap_box").show();
        status.val(1);
    } else {
        $(this).css('left', '0px'),
            $(this).css('background-color', '#fff'),
            $(this).parent(".wrap").css('background-color', '#ccc');
        // $(".wrap_box").hide();
        status.val(0);
    }
})

var ynum = '';
// 双击修改
function double(rr_num,for_num) {
    ynum = $('#num_'+rr_num+'_'+for_num).val();
    $('#num_'+rr_num+'_'+for_num).attr('readOnly',false);
    document.getElementById('num_'+rr_num+'_'+for_num).style.backgroundColor="#ffffff";
}
// 修改离开事件
function leave1(rr_num,for_num,name) {
    var r_num1 = rr_num - 1;
    var xnum = $('#num_'+rr_num+'_'+for_num).val();

    if(xnum == ynum){
        $('#num_'+rr_num+'_'+for_num).val(ynum);
    }else{
        if(name == '单位'){
            var attribute3 = [];
            for (var k1 in attribute) {
                attribute3[k1] = JSON.parse(attribute[k1]);
                for (var k2 in attribute3[k1]) {
                    if(k2 == '单位'){
                        attribute3[k1][k2] = xnum;
                    }
                }
                attribute[k1] =  JSON.stringify(attribute3[k1]);
                k2 = ++k1;

                $('#num_'+k2+'_'+for_num).val(xnum);
            }
        }else{
            if(name == '成本价' || name == '原价' || name == '现价' || name == '数量'){
                var attribute1 = JSON.parse(attribute[r_num1]);
                attribute1[name] = xnum;
                var attribute2 = JSON.stringify(attribute1);
                attribute[r_num1] = attribute2;
            }else{
                // 已有属性数组
                var list = [];
                for (var h in attribute) {
                    list1 = JSON.parse(attribute[h]);
                    var list2 = [];
                    for (var h1 in list1) {
                        var list3 = {};
                        list3[h1] = list1[h1];
                        list2.push(list3); //属性
                    }
                    for (var h_1 = 0;h_1 < 6;h_1++) {
                        list2.pop()
                    }
                    var srtj = '{';
                    for (var i = 0; i < list2.length; i++) {
                        for (var h2 in list2[i]) {
                            srtj += '"' + h2 + '":"' + list2[i][h2] + '",';
                        }
                    }
                    srtj = srtj.substring(0, srtj.length - 1);
                    srtj += '}';

                    list[h] = srtj;
                }

                var attribute1 = JSON.parse(attribute[r_num1]);
                // 修改后的属性数组
                var list_1 = [];
                for (var k in attribute1) {
                    if(k == name){
                        attribute1[k] = xnum;
                    }
                    var o = {};
                    o[k] = attribute1[k];
                    list_1.push(o); //属性
                }
                for (var h_1 = 0;h_1 < 6;h_1++) {
                    list_1.pop()
                }
                var srtj_1 = '{';
                for (var i = 0; i < list_1.length; i++) {
                    for (var h2 in list_1[i]) {
                        srtj_1 += '"' + h2 + '":"' + list_1[i][h2] + '",';
                    }
                }
                srtj_1 = srtj_1.substring(0, srtj_1.length - 1);
                srtj_1 += '}';
                if (in_array(srtj_1, list)) {
                    alert("属性重复，请核对属性");
                    $('#num_'+rr_num+'_'+for_num).val(ynum);
                }else{
                    var attribute2 = JSON.stringify(attribute1);
                    attribute[r_num1] = attribute2;
                }
            }
        }
        var attribute_value = '{';
        for (var i = 0; i < attribute.length; i++) {
            attribute_value += '"' + i + '":' + attribute[i] + ',';
        }

        attribute_value = attribute_value.substring(0, attribute_value.length - 1);
        attribute_value += '}';

        $(".attribute").val(attribute_value);
    }
    $('#num_'+rr_num+'_'+for_num).attr('readOnly',true);
    document.getElementById('num_'+rr_num+'_'+for_num).style.backgroundColor="#eeeeee";
}

function image_click(rr_num) {
    var rr_num1 = rr_num - 1;
    var attribute1 = JSON.parse(attribute[rr_num1]);

    KindEditor.ready(function (K) {
        var editor = K.editor({
            allowFileManager: true,
            uploadJson: "index.php?module=system&action=uploadImg", //上传功能
            fileManagerJson: 'kindeditor/php/file_manager_json.php', //网络空间
        });
        editor.loadPlugin('image', function () {
            editor.plugin.imageDialog({
                showRemote: false, //网络图片不开启
                //showLocal : false, //不开启本地图片上传
                imageUrl: K('#picurl_"+rr_num+"').val(),
                clickFn: function (url, title, width, height, border, align) {
                    attribute1['图片'] = url;
                    var attribute2 = JSON.stringify(attribute1);
                    attribute[rr_num1] = attribute2;
                    var attribute_value = '{';
                    for (var i = 0; i < attribute.length; i++) {
                        attribute_value += '"' + i + '":' + attribute[i] + ',';
                    }

                    attribute_value = attribute_value.substring(0, attribute_value.length - 1);
                    attribute_value += '}';

                    $(".attribute").val(attribute_value);
                    K('#picurl_'+rr_num).val(url);
                    $('#thumb_url_'+rr_num).attr("src", url);
                    editor.hideDialog();
                }
            });
        });
    });
}
</script>
{/literal}
</body>
</html>