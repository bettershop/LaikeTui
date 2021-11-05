
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />


{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>优惠券参数</title>
</head>
<body>


<nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=coupon&action=coupon">优惠券列表</a> <span class="c-gray en">&gt;</span> 
    优惠券参数 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


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
            <label class="form-label col-xs-4 col-sm-4"></label>
            <div class="formControls col-xs-8 col-sm-4">
                <button class="btn btn-primary radius" type="submit" name="Submit">保 存</button>
                <button class="btn btn-default radius" type="reset">重 置</button>
            </div>
        </div>
    </form>
</div>
</div>


{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}


</body>
</html>