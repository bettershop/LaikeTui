import { getStockInfo, addStock} from '@/api/goods/inventoryManagement'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'inventoryList',
    mixins: [mixinstest],
    data() {
        return {
            radio1:'库存列表',

            inputInfo: {
                storeName: null,
                goodsName: null,
            },

            tableData: [],
            loading: true,

            // 弹框数据
            dialogVisible2: false,
            ruleForm2: {
                addNum: '',
                total_num: null,
                num: null
            },
            rules2: {
                addNum: [
                    { required: true, message: '请输入增加库存量', trigger: 'blur' },
                ],
            },
            // table高度
            tableHeight: null,

            // 导出弹框数据
            dialogVisible: false,
        }
    },

    created() {
        this.getStockInfos()
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
        
        async getStockInfos() {
            const res = await getStockInfo({
                api: 'admin.goods.getStockInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                mchName: this.inputInfo.storeName,
                productTitle: this.inputInfo.goodsName
            })
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

        // 重置
        reset() {
            this.inputInfo.storeName = null
            this.inputInfo.goodsName = null
        },
  
        // 查询
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.getStockInfos().then(() => {
            this.loading = false
            if(this.tableData.length > 5) {
                this.showPagebox = true
            }
            })
        },

        enters() {
            
        },

        // 库存详情
        inventorydetails(value) {
            this.$router.push({
                path: '/goods/inventoryManagement/InventoryDetails',
                query: {
                    id: value.pid,
                    attrId: value.id
                }
            })
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getStockInfos().then(() => {
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
            this.getStockInfos().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        // 弹框方法
        dialogShow2(value) {
            console.log(value);
            this.dialogVisible2 = true
            this.ruleForm2.total_num = value.total_num
            this.ruleForm2.num = value.num
            this.id = value.id
            this.pid = value.pid
        },
          
        handleClose2(done) {
            this.dialogVisible2 = false
            this.$refs['ruleForm2'].clearValidate()
            this.ruleForm2.addNum = ''
            this.ruleForm2.total_num = null
            this.ruleForm2.num = null
        },

        // 添加库存
        determine(formName2) {
            this.$refs[formName2].validate(async (valid) => {
              if (valid) {
                try {
                  addStock({
                    api: 'admin.goods.addStock',
                    id: this.id,
                    pid: this.pid,
                    addNum: this.ruleForm2.addNum
                  }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                      this.$message({
                        message: '添加成功',
                        type: 'success',
                        offset: 100
                      })
                      this.dialogVisible2 = false
                      this.getStockInfos()
                      this.handleClose2()
                    }
                  })
                } catch (error) {
                  this.$message({
                    message: '请输入增加库存量',
                    type: 'error',
                    offset: 100
                  })
                }
              }
            });
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
                api: 'admin.goods.getStockInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                exportType: 1,
            },'Stock')
        },

        async exportAll() {
            exports({
                api: 'admin.goods.getStockInfo',
                pageNo: 1,
                pageSize: this.total,
                exportType: 1,
            },'allStock')
        },

        async exportQuery() {
            exports({
              api: 'admin.goods.getStockInfo',
              pageNo: this.dictionaryNum,
              pageSize: this.pageSize,
              exportType: 1,
              mchName: this.inputInfo.storeName,
              productTitle: this.inputInfo.goodsName
            },'queryStock')
        }
    }
}