<template>
  <div class="container">
    <el-form :model="formMain" :rules="formMain.rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
      <el-form-item label="登录页logo" prop="logo" required>
        <l-upload
          :limit="1"
          v-model="formMain.logo"
          text="建议尺寸：152*50px"
        >
        </l-upload>
      </el-form-item>
      <el-form-item label="版权信息" class="copyright" prop="copyright_information" required>
        <el-input v-model="formMain.copyright_information" placeholder="请输入版权信息"></el-input>
      </el-form-item>
      <el-form-item label="备案信息" class="record" prop="record_information" required>
        <el-input v-model="formMain.record_information" placeholder="请输入备案信息"></el-input>
      </el-form-item>

      <el-form-item class="link" label="登录页友情链接">
        <div v-for="(item,index) in formMain.linkPage" :key="index" class="linkBox">
          <el-input class="link-name" v-model="item.name" placeholder="请输入链接名称"></el-input>
          <el-input class="link-local" v-model="item.url" placeholder="请输入链接地址"></el-input>
          <div class="add-reduction">
            <i class="el-icon-circle-plus-outline" @click="addUrl()"  v-if="(index+1)===formMain.linkPage.length"></i>
            <i class="el-icon-remove-outline" @click="delUrl(index)" v-else></i>
            <span>用于平台登录页底部链接</span>
          </div>
        </div>
      </el-form-item>

      <div class="form-footer">
        <el-form-item>
          <el-button class="bgColor" type="primary" @click="Save('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
          <!-- <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button> -->
        </el-form-item>
      </div>
    </el-form>
  </div>

</template>

<script>
import main from "@/webManage/js/platform/system/systemConfig";
export default main
</script>


<style scoped lang="less">
@import "../../../webManage/css/platform/logisticsCompanyManage/logisticsCompanySave";
</style>
