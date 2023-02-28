import { orderList, delOrder, kuaidishow } from '@/api/order/orderList'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'orderLists',
    mixins: [mixinstest],
    data() {
        return {
            radio1:'',
            stateList: [
                {
                    value: '0',
                    label: '待付款'
                },
                {
                    value: '1',
                    label: '待发货'
                },
                {
                    value: '2',
                    label: '待收货'
                },
                {
                    value: '5',
                    label: '已完成'
                },
                {
                    value: '7',
                    label: '已关闭'
                }
            ],// 订单状态
            typeList: [
                {
                    value: '1',
                    label: '用户下单'
                },
                {
                    value: '2',
                    label: '店铺下单'
                },
                {
                    value: '3',
                    label: '平台下单'
                }
            ],// 订单类型
            inputInfo: {
                orderInfo: null,
                store: null,
                state: null,
                type: null,
                date: null
            },
            is_disabled: true, // 批量删除按钮
            goodsListType: null, // 商品列表类型
            orderList: [], // 订单号集合

            tableData: [],
            loading: true,

            // 弹框数据
            dialogVisible2: false,
            ruleForm: {
                courier_company: '',
                courier_no: '',
                logistics: ''
            },

            logisticsList: [],

            // 导出弹框数据
            dialogVisible: false,
            // table高度
            tableHeight: null
        }
    },

    created() {
        if(this.$route.params.pageSize) {
            this.pagination.page = this.$route.params.dictionaryNum
            this.dictionaryNum = this.$route.params.dictionaryNum
            this.pageSize = this.$route.params.pageSize
            this.radio1 = this.$route.params.radio1
        } else {
            this.$router.currentRoute.matched[2].meta.title = ''
        }
        if(this.$route.query.no || this.$route.query.value) {
            if(this.$route.query.no) {
                this.inputInfo.orderInfo = this.$route.query.no
                orderList({
                    api: 'admin.order.index',
                    pageNo: this.dictionaryNum,
                    pageSize: this.pageSize,
                    keyWord: this.$route.query.no,
                }).then(res => {
                    console.log(res);
                    this.total = res.data.data.total
                    this.tableData = res.data.data.list
                    this.loading = false
                    if(this.total < this.current_num) {
                        this.current_num = this.total
                    }
                })
            } else if(this.$route.query.value) {
                this.inputInfo.state = this.$route.query.value
                orderList({
                    api: 'admin.order.index',
                    pageNo: this.dictionaryNum,
                    pageSize: this.pageSize,
                    status: this.$route.query.value,
                }).then(res => {
                    console.log(res);
                    this.total = res.data.data.total
                    this.tableData = res.data.data.list
                    this.loading = false
                    if(this.total < this.current_num) {
                        this.current_num = this.total
                    }
                })
            }
            
        } else {
            this.orderLists()
        }
        this.$store.dispatch('orderNum/getOrderCount')
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    watch: {
        radio1() {
            if(this.$route.params.radio1 && this.$route.params.radio1 !== this.radio1) {
                this.pagination.page = 1
            }
            this.dictionaryNum = 1
            this.pageSize = 10
            this.currpage = 1
            this.current_num = 10
            if(this.radio1 == '1') {
                this.goodsListType = 1
                this.$router.currentRoute.matched[2].meta.title = '实物订单'
            } else if(this.radio1 == '2') {
                this.goodsListType = 2
                this.$router.currentRoute.matched[2].meta.title = '自提订单'
            } 
            // else if(this.radio1 == '3') {
            //     this.goodsListType = 3
            //     this.$router.currentRoute.matched[2].meta.title = '虚拟订单'
            // } else if(this.radio1 == '4') {
            //     this.goodsListType = 4
            //     this.$router.currentRoute.matched[2].meta.title = '活动订单'
            // } 
            else {
                this.goodsListType = null
                this.$router.currentRoute.matched[2].meta.title = ''
            }
            this.inputInfo.orderInfo = null,
            this.inputInfo.store = null,
            this.inputInfo.state = null,
            this.inputInfo.type = null,
            this.inputInfo.date = null
            this.orderLists()
            this.showPagebox = true
        },
        '$route.query.no': {
            handler:function(){
                this.inputInfo.orderInfo = this.$route.query.no
                orderList({
                    api: 'admin.order.index',
                    pageNo: this.dictionaryNum,
                    pageSize: this.pageSize,
                    keyWord: this.$route.query.no,
                }).then(res => {
                    console.log(res);
                    this.total = res.data.data.total
                    this.tableData = res.data.data.list
                    this.loading = false
                    if(this.total < this.current_num) {
                        this.current_num = this.total
                    }
                })
            },
            deep:true
        }
    },

    methods: {
        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
            console.log(this.$refs.tableFather.clientHeight);
		},
        async orderLists() {
            const res = await orderList({
                api: 'admin.order.index',
                pageSize: this.pageSize,
                pageNo: this.dictionaryNum,
                keyWord: this.inputInfo.orderInfo ? this.inputInfo.orderInfo : null,
                mchName: this.inputInfo.store ? this.inputInfo.store : null,
                status: this.inputInfo.state ? this.inputInfo.state : null,
                operationType: this.inputInfo.type ? this.inputInfo.type : null,
                startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                endDate: this.inputInfo.date ? this.inputInfo.date[1] : null,
                selfLifting: this.goodsListType
            })
            console.log(res);
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.pagination.page = this.dictionaryNum
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            this.$nextTick(() => {
                this.$refs.table.doLayout();
            });
        },

        // 重置
        reset() {
            this.inputInfo.orderInfo = null,
            this.inputInfo.store = null,
            this.inputInfo.state = null,
            this.inputInfo.type = null,
            this.inputInfo.date = null
        },
  
        // 查询
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.orderLists().then(() => {
            this.loading = false
            if(this.tableData.length > 5) {
                this.showPagebox = true
            }
            })
        },

        // 代客下单
        placeOrder() {
            this.$router.push('/order/orderList/valetOrder')
        },

        // 打印
        print() {
            if(this.orderList.length == 0) {
                this.$message({
                    message: '请选择订单',
                    type: 'warning',
                    offset: 100
                })
            } else {
                let routeData = this.$router.resolve({
                    path: "/print",
                    query: {
                        orderId: this.orderList
                    },
                });
    
                window.open(routeData.href, '_blank');
            }
        },

        // 批量删除
        delAll() {
            this.$confirm('确认删除此所选订单？此操作不可恢复！', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delOrder({
                    api: 'admin.order.del',
                    orders: this.orderList
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.$message({
                            message: '删除成功',
                            type: 'success',
                            offset: 100
                        })
                        this.orderLists()
                        this.$store.dispatch('orderNum/getOrderCount')
                    }
                })
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消删除',
                  offset: 100
                });          
            });
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.orderLists().then(() => {
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
            this.orderLists().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        // 订单详情
        Details(value) {
            this.$router.push({
                path: '/order/orderList/orderDetails',
                query: {
                    no: value.orderno,
                }
            })
        },
        
        // 编辑订单
        Edit(value) {
            this.$router.push({
                path: '/order/orderList/editorOrder',
                query: {
                    no: value.orderno,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize,
                    radio1: this.radio1
                }
            })
        },

        // 商品发货
        Delivery(value) {
            this.$router.push({
                path: '/order/orderList/goodsDelivery',
                query: {
                    no: value.orderno,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize,
                    radio1: this.radio1
                }
            })
        },

        // 查看物流
        Logistics(value) {

        },

        // 选框改变
        handleSelectionChange(val) {
            if(val.length==0){
                this.is_disabled = true
            }else{
                this.is_disabled = false
            }
            console.log(val);
            this.orderList = val.map(item => {
                return item.orderno
            })
            this.orderList = this.orderList.join(',')
        },

        // 弹框方法
        dialogShow2(value) {
            this.dialogVisible2 = true
            kuaidishow({
                api: 'admin.order.kuaidishow',
                orderno: value.orderno
            }).then(res  => {
                console.log(res);
                this.logisticsList = res.data.data.list
                this.ruleForm.courier_company = res.data.data.list[0].courier_num
                this.ruleForm.courier_no = res.data.data.list[0].kuaidi_name
                this.ruleForm.logistics = ''
            })
            console.log(value);
            
        },
        
        handleClose2(done) {
            this.dialogVisible2 = false
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
                api: 'admin.order.index',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                exportType: 1,
            },'pageorder')
        },
    
        async exportAll() {
            exports({
                api: 'admin.order.index',
                pageNo: 1,
                pageSize: this.total,
                exportType: 1,
            },'allorder')
        },
    
        async exportQuery() {
            exports({
                api: 'admin.order.index',
                pageNo: this.dictionaryNum,
                pageSize: this.total,
                exportType: 1,
                keyWord: this.inputInfo.orderInfo ? this.inputInfo.orderInfo : null,
                mchName: this.inputInfo.store ? this.inputInfo.store : null,
                status: this.inputInfo.state ? this.inputInfo.state : null,
                operationType: this.inputInfo.type ? this.inputInfo.type : null,
                startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                endDate: this.inputInfo.date ? this.inputInfo.date[1] : null,
                selfLifting: this.goodsListType
            },'queryorder')
        }
    }
}