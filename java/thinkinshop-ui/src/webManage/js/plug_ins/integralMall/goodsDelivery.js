import { deliveryView, deliverySave } from '@/api/order/orderList'

export default {
    name: 'goodsDelivery',

    data() {
        return {
            goodsTables: [],
            goodsNum: null,
            flag: false,
            courierList: [],
            id: null,
            // 弹框数据
            dialogVisible: false,
            ruleForm2: {
                kuaidi_name: null,
                kuaidi_no: null,
            },
            rules2: {
                kuaidi_name: [
                    { required: true, message: '请填写快递名称', trigger: 'change' }
                ],
                kuaidi_no: [
                    { required: true, message: '请填写快递单号', trigger: 'blur' }
                ],
            }
        }
    },

    created() {
        this.deliveryViews()
    },

    methods: {
        async deliveryViews() {
            const res = await deliveryView({
                api: 'admin.order.deliveryView',
                sNo: this.$route.query.no
            })
            this.goodsTables = res.data.data.goods
            this.goodsNum = res.data.data.goods.num
            this.courierList = res.data.data.express.list
            // this.id = res.data.data.goods[0].id
            this.id = res.data.data.goods.map(item => {
                return item.id
            })
        },

        handleSelectionChange(val) {
            console.log(val);
            if(val.length !== 0) {
                this.flag = true
            } else {
                this.flag = false
            }
        },

        // 弹框方法
        dialogShow() {
            if(this.flag) {
                this.dialogVisible = true
            } else {
                this.$message({
                    message: '请选择至少一款产品',
                    type: 'error',
                    offset: 100
                })
            }
        },
        
        handleClose(done) {
            this.dialogVisible = false
            this.$refs.ruleForm2.clearValidate()
        },

        // 发货
        determine(formName2) {
            this.$refs[formName2].validate(async (valid) => {
              console.log(this.ruleForm2);
              if (valid) {
                try {
                    if(this.ruleForm2.kuaidi_no.length < 10){
                        this.$message({
                            message:'快递单号输出格式有误，必须大于等于10哦',
                            type:'warning',
                            offset:100
                        })
                        return
                    } else {
                        deliverySave({
                            api: 'admin.order.deliverySave',
                            exId: this.ruleForm2.kuaidi_name,
                            exNo: this.ruleForm2.kuaidi_no,
                            orderDetailIds: this.id.join(',')
                        }).then(res => {
                            if(res.data.code == '200') {
                                this.$message({
                                    message: '发货成功',
                                    type: 'success',
                                    offset:100
                                })
                                this.$router.go(-1)
                            }
                            console.log(res);
                        })
                    }
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