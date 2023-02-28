<template>
  <div class="container">
    <div class="list">
        <div class="orderBox" v-for="(item, index) of orderList" :key="index">
            <el-card :bordered="false">
                <div slot="header" class="clearfix">
                    <span>销售订单</span>
                </div>
                <div class="grids">
                    <div>
                        <span>订单编号:</span>
                        <span>{{ item.list.data.sNo }}</span>
                    </div>
                    <div>
                        <span>支付方式:</span>
                        <span>{{ item.list.data.paytype }}</span>
                    </div>
                    <div>
                        <span>订单状态:</span>
                        <span>{{ item.list.detail[0].status }}</span>
                    </div>
                    <div>
                        <span>快递公司:</span>
                        <span> {{ getKuaidi(item.list.data) }} </span>
                    </div>

                    <div>
                        <span>订单来源:</span>
                        <span v-if="item.list.data.source == 1">微信小程序</span>
                        <span v-else-if="item.list.data.source==2">App</span>
                        <span v-else-if="item.list.data.source==3">支付宝小程序</span>
                        <span v-else-if="item.list.data.source==4">头条小程序</span>
                        <span v-else-if="item.list.data.source==5">百度小程序</span>
                        <span v-else-if="item.list.data.source==6">pc端</span>
                        <span v-else>H5移动端</span>
                    </div>
                    <div>
                        <span>订单类型:</span>
                        <span>{{ isOrderType(item.list.data) }}</span>
                        <!-- <span>{{ item.list.data.grText }}</span> -->
                    </div>

                    <div>
                        <span>配送费用:</span>
                        <span v-if="item.list.data.freight > 0"
                        >{{ item.list.data.freight }}</span
                        >
                        <span v-else>免邮</span>
                    </div>
                    <div>
                        <span>快递单号:</span>
                        <span>{{getOrderCode(item.list.data)}}</span>
                        <!-- <template v-if="getOrderCode(item.list.data) === '暂无'">
                          <span>{{ getOrderCode(item.list.data) }}</span>
                        </template>

                        <template>
                          <span v-for="(item2,i) in getOrderCode(item.data)" :key="i"
                            >{{ item2.num }} {{ item2.kuaidi_name }}</span
                          >
                        </template> -->
                    </div>

                    <div>
                        <span>收货人:</span>
                        <span>{{ item.list.data.name }}</span>
                    </div>

                    <div>
                        <span>联系电话:</span>
                        <span>{{ item.list.data.mobile }}</span>
                    </div>

                    <div>
                        <span>发货时间:</span>
                        <span v-if="item.list.data.deliver_time"
                        >{{ item.list.data.deliver_time }}</span
                        >
                        <span v-else>暂无</span>
                    </div>

                    <div>
                        <span>收货地址:</span>
                        <span>{{ item.list.data.address }}</span>
                    </div>

                    <div>
                        <span>下单时间:</span>
                        <span v-if="item.list.data.add_time">{{ item.list.data.add_time }}</span>
                        <span v-else>暂无</span>
                    </div>

                    <div>
                        <span>付款时间:</span>
                        <span v-if="item.list.data.pay_time">{{ item.list.data.pay_time }}</span>
                        <span v-else>暂无</span>
                    </div>

                    <div>
                        <span>收货时间:</span>
                        <span v-if="item.list.data.arrive_time"
                        >{{ item.list.data.arrive_time }}</span
                        >
                        <span v-else>暂无</span>
                    </div>
                    <div>
                        <span>订单说明:</span>
                        <span>{{ item.list.data.remarks }}</span>
                    </div>
                </div>

                <div class="dictionary-list" v-if="item.list.data.status === '待付款'">
                    <el-table :data="item.list.detail" border ref="table" class="el-table" style="width: 100%">
                        <el-table-column prop="p_id" label="商品ID" min-width="12.5%">
                        </el-table-column>
                        <el-table-column prop="p_name" label="商品名称" min-width="12.5%">
                        </el-table-column>
                        <el-table-column prop="name" label="店铺名称" min-width="12.5%">
                        </el-table-column>
                        <el-table-column prop="size" label="商品规格" min-width="12.5%">
                        </el-table-column>
                        <el-table-column prop="p_price" label="单价" min-width="12.5%">
                            <template slot-scope="scope">
                                <span>￥{{ scope.row.p_price }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="num" label="数量" min-width="12.5%">
                        </el-table-column>
                        <el-table-column prop="freight" label="运费" min-width="12.5%">
                        </el-table-column>
                        <el-table-column prop="p_price" label="小计" min-width="12.5%">
                        </el-table-column>
                    </el-table>
                </div>

                <div class="dictionary-list" v-if="item.list.data.status !== '待付款'">
                    <el-table :data="item.list.detail" border ref="table" class="el-table" style="width: 100%">
                        <el-table-column prop="p_id" label="商品ID" min-width="16%">
                        </el-table-column>
                        <el-table-column prop="p_name" label="商品名称" min-width="14%">
                        </el-table-column>
                        <el-table-column prop="size" label="商品规格" min-width="14%">
                        </el-table-column>
                        <el-table-column prop="p_price" label="单价" min-width="14%">
                            <template slot-scope="scope">
                                <span>￥{{ scope.row.p_price }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="num" label="数量" min-width="14%">
                        </el-table-column>
                        <el-table-column prop="freight" label="运费" min-width="14%">
                        </el-table-column>
                        <el-table-column prop="p_price" label="小计" min-width="14%">
                        </el-table-column>
                    </el-table>
                </div>
                <div class="note">
                    <div class="top">
                        <div>
                            <p>
                                <span>商品总价：</span>

                                <span>￥{{getGoodsPrice(item.list.detail)}}</span>
                            </p>
                            <p>
                                <span>运费：</span>
                                <span>￥{{ getAllFreight(item.list.detail) }}</span>
                            </p>
                            <p
                                    v-if="(item.list.data.otype == 'GM' || item.list.data.otype == '') && Number(item.list.coupon_price)>0"
                            >
                                <span>店铺优惠：</span>
                                <span>- ￥{{item.list.coupon_price}}</span>
                            </p>
                            <p
                                    v-if="(item.list.data.otype == 'GM' || item.list.data.otype == '') && item.list.discount_type"
                            >
                                <span>{{item.list.discount_type}}优惠：</span>
                                <span>- ￥{{item.list.preferential_amount}}</span>
                            </p>
                            <p v-if="item.list.data.otype != 'GM' && item.list.data.otype != ''">
                                <span>优惠金额：</span>
                                <span>- ￥{{item.list.coupon_price}}</span>
                            </p>
                            <p v-if="item.list.grade_rate!=0">
                                <span>会员折扣优惠：</span>
                                <span>- ￥{{item.list.grade_rate_amount.toFixed(2)}}</span>
                            </p>
                        </div>
                    </div>

                    <div class="bottom">
                        <p class="bold">
                            <span>合计支付：</span>
                            <span class="red">￥{{item.list.detail[0].z_price}}</span>
                        </p>
                    </div>
                </div>

                <div class="footer">
                    <div class="print-info">
                        <span>打印时间：{{ printTime }}</span>
                        <span>操作者：{{ item.list.operator }}</span>
                        <span></span>
                    </div>

                    <div class="copyright">
                      <span>
                        来客推（https://www.laiketui.com）
                      </span>

                      <span>
                        地址：长沙市岳麓区绿地中央广场5栋3408
                      </span>
                      <span>
                        电话：0731-86453408
                      </span>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
  </div>
</template>

<script>
Date.prototype.Format = function(fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        S: this.getMilliseconds(), //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(
            RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length)
        );
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(
                RegExp.$1,
                RegExp.$1.length == 1
                    ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length)
            );
    return fmt;
};
import { orderPrint } from '@/api/order/orderList'
export default {
    name: 'print',

    data() {
        return {
            orderList: [],
            loading: false,

            all_freight: 0,
            goodsPrice: 0,
            userpar: 0,
            grade_rate_amount: 0,
            coupon_price: 0,
            discount_type: 0,
            preferential_amount: 0,
            grade_rate: 0,
            printTime: new Date().Format("yyyy-MM-dd HH:mm:ss"),
        }
    },

    created() {
        this.orderPrints().then(() => {
            window.print()
            setTimeout(function() {
                window.close();
            },100)
        })
    },

    methods: {
        async orderPrints() {
            const res = await orderPrint({
                api: 'admin.order.orderPrint',
                sNo: this.$route.query.orderId
            })
            this.orderList = res.data.data
            console.log(this.orderList);
        },

        getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return false;
        },

        /**
         * @name 总价计算
         */
        imputedPrice(record) {
            let { num, p_price, freight } = record;
            // return round(parseFloat(p_price) * Number(num),2)
            return (
                parseFloat(p_price) * Number(num) +
                parseFloat(freight)
            ).toFixed(2);
        },

        /**
         * @name 判断订单类型
         */
        isOrderType(data) {
            if (data.otype == "pt") {
                data.grText = "拼团订单";
            } else if (data.otype == "JP") {
                data.grText = "竞拍订单";
            } else if (data.otype == "integral") {
                data.grText = "积分订单";
            } else {
                if (data.drawid > 0) {
                    data.grText = "抽奖订单";
                } else {
                    data.grText = "普通订单";
                }
            }
            return data.grText;
        },

        /**
         * @name 获取订单编号
         */
        getOrderCode(data) {
            if (data.fh == 0) {
                return "暂无";
            } else {
                return data.courier_num[0].num;
            }
        },

        getPrintTime() {},

        /**
         * 取得快递单号
         */
        getKuaidi(data) {
            if (data.fh == 0) {
                return "暂无";
            } else {
                return data.courier_num[0].kuaidi_name;
            }
        },

        getGoodsPrice(detail) {
            let goodsPrice = 0;
            detail.map((item) => {
                goodsPrice += parseFloat(item.p_price) * parseInt(item.num);
                // this.all_freight += parseFloat(item.freight)
            });
            return goodsPrice.toFixed(2);
        },

        getAllFreight(detail) {
            let all_freight = 0;
            detail.map((item) => {
                all_freight += parseFloat(item.freight);
            });
            return all_freight;
        },
    }
}
</script>

<style scoped lang="less">
.container {
  zoom: 0.64;
}
.container .list .orderBox {
  margin-bottom: 50px;
}
.container .list .el-card__body {
  padding: 0;
}
.container .list .el-card__header {
  padding: 0 0px;
}

.container .list .el-card__header .clearfix {
    display: flex;
    justify-content: center;
    align-items: center;
}

.container .list .el-card__header .clearfix span {
  font-weight: 900;
  text-align: center;
}
.container .list .grids {
  padding: 30px 0;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 10px;
}
.container .list .grids div span:nth-child(even) {
  color: #323544;
  font-size: 14px;
}
.container .list .grids div span:nth-child(odd) {
  color: #828b97;
  font-size: 14px;
}
.container .list .merchandise {
  padding: 0 0px;
  border: 1px solid #E9ECEF;
  border-top: none;
  position: relative;
  border-bottom: none;
}
.container .list .merchandise::before {
  content: "";
  height: 0px;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 999;
  border-top: 1px solid #E9ECEF;
}

.container .list .merchandise .nameimg {
  width: 60px;
  height: 60px;
}
.container .list .merchandise .sumups {
  position: absolute;
  top: 54px;
  background: #ffffff;
  width: 7.2%;
  display: flex;
  align-items: center;
}
.container .list .merchandise .small {
  font-size: 12px !important;
  color: #999 !important;
}
.container .list .merchandise .z_price {
  color: #323544;
  font-size: 14px;
  font-weight: bold;
}
.container .list .note {
  margin-top: 20px;
  padding: 0 0px;
}
.container .list .note p {
  display: inline-flex;
  justify-content: space-between;
  font-size: 16px;
  color: #323544;
  font-weight: bold;
  margin-left: 30px;
}
.container .list .note p.bold {
  font-weight: bold;
}
.container .list .note p.bold .red {
  color: black;
}
.container .list .note .bottom,
.container .list .note .top {
  display: inline-flex;
  flex-direction: row-reverse;
  width: 100%;
}
.container .list .footer {
  padding: 0 0px;
  display: flex;
  flex-direction: column;
  width: 100%;
  color: #333333;
  font-weight: 400;
  font-size: 14px;
}
.container .list .footer .copyright {
  display: flex;
  justify-content: space-between;
}
.container .list .footer .copyright span:first-child {
  width: 260px;
}
.container .list .footer .copyright span {
  width: 300px;
}
.container .list .footer .copyright span:last-child {
  width: 200px;
  text-align: right;
}
.container .list .footer .print-info {
  display: flex;
  margin-bottom: 10px;
  justify-content: space-between;
}
.container .list .footer .print-info span:first-child {
  width: 260px;
}
.container .list .footer .print-info span {
  width: 300px;
}
.container .list .footer .print-info span:last-child {
  width: 200px;
  text-align: right;
}

.dictionary-list {
    width: 100%;
    border-radius: 4px;
    /deep/.el-table {
        width: 100% !important;
        .el-table__header-wrapper {
        width: 100%;
        .el-table__header {
            width: 100% !important;
        }
        thead {
            tr {
                th{
                    text-align: center;
                    font-size: 14px;
                    font-weight: bold;
                    color: #414658;
                }
            }
        }
        }
        .el-table__body-wrapper {
            width: 100%;
            .el-table__body {
                width: 100% !important;
            }
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

        th {
            height: 61px;
        }
        .cell {
            .name-info {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                img {
                    width: 60px;
                    height: 60px;
                    margin-right: 10px;
                }
            }
        }
    }
    
    
}

</style>