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

<title>财务管理</title>
{literal}
<style>
td a{
	width: 44%;
	margin: 0 auto!important;
}
.dataTables_wrapper .dataTables_length{
	bottom: 13px;
}
#btn1:hover{
	background-color: #2481e5!important;
}
#btn1{
	height: 36px;
	line-height: 36px;
}
#btn2:hover{
	background-color: #0fa675!important;
}
#btn2{
	background-color: #17ba8e!important;
	height: 36px;
	line-height: 36px;
}
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe63a;</i> 财务管理 <span class="c-gray en">&gt;</span> 余额列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div class="text-c">
		<form name="form1" action="index.php?module=finance&action=yue" method="get">
			<input type="hidden" name="module" value="finance" />
			<input type="hidden" name="action" value="yue" />
			<input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />
			<div>
				<select name="otype" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
					<option value="all" {if $type =='all' } selected{/if}>全部</option>
					<option value="1" {if $type =='1' } selected{/if}>用户充值</option>
					<option value="2" {if $type =='2' }selected {/if}>申请提现</option>
					<option value="4" {if $type =='4' } selected{/if}>余额消费</option>
					<option value="5" {if $type =='5' } selected{/if}>退款</option>
					<option value="11" {if $type =='11' } selected{/if}>系统扣款</option>
					<option value="12" {if $type =='12' } selected{/if}>给好友转余额</option>
					<option value="13" {if $type =='13'} selected{/if}>转入余额</option>
					<option value="14" {if $type =='14'} selected{/if}>系统充值</option>
					<option value="19" {if $type =='19'} selected{/if}>消费金解封</option>
					<option value="20" {if $type =='20'} selected{/if}>抽奖中奖</option>
					<option value="21" {if $type =='21'} selected{/if}>提现成功</option>
					<option value="22" {if $type =='22'} selected{/if}>提现失败</option>
					<option value="23" {if $type =='23'} selected{/if}>取消订单</option>
				</select>
				<input type="text" class="input-text" style="width:150px" placeholder="用户ID" name="name" value="{$name}">

				<div style="position: relative;display: inline-block;">
				<input name="startdate" value="{$startdate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 25px;top: 7px;" onclick="new Calendar().show(document.form1.startdate);" />~
				</div>
				<div style="position: relative;display: inline-block;">
				<input  name="enddate" value="{$enddate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 10px;top: 7px;" onclick="new Calendar().show(document.form1.enddate);" />
				</div>
				<input type="submit" class="btn btn-success" id="btn1" value="查 询">
				&nbsp;
				<input type="button" value="导出" id="btn2" class="btn btn-success" onclick="excel('all')">
			</div>
		</form>
    </div>

	<div class="mt-20" style="background: #fff;">
		<table class="table table-border table-bordered table-bg table-hover">
			<thead>
				<tr class="text-c">
					<th width="150" aria-valuetext="user_id">用户ID</th>
					<th width="130" aria-valuetext="user_name">用户名</th>
					<th width="150" aria-valuetext="typename">会员等级</th>
					<th width="150" aria-valuetext="money">金额</th>
					<th width="130" aria-valuetext="source">来源</th>
					<th width="150" aria-valuetext="add_date">时间</th>
					<th width="150" aria-valuetext="type">类型</th>
		            <th width="100">操作</th>
		        </tr>
            </thead>
            <tbody>
	            {foreach from=$list item=item name=f1}
	                <tr class="text-c">
	                    <td>{$item->user_id}</td>
	                    <td>{$item->user_name}</td>
	                    <td>{$item->typename}</td>
						<td>
							{if $item->type == 1 ||$item->type == 5 || $item->type == 13 || $item->type == 14 || $item->type == 19 || $item->type == 20 || $item->type == 22 || $item->type == 23}+{$item->money}{/if}
							{if $item->type == 2 ||$item->type == 4 || $item->type == 11 || $item->type == 12 || $item->type == 21}-{$item->money}{/if}
						</td>
						<td>{if $item->source == 1}小程序{elseif $item->source == 2}app{/if}</td>
						<td>{$item->add_date}</td>
						<td>
							{if $item->type == 1 }用户充值{/if}
							{if $item->type == 2 }申请提现{/if}
							{if $item->type == 4 }余额消费{/if}
							{if $item->type == 5 }退款{/if}
							{*{if $item->type == 6 }红包提现到余额{/if}*}
							{if $item->type == 11 }系统扣款{/if}
							{if $item->type == 12 }给好友转余额{/if}
							{if $item->type == 13 }转入余额{/if}
							{if $item->type == 14 }系统充值{/if}
							{if $item->type == 19 }消费金解封{/if}
							{if $item->type == 20 }抽奖中奖{/if}
							{if $item->type == 21 }提现成功{/if}
							{if $item->type == 22 }提现失败{/if}
							{if $item->type == 23 }取消订单{/if}
						</td>
	                    <td style="min-width:120px;">
							<a style="text-decoration:none" class="ml-5" href="index.php?module=finance&action=yue_see&user_id={$item->user_id}" title="查看">
								<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;">
	                                <img src="images/icon1/ck.png"/>&nbsp;查看
	                            	</div>
                        		</div>
							</a>
	                    </td>
	                </tr>
	            {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type='text/javascript' src='modpub/js/calendar.js'></script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

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


