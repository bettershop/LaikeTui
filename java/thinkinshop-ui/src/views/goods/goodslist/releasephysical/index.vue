<template>
  <div class="container" id="hhhh">
    <el-form ref="ruleForm" class="form-search" :rules="rules" :model="ruleForm" label-width="100px">
      <div class="basic-info">
        <div class="header">
          <span>基本信息</span>
        </div>
        <div class="basic-block">
          <el-row :gutter="24">
            <el-col :span="8">
              <el-form-item label="商品标题" prop="goodsTitle">
                <el-input v-model="ruleForm.goodsTitle" placeholder="请输入商品标题"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="副标题" prop="subtitle">
                <el-input v-model="ruleForm.subtitle" placeholder="请输入商品描述"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="关键词" prop="keywords">
                <el-input v-model="ruleForm.keywords" placeholder="请输入商品关键词，多个用逗号隔开"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="8">
              <el-form-item label="商品分类" class="goods-class" prop="goodsClass">
                <el-cascader
                  v-model="ruleForm.goodsClass"
                  class="select-input"
                  ref="myCascader"
                  placeholder="请选择商品分类"
                  :options="classList"
                  :props="{ checkStrictly: true }"
                  @change="changeProvinceCity"
                  clearable>
                </el-cascader>
                <el-button type="primary" @click="dialogShow1">添加分类</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item class="goods-brand" label="商品品牌" prop="goodsBrand">
                <el-select class="select-input" v-model="ruleForm.goodsBrand" placeholder="请选择商品品牌">
                  <el-option v-for="item in brandList" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
                  </el-option>
                </el-select>
                <el-button type="primary" @click="dialogShow2">添加品牌</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <div></div>
            </el-col>
          </el-row>
          
          
          <el-form-item class="goods-img" required label="商品封面图" prop="goodsCover">
            <l-upload
              :limit="1"
              v-model="ruleForm.goodsCover"
              text="最多上传一张，建议尺寸：500px宽，C端首页瀑布流展示"
            >
            </l-upload>
          </el-form-item>
          <el-form-item class="goods-img" required label="商品展示图" prop="goodsShow">
            <l-upload
              :limit="5"
              :mainImg="true"
              v-model="ruleForm.goodsShow"
              text="最多上传五张，建议尺寸：500*500px，主图默认第一张"
            >
            </l-upload>
          </el-form-item>
        </div>
      </div>

      <div class="goods-attribute">
        <div class="header">
          <span>商品属性</span>
        </div>
        <div class="attribute-block">
          <el-form-item label="成本价" prop="cbj">
						<el-input type="number" min="0" v-model="ruleForm.cbj" placeholder="请设置商品的默认成本价"></el-input>
					</el-form-item>
          <el-form-item label="原价" prop="yj">
						<el-input type="number" min="0" v-model="ruleForm.yj" placeholder="请设置商品的默认原价"></el-input>
					</el-form-item>
          <el-form-item label="售价" prop="sj">
						<el-input type="number" min="0" v-model="ruleForm.sj" placeholder="请设置商品的默认售价"></el-input>
					</el-form-item>
          <el-form-item label="单位" prop="unit">
            <el-select class="select-input" v-model="ruleForm.unit" placeholder="请选择单位">
              <el-option v-for="item in unitList" :key="item.id" :label="item.text" :value="item.text">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="库存" class="inventory" prop="kucun">
						<el-input type="number" min="0" v-model="ruleForm.kucun" placeholder="请设置商品的默认库存">
            </el-input>
					</el-form-item>
          <el-form-item label="设置属性" class="add-attribute" prop="attr">
            <el-button type="primary" @click="addAttr">添加属性</el-button>
          </el-form-item>
          <el-form-item class="attribute-table" label="">
            <el-table  v-if="strArr.length > 0" :data="strArr" ref="table" style="width: 100%" height="340">
              <el-table-column v-for="(item,index) of attrTitle" :key="index" prop="attr_name" :label="item.attr_group_name" align="center" show-overflow-tooltip column-key="attr_name" :filters="filterTable[index]"
              :filter-method="filterHandler">
                <template slot-scope="scope">
                  <div v-text="scope.row.attr_list[index].attr_name"></div>
                </template>
              </el-table-column>
              <el-table-column label="成本价" align="center" min-width="150">
                <template slot-scope="scope">
                  <div>
                    <el-input type="number" min="0" v-model="scope.row.cbj" style="width: 140px;"></el-input>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="原价" align="center" min-width="150">
                <template slot-scope="scope">
                  <div>
                    <el-input type="number" min="0" v-model="scope.row.yj" style="width: 140px;"></el-input>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="售价" align="center" min-width="150">
                <template slot-scope="scope">
                  <div>
                    <el-input type="number" min="0" v-model="scope.row.sj" style="width: 140px;"></el-input>
                  </div>
                </template>
              </el-table-column>
              <el-table-column label="库存" align="center" min-width="150">
                <template slot-scope="scope">
                  <div>
                    <el-input type="number" min="0" v-model="scope.row.kucun" style="width: 140px;"></el-input>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="unit" label="单位" align="center">
              </el-table-column>
              <el-table-column label="条形码" align="center" width="200">
                <template slot-scope="scope">
                  <div style="display: flex;align-items: center;">
                    <el-input v-model="scope.row.bar_code"></el-input>
                    <el-link style="white-space: nowrap;margin-left: 10px;color: #2890FF;">扫码</el-link>
                  </div>
                </template>
              </el-table-column>
              <el-table-column fixed="right" label="上传图片" width="140">
                <template slot-scope="scope">
                  <div @click="clickImage">
                    <l-upload :limit="1" v-model="scope.row.img" text="" size="40"></l-upload>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </div>
      </div>

      <div class="goods-set">
        <div class="header">
          <span>商品设置</span>
        </div>
        <div class="set-block">
          <el-form-item class="inventory-warning" label="库存预警" prop="inventoryWarning">
            <span>当前库存量小于</span>
						<el-input v-model="ruleForm.inventoryWarning"></el-input>
            <span>时，商品库存报警</span>
            <span class="grey">可在库存列表中查看明细，预警数据将以红色加粗显示</span>
          </el-form-item>
          <el-form-item class="freight-set" label="运费设置" prop="freight">
            <el-select class="select-input" v-model="ruleForm.freight" placeholder="请选择运费">
              <el-option v-for="item in freightList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
            <el-button type="primary" @click="dialogShow4">添加运费</el-button>
          </el-form-item>
          <el-form-item label="显示标签" prop="checkedLabel">
						<el-checkbox-group v-model="ruleForm.checkedLabel">
              <el-checkbox v-for="label in labelList" :label="label.id" :key="label.id">{{label.name}}</el-checkbox>
            </el-checkbox-group>
					</el-form-item>
          <el-form-item class="activity-class" label="支持活动类型" prop="checkedActivity">
            <el-radio-group v-model="ruleForm.checkedActivity">
              <el-radio :disabled="togdisable" v-for="activity in activityList" :label="activity.value" :key="activity.value">{{activity.name}}</el-radio>
            </el-radio-group>
					</el-form-item>
          <el-form-item class="show-local" label="前端显示位置" prop="checked">
            <el-checkbox-group v-model="ruleForm.checked">
              <el-checkbox v-for="label in showAdrList" :label="label.value" :key="label.value">{{label.text}}</el-checkbox>
            </el-checkbox-group>
            <span class="show-font">如果不选，默认显示在全部商品里</span>
					</el-form-item>
          <el-form-item label="虚拟销量" prop="virtualSales">
						<el-input v-model="ruleForm.virtualSales"></el-input>
					</el-form-item>
          <el-form-item label="排序号">
						<el-input v-model="ruleForm.sequence"></el-input>
					</el-form-item>
        </div>
      </div>

      <div class="detail-container">
        <div class="header">
          <span>详细内容</span>
        </div>
        <div class="detail-block">
          <el-form-item label="商品详情" prop="content">
            <vue-editor 
              v-model="ruleForm.content"
              useCustomImageHandler
              @image-added="handleImageAdded"
            ></vue-editor>
					</el-form-item>
        </div>
      </div>
      
      <div class="footer-button">
        <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
        <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
      </div>
    </el-form>

    <!-- 添加分类 -->
    <div class="dialog-class">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加分类"
        :visible.sync="dialogVisible1"
        :before-close="handleClose1"
        :append-to-body="false"
      >
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2"  class="picture-ruleForm" label-width="100px">
          <el-form-item label="分类名称" prop="classname">
            <el-input v-model="ruleForm2.classname" placeholder="请输入分类名称"></el-input>
          </el-form-item>
          <el-form-item label="英文副标题" prop="subtitle">
            <el-input v-model="ruleForm2.subtitle" placeholder="请输入英文副标题"></el-input>
          </el-form-item>
          <el-form-item label="分类等级" prop="classlevel">
            <el-select class="select-input" v-model="ruleForm2.classlevel" placeholder="请输入分类等级">
              <el-option v-for="(item,index) in menuLevelList" :key="index" :label="item.label" :value="item.label">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="上级分类" prop="" v-if="inputShow !== 1">
            <div class="superior">
              <el-select v-show="inputShow >= 2" class="select-input" v-model="ruleForm2.select_2" placeholder="请选择一级菜单">
                <el-option v-for="(item,index) in options1" :key="index" :label="item.pname" :value="item.cid">
                </el-option>
              </el-select>
              <el-select v-show="inputShow >= 3" class="select-input" v-model="ruleForm2.select_3" placeholder="请选择二级菜单">
                <el-option v-for="(item,index) in options2" :key="index" :label="item.pname" :value="item.cid">
                </el-option>
              </el-select>
              <el-select  v-show="inputShow >= 4" class="select-input" v-model="ruleForm2.select_4" placeholder="请选择三级菜单">
                <el-option v-for="(item,index) in options3" :key="index" :label="item.pname" :value="item.cid">
                </el-option>
              </el-select>
              <el-select  v-show="inputShow >= 5" class="select-input" v-model="ruleForm2.select_5" placeholder="请选择四级菜单">
                <el-option v-for="(item,index) in options4" :key="index" :label="item.pname" :value="item.cid">
                </el-option>
              </el-select>
            </div>
          </el-form-item>
          <el-form-item class="upload-img" label="分类图标" prop="classLogo">
              <l-upload
                :limit="1"
                v-model="ruleForm2.classLogo"
                text="建议尺寸：122*40px，白色"
              >
              </l-upload>
          </el-form-item>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" type="primary" @click="submitForm2('ruleForm2')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
              <el-button class="bdColor" @click="handleClose1()" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <!-- 添加品牌 -->
    <div class="dialog-brand">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加品牌"
        :visible.sync="dialogVisible2"
        :before-close="handleClose2"
      >
        <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3"  class="picture-ruleForm" label-width="90px">
          <el-form-item class="brandname" label="品牌名称" prop="brandname">
            <el-input v-model="ruleForm3.brandname" placeholder="请输入品牌名称"></el-input>
          </el-form-item>
          <el-form-item class="upload-img" label="品牌logo" prop="brandLogo" required>
            <l-upload
              :limit="1"
              v-model="ruleForm3.brandLogo"
              text="建议尺寸：122*40px，白色"
            >
            </l-upload>
          </el-form-item>
          <el-form-item label="所属分类" prop="brandtype">
            <el-select class="select-input" v-model="ruleForm3.brandtype" placeholder="请选择所属分类">
              <el-option v-for="(item,index) in brandTypeList" :key="index" :label="item.pname" :value="item.pname">
                <div @click="getId(item.cid)">{{ item.pname }}</div>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="所属国家" prop="countries">
            <el-select class="select-input" v-model="ruleForm3.countries" placeholder="请选择所属国家">
              <el-option v-for="(item,index) in countriesList" :key="index" :label="item.zh_name" :value="item.zh_name">
                <div @click="getIds(item.id)">{{ item.zh_name }}</div>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item class="textarea" label="备注" prop="note">
            <el-input type="textarea" v-model="ruleForm3.note" placeholder="请填写备注"></el-input>
          </el-form-item>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" type="primary" @click="submitForm3('ruleForm3')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
              <el-button class="bdColor" @click="handleClose2" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <!-- 添加运费 -->
    <div class="dialog-freight">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加运费"
        :visible.sync="dialogVisible3"
        :before-close="handleClose3"
      >
        <el-form :model="ruleForm5" :rules="rules5" ref="ruleForm5" label-width="100px" class="demo-ruleForm">
          <div class="notice">
            <el-form-item class="title" label="运费名称" prop="templateName">
              <el-input v-model="ruleForm5.templateName" placeholder="请输入运费名称"></el-input>
            </el-form-item>
            <el-form-item class="rule-block" required label="运费规则" >
              <div class="add-rule">
                <el-button class="bgColor" @click="dialogShow5" type="primary" >添加规则</el-button>
              </div>
            </el-form-item>
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" type="primary" @click="submitForm4('ruleForm5')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
              <el-button class="bdColor" @click="dialogVisible3 = false" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
            </el-form-item>
          </div>
        </el-form>
        <div class="dictionary-list" v-if="tableData.length !== 0">
          <el-table :data="tableData" ref="table" class="el-table" style="width: 100%" :height="250">
            <el-table-column prop="freight" label="运费" width="80">
            </el-table-column>
            <el-table-column prop="name" label="省份">
            </el-table-column>
            <el-table-column label="操作" width="80">
              <template slot-scope="scope">
                <div class="OP-button">
                  <div class="OP-button-top">
                    <el-button icon="el-icon-delete" @click="Delete(scope.row,scope.$index)">删除</el-button>
                  </div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-dialog>
    </div>

    <!-- 添加运费模板 -->
    <div class="dialog-freightTemplate">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加运费规则"
        :visible.sync="dialogVisible4"
        :before-close="handleClose4"
      >
        <el-form :model="ruleForm6" :rules="rules6" ref="ruleForm6" label-width="100px" class="demo-ruleForm">
          <el-form-item label="运费" prop="freight">
            <el-input v-model="ruleForm6.freight"></el-input>
          </el-form-item>
          <el-form-item class="check-provinces" label="选择省份" prop="status">
            <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
            <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
              <el-checkbox v-for="city in cities" :label="city" :key="city">{{city}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" @click="dialogVisible4 = false">取 消</el-button>
              <el-button class="bdColor" type="primary" @click="determine('ruleForm6')">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <div class="model" v-show="dialogVisible1 || dialogVisible2"></div>
  </div>
</template>

<script>
import $ from 'jquery'
import { VueEditor } from 'vue2-editor'
import { getGoodsActiveList, label, choiceClass, goodsUnit, addGoods, getGoodsInfoById } from '@/api/goods/goodsList'
import { getFreightInfo, addFreight, getRegion } from '@/api/goods/freightManagement'
import { addClass } from '@/api/goods/goodsClass'
import { addBrand, getClassInfo, getCountry } from '@/api/goods/brandManagement'
import { dictionaryList } from '@/api/Platform/numerical'
import Config from "@/packages/apis/Config"
import { getStorage } from '@/utils/storage'
import axios from 'axios'
var attrFlag = true;
export default {
  name: 'releasephysical',

  components: {
    VueEditor
  },

  data() {
    return {
      ruleForm: {
        // 基本信息
        goodsTitle: '',
        subtitle: '',
        keywords: '',
        goodsClass: '',
        goodsBrand: '',
        goodsCover: '',
        goodsShow: '',

        // 商品属性
        cbj: '',
				yj: '',
				sj: '',
				unit: '',
				kucun: '',
				attr: '',

        // 商品设置
        inventoryWarning: '',
        freight: '',
        checkedLabel: [],
        checkedActivity: 1,
        checked: [],
        virtualSales: '',
        sequence: '',

        // 详细内容
        content: '' 
      },

      rules: {
        // 基本信息
        goodsTitle: [
          {required: true, message: '请输入商品标题', trigger: 'blur'}
        ],
        subtitle: [
          {required: true, message: '请输入商品描述', trigger: 'blur'}
        ],
        keywords: [
          {required: true, message: '请输入商品关键词', trigger: 'blur'}
        ],
        goodsClass: [
          {required: true, message: '请选择商品分类', trigger: 'blur'}
        ],
        goodsBrand: [
          {required: true, message: '请选择商品品牌', trigger: 'blur'}
        ],
        goodsCover: [
          {required: true, message: '请上传商品封面图', trigger: 'change'}
        ],
        goodsShow: [
          {required: true, message: '请上传商品展示图', trigger: 'change'}
        ],

        // 商品属性
        cbj: [
          {required: true, message: '请设置商品的默认成本价', trigger: 'blur'}
        ],
        yj: [
          {required: true, message: '请设置商品的默认原价', trigger: 'blur'}
        ],
        sj: [
          {required: true, message: '请设置商品的默认售价', trigger: 'blur'}
        ],
        unit: [
          {required: true, message: '请选择单位', trigger: 'change'}
        ],
        kucun: [
          {required: true, message: '请设置商品的默认库存', trigger: 'blur'}
        ],
        kucun: [
          {required: true, message: '请设置商品属性', trigger: 'blur'}
        ],
        attr: [
          {required: true, message: '请设置商品属性', trigger: 'change'}
        ],

        // 商品设置
        inventoryWarning: [
          {required: true, message: '请输入库存预警', trigger: 'blur'}
        ],
        freight: [
          {required: true, message: '请选择运费模板', trigger: 'change'}
        ],
        checkedActivity: [
          {required: true, message: '请选择支持活动类型', trigger: 'change'}
        ],
      },

      check_attr: [], // 编辑复制选中回显属性数据

      showAdrList: [], // 商品展示位置

      // 添加分类弹框数据
      dialogVisible1: false,
      ruleForm2: {
        classname: '',
        subtitle: '',
        classlevel: '',
        classLogo: '',
        select_2: '',
        select_3: '',
        select_4: '',
        select_5: '',
      },
      inputShow: 1,
      rules2: {
        classname: [
          { required: true, message: '请输入分类名称', trigger: 'blur' }
        ],
        subtitle: [
          { required: true, message: '请输入英文副标题', trigger: 'blur' }
        ],
        classlevel: [
          { required: true, message: '请输入分类等级', trigger: 'change' }
        ],
        superiorclass: [
          { required: true, message: '请输入上级分类', trigger: 'change' }
        ],
      },

      menuLevelList: [
        { 
          value: 1,
          label: '一级'
        },
        { 
          value: 2,
          label: '二级'
        },
        { 
          value: 3,
          label: '三级'
        },
        { 
          value: 4,
          label: '四级'
        },
        { 
          value: 5,
          label: '五级'
        }
      ],

      options1: [],

      options2: [],

      options3: [],

      options4: [],

      // 添加品牌弹框数据
      dialogVisible2: false,
      ruleForm3: {
        brandname: '',
        brandLogo: '',
        brandtype: '',
        countries: '',
        note: '',
      },
      rules3: {
        brandtype: [
          { required: true, message: '请填所属分类', trigger: 'change' }
        ],
        brandLogo: [
          { required: true, message: '请上传品牌logo', trigger: 'change' }
        ],
        countries: [
          { required: true, message: '请填写所属国家', trigger: 'change' }
        ],
        brandname: [
          { required: true, message: '请填写品牌名称', trigger: 'blur' }
        ],
      },

      brandTypeList: [],

      countriesList: [],

      id: null,
      countriesId: null,

      // 添加属性弹框数据
      imgArr: [],
			attrTitle: JSON.parse('[]', true), //可选规格数据
			strArr: JSON.parse('[]', true), //已选规格数据
      // strArr: [{"cbj":"11","yj":"1","sj":"1","unit":"根","kucun":"1","image":"https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/1624863705554.jpg","bar_code":"","attr_list":[{"attr_id":"","attr_name":"银灰色","attr_group_name":"颜色"}],"cid":""}],
      unitList: [],

      // 添加运费弹框数据
      dialogVisible3: false,
      ruleForm5: {
        templateName: '',
      },

      rules5: {
        templateName: [
          { required: true, message: '请输入模板名称', trigger: 'blur' },
        ],
      },

      // 添加运费规则弹框数据
      dialogVisible4: false,
      tableData: [],
      checkAll: false,
      checkedCities: [],
      cities: [],
      isIndeterminate: true,
      ruleForm6: {
        freight: null,
      },
      rules6: {
        freight: [
          { required: true, message: '请填写模板名称', trigger: 'blur' }
        ],
      },

      // 商品分类列表
      classList: [],

      // 商品分类列表
      brandList: [],

      // 支持活动类型
      activityList: [],

      // 标签
      labelList: [],

      // 运费
      freightList: [],

      togdisable: false,



      actionUrl: Config.baseUrl,

      goodsEditorBase: '',
      storeIds: '',
      mchid: '',

      emptyAttr: false,


      classIds: []
    }
  },

  created() {
    if(this.$route.query.name && this.$route.query.name == 'editor') {
      this.$router.currentRoute.matched[2].meta.title = '编辑商品'
      this.classIds = this.$route.query.classId.split('-').filter(item => {
        if(item !== '') {
          return item
        }
      }).map(item => {
        return parseInt(item)
      })
    } else if(this.$route.query.name && this.$route.query.name == 'copy') {
      this.$router.currentRoute.matched[2].meta.title = '复制商品'
      this.classIds = this.$route.query.classId.split('-').filter(item => {
        if(item !== '') {
          return item
        }
      }).map(item => {
        return parseInt(item)
      })
    } else {
      this.$router.currentRoute.matched[2].meta.title = '发布商品'
      this.emptyAttr = true
    }
    this.getDictionaryList()
    this.getClassInfoss()
    this.getGoodsActiveLists()
    this.labels()
    this.choiceClasss().then(() => {
      if(this.$route.query.id) {
        this.allClass(this.classList)
      }
    })
    this.getRegions()
    this.getClassInfos()
    this.getCountrys()
    this.goodsUnits()
    if(this.$route.query.name == 'editor' && this.$route.query.status !== '待上架') {
      this.togdisable = true
    }
    if(this.$route.query.name == 'editor' || this.$route.query.name == 'copy') {
      getGoodsInfoById({
        api: 'admin.goods.getGoodsInfoById',
        goodsId: this.$route.query.id
      }).then(res => {
        if(this.$route.query.name == 'editor') {
          this.mchid = res.data.data.list.mch_id
        }
        let goodsAttribute = res.data.data.list

        this.ruleForm.goodsTitle = goodsAttribute.product_title
        this.ruleForm.subtitle = goodsAttribute.subtitle
        this.ruleForm.keywords = goodsAttribute.keyword
        this.ruleForm.goodsClass = goodsAttribute.product_class.split('-').filter(item => {
          if(item !== '') {
            return item
          }
        }).map(item => {
          return parseInt(item)
        })
        this.ruleForm.goodsBrand = goodsAttribute.brand_id
        this.ruleForm.goodsCover = res.data.data.cover_map
        this.ruleForm.goodsShow = res.data.data.imgurls
        this.ruleForm.inventoryWarning = parseInt(res.data.data.initial.stockWarn)
        this.ruleForm.freight = parseInt(goodsAttribute.freight)
        if(goodsAttribute.s_type) {
          if(goodsAttribute.s_type.length <= 1) {
            this.ruleForm.checkedLabel.push(parseInt(goodsAttribute.s_type))
          } else {
            this.ruleForm.checkedLabel = goodsAttribute.s_type.split(',').map(item => {
              return parseInt(item)
            })
          }
        }
        this.ruleForm.checkedLabel = this.ruleForm.checkedLabel.filter(item => {
          if(item) {
            return item
          }
        })
        this.ruleForm.checkedActivity = parseInt(goodsAttribute.active)
        // this.ruleForm.checked = `${goodsAttribute.distributor_id}`
        if(res.data.data.show_adr[0].status) {
          this.ruleForm.checked.push(res.data.data.show_adr[0].value)
        }
        this.ruleForm.virtualSales = goodsAttribute.volume
        this.ruleForm.content = goodsAttribute.content
        this.ruleForm.cbj = res.data.data.initial.cbj
				this.ruleForm.yj = res.data.data.initial.yj
				this.ruleForm.sj = res.data.data.initial.sj
				this.ruleForm.unit = res.data.data.initial.unit
				this.ruleForm.kucun = res.data.data.initial.kucun
        this.attrTitle = res.data.data.attr_group_list
			  this.strArr = res.data.data.checked_attr_list
        // if(this.$route.query.name == 'copy') {
        //   this.strArr = this.strArr.map(item => {
        //     item.cid = ''
        //     return item
        //   })
        // }

        this.ruleForm.sequence = res.data.data.list.sort
        choiceClass({
          api: 'admin.goods.choiceClass',
          classId: res.data.data.class_id
        }).then(res => {
          this.brandList = res.data.data.list.brand_list.splice(-1,1)
        })

        this.check_attr = res.data.data.checked_attr_list.map(item => {
          return item.attr_list[0]
        })

        this.check_attr = this.check_attr.map(item => {
          return {
            label: item.attr_group_name + ':' + item.attr_name,
            name1: item.attr_group_name + ':' + item.attr_name,
            name: item.attr_group_name,
            sname: item.attr_name
          }
        })
        
      }).then(() => {
        this.getFreightInfos()
      })

      
    } else {
      this.togdisable = false
      this.getFreightInfos()
    }
    
  },

  mounted() {
    this.getBase()
    this.storeIds = getStorage('rolesInfo').storeId
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'physicalgoods' && (this.$route.query.name == 'editor' || this.$route.query.name == 'copy')) {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
      to.params.inputInfo = this.$route.query.inputInfo
    }   
    next();
  },

  watch: {
    // strArr(val){
    //   console.log(111);
		// 	if(val && val.length>0){
		// 		this.ruleForm.attr = 1
		// 	}else{
		// 		this.ruleForm.attr = ''
		// 	}
		// },
    'strArr': {
      handler(newName, oldName) {
        if(newName && newName.length>0){
          this.ruleForm.attr = 1
        }else{
          this.ruleForm.attr = ''
        }
        console.log(123);
      },
      deep: true,
      immediate: true
    },
    

		"ruleForm.cbj"(val,oldVal){
			if(this.strArr && this.strArr.length>0 && oldVal){
				this.strArr.filter(item=>{
					item.cbj = val
				})
			}
		},

		"ruleForm.yj"(val,oldVal){
			if(this.strArr && this.strArr.length>0 && oldVal){
				this.strArr.filter(item=>{
					item.yj = val
				})
			}
		},

		"ruleForm.sj"(val,oldVal){
			if(this.strArr && this.strArr.length>0 && oldVal){
				this.strArr.filter(item=>{
					item.sj = val
				})
			}
		},

		"ruleForm.unit"(val,oldVal){
			if(this.strArr && this.strArr.length>0 && oldVal){
				this.strArr.filter(item=>{
					item.unit = val
				})
			}
		},

		"ruleForm.kucun"(val,oldVal){
			if(this.strArr && this.strArr.length>0 && oldVal){
				this.strArr.filter(item=>{
					item.kucun = val
				})
			}
		},

    checkAll() {
      if(this.checkAll === true) {
        this.checkedCities = this.cities
      }
    },

    'ruleForm2.classlevel'() {
      if(this.ruleForm2.classlevel === '二级') {
        this.inputShow = 2
      } else if (this.ruleForm2.classlevel === '三级') {
        this.inputShow = 3
      } else if (this.ruleForm2.classlevel === '四级') {
        this.inputShow = 4
      } else if (this.ruleForm2.classlevel === '五级') {
        this.inputShow = 5
      } else {
        this.inputShow = 1
      }
    },

    'ruleForm2.select_2'(newVal){
			console.log(newVal)
      this.ruleForm2.select_3 = ''
      this.ruleForm2.select_4 = ''
      this.ruleForm2.select_5 = ''
      getClassInfo({
        api: 'admin.goods.getClassInfo',
        pageSize: 100,
        classId: newVal,
        type: 1
      }).then(res => {
        this.options2 = res.data.data.classInfo
      })
		},

		'ruleForm2.select_3'(newVal){
			console.log(newVal);
      this.ruleForm2.select_4 = ''
      this.ruleForm2.select_5 = ''
      getClassInfo({
        api: 'admin.goods.getClassInfo',
        pageSize: 100,
        classId: newVal,
        type: 1
      }).then(res => {
        this.options3 = res.data.data.classInfo
      })
		},

    'ruleForm2.select_4'(newVal){
			console.log(newVal);
      this.ruleForm2.select_5 = ''
      getClassInfo({
        api: 'admin.goods.getClassInfo',
        pageSize: 100,
        classId: newVal,
        type: 1
      }).then(res => {
        this.options4 = res.data.data.classInfo
      })
		},
  },

  computed: {
    filterTable(){
			let arr = [];
			if(this.attrTitle.length>0){
				this.attrTitle.filter((items,indexs)=>{
					arr.push([]);
					items.attr_list.filter(item=>{
						
						arr[indexs].push({
							text: item.attr_name,
							value: item.attr_name,
						})
					})
				})
			}
			return arr
		},

    uploadData() {
      {
        return {
          api: 'resources.file.uploadFiles',
          storeId: getStorage('laike_admin_uaerInfo').storeId,
          groupId: -1,
          uploadType: 2,
          accessId: this.$store.getters.token
        }
      }
    }
  },

  methods: {
    getBase() {
      if (process.env.NODE_ENV == 'development') {
        this.goodsEditorBase = process.env.VUE_APP_PROXY_API
      } else if (process.env.NODE_ENV == 'production') {
        this.goodsEditorBase = process.env.VUE_APP_BASE_API
      }
    },

    async getDictionaryList() {
      const res = await dictionaryList({
        api: 'saas.dic.getDictionaryInfo',
        key: '商品展示位置'
      })

      console.log(res);
      this.showAdrList = res.data.data.list
    },

    // 加载所有分类
    async allClass(value) {
      for(let i = 0; i < value.length -1; i++) {
        if(this.classIds.includes(value[i].value)) {
          choiceClass({
            api: 'admin.goods.choiceClass',
            classId: value[i].value
          }).then(res => {
            if(res.data.data.list.class_list.length !== 0) {
              res.data.data.list.class_list[0].forEach((item, index) => {
                let obj = item
                value[i].children.push({
                  value: obj.cid,
                  label: obj.pname,
                  index: index,
                  children: []
                })
              })

              this.allClass(value[i].children)
            }
          })
        } else {
          continue
        }
      }
    },

    filterHandler(value, row) {
			let flag = row.attr_list.some(item=>{
				return item.attr_name == value
			})
			return flag;
		},

    clickImage(){
			$('.el-table__body-wrapper').css('height','1000000');
			setTimeout(()=>{
				$(document).off().on('click',function(){
					$('.el-table__body-wrapper').css('height','265px');
				})
			},1000)
		},

    // 获取商品类别
    async choiceClasss() {
      const res = await choiceClass({
        api: 'admin.goods.choiceClass',
      })
      res.data.data.list.class_list[0].forEach((item, index) => {
        let obj = item
        this.classList.push({
          value: obj.cid,
          label: obj.pname,
          index: index,
          children: []
        })
      })
    },

    // 根据商品类别id获取商品品牌
    changeProvinceCity(value) {
      this.ruleForm.goodsBrand = []
      choiceClass({
        api: 'admin.goods.choiceClass',
        classId: value.length > 1 ? value[value.length - 1] : value[0]
      }).then(res => {
        let num = this.$refs.myCascader.getCheckedNodes()[0].data.index
        this.brandList = res.data.data.list.brand_list
        if(res.data.data.list.class_list[0].length !== 0) {
          this.$refs.myCascader.getCheckedNodes()[0].data.children = []
          res.data.data.list.class_list[0].forEach((item, index) => {
            let obj = item
            this.$refs.myCascader.getCheckedNodes()[0].data.children.push({
              value: obj.cid,
              label: obj.pname,
              index: index,
              children: []
            })
          })
        }
      })
    },

    // 获取支持活动类型
    async getGoodsActiveLists() {
      const res = await getGoodsActiveList({
        api: 'admin.goods.getGoodsActive',
      })
      this.activityList = res.data.data.filter(item => {
        if(item.status) {
          return item
        }
      })
    },

    // 获取商品标签
    async labels () {
      const res = await label({
        api: 'admin.label.index',
        pageSize: 9999,
      })
      this.labelList = res.data.data.list
    },

    // 获取商品单位
    async goodsUnits() {
      const res = await goodsUnit({
        api: 'saas.dic.getDictionaryInfo',
        pageSize: 20,
        key: '单位'
      })

      this.unitList = res.data.data.list
    },

    // 获取运费列表
    async getFreightInfos() {
      const res = await getFreightInfo({
        api: 'admin.goods.getFreightInfo',
        pageSize: 999,
        mchId: this.mchid ? this.mchid : getStorage('laike_admin_uaerInfo').mchId,
      })
      this.freightList = res.data.data.list
      if(!this.ruleForm.freight) {
        this.ruleForm.freight = this.freightList[0].id
      }
    },

    // 商品详情
    handleImageAdded(file, Editor, cursorLocation, resetUploader) {
      var formData = new FormData();
      formData.append("file", file) //第一个file 后台接收的参数名
      axios({
        url: this.actionUrl+"resources/file/uploadFiles",//上传路径
        method: "POST",
        params: {
          api: 'resources.file.uploadFiles',
          storeId: getStorage('laike_admin_uaerInfo').storeId,
          groupId: -1,
          uploadType: 2,
          accessId: this.$store.getters.token
        },
        data: formData
      }).then(result => {
        let url = result.data.data.imgUrls[0]; // 返回给你的图片路径
        Editor.insertEmbed(cursorLocation, "image", url);
        resetUploader();
      })
      .catch(err => {
          console.log(err);
      });
    },

    // 添加分类弹框方法 //
    async getClassInfoss() {
      const res = await getClassInfo({
        api: 'admin.goods.getClassInfo',
        pageSize: 100,
      })
      console.log(res);
      this.options1 = res.data.data.classInfo
    },

    dialogShow1() {
      this.dialogVisible1 = true
      this.ruleForm2.classname = ''
      this.ruleForm2.subtitle = ''
      this.ruleForm2.classlevel = ''
      this.ruleForm2.classLogo = ''
      this.$refs['ruleForm2'].clearValidate()
    },
    
    handleClose1(done) {
      this.dialogVisible1 = false
      this.ruleForm2.classname = ''
      this.ruleForm2.subtitle = ''
      this.ruleForm2.classlevel = ''
      this.ruleForm2.classLogo = ''
      this.$refs['ruleForm2'].clearValidate()
    },

    handleAvatarSuccess(res, file) {
      console.log(res);
      this.ruleForm2.classLogo = res.data.imgUrls[0]
    },

    submitForm2(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm2);
        if (valid) {
          try {
            if(this.inputShow === 1) {
              addClass({
                api: 'admin.goods.addClass',
                className: this.ruleForm2.classname,
                ename: this.ruleForm2.subtitle,
                level: 0,
                fatherId: 0,
                img: this.ruleForm2.classLogo
              }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.$message({
                    message: '添加成功',
                    type: 'success',
                    offset:100
                  })
                  this.dialogVisible1 = false
                  this.choiceClasss()
                }
              })
            } else {
              addClass({
                api: 'admin.goods.addClass',
                className: this.ruleForm2.classname,
                ename: this.ruleForm2.subtitle,
                level: this.inputShow - 1,
                fatherId: this.getfatherMenuId(this.inputShow),
                img: this.ruleForm2.classLogo
              }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.$message({
                    message: '添加成功',
                    type: 'success',
                    offset:100
                  })
                  this.ruleForm2.classname = ''
                  this.ruleForm2.subtitle = ''
                  this.ruleForm2.classlevel = ''
                  this.ruleForm2.classLogo = ''
                  // this.dialogVisible1 = false
                  this.handleClose1()
                  this.choiceClasss()
                }
              })
            }
          } catch (error) {
            this.$message({
              message: error.message,
              type: 'error',
              showClose: true
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    getType(value) {
        if(value === '商城') {
            return 0
        } else {
            return 1
        }
    },

    getfatherMenuId(value) {
      if(this.inputShow === 2) {
        return this.ruleForm2.select_2
      } else if(this.inputShow === 3) {
        if(this.ruleForm2.select_3 === '') {
          return this.ruleForm2.select_2
        } else {
          return this.ruleForm2.select_3
        }
      } else if(this.inputShow === 4) {
        if(this.ruleForm2.select_4 === '') {
          return this.ruleForm2.select_3
        } else if(this.ruleForm2.select_3 === '') {
          return this.ruleForm2.select_2
        } else {
          return this.ruleForm2.select_4
        }
      } else {
        if(this.ruleForm2.select_5 === '') {
          return this.ruleForm2.select_4
        } else if(this.ruleForm2.select_4 === '') {
          return this.ruleForm2.select_3
        } else if(this.ruleForm2.select_3 === '') {
          return this.ruleForm2.select_2
        } else {
          return this.ruleForm2.select_5
        }
      }
    },

    // 添加品牌弹框方法 //
    dialogShow2() {
      this.dialogVisible2 = true
      this.ruleForm3.brandname = ''
      this.ruleForm3.brandLogo = ''
      this.ruleForm3.brandtype = ''
      this.ruleForm3.countries = ''
      this.ruleForm3.note = ''
      this.$refs['ruleForm3'].clearValidate()
    },
    
    handleClose2(done) {
      this.dialogVisible2 = false
      this.ruleForm3.brandname = ''
      this.ruleForm3.brandLogo = ''
      this.ruleForm3.brandtype = ''
      this.ruleForm3.countries = ''
      this.ruleForm3.note = ''
      this.$refs['ruleForm3'].clearValidate()

    },

    handleAvatarSuccess1(res, file) {
      console.log(res);
      this.ruleForm3.brandLogo = res.data.imgUrls[0]
    },

    // 获取所属分类
    async getClassInfos() {
        const res = await getClassInfo({
          api: 'admin.goods.getClassInfo',
        })
        console.log(res);
        this.brandTypeList = res.data.data.classInfo
    },

    // 获取国家列表
    async getCountrys() {
        const res = await getCountry({
            api: 'admin.goods.getCountry',
        })
        console.log(res);
        this.countriesList = res.data.data
    },

    getId(value) {
        this.id = value
    },

    getIds(value) {
        this.countriesId = value
    },

    submitForm3(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm3);
        if (valid) {
        try {
          addBrand({
              api: 'admin.goods.addBrand',
              brandName: this.ruleForm3.brandname,
              brandLogo: this.ruleForm3.brandLogo,
              brandClass: this.id,
              producer: this.countriesId,
              remarks: this.ruleForm3.countries
          }).then(res => {
              console.log(res);
              if(res.data.code === '413') {
                this.$message({
                  message: res.data.message,
                  type: 'error',
                  offset: 100
                })
              } else {
                this.$message({
                  message: '添加成功',
                  type: 'success',
                  offset: 100
                })
                this.dialogVisible2 = false
                this.ruleForm3.brandname = ''
                this.ruleForm3.brandLogo = ''
                this.ruleForm3.brandtype = ''
                this.ruleForm3.countries = ''
                this.ruleForm3.note = ''
                this.$refs['ruleForm3'].clearValidate()
              }
          })
        } catch (error) {
          this.$message({
              message: error.message,
              type: 'error',
              showClose: true
          })
        }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    // 添加属性弹框方法 //

    /* 添加属性 */
		addAttr(){
			var imgattr = $('.attr-table .file-input')
      let page = this
			page.imgArr = []
			for (var j = 0; j < imgattr.length; j++) {
				page.strArr[j].img = $(imgattr).eq(j).val()
			}
			
			page.imgArr = JSON.parse(JSON.stringify(page.strArr))
			
			page.imgArr.filter(items=>{
			
				items.attrStr = ''
			
				items.attr_list.filter(item=>{
			
					items.attrStr += ',' + item.attr_name
			
				})
			
				items.attrStr = items.attrStr.replace(',','')
			
			})
			
			if (attrFlag) {
				var price1 = this.ruleForm.cbj; //成本价
				var price2 = this.ruleForm.yj; //原价
				var price3 = this.ruleForm.sj; //售价
				var size = this.ruleForm.unit; //单位
				var kucun = this.ruleForm.kucun; //库存
				let that = this
				if (!(price1 && price2 && price3 && size != 0 && kucun)) {
          this.$message({
            message: '请先完善商品价格、单位和库存！',
            type: 'error'
          })
					attrFlag = true
					return
				}
				if ($('body', parent.document).find("#maskClass").length > 0) {
					$('body', parent.document).find("#maskClass").css('display', '')
					$('body', parent.document).find("#maskClass iframe")[0].contentWindow.app.filterText = ''
					$('body', parent.document).find("#maskClass iframe")[0].contentWindow.app.loadFlag = false
          if(this.emptyAttr) {
            $('body', parent.document).find("#maskClass iframe")[0].contentWindow.app.checkAll = []
          }
				} else {
					var str =
						`
						<div class="maskNew" id="maskClass" style="z-index: 9999;">
							<div style="width:724px;height:540px;background:rgba(255,255,255,1);border-radius:4px;position: absolute;left: 50%;top: 50%;transform: translate(-50%,-50%);">
								<div style="display: flex;justify-content: space-between;align-items: center;height: 60px;box-sizing: border-box;padding-left: 20px;color: #414658;font-weight: bold;font-size: 16px;border-bottom: 2px solid #E9ECEF;">
									设置属性
									<a class="maskClose" href="javascript:void(0);" style="padding: 20px;"><img src="/bounced/img/gb.png"/></a>
								</div>
			
								<iframe ref="iframe" scrolling="yes" frameborder="0" src="/bounced/Choose.html" style="width: 100%;height: 410px"></iframe>
			
								<div style="height: 70px;box-sizing: border-box;border-top: 1px solid #E9ECEF;display: flex;align-items: center;justify-content: flex-end;">
									<a class="maskClose" href="javascript:;" style="width:112px; height:36px; border:1px solid rgba(40,144,255,1); border-radius:2px;display: flex;align-items: center;justify-content: center;color: #2890FF;margin-right: 10px;box-sizing: border-box;background-color: #FFFFFF;">取消</a>
									<a id="maskSave" href="javascript:;" style="width:112px;height:36px;background:rgba(40,144,255,1);border-radius:2px;display: flex;align-items: center;justify-content: center;color: #FEFEFE;margin-right: 20px;">确定</a>
								</div>
			
			
							</div>
						</div>
					`
					$('body', parent.document).append(str)
          
				}
        
				$('#maskSave').unbind().bind('click', function() {
			  	let checkAll = $("#maskClass iframe")[0].contentWindow.app.checkAll
					if(checkAll.length == 0){
            that.$message({
              message: '请选择商品属性',
              type: 'error',
              customClass: 'zZindex',
            });
						return
					}
          console.log(888888888888888888);
          console.log($("#maskClass iframe")[0].contentWindow.app);
					
					$("#strArr").html(JSON.stringify(checkAll))
					// classDivAppend()
					let attrTitle = []
					// page.attrTitle
			
					checkAll.filter(items=>{
						if(attrTitle.length == 0){
							let obj = {
								attr_group_name: items.name,
								attr_list: [
									{
										attr_name: items.sname,
										// chooseMe: true,
									}
								],
								// isShow: false
							}
							attrTitle.push(obj)
						}else{
							let index = attrTitle.findIndex((item)=>{
								return item.attr_group_name == items.name
							})
			
							if(index<0){
								let obj = {
									attr_group_name: items.name,
									attr_list: [
										{
											attr_name: items.sname,
											// chooseMe: true,
										}
									],
									// isShow: false
								}
								attrTitle.push(obj)
							}else{
								attrTitle[index].attr_list.push({
									attr_name: items.sname,
									// chooseMe: true,
								})
							}
						}
			
			
					})
			
					page.attrTitle = attrTitle
					var strArr = []
					
					var listX = 0
					for (var i = 0; i < attrTitle.length; i++) {
						var attr_list = attrTitle[i].attr_list
						if (listX == 0) {
							listX = attr_list.length
						} else {
							listX = attr_list.length > 0 ? (attr_list.length * listX) : listX
						}
					}
			
					for (var i = 0; i < listX; i++) {
						//如果strarr原本有数据
						if (page.strArr[i]) {
							strArr.push({
								"cbj": price1,
								"yj": price2,
								"sj": price3,
								"unit": size,
								"kucun": kucun,
								"img": page.strArr[i].image, //图片
								"bar_code": '', // 条形码
								"attr_list": [],
								"cid": page.strArr[i].cid
							})
						} else {
							//没有数据的时候新增商品属性值
							strArr.push({
								"cbj": price1,
								"yj": price2,
								"sj": price3,
								"unit": size,
								"kucun": kucun,
								"img": '', //图片
								"bar_code": '', // 条形码
								"attr_list": [],
								"cid": ''
							})
						}
					}
			
					var th_title = JSON.parse(JSON.stringify(attrTitle))
			
					digui(th_title, 0, listX)
			
					strArr.filter(items=>{
			
						var attrStr = ''
						var attrStr1 = ''
			
						items.attr_list.filter(item=>{
			
							attrStr += ',' + item.attr_name
			
						})
			
						let attr_list = items.attr_list
			
						attr_list.filter(item=>{
			
							attrStr1 += ',' + item.attr_name
			
						})
			
			
						attrStr = attrStr.replace(',','')
						attrStr1 = attrStr1.replace(',','')
						
						page.imgArr.filter(its=>{
							debugger
							if(its.attrStr == attrStr){
								items.image = its.image
							}
						})
			
					})

					
					let newAttrTitle = []
					strArr[0].attr_list.filter(items=>{
						attrTitle.filter(item=>{
							if(items.attr_group_name == item.attr_group_name){
								newAttrTitle.push(item)
							}
						})
					})
					page.attrTitle = newAttrTitle
			
					page.strArr = []
					setTimeout(function(){
						page.strArr = strArr
						
						console.log(page.attrTitle)
						console.log(page.strArr)
					},5)
			
					function digui(th_title, i, _listX) {
						// 如果该循环的子项没有东西则停止递归
						if (!th_title[i]) {
							if (i < (th_title.length - 1)) {
								th_title.splice(i, 1)
								digui(th_title, i, _listX)
								return
							}
							return
						}
			
						// 如果该项属性的没有属性值，则删除该项重新递归
						if (th_title[i].attr_list.length == 0) {
							th_title.splice(i, 1)
							digui(th_title, i, _listX)
							return
						}
			
						var xx = 0
						if (i == 0) {
							// 第一个规格属性的格式是白色白色白色,黑色黑色黑色
							for (var j = 0; j < th_title[i].attr_list.length; j++) {
								var value = th_title[i].attr_list[j].attr_name
								for (var x = 0; x < listX / th_title[i].attr_list.length; x++) {
									var name = th_title[i].attr_group_name
									strArr[xx].attr_list.push({
										'attr_id': '',
										'attr_name': value,
										'attr_group_name': name
									})
									xx++
								}
							}
						} else if (i < th_title.length - 1) {
			
							_listX = Math.round(_listX / th_title[i - 1].attr_list.length)
							// 外面这层循环代表当前属性在内循环完成之后进入新的循环,比如白色白色黑色黑色红色红色,完成之后再次白色白色黑色黑色红色红色循环,总行数除以前一个属性每个属性有多少行,得出总循环数
							for (var l = 0; l < listX / _listX; l++) {
								for (var j = 0; j < th_title[i].attr_list.length; j++) {
									var value = th_title[i].attr_list[j].attr_name
									// 当前规格的前一个每个属性行数,除当前
									for (var x = 0; x < _listX / th_title[i].attr_list.length; x++) {
										var name = th_title[i].attr_group_name
										strArr[xx].attr_list.push({
											'attr_id': '',
											'attr_name': value,
											'attr_group_name': name
										})
										xx++
									}
								}
							}
			
						} else {
							// 后面的规格属性格式是x,l,xl x,l,xl循环
							for (var x = 0; x < listX / th_title[i].attr_list.length; x++) {
								for (var j = 0; j < th_title[i].attr_list.length; j++) {
									var value = th_title[i].attr_list[j].attr_name
									var name = th_title[i].attr_group_name
									strArr[xx].attr_list.push({
										'attr_id': '',
										'attr_name': value,
										'attr_group_name': name
									})
									xx++
								}
							}
						}
						i++
						if (i < th_title.length) {
							digui(th_title, i, _listX)
						}
					}
					$("#maskClass").css('display', 'none')
				})

				$('body', parent.document).find('.maskClose').unbind().bind('click', function() {
					$('body', parent.document).find("#maskClass").remove()
				})
        this.emptyAttr = false
			}
		},

    // 添加运费弹框方法 //

    dialogShow4() {
      this.ruleForm5.templateName = ''
      this.tableData = []
      this.dialogVisible3 = true
    },

    handleClose3(done) {
      this.dialogVisible3 = false
      this.$refs['ruleForm5'].clearValidate()
    },

    Delete(value,index) {
      this.cities = this.cities.concat(value.name.split(","))
      this.tableData.splice(index,1)
    },

    // 添加运费
    submitForm4(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm4);
        if (valid) {
          try {
            addFreight({
              api: 'admin.goods.addFreight',
              name: this.ruleForm5.templateName,
              hiddenFreight: encodeURIComponent(JSON.stringify(this.tableData))
            }).then(res => {
              if(res.data.code == '200') {
                console.log(res);
                this.$message({
                  message: '添加成功',
                  type: 'success',
                  offset: 100
                })
                this.getFreightInfos()
                this.dialogVisible3 = false
              }
            })
          } catch (error) {
            this.$message({
              message: error.message,
              type: 'error',
              showClose: true
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    // 添加运费规则弹框方法
    dialogShow5() {
      this.isIndeterminate = false
      this.checkedCities= []
      this.ruleForm6.freight= null
      if(this.cities.length == 0 && this.tableData.length !== 0) {
        this.$message({
            message: '添加失败，地区运费规则已全部设置!',
            type: 'error',
            offset: 100
        })
      } else {
        this.dialogVisible4 = true
      }
      
      this.checkAll = false
      if(this.tableData.length === 0) {
        this.getRegions()
        
      }
    },
    
    handleClose4(done) {
      this.dialogVisible4 = false
      this.$refs['ruleForm6'].clearValidate()
    },

    // 获取城市列表
    async getRegions() {
      const res = await getRegion({
        api: 'admin.goods.getRegion',
        level: 2
      })
      console.log(res);
      this.cities = res.data.data.map(item => {
        return item.g_CName
      })
    },

    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },

    determine(formName) {
      this.$refs[formName].validate(async (valid) => {
        if(this.checkedCities.length == 0) {
          this.$message({
            message: '请选择省份',
            type: 'error',
            offset: 100
          })
        } else {
          if (valid) {
            try {
              var obj = {
                one: this.ruleForm6.freight,
                name: this.checkedCities.join(),
                freight: this.ruleForm6.freight
              }
              this.tableData.push(obj)
              console.log(this.tableData);
              this.cities = this.cities.filter(item => {
                if (!this.checkedCities.includes(item)) { 
                    return item
                }
              })
              this.dialogVisible4 = false
            } catch (error) {
              this.$message({
                message: '请输入运费',
                type: 'error',
                offset: 100
              })
            }
          } else {
            this.$message({
              message: '请输入运费',
              type: 'error',
              offset: 100
            })
            // return false;
          }
        }
      });
    },


    // 发布商品
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        console.log(this.strArr);
        if (valid) {
          try {
            console.log(this.strArr);
            $.ajax({
              cache: true,
              type: "POST",
              dataType: "json",
              url: this.goodsEditorBase+"/admin/goods/addGoods",
              data: {
                // api: 'admin.goods.addGoods',
                storeId: getStorage('rolesInfo').storeId,
                accessId: getStorage('laike_admin_uaerInfo').token,
                storeType: 8,
                pId: this.$route.query.name && this.$route.query.name !== 'copy' ? this.$route.query.id : null,
                mch_id: this.$route.query.name && this.$route.query.name == 'copy' ? getStorage('laike_admin_uaerInfo').mchId : null,
                // 基本信息
                productTitle: this.ruleForm.goodsTitle,
                subtitle: this.ruleForm.subtitle,
                keyword: this.ruleForm.keywords,
                productClassId: this.ruleForm.goodsClass[this.ruleForm.goodsClass.length-1],
                brandId: this.ruleForm.goodsBrand,
                coverMap: this.ruleForm.goodsCover,
                showImg: this.ruleForm.goodsShow.join(','),
                // 商品属性
                initial: `cbj=${this.ruleForm.cbj},yj=${this.ruleForm.yj},sj=${this.ruleForm.sj},kucun=${this.ruleForm.kucun},unit=${this.ruleForm.unit},stockWarn=${this.ruleForm.inventoryWarning}`,
                attrGroup: JSON.stringify(this.attrTitle),
                attrArr: JSON.stringify(this.strArr),
                // 商品设置
                stockWarn: this.ruleForm.inventoryWarning,
                freightId: this.ruleForm.freight,
                sType: this.ruleForm.checkedLabel.join(),
                active: parseInt(this.ruleForm.checkedActivity),
                displayPosition: this.ruleForm.checked.length ? this.ruleForm.checked.join(',') : null,
                volume: this.ruleForm.virtualSales,
                sort: this.ruleForm.sequence,
                // 产品内容
                content: this.ruleForm.content,
                unit: this.ruleForm.unit
              },
              async: true,
              success: (res) => {
                console.log(res);
                if(res.code == '200') {
                  this.$message({
                    message: '成功',
                    type: 'success',
                    offset: 100
                  })
                  this.$router.go(-1)
                } else {
                  this.$message({
                    message: res.message,
                    type: 'error',
                    offset: 100
                  })
                }
              },
            })
          } catch (error) {
            this.$message({
              message: error.message,
              type: 'error',
              offset: 100
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }

  }
}


</script>

<style scoped  lang="less">
.container {
  width: 100%;
  /deep/.el-form {
    padding-bottom: 38px;
    .header {
      width: 100%;
      height: 60px;
      line-height: 60px;
      border-bottom: 1px solid #E9ECEF;
      padding-left: 20px;
      span {
        font-weight: 400;
        font-size: 16px;
        color: #414658;
      }
    }
    .el-input {
      width: 400px;
      height: 40px;
      input {
        width: 400px;
        height: 40px;
      }
    }
    .composite {
      input {
        width: 345px !important;
      }
    }


    .basic-info {
      width: 100%;
      background-color: #fff;
      margin-bottom: 16px;
      .basic-block {
        margin-top: 40px;
        padding: 0 20px 40px 52px;
        .goods-img {
          width: 100%;
        }

        .goods-class {
          display: flex;
          .el-form-item__content {
            display: flex;
            margin-left: 0 !important;
            .select-input {
              width: 294px !important;
              margin-right: 10px;
              .el-input {
                width: 294px !important;
                input {
                  width: 294px !important;
                }
              }
            }
            button {
              width: 96px;
              height: 38px;
              border: 1px solid #2890FF;
              border-radius: 4px;
              background-color: #fff;
              color: #2890FF;
              margin-left: 10px;
              margin: 0;
              padding: 0;
            }
          }
        }

        .goods-brand {
          display: flex;
          .el-form-item__content {
            display: flex;
            margin-left: 0 !important;
            .select-input {
              width: 294px !important;
              margin-right: 10px;
              .el-input {
                width: 294px !important;
                input {
                  width: 294px !important;
                }
              }
            }
            button {
              width: 96px;
              height: 38px;
              border: 1px solid #2890FF;
              border-radius: 4px;
              background-color: #fff;
              color: #2890FF;
              margin-left: 10px;
              margin: 0;
              padding: 0;
            }
          }
        }
      }
    }

    .goods-attribute {
      width: 100%;
      background-color: #fff;
      margin-bottom: 16px;
      .attribute-block {
        margin-top: 40px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        padding: 0 20px 0 23px;
        .el-form-item {
          display: flex;
          .el-form-item__label {
            width: 130px !important;
          }
          .el-form-item__content {
            margin-left: 0 !important;
          }
          input {
            padding-right: 0px !important;
          }
        }

        .inventory {
          margin-right: auto;
          margin-left: 9px;
        }

        .add-attribute {
          width: 100%;
          button {
            width: 96px;
            height: 38px;
            border: 1px solid #2890FF;
            border-radius: 4px;
            background-color: #fff;
            color: #2890FF;
            margin-left: 10px;
            margin: 0;
            padding: 0;
          }
        }
        
      }
    }

    .goods-set {
      width: 100%;
      background-color: #fff;
      margin-bottom: 16px;
      .set-block {
        margin-top: 40px;
        padding: 0 20px 25px 23px;
        .el-form-item {
          display: flex;
          .el-form-item__label {
            width: 130px !important;
          }
          .el-form-item__content {
            margin-left: 0 !important;
          }
        }

        .inventory-warning {
          .el-input {
            width: 140px;
            height: 40px;
            margin: 0 8px;
            input  {
              width: 140px;
              height: 40px;
            }
          }
          .grey {
            color: rgb(151, 160, 180);
            margin-left: 14px;
          }
        }

        .freight-set {
          button {
            width: 96px;
            height: 38px;
            border: 1px solid #2890FF;
            border-radius: 4px;
            background-color: #fff;
            color: #2890FF;
            margin-left: 8px !important;
            margin: 0;
            padding: 0;
          }
        }

        .activity-class {
          .el-checkbox__inner {
            border-radius: 50px;
          }
        }

        .show-local {
          .el-form-item__content {
            display: flex;
          }
          .show-font {
            color: #97a0b4;
            margin-left: 20px;
          }
        }
        
      }
    }

    .detail-container {
      width: 100%;
      background-color: #fff;
      margin-bottom: 16px;
      .detail-block {
        margin-top: 40px;
        padding: 0 20px 70px 23px;
        .el-form-item {
          .quillWrapper {
            width: 1425px;
            height: 341px;
            .quillWrapper {
              .ql-container {
                height: 300px;
              }
            }
          }
          .el-form-item__label {
            width: 130px !important;
          }
          .el-form-item__content {
            margin-left: 129px !important;
          }
        }
        
      }
    }  

    .footer-button {
      position: fixed;
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
      button {
        width: 70px;
        height: 40px;
        padding: 0;
      }
      .bgColor {
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
    }
    .el-form-item__label {
      font-weight: normal;
    }
  }

  .dialog-class {
    /deep/.el-dialog {
      width: 640px;
      height: 490px;
      .el-dialog__header {
        width: 100%;
        height: 58px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 2px solid #E9ECEF;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
        }
      }
      .el-dialog__body {
        display: flex;
        justify-content: center;
        .el-form-item {
          display: flex;
          .el-form-item__content {
            width: 400px;
            margin-left: 0px !important;
          }
          .select-input {
            width: 580px;
            height: 40px;
          }
        }

        .superior {
          display: flex;
          .el-select {
            width: 100px !important;
            &:not(:first-child) {
              margin-left: 8px;
            }
            .el-input {
              width: 100px !important;
              input {
                width: 100px !important;
              }
            }
          }
        }

        .upload-img {
          .el-form-item__content {
            display: flex;
            align-items: center;
            .avatar-uploader .el-upload {
              border: 1px dashed #d9d9d9;
              border-radius: 6px;
              cursor: pointer;
              position: relative;
              overflow: hidden;
            }
            .avatar-uploader .el-upload:hover {
              border-color: #409EFF;
            }
            .avatar-uploader-icon {
              font-size: 28px;
              color: #8c939d;
              width: 80px;
              height: 80px;
              line-height: 80px;
              text-align: center;
            }
            .avatar {
              width: 80px;
              height: 80px;
              display: block;
            }
            .removeImg {
              position: absolute;
              right: 0;
              top: 0;
            }

            .text {
              margin-bottom: 10px;
              margin-left: 5px;
            }
          }
        }

        .form-footer {
          width: 100%;
          padding-left: 100Px;
          position: relative;
          height: 72px;
          line-height: 72px;
          bottom: 0;
        }
      }
    }
  }

  .dialog-brand {
    /deep/.el-dialog {
      width: 640px;
      min-height: 526px;
      .el-dialog__header {
        width: 100%;
        height: 58px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 2px solid #E9ECEF;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
        }
      }
      .el-dialog__body {
        display: flex;
        justify-content: center;
        .el-form {
          padding-bottom: 0;
        }
        .el-form-item {
          display: flex;
          .el-form-item__content {
            width: 400px;
            margin-left: 0px !important;
          }
          .select-input {
            width: 400px;
            height: 40px;
          }
        }

        .upload-img {
          .el-form-item__content {
            display: flex;
            align-items: center;
            .avatar-uploader .el-upload {
              border: 1px dashed #d9d9d9;
              border-radius: 6px;
              cursor: pointer;
              position: relative;
              overflow: hidden;
            }
            .avatar-uploader .el-upload:hover {
              border-color: #409EFF;
            }
            .avatar-uploader-icon {
              font-size: 28px;
              color: #8c939d;
              width: 80px;
              height: 80px;
              line-height: 80px;
              text-align: center;
            }
            .avatar {
              width: 80px;
              height: 80px;
              display: block;
            }
            .removeImg {
              position: absolute;
              right: 0;
              top: 0;
            }

            .text {
              margin-bottom: 10px;
              margin-left: 5px;
            }

            .el-form-item__error {
              margin-top: 10px;
            }
          }
        }

        .form-footer {
          padding-left: 90Px;
        }
      }
    }
  }

  .dialog-freight {
    // 弹框样式
    /deep/.el-dialog {
      width: 724px;
      height: 680px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      margin: 0 !important;
      .el-dialog__header {
        width: 100%;
        height: 58px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 2px solid #E9ECEF;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
        }
      }

      .el-dialog__body {
        .el-form {
          padding-bottom: 0px !important;
        }
        .notice {
          padding: 40px 0 0 60px;
          display: flex;
          flex-direction: column;
          .title {
            .el-form-item__label {
              font-weight: normal;
            }
            .el-form-item__label {
              color: #414658;
            }
            .el-form-item__content {
              display: flex;
              input {
                width: 420px;
                height: 40px;
              }
            }
          }
          
        }
        .form-footer {
          width: 174px;
          height: 72px;
          position: absolute;
          bottom: 0;
          right: 0;
          .el-form-item {
            padding: 0 !important;
            height: 100%;
            .el-form-item__content {
              height: 100%;
              line-height: 72px;
              margin: 0 !important;
            }
          }
          .bgColor:hover {
            background-color: #fff;
            color: #2890ff;
            border: 1px solid #2890ff;
          }
        }

        .dictionary-list {
          width: 558px !important;
          border-radius: 4px;
          margin-left: 140px;
          .el-table {
            .el-table__header-wrapper {
              thead {
                tr {
                  background-color: #f4f7f9 !important;
                  th{
                    height: 61px;
                    text-align: center;
                    font-size: 14px;
                    font-weight: bold;
                    color: #414658;
                    background-color: #f4f7f9 !important;
                  }
                }
              }
            }
            .el-table__body-wrapper {
              background-color: #f4f7f9;
              tbody {
                tr {
                  td{
                    height: 92px;
                    text-align: center;
                    font-size: 14px;
                    color: #414658;
                    font-weight: 400;
                    padding: 0;
                  }
                }
              }
            }
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
          }
        }
      }
    }
  }

  .dialog-freightTemplate {
    // 弹框样式
    /deep/.el-dialog {
      width: 680px;
      height: 680px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      margin: 0 !important;
      .el-dialog__header {
        width: 100%;
        height: 58px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 2px solid #E9ECEF;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
        }
      }

      .el-dialog__body {
        padding: 41px 0px 0px 0px !important;
        // border-bottom: 1px solid #E9ECEF;
        .check-provinces {
          .el-checkbox {
            width: 120px;
            height: 30px;
            margin-right: 14px;
          }
          .el-form-item__content {
            padding-top: 5px;
          }
        }
        .el-form-item__content {
          line-height: 30px;
        }
        .el-form-item {
          margin-bottom: 12px;
        }
        .el-form-item__label {
          font-weight: normal;
        }
        .el-input__inner {
          width: 304px;
          height: 40px;
        }
        .form-footer {
          width: 100%;
          height: 72px;
          position: absolute;
          bottom: 0;
          right: 0;
          border-top: 1px solid #E9ECEF;
          .el-form-item {
            padding: 0 !important;
            height: 100%;
            display: flex;
            justify-content: flex-end;
            margin-right: 17px;
            .el-form-item__content {
              height: 100%;
              line-height: 72px;
              margin: 0 !important;
            }
          }
          .bgColor:hover {
            background-color: #fff;
            color: #2890ff;
            border: 1px solid #2890ff;
          }
        }
      }
    }
  }

  /deep/.el-form-item__label {
    color: #414658;
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

  .model {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    opacity: .5;
    background: #000;
    z-index: 2000;
  }

}
</style>

<style>
.zZindex {
  z-index: 99999999 !important;
}
.maskNew{
	position: fixed;
	z-index: 10000;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	background: rgba(0,0,0,0.6);
	display: block;
}

/* 添加属性弹窗 */
.maskNew .mask-content{position: relative; background-color: #fff; display: flex !important; flex-direction: column;padding-top: 0; width: 680px; height: 510px!important; top: 50%;left: 50%;transform: translate(-50%,-50%);margin: 0!important;}
.maskNew .mask-title{line-height: 58px;border-bottom: 2px solid #E9ECEF;font-size: 16px;color: #414658;font-weight: bold;padding-left: 19px;margin-bottom: 0;}
.maskNew .mask-content-data{padding: 40px 40px 0;overflow: hidden;flex: 1;}
.maskNew .mask-content-data>div{display: flex;}
.maskNew .shooser_attrDiv{width: 520px;height: 272px;background: #F4F7F9;margin-top: 10px;overflow-x: hidden;overflow-y: auto;padding: 15px;box-sizing: border-box;font-size: 14px;}
.maskNew .shooser_attrDiv select{width: 240px;height: 36px;font-size: 14px;color: #414658!important;}
.maskNew .shooser_attrDiv>div,#choose_attrDiv .custom_attr{display: flex;align-items: center;}
.maskNew .shooser_attrDiv ul,.shooser_attrDiv label{margin-bottom: 0;}
.maskNew .shooser_attrDiv ul{width: 100%;max-height: 171px;position: absolute;background: #FFFFFF;border: 1px solid #D3DAE3;border-top: 0;overflow-y: auto;background: #FFFFFF;z-index: 99;}
.maskNew .shooser_attrDiv li{height: 34px;}
.maskNew .shooser_attrDiv li:hover{background: #F4F7F9;}
.maskNew .shooser_attrDiv li>label{width: 100%;height: 100%;display: flex;align-items: center;padding: 10px;box-sizing: border-box;}
.maskNew .attr_title{width: 90px;height: 36px;display: flex;align-items: center;justify-content: center;border: 1px solid #2890FF;border-radius: 2px;color: #2890FF;position: relative;margin: 20px 0;background: #FFFFFF;}
.maskNew .attr_title img{position: absolute;right: 0;top: 0;width: 12px;height: 12px;}
.maskNew .attr_content{display: flex;flex-wrap: wrap;}
.maskNew .attr_content label{margin-right: 20px;margin-bottom: 10px;}
.maskNew .custom_attrDiv .left_text{
	display: inline-block;
	width: 70px;
	text-align: right;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.maskNew .custom_attrDiv input[type='text']{
	padding-left: 10px;
}
.maskNew .custom_attrDiv a.addBtn,.custom_attrDiv a.removeBtn{
	display: inline-block;
	width: 88px;height: 36px;box-sizing: border-box;line-height: 34px;text-align: center;margin-left: 10px;
}
.maskNew .custom_attrDiv a.addBtn{
	color: #2890FF;
	border: 1px solid #2890FF;
}
.maskNew .custom_attrDiv a.removeBtn{
	color: #828B97;
	border: 1px solid #828B97;
}
.maskNew .custom_attrVlue{
	flex-wrap: wrap;
}
.maskNew .custom_attr{display: flex;}
.maskNew .custom_attrVlue a{
	display: flex;
	align-items: center;
	height: 36px;
	padding: 0 10px;
	background: #FFFFFF;
	color: #414658;
	border: 1px dashed #B2BCD1;
	margin-bottom: 10px;
	margin-right: 10px;
}
.maskNew .custom_attrVlue a img{
	width: 12px;height: 12px;margin-left: 10px;
}

.maskNew .mask-bottom{display: flex;justify-content: flex-end;border-top: 1px solid #E9ECEF;margin-top: auto;}
.maskNew .mask-bottom input[type=button]{width: 112px;height: 36px;line-height: 36px;text-align: center;margin: 16px 0;border: 0;outline: 0;cursor: pointer;}
.maskNew .mask-bottom input[type=button]:first-child{border: 1px solid #2890FF;color: #2890FF;margin-right: 10px;background: #FFFFFF;}
.maskNew .mask-bottom input[type=button]:last-child{background: #2890FF;color: #FFFFFF;margin-right: 20px;}

.maskNew .maskContent1 input[type=submit] {
	width: 100px;
	height: 40px;
	border: 1px solid #eee;
	border-radius: 5px;
	background: #008DEF;
	color: #fff;
	font-size: 16px;
	line-height: 40px;
	display: inline-block;


}

.maskNew .closeMaskBtn {
	width: 100px;
	height: 40px;
	border-radius: 5px;
	background: #fff;
	color: #008DEF;
	border: 1px solid #008DEF;
	font-size: 16px;
	line-height: 40px;
	display: inline-block;
	text-align: center;
	box-sizing: border-box;
	cursor: pointer;
	margin-right: 10px;
}

.maskNew .closeA {
	position: absolute;
	right: 10px;
	top: 15px;
	width: 30px;
	height: 30px;
	color: #eee;
}

.maskNew .checkbox{
	position: relative;
	display: inline-block;
	color: #414658;
	font-size: 14px;
	line-height: 14px;
	padding-left: 22px;
	-webkit-user-select:none;
    -moz-user-select:none;
    -ms-user-select:none;
    user-select:none;
}

.maskNew .checkbox i{
	position: absolute;
	display: block;
	left: 0;top: 0;
	width: 14px;height: 14px;
	border: 1px solid #B2BCD1;
	border-radius:2px;
	box-sizing: border-box;
}

</style>

<style scoped lang="less">
/deep/.attribute-table {
  width: 100% !important;
  .el-form-item__content {
    width: 100%;
    padding-left: 120px !important;
  }
  .el-table--scrollable-x .el-table__body-wrapper {
      overflow-x: auto;
  }
  .el-table .hidden-columns {
    visibility: hidden;
    position: absolute;
    z-index: -1;
  }
  .el-table--fit td.gutter,
  .el-table--fit th.gutter {
    border-right-width: 1px;
  }
  .el-table--scrollable-x .el-table__body-wrapper {
    overflow-x: auto;
  }
  .el-table--scrollable-y .el-table__body-wrapper {
    overflow-y: auto;
  }
  .el-table thead {
    color: #414658;
    font-weight: 500;
  }
  .el-table thead.is-group th {
    background: #f4f7f9;
  }
  .el-table td,
  .el-table th {
    padding: 12px 0;
    min-width: 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    text-overflow: ellipsis;
    vertical-align: middle;
    position: relative;
    text-align: center;
  }

  .el-table {
    .el-input{
      text-align: center;
      width: 140px;
      input {
        width: 140px;
        text-align: center;
      }
    }
  }

  .el-table .l-upload{
    display: flex;
    justify-content: center;
  }

  .el-table th {
    background-color: #F4F7F9;
  }
}

</style>