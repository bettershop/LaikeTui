import { getGoodsDetailInfo } from '@/api/plug_ins/stores'
export default {
    name: 'viewGoods',

    data() {
        return {
            ruleForm: {
                // 商品设置
                checkedLabel: [],
                checkedActivity: [],
                checked: '',

                content: ''
            },

            goodsInfo: {},

            header: {
                'background-color': '#F4F7F9',
                'font-weight': 'bold',
                'border-bottom': '1px solid #E9ECEF',
            },
            goodsDate: [],

            labelList: [
                {
                    value: '1',
                    name: '新品'
                },
                {
                    value: '2',
                    name: '热销'
                },
                {
                    value: '3',
                    name: '推荐'
                }
            ],

            activityList: [
                {
                    value: '1',
                    name: '正价'
                },
                {
                    value: '2',
                    name: '竞拍'
                },
                {
                    value: '3',
                    name: '砍价'
                },
                {
                    value: '4',
                    name: '拼团'
                },
                {
                    value: '5',
                    name: '积分'
                },
                {
                    value: '6',
                    name: '秒杀'
                },
                {
                    value: '7',
                    name: '会员'
                },
            ]

        }
    },

    created() {
        this.getGoodsDetailInfos()
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'goodsAudit') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
    },

    methods: {
        async getGoodsDetailInfos() {
            const res = await getGoodsDetailInfo({
                api: 'admin.mch.getGoodsDetailInfo',
                goodsId: this.$route.query.id
            })

            console.log(res);
            this.goodsDate = res.data.data.configureList
            this.goodsInfo = res.data.data.goodsInfo[0]
            this.ruleForm.checkedLabel = res.data.data.goodsInfo[0].s_type.split(',').filter(item => {
                if(item !== '') {
                    return item
                }
            })
            this.ruleForm.checkedActivity = res.data.data.goodsInfo[0].active
            this.ruleForm.checked = res.data.data.goodsInfo[0].show_adr
            this.ruleForm.content = res.data.data.goodsInfo[0].content
 0       }
    }
}