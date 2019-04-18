
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

<title>优惠券列表</title>
{literal}
<style type="text/css">
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe6ca;</i> 插件管理 <span class="c-gray en">&gt;</span> 优惠券列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="coupon" />
            <input type="hidden" name="ok" value="1" />
            <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />

            <input type="text" name="name" size='8' value="{$name}" id="" placeholder="用户id" style="width:200px" class="input-text">
            <input name="" id="" class="btn btn-success" type="submit" value="查询">
        </form>
        <div class="swivch">
        	<a href="index.php?module=coupon" class="btn1" style="color: #6a7076;" >活动</a>
            <a href="index.php?module=coupon&action=coupon" style="background-color: #62b3ff;color: #fff;" class="btn1">优惠券列表</a>
			<div class="clearfix"></div>
        </div>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>用户ID</th>
                    <th>活动名称</th>
                    <th>优惠券金额</th>
                    <th>领取时间</th>
                    <th>到期时间</th>
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
                    <td>{$item->money}</td>
                    <td>{$item->add_time}</td>
                    <td>{$item->expiry_time}</td>
                    <td>{if $item->status == 0}<span style="color:#30c02d;">正常</span>{elseif $item->status == 1}<span>使用中</span>{elseif $item->status == 2}<span style="color:#ff2a1f">已使用</span>{elseif $item->status == 3}<span style="color: #7A7A7A;">已过期</span>{/if}</td>
                    <td>
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=coupon&action=see&user_id={$item->user_id}" title="查看">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;padding: 5px;display: flex;align-items: center;"> 
                                <img src="images/icon1/ck.png"/>&nbsp;查看
                            	</div>
                    		</div>
                        </a>
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

</body>
</html>