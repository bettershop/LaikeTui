<template>
  <div class="container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
        <el-form-item label="登录页logo" prop="imgUrl" required>
            <l-upload
              :limit="1"
              v-model="ruleForm.imgUrl"
              text="建议尺寸：152*50px"
              @remove="onRemove"
              @getPic="onGetPic"
            >
            </l-upload>
        </el-form-item>
        <el-form-item label="版权信息" class="copyright" prop="copyright" required>
            <el-input v-model="ruleForm.copyright" placeholder="请输入版权信息"></el-input>
        </el-form-item>
        <el-form-item label="备案信息" class="record" prop="record" required>
            <el-input v-model="ruleForm.record" placeholder="请输入备案信息"></el-input>
        </el-form-item>
        <el-form-item class="link" label="登录页友情链接" prop="link">
            <el-input class="link-name" v-model="ruleForm.link.name" placeholder="请输入链接名称"></el-input>
            <el-input class="link-local" v-model="ruleForm.link.local" placeholder="请输入链接地址"></el-input>
            <div class="add-reduction">
                <i class="el-icon-remove-outline" @click="minus" v-show="attributeList.length !== 0"></i>
                <i class="el-icon-circle-plus-outline" @click="addOne" v-show="attributeList.length === 0"></i>
                <span>用于平台登录页底部链接</span>
            </div>
        </el-form-item>
        <el-form-item v-if="attributeList.length !== 0" class="add-link" label="" prop="code">
            <div class="add-info" v-for="(item,index) in attributeList" :key="index">
                <el-input class="link-names" v-model="attributeList[index].value.name" placeholder="请输入链接名称"></el-input>
                <el-input class="link-locals" v-model="attributeList[index].value.local" placeholder="请输入链接地址"></el-input>
                <div class="add-reduction">
                    <i class="el-icon-remove-outline" @click="minus"></i>
                    <i class="el-icon-circle-plus-outline" @click="addOnes" v-show="index === attributeList.length - 1"></i>
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
  </div>
</template>

<script>
import { getSystemIndex, addSystemConfig } from '@/api/Platform/system'
export default {
    name: 'system',

    data() {
        return {
            ruleForm: {
              imgUrl: 'https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1950846641,3729028697&fm=26&gp=0.jpg',
              copyright: '湖南壹拾捌号网络技术有限公司',
              record: '湘ICP备17020355号-2',
              link: {
                name: '',
                local: ''
              }
            },
            rules: {
                
            },
            attributeList: [],

        }
    },

    created() {
      this.getSystemIndexs()
    },

    methods: {
      async getSystemIndexs() {
        const res = getSystemIndex({
          api: 'admin.system.getSystemIndex'
        })
        console.log(res);
      },

      onRemove() {},

      onGetPic() {},

      addOne() {
        this.attributeList.push(
          {
            value: {
              name: '',
              local: ''
            }
          }
        )
      },

      minus() {
        if(this.attributeList.length !== 0) {
          this.attributeList.pop()
        }
      },

      addOnes() {
        this.attributeList.push(
          {
            value: {
              name: '',
              local: ''
            }
          }
        )
      },

      submitForm(formName) {
        this.$refs[formName].validate(async (valid) => {
          console.log(this.ruleForm);
          if (valid) {
          try {
            addSystemConfig({
              api: 'admin.system.addSystemConfig'
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
    },

}
</script>

<style scoped lang="less">
.container {
  width: 100%;
  height: 737px;
  background-color: #fff;
  padding: 60px 0 0 0;
  display: flex;
  justify-content: center;
  overflow: hidden;
  overflow-y: auto;
  /deep/.el-form {
    .el-form-item__label {
      font-weight: normal;
    }
    .copyright, .record{
      .el-form-item__content {
        .el-input {
          width: 580px;
          height: 40px;
          input {
            width: 580px;
            height: 40px;
          }
        }
      }
    }

    .link {
      .el-form-item__content {
        display: flex;
        .link-name {
          width: 180px;
          height: 40px;
          input {
            width: 180px;
            height: 40px;
          }
        }
        .link-local {
          width: 390px;
          height: 40px;
          margin-left: 10px;
          input {
            width: 390px;
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
          span {
              margin-left: 14px;
              position: relative;
              bottom: 3px;
              color: #97A0B4;
          }
        }
      }
    }

    .add-link {
      .el-form-item__content {
        display: flex;
        flex-direction: column;
        
        .add-info {
          display: flex;
          &:not(:last-child) {
            margin-bottom: 20px;
          }
          .link-names {
            width: 180px;
            height: 40px;
            input {
                width: 180px;
                height: 40px;
            }
          }
          .link-locals {
            width: 390px;
            height: 40px;
            margin-left: 10px;
            input {
                width: 390px;
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

}
</style>