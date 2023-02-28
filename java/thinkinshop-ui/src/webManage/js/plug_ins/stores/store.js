import { getMchInfo, delMchInfo } from '@/api/plug_ins/stores'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'store',
    mixins: [mixinstest],
    data() {
        return {
            radio1:'店铺',
            statusList: [
                {
                    value: '0',
                    label: '未营业'
                },
                {
                    value: '1',
                    label: '营业中'
                },
                {
                    value: '2',
                    label: '打烊'
                }
            ],// 会员等级

            inputInfo: {
                status: null,
                storeName: null,
                promiseStatus:null
            },

            tableData: [],
            loading: true,
            // table高度
            tableHeight: null
        }
    },

    created() {
        if(this.$route.params.pageSize) {
            this.pagination.page = this.$route.params.dictionaryNum
            this.dictionaryNum = this.$route.params.dictionaryNum
            this.pageSize = this.$route.params.pageSize
        }
        this.getMchInfos()
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
		},
        async getMchInfos() {
            const res = await getMchInfo({
                api: 'admin.mch.getMchInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                isOpen: this.inputInfo.status,
                name: this.inputInfo.storeName,
                promiseStatus: this.inputInfo.promiseStatus
            })
            this.current_num = 10
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
            this.inputInfo.status = null
            this.inputInfo.storeName = null
            this.inputInfo.promiseStatus = null
        },
  
        // 查询
        demand() {
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.getMchInfos().then(() => {
                this.loading = false
                if(this.tableData.length > 5) {
                    this.showPagebox = true
                }
            })
        },

        // 查看
        View(value) {
            this.$router.push({
                path: '/plug_ins/stores/viewStore',
                query: {
                    id: value.id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 修改
        Modify(value) {
            this.$router.push({
                path: '/plug_ins/stores/editorStore',
                query: {
                    id: value.id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('删除店铺后，系统将自动清空店铺相关信息，是否删除此店铺？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
                delMchInfo({
                    api: 'admin.mch.delMchInfo',
                    mchId: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getMchInfos()
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
            this.getMchInfos().then(() => {
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
            this.getMchInfos().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}