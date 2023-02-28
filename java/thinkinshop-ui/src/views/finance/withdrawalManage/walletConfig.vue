<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="提现审核" @click.native.prevent="$router.push('/finance/withdrawalManage/withdrawalExamineList')"></el-radio-button>
        <el-radio-button label="提现记录" @click.native.prevent="$router.push('/finance/withdrawalManage/withdrawalRecordList')"></el-radio-button>
        <el-radio-button label="钱包参数" @click.native.prevent="$router.push('/finance/withdrawalManage/walletConfig')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="add-menu">
      <el-form :model="mainForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="110px">
        <el-form-item label="最小充值金额" prop="min_cz">
          <el-input v-model="mainForm.min_cz" placeholder="请输入最小充值金额"></el-input>
        </el-form-item>
        <el-form-item label="最小提现金额" prop="min_amount">
          <el-input v-model="mainForm.min_amount" placeholder="请输入最小提现金额"></el-input>
        </el-form-item>

        <el-form-item label="最大提现金额" prop="max_amount">
          <el-input v-model="mainForm.max_amount" placeholder="请输入最大提现金额"></el-input>
        </el-form-item>
        <el-form-item class="poundage" label="手续费" prop="service_charge">
          <el-input v-model="mainForm.service_charge" placeholder="请输入手续费"></el-input>
          <div class="prompt">手续费为大于0小于1的小数,如0.05</div>
        </el-form-item>
        <el-form-item label="钱包单位" prop="unit">
          <el-input v-model="mainForm.unit" placeholder="请输入钱包单位"></el-input>
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
import main from "@/webManage/js/finance/withdrawalManage/walletConfig";
export default main
</script>

<style scoped lang="less">
  @import "../../../webManage/css/finance/withdrawalManage/walletConfig";
</style>
