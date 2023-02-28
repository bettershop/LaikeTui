import { getSecConfig, setSecConfig } from '@/api/plug_ins/seckill'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'

export default {
    name: 'seckillSet',
    components: {
        VueEditor
    },
    data() {
        return {
            radio1:'4',

            ruleForm: {
                isOpen: 1,
                limit_num: 0,
                set_trailer: 0,
                trailen_time: 1,
                activity_push: '',
                package_mail: 0,
                package_num: '',
                automatic_time: '',
                failure_time: '',
                afterSales_time: '',
                remind_day: '',
                remind_hours: '',
                auto_remind_day: '',
                content: ''
            },

            rules: {
                limit_num: [
                    { required: true, message: '请填写默认限购数量', trigger: 'blur' }
                ],
                activity_push: [
                    { required: true, message: '请填写活动消息推送', trigger: 'blur' }
                ],
                automatic_time: [
                    { required: true, message: '请填写自动收货时间', trigger: 'blur' }
                ],
                failure_time: [
                    { required: true, message: '请填写订单失效时间', trigger: 'blur' }
                ],
                afterSales_time: [
                    { required: true, message: '请填写订单售后时间', trigger: 'blur' }
                ],
                remind_day: [
                    { required: true, message: '请填写提醒限制', trigger: 'blur' }
                ],
                auto_remind_day: [
                    { required: true, message: '请填写提醒限制', trigger: 'blur' }
                ],
            }
        }
    },

    created() {
        this.getSecConfigs()
    },

    methods: {
        async getSecConfigs() {
            const res = await getSecConfig({
                api: 'admin.sec.label.getSecConfig'
            })
            console.log(res);
            const Config = res.data.data.res

            this.ruleForm.isOpen = Config.is_open
            this.ruleForm.limit_num = Config.buy_num
            this.ruleForm.set_trailer = Config.is_herald
            this.ruleForm.trailen_time = Config.heraldTime / 3600
            this.ruleForm.activity_push = Config.remind / 60
            this.ruleForm.package_mail = Config.package_settings
            this.ruleForm.package_num = Config.same_piece
            this.ruleForm.automatic_time = Config.auto_the_goods / 86400
            this.ruleForm.failure_time = Config.order_failure / 3600
            this.ruleForm.afterSales_time = Config.order_after / 86400
            this.ruleForm.remind_day = parseInt(Math.floor(Config.deliver_remind / 86400))
            this.ruleForm.remind_hours = Math.floor(((Config.deliver_remind - this.ruleForm.remind_day*3600*24) % 86400) / 3600)
            this.ruleForm.auto_remind_day = Config.auto_good_comment_day / 86400
            this.ruleForm.content = Config.rule
        },

        handleImageAdded(file, Editor, cursorLocation, resetUploader) {
            let random_name = new Date().getTime() + '.' + file.name.split('.').pop()
            const client = new OSS({
              region: "oss-cn-shenzhen.aliyuncs.com",
              secure: true,
              endpoint: 'oss-cn-shenzhen.aliyuncs.com',
              accessKeyId: "LTAI4Fm8MFnadgaCdi6GGmkN",
              accessKeySecret: "NhBAJuGtx218pvTE4IBTZcvRzrFrH4",
              bucket: 'laikeds'
            });
            client.multipartUpload(random_name, file)
            .then(res => {
              console.log(res);
              Editor.insertEmbed(cursorLocation, 'image', res.res.requestUrls[0])
              resetUploader()
            })
            .catch(err => {
              console.log(err)
            })
        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm);
              if (valid) {
                try {
                  setSecConfig({
                    api: 'admin.sec.label.setSecConfig',
                    isOpen: this.ruleForm.isOpen,
                    buyNum: Math.round(this.ruleForm.limit_num),
                    isHerald: this.ruleForm.set_trailer,
                    heraldTime: Math.round(this.ruleForm.trailen_time),
                    remind: Math.round(this.ruleForm.activity_push),
                    isFreeShipping: this.ruleForm.package_mail,
                    goodsNum: Math.round(this.ruleForm.package_num),
                    autoReceivingGoodsDay: Math.round(this.ruleForm.automatic_time),
                    orderInvalidTime: Math.round(this.ruleForm.failure_time),
                    returnDay: Math.round(this.ruleForm.afterSales_time),
                    deliverRemind: parseInt(this.ruleForm.remind_day) == 0 ? parseInt(this.ruleForm.remind_hours) / 24 : parseInt(this.ruleForm.remind_day) + parseInt(this.ruleForm.remind_hours) / 24,
                    autoCommentDay: Math.round(this.ruleForm.auto_remind_day),
                    rule: this.ruleForm.content
                  }).then(res => {
                    if(res.data.code == '200') {
                      this.$message({
                        message: '成功',
                        type: 'success',
                        offset: 100
                      })
                      this.getSecConfigs()
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

        oninput2(num) {
            var str = num
            str = str.replace(/[^\.\d]/g,'');
            str = str.replace('.','');

            return str
        },
    }
}