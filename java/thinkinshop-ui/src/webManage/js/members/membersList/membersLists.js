import { getUserInfo, userRechargeMoney, delUserById, updateUserGradeById, goodsStatus, method } from '@/api/members/membersList'
import { getStorage } from "@/utils/storage";
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'membersLists',
    mixins: [mixinstest],
    data() {
        return {
            radio1:'会员列表',

            membersGrade: [],// 会员等级

            overdueList: [
                {
                    value: '0',
                    label: '未过期'
                },
                {
                    value: '1',
                    label: '已过期'
                }
            ],// 是否过期

            methodList: [],

            sourceList: [],// 账号来源

            inputInfo: {
                uid: null,
                uname: null,
                grade: null,
                isOverdue: null,
                source: null,
                tel: null
            },

            tableData: [],
            loading: true,

            tag: '',

            // 弹框数据
            dialogVisible2: false,
            ruleForm: {
                balance: '',
                integral: '',
                level: '',
                effect_time: '1'
            },
            rules: {
                balance: [
                    {required: true, message: '请输入余额金额', trigger: 'blur'}
                ],
                integral: [
                    {required: true, message: '请输入余额金额', trigger: 'blur'}
                ],
                level: [
                    {required: true, message: '请选择会员等级', trigger: 'change'}
                ],
            },

            id: null,
            userId: null,
            toggle: null,
            // table高度
            tableHeight: null,
            // 导出弹框数据
            dialogVisible: false,
        }
    },

    created() {
        if(this.$route.params.pageSize) {
            this.pagination.page = this.$route.params.dictionaryNum
            this.dictionaryNum = this.$route.params.dictionaryNum
            this.pageSize = this.$route.params.pageSize
        }
        this.getUserInfos()
        this.goodsStatus()
        this.methods()
        this.sourceList = getStorage("laike_source").reverse()
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

        async getUserInfos() {
            const res = await getUserInfo({
                api: 'admin.user.getUserInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                uid: this.inputInfo.uid,
                uname: this.inputInfo.uname,
                grade: this.inputInfo.grade,
                isOverdue: this.inputInfo.isOverdue,
                source: this.inputInfo.source,
                tel: this.inputInfo.tel
            })
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

        async methods() {
            const res = await method({
                api: 'admin.user.getUserGradeType'
            })
            console.log(res);            
            this.methodList = res.data.data.gradeType
        },

        // 获取会员等级列表
        async goodsStatus() {
            const res = await goodsStatus({
                api: 'admin.user.goodsStatus'
            })

            console.log(res);
            let levelList = res.data.data.map(item => {
                return {
                    value: item.id,
                    label: item.name
                }
            })
            levelList.unshift({
                value: 0,
                label: '普通会员'
            })

            this.membersGrade = levelList
        },

        // 重置
        reset() {
            this.inputInfo.uid = null
            this.inputInfo.uname = null
            this.inputInfo.grade = null
            this.inputInfo.isOverdue = null
            this.inputInfo.source = null
            this.inputInfo.tel = null
        },
  
        // 查询
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.getUserInfos().then(() => {
            this.loading = false
            if(this.tableData.length > 5) {
                this.showPagebox = true
            }
            })
        },

        // 查看
        View(value) {
            this.$router.push({
                path: '/members/viewMembers',
                query: {
                    id: value.user_id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 编辑
        Edit(value) {
            this.$router.push({
                path: '/members/editorMembers',
                query: {
                    id: value.user_id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 删除
        Delete(value) {
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
                delUserById({
                    api: 'admin.user.delUserById',
                    id: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getUserInfos()
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

        // 代客下单
        valetOrder(value) {
            this.$router.push({
                path: '/order/orderList/valetOrder',
                query: {
                    id: value
                }
            })
        },

        tags(value) {
            if(this.tag == value.user_id) {
                this.tag = ''
            } else {
                this.tag = value.user_id
            }
        },

        addClass({row, column, rowIndex, columnIndex}) {
            if(columnIndex == 14) {
                return 'add'
            }
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getUserInfos().then(() => {
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
            this.getUserInfos().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        // 添加会员
        addMembers() {
            this.$router.push({
                path: '/members/addMembers',
            })
        },

        // 弹框方法
        dialogShow2(value,toggle) {
            console.log(value);
            this.dialogVisible2 = true
            this.id = value.id
            this.userId = value.user_id
            this.toggle = toggle
            this.ruleForm.balance = ''
            this.ruleForm.integral = ''
            if(this.toggle == 3) {
                this.ruleForm.effect_time = '1'
                this.membersGrade.filter(item => {
                    if(item.label == value.grade) {
                        this.ruleForm.level = item.value
                    }
                })
            }
        },
        
        handleClose2(done) {
            this.dialogVisible2 = false
            this.id = null
            this.userId = null
            this.toggle = null
            this.$refs['ruleForm'].clearValidate()
        },

        submitForm2(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm);
              if (valid) {
                try {
                    if(this.toggle === 1) {
                        userRechargeMoney({
                            api: 'admin.user.userRechargeMoney',
                            id: this.id,
                            money: this.ruleForm.balance,
                            type: 1
                        }).then(res => {
                            console.log(res);
                            if(res.data.code == '200') {
                                this.dialogVisible2 = false
                                this.getUserInfos()
                                this.$message({
                                    type: 'success',
                                    message: '充值成功!',
                                    offset: 100
                                })
                            }
                        })
                    } else if(this.toggle === 2) {
                        userRechargeMoney({
                            api: 'admin.user.userRechargeMoney',
                            id: this.id,
                            money: this.ruleForm.integral,
                            type: 3
                        }).then(res => {
                            console.log(res);
                            if(res.data.code == '200') {
                                this.dialogVisible2 = false
                                this.getUserInfos()
                                this.$message({
                                    type: 'success',
                                    message: '充值成功!',
                                    offset: 100
                                })
                            }
                        })
                    } else {
                        updateUserGradeById({
                            api: 'admin.user.updateUserGradeById',
                            userId: this.userId,
                            grade: this.ruleForm.level,
                            gradeType: this.ruleForm.effect_time
                        }).then(res => {
                            console.log(res);
                            if(res.data.code == '200') {
                                this.dialogVisible2 = false
                                this.getUserInfos()
                                this.$message({
                                    type: 'success',
                                    message: '修改成功!',
                                    offset: 100
                                })
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

        // 账号来源
        getSource(value) {
            if(value == '1') {
                return '小程序'
            } else if(value == '2') {
                return 'APP'
            } else if(value == '3') {
                return '支付宝小程序'
            } else if(value == '4') {
                return '头条小程序'
            } else if(value == '5') {
                return '百度小程序'
            } else if(value == '6') {
                return 'pc端'
            } else if(value == '7') {
                return 'H5移动端'
            }else {
                return '闪购'
            }
        },

        // 导出弹框方法
        dialogShow() {
            this.dialogVisible = true
        },
        
        handleClose(done) {
            this.dialogVisible = false
        },

        async exportPage() {
            exports({
                api: 'admin.user.getUserInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                exportType: 1,
            },'memberslist')
        },
    
        async exportAll() {
            exports({
                api: 'admin.user.getUserInfo',
                pageNo: 1,
                pageSize: this.total,
                exportType: 1,
            },'memberslist')
        },
    
        async exportQuery() {
            exports({
                api: 'admin.user.getUserInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.total,
                exportType: 1,
                uid: this.inputInfo.uid,
                uname: this.inputInfo.uname,
                grade: this.inputInfo.grade,
                isOverdue: this.inputInfo.isOverdue,
                source: this.inputInfo.source,
                tel: this.inputInfo.tel
            },'memberslist')
        }
    }
}