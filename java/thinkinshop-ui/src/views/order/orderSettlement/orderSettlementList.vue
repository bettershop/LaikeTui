<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input size="medium" v-model="search.orderNo" class="Search-input" placeholder="请输入结算订单号/订单编号"></el-input>
        </div>
        <div class="query-input">
          <el-input size="medium" v-model="search.mchName" class="Search-input" placeholder="请输入店铺名称"></el-input>
        </div>
        <div class="query-input">
          <el-select class="select-input" v-model="search.type" placeholder="请选择结算状态">
            <el-option label="已结算" value="1">
              <div>已结算</div>
            </el-option>
            <el-option label="待结算" value="0">
              <div>待结算</div>
            </el-option>
          </el-select>
        </div>

        <div class="query-input">
          <el-date-picker v-model="search.time" type="datetimerange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
          </el-date-picker>
        </div>

        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" @click="isExportBox=!isExportBox">{{$t('DemoPage.tableExamplePage.export')}}</el-button>
        </div>
      </div>
    </div>

    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
        class="el-table"
        style="width: 100%"
        :height="tableHeight">
        <el-table-column prop="id" label="结算单号" width="100">
        </el-table-column>
        <el-table-column prop="settlementPrice" label="结算金额" width="100">
        </el-table-column>
        <el-table-column prop="commission" label="佣金" width="100">
        </el-table-column>
        <el-table-column prop="r_commission" label="退还佣金" width="100">
        </el-table-column>
        <el-table-column prop="sNo" label="订单编号" width="200">
        </el-table-column>
        <el-table-column prop="z_price" label="订单金额" width="100">
        </el-table-column>
        <el-table-column prop="shopName" label="店铺名称" width="150">
        </el-table-column>
        <el-table-column prop="return_money" label="退单金额" width="100">
        </el-table-column>
        <el-table-column prop="status_name" label="结算状态" width="100">
        </el-table-column>
        <el-table-column label="结算时间" width="100">
          <template slot-scope="scope" v-if="scope.row.arrive_time!=null">
            {{ scope.row.arrive_time | dateFormat}}
          </template>
        </el-table-column>
        <el-table-column prop="z_freight" label="运费" width="100">
        </el-table-column>
        <el-table-column prop="mch_discount" label="店铺优惠" width="100">
        </el-table-column>
        <el-table-column prop="preferential_amount" label="平台优惠" width="100">
        </el-table-column>
        <el-table-column label="订单生成时间" width="160">
          <template slot-scope="scope">
            {{ scope.row.add_time | dateFormat}}
          </template>
        </el-table-column>


        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="See(scope.row.sNo)">查看</el-button>
                <el-button icon="el-icon-delete" @click="Del(scope.row.id)">删除</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <div class="pageBox" ref="pageBox" v-if="page.showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
          @size-change="handleSizeChange"
          :page-sizes="pagesizes" :current-page="pagination.page"
          @current-change="handleCurrentChange"
          :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

    <div class="dialog-export">
      <!-- 弹框组件 -->
      <el-dialog
        title="导出数据"
        :visible.sync="isExportBox"
        :before-close="isExportBoxClose"
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
import main from "@/webManage/js/order/orderSettlement/orderSettlementList";
export default main;
</script>

<style scoped lang="less">
@import "../../../common/commonStyle/form";
@import "../../../webManage/css/order/orderSettlement/orderSettlementList.less";
</style>
