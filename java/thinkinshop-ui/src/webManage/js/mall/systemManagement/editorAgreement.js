import { getAgreementIndex, addAgreement } from '@/api/mall/systemManagement'
import { VueEditor } from 'vue2-editor'
import { getStorage } from '@/utils/storage'
import OSS from 'ali-oss'
import $ from 'jquery'

export default {
    name: 'editorAgreement',

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
            ],

            goodsEditorBase: '',
        }
    },

    created() {
        this.getBase()
        this.getAgreementIndexs()
    },

    methods: {
        getBase() {
            if (process.env.NODE_ENV == 'development') {
              this.goodsEditorBase = process.env.VUE_APP_PROXY_API
            } else if (process.env.NODE_ENV == 'production') {
              this.goodsEditorBase = process.env.VUE_APP_BASE_API
            }
        },
        async getAgreementIndexs() {
            const res = await getAgreementIndex({
                api: 'admin.system.getAgreementIndex',
                id: this.$route.query.id
            })
            console.log(res);
            let info = res.data.data.list[0]
            this.ruleForm.agreement_title = info.name,
            this.ruleForm.protocol_type = info.type,
            this.ruleForm.user_agreement = info.content
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
            // addAgreement({
            //     api: 'admin.system.addAgreement',
            //     id: this.$route.query.id,
            //     title: this.ruleForm.agreement_title,
            //     type: this.ruleForm.protocol_type,
            //     content: this.ruleForm.user_agreement
            // }).then(res => {
            //     if(res.data.code == '200') {
            //       console.log(res);
            //       this.$message({
            //         type: 'success',
            //         message: '编辑成功!',
            //         offset: 100
            //       });
            //       this.$router.go(-1)
            //     }
            // })

            $.ajax({
                cache: true,
                type: "POST",
                dataType: "json",
                url: this.goodsEditorBase+"/admin/system/addAgreement",
                data: {
                  // api: 'admin.system.addAgreement',
                  storeId: getStorage('rolesInfo').storeId,
                  accessId: getStorage('laike_admin_uaerInfo').token,
                  storeType: 8,
                  id: this.$route.query.id,
                  title: this.ruleForm.agreement_title,
                  type: this.ruleForm.protocol_type,
                  content: this.ruleForm.user_agreement
                },
                async: true,
                success: (res) => {
                  console.log(res);
                  if(res.code == '200') {
                    this.$message({
                      message: '成功',
                      type: 'success',
                      offset: 100
                    })
                    this.$router.go(-1)
                  } else {
                    this.$message({
                      message: res.message,
                      type: 'error',
                      offset: 100
                    })
                  }
                },
            })
        }
    }
}