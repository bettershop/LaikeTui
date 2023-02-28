import { getUserInfo, goodsStatus, saveAddress } from '@/api/members/membersList'
import { choiceClass } from '@/api/goods/goodsList'
import cascade from '@/api/publics/cascade'
import { helpOrder, getGoodsConfigureList, Settlement } from '@/api/order/orderList'
export default {
    name: 'valetOrder',

    data() {
        return {
            user_id: '',
            header: {
                'background-color': '#F4F7F9',
                'font-weight': 'bold',
                'border-bottom': '1px solid #E9ECEF',
            },

            dialogVisible: false,
            tableRadio: '',
            gradeList: [
                {
                    value: '0',
                    label: '普通会员'
                },
                {
                    value: '30',
                    label: '钻石会员'
                },
                {
                    value: '25',
                    label: '黄金会员'
                },
                {
                    value: '24',
                    label: '白银会员'
                },
                {
                    value: '34',
                    label: '测试等级'
                }
            ],
            gradeValue: '',
            search: '',
            userdata: [],
            userchangedata: [],
            currpage: 1,
            current_num: '',
            total: 0,
            total2: 0,
            pagination: {
                page: 1,
                pagesize: 10,
            },
            pagesizes: [10, 25, 50, 100],

            dialogVisible2: false,
            ruleForm: {
                name: '',
                tel: '',
                sheng: '',
                city: '',
                quyu: '',
                address_xq: ''
            },
            rules: {
                name: [{
                    required: true,
                    message: '请输入收货人名称',
                    trigger: 'blur'
                }, ],
                tel: [{
                    required: true,
                    message: '请输入手机号',
                    trigger: 'blur'
                }, ],
                quyu: [{
                    required: true,
                    message: '请选择地址',
                    trigger: 'change'
                }, ],
                address_xq: [{
                    required: true,
                    message: '请输入详细地址',
                    trigger: 'blur'
                }, ]
            },
            
            ProData: [],
            
            dialogVisible3: false,
            tabKey: '0',
            BrandList: [],
            ProList: [],
            ChangeProList: [],
            BrandValue: '',
            sendClass: '',
            Proname: '',
            options: [],
            
            tableData: [],
            wipe_off: 0,
            sheng: [],
            shengName: '',
            shi: [],
            shiName: '',
            xian: [],
            xianName: '',
            addressInfo: {},
            AddIndex: '',
            cid: '',
            Info: {},

            classList: [],
            brandList: [],
            goodsClass: '',
            brand: '',

            //省市级联集
            shengList: {},
            shiList: {},
            xianList: {},

            // 结算信息
            discount: 0, // 折扣
            total_price: 0, // 总价
            discount_price: 0,  // 会员折扣
            knock: 0,  // 立减
            combined: 0,  // 合计支付


            userId: null,
            products: [],
            wipeOff: null,

            adr: false

        }
    },
    
    computed: {
        showItem2() {
            let showItem1 = (this.currpage - 1) * this.pagination.pagesize + this.pagination.pagesize;
            if (showItem1 > this.total) {
              showItem1 = this.total;
            }
                            
            let showItem = (this.currpage - 1) * this.pagination.pagesize + 1 + "-" + showItem1;
            return showItem;
        },
        showItem() {
            let showItem1 = (this.currpage - 1) * this.pagination.pagesize + this.pagination.pagesize;
            if (showItem1 > this.total2) {
            showItem1 = this.total2;
            }
        
            let showItem = (this.currpage - 1) * this.pagination.pagesize + 1 + "-" + showItem1;
            return showItem;
        }
    },

    watch: {
        sendClass(val,nval) {
            if (val.length == 0) {
                this.BrandList = []
            }
        },

        'tableData': {
            handler:function(newValue,oldValue){
                this.userId = this.tableData[0].user_id
                this.discount = newValue[0].gradeDiscount
                // if(newValue[0].gradeDiscount !== '暂无折扣' && total_price) {
                //     this.discount_price = this.total_price - this.total_price * this.discount
                //     this.combined = this.total_price * this.discount - this.knock
                // }

                if(this.tableData[0].userAddress == null) {
                    this.adr = true
                }  else {
                    this.adr = false
                }

                if(this.ProData.length) {
                    Settlement({
                        api: 'admin.valetOrder.Settlement',
                        userId: this.userId,
                        products: encodeURIComponent(JSON.stringify(this.products)),
                        wipeOff: this.knock
                    }).then(res => {
                        if(res.data.code == '200') {
                            this.total_price = res.data.data.goodsPriceTotal
                            this.discount_price = res.data.data.vipDiscountPrice
                            this.combined = res.data.data.payPrice
                        }
                        console.log(res);
                    })
                    
                }
                console.log('===========================');
                
            },
            deep:true,
        },

        'ProData': {
            handler:function(newValue,oldValue){
                this.total_price = 0
                if(this.ProData.length !== 0) {
                    this.ProData.forEach(item => {
                        this.total_price += item.price * item.nums
                    })

                    // if(this.discount && this.discount !== '暂无折扣') {
                    //     this.discount_price = this.total_price - this.total_price * this.discount
                    //     this.combined = this.total_price * this.discount - this.knock
                    // } else {
                    //     this.combined = this.total_price - this.knock
                    // }

                }

                this.products = newValue.map(item => {
                    return {
                        id: item.attr_id,
                        pid: item.goodsId,
                        num: item.nums
                    }
                })

                if(this.tableData.length) {
                    Settlement({
                        api: 'admin.valetOrder.Settlement',
                        userId: this.userId,
                        products: encodeURIComponent(JSON.stringify(this.products)),
                        wipeOff: this.knock
                    }).then(res => {
                        console.log(res);
                        this.total_price = res.data.data.goodsPriceTotal
                        this.discount_price = res.data.data.vipDiscountPrice
                        this.combined = res.data.data.payPrice
                    })
                }
                
            },
            deep:true,
        },

        discount() {
            if(this.discount === null || this.discount === '暂无折扣') {
                this.discount_price = null
            } else {
                this.discount_price = this.total_price - this.total_price * this.discount
                this.combined = this.total_price * this.discount - this.knock
            }
        }

        // total_price() {
        //     if(this.discount === null || this.discount === '暂无折扣') {
        //         this.combined = this.total_price
        //     } else {
        //         this.knock = this.total_price - this.total_price * this.discount
        //         this.combined = this.total_price * this.discount
        //     }
        // }
    },

    created() {
        this.getSheng()
        this.getUserList()
        this.getProList()
        this.choiceClasss()
        this.goodsStatus()
        if(this.$route.query.id) {
            getUserInfo({
                api: 'admin.user.getUserInfo',
                pagesize: this.pagination.pagesize,
                page: this.currpage,
                uname: this.$route.query.id,
            }).then(res => {
                console.log(res);
                this.tableData = res.data.data.list
            })
        }
    },

    methods: {
        // 获取会员等级列表
        async goodsStatus() {
            const res = await goodsStatus({
                api: 'admin.user.goodsStatus'
            })

            let levelList = res.data.data.map(item => {
                return {
                    value: item.id,
                    label: item.name
                }
            })
            levelList.unshift({
                value: '0',
                label: '普通会员'
            })

            this.gradeList = levelList
        },

        rowKeys(row) {
            return row.id
        },
        AddPro() {
            this.dialogVisible3 = true
            this.current_num = this.ProList.length
            this.currpage = 1
            this.pagination.pageSize = 10
        },
        AddUser() {
            this.dialogVisible = true
            this.current_num = this.userdata.length
            this.currpage = 1
            this.pagination.pageSize = 10
        },
        handleChange(val,index) {
            this.ChangeProList[index].nums = val
            this.ChangeProList = [...this.ChangeProList]
        },

        handleClose() {
            this.dialogVisible2 = false
            this.$refs['ruleForm'].clearValidate()
        },
        addAddress(index) {
            this.AddIndex = index
            this.dialogVisible2 = true
        },
        AddProconfirm() {
            this.ProData = this.ChangeProList
            this.dialogVisible3 = false
            this.getUserInfo()
        },
        ChangeProdel(index) {
            this.ChangeProList.splice(index, 1)
            this.getUserInfo()
        },
        Addconfirm() {
            this.$refs.ruleForm.validate((valid) => {
                if (valid) {
                    saveAddress({
                        api: 'admin.user.saveAddress',
                        userId: this.tableData[0].user_id,
                        userName: this.ruleForm.name,
                        mobile: this.ruleForm.tel,
                        address: this.ruleForm.address_xq,
                        isDefault: 0,
                        place: this.ruleForm.sheng + '-' + this.ruleForm.city + '-' + this.ruleForm.quyu
                    }).then(res => {
                        if(res.data.code == '200') {
                            this.tableData[this.AddIndex].userAddress = this.ruleForm
                            this.tableData = [...this.tableData]
                            this.ruleForm = {
                                name: '',
                                tel: '',
                                sheng: '',
                                city: '',
                                quyu: '',
                                address_xq: ''
                            }
                            this.dialogVisible2 = false
                            this.$refs['ruleForm'].clearValidate()
                        }
                    })
                    
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        query() {
            this.currpage = 1
            this.pagination.pagesize = 10
            this.getProList()
        },
        query2() {
            this.currpage = 1
            this.pagination.pagesize = 10
            this.getUserList()
        },

        // 获取商品类别
        async choiceClasss() {
            const res = await choiceClass({
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
            choiceClass({
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

        // 获取产品列表
        async getProList() {
            const res = await getGoodsConfigureList({
                api: 'admin.goods.getGoodsConfigureList',
                pageNo: this.currpage,
                pageSize: this.pagination.pagesize,
                cid: this.goodsClass[this.goodsClass.length-1],
                brandId: this.brand,
                productTitle: this.Proname,
            })
            this.ProList = res.data.data.goodsList
            this.current_num = this.ProList.length * this.currpage
            this.total2 = Number(res.data.data.total)
            this.ProList.filter(item => {
                item.nums = 1
            })
        },

        // payOrder() {
        //     let products = []
        //     if (this.ChangeProList.length == 0) {
        //         this.$message.error('请选择商品！')
        //         return
        //     }
        //     if (this.tableData.length == 0) {
        //         this.$message.error('请选择会员！')
        //         return
        //     }

        //     if (!this.tableData[0].address) {
        //         this.$message.error('请添加收货地址！')
        //         return
        //     }
        //     this.ChangeProList.filter(item => {
        //         products.push({
        //             id: item.id,
        //             pid: item.pid,
        //             num: item.nums
        //         })
        //     })
        //     let data = {
        //         user_id: this.user_id ? this.user_id : this.tableData[0].user_id,
        //         products: JSON.stringify(products),
        //         wipe_off: this.wipe_off,
        //         //收货信息
        //         mobile: this.tableData[0].address.mobile,
        //         name: this.tableData[0].address.name,
        //         sheng: this.tableData[0].address.city[0],
        //         shi: this.tableData[0].address.city[1],
        //         xian: this.tableData[0].address.city[2],
        //         address: this.tableData[0].address.address,
        //         pay: 'wallet_pay'
        //     }
        //     $.ajax({
        //         cache: true,
        //         type: "post",
        //         dataType: "json",
        //         url: 'index.php?module=orderslist&action=ValetOrder&m=payment',
        //         data: data,
        //         async: true,
        //         success: (res) => {
        //             if (res.code == 200) {
        //                 this.$message.success(res.message)
        //                 setTimeout(() => {
        //                     window.location.href="javascript:history.go(-1)";
        //                     //window.location.href = 'index.php?module=orderslist&action=Index'
        //                 },1000)
        //             } else {
        //                 this.$message.error(res.message)
        //             }
        //         }
        //     });
        // },
        wipeBlur(e) {
            if(e.target.value !== 0) {
                if(this.combined) {
                    if(this.discount && this.discount !== '暂无折扣') {
                        this.combined = this.total_price * this.discount - e.target.value
                    } else {
                        this.combined = this.total_price - e.target.value
                    }
                }
            } else if(e.target.value === 0 || e.target.value === '') {
                if(this.discount && this.discount !== '暂无折扣') {
                    this.combined = this.total_price * this.discount
                } else {
                    this.combined = this.total_price
                }
            }

            Settlement({
                api: 'admin.valetOrder.Settlement',
                userId: this.userId,
                products: encodeURIComponent(JSON.stringify(this.products)),
                wipeOff: this.knock
            }).then(res => {
                console.log(res);
                this.total_price = res.data.data.goodsPriceTotal
                this.discount_price = res.data.data.vipDiscountPrice
                this.combined = res.data.data.payPrice
            })
            
        },
        getUserInfo(type) {
            let products = []
            if (this.ChangeProList.length > 0) {
                this.ChangeProList.filter(item => {
                    products.push({
                        id: item.id,
                        pid: item.pid,
                        num: item.nums
                    })
                })
            }
            let data = {
                user_id: this.user_id ? this.user_id : this.tableData.length > 0 ? this.tableData[0].user_id : '',
                products: JSON.stringify(products),
                wipe_off: this.wipe_off,
                //收货信息
                mobile: this.tableData.length > 0 && this.tableData[0].address ? this.tableData[0].address.mobile : '',
                name: this.tableData.length > 0 && this.tableData[0].address ? this.tableData[0].address.name : '',
                sheng: this.tableData.length > 0 && this.tableData[0].address ? this.tableData[0].address.city[0] : '',
                shi: this.tableData.length > 0 && this.tableData[0].address ? this.tableData[0].address.city[1] : '',
                xian: this.tableData.length > 0 && this.tableData[0].address ? this.tableData[0].address.city[2] : '',
                address: this.tableData.length > 0 && this.tableData[0].address ? this.tableData[0].address.address : ''
            }
            console.log(data)
            // $.ajax({
            //     cache: true,
            //     type: "post",
            //     dataType: "json",
            //     url: 'index.php?module=orderslist&action=ValetOrder&m=Settlement',
            //     data: data,
            //     async: true,
            //     success: (res) => {
            //         if (res.code == 200) {
            //             // if (this.tableData.length == 0) {
            //             // 	this.tableData = []
            //             // 	this.tableData.push(res.data.user)
                            
            //             // } else {
            //             // 	this.tableData.push()
            //             // }
                        
            //             if (Object.values(res.data.address_arr).length > 0) {
            //                 this.tableData = []
            //                 this.tableData.push(res.data.user)
            //                 let city = [res.data.address_arr.sheng,res.data.address_arr.shi,res.data.address_arr.xian]
            //                 this.tableData.filter(item => {
            //                     item.address = {
            //                         mobile: res.data.address_arr.mobile,
            //                         name: res.data.address_arr.name,
            //                         city: city,
            //                         address: res.data.address_arr.address
            //                     }
            //                 })
            //             }
            //             this.Info = res.data
            //         }
            //     }
            // });
        },
        async getUserList() {
            const res = await getUserInfo({
                api: 'admin.user.getUserInfo',
                pagesize: this.pagination.pagesize,
                page: this.currpage,
                uname: this.search,
                grade: this.gradeValue,
            })
            console.log(1111111111);
            console.log(res);
            this.total = Number(res.data.data.total)
            this.userdata = res.data.data.list
            this.current_num = this.userdata.length
            if(this.total < this.current_num) {
                this.current_num = this.total
            }

        },
        Reset() {
            this.gradeValue = ''
            this.search = '' //会员ID,名称，手机号
            this.Proname = ''
            this.BrandValue = ''
            this.BrandList = []
            this.sendClass = ''
            this.cid = '',
            this.goodsClass = '',
            this.brand = ''
        },
        confirm() {
            this.user_id = ''
            this.dialogVisible = false
            this.tableData = []
            console.log(JSON.stringify(this.userchangedata),null,2)
            this.tableData.push(this.userchangedata)
            this.getUserInfo()
        },
        handleSelectionChange(e) {
            this.tableRadio = e
            this.userchangedata = e
        },
        handleSelectionChange2(e) {
            this.ChangeProList = e
        },
        //选择一页多少条
        handleSizeChange(e) {
            this.pagination.pagesize = e
            if (this.dialogVisible3) {
                this.getProList()
            } else {
                this.getUserList()
            }
        },
        //点击上一页，下一页
        handleCurrentChange(e) {
            this.currpage = e
            if (this.dialogVisible3) {
                this.getProList()
            } else {
                this.getUserList()
            }
        },
        cancel() {
            window.location.href="javascript:history.go(-1)";
        },
        getQueryVariable(variable) {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i = 0; i < vars.length; i++) {
                var pair = vars[i].split("=");
                if (pair[0] == variable) {
                    return pair[1];
                }
            }
            return (false);
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

        submitForm() {
            if(this.ProData.length == 0) {
                this.$message({
                    message: '请选择商品',
                    type: 'error',
                    offset:100
                })
            } else if (this.tableData.length == 0) {
                this.$message({
                    message: '请选择会员',
                    type: 'error',
                    offset:100
                })
            } else if (this.adr == true) {
                this.$message({
                    message: '请添加收货地址',
                    type: 'error',
                    offset:100
                })
            } else {
                helpOrder({
                    api: 'admin.order.helpOrder',
                    userId: this.userId,
                    products: encodeURIComponent(JSON.stringify(this.products)),
                    wipeOff: this.discount_price ? this.discount_price + this.knock : this.knock
                }).then(res => {
                    if(res.data.code == '200') {
                        this.$message({
                            message: '成功',
                            type: 'success',
                            offset:100
                        })
                        this.$router.go(-1)
                    }
                    console.log(res);
                })
            }
            
        }

    }
}