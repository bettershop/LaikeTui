
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link href="style/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="style/css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link href="style/css/style.css" rel="stylesheet" type="text/css" />
<link href="style/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />

<title>预约列表</title>
{literal}
<style>
   	td a{
        width: 28%;
        margin: 2% auto!important;
    }

</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe687;</i> 客户管理 <span class="c-gray en">&gt;</span> 预约列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="appointment" />
            <input type="text" name="name" size='8' value="{$name}" id="" placeholder=" 姓名" style="width:200px" class="input-text">
            日期范围：
            <div style="position: relative;display: inline-block;">
				<input name="startdate" value="{$startdate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 25px;top: 7px;" onclick="new Calendar().show(document.form1.startdate);" />~
				</div>
				<div style="position: relative;display: inline-block;">
				<input  name="enddate" value="{$enddate}" size="8" readonly class="scinput_s iptRl" style="" />
				<img src="images/icon1/rl.png" style="cursor:pointer;position: absolute;right: 10px;top: 7px;" onclick="new Calendar().show(document.form1.enddate);" />
				</div>
            <input name="" id="btn1" class="btn btn-success" type="submit" value="查询">
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>用户id</th>
                    <th>姓名</th>
                    <th>手机</th>
                    <th>数量</th>
                    <th>时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{$item->user_id}</td>
                    <td>{$item->name}</td>
                    <td>{$item->mobile}</td>
                    <td>{$item->num}</td>
                    <td>{$item->add_date}</td>
                    <td>{if $item->status == 0}<span style="color: #ff2a1f;">等待预约</span>{elseif $item->status == 1}<span style="color: #30c02d;">已预约</span>{else}<span style="color: #7A7A7A;">取消预约</span>{/if}</td>
                    <td style="width: 220px;text-align: center;">
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=appointment&action=see&id={$item->id}" title="查看详情" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/ck.png"/>&nbsp;查看
                            	</div>
                    		</div>
                        </a>
                        {if $item->status == 0}
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=appointment&action=make&id={$item->id}" title="预约" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/ck.png"/>&nbsp;预约
                            	</div>
                    		</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=appointment&action=cancel&id={$item->id}" title="取消预约" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/ck.png"/>&nbsp;取消
                            	</div>
                    		</div>
                        </a>
                        {/if}
                    </td>
                </tr>
            {/foreach}
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript" src="style/js/jquery.js"></script>
<script type='text/javascript' src='modpub/js/calendar.js'> </script>
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

{literal}
<script type="text/javascript">
$('.table-sort').dataTable({
    "aaSorting": [[ 1, "desc" ]],//默认第几个排序
    "bStateSave": true,//状态保存
    "aoColumnDefs": [
      //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
      {"orderable":false,"aTargets":[0,6]}// 制定列不参与排序
    ]
});
</script>
{/literal}
</body>
</html>