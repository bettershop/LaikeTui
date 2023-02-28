<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="基础配置" @click.native.prevent="$router.push('/mall/basicConfiguration')"></el-radio-button>
        <el-radio-button label="协议配置" @click.native.prevent="$router.push('/mall/agreement/protocolConfiguration')"></el-radio-button>
        <el-radio-button label="常见问题" @click.native.prevent="$router.push('/mall/commonProblem')"></el-radio-button>
        <el-radio-button label="新手指南" @click.native.prevent="$router.push('/mall/newbieGuide')"></el-radio-button>
        <el-radio-button label="售后服务" @click.native.prevent="$router.push('/mall/afterSales')"></el-radio-button>
        <el-radio-button label="关于我们" @click.native.prevent="$router.push('/mall/aboutUs')"></el-radio-button>
      </el-radio-group>
    </div>
    <div class="basic-configuration">
      <div class="header">
        <span>基本信息</span>
      </div>

      <el-form :model="ruleForm" :rules="rules" label-position="right" ref="ruleForm" label-width="180px" class="form-search">
        <div class="notice">
          <el-form-item label="是否需要注册：" required>
            <el-radio-group disabled v-model="ruleForm.is_Registered">
              <el-radio v-for="item in isRegisteredList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
            </el-radio-group>
            <span class="prompt">(第一次保存后不能更改,请谨慎选择)</span>
          </el-form-item>
          <el-form-item label="公司LOGO：" prop="companyLogo" required>
            <l-upload
              :limit="1"
              v-model="ruleForm.companyLogo"
              text="（建议尺寸：100px*100px）"
            >
            </l-upload>
          </el-form-item>
          <el-form-item label="微信用户默认头像：" prop="headImg" required>
            <l-upload
              :limit="1"
              v-model="ruleForm.headImg"
              text="（建议尺寸：100px*100px）"
            >
            </l-upload>
          </el-form-item>
          <el-form-item label="H5页面地址：" prop="h_Address">
            <el-input v-model="ruleForm.h_Address"></el-input>
          </el-form-item>
          <el-form-item label="前端消息保留天数：">
            <el-input v-model="ruleForm.front_message"></el-input>
            天
            <span class="prompt">(为空则不自动删除)</span>
          </el-form-item>
          <el-form-item label="移动端登录有效期：">
            <el-input v-model="ruleForm.login_validity"></el-input>
            小时
          </el-form-item>
          <el-form-item label="客服：">
            <el-input type="textarea" :rows="3" v-model="ruleForm.service" placeholder=""></el-input>
          </el-form-item>
          <el-form-item label="腾讯位置服务开发密钥：" prop="tx_key">
            <el-input v-model="ruleForm.tx_key"></el-input>
          </el-form-item>
          <el-form-item label="unipush推送：" style="margin-top:-10px">
            <el-switch v-model="ruleForm.is_unipush" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
            <div class="unipush-info" v-show="ruleForm.is_unipush == 1">
              <div class="appkey">
                <span>Appkey：</span>
                <el-input v-model="ruleForm.Appkey"></el-input>
              </div>
              <div class="Appid">
                <span>Appid：</span>
                <el-input v-model="ruleForm.Appid"></el-input>
              </div>
              <div class="MasterECRET">
                <span>MasterECRET：</span>
                <el-input v-model="ruleForm.MasterECRET"></el-input>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="是否开启快递100：" style="margin-top:-10px">
            <el-switch v-model="ruleForm.is_courier" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
            <div class="unipush-info" v-show="ruleForm.is_courier == 1">
              <div class="appkey">
                <span>查询接口地址：</span>
                <el-input v-model="ruleForm.api_address"></el-input>
              </div>
              <div class="Appid">
                <span>customer：</span>
                <el-input v-model="ruleForm.customer"></el-input>
              </div>
              <div class="MasterECRET">
                <span>授权key：</span>
                <el-input v-model="ruleForm.authorization"></el-input>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="同一账户登录踢人：" style="margin-top:-10px">
            <el-switch v-model="ruleForm.same_account" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </el-form-item>
          <div class="footer-button">
            <!-- <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button> -->
            <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import basicConfiguration from '@/webManage/js/mall/systemManagement/basicConfiguration'
export default basicConfiguration
</script>

<style scoped lang="less">
@import '../../../webManage/css/mall/systemManagement/basicConfiguration.less';
</style>