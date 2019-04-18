
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

<title>活动列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe6ca;</i> 砍价管理 <span class="c-gray en">&gt;</span> 砍价列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="product" />
            <select name="cid" class="select" style="width: 80px;height: 31px;vertical-align: middle;">
                <option value="" >全部</option>
                {$class}
            </select>
            <input type="text" name="product_title" size='8' value="{$product_title}" id="" placeholder=" 产品标题" style="width:200px" class="input-text">
            <input name="" id="" class="btn btn-success" type="submit" value="查询">
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>产品图片</th>
                    <th>产品标题</th>
                    <th>分类名称</th>
                    <th>排序号</th>
                    <th>发布时间</th>
                    <th>产品品牌</th>
                    <th>砍价价格</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{if $item->img != ''}<img onclick="pimg(this)" style="width: 50px;height: 50px;" src="../LKT/images/{$item->img}">{else}<span>暂无图片</span>{/if}</td>
                    <td>{$item->product_title}　[{$item->name}-{$item->color}-{$item->size}]</td>
                    <td>{$item->pname}</td>
                    <td>{$item->sort}</td>
                    <td>{$item->add_date}</td>
                    <td>{if $item->brand_name != ''}{$item->brand_name}{else}无{/if}</td>
                    <td><span style="color:#ff2a1f;">{$item->bargain_price}</span></td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" onclick="admin_add('编辑','index.php?module=product&action=bargain&id={$item->id}&sx_id={$item->sx_id}&status={$item->status}&product_title={$item->product_title}&bargain_price={$item->bargain_price}','','510')"><i class="Hui-iconfont">&#xe6df;</i></a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=product&action=bargain&id={$item->id}&sx_id={$item->sx_id}&status={$item->status}" title="关闭砍价" onclick="return confirm('确定要关闭商品砍价功能吗?')"><i class="Hui-iconfont">&#xe677;</i></a>
                    </td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="style/js/jquery.js"></script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

{literal}
<script type="text/javascript">
function admin_add(title,url,w,h){
    layer_show(title,url,w,h);
}
$('.table-sort').dataTable({
    "aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
      //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
      {"orderable":false,"aTargets":[0,6]}// 制定列不参与排序
    ]
});
</script>
{/literal}
</body>
</html>