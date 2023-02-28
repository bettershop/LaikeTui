<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="基础配置" @click.native.prevent="$router.push('/mall/basicConfiguration')"></el-radio-button>
        <el-radio-button label="协议配置" @click.native.prevent="$router.push('/mall/agreement/protocolConfiguration')"></el-radio-button>
        <el-radio-button label="常见问题" @click.native.prevent="$router.push('/mall/commonProblem')"></el-radio-button>
        <el-radio-button label="新手指南" @click.native.prevent="$router.push('/mall/newbieGuide')"></el-radio-button>
        <el-radio-button label="售后服务" @click.native.prevent="$router.push('/mall/afterSales')"></el-radio-button>
        <el-radio-button label="关于我们" @click.native.prevent="$router.push('/mall/aboutUs')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="basic-configuration">
      <div class="header">
        <span>新手指南</span>
      </div>

      <el-form :model="ruleForm" label-position="right" ref="ruleForm" label-width="90px" class="form-search">
        <div class="notice">
          <el-form-item label="购物流程：">
            <div class="richText-info">
              <vue-editor
                v-model="ruleForm.shopping_process"
                useCustomImageHandler
                @image-added="handleImageAdded"
              ></vue-editor>
            </div>
          </el-form-item>
          <el-form-item label="支付方式：">
            <div class="richText-info">
              <vue-editor 
                v-model="ruleForm.payment_method"
                useCustomImageHandler
                @image-added="handleImageAdded"
              ></vue-editor>
            </div>
          </el-form-item>
        </div>
        <div class="footer-button">
          <el-button plain class="footer-cancel fontColor" @click="$router.push('/mall/basicConfiguration')">取消</el-button>
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import newbieGuide from '@/webManage/js/mall/systemManagement/newbieGuide'
export default newbieGuide
</script>

<style scoped lang="less">
@import '../../../webManage/css/mall/systemManagement/newbieGuide.less';
</style>