
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
{literal}
<script type="text/javascript">
function check(f){
    if(f.bargain_price.value.trim() == "" ){
        alert('砍价初始值不能为空！');
        return false;
    }
    if(f.bargain_price.value.trim() * 100 <= f.price.value.trim() * 100 ){
       alert('砍价初始值要大于商品本身价值！');
       return false;
    }
    var can_num = f.can_num.value.trim();
    var bargain_price = f.bargain_price.value.trim();
    var parameter = f.parameter.value.trim();
    for (var i = 0; i < can_num; i++) {
        // 砍价之前的价格=最小整数(砍价之前的价格-砍价之前的价格*参数)
        bargain_price = Math.floor((bargain_price - bargain_price * parameter) * 100) / 100;
        // 当砍价之前的价格低于参数并且还有砍价次数,提醒卖家
        if(bargain_price < parameter && i != can_num){
            if (confirm('用户会在第'+i+'次时,砍价成功!')==true){ 
                return true;
            }else{
                parent.location.reload();
                return false;
            }
        }
        // 当(砍价之前的价格)大于(参数)并且(砍价之前的价格)小于(2倍参数)并且(还有砍价次数),提醒卖家
        if(bargain_price > parameter && bargain_price < parameter + parameter && i+1 == can_num){
            if (confirm('用户会在第'+can_num+'次时,砍价成功!')==true){ 
                return true;
            }else{
                parent.location.reload();
                return false;
            }
        }
    }
}
</script>
{/literal}
<title>设置初始值</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 产品管理 <span class="c-gray en">&gt;</span> 产品列表管理 <span class="c-gray en">&gt;</span> 设置初始值 </a></nav>
<div class="page-container">
    <form name="form1" action="index.php?module=product&action=bargain" class="form form-horizontal" method="post"   enctype="multipart/form-data" onsubmit="return check(this);">
        <input type="hidden" name="id" id="id" value="{$id}" >
        <input type="hidden" name="sx_id" id="sx_id"  value="{$sx_id}" >
        <input type="hidden" name="price" id="price"  value="{$price}" >
        <input type="hidden" name="can_num" id="can_num"  value="{$can_num}" >
        <input type="hidden" name="parameter" id="parameter"  value="{$parameter}" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <h4 style="text-align: center;">{$product_title}</h4>
            </div>
        </div>
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>砍价初始值：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="text" name="bargain_price" id='bargain_price' value="{$bargain_price}" class="input-text">
                    <text>商品从什么价位开始砍起</text>
                </div>
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