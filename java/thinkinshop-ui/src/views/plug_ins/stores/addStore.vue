<template>
  <div class="container">
      <div class="add-menu">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm"  class="picture-ruleForm" label-width="95px">
        <el-form-item class="upload-img" label="店铺LOGO" prop="logo">
            <l-upload
                :limit="1"
                v-model="ruleForm.logo"
                text="（建议上传100px*100px的图片）"
            >
            </l-upload>
        </el-form-item>
        <el-form-item label="店铺名称" prop="name">
          <el-input v-model="ruleForm.name" placeholder="请输入店铺名称"></el-input>
        </el-form-item>
        <el-form-item label="店铺信息" prop="shop_information">
          <el-input v-model="ruleForm.shop_information" placeholder="请输入店铺信息"></el-input>
        </el-form-item>
         <el-form-item label="经营范围" prop="shop_range">
          <el-input v-model="ruleForm.shop_range" placeholder="请输入经营范围"></el-input>
        </el-form-item>
        <!-- <el-form-item label="所在社区" prop="community_id">
          <el-input v-model="ruleForm.community_id" placeholder="请输入所在社区"></el-input>
        </el-form-item>
        <el-form-item label="物业管理员" prop="adminid">
          <el-input v-model="ruleForm.adminid" placeholder="请输入物业管理员"></el-input>
        </el-form-item> -->
        <el-form-item label="用户姓名" prop="realname">
            <el-input placeholder="请输入用户姓名" v-model="ruleForm.realname"></el-input>
        </el-form-item>
        <el-form-item label="身份证号" prop="ID_number">
            <el-input placeholder="请输入身份证号" v-model="ruleForm.ID_number"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="tel">
            <el-input placeholder="请输入联系电话" v-model="ruleForm.tel"></el-input>
        </el-form-item>
        <el-form-item class="cascadeAddress" label="所在地区" prop="sheng">
            <div class="cascadeAddress-block">
                <el-select class="select-input" v-model="ruleForm.sheng" placeholder="省">
                    <el-option v-for="(item,index) in shengList" :key="index" :label="item.g_CName" :value="item.g_CName">
                        <div @click="getShi(item.groupID)">{{ item.g_CName }}</div>
                    </el-option>
                </el-select>
                <el-select class="select-input" v-model="ruleForm.shi" placeholder="市">
                    <el-option v-for="(item,index) in shiList" :key="index" :label="item.g_CName" :value="item.g_CName">
                        <div @click="getXian(item.groupID)">{{ item.g_CName }}</div>
                    </el-option>
                </el-select>
                <el-select class="select-input" v-model="ruleForm.xian" placeholder="县">
                    <el-option v-for="(item,index) in xianList" :key="index" :label="item.g_CName" :value="item.g_CName">
                        <div>{{ item.g_CName }}</div>
                    </el-option>
                </el-select>
            </div>
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
            <el-input placeholder="请输入详细地址" v-model="ruleForm.address"></el-input>
        </el-form-item>
        <el-form-item label="详细地址">
            <el-radio v-model="ruleForm.shop_nature" label="1">个人</el-radio>
            <el-radio v-model="ruleForm.shop_nature" label="2">企业</el-radio>
        </el-form-item>
        <el-form-item label="营业执照">
            <l-upload
                :limit="1"
                v-model="ruleForm.business_license"
            >
            </l-upload>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            <el-button class="bdColor" @click="$router.go(-1)" plain>{{ $t('DemoPage.tableFromPage.cancel') }}</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import addStore from '@/webManage/js/plug_ins/stores/addStore'
export default addStore
</script>

<style scoped lang="less">
@import '../../../webManage/css/plug_ins/stores/addStore.less';
</style>