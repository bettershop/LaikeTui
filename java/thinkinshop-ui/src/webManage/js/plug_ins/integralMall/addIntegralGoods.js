import { get_class, getProList, addIntegral } from '@/api/plug_ins/integralMall'
import { mixinstest } from '@/mixins/index'

export default {
    name: 'addIntegralGoods',
    mixins: [mixinstest],

    data() {
        return {
            ruleForm: {
                integral: '',
                yuan: '',
                shelves_num: '',
            },

            rules: {
                integral: [
                    { required: true, message: '请填写兑换价格', trigger: 'blur' }
                ],
                shelves_num: [
                    { required: true, message: '请填写上架库存', trigger: 'blur' }
                ],
            },

            classList: [],
            brandList: [],
            inputInfo: {
                goodsClass: '',
                brand: '',
                name: ''
            },

            id: null,

            tableData: [],

            tableRadio: '',
            totlePrice: null,
            stock: null

        }
    },

    created() {
        this.getProLists()
        this.get_classs()
    },

    watch: {
        'ruleForm.shelves_num':{
            handler:function() {
                if(parseInt(this.ruleForm.shelves_num) == 0) {
                    this.ruleForm.shelves_num = ''
                }

                if(this.stock && parseInt(this.ruleForm.shelves_num) > this.stock) {
                    this.ruleForm.shelves_num = this.stock
                }
            }
        },

        'inputInfo.goodsClass':{
            handler:function() {
                this.inputInfo.brand = ''
            }
        },
    },

    methods: {
        async getProLists() {
            const res = await getProList({
                api: 'admin.integral.getProList',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                myClass: this.inputInfo.goodsClass[this.inputInfo.goodsClass.length-1],
                myBrand: this.inputInfo.brand,
                proName: this.inputInfo.name,
            })
            console.log(res);
            this.current_num = 10
            this.total = res.data.data.total
            this.tableData = res.data.data.res
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            if(this.total == 0) {
                this.showPagebox = false
            } else {
                this.showPagebox = true
            }
        },

        reset() {
            this.inputInfo.goodsClass = '',
            this.inputInfo.brand = '',
            this.inputInfo.name = ''
        },
    
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.dictionaryNum = 1
            this.getProLists()
        },

        tableHeaderColor({ row, column, rowIndex, columnIndex }) {
            if (rowIndex === 0) {
              return 'background-color: #f4f7f9;'
            }
        },

        // 获取商品类别
        async get_classs() {
            const res = await get_class({
                api: 'admin.goods.choiceClass',
            })
            res.data.data.list.class_list[0].forEach((item, index) => {
                let obj = item
                this.classList.push({
                    value: obj.cid,
                    label: obj.pname,
                    index: index,
                    children: []
                })
            })
        },

        // 根据商品类别id获取商品品牌
        changeProvinceCity(value) {
            get_class({
                api: 'admin.goods.choiceClass',
                classId: value.length > 1 ? value[value.length - 1] : value[0]
            }).then(res => {
                let num = this.$refs.myCascader.getCheckedNodes()[0].data.index
                this.brandList = res.data.data.list.brand_list.splice(-1,1)
                if(res.data.data.list.class_list[0].length !== 0) {
                    this.classList[num].children = []
                    res.data.data.list.class_list[0].forEach((item, index) => {
                        let obj = item
                        this.classList[num].children.push({
                            value: obj.cid,
                            label: obj.pname,
                            index: index,
                            children: []
                        })
                    })
                }
            })
        },

        handleSelectionChange(e) {
            console.log(e)
            this.id = e.id
            this.stock = e.stockNum
            this.totlePrice = e.price
            if(parseInt(this.ruleForm.shelves_num) > this.stock) {
                this.ruleForm.shelves_num = this.stock
            }
            if(parseInt(this.ruleForm.yuan) > this.totlePrice) {
                this.ruleForm.yuan = this.totlePrice
            }
        },

        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm);
              if (valid) {
                try {
                  addIntegral({
                    api: 'admin.integral.addIntegral',
                    goodsid: this.id,
                    stockNum: this.ruleForm.shelves_num,
                    integral: this.ruleForm.integral,
                    money: this.ruleForm.yuan
                  }).then(res => {
                    if(res.data.code == '200') {
                      this.$message({
                        message: '添加成功',
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

        //选择一页多少条
        handleSizeChange(e){
            this.pageSize = e
            this.getProLists().then(() => {
                this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
                this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
            })
        },

        //点击上一页，下一页
        handleCurrentChange(e){
            this.dictionaryNum = e
            this.currpage = ((e - 1) * this.pageSize) + 1
            this.getProLists().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
            })
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

            if(this.totlePrice && parseInt(this.ruleForm.yuan) > this.totlePrice) {
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