
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

</head>

<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 系统管理 <span class="c-gray en">&gt;</span> 退货地址设置 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <form name="form1" action="index.php?module=return&action=set" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>联系人：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="name" value="{$list->name}" class="input-text">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>联系电话：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="tel" value="{$list->tel}" class="input-text">
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>详细地址：</label>
                <div class="formControls col-xs-8 col-sm-6">
                    <input type="text" name="address" value="{$list->address}" class="input-text">
                </div>
            </div>

        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <!-- <button class="btn btn-default radius" type="reset">&nbsp;&nbsp;清空&nbsp;&nbsp;</button> -->
            </div>
        </div>
    </form>
</div>
</div>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
</body>
</html>