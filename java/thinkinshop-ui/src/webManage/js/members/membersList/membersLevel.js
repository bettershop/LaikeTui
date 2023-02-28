import { getUserGradeInfo, delUserGrade } from '@/api/members/membersLevel'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'membersLevel',
    mixins: [mixinstest],
    data() {
        return {
            radio1:'会员等级',

            tableData: [],
            loading: true,
            upgrade: [],
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
        this.getUserGradeInfos()
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
        async getUserGradeInfos() {
            const res = await getUserGradeInfo({
                api: 'admin.user.getUserGradeInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize
            })
            this.total = res.data.data.total
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
                path: '/members/addLevel',
                query: {
                    id: value.id,
                    type: 'editor',
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('确认删除该会员等级吗？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
                delUserGrade({
                    api: 'admin.user.delUserGrade',
                    id: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getUserGradeInfos()
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
            this.getUserGradeInfos().then(() => {
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
            this.getUserGradeInfos().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}