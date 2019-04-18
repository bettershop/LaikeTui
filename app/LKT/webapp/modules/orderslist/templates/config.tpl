
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

<title>订单参数</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe627;</i> 订单管理 <span class="c-gray en">&gt;</span> 订单参数 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <form name="form1" action="index.php?module=orderslist&action=config" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
           <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">退货时间：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="back" value="{$back*24}" class="input-text">
                    <!--<text style="color: red;">(如：买家签收后,2天之内允许退货)</text>-->
                </div>
                <label style="line-height: 30px;height: 30px;" class="col-xs-4 col-sm-0">小时</label>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4">订单失效时间：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="order_failure" value="{$order_failure}" class="input-text">
                    <text style="color: red;">(0表示不失效)</text>
                </div>
	            <label style="line-height: 30px;height: 30px;" class="col-xs-4 col-sm-0">小时</label>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-4">
                <button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
<!--                 <button class="btn btn-default radius" type="reset">&nbsp;&nbsp;重置&nbsp;&nbsp;</button> -->
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