
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
    {php}include BASE_PATH."/modules/assets/templates/footer.tpl";{/php}
	<title>拼团参数</title>
	{literal}
		<style type="text/css">
			.btn1 {
				height: 42px !important;
				padding: 0px 10px;
				line-height: 42px !important;
				width: 112px !important;
				display: flex;
				justify-content: center;
				align-items: center;
				color: #6a7076;
				background-color: #fff;
				font-size: 16px;
				border-right: 1px solid #ddd!important;

			}

			.active1 {
				color: #fff;
				background-color: #62b3ff;
			}


			.swivch a:hover {
				text-decoration: none;
				background-color:#2481e5!important;;
				color: #fff;
			}

			td a {
				margin:5px;
				float: left;
				padding: 3px 5px;
				height:auto;
			}
			td button {
				margin:4px 0 0 0;
				float: left;
				background: white;
				color:  #DCDCDC;
				border: 1px #DCDCDC solid;
				width:56px;
				height:22px;
			}
			#btn2{
				height: 36px;
				background-color: #77c037!important;
			}
			#btn2:hover{
				background-color: #57a821!important;
				border:1px solid #57a821!important;
			}
			form{
				padding: 0 !important;
				background: none !important;
			}
			/*.config-box{*/
			/*background-color: #fff;*/
			/*padding: 10px;*/
			/*}*/
			.HuiTab{
				background-color: #fff;
				padding: 10px;
				margin-bottom: 20px;
			}
			.s-title{
				border-bottom: 2px #e0dfdf solid;
				height: 40px;
				line-height: 40px;
				padding: 5px 0 10px 20px;
				font-size: 16px;
				font-weight: bold;
				color: #666;
			}
			.row .form-label{
				width: 20% !important;
				height: 36px;
				line-height: 33px;
			}
			.unit{
				height: 36px;
				line-height: 33px;
			}
			.input-text[type="number"] {
				width: 350px !important;
			}
			.radio{
				margin: 0 20px;
			}
			.foot_row{
				height: 70px;
				border-top: 1px solid #E9ECEF;
				width: 100%;
				display: flex;
				justify-content: flex-end;
				align-items: center;
				position: fixed;
				bottom: 0;
				z-index: 99999;
				background: #fff;
			}
			.foot_row button{
				float: right;
				margin-right: 100px;
				width: 112px;
				height: 36px;
				border: none;
			}
		</style>
	{/literal}

</head>
<body>

<nav class="breadcrumb">
	拼团管理
	<span class="c-gray en">&gt;</span>
	拼团设置
</nav>

<div class="page-container pd-20 page_absolute" >

	<div style="display: flex;flex-direction: row;font-size: 16px;" class="page_bgcolor">
		<div class="status qh " style="border-radius: 2px 0px 0px 2px !important;"><a
					href="index.php?module=pi&p=pintuan&c=Home&status=0" onclick="statusclick(0)">拼团商品</a></div>
		<div class="status qh "><a href="index.php?module=pi&p=pintuan&c=Home&status=4"
															  onclick="statusclick(4)">开团记录</a></div>
		<div class="status qh "><a href="index.php?module=pi&p=pintuan&c=Home&status=5"
															  onclick="statusclick(5)">参团记录</a></div>
		<div class="status qh isclick" style="border-radius: 0px 2px 2px 0px !important;"><a
					href="index.php?module=pi&p=pintuan&c=config" onclick="statusclick(5)">拼团设置</a></div>
	</div>
	<div class="page_h16"></div>

	<form name="form1" action="index.php?module=pi&p=pintuan&c=config" class="form form-horizontal" method="post"   enctype="multipart/form-data" >
		<div class="config-box">

			<div id="tab-system" class="HuiTab">
				<div class="s-title">
					基础设置
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-4"><span style="color: red;">*</span>拼团限时：</label>
					<div class="formControls col-xs-8 col-sm-4">
						<input type="number" name="group_time" value="{$group_time}" class="input-text" placeholder="请输入显示时间，单位默认为（次）">
						<span class="unit">小时</span>
					</div>

				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-4"><span style="color: red;">*</span>开团限制：</label>
					<div class="formControls col-xs-8 col-sm-8">
						<input type="number" name="open_num" value="{$open_num}" class="input-text" placeholder="请输入限制时间，">
						<span class="unit">个</span>&nbsp;<span class="unit" style="color: #97A0B4;">（用户可开团的上限数量，拼团成功或拼团失败不计入开团数） </span>
					</div>
				</div>
				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-4"><span style="color: red;">*</span>参团限制：</label>
					<div class="formControls col-xs-8 col-sm-8">
						<input type="number" name="can_num" value="{$can_num}" class="input-text" placeholder="请输入限制次数，单位默认为（次）">
						<span class="unit">次</span>&nbsp;<span class="unit" style="color: #97A0B4;">（用户参团上限数量，拼团成功或拼团失败不计入参团数） </span>
					</div>
				</div>

				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-4" >是否允许重复参团：</label>
					<div class="formControls col-xs-8 col-sm-8" style="display: flex;">
						<div class="radio">
							<label style="margin-top: .5rem;">
								<input type="radio" name="can_again" id="optionsRadios1" value="1" {if $can_again == '1'}checked{/if}> 是
							</label>
						</div>
						<div class="radio">
							<label style="margin-top: .5rem;">
								<input type="radio" name="can_again" id="optionsRadios1" value="0" {if $can_again == '0'}checked{/if}> 否
							</label>
						</div>
						<div class="radio">
							<label style="margin-top: .6rem;color: #97A0B4;">
								<div>（默认针对同一件商品）</div>
							</label>
						</div>
					</div>
				</div>

				<div class="row cl">
					<label class="form-label col-xs-4 col-sm-4" >是否开启团长优惠：</label>
					<div class="formControls col-xs-8 col-sm-8" style="display: flex;">
						<div class="radio">
							<label style="margin-top: .5rem;">
								<input type="radio" name="open_discount" id="optionsRadios1" value="1" {if $open_discount == '1'}checked{/if}> 是
							</label>
						</div>
						<div class="radio">
							<label style="margin-top: .5rem;">
								<input type="radio" name="open_discount" id="optionsRadios1" value="0" {if $open_discount == '0'}checked{/if}> 否
							</label>
						</div>
					</div>
				</div>
			</div>
			<div id="tab-system" class="HuiTab">
				<div class="s-title">
					规则设置
				</div>

				<div class="row cl" style="margin-bottom: 40px;    display: flex;    justify-content: center;">
					<div class="formControls col-xs-8 col-sm-10 codes">
						<script id="editor" type="text/plain" name="rule" style="width:100%;height:400px;">{$rule}</script>
					</div>
				</div>
			</div>

			<div class="foot_row">
				<button class="btn btn-primary radius" type="submit" name="Submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
			</div>
		</div>
	</form>
</div>
</div>
<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:absolute;"><img id="bigimg" src="" /></div></div>


{literal}

	<script>
		$(function(){
			var ue = UE.getEditor('editor');

			$("input[name='imgurls[]']").on("change",function(e){
				var val = $(this).val();
				if (val.length > 0) {
					$('.form_new_file').css('display','none');
				}
			});

			$('.file-item-delete-pp').on('click',function(){
				$('.form_new_file').css('display','flex');
			})

		});
	</script>

{/literal}
</body>
</html>