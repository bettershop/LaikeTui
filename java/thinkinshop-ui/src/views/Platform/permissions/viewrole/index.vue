<template>
  <div class="container">
      <div class="add-role">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="90px">
            <el-form-item class="role-name" label="角色名称" prop="rolename">
                <el-input :disabled="true" v-model="ruleForm.rolename" placeholder="请输入角色名称"></el-input>
            </el-form-item>
            <el-form-item class="permissions" label="绑定权限" prop="permissions">
                <el-tree
                    :data="treeData"
                    show-checkbox
                    node-key="id"
                    ref="tree"
                    v-model="ruleForm.rolelist"
                    :check-strictly="systemNodeFlag"
                    @check-change="handleCheckChange"
                    :props="defaultProps">
                </el-tree>
            </el-form-item>
            <el-form-item class="role-describe" label="角色描述" prop="roledescribe">
                <el-input :disabled="true" v-model="ruleForm.roledescribe" placeholder="请输入角色描述" type="textarea"></el-input>
            </el-form-item>
            <div class="form-footer">
            <el-form-item>
                <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
            </el-form-item>
            </div>
        </el-form>
      </div>
  </div>
</template>

<script>
import { getUserRoleInfo } from '@/api/Platform/merchants'
export default {
    name: 'viewrole',

    data() {
        return {
            ruleForm: {
                rolename: '',
                roledescribe: ''
            },
            rules: {
                rolename: [
                    { required: true, message: '请填角色名称', trigger: 'blur' }
                ],
                roledescribe: [
                    { required: true, message: '请填角色描述', trigger: 'blur' }
                ],
                permissions: [
                    { required: true, message: '请选择绑定角色', trigger: 'blur' }
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
      this.getUserRoleInfos(this.$route.params.id)
      this.ruleForm.rolename = this.$route.params.name
      this.ruleForm.roledescribe = this.$route.params.role_describe
    },

    methods: {
        async getUserRoleInfos(id) {
            const res = await getUserRoleInfo({
                api: 'admin.role.getUserRoleInfo',
                id: id
            })
            console.log(res);
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
                  .el-input {
                    width: 580px;
                    height: 40px;
                    input {
                      background-color: #f4f7f9;
                    }
                  }
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
            }

            .form-footer {
                .bdColor {
                    color: #6a7076;
                    border: 1px solid #d5dbe8;
                }
                .bdColor:hover {
                    color: #2890ff;
                    border: 1px solid #2890ff;
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
        color: #414658;
    }
    /deep/.el-input__inner::-webkit-input-placeholder {
        color: #414658;
    }
    /deep/.el-textarea__inner::-webkit-input-placeholder {
        color: #414658;
    }
    /deep/.el-tree-node__label {
        color: #414658;
    }
    /deep/.add-role .el-form .role-name .el-form-item__content .el-input input {
        background-color: #f4f7f9;
        color: #414658;
    }

    /deep/.add-role .el-form .role-describe .el-form-item__content .el-textarea textarea {
        width: 580px;
        height: 68px;
        color: #414658;
    }
}
</style>