
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
<title>积分列表</title>
</head>

<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe627;</i> 财务管理 <span class="c-gray en">&gt;</span> 积分列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=finance&action=jifen';" title="关闭"><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
	<div class="mt-20">
		<table class="table table-border table-bordered table-bg table-hover">
			<thead>
				<tr class="text-c">
					<th width="150" aria-valuetext="user_id">用户ID</th>
					<th width="130" aria-valuetext="user_name">用户名</th>
					<th width="150" aria-valuetext="mobile">手机号码</th>
					<th width="150" aria-valuetext="typename">会员等级</th>
					<th width="150" aria-valuetext="sign_score">充值积分</th>
					<th width="130" aria-valuetext="source">来源</th>
					<th width="150" aria-valuetext="sign_time">时间</th>
					<th width="150" aria-valuetext="type">充值方式</th>
				</tr>
			</thead>
            <tbody>
	            {foreach from=$list item=item name=f1}
	                <tr class="text-c">
	                    <td>{$item->user_id}</td>
	                    <td>{$item->user_name}</td>
	         			<td>
	         				{$item->mobile}
	         			</td>
	                    <td>{$item->typename}</td>
	                    <td>
							{if $item->type ==0 ||$item->type ==2|| $item->type ==4 || $item->type ==6 || $item->type ==7}+{$item->sign_score}{/if}
							{if $item->type ==1 ||$item->type ==3 ||$item->type ==5}-{$item->sign_score}{/if}
	         			</td>
						<td>{if $item->source == 1}小程序{elseif $item->source == 2}app{/if}</td>
						<td>{$item->sign_time}</td>
	                    <td>
	                    	{if $item->type == 0 }签到领积分{/if}
	                    	{if $item->type == 1 }消费积分{/if}
	                    	{if $item->type == 2 }首次关注得积分{/if}
	                    	{if $item->type == 3 }转积分给好友{/if}
	                    	{if $item->type == 4 }好友转积分{/if}
							{if $item->type == 5 }系统扣除{/if}
							{if $item->type == 6 }系统充值{/if}
							{if $item->type == 7 }抽奖{/if}
	                    </td>
	                </tr>
	            {/foreach}
            </tbody>
        </table>
    </div>
	<div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

<script type="text/javascript" src="style/js/jquery.js"></script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="style/js/H-ui.js"></script>
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

</body>
</html>