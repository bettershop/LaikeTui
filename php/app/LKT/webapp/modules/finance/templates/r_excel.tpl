<html>
<head>
<title>list</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body> 
<table border="1px">
  <tr>
    <td align="center">id</td>
    <td align="center">用户id</td>
    <td align="center">用户昵称</td>
    <td align="center">操作金额</td>
    <td align="center">添加时间</td>
    <td align="center">状态</td>
  </tr>
      {foreach from=$list item=item name=f1}
        <tr>
          <td>{$item->id}</td>
          <td>{$item->user_id}</td>
          <td>{$item->user_name}</td>
          <td>{$item->money}</td>
          <td>{$item->add_date}</td>
          <td>
            {if $item->type == 1}充值{/if}
            {if $item->type == 14}系统充值{/if}
          </td>

        </tr>
      {/foreach}
</table>
</body>
</html>