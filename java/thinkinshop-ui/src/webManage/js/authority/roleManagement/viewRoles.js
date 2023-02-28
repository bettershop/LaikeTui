import { getUserRoleInfo, getRoleListInfo } from '@/api/authority/roleManagement'
export default {
    name: 'viewRoles',

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
        this.getUserRoleInfos()
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
            this.checkChnage(this.treeData)
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

        checkChnage(value) {
            for(var i = 0; i < value.length; i++) {
                var children = value[i].children
                if (children != undefined) {
                    this.checkChnage(children)
                }
                value[i].disabled = true
            }
         }

    }
}