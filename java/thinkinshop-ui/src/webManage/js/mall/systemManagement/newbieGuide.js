import { getSystemIndex, updateBeginnerGuide } from '@/api/mall/systemManagement'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
export default {
    name: 'newbieGuide',

    components: {
        VueEditor
    },
    data() {
        return {
            radio1: '新手指南',
            ruleForm: {
                shopping_process: '',
                payment_method: ''
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
            this.ruleForm.shopping_process = systemInfo.shopping_process
            this.ruleForm.payment_method = systemInfo.payment_method
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
            updateBeginnerGuide({
                api: 'admin.system.updateBeginnerGuide',
                shoppingProcess: this.ruleForm.shopping_process,
                payType: this.ruleForm.payment_method
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