import { bannerIndex, bannerDel, bannerMoveTop, bannerRemove } from '@/api/plug_ins/template'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'playlist',
    mixins: [mixinstest],
    data() {
        return {
            radio1:'轮播图列表',

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
        this.bannerIndexs()
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
        async bannerIndexs() {
            const res = await bannerIndex({
                api: 'admin.diy.bannerIndex',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
            })
            this.current_num = 10
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            if(this.total == 0) {
                this.showPagebox = false
            }
            console.log(res);
        },

        // 移动
        moveUp(value) {
            if(value !== 0) {
                bannerRemove({
                  api: 'admin.diy.bannerRemove',
                  id: this.tableData[value - 1].id,
                  id1: this.tableData[value].id
                }).then(res => {
                    if(res.data.code == '200') {
                        this.bannerIndexs()
                        console.log(res);
                        this.$message({
                            type: 'success',
                            message: '操作成功!',
                            offset: 100
                        });
                    }
                })
            } else {
                bannerRemove({
                  api: 'admin.diy.bannerRemove',
                  id: this.tableData[value + 1].id,
                  id1: this.tableData[value].id
                }).then(res => {
                    if(res.data.code == '200') {
                        this.bannerIndexs()
                        console.log(res);
                        this.$message({
                            type: 'success',
                            message: '操作成功!',
                            offset: 100
                        });
                    }
                })
            }
        },

        // 置顶
        placedTop(value) {
            bannerMoveTop({
                api: 'admin.diy.bannerMoveTop',
                id: value.id
            }).then(res => {
                console.log(res);
                if(res.data.code == '200') {
                    this.bannerIndexs()
                    this.$message({
                        type: 'success',
                        message: '置顶成功!',
                        offset: 100
                    })
                }
            })
        },

        // 编辑
        Edit(value) {
            this.$router.push({
                name: 'editorSlideShow',
                params: value,
                query: {
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('确定要删除此轮播图吗?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
                bannerDel({
                    api: 'admin.diy.bannerDel',
                    id: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.bannerIndexs()
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
            this.bannerIndexs().then(() => {
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
            this.bannerIndexs().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}