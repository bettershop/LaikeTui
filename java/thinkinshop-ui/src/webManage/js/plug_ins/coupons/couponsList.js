import { getCouponCardInfo, activityisDisplay, delCoupon, receiveUserCoupon, getGiveUserInfo } from '@/api/plug_ins/coupons'
import { goodsStatus } from '@/api/members/membersList'
import { mixinstest } from '@/mixins/index'
import { validUsername } from '@/utils/validate'
export default {
    name: 'couponsList',
    mixins: [mixinstest],
    data() {
        return {
            radio1:'优惠券列表',

            couponsTypeList: [
                {
                    value: '1',
                    label: '免邮券'
                },
                {
                    value: '2',
                    label: '满减券'
                },
                {
                    value: '3',
                    label: '折扣券'
                },
                {
                    value: '4',
                    label: '会员赠券'
                },

            ],// 优惠券类型

            overdueList: [
                {
                    value: '0',
                    label: '已过期'
                },
                {
                    value: '1',
                    label: '未过期'
                },
            ],// 是否过期

            inputInfo: {
                couponsName: null,
                couponsType: null,
                is_overdue: null,
            },

            tableData: [],
            loading: true,

            // 弹框数据
            ProData: [],
            dialogVisible3: false,
            tabKey: '0',
            gradeList: [],
            ProList: [],
            ChangeProList: [],
            BrandValue: '',
            sendClass: '',
            Proname: '',
            options: [],

            grade: null,
            userName: null,
            userTotal: null,
            selectUser: null,

            loadings: false,
            is_show: true,

            hid: null,
            userIdList: [],

            // table高度
            tableHeight: null
        }
    },

    created() {
        if(this.$route.params.pageSize) {
            this.pagination.page = this.$route.params.dictionaryNum
            this.dictionaryNum = this.$route.params.dictionaryNum
            this.pageSize = this.$route.params.pageSize
        }
        this.getCouponCardInfos()
        this.goodsStatus()
        // this.getGiveUserInfos()
    },

    mounted() {
        this.$nextTick(function() {
            this.getHeight()
        })
        window.addEventListener('resize',this.getHeight(),false)
    },

    watch: {
        'ChangeProList': {
            handler:function(val,oldval){
                this.selectUser = this.ChangeProList.length
                this.userIdList = this.ChangeProList.map(item => {
                    return item.user_id
                })
            }
        }
    },

    methods: {
        // 获取table高度
        getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
            console.log(this.$refs.tableFather.clientHeight);
		},
        async getCouponCardInfos() {
            const res = await getCouponCardInfo({
                api: 'admin.coupon.getCouponCardInfo',
                mchId: 0,
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                name: this.inputInfo.couponsName,
                activityType: this.inputInfo.couponsType,
                isOverdue: this.inputInfo.is_overdue
            })
            this.total = res.data.data.total
            this.tableData = res.data.data.list
            this.loading = false
            if(this.total < this.current_num) {
                this.current_num = this.total
            }
            console.log(res);
        },

         // 重置
         reset() {
            this.inputInfo.couponsName = null
            this.inputInfo.couponsType = null
            this.inputInfo.is_overdue = null
        },
  
        // 查询
        demand() {
            this.currpage = 1
            this.current_num = 10
            this.showPagebox = false
            this.loading = true
            this.dictionaryNum = 1
            this.getCouponCardInfos().then(() => {
                this.loading = false
                if(this.tableData.length > 5) {
                    this.showPagebox = true
                }
            })
        },

        // 添加优惠券
        addCoupons() {
            this.$router.push({
                path: '/plug_ins/coupons/addCoupons',
            })
        },

        // 是否显示
        switchs(value) {
            activityisDisplay({
                api: 'admin.coupon.activityisDisplay',
                hid: value.id
            }).then(res => {
                if(res.data.code == '200') {
                    this.getCouponCardInfos()
                    this.$message({
                        type: 'success',
                        message: '成功!',
                        offset: 100
                    })
                }
            })
        },

        // 查看
        View(value) {
            this.$router.push({
                path: '/plug_ins/coupons/viewCoupons',
                query: {
                    id: value.id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        // 编辑
        Edit(value) {
            this.$router.push({
                path: '/plug_ins/coupons/editorCoupons',
                query: {
                    id: value.id,
                    dictionaryNum: this.dictionaryNum,
                    pageSize: this.pageSize
                }
            })
        },

        Delete(value) {
            this.$confirm('是否删除此优惠券活动？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                delCoupon({
                    api: 'admin.coupon.delCoupon',
                    hid: value.id
                }).then(res => {
                    console.log(res);
                    if(res.data.code == '200') {
                        this.getCouponCardInfos()
                        this.$message({
                            type: 'success',
                            message: '删除成功!',
                            offset: 100
                        })
                    }
                })
            }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消删除',
                offset: 100
            });          
            });
        },

        // 领取记录
        Receive(value) {
            this.$router.push({
                path: '/plug_ins/coupons/getRecord',
                query: {
                    id: value.id
                }
            })
        },

        // 赠送记录
        Giving(value) {
            this.$router.push({
                path: '/plug_ins/coupons/givingRecords',
                query: {
                    id: value.id
                }
            })
        },

        //选择一页多少条
        handleSizeChange(e){
            this.loading = true
            console.log(e);
            // this.current_num = e
            this.pageSize = e
            this.getCouponCardInfos().then(() => {
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
            this.getCouponCardInfos().then(() => {
                this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
                this.loading = false
            })
        },

        // 弹框方法
        async getGiveUserInfos(value) {
            const res = await getGiveUserInfo({
                api: 'admin.coupon.getGiveUserInfo',
                pageNo: this.dictionaryNum,
                pageSize: this.pageSize,
                name: this.userName,
                grade: this.grade,
                hid: value
            })
            console.log(res);
            this.is_show = true
            this.userTotal = res.data.data.total
            this.ProList = res.data.data.userList
            if(this.ProList.length < 6) {
                this.is_show = false
            }
        },

        addUser() {
            if(this.pageSize < this.userTotal || this.pageSize - this.userTotal < 10) {
                this.loadings = true
                this.pageSize += 10
                getGiveUserInfo({
                    api: 'admin.coupon.getGiveUserInfo',
                    pageNo: this.dictionaryNum,
                    pageSize: this.pageSize,
                    name: this.uname,
                    grade: this.grade,
                    hid: this.hid
                }).then(res => {
                    this.ProList = res.data.data.userList
                    this.loadings = false
                })
            } else {
                this.is_show = false
            }
        },

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
                value: 0,
                label: '普通会员'
            })

            this.gradeList = levelList
        },
        Reset() {
            this.grade = null,
            this.userName = null
        },

        query() {
            this.pageSize = 10
            this.is_show = true
            this.getGiveUserInfos(this.hid)
            this.$refs.multipleTable.clearSelection()
        },

        rowKeys(row) {
            return row.id
        },

        handleSelectionChange2(e) {
            this.ChangeProList = e
        },

        DeleteP(index,value) {
            this.ChangeProList.splice(index,1)
            this.$refs.multipleTable.toggleRowSelection(this.ProList.find((item) => {
                return value.user_id == item.user_id
            }),false)
        },

        givingCoupons(value) {
            this.getGiveUserInfos(value.id)
            this.hid = value.id
            this.pageSize = 10
            this.is_show = true
            this.dialogVisible3 = true
        },

        handleClose2() {
            this.dialogVisible3 = false
            this.ProList = []
            this.ChangeProList = []
            this.userTotal = null
            this.selectUser = null
            this.grade = null
            this.userName = null
            this.$refs.multipleTable.clearSelection()
        },

        AddProconfirm() {
            receiveUserCoupon({
                api: 'admin.coupon.receiveUserCoupon',
                userIds: this.userIdList.join(','),
                hid: this.hid
            }).then(res => {
                if(res.data.code == '200') {
                    this.$message({
                        message: '赠送成功',
                        type: 'success',
                        offset:100
                    })
                    this.handleClose2()
                }
            })
        },
    }
}