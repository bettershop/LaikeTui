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
{literal}
<style>
   	td a{
        width: 29%;
        margin: 2%!important;
        float: left;
    }
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 插件管理
	<span class="c-gray en">&gt;</span>
	抽奖活动
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<div class="page-container" style="padding:0px 10px">
	
	<div class="cl" style="margin-top: 10px;">
		<span class="l">
		<a class="btn newBtn radius" href="index.php?module=draw&action=addsign">
			<div style="height: 100%;display: flex;align-items: center;font-size: 14px;">
	          <img src="images/icon1/add.png" style="margin: 0px;"/>&nbsp;添加抽奖
	        </div>
		</a>

		</span>
		
		<span style="margin-left: 20px;">用户每天最多抽奖次数：</span>
		
		<input type="hidden" name="id11" value="{$id11}"/>
		<input type="number" name="parameters"  style="border: none;border-radius: 5px;padding:6px;width: 60px;margin-right:20px;" value="{$parameters}"/>
	
		<input type="submit" id="btn1" name="Submit" value="提 交" style="color: #fff;background-color: #2890FF;" class="btn radius Submit">
       
	</div>

	<div class="mt-20">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th >序号</th>
                    <th width="150">活动名称</th>
					<th>商品名称</th>
                    <th style="width:120px">开始时间</th>
                    <th style="width:120px">结束时间</th>
                    <th style="width:120px">每团所需人数</th>
                    <th style="width:120px">最少开团数</th>
                    <th style="width:180px">同一ID用户最多参与次数</th>
					<th style="width:120px">抽奖金额</th>
					<th style="width:120px">中奖次数</th>
					<th style="width:120px">活动状态</th>
					<th style="width:220px">操作</th>
				</tr>
			</thead>
			<tbody>				
				{foreach from=$list item = item name=f1}
				<tr class="text-c">
					<!-- <option value="{$item->id}">{$item->product_title}</option> -->
					<td  value="{$item->id}">{$item->id}</td>
					<td>{$item->name}</td>
                    <td style="text-align: left;" value ="{$item->draw_brandid}">{$item->draw_brandname}</td>
					<td>{$item->start_time}</td>
					<td>{$item->end_time}</td>
					<td>{$item->num}</td>
					<td>{$item->collage_number}</td>
					<td>{$item->cishu}</td>
                    <td>{$item->price}</td>
					<td>{$item->spelling_number}</td>

                    <td>{$item->status}</td>
                    <td>
                    	 {if $item->status1 == 3 ||$item->status1 == 4 ||$item->status1 == 5}
                           <a style="text-decoration:none" class="ml-5" href="index.php?module=draw&action=modify&id={$item->id}" title="修改" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
                			</div>
                        </a>
                        
                        {/if}
                        
                        {if $item->tistrue=='1'}
                            <font class="ml-5" style="color:red;" title="已删除">
                            	<i class="Hui-iconfont">&#xe6e2;</i>
                            </font>
                        {else}
                        		 {if $item->status1 =='3' ||$item->status1 == 4 }
                        		 		 <a style="text-decoration:none" class="ml-5" onclick="confirm1('活动未结束，不能删除',{$item->id})">
		                            	<div style="align-items: center;font-size: 12px;display: flex;">
			                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
			                                <img src="images/icon1/del.png"/>&nbsp;删除
			                            	</div>
		                				</div>
		                            </a>
                        		 {else}
		                            <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm('确定要删除此该活动吗?',{$item->id})">
		                            	<div style="align-items: center;font-size: 12px;display: flex;">
			                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
			                                <img src="images/icon1/del.png"/>&nbsp;删除
			                            	</div>
		                				</div>
		                            </a>
                            {/if}
                        {/if}

                        {if $item->status1=='1'}
							<a style="text-decoration:none" class="ml-5" href="index.php?module=draw&action=operation&id={$item->id}" title="详情" >
								<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
	                                <img src="images/icon1/ck.png"/>&nbsp;详情
	                            	</div>
                				</div>
							</a>
						
						{elseif $item->status1=='3'}
							<a style="text-decoration:none" class="ml-5" href="index.php?module=draw&action=operation&id={$item->id}" onclick="return confirm('活动还未结束，确定抽奖吗？')" title="详情" >
								<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
	                                <img src="images/icon1/jrxt.png"/>&nbsp;抽奖
	                            	</div>
                				</div>
							</a>
						{elseif $item->status1=='4'}
							<a style="text-decoration:none" class="ml-5"onclick="return confirm1('活动未达到开奖人数，不能开奖')" >
								<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
	                                <img src="images/icon1/jrxt.png"/>&nbsp;抽奖
	                            	</div>
                				</div>
							</a>
						{elseif $item->status1=='5'}
							<a style="text-decoration:none" class="ml-5"onclick="return confirm('活动未开始')" >
								<div style="align-items: center;font-size: 12px;display: flex;">
	                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
	                                <img src="images/icon1/jrxt.png"/>&nbsp;抽奖
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
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>
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
function excel(pageto) {
    var pagesize = $("#pagesize").val();
    location.href=location.href+'&pageto='+pageto+'&pagesize='+pagesize;
}
// $('.table-sort').dataTable({
// 	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
// 	"bStateSave": true,//状态保存
// 	"aoColumnDefs": [
// 	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
// 	  {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
// 	]
// });

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
      type:"get",
      data:{parameters:parameters,id11:id11},
      dataType:"json",
      success:function (data) {
        console.log(data);
        if(data == 1){
             appendMask("添加成功","cg")
          }
          if(data == 2){
             appendMask("修改成功","cg")


          }

      },
   });
});
function closeMask(id){
	$(".maskNew").remove();
    $.ajax({
    	type:"get",
    	url:"index.php?module=draw&action=del&id="+id,
    	async:true,
    	success:function(res){
    		console.log(res)
    		if(res==1){
    			appendMask("删除成功","cg");
    		}
    		else{
    			appendMask("删除失败","ts");
    		}
    	}
    });
}
function closeMask1(){
	
	$(".maskNew").remove();
	location.href="index.php?module=draw";
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
						<button class="closeMask" onclick=closeMask1() >取消</button>
					</div>
				</div>
			</div>	
		`)
}
function confirm1 (content,id){
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
						<button class="closeMask" style="margin-right:20px" onclick=closeMask1() >确认</button>
						<button class="closeMask" onclick=closeMask1() >取消</button>
					</div>
				</div>
			</div>	
		`)
}
function appendMask(content,src){
	$("body").append(`
			<div class="maskNew">
				<div class="maskNewContent">
					<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
					<div class="maskTitle">提示</div>	
					<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
					<div style="height: 50px;position: relative;top:20px;font-size: 22px;text-align: center;">
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