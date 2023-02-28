<template>
  <div class="universal-module">
    <el-main>
				<el-form :model="ruleForm" ref="ruleForm" label-width="115px" class="demo-ruleForm">
					<el-form-item label="单选框" prop="resource">
						<el-radio-group v-model="ruleForm.resource">
							<el-radio label="1">选中</el-radio>
							<el-radio label="2">未选中</el-radio>
						</el-radio-group>
					</el-form-item>

					<el-form-item label="多选框" prop="type">
						<el-checkbox-group v-model="ruleForm.type">
							<el-checkbox label="1" name="type">选中</el-checkbox>
							<el-checkbox label="2" name="type">选中</el-checkbox>
							<el-checkbox label="3" name="type">未选中</el-checkbox>
						</el-checkbox-group>
					</el-form-item>

					<el-form-item label="开关" prop="switch">
						<el-switch v-model="ruleForm.switch1" active-color="#13ce66">
						</el-switch>
						<el-switch v-model="ruleForm.switch2" active-color="#13ce66">
						</el-switch>
					</el-form-item>

					<el-form-item label="分页" prop="page">
						<l-pagination :pagination="pagination" @pagechange="changePage"></l-pagination>
					</el-form-item>

					<el-form-item label="时间" prop="time">
					<l-upload :limit="3" v-model="ruleForm.imgUrls" text="（最多上传三张，建议上传1：1尺寸的图片）"></l-upload>
					</el-form-item>

					<el-form-item label="时间" prop="time">
						<el-date-picker v-model="ruleForm.time" type="datetimerange" range-separator="至" start-placeholder="开始日期"
						 end-placeholder="结束日期">
						</el-date-picker>
					</el-form-item>

					<el-form-item label="标签页" prop="tabs">
						<el-radio-group v-model="ruleForm.tabs">
							<el-radio-button label="1">库存列表</el-radio-button>
							<el-radio-button label="2">库存预警</el-radio-button>
							<el-radio-button label="3">入货详情</el-radio-button>
							<el-radio-button label="4">出货详情</el-radio-button>
						</el-radio-group>
					</el-form-item>

					<el-form-item class="searchBox" label="查询" prop="search">
						<el-form :inline="true" class="demo-form-inline" ref="searchForm" :model="searchForm">
							<el-form-item prop="value1">
								<el-select placeholder="请选择商品分类" v-model="searchForm.value1">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
							<el-form-item prop="value2">
								<el-select placeholder="请选择商品品牌" v-model="searchForm.value2">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
							<el-form-item prop="value3">
								<el-select placeholder="请选择显示位置" v-model="searchForm.value3">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
							<el-form-item prop="value4">
								<el-input placeholder="请输入商品名称" v-model="searchForm.value4"></el-input>
							</el-form-item>
							<el-form-item>
								<el-button plain @click="resetForm">重置</el-button>
							</el-form-item>
							<el-form-item>
								<el-button type="primary">查询</el-button>
							</el-form-item>
						</el-form>

						<el-form :inline="true" class="demo-form-inline" ref="searchForm1" :model="searchForm1">
							<el-form-item prop="value1">
								<el-select placeholder="请选择商品分类" v-model="searchForm1.value1">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
							<el-form-item prop="value2">
								<el-select placeholder="请选择商品品牌" v-model="searchForm1.value2">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
							<el-form-item prop="value3">
								<el-select placeholder="请选择显示位置" v-model="searchForm1.value3">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>
							<el-form-item prop="value4">
								<el-select placeholder="请选择商品类型" v-model="searchForm1.value4">
									<el-option label="区域一" value="shanghai"></el-option>
									<el-option label="区域二" value="beijing"></el-option>
								</el-select>
							</el-form-item>

							<template v-if="isShow">
								<el-form-item prop="value5">
									<el-select placeholder="请选择商品状态" v-model="searchForm1.value5">
										<el-option label="区域一" value="shanghai"></el-option>
										<el-option label="区域二" value="beijing"></el-option>
									</el-select>
								</el-form-item>
								<el-form-item prop="value6">
									<el-select placeholder="请选择商品状态" v-model="searchForm1.value6">
										<el-option label="区域一" value="shanghai"></el-option>
										<el-option label="区域二" value="beijing"></el-option>
									</el-select>
								</el-form-item>

								<el-form-item prop="value7">
									<el-input placeholder="请输入商品名称" v-model="searchForm1.value7"></el-input>
								</el-form-item>
							</template>

							<el-form-item>
								<el-button plain @click="resetForm1">重置</el-button>
							</el-form-item>

							<el-form-item>
								<el-button type="primary">查询</el-button>
							</el-form-item>

							<el-form-item>
								<el-button type="text" @click="showClick">
									<template v-if="!isShow">
										展开<i class="el-icon-arrow-down el-icon--right"></i>
									</template>
									<template v-else>
										收起<i class="el-icon-arrow-up el-icon--right"></i>
									</template>
								</el-button>
							</el-form-item>
						</el-form>
					</el-form-item>

					<el-form-item label="下拉框" prop="select">
						<el-select v-model="value" clearable placeholder="请选择商品类型">
						    <el-option
						      v-for="item in options"
						      :key="item.value"
						      :label="item.label"
						      :value="item.value">
						    </el-option>
						</el-select>
						
						<el-select
						    v-model="value2"
						    multiple
						    collapse-tags
							filterable
						    placeholder="请选择属性名称">
						    <el-option
						      v-for="item in options"
						      :key="item.value"
						      :label="item.label"
						      :value="item.value">
						    </el-option>
						</el-select>
						
						<el-cascader :options="options1" :props="props"></el-cascader>
						
						<el-cascader :options="options1"></el-cascader>
					</el-form-item>
					
					<el-form-item label="穿梭框" prop="transfer">
						<l-transfer ref="transfer" :data="datas" text="添加新属性" @axios="getTransfer" @clicktext="rightClick"></l-transfer>
					</el-form-item>
					
				</el-form>
		</el-main>
  </div>
</template>

<script>
export default {
  name: 'universal-module',
  data(){
    const generateData = _ => {
      const data = [];
      const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
      const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu'];
      cities.forEach((city, index) => {
        data.push({
          label: city,
          key: index,
          pinyin: pinyin[index]
        });
      });
      return data;
    };
    return {
      ruleForm: {
        resource: '1',
        type: ['1', '2'],
        switch1: true,
        switch2: false,
        currentPage: 1,
        time: '',
        tabs: '1',
        imgUrls: []
      },
      searchForm: {
        value1: '',
        value2: '',
        value3: '',
        value4: '',
      },
      searchForm1: {
        value1: '',
        value2: '',
        value3: '',
        value4: '',
        value5: '',
        value6: '',
        value7: '',
      },
      isShow: false,
      options: [
        {
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }, {
          value: '选项6',
          label: '北京烤鸭1'
        }, {
          value: '选项7',
          label: '北京烤鸭2'
        },{
          value: '选项8',
          label: '北京烤鸭3'
        },{
          value: '选项9',
          label: '北京烤鸭4'
        },{
          value: '选项10',
          label: '北京烤鸭5'
        },
      ],
      value: '',
      value2: '',
      options1: [{
        value: 'zhinan',
        label: '指南',
        children: [{
        value: 'shejiyuanze',
        label: '设计原则',
        children: [{
          value: 'yizhi',
          label: '一致'
        }, {
          value: 'fankui',
          label: '反馈'
        }, {
          value: 'xiaolv',
          label: '效率'
        }, {
          value: 'kekong',
          label: '可控'
        }]
        }, {
        value: 'daohang',
        label: '导航',
        children: [{
          value: 'cexiangdaohang',
          label: '侧向导航'
        }, {
          value: 'dingbudaohang',
          label: '顶部导航'
        }]
        }]
      }, {
        value: 'zujian',
        label: '组件',
        children: [{
        value: 'basic',
        label: 'Basic',
        children: [{
          value: 'layout',
          label: 'Layout 布局'
        }, {
          value: 'color',
          label: 'Color 色彩'
        }, {
          value: 'typography',
          label: 'Typography 字体'
        }, {
          value: 'icon',
          label: 'Icon 图标'
        }, {
          value: 'button',
          label: 'Button 按钮'
        }]
        }, {
        value: 'form',
        label: 'Form',
        children: [{
          value: 'radio',
          label: 'Radio 单选框'
        }, {
          value: 'checkbox',
          label: 'Checkbox 多选框'
        }, {
          value: 'input',
          label: 'Input 输入框'
        }, {
          value: 'input-number',
          label: 'InputNumber 计数器'
        }, {
          value: 'select',
          label: 'Select 选择器'
        }, {
          value: 'cascader',
          label: 'Cascader 级联选择器'
        }, {
          value: 'switch',
          label: 'Switch 开关'
        }, {
          value: 'slider',
          label: 'Slider 滑块'
        }, {
          value: 'time-picker',
          label: 'TimePicker 时间选择器'
        }, {
          value: 'date-picker',
          label: 'DatePicker 日期选择器'
        }, {
          value: 'datetime-picker',
          label: 'DateTimePicker 日期时间选择器'
        }, {
          value: 'upload',
          label: 'Upload 上传'
        }, {
          value: 'rate',
          label: 'Rate 评分'
        }, {
          value: 'form',
          label: 'Form 表单'
        }]
        }, {
        value: 'data',
        label: 'Data',
        children: [{
          value: 'table',
          label: 'Table 表格'
        }, {
          value: 'tag',
          label: 'Tag 标签'
        }, {
          value: 'progress',
          label: 'Progress 进度条'
        }, {
          value: 'tree',
          label: 'Tree 树形控件'
        }, {
          value: 'pagination',
          label: 'Pagination 分页'
        }, {
          value: 'badge',
          label: 'Badge 标记'
        }]
        }, {
        value: 'notice',
        label: 'Notice',
        children: [{
          value: 'alert',
          label: 'Alert 警告'
        }, {
          value: 'loading',
          label: 'Loading 加载'
        }, {
          value: 'message',
          label: 'Message 消息提示'
        }, {
          value: 'message-box',
          label: 'MessageBox 弹框'
        }, {
          value: 'notification',
          label: 'Notification 通知'
        }]
        }, {
        value: 'navigation',
        label: 'Navigation',
        children: [{
          value: 'menu',
          label: 'NavMenu 导航菜单'
        }, {
          value: 'tabs',
          label: 'Tabs 标签页'
        }, {
          value: 'breadcrumb',
          label: 'Breadcrumb 面包屑'
        }, {
          value: 'dropdown',
          label: 'Dropdown 下拉菜单'
        }, {
          value: 'steps',
          label: 'Steps 步骤条'
        }]
        }, {
        value: 'others',
        label: 'Others',
        children: [{
          value: 'dialog',
          label: 'Dialog 对话框'
        }, {
          value: 'tooltip',
          label: 'Tooltip 文字提示'
        }, {
          value: 'popover',
          label: 'Popover 弹出框'
        }, {
          value: 'card',
          label: 'Card 卡片'
        }, {
          value: 'carousel',
          label: 'Carousel 走马灯'
        }, {
          value: 'collapse',
          label: 'Collapse 折叠面板'
        }]
        }]
      }, {
        value: 'ziyuan',
        label: '资源',
        children: [{
        value: 'axure',
        label: 'Axure Components'
        }, {
        value: 'sketch',
        label: 'Sketch Templates'
        }, {
        value: 'jiaohu',
        label: '组件交互文档'
        }]
      }],
      props: { checkStrictly: true },
      data: generateData(),
      value5: [],
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1;
      },
      pagination: {
        total: 30,
        pagesize: 10,
        currentPage: 1
      },

      datas: [
        {
          id: 1
        }
      ]
    }
  },

  methods:{
    resetForm(){
      this.$refs['searchForm'].resetFields();
    },
    resetForm1(){
      this.$refs['searchForm1'].resetFields();
    },
    changePage({page,size}) {
      console.log(`当前页: ${page}`);
    },
    showClick(){
      this.isShow = !this.isShow
    },
    async getTransfer(data, callback, failBack){

      // let { attribute } = await postRequest(`index.php?module=product&action=Ajax&m=attribute_0`,{
      //   page: data.page,
      //   strArr: JSON.stringify(data.checkAll),
      //   keyword: data.keyword
      // })

      // let list = []
      // attribute.filter(item=>{
      //   item.name = item.name0 + ':' + item.name1;
      //   item.id = item.id0 + ':' + item.id1;
      //   if(item.status){
      //     item.isCheck = true
      //   }
      //   list.push(item)
      // })

      // callback(list);
      // failBack();
    },
    rightClick(){
      this.$message.success('点击添加属性')
    }
  }
}
</script>

<style scoped lang="scss">
.el-main {
  padding: 0;
}

.searchBox .el-form-item__label{
	line-height: 60px;
}

.el-form--inline{
	margin-bottom: 20px;
}

.l-transfer{
	width: 640px;
}

.l-pagination[class]{
	width: 800px;
}

.el-form--inline{
	width: 1200px;
}

.upload{
	background-color: #ffffff;
}
</style>