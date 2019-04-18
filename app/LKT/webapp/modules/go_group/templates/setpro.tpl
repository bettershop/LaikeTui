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
<!--/meta 作为公共模版分离出去-->
{literal}
<style type="text/css">
  .product_title{
      width:98px;
      overflow:hidden;
      white-space:nowrap;
      text-overflow:ellipsis;
      -webkit-text-overflow:ellipsis;

  }
</style>
{/literal}
<title>活动设置</title>
</head>
<body>
<nav class="breadcrumb">插件管理
	<span class="c-gray en">&gt;</span>
	拼团活动
	<span class="c-gray en">&gt;</span>
	添加拼团
</nav>	
<div class="page-container">
	<form method="post" class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
		<div id="tab-category" class="HuiTab">
				<div class="tabCon">
	           <div class="mt-20">
                  <table class="table table-border table-bordered table-bg table-hover table-sort" style="width:98%;margin:0 auto;">
                     <thead>
                   <tr class="text-c">
                      <th width="50">商品id</th>
		                  <th width="100">商品名称</th>
		                  <th width="80">商品图片</th>
                      <th width="50">配置名</th>
                      <th width="50">颜色</th>
                      <th width="50">规格</th>
		                  <th width="80">商品价格</th>
		                  <th width="120">拼团价格<div style="color:#666666;font-size: 10px;">(双击可进行设置)</div></th>
		                  <th>是否支持团长优惠<div style="color:#666666;font-size: 10px;">(如支持,填写后请打勾确定.如不支持,可以不填,直接提交)</div></th>
		               </tr>
                     </thead>
                     <tbody>
	                   {foreach from=$arr item=item name=f1}
	                     <tr class="text-c" style="height:20px;">
                         <td>{$item->pid}</td>
	                       <td>
                          <div class="product_title">{$item->product_title}</div></td>
	                       <td><image src="{$item->image}" style="width: 90%;height:60px;"/></td>
	                       <td>{$item->name}</td>
                         <td>{$item->color}</td>
                         <td>{$item->size}</td>
                         <td>{$item->price}</td>
	                       <td id="{$item->id}">
	                    	    <div name="{$item->id}" ondblclick="set_group_price({$item->id})">{$item->price}</div>
	                    	    <input type="hidden" id="set_price_{$item->id}" value="{$item->price}">
	                       </td>
	                       <td>
		                       	<div style="height: 100%;text-align: center;align-items: center;display: flex;">
		                       		<div style="margin: 0 auto;text-align: center;align-items: center;display: flex;">
		                       		<input type="checkbox" class="inputC" id="group_member_{$item->id}" name="group_member_{$item->id}"
		                       			 onchange="memberPrice({$item->id})" style=" display: inline-block;margin-left: -20px; "/>
	                            <label for="group_member_{$item->id}" style="display: inline-block;"></label>
	                            <input type="text" style="width:80px;height:30px;line-height: 30px;margin-left: 10px;border:1px solid #eee" 
	                            	data-id="{$item->id}" id="price_member_{$item->id}" style="margin-left: 12px;" onkeyup="checkDecimal(this,{$item->id})"/>
	                         		</div>
		                       	</div>
                         </td>
	                     </tr>
	                   {/foreach}
                    </tbody>
                 </table>
              </div>
	        </div>	
        </div>
         <!-- {foreach from=$set item=item key=key}
            <input type="hidden" id="{$key}" value="{$item}">
         {/foreach}  --> 
            
        </div>
		<div class="row cl" style="margin: 10px;margin-top: 0px;">
			<div>
				<input class="btn btn-primary radius" type="button" onclick="group_tijiao()" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
				<input class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;返回&nbsp;&nbsp;" style="background: #EDEDED;border:0px;color:#fff;" onclick="javascript:history.back(-1);">
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
  var setgp = new Object;
  var tuanZ = new Object;
  function set_group_price(i){
  	var pid = i;
    
  	$('.text-c div[name='+i+']').hide();
  	var set_price = $('#set_price_'+i).val();
    $('.text-c td[id='+i+']').append('<input type="text" name="'+i+'" style="width:90px;" onkeyup="gDecimal(this,'+pid+')">');
    $('.text-c input[name='+i+']').attr("value",set_price);
    $('.text-c input[name='+i+']').blur(function(){
    	var price = $('.text-c input[name='+i+']').val();
    	var abc = price.indexOf('.');
    	var efg = price.indexOf('0');
    	if(efg == 0 && abc < 0){
    		price = price.substring(1);
    	}
      if(abc == 0 && price != ''){
        price = '0' + price;
      }
    	if(abc < 0 && price != ''){
    		price = price + '.00';
    	}else if(abc == (price.length-1)){
    		price = price + '00';
    	}
       price = parseFloat(price).toFixed(2);
       setgp[i] = price;
       
    	 $('.text-c input[name='+i+']').remove();
    	 $('.text-c div[name='+i+']').text(price);
    	 $('#set_price_'+i).val(price);
    	 $('.text-c div[name='+i+']').show(); 
    });
    
 }
  
  var canp={ 
      num:"",
      id:'' 
      } 
  var gDecimal=function(n,m){        //对文本进行正则处理
      var decimalReg=/^\d{0,8}\.{0,1}(\d{1,2})?$/;//var decimalReg=/^[-\+]?\d{0,8}\.{0,1}(\d{1,2})?$/; 
      if(n.value!=""&&decimalReg.test(n.value)){ 
      canp.num=n.value;
      canp.id = m; 
      }else{ 
      if(n.value!=""){ 
       if(canp.id == m){
        n.value=canp.num;
        }else{
        n.value='';  
        } 
       } 
     } 
   }

  var record={ 
      num:"",
      id:'' 
      } 
  var checkDecimal=function(n,m){
          //对文本进行正则处理
      var decimalReg=/^\d{0,8}\.{0,1}(\d{1,2})?$/;//var decimalReg=/^[-\+]?\d{0,8}\.{0,1}(\d{1,2})?$/; 
      if(n.value!=""&&decimalReg.test(n.value)){ 
      record.num=n.value;
      record.id = m; 
      }else{ 
      if(n.value!=""){
       if(record.id == m){
        n.value=record.num;
        }else{
        n.value='';  
        } 
       } 
     } 
   } 


  function memberPrice(i) {
    
     if($('input[name=group_member_'+i+']').prop('checked') == true){
        var price = $('#price_member_'+i).val();
          if(price == ''){
              alert('不允许为空!');
              $('input[name=group_member_'+i+']').prop('checked',false);
          }else{
            $('#price_member_'+i).attr('disabled','disabled');
            var abc = price.indexOf('.');
            var efg = price.indexOf('0');
            if(efg == 0 && abc < 0){
               price = price.substring(1);
             }
            if(abc == 0 && price != ''){
               price = '0' + price;
             }
            if(abc < 0 && price != ''){
               price = price + '.00';
             }else if(abc == (price.length-1)){
               price = price + '00';
             }
            price = parseFloat(price).toFixed(2);
            $('#price_member_'+i).val(price);
            tuanZ[i] = price;
          }
     }else{
        delete tuanZ[i];
        $('#price_member_'+i).removeAttr('disabled');
     }
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
  
   function delCookie(name)
{
var exp = new Date();
exp.setTime(exp.getTime() - 1);
var cval=getCookie(name);
if(cval!=null)
document.cookie= name + "="+cval+";expires="+exp.toGMTString();
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
    delCookie("proids");
        var str = '{';
        $.each(setgp,function(index,element){
              str+= '"'+index+'":"'+element+'",';
            });

            str = str.substring(0,(str.length-1));
            str += '}';
    	  
        var tuan = '{';
        $.each(tuanZ,function(index,element){
              tuan+= '"'+index+'":"'+element+'",';
            });

            tuan = tuan.substring(0,(tuan.length-1));
            tuan += '}';

            $.ajax({
               url:"index.php?module=go_group&action=setpro",
               type:"post",
               data:{gprice:str,mprice:tuan},
               dataType:"json",
               success:function(data) {
                   if(data.code == 1){
                   	   
                       location.href = 'index.php?module=go_group&action=index';
                   	   // window.parent.location.reload();
                       // var index = parent.layer.getFrameIndex(window.name);
                       // parent.layer.close(index);
                   }
               },
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

$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	$("#tab-category").Huitab({
		index:0
	});
	/*$("#form-category-add").validate({
		rules:{
			
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			//$(form).ajaxSubmit();
			var index = parent.layer.getFrameIndex(window.name);
			//parent.$('.btn-refresh').click();
			parent.layer.close(index);
		}
	});*/
});

</script>
{/literal}
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>