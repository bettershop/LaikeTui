
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

<title>添加软件</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe654;</i> 系统管理 <span class="c-gray en">&gt;</span> 版本管理 <span class="c-gray en">&gt;</span> 添加版本 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=software';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <form name="form1" action="index.php?module=software&action=add" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <div class="row cl">
            <label class="form-label col-4">软件名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" value="" placeholder="" id="" name="name">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4">软件图标：</label>
            <div class="formControls col-xs-8 col-sm-4"> 
                <img id="thumb_url" src='../LKT/images/nopic.jpg' style="height:100px;width:150">
                <input type="hidden"  id="picurl" name="image" datatype="*" nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpic" >
                <button class="btn btn-success" id="image"  type="button" >选择图片</button>
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>软件版本：</label>
            <div class="formControls col-4 skin-minimal">
                <div class="radio-box">
                    <input name="software_version" type="radio" value="0"/>
                    <label for="sex-0">开源版</label>
                </div>
                <div class="radio-box">
                    <input name="software_version" type="radio" value="1" />
                    <label for="sex-0">运营版</label>
                </div>
                <div class="radio-box">
                    <input name="software_version" type="radio" value="2" checked="" />
                    <label for="sex-0">商业版</label>
                </div>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>软件类型：</label>
            <div class="formControls col-4 skin-minimal">
                <div class="radio-box">
                    <input name="software_type" type="radio" value="0" />
                    <label for="sex-0">小程序</label>
                </div>
                 <div class="radio-box">
                    <input name="software_type" type="radio" value="1" />
                    <label for="sex-0">APP</label>
                </div>               
                <div class="radio-box">
                    <input name="software_type" type="radio" value="2" checked="" />
                    <label for="sex-0">后台</label>
                </div>
            </div>
            <div class="col-4"> </div>
        </div>
  
        <div class="row cl">
            <label class="form-label col-4">版本号：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" value="" placeholder="" id="" name="edition">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">更新介绍：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" value="" placeholder="" id="" name="edition_text">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">上传软件包：</label>
            <div class="formControls col-4">
                <input type="file" name="edition_url" accept=".zip"/>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">上一版本号：</label>
            <div class="formControls col-4 click_select">
            <select name="old_version" class="select">
                <option value=''>无</option>
                {$ops}
            </select>
            </div>
        </div>
         <div class="row cl">
            <label class="form-label col-4"></label>
            <div class="formControls col-4">注：上一版号添加好后，将不能修改，请确认是否正确！</div>
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

$("input[name='software_type']").change(function () {
    var select_type = $("input[name='software_type']:checked").val();
    var select_version = $("input[name='software_version']:checked").val();
    $.ajax({
       type: "POST",
       url: "index.php?module=software&action=add",
       data: "m=select&type="+select_type+'&select_version='+select_version,
       success: function(msg){
         $(".select").empty();
         $(".select").append(msg);
       }
    });
});
$("input[name='software_version']").change(function () {
    var select_version = $("input[name='software_version']:checked").val();
    var select_type = $("input[name='software_type']:checked").val();
    $.ajax({
       type: "POST",
       url: "index.php?module=software&action=add",
       data: "m=select&type="+select_type+'&select_version='+select_version,
       success: function(msg){
         $(".select").empty();
         $(".select").append(msg);
       }
    });
});
</script>
{/literal}
</body>
</html>