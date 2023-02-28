import {index, save} from '@/api/Platform/system'
import {isEmpty} from "element-ui/src/utils/util";

export default {
  name: 'systemConfig',

  //初始化数据
  data() {
    return {
      formMain: {},
    }
  },
  //组装模板
  created() {
    this.loadData()
  },

  methods: {
    async loadData() {
      const res = await index({
        api: 'admin.system.getSetSystem',
        storeId: 0

      }).then(data => {
        let obj = data.data.data.config;
        console.log(obj)
        if (isEmpty(obj)) {
          obj = {id: null, logoUrl: "", copyrightInformation: null, recordInformation: null, linkPage: null};
        }
        if (!isEmpty(obj.link_to_landing_page)) {
          obj.linkPage = JSON.parse(obj.link_to_landing_page);
        } else {
          obj.linkPage = [{name: null, url: null}];
        }
        obj.rules = {
          logo: [{required: true, message: '请选择Logo', trigger: 'change'}],
          copyright_information: [{required: true, message: '请输版权信息', trigger: 'change'}],
          record_information: [{required: true, message: '请输备案信息', trigger: 'change'}],
        }
        this.formMain = obj;
      });
    },
    addUrl() {
      this.formMain.linkPage.push({})
    },
    delUrl(index) {
      this.formMain.linkPage.splice(index, 1)
    },
    //添加/编辑
    async Save(formName) {
      let text = "修改";
      if (isEmpty(this.formMain.id)) {
        text = "添加";
      }

      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          const res = await index({
            api: 'admin.system.setSystem',
            id: this.formMain.id,
            logoUrl: this.formMain.logo,
            copyrightInformation: this.formMain.copyright_information,
            recordInformation: this.formMain.record_information,
            linkPageJson: encodeURIComponent(JSON.stringify(this.formMain.linkPage)),
            storeId: 0
          }).then(data => {
            if (data.data.code == '200' && !isEmpty(data)) {
              this.$message({
                message: text + "成功",
                type: 'success',
                offset: 100
              })
            }
          })
        } else {
          return false;
        }
      })

    },

  }

}
