<template>
  <div class="container">
    <div class="header">
      <span>添加会员</span>
    </div>

    <el-form :model="ruleForm" label-position="right" :rules="rule" ref="ruleForm" label-width="80px" class="form-search">
      <div class="notice">
        <el-form-item label="会员头像" id="upload" prop="membersHead" ref="membersHead">
						<l-upload
              :limit="1"
              v-model="ruleForm.membersHead"
              text="(会员头像默认显示会员设置的头像"
            >
            </l-upload>
				</el-form-item>
        <el-form-item label="会员名称" prop="userName">
          <el-input v-model="ruleForm.userName" placeholder="请输入会员名称"></el-input>
          <span class="gray">(会员名称默认显示会员设置的昵称)</span>
        </el-form-item>
        <el-form-item label="会员级别" prop="grade">
          <el-select class="select-input" v-model="ruleForm.grade" placeholder="请选择会员等级">
            <el-option v-for="(item,index) in membersGrade" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <span class="gray">(会员级别默认显示普通会员)</span>
        </el-form-item>
        <el-form-item label="会员账号" prop="zhanghao">
          <el-input v-model="ruleForm.zhanghao" placeholder="请输入会员账号"></el-input>
        </el-form-item>
        <el-form-item label="会员密码" prop="mima">
          <el-input v-model="ruleForm.mima" placeholder="请输入会员密码" show-password auto-complete="new-password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmMima">
          <el-input v-model="ruleForm.confirmMima" placeholder="请确认密码" show-password auto-complete="new-password"></el-input>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="ruleForm.phone" placeholder="请输入手机号码"></el-input>
        </el-form-item>
        <el-form-item label="账号来源" prop="source">
          <el-select class="select-input" v-model="ruleForm.source" placeholder="账号来源">
            <el-option v-for="(item,index) in sourceList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      
        <div class="footer-button">
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
        </div>
      </div>
		</el-form>
  </div>
</template>

<script>
import addMembers from '@/webManage/js/members/membersList/addMembers'
export default addMembers
</script>

<style scoped lang="less">
@import '../../../../webManage/css/members/membersList/addMembers.less';
</style>