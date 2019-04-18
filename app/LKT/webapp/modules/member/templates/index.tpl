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

<title>管理员列表</title>
{literal}
<style>
   	td a{
        width: 29%;
        margin: 1.5%!important;
        float: left;
    }
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe62d;</i> 管理员管理 <span class="c-gray en">&gt;</span> 管理员列表 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
    <div style="clear:both;">
        <button class="btn newBtn radius" onclick="location.href='index.php?module=member&action=add';" >
        	<div style="height: 100%;display: flex;align-items: center;font-size: 14px;">
                <img src="images/icon1/add.png"/>&nbsp;添加管理员
           	 </div>
        </button>
        <!--<button class="btn radius" onclick="multiple_del()" style="height:36px;width: 84px;" >
        	<div style="height: 100%;display: flex;align-items: center;font-size: 14px;background: #fff;text-align: center;">
                <img src="images/icon1/del.png"/>&nbsp;删除
           	</div>
        </button>-->
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <!--<th width="25"><input type="checkbox" name="" value=""></th>-->
                    <th width="30">id</th>
                    <th width="150px">管理员账号</th>
                    <th>添加人</th>
                    <th>许可</th>
                    <th width="80px">角色</th>
                    <th width="220px">操作</th>
                </tr>
            </thead>
            <tbody>
            {foreach from=$list item=item name=f1}
                <tr class="text-c">
                    <!--<td><input type="checkbox" value="{$item->id}" name="del[]"></td>-->
                    <td>{$item->id}</td>
                    <td>{$item->name}</td>
                    <td>{$item->admin_name}</td>
                    <td style="text-align: left;">{$item->permission}</td>
                    <td>{$item->role_name}</td>
                    <td>
                        {if $item->status == 1}
                        <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm1('确定要启用该管理员吗?',{$item->id},'启用')" title="启用" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/qy.png"/>&nbsp;启用
                            	</div>
                    		</div>
                        </a>
                        {elseif $item->status == 2}
                        <a style="text-decoration:none" class="ml-5" href="javascript:void(0);" onclick="confirm1('确定要禁用该管理员吗?',{$item->id},'禁用')" title="禁用" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/jy.png"/>&nbsp;禁用
                            	</div>
                    		</div>
                        </a>
                        {/if}
                        <a style="text-decoration:none" class="ml-5" href="index.php?module=member&action=modify&id={$item->id}" title="修改" >
                        	<div style="align-items: center;font-size: 12px;display: flex;">
                            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
                                <img src="images/icon1/xg.png"/>&nbsp;修改
                            	</div>
                    		</div>
                        </a>
                        <a title="删除" href="javascript:;" onclick="confirm('确定要删除此管理员吗?',{$item->id})" class="ml-5" style="text-decoration:none">
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
// $('.table-sort').dataTable({
//     "aaSorting": [[ 1, "asc" ]],//默认第几个排序
//     "bStateSave": true,//状态保存
//     "aoColumnDefs": [
//       //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
//       {"orderable":false,"aTargets":[0,5]}// 制定列不参与排序
//     ]
// });

/*删除*/
function del(obj,id){
    confirm('确认要删除吗？',"ts")
    $.get("index.php?module=member&action=del",{'id':id},function(res){
            if(res.status=="1"){
                appendMask('删除成功','cg');
                location.replace(location.href)
            }else{
                appendMask('删除失败','ts');
                 location.replace(location.href)
            }
        },"json");

}
/*批量删除*/
function multiple_del(){
    var checkbox=$("input[name='del[]']:checked");//被选中的复选框对象
    var strId="";
    for(var i=0;i<checkbox.length;i++){
        strId+=checkbox.eq(i).val()+",";
    }

    if(strId==""){
        appendMaks('未选择数据!','ts');
        return false;
    }
    if(confirm('确认要删除吗？')){
        $.get("index.php?module=member&action=del",{'id':strId},function(res){
            if(res.status=="1"){
                appendMaks('删除成功','cg');
                location.href="index.php?module=member";
            }else{
                appendMask('删除失败','ts');
                location.href="index.php?module=member";
            }
        },"json");
    };
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
    	url:"index.php?module=member&action=del&id="+id,
    	async:true,
    	dataType:"json",
    	success:function(res){
    		console.log(res)
    		if(res.status==1){
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
	location.href="index.php?module=member";
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
function closeMask2(id,content){
	$(".maskNew").remove();
    $.ajax({
    	type:"post",
    	url:"index.php?module=member&action=status&id="+id,
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
    	url:"index.php?module=member&action=status&id="+id,
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
</script>
{/literal}
</body>
</html>