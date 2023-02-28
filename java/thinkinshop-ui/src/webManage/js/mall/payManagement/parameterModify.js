import { paymentParmaInfo, setPaymentParma } from '@/api/mall/payManagement'
import Config from "@/packages/apis/Config";
import { getStorage } from '@/utils/storage'
export default {
    name: 'parameterModify',

    data() {
      return {
        // 微信支付
        ruleForm1: {
          status: '',
          appid: '',
          appsecret: '',
          notify_url: '',
          mch_id: '',
          mch_key: '',
          cert_pem: '',
          key_pem: '',
          cert_p12: '',
        },
        cert_p12_list:[],
        actionUrl: Config.baseUrl,
        rules1: {
          appid: [
            { required: true, message: '请填写appid', trigger: 'blur' }
          ],
          appsecret: [
            { required: true, message: '请填写appsecret', trigger: 'blur' }
          ],
          notify_url: [
            { required: true, message: '请填写回调路径', trigger: 'blur' }
          ],
          mch_id: [
            { required: true, message: '请填写微信支付商户号', trigger: 'blur' }
          ],
          mch_key: [
            { required: true, message: '请填写微信支付Api密钥', trigger: 'blur' }
          ],
          cert_pem: [
            { required: true, message: '请填写微信支付apiclient_cert.pem', trigger: 'blur' }
          ],
          key_pem: [
            { required: true, message: '请填写菜单名称', trigger: 'blur' }
          ],
        },

        // 支付宝支付
        ruleForm2: {
          status: '',
          appid: '',
          notify_url: '',
          signType: 'RSA',
          encryptKey:'',
          rsaPrivateKey: '',
          alipayrsaPublicKey: '',
        },
        rules2: {
          appid: [
            { required: true, message: '请填写AppId', trigger: 'blur' }
          ],
          notify_url: [
            { required: true, message: '请填写支付宝回调路径', trigger: 'blur' }
          ],
          signType: [
            { required: true, message: '请选择签名类型', trigger: 'blur' }
          ],
          encryptKey: [
            { required: true, message: '请填写加密密钥', trigger: 'blur' }
          ],
          rsaPrivateKey: [
            { required: true, message: '请填写开发者私钥', trigger: 'blur' }
          ],
          alipayrsaPublicKey: [
            { required: true, message: '请填写支付宝公钥', trigger: 'blur' }
          ],
        },

        // 头条支付
        ruleForm3: {
          status: '',
          ttAppid: '',
          ttAppSecret: '',
          notify_url: '',
          ttshid:'',
          ttpayappid: '',
          ttpaysecret: '',
        },
        rules3: {
          ttAppid: [
            { required: true, message: '请填写ttAppid', trigger: 'blur' }
          ],
          ttAppSecret: [
            { required: true, message: '请填写头条小程序秘钥', trigger: 'blur' }
          ],
          notify_url: [
            { required: true, message: '请填写回调路径', trigger: 'blur' }
          ],
          ttshid: [
            { required: true, message: '请填写头条支付商户ID', trigger: 'blur' }
          ],
          ttpayappid: [
            { required: true, message: '请填写头条支付appid', trigger: 'blur' }
          ],
          ttpaysecret: [
            { required: true, message: '请填写头条支付秘钥', trigger: 'blur' }
          ],
        },

        // 百度支付
        ruleForm4: {
          status: '',
          bdmpappid: '',
          bdmpappsk: '',
          appid: '',
          appkey: '',
          dealId: 'RSA',
          rsaPrivateKey:'',
          rsaPublicKey: '',
        },
        rules4: {
          bdmpappid: [
            { required: true, message: '请填写AppKey', trigger: 'blur' }
          ],
          bdmpappsk: [
            { required: true, message: '请填写bdmpappsk', trigger: 'blur' }
          ],
          appid: [
            { required: true, message: '请填写appid', trigger: 'blur' }
          ],
          appkey: [
            { required: true, message: '请填写支付APPKEY', trigger: 'blur' }
          ],
          dealId: [
            { required: true, message: '请填写dealld', trigger: 'blur' }
          ],
          rsaPrivateKey: [
            { required: true, message: '请填写开发者私钥', trigger: 'blur' }
          ],
          rsaPublicKey: [
            { required: true, message: '请填写平台公钥', trigger: 'blur' }
          ],
        },

        // 钱包
        ruleForm5: {
          status: '',
        },
      }
    },

    computed: {
      uploadData() {
        {
          return {
            api: 'admin.payment.uploadCertP12',
            storeId: getStorage('laike_admin_uaerInfo').storeId,
            accessId: this.$store.getters.token
          }
        }
      }
    },

    beforeRouteLeave (to, from, next) {        
      if (to.name == 'payList') {
        to.params.dictionaryNum = this.$route.query.dictionaryNum
        to.params.pageSize = this.$route.query.pageSize
      }   
      next();
    },
  
    created() {
      this.paymentParmaInfos()
    },

    methods: {
      handleAvatarSuccess(res, file) {
        console.log(res);
        this.ruleForm1.cert_p12 = res.data.savePath
      },
      async paymentParmaInfos() {
        const res = await paymentParmaInfo({
          api: 'admin.payment.paymentParmaInfo',
          id: this.$route.query.id
        })
        console.log(res);
        if(this.$route.query.id == 4 || this.$route.query.id == 5 || this.$route.query.id == 6 || this.$route.query.id == 10 || this.$route.query.id == 12) {
          this.ruleForm1.status = res.data.data.config.status,
          this.ruleForm1.appid = res.data.data.config.appid,
          this.ruleForm1.appsecret = res.data.data.config.appsecret,
          this.ruleForm1.notify_url = res.data.data.config.notify_url,
          this.ruleForm1.mch_id = res.data.data.config.mch_id,
          this.ruleForm1.mch_key = decodeURIComponent(res.data.data.config.mch_key),
          this.ruleForm1.cert_pem = decodeURIComponent(res.data.data.config.cert_pem),
          this.ruleForm1.key_pem = decodeURIComponent(res.data.data.config.key_pem);
          if(res.data.data.config.cert_p12){
            this.ruleForm1.cert_p12 = res.data.data.config.cert_p12,
            this.cert_p12_list = [ {name:'apiclient_cert.p12',url:res.data.data.config.cert_p12}];
          }
        } else if(this.$route.query.id == 1 || this.$route.query.id == 7 || this.$route.query.id == 11 || this.$route.query.id == 13) {
          if(this.$route.query.id !== 7){
            this.ruleForm2.status = res.data.data.config.status,
            this.ruleForm2.appid = res.data.data.config.appid,
            this.ruleForm2.notify_url = res.data.data.config.notify_url,
            this.ruleForm2.signType = decodeURIComponent(res.data.data.config.signType),
            this.ruleForm2.encryptKey = decodeURIComponent(res.data.data.config.encryptKey),
            this.ruleForm2.rsaPrivateKey = decodeURIComponent(res.data.data.config.rsaPrivateKey),
            this.ruleForm2.alipayrsaPublicKey = decodeURIComponent(res.data.data.config.alipayrsaPublicKey)
          } else {
            this.ruleForm2.status = res.data.data.config.status,
            this.ruleForm2.appid = res.data.data.config.appid,
            this.ruleForm2.notify_url = res.data.data.config.notify_url,
            this.ruleForm2.signType = decodeURIComponent(res.data.data.config.signType),
            this.ruleForm2.rsaPrivateKey = decodeURIComponent(res.data.data.config.rsaPrivateKey),
            this.ruleForm2.alipayrsaPublicKey = decodeURIComponent(res.data.data.config.alipayrsaPublicKey)
          }
        } else if(this.$route.query.id == 8) {
          this.ruleForm3.status = res.data.data.config.status,
          this.ruleForm3.ttAppid = res.data.data.config.ttAppid,
          this.ruleForm3.ttAppSecret = res.data.data.config.ttAppSecret,
          this.ruleForm3.notify_url = res.data.data.config.notify_url,
          this.ruleForm3.ttshid = res.data.data.config.ttshid,
          this.ruleForm3.ttpayappid = res.data.data.config.ttpayappid,
          this.ruleForm3.ttpaysecret = res.data.data.config.ttpaysecret
        } else if(this.$route.query.id == 9) {
          this.ruleForm4.status = res.data.data.config.status,
          this.ruleForm4.bdmpappid = res.data.data.config.bdmpappid,
          this.ruleForm4.bdmpappsk = res.data.data.config.bdmpappsk,
          this.ruleForm4.appid = res.data.data.config.appid,
          this.ruleForm4.appkey = res.data.data.config.appkey,
          this.ruleForm4.dealId = res.data.data.config.dealId,
          this.ruleForm4.rsaPrivateKey = res.data.data.config.rsaPrivateKey,
          this.ruleForm4.rsaPublicKey = res.data.data.config.rsaPublicKey
        }
      },

      submitForm(formName) {
          this.$refs[formName].validate(async (valid) => {
            if (valid) {
              try {
                let json = {}
                if(this.$route.query.id == 4 || this.$route.query.id == 5 || this.$route.query.id == 6 || this.$route.query.id == 10 || this.$route.query.id == 12) {
                  
                  json.status = this.ruleForm1.status,
                  json.appid = this.ruleForm1.appid,
                  json.appsecret = this.ruleForm1.appsecret,
                  json.notify_url = this.ruleForm1.notify_url,
                  json.mch_id = this.ruleForm1.mch_id,
                  json.mch_key = encodeURIComponent(this.ruleForm1.mch_key),
                  json.cert_pem = encodeURIComponent(this.ruleForm1.cert_pem),
                  json.key_pem = encodeURIComponent(this.ruleForm1.key_pem),
                  json.cert_p12 = this.ruleForm1.cert_p12
                } else if(this.$route.query.id == 1 || this.$route.query.id == 7 || this.$route.query.id == 11 || this.$route.query.id == 13) {
                  
                  if(this.$route.query.id !== 7){
                    json.status = this.ruleForm2.status,
                    json.appid = this.ruleForm2.appid,
                    json.notify_url = this.ruleForm2.notify_url,
                    json.signType = encodeURIComponent(this.ruleForm2.signType),
                    json.encryptKey =encodeURIComponent(this.ruleForm2.encryptKey),
                    json.rsaPrivateKey = encodeURIComponent(this.ruleForm2.rsaPrivateKey),
                    json.alipayrsaPublicKey = encodeURIComponent(this.ruleForm2.alipayrsaPublicKey)
                  } else {
                    json.status = this.ruleForm2.status,
                    json.appid = this.ruleForm2.appid,
                    json.notify_url = this.ruleForm2.notify_url,
                    json.signType = encodeURIComponent(this.ruleForm2.signType),
                    json.rsaPrivateKey = encodeURIComponent(this.ruleForm2.rsaPrivateKey),
                    json.alipayrsaPublicKey = encodeURIComponent(this.ruleForm2.alipayrsaPublicKey)
                  }
                } else if(this.$route.query.id == 8) {
                  json.status = this.ruleForm4.status,
                  json.ttAppid = this.ruleForm4.ttAppid,
                  json.ttAppSecret = this.ruleForm4.ttAppSecret,
                  json.notify_url = this.ruleForm4.notify_url,
                  json.ttshid = this.ruleForm4.ttshid,
                  json.ttpayappid = this.ruleForm4.ttpayappid,
                  json.ttpaysecret = this.ruleForm4.ttpaysecret
                } else if(this.$route.query.id == 9) {
                  json.status = this.ruleForm4.status,
                  json.bdmpappid = this.ruleForm4.bdmpappid,
                  json.bdmpappsk = this.ruleForm4.bdmpappsk,
                  json.appid = this.ruleForm4.appid,
                  json.appkey = this.ruleForm4.appkey,
                  json.dealId = this.ruleForm4.dealId,
                  json.rsaPrivateKey = encodeURIComponent(this.ruleForm4.rsaPrivateKey),
                  json.rsaPublicKey = encodeURIComponent(this.ruleForm4.rsaPublicKey)
                }
                console.log(json);
                setPaymentParma({
                  api: 'admin.payment.setPaymentParma',
                  id: this.$route.query.id,
                  json: json
                }).then(res => {
                  console.log(res);
                  if(res.data.code == '200') {
                    this.$router.go(-1)
                    this.$message({
                      message: '修改成功',
                      type: 'success',
                      offset: 100
                    })
                  }
                })
              } catch (error) {
                this.$message({
                    message: error.message,
                    type: 'error',
                    showClose: true
                })
              }
            } else {
                console.log('error submit!!');
                return false;
            }
          });
      },      
    }
}