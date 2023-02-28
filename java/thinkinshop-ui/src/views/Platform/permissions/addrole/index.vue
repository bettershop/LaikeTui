<template>
  <div class="container">
      <div class="add-role">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="90px">
            <el-form-item class="role-name" label="角色名称" prop="rolename">
                <el-input v-model="ruleForm.rolename" placeholder="请输入角色名称"></el-input>
            </el-form-item>
            <el-form-item class="permissions" label="绑定权限" prop="rolelist">
                <el-tree
                    :data="treeData"
                    show-checkbox
                    node-key="id"
                    ref="tree"
                    v-model="ruleForm.rolelist"
                    @check-change="handleCheckChange"
                    :props="defaultProps">
                </el-tree>
            </el-form-item>
            <el-form-item class="role-describe" label="角色描述" prop="roledescribe">
                <el-input v-model="ruleForm.roledescribe" placeholder="请输入角色描述" type="textarea"></el-input>
            </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
        </el-form>
      </div>
  </div>
</template>

<script>
import { getUserRoleInfo } from '@/api/Platform/merchants'
import { addUserRoleMenu } from '@/api/Platform/permissions'

export default {
    name: 'addrole',

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
                rolelist: [
                    { required: true, message: '请选择绑定角色', trigger: 'change' }
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
                api: 'admin.role.getUserRoleInfo',
            })
            console.log(res);
            this.treeData = res.data.data.menuList
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

        // 添加角色
        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
            console.log(this.ruleForm);
            if (valid) {
                try {
                    addUserRoleMenu({
                        api: "saas.role.addUserRoleMenu",
                        permissions: this.idList.join(),
                        roleName: this.ruleForm.rolename,
                        describe: this.ruleForm.roledescribe
                    }).then(res => {
                        if(res.data.code == '200') {
                            console.log(res);
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
</script>

<style scoped lang="less">
.container {
    width: 100%;
    height: 737px;
    background-color: #fff;
    padding: 40px 0 0 0;
    color: #414658;
    position: relative;
    /deep/.add-role {
        display: flex;
        justify-content: center;
        .el-form {
            .role-name {
                display: flex;
                .el-form-item__content {
                    width: 580px;
                    // height: 40px;
                }
                .select-input {
                    width: 580px;
                    height: 40px;
                }
            }

            .permissions {
                display: flex;
                height: 440px;
                overflow: hidden;
                overflow-y: auto;
                .el-form-item__content {
                    width: 580px;
                    .el-tree {
                        .el-tree-node {
                            .el-tree-node__content {
                                height: 40px;
                            }
                        }
                    }
                }
            }

            .role-describe {
                .el-form-item__content {
                    .el-textarea {
                        width: 580px;
                        height: 68px;
                        textarea {
                            width: 580px;
                            height: 68px;
                        }
                    }
                }
                .el-form-item__error {
                    left: 90px;
                }
            }

            .el-form-item {
                &:not(:last-child) {
                    .el-form-item__content {
                        margin-left: 0px !important;
                    }
                }
            }
        }
    }

    /deep/.el-form-item__label {
        font-weight: normal;
    }
}
</style>