<template>
  <div class="container">
    <div class="header">
      <span>编辑会员信息</span>
    </div>
    
    <div class="info-block" v-if="userInfo">
      <el-form :model="ruleForm" :rules="rule" ref="ruleForm" label-position="right" label-width="100px" class="form-search">
        <el-form-item class="members-head" label="会员头像：">
          <img :src="ruleForm.headimgurl" alt="">
        </el-form-item>
        <el-form-item label="会员ID：">
          <span>{{ userInfo.user_id }}</span>
        </el-form-item>
        <el-form-item label="会员名称：" prop="user_name">
          <el-input v-model="ruleForm.user_name" placeholder="请输入会员名称"></el-input>
        </el-form-item>
        <el-form-item label="会员账号：">
          <span>{{ userInfo.zhanghao }}</span>
        </el-form-item>
        <el-form-item label="会员等级：">
          <el-select class="select-input" v-model="ruleForm.grade" placeholder="请选择会员等级">
            <el-option v-for="(item,index) in membersGrade" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专属折扣：">
          <span>{{ userInfo.gradeDiscount }}</span>
        </el-form-item>
        <el-form-item label="到期时间：">
          <span>{{ userInfo.grade_end }}</span>
        </el-form-item>
        <el-form-item label="手机号码：" prop="mobile">
          <el-input v-model="ruleForm.mobile" placeholder="请输入手机号码"></el-input>
        </el-form-item>
        <el-form-item label="登录密码：" prop="loginPwd">
          <el-input v-model="ruleForm.loginPwd" show-password placeholder="请输入登录密码"></el-input>
        </el-form-item>
        <el-form-item label="支付密码：">
          <el-input v-model="ruleForm.paypwd" show-password maxlength="6" onkeyup="this.value = this.value.replace(/[^\d.]/g,'');" placeholder="请输入支付密码"></el-input>
        </el-form-item>
        <el-form-item label="账号余额：">
          <el-input v-model="ruleForm.money" placeholder="请输入账号余额"></el-input>
        </el-form-item>
        <el-form-item label="积分余额：">
          <el-input v-model="ruleForm.score" placeholder="请输入积分余额"></el-input>
        </el-form-item>
        <el-form-item label="会员生日：">
          <!-- <el-input v-model="ruleForm.birthday" placeholder="请输入会员生日"></el-input> -->
          <el-date-picker
            v-model="ruleForm.birthday"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="账号来源：">
          <span>{{ getSource(userInfo.source) }}</span>
        </el-form-item>
        <el-form-item label="有效订单数：">
          <span>{{ userInfo.z_num }}</span>
        </el-form-item>
        <el-form-item label="交易金额：">
          <span>￥{{ userInfo.z_price }}</span>
        </el-form-item>
        <el-form-item label="访问次数：">
          <span>{{  }}</span>
        </el-form-item>
        <el-form-item label="最后登录：">
          <span>{{ userInfo.last_time | dateFormat }}</span>
        </el-form-item>
        <el-form-item label="注册时间：">
          <span>{{ userInfo.Register_data }}</span>
        </el-form-item>

        <div class="footer-button">
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
        </div>
		  </el-form>
    </div>
  </div>
</template>

<script>
import editorMembers from '@/webManage/js/members/membersList/editorMembers'
export default editorMembers
</script>

<style scoped lang="less">
@import '../../../../webManage/css/members/membersList/editorMembers.less';
</style>