
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>系统参数</title>

</head>

<body>


<nav class="breadcrumb">
    小程序 <span class="c-gray en">&gt;</span> 
    支付设置
</nav>

<div class="page-container">
    <form name="form1" action="" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>商户号：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="mch_id" value="{$mch_id}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>API密钥：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="mch_key" value="{$mch_key}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>支付证书文件：</label>
                <div class="formControls col-xs-8 col-sm-6" >
                    <input style="padding-left: 10px;" type="file" accept=".zip" name="upload_cert">
                    <span style="color: #333;font-size: 12px;">注:仅可上传zip压缩包，需要有open_zip函数支持</span>
                </div>
            </div>
            <div class="row cl" style="display:none">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>证书路径：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="mch_cert" value="{$mch_cert}" class="input-text">
                    <i style="color: red;padding-right: 5px;margin-top: 5px;vertical-align: -4px;">*</i><text style="color: #333;font-size: 12px;">上传过支付证书后字体自动生成(退款等需要)</text>
                </div>
            </div>
        </div>
        <div class="row cl">
          <label class="form-label col-xs-4 col-sm-4"></label>
            <div class="formControls col-xs-8 col-sm-6">
                <button class="btn btn-primary radius" type="submit" name="Submit">保 存</button>
                <button class="btn btn-default radius" type="reset">取 消</button>
            </div>
        </div>
    </form>
</div>
</div>


{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}


</body>
</html>