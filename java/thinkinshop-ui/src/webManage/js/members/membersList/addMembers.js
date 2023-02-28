import { saveUser } from '@/api/members/membersList'
import { getUserConfigInfo } from '@/api/members/membersSet'
export default {
    name: 'addMembers',

    data() {
        var validatePass = (rule, value, callback) => {
            if (value === '') {
              callback(new Error('请输入密码'));
            } else {
              if (this.ruleForm.confirmMima !== '') {
                this.$refs.ruleForm.validateField('confirmMima');
              }
              callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.mima) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };

        return {
            ruleForm: {
                membersHead: '',
                userName: '',
                grade: '普通会员',
                zhanghao: '',
                mima: '',
                confirmMima: '',
                phone: '',
                source: '小程序',
            },
        
            rule: {
                membersHead:[
                    { required: true, message: '请上传会员头像', trigger: 'change' },
                ],
                userName: [
                    { required: true, message: '请填写会员名称', trigger: 'blur' },
                ],
                zhanghao: [
                    { required: true, message: '请填写会员账号', trigger: 'blur' },
                ],
                mima: [
                    { required: true, message: '请填写密码', trigger: 'blur' },
                    { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
                    { validator: validatePass, trigger: 'blur' }
                ],
                confirmMima: [
                    { required: true, message: '请确认密码', trigger: 'blur' },
                    { min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur' },
                    { validator: validatePass2, trigger: 'blur', required: true }
                ],
                phone: [
                    { required: true, message: '请填写手机号', trigger: 'blur' },
                ],
                source: [
                    { required: true, message: '请填写账号来源', trigger: 'blur' },
                ],
            },

            membersGrade: [
                {
                    value: '0',
                    label: '普通会员'
                },
                {
                    value: '30',
                    label: '钻石会员'
                },
                {
                    value: '25',
                    label: '黄金会员'
                },
                {
                    value: '24',
                    label: '白银会员'
                },
                {
                    value: '34',
                    label: '测试等级'
                }
            ],// 会员等级

            sourceList: [
                {
                    value: '1',
                    label: '小程序'
                },
                {
                    value: '2',
                    label: 'APP'
                },
                {
                    value: '3',
                    label: '支付宝小程序'
                },
                {
                    value: '4',
                    label: '头条小程序'
                },
                {
                    value: '5',
                    label: '百度小程序'
                },
                {
                    value: '6',
                    label: 'pc端'
                },
                {
                    value: '7',
                    label: 'H5移动端'
                },
            ],// 账号来源
        }
    },

    created() {
        this.getUserConfigInfos()
    },

    watch: {
        'ruleForm.membersHead'() {
            if(this.ruleForm.membersHead) {
                this.$refs['membersHead'].clearValidate()
            }
        }
    },

    methods: {
        async getUserConfigInfos() {
            const res = await getUserConfigInfo({
                api: 'admin.user.getUserConfigInfo'
            })
            console.log(res);
            // this.ruleForm.membersHead = res.data.data.wx_headimgurl
            // this.ruleForm.userName = res.data.data.wx_name
        },

        // 添加会员
        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
            console.log(this.ruleForm);
            if (valid) {
                try {
                    saveUser({
                        api: 'admin.user.saveUser',
                        headerUrl: this.ruleForm.membersHead,
                        userName: this.ruleForm.userName,
                        grade: this.ruleForm.grade == '普通会员' ? 0 : this.ruleForm.grade,
                        zhanghao: this.ruleForm.zhanghao,
                        mima: this.ruleForm.mima,
                        phone: this.ruleForm.phone,
                        source: this.ruleForm.source == '小程序' ? 1 : this.ruleForm.source
                    }).then(res => {
                        if(res.data.code == '200') {
                            this.$message({
                                message: '添加成功',
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
        },
    }
}