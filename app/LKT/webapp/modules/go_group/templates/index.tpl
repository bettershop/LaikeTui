<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
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
		<div class="status qh {if $status == 0}isclick{/if}"><a href="index.php?module=go_group&action=index&status=0" onclick="statusclick(0)">全部</a></div>
		<div class="status qh {if $status == 1}isclick{/if}"><a href="index.php?module=go_group&action=index&status=1" onclick="statusclick(1)">未开始</a></div>
		<div class="status qh {if $status == 2}isclick{/if}"><a href="index.php?module=go_group&action=index&status=2" onclick="statusclick(2)">进行中</a></div>
		<div class="status qh {if $status == 3}isclick{/if}"><a href="index.php?module=go_group&action=index&status=3" onclick="statusclick(3)">已结束</a></div>
	</div>
	<div style="margin-top: 20px;">
			<a class="btn newBtn radius" href="index.php?module=go_group&action=addgroup'">
				<div style="height: 100%;display: flex;align-items: center;">
            <img src="images/icon1/add.png"/>&nbsp;添加拼团
        </div>
			</a>
	</div>
	<input type="hidden" id="is_have_show" value="{$is_show}" />
	<!-- <input type="hidden" name="status" class="statusclick" value="{$status}" /> -->
	<div class="mt-20">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
                    <th width="120">项目名称</th>
                    <th width="80">拼团号</th>
					<th width="80">拼团人数</th>
                    <th width="150">拼团时限</th>
                    <th width="180">活动时间</th>
                    <th>限参团数</th>
                    <th>限购件数</th>
					<th width="180">活动状态</th>
					<th style="width: 180px;">操作</th>
				</tr>
			</thead>
			<tbody>
               {foreach from=$list item=item}
				<tr class="text-c">
                    <td>{$item->groupname}</td>
                    <td>{$item->status}</td>
					<td>{$item->man_num}</td>
					<td>{$item->time_over}</td>
					<td>{$item->time}</td>
					<td>{$item->groupnum}</td>
                    <td>{$item->productnum}</td>
                    
					<td style="width:200px;text-align: center;">
						{if $item->code==1}
						<span style="color:#4169E1;margin-right: 58px;">未开始</span>
						{elseif $item->code==2}
									  {if $item->is_show==1}
										  <span style="color:green;">执行中
										  </span>
									  {else}
										  <span style="color:orange;">未开始
										  </span>
									  {/if}
						{else}
									  {if $item->is_show==1}
										  <span style="color:green;">执行中
										  </span>
										  
										  {else}
										  <span style="color:red;">已结束</span>
										{/if}
						{/if}
						 
					</td>
					
					<td class="f-14">
						
						

						
						{if $item->code==1}
						<span style="color:#4169E1;margin-right: 58px;">未到活动时间</span>
						{elseif $item->code==2}
									  {if $item->is_show==1}
										
										<a title="编辑活动" href="index.php?module=go_group&action=modify&set=msg&id={$item->status}&status=1" style="text-decoration:none;">
										编辑活动
										</a>
										<a title="查看商品" href="index.php?module=go_group&action=grouppro&id={$item->status}&status=1" style="text-decoration:none;">
											查看商品
										</a>

									  {else}
									  <a title="编辑活动" href="index.php?module=go_group&action=modify&set=msg&id={$item->status}&status=1" style="text-decoration:none;">
										编辑活动
										</a>
										<a title="编辑商品" href="index.php?module=go_group&action=grouppro&id={$item->status}" style="text-decoration:none;">
											编辑商品
										</a>
										  <a title="执行" href="javascript:;" onclick="system_category_del(this,{$item->status},2)" class="ml-5" >执行</a>
										  <a title="删除" href="javascript:;" onclick="system_category_del(this,{$item->status},1)" class="ml-5" >删除</a>
									  {/if}
						{else}
									  {if $item->is_show==1}
										<a title="编辑活动" href="index.php?module=go_group&action=modify&set=msg&id={$item->status}&status=1" style="text-decoration:none;">
										编辑活动
										</a>
										<a title="查看商品" href="index.php?module=go_group&action=grouppro&id={$item->status}&status=1" style="text-decoration:none;">
											查看商品
										</a>
										{else}
										<a title="删除" href="javascript:;" onclick="system_category_del(this,{$item->status},1)" class="ml-5" >删除</a>
										{/if}
						{/if}
						  
						
						
						</td>
				</tr>
               {/foreach}
			</tbody>
		</table>
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="style/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

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

$('.table-sort').dataTable({
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
	"bStateSave": true,//状态保存
	"aoColumnDefs": [
	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
	  {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
	]
});

///*系统-栏目-添加*/
//function system_category_add(title,url,w,h){
//	layer_show(title,url,w,h);
//}
///*系统-栏目-编辑*/
//function system_category_edit(title,url,id,w,h){
//	layer_show(title,url,w,h);
//}
/*系统-栏目-删除*/
function system_category_del(obj,id,control){
	if(control == 1){
	  confirm('确认要删除吗？',id);
		//   function(index){
	 //        //alert(id);
		// 	$.ajax({
		// 		type: 'POST',
		// 		url: 'index.php?module=go_group&action=index&use=1',
		// 		dataType: 'json',
	 //            data:{id:id},
		// 		success: function(data){
		// 		  if(data.status == 1){
		// 			 layer.msg('已删除!',{icon:1,time:800});
	 //                 location.replace(location.href);
	 //                }
		// 		},
		// 		error:function(data) {
		// 			console.log(data.msg);
		// 		},
		// 	});
		// }
  }
	else if(control == 2){
  	if(parseInt($('#is_have_show').val()) > 0){
  		appendMask('已有活动正在执行，如必须要执行此活动，请先结束正在执行的活动 !',"ts");
  	}else{
  	 layer.confirm('确认要执行吗？',function(index){
        
		$.ajax({
			type: 'POST',
			url: 'index.php?module=go_group&action=index&use=2',
			dataType: 'json',
            data:{id:id},
			success: function(data){
			  if(data.status == 1){
				 layer.msg('已执行!',{icon:1,time:800});
                 location.reload();
                }
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	  });
  	}
  }else if(control == 3){
	  layer.confirm('确认要结束吗？(请谨慎操作 !)',function(index){
        //alert(id);
		$.ajax({
			type: 'POST',
			url: 'index.php?module=go_group&action=index&use=3',
			dataType: 'json',
            data:{id:id},
			success: function(data){
			  if(data.status == 1){
				 layer.msg('已结束!',{icon:1,time:800});
                 location.reload();
                }
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
  }
}
function closeMask(id){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=go_group&action=del&use=1&id="+id,
    	async:true,
    	success:function(res){ 		
    		res = JSON.parse(res);
    		if(res.status=='1'){
    			appendMask("删除成功","cg");
    		}
    		else{
    			appendMask("删除失败","ts");
    		}
    	}
    });
}
function closeMask2(id){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=go_group&action=enable&id="+id,
    	async:true,
    	success:function(res){
    		console.log(res)
    		if(res==1){
    			appendMask("启用成功","cg");
    		}
    		else{
    			appendMask("启用失败","ts");
    		}
    	}
    });
}
function closeMask1(){
	
	$(".maskNew").remove();
	location.replace(location.href);
}
function confirm (content,id){
	$("body").append(`
			<div class="maskNew">
				<div class="maskNewContent">
					<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
					<div class="maskTitle">提示</div>	
					<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
					<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
						${content}
					</div>
					<div style="text-align:center;margin-top:30px">
						<button class="closeMask" style="margin-right:20px" onclick=closeMask("${id}") >确认</button>
						<button class="closeMask" onclick=closeMask1()>取消</button>
					</div>
				</div>
			</div>	
		`)
}
function confirm1 (content,id){
	$("body").append(`
			<div class="maskNew">
				<div class="maskNewContent" >
					<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
					<div class="maskTitle">提示</div>	
					<div style="text-align:center;margin-top:30px"><img src="images/icon1/ts.png"></div>
					<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
						${content}
					</div>
					<div style="text-align:center;margin-top:30px">
						<button class="closeMask" style="margin-right:20px" onclick=closeMask2("${id}") >确认</button>
						<button class="closeMask" onclick=closeMask1() >取消</button>
					</div>
				</div>
			</div>	
		`)
}
function appendMask(content,src){
	$("body").append(`
			<div class="maskNew">
				<div class="maskNewContent" style="height:400px">
					<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
					<div class="maskTitle">提示</div>	
					<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
					<div style="height: 100px;position: relative;top:20px;font-size: 22px;text-align: center;">
						${content}
					</div>
					<div style="text-align:center;margin-top:30px">
						<button class="closeMask" onclick=closeMask1() >确认</button>
					</div>
					
				</div>
			</div>	
		`)
}
</script>
{/literal}
</body>
</html>