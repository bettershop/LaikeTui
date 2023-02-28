import { addUserGrade, getGiveGoodsList, getUserGradeInfo } from '@/api/members/membersLevel'
export default {
    name: 'addLevel',

    data() {
        return {
            ruleForm: {
                levelNamme: '',
                bgImgUrl: '',
                smallLogo: '',
                membersFontColor: '',
                dateFontColor: '',
                discount: '',
                month: '',
                season: '',
                years: '',
                givingGoods: '',
                note: ''
            },

            membersChange: '',

            givingGoodsList: [],// 会员赠送商品列表
        }
    },

    created() {
        this.getGiveGoodsLists()

        if(this.$route.query.type) {
            this.membersChange = '编辑会员等级'
            this.$route.meta.title = '编辑会员等级'
            this.getUserGradeInfos()
        } else {
            this.membersChange = '添加会员等级'
            this.$route.meta.title = '添加会员等级'
        }
    },

    beforeRouteLeave (to, from, next) {        
        if (to.name == 'membersLevel' && this.$route.query.type == 'editor') {
          to.params.dictionaryNum = this.$route.query.dictionaryNum
          to.params.pageSize = this.$route.query.pageSize
        }   
        next();
    },

    methods: {
        // 获取等级信息
        async getUserGradeInfos() {
            const res = await getUserGradeInfo({
                api: 'admin.user.getUserGradeInfo',
                gid: this.$route.query.id
            })
            let levelInfo = res.data.data.list[0]

            this.ruleForm.levelNamme = levelInfo.name
            this.ruleForm.bgImgUrl = levelInfo.imgurl
            this.ruleForm.smallLogo = levelInfo.imgurl_s
            this.ruleForm.membersFontColor = levelInfo.font_color
            this.ruleForm.dateFontColor = levelInfo.date_color
            this.ruleForm.discount = levelInfo.rate
            this.ruleForm.month = levelInfo.money
            this.ruleForm.season = levelInfo.money_j
            this.ruleForm.years = levelInfo.money_n
            this.ruleForm.givingGoods = levelInfo.pro_id
            this.ruleForm.note = levelInfo.remark
        },

        // 获取赠送商品下拉数据
        async getGiveGoodsLists() {
            const res = await getGiveGoodsList({
                api: 'admin.user.getGiveGoodsList'
            })
            this.givingGoodsList = res.data.data.list
        },

        // 添加等级
        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
              console.log(this.ruleForm);
              if (valid) {
                try {
                    addUserGrade({
                        api: 'admin.user.addUserGrade',
                        name: this.ruleForm.levelNamme,
                        backImage: this.ruleForm.bgImgUrl,
                        miniImage: this.ruleForm.smallLogo,
                        fontColor: this.ruleForm.membersFontColor,
                        dateColor: this.ruleForm.dateFontColor,
                        discountRate: this.ruleForm.discount,
                        money: this.ruleForm.month,
                        moneyJ: this.ruleForm.season,
                        moneyN: this.ruleForm.years,
                        goodsId: this.ruleForm.givingGoods,
                        givingGoods: this.ruleForm.givingGoods,
                        remarks: this.ruleForm.note,
                        id: this.$route.query.id ? this.$route.query.id : null
                    }).then(res => {
                        if(res.data.code == '200') {
                            this.$message({
                                message: '添加成功',
                                type: 'success',
                                offset:100
                            })
                            this.$router.go(-1)
                        }
                    })
                } catch (error) {
                  this.$message({
                      message: error.message,
                      type: 'error',
                      showClose: true
                  })
                }
              } else {
                  console.log('error submit!!');
                  return false;
              }
            });
        },
    }
}