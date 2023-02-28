import { getRoleListInfo, delUserRoleMenu } from '@/api/authority/roleManagement'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'roleList',
    mixins: [mixinstest],

    data() {
        return {
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
        this.getRoleListInfos()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    beforeRouteLeave(to, from, next) {
        if (to.name == 'editorRoles') {
          from.meta.keepAlive = true
        } else {
          from.meta.keepAlive = false
        }
        next()
    },

    methods: {
        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
            console.log(this.$refs.tableFather.clientHeight);
		},
        async getRoleListInfos() {
            const res = await getRoleListInfo({
                api: 'admin.role.getRoleListInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize
            })
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

        // 查看
        View(value) {
            this.$router.push({
                path: '/authority/roleManagement/viewRoles',
                query: {
                    id: value.id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 编辑
        Edit(value) {
            this.$router.push({
                path: '/authority/roleManagement/editorRoles',
                query: {
                    id: value.id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('确定要删除这个角色吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
                delUserRoleMenu({
                    api: 'admin.role.delUserRoleMenu',
                    id: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getRoleListInfos()
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
            this.getRoleListInfos().then(() => {
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
            this.getRoleListInfos().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}