import pageData from "@/api/constant/page";
import {del, save, index} from "@/api/order/comment";
import {isEmpty} from "element-ui/src/utils/util";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'list',
  mixins: [mixinstest],
  //初始化数据
  data() {
    return {
      page: pageData.data(),
      choseDate: [],
      orderNo: null,
      commentTypeList: [{val: 'GOOD', name: '好评'}, {val: 'NOTBAD', name: '中评'}, {val: 'BAD', name: '差评'}],
      dataForm: {
        id: null,
        CommentType: null
      },
      rules: {
        name: [
          {required: true, message: '标签名称不能为空', trigger: 'blur'}
        ]
      },

      dialogVisible: false,
      id: null,
      ruleForm: {
        comment_content: "",
      },
      rules: {
        comment_content: [
          { required: true, message: "请输入回复内容", trigger: "blur" },
        ]
      },
      // table高度
      tableHeight: null
    }
  },
  //组装模板
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
      await this.getList().then()
    },
    // 获取列表
    async getList() {
      const res = await index({
        api: 'admin.order.getCommentsInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        orderType: 'MS',
        orderno: this.dataForm.orderNo,
        type: this.dataForm.CommentType,
        startDate: isEmpty(this.dataForm.choseDate) ? null : this.dataForm.choseDate[0],
        endDate: isEmpty(this.dataForm.choseDate) ? null : this.dataForm.choseDate[1],
      });
      this.total = res.data.data.total
      this.page.tableData = res.data.data.list
      this.page.loading = false
      if (res.data.data.total < 10) {
        this.current_num = this.total
      }
      if (this.total < this.current_num) {
        this.current_num = this.total
      }
    },

    // 重置
    reset() {
      this.dataForm.CommentType = null
      this.dataForm.orderNo = null
      this.dataForm.choseDate = []
    },
    

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.page.loading = true
      this.dictionaryNum = 1
      this.loadData().then(() => {
        this.page.loading = false
        if (this.page.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    // 弹框方法
    dialogShow(value) {
      this.ruleForm.comment_content = ''
      this.id = value.id
      this.dialogVisible = true
    },

    handleClose(done) {
      this.dialogVisible = false
      this.$refs['ruleForm'].clearValidate()
    },

    // 回复
    determine(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            save({
              api: "admin.order.replyComments",
              commentId: this.id,
              commentText: this.ruleForm.comment_content,
            }).then((res) => {
              console.log(res);
              if (res.data.code == "200") {
                this.$message({
                  message: "成功",
                  type: "success",
                  offset: 100,
                });
                this.getList()
                this.dialogVisible = false;
              }
            });
          } catch (error) {

          }
        } else {

        }
      });
    },

    //选择一页多少条
    handleSizeChange(e) {
      this.page.loading = true
      // this.page.current_num = e
      this.pageSize = e
      this.loadData().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.page.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.page.loading = false
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
    Reply(id) {
      this.$router.push({
        name: 'commentReply',
        params: {id: id},
      })
    },
    //修改
    async Edit(id) {
      this.$router.push({
        path: '/plug_ins/seckill/commentEdit',
        query: {
          id: id
        }
      })
    },

    // 删除
    Delete(id) {
      this.$confirm('确定删除此评论吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        del({
          api: 'admin.order.delComments',
          commentId: id
        }).then(res => {
          this.demand();
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }

  }

}
