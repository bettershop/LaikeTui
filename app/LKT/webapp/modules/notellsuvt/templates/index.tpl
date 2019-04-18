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
<title>用户管理</title>
{literal}
<style>
   	td a{
        width: 90%;
        margin: 2%!important;
        float: left;
    }
</style>
{/literal}
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe62f;</i> 公告管理 <span class="c-gray en">&gt;</span> 消息公告 <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="pd-20">
	<div style="display:flex">
		<input type="checkbox" id="ipt1" id="allAndNotAll"/>
        <label for="ipt1" id="qxIpt">全选</label>
		<a class="btn btn-primary radius" id="getAllSelectedId" style="margin-left:20px;" onclick="deluser('group',{$item->id})">删除所选信息</a>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                	<th width="40">选择</th>
                	<th width="40">序</th>
		            <th width="100">头像</th>
		            <th width="150">微信昵称</th>
		            <th width="130">标题</th>
		            <th width="150">内容</th>
                	<th width="150">发件人</th>
		            <th width="100">时间</th>
		            <th width="100px">操作</th>
		        </tr>
            </thead>
            <tbody>
	            {foreach from=$re item=item name=f1}
	                <tr class="text-c">
	                	<td>
	                		<div style="display: flex;align-items: center;height: 60px;">
	                            <input name="{$item->id}"  id="{$item->id}" type="checkbox" class="inputC " onchange="selectId({$item->id})  value="{$item->id}">
	                            <label for="{$item->id}"></label>
	                        </div>
	                	</td>
	                    <td>{$smarty.foreach.f1.iteration}</td>
	                    <td><image class='pimg' src="{$item->headimgurl}" style="width: 60px;height:60px;border-radius: 30px;border: 1px solid darkgray;"/></td>
	                    <td>{$item->r_name}</td>
	                    <td>{$item->title}</td>
	                    <td>{$item->content}</td>
						<td>{$item->name}</td>
	                    <td>{$item->time}</td>
	                    <td>
	                        <a style="text-decoration:none" class="ml-5" onclick="deluser('only',{$item->id})">
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
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>
<script type='text/javascript' src='modpub/js/calendar.js'> </script>
<script type="text/javascript" src="style/js/jquery.js"></script>
<script type="text/javascript" src="style/FineMessBox/js/common.js"></script>
<script type="text/javascript" src="style/FineMessBox/js/subModal.js"></script>

<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script> 
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script> 
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="style/js/H-ui.js"></script> 
<script type="text/javascript" src="style/js/H-ui.admin.js"></script>


{literal}
<script type="text/javascript">
function excel(pageto) {
    var pagesize = $("[name='DataTables_Table_0_length'] option:selected").val();
    var page = $(".current").text();
    location.href=location.href+'&pageto='+pageto+'&pagesize='+pagesize+'&page='+page;
}
$('.table-sort').dataTable({
    "aaSorting": [[ 7, "desc" ]],//默认第几个排序
    "bStateSave": false,//状态保存
    "aoColumnDefs": [
      //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
      {"orderable":false,"aTargets":[0,1,2,3,4,5,6,8]}// 制定列不参与排序
    ]
});

var ids= [];
$("#allAndNotAll").click(function() {
	if (this.checked){
		$("input[name=checkid]").each(function(index){
			  $(this).prop("checked", true);
			  var val = $(this).val();
			  arrModify(ids,val,1);
		});
	} else {
		$("input[name=checkid]").each(function(index) {
			  $(this).prop("checked", false);
			  var val = $(this).val();
			  arrModify(ids,val,2);
		});
	}

});
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	   if (this[i] == val) return i;
	}
	return -1;
}
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
}
    
function arrModify(arr,val,type) {   //１为增加元素  2为删除元素
	if(type == 1){
	   var index = $.inArray(val,arr);
	   if(index === -1) arr.push(val);
	}else if(type == 2){
	   arr.remove(val);
	}
	return arr;
}
    
function selectId(i){
	i = i.toString();
	var index = $.inArray(i,ids);  //判断数组中是否存在该值,如存在返回下标值,如不存在，返回-1
	if($('#checkid'+i).prop('checked') == true){
	  if(index === -1) ids.push(i);
	}else{
	  ids.remove(i);
	}
}
Array.prototype.distinct = function(){   //数组去重
	var arr = this,
	result = [],
	i,
	j,
	len = arr.length;
	for(i = 0; i < len; i++){
		for(j = i + 1; j < len; j++){
			if(arr[i] === arr[j]){
				j = ++i;
			}
		}
		result.push(arr[i]);
	}
	return result;
}
   
          
function deluser(use,id){
	if(use == 'only'){
		arrModify(ids,id,1);
	}
	if(ids.length === 0 && use == 'group'){
		layer.alert('没有选择消息！',{
			icon: 5,
			title: "提示"
		});
	}else{
		layer.confirm('确定要删除吗？',function(index){
			ids = ids.distinct();
			var delIds=ids.join(",");
			$.ajax({
				url:"index.php?module=notellsuvt&action=del",
				type:"post",
				data:{id:delIds},
				dataType:"json",
				success:function(data) {
					if(data.code == 1){
						layer.alert(data.msg, {
							skin: 'layui-layer-lan'
							,closeBtn: 0
							,anim: 4 //动画类型
						});
						location.reload();
					}
				},
			});
		});
	}
}
</script>
{/literal}
</body>
</html>


