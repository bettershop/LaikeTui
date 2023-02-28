<template>
  <div class="container">
    <div class="header">
      <span>基本信息</span>
    </div>
    <el-form :model="ruleForm" :rules="rule" ref="ruleForm" class="form-search">
      <div class="notice">
        <el-form-item class="title" label="公告标题" prop="goodsTitle">
          <el-input v-model="ruleForm.goodsTitle" placeholder="请输入公告标题"></el-input>
        </el-form-item>
        <el-form-item class="Select" label="公告类型" prop="region">
          <el-select class="select-input" v-model="ruleForm.region" placeholder="请选择公告类型">
            <el-option v-for="(item,index) in option" :key="index" :label="item.data" :value="item.data">
            </el-option>
          </el-select>
        </el-form-item>
      </div>

      <div class="Commencement-date">
        <el-form-item label="有效时间" prop="range">
          <el-date-picker v-model="ruleForm.range"
            type="datetimerange" range-separator="至" start-placeholder="开始日期"
            end-placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss"
            :editable="false">
          </el-date-picker>
          <div class="introduce">
            <span>{{ introduce }}</span>
          </div>
        </el-form-item>
      </div>

      <div class="rich-text">
        <el-form-item required label="公告内容">
        </el-form-item>

        <div class="richText-info">
          <vue-editor 
            v-model="content"
            useCustomImageHandler
            @image-added="handleImageAdded"
          ></vue-editor>
        </div>

        <div class="footer-button">
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
        </div>
      </div>
		</el-form>
    
  </div>
</template>

<script>
import { VueEditor } from 'vue2-editor'
import OSS from 'ali-oss'
import { addSysNoticeInfo } from '@/api/Platform/bulletin.js'
import Config from "@/packages/apis/Config"
import { getStorage } from '@/utils/storage'
import axios from 'axios'
export default {
  name: 'announcementrelease',

  components: {
    VueEditor
  },

  data() {
    return {
      ruleForm: {
        goodsTitle: '',
        region: '系统维护',
        range: ''
      },
      type: 1,
      option: [
        {
          data: '系统维护'
        },
        {
          data: '版本升级'
        }
      ],

      rule: {
        goodsTitle: [
          { required: true, message: '请输入公告标题', trigger: 'blur' },
        ],
        region: [
          { required: true, message: '请选择公告类型', trigger: 'change' }
        ],
        range: [
          { required: true, message: '请选择有效时间', trigger: 'change' }
        ]
        
      },

      value1: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
      introduce: '（有效时间内用户无法登录系统）',

      actionUrl: Config.baseUrl,
      // 富文本编辑器数据
      content: '' 
    }
  },

  created() {
    if(this.$route.params.title) {
      this.ruleForm.goodsTitle = this.$route.params.title
      this.ruleForm.region = this.getverson(this.$route.params.type)
      this.ruleForm.range = []
      this.ruleForm.range[0] = this.$route.params.startdate
      this.ruleForm.range[1] = this.$route.params.enddate
      this.msg = this.$route.params.content

    } else {
      this.ruleForm.goodsTitle = ''
      this.ruleForm.region = '系统维护'
      this.ruleForm.range = ''
      this.msg = ''
    }
  },

  watch: {
    'ruleForm.region': {
      handler: function() {
        if(this.ruleForm.region === '系统维护') {
          this.introduce = '（有效时间内用户无法登录系统）'
          this.type = 1
        } else {
          this.introduce = '（有效时间内用户登录系统会有版本升级提示）'
          this.type = 2
        }
      }
    }
  },

  methods: {
    getverson(val) {
      if(val === 1) {
        return '系统维护'
      } else {
        return '版本升级'
      }
    },

    handleImageAdded(file, Editor, cursorLocation, resetUploader) {
      var formData = new FormData();
      formData.append("file", file) //第一个file 后台接收的参数名
      axios({
        url: this.actionUrl+"resources/file/uploadFiles",//上传路径
        method: "POST",
        params: {
          api: 'resources.file.uploadFiles',
          storeId: getStorage('laike_admin_uaerInfo').storeId,
          groupId: -1,
          uploadType: 2,
          accessId: this.$store.getters.token
        },
        data: formData
      }).then(result => {
        let url = result.data.data.imgUrls[0]; // 返回给你的图片路径
        Editor.insertEmbed(cursorLocation, "image", url);
        resetUploader();
      })
      .catch(err => {
          console.log(err);
      });
    },

    // 发布/修改公告
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            addSysNoticeInfo({
              api: 'saas.sysNotice.addSysNoticeInfo',
              storeType: 8,
              title: this.ruleForm.goodsTitle,
              type: this.type,
              startDate: this.ruleForm.range[0],
              endDate: this.ruleForm.range[1],
              content: this.content
            }).then(res => {
              if(res.data.code == '200') {
                this.$message({
                  message: '添加成功',
                  type: 'success',
                  offset: 100
                })
                console.log(res);
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
    .el-form-item__label {
      font-weight: normal;
    }
    .notice {
      padding: 40px 0 0 60px;
      display: flex;
      .Select {
        margin-left: 40px;
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

    .Commencement-date {
      padding-left: 60px;
      .el-form-item__label {
        padding-right: 12px;
      }
      .el-form-item__content {
        display: flex;
        .el-date-editor {
          width: 420px;
        }
      }
      .introduce {
        color: #acb3c3;
      }
      
    }

    .rich-text {
      padding-left: 60px;
      display: flex;
      width: 90%;

      .text-title {
        margin-right: 20px;
        span {
          font-size: 14px;
          font-weight: 400;
          color: #414658;
        }
      }
    }

    .richText-info {
      flex: 1;
      height: 341px;
      .quillWrapper {
        .ql-container {
          height: 300px;
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

}
</style>