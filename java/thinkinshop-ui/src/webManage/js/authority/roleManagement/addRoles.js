import { getUserRoleInfo, addUserRoleMenu } from '@/api/authority/roleManagement'
export default {
    name: 'addRoles',

    data() {
        return {
            ruleForm: {
                rolename: '',
                roledescribe: '',
                rolelist: []
            },
            rules: {
                rolename: [
                    { required: true, message: '请填角色名称', trigger: 'blur' }
                ],
                roledescribe: [
                    { required: true, message: '请填角色描述', trigger: 'blur' }
                ],
            },

            treeData: [],
            defaultProps: {
                children: 'children',
                label: 'title'
            },
            idList: []
        }
    },

    created() {
        this.getUserRoleInfos()
    },

    methods: {
        async getUserRoleInfos() {
            const res = await getUserRoleInfo({
                api: 'admin.role.getUserRoleInfo'
            })
            console.log(res);
            this.treeData = res.data.data.menuList
            console.log(res);
        },

        filterNode(value) {
            if(value && value[0]) {
                filterNode(value.children)
            } else {
                value.children = []
            }
        },

        handleCheckChange () {
            let res = this.$refs.tree.getCheckedNodes()
            let arr = []
            res.forEach((item) => {
                arr.push(item.id)
            })
            this.idList = res.map(item => {
                return item.id
            })
            this.ruleForm.rolelist = this.idList
            console.log(this.idList);
        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
            console.log(this.ruleForm);
            if (valid) {
                try {
                    addUserRoleMenu({
                        api: "admin.role.addUserRoleMenu",
                        permissions: this.idList.join(),
                        roleName: this.ruleForm.rolename,
                        describe: this.ruleForm.roledescribe,
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