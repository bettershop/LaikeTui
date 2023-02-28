import { getWithdrawalExamineInfo, withdrawalExamine } from '@/api/plug_ins/stores'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'withdrawalAudit',
  mixins: [mixinstest],
  data() {
    return {
      radio1:'提现审核',

      inputInfo: {
        storeName: null,
        phone: null,
        date: null
      },

      tableData: [],
      loading: true,

      // 弹框数据
      dialogVisible2: false,
      ruleForm: {
        reason: '',
      },
      
      rules: {
        reason: [
          { required: true, message: '请输入拒绝理由s', trigger: 'blur' }
        ],
      },
      id: null,

       // table高度
       tableHeight: null,

      // 导出弹框数据
      dialogVisible: false,
    }
  },

  created() {
    this.getWithdrawalExamineInfos()
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
    async getWithdrawalExamineInfos() {
      const res = await getWithdrawalExamineInfo({
        api: 'admin.mch.getWithdrawalExamineInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        userName: this.inputInfo.storeName,
        phone: this.inputInfo.phone,
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
      this.inputInfo.date = null
    },

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getWithdrawalExamineInfos().then(() => {
      this.loading = false
      if(this.tableData.length > 5) {
          this.showPagebox = true
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
        withdrawalExamine({
          api: 'admin.mch.withdrawalExamine',
          id: value.id,
          stauts: 1
          }).then(res => {
            console.log(res);
            if(res.data.code == '200') {
              this.getWithdrawalExamineInfos()
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

    // 拒绝
    Refused(value) {
        
    },

    //选择一页多少条
    handleSizeChange(e){
        this.loading = true
        console.log(e);
        // this.current_num = e
        this.pageSize = e
        this.getWithdrawalExamineInfos().then(() => {
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
      this.getWithdrawalExamineInfos().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
    },

    // 弹框方法
    dialogShow2(value,toggle) {
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

    submitForm2(formName) {
        this.$refs[formName].validate(async (valid) => {
          console.log(this.ruleForm);
          if (valid) {
            try {
              withdrawalExamine({
                api: 'admin.mch.withdrawalExamine',
                id: this.id,
                stauts: 2,
                text: this.ruleForm.reason

                }).then(res => {
                  console.log(res);
                  if(res.data.code == '200') {
                    this.getWithdrawalExamineInfos()
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
        api: 'admin.mch.getWithdrawalExamineInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        exportType: 1,
      },'WithdrawalExamineInfo')
    },

    async exportAll() {
      exports({
        api: 'admin.mch.getWithdrawalExamineInfo',
        pageNo: 1,
        pageSize: this.total,
        exportType: 1,
      },'WithdrawalExamineInfo')
    },

    async exportQuery() {
      exports({
        api: 'admin.mch.getWithdrawalExamineInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.total,
        exportType: 1,
        userName: this.inputInfo.storeName,
        phone: this.inputInfo.phone,
        startDate: this.inputInfo.date ? this.inputInfo.date[0] : null,
        endDate: this.inputInfo.date ? this.inputInfo.date[1] : null
      },'WithdrawalExamineInfo')
    }
  }
}