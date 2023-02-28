import { getUserInfo } from '@/api/members/membersList'

export default {
    name: 'viewMembers',

    data() {
        return {
            userInfo: null
        }
    },

    created() {
        this.getUserInfos()
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'membersList') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
    },

    methods: {
        async getUserInfos() {
            const res = await getUserInfo({
                api: 'admin.user.getUserInfo',
                uid: this.$route.query.id
            })
            this.userInfo = res.data.data.list[0]
            console.log(this.userInfo);
        },

        getSource(value) {
            if(value == 1) {
                return '小程序'
            } else if(value == 2) {
                return 'APP'
            } else if(value == 3) {
                return '支付宝小程序'
            } else if(value == 4) {
                return '头条小程序'
            } else if(value == 5) {
                return '百度小程序'
            } else if(value == 6) {
                return 'pc端'
            } else if(value == 7) {
                return 'H5移动端'
            } else {
                return '闪购'
            }
        }
    }
}