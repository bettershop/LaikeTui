import { getSecRecord, delSecRecord } from '@/api/plug_ins/seckill'
import { mixinstest } from '@/mixins/index'

export default {
    name: 'seckillRecord',
    mixins: [mixinstest],

    data() {
        return {
            radio1:'2',
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
                user: null,
                goodsName: null,
                date: null
            },

            tableData: [],
            loading: true,
            // table高度
            tableHeight: null
        }
    },

    created() {
        if(this.$route.query.name) {
            this.inputInfo.goodsName = this.$route.query.name
            this.inputInfo.date = this.$route.query.date
        }
        this.getSecRecords()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    methods: {
        async getSecRecords() {
            const res = await getSecRecord({
                api: 'admin.sec.label.getSecRecord',
                pageSize: this.pageSize,
                pageNo: this.dictionaryNum,
                user: this.inputInfo.user,
                goodsName: this.inputInfo.goodsName,
                startdate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                enddate: this.inputInfo.date ? this.inputInfo.date[1] : null
            })
            console.log(res);
            this.current_num = 10
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

        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},
        
        // 重置
        reset() {
            this.inputInfo.user = null
            this.inputInfo.goodsName = null
            this.inputInfo.date = null
        },
  
        // 查询
        demand() {
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.getSecRecords().then(() => {
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
                    id: value.id
                }
            })
        },

        // 修改
        Modify(value) {
            this.$router.push({
                path: '/plug_ins/stores/editorStore',
                query: {
                    id: value.id
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('确定删除此记录么？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
                delSecRecord({
                    api: 'admin.sec.label.delSecRecord',
                    rid: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getSecRecords()
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
            this.getSecRecords().then(() => {
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
            this.getSecRecords().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}