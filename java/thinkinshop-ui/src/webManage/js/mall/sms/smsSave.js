import {index, save} from '@/api/mall/sms/smsManage'
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'smsSave',

  //初始化数据
  data() {
    return {
      mainForm: {
        type: null, category: null, smsTemplateId: null, templateStr: null, content: "", rules: {
          type: [{required: true, message: '请选择类型', trigger: 'change'}],
          category: [{required: true, message: '请选择类别', trigger: 'change'}],
          smsTemplateId: [{required: true, message: '请选择模板', trigger: 'change'}],
          content: [{required: true, message: '请输入内容', trigger: 'change'}],
        }
      },
      smsTypeList: [],
      categoryList: [],
      smsTemplateList: [],

      smsTemplateType:''

    }
  },
  //组装模板
  created() {
    this.loadData()
  },

  methods: {
    rules() {
      this.mainForm.rules = {
        type: [{required: true, message: '请选择类型', trigger: 'change'}],
        category: [{required: true, message: '请选择类别', trigger: 'change'}]
      }
    },
    change_id(){
       this.mainForm.smsTemplateId = this.smsTemplateType
    },
    async loadData() {
      let id = this.$route.params.id;
      await this.getType();

      if (!isEmpty(id)) {
        const res = await index({api: 'admin.system.getSmsInfo', id: id}).then(data => {
          data = data.data.data.list[0];
          this.mainForm = data;

          this.mainForm.id = data.id;
          this.mainForm.type = data.type.toString();
          for (let i = 0; i < this.smsTypeList.length; i++) {
            let item = this.smsTypeList[i];
            if (item.value === data.type.toString()) {
              index({api: 'admin.system.getSmsTypeList', superName: item.text}).then(obj => {
                obj = obj.data.data;
                this.categoryList = obj.list;
                this.smsTemplateList = obj.templateList;
                this.mainForm.category = data.type1.toString();

                index({api: 'admin.system.getSmsTemplateInfo', id: data.Template_id}).then(obj => {
                  this.mainForm.content = obj.data.data.list[0].content;
                  this.mainForm.smsTemplateId = data.Template_id;
                  this.smsTemplateType = data.Template_id;
                  this.mainForm.content = data.content1;
                  this.smsTemplateList = obj.data.data.list
                });

              });
              break;
            }
          }

        });
      }
    },
    //添加/编辑
    async Save(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            save({
              api: 'admin.system.addMessageList',
              id: this.mainForm.id,
              type: this.mainForm.type,
              category: this.mainForm.category,
              smsTemplateId: this.mainForm.smsTemplateId,
              templateStr: this.mainForm.content,
            }).then(data => {
              if (!isEmpty(data) && data.data.code === "200") {
                this.$message({
                  message: "操作成功",
                  type: 'success',
                  offset: 100
                })
                this.$router.go(-1)
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
        this.getSmsTemplateList();
      });
    },
    //获取短信模板
    async getSmsTemplateList(id) {
      const res = await index({api: 'admin.system.getSmsTemplateList', type: this.mainForm.type, id: id}).then(data => {
        data = data.data.data.templateList;
        this.smsTemplateList = data;
        this.smsTemplateType = data.name;
        this.mainForm.smsTemplateId = data.id;
        this.mainForm.content = null;
      });

    },
    //获取短信模板内容
    async getTemplate(typeId) {
      const res = await index({api: 'admin.system.getSmsTemplateInfo', id: typeId}).then(data => {
        this.mainForm.content = data.data.data.list[0].content;
      });
    },


  }

}
