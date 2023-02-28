<template>
  <div class="container">
	<el-radio-group fill="#2890ff" text-color="#fff" v-if="this.name === 'Super'" v-model="radio1">
		<el-radio-button :label="$t('DemoPage.tableExamplePage.TAB1')"></el-radio-button>
		<el-radio-button :label="$t('DemoPage.tableExamplePage.TAB2')"></el-radio-button>
		<el-radio-button :label="$t('DemoPage.tableExamplePage.TAB3')"></el-radio-button>
		<el-radio-button :label="$t('DemoPage.tableExamplePage.TAB4')"></el-radio-button>
	</el-radio-group>

	<div class="Search">
		<div class="Search-condition">
			<el-select v-model="value" clearable :placeholder="$t('DemoPage.tableExamplePage.Select_commodity_category')">
				<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<el-select v-model="value" clearable :placeholder="$t('DemoPage.tableExamplePage.Choose_a_Product_Brand')">
				<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<el-select v-model="value" clearable :placeholder="$t('DemoPage.tableExamplePage.Select_display_location')">
				<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<el-select v-model="value" clearable :placeholder="$t('DemoPage.tableExamplePage.Select_Product_Type')">
				<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<el-select v-model="value" clearable :placeholder="$t('DemoPage.tableExamplePage.Select_commodity_status')">
				<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
				</el-option>
			</el-select>
			<el-input v-model="input" size="medium" class="Search-input" :placeholder="$t('DemoPage.tableExamplePage.The_input')"></el-input>
			<el-button class="bgColor" type="primary">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
			<el-button class="fontColor">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
		</div>
		<el-button class="bgColor" type="primary">{{$t('DemoPage.tableExamplePage.export')}}</el-button>
	</div>

	<div class="card-body">
		<el-form ref="form" class="form-search" :model="form" label-width="90px">
			<el-row class="blockrow" :gutter="20">
				<div v-if="is_open">
					<el-col :span="8">
						<el-form-item :label="$t('DemoPage.tableExamplePage.time')">
							<el-date-picker type="datetime" clearable v-model="form.datetime" :placeholder="$t('DemoPage.tableExamplePage.Select_Add_Time')">
							</el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item :label="$t('DemoPage.tableExamplePage.state')">
							<el-select :placeholder="$t('DemoPage.tableExamplePage.Selection_state')" clearable v-model="form.state">
								<el-option label="区域一" value="shanghai"></el-option>
								<el-option label="区域二" value="beijing"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="8">
						<el-form-item :label="$t('DemoPage.tableExamplePage.Valid_time')">
							<el-date-picker type="datetime"  clearable v-model="form.time" :placeholder="$t('DemoPage.tableExamplePage.Select_effective_time')">
							</el-date-picker>
						</el-form-item>
					</el-col>
				</div>
				<el-col :span="8">
					<el-form-item :label="$t('DemoPage.tableExamplePage.Menu_Name')">
						<el-input v-model="form.name" :placeholder="$t('DemoPage.tableExamplePage.Enter_menu_name')"></el-input>
					</el-form-item>
				</el-col>
				<el-col :span="8" justify="center">
					<el-form-item :label="$t('DemoPage.tableExamplePage.Commodity_brand')">
						<el-select :placeholder="$t('DemoPage.tableExamplePage.Choose_a_Product_Brand')" clearable v-model="form.pp">
							<el-option label="区域一" value="shanghai"></el-option>
							<el-option label="区域二" value="beijing"></el-option>
						</el-select>
					</el-form-item>
				</el-col>
				<!-- :offset="is_open?16:''" -->
				<el-col :span="8" :align="is_open?'right':'left'">
					<div class="Search-Button">
						<div >
							<el-button class="bgColor" type="primary">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
							<el-button class="fontColor fontColors">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
							<el-button type="text" @click="Open">{{ Open_text }}<i :class="[is_open?'el-icon-arrow-up':'el-icon-arrow-down']"></i></el-button>
						</div>
						<el-button class="bgColor" type="primary">{{$t('DemoPage.tableExamplePage.export')}}</el-button>
					</div>
				</el-col>
			</el-row>
		</el-form>
		<div class="function-button">
			<el-button class="bgColor" type="primary" icon="el-icon-circle-plus-outline">{{$t('DemoPage.tableExamplePage.Add_menu')}}</el-button>
			<el-button class="fontColor" icon="el-icon-delete" :disabled="is_disabled">{{$t('DemoPage.tableExamplePage.Batch_delete')}}</el-button>
		</div>
		<el-table :data="tableData"  element-loading-text="拼命加载中..." v-loading="loading" ref="table" @selection-change="handleSelectionChange" class="el-table" style="width: 100%"
			:height="tableHeight">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column fixed sortable prop="menuID" :label="$t('DemoPage.tableExamplePage.menu_id')" width="150">
			</el-table-column>
			<el-table-column prop="img" :label="$t('DemoPage.tableExamplePage.picture')" width="120">
				<template slot-scope="scope">
					<div class="table-img">
						<el-image :src="scope.row.img" :preview-src-list="srcList"
							fit="cover"></el-image>
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="goods_name" :label="$t('DemoPage.tableExamplePage.Goods_details')" width="400">
				<template slot-scope="scope">
					<div class="table-info">
						<el-image src="https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/a6051502404600.jpg" fit="fill"></el-image>
						<p>{{ scope.row.goods_name }}</p>
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="menuName" :label="$t('DemoPage.tableExamplePage.menu_name')" width="120">
			</el-table-column>
			<el-table-column prop="is_switch" :label="$t('DemoPage.tableExamplePage.switch')" width="120">
				<template>
					<el-switch v-model="el_switch" active-color="#00ce6d" inactive-color="#d4dbe8">
					</el-switch>
				</template>
			</el-table-column>
			<el-table-column prop="suID" :label="$t('DemoPage.tableExamplePage.Belongs_to')" width="100">
			</el-table-column>
			<el-table-column :label="$t('DemoPage.tableExamplePage.Valid_time')" width="300">
				<template slot-scope="scope">
					<div class="table-time">
						<span>开始时间：{{ scope.row.start_time }}</span>
						<span>结束时间：{{ scope.row.end_time }}</span>
					</div>
				</template>
			</el-table-column>
			<el-table-column prop="add_time" label="添加时间" width="200">
			</el-table-column>
			<el-table-column label="状态" width="120">
				<template slot-scope="scope">
					<span v-if="scope.row.state==0" class="table-state">待审核</span>
					<span v-else-if="scope.row.state==1" class="table-state" style="color: #13CE66;">成功</span>
					<span v-else style="color: #FF453D;">失败</span>
				</template>
			</el-table-column>
			<el-table-column :label="$t('DemoPage.tableExamplePage.operation')" fixed="right" width="300">
				<template>
					<div class="OP-button">
						<div class="OP-button-top">
							<el-button icon="el-icon-edit-outline" @click="Edit">{{ $t('DemoPage.tableExamplePage.edit') }}</el-button>
							<el-button icon="el-icon-delete" @click="Delete">{{ $t('DemoPage.tableExamplePage.delete') }}</el-button>
						</div>
						<div class="OP-button-bottom">
							<el-button icon="el-icon-document-copy">{{ $t('DemoPage.tableExamplePage.copy') }}</el-button>
							<el-button icon="el-icon-view">{{ $t('DemoPage.tableExamplePage.look_junior') }}</el-button>
							<el-dropdown trigger="click" class="table-dropdown">
								<span class="el-dropdown-link">
									{{ $t('DemoPage.tableExamplePage.more') }}
									<i class="el-icon-arrow-down el-icon--right"></i>
								</span>
								<el-dropdown-menu slot="dropdown">
									<el-dropdown-item>余额充值</el-dropdown-item>
									<el-dropdown-item>积分修改</el-dropdown-item>
									<el-dropdown-item>等级修改</el-dropdown-item>
								</el-dropdown-menu>
							</el-dropdown>
						</div>
					</div>
				</template>
			</el-table-column>
		</el-table>
		<div class="pageBox">
			<div class="pageLeftText">显示</div>
			<el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页" @size-change="handleSizeChange"
				:page-sizes="pagesizes" :current-page="pagination.page" @current-change="handleCurrentChange" :total="total">
				<div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
			</el-pagination>
		</div>
	</div>
  </div>
</template>

<script>
const tableData = []
for(var i =0;i<10;i++){
	tableData.push({
		menuID:'SH_'+i,
		img:'http://xiaochengxu.houjiemeishi.com/V3/images/image_1/1610517329502.jpeg',
		goods_name:'adidas 阿迪达斯 NEO板鞋2018夏季新款运动帆布鞋休闲鞋DA9530 DB009'+i,
		menuName:'商户管理'+i,
		is_switch:true,
		suID:'SH_'+i,
		start_time:'2020-11-08 15:22:00',
		end_time:'2020-11-09 15:22:00',
		add_time:'2021-11-08 19:32:00',
		state:i
	})
}

export default {
    name: 'TableExample',
    data: function() {
        return {
            visible: false ,
            radio1:'标签页一',
            options: [
				{value: '选项1',label: '黄金糕'},
				{value: '选项2',label: '双皮奶'},
				{value: '选项3',label: '蚵仔煎'},
				{value: '选项4',label: '龙须面'},
				{value: '选项5',label: '北京烤鸭'}
			],
            value: '',
            input:'',
			tableData: tableData,
			el_switch:'',
			total:20,
			pagesizes: [10, 25, 50, 100],
			pagination: {
				page: 1,
				pagesize: 10,
			},
			currpage:1,
			current_num:'',
			is_disabled:true,
			tableHeight:null,//表格高度
			form:{
				datetime:'',
				time:'',
				state:'',
				name:'',
				pp:''
			},
			is_open:false,
			Open_text:'展开',
			loading:true,
			srcList:['http://xiaochengxu.houjiemeishi.com/V3/images/image_1/1610517329502.jpeg'],//大图预览
			parentVM: null
        }
    },
	computed: {
		name() {
			return this.$store.getters.name
		}
	},
	mounted () {
		this.getHeight()
		this.current_num = this.tableData.length
		setTimeout(() =>{
			this.loading = false
		},2000)
	    //增加监听事件，窗口变化时得到高度。
	    window.addEventListener('resize',this.getHeight(),false)
	},
	created() {
		//  this.parentVM = $(parent.window)[0].VM;
	},
	methods:{
		//选择一页多少条
		handleSizeChange(e){
			console.log(e);
		},
		//点击上一页，下一页
		handleCurrentChange(e){
			this.currpage = e
		},
		
		handleSelectionChange(val){
			if(val.length==0){
				this.is_disabled = true
			}else{
				this.is_disabled = false
			}
		},
		getHeight(){
			this.tableHeight=document.documentElement.clientHeight-325
		},
		Open(){
			if(this.is_open){
				this.is_open = false 
				this.Open_text = '展开'
			}else{
				this.is_open = true 
				this.Open_text = '收起'
			} 
		},
		Delete(id) {
		    if (id) {
		        // this.parentVM.dialog('删除提示', '是否确认删除', true);
		        // console.log(VM);
		        // VM.$data.dialogVisible = true
		        // console.log(VM);
		    } else {
		        // 批量删除
		    }
		},
		handleClose () {
		    // this.parentVM.$data.dialogVisible = false;
		},
		handleConfirm () {
		    // this.$message({
		    //     showClose: true,
		    //     message: '删除成功',
		    //     type: 'success'
		    // });
		    // setTimeout(() => {
		    //     this.parentVM.$data.dialogVisible = false;
		    // }, 1200)
		
		},
		Edit(){
			window.location.href = 'index.php?module=demo&action=ComplexFormDemo&id=1'
		}
  }
}
</script>

<style scoped lang="less">
/deep/.el-radio-group {
	.el-radio-button {
		width: 112px;
		height: 42px;
		&:not(:last-child) .el-radio-button__inner {
			border-right: 1px solid #dcdfe6;
		}
	}
	.el-radio-button__inner {
		color: #6a7076;
		display: block;
		font-size: 16px;
		width: 112px;
		height: 42px;
		border: none;
		&:hover {
			color: #2890ff;
		}
	}
}

/deep/.Search{
	display: flex;
	align-items: center;
	justify-content: space-between;
	background: #ffffff;
	border-radius: 4px;
	padding: 10px;
	margin: 16px 0 17px 0;
	.Search-condition{
		display: flex;
		align-items: center;
	}
    .Search-input{
		width: 280px;
		margin-right: 10px;
		input{
			height: 40px;
			line-height: 40px;
		}
    }
	.el-select{
		width: 180px;
		margin-right: 8px;
	}
	.bgColor {
		background-color: #2890ff;
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
		background-color: #fff;
	}
	.el-input__inner {
		border: 1px solid #d5dbe8;
	}
	.el-input__inner:hover {
		border: 1px solid #b2bcd4;
	}
	.el-input__inner:focus {
		border-color: #409eff;
	}
	.el-input__inner::-webkit-input-placeholder {
		color: #97a0b4;
	}
}

/deep/.function-button{
	margin-bottom: 15px;
	.bgColor {
		background-color: #2890ff;
	}
	.bgColor:hover {
		background-color: #70aff3;
	}
	.fontColor {
		color: #6a7076;
		border: none;
		height: 40px;
	}
}

/deep/.form-search{
	background: #FFFFFF;
	padding: 0 24px;
	margin: 10px 0;
	.Search-Button{
		display: flex;
		justify-content: space-between;
	}
	.el-select{
		width: 100%;
	}
	.el-date-editor.el-input{
		width: 100%;
	}
	.el-col{
		.el-form-item{
			margin: 12px 0;
		}
		.Search-Button{
			margin: 12px 0;
		}
	}
	.el-form-item__label {
		color: #414658;
	}
	.bgColor {
		background-color: #2890ff;
	}
	.bgColor:hover {
		opacity: 0.8;
	}
	.fontColor {
		color: #6a7076;
		border: 1px solid #d5dbe8;
		margin-left: 14px;
		background-color: #fff;
	}
	.fontColor:hover {
		color: #2890ff;
		border: 1px solid #2890ff;
	}
	.el-input__inner {
		border: 1px solid #d5dbe8;
	}
	.el-input__inner:hover {
		border: 1px solid #b2bcd4;
	}
	.el-input__inner:focus {
		border-color: #409eff;
	}
	.el-input__inner::-webkit-input-placeholder {
		color: #97a0b4;
	}

	.el-form-item__label {
		font-weight: normal;
	}

}

/deep/.el-table th > .cell {
    text-align: center;
	font-size: 14px;
	font-weight: bold;
	color: #414658;
}

/deep/.el-table .cell {
    text-align: center;
	font-size: 14px;
	color: #414658;
	font-weight: 400;
}

/deep/.el-table{
	.table-time{
		display: flex;
		flex-direction: column;
		align-items: center;
		span{
			font-size: 14px;
			font-weight: 400;
			color: #414658;
		}
	}
	.table-img{
		.el-image{
			width: 60px;
			height: 60px;
		}
	}
	.table-info{
		display: flex;
		align-items: center;
		.el-image{
			width: 60px;
			height: 60px;
		}
		p{
			width: calc(100% - 10px - 60px);
			text-align: left;
			padding-left: 10px;
			font-size: 14px;
			font-weight: 400;
			color: #414658;
		}
	}
	.table-state{
		font-size: 14px;
		font-weight: 400;
		color: #414658;
	}
	.OP-button{
		display: flex;
		flex-direction: column;
		align-items: flex-end;
		button{
			padding: 5px;
			height: 22px;
			background: #FFFFFF;
			border: 1px solid #D5DBE8;
			border-radius: 2px;
			font-size: 12px;
			font-weight: 400;
			color: #888F9E;
		}
		button:hover{
			border:1px solid rgb(64, 158, 255);
			color: rgb(64, 158, 255);
		}
		button:hover i{
			color: rgb(64, 158, 255);
		}
		.OP-button-top{
			margin-bottom: 8px;
		}
		.table-dropdown{
			.el-dropdown-link{
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
		}
	}
}

/deep/.pageBox{
	display: flex;
	align-items: center;
	justify-content: space-between;
	background: #FFFFFF;
	padding: 0 20px;
	height: 76px;
	.pageLeftText{
		font-size: 14px;
		font-weight: 400;
		color: #6A7076;
	}
	.el-pagination {
	    flex: 1;
	    display: flex;
	    align-items: center;
	    padding: 0;
	}
	.el-pagination__sizes{
		height: 36px!important;
		line-height: 36px!important;
		.el-input--mini .el-input__inner{
			height: 36px!important;
			line-height: 36px!important;
		}
	}
	.pageRightText {
	    margin-right: auto;
		font-size: 14px;
		font-weight: 400;
		color: #6A7076;
	}
	.btn-next,.btn-prev{
		padding: 0;
		width: 82px;
		height: 36px;
		border: 1px solid #D5DBE8;
		border-radius: 2px;
	}
	.btn-prev{
		margin-right: 8px;
	}
	
	.el-pager li{
		width: 36px;
		height: 36px;
		line-height: 36px;
		border: 1px solid #D5DBE8!important;
		border-radius: 2px;
		color: #6A7076;
		margin-right: 8px;
	}
	.el-pager li:hover{
		border: 1px solid #2890FF!important;
		color: #2890FF;
	}
	.el-pager li.active{
		border: 1px solid #D5DBE8!important;
		color: #FFFFFF!important;
		background: #2890FF;
	}

	.el-pager {
		li {
			font-weight: 400;
		}
	}
	.btn-next {
		color: #6A7076;
	}

	.btn-next:hover {
		color: #2890FF;
	}

}

/deep/ .el-table .OP-button button {
	line-height: 9px;
}
/deep/ .el-table .OP-button .table-dropdown .el-dropdown-link {
	padding: 2px 5px;
}

.form-search,.el-table {
	border-radius: 4px;
}

// /deep/tbody .el-checkbox::after {
// 	content: '';
// 	display: block;
// 	width: 10px;
// 	height: 10px;
// 	background-color: red;
// 	border-radius: 50px;
// }


</style>