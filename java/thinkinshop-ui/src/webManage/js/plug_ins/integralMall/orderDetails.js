import { orderDetailsInfo } from '@/api/order/orderList'

export default {
    name: 'orderDetails',

    data() {
        return {
            dataInfo: null,
            goodsTables: [],
            totleInfo: null,
            noteList: [],
            totalIntegral: ''
        }
    },

    created() {
        this.orderDetailsInfos()
    },

    methods: {
        async orderDetailsInfos() {
            const res = await orderDetailsInfo({
                api: 'admin.order.orderDetailsInfo',
                sNo: this.$route.query.no
            })
            this.dataInfo = res.data.data.data
            this.goodsTables = res.data.data.detail
            this.totleInfo = res.data.data
            let remarksList = res.data.data.data.remarks
            if(remarksList) {
                for(let key in remarksList){
                    this.noteList.push(remarksList[key])
                }
            }
            this.totalIntegral = this.goodsTables[0].after_discount * this.goodsTables[0].num
        },

        getSource(value) {
            if(value == '1') {
                return '小程序'
            } else if(value == '2') {
                return 'APP'
            } else if(value == '3') {
                return '支付宝小程序'
            } else if(value == '4') {
                return '头条小程序'
            } else if(value == '5') {
                return '百度小程序'
            } else if(value == '6') {
                return 'pc端'
            } else if(value == '7') {
                return 'H5移动端'
            }else {
                return '闪购'
            }
        }
    }
}