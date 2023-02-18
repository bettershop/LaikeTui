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
<nav class="breadcrumb"><span class="c-gray en"></span>插件管理
	<span class="c-gray en">&gt;</span>
	<a onclick="window.history.go(-1)">拼团活动</a>
	<span class="c-gray en">&gt;</span>
	添加拼团
</nav>	
<div class="page-container pd-20 page_absolute">
	<form method="post" class="form form-horizontal" id="form-category-add" enctype="multipart/form-data">
    
		<div id="tab-category" class="HuiTab">
				<div class="tabCon">
	           <div class="mt-20">
                  <table class="table-border tab_content {if count($arr)>10}table-sort{/if}" style="">
                     <thead>
                   <tr class="text-c tab_tr">
                      <th width="50">
                        <input type="checkbox" class="inputC input_agreement_protocol" id="ipt1" name="ipt1" value="" >
                        <label for="ipt1"></label>
                      </th>
		                  <th>商品名称</th>
		                  <th width="100">商品图片</th>
                      <th width="100">库存</th>
                      <th width="180">规格</th>
		                  <th width="120">商品价格</th>
		               </tr>
                     </thead>
                     <tbody>
	                   {foreach from=$arr item=item name=f1}
                      
	                     <tr class="text-c tab_td">
                         <td>
                            <div class="text-c">
                            <input type="checkbox" class="inputC input_agreement_protocol" id="checkid{$item->id}" name="checkid{$item->id}" value="{$item->id}" style="position: absolute;" data-type="checkpro" onchange="attr_tijiao({$item->id})">
                              <label for="checkid{$item->id}"></label>
                            </div>
                        </td>
	                       <td style="text-align: left;">{$item->product_title}</td>
	                       <td><img src="{$item->image}" style="width: 60px;height:60px;"/></td>
	                      
                         <td>{$item->num}</td>
                         <td>{$item->attr}</td>
                         <td>{$item->price}</td>
             
	                     </tr>
	                   {/foreach}
                    </tbody>
                 </table>
              </div>
	        </div>	
        </div>
            <div class="row cl row_cl" style="clear: both;float:right;">
							<div>
								
								<input class="btn btn-primary radius ta_btn4" type="button" value="&nbsp;&nbsp;返回&nbsp;&nbsp;" style="border: 1px solid #2890FF!important;color:#fff;" onclick="javascript:history.back(-1);">
								<input class="btn btn-primary radius ta_btn3" type="button" onclick="group_tijiao()" value="&nbsp;&nbsp;下一步&nbsp;&nbsp;">
							</div>
						</div>
        </div>
		
	</form>
	<div class="page_h20"></div>
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
  var tuanZ = new Object;
  
  var aa=$(".pd-20").height();
		var bb=$(".table-border").height();
		console.log(aa)
		if(aa<bb){
			$(".page_h20").css("display","block")
		}else{
			$(".page_h20").css("display","none")
			$(".row_cl").addClass("page_footer")
		}
		
  function attr_tijiao(i){
    if($('input[name=checkid'+i+']').prop('checked') == true){
        tuanZ[i] = '~';
    }else{
        delete tuanZ[i];
    }
  }

   $("#ipt1").change(function (){
       var allcheck = $(this).prop('checked');
       if(allcheck == true){
          $.each($('input[data-type=checkpro]'),function (index,element){
            var attr_id = $(element).val();
              if(!tuanZ[attr_id]){
                 tuanZ[attr_id] = '~';
              }
          });
       }else{
          $.each($('input[data-type=checkpro]'),function (index,element){
            var attr_id = $(element).val();
              if(tuanZ[attr_id]){
                 delete tuanZ[attr_id];
              }
          });
       }
   })
  
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


 
    function group_tijiao() {

         var goods_id = '{/literal}{$arr[0]->pid}{literal}';
         if($.isEmptyObject(tuanZ) == true){
          layer.msg("请选择至少一款属性");
          return false;
         }
         
         tuanZ1 = JSON.stringify(tuanZ);
         
            $.ajax({
               url:"index.php?module=pi&p=pintuan&c=setpro",
               type:"post",
               data:{goods_id:goods_id,tuanZ:tuanZ1},
               dataType:"json",
               success:function(data) {
                   if(data.code == 1){
                   		location.href = 'index.php?module=pi&p=pintuan&c=addgroup';
                   }else{
                    layer.msg('发布失败!');  
                   }
               },
             })
         
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