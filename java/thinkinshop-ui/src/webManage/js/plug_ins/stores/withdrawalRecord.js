import { getWithdrawalInfo } from '@/api/plug_ins/stores'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'withdrawalRecord',
  mixins: [mixinstest],
  data() {
    return {
      radio1:'提现记录',

      inputInfo: {
        storeName: null,
        phone: null,
        status: null,
        date: null
      },

      statusList: [
        {
          value: 1,
          label: '审核已通过'
        },
        {
          value: 2,
          label: '已拒绝'
        },
      ],// 审核状态

      tableData: [],
      loading: true,
       // table高度
       tableHeight: null,

      // 导出弹框数据
      dialogVisible: false,
    }
  },

  created() {
    this.getWithdrawalInfos()
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
    async getWithdrawalInfos() {
      const res = await getWithdrawalInfo({
        api: 'admin.mch.getWithdrawalInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        mchName: this.inputInfo.storeName,
        phone: this.inputInfo.phone,
        status: this.inputInfo.status,
        startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
        endDate: this.inputInfo.date ? this.inputInfo.date[1] : null
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
      this.inputInfo.phone = null
      this.inputInfo.storeName = null
      this.inputInfo.status = null
      this.inputInfo.date = null
    },

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getWithdrawalInfos().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    //选择一页多少条
    handleSizeChange(e){
      this.loading = true
      console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.getWithdrawalInfos().then(() => {
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
      this.getWithdrawalInfos().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
    },

    // 导出弹框方法
    dialogShow() {
        this.dialogVisible = true
    },

    handleClose(done) {
        this.dialogVisible = false
    },

    async exportPage() {
      exports({
        api: 'admin.mch.getWithdrawalInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        exportType: 1,
      },'WithdrawalInfo')
    },

    async exportAll() {
      exports({
        api: 'admin.mch.getWithdrawalInfo',
        pageNo: 1,
        pageSize: this.total,
        exportType: 1,
      },'WithdrawalInfo')
    },

    async exportQuery() {
      exports({
        api: 'admin.mch.getWithdrawalInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.total,
        exportType: 1,
        mchName: this.inputInfo.storeName,
        phone: this.inputInfo.phone,
        status: this.inputInfo.status,
        startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
        endDate: this.inputInfo.date ? this.inputInfo.date[1] : null
      },'WithdrawalInfo')
    }

  }
}