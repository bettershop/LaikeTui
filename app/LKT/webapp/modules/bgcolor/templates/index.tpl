
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

<title>背景颜色管理</title>
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe616;</i> 系统管理 <span class="c-gray en">&gt;</span> 前台背景颜色 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div style="clear:both;">
        <button class="btn newBtn radius" value="添加颜色" onclick="location.href='index.php?module=bgcolor&action=add';">
        	<div style="height: 100%;display: flex;align-items: center;font-size: 14px;">
                <img src="images/icon1/add.png" style="margin: 0px;"/>&nbsp;添加颜色
           	</div>
        </button>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>颜色名称</th>
                    <th>颜色</th>
                    <th>排序号</th>
                    <th>状态</th>
                    <th style="width: 200px">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <td>{$smarty.foreach.f1.iteration}</td>
                    <td>{$item->color_name}</td>
                    <td style="background-color:{$item->color} "></td>
                    <td>{$item->sort}</td>
                    <td>{if $item->status == 0}<span style="color: #ff2a1f;">未启用</span>{else $item->status == 1}<span style="color: #30c02d;">已启用</span>{/if}</td>
                    <td>    
                        <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm1('确定要使用这个颜色吗?',{$item->id})" title="启用" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/qy.png"/>&nbsp;启用
                            	</div>
            				</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="" title="修改" >
	                        <div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
            				</div>
                        </a>
                        <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm('确定要删除这个颜色吗?',{$item->id})">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/del.png"/>&nbsp;删除
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

{literal}
<script type="text/javascript">
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
function closeMask(id){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=bgcolor&action=del&id="+id,
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
function closeMask2(id){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=bgcolor&action=enable&id="+id,
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
	location.href="index.php?module=bgcolor";
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
						<button class="closeMask" style="margin-right:20px" onclick=closeMask2("${id}") >确认</button>
						<button class="closeMask" onclick=closeMask1() >取消</button>
					</div>
				</div>
			</div>	
		`)
}
</script>
{/literal}
</body>
</html>