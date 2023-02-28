import { getSystemIndex, updateCommonProblem } from '@/api/mall/systemManagement'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
export default {
    name: 'commonProblem',
    components: {
        VueEditor
    },
    data() {
        return {
            radio1: '常见问题',
            ruleForm: {
                after_sales_issues: '',
                payment_issues: ''
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
            this.ruleForm.after_sales_issues = systemInfo.after_sales_issues
            this.ruleForm.payment_issues = systemInfo.payment_issues
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
            updateCommonProblem({
                api: 'admin.system.updateCommonProblem',
                returnProblem: this.ruleForm.after_sales_issues,
                payProblem: this.ruleForm.payment_issues
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