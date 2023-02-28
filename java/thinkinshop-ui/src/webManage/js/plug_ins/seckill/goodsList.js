import { secLabelGoodsList, delPro, labelGoodsSwitch } from '@/api/plug_ins/seckill'
import { mixinstest } from '@/mixins/index'

export default {
    name: 'addGoods',
    mixins: [mixinstest],
    data() {
        return {
            inputInfo: {
                seckill_status: null,
                goodsName: null,
                date: null,
            },

            seckillStatusList: [
                {
                    value: 0,
                    label: '未开始'
                },
                {
                    value: 1,
                    label: '进行中'
                },
                {
                    value: 2,
                    label: '已结束'
                }
            ],// 秒杀状态

            tableData: [],
            loading: true,

            is_disabled: true,
            idList: [],

            // table高度
            tableHeight: null
        }
    },

    created() {
        this.secLabelGoodsLists()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    methods: {
        async secLabelGoodsLists() {
            const res = await secLabelGoodsList({
                api: 'admin.sec.label.secLabelGoodsList',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                labelId: this.$route.query.id,
                secStatus: this.inputInfo.seckill_status,
                keyName: this.inputInfo.goodsName,
                startdate: this.inputInfo.date ? this.inputInfo.date[0] : null,
                enddate: this.inputInfo.date ? this.inputInfo.date[1] : null
            })
            console.log(res);
            this.current_num = 10
            this.total = res.data.data.total
            this.tableData = res.data.data.goodsList
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
        },

        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
            console.log(this.$refs.tableFather.clientHeight);
		},

        // 重置
        reset() {
            this.inputInfo.seckill_status = null
            this.inputInfo.goodsName = null
            this.inputInfo.date = null
        },
  
        // 查询
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.secLabelGoodsLists().then(() => {
                this.loading = false
                if(this.tableData.length > 5) {
                    this.showPagebox = true
                }
            })
        },

        delAll() {
            this.$confirm('确定删除所选秒杀商品？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delPro({
                      api: 'admin.sec.label.delPro',
                      id: this.idList
                  }).then(res => {
                      console.log(res);
                      if(res.data.code == '200') {
                          this.secLabelGoodsLists()
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
                path: '/plug_ins/seckill/addGoods',
                query: {
                    id: this.$route.query.id
                }
            })
        },

        // 是否显示
        switchs(value) {
            labelGoodsSwitch({
                api: 'admin.sec.label.labelGoodsSwitch',
                id: value.id
            }).then(res => {
                if(res.data.code == '200') {
                    this.$message({
                        type: 'success',
                        message: '成功!',
                        offset: 100
                    })
                }
                this.secLabelGoodsLists()
            })
        },

        Edit(value) {
            this.$router.push({
                path: '/plug_ins/seckill/editorGoods',
                query: {
                    id: value.id,
                    labelId: this.$route.query.id
                }
            })
        },

        details(value) {
            console.log(value);
            this.$router.push({
                path: '/plug_ins/seckill/seckillRecord',
                query: {
                    name: value.product_title,
                    date: [this.conversionTime(value.starttime),this.conversionTime(value.endtime)]
                }
            })
        },

        // 查看
        View(value) {
            console.log(value);
            this.$router.push({
                path: '/plug_ins/seckill/viewGoods',
                query: {
                    id: value.id,
                    labelId: this.$route.query.id
                }
            })
        },

        // 格式化时间
        conversionTime(val) {
            const d = new Date(val); // val 为表格内取到的后台时间
            const month = d.getMonth() + 1 < 10 ? `0${d.getMonth() + 1}` : d.getMonth() + 1;
            const day = d.getDate() < 10 ? `0${d.getDate()}` : d.getDate();
            const hours = d.getHours() < 10 ? `0${d.getHours()}` : d.getHours();
            const min = d.getMinutes() < 10 ? `0${d.getMinutes()}` : d.getMinutes();
            const sec = d.getSeconds() < 10 ? `0${d.getSeconds()}` : d.getSeconds();
            const times = `${d.getFullYear()}-${month}-${day} ${hours}:${min}:${sec}`;
            return times;
        },

        Delete(value) {
            this.$confirm('确定删除所选秒杀商品？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delPro({
                      api: 'admin.sec.label.delPro',
                      id: value.id
                  }).then(res => {
                      console.log(res);
                      if(res.data.code == '200') {
                          this.secLabelGoodsLists()
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
            this.secLabelGoodsLists().then(() => {
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
            this.secLabelGoodsLists().then(() => {
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