<template>
  <div class="container">
    <div class="header">
      <span>{{ $route.query.name === 'editor' ? '编辑模板' : '查看模板' }}</span>
    </div>

    <el-form :model="ruleForm" :rules="rule" ref="ruleForm" class="form-search">
      <div class="notice">
        <el-form-item class="title" label="运费名称" prop="templateName">
          <el-input v-model="ruleForm.templateName" placeholder="请输入运费名称" :disabled="disabled"></el-input>
        </el-form-item>
        <el-form-item class="rule-block" required label="运费规则" >
          <div class="add-rule" v-if="$route.query.name === 'editor'">
            <el-button class="bgColor" @click="dialogShow" type="primary" >添加规则</el-button>
          </div>
        </el-form-item>
      </div>
      
      <div class="footer-button" v-if="$route.query.name === 'editor'">
        <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
        <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
      </div>
		</el-form>
    <div class="dictionary-list" v-if="tableData.length !== 0">
      <el-table :data="tableData" ref="table" class="el-table">
        <el-table-column prop="one" label="运费" width="40">
        </el-table-column>
        <el-table-column prop="name" label="省份" width="300">
        </el-table-column>
        <el-table-column label="操作" width="142" v-if="$route.query.name === 'editor'">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-delete" @click="Delete(scope.$index)">删除</el-button>
              </div>
            </div>
		      </template>
        </el-table-column>
		  </el-table>
      <div class="footer-button" v-if="$route.query.name  == 'view'" style="margin-top:20px;">
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
      </div>
    </div>
    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        title="添加数据名称"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
          <el-form-item label="运费" prop="freight">
            <el-input v-model="ruleForm2.freight" @keyup.native="ruleForm2.freight = oninput2(ruleForm2.freight)"></el-input>
          </el-form-item>
          <el-form-item class="check-provinces" label="选择省份" prop="status">
            <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
            <el-checkbox-group v-model="checkedCities" @change="handleCheckedCitiesChange">
              <el-checkbox v-for="city in cities" :label="city" :key="city">{{city}}</el-checkbox>
            </el-checkbox-group>
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
import { addFreight, getRegion } from '@/api/goods/freightManagement'
export default {
  name: 'editortemplate',

  data() {
    return {
      ruleForm: {
        templateName: '',
      },

      rule: {
        templateName: [
          { required: true, message: '请输入模板名称', trigger: 'blur' },
        ],
      },

      tableData: [],

      checkAll: false,
      checkedCities: [],
      cities: [],
      isIndeterminate: true,
      id: null,
      // 弹框数据
      dialogVisible: false,
      ruleForm2: {
        freight: null,
      },
      rules2: {
        freight: [
          { required: true, message: '请填写模板名称', trigger: 'change' }
        ],
      },

      disabled: false

    }
  },

  created() {
    this.getRegions()
    this.ruleForm.templateName = this.$route.params.name
    this.id = this.$route.params.id
    for(let key  in this.$route.params.freight){
      this.tableData.push(this.$route.params.freight[key])
    }

    if(this.$route.query.name === 'view') {
      this.disabled = true
      this.$router.currentRoute.matched[2].meta.title = '查看'
    } else {
      this.$router.currentRoute.matched[2].meta.title = '编辑模板'
    }
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'freightlist') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },

  watch: {
    checkAll() {
      if(this.checkAll === true) {
        this.checkedCities = this.cities
      }
    }
  },

  // beforeRouteLeave (to, from, next) {        
  //   if (to.name == 'freightlist') {
  //     to.meta.keepAlive = true;    
  //   }        
  //   next();
  // },

  methods: {
    async getRegions() {
      const res = await getRegion({
        api: 'admin.goods.getRegion',
        level: 2
      })
      console.log(res);
      this.cities = res.data.data.map(item => {
        return item.g_CName
      })
    },

    handleCheckAllChange(val) {
      this.checkedCities = val ? cityOptions : [];
      this.isIndeterminate = false;
    },
    handleCheckedCitiesChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.cities.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.cities.length;
    },

    Delete(value) {
      this.tableData.splice(value,1)
    },

    // 弹框方法
    dialogShow() {
      this.checkedCities= [],
      this.ruleForm2.freight= null,
      this.dialogVisible = true
    },
    
    handleClose(done) {
      this.dialogVisible = false
    },

    determine(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            var obj = {
              one: this.ruleForm2.freight,
              name: this.checkedCities.join(),
              freight: this.ruleForm2.freight
            }
            this.tableData.push(obj)
            console.log(this.tableData);
            this.dialogVisible = false
          } catch (error) {
            // this.$message({
            //   message: '数据名称不能为空',
            //   type: 'error',
            //   offset: 100
            // })
          }
        } else {
          // this.$message({
          //   message: '数据名称不能为空',
          //   type: 'error',
          //   offset: 100
          // })
          // return false;
        }
      });
    },

    // 编辑运费
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            addFreight({
              api: 'admin.goods.addFreight',
              name: this.ruleForm.templateName,
              hiddenFreight: encodeURIComponent(JSON.stringify(this.tableData)),
              id: this.id
            }).then(res => {
              console.log(res);
              this.$message({
                message: '编辑成功',
                type: 'success',
                offset: 100
              })
              console.log(res);
            })
            
            this.$router.go(-1)
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

    oninput2(num, limit) {
      var str = num
      str = str.replace(/[^\.\d]/g,'');
      str = str.replace('.','');

      return str
    },

  }
}
</script>

<style scoped lang="less">
.container {
  width: 100%;
  height: 737px;
  background-color: #fff;
  border-radius: 4px;
  .header {
    width: 100%;
    height: 60px;
    line-height: 60px;
    padding-left: 20px;
    border-bottom: 1px solid #E9ECEF;
    span {
      font-size: 16px;
      font-weight: 400;
      color: #414658;
    }
  }

  /deep/.el-form {
    width: 100%;
    .notice {
      padding: 40px 0 0 60px;
      display: flex;
      flex-direction: column;
      margin-left:50%;
      transform: translateX(-50%);
      .title {
        .el-form-item__label {
          font-weight: normal;
        }
        .el-form-item__label {
          color: #414658;
        }
        .el-form-item__content {
          display: flex;
          input {
            width: 420px;
            height: 40px;
          }
        }
      }

    }

    .footer-button {
      position: fixed;
      right: 0;
      bottom: 40px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      padding: 15px 20px;
      border-top: 1px solid #E9ECEF;
      background: #FFFFFF;
      width: 300%;
      z-index: 10;
      button {
        width: 70px;
        height: 40px;
      }
    }
  }

  .dictionary-list {
        width: 800px;
        border-radius: 4px;
        margin-left: 550px;
        margin-top:-10px;
        /deep/.el-table {
          width: 800px !important;
          .el-table__header-wrapper {
            width: 800px;
            background-color: #f4f7f9 !important;
            .el-table__header {
              width: 800px !important;
              background-color: #f4f7f9 !important;
            }
            thead {
              tr {
                background-color: #f4f7f9 !important;
                th{
                  height: 61px;
                  text-align: center;
                  font-size: 14px;
                  font-weight: bold;
                  color: #414658;
                  background-color: #f4f7f9 !important;
                }
              }
            }
          }
          .el-table__body-wrapper {
            width: 800px;
            background-color: #f4f7f9;
            .el-table__body {
              width: 800px !important;
            }
            tbody {
              tr {
                td{
                  height: 92px;
                  text-align: center;
                  font-size: 14px;
                  color: #414658;
                  font-weight: 400;
                  padding: 0;
                }
              }
            }
          }

          .OP-button{
            button{
              padding: 5px;
              height: 22px;
              background: #FFFFFF;
              border: 1px solid #D5DBE8;
              border-radius: 2px;
              font-size: 12px;
              font-weight: 400;
              color: #888F9E;
            }
            button:hover{
              border:1px solid rgb(64, 158, 255);
              color: rgb(64, 158, 255);
            }
            button:hover i{
              color: rgb(64, 158, 255);
            }
            .OP-button-top{
              // margin-bottom: 8px;
              button {
                &:not(:first-child) {
                  margin-left: 8px !important;
                }
              }
            }
          } 
        }
      }
   .dialog-block {
    // 弹框样式
    /deep/.el-dialog {
      width: 680px;
      height: 590px;
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
        padding: 41px 0px 0px 0px !important;
        // border-bottom: 1px solid #E9ECEF;
        .check-provinces {
          .el-checkbox {
            width: 120px;
            height: 32px;
            margin-right: 14px;
          }
          .el-form-item__content {
            padding-top: 5px;
          }
        }
        .el-form-item__content {
          line-height: 32px;
        }
        .el-form-item {
          margin-bottom: 12px;
        }
        .el-form-item__label {
          font-weight: normal;
        }
        .el-input__inner {
          width: 304px;
          height: 40px;
        }
        .form-footer {
          width: 100%;
          height: 72px;
          position: absolute;
          bottom: 0;
          right: 0;
          border-top: 1px solid #E9ECEF;
          .el-form-item {
            padding: 0 !important;
            height: 100%;
            display: flex;
            justify-content: flex-end;
            margin-right: 17px;
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

  /deep/.el-form-item__label {
    font-weight: normal;
  }
}
</style>