<template>
  <div class="container">
    <div class="header">
      <span>会员详情</span>
    </div>
    
    <div class="info-block" v-if="userInfo">
      <el-form label-position="right" label-width="100px" class="form-search">
        <el-form-item class="members-head" label="会员头像：">
          <img :src="userInfo.headimgurl" alt="">
        </el-form-item>
        <el-form-item label="会员ID：">
          <span>{{ userInfo.user_id }}</span>
        </el-form-item>
        <el-form-item label="会员名称：">
          <span>{{ userInfo.user_name }}</span>
        </el-form-item>
        <el-form-item label="会员账号：">
          <span>{{ userInfo.zhanghao }}</span>
        </el-form-item>
        <el-form-item label="会员等级：">
          <span>{{ userInfo.grade }}</span>
        </el-form-item>
        <el-form-item label="专属折扣：">
          <span>{{ userInfo.gradeDiscount }}</span>
        </el-form-item>
        <el-form-item label="到期时间：">
          <span>{{ userInfo.grade_end }}</span>
        </el-form-item>
        <el-form-item label="手机号码：">
          <span>{{ userInfo.mobile }}</span>
        </el-form-item>
        <el-form-item label="登录密码：">
          <span>{{ '******' }}</span>
        </el-form-item>
        <el-form-item label="支付密码：">
          <span>{{ '******' }}</span>
        </el-form-item>
        <el-form-item label="账号余额：">
          <span>￥{{ userInfo.money }}</span>
        </el-form-item>
        <el-form-item label="积分余额：">
          <span>￥{{ userInfo.score }}</span>
        </el-form-item>
        <el-form-item label="会员生日：">
          <span>{{ userInfo.birthday }}</span>
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
        <el-form-item label="推送CID：">
          <span>{{ userInfo.clientId }}</span>
        </el-form-item>
        <div class="footer-button">
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
        </div>
		  </el-form>
    </div>
  </div>
</template>

<script>
import viewMembers from '@/webManage/js/members/membersList/viewMembers'
export default viewMembers
</script>

<style scoped lang="less">
@import '../../../../webManage/css/members/membersList/viewMembers.less';
</style>