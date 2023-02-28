import { getTaoBaoWorkeListDetail, executeTaoBaoById } from '@/api/goods/taobaoAssistant'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'viewTask',

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
        this.getTaoBaoWorkeListDetails()
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
        async getTaoBaoWorkeListDetails() {
            const res = await getTaoBaoWorkeListDetail({
                api: 'admin.goods.getTaoBaoWorkeListDetail',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                wid: this.$route.query.id
            })
            console.log(res);
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
        },

        Edit(value) {
            this.$router.push({
                path: '/goods/goodslist/releasephysical',
                query: {
                  id: value.goodsId,
                  name: 'editor'
                },
            })
        },

        viewLink(url) {
            window.open(url, '_blank')
        },

        draws(value) {
            executeTaoBaoById({
                api: 'admin.goods.restoreExecuteTaoBaoById',
                taskId: value.id
            }).then(res => {
                console.log(res);
                if(res.data.code == '200') {
                    this.getTaoBaoWorkeListDetails()
                    this.$message({
                        type: 'success',
                        message: '执行成功!',
                        offset: 100
                    });
                }
            })
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getTaoBaoWorkeLists().then(() => {
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
            this.getTaoBaoWorkeLists().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })  
        },
    }
    
}