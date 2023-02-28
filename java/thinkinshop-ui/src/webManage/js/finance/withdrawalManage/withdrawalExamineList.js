import {index, save} from '@/api/finance/withdrawalManage'
import pageData from '@/api/constant/page'
import { exports } from '@/api/export'
import {isEmpty} from "element-ui/src/utils/util";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'withdrawalExamineList',
  mixins: [mixinstest],
  data() {
    return {
      radio1: '提现审核',
      id: null,
      page: pageData.data(),
      backForm: {
        reason: "",
        rules: {reason: [{required: true, message: '请输入拒绝理由', trigger: 'change'}],}
      },
      // 弹框数据
      dialogVisible2: false,
      // 导出弹框数据
      dialogVisible: false,
      // table高度
      tableHeight: null
    }
  },

  created() {
    this.loadData()
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
    async loadData() {
      const res = await index({
        api: 'admin.user.getWithdrawalInfo',
        status: 0,
        userName: this.page.inputInfo.name,
        phone: this.page.inputInfo.phone,
        startDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[0],
        endDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[1],
      })
      this.total = res.data.data.total
      this.page.tableData = res.data.data.list
      this.page.loading = false
      if(res.data.data.total < 10) {
        this.current_num = this.total
      }
    },

    // 重置
    reset() {
      this.page.inputInfo.phone = null
      this.page.inputInfo.name = null
      this.page.inputInfo.date = null
    },

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.page.showPagebox = false
      this.page.loading = true
      this.dictionaryNum = 1
      this.loadData().then(() => {
        this.page.loading = false
        if (this.page.tableData.length > 5) {
          this.page.showPagebox = true
        }
      })
    },

    // 通过
    Through(value) {
      this.$confirm('确定要通过该用户的申请？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        save({
          api: 'admin.mch.withdrawalExamine',
          id: value.id,
          stauts: 1
        }).then(res => {
          console.log(res);
          if (res.data.code == '200') {
            this.loadData()
            this.$message({
              type: 'success',
              message: '成功!',
              offset: 100
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消',
          offset: 100
        });
      });
    },

    //选择一页多少条
    handleSizeChange(e) {
      console.log(e);
      this.page.loading = true
      // this.current_num = e
      this.pageSize = e
      this.loadData().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.loading = false
      })
    },


    //点击上一页，下一页
    handleCurrentChange(e) {
      this.page.loading = true
      this.dictionaryNum = e
      this.currpage = ((e - 1) * this.pageSize) + 1
      this.loadData().then(() => {
        this.current_num = this.page.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.page.loading = false
      })
    },

    // 弹框方法
    dialogShow2(value, toggle) {
      console.log(value);
      this.dialogVisible2 = true
      this.id = value.id
      this.ruleForm.reason = ''
    },

    handleClose2(done) {
      this.dialogVisible2 = false
      this.id = null
      this.$refs['ruleForm'].clearValidate()
    },

    // 拒绝
    Refused(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            save({
              api: 'admin.user.withdrawalExamine',
              id: this.id,
              stauts: 2,
              refuse: this.backForm.reason
            }).then(res => {
              console.log(res);
              if (res.data.code == '200') {
                this.loadData()
                this.$message({
                  type: 'success',
                  message: '成功!',
                  offset: 100
                })
                this.handleClose2()
              }
            })
          } catch (error) {
            this.$message({
              message: error.message,
              type: 'error',
              showClose: true
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
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
        api: 'admin.user.getWithdrawalInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        exportType: 1,
        status: 0
      },'WithdrawalInfo')
    },

    async exportAll() {
      exports({
        api: 'admin.user.getWithdrawalInfo',
        pageNo: 1,
        pageSize: this.total,
        exportType: 1,
        status: 0
      },'WithdrawalInfo')
    },

    async exportQuery() {
      exports({
        api: 'admin.user.getWithdrawalInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.total,
        exportType: 1,
        status: 0,
        userName: this.page.inputInfo.name,
        phone: this.page.inputInfo.phone,
        startDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[0],
        endDate: isEmpty(this.page.inputInfo.date) ? "" : this.page.inputInfo.date[1],
      },'WithdrawalInfo')
    }
  }
}
