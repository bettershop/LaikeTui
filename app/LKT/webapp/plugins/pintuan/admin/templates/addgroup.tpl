<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />

<link rel="stylesheet" type="text/css" href="style/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="style/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="style/lib/Hui-iconfont/1.0.7/iconfont.css" />
<link rel="stylesheet" type="text/css" href="style/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="style/css/style.css" />
{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
{literal}
<style type="text/css">
   .content{
   	  border:2px red solid;
   }
   .row .form-label{
   	width: 18%!important;
   }
   .manlevel{
   	 margin:10px 0;
   }
   .shengyuman{
   	  width:207px;display: inline-block;
   }
   body{
   	 font-size:14px;
   }
</style>
{/literal}
<title>活动设置</title>
</head>
<body>
<nav class="breadcrumb">
	<span class="c-gray en"></span>
	插件管理
	<span class="c-gray en">&gt;</span>
	<a href="window.history.go(-1)">拼团活动</a>
	<span class="c-gray en">&gt;</span>
	添加拼团
</nav>
<div class="page-container pd-20 page_absolute">
	<form class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
		<div id="tab-category" class="HuiTab">
			<div class="tabCon">
			  <div style="height:490px;overflow-y: scroll;">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>拼团人数设置：</label>
					<div class="formControls col-xs-8 col-sm-9" style="background-color: #F5F5F5;margin-bottom: 10px;border-radius: 5px;padding: 10px;">
						<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;添加人数&nbsp;&nbsp;" style="height: 36px!important;" onclick="addlever()">
						<div style="margin:10px;" id="setlevel">
						 <div class="manlevel">
						    <input type="number" max="50" min="1" class="input-text" value="" name="min_man" style="width:60px;" onkeyup="onkeyup1(this)">&nbsp;&nbsp;人团&nbsp;&nbsp;		 	
							<span style="margin-left:17px;">折扣价: 参团</span>
							<input type="number" class="input-text" value=""  name="canprice" style="width:80px;" onkeyup="onkeyup1(this)">
							%
							<span style="margin-left: 5px;">开团</span>
							<input type="number" class="input-text" value=""  name="memberprice" style="width:80px;" onkeyup="onkeyup1(this)">
							%
						 </div>
						</div>
						 <!-- <span style="font-size: 10px;color:red;margin: 20px 0 0 10px;">（请设置价格优惠,如果不添加人数等级,则该产品的团默认均为此价格）</span> -->
					</div>
					<div class="col-3">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>拼团倒计时：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="number" min="1" class="input-text" value="" placeholder="" id="" name="timehour" style="width:60px;" onkeyup="onkeyup1(this)"><span> 小时 </span>
						<span style="margin-left: 10px;font-size: 10px;color:red;">（建议不要超过24小时,小时数不能小于1）</span>
					</div>
					<div class="col-3">
					</div>
				</div>
				<div class="row cl" >
					<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>开始时间：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" class="input-text" value="" autocomplete="off" placeholder="" id="group_start_time" name="starttime" style="width:150px;">
					</div>
					<div class="col-3">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>结束时间：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<div style="margin-top: 0px;">
						    <input type="radio" value="2" placeholder="" id="endtime" name="endtime" onchange="radioChange(2)" checked ><span style="margin-left: 10px;">定期结束</span>
						    <input type="text" class="input-text" autocomplete="off" value="" placeholder="" id="group_end_time" name="group_end_time" style="width:150px;margin-left: 10px;">
					    </div>
					    <div style="margin-top: 15px;">
					    	<input type="radio" value="1" placeholder="" id="ctime" name="endtime" onchange="radioChange(1)" >
					    	<span style="margin-left: 10px;">长期</span>
					    	<input type="text" class="input-text" autocomplete="off" value="" placeholder="" id="end_year" name="end_year" style="width:150px;margin-left: 34px;" disabled>
					    	<span style="margin-left: 10px;font-size: 10px;color:red;">（长期的默认期限是一年,开始时间和结束时间不能小于现在时间,且开始时间不能大于结束时间）</span>
					    </div>
					</div>
					<div class="col-3">
					</div>
				</div>
				<div class="row cl" >
					<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户可同时进行的团数：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="number" min="1" class="input-text" value="" placeholder="" id="" name="groupnum" style="width:60px;" onkeyup="onkeyup1(this)">

						<span style="margin-left: 10px;font-size: 10px;color:red;">（数字不能小于1，已成功或已失败的团不计入团数）</span>
					</div>
					<div class="col-3">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>用户参团可购买件数：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="number" min="1" class="input-text" value="" placeholder="" id="" name="productnum" style="width:60px;" onkeyup="onkeyup1(this)">
						<span style="margin-left: 10px;font-size: 10px;color:red;">（数字不能小于1）</span>
					</div>
					<div class="col-3">
					</div>
				</div>
				</div>
				<div class="row cl page_footer" >
					<div >
						<input class="btn btn-primary radius ta_btn4" onclick='javascript:history.back(-1)' type="reset" value="取消" style="background: #fff!important;">
						<input class="btn btn-primary radius ta_btn3" type="button" value="保存" onclick="baocungroup()">
					</div>
				</div>
			</div>
	</form>
</div>
</div>

	

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<!-- <script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> -->
<script type="text/javascript" src="style/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="style/laydate/laydate.js"></script>
{php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}
{literal}
<script type="text/javascript">
  var radio = 1;

   function radioChange(i){
   	  var group_end_time = $('#group_end_time');
      if(i == 1){
      	  group_end_time.attr('disabled','disabled');
      	  group_end_time.val('');
      	  radio = 1;
      	  var startdate = $("#group_start_time").val();
      	  if(startdate != '' && startdate.length == 19){
      	  	var day = startdate.split(' ');
			var str = startdate.replace(/-/g,'/');
	    	var d = new Date(str);
	    	var oneYear = oneYearPast(d);
	    	oneYear = oneYear + ' ' + day[1];
	    	$("#end_year").val(oneYear)
      	  }
      }else{
      	  group_end_time.removeAttr('disabled');
      	  $("#end_year").val('');
      	  radio = 2;
      }
   }

   laydate.render({
	  elem: '#group_start_time', //指定元素
	   trigger: 'click',
	  type: 'datetime',
	  done: function(value, date){
	  	var radio = $("input[name=endtime]:checked").val();

	  	if(radio == 1){
	        var day = value.split(' ');
			var str = value.replace(/-/g,'/');
	    	var d = new Date(str);
	    	var oneYear = oneYearPast(d);
	    	oneYear = oneYear + ' ' + day[1];
	    	$("#end_year").val(oneYear)
	    }	
	    //alert('你选择的日期是：' + value + '\n获得的对象是' + JSON.stringify(date));
	  }
	});
   
 laydate.render({
  elem: '#group_end_time',
	 trigger: 'click',
  type: 'datetime'
});
 
 
//一年后的今天的前一天
function oneYearPast(time)
{
	//var time=new Date();
	var year=time.getFullYear()+1;
	var month=time.getMonth()+1;
	var day=time.getDate();
	
	if(month<10){
		month="0"+month;
	}
	
	if(day>1){
		day = day;
	}else{
		month = month-1;
		if(month<10){
			month="0"+month;
		}
		if(month==0){
			month = 12;
		}
		day=new Date(year,month,0).getDate();
	}
	
	var v2=year+'-'+month+'-'+day;
	return v2;		
}

 $("input[name=endtime]").change(function(){
    		if($("input[name=endtime]:checked").val() == 1){
    		  $('input[name=group_end_time]').removeClass('content');
    	  }
    });
 
 
 function addlever() {
 
    var node = '<div class="manlevel"><input type="number" max="50" min="1" class="input-text" value="" name="min_man" style="width:60px;" onkeyup="onkeyup1(this)">&nbsp;&nbsp;人团&nbsp;&nbsp;<span style="margin-left:20px;">折扣价: 参团</span><input type="number" class="input-text" value=""  name="canprice" style="width:80px;margin-left:5px;" onkeyup="onkeyup1(this)">&nbsp;%<span style="margin-left: 10px;">开团</span><input type="number" class="input-text" value=""  name="memberprice" style="width:80px;margin-left:5px;" onkeyup="onkeyup1(this)">&nbsp;%<input class="btn btn-primary radius" type="button" onclick="removepro(event)" value="删除" style="margin-left:10px;height: 36px!important;"></div>';

    var setlevel = $('#setlevel');
    setlevel.append(node);
  }

  function onkeyup1(ob){
  	 if(ob.value.length==1){ob.value=ob.value.replace(/[^1-9]/g,'')}else{ob.value=ob.value.replace(/\D/g,'')}
  }

  function removepro(event){
     var goods = event.srcElement.parentNode;
     goods.parentNode.removeChild(goods);
     
  }

 function baocungroup(){
 	
 	var checkdata = true;
 	var gdata = {};
 	
 	var timehour = gdata['timehour'] = $("input[name=timehour]").val();
 	var starttime = gdata['starttime'] = $("input[name=starttime]").val();	
 	var groupnum = gdata['groupnum'] = $("input[name=groupnum]").val();
 	var productnum = gdata['productnum'] = $("input[name=productnum]").val();
    var overtype = gdata['overtype'] = $("input[name=endtime]:checked").val();
    var endtime = $("input[name=group_end_time]").val();
    
    var manlevel = $('.manlevel');
    var glevel = {};
   
    $.each(manlevel,function (index,element){
    	var min_man = parseInt($(element).children('input[name=min_man]').val());  	
    	var canprice = $(element).children('input[name=canprice]').val();
    	var memberprice = $(element).children('input[name=memberprice]').val();
    	if(glevel[min_man]){
    		layer.msg('人数设置参数重复');
            checkdata = false;
            return;
    	}
    	if(min_man == ''){
        	layer.msg('人数设置参数不能为空');
            checkdata = false;
            return;
        }   	
        if(canprice == '' || memberprice == ''){
        	layer.msg('价格设置参数不能为空');
            checkdata = false;
            return;
        }
        if(parseInt(canprice) < parseInt(memberprice)){
        	layer.msg('参团价格不能小于开团价格');
            checkdata = false;
            return;
        }
        glevel[min_man] = canprice+'~'+memberprice;
    })
   
    
   if(checkdata == true){
		if(timehour == ''){
			layer.msg('拼团倒计时设置不能为空');
			checkdata = false;
		    return;
		}else if(starttime == ''){
			
			layer.msg('开始时间设置不能为空');
			checkdata = false;
		    return;
		}else if($("input[name=endtime]:checked").val() == 2 && endtime == ''){    	
			
			layer.msg('结束时间设置不能为空');
			checkdata = false;
		    return;
		}else if(groupnum == ''){
			
			layer.msg('团数限制不能为空');
			checkdata = false;
		    return;
		}else if(productnum == ''){
			
			layer.msg('产品购买件数限制不能为空');
			checkdata = false;
		    return;
		}
   }

   if(checkdata == true){
   	var stime = new Date(starttime).getTime();
    var now = new Date().getTime();
     now1 = now-(3600 * 1000);
    if(overtype == '2'){
    	gdata['endtime'] = endtime;
    	var etime = new Date(endtime).getTime();
        if(stime < now1 || etime < now || stime >= etime){
        	layer.msg('时间设置不合格!');
        	return false;
        }
    }else{
    	gdata['endtime'] = 'changqi';
    	if(stime < now1){
        	layer.msg('时间设置不合格!');
        	return false;
        }
    }
    
   	  gdata = JSON.stringify(gdata);
 	    $.ajax({
               url:"index.php?module=pi&p=pintuan&c=addgroup",
               type:"post",
               data:{gdata:gdata,glevel:glevel},
               dataType:"json",
               success:function(data) {
                   if(data.code == 1){
                   	   layer.msg('添加成功!',{time:1000},function(){
                           window.location.href = 'index.php?module=pi&p=pintuan';
                       });             	        
                   }else{
                    layer.msg('发布失败!');  
                   }
               },
             })
 }
}


	$('.table-sort').dataTable({
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
	"bStateSave": true,//状态保存
	"aoColumnDefs": [
	  //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
	  {"orderable":false,"aTargets":[0,4]}// 制定列不参与排序
	]
});

$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#tab-category").Huitab({
		index:0
	});
	
	
});

</script>
{/literal}
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>