import { editOrderView, saveEditOrder } from '@/api/order/orderList'
import cascade from '@/api/publics/cascade'
import {isEmpty} from "element-ui/src/utils/util";

export default {
    name: 'editorOrder',
    data() {
        return {
            dataInfo: null,
            goodsTables: [],
            totleInfo: null,

            ruleForm: {
                status: null,
                name: '',
                mobile: '',
                sheng: '',
                shi: '',
                xian: '',
                r_address: '',
                remarks: '',
                pay_price: '',
            },

            rules:{
                name:[{required: true, message: '请填写收货人', trigger: 'blur'}],
                mobile:[{required: true, validator: (rule, value, callback) => {
                    if (!value) {
                      callback(new Error('请输入联系方式'))
                    } else {
                      const reg = /^1[3|4|5|6|7|8][0-9]\d{8}$/
                      if (reg.test(value)) {
                        callback()
                      } else {
                        return callback(new Error('请输入正确的电话'))
                      }
                    }
                  }, trigger: 'blur'}],
                xian:[{required: true, message: '请选择联系地址', trigger: 'change'}],
                r_address:[{required: true, message: '请填写详细地址', trigger: 'blur'}]
            },

            stateList: [
                {
                    value: '0',
                    label: '待付款'
                },
                {
                    value: '1',
                    label: '待发货'
                }
            ],// 订单状态

            //省市级联集
            shengList: {},
            shiList: {},
            xianList: {}
        }
    },

    created() {
        this.getSheng()
        this.editOrderViews().then(() => {
            this.cascadeAddress();
        })
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'orderLists') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
          to.params.radio1 = this.$route.query.radio1
        }   
        next();
    },

    methods: {
        async editOrderViews() {
            const res = await editOrderView({
                api: 'admin.order.editOrderView',
                sNo: this.$route.query.no
            })
            console.log(res);
            this.dataInfo = res.data.data.data
            this.goodsTables = res.data.data.detail
            this.totleInfo = res.data.data

            this.ruleForm.name = res.data.data.data.name,
            this.ruleForm.mobile = res.data.data.data.mobile,
            this.ruleForm.sheng = res.data.data.data.sheng,
            this.ruleForm.shi = res.data.data.data.shi,
            this.ruleForm.xian = res.data.data.data.xian,
            this.ruleForm.r_address = res.data.data.data.r_address,
            this.ruleForm.remarks = Object.keys(res.data.data.data.remarks).length === 0 ? '' : ''

            if(this.dataInfo.status == '待付款') {
                this.ruleForm.pay_price = this.totleInfo.pay_price
                this.ruleForm.status = '0'
            }
        },

        getSource(value) {
            if(value == '1') {
                return '小程序'
            } else if(value == '2') {
                return 'APP'
            } else if(value == '3') {
                return '支付宝小程序'
            } else if(value == '4') {
                return '头条小程序'
            } else if(value == '5') {
                return '百度小程序'
            } else if(value == '6') {
                return 'pc端'
            } else if(value == '7') {
                return 'H5移动端'
            }else {
                return '闪购'
            }
        },
        

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
                    saveEditOrder({
                        api: 'admin.order.saveEditOrder',
                        orderNo: this.$route.query.no,
                        userName: this.ruleForm.name,
                        tel: this.ruleForm.mobile,
                        shen: this.ruleForm.sheng,
                        shi: this.ruleForm.shi,
                        xian: this.ruleForm.xian,
                        address: this.ruleForm.r_address,
                        remarks: this.ruleForm.remarks,
                        orderStatus: this.dataInfo.status == '待付款' ? this.ruleForm.status : null,
                        orderAmt: this.dataInfo.status == '待付款' ? this.ruleForm.spz_price : null
                    }).then(res => {
                        console.log(res);
                        if(res.data.code == '200') {
                            this.$message({
                                message: '编辑成功',
                                type: 'success',
                                offset:100
                            })
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