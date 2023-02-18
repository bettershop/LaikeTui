<html>
<head>
<title>list</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body> 
<table border="1px">
  <tr>
    <th style="width: 5%;height: 40px;">用户ID</th>
    <th style="width: 20%;height: 40px;">用户名</th>
    <th style="width: 20%;height: 40px;">会员等级</th>
    <th style="width: 10%;height: 40px;">金额</th>
    <th style="width: 15%;height: 40px;">时间</th>
    <th style="width: 20%;height: 40px;">类型</th>
</tr>
   {foreach from=$list item=item name=f1}
    <tr class="text-c">
        <td>{$item->user_id}</td>
        <td>{$item->user_name}</td>
        <td>{$item->typename}</td>
        <td>
            {if $item->type == 1 ||$item->type == 5 || $item->type == 13 || $item->type == 14 || $item->type == 19 || $item->type == 20 || $item->type == 22 || $item->type == 23}+{$item->money}{/if}
            {if $item->type == 2 ||$item->type == 4 || $item->type == 11 || $item->type == 12 || $item->type == 21}-{$item->money}{/if}
        </td>
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
    </tr>
{/foreach}
</table>
</body>
</html>