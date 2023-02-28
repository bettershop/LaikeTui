<template>
<div class="container-from">
  <div class="container-full base-form">
    <div class="base-form-container">
      <div class="form-container">
        <el-form
          :model="ruleForm"
          :rules="rules"
          ref="ruleForm"
          label-width="100px"
          class="demo-ruleForm"
        >
          <div class="form-scroll">
            <el-form-item :label="$t('DemoPage.tableFromPage.username')" prop="username">
              <el-input :disabled="pageType === 3" v-model="ruleForm.username"
                :placeholder="$t('DemoPage.tableFromPage.Enter_user_name')" maxlength="10" show-word-limit></el-input>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Active_area')" prop="region">
              <el-select :disabled="pageType === 3" v-model="ruleForm.region"
                :placeholder="$t('DemoPage.tableFromPage.Select_active_area')">
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item :label="$t('DemoPage.tableFromPage.Multi_category_display')">
              <el-select :disabled="pageType === 3" v-model="ruleForm.product_class" multiple
                filterable :placeholder="$t('DemoPage.tableFromPage.choose')">
                <el-option v-for="item in product_class_options" :key="item.value"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.superiorID')" prop="p_id">
              <el-input :disabled="pageType === 3 || pageType === 2" v-model="ruleForm.p_id"
                :placeholder="$t('DemoPage.tableFromPage.Remote_validation_example')"></el-input>
            </el-form-item>

            <el-form-item :label="$t('DemoPage.tableFromPage.cell_phone_number')" prop="phone" required>
              <el-input :disabled="pageType === 3" v-model="ruleForm.phone" :placeholder="$t('DemoPage.tableFromPage.Example_of_mobile_phone_number_verification')">
              </el-input>
            </el-form-item>

            <el-form-item class="select-w-188" :label="$t('DemoPage.tableFromPage.address')" required>
              <el-row type="flex" justify="space-between">
                <el-col :span="7">
                  <el-form-item prop="sheng_id">
                    <el-select :disabled="pageType === 3" v-model="ruleForm.sheng_id" :placeholder="$t('DemoPage.tableFromPage.province')" @change="onShengChange">
                      <el-option v-for="(item,index) of sheng_options" :key="index" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="7">
                  <el-form-item prop="shi_id">
                    <el-select :disabled="pageType === 3" v-model="ruleForm.shi_id" :placeholder="$t('DemoPage.tableFromPage.city')" @change="onShiChange">
                      <el-select :disabled="pageType === 3" v-model="ruleForm.shi_id" placeholder="市" @change="onShiChange">
													<el-option v-for="(item,index) of shi_options" :key="index" :label="item.label" :value="item.value"></el-option>
												</el-select>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :span="7">
                  <el-form-item prop="xian_id">
                    <el-select :disabled="pageType === 3" v-model="ruleForm.xian_id" :placeholder="$t('DemoPage.tableFromPage.county')" @change="onXianChange">
                      <el-option v-for="(item,index) of xian_options" :key="index" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.choose_time')" required>
              <el-col :span="11">
                <el-form-item prop="date1">
                  <el-date-picker :disabled="pageType === 3" value-format="yyyy-MM-dd"
                    :editable="false" type="date" :placeholder="$t('DemoPage.tableFromPage.Select_a_date')"
                    v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col class="line" :span="2">-</el-col>
              <el-col :span="11">
                <el-form-item prop="date2">
                  <el-time-picker :disabled="pageType === 3" value-format="HH:mm:ss"
                    :editable="false" :placeholder="$t('DemoPage.tableFromPage.choose_time')" v-model="ruleForm.date2"
                    style="width: 100%;"></el-time-picker>
                </el-form-item>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Range_of_choice')" required>
              <el-col>
                <el-form-item prop="range">
                  <el-date-picker :disabled="pageType === 3" v-model="ruleForm.range"
                    type="datetimerange" :range-separator="$t('DemoPage.tableFromPage.to')" :start-placeholder="$t('DemoPage.tableFromPage.Start_date')"
                    :end-placeholder="$t('DemoPage.tableFromPage.End_data')" value-format="yyyy-MM-dd HH:mm:ss"
                    :editable="false">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Instant_delivery')" prop="delivery">
              <el-switch active-color="#13ce66" inactive-color="#d5dbe8" :disabled="pageType === 3" v-model="ruleForm.delivery"></el-switch>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Nature_of_the_activities')" prop="type">
              <el-checkbox-group fill="#2890ff" :disabled="pageType === 3" v-model="ruleForm.type">
                <el-checkbox :label="$t('DemoPage.tableFromPage.food')" name="type"></el-checkbox>
                <el-checkbox :label="$t('DemoPage.tableFromPage.To_push_the_activity')" name="type"></el-checkbox>
                <el-checkbox :label="$t('DemoPage.tableFromPage.Offline_Theme_Activities')" name="type"></el-checkbox>
                <el-checkbox :label="$t('DemoPage.tableFromPage.Pure_Brand_Exposure')" name="type"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Special_resources')" prop="resource">
              <el-radio-group :disabled="pageType === 3" v-model="ruleForm.resource">
                <el-radio label="0">{{ $t('DemoPage.tableFromPage.Online_Brand_Sponsorship') }}</el-radio>
                <el-radio label="1">{{ $t('DemoPage.tableFromPage.Offline_venue_free') }}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Activity_form')" prop="desc">
              <el-input :disabled="pageType === 3" type="textarea" v-model="ruleForm.desc">
              </el-input>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.price')" prop="price">
              <el-input :disabled="pageType === 3" v-model="ruleForm.price"></el-input>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Keep_the_number')" prop="day">
              <el-input :disabled="pageType === 3" v-model="ruleForm.day"></el-input>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.discount')" prop="discount">
              <el-input :disabled="pageType === 3" v-model="ruleForm.discount">
                <template slot="append">%</template>
              </el-input>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Upload_photo')" prop="imgUrl">
              <l-upload :disabled="pageType === 3" :limit="1" v-model="ruleForm.imgUrl">
              </l-upload>
            </el-form-item>
            <el-form-item :label="$t('DemoPage.tableFromPage.Upload_photos')" prop="imgUrls">
              <l-upload :disabled="pageType === 3" :limit="3" v-model="ruleForm.imgUrls"
                :text="$t('DemoPage.tableFromPage.photos_info')"></l-upload>
            </el-form-item>
          </div>
          <div class="base-form-gap"></div>
          <div class="form-footer">
            <el-form-item v-if="pageType !== 3">
              <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
              <el-button class="fontColor" @click="back" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
            </el-form-item>
            <el-form-item v-else>
              <el-button @click="back" plain>返回</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</div>
</template>

<script>
async function customPidValidator(rule, value, callback) {
  if (Number.parseInt(value) > 10) {
    callback();
  } else {
    callback(new Error('父级id不存在'));
  }
}
export default {
  name: 'TableFrom',
  data() {
    return {
      ruleForm: {
        username: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        sheng: '',
        sheng_id: '',
        shi: '',
        shi_id: '',
        xian: '',
        xian_id: '',
        range: '',
        price: '',
        day: '',
        discount: '',
        product_class: [],
        p_id: '',
        phone: '',
        imgUrl: '',
        imgUrls: [],
        id: ''
      },
      sheng_options: [],
      shi_options: [],
      xian_options: [],
      product_class_options: [{
        value: 1,
        label: '黄金糕'
      }, {
        value: 2,
        label: '双皮奶'
      }, {
        value: 3,
        label: '蚵仔煎'
      }, {
        value: 4,
        label: '龙须面'
      }, {
        value: 5,
        label: '北京烤鸭'
      }],
      rules: {
        username: [
            {required: true, message: '请输入用户名称', trigger: 'blur'},
            {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        region: [
            {required: true, message: '请选择活动区域', trigger: 'change'}
        ],
        sheng_id: [
            {required: true, message: '请选择省', trigger: 'change'}
        ],
        shi_id: [
            {required: true, message: '请选择市', trigger: 'change'}
        ],
        xian_id: [
            {required: true, message: '请选择县', trigger: 'change'}
        ],
        date1: [
            { required: true, message: '请选择日期', trigger: 'change'}
        ],
        date2: [
            { required: true, message: '请选择时间', trigger: 'change'}
        ],
        // TODO: 没有加type进行验证，因为这里把 editable 禁止后 不可能选出其他的格式，加了格式以后自定义的组件会报错，原因不详
        range: [
            {required: true, message: '请选择时间', trigger: 'change'}
        ],
        type: [
            {type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change'}
        ],
        resource: [
            {required: true, message: '请选择活动资源', trigger: 'change'}
        ],
        desc: [
            {required: true, message: '请填写活动形式', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请填写价格', trigger: 'blur'},
          // {validator: customFloatValidator, trigger: 'blur'},
        ],
        day: [
          {required: true, message: '请填写保留天数', trigger: 'blur'},
          // {validator: customIntegerValidator, trigger: 'blur'},
        ],
        discount: [
            {required: true, message: '请填写折扣', trigger: 'blur'},
            // {validator: customPercentageValidator, trigger: 'blur'},
        ],
        p_id: [
            {required: true, message: '请填写父级id', trigger: 'blur'},
            // {validator: customIntegerValidator, trigger: 'blur'},
            // {validator: customPidValidator, trigger: 'blur'},
        ],
        phone: [
            {required: true, message: '请填写手机号', trigger: 'blur'},
            // {validator: customPhoneValidator, trigger: 'blur'},
        ],
        imgUrl: [
            {required: true, message: '请上传图片', trigger: 'blur'},
        ]
      },
      pageType: 1 // 1，新增，2编辑，3查看
    };
  },
  async created() {
    this.getData();
    await this.getAddress('sheng', 0)

    if (this.pageType !== 1) {
      await this.getAddress('shi', this.ruleForm.sheng_id)
      await this.getAddress('xian', this.ruleForm.shi_id)
    }
  },
  mounted  () {
  },
  computed: {
    pageName () {
      if (this.pageType === 1) {
          return '新增'
      } else if (this.pageType === 2) {
          return '编辑'
      } else if (this.pageType === 3) {
          return '查看'
      }
    }
  },
  methods: {
    getData () {
        // let id = $('#a-id').val();
        // let see = $('#a-see').val();

        // if (Number.parseInt(id)) {
        //     this.ruleForm = JSON.parse($('#a-form').html());
        //     if (Number.parseInt(see)) {
        //         // 查看
        //         this.pageType = 3;
        //     } else {
        //         // 编辑
        //         this.pageType = 2;
        //     }
        // } else {
        //     // 添加
        //     this.pageType = 1;
        // }
    },
    /**
     *
     * @returns {Promise<void>}
     */
    async onShiChange(value) {
      this.xian_options = [];
      this.ruleForm.xian = ''
      this.ruleForm.xian_id = ''

      this.setShi(value);
      await this.getAddress('xian', value)
    },
    async onShengChange(value) {
      this.setSheng(value);
      this.shi_options = [];
      this.xian_options = [];
      this.ruleForm.shi = ''
      this.ruleForm.shi_id = ''
      this.ruleForm.xian = ''
      this.ruleForm.xian_id = ''
      await this.getAddress('shi', value)
    },

    async onXianChange(value) {
      this.setXian(value);
    },
    setSheng(value) {
      for (let i = 0; i < this.sheng_options.length; i++) {
        let item = this.sheng_options[i];
        if (item.value == value) {
          this.ruleForm.sheng = item.label;
        }
      }
    },
    setShi(value) {
      for (let i = 0; i < this.shi_options.length; i++) {
        let item = this.shi_options[i];
        if (item.value == value) {
          this.ruleForm.shi = item.label;
        }
      }
    },
    setXian(value) {
      for (let i = 0; i < this.xian_options.length; i++) {
        let item = this.xian_options[i];
        if (item.value == value) {
            this.ruleForm.xian = item.label;
        }
      }
    },
    async getAddress(type, GroupID) {
      // let {
      //   code,
      //   data,
      //   message
      // } = await postRequest(`index.php?module=demo&action=AjaxDemo&GroupID=${GroupID}`)
      // if (code === 200) {
      //   if (type === 'sheng') {
      //     this.sheng_options = data;
      //   } else if (type === 'shi') {
      //     this.shi_options = data
      //   } else if (type === 'xian') {
      //     this.xian_options = data
      //   } else {
      //     console.error('类型错误')
      //   }
      // } else {

      // }
    },
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
              await this.submit('')
              this.$message({
                  message: error.message,
                  type: 'success',
                  showClose: true,
                  onClose: () => {
                      this.back()
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
    async submit (url) {
      let res = await postRequest(url, this.ruleForm)
      if (res.code === 200) {
          return res;
      } else {
          return Promise.reject(res)
      }
    },
    back() {
      window.history.back()
    },

  }
}
</script>

<style scoped lang="less">
.container-from {
  width: 100%;
  background-color: #fff;
}
/deep/.container-full {
  margin: 0 auto;
  width: 720px;
  height: 735px;
  overflow-y: auto;
  &.base-form {
    padding: 0px 20px;
    flex: 1;
    overflow: hidden;

    .base-form-container {
      background: #fff;
      padding-top: 40px;
      padding-bottom: 40px;
      height: 100%;
      box-sizing: border-box;

      .form-container {
        width: 684px;
        margin: 0 auto;
        height: 100%;

        .demo-ruleForm {
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          height: 100%;

          .line {
            text-align: center;
          }

          .form-scroll {
            //overflow-y: scroll;
            height: 100vh;
            overflow: auto;
            padding-right: 10px;
            .el-form-item:last-child {
              margin-bottom: 0;
            }
          }
          .base-form-gap {
            height: 22px;
          }

          .form-footer {
            height: 40px;
          }
        }


      }
    }
  }

  .el-form-item__label {
    color: #414658;
  }
  .el-input__inner {
    color: #414658;
    border: 1px solid #d5dbe8;
  }
  .el-input__inner:hover {
		border: 1px solid #b2bcd4;
	}
  .el-input__inner::-webkit-input-placeholder {
		color: #97a0b4;
	}
  .bgColor {
    background-color: #2890ff;
  }
  .bgColor:hover {
		opacity: 0.8;
	}
  .fontColor {
    color: #6a7076;
    border: 1px solid #d5dbe8;
    margin-left: 14px;
  }
  .fontColor:hover {
    color: #2890ff;
    border: 1px solid #2890ff;
  }
  .el-checkbox__label,.el-radio__label {
    color: #414658;
  }

  .el-input__inner:focus {
		border-color: #409eff;
	}

  .el-form-item__label {
    font-weight: normal;
  }

  // .el-checkbox__inner {
  //   background-color: #2890ff;
  //   border-color: #2890ff;
  // }

  
}
.container-from {
  border-radius: 4px;
}


</style>