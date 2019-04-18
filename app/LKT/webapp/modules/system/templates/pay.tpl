
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
<link href="style/css/style.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />

<title>系统参数</title>
{literal}


<style type="text/css">

@font-face {font-family: "iconfont";
  src: url('iconfont.eot?t=1530949902201'); /* IE9*/
  src: url('iconfont.eot?t=1530949902201#iefix') format('embedded-opentype'), /* IE6-IE8 */
  url('data:application/x-font-woff;charset=utf-8;base64,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') format('woff'),
  url('iconfont.ttf?t=1530949902201') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
  url('iconfont.svg?t=1530949902201#iconfont') format('svg'); /* iOS 4.1- */
}

.iconfont {
  font-family:"iconfont" !important;
  font-size:30px;
  font-style:normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.icon-zhengshu-copy:before { content: "\e640"; }
</style>

{/literal}
</head>

<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 系统管理 <span class="c-gray en">&gt;</span> 支付设置 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
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
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>商户key：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="mch_key" value="{$mch_key}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>支付证书文件：</label>
                <div class="iconfont icon-zhengshu-copy" >
                    <input style="padding-left: 10px;" type="file" accept=".zip" name="upload_cert">
                    <span style="color: #333;font-size: 12px;">注:仅可上传zip压缩包</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>证书路径：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="mch_cert" value="{$mch_cert}" class="input-text">
                    <i style="color: red;padding-right: 5px;margin-top: 5px;vertical-align: -4px;">*</i><text style="color: #333;font-size: 12px;">上传过支付证书后字体自动生成(退款等需要)</text>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <button class="btn btn-default radius" type="reset">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>
    </form>
</div>
</div>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
</body>
</html>