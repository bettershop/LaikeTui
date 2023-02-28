<template>
  <div class="container">
    <div class="add-menu">
      <el-form :model="dataMain" :rules="rules" ref="ruleForm" class="picture-ruleForm" label-width="110px">
        <el-form-item label="管理员账号:" prop="name">
          <el-input v-model="dataMain.name" v-if="dataMain.id==null" placeholder="请输入管理员账号"></el-input>
          <el-input v-model="dataMain.name" disabled v-else></el-input>
        </el-form-item>
        <el-form-item label="所属客户编号:">
          <template>
            {{customer_number}}
          </template>
        </el-form-item>
        <el-form-item label="新密码:" v-if="dataMain.id!=null">
          <el-input v-model="dataMain.passwordNew" placeholder="请输入新密码"></el-input>
        </el-form-item>
        <el-form-item label="管理员密码:" prop="password" v-if="dataMain.id==null">
          <el-input v-model="dataMain.password" placeholder="请输入管理员密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码:" prop="passwordOld" v-if="dataMain.id==null">
          <el-input v-model="dataMain.passwordOld" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select class="select-input" v-model="dataMain.roleId" placeholder="请选择">
            <el-option v-for="(item,index) in roleList" :key="index" :label="item.name" :value="item.id">
              <div>{{ item.name }}</div>
            </el-option>
          </el-select>
        </el-form-item>


        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="Save('ruleForm')">{{
              $t('DemoPage.tableFromPage.save') }}
            </el-button>
            <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import main from "@/webManage/js/authority/adminUserManage/addAdminUser";
export default main;
</script>

<style scoped lang="less">
  @import "../../../webManage/css/authority/addAdminUser";
</style>
