{include file="../../include_path/header.tpl" sitename="DIY头部"}
<script type="text/javascript" src="style/js/laydate/laydate.js"></script> <!-- 时间插件-->
<body>

<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=stock">库存管理</a> <span class="c-gray en">&gt;</span> 
    查看预警记录 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20 page_absolute">
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c tab_tr">
                    <th class="tab_num">序号</th>
                    <th class="tab_title">商品名称</th>
                    <th >商品售价</th>
                    <th>商品规格</th>
                    <th>商品状态</th>
                    <th >商品总库存</th>
                    <th >剩余库存</th>
                    <th class="tab_time">预警时间</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c tab_td">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{$item->product_title}</td>
                    <td>{$item->price}</td>
                    <td>{$item->specifications}</td>
                    <td>
                        <div class="tab_block">
                            {if $item->status == 0}
                                <span style="background-color: #5eb95e;" class="badge statu badge-success">已上架</span>
                            {elseif $item->status == 1}
                                <span class="badge statu badge-default">已下架</span>
                            {elseif $item->status == 2}
                                <span class="badge statu badge-default">待上架</span>
                            {/if}
                        </div>
                    </td>
                    <td>{$item->total_num}</td>
                    <td>{$item->num}</td>
                    <td>{$item->add_date}</td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
   <div class="tb-tab" style="text-align: center;justify-content: center;position: sticky;bottom: 0;background-color: #fff;width: 100%;z-index: 20;">{$pages_show}</div>
    <div class="page_h20"></div>
</div>


{literal}
<script type="text/javascript">

var aa=$(".pd-20").height()-56;
var bb=$(".table-border").height();
if(aa<bb){
	$(".page_h20").css("display","block")
}else{
	$(".page_h20").css("display","none")
}
laydate.render({
    elem: '#startdate', //指定元素
    type: 'datetime'
});
laydate.render({
    elem: '#enddate',
    type: 'datetime'
});
</script>
{/literal}
</body>
</html>