import { getAgreementIndex, delAgreement } from '@/api/mall/systemManagement'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'protocolConfiguration',
    mixins: [mixinstest],
    data() {
        return {
            radio1: '协议配置',

            tableData: [],
            loading: true,
            // table高度
            tableHeight: null,
        }
    },

    created() {
        this.getAgreementIndexs()
    },

    mounted() {
        this.$nextTick(function() {
          this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    
    },

    methods: {
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},
        async getAgreementIndexs() {
            const res = await getAgreementIndex({
                api: 'admin.system.getAgreementIndex',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
            })
            this.current_num = 10
            this.total = res.data.data.list.length
            this.tableData = res.data.data.list
            this.upgrade = res.data.data.upgrade
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

        // 编辑
        Edit(value) {
            this.$router.push({
                path: '/mall/agreement/editorAgreement',
                query: {
                    id: value.id
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('确定要删除这篇协议吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delAgreement({
                    api: 'admin.system.delAgreement',
                    id: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getAgreementIndexs()
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

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getAgreementIndexs().then(() => {
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
            this.getAgreementIndexs().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}