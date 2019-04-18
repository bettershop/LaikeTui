
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

<title>活动列表</title>
{literal}
<style>
   	td a{
        width: 90%;
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
    	background-color: #2890FF;
    	color: #fff!important;
    }
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe623;</i> 插件管理 <span class="c-gray en">&gt;</span> 积分设置 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="swivch">
    	
        <a href="index.php?module=sign" class="btn1" style="background-color: #62b3ff;color: #fff;">活动列表</a>
        <a href="index.php?module=sign&action=record" class="btn1">签到记录</a>
        
        <div class="clearfix" style="margin-top: 0px;"></div>
    </div>
    <div class="mt-20" style="clear:both;">
        <a class="btn newBtn radius" href="index.php?module=sign&action=add">
        	<i class="Hui-iconfont">&#xe610;</i>&nbsp;发布活动
        </a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>活动图片</th>
                    <th>添加时间</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td><image src="{$uploadImg}{$item->image}" style="width: 100px;"/></td>
                    <td>{$item->add_time}</td>
                    <td>{$item->starttime}</td>
                    <td>{$item->endtime}</td>
                    <td>{if $item->status == 0}<span>未启用</span>{elseif $item->status == 1}<span style="color:#30c02d;">启用</span>{else}<span>已结束</span>{/if}</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=sign&action=modify&id={$item->id}&uploadImg={$uploadImg}" title="修改">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
            				</div>
                        </a>
                        {if $item->status != 1}
                            <a style="text-decoration:none" class="ml-5" href="index.php?module=sign&action=del&id={$item->id}&uploadImg={$uploadImg}" onclick="return confirm('确定要删除此活动吗?')">
                            	<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
	                                <img src="images/icon1/del.png"/>&nbsp;删除
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
      {"orderable":false,"aTargets":[0,6]}// 制定列不参与排序
    ]
});
</script>
{/literal}
</body>
</html>