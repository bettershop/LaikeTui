<template>
  <div class="container">
    <div class="add-menu">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="90px">
        <el-form-item label="菜单类型" prop="menutype">
          <el-select class="select-input" v-model="ruleForm.menutype" placeholder="请输入菜单类型">
            <el-option v-for="(item,index) in menuTypeList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单等级" prop="menulevel">
          <el-select class="select-input" v-model="ruleForm.menulevel" placeholder="请输入菜单等级">
            <el-option v-for="(item,index) in menuLevelList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuname">
          <el-input v-model="ruleForm.menuname" placeholder="请输入菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="path" prop="path">
          <el-input v-model="ruleForm.path" placeholder="请输入path"></el-input>
        </el-form-item>
        <el-form-item label="是否核心" prop="is_core">
          <el-radio-group v-model="ruleForm.is_core">
            <el-radio v-for="label in coreList" :label="label.value" :key="label.value">{{label.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上级菜单" prop="superiorclass" v-if="inputShow !== 1">
          <div class="superior">
            <el-select v-show="inputShow >= 2" class="select-input" v-model="ruleForm.select_2" placeholder="请选择一级菜单">
              <el-option v-for="(item,index) in options1" :key="index" :label="item.title" :value="item.id">
              </el-option>
            </el-select>
            <el-select v-show="inputShow >= 3" class="select-input" v-model="ruleForm.select_3" placeholder="请选择二级菜单">
              <el-option v-for="(item,index) in options2" :key="index" :label="item.title" :value="item.id">
              </el-option>
            </el-select>
            <el-select  v-show="inputShow >= 4" class="select-input" v-model="ruleForm.select_4" placeholder="请选择三级菜单">
              <el-option v-for="(item,index) in options3" :key="index" :label="item.title" :value="item.id">
              </el-option>
            </el-select>
          </div>
        </el-form-item>
         <el-form-item label="菜单地址" prop="menulocal" v-if="inputShow !== 1">
          <el-input v-model="ruleForm.menulocal" placeholder="请输入菜单地址"></el-input>
        </el-form-item>
        <el-form-item label="导览名称" prop="tourname">
          <el-input v-model="ruleForm.tourname" placeholder="请输入导览名称"></el-input>
        </el-form-item>
        <el-form-item label="导览简介" prop="tourintroduction" v-if="inputShow !== 1">
          <el-input v-model="ruleForm.tourintroduction" placeholder="请输入导览简介（最多28字）"></el-input>
        </el-form-item>
        <el-form-item class="upload-img" label="默认图标" prop="defaultLogo" required v-if="inputShow === 1">
            <l-upload
              :limit="1"
              v-model="ruleForm.defaultLogo"
              text="最多上传一张，建议上传24*24px尺寸的图片"
            >
            </l-upload>
        </el-form-item>
        <el-form-item class="upload-img" label="选中图标" prop="selectLogo" required v-if="inputShow === 1">
            <l-upload
              :limit="1"
              v-model="ruleForm.selectLogo"
              text="最多上传一张，建议上传24*24px尺寸的图片"
            >
            </l-upload>
        </el-form-item>
        <el-form-item class="upload-img" label="导览图标" prop="tourlogo" v-if="inputShow !== 1">
            <l-upload
              :limit="1"
              v-model="ruleForm.tourlogo"
              text="最多上传一张，建议上传64*64px尺寸的图片"
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
import { addMenuInfo, getMenuLeveInfo } from '@/api/Platform/permissions'
export default {
  name: 'editormenulevel',

  data() {
    return {
      ruleForm: {
        menutype: '',
        menulevel: 1,
        menuname: '',
        path: '',
        is_core: 1,
        select_2: '',
        select_3: '',
        select_4: '',
        menulocal: '',
        tourname: '',
        tourintroduction: '',
        defaultLogo: '',
        selectLogo: '',
        tourlogo: ''
      },
      rules: {
        menutype: [
          { required: true, message: '请填菜单类型', trigger: 'change' }
        ],
        menulevel: [
          { required: true, message: '请填写菜单等级', trigger: 'change' }
        ],
        menuname: [
          { required: true, message: '请填写菜单名称', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '请填写菜单path', trigger: 'blur' }
        ],
        is_core: [
          { required: true, message: '请选择是否核心菜单', trigger: 'change' }
        ],
        menulocal: [
          { required: true, message: '请填写菜单地址', trigger: 'blur' }
        ],
        defaultLogo: [
          { required: true, message: '请上传默认图标', trigger: 'change' }
        ],
        selectLogo: [
          { required: true, message: '请上传选中图标', trigger: 'change' }
        ],
      },

      inputShow: 1,

      menuTypeList: [
        { 
          value: 1,
          label: '商城'
        },
        { 
          value: 0,
          label: '控制台'
        }
      ],

      coreList: [
        { 
          value: 1,
          label: '是'
        },
        { 
          value: 0,
          label: '否'
        }
      ],

      menuLevelList: [
        { 
          value: 1,
          label: '一级菜单'
        },
        { 
          value: 2,
          label: '二级菜单'
        },
        { 
          value: 3,
          label: '三级菜单'
        },
        { 
          value: 4,
          label: '四级菜单'
        }
      ],

      options1: [],

      options2: [],

      options3: [],

      options4: [],
    }
  },

  watch: {
    'ruleForm.defaultLogo'() {
      if(this.ruleForm.defaultLogo) {
        this.$refs.ruleForm.clearValidate('defaultLogo')
      }
    },

    'ruleForm.selectLogo'() {
      if(this.ruleForm.selectLogo) {
        this.$refs.ruleForm.clearValidate('selectLogo')
      }
    },

    'ruleForm.menulevel'() {
      if(this.ruleForm.menulevel === 2) {
        this.inputShow = 2
        this.ruleForm.select_3 = ''
        this.ruleForm.select_4 = ''
      } else if (this.ruleForm.menulevel === 3) {
        this.inputShow = 3
        this.ruleForm.select_4 = ''
      } else if (this.ruleForm.menulevel === 4) {
        this.inputShow = 4
      }else {
        this.inputShow = 1
        this.ruleForm.select_2 = ''
        this.ruleForm.select_3 = ''
        this.ruleForm.select_4 = ''
      }
    },

    'ruleForm.select_2'(newVal,oldVal){
			if(oldVal) {
        this.ruleForm.select_3 = ''
        this.ruleForm.select_4 = ''
      }
      getMenuLeveInfo({
        api: 'saas.role.getMenuLeveInfo',
        pageSize: 100,
        sid: newVal
      }).then(res => {
        this.options2 = res.data.data.list
      })
		},

		'ruleForm.select_3'(newVal,oldVal){
			console.log(newVal);
      if(oldVal) {
        this.ruleForm.select_4 = ''
      }
      getMenuLeveInfo({
        api: 'saas.role.getMenuLeveInfo',
        pageSize: 100,
        sid: newVal
      }).then(res => {
        this.options3 = res.data.data.list
      })
		},

    'ruleForm.select_4'(newVal){
			console.log(newVal);
      this.ruleForm.select_5 = ''
      getMenuLeveInfo({
        api: 'saas.role.getMenuLeveInfo',
        pageSize: 100,
        sid: newVal
      }).then(res => {
        this.options4 = res.data.data.list
      })
		},
  },

  created() {
    this.getMenuLeveInfos()
    this.ruleForm.menulevel = this.$route.query.menulevel
    this.ruleForm.menuname = this.$route.params.title
    this.ruleForm.path = this.$route.params.module
    this.ruleForm.is_core = this.$route.params.is_core
    this.ruleForm.menulocal = this.$route.params.url
    this.ruleForm.tourname = this.$route.params.guide_name
    this.ruleForm.defaultLogo = this.$route.params.image
    this.ruleForm.selectLogo = this.$route.params.image1
    this.ruleForm.menutype = this.$route.params.type
    this.ruleForm.tourintroduction = this.$route.params.briefintroduction
    this.ruleForm.tourlogo = this.$route.params.image1
    if(this.$route.query.menulevel == 2) {
        getMenuLeveInfo({
          api: 'saas.role.getMenuLeveInfo',
          pageSize: 100,
          id: this.$route.params.id
        }).then(res => {
          console.log(res);
          this.ruleForm.select_2 = res.data.data.list[0].s_id
        })
    } else if (this.$route.query.menulevel == 3) {
        getMenuLeveInfo({
          api: 'saas.role.getMenuLeveInfo',
          pageSize: 100,
          id: this.$route.params.s_id
        }).then(res => {
          this.ruleForm.select_2 = res.data.data.list[0].s_id
          this.ruleForm.select_3 = res.data.data.list[0].id
        })
    } else if (this.$route.query.menulevel == 4) {
      getMenuLeveInfo({
        api: 'saas.role.getMenuLeveInfo',
        pageSize: 100,
        id: this.$route.params.s_id
      }).then(res => {
        console.log(res);
        this.ruleForm.select_2 = res.data.data.list[0].s_id
        this.ruleForm.select_3 = this.ruleForm.select_3 = res.data.data.list[0].id          
        this.ruleForm.select_4 = this.$route.params.id
      })

    }
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'menulist' || to.name == 'viewmenu') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },


  methods: {
    // 获取等级菜单列表
    async getMenuLeveInfos() {
      const res = await getMenuLeveInfo({
        api: 'saas.role.getMenuLeveInfo',
        pageSize: 100,
      })
      console.log(res);
      this.options1 = res.data.data.list
      console.log(this.options1);
    },

    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            if(this.inputShow === 1) {
              addMenuInfo({
                api: 'saas.role.addMenuInfo',
                menuClass: this.getType(this.ruleForm.menutype),
                level: this.ruleForm.menulevel,
                menuName: this.ruleForm.menuname,
                path: this.ruleForm.path,
                menuUrl: this.ruleForm.menulocal,
                guideName :this.ruleForm.tourname,
                defaultLogo: this.ruleForm.defaultLogo,
                chekedLogo: this.ruleForm.selectLogo,
                isCore: this.ruleForm.is_core,
                menuClass: this.ruleForm.menutype,
                mid: this.$route.params.id,
              }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.$message({
                    message: '修改成功',
                    type: 'success',
                    offset:100
                  })
                  this.$router.go(-1)
                }
              })
            } else {
              addMenuInfo({
                api: 'saas.role.addMenuInfo',
                menuClass: this.getType(this.ruleForm.menutype),
                level: this.ruleForm.menulevel,
                menuName: this.ruleForm.menuname,
                path: this.ruleForm.path,
                fatherMenuId: this.getfatherMenuId(this.inputShow),
                menuUrl: this.ruleForm.menulocal,
                guideName :this.ruleForm.tourname,
                briefintroduction: this.ruleForm.tourintroduction,
                chekedLogo: this.ruleForm.tourlogo,
                isCore: this.ruleForm.is_core,
                menuClass: this.ruleForm.menutype,
                mid: this.$route.params.id
              }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.$message({
                    message: '修改成功',
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
      if(value === '商城菜单') {
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
  height: 100%;
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