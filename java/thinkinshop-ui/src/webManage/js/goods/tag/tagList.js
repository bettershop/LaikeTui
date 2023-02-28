import pageData from "@/api/constant/page";
import {del, save, index} from "@/api/goods/tag/tagList";
import {isEmpty} from "element-ui/src/utils/util";
import { valid } from "mockjs";

export default {
  name: 'tagList',
  //初始化数据
  data() {
    return {
      page: pageData.data(),
      dialogVisible: false,
      title: '',
      dataForm: {
        id: null, 
        name: ''
      },
      rules: {
        name: [
          {required: true, message: '标签名称不能为空', trigger: 'blur'}
        ]
      },
      // table高度
      tableHeight: null,
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
      console.log(this.$refs.tableFather.clientHeight);
		},
    async loadData() {
      await this.getList().then()
    },
    // 获取列表
    async getList() {
      const res = await index({
        api: 'admin.label.index',
        pageNo: this.page.dictionaryNum,
        pageSize: this.page.pageSize,
        name: this.page.inputInfo.name,
        type: 2
      });
      this.page.total = res.data.data.total
      this.page.tableData = res.data.data.list
      this.page.loading = false
      if (res.data.data.total < 10) {
        this.page.current_num = this.page.total
      }
      if (this.page.total < this.page.current_num) {
        this.page.current_num = this.page.total
      }
    },

    dialogShow() {
      this.dialogVisible = true
      this.dataForm = []
      this.title = '添加标签';
    },

    // 重置
    reset() {
      this.page.inputInfo.name = ''
    },
    handleClose() {
      this.dataForm.name = ''
      this.dialogVisible = false;
    },

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.page.showPagebox = false
      this.page.loading = true
      this.page.dictionaryNum = 1
      this.loadData().then(() => {
        this.page.loading = false
        if (this.page.tableData.length > 5) {
          this.page.showPagebox = true
        }
      })
    },

    //选择一页多少条
    handleSizeChange(e) {
      this.page.loading = true
      // this.page.current_num = e
      this.page.pageSize = e
      this.loadData().then(() => {
        this.page.currpage = ((this.page.dictionaryNum - 1) * this.page.pageSize) + 1
        this.page.current_num = this.page.tableData.length === this.page.pageSize ? this.page.dictionaryNum * this.page.pageSize : this.page.total
        this.page.loading = false
      })
    },

    //点击上一页，下一页
    handleCurrentChange(e) {
      this.page.loading = true
      this.page.dictionaryNum = e
      this.page.currpage = ((e - 1) * this.page.pageSize) + 1
      this.loadData().then(() => {
        this.page.current_num = this.page.tableData.length === this.page.pageSize ? e * this.page.pageSize : this.page.total
        this.page.loading = false
      })

    },
    // 下载文件
    async Show(id) {
      if (!isEmpty(id)) {
        this.title = '修改标签';
      }
      this.dialogVisible = true;
      const res = await index({
        api: 'admin.label.index',
        id: id
      });
      let data = res.data.data.list[0];
      if (!isEmpty(data)) {
        this.dataForm.id = data.id;
        this.dataForm.name = data.name;
      }
    },
    //添加/编辑
    async Save(dataForm) {
      this.$refs[dataForm].validate(async (valid) => {
        if(!valid) {
          this.$message({
            type: 'error',
            message: '标签名称为空，请输入标签名'
          })
        } else {
          const res = await save({
            api: 'admin.label.addGoodsLabel',
            name: this.dataForm.name,
            id: this.dataForm.id
          });
          
          if(res.data.code == '200') {
            if (!isEmpty(res)) {
              this.$message({
                type: 'success',
                message: this.title + '成功!'
              })
              this.dataForm.name = ''
            } else {
              this.$message({
                type: 'error',
                message: this.title + '失败!'
              })
            }
            this.dialogVisible = false;
            this.demand();
          }
          
      
        }  
      })
    },

    // 删除
    Delete(id) {
      this.$confirm('确定删除此标签吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        del({
          api: 'admin.label.delGoodsLabel',
          type: 2,
          id: id
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
