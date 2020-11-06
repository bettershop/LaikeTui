<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="style/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="style/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="style/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="style/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="style/css/style.css" />
{literal}
  <style type="text/css">
   
     .isclick a{
        color: #ffffff;
     }
     .status{
     	width: 80px;
     	height: 40px;
     	line-height: 40px;
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    background-color: #fff;
	    margin-left: 0px;
     }
     .status a:hover{
     	text-decoration: none;
     	color: #fff;
     }
     .status:hover{
     	background-color: #2890FF;
     }
     .status:hover a{
     	color: #fFF;
     }
     .isclick{
     	width:80px;
     	height:40px;
     	background: #3399ff;
     	display: flex;
     	flex-direction: row;
     	align-items: center;
     	justify-content: center;
     }
     td a{
            width: 44%;
            float: left;
            margin: 2%!important;
        }

  </style>
{/literal}
<title>拼团活动管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 插件管理
	<span class="c-gray en">&gt;</span>
	拼团活动
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container" style="padding: 0px 10px;">
	
	
	<div style="margin-top:10px;display: flex;flex-direction: row;">
		<div class="status qh {if $status == 0}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=Home&status=0" onclick="statusclick(0)">全部</a></div>
		<div class="status qh {if $status == 1}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=Home&status=1" onclick="statusclick(1)">未开始</a></div>
		<div class="status qh {if $status == 2}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=Home&status=2" onclick="statusclick(2)">进行中</a></div>
		<div class="status qh {if $status == 3}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=Home&status=3" onclick="statusclick(3)">已结束</a></div>
		<div class="status qh {if $status == 4}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=kaituan&status=4" onclick="statusclick(4)">开团记录</a></div>
		<div class="status qh {if $status == 5}isclick{/if}"><a href="index.php?module=pi&p=pintuan&c=cantuan&status=5" onclick="statusclick(5)">参团记录</a></div>
	</div>
	<form name="form1" action="index.php" method="get">
        <input type="hidden" name="module" value="pintuan" />
        <input type="hidden" name="action" value="cantuan" />
        <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />
		<input type="text" name="product_title" size='8' value="{$product_title}" id="product_title" placeholder="请输入商品名称" autocomplete="off" style="width:200px" class="input-text">
		<input type="text" name="user" size='8' value="{$user}" id="user" placeholder="请输入会员名称" autocomplete="off" style="width:200px" class="input-text">
        <input name="" id="btn9" class="btn btn-success" type="submit" value="查询">
        <input type="button" value="重 置" id="btn8" style="border: 1px solid #D5DBE8; color: #6a7076;" class="reset" onclick="resetButton()"  />
	</form>
	<div class="mt-20">
		 <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr class="text-c">
                <th>商品名称</th>
                <th>会员名称</th>
                <th>零售价</th>
                <th>参团价格</th>
                <th>参团时间</th>
            </tr>
            </thead>
            <tbody>
             {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$item->p_name}</td>
                    <td>{$item->user_name}</td>
                    <td>{$item->price} 元</td>
                    <td>{$item->can_price} 元</td>
                    <td>{$item->add_time}</td>
                </tr>
            {/foreach}
            </tbody>
        </table>
	</div>
  <div style="text-align: center;display: flex;justify-content: flex-end;">{$pages_show}</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="style/lib/laypage/1.2/laypage.js"></script>
{literal}
<script type="text/javascript">

  function statusclick(d){
  	$('.status').each(function(i){
      if(d == i){
  		 $(this).addClass('isclick');
  	  }else{
  	  	 $(this).removeClass('isclick');
  	  }
  	})
      
  }
function resetButton(){
    $("#product_title").val("");
    $("#user").val("");
}
</script>
{/literal}
</body>
</html>