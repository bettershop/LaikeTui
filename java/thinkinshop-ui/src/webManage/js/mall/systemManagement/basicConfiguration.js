import { getSystemIndex, addSystemConfig } from '@/api/mall/systemManagement'
import { getStorage } from '@/utils/storage'

import $ from 'jquery'
export default {
    name: 'basicConfiguration',

    data() {
        return {
            radio1: '基础配置',
            ruleForm: {
                is_Registered: 1,
                companyLogo: '',
                headImg: '',
                h_Address: '',
                front_message: '',
                login_validity: '',
                service: '',
                tx_key: '',
                is_unipush: 1, // unipush推送
                Appkey: '',
                Appid: '',
                MasterECRET: '',
                is_courier: 1, // 是否开启快递100
                api_address: '',
                customer: '',
                authorization: '',
                same_account: 1
            },

            rules:{
                companyLogo:[{required: true, message: '请上传公司LOGO', trigger: 'change'}],
                headImg:[{required: true, message: '请上传微信头像', trigger: 'change'}],
                h_Address:[{required: true, message: '请填写H5地址', trigger: 'blur'}],
                tx_key:[{required: true, message: '请填写开发者密钥', trigger: 'blur'}],
            },

            isRegisteredList: [
                {
                    value: 2,
                    name: '注册'
                },
                {
                    value: 0,
                    name: '免注册'
                }
            ],
            goodsEditorBase: '',
        }
    },

    created() {
        this.getBase()
        this.getSystemIndexs()
    },

    mounted() {
        this.getBase()
    },

    methods: {
        getBase() {
            if (process.env.NODE_ENV == 'development') {
              this.goodsEditorBase = process.env.VUE_APP_PROXY_API
            } else if (process.env.NODE_ENV == 'production') {
              this.goodsEditorBase = process.env.VUE_APP_BASE_API
            }
        },
        async getSystemIndexs() {
            const res = await getSystemIndex({
                api: 'admin.system.getSystemIndex',
            })
            let systemInfo = res.data.data.data
            this.ruleForm.is_Registered = parseInt(systemInfo.is_register),
            this.ruleForm.companyLogo = systemInfo.logo,
            this.ruleForm.headImg = systemInfo.wx_headimgurl,
            this.ruleForm.h_Address = systemInfo.h5_domain,
            this.ruleForm.front_message = systemInfo.message_day,
            this.ruleForm.login_validity = systemInfo.exp_time,
            this.ruleForm.service = systemInfo.customer_service,
            this.ruleForm.tx_key = systemInfo.tencent_key,
            this.ruleForm.is_unipush = systemInfo.is_push, // unipush推送
            this.ruleForm.Appkey = systemInfo.push_Appkey,
            this.ruleForm.Appid = systemInfo.push_Appid,
            this.ruleForm.MasterECRET = systemInfo.push_MasterECRET,
            this.ruleForm.is_courier = systemInfo.is_express, // 是否开启快递100
            this.ruleForm.api_address = systemInfo.express_address,
            this.ruleForm.customer = systemInfo.express_number,
            this.ruleForm.authorization = systemInfo.express_key,
            this.ruleForm.same_account = systemInfo.is_Kicking
            console.log(res);
        },

        getBase() {
            if (process.env.NODE_ENV == 'development') {
              this.goodsEditorBase = process.env.VUE_APP_PROXY_API
            } else if (process.env.NODE_ENV == 'production') {
              this.goodsEditorBase = process.env.VUE_APP_BASE_API
            }
          },

        submitForm(ruleForm) {
            // addSystemConfig({
            //     api: 'admin.system.addSystemConfig',
            //     isRegister: this.ruleForm.is_Registered,
            //     logoUrl: this.ruleForm.companyLogo,
            //     wxHeader: this.ruleForm.headImg,
            //     pageDomain: this.ruleForm.h_Address,
            //     messageSaveDay: this.ruleForm.front_message,
            //     appLoginValid: this.ruleForm.login_validity,
            //     serverClient: this.ruleForm.service,
            //     tencentKey: this.ruleForm.tx_key,
            //     unipush: this.ruleForm.is_unipush,
            //     pushAppkey: this.ruleForm.Appkey,
            //     pushAppid: this.ruleForm.Appid,
            //     pushMasterEcret: this.ruleForm.MasterECRET,
            //     isExpress: this.ruleForm.is_courier,
            //     expressAddress: this.ruleForm.api_address,
            //     expressNumber: this.ruleForm.customer,
            //     expressKey: this.ruleForm.authorization,
            //     isKicking: this.ruleForm.same_account
            // }).then(res => {
            //     if(res.data.code == '200') {
            //       console.log(res);
            //       this.$message({
            //         type: 'success',
            //         message: '成功!',
            //         offset: 100
            //       });
            //     }
            // })

            this.$refs[ruleForm].validate(async (valid) => {
              if(valid){
                try{
                  $.ajax({
                    cache: true,
                    type: "POST",
                    dataType: "json",
                    url: this.goodsEditorBase + "/admin/system/addSystemConfig",
                    data: {
                      storeId: getStorage('rolesInfo').storeId,
                      accessId: getStorage('laike_admin_uaerInfo').token,
                      storeType: 8,
                      // api: 'admin.system.addSystemConfig',
                        isRegister: this.ruleForm.is_Registered,
                        logoUrl: this.ruleForm.companyLogo,
                        wxHeader: this.ruleForm.headImg,
                        pageDomain: this.ruleForm.h_Address,
                        messageSaveDay: this.ruleForm.front_message,
                        appLoginValid: this.ruleForm.login_validity,
                        serverClient: this.ruleForm.service,
                        tencentKey: this.ruleForm.tx_key,
                        unipush: this.ruleForm.is_unipush,
                        pushAppkey: this.ruleForm.Appkey,
                        pushAppid: this.ruleForm.Appid,
                        pushMasterEcret: this.ruleForm.MasterECRET,
                        isExpress: this.ruleForm.is_courier,
                        expressAddress: this.ruleForm.api_address,
                        expressNumber: this.ruleForm.customer,
                        expressKey: this.ruleForm.authorization,
                        isKicking: this.ruleForm.same_account
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
                        this.getSystemIndexs()
                      } else {
                        this.$message({
                          message: res.message,
                          type: 'error',
                          offset: 100
                        })
                      }
                    },
                  })
                }catch(err){
                  this.$message({
                    message: err,
                    type: 'error',
                    offset: 100
                  })
                }
              }
                 
            });
        }
    }
}