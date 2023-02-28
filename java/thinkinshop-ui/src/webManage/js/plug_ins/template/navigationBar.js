import { uiIndex, uiSave, uiDel, uiIsShowSwitch, uiMove, uiTop, bannerPathList } from '@/api/plug_ins/template'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'navigationBar',
  mixins: [mixinstest],
  data() {
    return {
      radio1:'UI导航栏',

      tableData: [],
      loading: true,

      // 弹框数据
      dialogVisible: false,
      ruleForm: {
        name: '',
        img: '',
        class1: 0,
        url: '',
        is_display: 1
      },

      title: '',

      navUrl: '',

      classList1: [
        {
          value: 2,
          label: '商品'
        },
        {
          value: 1,
          label: '分类'
        },
        {
          value: 3,
          label: '店铺'
        },
        {
          value: 0,
          label: '自定义'
        }
      ],

      classList2: [],
      baseUrl: '',
      
      rules: {
        reason: [
          { required: true, message: '请输入拒绝理由', trigger: 'blur' }
        ],
      },
      id: null,
      // table高度
      tableHeight: null

    }
  },

  created() {
    this.uiIndexs()
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
    },
    async uiIndexs() {
      const res = await uiIndex({
        api: 'admin.diy.uiIndex',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
      })
      this.current_num = 10
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false
      if(this.total < this.current_num) {
          this.current_num = this.total
      }
      console.log(res);
    },

    change(item) {
      if(item.value !== 0) {
        this.ruleForm.url = ''
        this.bannerPathLists(item.value)
        if(item.value == 2) {
          this.navUrl = '/pages/goods/goodsDetailed'
        } else if(item.value == 1) {
          this.navUrl = '/pages/goods/goods'
        } else {
          this.navUrl = '/pagesA/store/store'
        }
      } else {
        this.classList2 = []
        this.ruleForm.url = ''
      }
      console.log(item);
    },

    // 是否显示
    switchs(value) {
      uiIsShowSwitch({
        api: 'admin.diy.uiIsShowSwitch',
        id: value.id
      }).then(res => {
        if(res.data.code == '200') {
          this.uiIndexs()
          console.log(res);
          this.$message({
            type: 'success',
            message: '成功!',
            offset: 100
          });
        }
      })
    },

    // 移动
    moveUp(value) {
      if(value !== 0) {
        uiMove({
          api: 'admin.diy.uiMove',
          id: this.tableData[value - 1].id,
          id1: this.tableData[value].id
        }).then(res => {
          if(res.data.code == '200') {
            this.uiIndexs()
            console.log(res);
            this.$message({
              type: 'success',
              message: '操作成功!',
              offset: 100
            });
          }
        })
      } else {
        uiMove({
          api: 'admin.diy.uiMove',
          id: this.tableData[value + 1].id,
          id1: this.tableData[value].id
        }).then(res => {
          if(res.data.code == '200') {
            this.uiIndexs()
            console.log(res);
            this.$message({
              type: 'success',
              message: '操作成功!',
              offset: 100
            });
          }
        })
      }
    },

    // 置顶
    placedTop(value) {
      uiTop({
        api: 'admin.diy.uiTop',
        id: value.id
      }).then(res => {
          console.log(res);
          if(res.data.code == '200') {
              this.uiIndexs()
              this.$message({
                  type: 'success',
                  message: '置顶成功!',
                  offset: 100
              })
          }
      })
    },

    // 删除
    Delete(value) {
      this.$confirm('确定要删除此数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        uiDel({
            api: 'admin.diy.uiDel',
            id: value.id
        }).then(res => {
          console.log(res);
          if(res.data.code == '200') {
            this.uiIndexs()
            this.$message({
                type: 'success',
                message: '删除成功!',
                offset: 100
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除',
          offset: 100
        });          
      });
    },

    //选择一页多少条
    handleSizeChange(e){
      this.loading = true
      console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.uiIndexs().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.loading = false
      })
    },

    //点击上一页，下一页
    handleCurrentChange(e){
      this.loading = true
      this.dictionaryNum = e
      this.currpage = ((e - 1) * this.pageSize) + 1
      this.uiIndexs().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
    },

    
    // 弹框方法
    dialogShow(value, flag) {
      this.title = ''
      if(flag) {
        this.title = '添加UI导航栏'
        this.ruleForm.name = '',
        this.ruleForm.img = '',
        this.ruleForm.class1 = 0,
        this.ruleForm.url = '',
        this.ruleForm.is_display = 1
      } else {
        console.log(value);
        if(value.type !== 0) {
          this.bannerPathLists(value.type)
        }
        this.title = '编辑UI导航栏'
        this.ruleForm.name = value.name
        this.ruleForm.img = value.image
        this.ruleForm.class1 = value.type
        
        if(value.type == 0) {
          this.ruleForm.url = value.url.split('?')[1]
        } else {
          this.ruleForm.url = value.url.split('?')[1]
        }
        this.ruleForm.is_display = value.isshow
        console.log(value);
      }
      this.dialogVisible = true
      if(value) {
        this.id = value.id
      }
    },

    handleAvatarSuccess(res, file) {
      console.log(res);
      this.ruleForm.img = res.data.imgUrls[0]
    },

    removeImgs() {
      this.ruleForm.img = ''
    },

    async bannerPathLists(value) {
      const res = await bannerPathList({
          api: 'admin.diy.bannerPathList',
          type: value
      })
      console.log(res);
      this.classList2 = res.data.data.list
    },
  
    handleClose(done) {
      this.dialogVisible = false
      this.$refs.upload.fileList = []
      this.id = null
      this.$refs['ruleForm'].clearValidate()
      
    },

    submitForm2() {
      if(this.title === '添加UI导航栏') {
        uiSave({
          api: 'admin.diy.uiSave',
          name: this.ruleForm.name,
          picUrl: this.ruleForm.img,
          type0: this.ruleForm.class1,
          url: this.ruleForm.class1 !== 0 ? this.navUrl + '?' + this.ruleForm.url : this.ruleForm.url,
          isShow: this.ruleForm.is_display
        }).then(res => {
          if(res.data.code == '200') {
            this.uiIndexs()
            console.log(res);
            this.$message({
              type: 'success',
              message: '添加成功!',
              offset: 100
            });
            
            this.dialogVisible = false
            this.$refs.upload.fileList = []
          }
        })
      } else {
        uiSave({
          api: 'admin.diy.uiSave',
          name: this.ruleForm.name,
          picUrl: this.ruleForm.img,
          type0: this.ruleForm.class1,
          url: this.ruleForm.class1 !== 0 ? this.navUrl + '?' + this.ruleForm.url : this.ruleForm.url,
          isShow: this.ruleForm.is_display,
          id: this.id
        }).then(res => {
          if(res.data.code == '200') {
            this.uiIndexs()
            console.log(res);
            this.$message({
              type: 'success',
              message: '编辑成功!',
              offset: 100
            });

            this.dialogVisible = false
          }
        })
      }
    },

    getUrl() {

    }
  }
}