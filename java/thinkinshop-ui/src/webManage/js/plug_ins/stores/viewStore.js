import { getMchInfo } from '@/api/plug_ins/stores'
export default {
    name: 'viewStore',

    data() {
        return {
            storeInfo: null,
        }
    },

    created() {
        this.getMchInfos()
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'auditList' || to.name == 'store') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
    },

    methods: {
        async getMchInfos() {
            const res = await getMchInfo({
                api: 'admin.mch.getMchInfo',
                id: this.$route.query.id
            })

            console.log(res);
            this.storeInfo = res.data.data.list[0]
        }
    }
}