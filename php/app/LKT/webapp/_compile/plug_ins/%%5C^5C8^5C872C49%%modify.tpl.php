<?php /* Smarty version 2.6.26, created on 2021-03-11 13:08:04
         compiled from modify.tpl */ ?>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

<?php echo '
<script>
function change(){
  var type = $(\'input[name="type"]:checked\').val();
  $.ajax({
    type: "GET",
    url: location.href+\'&action=ajax&type=\'+type,
    data: "",
    success: function(msg){
      $(".select").html(msg);
    }
  });
}
</script>
'; ?>

<title>修改插件</title>
</head>
<body>
<nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=plug_ins">插件列表</a> <span class="c-gray en">&gt;</span>
    编辑插件 <span class="c-gray en">&gt;</span>
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
  <form name="form1" action="index.php?module=plug_ins&action=modify" class="form form-horizontal" method="post" enctype="multipart/form-data" >
    <input type="hidden" name="id" value="<?php echo $this->_tpl_vars['id']; ?>
" >
    <input type="hidden" name="uploadImg" value="<?php echo $this->_tpl_vars['uploadImg']; ?>
" >
    <div class="row cl">
      <label class="form-label col-4"><span class="c-red">*</span>首页插件名称：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['name']; ?>
" placeholder="" id="" name="name">
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4">个人中心插件名称：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['subtitle_name']; ?>
" placeholder="" id="" name="subtitle_name">
      </div>
    </div>

    <div class="row cl">
      <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>首页插件图标：</label>
      <div class="formControls col-xs-8 col-sm-4"> 
        <?php if ($this->_tpl_vars['image'] != ''): ?>
            <img id="thumb_url1" src='<?php echo $this->_tpl_vars['uploadImg']; ?>
<?php echo $this->_tpl_vars['image']; ?>
' style="height:100px;width:150">
        <?php else: ?>
            <img id="thumb_url1" src='<?php echo $this->_tpl_vars['uploadImg']; ?>
nopic.jpg' style="height:100px;width:150">
        <?php endif; ?>
        <input type="hidden"  id="picurl1" name="image" datatype="*" nullmsg="请选择图片"/> 
        <input type="hidden" name="oldpic1" value=<?php echo $this->_tpl_vars['image']; ?>
>
        <button class="btn btn-success" id="image1"  type="button" >选择图片</button>
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-xs-4 col-sm-4">个人中心插件图标：</label>
      <div class="formControls col-xs-8 col-sm-4"> 
        <?php if ($this->_tpl_vars['subtitle_image'] != ''): ?>
            <img id="thumb_url2" src='<?php echo $this->_tpl_vars['uploadImg']; ?>
<?php echo $this->_tpl_vars['subtitle_image']; ?>
' style="height:100px;width:150">
        <?php else: ?>
            <img id="thumb_url2" src='<?php echo $this->_tpl_vars['uploadImg']; ?>
nopic.jpg' style="height:100px;width:150">
        <?php endif; ?>
        <input type="hidden"  id="picurl2" name="subtitle_image" datatype="*" nullmsg="请选择图片"/> 
        <input type="hidden" name="oldpic2" value=<?php echo $this->_tpl_vars['subtitle_image']; ?>
>
        <button class="btn btn-success" id="image2"  type="button" >选择图片</button>
      </div>
      <div class="col-4"> </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4"><span class="c-red">*</span>首页链接：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['url']; ?>
" placeholder="" id="" name="url">
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4">个人中心链接：</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['subtitle_url']; ?>
" placeholder="" id="" name="subtitle_url">
      </div>
    </div>
    <div class="row cl">
        <label class="form-label col-4">排序号：</label>
        <div class="formControls col-4">
            <input type="text" class="input-text" value="<?php echo $this->_tpl_vars['sort']; ?>
" placeholder="" id="" name="sort">
        </div>
    </div>

    <div class="row cl">
      <label class="form-label col-4">插件标志code:</label>
      <div class="formControls col-4">
        <input type="text" class="input-text" disabled value="<?php echo $this->_tpl_vars['code']; ?>
" placeholder="" id="" name="code">
      </div>
    </div>
    <div class="row cl">
      <label class="form-label col-4"></label>
      <div class="formControls col-4">
        <button class="btn btn-primary radius" type="submit" name="Submit">提 交</button>
        <button class="btn btn-secondary radius" type="reset" name="reset">重 写</button>
      </div>
    </div>
  </form>
  <input type="hidden" id="pic" value="<?php echo $this->_tpl_vars['pic']; ?>
" >
</div>


<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>

<?php echo '
<script>
KindEditor.ready(function(K) {
  var pic = $("#pic").val();
  var editor = K.editor({
      allowFileManager : true,       
      uploadJson : "index.php?module=system&action=uploadImg", //上传功能
      fileManagerJson : \'style/kindeditor/php/file_manager_json.php?dirpath=\'+pic, //网络空间
    });
  //上传背景图片
  K(\'#image1\').click(function() {
    editor.loadPlugin(\'image\', function() {
      editor.plugin.imageDialog({
        showRemote : false, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K(\'#picurl1\').val(),
          clickFn : function(url, title, width, height, border, align) {
          K(\'#picurl1\').val(url);
          $(\'#thumb_url1\').attr("src",url);
          editor.hideDialog();
        }
      });
    });
  });
  //上传背景图片
  K(\'#image2\').click(function() {
    editor.loadPlugin(\'image\', function() {
      editor.plugin.imageDialog({
        showRemote : false, //网络图片不开启
        //showLocal : false, //不开启本地图片上传
        imageUrl : K(\'#picurl2\').val(),
          clickFn : function(url, title, width, height, border, align) {
          K(\'#picurl2\').val(url);
          $(\'#thumb_url2\').attr("src",url);
          editor.hideDialog();
        }
      });
    });
  });
});
</script>
'; ?>

</body>
</html>