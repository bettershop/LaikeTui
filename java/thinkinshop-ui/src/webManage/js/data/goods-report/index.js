import echarts from 'echarts'
import pageData from '@/api/constant/page'
import { index, getGoods } from "@/api/data";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'orde-report',
  mixins: [mixinstest],
  data() {
    return {
      page: pageData.data(),
      mainData: {product_num: 0, customer_num: 0}
    }
  },
  mounted() {
    this.loadData()
    this.getGoods()
  },
  methods: {
    async loadData() {
      const res = await index({
        api: 'admin.data.getGoodsReport',
      })
      let main = res.data.data;
      this.mainData.product_num = main.product_num;
      this.mainData.customer_num = main.customer_num;

      //处理数据
      let report = {};
      report.top10 = [];
      report.top10Name = [];
      report.top10Volume = [];
      for (let i = 0; i < main.top10.length; i++) {
        report.top10Name.push(main.top10[i].product_title);
        report.top10Volume.push(main.top10[i].volume);
        report.top10.push({value: main.top10[i].volume, name: main.top10[i].product_title})
      }
      //商品分类数据处理
      report.classNames = [];
      report.classInNums = [];
      report.classOutNums = [];
      report.classSyNums = [];
      report.classStocks = [];
      for (let i = 0; i < main.stock.length; i++) {
        report.classNames.push(main.stock[i].className);
        report.classInNums.push(main.stock[i].in_num);
        report.classOutNums.push(main.stock[i].out_num);
        report.classSyNums.push(main.stock[i].sy_num);
      }
      report.classStocks.push({
          name: '入库数量',
          type: 'bar',
          data: report.classInNums,
        },
        {
          name: '出库数量',
          type: 'bar',
          data: report.classOutNums,
        },
        {
          name: '剩余库存',
          type: 'bar',
          data: report.classSyNums,
        })

      this.loadLine(report);
    },//选择一页多少条

    async getGoods() {
      const res = await getGoods({
        api: 'admin.data.getGoodsReportGoodsList',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
      })
      this.total = res.data.data.total
      this.page.tableData = res.data.data.goodsStock
      this.page.loading = false
      if (this.total < this.current_num) {
        this.current_num = this.total
      }
      console.log(9999999);
      console.log(res);
    },

    handleSizeChange(e) {
      this.page.loading = true
      this.current_num = e
      this.pageSize = e
      this.getGoods().then(() => {
        this.loading = false
      })
      this.loading = true
    },
    //点击上一页，下一页
    handleCurrentChange(e) {
      this.page.loading = true
      this.dictionaryNum = e
      this.currpage = ((e - 1) * this.pageSize) + 1
      this.getGoods().then(() => {
        this.current_num = this.page.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.page.loading = false
      })
    },
    loadLine(data) {
      let optionGoods = {
        title: {
          text: '商品前十销量排行',
          x: 'left'
        },
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient: 'vertical',
          left: 'right',
          data: data.top10Name
        },
        series: [
          {
            name: '销量排行',
            type: 'pie',
            radius: '60%',
            center: ['50%', '60%'],
            data: data.top10,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]

      }
      this.myChartOne = echarts.init(document.getElementById('goodsMap'))
      this.myChartOne.setOption(optionGoods)

      let optionStock = {
        title: {
          text: '商品库存对比',
          x: 'left'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {            // 坐标轴指示器，坐标轴触发有效
            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          data: ['入库数量', '出库数量', '剩余库存']
        },


        xAxis: [
          {
            type: 'category',
            data: data.classNames
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: data.classStocks
      }

      this.myChartOne1 = echarts.init(document.getElementById('stockMap'))
      this.myChartOne1.setOption(optionStock)
    },
  }
}
