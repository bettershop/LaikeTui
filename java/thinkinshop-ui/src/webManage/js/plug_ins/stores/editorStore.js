import { getMchInfo, modifyMchInfo } from '@/api/plug_ins/stores'
import cascade from '@/api/publics/cascade'
export default {
    name: 'editorStore',

    data() {
        return {
            ruleForm: {
                roomid: null,
                shop_information: null,
                confines: null,
                tel: null,
                sheng: '',
                shi: '',
                xian: '',
                address: null,
                shop_nature: null
            },

            storeInfo: null,

            //省市级联集
            shengList: {},
            shiList: {},
            xianList: {},

            belongList: [
                {
                    value: 0,
                    name: '个人'
                },
                {
                    value: 1,
                    name: '企业'
                }
            ],

        }
    },

    created() {
        this.getSheng()
        this.getMchInfos().then(() => {
            this.cascadeAddress();
        })
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'store') {
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

            let editorInfo = res.data.data.list[0]

            this.ruleForm.roomid = editorInfo.roomid,
            this.ruleForm.shop_information = editorInfo.shop_information,
            this.ruleForm.confines = editorInfo.confines,
            this.ruleForm.tel = editorInfo.tel,
            this.ruleForm.sheng = editorInfo.sheng,
            this.ruleForm.shi = editorInfo.shi,
            this.ruleForm.xian = editorInfo.xian,
            this.ruleForm.address = editorInfo.address,
            this.ruleForm.shop_nature = editorInfo.shop_nature
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
            modifyMchInfo({
                api: 'admin.mch.modifyMchInfo',
                id: this.$route.query.id,
                roomid: this.ruleForm.roomid,
                mchInfo: this.ruleForm.shop_information,
                confines: this.ruleForm.confines,
                tel: this.ruleForm.tel,
                shen: this.ruleForm.sheng,
                shi: this.ruleForm.shi,
                xian: this.ruleForm.xian,
                address: this.ruleForm.address,
                nature: this.ruleForm.shop_nature
            }).then(res => {
                if(res.data.code == '200') {
                    this.$message({
                        message: '编辑成功',
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