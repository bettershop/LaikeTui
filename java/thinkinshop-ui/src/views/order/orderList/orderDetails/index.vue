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
                <li>
                    <span>订单状态：</span>{{ dataInfo.status }}
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
                <li class="notes">
                    <span>订单备注：</span>
                    <!-- <ul class="remaks" v-if="noteList">
                        <li v-for="(item,index) in noteList" :key="index">
                            {{ item }}
                        </li>
                    </ul> -->
                    <div class="remaks">
                        <span v-for="(item,index) in goodsTables" :key="index">
                            {{ item.remarks }}
                        </span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="consignee-info" v-if="dataInfo">
        <div class="header">
            <span>收货人信息</span>
        </div>
        <div class="consignee-block">
            <ul class="items">
                <li>
                    <span>收货人：</span>{{ dataInfo.name }}
                </li>
                <li>
                    <span>联系电话：</span>{{ dataInfo.mobile }}
                </li>
                <li>
                    <span>收货地址：</span>{{ dataInfo.address }}
                </li>
                <!-- <li>
                    <span>订单备注：</span>{{ Object.keys(dataInfo.remarks).length === 0 ? '' : '' }}
                </li> -->
            </ul>
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
                            <span v-if="scope.row.p_price">{{ scope.row.p_price.toFixed(2) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="num" label="数量" width="152">
                    </el-table-column>
                    <el-table-column prop="total_num" label="库存" width="152">
                    </el-table-column>
                    <el-table-column prop="grade_rate" label="会员折扣" width="152">
                    </el-table-column>
                    <el-table-column prop="p_price" label="小计" width="152">
                        <template slot-scope="scope">
                            <span v-if="scope.row.p_price">{{ scope.row.p_price.toFixed(2) }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" align="center" fixed="right" width="140">
                        <template>
                            <div class="OP-button">
                                <div class="OP-button-top">
                                    <el-button icon="el-icon-view" @click="$router.push('/goods/inventoryManagement/inventoryList')">查看</el-button>
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
                <div class="goods-totalprice" v-if="totleInfo.z_freight">
                    <span class="title name">运费：</span>
                    <span class="price">￥{{ totleInfo.z_freight.toFixed(2) }}</span>
                </div>
                <div class="discount-stores">
                    <span class="title name">店铺优惠：</span>
                    <span class="price">￥{{ totleInfo.coupon_price.toFixed(2) }}</span>
                </div>
                <div class="discount-platform">
                    <span class="title name">平台优惠：</span>
                    <span class="price">￥{{ totleInfo.preferential_amount.toFixed(2) }}</span>
                </div>
                <div class="totle-pay">
                    <span class="title-totle  name">合计支付：</span>
                    <span class="red price">￥{{ totleInfo.pay_price.toFixed(2) }}</span>
                </div>
            </div>
        </div>
    </div>
  </div>
</template>

<script>
import orderDetails from '@/webManage/js/order/orderList/orderDetails'
export default orderDetails
</script>

<style scoped lang="less">
@import '../../../../webManage/css/order/orderList/orderDetails.less';
</style>