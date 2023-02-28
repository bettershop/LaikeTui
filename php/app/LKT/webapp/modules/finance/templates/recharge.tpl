
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />

{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}

	<title>财务管理</title>
</head>
<body>

<nav class="breadcrumb">
    财务管理 <span class="c-gray en">&gt;</span> 
    充值列表 
</nav>


<div class="pd-20">
	<div class="text-c">
		<form name="form1" action="index.php?module=finance&action=recharge" method="get">
			<input type="hidden" name="module" value="finance" />
			<input type="hidden" name="action" value="recharge" />
			<input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />
			<input type="text" class="input-text" style="width:250px" placeholder="用户ID" autocomplete="off" name="zhanghao" value="{$zhanghao}">
			<input type="text" class="input-text" style="width:250px" placeholder="手机号" autocomplete="off" name="mobile" value="{$mobile}">
			<input type="text" class="input-text" style="width:250px" placeholder="昵称" autocomplete="off" name="user_name" value="{$user_name}">
			<input type="submit" class="btn btn-success" value="查 询">
			<input type="button" value="导出" class="btn btn-success" onclick="excel('all')">
		</form>
	</div>

	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover">
			<thead>
			<tr class="text-c">
				<th width="150">用户ID</th>
				<th width="150">用户昵称</th>
				<th width="130">操作金额</th>
				<th width="50">来源</th>
				<th width="150">添加时间</th>
				<th width="100">类型</th>
			</tr>
			</thead>
			<tbody>
			{foreach from=$list item=item name=f1}
				<tr class="text-c">
					<td>{$item->user_id}</td>
					<td>{$item->user_name}</td>
					<td>{$item->money}</td>
					<td>{if $item->source == 1}小程序{elseif $item->source == 2}app{/if}</td>
					<td>{$item->add_date}</td>
					<td>
						{if $item->type == 1}充值{/if}
						{if $item->type == 14}系统充值{/if}
					</td>

				</tr>
			{/foreach}
			</tbody>
		</table>
	</div>
	<div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}


{literal}
<script type="text/javascript">
function excel(pageto) {
	var pagesize = $("#pagesize").val();
	location.href=location.href+'&pageto='+pageto+'&pagesize='+pagesize;
}
</script>
{/literal}
</body>
</html>


