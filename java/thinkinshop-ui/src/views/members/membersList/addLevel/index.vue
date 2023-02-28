<template>
  <div class="container">
    <div class="header">
        <span>{{ membersChange }}</span>
    </div>
    <el-form ref="ruleForm" class="form-search" :model="ruleForm" label-width="130px">
      <div class="basic-info">
        <div class="basic-block">
            <el-form-item label="会员等级名称" required>
				<el-input v-model="ruleForm.levelNamme" placeholder="请输入会员等级名称"></el-input>
			</el-form-item>
            <el-form-item label="会员背景图：">
                <l-upload
                    :limit="1"
                    v-model="ruleForm.bgImgUrl"
                    text="（展示图最多上传一张，建议上传690px*300px的图片）"
                >
                </l-upload>
			</el-form-item>
             <el-form-item label="小图标：">
                <l-upload
                    :limit="1"
                    v-model="ruleForm.smallLogo"
                    text="（展示图最多上传一张，建议上传33px*30px的图片）"
                >
                </l-upload>
			</el-form-item>
            <el-form-item label="会员字体颜色：" style="margin-bottom:10px">
				<el-input v-model="ruleForm.membersFontColor" style="top:-14px" placeholder="请输入会员字体颜色"></el-input>
                <el-color-picker v-model="ruleForm.membersFontColor"></el-color-picker>
			</el-form-item>
            <el-form-item label="日期字体颜色：" style="margin-bottom:0px">
				<el-input v-model="ruleForm.dateFontColor" style="top:-14px" placeholder="请输入日期字体颜色"></el-input>
                <el-color-picker v-model="ruleForm.dateFontColor"></el-color-picker>
			</el-form-item>
            <el-form-item label="晋升条件：" required>
				<div class="promotion-condition">
                    <p>购买会员服务</p>
                    <p>补差额升级</p>
                </div>
			</el-form-item>
            <el-form-item class="discount" label="专属折扣：" style="margin-top:-10px" required>
				<el-input type="number" min="1" max="9" v-model="ruleForm.discount" placeholder="请输入专属折扣"></el-input>
			</el-form-item>
             <el-form-item label="开通方式：" required>
                <!-- <span>包月</span> -->
				<el-input class="interval" v-model="ruleForm.month" placeholder=""></el-input>
                <span> /每月</span>
            </el-form-item>
            <el-form-item label="">
                <!-- <span>包季</span> -->
				<el-input class="interval" v-model="ruleForm.season" placeholder=""></el-input>
                <span> /每季</span>
            </el-form-item>
            <el-form-item label="">
                <!-- <span>包年</span> -->
				<el-input class="interval" v-model="ruleForm.years" placeholder=""></el-input>
                <span> /每年</span>
            </el-form-item>
            <el-form-item label="会员赠送商品：">
				<el-select class="select-input" v-model="ruleForm.givingGoods" placeholder="请选择赠送商品">
                    <el-option v-for="(item,index) in givingGoodsList" :key="index" :label="item.product_title" :value="item.id">
                    </el-option>
                </el-select>
			</el-form-item>
            <el-form-item label="备注：">
				<el-input type="textarea" :rows="3" v-model="ruleForm.note" placeholder=""></el-input>
			</el-form-item>
            <div class="btn">
                <el-form-item>
                    <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
                    <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
                </el-form-item>
            </div>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>
import addLevel from '@/webManage/js/members/membersList/addLevel'
export default addLevel
</script>

<style scoped lang="less">
@import '../../../../webManage/css/members/membersList/addLevel.less';
</style>