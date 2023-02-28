import { getUserInfo, updateUserById, goodsStatus } from '@/api/members/membersList'
import { getTime } from '@/utils/utils'
export default {
    name: 'editorMembers',

    data() {
        return {
            membersGrade: [],// 会员等级

            userInfo: null,

            ruleForm: {
                headimgurl: '',
                user_name: '',
                grade: '',
                mobile: '',
                loginPwd: '',
                paypwd: '',
                money: '',
                score: '',
                birthday: '',
            },
        
            rule: {
                user_name: [
                    { required: true, message: '请输入用户名称', trigger: 'blur' },
                ],
                mobile: [
                    { required: true, message: '请输入手机号码', trigger: 'blur' },
                ],
                loginPwd: [
                    { required: true, message: '请输入登录密码', trigger: 'blur' },
                ],
            },

            changePwd: false,
            changePayPwd: false
        }
    },

    created() {
        this.getUserInfos()
        this.goodsStatus()
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'membersList') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
    },
    
    watch: {
        'ruleForm.loginPwd': {
            handler: function() {
                if(this.ruleForm.loginPwd !== '******') {
                    this.changePwd = true
                }
            }
        },

        'ruleForm.paypwd': {
            handler: function() {
                if(this.ruleForm.paypwd !== '******') {
                    this.changePayPwd = true
                }
            }
        }
    },

    methods: {
        async getUserInfos() {
            const res = await getUserInfo({
                api: 'admin.user.getUserInfo',
                uid: this.$route.query.id
            })
            this.userInfo = res.data.data.list[0]
            this.ruleForm.headimgurl = res.data.data.list[0].headimgurl
            this.ruleForm.user_name = res.data.data.list[0].user_name
            this.ruleForm.grade = res.data.data.list[0].grade
            this.ruleForm.mobile = res.data.data.list[0].mobile
            this.ruleForm.loginPwd = res.data.data.list[0].loginPwd === false ? '' : '******'
            this.ruleForm.paypwd = res.data.data.list[0].isPaymentPwd === false ? '' : '******'
            this.ruleForm.money = res.data.data.list[0].money
            this.ruleForm.score = res.data.data.list[0].score
            this.ruleForm.birthday = res.data.data.list[0].birthday
            console.log(this.userInfo)
        },

        // 获取会员等级下拉
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

        getSource(value) {
            if(value == 1) {
                return '小程序'
            } else if(value == 2) {
                return 'APP'
            } else if(value == 3) {
                return '支付宝小程序'
            } else if(value == 4) {
                return '头条小程序'
            } else if(value == 5) {
                return '百度小程序'
            } else if(value == 6) {
                return 'pc端'
            } else if(value == 7) {
                return 'H5移动端'
            } else {
                return '闪购'
            }
        },

        getGrade(value) {
            if(typeof value !== 'number') {
                if(value == '普通会员') {
                    return 0
                } else if(value == '钻石会员') {
                    return 30
                } else if(value == '黄金会员') {
                    return 25
                } else if(value == '白银会员') {
                    return 24
                } else {
                    return 34
                }
            } else {
                return value
            }
        },

        // 修改会员信息
        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm);
              if (valid) {
                try {
                    updateUserById({
                        api: 'admin.user.updateUserById',
                        userId: this.$route.query.id,
                        uname: this.ruleForm.user_name,
                        grade: this.getGrade(this.ruleForm.grade),
                        phone: this.ruleForm.mobile,
                        pwd: this.changePwd ? this.ruleForm.loginPwd : null,
                        paypwd: this.changePayPwd ? this.ruleForm.paypwd : null,
                        money: this.ruleForm.money,
                        jifen: this.ruleForm.score,
                        birthday: this.ruleForm.birthday ? getTime(this.ruleForm.birthday) : null
                    }).then(res => {
                        console.log(res);
                        if(res.data.code == '200') {
                            this.$message({
                                message: '修改成功',
                                type: 'success',
                                offset: 100
                            })
                            this.$router.go(-1)
                        }
                    })
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
        }
    }
}