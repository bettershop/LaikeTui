
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>产品参数</title>
</head>
<body>
<nav class="breadcrumb">
    配置管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=product_config">库存设置</a>
</nav>

<div class="page-container">
    <form name="form1" action="index.php?module=product_config" class="form form-horizontal" method="post" enctype="multipart/form-data" >
        <div id="tab-system" class="HuiTab">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-4"><span class="c-red">*</span>最低库存：</label>
                <div class="formControls col-xs-8 col-sm-4">
                    <input type="number" name="config[min_inventory]" value="{$config->min_inventory}" id="min_inventory" class="input-text" style="width:140px">
                </div>
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
{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script type="text/javascript">
$('.table-sort').dataTable({
    "aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
      //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
      {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
    ]
});
$("#min_inventory").blur(function(){
    var m = $("#min_inventory").val();
    if(m < 0){
        alert("最低库存不能为负数！");
        $("#min_inventory").val('');
        return false;
    }
    if(m%1 != 0){
        alert("最低库存不能为小数！");
        $("#min_inventory").val('');
        return false;
    }
});
</script>
{/literal}
</body>
</html>