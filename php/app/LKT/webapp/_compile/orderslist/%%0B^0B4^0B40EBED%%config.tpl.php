<?php /* Smarty version 2.6.26, created on 2021-03-11 19:52:21
         compiled from config.tpl */ ?>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<?php include BASE_PATH."/modules/assets/templates/top.tpl"; ?>

<title>订单参数</title>
</head>
<body>
<nav class="breadcrumb">
    配置管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=orderslist&action=config">订单参数</a>
</nav>
<div class="page-container">
    <form name="form1" action="index.php?module=orderslist&action=config" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
           <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">退货时间：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="back" value="<?php echo $this->_tpl_vars['back']; ?>
" class="input-text">
                    <!--<text style="color: red;">(如：买家签收后,2天之内允许退货)</text>-->
                </div>
                <label style="line-height: 30px;height: 30px;" class="col-xs-4 col-sm-0">小时</label>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">订单失效时间：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="order_failure" value="<?php echo $this->_tpl_vars['order_failure']; ?>
" class="input-text">
                    <text style="color: red;">(0表示不失效)</text>
                </div>
	            <label style="line-height: 30px;height: 30px;" class="col-xs-4 col-sm-0">小时</label>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">快递100公司编号：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="customer" value="<?php echo $this->_tpl_vars['customer']; ?>
" class="input-text">
                </div>
                <label style="line-height: 30px;height: 30px;" class="col-xs-4 col-sm-0"><a href="https://www.kuaidi100.com/" target="_blank">[申请点这里]</a></label>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">客户授权key：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="kdkey" value="<?php echo $this->_tpl_vars['kdkey']; ?>
" class="input-text">
                    <text style="color: red;"></text>
                </div>
                <label style="line-height: 30px;height: 30px;" class="col-xs-4 col-sm-0"></label>
            </div>

        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-4"></label>
            <div class="formControls col-xs-8 col-sm-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>

            </div>
        </div>
    </form>
</div>
</div>

<?php include BASE_PATH."/modules/assets/templates/footer.tpl"; ?>

</body>
</html>