<!DOCTYPE HTML>
<html>

<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	{php}include BASE_PATH."/modules/assets/templates/top.tpl";{/php}
	<link rel="stylesheet" type="text/css" href="style/css/bootstrap.min.css" />
	<script src="style/lib/jquery/1.9.1/jquery.js"></script>
	<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="style/js/H-ui.min.js"></script>
	<script src="style/js/vue.min.js"></script>
	
	<title>添加积分商品</title>

	
	{literal}
	<style type="text/css">
		.page_h10 {
			height: 68px!important;
			margin-top: 140px;
			border-top: 1px solid #E9ECEF;
			position: fixed;
			z-index: 1;
			bottom: 0;
			width: 99%;
			background: #ffffff;
		}

		.form-horizontal {
			margin-bottom: 10px;
			padding: 10px 10px 0 10px !important;
		}

		.modalform-btn {
			width:60px;
			height:36px;
			padding: 0;
			background:rgba(40,144,255,1);
			border-radius:2px;
			color:rgba(255,255,255,1);
		}

		.tabls  tbody {
      display:block;
      height:240px;
      overflow-y:overlay;
    }
    .tabls  thead, .tabls tbody tr {
      display:table;
      width:100%;
      table-layout:fixed;
			padding:0;
    }

		.tfoots {
			height:60px;
			background:rgba(255,255,255,1);
			border:1px solid rgba(233,236,239,1);
			border: 1px solid rgba(233,236,239,1);
			display: flex;
    	justify-content: space-between;
			align-items: center;
			padding:0px 23px;
		}

		.boxfoot {
			max-width:1460px;
			border-radius: 2px;
    	background: rgba(255,255,255,1);
			border:1px solid rgba(233,236,239,1);
			border-radius:2px;
		}

		.tfoots-btn {
			width:56px;
			height:36px;
			background:rgba(40,144,255,1);
			border-radius:2px;
			font-size:14px;
			font-weight:400;
			color:rgba(255,255,255,1);
			display: flex;
			align-items: center;
			justify-content: center;
		}

		.classss {
			width:195px;
			height:36px;
			background:rgba(255,255,255,1);
			border:1px solid rgba(213,219,232,1);
			border-radius:2px;
		}

		.classinput {
			width:346px;
			height:36px;
			background:rgba(255,255,255,1);
			border:1px solid rgba(213,219,232,1);
			border-radius:2px;
		}

		.acisx {
			width:112px;
			height:42px;
			background:rgba(40,144,255,1);
			border-radius:0px 2px 2px 0px;
			font-size:16px;
			font-weight:400;
			color:rgba(255,255,255,1);
		}

		.scisxs {
			width:112px;
			height:42px;
			background:rgba(237,241,245,1);
			border:1px solid rgba(213,219,232,1);
			border-radius:2px 2px 2px 0px;
			font-size:16px;
			font-weight:400;
			color:rgba(106,112,118,1);
		}

		.loadding {
			position:fixed;
			top: 0;
			width:100%;
			min-height:100vh;
			z-index: 999;
    	background: #ffffff;
		}
		.loadEffect{
			width: 100px;
			height: 100px;
			position: relative;
			margin: 0 auto;
			margin-top:100px;
		}
		.loadEffect span{
				display: inline-block;
				width: 16px;
				height: 16px;
				border-radius: 50%;
				background: #2890FF;
				position: absolute;
				-webkit-animation: load 1.04s ease infinite;
		}
		@-webkit-keyframes load{
				0%{
						opacity: 1;
				}
				100%{
						opacity: 0.2;
				}
		}
		.loadEffect span:nth-child(1){
				left: 0;
				top: 50%;
				margin-top:-8px;
				-webkit-animation-delay:0.13s;
		}
		.loadEffect span:nth-child(2){
				left: 14px;
				top: 14px;
				-webkit-animation-delay:0.26s;
		}
		.loadEffect span:nth-child(3){
				left: 50%;
				top: 0;
				margin-left: -8px;
				-webkit-animation-delay:0.39s;
		}
		.loadEffect span:nth-child(4){
				top: 14px;
				right:14px;
				-webkit-animation-delay:0.52s;
		}
		.loadEffect span:nth-child(5){
				right: 0;
				top: 50%;
				margin-top:-8px;
				-webkit-animation-delay:0.65s;
		}
		.loadEffect span:nth-child(6){
				right: 14px;
				bottom:14px;
				-webkit-animation-delay:0.78s;
		}
		.loadEffect span:nth-child(7){
				bottom: 0;
				left: 50%;
				margin-left: -8px;
				-webkit-animation-delay:0.91s;
		}
		.loadEffect span:nth-child(8){
				bottom: 14px;
				left: 14px;
				-webkit-animation-delay:1.04s;
		}

		.acisx:active, .acisx:focus{
			background-color: rgba(40,144,255,1) !important;
			background-image: none;
			border-color: rgba(40,144,255,1) !important;
		}

		.scisxs:active, .scisxs:focus {
			background-color: rgba(237,241,245,1) !important;
			background-image: none;
			border-color: rgba(237,241,245,1) !important;
		}

		



	</style>
	{/literal}
</head>

<body>
{literal}
<div class="loadding">
	<div class="loadEffect">
			<span></span>
			<span></span>
			<span></span>
			<span></span>
			<span></span>
			<span></span>
			<span></span>
			<span></span>
	</div>
</div>
<div id="app">
	<nav class="breadcrumb">
		<a href="index.php?module=pi&p=score&c=Home" >积分商城</a> <span class="c-gray en">&gt;</span>
		添加商品
	</nav>

	<div id="addpro" class="pd-20" style="background-color: white;">
		<p style="font-size: 15px;" class="page_title">
			添加商品
		</p>
		<div style="height: 600px;overflow: overlay;">
			<form name="form1" id="form1" class="form-horizontal" method="post" enctype="multipart/form-data" style="height: 1000px;overflow: hidden;">

				<div class="row cl">
					<div class="col-12 row" style="margin-bottom: 14px;">
						<label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">
							<span style="color: #FF0404;">*</span>积分商品：
						</label>

						<div class="formControls col-10" style="display: flex;">
							<button type="button" class="btn btn-info" :class=" istype?'acisx':'scisxs' " @click="chandchick(true)">选择商品</button>
							<button type="button" class="btn btn-info" :class=" !istype?'acisx':'scisxs' " @click="chandchick(false)">已选商品</button>
						</div>

						<div class="formControls col-10" v-show="istype">
							<div style="margin: 14px 0px;">
									<form id="modalform">
										<div class="row" style="display: flex;padding-left: 15px;">
											<div>
												<select class="select classss" size="1" name="my_class" id="spfl" style="height: 36px;width:195px;">
												</select>
											</div>

											<div>
												<select class="select classss" size="1" name="my_brand" id="sppp" style="height: 36px;width:195px;">
												</select>
											</div>

											<div>
												<input name="pro_name" type="text" class="input-text classinput" style="height: 36px;width:346px;" placeholder="请输入商品名称">
											</div>

											<div>
												<button type="button" class="btn modalform-btn" @click="search()">查询</button>
											</div>
										</div>
									</form>
							</div>

							<div class="boxfoot">
								<table class="table table-hover tabls">
									<thead>
										<tr>
											<th><input type="checkbox" id="checkbox-1" style="visibility: initial;" @click="checkall" ></th>
											<th>商品名称</th>
											<th>商品ID</th>
											<th>价格</th>
											<th>库存</th>
										</tr>
									</thead>
									<tbody @scroll="onScroll" ref="nameScroll">
											<tr v-for="(item,i) in splist" :key="i">
												<td>
													<input type="checkbox" id="checkbox-1" style="visibility: initial;" v-model="item.is" :value="i" @click="checkboxs($event,i,item.id)">
												</td>
												<td>
													<div>
														<img :src="item.image" style="width: 40px;height:40px;">
														{{  item.product_title}}
													</div>
												</td>
												<td>
													{{ item.id }}
												</td>
												<td>{{ item.price }}</td>
												<td>{{ item.num }}</td>
											</tr>

											<tr style="display: flex;align-items: center;justify-content: center;">
												<td style="border-top: 0px;background: #ffffff01;">{{ loaddingtext }}</td>
											</tr>
											<tr v-if="splist.length == 0" style="height: 27vh;display: flex;align-items: center;justify-content: center;">
												<td style="border-top: 0px;background: #ffffff01;">暂无商品</td>
											</tr>
									</tbody>
								</table>
								<div class="tfoots">
									<div style="display: flex;align-items: center;">
										<span style="font-size:14px;font-weight:400;color:rgba(65,70,88,1);">
											已选
												<span style="color:#2890FF;font-weight:Bold;"> {{ checkboxlist.length }} </span>
											款商品
										</span>
									</div>

									<div>
										<span style="font-size:14px;font-weight:400;color:rgba(65,70,88,1);">
											共款
											<span style="font-weight:Bold;">
												{{ total }}
											</span>
											商品
										</span>
									</div>
								</div>
							</div>

						</div>

						<div class="formControls col-10" v-show="!istype">

							<div class="boxfoot" style="margin-top: 14px;">
								<table class="table table-hover tabls">
									<thead>
										<tr>
											<th style="text-align: center;">商品名称</th>
											<th style="text-align: center;">商品ID</th>
											<th style="text-align: center;">价格</th>
											<th style="text-align: center;">库存</th>
											<th style="text-align: center;">操作</th>
										</tr>
									</thead>
									<tbody>

										<tr v-for="(item,i) in checkboxlist" :key="i">
											<td>
												<div>
													<img :src="item.image" style="width: 40px;height:40px;">
													{{  item.product_title}}
												</div>
											</td>

											<td style="text-align: center;">
												{{ item.id }}
											</td>

											<td style="text-align: center;">
												{{ item.price }}
											</td>
											<td style="text-align: center;">
												{{ item.num }}
											</td>
											<td style="display: flex;justify-content: center;">
												<a title="删除" href="javascript:;" data-g_status="1" style="width: 56px;height: 22px;display: flex;justify-content: center;align-items: center;" @click="deletes(i)">
													<img src="images/icon1/del.png" style="width: 13px;height:13px;margin-right: 0px;">&nbsp;删除
												</a>
											</td>
										</tr>
										<tr v-if="checkboxlist.length == 0" style="height: 27vh;display: flex;align-items: center;justify-content: center;">
											<td style="border-top: 0px;background: #ffffff01;">暂未选择商品</td>
										</tr>
									</tbody>
								</table>
							</div>

						</div>
					</div>

					
					<div class="col-12 row">

						<label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">
							<span style="color: #FF0404;">*</span>消耗积分：
						</label>

						<div class="formControls col-10">
							<input type="number" class="input-text" style="width:190px;height:36px;background:rgba(255,255,255,1);border:1px solid rgba(213,219,232,1);border-radius:2px;" v-model="commissions">
							<span>%</span>
						</div>
					</div>

					<div class="col-12 row">
						<label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">
							是否显示：
						</label>

						<div class="formControls col-10" style="display: flex;">
							<div>
								<input type="radio" name="drone" value="1" checked @click="radios">
								<label for="dewey">是</label>
							</div>

							<div style="margin-left: 20px;">
								<input type="radio" name="drone" value="0" @click="radios">
								<label for="louie">否</label>
							</div>
						</div>

						<div class="formControls col-10" v-if="isshowtyoe">

							<div style="width:400px;height:44px;background:rgba(244,247,249,1);border-radius:2px;display: flex;align-items: center;justify-content: space-around;">
							
								<span>
									显示位置:
								</span>
									
								<div>
									<input type="checkbox" id="dewey" v-model="is_show1" style="visibility: visible;">
									<label for="dewey">热销单品</label>
								</div>
								<div>
									<input type="checkbox" id="dewey" v-model="is_show2" style="visibility: visible;">
									<label for="dewey">购物车</label>
								</div>
									<div>
									<input type="checkbox" id="dewey" v-model="is_show3" style="visibility: visible;">
									<label for="dewey">个人中心</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="page_h10" style="margin-top: 0px;">
			<input class="fo_btn2" type="button" name="Submit" value="保存" @click="baocungroup">
			<input type="button" name="reset" value="取消" class="fo_btn1" onclick="javascript :history.back(-1);">
		</div>
	</div>
</div>
{/literal}
	
</body>

</html>