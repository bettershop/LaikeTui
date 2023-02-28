<template>
  <div class="container">
    <div class="add-menu">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="90px">
        <el-form-item label="发货人" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入发货人"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="tel">
          <el-input v-model="ruleForm.tel" placeholder="请输入联系电话"></el-input>
        </el-form-item>
        <el-form-item label="所在地区" prop="sheng">
          <el-select class="select-input" v-model="ruleForm.sheng" placeholder="省">
            <el-option v-for="(item,index) in shengList" :key="index" :label="item.g_CName" :value="item.g_CName">
              <div @click="getShi(item.groupID)">{{ item.g_CName }}</div>
            </el-option>
          </el-select>
          <el-select class="select-input" v-model="ruleForm.shi" placeholder="市">
            <el-option v-for="(item,index) in shiList" :key="index" :label="item.g_CName" :value="item.g_CName">
              <div @click="getXian(item.groupID)">{{ item.g_CName }}</div>
            </el-option>
          </el-select>
          <el-select class="select-input" v-model="ruleForm.xian" placeholder="县">
            <el-option v-for="(item,index) in xianList" :key="index" :label="item.g_CName" :value="item.g_CName">
              <div>{{ item.g_CName }}</div>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item  label="详细地址" prop="address">
          <el-input v-model="ruleForm.address" placeholder="请输入详细地址"></el-input>
        </el-form-item>
        <el-form-item label="邮政编码" prop="code">
          <el-input v-model="ruleForm.code" placeholder="请输入邮政编码"></el-input>
        </el-form-item>
        <el-form-item label="是否默认" prop="is_default">
          <el-radio-group v-model="ruleForm.is_default">
            <el-radio v-model="ruleForm.is_default" label="1">是</el-radio>
            <el-radio v-model="ruleForm.is_default" label="0">否</el-radio>
          </el-radio-group>
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
import addressSave from "@/webManage/js/mall/aftersaleAddress/addressSave";
export default addressSave
</script>

<style scoped lang="less">
  @import "../../../webManage/css/mall/aftersaleAddress/addressSave";
</style>
