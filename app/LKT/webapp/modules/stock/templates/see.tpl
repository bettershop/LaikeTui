{include file="../../include_path/header.tpl" sitename="DIY头部"}
<body>
   

<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=stock">库存管理</a> <span class="c-gray en">&gt;</span> 
    库存详情 <span class="c-gray en">&gt;</span> 
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
                    <th >入库/出库状态</th>
                    <th >入库/出库数量</th>
                    <th class="tab_time">入库/出库时间</th>
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
                     {if $item->type == 0}
                        <td>入库</td>
                        
                    {else}
                        <td>出库</td>
              
                    {/if}
                    {if $item->type != 2}
                        <td {if $item->type == 0}style="color: #0abf0a;"{else}style="color:red;"{/if}>{$item->flowing_num}</td>
                        
                    {else}
                        <td>{$item->flowing_num}</td>
              
                    {/if}
                    <td class="tab_time">{$item->add_date}</td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
    <div class="page_h20"></div>
</div>
{include file="../../include_path/footer.tpl"}
{literal}
<script type="text/javascript">

var aa=$(".pd-20").height()-56;
var bb=$(".table-border").height();
if(aa<bb){
	$(".page_h20").css("display","block")
}else{
	$(".page_h20").css("display","none")
}
</script>
{/literal}
</body>
</html>