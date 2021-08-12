
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

<title>新闻分类管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 新闻管理 <span class="c-gray en">&gt;</span> 新闻列表管理 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="newslist" />
            <select name="cat_id" class="select" style="width: 80px;height: 31px;vertical-align: middle;">
                <option value="" >全部</option>
                {foreach from=$class item=item name=f1}
                    <option value="{$item->cat_id}">{$item->cat_name}</option>
                {/foreach}
            </select>
            <input type="text" name="news_title" size='8' value="{$news_title}" id="" placeholder=" 新闻标题" style="width:200px" class="input-text">
            <input name="" id="" class="btn btn-success" type="submit" value="查询">
        </form>
    </div>
    <div style="clear:both;">
        <input type="button" class="btn btn-primary radius" value="发布新闻" onclick="location.href='index.php?module=newslist&action=add';" />
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>分类名称</th>
                    <th>新闻标题</th>
                    <th>排序号</th>
                    <th>发布时间</th>
                    <th>分享次数</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{$item->cat_name}</td>
                    <td>{$item->news_title}</td>
                    <td>{$item->sort}</td>
                    <td>{$item->add_date}</td>
                    <td>{$item->share_num}</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=newslist&action=view&id={$item->id}" title="查看分享列表" ><i class="Hui-iconfont">&#xe6ab;</i></a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=newslist&action=amount&id={$item->id}" title="分享红包设置" ><i class="Hui-iconfont" style="color: red;">&#xe6b7;</i></a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=newslist&action=modify&id={$item->id}&uploadImg={$uploadImg}" title="修改" ><i class="Hui-iconfont">&#xe6df;</i></a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=newslist&action=del&id={$item->id}&uploadImg={$uploadImg}" onclick="return confirm('确定要删除此新闻吗?')"><i class="Hui-iconfont">&#xe6e2;</i></a>
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