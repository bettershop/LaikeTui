<template>
  <div class="container">
    <div class="picture-info">
      <div class="ali-cloud">
        <el-form :model="ruleForm3" :rules="rules3" ref="ruleForm3"  class="aliCloud-ruleForm" label-width="187px">
          <el-form-item class="head-title" label="阿里云OSS配置:">
          </el-form-item>
          <el-form-item label="Bucket(存储空间名称)" prop="ossbucket">
            <el-input v-model="ruleForm3.ossbucket" placeholder="请输入Bucket(存储空间名称)"></el-input>
            <span>请设置存储空间为公共读</span>
          </el-form-item>
          <el-form-item label="Endpoint" prop="ossendpoint">
            <el-input v-model="ruleForm3.ossendpoint" placeholder="请输入Endpoint"></el-input>
            <span>例子：oss-cn-hangzhou.aliyuncs.com，结尾不需要/</span>
          </el-form-item>
          <el-form-item label="MyEndpoint(自定义域名)" prop="myEndpoint">
            <el-input v-model="ruleForm3.myEndpoint" placeholder="请输入MyEndpoint(自定义域名)"></el-input>
            <span>例子：aaa.bbb.com，结尾不需要/</span>
          </el-form-item>
          <el-form-item class="isOpenDiyDomain" label="是否开启自定义域名">
            <el-radio-group v-model="ruleForm3.isOpenDiyDomain" style="margin-left:-20Px">
              <el-radio v-for="item in isOpenDiyDomainList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="Access Key ID" prop="ossaccesskey">
            <el-input v-model="ruleForm3.ossaccesskey" placeholder="请输入Access Key ID"></el-input>
          </el-form-item>
          <el-form-item label="Access Key Secret" prop="ossaccesssecret">
            <el-input v-model="ruleForm3.ossaccesssecret" placeholder="请输入Access Key Secret"></el-input>
          </el-form-item>
          <el-form-item label="图片样式接口（选填）">
            <el-input v-model="ruleForm3.ossimgstyleapi" placeholder="请输入图片样式接口（选填）"></el-input>
          </el-form-item>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" type="primary" @click="submitForm3('ruleForm3')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
              <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
      
    </div>
  </div>
</template>

<script>
import { addImageConfigInfo, getImageConfigInfo } from '@/api/Platform/graphics'
export default {
  name: 'graphics',

  data() {
    return {
      // 上传方式数据
      ruleForm1: {
        type: '1'
      },
      radio: '本地',
      rules1: {
        type: [
          { required: true, message: '是', trigger: 'change' }
        ],
      },

      //阿里云数据
      ruleForm3: {
        ossbucket: '',
        ossendpoint: '',
        myEndpoint: '',
        isOpenDiyDomain: '',
        ossaccesskey: '',
        ossaccesssecret	: '',
        ossimgstyleapi: ''
      },
      rules3: {
        ossbucket: [
          { required: true, message: '请填写储存空间名称', trigger: 'blur' }
        ],
        ossendpoint: [
          { required: true, message: '请填写Endpoint', trigger: 'blur' }
        ],
        ossaccesskey: [
          { required: true, message: '请填写Access Key ID', trigger: 'blur' }
        ],
        ossaccesssecret: [
          { required: true, message: '请填写Access Key Secret', trigger: 'blur' }
        ],
      },

      isOpenDiyDomainList: [
        {
          value: 0,
          name: '否'
        },
        {
          value: 1,
          name: '是'
        },
      ],
    }
  },

  watch: {
    
  },

  created() {
    this.getImageConfigInfos2()
  },

  methods: {
    async getImageConfigInfos2() {
      const res = await getImageConfigInfo({
        api: 'saas.image.getImageConfigInfo',
        type: 2
      })
      console.log(res);
      this.ruleForm3.ossbucket = res.data.data.data[0].attrvalue
      this.ruleForm3.ossendpoint = res.data.data.data[1].attrvalue
      this.ruleForm3.myEndpoint = res.data.data.data[5].attrvalue
      this.ruleForm3.isOpenDiyDomain = parseInt(res.data.data.data[2].attrvalue)
      this.ruleForm3.ossaccesskey = res.data.data.data[3].attrvalue
      this.ruleForm3.ossaccesssecret	 = res.data.data.data[4].attrvalue
      this.ruleForm3.ossimgstyleapi = res.data.data.data[6].attrvalue
    },

    submitForm3(formName) {
      addImageConfigInfo({
        api: 'saas.image.addImageConfigInfo',
        type: 2,
        ossbucket: this.ruleForm3.ossbucket,
        ossendpoint: this.ruleForm3.ossendpoint,
        myEndpoint: this.ruleForm3.myEndpoint,
        isOpenDiyDomain: this.ruleForm3.isOpenDiyDomain,
        ossaccesskey: this.ruleForm3.ossaccesskey,
        ossaccesssecret: this.ruleForm3.ossaccesssecret,
        ossimgstyleapi: this.ruleForm3.ossimgstyleapi
      }).then(res => {
        console.log(res);
        if(res.data.code == '200') {
          this.$message({
            message: '成功',
            type: 'success',
          })
        }
      })
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
  /deep/.header-radio {
    padding-left: 446px;
    
    .el-radio {
      margin-right: 20px;
    }
  }

  /deep/.picture-info {
    .local {
      padding-left: 431px;
      .picture-ruleForm {
        .el-form-item {
          display: flex;
          .el-form-item__content {
            width: 580px;
            height: 40px;
            .el-radio-group{
              margin-left: -20Px;
            }
          }
        }

        .form-footer {
          padding-left: 106px;
        }
      }
      
    }

    .ali-cloud {
      padding-left: 348px;
      .aliCloud-ruleForm {
        .el-form-item {
          display: flex;
          &:not(:last-child) {
            .el-form-item__content {
              margin-left: 0px !important;
            }
          }
          .el-form-item__content {
            .el-radio {
              margin-right: 0px;
            }
            .el-input {
              width: 580px;
              height: 40px;
            }
            span {
              margin-left: 14px;
              color: #97A0B4;
            }
          }
        }

        .head-title {
          .el-form-item__label {
            font-size: 16px;
            color: #414658 !important;
          }
        }

        .form-footer {
          .bgColor {
            width: 70px;
            height: 40px;
            text-align: center;
            background-color: #2890ff;
            color: #fff;
            span {
              color: #fff;
              margin-left: 0;
            }
          }
          .bgColor:hover {
            opacity: 0.8;
          }
          .bdColor {
            width: 70px;
            height: 40px;
            text-align: center;
            color: #6a7076;
            border: 1px solid #d5dbe8;
            margin-left: 14px;
            span {
              margin-left: 0;
            }
          }
          .bdColor:hover {
            color: #2890ff;
            border: 1px solid #2890ff;
          }
        }
      }
    }
    
  }
  // /deep/.el-form-item__label {
  //   font-weight: normal;
  // }
}
</style>