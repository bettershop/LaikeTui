<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="">
          订单列表<span class="order_dfhNum" :class="[ radio1 == '' ? 'active' : '']">({{ this.$store.getters.orderListNum }})</span>
        </el-radio-button>
        <el-radio-button label="1">
          实物订单<span class="order_dfhNum" :class="[ radio1 == '1' ? 'active' : '']">({{ this.$store.getters.physicalOrderNum }})</span>
        </el-radio-button>
        <el-radio-button label="2">
          自提订单
        </el-radio-button>
        <!-- <el-radio-button label="3">
          虚拟订单
        </el-radio-button>
        <el-radio-button label="4">
          活动订单<span class="order_dfhNum" :class="[ radio1 == '4' ? 'active' : '']">({{ this.$store.getters.activityOrderNum }})</span>
        </el-radio-button> -->
      </el-radio-group>
    </div>

    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.orderInfo" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入订单编号/姓名/电话/会员ID"></el-input>
          <el-input v-model="inputInfo.store" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称"></el-input>
          <el-select class="select-input" v-model="inputInfo.state" placeholder="请选择订单状态">
            <el-option v-for="item in stateList" :key="item.brand_id" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-select class="select-input" v-model="inputInfo.type" placeholder="请选择下单类型">
            <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
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

    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="placeOrder">代客下单</el-button>
      <el-button class="bgColor el-icon-printer" type="primary"  @click="print">打印订单</el-button>
      <el-button class="fontColor" @click="delAll" :disabled="is_disabled" icon="el-icon-delete" >批量删除</el-button>
    </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" type="selection" width="55">
        </el-table-column>
        <el-table-column prop="orderInfo" label="订单信息" width="400" align="left">
          <template slot-scope="scope">
            <div class="head-info">
              <span class="red-dot" v-if="scope.row.status == '代发货'"></span>
              <span style="margin-right:1.875rem">订单编号：{{ scope.row.orderno }}</span>
              <span>商户订单编号：{{ scope.row.mchOrderNo }}</span>
              <span>创建时间：{{ scope.row.createDate }}</span>
              <span>店铺：{{ scope.row.mchName }}</span>
            </div>
            <div class="content-info">
              <div class="img-item">
                <img :src="scope.row.goodsImgUrl" alt="">
              </div>
              <div class="goods-item">
                <span class="name">{{ scope.row.goodsName }}</span>
                <span>规格：{{ scope.row.attrStr }}</span>
                <span>数量：{{ scope.row.needNum}}</span>
                <span>价格：{{ scope.row.goodsPrice }}元</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="orderPrice" label="订单总价" width="150">
          <template slot-scope="scope">
            <span>￥{{scope.row.orderPrice}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="goodsNum" label="数量" width="150">
        </el-table-column>
        <el-table-column prop="price" label="下单类型" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.operation_type == '1' ? '用户下单' : scope.row.operation_type == '2' ? '店铺下单' : '平台下单' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="订单状态" width="150">
        </el-table-column>
        <el-table-column prop="name" label="订单类型" width="150">
          <template slot-scope="scope">
            <span>{{scope.row.otype}}订单</span>
          </template>
        </el-table-column>
        <el-table-column prop="volume" label="买家信息" width="200" show-overflow-tooltip>
          <template slot-scope="scope">
            <div class="id">
              <span class="id-item">会员ID：</span>
              <span>{{ scope.row.userId }}</span>
            </div>
            <div class="name">
              <span class="name-item">会员名称：</span>
              <span>{{ scope.row.userName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="pay" label="支付方法" width="150">
        </el-table-column>
        <el-table-column v-if="['','1','4'].includes(radio1)" prop="expressStr" label="物流信息" width="300">
          <template slot-scope="scope">
            <div class="item">
              快递单号：{{ scope.row.expressStr }}
            </div>
            <div class="item">
              运费：{{ scope.row.freight }}
            </div>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="Details(scope.row)">订单详情</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)" v-if="['待付款','待发货'].includes(scope.row.status)">编辑订单</el-button>
                <el-button icon="el-icon-box" @click="Delivery(scope.row)" v-if="['','1','4'].includes(radio1)" v-show="['待发货'].includes(scope.row.status)">商品发货</el-button>
                <el-button icon="el-icon-truck" @click="dialogShow2(scope.row)"  v-if="scope.row.courier_num && scope.row.courier_num.length>0" class="logistics">查看物流</el-button>
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
        title="物流信息"
        :visible.sync="dialogVisible2"
        :before-close="handleClose2"
      >
        <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <div class="task-container" v-for="(item,index) in logisticsList" :key="index">
            <div class="courier-company">
              快递公司：<span>{{ item.kuaidi_name }}</span>
            </div>
            <div class="courier-no">
              快递单号：<span>{{ item.courier_num }}</span>
            </div>
            <div class="logistics">
              <!-- 物流跟踪：<span>{{ ruleForm.logistics }}</span> -->
              物流跟踪：<span>暂无物流信息！</span>
            </div>
          </div>
        </el-form>
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
import orderLists from '@/webManage/js/order/orderList/orderLists'
export default orderLists
</script>

<style scoped lang="less">
@import '../../../../webManage/css/order/orderList/orderLists.less';
</style>