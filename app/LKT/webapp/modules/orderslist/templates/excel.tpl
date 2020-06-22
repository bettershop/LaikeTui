<html>

	<head>
		<title>订单列表</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	</head>
	<body>
		<table border="1px">
			<tr>
				<th colspan="25" style="height: 100px;font-size: 20px;">订单列表</th>
			</tr>
			<tr>
				<th colspan="25">导出时间:{$now_data}</th>
			</tr>

			<tr>
				<th style="width: 170px;">订单号</th>
				<th style="width: 170px;">创单时间</th>
				<th style="width:250px!important;" width="250px">产品名称</th>
				<th style="width: 170px;">规格</th>
				<th style="width: 75px;">数量</th>
				<th style="width: 75px;">小计</th>
				<th style="width: 75px;">订单总计</th>
				<th style="width: 75px;">订单状态</th>
				<th style="width: 75px;">订单类型</th>
				<th style="width: 75px;">用户ID</th>
				<th style="width: 75px;">联系人</th>
				<th>电话</th>
				<th style="width:250px!important;" width="250px">地址</th>
				<th style="width: 75px;">支付方式</th>
				<th style="width: 75px;">物流单号</th>
				<th style="width: 75px;">运费</th>
			</tr>
			{foreach from=$order item=item name=f1}
				{foreach from=$item->products item=item2 name=f2}
					<tr>
						<td style="mso-number-format:'\@';">
							{$item->sNo}
						</td>
						<td>
							<div style="text-align: center;">
								<span >{$item->add_time}</span>
							</div>
						</td>
						<td class="tbText" style="width:250px!important;text-align: left;" width="250px">
							<span style="text-align: left;">{$item2->product_title}</span>
						</td>
						<td  width="150px">
							<span style="text-align: left;">{$item2->size}</span>
						</td>
						<td class="tbText" style="width:250px!important;text-align: left;" width="250px">
							<span style="text-align: left;">{$item2->num}</span>
						</td>
						<td  width="150px" style="text-align: left;">{$item2->p_price}</td>
						<td style="text-align: left;">{$item2->p_priceee}</td>
						<td style="text-align: left;">
							<div>
								<span style="">{$item->status}</span>
							</div>
						</td>
						<td>
							<div>
								<span >{if $item->otype == 'pt'}拼团订单{else}普通订单{/if}</span>
							</div>
						</td>
						<td>
							<div  class="goods-name">
								<span >{$item->user_id}</span>
							</div>
						</td>
						<td>
							<div  class="goods-name">
								<span >{$item->user_name}</span>
							</div>
						</td>
						<td  width="150px" style="text-align: left;">{$item->mobile}</td>
						<td  width="150px" style="text-align: left;">{$item->address}</td>
						<td>
							<div>
								<span>
									{if $item->pay == 'wxPay'}微信支付
									{elseif $item->pay == 'wallet_Pay'}余额支付
									{elseif $item->pay == 'consumer_pay'}消费金支付
									{else}组合支付{/if}
								</span>
							</div>
						</td>
						<td  width="150px" style="text-align: left;">
							{if $item2->courier_num !='' }<span>{$item2->courier_num}({$item2->kuaidi_name})</span>
							{else}暂无{/if}
						</td>
						<td  width="150px" style="text-align: left;">{$item2->freight}</td>
					
					</tr>
				{/foreach}
			{/foreach}
		</table>
	</body>

</html>