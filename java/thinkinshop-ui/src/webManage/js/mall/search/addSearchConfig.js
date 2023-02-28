import {index, save} from '@/api/mall/search/searchApi'
import {isEmpty} from "element-ui/src/utils/util";


export default {
  name: 'addSearchConfig',

  data() {
    return {
      dataForm: {
        id: null,
        is_open: false,
        keyword: '',
        mch_keyword: '',
        num: 0
      },
      rules: {
        keyword: [
          {required: true, message: '请输入关键词', trigger: 'blur'}
        ],
        num: [
          {required: true, message: '请输入关键词数量', trigger: 'blur'}
        ]
      },
    }
  },

  created() {
    this.loadData()
  },

  methods: {
    async loadData() {
      const res = await index({
        api: 'admin.system.getSearchConfigIndex'
      });

      if (!isEmpty(res)) {
        let data = res.data.data.data;
        this.dataForm = data;
        this.dataForm.is_open = data.is_open === 1;
      }
    },

    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        // let text = "添加成功";
        // if (!isEmpty(this.dataForm.id)) {
        //   text = "修改成功";
        // }
        if (valid) {
          try {
            save({
              api: "admin.system.addSearchConfig",
              isOpen: this.dataForm.is_open ? 1 : 0,
              limitNum: this.dataForm.num,
              keyword: this.dataForm.keyword
            }).then(res => {
              if (res.data.code == 200) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  offset: 100
                })
              }
              this.loadData()
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
