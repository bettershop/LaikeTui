<html>
<head>
    <title>list</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
</head>
<body>
<table border="1px">
    <tr>
        <th width="30">序号</th>
        <th width="100">商品名称</th>
        <th width="70">商品售价</th>
        <th>商品规格</th>
        <th width="70">商品状态</th>
        <th width="70">商品总库存</th>
        <th width="70">出库数量</th>
        <th>入库时间</th>
    </tr>
    {foreach from=$excel item=item name=f1}
        <tr>
            <td>{$smarty.foreach.f1.iteration}</td>
            <td>{$item->product_title}</td>
            <td>{$item->price}</td>
            <td>{$item->specifications}</td>
            <td><div class="tab_block">
                            {if $item->status == 0}
                                <span style="background-color: #5eb95e;" class="badge statu badge-success">已上架</span>
                            {elseif $item->status == 1}
                                <span class="badge statu badge-default">已下架</span>
                            {elseif $item->status == 2}
                                <span class="badge statu badge-default">待上架</span>
                            {/if}
                        </div></td>
            <td>{$item->total_num}</td>
            <td style="color:red;">{$item->flowing_num}</td>
            <td>{$item->add_date}</td>
        </tr>
    {/foreach}
</table>
</body>
</html>