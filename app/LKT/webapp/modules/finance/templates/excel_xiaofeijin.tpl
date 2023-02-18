<html>
<head>
<title>list</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body> 
<table border="1px">
  <tr>
    <th style="width: 5%;height: 40px;">序</th>
    <!-- <th style="width: 5%;height: 40px;">用户ID</th> -->
    <th style="width: 15%;height: 40px;">用户名</th>
    <th style="width: 15%;height: 40px;">来源的用户</th>
    <th style="width: 15%;height: 40px;">手机号码</th>
    <th style="width: 10%;height: 40px;">会员等级</th>
    <th style="width: 5%;height: 40px;">充值金额</th>
    <th style="width: 15%;height: 40px;">充值时间</th>
    <th style="width: 10%;height: 40px;">充值方式</th>
  </tr>
  {foreach from=$list item=item name=f1}
  <tr class="text-c">
      <td>{$smarty.foreach.f1.iteration}</td>
      <td>{$item->user_id}</td>
      <td>{$item->user_name}</td>
      <td>{$item->name}</td>
       <td>{$item->mobile}</td>
       <td>{$item->typename}</td>
      <td>
        {if $item->type ==1 ||$item->type ==5}+{$item->money}{/if}
        {if $item->type ==2 ||$item->type ==4 ||$item->type ==6 ||$item->type ==7}-{$item->money}{/if}
      </td>
      <td>{$item->add_date}</td>
      <td>
        {if $item->type == 1 }转入（收入）消费金{/if}
        {if $item->type == 2 }提现{/if}
        {if $item->type == 4 }使用消费金{/if}
        {if $item->type == 5 }收入消费金{/if}
        {if $item->type == 6 }系统扣款{/if}
        {if $item->type == 7 }消费金解封{/if}
      </td>
  </tr>
{/foreach}
</table>
</body>
</html>