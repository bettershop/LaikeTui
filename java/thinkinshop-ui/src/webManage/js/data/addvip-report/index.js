import echarts from 'echarts'
import pageData from '@/api/constant/page'
import { index, getAddUserList} from "@/api/data";
import {isEmpty} from "element-ui/src/utils/util";
import source from "@/store/modules/source";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'addvit-report',
  mixins: [mixinstest],
  data() {
    return {
      tbl: "新增会员报表",
      page: pageData.data(),
      source: this.getSource(),
    }
  },
  mounted() {
    this.loadData().then(() => {
      this.getAddUserList()
    })
    
  },
  methods: {
    async loadData() {
      const res = await index({
        api: 'admin.data.getAddUserInfo',
        startDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[0],
        endDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[1],
      })
      let dates = []
      dates.push(res.data.data.startDate);
      dates.push(res.data.data.endDate);
      this.page.inputInfo.date = dates;
      //处理数据
      let report = {};
      //pc
      let pc = res.data.data.sum_arr_pc;
      let pcNums = [];
      let pcDate = [];
      for (let i = 0; i < pc.length; i++) {
        pcNums.push(pc[i].sum);
        pcDate.push(pc[i].rdate);
      }
      report.pcNums = pcNums;
      report.pcDate = pcDate;
      //微信
      let wx = res.data.data.sum_arr_wx;
      let wxNums = [];
      let wxDate = [];
      for (let i = 0; i < wx.length; i++) {
        wxNums.push(wx[i].sum);
        wxDate.push(wx[i].rdate);
      }
      report.wxNums = wxNums;
      report.wxDate = wxDate;
      //app
      let app = res.data.data.sum_arr_app;
      let appNums = [];
      let appDate = [];
      for (let i = 0; i < app.length; i++) {
        appNums.push(app[i].sum);
        appDate.push(app[i].rdate);
      }
      report.appNums = appNums;
      report.appDate = appDate;

      this.loadLine(report);
    },

    async getAddUserList() {
      const res = await getAddUserList({
        api: 'admin.data.getAddUserList',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        startDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[0],
        endDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[1],
      })
      this.total = res.data.data.total
      this.page.tableData = res.data.data.list
      this.page.loading = false
      if(res.data.data.total < 10) {
        this.current_num = this.total
      }
    },

    reset() {
      this.page.inputInfo.date = null;
    },

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      // this.loadData()
      this.page.showPagebox = false
      this.page.loading = true
      this.dictionaryNum = 1
      this.getAddUserList().then(() => {
        this.page.loading = false
        if (this.page.tableData.length > 5) {
          this.page.showPagebox = true
        }
      })
    },

    //选择一页多少条
    handleSizeChange(e) {
      console.log(e);
      this.page.loading = true
      // this.current_num = e
      this.pageSize = e
      this.getAddUserList().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.page.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.loading = false
      })
    },

    //点击上一页，下一页
    handleCurrentChange(e) {
      this.page.loading = true
      this.dictionaryNum = e
      this.currpage = ((e - 1) * this.pageSize) + 1
      this.getAddUserList().then(() => {
        this.current_num = this.page.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.page.loading = false
      })
    },
    loadLine(data) {
      let option = {
        title: {
          top: 50,
          text: '新增用户折线图'
        },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          top: 10,
          data: ['小程序', '手机App', 'PC']
        },
        grid: {
          containLabel: true,
          show: true,
        },
        toolbox: {
          left: 900,
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
          // startValue: day_arr[0],
        }, {
          type: 'inside'
        }],
        //x轴值
        xAxis: [{
          axisLabel: {
            interval: 0,
            rotate: 40
          },
          axisLine: {
            lineStyle: {
              color: 'red'
            }
          },

          type: 'category',
          boundaryGap: false,
          data: data.appDate

        }],
        //y轴值
        yAxis: {
          type: 'value',
          minInterval: 1,
        },
        //类别
        series: [
          {
            name: '小程序',
            type: 'line',
            color: ['#B3EE3A'],
            data: data.wxNums,
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ],

            },
            markLine: {
              data: [
                {type: 'average', name: '平均值-小程序'}
              ]
            }
          },
          {
            name: '手机App',
            type: 'line',

            color: ['#4876FF'],
            smooth: 0.3,
            data: data.appNums,
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ],

            },
            markLine: {
              data: [
                {type: 'average', name: '平均值-App'}
              ]
            }
          }
          ,
          {
            name: 'PC',
            type: 'line',

            color: ['#AEAEAE'],
            smooth: 0.3,
            data: data.pcNums,
            markPoint: {
              data: [
                {type: 'max', name: '最大值'},
                {type: 'min', name: '最小值'}
              ],

            },
            markLine: {
              data: [
                {type: 'average', name: '平均值-App'}
              ]
            }
          }

        ]
      }
      this.myChartOne = echarts.init(document.getElementById('myChart'))
      this.myChartOne.setOption(option)
    },
  }
}
