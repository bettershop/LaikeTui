import echarts from 'echarts'
import {index} from "@/api/data";
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'addvit-report',
  data() {
    return {
      dataMain: {
        notPay: 0,
        noSendOrderNum: 0,
        notDeliverNum: 0,
        orderNotCommentsNum: 0,
        orderReturnWaitNum: 0,
        /**
         *实时营业额
         */
        //总消费额
        storeAmtTotal: 0,
        //销售同比
        dayAmtProportion: 0,
        dayAmtProportionAmt: 0,
        //今日消费基恩
        toDaySaleTotal: 0,
        //昨日消费额
        yesterdaySale: 0,
        //本月消费金额
        monthAmtTotal: 0,
        /**
         * 实时订单数
         */
        orderNumTotal: 0,
        //本月订单数
        monthOrderNum: 0,
        //昨日订单
        yesterdayOrderNum: 0,
        //订单数同比
        yoy_order: 0,
        yoy_order_num: 0,
        /**
         * 实时访客数 今天 昨天 总访问 占比/率
         */
        todayBrowseTotal: 0,
        yesterdayBrowseTotal: 0,
        monthBrowseTotal: 0,
        coustomer_num_z: 0,
        //同比
        yesterdayBrowseNum: 0,
        yesterdayBrowse: 0,
        /**
         * 公告
         */
        sysNotice: [],
        /**
         * 会员统计
         */
        userTotal: 0,
      },
      orderType: 4,

      // 累计订单
      z_num: 0,
      // 累计营业额
      z_total: 0
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      const res = await index({
        api: 'admin.home.index',
      }).then(data => {
        let main = data.data.data;
        this.dataMain.today_order_num = main.list_sno[0]
        this.dataMain.todayBrowseTotal  = main.todayBrowseTotal
        this.dataMain.daySale = main.daySale
        this.dataMain.notPay = main.list_sno[1]
        this.dataMain.noSendOrderNum = main.list_sno[2]
        this.dataMain.notDeliverNum = main.list_sno[5]
        this.dataMain.orderNotCommentsNum = main.list_sno[4]
        this.dataMain.orderReturnWaitNum = main.list_sno[3]
        //实时营业额
        this.dataMain.storeAmtTotal = main.toDaySaleTotal 
        //销售同比
        this.dataMain.dayAmtProportion = main.dayAmtProportion
        this.dataMain.dayAmtProportionAmt = main.dayAmtProportionAmt
        //今日消费基恩
        this.dataMain.toDaySaleTotal = main.toDaySaleTotal
        //昨日消费额
        this.dataMain.yesterdaySale = main.yesterdaySale
        //本月消费额
        this.dataMain.monthAmtTotal = main.monthAmtTotal
        /**
         * 实时订单数
         */
        this.dataMain.orderNumTotal = main.list_sno[0];
        this.dataMain.monthOrderNum = main.monthOrderNum;
        this.dataMain.yesterdayOrderNum = main.yesterdayOrderNum;
        this.dataMain.yoy_order = main.yoy_order;
        this.dataMain.yoy_order_num = main.yoy_order_num;
        /**
         * 实时访客数
         */
        this.dataMain.coustomer_num_z = main.coustomer_num_z;
        this.dataMain.yesterdayBrowseTotal = main.yesterdayBrowseTotal;
        this.dataMain.monthBrowseTotal = main.monthBrowseTotal;
        this.dataMain.yesterdayBrowseNum = main.yesterdayBrowseNum;
        this.dataMain.yesterdayBrowse = main.yesterdayBrowse;
        this.dataMain.sysNotice = main.sysNotice

        this.dataMain.userTotal = main.userTotal;

        //统计图数据处理
        let report = {};
        //报表数据
        let newWeekUserList = main.newWeekUserList;
        let userNums = [];
        let userDate = [];
        for (let i = 0; i < newWeekUserList.length; i++) {
          userNums.push(newWeekUserList[i].num);
          userDate.push(newWeekUserList[i].date);
        }
        report.newWeekUser = {userNums: userNums, userDate: userDate};

        // 累计订单
        this.z_num = data.data.data.z_num
        // 累计营业额
        this.z_total = data.data.data.z_total

        console.log(data.data.data);

        this.loadLine(report);
        this.loadOrder();
      })
    },

    toOrderDetail(value) {
      if(value == '退货') {
        this.$router.push({
          path: '/order/salesReturn',
        })
      } else {
        this.$router.push({
          path: '/order/orderList/orderLists',
          query: {
            value: value
          }
        })
      }
    },

    toNoticeDetail(id){
        this.$router.push({
          path: '/mall/systemNotice/noticeDetail',
          query: {
            id:id
          }
          
        })
    },
    loadLine(data) {
      let option = {
        xAxis: {
          data: data.newWeekUser.userDate
        },
        yAxis: {
          splitLine: {
            show: false
          },
          min: 0,
          minInterval: 10,
        },
        grid: {
          top: 10,
          bottom: 20,
          left: 40,
          right: 20,
        },
        tooltip: {
          formatter: " {a} <br/> {c} "
        },
        series: {
          name: "会员人数",
          data: data.newWeekUser.userNums,
          type: "bar",

        },
        color: "#0880ff",
      }
      this.myChartOne = echarts.init(document.getElementById('memberTJT'))
      this.myChartOne.setOption(option)
    },
    loadOrderReport(type) {
      this.orderType = type;
      this.loadOrder().then(r => {});
    },
    async loadOrder() {
      const res = await index({
        api: 'admin.data.getOrderReport',
        type: this.orderType,
      }).then(data => {
        //报表数据
        let orderList = data.data.data.list;
        let orderNums = [];
        let orderDate = [];
        for (let i = 0; i < orderList.length; i++) {
          orderNums.push(orderList[i].sum)
          orderDate.push(orderList[i].rdate)
        }
        let orderOption = {
          xAxis: {
            data: orderDate
          },
          yAxis: {
            splitLine: {
              show: false
            },
            min: 0,
            minInterval: 1,
          },
          grid: {
            top: 10,
            bottom: 20,
            left: 40,
            right: 20,
          },
          tooltip: {
            formatter: " {a} <br/> {c} "
          },
          color: "#0880ff",
          series: {
            name: "订单数量",
            data: orderNums,
            type: "line",
          }
        }

        this.myChartOne = echarts.init(document.getElementById('ddTJT'))
        this.myChartOne.setOption(orderOption)
      })
    },
  }
}
