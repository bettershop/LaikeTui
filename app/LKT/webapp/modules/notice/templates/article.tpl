
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

<title>公告详情</title>
{literal}
<style>
   	td a{
        width: 44%;
        margin: 2%!important;
        float: left;
    }
    
    /*标题*/
   .breadcrumb{
   	margin-top: 20px;
   	margin-left: 20px;
   }
    .gg_div{padding:20px;background-color: #fff;margin: 10px 20px;}
    .gg_title{font-size: 28px;color: #414658;line-height: 40px;}
    .gg_time{padding-top: 10px;border-bottom: 1px solid #E9ECEF;padding-bottom: 0px;}
    .gg_time span{font-size: 14px;color:#97a0b4;}
    .gg_time_time{padding-right: 20px;}
    
    /*内容*/
   .gg_word{padding:20px 0;}
   .gg_word img{max-width: 50%;max-height: 500px;}
   .gg_word p{font-size: 16px;color: #6a7076;line-height:25px;padding-top: 20px;margin-bottom: 0;text-indent: 2em;} 
   .gg_word p img{
   	display: block;
   }
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe62f;</i> 系统首页 <span class="c-gray en">&gt;</span>公告详情 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>

	<!--内容-->
	<div class="gg_div">
		<div class="gg_title">{$res_notice[0]->name}</div>
		<div class="gg_time">
			<span class="gg_time_time">{$res_notice[0]->time}</span>
			<span class="gg_name">{$res_notice[0]->user}</span>
		</div>
		<div class="gg_word">
			<img src="{$uploadImg}{$res_notice[0]->img_url}"/>
			{$res_notice[0]->detail}
			<div class="clearfix"></div>
		</div>
	</div>
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>

</body>
</html>