<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="库存列表" @click.native.prevent="$router.push('/goods/inventoryManagement/inventoryList')"></el-radio-button>
        <el-radio-button label="库存预警" @click.native.prevent="$router.push('/goods/InventoryWarnings/InventoryWarning')"></el-radio-button>
        <el-radio-button label="入货详情" @click.native.prevent="$router.push('/goods/cargoDetail')"></el-radio-button>
        <el-radio-button label="出货详情" @click.native.prevent="$router.push('/goods/shippingDetail')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.storeName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称"></el-input>
          <el-input v-model="inputInfo.goodsName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入商品名称"></el-input>
          <div class="select-date">
            <el-date-picker v-model="inputInfo.date"
              type="datetimerange" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss"
              :editable="false">
            </el-date-picker>
          </div>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button>
        </div>
      </div>
	  </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="attrId" label="序号" width="88">
        </el-table-column>
        <el-table-column prop="goods_img" label="商品名称" width="230">
          <template slot-scope="scope">
            <div class="goods-info">
              <img :src="scope.row.imgurl" alt="">
              <span>{{ scope.row.product_title }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="售价" width="154">
          <template slot-scope="scope">
            <span>{{ scope.row.price.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="specifications" label="规格" width="154">
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
            <template slot-scope="scope">
              <span class="ststus">{{ scope.row.status == 2 ? '上架' : scope.row.status == 3 ? '下架' : '待上架'}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="total_num" label="总库存" width="170">
        </el-table-column>
        <el-table-column prop="num" label="剩余库存" width="170">
        </el-table-column>
        <el-table-column prop="name" show-overflow-tooltip label="所属店铺" width="154">
        </el-table-column>
        <el-table-column prop="upper_shelf_time" label="上架时间" width="200">
          <template slot-scope="scope">
            <span v-if="!scope.row.upper_shelf_time"></span>
            <span v-else>{{ scope.row.upper_shelf_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="warningRecord(scope.row)">预警记录</el-button>
                <el-button icon="el-icon-circle-plus-outline" @click="dialogShow2(scope.row)">添加库存</el-button>
              </div>
            </div>
		      </template>
        </el-table-column>
	    </el-table>
      <div class="pageBox" ref="pageBox" v-if="showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页" @size-change="handleSizeChange"
          :page-sizes="pagesizes" :current-page="pagination.page" @current-change="handleCurrentChange" :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加库存"
        :visible.sync="dialogVisible2"
        :before-close="handleClose2"
      >
        <div class="dialog-container">
          <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
            <el-form-item label="增加库存" prop="addNum">
              <el-input v-model="ruleForm2.addNum" placeholder="请输入增加库存量"></el-input>
            </el-form-item>
            <el-form-item label="剩余库存">
              <span>{{ ruleForm2.num }}</span>
            </el-form-item>
            <el-form-item label="总库存">
              <span>{{ ruleForm2.total_num }}</span>
            </el-form-item>
            <div class="form-footer">
              <el-form-item>
                <el-button class="bgColor" @click="handleClose2">取 消</el-button>
                <el-button class="bdColor" type="primary" @click="determine('ruleForm2')">确 定</el-button>
              </el-form-item>
            </div>
        </el-form>
        </div>
        <!-- <div slot="footer" class="form-footer">
          
        </div> -->
      </el-dialog>
    </div>

    <div class="dialog-export">
      <!-- 弹框组件 -->
      <el-dialog
        title="导出数据"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <div class="item" @click="exportPage">
          <i class="el-icon-document"></i>
          <span>导出本页</span>
        </div>
        <div class="item item-center" @click="exportAll">
          <i class="el-icon-document-copy"></i>
          <span>导出全部</span>
        </div>
        <div class="item" @click="exportQuery">
          <i class="el-icon-document"></i>
          <span>导出查询</span>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import InventoryWarning from '@/webManage/js/goods/inventoryManagement/InventoryWarning'
export default InventoryWarning
</script>

<style scoped lang="less">
@import '../../../webManage/css/goods/inventoryManagement/InventoryWarning.less';
</style>