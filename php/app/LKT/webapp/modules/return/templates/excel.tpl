<html>
<head>
<title>list</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
</head>
<body> 
<table border="1px">
  <tr>
    <td align="center" style="width: 50px">序号</td>
    <td align="center" style="width: 100px">用户ID</td>
    <td align="center" style="width: 400px">产品名称</td>
    <td align="center" style="width: 100px">产品价格</td>
    <td align="center" style="width: 100px">数量</td>
    <td align="center" style="width: 200px">订单号</td>
    <td align="center" style="width: 200px">发布时间</td>
    <td align="center" style="width: 100px">状态</td>
  </tr>
  {foreach from=$list item=item name=f1}
  <tr>
    <td align="center" style="width: 50px">{$smarty.foreach.f1.iteration}</td>
    <td align="center" style="width: 100px;">{$item->user_id}</td>
    <td align="center" style="width: 400px">{$item->p_name}</td>
    <td align="center" style="width: 100px">{$item->p_price}</td>
    <td align="center" style="width: 100px">{$item->num}{$item->unit}</td>
    <td align="center" style="width: 200px">`{$item->r_sNo}</td> 
    <td align="center" style="width: 200px">{$item->add_time}</td>    
    <td align="center" style="width: 100px">{if $item->r_type == 0}<span style="color: red;">审核中</span>{elseif $item->r_type == 1}<span >审核通过</span>{else}<span>已拒绝</span>{/if}</td>
  </tr>
  {/foreach}
</table>
</body>
</html>