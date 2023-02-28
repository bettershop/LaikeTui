<!DOCTYPE HTML>
<html>

<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	<link rel="stylesheet" type="text/css" href="style/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="style/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="style/css/style.css" />
	<link rel="stylesheet" type="text/css" href="style/kindeditor/themes/default/default.css" />

	<script src="style/lib/jquery/1.9.1/jquery.js"></script>
	<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="style/js/H-ui.min.js"></script>

	<script src="style/js/vue.min.js"></script>
	
	<title>编辑商品</title>

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

	</style>
	{/literal}
</head>

<body>
{literal}
<div id="app">
	<nav class="breadcrumb">
		<a href="index.php?module=pi&p=score&c=Home" >积分商城</a> <span class="c-gray en">&gt;</span>
		编辑商品
	</nav>

	<div id="addpro" class="pd-20" style="background-color: white;">
		<p style="font-size: 15px;" class="page_title">
			编辑商品
		</p>
		<div>
			<form name="form1" id="form1" class="form-horizontal" method="post" enctype="multipart/form-data">

				<div class="row cl">



					<div class="col-12 row">

						<label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">
							<span style="color: #FF0404;">*</span>积分：
						</label>

						<div class="formControls col-10">
							<input type="number" class="input-text" style="width:190px;height:36px;background:rgba(255,255,255,1);border:1px solid rgba(213,219,232,1);border-radius:2px;" v-model="score">

						</div>
					</div>

					<div class="col-12 row">
						<label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">
							是否显示：
						</label>

						<div class="formControls col-10" style="display: flex;">

							<div>
								<input type="radio" name="is_show" v-model="is_show" value="1" checked @click="radios">
								<label for="dewey">是</label>
							</div>
							<div>
								<input type="radio" name="is_show" v-model="is_show" value="0" @click="radios">
								<label for="dewey">否</label>
							</div>

						</div>


					</div>
				</div>
			</form>
		</div>
		<div class="page_h10">
			<input class="fo_btn2" type="button" name="Submit" value="保存" @click="baocungroup">
			<input type="button" name="reset" value="取消" class="fo_btn1" onclick="javascript :history.back(-1);">
		</div>
	</div>
</div>
{/literal}
	{literal}
	<script type="text/javascript">
		let vm = new Vue({
			el:'#app',
			data:{
				selectdefault:'1',
				istype: true,
				splist: [],
				total: 0,
				checkboxlist: [],
				lvlist: [],
				is_show: 1,
				score: 0,
				type: 2,
				leve: 0,
				isshowtyoeb:true
			},
			created(){

			},
			mounted(){
				this.getCommodityList()
			},
			methods:{
				getQueryVariable(variable){
					var query = window.location.search.substring(1);
					var vars = query.split("&");
					for (var i=0;i<vars.length;i++) {
						var pair = vars[i].split("=");
						if(pair[0] == variable){return pair[1];}
					}
       				return(false);
				},
				chandchick(is){
					this.istype = is
				},
				// 获取商品列表
				getCommodityList(){
					let vm = this
					$.ajax({
						url: "index.php?module=pi&p=score&c=modify&m=ajaxmodify&id=" + this.getQueryVariable('id'),
						async: false,
						success: function (res) {
							res = JSON.parse(res)
							if(res.code === 200){

								let attributes = res.data[0]
								let {  score, is_show } = attributes

								vm.is_show = is_show
								vm.score = score


							}
						}
					})
				},
				checkboxs(event,i){
					if(event.path[0].checked){
						this.checkboxlist.push(this.splist[i])
					} else {
						this.checkboxlist.splice(i,1)
					}
				},
				selects(ele){
					let value = parseInt(ele.target.value)
					this.lvlist = []
					this.leve = value
					if(value){

					}
				},
				baocungroup(){

					let data = {}
					if(this.score.toString() == ''){
						layer.msg('请输入积分！')
						return
					}

					data['score'] = this.score
					data['is_show'] = this.is_show

					$.ajax({
							url:'index.php?module=pi&p=score&c=modify&m=insert',
							method:'POST',
							data:{
								id: this.getQueryVariable('id'),
								is_show: data.is_show,
								score: data.score
							},
							success:function(res){
								res = JSON.parse(res)
								if (res.code == 200) {

									layer.msg(res.message);

									setTimeout(function () {
										location.href = 'index.php?module=pi&p=score&c=Home'
									}, 800)

								} else if (res.status == 1) {
									layer.msg(res.info);
								} else if (res.status == 2) {
									layer.msg(res.info);
								}
							}
					})

				},
				radios(ele){
					if(ele.target.value == '0'){
						this.isshowtyoe = false
					} else {
						this.isshowtyoe = true
					}
				},
				radiosa(ele){
					this.type = ele.target.value
				},
				deletes(i){
					this.checkboxlist[i].is = false
					this.checkboxlist.splice(i,1)
				},
				checkall(ele){
					let is = ele.target.checked
					if(is){

						for(let item of this.splist){
							item.is = true
						}

						this.checkboxlist.push(...this.splist)

					} else {
						for(let item of this.splist){
							item.is = false
						}

						this.checkboxlist =[]
					}
				}
			}
		})

	</script>

	{/literal}
</body>

</html>
