
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

<title>优惠券列表</title>
</head>
<body>

<nav class="breadcrumb">
    插件管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=coupon&action=coupon">优惠券列表</a> <span class="c-gray en">&gt;</span> 
    查看用户优惠券列表 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20">
    <div class="text-c"> 

    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>用户ID</th>
                    <th>活动名称</th>
                    <th>优惠券金额</th>
                    <th>领取时间</th>
                    <th>到期时间</th>
                    <th>状态</th>
                </tr>
            </thead>
            <tbody>
                {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{$item->user_id}</td>
                    <td>{$item->name}</td>
                    <td>{$item->money}</td>
                    <td>{$item->add_time}</td>
                    <td>{$item->expiry_time}</td>
                    <td>{if $item->status == 0}<span style="color:#30c02d;">正常</span>{elseif $item->status == 1}<span>使用中</span>{elseif $item->status == 2}<span style="color:#ff2a1f">已使用</span>{elseif $item->status == 3}<span style="color: #7A7A7A;">已过期</span>{/if}</td>
                </tr>
                {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>


{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}


</body>
</html>