import {index, save} from '@/api/mall/sms/smsManage'
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'smsTemplateSave',

  //初始化数据
  data() {
    return {
      mainForm: {
        type: null, category: null, smsTemplateId: null, templateStr: null, content: "",
      },
      smsTypeList: [],
      categoryList: [],

      cate_type:'',

    }
  },
  //组装模板
  created() {
    this.loadData()
    this.rules()
  },

  methods: {
    rules() {
      this.mainForm.rules = {
        type: [{required: true, message: '请选择类型', trigger: 'change'}],
        category: [{required: true, message: '请选择类别', trigger: 'change'}],
        SignName: [{required: true, message: '请输入签名', trigger: 'change'}],
        name: [{required: true, message: '请输入短信模板名称', trigger: 'change'}],
        TemplateCode: [{required: true, message: '请输入短信模板Code', trigger: 'change'}],
        phone: [{required: true, message: '请输入短信接收号码', trigger: 'change'}],
        content: [{required: true, message: '请输入发送内容', trigger: 'change'}],
      }
    },
    async loadData() {
      let id = this.$route.params.id;
      await this.getType();

      if (!isEmpty(id)) {
        const res = await index({api: 'admin.system.getSmsTemplateInfo', id: id}).then(data => {
          data = data.data.data.list[0];
          this.mainForm = data;

          this.mainForm.id = data.id;
          this.mainForm.type = data.type.toString();
          for (let i = 0; i < this.smsTypeList.length; i++) {
            let item = this.smsTypeList[i];
            
            if (item.value === data.type.toString()) {
              index({api: 'admin.system.getSmsTypeList', superName: item.text}).then(obj => {
                obj = obj.data.data;
                this.cate_type = data.type1.toString();
                this.categoryList = obj.list;
                this.smsTemplateList = obj.templateList;
              });
              break;
            }
          }

        });
      }
    },
    change_type(){
      this.mainForm.category = this.cate_type
    },
    //添加/编辑
    async Save(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            save({
              api: 'admin.system.addMessage',
              id: this.mainForm.id,
              SignName: this.mainForm.SignName,
              name: this.mainForm.name,
              code: this.mainForm.TemplateCode,
              content: this.mainForm.content,
              type: this.mainForm.type,
              category: this.mainForm.category,
              phone: this.mainForm.phone,
            }).then(data => {
              if(data.data.code == '200') {
                if (!isEmpty(data)) {
                  this.$message({
                    message: "操作成功",
                    type: 'success',
                    offset: 100
                  })
                  this.$router.go(-1)
                }
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
    //获取类别
    async getType() {
      const res = await index({api: 'admin.system.getSmsTypeList'}).then(data => {
        data = data.data.data;
        this.smsTypeList = data.list[0].value;
        this.categoryList = null;
        this.smsTemplateList = null;
      });
    },
    //获取类型+短信模板
    async getCategory(name) {
      const res = await index({api: 'admin.system.getSmsTypeList', superName: name}).then(data => {
        data = data.data.data;
        this.categoryList = data.list;
        this.mainForm.category = null;
      });
    },


  }

}
