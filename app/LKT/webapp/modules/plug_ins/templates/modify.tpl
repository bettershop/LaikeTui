
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
<script>
function change(){
  var type = $('input[name="type"]:checked').val();
  $.ajax({
    type: "GET",
    url: location.href+'&action=ajax&type='+type,
    data: "",
    success: function(msg){
      $(".select").html(msg);
    }
  });
}
</script>
{/literal}
<title>修改插件</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe646;</i>插件管理 <span class="c-gray en">&gt;</span> 插件列表 <span class="c-gray en">&gt;</span> 修改插件 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=plug_ins';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
  <form name="form1" action="index.php?module=plug_ins&action=modify" class="form form-horizontal" method="post" enctype="multipart/form-data" >
    <input type="hidden" name="id" value="{$id}" >
    <input type="hidden" name="uploadImg" value="{$uploadImg}" >
    <div class="row cl">
      <label class="form-label col-4"><span class="c-red">*</span>首页插件名称：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="{$name}" placeholder="" id="" name="name">
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4">个人中心插件名称：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="{$subtitle_name}" placeholder="" id="" name="subtitle_name">
      </div>
    </div>
    <div class="row cl">
        <label class="form-label col-4"><span class="c-red"></span>插件类型：</label>
        <div class="formControls col-4 skin-minimal">
            <div class="radio-box">
                <input name="type" type="radio" value="0" {if type == 0} checked="checked" {/if} onclick="change()"/>
                <label for="sex-0">小程序</label>
            </div>
            <div class="radio-box">
                <input name="type" type="radio" value="1" {if type == 1} checked="checked" {/if} onclick="change()"/>
                <label for="sex-1">app</label>
            </div>
        </div>
        <div class="col-4"> </div>
    </div>
    <div class="row cl">
        <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>请选择软件：</label>
        <div class="formControls col-xs-8 col-sm-4"> 
            <select name="software_id" class="select" style="width: 80px;height: 31px;vertical-align: middle;">
                {$list}
            </select>
        </div>
        <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>首页插件图标：</label>
      <div class="formControls col-xs-8 col-sm-4"> 
        {if $image != ''}
            <img id="thumb_url1" src='{$uploadImg}{$image}' style="height:100px;width:150">
        {else}
            <img id="thumb_url1" src='{$uploadImg}nopic.jpg' style="height:100px;width:150">
        {/if}
        <input type="hidden"  id="picurl1" name="image" datatype="*" nullmsg="请选择图片"/> 
        <input type="hidden" name="oldpic1" value={$image}>
        <button class="btn btn-success" id="image1"  type="button" >选择图片</button>
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-xs-4 col-sm-4">个人中心插件图标：</label>
      <div class="formControls col-xs-8 col-sm-4"> 
        {if $subtitle_image != ''}
            <img id="thumb_url2" src='{$uploadImg}{$subtitle_image}' style="height:100px;width:150">
        {else}
            <img id="thumb_url2" src='{$uploadImg}nopic.jpg' style="height:100px;width:150">
        {/if}
        <input type="hidden"  id="picurl2" name="subtitle_image" datatype="*" nullmsg="请选择图片"/> 
        <input type="hidden" name="oldpic2" value={$subtitle_image}>
        <button class="btn btn-success" id="image2"  type="button" >选择图片</button>
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4"><span class="c-red">*</span>首页链接：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="{$url}" placeholder="" id="" name="url">
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4">个人中心链接：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="{$subtitle_url}" placeholder="" id="" name="subtitle_url">
      </div>
    </div>
    <div class="row cl">
        <label class="form-label col-4">排序号：</label>
        <div class="formControls col-4">
            <input type="text" class="input-text" value="{$sort}" placeholder="" id="" name="sort">
        </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4">参数设置地址：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" disabled value="{$http}" placeholder="" id="" name="http">
      </div>
    </div>
    <div class="row cl">
      <div class="col-10 col-offset-4">
        <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 提 交</button>
        <!-- <button class="btn btn-secondary radius" type="reset" name="reset"><i class="Hui-iconfont">&#xe632;</i> 重 写</button> -->
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
  K('#image1').click(function() {
    editor.loadPlugin('image', function() {
      editor.plugin.imageDialog({
        //showRemote : false, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K('#picurl1').val(),
          clickFn : function(url, title, width, height, border, align) {
          K('#picurl1').val(url);
          $('#thumb_url1').attr("src",url);
          editor.hideDialog();
        }
      });
    });
  });
  //上传背景图片
  K('#image2').click(function() {
    editor.loadPlugin('image', function() {
      editor.plugin.imageDialog({
        //showRemote : false, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K('#picurl2').val(),
          clickFn : function(url, title, width, height, border, align) {
          K('#picurl2').val(url);
          $('#thumb_url2').attr("src",url);
          editor.hideDialog();
        }
      });
    });
  });
});
</script>
{/literal}
</body>
</html>