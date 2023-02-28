<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="会员列表" @click.native.prevent="$router.push('/members/membersList')"></el-radio-button>
        <el-radio-button label="会员等级" @click.native.prevent="$router.push('/members/membersLevel')"></el-radio-button>
        <el-radio-button label="会员设置" @click.native.prevent="$router.push('/members/membersSet')"></el-radio-button>
      </el-radio-group>
    </div>

    <el-form ref="ruleForm" class="form-search" :model="ruleForm" label-width="130px">
      <div class="basic-info">
        <div class="header">
          <span>基础设置</span>
        </div>
        <div class="basic-block">
          <el-form-item label="默认头像设置：">
						<l-upload
              :limit="1"
              v-model="ruleForm.wx_headimgurl"
              text="（展示图最多上传一张，建议上传60px*60px的图片）"
            >
            </l-upload>
					</el-form-item>
          <el-form-item label="默认昵称设置：">
						<el-input v-model="ruleForm.wx_name" placeholder="请输入默认昵称设置"></el-input>
					</el-form-item>
          <el-form-item label="自动续费提醒：">
						<el-switch v-model="ruleForm.is_auto" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
            <div class="set" v-show="ruleForm.is_auto == 1">
              <span>到期前</span>
              <el-input class="interval" v-model="ruleForm.auto_time"></el-input>
              <span>天，每次登陆时，自动弹窗提示</span>
            </div>
					</el-form-item>
          <el-form-item label="开通方式：">
            <el-checkbox-group v-model="ruleForm.str_method">
              <el-checkbox  v-for="openModes in openModeList" :label="openModes.value" :key="openModes.value">{{openModes.name}}</el-checkbox>
            </el-checkbox-group>
					</el-form-item>
          <el-form-item label="享受优惠商品：" class="preferentialGoods">
            <el-checkbox class="allGoods" :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
						<el-checkbox-group v-model="ruleForm.preferentialGoods">
              <el-checkbox v-for="label in preferentialGoodsList" :label="label.id" :key="label.id" @change="handleCheckedCitiesChange">{{label.name}}</el-checkbox>
            </el-checkbox-group>
					</el-form-item>
          <el-form-item label="是否开余额支付：">
						<el-switch v-model="ruleForm.is_wallet" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
					</el-form-item>
          <el-form-item label="等级晋升设置：">
            <el-checkbox checked disabled>购买会员服务</el-checkbox>
            <el-checkbox v-model="ruleForm.upgrade">补差额升级</el-checkbox>
					</el-form-item>
          <el-form-item label="会员生日特权：">
						<el-switch v-model="ruleForm.is_birthday" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
            <div v-show="ruleForm.is_birthday == 1" class="birthday">
              <span>购买会员后，会员单天购买任意商品即可获得商品付款金额的</span>
              <el-input class="interval" v-model="ruleForm.bir_multiple"></el-input>
              <span>倍积分（此特权不能与购物赠送积分同时享受）</span>
            </div>
					</el-form-item>
          <el-form-item label="VIP等比例积分：">
						<el-switch v-model="ruleForm.is_jifen" @change="switchs2" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
            <div class="level-integration" v-show="ruleForm.is_jifen == 1">
              <span>积分发放：</span>
              <el-radio-group v-model="ruleForm.pointsOut">
                <el-radio v-for="label in pointsOutList2" :label="label.value" :key="label.value">{{label.name}}</el-radio>
              </el-radio-group>
            </div>
					</el-form-item>
          <el-form-item label="会员赠送商品：">
						<el-switch v-model="ruleForm.is_product" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
            <div class="birthday" v-show="ruleForm.is_product == 1">
              <span>赠送商品有效期</span>
              <el-input class="interval" v-model="ruleForm.valid"></el-input>
              <span>天</span>
            </div>
					</el-form-item>
          <el-form-item label="分享海报设置：">
						<l-upload
              :limit="1"
              v-model="ruleForm.poster"
              text="（展示图最多上传一张，建议上传60px*60px的图片）"
            >
            </l-upload>
					</el-form-item>
        </div>
      </div>
      <div class="configuration-info">
        <div class="header">
          <span>会员权益</span>
        </div>
        <div class="configuration-block">
          <div class="richText-info">
              <vue-editor 
                v-model="content"
                useCustomImageHandler
                @image-added="handleImageAdded"
              ></vue-editor>
          </div>
        </div>
      </div>
      
      <div class="footer-button">
        <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import membersSet from '@/webManage/js/members/membersList/membersSet'
export default membersSet
</script>

<style scoped lang="less">
@import '../../../../webManage/css/members/membersList/membersSet.less';
</style>