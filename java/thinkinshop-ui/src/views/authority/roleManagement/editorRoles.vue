<template>
  <div class="container">
    <div class="add-role">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="90px">
        <el-form-item class="role-name" label="角色名称" prop="rolename">
            <el-input v-model="ruleForm.rolename" placeholder="请输入角色名称"></el-input>
        </el-form-item>
        <el-form-item class="permissions" label="绑定权限" prop="" required>
          <el-tree
            :data="treeData"
            show-checkbox
            node-key="id"
            ref="tree"
            v-model="ruleForm.rolelist"
            :check-strictly="systemNodeFlag"
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
import editorRoles from '@/webManage/js/authority/roleManagement/editorRoles'
export default editorRoles
</script>

<style scoped lang="less">
@import '../../../webManage/css/authority/roleManagement/editorRoles.less';
</style>