import { getGoodsExamineInfo, goodsExamine } from '@/api/plug_ins/stores'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'goodsAudit',
  mixins: [mixinstest],
  data() {
    return {
      radio1:'商品审核',

      inputInfo: {
        goodsName: null,
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
          { required: true, message: '请输入拒绝理由s', trigger: 'blur' }
        ],
      },

      id: null,
      // table高度
      tableHeight: null
    }
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    if(this.$route.query.name) {
      this.inputInfo.goodsName = this.$route.query.no
      getGoodsExamineInfo({
        api: 'admin.mch.getGoodsExamineInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        goodsName: this.$route.query.name
      }).then(res => {
          console.log(res);
          this.current_num = 10
          this.total = res.data.data.total
          this.tableData = res.data.data.list
          this.loading = false
          if(this.total < this.current_num) {
            this.current_num = this.total
          }
      })
    } else {
      this.getGoodsExamineInfos()
    }
    // this.getGoodsExamineInfos()
  },

  mounted() {
    this.$nextTick(function() {
      this.getHeight()
    })
    window.addEventListener('resize',this.getHeight(),false)
  },

  watch: {
    '$route.query.no': {
      handler:function(){
        this.inputInfo.goodsName = this.$route.query.no
        getGoodsExamineInfo({
          api: 'admin.mch.getGoodsExamineInfo',
          pageNo: this.dictionaryNum,
          pageSize: this.pageSize,
          goodsName: this.$route.query.name
        }).then(res => {
            console.log(res);
            this.current_num = 10
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
              this.current_num = this.total
            }
        })
      },
      deep:true
    }
  },

  methods: {
    // 获取table高度
    getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},
    async getGoodsExamineInfos() {
      const res = await getGoodsExamineInfo({
        api: 'admin.mch.getGoodsExamineInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        mchName: this.inputInfo.storeName,
        goodsName: this.inputInfo.goodsName
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
      this.inputInfo.goodsName = null
      this.inputInfo.storeName = null
    },

    // 查询
    demand() {
        this.currpage = 1
        this.current_num = 10
        this.showPagebox = false
        this.loading = true
        this.dictionaryNum = 1
        this.getGoodsExamineInfos().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
            this.showPagebox = true
        }
        })
    },

    // 查看
    View(value) {
      this.$router.push({
        path: '/plug_ins/stores/viewGoods',
        query: {
          id: value.id,
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    // 通过
    Through(value) {
      this.$confirm('确认通过审核并上架此商品吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        goodsExamine({
          api: 'admin.mch.goodsExamine',
          goodsId: value.id,
          status: 1,
        }).then(res => {
          console.log(res);
          if(res.data.code == '200') {
            this.getGoodsExamineInfos()
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
      this.pageSize = e
      this.getGoodsExamineInfos().then(() => {
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
      this.getGoodsExamineInfos().then(() => {
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
            goodsExamine({
              api: 'admin.mch.goodsExamine',
              goodsId: this.id,
              status: 0,
              text: this.ruleForm.reason
            }).then(res => {
              console.log(res);
              if(res.data.code == '200') {
                this.getGoodsExamineInfos()
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

  }
}