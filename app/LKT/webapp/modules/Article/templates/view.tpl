
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>新闻分享列表</title>
</head>
<body>
<nav class="breadcrumb">
    系统管理 <span class="c-gray en">&gt;</span>
    <a href="index.php?module=Article">文章列表</a> <span class="c-gray en">&gt;</span>
    分享列表 <span class="c-gray en">&gt;</span>
    <a href="javascript:history.go(-1)">返回</a>
</nav>
<div class="pd-20">
    <div class="cl pd-5 bg-1 bk-gray mt-20"> 
        <h1 style="text-align: center;">{$Article_title}</h1> 
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20"> 
        <span class="l" bgColor="white">
        </span> 
        <span class="r">共有数据：<strong>{$total}</strong> 条</span> 
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>用户id</th>
                    <th>微信id</th>
                    <th>微信昵称</th>
                    <th>性别</th>
                    <th>发布时间</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{$item->user_id}</td>
                    <td>{$item->wx_id}</td>
                    <td>{$item->wx_name}</td>
                    <td>
                        {if $item->sex==0}未知{/if}
                        {if $item->sex==1}男{/if}
                        {if $item->sex==2}女{/if}
                    </td>
                    <td>{$item->share_add}</td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
</div>
{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}

{literal}
<script type="text/javascript">
$('.table-sort').dataTable({
    "aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
      {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
    ]
});
</script>
{/literal}
</body>
</html>