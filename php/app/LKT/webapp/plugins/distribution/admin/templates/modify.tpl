<!DOCTYPE HTML>
<html>

<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	<link rel="Bookmark" href="/favicon.ico">
	<link rel="Shortcut Icon" href="/favicon.ico" />

	<link rel="stylesheet" type="text/css" href="style/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="style/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="style/css/style.css" />
	<link rel="stylesheet" type="text/css" href="style/kindeditor/themes/default/default.css" />

	<script src="style/lib/jquery/1.9.1/jquery.js"></script>
	<script type="text/javascript" src="style/lib/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="style/js/H-ui.min.js"></script>

	<script src="style/js/vue.min.js"></script>
	
	<title>添加分销商品</title>

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
	<nav class="breadcrumb" style="font-size: 16px;">
		<i class="Hui-iconfont">&#xe6ca;</i>
		插件管理
		<span class="c-gray en">&gt;</span>
		<a style="margin-top: 10px;" onclick="location.href='index.php?module=pi&p=distribution&c=goods&status=2';">分销</a>
		<span class="c-gray en">&gt;</span>
		编辑分销商品
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
							<span style="color: #FF0404;">*</span>分销层级：
						</label>

						<div class="formControls col-10">

							<select name="pets" id="pet-select" @change="selects" style="width:340px;" v-model="selectdefault">
								<option value="">请选择向上返佣的层级</option>
								<option value="1">1级</option>
								<option value="2">2级</option>
								<option value="3">3级</option>
							</select>

						</div>

						<div class="formControls col-10">
							<div style="width:400px;background:rgba(244,247,249,1);border-radius:2px;padding: 1px 15px;margin: 10px 0px;" v-if="lvlist.length">

								<div v-for="(item,i) in lvlist" :key="i" style="margin: 14px 0px;">
									<span style="font-size:14px;font-weight:400;color:rgba(65,70,88,1);">{{ item.text }}:</span>
									<input type="text" class="input-text" style="height: 36px;width:190px;background:rgba(255,255,255,1);border:1px solid rgba(213,219,232,1);border-radius:2px;" v-model="item.name">
									<span style="font-size:14px;font-weight:400;color:rgba(65,70,88,1);">%</span>
								</div>
							
							</div>
						</div>
					</div>

					<div class="col-12 row">
						<label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">
							<span style="color: #FF0404;">*</span>佣金发放时间：
						</label>

						<div class="formControls col-10" style="display: flex;">
							<div>
								<input type="radio" id="dewey" name="type" value="2" checked @click="radiosa">
								<label for="dewey">确认收货</label>
							</div>
						</div>
					</div>

					<div class="col-12 row">

						<label class="form-label col-2" style="margin-top: 0px;padding-right: 0px!important;height: 36px;line-height: 36px;">
							<span style="color: #FF0404;">*</span>佣金手续费：
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

							<div v-if="isshowtyoeb">
								<input type="radio" name="drone" value="1" checked @click="radios">
								<label for="dewey">是</label>
							</div>
							<div v-else>
								<input type="radio" name="drone" value="1" @click="radios">
								<label for="dewey">是</label>
							</div>

							<div v-if="!isshowtyoeb" style="margin-left: 20px;">
								<input type="radio" name="drone" value="0" checked @click="radios">
								<label for="louie">否</label>
							</div>
							<div v-else style="margin-left: 20px;">
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
									<label for="dewey">分销商品</label>
								</div>
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
				isshowtyoe: true,
				is_show1: false,
				is_show2: false,
				is_show3: false,
				commissions: 0,
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
						url: "index.php?module=pi&p=distribution&c=modify&m=ajaxmodify&id=" + this.getQueryVariable('id'),
						async: false,
						success: function (res) {
							res = JSON.parse(res)
							if(res.code === 200){

								let attributes = res.data[0]
								let { commissions, leve, is_show } = attributes

								vm.selectdefault = leve
								vm.leve = leve
								vm.commissions = commissions

								if(leve){
									for(let i = 0; i < leve;i++){

										vm.lvlist.push({
											text: i + 1 + '级佣金比例',
											name:attributes['leve'+(i + 1)]
										})

									}
								}

								if(is_show == "0"){
									vm.isshowtyoe = false
									vm.isshowtyoeb = false
								} else {
									is_show = is_show.split(',')

									for(let item of is_show){
										vm['is_show'+item] = true
									}

								}

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
						for(let i = 0; i < value;i++){

							this.lvlist.push({
								text: i + 1 + '级佣金比例',
								name:''
							})

						}
					}
				},
				baocungroup(){

					let data = {}
					if(this.commissions != 0){
						data['commissions'] = this.commissions
					} else {
						layer.msg('请输入佣金手续费！')
						return
					}

					if(this.isshowtyoe){
						let isshowlist = []
						if(this.is_show1){
							isshowlist.push(1)
						}

						if(this.is_show2){
							isshowlist.push(2)
						}

						if(this.is_show3){
							isshowlist.push(3)
						}
						isshowlist.push(4)
						if(!isshowlist.length){
							layer.msg('请选择显示位置！')
							return
						}
						data['is_show'] = isshowlist.join(',')
					} else {
						data['is_show'] = '0'
					}

					data['leve'] = this.leve
					data['type'] = this.type

					for(let i in this.lvlist){
						let s =  parseInt(i) + 1
						data['leve' + s] = this.lvlist[i].name
					}

					$.ajax({
							url:'index.php?module=pi&p=distribution&c=modify&m=baocun',
							method:'POST',
							data:{
								id: this.getQueryVariable('id'),
								leve: data.leve,
								leve1: data.leve1,
								leve2: data.leve2 || 0,
								leve3: data.leve3 || 0,
								is_show: data.is_show,
								commissions: data.commissions,
								type: data.type
							},
							success:function(res){
								res = JSON.parse(res)
								if (res.code == 200) {

									layer.msg(res.message);

									setTimeout(function () {
										location.href = 'index.php?module=pi&p=distribution&c=goods'
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
