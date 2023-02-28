import { getCouponCardInfo, addCoupon, getAssignGoods, getAssignGoodsClass } from '@/api/plug_ins/coupons'
import { getTime } from '@/utils/utils'
export default {
    name: 'viewCoupons',
    data() {
        return {
            ruleForm: {
                coupons_type: '',
                coupons_name: '',
                issue_number: '',
                num: '',
                face_value: '',
                consumption_threshold: '',
                available_range: '',
                date: [],
                instructions: '',
                select_goods: []
            },

            defaultProps: {
                children: 'child',
                label: 'cname'
            },

            availableRangeList: [
                {
                    value: 1,
                    name: '全部商品'
                },
                {
                    value: 2,
                    name: '指定商品'
                },
                {
                    value: 3,
                    name: '指定分类'
                }
            ],

            goodsList: [], // 商品列表

            toData:[]
        }
    },

    created() {
        this.getAssignGoods()
        this.getCouponCardInfos()
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'couponsList') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
      },

    methods: {
        async getCouponCardInfos() {
            const res = await getCouponCardInfo({
              api: 'admin.coupon.getCouponCardInfo',
              mchId: 0,
              hid: this.$route.query.id,
            })
            let couponsInfo = res.data.data.list[0]
            this.ruleForm.coupons_type = couponsInfo.activity_type
            this.ruleForm.coupons_name = couponsInfo.name
            this.ruleForm.issue_number = couponsInfo.circulation
            this.ruleForm.num = couponsInfo.num
            this.ruleForm.face_value = couponsInfo.money
            this.ruleForm.consumption_threshold = couponsInfo.z_money
            for (const item of this.availableRangeList) {
              if(item.name === couponsInfo.type) {
                this.ruleForm.available_range = item.value
                break
              }
            }
            if(couponsInfo.goodsIdList) {
                this.ruleForm.select_goods = couponsInfo.goodsIdList.map(item => {
                  return item.id
                })
            }
            if(couponsInfo.classIdList) {
                this.toData = couponsInfo.classIdList.map(item => {
                    return {
                        child: [],
                        cid: item.cid,
                        cname: item.pname,
                        level: item.level,
                        sid: item.sid
                    }
                })
            }
            this.ruleForm.date[0] = getTime(couponsInfo.start_time)
            this.ruleForm.date[1] = getTime(couponsInfo.end_time)
            this.ruleForm.instructions = couponsInfo.Instructions
            console.log(res);
        },

        async getAssignGoods() {
            const res = await getAssignGoods({
              api: 'admin.coupon.getAssignGoods',
            })
            this.goodsList = res.data.data.list
        },
    }
}