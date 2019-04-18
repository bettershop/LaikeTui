<html>
<head>
<title>list</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body> 
<table border="1px">
  <tr>
    <td align="center">账户名</td>
    <td align="center">提交时间</td>
    <td align="center">提现金额</td>
    <td align="center">提现手续费</td>
    <td align="center">银行名称</td>
    <td align="center">持卡人姓名</td>
    <td align="center">卡号</td>
    <td align="center">联系电话</td>
    <td align="center">状态</td>
  </tr>
  {foreach from=$list item=item name=f1}
  <tr>
    <td align="center">{$item->name}</td>
    <td>{$item->add_date}</td>
    <td>{$item->money}元</td>
    <td>{$item->s_charge}元</td>
    <td>{$item->Bank_name}</td>
    <td>{$item->Cardholder}</td>
    <td>`{$item->Bank_card_number}</td>
    <td>{$item->mobile}</td>
    <td>{if $item->status == 0}未审核{elseif $item->status == 1}审核通过{else}已拒绝{/if}</td>
  </tr>
  {/foreach}
</table>
</body>
</html>