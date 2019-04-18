
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

<title>钱包参数</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 配置管理 <span class="c-gray en">&gt;</span> 钱包参数 </nav>
<div class="page-container">
    <form name="form1" action="index.php?module=finance&action=config" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-4">最小充值金额：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$min_cz}" id="min_cz" name="min_cz">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">最小提现金额：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$min_amount}" id="min_amount" name="min_amount">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">最大提现金额：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$max_amount}" id="max_amount" name="max_amount">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">手续费：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$service_charge}" id="service_charge" name="service_charge">
                    <span style="color:#666;">手续费为大于0小于1的小数,如0.005</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">小程序和APP钱包单位：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$unit}" id="unit" name="unit">
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">提现基数：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$multiple}" id="multiple" name="multiple">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">转账基数：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$transfer_multiple}" id="transfer_multiple" name="transfer_multiple">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">充值基数：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="{$cz_multiple}" id="cz_multiple" name="cz_multiple">
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
                {*<button class="btn btn-default radius" type="reset">&nbsp;&nbsp;重置&nbsp;&nbsp;</button>*}
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