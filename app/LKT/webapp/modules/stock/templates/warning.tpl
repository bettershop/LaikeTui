{include file="../../include_path/header.tpl" sitename="DIY头部"}

<title>库存预警</title>
{literal}
<style>
td a{
    width: 29%;
    float: left;
    margin: 2%!important;
}
.btn1{
    width: 80px;
    height: 40px;
    line-height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    float: left;
    color: #6a7076;
    background-color: #fff;
}
.btn1:hover{
    text-decoration: none;
}
.swivch a:hover{
    text-decoration: none;
    background-color: #2481e5!important;
    color: #fff!important;
}
</style>
<script type="text/javascript" src="style/js/laydate/laydate.js"></script> <!-- 时间插件-->

{/literal}
</head>
<body>

<nav class="breadcrumb">
    商品管理 <span class="c-gray en">&gt;</span> 
    <a href="index.php?module=stock">库存管理</a> <span class="c-gray en">&gt;</span> 
    库存预警 <span class="c-gray en">&gt;</span> 
    <a href="javascript:history.go(-1)">返回</a>
</nav>


<div class="pd-20 page_absolute" style="height: 100%">

    <div class="swivch page_bgcolor swivch_bot">
            <a href="index.php?module=stock" class="btn1">库存列表</a>
            <a href="index.php?module=stock&action=warning" class="btn1 swivch_active" style="border-right: 1px solid #ddd!important;">库存预警</a>
            <a href="index.php?module=stock&action=enter" class="btn1 " >入货详情</a>
            <a href="index.php?module=stock&action=shipment" class="btn1 " >出货详情</a>
        <div class="clearfix" style="margin-top: 0px;"></div>
    </div>
    <div class="page_h16"></div>
    <div class="text-c text_c">
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="stock" />
            <input type="hidden" name="action" value="warning" />
            <input type="text" name="name" size='8' value="{$name}" id="name" placeholder="请输入商品名称" style="width:200px" class="input-text">
            <input type="text" class="input-text seach_bottom" value="{$startdate}" placeholder="请输入开始时间" id="startdate" name="startdate" style="width:150px;">

            <span class="select seach_bottom" style="border: 0;vertical-align: middle;">至</span>
            <input type="text" class="input-text seach_bottom" value="{$enddate}" placeholder="请输入结束时间" id="enddate" name="enddate" style="width:150px;">
            <input type="button" value="重置" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076; height: 31px;border-radius: 5px;" class="reset" onclick="empty()" />

            <input name="" id="btn9" class="btn " type="submit" value="查询">
            <input id="btn1" class="btn btn-success" type="button" value="导出" onclick="excel(location.href)" style="float: right;">
        </form>
    </div>
    <div class="page_h16"></div>
    <div class="" style="height: 960px !important;">
        <table class="table-border tab_content">
            <thead>
                <tr class="text-c tab_tr">
                    <th class="tab_num">序号</th>
                    <th class="tab_title">商品名称</th>
                    <th >商品售价</th>
                    <th>商品规格</th>
                    <th>商品状态</th>
                    <th >商品总库存</th>
                    <th >剩余库存</th>
                    <th class="tab_time">供货时间</th>
                    <th class="tab_dat">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c tab_td">
                    <td class="tab_num">{$smarty.foreach.f1.iteration}</td>
                    <td class="tab_title">{$item->product_title}</td>
                    <td>{$item->price}</td>
                    <td>{$item->specifications}</td>
                    <td>
                        <div class="tab_block">
                            {if $item->status == 2}
                                <span class="badge statu badge-default">待上架</span>
                            {elseif $item->status == 1}
                                <span class="badge statu badge-default">已下架</span>
                            {elseif $item->status == 0}
                                <span style="background-color: #5eb95e;" class="badge statu badge-success">已上架</span>
                            {/if}
                        </div>
                    </td>
                    <td>{$item->total_num}</td>
                    <td>{$item->num}</td>
                    <td class="tab_time">{$item->add_date}</td>
                    <td class="tab_dat">
                            <a style="text-decoration:none;width: 100px;" class="ml-5" href="index.php?module=stock&action=add&id={$item->id}&pid={$item->pid}" title="增加库存" >
                                <img src="images/icon1/add_g.png"/>&nbsp;增加库存
                            </a>
                            <a style="text-decoration:none;width: 100px;" class="ml-5" href="index.php?module=stock&action=seewarning&id={$item->id}&pid={$item->pid}" title="查看预警记录" >
                                <img src="images/icon1/ck.png"/>&nbsp;查看预警记录
                            </a>
                    </td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
   <div class="tb-tab" style="text-align: center;justify-content: center;position: sticky;bottom: 0;background-color: #fff;width: 100%;z-index: 20;">{$pages_show}</div>
</div>

{literal}
<script type="text/javascript">
$(function(){
	// 根据框架可视高度,减去现有元素高度,得出表格高度
	var Vheight = $(window).height()-56-56-46-16-16-($('.tb-tab').text()?70:0)
	$('.table-scroll').css('height',Vheight+'px')
});
laydate.render({
    elem: '#startdate', //指定元素
    type: 'datetime'
});
laydate.render({
    elem: '#enddate',
    type: 'datetime'
});
function empty() {
    $("#name").val('');
    $("#startdate").val('');
    $("#enddate").val('');
}
function excel(url) {
    export_popup(url);
}
function export_popup(url) {
        var res = `<div class="pup_div" id="pup_div">
                        <div class="pup_flex">
                            <div class="pup_auto">
                                <div class="pup_head"><span>导出数据</span>
                                    <img src="images/icon/cha.png" onclick="export_close('${url}','')">
                                </div>
                                
                                <div class="pup_dc">
                                    <div class="pup_dcv" onclick="export_close('${url}','This_page')">
                                        <div>
                                            <img src="images/iIcon/scby.png" />
                                            <p>导出本页</p>
                                        </div>
                                    </div>
                                    <div class="pup_dcv" onclick="export_close('${url}','whole')">
                                        <div>
                                            <img src="images/iIcon/dcqb.png" />
                                            <p>导出全部</p>
                                        </div>
                                    </div>
                                    <div class="pup_dcv" onclick="export_close('${url}','inquiry')"> 
                                        <div>
                                            <img src="images/iIcon/dcss.png" />
                                            <p>导出查询</p>
                                        </div>
                                    </div>
                                </div>
                                
                            </div>
                        </div>
                    </div>`;
        $("body").append(res);
}
    
function export_close(url,type) {
        $("#pup_div").remove();
        location.href=location.href+'&pageto='+type;
}
</script>
{/literal}
</body>
</html>