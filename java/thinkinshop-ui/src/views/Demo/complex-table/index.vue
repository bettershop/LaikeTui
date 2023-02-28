<template>
  <div class="complex-table">
	<div id="app">
		<div class="card">
			<el-form ref="ruleForm" class="form-search" :rules="rules" :model="ruleForm" label-width="100px">
				<el-card class="box-card">
					<div slot="header" class="clearfix">
						<span>基本信息</span>
					</div>
					<el-row :gutter="20">
						<el-col :span="8">
							<el-form-item label="商品标题" prop="name">
								<el-input :disabled="is_see" v-model="ruleForm.name" placeholder="请输入商品标题"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item class="choose" label="商品副标题">
								<el-input :disabled="is_see" v-model="ruleForm.name" placeholder="请输入商品副标题"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="默认状态" prop="region">
								<el-select :disabled="is_see" v-model="ruleForm.region" :popper-append-to-body="false" placeholder="请选择默认状态">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
						</el-col>

					</el-row>
					<el-row :gutter="20">
						<el-col class="upload" :span="12">
							<el-form-item label="商品封面图" prop="imgUrls">
									<l-upload :limit="3" :disabled="is_see" v-model="ruleForm.imgUrls" text="（最多上传三张，建议上传1：1尺寸的图片）"></l-upload>
							</el-form-item>
						</el-col>
						<el-col class="upload" :span="12">
							<el-form-item label="商品封面图" prop="imgUrls">
									<l-upload :limit="1" :disabled="is_see" v-model="ruleForm.imgUrls" text="（最多上传一张，建议上传1：1尺寸的图片）"></l-upload>
							</el-form-item>
						</el-col>
					</el-row>
				</el-card>
				
				<el-card class="box-card">
					<div slot="header" class="clearfix">
						<span>商品属性</span>
					</div>
					<el-row :gutter="20">
						<el-col :span="8">
							<el-form-item label="成本价" prop="Cost_price">
								<el-input :disabled="is_see" v-model="ruleForm.Cost_price" clearable placeholder="请输入成本价"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="原价" prop="original_price">
								<el-input :disabled="is_see" v-model="ruleForm.original_price" clearable placeholder="请输入商品原价"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="售价" prop="price">
								<el-input :disabled="is_see" v-model="ruleForm.price" clearable placeholder="请输入商品售价"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="单位" prop="unit">
								<el-select :disabled="is_see" v-model="ruleForm.unit" placeholder="请选择单位">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col :span="8">
							<el-form-item label="库存" prop="stock">
								<el-input :disabled="is_see" v-model="ruleForm.stock" clearable placeholder="请输入库存"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-form-item label="默认状态">
						<div class="table-query">
							<div class="query-state">
								<el-select :disabled="is_see" v-model="ruleForm.unit" placeholder="请选择商品分类">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
								<el-select :disabled="is_see" v-model="ruleForm.unit" placeholder="请选择商品品牌">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
								<el-input :disabled="is_see" class="query-input" v-model="ruleForm.stock" clearable placeholder="请输入商品名称"></el-input>
								<el-button class="bgColor" type="primary">查询</el-button>
							</div>
							<el-table :data="tableData"  element-loading-text="拼命加载中..." v-loading="loading" ref="table" @selection-change="handleSelectionChange" class="el-table" style="width: 100%"
								:height="tableHeight">
								<el-table-column type="selection" width="55">
								</el-table-column>
								<el-table-column  prop="menuID" label="商品编号" width="150">
								</el-table-column>
								
								<el-table-column prop="goods_name" label="商品详情" width="400">
									<template slot-scope="scope">
										<div class="table-info">
											<el-image :preview-src-list="srcList" src="https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/a6051502404600.jpg" fit="fill"></el-image>
											<p>{{ scope.row.goods_name }}</p>
										</div>
									</template>
								</el-table-column>
								<el-table-column prop="menuName" label="规格" width="170">
								</el-table-column>
								
								<el-table-column prop="suID" label="店铺名称" width="170">
								</el-table-column>
								<el-table-column prop="stock" label="库存" width="170">
								</el-table-column>
								<el-table-column prop="stockyj" label="库存预警" width="170">
								</el-table-column>
								<el-table-column prop="price" label="零售价" width="170">
								</el-table-column>
							</el-table>
						</div>
					</el-form-item>
				</el-card>
			</el-form>
		</div>
		<div class="footer-button">
			<el-button plain class="footer-cancel fontColor" @click="Back">{{is_see?'返回':'取消'}}</el-button>
			<el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')" v-if="!is_see">保存</el-button>
		</div>
	</div>

	<div style="display: none">
		<input type="hidden" ref="param_id" value="{$id}">
		<input type="hidden" ref="param_see" value="{$see}" id="param-see">
	</div>
  </div>
</template>

<script>
async function customPhone(rule, value, callback) {
	let reg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/
	if (reg.test(value)) {
		callback()
	} else {
		callback(new Error('请输入正确的价格'));
	}
}
const tableData = []
for (var i = 0; i < 10; i++) {
	tableData.push({
		menuID: 'SH_' + i,
		img: 'http://xiaochengxu.houjiemeishi.com/V3/images/image_1/1610517329502.jpeg',
		goods_name: 'adidas 阿迪达斯 NEO板鞋2018夏季新款运动帆布鞋休闲鞋DA9530 DB009' + i,
		menuName: '白色+L' + i,
		is_switch: true,
		suID: '来客推',
		stock: 5 + i,
		stockyj: 6 + i,
		price: 45 + i,
		state: i
	})
}
export default {
    name: 'ComplexTable',

    data: function() {
		return {
			id: '',
			is_see: false,
			total: 20,
			pagesizes: [10, 25, 50, 100],
			pagination: {
				page: 1,
				pagesize: 10,
			},
			tableData: tableData,
			current_num: '',
			currpage: 1,
			tableHeight: null, //表格高度
			loading: true,
			srcList: ['http://xiaochengxu.houjiemeishi.com/V3/images/image_1/1610517329502.jpeg'], //大图预览
			is_scroll: false,//是否滚动完，数据加载完毕
			ruleForm: {
				name: '',
				region: '',
				Cost_price: '',
				original_price: '',
				price: '',
				unit: '',
				stock: '',
				imgUrls: ''
			},
			rules: {
				name: [{
					required: true,
					message: '请输入活动名称',
					trigger: 'blur'
				}, ],
				region: [{
					required: true,
					message: '请选择活动区域',
					trigger: 'change'
				}],
				Cost_price: [{
						required: true,
						message: '请输入成本价',
						trigger: 'blur'
					},
					{
						validator: customPhone,
						trigger: 'blur'
					}
				],
				original_price: [{
						required: true,
						message: '请输入原价',
						trigger: 'blur'
					},
					{
						validator: customPhone,
						trigger: 'blur'
					}
				],
				price: [{
						required: true,
						message: '请输入售价',
						trigger: 'blur'
					},
					{
						validator: customPhone,
						trigger: 'blur'
					}
				],
				unit: [{
					required: true,
					message: '请选择单位',
					trigger: 'change'
				}, ],
				stock: [{
					required: true,
					message: '请输入库存',
					trigger: 'blur'
				}, ],
				imgUrls: [{
					required: true,
					message: '请上传商品封面图',
					trigger: 'change'
				}, ]
			}
		}

	},

    mounted() {
		this.getHeight()
		this.current_num = this.tableData.length
		setTimeout(() => {
			this.loading = false
		}, 1000)
		//增加监听事件，窗口变化时得到高度。
		window.addEventListener('resize', this.getHeight(), false)

		this.$refs.table.bodyWrapper.addEventListener('scroll', (res) => { // 监听滚动事件
			const height = res.target
			const clientHeight = height.clientHeight // 表格视窗高度 即wraper
			const scrollTop = height.scrollTop // 表格内容已滚动的高度
			const scrollHeight = height.scrollHeight // 表格内容撑起的高度
			if(this.is_scroll) return
			if (this.currpage >= 2 && scrollHeight - (clientHeight + scrollTop) < 1) {
				// this.$message.warning('没有更多数据啦')
				this.is_scroll = true
				setTimeout(() => {
					this.loading = false
				}, 2000)
				return
			}
			if (scrollHeight - (clientHeight + scrollTop) < 1) {
				this.loading = true
				this.currpage++
				this.pagination.page++
				setTimeout(() => {
					this.tableData = this.tableData.concat(tableData)
				})

			}
			setTimeout(() => {
				this.loading = false
			}, 2000)
		}, true)
	},
	created() {
		// if (this.$refs.param_id.val()) {
		// 	this.ruleForm = {
		// 		name: '啦啦啦啦',
		// 		region: 'shanghai',
		// 		Cost_price: 10,
		// 		original_price: 15,
		// 		price: 20,
		// 		unit: 'beijing',
		// 		stock: 100,
		// 		imgUrls: [
		// 			'https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/1611832740846.jpg',
		// 			'https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/7/1611914432645.gif',
		// 			'https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/1611832737588.jpg'
		// 		]
		// 	}
		// }
		// if (this.$refs.param_see.val()) {
		// 	this.is_see = truec 
		// }
		// this.id = this.$refs.param("#param").val()


	},

    methods: {
		submitForm(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					alert('submit!');
				} else {
					console.log('error submit!!');
					return false;
				}
			});
		},
		Back() {
			window.history.back()
		},
		getHeight() {
			this.tableHeight = document.documentElement.clientHeight - 325
		},
		handleSelectionChange(val) {

		},
		//选择一页多少条
		handleSizeChange(e){
			console.log(e);
		},
	}
}
</script>

<style scoped lang="less">
#app {
	background-color: #edf1f5;
}

.box-card {
  margin-bottom: 20px;
}
.box-card .el-select {
  width: 100%;
}
.el-table th > .cell {
  text-align: center;
  font-size: 14px;
  font-weight: bold;
  color: #414658;
}
.el-table .cell {
  text-align: center;
  font-size: 14px;
  color: #414658;
  font-weight: 400;
}
.table-query .query-state {
  display: flex;
  align-items: center;
}
.table-query .query-state .el-select {
  width: 180px;
  margin-right: 10px;
}
.table-query .query-state .query-input {
  width: 280px;
  margin-right: 10px;
}
.el-table .table-time {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.el-table .table-time span {
  font-size: 14px;
  font-weight: 400;
  color: #414658;
}
.el-table .table-img .el-image {
  width: 60px;
  height: 60px;
}
.el-table .table-info {
  display: flex;
  align-items: center;
}
.el-table .table-info .el-image {
  width: 60px;
  height: 60px;
}
.el-table .table-info p {
  width: calc(100% - 10px - 60px);
  text-align: left;
  padding-left: 10px;
  font-size: 14px;
  font-weight: 400;
  color: #414658;
}
.el-table .table-state {
  font-size: 14px;
  font-weight: 400;
  color: #414658;
}
.el-table .OP-button {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}
.el-table .OP-button button {
  padding: 5px;
  height: 22px;
  background: #FFFFFF;
  border: 1px solid #D5DBE8;
  border-radius: 2px;
  font-size: 12px;
  font-weight: 400;
  color: #888F9E;
}
.el-table .OP-button button:hover {
  border: 1px solid #409eff;
  color: #409eff;
}
.el-table .OP-button button:hover i {
  color: #409eff;
}
.el-table .OP-button .OP-button-top {
  margin-bottom: 8px;
}
.el-table .OP-button .table-dropdown .el-dropdown-link {
  cursor: pointer;
  padding: 0 5px;
  height: 22px;
  border: 1px solid #D5DBE8;
  border-radius: 2px;
  margin-left: 10px;
  font-size: 12px;
  font-weight: 400;
  color: #888F9E;
}
.pageBox {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #FFFFFF;
  padding: 0 20px;
  height: 76px;
}
.pageBox .pageLeftText {
  font-size: 14px;
  color: #6A7076;
  font-weight: 300;
}
.pageBox .el-pagination {
  flex: 1;
  display: flex;
  align-items: center;
  padding: 0;
}
.pageBox .el-pagination__sizes {
  height: 36px!important;
  line-height: 36px!important;
}
.pageBox .el-pagination__sizes .el-input--mini .el-input__inner {
  height: 36px!important;
  line-height: 36px!important;
}
.pageBox .pageRightText {
  margin-right: auto;
  font-size: 14px;
  font-weight: 400;
  color: #6A7076;
}
.pageBox .btn-next,
.pageBox .btn-prev {
  padding: 0;
  width: 82px;
  height: 36px;
  border: 1px solid #D5DBE8;
  border-radius: 2px;
}
.pageBox .btn-prev {
  margin-right: 8px;
}
.pageBox .el-pager li {
  width: 36px;
  height: 36px;
  line-height: 36px;
  border: 1px solid #D5DBE8 !important;
  border-radius: 2px;
  color: #6A7076;
  margin-right: 8px;
}
.pageBox .el-pager li:hover {
  border: 1px solid #2890FF !important;
  color: #2890FF;
}
.pageBox .el-pager li.active {
  border: 1px solid #D5DBE8 !important;
  color: #FFFFFF !important;
  background: #2890FF;
}
.footer-button {
  position: fixed;
  /* left: 0; */
  right: 0;
  bottom: 40px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 15px 20px;
  border-top: 1px solid #E9ECEF;
  background: #FFFFFF;
  width: 300%;
  z-index: 10;
}
.footer-button button {
  width: 112px;
  height: 36px;
}

/deep/.el-card__header {
	color: #414658;
	font-size: 16px;
}

.upload {
	width: 100%;
}

/deep/.has-gutter {
	background-color: #f4f7f9;
	color: #414658;
	font-size: 14px;
	font-weight: bold;
}

/deep/.el-table thead{
	background-color: #f4f7f9;
}

/deep/.el-table_3_column_25 {
	padding: 0;
	height: 50px;
}

/deep/.el-table thead th {
	text-align: center;
}

/deep/.el-table tbody td {
	text-align: center;
}

/deep/.el-input__inner {
	border: 1px solid #d5dbe8;
}
/deep/.el-input__inner:hover {
	border: 1px solid #b2bcd4;
}
/deep/.el-input__inner:focus {
	border-color: #409eff;
}
/deep/.el-input__inner::-webkit-input-placeholder {
	color: #97a0b4;
}

/deep/.el-form-item__label {
	font-weight: normal;
}


/deep/.footer-cancel,.footer-save {
	width: 70px !important;
	height: 40px !important;
}

/deep/.el-form-item__label {
	color: #414658;
}

/deep/.el-form-item__label:before {
	color: #ff453d !important;
}

/deep/.el-input__inner {
	border: 1px solid #d5dbe8;
}
/deep/.el-input__inner:hover {
	border: 1px solid #b2bcd4;
}
/deep/.el-input__inner:focus {
	border-color: #409eff;
}
/deep/.el-input__inner::-webkit-input-placeholder {
	color: #97a0b4;
}

/deep/thead tr th {
	background-color: #f4f7f9;
	font-size: 14px;
	font-weight: bold;
	color: #414648;
}

/deep/ .el-table  {
	margin-top: 16px;
}

/deep/.el-card__body {
	padding: 40px 0 20px 20px;
}

/deep/.bgColor {
	background-color: #2890ff;
	
}
/deep/.mgleft {
	margin-left: 14px;
}
.bgColor:hover {
	opacity: 0.8;
}

.fontColor {
	color: #6a7076;
	border: 1px solid #d5dbe8;
	margin-left: 14px;
}
.fontColor:hover {
	color: #2890ff;
	border: 1px solid #2890ff;
}

/deep/.el-table th {
	padding: 0;
	height: 50px;
}

/deep/.el-input__inner {
	width: 420px;
}
/deep/.el-input__suffix {
	right: 10px;
}

/deep/.el-select {
	width: 420px !important;
}

/deep/.el-col {
	padding-left: 0px !important;
}

/deep/.el-table__row td .cell {
	color: #414658;
}

/deep/.el-row {
	margin-left: 0px !important;
}



</style>