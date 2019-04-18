
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

<title>系统参数设置</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 新闻管理 <span class="c-gray en">&gt;</span> 新闻列表管理 <span class="c-gray en">&gt;</span> 分享红包设置 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=Article';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <form name="form1" action="index.php?module=Article&action=amount" class="form form-horizontal" method="post" enctype="multipart/form-data" style="text-align: center;">
        <input type="hidden" name="id" value="{$id}" />
        <div style="text-align: center;font-size: 30px;color: red;">{$title}</div>
        <div class="row cl">
            <label class="form-label col-5">分享总金额：</label>
            <div class="formControls col-2">
                <input type="text" class="input-text" value="{$total_amount}" name="total_amount">
            </div>
            <label class="form-label col-2" style="text-align:left;">元</label>
        </div>
        <div class="row cl">
            <label class="form-label col-5"><span class="c-red"></span>红包个数：</label>
            <div class="formControls col-2">
                <input type="text" class="input-text" value="{$total_num}" name="total_num">
            </div>
            <label class="form-label col-2" style="text-align:left;">个</label>
        </div>
        <div class="row cl">
            <label class="form-label col-5"><span class="c-red"></span>祝福语句：</label>
            <div class="formControls col-2">
                <input type="text" class="input-text" value="{$wishing}" name="wishing">
            </div>
            <label class="form-label col-2" style="text-align:left;">如：恭喜发财</label>
        </div>
        <div class="row cl">
            <div class="col-10">
                <button class="btn btn-primary radius" type="submit" name="Submit"> 提 交 </button>
                <button class="btn btn-secondary radius" type="reset" name="reset"> 重 写 </button>
            </div>
        </div>
    </form>
</div>
</body>
</html>