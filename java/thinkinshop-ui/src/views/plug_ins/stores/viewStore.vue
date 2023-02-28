<template>
  <div class="container">
    <div class="header">
      <span>店铺详情</span>
    </div>

    <el-form label-position="right" ref="ruleForm" label-width="100px" class="form-search" v-if="storeInfo">
      <div class="notice">
        <el-form-item class="members-head" label="店铺LOGO：" prop="userName">
          <img :src="storeInfo.logo" alt="">
        </el-form-item>
        <el-form-item label="店铺名称：" prop="">
          <span>{{ storeInfo.name }}</span>
        </el-form-item>
        <el-form-item label="直播间房号：" prop="">
          <span>{{ storeInfo.roomid }}</span>
        </el-form-item>
        <el-form-item label="店铺信息：" prop="">
          <span>{{ storeInfo.shop_information }}</span>
        </el-form-item>
        <el-form-item label="经营范围：" prop="">
          <span>{{ storeInfo.shop_range }}</span>
        </el-form-item>
        <el-form-item label="用户名称：" prop="">
          <span>{{ storeInfo.user_name }}</span>
        </el-form-item>
        <el-form-item label="真实姓名：" prop="">
          <span>{{ storeInfo.realname }}</span>
        </el-form-item>
        <el-form-item label="身份证号码：" prop="">
          <span>{{ storeInfo.ID_number }}</span>
        </el-form-item>
        <el-form-item label="联系电话：" prop="">
          <span>{{ storeInfo.tel }}</span>
        </el-form-item>
        <el-form-item label="联系地址：" prop="">
          <span>{{ storeInfo.address }}</span>
        </el-form-item>
        <el-form-item label="所属性质：" prop="">
          <span>{{ storeInfo.shop_nature == 0 ? '个人' : '企业' }}</span>
        </el-form-item>
        <el-form-item class="business-license" label="营业执照：" prop="">
          <img :src="storeInfo.business_license" alt="">
        </el-form-item>
        <el-form-item label="审核状态：" prop="">
          <span>{{ storeInfo.review_status == 0 ? '待审核' : storeInfo.review_status == 1 ? '审核通过' : '审核不通过' }}</span>
        </el-form-item>
        <el-form-item label="营业状态：" prop="">
          <span>{{ storeInfo.is_open == 0 ? '未营业' : storeInfo.is_open == 1 ? '营业中' : '已打烊' }}</span>
        </el-form-item>
        <el-form-item label="拒绝理由：" prop="" v-if="$route.query.status && $route.query.status == '审核不通过'">
          <span>{{ storeInfo.review_result }}</span>
        </el-form-item>
        <el-form-item class="footer-button">
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
        </el-form-item>
      </div>
      
      <!-- <div class="footer-button">
        <el-form-item>
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
        </el-form-item>
      </div> -->
	  </el-form>
  </div>
</template>

<script>
import viewStore from '@/webManage/js/plug_ins/stores/viewStore'
export default viewStore
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/viewStore.less';
</style>