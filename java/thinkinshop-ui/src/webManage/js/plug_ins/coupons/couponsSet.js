import { getCouponConfigInfo, addCouponConfig } from '@/api/plug_ins/coupons'
export default {
    name: 'couponsSet',

    data() {
        return {
            radio1:'优惠券设置',

            ruleForm: {
                switchs: 1, // 是否开启优惠券
                clear_coupon: '1', // 是否自动清除过期优惠券
                clear_coupon_day: 1,
                clear_activity: '1', // 是否自动清除过期活动
                clear_activity_day: 1,
                limit_get: '1', // 限领设置
                coupons_type: [],// 享受优惠商品
            },
            
            clearCouponList: [
                {
                    value: 1,
                    name: '是'
                },
                {
                    value: 0,
                    name: '否'
                },
            ],

            clearActivityList: [
                {
                    value: 1,
                    name: '是'
                },
                {
                    value: 0,
                    name: '否'
                },
            ],

            limitGetList: [
                {
                    value: 0,
                    name: '每人限领1张'
                },
                {
                    value: 1,
                    name: '每人可领多张'
                },
            ],

            checkAll: false,
            isIndeterminate: true,
            couponsTypeList: [
                {
                    id: '1',
                    name: '免邮券'
                },
                {
                    id: '2',
                    name: '满减券'
                },
                {
                    id: '3',
                    name: '折扣券'
                },
                {
                    id: '4',
                    name: '会员赠券'
                }
            ],
        }
    },

    created() {
        this.getCouponConfigInfos()
    },

    methods: {
        async getCouponConfigInfos() {
            const res = await getCouponConfigInfo({
                api: 'admin.coupon.getCouponConfigInfo'
            })
            console.log(res);
            let setInfo = res.data.data.data
            this.ruleForm.switchs = setInfo.is_status
            this.ruleForm.clear_coupon = setInfo.coupon_del
            this.ruleForm.clear_coupon_day = setInfo.coupon_day
            this.ruleForm.clear_activity = setInfo.activity_del
            this.ruleForm.clear_activity_day = setInfo.activity_day
            this.ruleForm.limit_get = setInfo.limit_type
            this.ruleForm.coupons_type = setInfo.coupon_type.split(',')

        },

        handleCheckAllChange(val) {
            console.log(this.checkAll);
            this.ruleForm.coupons_type = val ? this.couponsTypeList.map(item => { return item.id }) : [];
            this.isIndeterminate = false;
        },

        handleCheckedCitiesChange(value) {
            let checkedCount = value.length;
            this.checkAll = checkedCount === this.couponsTypeList.length;
            this.isIndeterminate = checkedCount > 0 && checkedCount < this.couponsTypeList.length;
        },

        submitForm() {
            addCouponConfig({
                api: 'admin.coupon.addCouponConfig',
                isOpen: this.ruleForm.switchs,
                isAutoClearCoupon: this.ruleForm.clear_coupon,
                autoClearCouponDay: this.ruleForm.clear_coupon_day,
                isAutoClearaAtivity: this.ruleForm.clear_activity,
                autoClearaAtivityDay: this.ruleForm.clear_activity_day,
                limitType: this.ruleForm.limit_get,
                couponType: this.ruleForm.coupons_type.join(',')
            }).then(res => {
                if(res.data.code == '200') {
                    this.getCouponConfigInfos()
                    this.$message({
                        type: 'success',
                        message: '保存成功!',
                        offset: 100
                    })
                }
            })
        }
    }
}