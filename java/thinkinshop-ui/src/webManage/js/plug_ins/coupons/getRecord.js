import { seeCouponLogger } from '@/api/plug_ins/coupons'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'getRecord',
    mixins: [mixinstest],
    data() {
        return {
            stateList: [
                {
                    value: '0',
                    label: '未使用'
                },
                {
                    value: '1',
                    label: '使用中'
                },
                {
                    value: '2',
                    label: '已使用'
                },
                {
                    value: '3',
                    label: '已过期'
                }
            ],// 订单状态
            inputInfo: {
                orderNo: null,
                membersName: null,
                state: null,
            },

            tableData: [],
            loading: true,

            sNo: null,
            // table高度
            tableHeight: null
        }
    },

    created() {
        this.seeCouponLoggers()
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
        async seeCouponLoggers() {
            const res = await seeCouponLogger({
                api: 'admin.coupon.seeCouponLogger',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                hid: this.$route.query.id,
                status: this.inputInfo.state,
                sNo: this.inputInfo.orderNo,
                name: this.inputInfo.membersName
            })
            this.current_num = 10
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.sNo = res.data.data.sNo
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

         // 重置
         reset() {
            this.inputInfo.orderNo = null
            this.inputInfo.membersName = null
            this.inputInfo.state = null
        },
  
        // 查询
        demand() {
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.seeCouponLoggers().then(() => {
                this.loading = false
                if(this.tableData.length > 5) {
                    this.showPagebox = true
                }
            })
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            // this.current_num = e
            this.pageSize = e
            this.seeCouponLoggers().then(() => {
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
            this.seeCouponLoggers().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        goOrderList(value) {
            this.$router.push({
                path: '/order/orderList/orderLists',
                query: {
                  no: value
                }
            })
        }

    }
}