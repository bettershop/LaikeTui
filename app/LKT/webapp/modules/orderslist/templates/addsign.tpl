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

	{literal}
		<script type="text/javascript">
            function check(f) {
                if (Trim(f.product_title.value) == "") {
                    alert("产品名称不能为空！");
                    f.product_title.value = '';
                    return false;
                }
                if (Trim(f.keyword.value) == "") {
                    alert("关键词不能为空！");
                    f.keyword.value = '';
                    return false;
                }
                if (Trim(f.sort.value) == "") {
                    alert("排序不能为空！");
                    f.sort.value = '';
                    return false;
                }
                f.sort.value = Trim(f.sort.value);
                if (!/^(([1-9][0-9]*)|0)(\.[0-9]{1,2})?$/.test(f.sort.value)) {
                    alert("排序号必须为数字，且格式为 ####.## ！");
                    f.sort.value = '';
                    return false;
                }
                return true;
            }
		</script>
	{/literal}
	<title>快递信息</title>
</head>
<body style="width: 100%">
<div class="page-container" style="margin: 0 60px 0 60px;">
	<form action="" method="post" class="form form-horizontal" id="form-category-add" enctype="multipart/form-data" onsubmit="return check(this);">
		<div id="tab-category" class="HuiTab">
			<div class="tabCon">
				<div class="row cl" >
					<input type="hidden" name="sNo" value="" class="order_id">
					<input type="hidden" name="trade" value="3">
					<label class="form-label col-xs-4 col-sm-3" >快递公司：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<form name="hh" action="" method="post" >
						    <span class="search">
							    <input class="ww" id="makeInput" autocomplete="off" onkeyup="setContent(this,event);" onfocus="setDemo(this,event)" type="text" placeholder="请选择或输入快递名称" style="width: 153px;height:23px;">
							    <select name="kuaidi" class="selectName" id="hh" style="display: none; width: 153px;margin-top: 1px;margin-left: 0px;" onkeyup="getfocus(this,event)" onclick="choose(this)" size="10" id ="num1">
									{foreach from=$express item = item name=f1}
										<option value="{$item->id}" >{$item->kuaidi_name}</option>
									{/foreach}
							    </select>
							</span>
						</form>
					</div>
					<div class="col-3">
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-3">快递单号：</label>
					<div class="formControls col-xs-8 col-sm-9">
						<input type="text" value="" name="danhao" placeholder="请输入正确的快递单号"/>
					</div>
				</div>
			</div>
		</div>
		<div class="row cl">
			<div class="col-9 " style="margin-left:40%">
				<input class="qd" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
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
        var id = $('input[name=sNo]').val(); // 订单号
        var express = $('select[name=kuaidi]').val(); // 快递公司id
        var express_name = $('select[name=kuaidi]').find("option:selected").text(); // 快递公司名称
        var courier_num = $('input[name=danhao]').val(); // 快递单号
        // var freight = $('input[name=yunfei]').val();
        var otype = '{/literal}{$otype}{literal}'; // 类型
        $.ajax({
            url:"index.php?module=orderslist&action=addsign",
            type:"post",
            data:{sNo:id,trade:3,express:express,courier_num:courier_num,otype:otype,express_name:express_name},
            dataType:"json",
            success:function (data) {
                console.log(data);
                if(data == 1){
                    window.parent.location.reload();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else if(data == 2){
                    alert('请选择快递公司');
                }else if(data == 3){
                    alert('请填写快递单号');
                }else{
                    alert('快递单号已存在，请勿重复');
                }
            },
        });
    });
</script>
{/literal}
</body>
</html>