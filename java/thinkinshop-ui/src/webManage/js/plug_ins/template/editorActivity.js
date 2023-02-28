import { activityList, getActGoodsList, fullReduction, getGoodsList, activitySave, getPluginTypeList } from '@/api/plug_ins/template'
import { choiceClass } from '@/api/goods/goodsList'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'editorActivity',
    mixins: [mixinstest],
    data() {
        return {
            header: {
                'background-color': '#F4F7F9',
                'font-weight': 'bold',
                'border-bottom': '1px solid #E9ECEF',
            },

            ruleForm: {
                activeType: 0,
                activeTitle: '',
                plugType: '',
                plugTitle: '',
                setActivity: ''
            },

            dialogVisible3: false,
            ProList: [],
            ChangeProList: [],
            ProData: [],
            classList: [],
            brandList: [],
            goodsClass: null,
            brand: null,
            Proname: null,
            pid: null,
        
            activeTypeList: [
                {
                    value: 0,
                    name: '活动专题'
                },
                {
                    value: 1,
                    name: '营销插件'
                },
            ],

            plugTypeList: [],

            setActivityList: []
        }
    },

    watch: {
        'ChangeProList': {
            handler: function() {
                this.pid = this.ChangeProList.map(item => {
                    return item.id
                })
            }
        }
    },

    created() {
        this.choiceClasss()
        this.getGoodsLists()
        this.getPluginTypeLists()
        this.fullReductions()
        this.activityLists()
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'activity') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
    },

    methods: {
        async activityLists() {
            const res = await activityList({
                api: 'admin.diy.activityList',
                id: this.$route.query.id
            })

            console.log(res);
            let info = res.data.data.list[0]
            this.ruleForm.activeType = info.activity_type
            if(info.activity_type == 0) {
                this.ruleForm.activeTitle = info.name
                this.getActGoodsLists()
            } else {
                this.ruleForm.plugType = info.plug_type
                if(info.plug_type !== 10) {
                    this.ruleForm.plugTitle = info.name
                } else {
                    this.ruleForm.setActivity = info.subtraction_id.split(",").map(item => {
                        return parseInt(item)
                    })
                }
            }
        },

        // 获取活动商品列表
        async getActGoodsLists() {
            const res = await getActGoodsList({
                api: 'admin.diy.getActGoodsList',
                id: this.$route.query.id
            })
            this.ChangeProList = res.data.data.list
        },

        // 获取满减活动列表
        async fullReductions() {
            const res = await fullReduction({
                api: 'app.admin.subtraction.index'
            })
            this.setActivityList = res.data.data.list
            console.log(res);
        },

        async getGoodsLists() {
            const res = await getGoodsList({
              api: 'admin.diy.getGoodsList',
              pageNo: this.dictionaryNum,
              pageSize: this.pageSize,
              goodsName: this.Proname,
              classId: this.goodsClass,
              brandId: this.brand
            })
            this.current_num = 10
            this.total = res.data.data.total
            this.ProList = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

        async getPluginTypeLists() {
            const res = await getPluginTypeList({
                api: 'admin.diy.getPluginTypeList',
            })

            this.plugTypeList = res.data.data.list
            console.log(res);
        },

        AddPro() {
            this.dialogVisible3 = true
        },

        rowKeys(row) {
            return row.id
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

        Reset() {
            this.goodsClass = null
            this.brand = null
            this.Proname = null
        },

        query() {
            this.dictionaryNum = 1
            this.getGoodsLists()
        },

        handleSelectionChange2(e) {
            this.ChangeProList = e
        },

        AddProconfirm() {
            this.dialogVisible3 = false
        },

        ChangeProdel(index) {
            this.ChangeProList.splice(index, 1)
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getGoodsLists().then(() => {
                this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
                this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
                this.loading = false
            })
        },

        //点击上一页，下一页
        handleCurrentChange(e){
            this.loading = true
            this.dictionaryNum = e
            this.currpage = ((e - 1) * this.pageSize) + 1
            this.getGoodsLists().then(() => {
                this.current_num = this.ProList.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        submitForm() {
            if(this.ruleForm.activeType === 0) {
                activitySave({
                    api: 'admin.diy.activitySave',
                    activityType: this.ruleForm.activeType,
                    name: this.ruleForm.activeTitle,
                    pid: this.pid.join(','),
                    id: this.$route.query.id
                }).then(res => {
                    if(res.data.code == '200') {
                      console.log(res);
                      this.$message({
                        type: 'success',
                        message: '编辑成功!',
                        offset: 100
                      });
                      this.$router.go(-1)
                    }
                })
            } else {
                activitySave({
                    api: 'admin.diy.activitySave',
                    activityType: this.ruleForm.activeType,
                    plugType: this.ruleForm.plugType,
                    name: this.ruleForm.plugType !== 10 ? this.ruleForm.plugTitle : null,
                    subtractionId: this.ruleForm.plugType == 10 ? this.ruleForm.setActivity.join(',') : null,
                    id: this.$route.query.id
                }).then(res => {
                    if(res.data.code == '200') {
                      console.log(res);
                      this.$message({
                        type: 'success',
                        message: '编辑成功!',
                        offset: 100
                      });
                      this.$router.go(-1)
                    }
                })
            }
            
        }

    }
}