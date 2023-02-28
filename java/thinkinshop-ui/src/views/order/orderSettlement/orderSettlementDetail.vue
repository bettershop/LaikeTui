<template>
  <div class="container">
    <div class="merchants-list">
      <el-form :model="mainData" element-loading-text="拼命加载中..." ref="ruleForm" label-width="115px"
               class="demo-ruleForm">
        <div class="card">
          <div class="title">基本信息</div>
          <el-row :gutter="4">
            <el-col :lg="6">
              <el-form-item label="订单编号:">
                {{mainData.sNo}}
              </el-form-item>
            </el-col>
            <el-col :lg="6">
              <el-form-item label="订单类型:">
                {{mainData.orderTypeName}}订单
              </el-form-item>
            </el-col>
            <el-col :lg="6">
              <el-form-item label="订单状态:">
                {{mainData.status}}
              </el-form-item>
            </el-col>
            <el-col :lg="6">
              <el-form-item label="配送费用:">
                {{orderInfo.z_freight}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="4">
            <el-col :lg="6">
              <el-form-item label="订单来源:">
                <template>
                  {{ source.get(mainData.source.toString()) }}
                </template>
              </el-form-item>
            </el-col>
            <el-col :lg="6">
              <el-form-item label="支付方式:">
                {{mainData.paytype}}
              </el-form-item>
            </el-col>
            <el-col :lg="6">
              <el-form-item label="下单时间:">
                {{mainData.add_time | dateFormat}}
              </el-form-item>
            </el-col>
            <el-col :lg="6">
              <el-form-item label="付款时间:">
                {{mainData.pay_time | dateFormat}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="2">
            <el-col :lg="6">
              <el-form-item label="发货时间:" v-if="mainData.arrive_time!=null">
                {{mainData.arrive_time | dateFormat}}
              </el-form-item>
            </el-col>
            <el-col :lg="6">
              <el-form-item label="收货时间:">
                {{mainData.deliver_time | dateFormat}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="1">
            <el-col :lg="6">
              <el-form-item label="快递单号:">
                {{orderInfo.expressStr}}
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>

    <div class="merchants-list">
      <el-form :model="mainData" element-loading-text="拼命加载中..." ref="ruleForm" label-width="115px"
               class="demo-ruleForm">
        <div class="card">
          <div class="title">收货人信息</div>
          <el-row :gutter="1">
            <el-col :lg="12">
              <el-form-item label="收货人:">
                {{mainData.user_name}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="1">
            <el-col :lg="12">
              <el-form-item label="联系电话:">
                {{mainData.mobile}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="1">
            <el-col :lg="12">
              <el-form-item label="收货地址:">
                {{mainData.address}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="1">
            <el-col :lg="12">
              <el-form-item label="备注信息:">
                {{mainData.remark}}
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
    </div>

    <div class="merchants-list">
      <div class="card">
        <div class="title">收货人信息</div>
        <div class="dictionary-list">
          <el-table :data="goodsList" ref="table" class="el-table" style="width: 100%">
            <el-table-column prop="p_name" align="center" label="商品名称" width="400">
              <template slot-scope="scope">
                <div class="name-info">
                  <img :src="scope.row.pic" alt="">
                  <span>{{ scope.row.p_name }}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="size" align="center" label="商品规格">
            </el-table-column>
            <el-table-column prop="p_id" align="center" label="商品编号">
            </el-table-column>
            <el-table-column prop="p_price" align="center" label="单价">
            </el-table-column>
            <el-table-column prop="num" align="center" label="数量">
            </el-table-column>
            <el-table-column prop="stockNum" align="center" label="库存">
            </el-table-column>
            <el-table-column prop="grade_rate" align="center" label="会员折扣">
            </el-table-column>
            <el-table-column prop="p_price" align="center" label="小计">
              <template slot-scope="scope">
                {{scope.row.p_price*scope.row.num}}
              </template>
            </el-table-column>
            <el-table-column align="center" prop="returnStatus" label="退款状态">
              <template slot-scope="scope">
                {{ scope.row.returnInfo.statusName }}
              </template>
            </el-table-column>

            <el-table-column label="操作" align="center" fixed="right" width="140">
              <template>
                <div class="OP-button">
                  <div class="OP-button-top" @click="$router.push('/goods/inventoryManagement/inventoryList')">
                    <el-button icon="el-icon-view">查看</el-button>
                  </div>
                </div>
              </template>
            </el-table-column>

          </el-table>
        </div>
      </div>
    </div>
    <div class="statistical">
      <div class="goods-totalprice">
        <span class="title name">商品总价：</span>
        <span class="price">￥{{orderInfo.spz_price.toFixed(2)}}
        </span>
      </div>
      <div class="discount-stores">
        <span class="title name">总运费：</span>
        <span class="price">￥{{orderInfo.z_freight.toFixed(2)}}</span>
      </div>
      <div class="discount-stores">
        <span class="title name">店铺优惠：</span>
        <span class="price">￥{{orderInfo.reduce_price.toFixed(2)}}</span>
      </div>
      <div class="discount-platform">
        <span class="title name">平台优惠：</span>
        <span class="price">￥{{orderInfo.preferential_amount.toFixed(2)}}</span>
      </div>
      <div class="totle-pay">
        <span class="title-totle  name">合计支付：</span>
        <span class="red price">￥{{mainData.z_price.toFixed(2)}}</span>
      </div>
      <div class="hr"></div>
    </div>
  </div>
</template>

<script>
import main from "@/webManage/js/order/orderSettlement/orderSettlementDetail";

export default main;
</script>


<style scoped lang="less">
  @import "../../../webManage/css/order/orderSettlement/orderSettlementDetail";
</style>
