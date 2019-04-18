
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

<title>签到列表</title>
{literal}
<style>
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
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe623;</i> 签到活动管理 <span class="c-gray en">&gt;</span> 积分设置 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="swivch">
        <a href="index.php?module=sign" class="btn1">活动列表</a>
        <a href="index.php?module=sign&action=record" class="btn1" style="background-color: #62b3ff;color: #fff;">签到记录</a>
        <div class="clearfix" style="margin-top: 0px;"></div>
    </div>
    <div class="mt-20 text-c">
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="sign" />
            <input type="hidden" name="action" value="record" />
            <input type="hidden" name="pagesize" value="{$pagesize}" />
            <select name="source" class="select" style="width: 120px;height: 31px;vertical-align: middle;">
                <option value="0" selected>用户来源</option>
                <option value="1" {if $source == 1} selected {/if}>小程序</option>
                <option value="2" {if $source == 2} selected {/if}>手机app</option>
            </select>
            <input type="text" class="input-text" style="width:200px" placeholder="用户名" name="name" value="{$name}">
            <input type="text" class="input-text" style="width:200px" placeholder="手机号码" name="tel" value="{$tel}">
            <input name="" id="" class="btn btn-success" type="submit" value="查询">
        </form>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>用户id</th>
                    <th>来源</th>
                    <th>签到积分</th>
                    <th>事件</th>
                    <th>联系电话</th>
                    <th>签到时间</th>
                </tr>
            </thead>
            <tbody>
                {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{$item->user_id}</td>
                    <td>{if $item->source == 1}小程序{elseif $item->source == 2}app{/if}</td>
                    <td>{$item->sign_score}</td>
                    <td>{$item->record}</td>
                    <td>{$item->mobile}</td>
                    <td>{$item->sign_time}</td>
                </tr>
                {/foreach}
            </tbody>
        </table>
    </div>
    <div style="text-align: center;display: flex;justify-content: center;">{$pages_show}</div>
</div>

<script type="text/javascript" src="style/js/jquery.js"></script>

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
      {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
    ]
});
</script>
{/literal}
</body>
</html>