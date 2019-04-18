
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

<title>砍价参数</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 砍价管理 <span class="c-gray en">&gt;</span> 砍价参数 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=plug_ins';" title="关闭" ><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="page-container">
    <form name="form1" action="index.php?module=bargain&action=config" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
        <input type="hidden" name="plug_ins_id" value="{$plug_ins_id}" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>能砍的次数：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="can_num" value="{$can_num}" class="input-text">
                    <text>每件商品,最多能砍价几次</text>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>每天最多帮别人砍的次数：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="help_num" value="{$help_num}" class="input-text">
                    <text>如：您每天只能帮3个朋友砍价</text>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>每次砍价的参数：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="parameter" value="{$parameter}" class="input-text">
                    <text>商品的价格*参数=砍掉的价格,当商品价格低于参数时,再砍次价格就为0,砍价成功. 如：100元商品,参数是0.3,砍掉的价格依次（30、21、14.7、10.29、7.2、5.04、3.53、2.47等）</text>
                </div>
                <label class="form-label col-xs-4 col-sm-0">元</label>
            </div>
            
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>逾期失效时间：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="invalid_time" value="{$invalid_time}" class="input-text">
                    <text>砍价成功后,多长时间没填写收货信息就过期</text>
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