import { labelIndex, labelSwitch, sortMove, labelTop, delLabel, addLabel } from '@/api/plug_ins/seckill'
import { mixinstest } from '@/mixins/index'

export default {
    name: 'seckillLabel',
    mixins: [mixinstest],

    data() {
        return {
            radio1:'1',

            tableData: [],

            // 弹框数据
            dialogVisible: false,
            title: '',
            id: null,
            ruleForm: {
                name: "",
                title: "",
            },
            rules: {
                name: [
                  { required: true, message: "请输入标签名称", trigger: "blur" },
                ]
            },

            previousId: null,

            loading: true,
            // table高度
            tableHeight: null
        }
    },

    created() {
        this.labelIndexs()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    methods: {
        async labelIndexs() {
            const res = await labelIndex({
                api: 'admin.sec.label.index',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
            })
            console.log(res);
            this.current_num = 10
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
        },

        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},

        // 是否显示
        switchs(value) {
            labelSwitch({
                api: 'admin.sec.label.labelSwitch',
                id: value.id
            }).then(res => {
                if(res.data.code == '200') {
                    this.$message({
                        type: 'success',
                        message: '成功!',
                        offset: 100
                    })
                }
                this.labelIndexs()
            })
        },

        // 移动
        moveUp(value) {
            if(value == 0 && this.currpage == 1) {
                sortMove({
                    api: 'admin.sec.label.sortMove',
                    moveId: this.tableData[value + 1].id,
                    moveId1: this.tableData[value].id
                }).then(res => {
                    if(res.data.code == '200') {
                        this.$message({
                            type: 'success',
                            message: '操作成功!',
                            offset: 100
                        });
                    }
                    this.labelIndexs()
                })
            } else {
                if(value == 0) {
                    sortMove({
                        api: 'admin.sec.label.sortMove',
                        moveId: this.tableData[value].id,
                        moveId1: this.previousId
                    }).then(res => {
                        if(res.data.code == '200') {
                            console.log(res);
                            this.$message({
                                type: 'success',
                                message: '操作成功!',
                                offset: 100
                            });
                        }
                        this.labelIndexs()
                    })
                } else {
                    sortMove({
                        api: 'admin.sec.label.sortMove',
                        moveId: this.tableData[value - 1].id,
                        moveId1: this.tableData[value].id
                    }).then(res => {
                        if(res.data.code == '200') {
                            console.log(res);
                            this.$message({
                                type: 'success',
                                message: '操作成功!',
                                offset: 100
                            });
                        }
                        this.labelIndexs()
                    })
                }
            }
        },
  
        // 置顶
        placedTop(value) {
            labelTop({
                api: 'admin.sec.label.top',
                id: value.id
            }).then(res => {
                console.log(res);
                if(res.data.code == '200') {
                    this.$message({
                        type: 'success',
                        message: '置顶成功!',
                        offset: 100
                    })
                }
                this.labelIndexs()
            })
        },

        // 修改
        Modify(value) {
            this.$router.push({
                path: '/plug_ins/stores/editorStore',
                query: {
                    id: value.id
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('确定删除此标签么？', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
                delLabel({
                    api: 'admin.sec.label.delLabel',
                    id: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.labelIndexs()
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

        // 添加商品
        addGoods(value) {
            this.$router.push({
                path: '/plug_ins/seckill/addGoods',
                query: {
                    id: value.id
                }
            })
        },

        // 商品列表
        goodsList(value) {
            this.$router.push({
                path: '/plug_ins/seckill/goodsList',
                query: {
                    id: value.id
                }
            })
        },

        // 弹框方法
        dialogShow(value) {
            if(value) {
                this.title = '编辑标签'
                this.id = value.id
                this.ruleForm.name = value.name
                this.ruleForm.title = value.title
            } else {
                this.title = '添加标签'
                this.id = null
            }
            this.dialogVisible = true
        },
    
        handleClose(done) {
            this.dialogVisible = false
            this.$refs['ruleForm'].clearValidate()
        },

        // 新增标签
        determine(formName) {
            this.$refs[formName].validate(async (valid) => {
                if (valid) {
                    try {
                    addLabel({
                        api: "admin.sec.label.addLabel",
                        title: this.ruleForm.title,
                        name: this.ruleForm.name,
                        id: this.id ? this.id : null
                    }).then((res) => {
                        console.log(res);
                        if (res.data.code == "200") {
                            this.$message({
                                message: "修改成功",
                                type: "success",
                                offset: 100,
                            });
                            this.dialogVisible = false;
                            this.labelIndexs()
                        }
                    });
                    } catch (error) {

                    }
                } else {

                }
            });
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            this.pageSize = e
            this.labelIndexs().then(() => {
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
            if(this.currpage !== 1) {
                this.previousId = this.tableData[this.tableData.length -1].id
            }
            this.labelIndexs().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },
    }
}