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
        <el-table-column prop="price" label="售价" width="280">
          <template slot-scope="scope">
            <span>{{ scope.row.price.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="specifications" label="规格" width="280">
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
            <template slot-scope="scope">
              <span class="ststus" :class="[ scope.row.status == 2 ? 'active1' : scope.row.status == 3 ? 'active2' : 'active3' ]">{{ scope.row.status == 2 ? '已上架' : scope.row.status == 3 ? '已下架' : '待上架'}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="flowing_num" label="入库量" width="170">
        </el-table-column>
        <el-table-column prop="name" show-overflow-tooltip label="店铺名称" width="280">
        </el-table-column>
        <el-table-column fixed="right" prop="add_date" label="入库时间" width="200">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
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
import cargoDetails from '@/webManage/js/goods/inventoryManagement/cargoDetails'
export default cargoDetails
</script>

<style scoped lang="less">
@import '../../../webManage/css/goods/inventoryManagement/cargoDetails.less';
</style>