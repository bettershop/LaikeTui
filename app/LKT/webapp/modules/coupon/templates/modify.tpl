
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
<script type="text/javascript">
function show(obj){
    if(obj.value=='2'){ // 节日/活动
        document.getElementById("name").readOnly = false; // 活动名称
        document.getElementById('txt').style.display = ""; // 不显示
        document.getElementById('txt_1').style.display = ""; // 金额不显示
        document.getElementById('txt_2').style.display = "none"; // 减不显示
        document.getElementById('product_class_id').style.display = ""; // 优惠劵类型id
        document.getElementById('num').style.display = ""; // 数量不显示
        document.getElementById('z_money').style.display = "none"; // 满金额不显示
        document.getElementById('time').style.display = ""; // 时间显示
    }else if(obj.value=='1'){ // 注册
        document.getElementById("name").readOnly = false; // 活动名称
        document.getElementById('txt').style.display = ""; // 显示
        document.getElementById('txt_1').style.display = ""; // 金额显示
        document.getElementById('txt_2').style.display = "none"; // 减不显示
        document.getElementById('product_class_id').style.display = "none"; // 优惠劵类型id
        document.getElementById('num').style.display = "none"; // 数量
        document.getElementById('z_money').style.display = "none"; // 满金额不显示
        document.getElementById('time').style.display = "none"; // 时间不显示
    }else if(obj.value=='3'){ // 满减
        document.getElementById("name").readOnly = true; // 活动名称
        document.getElementById('txt').style.display = ""; // 显示
        document.getElementById('txt_1').style.display = "none"; // 金额不显示
        document.getElementById('txt_2').style.display = ""; // 减显示
        document.getElementById('product_class_id').style.display = "none"; // 优惠劵类型id
        document.getElementById('num').style.display = ""; // 数量
        document.getElementById('z_money').style.display = ""; // 满金额显示
        document.getElementById('time').style.display = ""; // 时间显示
    }
}
function change(){
    var product_class_id = $('select[name="product_class_id"]').children('option:selected').val();
    $.ajax({
        type: "GET",
        url: location.href+'&action=ajax&product_class_id='+product_class_id,
        data: "",
        success: function(msg){
            if(msg == 0){
                document.getElementById('product_id').style.display = 'none';
            }else{
                document.getElementById('product_id').style.display = '';
                $(".select2").html(msg);
            }
        }
    });
}
</script>
{/literal}
<title>修改活动</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe6ca;</i> 优惠券管理 <span class="c-gray en">&gt;</span> 活动列表 <span class="c-gray en">&gt;</span> 修改活动 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=coupon';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <form name="form1" action="index.php?module=coupon&action=modify" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <input type="hidden" name="id" value="{$id}">
        <input type="hidden" name="status" class="status" value="{$status}">
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>活动名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" placeholder="" id="name" value="{$name}" name="name" {if $activity_type==3}readonly{/if}>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>活动类型：</label>
            <div class="formControls col-4 skin-minimal">
                <div class="radio-box">
                    <input name="activity_type" type="radio" value="1" class="activity_type" checked="checked" onClick="show(this)" {if $activity_type==1}checked="checked"{/if}/>
                    <label for="sex-1">注册</label>
                </div>
                <div class="radio-box">
                    <input name="activity_type" type="radio" value="2" class="activity_type" onClick="show(this)" {if $activity_type==2}checked="checked"{/if}/>
                    <label for="sex-2">节日/活动</label>
                </div>
                {*<div class="radio-box">*}
                    {*<input name="activity_type" type="radio" value="3" class="activity_type" onClick="show(this)" {if $activity_type==3}checked="checked"{/if}/>*}
                    {*<label for="sex-3">满减</label>*}
                {*</div>*}
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl" style="display:{if $activity_type != 2}none{/if};" id="product_class_id" onchange="change()">
            <label class="form-label col-4">活动指定商品类型：</label>
            <select name="product_class_id" class="select1" style="width: 80px;height: 31px;vertical-align: middle;">
                {$list}
            </select>
            全部代表通用
        </div>
        <div class="row cl" style="display:{if $product_class_id == 0}none{/if};" id="product_id">
            <label class="form-label col-4">活动指定商品：</label>
            <select name="product_id" class="select2" style="width: 80px;height: 31px;vertical-align: middle;">
                {$list1}
            </select>
            全部代表该分类下面商品通用
        </div>
        
        <div class="row cl" id="txt">
            <label class="form-label col-4" id="txt_1" style="display:{if $activity_type == 3}none{/if};"><span class="c-red">*</span>金额：</label>
            <label class="form-label col-4" id="txt_2" style="display:{if $activity_type != 3}none{/if};"><span class="c-red">*</span>减：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" placeholder="" id="money" value="{$money}" name="money">
            </div>
        </div>
        
        <div class="row cl" id="z_money" style="display:{if $activity_type != 3}none{/if};">
            <label class="form-label col-4"><span class="c-red">*</span>金额满：</label>
            <div class="formControls col-4">
                <input type="number" class="input-text" placeholder="" id="z_money1" value="{$z_money}" name="z_money">
            </div>
        </div>
        
        <div class="row cl" id="num" style="display:{if $activity_type == 1}none{/if};">
            <label class="form-label col-4" >数量：</label>
            <div class="formControls col-2">
                <input type="number" class="input-text" placeholder="" value="{$num}" name="num" id="num1">
            </div>
            <text style="line-height:30px;">0表示没限制数量</text>
        </div>
        
        <div class="row cl" id="time" style="display:{if $activity_type == 1}none{/if};">
            <label class="form-label col-4"><span class="c-red">*</span>活动时间：</label>
            <div class="formControls col-6">
                <input name="start_time" value="{$start_time}" size="8" readonly class="scinput_s" style="width: 200px;height:26px;font-size: 14px;vertical-align: middle;" />
                <img src="modpub/images/datetime.gif" style="cursor:pointer;" onclick="new Calendar().show(document.form1.start_time);" />
                -
                <input name="end_time" value="{$end_time}" size="8" readonly  class="scinput_s" style="width: 200px;height:26px;font-size: 14px;vertical-align: middle;"/>
                <img src="modpub/images/datetime.gif" style="cursor:pointer;" onclick="new Calendar().show(document.form1.end_time);" />
            </div>
        </div>
        
        <div class="row cl">
            <div class="col-10 col-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 提 交</button>
                <button class="btn btn-secondary radius" type="reset" name="reset"><i class="Hui-iconfont">&#xe632;</i> 重 写</button>
            </div>
        </div>
    </form>
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
KindEditor.ready(function(K) {
  var editor = K.editor({
      allowFileManager : true,       
      uploadJson : "index.php?module=system&action=uploadImg", //上传功能
      fileManagerJson : 'kindeditor/php/file_manager_json.php', //网络空间
    });
  //上传背景图片
  K('#image').click(function() {
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
});
$("#money").blur(function(){
    var money = document.getElementById('money').value; // 减
    var z_money = document.getElementById('z_money1').value; // 满
    var activity_type = document.getElementsByName("activity_type");
    var selectvalue=null;   //  selectvalue为radio中选中的值
    for(var i=0;i<activity_type.length;i++){
        if(activity_type[i].checked==true) {
            selectvalue=activity_type[i].value;
            break;
        }
    }
    if (money=='') {
        alert('请输入');
        return false;
    }
    if(money > 0){
        money = parseInt(money);
        $("#money").val(money);
        if(selectvalue == 3){
            if(z_money == ''){
                $("#name").val('减'+money);
            }else{
                z_money = parseInt(z_money);
                $("#name").val('满'+z_money+'减'+money);
            }
        }
    }else{
        $("#money").val('');
        alert('输入的值要大于0');
        return false;
    }
});
$("#z_money1").blur(function(){
    var money = document.getElementById('money').value; // 减
    var z_money = document.getElementById('z_money1').value; // 满
    if (z_money=='') {
        alert('请输入');
        return false;
    }
    if(z_money > 0){
        z_money = parseInt(z_money);
        $("#z_money1").val(z_money);

        if(money == ''){
            $("#name").val('满'+z_money);
        }else{
            money = parseInt(money);
            $("#name").val('满'+z_money+'减'+money);
        }
    }else{
        $("#z_money1").val('');
        alert('输入的值要大于0');
        return false;
    }
});


if($(".status").val() == 1){
    document.getElementById('name').readOnly = true;
    document.getElementById('money').readOnly = true;
    document.getElementById('z_money1').readOnly = true;
    document.getElementById('num1').readOnly = true;
    // document.getElementById('activity_type').disabled = 'true';
    $('.activity_type').attr('disabled','disabled');
    $('.select1').attr('disabled','disabled');
    $('.select2').attr('disabled','disabled');

}
</script>

{/literal}
</body>
</html>