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
<link href="style/ssd1.css" rel="stylesheet" type="text/css" />
<script src="style/jquery-1.11.3.js"></script>
<script language="javascript"  src="style/ssd1.js"> </script>
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->

<title>快递信息</title>
{literal}
<style type="text/css">
	.row .form-label{
		width: 18%!important;
	}
</style>
{/literal}
</head>
<body style="width: 100%">
	<nav class="breadcrumb">
		插件管理
		<span class="c-gray en">&gt;</span>
		拼团活动
		<span class="c-gray en">&gt;</span>
		参数设置
	</nav>
<div class="page-container" style="margin: 10px;background-color:#fff">
	
		<div id="tab-category" class="HuiTab">
			<div class="tabCon">
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">拼团失败的退款方式：</label>
					<div class="formControls col-xs-8 col-sm-9">
						{foreach from=$arr item=item key=key}
						<div>
						<label for="{$key}">{$item}:</label>
						<input type="radio" id="{$key}" value="{$key}" name="refun" {if $key==$type}checked{/if} style="margin-left: 10px;" /> 
						
						</div>
						{/foreach}
					</div>
			<!-- 		<div class="col-3">
					</div> -->
				</div>
				
			
			</div>
		</div>
		<div class="row cl">
			<div class="col-9 " style="margin-left:20%">
				<input class="qd closeMask" style="height: 30px;line-height: 30px;font-size: 14px;width: 50px;" type="button" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
			</div>
		</div>
	
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
    var retype = $('input[name=refun]:checked').val();
    // alert(id);
   $.ajax({
      url:"index.php?module=go_group&action=config",
      type:"post",
      data:{retype:retype},
      dataType:"json",
      success:function (data) {
      	console.log(data);
        if(data.code == 1){
        	appendMask("修改成功！","cg")
          
		    var index = parent.layer.getFrameIndex(window.name);
		    parent.layer.close(index);

          }
      },
   });
});
			function closeMask1(){
				$(".maskNew").remove();
				 location.href="index.php?module=plug_ins";
			}
			function appendMask(content,src){
				$("body").append(`
						<div class="maskNew">
							<div class="maskNewContent" style="height:300px">
								<a href="javascript:void(0);" class="closeA" onclick=closeMask1() ><img src="images/icon1/gb.png"/></a>
								<div class="maskTitle">提示</div>	
								<div style="text-align:center;margin-top:30px"><img src="images/icon1/${src}.png"></div>
								<div style="height: 100px;position: relative;top:20px;font-size: 22px;text-align: center;">
									${content}
								</div>
								<div style="text-align:center;">
									<button class="closeMask" onclick=closeMask1() >确认</button>
								</div>
								
							</div>
						</div>	
					`)
			}
</script>
{/literal}
</body>
</html>