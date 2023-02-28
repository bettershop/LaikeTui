<template>
  <div class="container">
    <div class="header">
      <span>店铺信息编辑</span>
    </div>

    <el-form :model="ruleForm" label-position="right" ref="ruleForm" label-width="100px" class="form-search" v-if="storeInfo">
      <div class="notice">
        <el-form-item label="店铺名称：" prop="">
          <span>{{ storeInfo.name }}</span>
        </el-form-item>
        <el-form-item class="input-interval" label="直播间房号：" prop="">
          <el-input v-model="ruleForm.roomid"></el-input>
        </el-form-item>
        <el-form-item class="input-interval" label="店铺信息：" prop="" required>
          <el-input v-model="ruleForm.shop_information"></el-input>
        </el-form-item>
        <el-form-item label="经营范围：" prop="" required>
          <el-input v-model="ruleForm.confines"></el-input>
        </el-form-item>
        <el-form-item label="店铺状态：" prop="">
          <span>{{ storeInfo.is_open == 0 ? '未营业' : storeInfo.is_open == 1 ? '营业中' : '已打烊' }}</span>
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
        <el-form-item class="input-interval" label="联系电话：" prop="" required>
          <el-input v-model="ruleForm.tel"></el-input>
        </el-form-item>
        <el-form-item class="cascadeAddress input-interval" label="所在地区：" required>
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
        <el-form-item label="详细地址：" prop="" required>
          <el-input v-model="ruleForm.address"></el-input>
        </el-form-item>
        <el-form-item label="所属性质：" prop="">
            <el-radio-group v-model="ruleForm.shop_nature">
              <el-radio v-for="item in belongList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item class="business-license" label="营业执照：" prop="">
          <img :src="storeInfo.business_license" alt="">
        </el-form-item>
        <el-form-item class="footer-button">
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
        </el-form-item> 
      </div>
      
      <!-- <div class="footer-button">
        <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
        <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
      </div> -->
	  </el-form>
  </div>
</template>

<script>
import editorStore from '@/webManage/js/plug_ins/stores/editorStore'
export default editorStore
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/editorStore.less';
</style>