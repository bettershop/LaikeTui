import { getRefundList } from '@/api/order/salesReturn'

export default {
    name: 'salesReturnDetails',

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
            sales_imgs: []

        }
    },

    created() {
        this.getRefundLists()
    },

    methods: {
        async getRefundLists() {
            const res = await getRefundList({
                api: 'admin.order.getRefundList',
                id: this.$route.query.id
            })
            console.log(res);
            this.goodsDate.push(res.data.data.list)
            this.totle_price = res.data.data.list.total
            this.applyInfo = res.data.data.list
            this.rdata = res.data.data.rdata
            this.sales_imgs = res.data.data.imgs
        }
    }
}