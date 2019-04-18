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
<!-- <link rel="stylesheet" type="text/css" href="style/css/style.css" /> -->
<title>活动设置</title>
<style type="text/css">
  {literal}
  　　　/*隐藏掉我们模型的checkbox*/
    .my_protocol .input_agreement_protocol {
                appearance: none;
                -webkit-appearance: none;
                outline: none;
                display: none;
            }
    /*未选中时*/        
   .my_protocol .input_agreement_protocol+span {
                width: 16px;
                height: 16px;
                background-color: red;
                display: inline-block;
                background: url(../../Images/TalentsRegister/icon_checkbox.png) no-repeat;
                background-position-x: 0px;
                background-position-y: -25px;
                position: relative;
                top: 3px;
            }
   /*选中checkbox时,修改背景图片的位置*/            
   .my_protocol .input_agreement_protocol:checked+span {
                background-position: 0 0px
            }       
            {/literal}
</style>
</head>
<body>
	<nav class="breadcrumb">插件管理
	<span class="c-gray en">&gt;</span>
	拼团活动
	<span class="c-gray en">&gt;</span>
	添加拼团
</nav>
<div class="page-container" style="padding:0px 10px;">
	<form method="post" class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
		    <form name="form1"  method="get" >
			    <input type="hidden" name="module" value="go_group" />
			    <input type="hidden" name="action" value="addproduct" />
			    <select name="cid" class="select" style="width: 180px;height: 31px;vertical-align: middle;margin-top: 10px;">
			        <option value="" >全部</option>
			        {$class}
			    </select>
			    <input type="text" name="pro_name" size='8' value="{$user_name}" id="" placeholder="商品名称" style="width:180px;margin-top: 10px;" class="input-text">
			    <input name="" id="" class="btn btn-success" type="submit" style="margin-top: 10px;margin-left: 20px;" value="查询">
				</form>
	  	</div>
    	<div class="tabCon1" style="margin:10px;margin-top:0px;">
	           <div class="mt-20">
                  <table class="table table-border table-bordered table-bg table-hover table-sort" style="margin:0 auto;">
                     <thead>
                       <tr class="text-c">
                	    <th style="width: 10%;">选择</th>
		                  <th style="width: 10%;">商品类别</th>
		                  <th style="width: 50%;">商品名称</th>
		                  <th style="width: 10%;">商品库存</th>
		                  <th style="width: 10%;">商品图片</th>
		                  <th style="width: 10%;">商品id</th>
		               </tr>
                     </thead>
                     <tbody>
	                   {foreach from=$arr item=item}
	                     <tr class="text-c" style="height:20px;">
	                       <td>
                          <div class="text-c">
                          <input type="checkbox" class="inputC input_agreement_protocol" id="checkid{$item->id}" name="checkid{$item->id}" 
                          	value="{$item->id}" onchange="product_tijiao({$item->id})" {if $item->checked === 1}checked{/if}>
                          	<label for="checkid{$item->id}"></label>
                          </div>
                        </td>
	                       <td><div id="prod_class_{$item->id}">{$item->pname}</div></td>
	                       <td>{$item->product_title}</td>
	                       <td>{$item->product_title}</td> <!--此处换数据-->
	                       <td><image src="{$item->image}" style="width: 80%;height:60px;"/></td>
	                       <td>{$item->id}</td>
	                     </tr>
	                   {/foreach}
                    </tbody>
                 </table>
              </div>
	        </div>	
        </div>
         <!-- {foreach from=$set item=item key=key}
            <input type="hidden" id="{$key}" value="{$item}">
         {/foreach} -->
		<div class="row cl" style="margin: 10px;margin-top: 0px;">
			<div class="col-9 ">
				<input class="btn btn-primary radius" type="button" onclick="group_tijiao()" value="&nbsp;&nbsp;下一步&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;返回&nbsp;&nbsp;" onclick="javascript:history.back(-1);" style="background: #EDEDED;border:0px;color:#fff;">
			</div>
		</div>
	</form>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="style/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
<script type="text/javascript" src="style/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="style/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="style/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="style/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="style/lib/laypage/1.2/laypage.js"></script>

{literal}
<script type="text/javascript">
  
  function set_group_price(i){
  	
  	$('.text-c div[name='+i+']').hide();
  	var set_price = $('#set_price_'+i).val();
    $('.text-c td[id='+i+']').append('<input type="text" name="'+i+'" style="width:60px;">');
    $('.text-c input[name='+i+']').attr("value",set_price);
    $('.text-c input[name='+i+']').blur(function(){
    	var price = $('.text-c input[name='+i+']').val();
    	var abc = price.indexOf('.');
    	var efg = price.indexOf('0');
    	if(efg == 0 && abc < 0){
    		price = price.substring(1);
    	}
    	if(abc < 0 && price != ''){
    		price = price + '.00';
    	}else if(abc == (price.length-1)){
    		price = price + '00';
    	}
    	 $('.text-c input[name='+i+']').remove();
    	 $('.text-c div[name='+i+']').text(price);
    	 $('#set_price_'+i).val(price);
    	 $('.text-c div[name='+i+']').show(); 
    });
    
 }

 /*function my_array_name(m){
    var valArr = new Object;
 	$('input[name="'+m+'[]"]:checked').each(function(){
 		var id = $(this).val();
 		valArr[id] = $('#set_price_'+id).val();
 	})
    return valArr;
}*/
// function delCookie(name)
// {
// var exp = new Date();
// exp.setTime(exp.getTime() - 1);
// var cval=getCookie(name);
// if(cval!=null)
// document.cookie= name + "="+cval+";expires="+exp.toGMTString();
// }
//  delCookie("proids");


    
    var data = getCookie("proids");
     
function product_tijiao(i){
  // var classname = $('#prod_class_'+i).text();
  var id = ',' + i + ',';
  var idindex = data.indexOf(id);
  if($('input[name=checkid'+i+']').prop('checked') == true){
     if(data.length == 0){                          //没有存      
        data = ',' + i + ',';
     }else{
         if(idindex < 0){    //没有此id
             data += i + ',';
         }
     }
   }else{                                              
         if(idindex >= 0){
            data = data.replace(id,',');  
      }
   }
   console.log(data);
     setCookie("proids",data,1);
}    
 
  
  function setCookie(c_name,value,expiredays)
  {
  var exdate=new Date()
  exdate.setDate(exdate.getDate()+expiredays)
  document.cookie=c_name+ "=" +escape(value)+
  ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
  }

  function getCookie(c_name)
  {
  if (document.cookie.length>0)
    {
    c_start=document.cookie.indexOf(c_name + "=")
    if (c_start!=-1)
      { 
      c_start=c_start + c_name.length+1 
      c_end=document.cookie.indexOf(";",c_start)
      if (c_end==-1) c_end=document.cookie.length
      return unescape(document.cookie.substring(c_start,c_end))
      } 
    }
  return ""
  }

    function group_tijiao() {

    	// var set = '{';
    	// set += '"'+$('input[id=groupname]').attr('id')+'":"'+$('input[id=groupname]').val()+'",';
     //    set += '"'+$('input[id=peoplenum]').attr('id')+'":"'+$('input[id=peoplenum]').val()+'",';
     //    set += '"'+$('input[id=timehour]').attr('id')+'":"'+$('input[id=timehour]').val()+'",';
     //    set += '"'+$('input[id=timeminite]').attr('id')+'":"'+$('input[id=timeminite]').val()+'",';
     //    set += '"'+$('input[id=starttime]').attr('id')+'":"'+$('input[id=starttime]').val()+'",';
     //    set += '"'+$('input[id=endtime]').attr('id')+'":"'+$('input[id=endtime]').val()+'",';
     //    set += '"'+$('input[id=groupnum]').attr('id')+'":"'+$('input[id=groupnum]').val()+'",';
     //    set += '"'+$('input[id=productnum]').attr('id')+'":"'+$('input[id=productnum]').val()+'",';
     //    set += '"'+$('input[id=radio]').attr('id')+'":"'+$('input[id=radio]').val()+'"}';

        if(data == ',' || data == ''){
           layer.msg("请选择至少一款产品");
        }else{
            $.ajax({
               url:"index.php?module=go_group&action=setpro&from=pro",
               type:"get",
               data:{},
               dataType:"json",
               success:function(data) {
                   if(data.code == 1){
                   	   window.location.href = 'index.php?module=go_group&action=setpro&from=attr';
                       // alert(123);
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