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
<link href="style/ssd1.css" rel="stylesheet" type="text/css" />
<script src="style/jquery-1.11.3.js"></script>
<script language="javascript"  src="style/ssd1.js"> </script>


<title>快递信息</title>
</head>
<body style="width: 100% ;    display: flex;align-items: center;">
<div class="page-container" style="margin: 0 auto;">

	{if $res ==1 }
	 <p style=" text-align: center;height: 100%;">未查询到物流信息</p>
	{else}
	{foreach from=$res item="item" key="key"}
		<div class='time' style="margin-left: 30px;">{$item->time}</div>
        <div class='step' style="font-size: 0.5rem; padding: 5px 20px;    margin-left: 30px;">{$item->context}</div>
	{/foreach}
	{/if}

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
{literal}
<script type="text/javascript">
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
$(".qd").click(function(){
    var id = $('input[name=sNo]').val();
    var express = $('select[name=kuaidi]').val();
    var express_name = $('select[name=kuaidi]').find("option:selected").text();
    var courier_num = $('input[name=danhao]').val();
    var freight = $('input[name=yunfei]').val();
    var otype = '{/literal}{$otype}{literal}';
   $.ajax({
      url:"index.php?module=orderslist&action=addsign",
      type:"post",
      data:{sNo:id,trade:3,express:express,courier_num:courier_num,freight:freight,otype:otype,express_name:express_name},
      dataType:"json",
      success:function (data) {
      	console.log(data);
        if(data == 1){
           window.parent.location.reload();
		    var index = parent.layer.getFrameIndex(window.name);
		    parent.layer.close(index);

          }
      },
   });
});
</script>
{/literal}
</body>
</html>