import { index, setPaymentSwitch } from '@/api/mall/payManagement'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'payList',

    mixins: [mixinstest],

    data() {
        return {
          tableData: [],
          loading: true,   
          // table高度
          tableHeight: null, 
        }
      },
    
      created() {
        if(this.$route.params.pageSize) {
            this.pagination.page = this.$route.params.dictionaryNum
            this.dictionaryNum = this.$route.params.dictionaryNum
            this.pageSize = this.$route.params.pageSize
        }
        this.indexs()
      },

      mounted() {
        this.$nextTick(function() {
          this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    
      },

      beforeRouteLeave(to, from, next) {
        if (to.name == 'parameterModify') {
          from.meta.keepAlive = true
        } else {
          from.meta.keepAlive = false
        }
        next()
      },
    
      methods: {
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},
        async indexs() {
            const res = await index({
                api: 'admin.payment.index',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
            })
            console.log(res);
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
        },

        // 改变开启状态
        switchs(value) {
            setPaymentSwitch({
                api: 'admin.payment.setPaymentSwitch',
                id: value.id
            }).then(res => {
                if(res.data.code == '200') {
                    this.$message({
                        type: 'success',
                        message: '执行成功!',
                        offset: 100
                    });
                    this.indexs()
                }
            })
        },

        // 参数修改
        parameter(value) {
            this.$router.push({
                path: '/mall/payManagement/parameterModify',
                query: {
                    id: value.id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.indexs().then(() => {
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
            this.indexs().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })  
        },
    }
    
}