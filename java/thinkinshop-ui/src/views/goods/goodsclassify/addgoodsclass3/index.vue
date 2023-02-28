<template>
  <div class="container">
    <div class="add-menu">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="100px">
        <el-form-item label="分类名称" prop="classname">
          <el-input v-model="ruleForm.classname" placeholder="请输入分类名称"></el-input>
        </el-form-item>
        <el-form-item label="英文副标题" prop="subtitle">
          <el-input v-model="ruleForm.subtitle" placeholder="请输入英文副标题"></el-input>
        </el-form-item>
        <el-form-item label="分类等级" prop="classlevel">
          <el-select class="select-input" v-model="ruleForm.classlevel" disabled placeholder="请选择分类等级">
            <el-option v-for="(item,index) in menuLevelList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上级分类" prop="" v-if="inputShow !== 1">
          <div class="superior">
            <el-select v-show="inputShow >= 2" class="select-input" disabled v-model="ruleForm.select_2" placeholder="请选择一级菜单">
              <el-option v-for="(item,index) in options1" :key="index" :label="item.pname" :value="item.cid">
              </el-option>
            </el-select>
            <el-select v-show="inputShow >= 3" class="select-input" disabled v-model="ruleForm.select_3" placeholder="请选择二级菜单">
              <el-option v-for="(item,index) in options2" :key="index" :label="item.pname" :value="item.cid">
              </el-option>
            </el-select>
            <el-select  v-show="inputShow >= 4" class="select-input" v-model="ruleForm.select_4" placeholder="请选择三级菜单">
              <el-option v-for="(item,index) in options3" :key="index" :label="item.pname" :value="item.cid">
              </el-option>
            </el-select>
            <el-select  v-show="inputShow >= 5" class="select-input" v-model="ruleForm.select_5" placeholder="请选择四级菜单">
              <el-option v-for="(item,index) in options4" :key="index" :label="item.pname" :value="item.cid">
              </el-option>
            </el-select>
          </div>
        </el-form-item>
        <el-form-item class="upload-img" label="分类图标" prop="classLogo">
            <l-upload
              :limit="1"
              v-model="ruleForm.classLogo"
              text="最多上传一张，建议上传24*24px尺寸的图片"
            >
            </l-upload>
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
import { getClassInfo, addClass, getClassLevelTopAllInfo } from '@/api/goods/goodsClass'
export default {
  name: 'addgoodsclass3',

  data() {
    return {
      ruleForm: {
        classname: '',
        subtitle: '',
        classlevel: 3,
        classLogo: '',
        // superiorclass: ''
        select_2: '',
        select_3: '',
        select_4: '',
        select_5: '',
      },
      inputShow: 3,
      rules: {
        classname: [
          { required: true, message: '请填写菜单名称', trigger: 'blur' }
        ],
        subtitle: [
          { required: true, message: '请填写导览名称', trigger: 'blur' }
        ],
        classlevel: [
          { required: true, message: '请填写菜单等级', trigger: 'change' }
        ],
        superiorclass: [
          { required: true, message: '请填写上级分类', trigger: 'change' }
        ],
      },

      menuLevelList: [
        { 
          value: 1,
          label: '一级'
        },
        { 
          value: 2,
          label: '二级'
        },
        { 
          value: 3,
          label: '三级'
        },
        { 
          value: 4,
          label: '四级'
        },
        { 
          value: 5,
          label: '五级'
        }
      ],

      options1: [],

      options2: [],

      options3: [],

      options4: [],
    }
  },

  watch: {
    'ruleForm.classlevel'() {
      if(this.ruleForm.classlevel === 2) {
        this.inputShow = 2
      } else if (this.ruleForm.classlevel === 3) {
        this.inputShow = 3
      } else if (this.ruleForm.classlevel === 4) {
        this.inputShow = 4
      } else if (this.ruleForm.classlevel === 5) {
        this.inputShow = 5
      } else {
        this.inputShow = 1
      }
    },

  },

  created() {
    this.getClassLevelTopAllInfos()
  },

  methods: {
    async getClassInfos() {
      const res = await getClassInfo({
        api: 'admin.goods.getClassInfo',
        pageSize: 100,
      })
      console.log(res);
      this.options1 = res.data.data.classInfo
    },

    // 获取当前分类的所有上级分类
    async getClassLevelTopAllInfos() {
      const res = await getClassLevelTopAllInfo({
        api: 'admin.goods.getClassLevelTopAllInfo',
        classId: this.$route.query.id
      })
      console.log(res);
      this.options1 = res.data.data.levelInfoList[0]
      this.ruleForm.select_2 = res.data.data.levelInfoList[0][0].cid
      this.options2 = res.data.data.levelInfoList[1]
      this.ruleForm.select_3 = res.data.data.levelInfoList[1][0].cid
    },

    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            if(this.inputShow === 1) {
              addClass({
                api: 'admin.goods.addClass',
                className: this.ruleForm.classname,
                ename: this.ruleForm.subtitle,
                level: this.ruleForm.menulevel,
                img: this.ruleForm.classLogo
              }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.$message({
                    message: '添加成功',
                    type: 'success',
                    offset:100
                  })
                  this.$router.go(-1)
                }
              })
            } else {
              addClass({
                api: 'admin.goods.addClass',
                className: this.ruleForm.classname,
                ename: this.ruleForm.subtitle,
                level: 2,
                fatherId: this.getfatherMenuId(this.inputShow),
                img: this.ruleForm.classLogo
              }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.$message({
                    message: '添加成功',
                    type: 'success',
                    offset:100
                  })
                  this.$router.go(-1)
                }
              })
            }
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

    getType(value) {
      if(value === '商城') {
          return 0
      } else {
          return 1
      }
    },

    getfatherMenuId(value) {
      if(this.inputShow === 2) {
        return this.ruleForm.select_2
      } else if(this.inputShow === 3) {
        if(this.ruleForm.select_3 === '') {
          return this.ruleForm.select_2
        } else {
          return this.ruleForm.select_3
        }
      } else if(this.inputShow === 4) {
        if(this.ruleForm.select_4 === '') {
          return this.ruleForm.select_3
        } else if(this.ruleForm.select_3 === '') {
          return this.ruleForm.select_2
        } else {
          return this.ruleForm.select_4
        }
      } else {
        if(this.ruleForm.select_5 === '') {
          return this.ruleForm.select_4
        } else if(this.ruleForm.select_4 === '') {
          return this.ruleForm.select_3
        } else if(this.ruleForm.select_3 === '') {
          return this.ruleForm.select_2
        } else {
          return this.ruleForm.select_5
        }
      }
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

    .superior {
      display: flex;
      width: 580px;
      .el-select {
        width: auto;
        flex: 1;
        &:not(:first-child) {
          margin-left: 8px;
        }
        .el-input {
          width: auto;
        }
      }
    }
      
  }

  /deep/.el-form-item__label {
    font-weight: normal;
  }

}
</style>