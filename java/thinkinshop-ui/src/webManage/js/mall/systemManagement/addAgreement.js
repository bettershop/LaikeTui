import { addAgreement } from '@/api/mall/systemManagement'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
export default {
    name: 'addAgreement',

    components: {
        VueEditor
    },

    data() {
        return {
            radio1: '关于我们',
            ruleForm: {
                agreement_title: '',
                protocol_type: 0,
                user_agreement: ''
            },

            protocolTypeList: [
                {
                    value: 0,
                    name: '注册'
                },
                {
                    value: 2,
                    name: ' 隐私协议'
                },
                {
                    value: 1,
                    name: '店铺申请'
                }
            ]
        }
    },

    watch: {
        'ruleForm.protocol_type': {
            handler() {
                this.ruleForm.user_agreement = ''
            }
        }
    },

    methods: {
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
            addAgreement({
                api: 'admin.system.addAgreement',
                title: this.ruleForm.agreement_title,
                type: this.ruleForm.protocol_type,
                content: this.ruleForm.user_agreement
            }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.$message({
                    type: 'success',
                    message: '添加成功!',
                    offset: 100
                  });
                  this.$router.go(-1)
                }
            })
        }
    }
}