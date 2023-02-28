import { getActGoodsList, actGoodsMove, actGoodsTop, actGoodsSwitch } from '@/api/plug_ins/template'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'activityGoods',
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
        this.getActGoodsLists()
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
        async getActGoodsLists() {
            const res = await getActGoodsList({
                api: 'admin.diy.getActGoodsList',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                id: this.$route.query.id
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

        // 是否显示
        switchs(value) {
            actGoodsSwitch({
                api: 'admin.diy.actGoodsSwitch',
                id: value.id
            }).then(res => {
                if(res.data.code == '200') {
                    this.getActGoodsLists()
                    this.$message({
                    type: 'success',
                    message: '成功!',
                    offset: 100
                    });
                }
            })
        },

        // 移动
        moveUp(value) {
            if(value !== 0) {
                actGoodsMove({
                    api: 'admin.diy.actGoodsMove',
                    id: this.$route.query.id,
                    goodsId: this.tableData[value - 1].id,
                    goodsId1: this.tableData[value].id
                }).then(res => {
                    if(res.data.code == '200') {
                    this.getActGoodsLists()
                    this.$message({
                        type: 'success',
                        message: '操作成功!',
                        offset: 100
                    });
                    }
                })
            } else {
                actGoodsMove({
                    api: 'admin.diy.actGoodsMove',
                    id: this.$route.query.id,
                    goodsId: this.tableData[value + 1].id,
                    goodsId1: this.tableData[value].id
                }).then(res => {
                    if(res.data.code == '200') {
                    this.getActGoodsLists()
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
            actGoodsTop({
            api: 'admin.diy.actGoodsTop',
            id: this.$route.query.id,
            goodsId: value.id
            }).then(res => {
                console.log(res);
                if(res.data.code == '200') {
                    this.getActGoodsLists()
                    this.$message({
                        type: 'success',
                        message: '置顶成功!',
                        offset: 100
                    })
                }
            })
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getActGoodsLists().then(() => {
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
            this.getActGoodsLists().then(() => {
            this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
            this.loading = false
            })
        },
    }
}