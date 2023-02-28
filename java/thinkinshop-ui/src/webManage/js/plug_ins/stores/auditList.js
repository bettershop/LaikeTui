import { getMchExamineInfo, examineMch } from '@/api/plug_ins/stores'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'auditList',
    mixins: [mixinstest],
    data() {
      return {
        radio1:'审核列表',

        statusList: [
          {
            value: '0',
            label: '待审核'
          },
          {
            value: '2',
            label: '审核不通过'
          },
        ],// 审核状态

        inputInfo: {
          status: null,
          storeName: null,
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
            { required: true, message: '请输入拒绝理由', trigger: 'blur' }
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
      if(this.$route.params.pageSize) {
        this.pagination.page = this.$route.params.dictionaryNum
        this.dictionaryNum = this.$route.params.dictionaryNum
        this.pageSize = this.$route.params.pageSize
      }
      this.getMchExamineInfos()
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
      async getMchExamineInfos() {
        const res = await getMchExamineInfo({
            api: 'admin.mch.getMchExamineInfo',
            pageNo: this.dictionaryNum,
            pageSize: this.pageSize,
            reviewStatus: this.inputInfo.status,
            name: this.inputInfo.storeName
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
        this.inputInfo.status = null
        this.inputInfo.storeName = null
      },

      // 查询
      demand() {
          this.currpage = 1
          this.current_num = 10
          this.showPagebox = false
          this.loading = true
          this.dictionaryNum = 1
          this.getMchExamineInfos().then(() => {
          this.loading = false
          if(this.tableData.length > 5) {
              this.showPagebox = true
          }
          })
      },

      // 查看
      View(value) {
        this.$router.push({
          path: '/plug_ins/stores/viewStore',
          query: {
            id: value.id,
            status: value.examineName,
            dictionaryNum: this.dictionaryNum,
            pageSize: this.pageSize
          }
        })
      },

      // 通过
      Through(value) {
        this.$confirm('确认通过店铺审核？请仔细查看店铺信息！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          examineMch({
              api: 'admin.mch.examineMch',
              reviewStatus: 1,
              mchId: value.id
          }).then(res => {
              console.log(res);
              if(res.data.code == '200') {
                  this.getMchExamineInfos()
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
            message: '已取消删除',
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
          this.getMchExamineInfos().then(() => {
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
          this.getMchExamineInfos().then(() => {
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
                examineMch({
                    api: 'admin.mch.examineMch',
                    reviewStatus: 2,
                    mchId: this.id,
                    text: this.ruleForm.reason
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                      this.getMchExamineInfos()
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
          api: 'admin.mch.getMchExamineInfo',
          pageNo: this.dictionaryNum,
          pageSize: this.pageSize,
          exportType: 1,
        },'MchExamineInfo')
      },

      async exportAll() {
        exports({
          api: 'admin.mch.getMchExamineInfo',
          pageNo: 1,
          pageSize: this.total,
          exportType: 1,
        },'MchExamineInfo')
      },

      async exportQuery() {
        exports({
          api: 'admin.mch.getMchExamineInfo',
          pageNo: this.dictionaryNum,
          pageSize: this.total,
          exportType: 1,
          reviewStatus: this.inputInfo.status,
          name: this.inputInfo.storeName
        },'MchExamineInfo')
      }
    }
}