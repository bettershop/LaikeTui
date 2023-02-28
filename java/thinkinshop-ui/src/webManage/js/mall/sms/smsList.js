import {index, save} from '@/api/mall/sms/smsManage'
import pageData from '@/api/constant/page'
import {isEmpty} from "element-ui/src/utils/util";
import {del} from "@/api/mall/aftersaleAddress/aftersaleAddress";
import {setStorage, getStorage} from "@/utils/storage";
import { mixinstest } from '@/mixins/index'

export default {
  name: 'smsList',
  mixins: [mixinstest],
  //初始化数据
  data() {
    return {
      tabPosition: '1',
      tabPositionKey: 'smsTbl',
      flag: false,
      page: pageData.data(),
      mainForm: {},
    }
  },
  //组装模板
  created() {
    this.loadData()
  },

  methods: {
    rules() {
      this.mainForm.rules = {
        accessKeyId: [{required: true, message: '请输入阿里云API的密钥ID', trigger: 'change'}],
        accessKeySecret: [{required: true, message: '请输入阿里云API的密钥', trigger: 'change'}],
      }
    },
    async loadData() {
      let tbl = getStorage(this.tabPositionKey, this.tabPosition)
      if (!isEmpty(tbl)) {
        this.tabPosition = tbl;
      }
      //默认短信列表
      if (this.tabPosition === '2') {
        //短信模板
        const res = await index({api: 'admin.system.getSmsTemplateInfo'}).then(data => {
          this.total = data.data.data.total
          this.page.tableData = data.data.data.list
          this.page.loading = false
          if (data.data.data.total < 10) {
            this.current_num = this.page.total
          }
          if (this.total < this.current_num) {
            this.current_num = this.total
          }
        });
      } else if (this.tabPosition === '3') {
        //核心设置
        const res = await index({api: 'admin.system.getTemplateConfigInfo'}).then(data => {
          data = data.data.data.data;
          this.mainForm = data;
          this.rules()
        });
      } else {
        const res = await index({api: 'admin.system.getSmsInfo'}).then(data => {
          this.total = data.data.data.total
          this.page.tableData = data.data.data.list
          this.page.loading = false
          if (data.data.data.total < 10) {
            this.current_num = this.total
          }
          if (this.total < this.current_num) {
            this.current_num = this.total
          }
        });
      }

    },
    tbl() {
      setStorage(this.tabPositionKey, this.tabPosition)
      this.page = pageData.data();
      //解决数据源发生变化不重新渲染表格的问题
      this.flag = !this.flag;
      this.loadData();
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
    //选择一页多少条
    handleSizeChange(e) {
      this.page.loading = true
      this.page.pageSize = e
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
    Save(id) {
      let flag = 'smsSave';
      if (!isEmpty(id)) {
        flag = 'smsEdit';
      }
      if (this.tabPosition === '2') {
        flag = 'smsTemplateSave';
        if (!isEmpty(id)) {
          flag = 'smsTemplateEdit';
        }
      }
      this.$router.push({
        name: flag,
        params: {id: id}
      })
    },
    Delete(id) {
      var api = "admin.system.delMessageList";
      if (this.tabPosition === '2') {
        api = "admin.system.delMessage";
      }
      this.$confirm('确定删除此模板吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        del({
          api: api,
          id: id
        }).then(res => {
          console.log(res);
          if(res.data.code = '200' && res.data.data.message !== '该模板正在使用') {
            // if (!isEmpty(res.data)) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            // }
            this.demand();
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    async saveConfig(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            save({
              api: 'admin.system.addTemplateConfig',
              key: this.mainForm.accessKeyId,
              secret: this.mainForm.accessKeySecret,
            }).then(data => {
              if (!isEmpty(data)) {
                this.$message({
                  message: "操作成功",
                  type: 'success',
                  offset: 100
                })
              }
            });
          } catch (e) {
            this.$message({
              message: e.message,
              type: 'error',
              showClose: true
            })
          }
        } else {
          return false;
        }
      });
    },

  }

}
