<template>
  <div class="container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item class="Select code-input" label="数据名称" prop="name">
          <el-select :disabled="true" class="select-input" v-model="ruleForm.name">
            <el-option label="name" value="name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="code-input" label="数据编码" prop="numberCode">
          <el-input :disabled="true" v-model="ruleForm.numberCode"></el-input>
        </el-form-item>
        <el-form-item class="code-input" label="code" prop="code">
          <el-input :disabled="true" v-model="ruleForm.code"></el-input>
        </el-form-item>
        <el-form-item label="值" prop="value">
          <el-input v-model="ruleForm.value"></el-input>
        </el-form-item>
        <el-form-item label="是否生效" prop="resource">
          <el-radio-group v-model="ruleForm.resource">
            <el-radio v-model="ruleForm.resource" label="1">是</el-radio>
            <el-radio v-model="ruleForm.resource" label="0">否</el-radio>
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
</template>

<script>
import { addDictionaryTable } from '@/api/Platform/numerical'

export default {
  name: 'compile',

  data() {
    return {
      ruleForm: {
        name: '',
        numberCode: '',
        code: '',
        value: '',
        resource: '1',
      },
      id: null,
      radio: '1',
      rules: {
        // name: [{ required: true, trigger: 'blur' }],
        // numberCode: [{ required: true, trigger: 'blur' }],
        // code: [{ required: true, trigger: 'blur' }],
        value: [{ required: true, trigger: 'blur' }],
        // resource: [{ required: true, trigger: 'change' }],
      }
    }
  },

  created() {
    console.log(this.$route.params);
    this.ruleForm.name= this.$route.params.name,
    this.ruleForm.numberCode= this.$route.params.code,
    this.ruleForm.code= this.$route.params.value,
    this.ruleForm.value= this.$route.params.text,
    this.ruleForm.resource= `${this.$route.params.status}`
    this.id = this.$route.params.sid
  },

  beforeRouteLeave (to, from, next) {        
    if (to.name == 'dictionarylist') {
      to.params.dictionaryNum = this.$route.query.dictionaryNum
      to.params.pageSize = this.$route.query.pageSize
    }   
    next();
  },


  methods: {
    // 添加/修改字典表明细
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            addDictionaryTable({
              api: 'saas.dic.addDictionaryDetailInfo',
              id: this.$route.params.id,
              dataCode: this.ruleForm.numberCode,
              valueCode: this.ruleForm.value,
              valueName: this.ruleForm.code,
              isOpen: parseInt(this.ruleForm.resource),
              sid: this.id,
            }).then(res => {
              if(res.data.code == '200') {
                this.$message({
                  message: '修改成功',
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

    back() {},
  }
}
</script>

<style scoped lang="less">
.container {
  width: 100%;
  height: 737px;
  background-color: #fff;
  padding-top: 40px;
  /deep/.el-form {
    width: 692px;
    margin: 0 auto;
    .Select {
      .select-input {
        width: 592px;
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
    
  }
  
}
</style>