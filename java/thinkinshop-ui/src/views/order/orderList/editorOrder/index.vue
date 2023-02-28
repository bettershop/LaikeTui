<template>
  <div class="container">
    <div class="basic-info" v-if="dataInfo">
        <div class="header">
            <span>基础信息</span>
        </div>
        <div class="basic-block">
            <ul class="items">
                <li>
                    <span>订单编号：</span>{{ dataInfo.sNo }}
                </li>
                <li class="order-status">
                    <span v-show="dataInfo.status == '待付款'">订单状态：</span>
                    <el-select class="select-input" v-if="dataInfo.status == '待付款'" v-model="ruleForm.status" placeholder="请选择订单状态">
                        <el-option v-for="item in stateList" :key="item.brand_id" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                    <span v-else>订单状态：{{ dataInfo.status }}</span>
                </li>
                <li>
                    <span>订单类型：</span>{{ dataInfo.orderTypeName }}订单
                </li>
                <li>
                    <span>订单来源：</span>{{ getSource(dataInfo.source) }}
                </li>
            </ul>
            <ul class="items">
                <li>
                    <span>支付方式：</span>{{ dataInfo.paytype }}
                </li>
                <li>
                    <span>下单时间：</span>{{ dataInfo.add_time }}
                </li>
                <li>
                    <span>付款时间：</span>{{ dataInfo.pay_time }}
                </li>
                <li>
                    <span>发货时间：</span>{{ dataInfo.deliver_time }}
                </li>
            </ul>
            <ul class="items">
                <li>
                    <span>收货时间：</span>{{ dataInfo.arrive_time }}
                </li>
                <li>
                    <span>会员ID：</span>{{ dataInfo.user_id }}
                </li>
                <li>
                    <span>会员名称：</span>{{ dataInfo.user_name }}
                </li>
                <li>
                    <span>订单备注：</span>{{ Object.keys(dataInfo.remarks).length === 0 ? '' : '' }}
                </li>
            </ul>
        </div>
    </div>
    <div class="consignee-info" v-if="dataInfo">
        <div class="header">
            <span>收货人信息</span>
        </div>
        <div class="consignee-block">
            <el-form ref="ruleForm" :rules="rules" class="form-search" :model="ruleForm" label-width="100px">
              <el-form-item label="收货人" prop="name" required>
                <el-input v-model="ruleForm.name" placeholder="请输入收货人"></el-input>
              </el-form-item>
              <el-form-item label="联系电话" prop="mobile" required>
                <el-input v-model="ruleForm.mobile" placeholder="请输入联系电话"></el-input>
              </el-form-item>
              <el-form-item class="cascadeAddress" label="联系地址" prop="xian">
                <div class="cascadeAddress-block">
                  <el-select class="select-input" v-model="ruleForm.sheng" placeholder="省">
                    <el-option v-for="(item,index) in shengList" :key="index" :label="item.g_CName" :value="item.g_CName">
                      <div @click="getShi(item.groupID)">{{ item.g_CName }}</div>
                    </el-option>
                  </el-select>
                  <el-select class="select-input" v-model="ruleForm.shi" placeholder="市">
                    <el-option v-for="(item,index) in shiList" :key="index" :label="item.g_CName" :value="item.g_CName">
                      <div @click="getXian(item.groupID)">{{ item.g_CName }}</div>
                    </el-option>
                  </el-select>
                  <el-select class="select-input" v-model="ruleForm.xian" placeholder="县">
                    <el-option v-for="(item,index) in xianList" :key="index" :label="item.g_CName" :value="item.g_CName">
                      <div>{{ item.g_CName }}</div>
                    </el-option>
                  </el-select>
                </div>
              </el-form-item>
              <el-form-item label="" prop="r_address" required>
                <el-input v-model="ruleForm.r_address" placeholder=""></el-input>
              </el-form-item>
              <el-form-item label="订单备注" prop="remarks">
                <el-input v-model="ruleForm.remarks" placeholder="请输入订单备注"></el-input>
              </el-form-item>
            </el-form>
        </div>
    </div>
    <div class="goods-info" v-if="dataInfo">
        <div class="header">
            <span>商品信息</span>
        </div>
        <div class="goods-block">
            <div class="dictionary-list">
                <el-table :data="goodsTables" ref="table" class="el-table" style="width: 100%">
                    <el-table-column prop="p_name" label="商品名称" width="400">
                        <template slot-scope="scope">
                            <div class="name-info">
                                <img :src="scope.row.pic" alt="">
                                <span>{{ scope.row.p_name }}</span>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="size" label="商品规格" width="158">
                    </el-table-column>
                    <el-table-column prop="p_id" label="商品编号" width="152">
                    </el-table-column>
                    <el-table-column prop="p_price" label="单价" width="152">
                        <template slot-scope="scope">
                            <span>{{ scope.row.p_price.toFixed(2) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="num" label="数量" width="152">
                    </el-table-column>
                    <el-table-column prop="total_num" label="库存" width="152">
                    </el-table-column>
                    <el-table-column prop="grade_rate" label="会员折扣" width="152">
                        <template slot-scope="scope">
                            <span>{{ scope.row.grade_rate == 0 ? '无' : scope.row.grade_rate }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="p_price" label="小计" width="152">
                        <template slot-scope="scope">
                            <span>{{ scope.row.p_price }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" fixed="right" width="140">
                        <template slot-scope="scope">
                            <div class="OP-button">
                                <div class="OP-button-top">
                                    <el-button icon="el-icon-view" @click="Delete(scope.$index)">查看</el-button>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <div class="statistical">
                <div class="goods-totalprice">
                    <span class="title name">商品总价：</span>
                    <span class="price">￥{{ totleInfo.spz_price.toFixed(2) }}</span>
                </div>
                <div class="discount-stores">
                    <span class="title name">店铺优惠：</span>
                    <span class="price">-￥{{ totleInfo.coupon_price }}</span>
                </div>
                <div class="discount-platform">
                    <span class="title name">平台优惠：</span>
                    <span class="price">-￥{{ totleInfo.preferential_amount.toFixed(2) }}</span>
                </div>
                <div class="totle-pay">
                    <span class="title-totle  name">合计支付：</span>
                    <el-input v-if="dataInfo.status == '待付款'" v-model="ruleForm.pay_price" size="medium"></el-input>
                    <span v-else class="red price">￥<i>{{ totleInfo.pay_price.toFixed(2) }}</i></span>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-button">
      <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
      <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
    </div>
  </div>
</template>

<script>
import orderDetails from '@/webManage/js/order/orderList/editorOrder'
export default orderDetails
</script>

<style scoped lang="less">
@import '../../../../webManage/css/order/orderList/editorOrder.less';
</style>