import { secLabelGoodsList, addPro, getProAttrList } from '@/api/plug_ins/seckill'

export default {
    name: 'viewGoods',

    data() {
        return {
            ruleForm: {
                price_type: 0,
                price: '',
                activity_time: []
            },

            rules: {
                price: [
                    { required: true, message: '请填写秒杀价格', trigger: 'blur' }
                ],
                shelves_num: [
                    { required: true, message: '请填写上架库存', trigger: 'blur' }
                ],
                remaining_num: [
                    { required: true, message: '请填写剩余库存', trigger: 'blur' }
                ],
                activity_time: [
                    { required: true, message: '请选择活动时间', trigger: 'change' }
                ],
            },

            goodsId: '',

            tableData: [],

            priceList: [
                {
                    value: 0,
                    name: '百分比'
                },
                {
                    value: 1,
                    name: '固定值'
                },
            ],

            text: '%',

            // 多规格弹框数据
            dialogVisible3: false,
            ProList: []

        }
    },

    created() {
        this.secLabelGoodsLists().then(() => {
            this.getProAttrLists()
        })
    },

    watch: {
        'ruleForm.price_type':{
            handler:function() {
                if(this.ruleForm.price_type == 0) {
                    this.text = '%'
                } else {
                    this.text = '元'
                }
            }
        },

        'ruleForm.price':{
            handler:function() {
                if(this.ruleForm.price_type == 1) {
                    if(parseInt(this.ruleForm.price) > parseInt(this.tableData[0].goodsPrice)) {
                        this.ruleForm.price = this.tableData[0].goodsPrice
                    }
                }
            }
        }
    },

    beforeRouteLeave(to, from, next) {
        let that = this
        console.log(to)
        if (to.path == "/plug_ins/seckill/goodsList") {
          console.log(that.$route.query.id)
          to.query.id = that.$route.query.labelId
        }
        next()
    },

    methods: {
        async secLabelGoodsLists() {
            const res = await secLabelGoodsList({
                api: 'admin.sec.label.secLabelGoodsList',
                labelId: this.$route.query.labelId,
                secGoodsId: this.$route.query.id
            })
            console.log(res);
            this.tableData = res.data.data.goodsList
            this.ruleForm.price_type = this.tableData[0].price_type
            this.ruleForm.price = this.tableData[0].price
            this.ruleForm.shelves_num = this.tableData[0].max_num
            this.ruleForm.remaining_num = this.tableData[0].stockNum
            this.ruleForm.activity_time = [this.conversionTime(this.tableData[0].starttime),this.conversionTime(this.tableData[0].endtime)]
            this.goodsId = this.tableData[0].goodsId,
            this.id = this.tableData[0].id
        },

        tableHeaderColor({ row, column, rowIndex, columnIndex }) {
            if (rowIndex === 0) {
              return 'background-color: #f4f7f9;'
            }
        },

        conversionTime(val) {
            const d = new Date(val); // val 为表格内取到的后台时间
            const month = d.getMonth() + 1 < 10 ? `0${d.getMonth() + 1}` : d.getMonth() + 1;
            const day = d.getDate() < 10 ? `0${d.getDate()}` : d.getDate();
            const hours = d.getHours() < 10 ? `0${d.getHours()}` : d.getHours();
            const min = d.getMinutes() < 10 ? `0${d.getMinutes()}` : d.getMinutes();
            const sec = d.getSeconds() < 10 ? `0${d.getSeconds()}` : d.getSeconds();
            const times = `${d.getFullYear()}-${month}-${day} ${hours}:${min}:${sec}`;
            return times;
        },

        onInputBlur() {
            if(parseInt(this.ruleForm.price) <= 0) {
                this.ruleForm.price = 1
            }
        },

        // 获取规格信息
        async getProAttrLists() {
            const res = await getProAttrList({
                api: 'admin.sec.label.getProAttrList',
                goodsId: this.tableData[0].goodsId,
                acId: this.$route.query.id
            })

            this.ProList = res.data.data.list
        },

        // 多规格弹框方法
        specifications(value) {
            this.dialogVisible3 = true
        },

        AddProconfirm() {

        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm);
              if (valid) {
                try {
                  addPro({
                    api: 'admin.sec.label.addPro',
                    id: this.$route.query.labelId,
                    secGoodsId: this.id,
                    priceType: this.ruleForm.price_type,
                    price: this.ruleForm.price,
                    startDate: this.ruleForm.activity_time[0],
                    endDate: this.ruleForm.activity_time[1],
                    goodsIds: this.goodsId
                  }).then(res => {
                    if(res.data.code == '200') {
                      this.$message({
                        message: '编辑成功',
                        type: 'success',
                        offset: 100
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

        oninput(num, limit) {
            var str = num
            var len1 = str.substr(0, 1)
            var len2 = str.substr(1, 1)
            //如果第一位是0，第二位不是点，就用数字把点替换掉
            if (str.length > 1 && len1 == 0 && len2 != ".") {
              str = str.substr(1, 1)
            }
            //第一位不能是.
            if (len1 == ".") {
              str = ""
            }
            //限制只能输入一个小数点
            if (str.indexOf(".") != -1) {
              var str_ = str.substr(str.indexOf(".") + 1)
              if (str_.indexOf(".") != -1) {
                str = str.substr(0, str.indexOf(".") + str_.indexOf(".") + 1)
              }
            }
            //正则替换
            str = str.replace(/[^\d^\.]+/g, '') // 保留数字和小数点
            if (limit / 1 === 1) {
              str = str.replace(/^\D*([0-9]\d*\.?\d{0,1})?.*$/,'$1') // 小数点后只能输 1 位
            } else {
              str = str.replace(/^\D*([0-9]\d*\.?\d{0,2})?.*$/,'$1') // 小数点后只能输 2 位
            }

            if(this.ruleForm.price_type == 1 && this.totlePrice !== 0 && parseInt(this.ruleForm.price) > this.totlePrice) {
                str = this.totlePrice
            }
            return str
        },

        oninput2(num) {
            var str = num
            str = str.replace(/[^\.\d]/g,'');
            str = str.replace('.','');

            return str
        },
    }
}