import { integralGoodsList, delIntegralGoods, topIntegralGoods } from '@/api/plug_ins/integralMall'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'integralGoodsList',
    mixins: [mixinstest],

    data() {
        return {
            radio1:'1',

            inputInfo: {
                goodsName: null,
            },

            tableData: [],
            loading: true,

            is_disabled: true,
            idList: [],

            // table高度
            tableHeight: null
        }
    },

    created() {
        this.integralGoodsLists()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    methods: {
        async integralGoodsLists() {
            const res = await integralGoodsList({
                api: 'admin.integral.index',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                goodsName: this.inputInfo.goodsName
            })
            console.log(res);
            this.current_num = 10
            this.total = res.data.data.num
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
            console.log(this.$refs.tableFather.clientHeight);
		},

        // 重置
        reset() {
            this.inputInfo.goodsName = null
        },
  
        // 查询
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.integralGoodsLists().then(() => {
                this.loading = false
                if(this.tableData.length > 5) {
                    this.showPagebox = true
                }
            })
        },

        delAll() {
            this.$confirm('确定删除所选积分商品？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delIntegralGoods({
                      api: 'admin.integral.del',
                      ids: this.idList
                  }).then(res => {
                      console.log(res);
                      if(res.data.code == '200') {
                          this.integralGoodsLists()
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

        addGoods() {
            this.$router.push({
                path: '/plug_ins/integralMall/addIntegralGoods',
            })
        },

        // 置顶
        placedTop(value) {
            topIntegralGoods({
                api: 'admin.integral.top',
                id: value.id
            }).then(res => {
                console.log(res);
                if(res.data.code == '200') {
                    this.$message({
                        type: 'success',
                        message: '置顶成功!',
                        offset: 100
                    })
                }
                this.integralGoodsLists()
            })
        },

        Edit(value) {
            this.$router.push({
                path: '/plug_ins/integralMall/editorIntegralGoods',
                query: {
                    id: value.id,
                }
            })
        },

        Delete(value) {
            this.$confirm('确定删除所选积分商品？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delIntegralGoods({
                      api: 'admin.integral.del',
                      ids: value.id
                  }).then(res => {
                      console.log(res);
                      if(res.data.code == '200') {
                          this.integralGoodsLists()
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
            this.pageSize = e
            this.integralGoodsLists().then(() => {
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
            this.integralGoodsLists().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        // 选框改变
        handleSelectionChange(val) {
            if(val.length==0){
                this.is_disabled = true
            }else{
                this.is_disabled = false
            }
            console.log(val);
            this.idList = val.map(item => {
                return item.id
            })
            this.idList = this.idList.join(',')
        },

    }
}