import { mchTurnoverNewUserReport } from '@/api/Platform/reportManagement'
import { mixinstest } from '@/mixins/index'
import echarts from 'echarts'
export default {
    name: 'addUsersReport',
    mixins: [mixinstest],
    data() {
      return {
        radio1:'商户新增用户报表',

        value1: '',
        SHinput: null,
        tabIndex: 1,
        tableData: [],
        period: 'day',
        sort: 0,
        xAxisdata: [],
        seriesdata: []
      }
    },

    created() {
        this.mchTurnoverNewUserReports()
        this._axiosTable()
      },
    
      methods: {
        async mchTurnoverNewUserReports() {
            const res = await mchTurnoverNewUserReport({
                api: 'admin.turnover.mchTurnoverNewUserReport',
                type: this.period,
                startDate: this.value1[0],
                endDate: this.value1[1]
            })
            this.loadLine(res.data.data.list)
        },
    
        loadLine(data) {
            this.xAxisdata = []
            this.seriesdata = []
            if(data) {
                data.filter(item => {
                    this.xAxisdata.push(item.name)
                    this.seriesdata.push(Number(item.total))
                })
            }
            let option = {
                color: ['#2890FF'],
                xAxis: {
                    type: "category",
                    data: this.xAxisdata,
                },
                tooltip: {
                    trigger: "axis",
                    axisPointer: {
                        type: "shadow"
                    }
                },
                yAxis: {
                    // splitNumber: 3
                },
                series: [{
                    name: '营业额',
                    data: this.seriesdata,
                    barWidth: 74,
                    type: 'bar'
                }]
            };
            this.myChart = echarts.init(document.getElementById('chart'));
            this.myChart.setOption(option);
        },
    
        sortChange(e) {
            console.log(e)
            if (e.order == 'ascending') {
                this.sort = 0
            } else if (e.order == 'descending') {
                this.sort = 1
            } else {
                this.sort = 0
            }
            this._axiosTable()
        },
    
        Tab(index) {
            this.tabIndex = index
            if (index == 0) {
                this.period = 'yesterday'
            } else if (index == 1) {
                this.period = 'day'
            } else if (index == 2) {
                this.period = 'week'
            } else if (index == 3) {
                this.period = 'month'
            }
            this.value1 = ''
            this.mchTurnoverNewUserReports()
        },
    
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.dictionaryNum = 1
            this._axiosTable()
        },
    
        async _axiosTable() {
            const res = await mchTurnoverNewUserReport({
                api: 'admin.turnover.mchTurnoverNewUserReport',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                sortType: this.sort,
                mchName: this.SHinput
            })
            this.current_num = 10
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
        },
    
        //选择一页多少条
        handleSizeChange(e){
            console.log(e);
            this.current_num = e
            this.pageSize = e
            this._axiosTable()
        },
    
        //点击上一页，下一页
        handleCurrentChange(e){
            this.dictionaryNum = e
            this.currpage = ((e - 1) * this.pageSize) + 1
            this._axiosTable().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
            })
        },
    
        change(e) {
            this.period = ''
            this.mchTurnoverNewUserReports()
        }
    }
}