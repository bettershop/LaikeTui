import { getUserRoleInfo, addUserRoleMenu, getRoleListInfo } from '@/api/authority/roleManagement'
export default {
    name: 'editorRoles',

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
            idList: [],
            systemNodeFlag: false
        }
    },

    created() {
        this.getUserRoleInfos().then(() => {
            // this.systemNodeFlag = false
        })
        this.getRoleListInfos()
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'roleList') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
    },

    methods: {
        async getRoleListInfos() {
            const res = await getRoleListInfo({
                api: 'admin.role.getRoleListInfo',
                id: this.$route.query.id
            })
            this.ruleForm.rolename = res.data.data.list[0].name
            this.ruleForm.roledescribe = res.data.data.list[0].role_describe
            console.log(res);
        },

        async getUserRoleInfos() {
            const res = await getUserRoleInfo({
                api: 'admin.role.getUserRoleInfo',
                id: this.$route.query.id
            })
            this.treeData = res.data.data.menuList
            this.recursionNodes(res.data.data.menuList)
            let that = this
            setTimeout(function() {
                that.idList.forEach(item => {
                    that.$refs.tree.setChecked(item,true,false)
                })
            },500)
        },

        recursionNodes(childNodes) {
            const nodes = childNodes
            for (const item of nodes) {
              if (item.checked) {
                this.idList.push(item.id)
              }
              if (item.children) {
                this.recursionNodes(item.children)
              }
            }
        },

        filterNode(value) {
            if(value && value[0]) {
                filterNode(value.children)
            } else {
                value.children = []
            }
        },

        handleCheckChange () {
            let res = this.$refs.tree.getCheckedNodes().concat(this.$refs.tree.getHalfCheckedNodes())
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
            this.idList = []
            const selectIds = this.$refs.tree.getCheckedNodes().concat(this.$refs.tree.getHalfCheckedNodes()).map(item => {
                return item.id
            })
            this.idList.push(...selectIds)
            this.$refs[formName].validate(async (valid) => {
            console.log(this.idList);
            if (valid) {
                try {
                    addUserRoleMenu({
                        api: "admin.role.addUserRoleMenu",
                        permissions: this.idList.join(),
                        roleName: this.ruleForm.rolename,
                        describe: this.ruleForm.roledescribe,
                        id: this.$route.query.id
                    }).then(res => {
                        if(res.data.code == '200') {
                            this.$message({
                                message: '编辑成功',
                                type: 'success',
                                offset: 100
                            })
                        }
                        this.$router.go(-1)
                        console.log(res);
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