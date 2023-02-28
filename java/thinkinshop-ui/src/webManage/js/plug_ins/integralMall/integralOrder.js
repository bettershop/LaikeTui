import { orderList, delOrder, closeOrder } from '@/api/plug_ins/integralMall'
import { kuaidishow } from '@/api/order/orderList'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'

export default {
    name: 'integralOrder',
    mixins: [mixinstest],

    data() {
        return {
            radio1:'2',
            inputInfo: {
                name: null,
                mchName: null,
                state: null,
                date: null
            },
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

            tableData: [],
            loading: true,

            idList: [],
            idPrintList: [],
            is_disabled: true,

            // 弹框数据
            dialogVisible2: false,
            ruleForm: {
                courier_company: '',
                courier_no: '',
                logistics: ''
            },

            // table高度
            tableHeight: null,

            // 导出弹框数据
            dialogVisible: false,
        }
    },

    created() {
        this.orderLists()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    methods: {
        rowstyles({row,rowIndex}){
            let styleJson = {
                "border":"none"
            };
            return styleJson
        },

        async orderLists() {
            const res = await orderList({
                api: 'admin.integral.order.index',
                pageSize: this.pageSize,
                pageNo: this.dictionaryNum,
                keyWord: this.inputInfo.name,
                mchName: this.inputInfo.mchName,
                status: this.inputInfo.state,
                startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                endDate: this.inputInfo.date ? this.inputInfo.date[1] : null
            })
            console.log(res);
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
        },
        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},
        
        // 重置
        reset() {
            this.inputInfo.name = null
            this.inputInfo.mchName = null
            this.inputInfo.state = null
            this.inputInfo.date = null
        },
  
        // 查询
        demand() {
            // this.showPagebox = false
            this.currpage = 1
            this.current_num = 10
            this.loading = true
            this.dictionaryNum = 1
            this.orderLists().then(() => {
                this.loading = false
                // if(this.tableData.length > 5) {
                //     this.showPagebox = true
                // }
            })
        },

        // 订单售后
        afterSales() {
            this.$router.push({
                path: '/plug_ins/integralMall/afterSaleList'
            })
        },

        // 评价管理
        evaluation() {
            this.$router.push({
                path: '/plug_ins/integralMall/commentList'
            })
        },

        // 订单结算
        settlement() {
            this.$router.push({
                path: '/plug_ins/integralMall/orderSettlementList'
            })
        },

        // 订单打印
        print() {
            if(this.idList.length == 0) {
                this.$message({
                    message: '请选择订单',
                    type: 'warning',
                    offset: 100
                })
            } else {
                let routeData = this.$router.resolve({
                    path: "/print",
                    query: {
                        orderId: this.idPrintList
                    },
                });
    
                window.open(routeData.href, '_blank');
            }
        },

        // 批量删除
        delAll() {
            this.$confirm('确定删除所选积分商品？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delOrder({
                      api: 'admin.integral.order.delOrder',
                      id: this.idList
                  }).then(res => {
                      console.log(res);
                      if(res.data.code == '200') {
                          this.orderLists()
                          this.$message({
                              type: 'success',
                              message: '删除成功!',
                              offset: 100
                          })
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

        handleSelectionChange(val) {
            if(val.length !== 0) {
                this.is_disabled = false
            } else {
                this.is_disabled = true
            }
            this.idList = val.map(item => {
                return item.id
            })
            this.idPrintList = val.map(item => {
                return item.orderno
            })
            this.idList = this.idList.join(',')
            this.idPrintList = this.idPrintList.join(',')
        },

        // 订单详情
        Details(value) {
            console.log(value);
            this.$router.push({
                path: '/plug_ins/integralMall/orderDetails',
                query: {
                    no: value.orderno
                }
            })
        },

        // 编辑订单
        Edit(value) {
            console.log(value);
            this.$router.push({
                path: '/plug_ins/integralMall/editorOrder',
                query: {
                    no: value.orderno
                }
            })
        },

        // 商品发货
        Delivery(value) {
            console.log(value);
            this.$router.push({
                path: '/plug_ins/integralMall/goodsDelivery',
                query: {
                    no: value.orderno
                }
            })
        },

        // 关闭订单
        closeOrder(value) {
            console.log(value);
            closeOrder({
                api: 'admin.integral.order.closeOrder',
                id: value.id
            }).then(res => {
                if(res.data.code == '200') {
                    console.log(res);
                    this.$message({
                        type: 'success',
                        message: '操作成功！',
                        offset: 100
                    })
                    this.orderLists()
                }
            })
        },

        // 弹框方法
        dialogShow2(value) {
            this.dialogVisible2 = true
            kuaidishow({
                api: 'admin.order.kuaidishow',
                orderno: value.orderno
            }).then(res  => {
                console.log(res);
                this.ruleForm.courier_company = res.data.data.list[0].courier_num
                this.ruleForm.courier_no = res.data.data.list[0].kuaidi_name
                this.ruleForm.logistics = ''
            })
            console.log(value);
            
        },
        
        handleClose2(done) {
            this.dialogVisible2 = false
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
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

        // 导出弹框方法
        dialogShow() {
            this.dialogVisible = true
        },

        showPrice(price){
            return parseInt(price).toFixed(0)
        },
      
        handleClose(done) {
            this.dialogVisible = false
        },
  
        async exportPage() {
            exports({
                api: 'admin.integral.order.index',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                exportType: 1,
            },'pageorder')
        },
    
        async exportAll() {
            exports({
                api: 'admin.integral.order.index',
                pageNo: 1,
                pageSize: this.total,
                exportType: 1,
            },'allorder')
        },
    
        async exportQuery() {
            exports({
                api: 'admin.integral.order.index',
                pageNo: this.dictionaryNum,
                pageSize: this.total,
                exportType: 1,
                keyWord: this.inputInfo.name,
                mchName: this.inputInfo.mchName,
                status: this.inputInfo.state,
                startdate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                enddate: this.inputInfo.date ? this.inputInfo.date[1] : null
            },'queryorder')
        }
    }
}