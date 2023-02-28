<template>
  <div class="container">
      <div class="basic-configuration">
      <div class="header">
        <span>编辑协议</span>
      </div>

      <el-form :model="ruleForm" label-position="right" ref="ruleForm" label-width="130px" class="form-search">
        <div class="notice">
          <el-form-item label="协议标题：" required>
            <el-input v-model="ruleForm.agreement_title"></el-input>
          </el-form-item>
          <el-form-item label="协议类型：" required>
            <el-radio-group v-model="ruleForm.protocol_type">
              <el-radio v-for="item in protocolTypeList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="用户协议：">
            <div class="richText-info">
              <vue-editor
                v-model="ruleForm.user_agreement"
                useCustomImageHandler
                @image-added="handleImageAdded"
              ></vue-editor>
            </div>
          </el-form-item>
        </div>
        <div class="footer-button">
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import editorAgreement from '@/webManage/js/mall/systemManagement/editorAgreement'
export default editorAgreement
</script>

<style scoped lang="less">
@import '../../../../webManage/css/mall/systemManagement/editorAgreement.less';
</style>