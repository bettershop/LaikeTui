import cascade from '@/api/publics/cascade'
import { addMch } from '@/api/goods/goodsList'
import { setStorage, getStorage } from "@/utils/storage";
export default {
    name: 'addStore',

    data() {
        return {
            ruleForm: {
                logo: '',
				name: '',
				shop_information: '',
				shop_range: '',
				// community_id: '',
				// adminid: '',
				realname: '',
				ID_number: '',
				tel: '',
				sheng: '',
                shi: '',
                xian: '',
				address: "",
				shop_nature: '1',
				business_license: "",
            },
              
            rules: {
                logo: [{
                    required: true,
                    message: '请选择店铺LOGO',
                    trigger: 'change'
                }],
            name: [{
                    required: true,
                    message: '请输入店铺名称',
                    trigger: 'blur'
                }],
            shop_information: [{
                    required: true,
                    message: '请输入店铺简介',
                    trigger: 'blur'
                }],
            shop_range: [{
                    required: true,
                    message: '请输入店铺经营范围',
                    trigger: 'blur'
                }],
            // community_id: [{
            //     required: true,
            //     message: '请选择所在社区',
            //     trigger: 'change'
            // }],
            // adminid: [{
            //     required: true,
            //     message: '请选择物业管理员',
            //     trigger: 'change'
            // }],
            realname: [{
                    required: true,
                    message: '请输入用户姓名',
                    trigger: 'blur'
                }
            ],
            ID_number: [{
                    required: true,
                    message: '请输入身份证号',
                    trigger: 'blur'
                }
            ],
            tel: [{
                    required: true,
                    message: '请输入联系电话',
                    trigger: 'blur'
                }
            ],
            sheng: [{
                required: true,
                message: '请选择省或直辖市',
                trigger: 'change'
            }],
            address:[{
                    required: true,
                    message: '请输入详细地址',
                    trigger: 'blur'
                }]	
            },

            //省市级联集
            shengList: {},
            shiList: {},
            xianList: {}
        }
    },

    created() {
        this.getSheng()
    },

    methods: {
        // 获取省级
        async getSheng() {
            const res = await cascade.getSheng()
            this.shengList = res.data.data
        },

        // 获取市级
        async getShi(sid, flag) {
            const res = await cascade.getShi(sid)
            this.shiList = res.data.data
            if (!flag) {
                this.ruleForm.shi = "";
                this.ruleForm.xian = "";
            }
        },

        // 获取县级
        async getXian(sid, flag) {
            const res = await cascade.getXian(sid)
            this.xianList = res.data.data
            if (!flag) {
                this.ruleForm.xian = "";
            }
        },

        //省市级联回显
        async cascadeAddress() {
            //省市级联
            for (const sheng of this.shengList) {
                if (sheng.g_CName === this.ruleForm.sheng) {
                    await this.getShi(sheng.groupID, true);
                        for (const shi of this.shiList) {
                            if (shi.g_CName === this.ruleForm.shi) {
                                await this.getXian(shi.groupID, true);
                                break;
                            }
                        }
                    break;
                }
            }
        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm);
              if (valid) {
                try {
                    addMch({
                        api: 'admin.goods.addMch',
                        logo: this.ruleForm.logo,
                        name: this.ruleForm.name,
                        shop_information: this.ruleForm.shop_information,
                        shop_range: this.ruleForm.shop_range,
                        realname: this.ruleForm.realname,
                        ID_number: this.ruleForm.ID_number,
                        tel: this.ruleForm.tel,
                        city_all: `${this.ruleForm.sheng}-${this.ruleForm.shi}-${this.ruleForm.xian}`,
                        address: this.ruleForm.address,
                        shop_nature: this.ruleForm.shop_nature,
                        business_license: this.ruleForm.business_license
                    }).then(res => {
                        console.log(res);
                        if(res.data.code == '200') {
                            this.$message({
                                message: '添加成功',
                                type: 'success',
                                offset: 100
                            })
                            const laike_admin_uaerInfo = getStorage('laike_admin_uaerInfo')
                            laike_admin_uaerInfo.mchId = res.data.data.mchId
                            setStorage('laike_admin_uaerInfo',laike_admin_uaerInfo)
                            this.$router.go(-1)
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