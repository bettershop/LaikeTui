import echarts from 'echarts'
import pageData from '@/api/constant/page'
import {index} from "@/api/data";
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'vip-proportion',
  data() {
    return {
      tbl: "会员比例",
      page: pageData.data(),
    }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      const res = await index({
        api: 'admin.data.getUserDistributionInfo',
        startDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[0],
        endDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[1],
      })
      let dates = []
      dates.push(res.data.data.startDate);
      dates.push(res.data.data.endDate);
      this.page.inputInfo.date = dates;
      //处理数据
      let report = res.data.data;

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
        tooltip: {
          trigger: 'item',
        },
        legend: {
          orient: 'vertical',
          top:'20%',
          left: '20%',
        },
        color: [
          '#F8DD29',
          '#F49610',
          '#F30000'
        ],
        series: [
          {
            name: '访问来源',
            type: 'pie',
            radius: ['50%', '70%'],
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              {value: data.num_wx, name: '微信小程序'},
              {value: data.num_app, name: '手机App'},
              {value: data.num_pc, name: 'PC'}
            ]
          }
        ]
      }
      this.myChartOne = echarts.init(document.getElementById('myChart'))
      this.myChartOne.setOption(option)
    },
  }
}
