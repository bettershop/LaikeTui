<template>
  <div class="container">
    <el-form ref="ruleForm" class="form-search" label-width="100px">
      <div class="goods-info">
        <div class="header">
          <span>商品信息</span>
        </div>
        <div class="goods-block">
            <el-table :data="goodsDate" style="max-height: 350px;" :header-cell-style="header">
              <el-table-column prop="" align="center" label="商品名称">
                <template slot-scope="scope">
                  <img :src="scope.row.imgurl" alt="">
                  <span>{{ scope.row.p_name }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="size" align="center" label="商品规格">
              </el-table-column>
              <el-table-column prop="pid" align="center" label="商品编码">
              </el-table-column>
              <el-table-column prop="p_price" align="center" label="单价">
              </el-table-column>
              <el-table-column prop="num" align="center" label="数量">
              </el-table-column>
              <el-table-column prop="freight" align="center" label="运费">
              </el-table-column>
              <el-table-column prop="z_price" align="center" label="小计">
              </el-table-column>
            </el-table>
            <div class="totle-price">
              <div class="totle-price-item">
                <span>合计：</span>
                <span>{{ totle_price }}</span>
              </div>
            </div>
        </div>
      </div>
      <div class="apply-info" v-if="applyInfo">
        <div class="header">
          <span>申请信息</span>
        </div>
        <div class="apply-block">
          <el-form ref="ruleForm" class="form-search" label-width="100px">
            <el-form-item label="售后类型：">
              {{ applyInfo.re_type == 1 ? '退货退款' : applyInfo.re_type == 2 ? '仅退款' : '换货' }}
            </el-form-item>
            <el-form-item label="申请原因：">
              {{ applyInfo.content }}
            </el-form-item>
            <el-form-item class="right" label="订单编号：">
              {{ applyInfo.sNo }}
            </el-form-item>
            <el-form-item label="申请金额：">
              <span v-if="applyInfo.re_apply_money">￥{{ applyInfo.re_apply_money.toFixed(2) }}</span>
            </el-form-item>
            <el-form-item label="售后凭证：">
              <ul class="sales-img" v-if="sales_imgs">
                <!-- <li v-for="(item,index) in sales_imgs" :key="index">
                  <img :src="item" alt="" >
                </li> -->
                <viewer :images="sales_imgs">
                  <img v-for="src in sales_imgs" :src="src" :key="src">
                </viewer>
              </ul>
              <img v-if="applyInfo.imgurl" :src="applyInfo.imgurl[0]" alt="">
            </el-form-item>
            <el-form-item class="right" label="申请时间：">
              {{ applyInfo.re_time | dateFormat }}
            </el-form-item>
          </el-form>
        </div>
      </div>

      <div class="audit-info" v-if="applyInfo">
        <div class="header">
          <span>审核记录</span>
        </div>
        <div class="audit-block">
          <div class="item">
            <span class="gray">审核结果：</span>
            <span v-if="applyInfo.r_type == 0">待审核</span>
            <span v-if="applyInfo.r_type == 1">退换中</span>
            <span v-if="applyInfo.r_type == 3">退换中</span>
            <span v-if="applyInfo.r_type == 11" style="color: #7CCD7C;">退换成功</span>
            <span v-if="applyInfo.r_type == 5" style="color: #ff2a1f;">退换失败</span>
            <span v-if="applyInfo.r_type == 4" style="color: #7CCD7C;">退款成功</span>
            <span v-if="applyInfo.r_type == 9" style="color: #7CCD7C;">退款成功</span>
            <span v-if="applyInfo.r_type == 12" style="color: #7CCD7C;">换货成功</span>
            <span v-if="applyInfo.r_type == 2" style="color: #ff2a1f;">退款失败</span>
            <span v-if="applyInfo.r_type == 8 && applyInfo.r_type == '已收货'" style="color: #7CCD7C;">退换成功</span>
            <span v-if="applyInfo.r_type == 8 && applyInfo.r_type !== '已收货'" style="color: #ff2a1f;">退款失败</span>
            <!-- <span v-else style="color: #ff2a1f;">退款失败</span> -->
          </div>
          <div class="item">
            <span class="gray" v-if="applyInfo.audit_time">审核时间：{{ applyInfo.audit_time | dateFormat }}</span>
            <span class="gray" v-else>审核时间：</span>
          </div>
          <div class="item" v-if="rdata && rdata[0]">
            <span class="gray">回寄物流：{{ rdata[0].express_num }}({{ rdata[0].express }})</span>
          </div>
          <div class="item" v-if="rdata && rdata[0]">
            <span class="gray">回寄时间：{{ rdata[0].add_data | dateFormat }}</span>
          </div>
          <div class="item" v-if="rdata && rdata[1]">
            <span class="gray">售后物流：{{ rdata[1].express_num }}({{ rdata[1].express }})</span>
          </div>
          <div class="item" v-if="rdata && rdata[1]">
            <span class="gray">回寄时间：{{ rdata[1].add_data | dateFormat }}</span>
          </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import salesReturnDetails from '@/webManage/js/order/salesReturn/salesReturnDetails'
export default salesReturnDetails
</script>

<style scoped lang="less">
@import '../../../../webManage/css/order/salesReturn/salesReturnDetails.less';
</style>