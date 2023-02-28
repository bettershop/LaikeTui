<template>
  <div class="container">
    <div class="main-content">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="110px" class="demo-ruleForm">
        <el-form-item class="Select" label="数据名称" prop="region">
          <el-select class="select-input" v-model="ruleForm.region" placeholder="请选择数据名称">
            <el-option v-for="item in Dictionary" :key="item.id" :label="item.name" :value="item.name">
              <div @click="getId(item.id)">{{ item.name }}</div>
            </el-option>
          </el-select>
          <el-button type="primary" @click="dialogShow">添加名称</el-button>
        </el-form-item>
        <el-form-item class="code-input" label="数据编码" prop="numberCode">
          <el-input v-model="ruleForm.numberCode"></el-input>
        </el-form-item>
       <el-form-item class="Select" label="所属属性名称" prop="name" v-if="id == 16">
          <el-select class="select-input" v-model="ruleForm.name" placeholder="请选择所属属性名称">
            <el-option v-for="item in ruleForm.list" :key="item.id" :label="item.text" :value="item.text">
              <div @click="getArrId(item.id)">{{ item.text }}</div>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="code" prop="code">
          <el-input v-model="ruleForm.code" placeholder="请输入code"></el-input>
        </el-form-item>
        <el-form-item label="值" prop="value">
          <el-input v-model="ruleForm.value" placeholder="请输入值"></el-input>
        </el-form-item>
        <el-form-item label="是否生效" prop="resource">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio v-for="item in isRegisteredLists" :label="item.value" :key="item.value">{{item.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>

    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加数据名称"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
        :modal-append-to-body='true'
      >
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
          <el-form-item label="数据名称" prop="dataName">
            <el-input v-model="ruleForm2.dataName"></el-input>
          </el-form-item>
          <el-form-item label="是否生效" prop="status">
            <el-radio-group v-model="ruleForm2.status">
              <el-radio v-for="item in isRegisteredList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <div class="form-footer">
            <el-form-item>
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="determine('ruleForm2')">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>
    

  </div>
</template>

<script>
import { dictionaryDirectories, directoriesCode, addDataName, addDictionaryTable } from '@/api/Platform/numerical'
export default {
  name: 'adddictionary',

  data() {
    return {
      ruleForm: {
        region: '',
        numberCode: '',
        code: '',
        value: '',
        resource: 1,
        list:[],
        name:''
      },
      // radio1: '1',
      rules: {
        region: [
          { required: true, message: '请选择数据名称', trigger: 'change' }
        ],
        code: [
          { required: true, message: '请输入code', trigger: 'blur' },
        ],
        value: [
          { required: true, message: '请输入值', trigger: 'blur' },
        ],
        resource: [
          { required: true, message: '是', trigger: 'change' }
        ],
        name: [
          { required: true, message: '请选择所属属性名称', trigger: 'change' }
        ],
      },
      Dictionary: [],
      id: null,
      arrId:null,

      // 弹框数据
      dialogVisible: false,
      ruleForm2: {
        dataName: '',
        status: 1,
      },
      isRegisteredList: [
        {
          value: 0,
          name: '否'
        },
        {
          value: 1,
          name: '是'
        }
      ],

      isRegisteredLists: [
        {
          value: 0,
          name: '否'
        },
        {
          value: 1,
          name: '是'
        }
      ],
      rules2: {
        dataName: [
          { required: true, message: '请填写数据名称', trigger: 'change' }
        ],
        statues: [
          { required: true, message: '是', trigger: 'change' }
        ],
      }
    }
  },

  created() {
    this.getDictionaryDirectories()
  },

  watch: {
    'ruleForm.region': {
      handler() {
        this.getDirectoriesCode()
        console.log(this.id);
      },
    },
    'ruleForm.name': {
      handler() {
        this.getDirectoriesCode()
        console.log(this.id);
      },
    }
  },

  methods: {
    getId(value) {
      this.id = value
    },
    getArrId(val){
      this.arrId = val
    },

    // 获取菜单目录下拉
    async getDictionaryDirectories() {
      const res = await dictionaryDirectories({
        api: 'saas.dic.getDictionaryCatalogList',
      })
      this.Dictionary = res.data.data.data
      console.log(res);
    },

    // 获取目录编码
    async getDirectoriesCode() {
      const res = await directoriesCode({
        api: 'saas.dic.getDictionaryCode',
        id: this.id
      }).catch(() => {
        this.ruleForm.numberCode
      })
      this.ruleForm.numberCode = res.data.data.code
      this.ruleForm.list = res.data.data.list
      console.log(res);
      
    },

    // 添加/修改字典表明细
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            addDictionaryTable({
              api: 'saas.dic.addDictionaryDetailInfo',
              dataCode: this.ruleForm.numberCode,
              valueCode: this.ruleForm.value,
              valueName: this.ruleForm.code,
              isOpen: parseInt(this.ruleForm.resource),
              sid: this.id,
              attrId: this.arrId,
            }).then(res => {
              if(res.data.code == '200') {
                this.$message({
                  message: '添加成功',
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


    // 弹框方法
    dialogShow() {
      this.ruleForm2.dataName= '',
      this.ruleForm2.status= 1,
      this.dialogVisible = true
    },
    
    handleClose(done) {
      this.dialogVisible = false
    },

    // 添加字典目录
    determine(formName2) {
      this.$refs[formName2].validate(async (valid) => {
        if (valid) {
          try {
            addDataName({
              api: 'saas.dic.addDictionaryInfo',
              name: this.ruleForm2.dataName,
              isOpen: parseInt(this.ruleForm2.status),
              token: this.$store.getters.token,
            }).then(res => {
              if(res.data.code == '200') {
                console.log(res);
                this.getDictionaryDirectories()
                this.$message({
                  message: '添加成功',
                  type: 'success',
                  offset: 100
                })
                this.dialogVisible = false
              }
            })
          } catch (error) {
            this.$message({
              message: '数据名称不能为空',
              type: 'error',
              offset: 100
            })
          }
        } else {
          this.$message({
            message: '数据名称不能为空',
            type: 'error',
            offset: 100
          })
          // return false;
        }
      });
    }

  }
}
</script>

<style scoped lang="less">
.container {
  width: 100%;
  height: 737px;
  background-color: #fff;
  padding-top: 40px;
  .main-content {
    /deep/.el-form {
      width: 692px;
      margin: 0 auto;
      .el-form-item {
        display: flex;
        &:not(:last-child) {
          .el-form-item__content {
            margin-left: 0px !important;
            .el-radio-group{
              display: flex;
              align-items: center;
            }
          }
        }
      }
      .el-form-item__label {
        font-weight: normal;
      }

      .Select {
        .select-input {
          width: 486px;
        }
        button {
          width: 96px;
          height: 38px;
          border: 1px solid #2890FF;
          border-radius: 4px;
          background-color: #fff;
          color: #2890FF;
          margin-left: 10px;
        }
        
      }

      .code-input {
        input {
          background-color: #F4F7F9;
          border: 1px solid #D5DBE8;
        }
      }

      .el-form-item__content {
        display: flex;
        .el-input {
          width: 486px;
          height: 40px;
          input {
            width: 486px;
            height: 40px;
          }
        }
      }
      
    }
  }

  .dialog-block {
    // 弹框样式
    /deep/.el-dialog {
      width: 580px;
      height: 310px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      margin: 0 !important;
      .el-dialog__header {
        width: 100%;
        height: 58px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 2px solid #E9ECEF;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
        }
      }

      .el-dialog__body {
        padding: 41px 60px 0px 60px !important;
        border-bottom: 1px solid #E9ECEF;
        .el-form-item__label {
          font-weight: normal;
        }
        .form-footer {
          width: 174px;
          height: 72px;
          position: absolute;
          bottom: 0;
          right: 0;
          .el-form-item {
            padding: 0 !important;
            height: 100%;
            .el-form-item__content {
              height: 100%;
              line-height: 72px;
              margin: 0 !important;
            }
          }
        }
      }
    }
  }

}
</style>