import { getSystemIndex, updateRefundService } from '@/api/mall/systemManagement'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
export default {
    name: 'afterSales',
    components: {
        VueEditor
    },
    data() {
        return {
            radio1: '售后服务',
            ruleForm: {
                return_policy: '',
                cancellation_order: '',
                refund_process: '',
                refund_instructions: ''
            },

        }
    },

    created() {
        this.getSystemIndexs()
    },

    methods: {
        async getSystemIndexs() {
            const res = await getSystemIndex({
                api: 'admin.system.getSystemIndex',
            })
            let systemInfo = res.data.data.data
            this.ruleForm.return_policy = systemInfo.return_policy
            this.ruleForm.cancellation_order = systemInfo.cancellation_order
            this.ruleForm.refund_process = systemInfo.refund_process
            this.ruleForm.refund_instructions = systemInfo.refund_instructions
            console.log(res);
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

        submitForm() {
            updateRefundService({
                api: 'admin.system.updateRefundService',
                refundPolicy: this.ruleForm.return_policy,
                cancelOrderno: this.ruleForm.cancellation_order,
                refundMoney: this.ruleForm.refund_process,
                refundExplain: this.ruleForm.refund_instructions
            }).then(res => {
                if(res.data.code == '200') {
                  this.getSystemIndexs()
                  console.log(res);
                  this.$message({
                    type: 'success',
                    message: '修改成功!',
                    offset: 100
                  });
                }
            })
        }
    }
}