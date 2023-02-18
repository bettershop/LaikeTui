<html>
<head>
  <title>list</title>
  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body>
<table border="1px">
  <tr>
    <td align="center" style="width: 50px">序</td>
    <td align="center" style="width: 150px">管理员账号</td>
    <td align="center">事件</td>
    <td align="center">操作时间</td>
  </tr>
  {foreach from=$list item=item name=f1}
    <tr>
      <td align="center" style="width: 50px">{$smarty.foreach.f1.iteration}</td>
      <td align="center" style="width: 100px">{$item->admin_name}</td>
      <td align="center" >{$item->event}</td>
      <td align="center">{$item->add_date}</td>
    </tr>
  {/foreach}
</table>
</body>
</html>