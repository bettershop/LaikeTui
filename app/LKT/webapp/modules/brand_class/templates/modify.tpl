
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
{literal}
<script type="text/javascript">
function check(f){
    if(Trim(f.pname.value)==""){
        alert("品牌名称不能为空！");
        f.pname.value = '';
        return false;
    }

    return true;
}
</script>
{/literal}
<title>修改新闻分类</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 产品品牌管理 <span class="c-gray en">&gt;</span> 修改产品品牌 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=brand_class';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <form name="form1" action="index.php?module=brand_class&action=modify" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <input type="hidden" name="cid" value="{$brand_id}" />
        <input type="hidden" name="uploadImg" value="{$uploadImg}" id="uploadImg">
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>中文名称：</label>
            <div class="formControls col-4">
                <input type="text" class="input-text" name="pname" id="pname" value="{$brand_name}" datatype="*6-18" style="width: 260px;">
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>英文名称：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" name="y_pname" id="y_pname" value="{$brand_name}" datatype="*6-18" style="width: 260px;" >
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"><span class="c-red"></span>品牌图片：</label>
            <div class="formControls col-xs-8 col-sm-4"> 
                <img id="thumb_url" src='{$uploadImg}{$brand_pic}' style="height:100px;width:150px;" >
                <input type="hidden"  id="picurl" name="image" datatype="*" nullmsg="请选择图片"/> 
                <input type="hidden" name="oldpic" value="{$brand_pic}" id="oldpic">
                <button class="btn btn-success" id="image"  type="button" >选择图片</button>
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red" ></span>产地：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" name="producer" id="producer" datatype="*6-18" style="width: 260px;" value="{$producer}">
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>排序：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" name="sort" id="sort" datatype="*6-18" value="{$sort}" style="width: 260px;">
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>备注：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" name="remarks" id="remarks" datatype="*6-18" style="width: 260px;" value="{$remarks}">
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <div class="col-8 col-offset-4">
                <input type="submit" name="Submit" value="提 交" class="btn btn-primary radius">
                <input type="button" value="清空" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076;" class="reset" onclick="empty()"/>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript" src="style/js/jquery.js"></script>

<!-- 新增编辑器引入文件 -->
<link rel="stylesheet" href="style/kindeditor/themes/default/default.css" />
<script src="style/kindeditor/kindeditor-min.js"></script>
<script src="style/kindeditor/lang/zh_CN.js"></script>
{literal}
<script>
// 清空
function empty(){
    $("#pname").val('');
    $("#y_pname").val('');
    $("#producer").val('');
    $("#sort").val('');
    $("#remarks").val('');
    var src = $("#uploadImg").val();
    src = src+'nopic.jpg';
    $("#thumb_url").attr("src",src);
    $("#picurl").val('');
    $("#oldpic").val('');
}
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
        showRemote : false, //网络图片不开启
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
</script>
{/literal}
</body>
</html>