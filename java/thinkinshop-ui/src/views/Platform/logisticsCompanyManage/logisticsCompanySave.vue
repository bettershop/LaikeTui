<template>
  <div class="container">
    <div class="add-menu">
      <el-form :model="formMain" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="120px">
        <el-form-item label="物流公司名称" prop="kuaidi_name">
          <el-input v-model="formMain.kuaidi_name" placeholder="请输入物流公司名称"></el-input>
        </el-form-item>
        <el-form-item label="编码" prop="type">
          <el-input v-model="formMain.type" placeholder="请输入编码"></el-input>
        </el-form-item>
        <el-form-item  label="序号" prop="sort">
          <el-input v-model="formMain.sort" placeholder="请输入序号"></el-input>
        </el-form-item>
        <el-form-item  label="开关">
          <el-switch v-model="formMain.is_open"/>
        </el-form-item>

        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="Save('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import main from "@/webManage/js/platform/logisticsCompanyManage/logisticsCompanySave";

export default main
</script>


<style scoped lang="less">
  @import "../../../webManage/css/platform/logisticsCompanyManage/logisticsCompanySave";
</style>
