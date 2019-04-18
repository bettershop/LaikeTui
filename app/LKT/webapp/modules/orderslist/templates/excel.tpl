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
				<th style="width: 150px;text-align: center;">下单时间</th>
				<th style="width: 150px;text-align: center;">订单号</th>
				<th style="width: 150px;text-align: center;">用户名</th>

				<th style="width: 150px;text-align: center;;">商品名称</th>
                <th style="width: 150px;text-align: center;;">商品编号</th>
                <th style="width: 150px;text-align: center;;">规格</th>
				<th style="width: 150px;text-align: center;;">数量</th>
				<th style="width: 150px;text-align: center;;">小计</th>

				<th style="width: 150px;text-align: center;">总金额</th>
				<th style="width: 150px;text-align: center;">余额支付</th>
				<th style="width: 150px;text-align: center;">微信支付</th>
				<th style="width: 150px;text-align: center;">消费支付</th>
				<th style="width: 150px;text-align: center;">运费</th>
				<th style="width: 150px;text-align: center;">佣金</th>
				<th style="width: 150px;text-align: center;">实付金额</th>
				<th style="width: 150px;text-align: center;">订单状态</th>
				<th style="width: 150px;text-align: center;">支付方式</th>
				<th style="width: 150px;text-align: center;">发货状态</th>
				<th style="width: 150px;text-align: center;">发货方式</th>
				<th style="width: 150px;text-align: center;">订单类型</th>
				<th style="width: 150px;text-align: center;">收货人</th>
				<th style="width: 150px;text-align: center;">电话</th>
				<th style="width: 150px;text-align: center;;">地址</th>
				<th style="width: 100px;text-align: center;">快递名称</th>
				<th style="width: 150px;text-align: center;;">快递单号</th>
			</tr>
{foreach from=$order item=item name=f1}
			{foreach from=$item->products item=item2 name=f2}
			<tr>
				<td style="width: 150px;text-align: center;">{$item->add_time}</td>
				<td style="width: 150px;text-align: center;">`{$item->sNo}</td>
				<td style="width: 150px;text-align: center;">{$item->user_name}</td>
				<td style="height:50px;text-align: center;">{$item2->product_title}</td>
                <td style="height:50px;text-align: center;">{$item2->product_number}</td>
                <td style="text-align: center;">{$item2->size}</td>
				<td style="text-align: center;">{$item2->num}{$item2->unit}</td>
				<td style="text-align: center;">{$item2->num*$item2->p_price}元</td>
				<td style="width: 150px;text-align: center;">{$item->spz_price}元（含运费）</td>
				<td style="width: 150px;text-align: center;">{$item->balance_pay}</td>
				<td style="width: 150px;text-align: center;">{$item->weixin_pay}</td>
				<td style="width: 150px;text-align: center;">{$item->consumer_money}</td>
				<td style="width: 150px;text-align: center;">0.00元</td>
				<td style="width: 150px;text-align: center;">{$item->yongjin}</td>
				<td style="width: 150px;text-align: center;">{$item->z_price}</td>
				<td style="width: 150px;text-align: center;">{$item->status}</td>
				<td style="width: 150px;text-align: center;">{if $item->pay == 'wxPay'}微信支付{elseif $item->pay == 'wallet_Pay'}余额支付{else}组合支付{/if}</td>
				<td style="width: 150px;text-align: center;">快递发送</td>
				<td style="width: 150px;text-align: center;">{if $item->statu > 1}已发货{else}未发货{/if}</td>
				<td style="width: 150px;text-align: center;">{if $item->otype == 'pt'}拼团订单{else}{if $item->drawid>0}抽奖订单{else}普通订单{/if}{/if}</td>
				<td style="width: 150px;text-align: center;">{$item->name}</td>
				<td style="width: 150px;text-align: center;">{$item->mobile}</td>
				<td style="width: 150px;text-align: center;">{$item->address}</td>
				<td style="width: 150px;text-align: center;">{$item->kuaidi_name}</td>
				<td style="width: 150px;text-align: center;">`{$item->products[0]->courier_num}</td>
			</tr>			
			{/foreach}	
{/foreach}			
		</table>
	</body>

</html>