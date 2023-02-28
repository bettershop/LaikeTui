import echarts from 'echarts'
import pageData from '@/api/constant/page'
import {index} from "@/api/data";
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'orde-report',
  data() {
    return {
      page: pageData.data(),
      orderCount: {z_price: 0, yx_amt: 0, num: 0, yx_num: 0}
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      const res = await index({
        api: 'admin.data.getOrderReport',
        startDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[0],
        endDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[1],
      })
      let dates = []
      dates.push(res.data.data.startDate);
      dates.push(res.data.data.endDate);
      this.page.inputInfo.date = dates;

      this.orderCount = res.data.data.data[0];
      let orderList = res.data.data.list;
      //处理数据
      let report = {};
      let dateList = [];
      let orderNumList = [];
      let amtList = [];
      for (let i = 0; i < orderList.length; i++) {
        dateList.push(orderList[i].r_date);
        orderNumList.push(orderList[i].num);
        amtList.push(orderList[i].z_price);
      }
      report.dateList = dateList;
      report.orderNumList = orderNumList;
      report.amtList = amtList;
      this.loadLine(report);
    },
    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.page.showPagebox = false
      this.page.loading = true
      this.page.dictionaryNum = 1
      this.loadData().then(() => {
        this.page.loading = false
        if (this.page.tableData.length > 5) {
          this.page.showPagebox = true
        }
      })
    },
    reset() {
      this.page.inputInfo.date = null;
    },
    loadLine(data) {
      let option = {
        title: {
          top: 50,
          text: '订单数量折线图'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {

          containLabel: true,
          show: true,
        },
        toolbox: {
          left: 800,
          top: 5,
          feature: {
            dataZoom: {
              show: true,
            },
            restore: {},
            saveAsImage: {},
            magicType: {
              type: ['line', 'bar'],
            }

          }
        },
        dataZoom: [{
          startValue: data.dateList[0],
        },
          {
            type: 'inside'
          }],
        xAxis: {
          type: 'category',
          data: data.dateList
        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: {
          name: '订单数',
          type: 'line',
          data: data.orderNumList,
          markPoint: {
            data: [
              {type: 'max', name: '最大值'},
              {type: 'min', name: '最小值'}
            ],
          },
          markLine: {
            data: [
              {type: 'average', name: '平均值'}
            ]
          }
        }
      }
      this.myChartOne = echarts.init(document.getElementById('orderMap'))
      this.myChartOne.setOption(option)

      let option1 = {
        title: {
          top: 50,
          text: '销售额折线图'
        },
        tooltip: {
          trigger: 'axis'
        },
        grid: {
          containLabel: true,
          show: true,
        },
        toolbox: {
          left: 800,
          top: 5,
          feature: {
            dataZoom: {
              show: true,
            },
            restore: {},
            saveAsImage: {},
            magicType: {
              type: ['line', 'bar'],
            }

          }
        },
        dataZoom: [{
          startValue: data.dateList[0],
        },
          {
            type: 'inside'
          }],
        xAxis: {

          axisLine: {
            lineStyle: {
              color: 'red'
            }
          },

          type: 'category',

          data: data.dateList

        },
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        series: {
          name: '销售额',
          type: 'line',

          color: ['#4876FF'],
          data: data.amtList,
          markPoint: {
            data: [
              {type: 'max', name: '最大值'},
              {type: 'min', name: '最小值'}
            ]
          },
          markLine: {
            data: [
              {type: 'average', name: '平均值'}
            ]
          }
        }

      }
      this.myChartOne1 = echarts.init(document.getElementById('priceMap'))
      this.myChartOne1.setOption(option1)

    },
  }
}
