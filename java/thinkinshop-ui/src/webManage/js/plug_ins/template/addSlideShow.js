import { bannerPathList, bannerSave } from '@/api/plug_ins/template'
export default {
    name: 'addSlideShow',
    data() {
        return {
            ruleForm: {
                img: '',
                class1: 2,
                url: ''
            },

            classList1: [
                {
                    value: 2,
                    label: '商品'
                },
                {
                    value: 1,
                    label: '分类'
                },
                {
                    value: 3,
                    label: '店铺'
                },
                {
                    value: 0,
                    label: '自定义'
                }
            ],

            classList2: [],
            baseUrl: '/pages/goods/goodsDetailed?productId=',

        }
    },

    watch: {
        'ruleForm.class1'() {
            if(this.ruleForm.class1 == 1) {
                this.baseUrl = '/pages/goods/goods?cid='
                this.ruleForm.url = ''
                this.bannerPathLists(this.ruleForm.class1)
            } else if(this.ruleForm.class1 == 3) {
                this.baseUrl = '/pagesA/store/store?shop_id='
                this.ruleForm.url = ''
                this.bannerPathLists(this.ruleForm.class1)
            } else if(this.ruleForm.class1 == 0) {
                this.baseUrl = ''
                this.ruleForm.url = ''
                this.classList2 = []
            } else {
                this.baseUrl = '/pages/goods/goodsDetailed?productId='
                this.ruleForm.url = ''
                this.bannerPathLists(this.ruleForm.class1)
            }
        }
    },

    created() {
        this.bannerPathLists(2)
    },
    
    methods: {
        async bannerPathLists(value) {
            const res = await bannerPathList({
                api: 'admin.diy.bannerPathList',
                type: value
            })
            console.log(res);
            this.classList2 = res.data.data.list
        },

        submitForm() {
            bannerSave({
                api: 'admin.diy.bannerSave',
                type0: this.ruleForm.class1,
                url: this.getUrl(),
                picUrl: this.ruleForm.img
            }).then(res => {
                if(res.data.code == '200') {
                    this.$message({
                        message: '添加成功',
                        type: 'success',
                        offset:100
                    })
                    this.$router.go(-1)
                }
                console.log(res);
            })
        },

        getUrl() {
            if(this.ruleForm.class1 == 0) {
                return this.ruleForm.url
            } else {
                if(this.ruleForm.url) {
                    return this.baseUrl + this.ruleForm.url.split('=')[1]
                } else {
                    return ''
                }
            }
        }
    }
    
}