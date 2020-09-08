
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>添加活动</title>
</head>
<body>



<nav class="breadcrumb">
    系统管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=notice">公告列表</a> <span class="c-gray en">&gt;</span> 
    发布公告 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
    <form name="form1" action="index.php?module=notice&action=add" class="form form-horizontal" method="post" onsubmit="return checkUser()" enctype="multipart/form-data" >
        
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>公告名称：</label>
            <div class="formControls col-10"> 
               <input type="text" name="notice" required="required" id="noticeId" style="width:280px">
               <input type="hidden" id="hiddenId" >
            </div>
        </div>


        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>公告图片：</label>
            <div class="formControls col-xs-8 col-sm-8"> 
                <img id="thumb_url" src='{$uploadImg}nopic.jpg' style="height:100px;width:150">
                <input type="hidden"  id="picurl" name="image" datatype="*" nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpic" >
                <button class="btn btn-success" id="image"  type="button" >选择图片</button>
            </div>
            <div class="col-4"> </div>
        </div>
        


        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>活动介绍：</label>
            <div class="formControls col-10"> 
                <script id="editor" type="text/plain" name="detail" style="width:100%;height:400px;">{$detail}</script>
                 
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"></label>
            <div class="formControls col-10">
                <button class="btn btn-primary radius" name="Submit" >提 交</button>
            </div>
        </div>
    </form>
    <input type="hidden" id="pic" value="{$pic}" >
</div>

<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/ueditor.all.min.js"></script>
<script type="text/javascript" src="style/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css"/>
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>
<script language="javascript"  src="style/js/check.js"> </script>
<script src="style/js/vue.min.js"></script>
{literal}
<script>
KindEditor.ready(function(K) {
     var pic = $("#pic").val();
  var editor = K.editor({
      allowFileManager : true,       
      uploadJson : "index.php?module=system&action=uploadImg", //上传功能
      fileManagerJson : 'style/kindeditor/php/file_manager_json.php?dirpath='+pic, //网络空间
    });
  //上传背景图片
  K('#image').click(function() {
    editor.loadPlugin('image', function() {
      editor.plugin.imageDialog({
        showRemote : false, //网络图片不开启
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
</script>
<script type="text/javascript">
var isShow = false
$(function(){
    var ue = UE.getEditor('editor');
    $("#islabel1").hide()
    $("#islabel2").hide()
    $("#islabel3").hide()
    $("#ischange").click(function(e) {
        isShow = !isShow
        if(isShow){
            $('#iaaaa').remove()
            $("#islabel1").show()
            $("#islabel2").show()
            $("#islabel3").show()
            return
        }       

        $("#islabel1").hide()
        $("#islabel2").hide()
        $("#islabel3").hide()
        $('#iaaaa').hide()
        $("#ishidden").append(`<input type="hidden" name="is_distribution" value="0" id="iaaaa">`)
    })
        var distribution =$("input[name='distribution']").val();
    if(distribution == 1){
        $("#islabel1").show()
        $("#islabel2").show()
        $("#islabel3").show()
    }

});
</script>
{/literal}
</body>
</html>