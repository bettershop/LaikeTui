import { getRefundList } from '@/api/plug_ins/seckill'
import { mixinstest } from '@/mixins/index'
import { exports } from '@/api/export/index'

export default {
    name: 'afterSaleList',
    mixins: [mixinstest],

    data() {
        return {
            stateList: [
                {
                    value: '7',
                    label: '待审核'
                },
                {
                    value: '1',
                    label: '退款中'
                },
                {
                    value: '2',
                    label: '退款成功'
                },
                {
                    value: '3',
                    label: '退款失败'
                },
                {
                    value: '4',
                    label: '换货中'
                },
                {
                    value: '5',
                    label: '换货成功'
                },
                {
                    value: '6',
                    label: '换货失败'
                },
            ],// 订单状态
            inputInfo: {
                orderNo: null,
                state: null,
                date: null
            },

            tableData: [],
            loading: true,

            id: null,
            toggle: null,

            type: '',

            content: '',

            courierList: [],
            
            // table高度
            tableHeight: null,

            // 导出弹框数据
            dialogVisible: false,
        }
    },

    created() {
        this.getRefundLists()
        this.deliveryViews()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    methods: {
        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
            console.log(this.$refs.tableFather.clientHeight);
		},
        async getRefundLists() {
            const res = await getRefundList({
                api: 'admin.sec.order.getRefundList',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                orderno: this.inputInfo.orderNo ? this.inputInfo.orderNo : null,
                status: this.inputInfo.state ? this.inputInfo.state : null,
                startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                endDate: this.inputInfo.date ? this.inputInfo.date[1] : null,
            })
            console.log(res);
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            if(this.total == 0) {
                this.showPagebox = false
            } else {
                this.showPagebox = true
            }
        },

        async deliveryViews() {
            const res = await deliveryView({
                api: 'admin.order.deliveryView',
            })
            this.courierList = res.data.data.express
        },

        // 重置
        reset() {
            this.inputInfo.orderNo = null,
            this.inputInfo.state = null,
            this.inputInfo.date = null
        },
  
        // 查询
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.getRefundLists().then(() => {
                this.loading = false
                if(this.tableData.length > 5) {
                    this.showPagebox = true
                }
            })
        },

        View(value) {
            this.$router.push({
                path: '/plug_ins/seckill/afterSaleDetails',
                query: {
                    id: value.id
                }
            })
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            // this.current_num = e
            this.pageSize = e
            this.getRefundLists().then(() => {
                this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
                this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
                this.loading = false
            })
        },

        //点击上一页，下一页
        handleCurrentChange(e){
            this.loading = true
            this.dictionaryNum = e
            this.currpage = ((e - 1) * this.pageSize) + 1
            this.getRefundLists().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        // 导出弹框方法
        dialogShow() {
            this.dialogVisible = true
        },
      
        handleClose(done) {
            this.dialogVisible = false
        },

        async exportPage() {
            exports({
                api: 'admin.sec.order.getRefundList',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                exportType: 1,
            },'pageorder')
        },
    
        async exportAll() {
            exports({
                api: 'admin.sec.order.getRefundList',
                pageNo: 1,
                pageSize: this.total,
                exportType: 1,
            },'allorder')
        },
    
        async exportQuery() {
            exports({
                api: 'admin.sec.order.getRefundList',
                pageNo: this.dictionaryNum,
                pageSize: this.total,
                exportType: 1,
                orderno: this.inputInfo.orderNo ? this.inputInfo.orderNo : null,
                status: this.inputInfo.state ? this.inputInfo.state : null,
                startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                endDate: this.inputInfo.date ? this.inputInfo.date[1] : null,
            },'queryorder')
        }
    }
}