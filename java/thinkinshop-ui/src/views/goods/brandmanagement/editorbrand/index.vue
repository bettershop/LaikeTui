<template>
  <div class="container">
    <div class="add-menu">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="90px">
        <el-form-item label="品牌名称" prop="brandname">
          <el-input v-model="ruleForm.brandname" placeholder="请输入品牌名称"></el-input>
        </el-form-item>
        <el-form-item class="upload-img" label="品牌logo" prop="brandLogo" required>
            <l-upload
              :limit="1"
              v-model="ruleForm.brandLogo"
              text="建议上传120*120px的图片"
            >
            </l-upload>
        </el-form-item>
        <el-form-item label="所属分类" prop="brandtype">
          <el-select class="select-input" multiple v-model="ruleForm.brandtype" placeholder="请选择所属分类">
            <el-option
              v-for="item in brandTypeList"
              :key="item.cid"
              :label="item.pname"
              :value="item.cid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属国家" prop="countries">
          <el-select class="select-input" v-model="ruleForm.countries" placeholder="请选择所属国家">
            <el-option v-for="(item,index) in countriesList" :key="index" :label="item.zh_name" :value="item.id">
              <div @click="getIds(item.id)">{{ item.zh_name }}</div>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="textarea" label="备注" prop="note">
          <el-input type="textarea" v-model="ruleForm.note" placeholder="请填写备注"></el-input>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { addBrand, getClassInfo, getCountry } from '@/api/goods/brandManagement'
export default {
  name: 'editorbrand',

  data() {
    return {
      ruleForm: {
        brandname: '',
        brandLogo: '',
        brandtype: '',
        countries: '',
        note: '',
      },
      rules: {
        brandtype: [
          { required: true, message: '请填所属分类', trigger: 'change' }
        ],
        countries: [
          { required: true, message: '请填写所属国家', trigger: 'change' }
        ],
        brandname: [
          { required: true, message: '请填写品牌名称', trigger: 'blur' }
        ],
        brandLogo: [
          { required: true, message: '请上传品牌logo', trigger: 'change' }
        ],
      },

      brandTypeList: [],
      countriesList: [],
      id: null,
      countriesId: null
    }
  },

  created() {
    this.getClassInfos().then(() => {
      this.ruleForm.brandtype = this.brandTypeList.filter(item => {
        if(this.$route.params.categories.includes(item.pname)) {
          return item.cid
        }
      }).map(item => {
        return item.cid
      })
    })
    this.getCountrys().then(() => {
      this.ruleForm.countries = parseInt(this.$route.params.producer)
    })
    this.ruleForm.brandname = this.$route.params.brand_name
    this.ruleForm.brandLogo = this.$route.params.brand_pic
    
    this.ruleForm.note = this.$route.params.remarks
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'brandlist') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },

  mounted() {
    for(var i=0; i<this.brandTypeList.length - 1; i++) {
      if(this.brandTypeList[i].pname === this.$route.params.categories) {
        this.id = this.brandTypeList[i].cid
        break
      }
    }
  },

  methods: {
    // 获取所属分类
    async getClassInfos() {
        const res = await getClassInfo({
          api: 'admin.goods.getClassInfo',
          pageNo: 1,
          pageSize: 999,
        })
        console.log(res);
        this.brandTypeList = res.data.data.classInfo
    },

    // 获取国家列表
    async getCountrys() {
        const res = await getCountry({
            api: 'admin.goods.getCountry',
        })
        console.log(res);
        this.countriesList = res.data.data
    },

    getId(value) {
      this.id = value
    },

    getIds(value) {
      this.countriesId = value
    },

    // getId(value) {
    //   value = Array.from(value)
    //   value.foreach(item => {
    //     if(item.pname === this.$route.params.categories) {
    //       this.id = item.cid
    //     }
    //   })
    // },

    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            addBrand({
                api: 'admin.goods.addBrand',
                brandId: this.$route.params.brand_id,
                brandName: this.ruleForm.brandname,
                brandLogo: this.ruleForm.brandLogo,
                brandClass: this.ruleForm.brandtype.join(','),
                producer: this.ruleForm.countries,
                remarks: this.ruleForm.note
            }).then(res => {
              if(res.data.code == '200') {
                console.log(res);
                this.$message({
                  message: '编辑成功',
                  type: 'success',
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
</script>

<style scoped lang="less">
.container {
  width: 100%;
  height: 737px;
  background-color: #fff;
  padding: 40px 0 0 0;
  color: #414658;
  /deep/.add-menu {
    display: flex;
    justify-content: center;
    .el-form-item {
      display: flex;
      &:not(:last-child) {
        .el-form-item__content {
          margin-left: 0px !important;
        }
      }
      .el-form-item__content {
        width: 580px;
      }
      .select-input {
        width: 580px;
        height: 40px;
      }
    }
      
  }

  /deep/.el-form-item__label {
    font-weight: normal;
  }

}
</style>