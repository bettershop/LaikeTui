import { getTaoBaoWorkeList, delTaoBaoTask, executeTaoBaoById, addTaoBaoTask, getTaoBaoWorkeListDetail } from '@/api/goods/taobaoAssistant'
import { choiceClass } from '@/api/goods/goodsList'
import { mixinstest } from '@/mixins/index' 
export default {
  name: 'taskList',

  mixins: [mixinstest],
  data() {
    return {
      tableData: [],
      loading: true,
      is_disabled: true,
      idList: [],
      inputInfo: {
        status: '',
        name: '',
      },
      Dictionary: [
        { 
          value: '0',
          label: '待执行'
        },
        { 
          value: '1',
          label: '执行中'
        },
        { 
          value: '2',
          label: '执行完成'
        }
      ],

      freight:null,

      // 弹框数据
      dialogVisible: false,
      ruleForm: {
        goodsClass: '',
        goodsBrand: '',
        taskName: '',
        linkId: ''
      },
      rules: {
        taskName: [
          {required: true, message: '请输入任务名称', trigger: 'blur'}
        ],
        goodsClass: [
          {required: true, message: '请输入商品分类', trigger: 'blur'}
        ],
        goodsBrand: [
          {required: true, message: '请输入商品品牌', trigger: 'blur'}
        ],
        linkId: [
          {required: true, message: '请输入宝贝链接', trigger: 'blur'}
        ],
      },

      // 商品分类列表
      classList: [],

      // 商品品牌列表
      brandList: [],

      attributeList: [],

      id: '',

      tag: false,

      // table高度
      tableHeight: null

    }
  },

  created() {
    this.getTaoBaoWorkeLists()
    this.choiceClasss()    
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
      console.log(this.$refs.tableFather.clientHeight);
		},

    async getTaoBaoWorkeLists() {
      const res = await getTaoBaoWorkeList({
        api: 'admin.goods.getTaoBaoWorkeList',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        status: this.inputInfo.status !== '' ? parseInt(this.inputInfo.status) : '',
          taskName: this.inputInfo.name
      })
      console.log(res);
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false
    },

    reset() {
      this.inputInfo.status = ''
      this.inputInfo.name = ''
    },

    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getTaoBaoWorkeLists().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    //选择一页多少条
    handleSizeChange(e){
        this.loading = true
        console.log(e);
        // this.current_num = e
        this.pageSize = e
        this.getTaoBaoWorkeLists().then(() => {
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
      this.getTaoBaoWorkeLists().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })  
	},

    async Edit(value) {
        this.dialogVisible = true
        await getTaoBaoWorkeListDetail({
            api: 'admin.goods.getTaoBaoWorkeListDetail',
            wid: value.id
        }).then(res => {
            this.allClass(this.classList)
            console.log(res);
            let item = res.data.data.list[0]
            this.id = item.w_id
            this.ruleForm.taskName = item.title
            this.ruleForm.goodsClass = item.cid.split('-').filter(item => {
                if(item !== '') {
                    return item
                }
            }).map(item => {
                return parseInt(item)
            })
            this.ruleForm.goodsBrand = item.brand_id
            let list = []
            res.data.data.list.forEach(item => {
                list.push(item.itemid)
            })
            if(list.length < 2 ) {
                this.ruleForm.linkId = list[0]
            } else {
                this.attributeList = []
                this.ruleForm.linkId = list.shift()
                list.forEach((item,index) => {
                    this.attributeList[index] = item
                })
            }
            choiceClass({
                api: 'admin.goods.choiceClass',
                classId: this.ruleForm.goodsClass[this.ruleForm.goodsClass.length - 1]
            }).then(res => {
                this.brandList = res.data.data.list.brand_list.splice(-1,1)
            })

        })
    },

    // 立即执行
    async immediately(value) {
        await executeTaoBaoById({
            api: 'admin.goods.executeTaoBaoById',
            wids: value.id
        }).then(res => {
          console.log(res);
          this.getTaoBaoWorkeLists()
          this.$message({
              type: 'success',
              message: '执行成功!',
              offset: 100
          });
            // if(res.data.code == '200') {
                
            // }
        })
    },

    details(value) {
      this.$router.push({
        path: '/goods/taobaoAssistant/viewTask',
        query: {
          id: value.id
        }
      })
    },

    // 选框改变
    handleSelectionChange(val) {
        if(val.length==0){
            this.is_disabled = true
        }else{
            this.is_disabled = false
        }
      console.log(val);
      this.idList = val.map(item => {
        return item.id
      })
      this.idList = this.idList.join(',')
    },

    // 批量删除
    delAll() {
      this.$confirm('确定删除已选任务？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delTaoBaoTask({
            api: 'admin.goods.delTaoBaoTask',
            wids: this.idList
        }).then(res => {
            console.log(res);
            if(res.data.code == '200') {
                this.getTaoBaoWorkeLists()
                this.$message({
                    type: 'success',
                    message: '删除成功!',
                    offset: 100
                });
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

    // 批量执行
    performAll() {
      executeTaoBaoById({
          api: 'admin.goods.executeTaoBaoById',
          wids: this.idList
      }).then(res => {
          if(res.data.code == '200' && res.data.data) {
            this.getTaoBaoWorkeLists()
            this.$message({
              type: 'success',
              message: '执行成功!',
              offset: 100
            });
          }
      })
    },

    // 弹框方法
    dialogShow() {
      this.dialogVisible = true
      this.ruleForm.goodsClass = ''
      this.ruleForm.goodsBrand = ''
      this.ruleForm.taskName = ''
      this.ruleForm.linkId = ''
      this.attributeList = []
    },
    
    handleClose(done) {
      this.dialogVisible = false
      this.$refs['ruleForm'].clearValidate()
    },

    addOne() {
      this.attributeList.push(
          ''
      )
    },

    minus() {
      if(this.attributeList.length !== 0) {
          this.attributeList.pop()
      }
    },

    addOnes() {
      this.attributeList.push(
          ''
      )
    },

    // 获取商品类别
    async choiceClasss() {
      const res = await choiceClass({
        api: 'admin.goods.choiceClass',
      })
      res.data.data.list.class_list[0].forEach((item, index) => {
        let obj = item
        this.classList.push({
          value: obj.cid,
          label: obj.pname,
          index: index,
          children: []
        })
      })
    },

    // 递归加载所有分类
    async allClass(value) {
        for(let i = 0; i < value.length -1; i++) {
          choiceClass({
            api: 'admin.goods.choiceClass',
            classId: value[i].value
          }).then(res => {
            if(res.data.data.list.class_list.length !== 0) {
              res.data.data.list.class_list[0].forEach((item, index) => {
                let obj = item
                value[i].children.push({
                  value: obj.cid,
                  label: obj.pname,
                  index: index,
                  children: []
                })
              })
  
              this.allClass(value[i].children)
            }
          })
        }
    },

    // 根据商品类别id获取商品品牌
    changeProvinceCity(value) {
      choiceClass({
        api: 'admin.goods.choiceClass',
        classId: value.length > 1 ? value[value.length - 1] : value[0]
      }).then(res => {
        let num = this.$refs.myCascader.getCheckedNodes()[0].data.index
        this.brandList = res.data.data.list.brand_list
        if(res.data.data.list.class_list[0].length !== 0) {
          this.$refs.myCascader.getCheckedNodes()[0].data.children = []
          res.data.data.list.class_list[0].forEach((item, index) => {
            let obj = item
            this.$refs.myCascader.getCheckedNodes()[0].data.children.push({
              value: obj.cid,
              label: obj.pname,
              index: index,
              level: obj.level,
              children: []
            })
          })
        }
      })
    },

    // 添加/编辑任务
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            if(this.attributeList.length === 0) {
                addTaoBaoTask({
                    api: 'admin.goods.addTaoBaoTask',
                    itemId: this.attributeList.length == 0 ? this.ruleForm.linkId : this.ruleForm.linkId + ',' + this.attributeList.join(','),
                    taskName: this.ruleForm.taskName,
                    classId: this.ruleForm.goodsClass[this.ruleForm.goodsClass.length - 1],
                    brandId: this.ruleForm.goodsBrand
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                      this.getTaoBaoWorkeLists()
                      this.dialogVisible = false
                      this.$message({
                          type: 'success',
                          message: '添加成功!',
                          offset: 100
                      });
                    }
                })
            } else {
                addTaoBaoTask({
                    api: 'admin.goods.addTaoBaoTask',
                    itemId: this.ruleForm.linkId + ',' + this.attributeList.join(','),
                    taskName: this.ruleForm.taskName,
                    classId: this.ruleForm.goodsClass[this.ruleForm.goodsClass.length - 1],
                    brandId: this.ruleForm.goodsBrand,
                    taskWorkId: this.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getTaoBaoWorkeLists()
                        this.$message({
                            type: 'success',
                            message: '成功!',
                            offset: 100
                        });
                        this.dialogVisible = false
                    }
                })
            }
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