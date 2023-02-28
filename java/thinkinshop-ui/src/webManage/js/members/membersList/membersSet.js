import { getUserConfigInfo, addUserRule } from '@/api/members/membersSet'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
export default {
    name: 'membersSet',
    components: {
        VueEditor
    },
    data() {
        return {
            radio1:'会员设置',

            ruleForm: {
                // 基础信息
                wx_headimgurl: '',// 默认头像设置
                wx_name: '',// 默认昵称设置
                is_auto: '',// 自动续费提醒
                auto_time: '',
                str_method: [],// 开通方式
                preferentialGoods: [],// 享受优惠商品
                is_wallet: '',// 开通余额支付
                upgrade: '',// 等级晋升设置
                is_birthday: '',// 会员生日特权
                bir_multiple: '',
                is_jifen: '',// VIP等比例积分
                pointsOut: '',
                is_product: '',// 会员赠送商品
                valid: '',
                poster: '',// 分享海报设置
        
            },

            pointsOutList1: [
                {
                    value: 1,
                    name: '收货后'
                },
                {
                    value: 2,
                    name: '付款后'
                },
            ],

            openModeList: [
                {
                    value: '1',
                    name: '包月'
                },
                {
                    value: '2',
                    name: '包季'
                },
                {
                    value: '3',
                    name: '包年'
                },
            ],

            checkAll: false,
            isIndeterminate: true,
            preferentialGoodsList: [
                {
                    id: '1',
                    name: '正价商品'
                },
                {
                    id: '2',
                    name: '拼团商品'
                },
                {
                    id: '3',
                    name: '砍价商品'
                },
                {
                    id: '4',
                    name: '竞拍商品'
                },
            ],

            levelSetList: [
                {
                    id: '1',
                    name: '购买会员服务'
                },
                {
                    id: '2',
                    name: '补差额升级'
                },
            ],

            pointsOutList2: [
                {
                    value: 1,
                    name: '收货后'
                },
                {
                    value: 0,
                    name: '付款后'
                },
            ],

            open: false,
            open2: false,

            // 富文本编辑器数据
            content: '' 
        }
    },

    created() {
        this.getUserConfigInfos()
    },

    watch: {

    },

    methods: {
        async getUserConfigInfos() {
            const res = await getUserConfigInfo({
                api: 'admin.user.getUserConfigInfo'
            })
            console.log(res);
            let userInfo = res.data.data
            let setInfo = res.data.data.gradeRule[0]
            this.ruleForm.wx_headimgurl = userInfo.wx_headimgurl,
            this.ruleForm.wx_name = userInfo.wx_name,
            this.ruleForm.is_auto = setInfo.is_auto,
            this.ruleForm.auto_time = setInfo.auto_time,
            this.ruleForm.is_wallet = setInfo.is_wallet,
            this.ruleForm.is_birthday = setInfo.is_birthday,
            this.ruleForm.bir_multiple = setInfo.bir_multiple,
            this.ruleForm.is_jifen = setInfo.is_jifen,
            this.ruleForm.pointsOut = setInfo.jifen_m,
            this.ruleForm.is_product = setInfo.is_product,
            this.ruleForm.valid = setInfo.valid,
            this.ruleForm.poster = setInfo.poster
            
            this.ruleForm.upgrade = setInfo.upgrade === "1,2" ? true : false,
            this.ruleForm.str_method = setInfo.method.split(','),
            this.ruleForm.preferentialGoods = setInfo.active.split(',')
            this.content = setInfo.rule
        },

        handleImageAdded(file, Editor, cursorLocation, resetUploader) {
            let random_name = new Date().getTime() + '.' + file.name.split('.').pop()
            const client = new OSS({
              region: "oss-cn-shenzhen.aliyuncs.com",
              secure: true,
              endpoint: 'oss-cn-shenzhen.aliyuncs.com',
              accessKeyId: "LTAI4Fm8MFnadgaCdi6GGmkN",
              accessKeySecret: "NhBAJuGtx218pvTE4IBTZcvRzrFrH4",
              bucket: 'laikeds'
            });
            client.multipartUpload(random_name, file)
            .then(res => {
              console.log(res);
              Editor.insertEmbed(cursorLocation, 'image', res.res.requestUrls[0])
              resetUploader()
            })
            .catch(err => {
              console.log(err)
            })
        },
        
        handleCheckAllChange(val) {
            console.log(this.checkAll);
            this.ruleForm.preferentialGoods = val ? this.preferentialGoodsList.map(item => { return item.id }) : [];
            this.isIndeterminate = false;
        },
        handleCheckedCitiesChange(value) {
            let checkedCount = value.length;
            this.checkAll = checkedCount === this.preferentialGoodsList.length;
            this.isIndeterminate = checkedCount > 0 && checkedCount < this.preferentialGoodsList.length;
        },

        switchs1(value) {
            console.log(value);
            if(value == 1) {
                this.open = true
            } else {
                this.open = false
            }
        },

        switchs2(value) {
            console.log(value);
            if(value == 1) {
                this.open2 = true
            } else {
                this.open2 = false
            }
        },

        submitForm() {
            addUserRule({
                api: 'admin.user.addUserRule',
                wxImgUrl: this.ruleForm.wx_headimgurl,
                wxName: this.ruleForm.wx_name,
                isAutoSwitch: this.ruleForm.is_auto,
                autoDayExpire: this.ruleForm.auto_time,
                method: this.ruleForm.str_method.join(','),
                active: this.ruleForm.preferentialGoods.join(','),
                isWallet: this.ruleForm.is_wallet,
                upgrade: this.ruleForm.upgrade == true ? '2' : '',
                isBirthday: this.ruleForm.is_birthday,
                birMultiple: this.ruleForm.bir_multiple,
                isProduct: this.ruleForm.is_product,
                valid: this.ruleForm.valid,
                isJifen: this.ruleForm.is_jifen,
                jifenM: this.ruleForm.pointsOut,
                poster: this.ruleForm.poster,
                rule: this.content
            }).then(res => {
                if(res.data.code == '200') {
                    this.$message({
                        message: '保存成功',
                        type: 'success',
                        offset:100
                    })
                    this.getUserConfigInfos()
                }
                console.log(res);
            })
        }

    }
}