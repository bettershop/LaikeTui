
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>钱包参数</title>
</head>
<body>
<nav class="breadcrumb"> 配置管理 <span class="c-gray en">&gt;</span> 钱包参数 </nav>
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
                <label class="form-label col-4">小程序钱包单位：</label>
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
            <label class="form-label col-4"></label>
            <div class="formControls col-4" >
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