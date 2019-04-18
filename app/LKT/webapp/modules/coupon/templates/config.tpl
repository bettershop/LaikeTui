
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

<title>优惠券参数</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 优惠券管理 <span class="c-gray en">&gt;</span> 优惠券参数 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=plug_ins';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="page-container">
    <form name="form1" action="index.php?module=coupon&action=config" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <input type="hidden" name="plug_ins_id" value="{$plug_ins_id}" >
        <input type="hidden" name="software_id" value="{$software_id}" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>优惠券活动删除日期：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="activity_overdue" value="{$activity_overdue}" class="input-text">
                    <text>如：活动结束2天后,删除该活动. 0 表示不删除</text>
                </div>
                <label class="form-label col-xs-4 col-sm-0">天</label>
            </div>
            {*<div class="row cl">*}
                {*<label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>优惠券有效期：</label>*}
                {*<div class="formControls col-xs-8 col-sm-4">*}
                    {*<input type="text" name="coupon_validity" value="{$coupon_validity}" class="input-text">*}
                    {*<text>如：优惠券领取,7天以后失效</text>*}
                {*</div>*}
                {*<label class="form-label col-xs-4 col-sm-0">天</label>*}
            {*</div>*}
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>优惠券过期时间：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="coupon_overdue" value="{$coupon_overdue}" class="input-text">
                    <text>如：优惠券过期,2天后自动删除. 0 表示不删除</text>
                </div>
                <label class="form-label col-xs-4 col-sm-0">天</label>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                <button class="btn btn-default radius" type="reset">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>
            </div>
        </div>
    </form>
</div>
</div>
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div> 
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

</body>
</html>