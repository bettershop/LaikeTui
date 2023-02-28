import {VueEditor} from 'vue2-editor'
import pageData from '@/api/constant/page'
import {index, save} from '@/api/mall/terminalConfig'
import {isEmpty} from "element-ui/src/utils/util";
import fa from "element-ui/src/locale/lang/fa";
import {del} from "@/api/mall/aftersaleAddress/aftersaleAddress";


export default {
  name: 'addressSave',

  data() {
    return {
      page: pageData.data(),
      tabPosition: "2",
      type: 0,
      dialogVisible: false,
      isAddGuidBox: false,
      appForm: {editions: "123"},
      weiXinForm: {},
      //引导图
      guidList: [],
      guidTotal: 0,
      //编辑引导图参数
      guidForm: {},
      //添加引导图参数
      guidFormAdd: {type: "0", image: null, sort: null},
      rules: {
        appname: [{required: true, message: '请输入App名称', trigger: 'blur'}],
        editions: [{required: true, message: '请输入版本号', trigger: 'blur'}],
        android_url: [{required: true, message: '请输入安卓下载地址', trigger: 'blur'}],
        ios_url: [{required: true, message: '请输入IOS下载地址', trigger: 'blur'}],
        edition: [{required: true, message: '请输入APP域名', trigger: 'blur'}],
        image: [{required: true, message: '请上传引导图', trigger: 'change'}],
        appId: [{required: true, message: '请输入小程序ID', trigger: 'blur'}],
        appSecret: [{required: true, message: '请输入小程序密钥', trigger: 'blur'}],
      },
      wxRules: {
        appId: [{required: true, message: '请输入小程序ID', trigger: 'blur'}],
        appSecret: [{required: true, message: '请输入小程序密钥', trigger: 'blur'}],
      },
    }
  },
  components: {
    VueEditor
  },
  watch:{
    'guidFormAdd.image': {
      handler:function() {
        if(this.guidFormAdd.image != null) {
          this.$refs['ruleForm2'].clearValidate();
        }
      }
    },
  },
  created() {
    this.loadData()
  },

  methods: {

    async loadData() {
      const res = await index({
        api: 'admin.terminal.index',
        type: this.tabPosition,
        storeType: this.tabPosition
      });
      let guidList = null;
      if (!isEmpty(res)) {
        let data = res.data.data;
        guidList = data.guide_list;
        this.guidTotal = data.guide_total;

        if (this.tabPosition === '2') {
          //版本号
          data.appInfo.editions = data.appInfo.edition;
          //域名
          data.appInfo.edition = data.edition;
          // data.appInfo.type = data.appInfo.type === 1;
          this.appForm = data.appInfo;
          console.dir(this.appForm.type)
        } else {
          //小程序
          data.weiXinInfo.edition = data.edition;
          data.weiXinInfo.appId = data.appId;
          data.weiXinInfo.appSecret = data.appSecret;
          data.weiXinInfo.Hide_your_wallet = data.Hide_your_wallet === 1;
          this.weiXinForm = data.weiXinInfo
        }
      }
      this.page.total = this.guidTotal
      this.page.tableData = guidList
      this.page.loading = false
      if (this.page.tableData < 10) {
        this.page.current_num = this.page.total
      }
      if (this.page.total < this.page.current_num) {
        this.page.current_num = this.page.total
      }
      this.page.showPagebox = true;
    },
    tbl(tblId) {
      this.loadData();
      if(this.tabPosition == '1') {
        this.type = 0
      } else {
        this.type = 1
      }
    },
    //选择一页多少条
    handleSizeChange(e) {
      this.page.loading = true
      // this.page.current_num = e
      this.page.pageSize = e
      this.getBrandInfos().then(() => {
        this.page.currpage = ((this.page.dictionaryNum - 1) * this.page.pageSize) + 1
        this.page.current_num = this.page.tableData.length === this.page.pageSize ? this.page.dictionaryNum * this.page.pageSize : this.page.total
        this.page.loading = false
      })
    },
    //改变type 的值
    change_type(){
      this.appForm.type == false ? 1 : 0
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
    //点击上一页，下一页
    handleCurrentChange(e) {
      this.page.loading = true
      this.page.dictionaryNum = e
      this.page.currpage = ((e - 1) * this.page.pageSize) + 1
      this.getBrandInfos().then(() => {
        this.page.current_num = this.page.tableData.length === this.page.pageSize ? e * this.page.pageSize : this.page.total
        this.page.loading = false
      })

    },
    showisAddGuidBox(){
      this.isAddGuidBox = true
    },

    exgNumber(){
      this.guidFormAdd.sort = Number(this.guidFormAdd.sort)
      if(this.guidFormAdd.sort != this.guidFormAdd.sort.toFixed(0)){
        this.$message({
          message:'排序号不能为小数',
          type:'warning',
        })
        this.guidFormAdd.sort = this.guidFormAdd.sort.toFixed(0)
      }
    },

    //图片上传
    handleImageAdded() {

    },
    Delete(id) {
      this.$confirm('删除此引导图?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        del({
          api: 'admin.weixinApp.delWeiXinGuideImage',
          type: this.tabPosition,
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
    },
    //加载引导图信息
    async loadGuid(id) {
      this.dialogVisible = true
      const res = await index({
        api: 'admin.weixinApp.getWeiXinGuideImageInfo',
        id: id,
        storeType: this.tabPosition
      });
      
      console.log(res)
      let data = res.data.data;
      this.guidForm = data.list[0];
      this.guidForm.type = this.guidForm.type.toString();
    },
    //保存引导图
    async saveGuid(id,ruleForm) {
      this.guidFormAdd.sort = Number(this.guidFormAdd.sort)
      if(this.guidFormAdd.sort != this.guidFormAdd.sort.toFixed(0)){
        this.$message({
          message:'排序号不能为小数',
          type:'warning',
        })
        this.guidFormAdd.sort = this.guidFormAdd.sort.toFixed(0)
      }
      this.$refs[ruleForm].validate(valid =>{
        if(valid){
          try{
            if (!isEmpty(id)) {
              const res = save({
                api: 'admin.weixinApp.addWeiXinGuideImage',
                id: id,
                type: parseInt(this.guidForm.type),
                sort: this.guidForm.sort,
                imgUrl: this.guidForm.image,
                storeType: this.tabPosition
              });
    
              res.then(ress => {
                console.dir(ress)
                if(ress.data.code == 200){
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    offset: 100
                  })
                  this.loadData()
                  this.dialogVisible = false
                  this.guidFormAdd = {type: "0", image: null, sort: null}
                  this.$refs.upload.fileList = []
                }
              })
            } else {
              const res = save({
                api: 'admin.weixinApp.addWeiXinGuideImage',
                type: parseInt(this.guidFormAdd.type),
                sort: parseInt(this.guidFormAdd.sort),
                imgUrl: this.guidFormAdd.image,
                storeType: this.tabPosition
              });
              res.then(ress => {
                console.dir(ress)
                if(ress.data.code == 200){
                  this.$message({
                    message: '添加成功',
                    type: 'success',
                    offset: 100
                  })
                  this.loadData()
                  this.isAddGuidBox = false
                  this.guidFormAdd = {type: "0", image: null, sort: null}
                  this.$refs.upload.fileList = []
                }
              })
            }   
          }catch(err){
            this.$message({
              message: '脑残吗',
              type: 'error',
              offset: 100
            })
          }
        }
      })
        
    },
    close2() {
      this.isAddGuidBox = false;
      this.guidFormAdd = {type: "0", image: null, sort: null}
      this.$refs.upload.fileList = []
      this.$refs['ruleForm2'].clearValidate();
    },
    close() {
      this.dialogVisible = false;
      this.guidForm = {image: null};
      this.$refs['ruleForm2'].clearValidate();
    },
    cancel(){
      this.$refs['ruleForm2'].clearValidate();
      this.$refs.upload.fileList = []
      this.isAddGuidBox = false
      this.dialogVisible = false
    },
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        let text = "修改成功";
        let param;
        
        if (this.tabPosition === '2') {
          param = {
            api: 'admin.terminal.saveApp',
            id: this.appForm.id,
            appName: this.appForm.appname,
            iosUrl: this.appForm.ios_url,
            androidUrl: this.appForm.android_url,
            type: this.appForm.type,
            content: this.appForm.content,
            appDomainName: this.appForm.edition,
            edition: this.appForm.editions,
            storeType: this.tabPosition
          }
        } else {
          param = {
            api: 'admin.terminal.saveWeiXinApp',
            appId: this.weiXinForm.appId,
            appSecret: this.weiXinForm.appSecret,
            paySuccess: this.weiXinForm.pay_success,
            delivery: this.weiXinForm.delivery,
            refund_res: this.weiXinForm.refund_res,
            hideWallet: this.weiXinForm.Hide_your_wallet ? 1 : 0,
            storeType: this.tabPosition
          }
        }
        if (valid) {
          try {
            save(param).then(res => {
              if (!isEmpty(res)) {
                this.$message({
                  message: text,
                  type: 'success',
                  offset: 100
                })
                this.demand();
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
