import { getStoreConfigInfo, setStoreConfigInfo } from '@/api/plug_ins/stores'
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
export default {
  name: 'storeSet',

  components: {
    VueEditor
  },

  data() {
    return {
      radio1:'店铺设置',

      ruleForm: {
        switchs: '',
        defaultLogo: '',
        delete_day: '',
        goodsUpload: [],
        min_pric: '',
        max_price: '',
        poundage: '',
        isPromiseSwitch:'',
        bondmoney:'',
      },

      goodsUploadList: [
        {
          id: 1,
          name: '上传商品'
        },
        {
          id: 2,
          name: '自选商品'
        },
      ],

      setInfo: {},

      // 富文本编辑器数据
      content: '' ,
      remark:'',
    }
  },

  created() {
    this.getStoreConfigInfos()
  },

  methods: {
    async getStoreConfigInfos() {
      const res = await getStoreConfigInfo({
        api: 'admin.mch.getStoreConfigInfo'
      })
      console.log(res);
      let setInfo = res.data.data.data
      this.ruleForm.switchs = setInfo.is_display
      this.ruleForm.isPromiseSwitch = setInfo.promise_switch
      this.ruleForm.bondmoney = setInfo.promise_amt.toFixed(2)
      this.remark = setInfo.promise_text
      this.ruleForm.defaultLogo = setInfo.logo
      this.ruleForm.delete_day = setInfo.delete_settings
      this.ruleForm.goodsUpload = setInfo.commodity_setup.split(',').map(item => {
        return parseInt(item)
      })
      this.ruleForm.min_pric = setInfo.min_charge,
      this.ruleForm.max_price = setInfo.max_charge,
      this.ruleForm.poundage = setInfo.service_charge
      this.content = setInfo.illustrate
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

    submitForm() {
      setStoreConfigInfo({
        api: 'admin.mch.setStoreConfigInfo',
        isOpenPlugin: this.ruleForm.switchs,
        logiUrl: this.ruleForm.defaultLogo,
        outDayDel: this.ruleForm.delete_day,
        uploadType: this.ruleForm.goodsUpload.join(','),
        minWithdrawalMoney: this.ruleForm.min_pric,
        maxWithdrawalMoney: this.ruleForm.max_price,
        serviceCharge: this.ruleForm.poundage,
        illustrate: this.content,
        promiseSwitch:this.ruleForm.isPromiseSwitch,
        promiseAmt:this.ruleForm.bondmoney,
        promiseText:this.remark,
      }).then(res => {
        if(res.data.code == '200') {
          this.getStoreConfigInfos()
          this.$message({
            type: 'success',
            message: '成功!',
            offset: 100
          })
        }
        console.log(res);
      })
    },

    oninput(num, limit) {
      var str = num
      var len1 = str.substr(0, 1)
      var len2 = str.substr(1, 1)
      //如果第一位是0，第二位不是点，就用数字把点替换掉
      if (str.length > 1 && len1 == 0 && len2 != ".") {
        str = str.substr(1, 1)
      }
      //第一位不能是.
      if (len1 == ".") {
        str = ""
      }
      //限制只能输入一个小数点
      if (str.indexOf(".") != -1) {
        var str_ = str.substr(str.indexOf(".") + 1)
        if (str_.indexOf(".") != -1) {
          str = str.substr(0, str.indexOf(".") + str_.indexOf(".") + 1)
        }
      }
      //正则替换
      str = str.replace(/[^\d^\.]+/g, '') // 保留数字和小数点
      if (limit / 1 === 1) {
        str = str.replace(/^\D*([0-9]\d*\.?\d{0,1})?.*$/,'$1') // 小数点后只能输 1 位
      } else {
        str = str.replace(/^\D*([0-9]\d*\.?\d{0,2})?.*$/,'$1') // 小数点后只能输 2 位
      }

      if(this.ruleForm.price_type == 1 && this.totlePrice !== 0 && parseInt(this.ruleForm.price) > this.totlePrice) {
          str = this.totlePrice
      }
      return str
    }
  }
}