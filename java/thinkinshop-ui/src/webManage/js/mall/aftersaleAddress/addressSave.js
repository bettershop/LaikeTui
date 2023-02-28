import cascade from '@/api/publics/cascade'
import {index, save} from '@/api/mall/aftersaleAddress/aftersaleAddress'
import {isEmpty} from "element-ui/src/utils/util";


export default {
  name: 'addressSave',

  data() {
    return {
      ruleForm: {
        name: '',
        tel: '',
        sheng: '',
        shi: '',
        xian: '',
        address: '',
        is_default: '1',
        code: ''
      },
      rules: {
        name: [
          {required: true, message: '请填写发货人', trigger: 'blur'}
        ],
        tel: [
          {required: true, message: '请填写联系电话', trigger: 'blur'}
        ],
        sheng: [
          {required: true, message: '请选择所在省份', trigger: 'change'}
        ],
        shi: [
          {required: true, message: '请选择所在市区', trigger: 'change'}
        ],
        xian: [
          {required: true, message: '请选择所在县', trigger: 'change'}
        ],
        address: [
          {required: true, message: '请输入详细地址', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入邮政编码', trigger: 'blur'}
        ]
      },
      //省市级联集
      shengList: {},
      shiList: {},
      xianList: {}
    }
  },

  created() {
    this.getSheng()
    this.loadData(this.$route.params.id)
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'list' && this.$route.name == 'edit') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },


  methods: {
    async loadData(id) {
      if (!isEmpty(id)) {
        const res = await index({
          api: 'admin.system.getAddressInfo',
          type: 2,
          id: id
        });
        if (!isEmpty(res)) {
          let data = res.data.data.list[0];
          this.ruleForm = data;
          data.is_default = data.is_default.toString();
          await this.cascadeAddress();
        }
      }
    },
    // 获取省级
    async getSheng() {
      const res = await cascade.getSheng()
      this.shengList = res.data.data
    },
    // 获取市级
    async getShi(sid, flag) {
      const res = await cascade.getShi(sid)
      this.shiList = res.data.data
      if (!flag) {
        this.ruleForm.shi = "";
        this.ruleForm.xian = "";
      }
    },
    // 获取县级
    async getXian(sid, flag) {
      const res = await cascade.getXian(sid)
      this.xianList = res.data.data
      if (!flag) {
        this.ruleForm.xian = "";
      }
    },
    //省市级联回显
    async cascadeAddress() {
      //省市级联
      for (const sheng of this.shengList) {
        if (sheng.g_CName === this.ruleForm.sheng) {
          await this.getShi(sheng.groupID, true);
          for (const shi of this.shiList) {
            if (shi.g_CName === this.ruleForm.shi) {
              await this.getXian(shi.groupID, true);
              break;
            }
          }
          break;
        }
      }
    },

    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        let text = "添加成功";
        if (!isEmpty(this.ruleForm.id)) {
          text = "修改成功";
        }
        if (valid) {
          try {
            save({
              api: "admin.system.addAddressInfo",
              id: this.ruleForm.id,
              name: this.ruleForm.name,
              tel: this.ruleForm.tel,
              type: 2,
              code: this.ruleForm.code,
              address: this.ruleForm.address,
              shen: this.ruleForm.sheng,
              shi: this.ruleForm.shi,
              xian: this.ruleForm.xian,
              isDefault: this.ruleForm.is_default
            }).then(res => {
              if (res.data.code == '200' && !isEmpty(res)) {
                this.$message({
                  message: text,
                  type: 'success',
                  offset: 100
                })
                this.$router.go(-1)
              }
            })
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
