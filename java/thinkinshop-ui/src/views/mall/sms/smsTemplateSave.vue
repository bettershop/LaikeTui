<template>
  <div class="container">
    <el-form :model="mainForm" :rules="mainForm.rules" ref="ruleForm" class="picture-ruleForm" label-width="150px">
      <el-form-item label="短信签名" prop="SignName">
        <el-input v-model="mainForm.SignName" placeholder="请输入短信签名"></el-input>
      </el-form-item>
      <el-form-item label="短信模板名称" prop="name">
        <el-input v-model="mainForm.name" placeholder="请输入短信模板名称"></el-input>
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-select class="select-input" v-model="mainForm.type" placeholder="请选择类型" value-key="text">
          <el-option v-for="(item,index) in smsTypeList" :key="index" :label="item.text" :value="item.value">
            <div @click="getCategory(item.text)">{{ item.text }}</div>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="类别" prop="category">
        <el-select class="select-input" v-model="cate_type" placeholder="请选择类别" @change="change_type">
          <el-option v-for="(item,index) in categoryList" :key="index" :label="item.text" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="短信模板Code" prop="TemplateCode">
        <el-input v-model="mainForm.TemplateCode" placeholder="请输入短信模板Code"></el-input>
      </el-form-item>
      <el-form-item label="短信测试手机号" prop="phone">
        <el-input v-model="mainForm.phone" placeholder="请输入短信测试手机号">
          <template slot="append">测试验证</template>
        </el-input>
        <!-- <span class="right">(验证短信是否成功！)</span> -->
      </el-form-item>
      <el-form-item label="发送内容" prop="content">
        <el-input v-model="mainForm.content" type="textarea" :rows="2" placeholder="请输入如：您有新的订单待处理，当前状态：${status}，订单摘要:${remark}，请及时处理。"></el-input>
        <!-- <span class="right">如：您有新的订单待处理，当前状态：${status}，订单摘要:${remark}，请及时处理。</span> -->
      </el-form-item>

      <div class="form-footer">
        <el-form-item>
          <el-button class="bgColor" type="primary" @click="Save('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}
          </el-button>
          <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}
          </el-button>
        </el-form-item>
      </div>
      <!-- <div class="footer-button">
        <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
        <el-button type="primary" class="footer-save bgColor mgleft" @click="Save('ruleForm')">保存</el-button>
      </div> -->
    </el-form>
  </div>
</template>

<script>
import main from "@/webManage/js/mall/sms/smsTemplateSave";

export default main
</script>

<style scoped lang="less">
  @import "../../../webManage/css/mall/sms/smsTemplateSave";
</style>
