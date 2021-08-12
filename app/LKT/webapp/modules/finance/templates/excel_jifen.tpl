<html>
<head>
<title>list</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body> 
<table border="1px">
  <tr>
    <th style="width: 5%;height: 40px;">用户ID</th>
    <th style="width: 15%;height: 40px;">用户名</th>
    <th style="width: 20%;height: 40px;">手机号码</th>
    <th style="width: 10%;height: 40px;">会员等级</th>
    <th style="width: 5%;height: 40px;">充值积分</th>
    <th style="width: 15%;height: 40px;">时间</th>
    <th style="width: 10%;height: 40px;">充值方式</th>
  </tr>
  {foreach from=$list item=item name=f1}
  <tr class="text-c">
      <td>{$item->user_id}</td>
      <td>{$item->user_name}</td>
      <td>{$item->mobile}</td> 
      <td>{$item->typename}</td>
      <td>
          {if $item->type ==0 ||$item->type ==2|| $item->type ==4 || $item->type ==6 || $item->type ==7}+{$item->sign_score}{/if}
          {if $item->type ==1 ||$item->type ==3 ||$item->type ==5}-{$item->sign_score}{/if}
      </td>
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
</table>
</body>
</html>