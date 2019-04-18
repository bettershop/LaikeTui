
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

<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe63a;</i> 财务管理 <span class="c-gray en">&gt;</span> 提现列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="#" onclick="location.href='index.php?module=finance';" title="关闭"><i class="Hui-iconfont">&#xe6a6;</i></a></nav>
<div class="pd-20">
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                	<th width="40">序</th>
		            <th width="150" aria-valuetext="name">用户名</th>
					<th width="130" aria-valuetext="source">来源</th>
					<th width="130" aria-valuetext="add_date">提交时间</th>
		            <th width="150" aria-valuetext="money">提现金额</th>
		            <th width="150" aria-valuetext="s_charge">提现手续费</th>
		            <th width="150" aria-valuetext="Bank_name">银行名称</th>
		            <th width="150" aria-valuetext="Cardholder">持卡人姓名</th>
		            <th width="150" aria-valuetext="Bank_card_number">卡号</th>
		            <th width="150" aria-valuetext="mobile">联系电话</th>
		            <th width="100" aria-valuetext="name">状态</th>
				</tr>
            </thead>
            <tbody>
	            {foreach from=$list item=item name=f1}
	                <tr class="text-c">
	                    <td>{$smarty.foreach.f1.iteration}</td>
	                    <td>{$item->name}</td>
						<td>{if $item->source == 1}小程序{elseif $item->source == 2}app{/if}</td>
						<td>{$item->add_date}</td>
	                    <td>{$item->money}元</td>
	                    <td>{$item->s_charge}元</td>
	                    <td>{$item->Bank_name}</td>
	                    <td>{$item->Cardholder}</td>
	                    <td>{$item->Bank_card_number}</td>
	                    <td>{$item->mobile}</td>
	                    <td>{if $item->status == 0}<span style="color: #ff2a1f;">未审核</span>{elseif $item->status == 1}<span style="color: #30c02d;">审核通过</span>{else}<span style="color: #7A7A7A;">已拒绝</span>{/if}</td>
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


