import {index, save} from '@/api/Platform/logisticsCompanyManage'
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'logisticsCompanySave',

  //初始化数据
  data() {
    return {
      formMain: {id: null},
      rules: {
        kuaidi_name: [{required: true, message: '请输入物流公司名称', trigger: 'blur'}],
        type: [{required: true, message: '请输入编码', trigger: 'blur'}],
        sort: [{required: true, message: '请输入序号', trigger: 'blur'}],
      },
    }
  },
  //组装模板
  created() {
    this.id = this.$route.params.id;
    if (!isEmpty(this.id)) {
      this.loadData(this.id)
    }
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'logisticsCompanyList' && this.$route.name == 'logisticsCompanyEdit') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },

  methods: {
    async loadData(id) {
      const res = await index({
        api: 'admin.express.index',
        id: id,
      }).then(data => {
        let obj = data.data.data.list[0];
        obj.is_open = obj.is_open === 1;
        this.formMain = data.data.data.list[0];
      });
    },
    //添加/编辑
    async Save(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            let text = "修改";
            if (isEmpty(this.formMain.id)) {
              text = "添加";
            }
            const res = await index({
              api: 'admin.express.expressSave',
              id: this.formMain.id,
              sort: this.formMain.sort,
              name: this.formMain.kuaidi_name,
              code: this.formMain.type,
              switchse: this.formMain.is_open ? 1 : 2,
            }).then(data => {
              if (!isEmpty(data) && data.data.code == '200') {
                this.$message({
                  message: text + "成功",
                  type: 'success',
                  offset: 100
                })
                this.$router.go(-1)
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
