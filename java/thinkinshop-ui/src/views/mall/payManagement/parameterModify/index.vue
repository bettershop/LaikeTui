<template>
 <div class="container">
    <div class="parameters-change">
      <el-form v-if="this.$route.query.id == 4 || this.$route.query.id == 5 || this.$route.query.id == 6 || this.$route.query.id == 10 || this.$route.query.id == 12" :model="ruleForm1" :rules="rules1" ref="ruleForm1"  class="wx-form" label-width="210px">
        <el-form-item required label="是否启用" prop="status">
          <el-switch v-model="ruleForm1.status" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
          </el-switch>
        </el-form-item>
        <el-form-item label="AppId" prop="appid">
          <el-input v-model="ruleForm1.appid" placeholder="请输入AppId"></el-input>
        </el-form-item>
        <el-form-item label="AppSecret" prop="appsecret" class="prompt">
          <el-input v-model="ruleForm1.appsecret" placeholder="请输入AppSecret"></el-input>
          <div class="red-prompt">
            <span>注：若微信支付尚未开通，以下选项请设置0</span>
          </div>
        </el-form-item>
        <el-form-item label="回调路径" prop="notify_url" class="prompt">
          <el-input v-model="ruleForm1.notify_url" placeholder="请输入回调路径"></el-input>
          <div class="red-prompt">
            <span>注：回调路径默认为 https://java.houjiemeishi.com/wx_notify</span>
          </div>
        </el-form-item>
        <el-form-item label="微信支付商户号" prop="mch_id">
          <el-input v-model="ruleForm1.mch_id" placeholder="请输入微信支付商户号"></el-input>
        </el-form-item>
        <el-form-item label="微信支付Api密钥" prop="mch_key">
          <el-input v-model="ruleForm1.mch_key" placeholder="请输入微信支付Api密钥"></el-input>
        </el-form-item>
        <el-form-item label="微信支付apiclient_cert.pem" prop="cert_pem" class="prompt addHeight">
          <el-input type="textarea" v-model="ruleForm1.cert_pem" placeholder="请输入微信支付apiclient_cert.pem"></el-input>
          <div class="black-prompt">
            <span>使用文本编辑器打开apiclient_cert.pem文件，将文件的全部内容复制进来</span>
          </div>
        </el-form-item>
        <el-form-item label="微信支付apiclient_key.pem" prop="key_pem" class="prompt addHeight">
          <el-input type="textarea" v-model="ruleForm1.key_pem" placeholder="请输入微信支付apiclient_key.pem"></el-input>
          <div class="black-prompt">
            <span>使用文本编辑器打开apiclient_key.pem文件，将文件的全部内容复制进来</span>
          </div>
        </el-form-item>
        <el-form-item label="微信apiclient_cert.p12" prop="cert_p12" class="prompt addHeight">
          <el-upload 
            :action="actionUrl"
            :data="uploadData"
            :show-file-list="true"
            :on-success="handleAvatarSuccess"
            :multiple="false" 
            :file-list="cert_p12_list"
            accept=".p12"  
            :limit=1 >
            <el-button size="small" type="primary" >上传文件</el-button>
          </el-upload>
          <div class="black-prompt">
            <span>微信商户配置好的p12文件原文件</span>
          </div>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm1')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>

      <el-form v-if="this.$route.query.id == 1 || this.$route.query.id == 7 || this.$route.query.id == 11 || this.$route.query.id == 13" :model="ruleForm2" :rules="rules2" ref="ruleForm2"  class="zfb-form" label-width="210px">
        <el-form-item required label="是否启用" prop="status">
          <el-switch v-model="ruleForm2.status" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
          </el-switch>
        </el-form-item>
        <el-form-item label="支付宝AppId" prop="appid">
          <el-input v-model="ruleForm2.appid" placeholder="请输入支付宝AppId"></el-input>
        </el-form-item>
        <el-form-item label="支付宝回调路径" prop="notify_url" class="prompt">
          <el-input v-model="ruleForm2.notify_url" placeholder="请输入支付宝回调路径"></el-input>
          <div class="red-prompt">
            <span>注：回调路径默认为 https://xiaochengxu.houjiemeishi.com/V3/notify_url.php</span>
          </div>
        </el-form-item>
        <el-form-item label="签名类型" prop="signType">
          <el-radio-group v-model="ruleForm2.signType">
            <el-radio :label="'RSA'">RSA</el-radio>
            <el-radio :label="'RSA2'">RSA2</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="this.$route.query.id !== 7" label="加密密钥" prop="encryptKey">
          <el-input v-model="ruleForm2.encryptKey" placeholder="请输入加密密钥"></el-input>
        </el-form-item>
        <el-form-item label="开发者私钥" prop="rsaPrivateKey" class="prompt addHeight">
          <el-input type="textarea" v-model="ruleForm2.rsaPrivateKey" placeholder="请输入开发者私钥"></el-input>
          <div class="black-prompt">
            <span>请填写开发者私钥去头去尾去回车，一行字符串</span>
          </div>
        </el-form-item>
        <el-form-item label="支付宝公钥" prop="alipayrsaPublicKey" class="prompt addHeight">
          <el-input type="textarea" v-model="ruleForm2.alipayrsaPublicKey" placeholder="请输入支付宝公钥"></el-input>
          <div class="black-prompt">
            <span>填写支付宝公钥，一行字符串</span>
          </div>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm2')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>

      <el-form v-if="this.$route.query.id == 8" :model="ruleForm3" :rules="rules3" ref="ruleForm3"  class="tt-form" label-width="210px">
        <el-form-item required label="是否启用" prop="status">
          <el-switch v-model="ruleForm3.status" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
          </el-switch>
        </el-form-item>
        <el-form-item label="头条小程序AppId" prop="ttAppid">
          <el-input v-model="ruleForm3.ttAppid" placeholder="请输入头条小程序AppId"></el-input>
        </el-form-item>
        <el-form-item label="头条小程序秘钥" prop="ttAppSecret">
          <el-input v-model="ruleForm3.ttAppSecret" placeholder="请输入头条小程序秘钥"></el-input>
        </el-form-item>
        <el-form-item label="支付宝回调路径" prop="notify_url" class="prompt">
          <el-input v-model="ruleForm3.notify_url" placeholder="请输入支付宝回调路径"></el-input>
          <div class="red-prompt">
            <span>注：回调路径默认为 https://xiaochengxu.houjiemeishi.com/V3/notify_url.php</span>
          </div>
        </el-form-item>
        <el-form-item label="头条支付商户ID" prop="ttshid">
          <el-input v-model="ruleForm3.ttshid" placeholder="请输入头条支付商户ID"></el-input>
        </el-form-item>
        <el-form-item label="头条支付appid" prop="ttpayappid" class="prompt">
          <el-input v-model="ruleForm3.ttpayappid" placeholder="请输入头条支付appid"></el-input>
          <div class="red-prompt">
            <span>注：与上面的小程序appid不是同一个,在头条商户支付页面获取。</span>
          </div>
        </el-form-item>
        <el-form-item label="头条支付秘钥" prop="ttpaysecret" class="prompt">
          <el-input v-model="ruleForm3.ttpaysecret" placeholder="请输入头条支付秘钥"></el-input>
          <div class="red-prompt">
            <span>注：与上面的小程序秘钥不是同一个,在头条商户支付页面获取。</span>
          </div>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm3')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>

      <el-form v-if="this.$route.query.id == 9" :model="ruleForm4" :rules="rules4" ref="ruleForm4" class="bd-form" label-width="210px">
        <el-form-item required label="是否启用" prop="status">
          <el-switch v-model="ruleForm4.status" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
          </el-switch>
        </el-form-item>
        <el-form-item label="百度小程序AppKey" prop="bdmpappid">
          <el-input v-model="ruleForm4.bdmpappid" placeholder="请输入百度小程序AppKey"></el-input>
        </el-form-item>
        <el-form-item label="百度小程序AppSecret" prop="bdmpappsk">
          <el-input v-model="ruleForm4.bdmpappsk" placeholder="请输入百度小程序AppSecret"></el-input>
        </el-form-item>
        <el-form-item label="支付APPID" prop="appid">
          <el-input v-model="ruleForm4.appid" placeholder="请输入支付APPID"></el-input>
        </el-form-item>
        <el-form-item label="支付APPKEY" prop="appkey">
          <el-input v-model="ruleForm4.appkey" placeholder="请输入支付APPKEY"></el-input>
        </el-form-item>
        <el-form-item label="dealld" prop="dealId">
          <el-input v-model="ruleForm4.dealId" placeholder="请输入dealld"></el-input>
        </el-form-item>
        <el-form-item label="开发者私钥" prop="rsaPrivateKey" class="prompt addHeight">
          <el-input type="textarea" v-model="ruleForm4.rsaPrivateKey" placeholder="请输入开发者私钥"></el-input>
          <div class="black-prompt">
            <span>填写百度开发者私钥（文档里面直接复制粘贴）</span>
          </div>
        </el-form-item>
        <el-form-item label="平台公钥" prop="rsaPublicKey" class="prompt addHeight">
          <el-input type="textarea" v-model="ruleForm4.rsaPublicKey" placeholder="请输入平台公钥"></el-input>
          <div class="black-prompt">
            <span>填写平台公钥，一行字符串</span>
          </div>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm4')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>

      <el-form v-if="this.$route.query.id == 3" :model="ruleForm5" :rules="rules4" ref="ruleForm5" class="bd-form" label-width="210px">
        <el-form-item required label="是否启用" prop="status">
          <el-switch v-model="ruleForm5.status" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
          </el-switch>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm5')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import parameterModify from '@/webManage/js/mall/payManagement/parameterModify'
export default parameterModify
</script>

<style scoped lang="less">
@import '../../../../webManage/css/mall/payManagement/parameterModify.less';
</style>