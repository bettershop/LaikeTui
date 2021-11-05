<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}


<title>系统参数</title

</head>

<body>


<nav class="breadcrumb">
    小程序 <span class="c-gray en">&gt;</span> 
    系统设置
</nav>


<div class="page-container">
    <form name="form1" action="index.php?module=system" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>公司LOGO：</label>
                <div class="formControls col-xs-8 col-sm-6"> 
                {if $logo != ''}
                    <img id="thumb_url" src='{$uploadImg}{$logo}' style="height:100px;width:150">
                {else}
                    <img id="thumb_url" src='../LKT/images/nopic.jpg' style="height:100px;width:150">
                {/if}
                    <input type="hidden"  id="picurl" name="image" datatype="*" nullmsg="请选择图片"/> 
                    <input type="hidden" name="oldpic" value="{$logo}" >
                    <button class="btn btn-success" id="image"  type="button" >选择图片</button>
                </div>
                <div class="col-4"> </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>小程序名称：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="company" value="{$company}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>小程序AppID：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="appid" value="{$appid}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>小程序Appsecret：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="appsecret" value="{$appsecret}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>小程序接口路径：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="domain" value="{$domain}" class="input-text">
                </div>
            </div>

            <div class="row cl" style="display:none">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>ip地址：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="ip" value="{$ip}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>系统后台路径：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="uploadImg_domain" value="{$uploadImg_domain}" class="input-text">
                    * 也与图片路径有关 有可能导致图片不显示
                </div>
            </div>
            <div class="row cl" style="display: none;">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>图片上传位置：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="uploadImg" value="{$uploadImg}" class="input-text">
                    <i style="color: red;padding-right: 5px;margin-top: 5px;vertical-align: -4px;">*</i><text style="color: #333;font-size: 12px;">请勿经常改动，改动一次所有图片得重新上传一遍</text>
                </div>
            </div>
            <div class="row cl" style="display: none;">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>软件上传位置：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="upload_file" value="{$upload_file}" class="input-text">
                </div>
            </div>
			
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">系统缓存：</label>
				
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="button" class="btn btn-warning radius" style="width:100px" onclick="clearCache()" value="清除系统缓存" >
                    
                </div>
            </div>
			
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"></label>
            <div class="formControls col-xs-8 col-sm-6" >
			
                <button class="btn btn-primary radius" type="submit" name="Submit" >
					保 存
				</button>
                <button class="btn btn-default radius" type="reset">取 消</button>
				
            </div>
        </div>
    </form>

    
    
</div>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script type="text/javascript">

function clearCache(){
    $.ajax({
        url: 'index.php?module=system&action=delFile',
        success: function(msg){
            var res = JSON.parse(msg)
            if(res.status == 1){
                alert("清理成功");
            }else{
                alert("清理失败");
            }
        }
    });

    return;
}

</script>
<script>
KindEditor.ready(function(K) {
  var editor = K.editor({
      allowFileManager : true,       
      uploadJson : "index.php?module=system&action=uploadImg", //上传功能
      fileManagerJson : 'style/kindeditor/php/file_manager_json.php', //网络空间
    });
  //上传背景图片
  K('#image').click(function() {
    editor.loadPlugin('image', function() {
      editor.plugin.imageDialog({
        showRemote : true,
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