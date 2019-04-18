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
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>抽奖活动管理</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 插件管理
	<span class="c-gray en">&gt;</span>
	抽奖活动
		<span class="c-gray en">&gt;</span>
	操作
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
	<div class="mt-20">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th colspan="6">{$name}</th>
				</tr>
			</thead>
			<thead>
				<tr class="text-c">
					<th >序号</th>
                    <th width="150">用户名</th>
					<th>团长</th>
                    <th width="150">参加时间</th>
                    <th>状态</th>
                    <th width="180">操作</th>
                    
				</tr>
			</thead>
			<tbody>
				{foreach from=$list item = item name=f1}
				<tr class="text-c">
					<!-- <option value="{$item->id}">{$item->product_title}</option> -->
					<td>{$smarty.foreach.f1.iteration}</td>
					<!-- <td  value="{$item->id}">{$item->id}</td> -->
					<td value="{$item->id}">{$item->user_name}</td>
                    <td value ="{$item->draw_id}">{$item->role_name}</td>
					<td>{$item->time}</td>
					{if $item->lottery_status == 3}
					<td>未中奖</td>
					{elseif $item->lottery_status ==4}
					<td>中奖</td>
					{else}
					<td>待开奖</td>
					{/if}

					
	                    <td style="width: 30px;">
	                        {if $item->lottery_status == 0 || $item->lottery_status == 3}
		                        {if $r05 >= $spelling_number}
									<a style="text-decoration:none" class="ml-5" title="抽奖成功" onclick="return confirm('中奖人数大于你设定的中奖人数了')"><i class="Hui-iconfont">&#xe6a7;</i></a>
								{else}
		                        	<a style="text-decoration:none" class="ml-5" href="index.php?module=draw&action=whether&userid={$item->id}&lottery_status=4&id={$item->draw_id}" title="抽奖成功" onclick="return confirm('确定要让他中奖吗?')"><i class="Hui-iconfont">&#xe6a7;</i></a>
		                        {/if}
	                        {elseif $item->lottery_status ==4}
	                        <a style="text-decoration:none" class="ml-5" href="index.php?module=draw&action=whether&userid={$item->id}&lottery_status=3&id={$item->draw_id}" title="禁用" onclick="return confirm('确定要他与奖品擦肩而过吗?')"><i class="Hui-iconfont">&#xe6a7;</i></a>
	                        {else}
	                        <a style="text-decoration:none" class="ml-5"  title="未到抽奖时间" onclick="return confirm('还没到抽奖时间哦！')"><i class="Hui-iconfont">&#xe6a7;</i></a>
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


/*系统-栏目-添加*/
function system_category_add(title,url,w,h){
	layer_show(title,url,w,h);
}
/*系统-栏目-编辑*/
function system_category_edit(title,url,id,w,h){
	layer_show(title,url,w,h);
}
/*系统-栏目-删除*/
function system_category_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
        // alert(id);
		$.ajax({
			type: 'POST',
			url: 'index.php?module=draw&action=del',
			dataType: 'json',
            data:{id:id},
			success: function(data){
			  if(data.status == 1){
				 layer.msg('已删除!',{icon:1,time:1000});
                 location.reload();
                }
			},
			error:function(data) {
				console.log(data.msg);
			},
		});
	});
}

//参数修改
$(".Submit").click(function(){
    var id11 = $('input[name=id11]').val();
    var parameters = $('input[name=parameters]').val();
   $.ajax({
      url:"index.php?module=draw&action=parameters",
      type:"post",
      data:{parameters:parameters,id11:id11},
      dataType:"json",
      success:function (data) {
        console.log(data);
        if(data == 1){
             alert('修改成功！');
           window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);

          }
          if(data == 2){
             alert('添加成功！');
           window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);

          }

      },
   });
});
</script>
{/literal}
</body>
</html>