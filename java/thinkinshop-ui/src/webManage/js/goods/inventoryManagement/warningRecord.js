import { getStockDetailInfo } from '@/api/goods/inventoryManagement'
import { mixinstest } from '@/mixins/index'
export default {
    naem: 'warningRecord',
    mixins: [mixinstest],

    data() {
        return {
            tableData: [],
            loading: true,
            // table高度
            tableHeight: null,
        }
    },

    created() {
        this.getStockDetailInfos()
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
        async getStockDetailInfos() {
            const res = await getStockDetailInfo({
                api: 'admin.goods.getStockDetailInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                pid: this.$route.query.id,
                attrId: this.$route.query.attrId,
                type: 2
            })
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

         //选择一页多少条
         handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getStockDetailInfos().then(() => {
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
            this.getStockDetailInfos().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}