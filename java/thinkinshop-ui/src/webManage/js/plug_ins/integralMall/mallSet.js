import { getConfigInfo, addConfigInfo } from '@/api/plug_ins/integralMall'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
export default {
    name: "mallSet",
    components: {
        VueEditor
    },

    data() {
        return {
            radio1:'3',

            ruleForm: {
                isOpen: 1,
                integral_proportion: '',
                issue_time: 0,
                overdue_set: '',
                slideshow: '',
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
                integral_proportion: [
                    { required: true, message: '请填写购物赠送积分比例', trigger: 'blur' }
                ],
                overdue_set: [
                    { required: true, message: '请填写购物赠送积分比例', trigger: 'blur' }
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
            },

            issueTimeList: [
                {
                    value: 0,
                    name: '收货后'
                },
                {
                    value: 1,
                    name: '付款后'
                },
            ],
        }
    },

    created() {
        this.getConfigInfos()
    },

    methods: {
        async getConfigInfos() {
            const res = await getConfigInfo({
                api: 'admin.integral.getConfigInfo'
            })
            console.log(res);
            const Config = res.data.data.config

            this.ruleForm.isOpen = Config.status
            this.ruleForm.slideshow = Config.bg_img

            this.ruleForm.integral_proportion = Config.proportion
            this.ruleForm.issue_time = Config.give_status
            this.ruleForm.overdue_set = Config.overdue_time / 86400

            this.ruleForm.package_mail = Config.package_settings
            this.ruleForm.package_num = Config.same_piece
            this.ruleForm.automatic_time = Config.auto_the_goods / 86400
            this.ruleForm.failure_time = Config.order_failure / 3600
            this.ruleForm.afterSales_time = Config.order_after / 86400
            this.ruleForm.remind_day = parseInt(Math.floor(Config.deliver_remind / 86400))
            this.ruleForm.remind_hours = Math.floor(((Config.deliver_remind - this.ruleForm.remind_day*3600*24) % 86400) / 3600)
            this.ruleForm.auto_remind_day = Config.auto_good_comment_day / 86400
            this.ruleForm.content = Config.content
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
                  addConfigInfo({
                    api: 'admin.integral.addConfigInfo',
                    status: this.ruleForm.isOpen,
                    imgUrls: this.ruleForm.slideshow,

                    proportion: this.ruleForm.integral_proportion,
                    giveStatus: this.ruleForm.issue_time,
                    overdueTime: parseInt(this.ruleForm.overdue_set) * 86400,

                    isFreeShipping: this.ruleForm.package_mail,
                    goodsNum: Math.round(this.ruleForm.package_num),
                    autoReceivingGoodsDay: Math.round(this.ruleForm.automatic_time),
                    orderInvalidTime: Math.round(this.ruleForm.failure_time),
                    returnDay: Math.round(this.ruleForm.afterSales_time),
                    deliverRemind: parseInt(this.ruleForm.remind_day) == 0 ? parseInt(this.ruleForm.remind_hours) * 3600 : parseInt(this.ruleForm.remind_day) * 86400  + parseInt(this.ruleForm.remind_hours) * 3600,
                    autoCommentDay: Math.round(this.ruleForm.auto_remind_day),
                    content: this.ruleForm.content
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