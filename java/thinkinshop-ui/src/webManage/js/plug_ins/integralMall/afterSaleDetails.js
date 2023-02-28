import { getRefundList,examine } from '@/api/order/salesReturn'
import { deliveryView } from '@/api/order/orderList'

export default {
    name: 'afterSaleDetails',

    data() {
        return {
            header: {
                'background-color': '#F4F7F9',
                'font-weight': 'bold',
                'border-bottom': '1px solid #E9ECEF',
            },

            goodsDate: [],
            totle_price: '',

            applyInfo: {},
            rdata: [],
            sales_imgs: [],

            id: null,
            toggle: null,

            type: '',

            content: '',

            r_type: '',
            re_type: '',

            courierList: [],

            // 拒绝弹框数据
            dialogVisible3: false,
            ruleForm1: {
                reason: '',
            },
            rules: {
                reason: [
                    { required: true, message: '请填写拒绝理由', trigger: 'blur' }
                ],
            },

            // 退换物流弹框数据
            dialogVisible4: false,
            ruleForm2: {
                kuaidi_name: null,
                kuaidi_no: null,
            },
            rules1: {
                kuaidi_name: [
                    { required: true, message: '请选择快递公司', trigger: 'change' }
                ],
                kuaidi_no: [
                    { required: true, message: '请填写快递单号', trigger: 'blur' }
                ],
            },

        }
    },

    created() {
        this.getRefundLists()
        this.deliveryViews()
    },

    methods: {
        async getRefundLists() {
            const res = await getRefundList({
                api: 'admin.order.getRefundList',
                id: this.$route.query.id
            })
            console.log(res);
            if(this.goodsDate.length == 0) {
                this.goodsDate.push(res.data.data.list)
            }
            this.totle_price = res.data.data.list.total
            this.applyInfo = res.data.data.list
            this.rdata = res.data.data.rdata
            this.sales_imgs = res.data.data.imgs
            this.r_type = res.data.data.list.r_type
            this.re_type = res.data.data.list.re_type
            this.id = res.data.data.list.id
        },

        async deliveryViews() {
            const res = await deliveryView({
                api: 'admin.order.deliveryView',
            })
            this.courierList = res.data.data.express
        },

        // 弹框方法
        dialogShow2(toggle) {
            if(toggle == 2) {
                this.dialogVisible3 = true
                if(this.r_type == 0) {
                    this.type = 10
                } else {
                    this.type = 5
                }
            } else {
                if(this.r_type == 0) {
                    this.type = 1
                    this.$confirm('确定要通过该用户的申请,并让用户寄回?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                      }).then(() => {
                        examine({
                            api: 'admin.order.examine',
                            id: this.id,
                            type: this.type,
                        }).then(res => {
                            console.log(res);
                            if(res.data.code == '200') {
                                this.dialogVisible2 = false
                                this.getRefundLists()
                                this.$message({
                                    type: 'success',
                                    message: '成功!',
                                    offset: 100
                                })
                                this.getRefundLists()
                            }
                        })
                      }).catch(() => {
                        this.$message({
                          type: 'info',
                          message: '已取消删除',
                          offset: 100
                        });          
                    });
                } else {
                    this.dialogVisible4 = true
                    this.type = 11
                }
            }
        },


        handleClose3(done) {
            this.dialogVisible3 = false
            this.$refs['ruleForm1'].clearValidate()
        },

        submitForm2(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm1);
              if (valid) {
                try {
                    examine({
                        api: 'admin.order.examine',
                        id: this.id,
                        text: this.ruleForm1.reason,
                        type: this.type
                    }).then(res => {
                        console.log(res);
                        if(res.data.code == '200') {
                            this.dialogVisible3 = false
                            this.$message({
                                type: 'success',
                                message: '成功!',
                                offset: 100
                            })
                            this.getRefundLists()
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

        // 填写退还物流
        handleClose4(done) {
            this.dialogVisible4 = false
            this.$refs['ruleForm2'].clearValidate()
        },

        submitForm3(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm2);
              if (valid) {
                try {
                    examine({
                        api: 'admin.order.examine',
                        id: this.id,
                        type: this.type,
                        expressId: this.ruleForm2.kuaidi_name,
                        courierNum: this.ruleForm2.kuaidi_no,
                    }).then(res => {
                        console.log(res);
                        if(res.data.code == '200') {
                            this.dialogVisible4 = false
                            this.$message({
                                type: 'success',
                                message: '成功!',
                                offset: 100
                            })
                            this.getRefundLists()
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