import {save, index, del} from "@/api/authority/authorityManage";
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'addAdminUser',
  //初始化数据
  data() {
    return {
      dataMain: {passwordOld: null, roleId: null, passwordNew: null},
      roleList: {},
      customer_number: null,
      id: null,
      rules: {
        name: [{required: true, message: '请输入管理员账号', trigger: 'blur'}],
        password: [{required: true, message: '请输入管理员密码', trigger: 'blur'}],
        passwordOld: [{required: true, message: '请再次输入管理员密码', trigger: 'blur'}],
        roleId: [{required: true, message: '请选择角色', trigger: 'change'}],
      },
    }
  },
  //组装模板
  created() {
    this.loadData();
  },
  beforeRouteLeave (to, from, next) {        
    if (to.name == 'adminUserList' && this.$route.name == 'editAdminUser') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },
  mounted() {
  },
  methods: {
    async loadData() {
      this.id = this.$route.params.id;
      this.customer_number = this.$route.params.customer_number;
      if (!isEmpty(this.id)) {
        //加载管理员信息
        await index({
          api: 'admin.role.getAdminInfo',
          id: this.id,
        }).then(data => {
          if (!isEmpty(data)) {
            data = data.data.data;
            this.customer_number = data.customer_number;
            let main = data.list[0];
            main.password = null
            main.roleId = Number(main.role);
            this.dataMain = data.list[0];
          }
        });
      }
      //加载角色下拉
      await index({
        api: 'admin.role.getUserRoles',
      }).then(data => {
        if (!isEmpty(data)) {
          data = data.data.data.roleList;
          this.roleList = data;
        }
      });

    },
    async Save(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            if (isEmpty(this.id)) {
              if (this.dataMain.password !== this.dataMain.passwordOld) {
                this.$message({
                  message: "两次密码输入不一致",
                  type: 'error',
                  offset: 100
                })
                return
              }
            }
            if (isEmpty(this.dataMain.roleId)) {
              this.$message({
                message: "请选择角色",
                type: 'error',
                offset: 100
              })
              return
            }
            let text = "添加成功";
            if (!isEmpty(this.id)) {
              text = "修改成功";
            }
            await save({
              api: 'admin.role.addAdminInfo',
              id: this.id,
              adminName: this.dataMain.name,
              roleId: this.dataMain.roleId,
              adminPWD: this.dataMain.password ? this.dataMain.password : this.dataMain.passwordNew,
            }).then(data => {
              if(data.data.code == '200' && !isEmpty(data)) {
                this.$message({
                  message: text,
                  type: 'success',
                  offset: 100
                })
                this.$router.go(-1);
              }
            });
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
      
    }


  }

}
