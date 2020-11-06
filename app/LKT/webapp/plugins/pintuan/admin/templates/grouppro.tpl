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
  #DataTables_Table_0_wrapper{
  	padding-bottom: 100px;
  }
  #DataTables_Table_0_length{
  	bottom: 63px;
  }
  
</style>
{/literal}
<title>活动设置</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe616;</i>
	 插件管理 <span class="c-gray en">&gt;</span> 
	 拼团<span class="c-gray en">&gt;</span>
	商品
</nav>
<div class="page-container" style="padding-top: 0px;">
	<form method="post" class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
		<div id="tab-category" class="HuiTab">
			<input type="hidden" name="status" value="{$status}" />
        <div class="tabCon">
           <div class="">
              <table class="table table-border table-bordered table-bg table-hover table-sort">
                 <thead>
                   <tr class="text-c">
                      <th width="50">团号</th>
                      <th width="80">商品图片</th>
		                  <th style="width: 300px;">商品名称</th>
		                
                      <th width="50">配置名</th>
                      <th width="50">颜色</th>
                      <th width="50">规格</th>
		                  <th>商品价格</th>
		                  <th width="110" style="padding:0px;">拼团价格</th>
		                  <th width="110" style="padding:0px;">团长价格</th>
		                {if $status < 1}<th width="50">操作</th>{/if}
		               </tr>
                     </thead>
                     <tbody>
	                   {foreach from=$list item=item name=f1}
	                     <tr class="text-c" style="height:20px;">
                         <td>{$item->group_id}</td>
                         <td><img src="{$item->image}" style="width: 90%;height:60px;"/></td>
	                       <td style="text-align: left;">{$item->pro_name}</td>
	                       <td>{$item->attr_name}</td>
                         <td>{$item->color}</td>
                         <td>{$item->guige}</td>
                         <td>{$item->market_price}</td>
                         {if $status < 1}
	                       <td id="{$item->id}">
	                    	    <!-- <div name="modify_group_{$item->id}" ondblclick="set_group_price({$item->id})">{$item->group_price}</div> -->
	                    	    <input type="number" style="height: 36px;line-height: 36px;border: 1px solid #eee;padding-left: 20px;" name="modify_group_{$item->id}" id="set_group_{$item->id}" value="{$item->group_price}">  
	                       </td>
	                       <td id="m{$item->id}">
                            <!-- <div name="modify_member_{$item->id}" ondblclick="set_member_price({$item->id})">{$item->member_price}</div> -->
	                    	    <input type="number"  style="height: 36px;line-height: 36px;border: 1px solid #eee;padding-left: 20px;" name="modify_member_{$item->id}" id="set_member_{$item->id}" value="{$item->member_price}"> 
                         </td>
                         {else}
                         <td id="{$item->id}">
                            <div name="modify_group_{$item->id}" >{$item->group_price}</div>
                         </td>
                         <td id="m{$item->id}">
                            <div name="modify_member_{$item->id}" >{$item->member_price}</div>
                         </td>
                         {/if}   
                         {if $status < 1}
                         <td><a style="width: 60px;display: block;" title="删除产品" href="javascript:;" onclick="system_category_del(this,{$item->id},1)" class="ml-5">
                     					<div style="align-items: center;font-size: 12px;display: flex;">
									            	<div style="margin:0 auto;;display: flex;align-items: center;"> 
									                <img src="images/icon1/del.png"/>&nbsp;删除
									            	</div>
															</div>
                         		</a>
                         </td>
                         {/if}
	                     </tr>
	                   {/foreach}
                    </tbody>
                 </table>
              </div>
	        </div>	
        </div> 
            
        </div>
    {if $status < 1}    
		<div class="row cl" style="margin: 0 auto;text-align: center;position: relative;top: -55px;">
			<div class="" style="margin-right: 40px;">
				<input class="btn btn-primary radius" type="button" onclick="modify_pro()" value="保  存">			
			</div>
		</div>
    {/if}

	</form>
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
<script type="text/javascript" src="style/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="style/lib/laypage/1.2/laypage.js"></script>

{literal}
<script type="text/javascript">
 
 var setgp = new Object;
  var tuanZ = new Object;
  function set_group_price(i){
  	var pid = i;
    
  	$('div[name=modify_group_'+i+']').hide();
  	var set_price = $('#set_group_'+i).val();
    $('.text-c td[id='+i+']').append('<input type="text" name="modify'+i+'" style="width:80px;" onkeyup="gDecimal(this,'+pid+')">');
    $('.text-c input[name=modify'+i+']').attr("value",set_price);
    $('.text-c input[name=modify'+i+']').blur(function(){
    	var price = $('.text-c input[name=modify'+i+']').val();
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
       
    	 $('.text-c input[name=modify'+i+']').remove();
    	 $('.text-c div[name=modify_group_'+i+']').text(price);
    	 $('#set_group_'+i).val(price);
    	 $('.text-c div[name=modify_group_'+i+']').show(); 
    });
    
 }

  function set_member_price(i){
  	var pid = i;
    
  	$('div[name=modify_member_'+i+']').hide();
  	var set_price = $('#set_member_'+i).val();
    $('.text-c td[id=m'+i+']').append('<input type="text" name="modifytoo'+i+'" style="width:80px;" onkeyup="checkDecimal(this,'+pid+')">');
    $('.text-c input[name=modifytoo'+i+']').attr("value",set_price);
    $('.text-c input[name=modifytoo'+i+']').blur(function(){
    	var price = $('.text-c input[name=modifytoo'+i+']').val();
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
       tuanZ[i] = price;
       
    	 $('.text-c input[name=modifytoo'+i+']').remove();
    	 $('.text-c div[name=modify_member_'+i+']').text(price);
    	 $('#set_member_'+i).val(price);
    	 $('.text-c div[name=modify_member_'+i+']').show(); 
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
   
   function modify_pro() {

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
               url:"index.php?module=pi&p=pintuan&c=modify&set=gpro",
               type:"post",
               data:{gprice:str,mprice:tuan},
               dataType:"json",
               success:function(data) {
                   if(data.gcode == 1 && data.mcode == 1){
                       location.href = 'index.php?module=pi&p=pintuan&c=Home';
                   }
               },
             })
         
      }
    var prolen = '{/literal}{$len}{literal}';
        prolen = parseInt(prolen);
function system_category_del(obj,id,control){
        if(prolen <= 1){
           layer.msg('删除失败,至少得保留一款产品!');
           return false;
        }
  layer.confirm('确认要删除吗？',function(index){        
    $.ajax({
      type: "post",
      url: "index.php?module=pi&p=pintuan&c=modify&set=delpro",
      dataType: "json",
      data:{id:id},
      success: function(data){
        if(data.code == 1){
              layer.msg('已删除!',{icon:1,time:800});
              location.reload();
          }
      },
      error:function() {
          layer.msg('网络出错!',{icon:1,time:800});
      }
    });
  });

  }

$('.table-sort').dataTable({
	"aaSorting": [[ 1, "desc" ]],//默认第几个排序
	"bStateSave": true,//状态保存
	"aoColumnDefs": [
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