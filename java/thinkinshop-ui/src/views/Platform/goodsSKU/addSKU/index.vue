<template>
  <div class="container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item class="Select" label="属性名称" prop="region">
        <el-select v-if="!$route.query.name" class="select-input" v-model="ruleForm.region" placeholder="请选择数据名称">
          <el-option v-for="item in Dictionary" :key="item.id" :label="item.name" :value="item.name">
            <div @click="getId(item.id)">{{ item.name }}</div>
          </el-option>
        </el-select>
        <el-button v-if="!$route.query.name" type="primary" @click="dialogShow">添加名称</el-button>
        <span v-if="$route.query.name">{{ ruleForm.region }}</span>
      </el-form-item>
      <el-form-item class="attribute-values" label="属性值" prop="attributeValues">
        <div class="add-info" v-for="(item,index) in attributeList" :key="index">
          <el-input v-model="attributeList[index].value" placeholder="请输入属性值"></el-input>
          <div class="add-reduction">
            <i class="el-icon-remove-outline" @click="minus(index)" v-if="attributeList.length !== 1"></i>
            <i class="el-icon-circle-plus-outline" @click="addOne" v-show="index === attributeList.length - 1"></i>
          </div>
        </div>
      </el-form-item>
      <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
      </div>
    </el-form>

    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加属性名称"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
          <el-form-item label="数据名称" prop="dataName">
            <el-input v-model="ruleForm2.dataName"></el-input>
          </el-form-item>
          <el-form-item label="是否生效" prop="status">
            <el-radio-group v-model="ruleForm2.status">
              <el-radio v-model="radio" label="1">是</el-radio>
              <el-radio v-model="radio" label="0">否</el-radio>
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
import { getSkuAttributeList, addSkuName, addSku, getSkuInfo } from '@/api/Platform/goodsSku'
export default {
  name: 'addSKU',

  data() {
    return {
      ruleForm: {
        region: '',
      },
      sid: '',
      rules: {
        region: [
          { required: true, message: '请选择属性名称', trigger: 'change' }
        ]
      },
      Dictionary: [],
      attributeList: [],
      attribute: '',

      // 弹框数据
      dialogVisible: false,
      ruleForm2: {
        dataName: '',
        status: '1',
      },
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
    this.getSkuAttributeLists()
    if(this.$route.query.name) {
      this.getSkuInfos()
      this.$router.currentRoute.matched[2].meta.title = '编辑SKU'
    } else {
      this.$router.currentRoute.matched[2].meta.title = '添加SKU'
    }

    if(this.attributeList.length == 0) {
      this.attributeList.push({
        value: ''
      })
    }
  },

  watch: {
    'attributeList': {
      handler:function() {
        if(this.attributeList.length !== 0) {
          this.attribute = this.attributeList.map(item => {
            if(item.value !== '') {
              return item.value
            }
          }).join(',')
        }
      },
      deep: true
    }
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'goodsSKUlist' && this.$route.query.name == 'editor') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },

  methods: {
    async getSkuAttributeLists() {
      const res = await getSkuAttributeList({
        api: 'saas.dic.getSkuAttributeList',
        pageSize: 999
      })
      this.Dictionary = res.data.data.list
      console.log(res);
    },

    async getSkuInfos() {
      const res = await getSkuInfo({
        api: 'saas.dic.getSkuInfo',
        sid: this.$route.query.id
      })
      console.log(res);
      this.ruleForm.region = this.$route.query.sname
      var attrList = res.data.data.list.map(item => {
        return {
          value: item.name
        }
      })
      console.log(attrList);
      this.attributeList = attrList
      
    },

    getId(val) {
      this.sid = val
      console.log(this.sid);
    },

    addOne() {
      this.attributeList.push(
        {
          value: ''
        }
      )
    },

    minus(index) {
      if(this.attributeList.length !== 0) {
        this.attributeList.splice(index, 1)　
      }
    },

    // 弹框方法
    dialogShow() {
      this.ruleForm2.dataName= '',
      this.ruleForm2.status= '1',
      this.dialogVisible = true
    },
    
    handleClose(done) {
      this.dialogVisible = false
    },

    radio() {

    },

    // 添加SKU
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            addSku({
              api: 'saas.dic.addSku',
              sid: this.$route.query.id ? this.$route.query.id : this.sid,
              attributeList: this.attribute ? this.attribute : null,
              type: this.$route.query.id ? 1 : 0
            }).then(res => {
              console.log(res);
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

    // 添加商品名称
    determine(formName2) {
      this.$refs[formName2].validate(async (valid) => {
        if (valid) {
          try {
            addSkuName({
              api: 'saas.dic.addSkuName',
              skuName: this.ruleForm2.dataName,
              isOpen: parseInt(this.ruleForm2.status),
            }).then(res => {
              if(res.data.code == '200') {
                console.log(res);
                this.getSkuAttributeLists()
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
  padding: 40px 0 0 460px;
  /deep/.el-form {
    .el-form-item__label {
      font-weight: normal;
    }
    .Select {
      .select-input {
        width: 474px;
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

    .attribute-values {
      .el-form-item__content {
        display: flex;
        flex-direction: column;
        .add-info {
          display: flex;
          &:not(:last-child) {
            margin-bottom: 20px;
          }
          .el-input {
            width: 580px;
            height: 40px;
            input {
              width: 580px;
              height: 40px;
            }
          }

          .add-reduction {
            i {
              font-size: 20px;
              margin-left: 10px;
            }
            .el-icon-circle-plus-outline {
              color: #2890FF;
            }
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
          top: 0;
        }
        .el-dialog__title {
          font-weight: normal;
        }
        
      }

      .el-dialog__body {
        padding: 41px 60px 0px 60px !important;
        border-bottom: 1px solid #E9ECEF;
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