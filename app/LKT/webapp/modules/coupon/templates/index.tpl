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
<style type="text/css">
td a{
    width: 44%;
    margin: 2%!important;
    float: left;
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
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe6ca;</i> 插件管理 <span class="c-gray en">&gt;</span>优惠券 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div class="text-c"> 
        <form name="form1" action="index.php" method="get">
            <input type="hidden" name="module" value="coupon" />
            <input type="hidden" name="pagesize" value="{$pagesize}" id="pagesize" />

            <select name="activity_type" class="select" style="width: 150px;height: 31px;vertical-align: middle;">
                <option value="0" {if $activity_type == '0'}selected{/if}>请选择活动类型</option>
                <option value="1" {if $activity_type == '1'}selected{/if}>注册</option>
                <option value="2" {if $activity_type == '2'}selected{/if}>节日/活动</option>
                <option value="3" {if $activity_type == '3'}selected{/if}>满减</option>
            </select>
            <input type="text" name="name" size='8' value="{$name}" id="" placeholder="活动名称" style="width:200px" class="input-text">
            <input name="" id="" class="btn btn-success" type="submit" value="查询" >
            <a class="btn newBtn radius" href="index.php?module=coupon&action=add" style="height: 31px!important;border: none;">
            	<div style="height: 100%;display: flex;align-items: center;font-size: 14px;">
                	<img src="images/icon1/add.png" style="margin: 0px;"/>&nbsp;发布活动
           		</div>
            </a>
        </form>
        <div class="swivch">
        	<a href="index.php?module=coupon" class="btn1" style="background-color: #62b3ff;color: #fff;">活动</a>
            <a href="index.php?module=coupon&action=coupon" class="btn1" style="color: #6a7076;">优惠券列表</a>
			<div class="clearfix"></div>
        </div>
    </div>
    
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover">
            <thead>
                <tr class="text-c">
                    <th>序</th>
                    <th>活动名称</th>
                    <th>活动类型名称</th>
                    <th>领取人数</th>
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
                    <td>{$item->name}</td>
                    <td>{$item->activity_type}</td>
                    <td>{$item->num}</td>
                    <td>{$item->start_time}</td>
                    <td>{$item->end_time}</td>
                    <td>{if $item->status == 0}
                    <span style="color: #fff;background: #EE2C2C;width:20px;border-radius: 10px;padding: 0 10px;">未启用</span>
                    {elseif $item->status == 1}
                    <span style="color: #fff;width:20px;background:#3CB371;border-radius: 10px;padding: 0 10px;">已启用</span>
                    {elseif $item->status == 2}
                    <span style="color: #fff;width:20px;background:#EE9A00;border-radius: 10px;padding: 0 10px;">已禁用</span>
                    {else}
                    <span style="color: #fff;width:20px;background:#00B2EE;border-radius: 10px;padding: 0 10px;">已结束</span>
                    {/if}</td>
                    <td>
                        {if $item->status == 2}
                            <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" title="启用" onclick="confirm1('确定要启用该活动?',{$item->id},'启用')">
                            	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;padding: 5px;display: flex;align-items: center;"> 
                                <img src="images/icon1/qy.png"/>&nbsp;启用
                            	</div>
                    		</div>
                            </a>
                        {elseif $item->status ==1}
                            <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" title="禁用" onclick="confirm1('确定要禁用该活动?',{$item->id},'禁用')">
                            	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;padding: 5px;display: flex;align-items: center;"> 
                                <img src="images/icon1/jy.png"/>&nbsp;禁用
                            	</div>
                    		</div>
                            </a>
                        {/if}
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=coupon&action=modify&id={$item->id}" title="修改">
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;padding: 5px;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
                    		</div>
                        </a>
                        {if $item->status != 1}
                            <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm('确定要删除此活动吗?',{$item->id})">
                            	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;padding: 5px;display: flex;align-items: center;"> 
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

function confirm1 (content,id,content1){
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
						<button class="closeMask" style="margin-right:20px" onclick=closeMask2("${id}","${content1}") >确认</button>
						<button class="closeMask" onclick=closeMask1() >取消</button>
					</div>
				</div>
			</div>	
		`)
}
function closeMask2(id,content){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=coupon&action=whether&id="+id,
    	async:true,
    	success:function(res){
    		console.log(res);
    		if(content=="启用"){
    			if(res==1){
    			appendMask("启用成功","cg");
	    		}
	    		else{
	    			appendMask("启用失败","ts");
	    		}
    		}
    		else{
    			if(res==1){
    			appendMask("禁用成功","cg");
	    		}
	    		else{
	    			appendMask("禁用失败","ts");
	    		}
    		}
    	}
    });
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
function closeMask(id){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=coupon&action=del&id="+id,
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
	location.href="index.php?module=coupon";
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
</script>
{/literal}
</body>
</html>