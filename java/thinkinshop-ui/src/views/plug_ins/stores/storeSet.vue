<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="店铺" @click.native.prevent="$router.push('/plug_ins/stores/store')"></el-radio-button>
        <el-radio-button label="审核列表" @click.native.prevent="$router.push('/plug_ins/stores/auditList')"></el-radio-button>
        <el-radio-button label="保证金记录" @click.native.prevent="$router.push('/plug_ins/stores/bondMoney')"></el-radio-button>
        <el-radio-button label="商品审核" @click.native.prevent="$router.push('/plug_ins/stores/goodsAudit')"></el-radio-button>
        <el-radio-button label="提现审核" @click.native.prevent="$router.push('/plug_ins/stores/withdrawalAudit')"></el-radio-button>
        <el-radio-button label="提现记录" @click.native.prevent="$router.push('/plug_ins/stores/withdrawalRecord')"></el-radio-button>
        <el-radio-button label="店铺设置" @click.native.prevent="$router.push('/plug_ins/stores/storeSet')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="store-set">
      <el-form :model="ruleForm" label-position="right" ref="ruleForm" label-width="135px" class="form-search">
        <div class="notice">
          <el-form-item label="是否开启插件：">
            <el-switch v-model="ruleForm.switchs" @change="switchs(scope.row)" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </el-form-item>
          <el-form-item label="店铺默认LOGO：" required>
            <l-upload
              :limit="1"
              v-model="ruleForm.defaultLogo"
              text="（建议上传690*180px尺寸的图片）"
            >
            </l-upload>
          </el-form-item>
          <el-form-item class="del-set" label="删除设置：" required>
            <div class="set">
              <span>超过</span>
              <el-input v-model="ruleForm.delete_day"></el-input>
              <span>个月未登录的店铺，系统自动删除</span>
            </div>
          </el-form-item>
          <el-form-item label="商品上传方式：" required>
            <el-checkbox-group v-model="ruleForm.goodsUpload">
              <el-checkbox v-for="label in goodsUploadList" :label="label.id" :key="label.id">{{label.name}}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="最低提现金额：" required>
            <el-input v-model="ruleForm.min_pric" placeholder="请输入最低提现金额"></el-input>
          </el-form-item>
          <el-form-item label="最大提现金额：" required>
            <el-input v-model="ruleForm.max_price" placeholder="请输入最大提现金额"></el-input>
          </el-form-item>
          <el-form-item class="poundages" label="手续费：" required>
            <el-input type="number" min="0" v-model="ruleForm.poundage" placeholder=""></el-input>
            <span>手续费为大于0小于1的小数,如0.05</span>
          </el-form-item>
          <el-form-item label="保证金开关：" required>
            <template>
              <el-switch v-model="ruleForm.isPromiseSwitch" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
              </el-switch>
              <span style="color: #97A0B4;">（建议不要反复开启关闭）</span>
              <div class="security-deposit" v-show="ruleForm.isPromiseSwitch == 1">
                <div required>
                  <label style="color:red;margin-top:2px">*</label>
                  <span>保证金：</span>
                  <el-input v-model="ruleForm.bondmoney" placeholder="请输入保证金" @keyup.native="ruleForm.bondmoney = oninput(ruleForm.bondmoney,2)"></el-input><span>元</span>
                </div>
                <div class="richText-info money">
                  <span>保证金说明：</span>
                  <vue-editor 
                    v-model="remark"
                    useCustomImageHandler
                    @image-added="handleImageAdded"
                  ></vue-editor>
                </div>
              </div>
            </template>
          </el-form-item>
          <el-form-item class="instructions" label="提现说明：">
            <div class="richText-info">
              <vue-editor 
                v-model="content"
                useCustomImageHandler
                @image-added="handleImageAdded"
              ></vue-editor>
            </div>
          </el-form-item>
        </div>
        
        <div class="footer-button">
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import storeSet from '@/webManage/js/plug_ins/stores/storeSet'
export default storeSet
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/storeSet.less';
.money{
  margin-top:20px;
  font-size: 14px;
  font-family: MicrosoftYaHei;
  color: #414658;
  opacity: 1;
    span{
      margin:0 5px;
    }
    .el-input{
      .el-input__inner{
        width:320px !important;
        height:36px;
      }
    }
}
</style>