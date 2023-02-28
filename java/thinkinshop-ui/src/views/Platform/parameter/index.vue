<template>
  <div class="container">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="133px" class="demo-ruleForm">
        <el-form-item label="第三方平台appid" prop="appid">
            <el-input v-model="ruleForm.appid" placeholder="请输入第三方平台appid"></el-input>
        </el-form-item>
        <el-form-item label="第三方平台密钥" prop="appsecret">
            <el-input v-model="ruleForm.appsecret" placeholder="请输入第三方平台密钥"></el-input>
        </el-form-item>
        <el-form-item label="消息检验Token" prop="checkToken">
            <el-input v-model="ruleForm.checkToken" placeholder="请输入消息检验Token"></el-input>
        </el-form-item>
        <el-form-item label="消息加解密key" prop="encryptKey">
            <el-input v-model="ruleForm.encryptKey" placeholder="消息加解密key"></el-input>
        </el-form-item>
        <el-form-item label="服务器域名" prop="serveDomain">
            <el-input v-model="ruleForm.serveDomain" placeholder="请输入服务器域名"></el-input>
            <span class="prompt">多个域名以英文,分隔 域名不需要加https</span>
        </el-form-item>
        <el-form-item label="业务域名" prop="workDomain">
            <el-input v-model="ruleForm.workDomain" placeholder="请输入业务域名"></el-input>
            <span class="prompt">多个域名以英文,分隔 域名不需要加https</span>
        </el-form-item>
        <el-form-item label="授权回调地址" prop="redirectUrl">
            <el-input v-model="ruleForm.redirectUrl" placeholder="请输入授权回调地址"></el-input>
        </el-form-item>
        <el-form-item label="小程序请求地址" prop="miniUrl">
            <el-input v-model="ruleForm.miniUrl" placeholder="请输入小程序请求地址"></el-input>
        </el-form-item>
        <el-form-item label="客服请求地址" prop="kefuUrl">
            <el-input v-model="ruleForm.kefuUrl" placeholder="请输入客服请求地址"></el-input>
        </el-form-item>
        <el-form-item label="体验二维码地址" prop="qrCode">
            <el-input v-model="ruleForm.qrCode" placeholder="请输入体验二维码地址"></el-input>
        </el-form-item>
        <el-form-item label="根目录路径" prop="endurl">
            <el-input v-model="ruleForm.endurl" placeholder="请输入根目录路径"></el-input>
            <span class="prompt">路径末尾必须有反斜杠：/</span>
        </el-form-item>
        <div class="form-footer">
            <el-form-item>
                <el-button class="bgColor" type="primary" @click="submitForm('ruleForm')">{{ $t('DemoPage.tableFromPage.save') }}</el-button>
            </el-form-item>
        </div>
    </el-form>
  </div>
</template>

<script>
import { addThridParmate, getThridParmate} from '@/api/Platform/parameter'

export default {
    name: 'parameter',

    data() {
        return {
            ruleForm: {
                id:1,
                // 第三方平台appid
                appid: '',
                // 第三方平台秘钥
                appsecret: '',
                // 消息检验Token
                checkToken: '',
                // 消息加解密key
                encryptKey: '',
                // 服务器域名
                serveDomain: '',
                // 业务域名
                workDomain: '',
                // 授权回调地址
                redirectUrl: '',
                // 小程序请求地址
                miniUrl: '',
                // 客服请求地址
                kefuUrl: '',
                // 体验二维码地址
                qrCode: '',
                // 根目录路径
                endurl: ''
            },
            rules: {
                appid: [
                    { required: true, message: '第三方平台appid', trigger: 'blur' },
                ],
                platformKey: [
                    { required: true, message: '请输入第三方平台密钥', trigger: 'blur' },
                ]
            },
        }
    },

    created(){
        this.axios();
    },

    methods: {
        async axios(){
            let params = Object.assign({
                api: 'saas.authorize.getThridParmate'
            },this.ruleForm);
            const res = await getThridParmate(params);
            let data = res.data.data.list;
            this.ruleForm.appid = data.appid;
            this.ruleForm.appsecret = data.appsecret;
            this.ruleForm.checkToken = data.check_token;
            this.ruleForm.encryptKey = data.encrypt_key;
            this.ruleForm.serveDomain = data.serve_domain;
            this.ruleForm.workDomain = data.work_domain;
            this.ruleForm.redirectUrl = data.redirect_url;
            this.ruleForm.miniUrl = data.mini_url;
            this.ruleForm.kefuUrl = data.kefu_url;
            this.ruleForm.qrCode = data.qr_code;
            this.ruleForm.endurl = data.endurl;
        },
        submitForm(formName) {
            this.$refs[formName].validate(async (valid) => {
                console.log(this.ruleForm);
                if (valid) {
                try {
                    addThridParmate(Object.assign({
                        api: 'saas.authorize.addThridParmate',
                        id:1
                    },this.ruleForm)).then(() => {
                        this.$message({
                            message: '添加成功',
                            type: 'success',
                        })
                        this.axios();
                    })
                } catch (error) {
                    this.$message({
                        message: error.message,
                        type: 'error',
                        showClose: true
                    })
                }
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
    }
}
</script>

<style scoped lang="less">
.container {
    width: 100%;
    height: 737px;
    background-color: #fff;
    padding: 40px 0 0 407px;
    overflow: hidden;
    /deep/.el-form {
        height: 697px;
        overflow-y: auto;
        padding-bottom: 20px;
        .el-form-item__label {
            font-weight: normal !important;
        }
    }


    /deep/.el-form-item {
        margin-bottom: 20px;
        .el-input {
            width: 580px;
            height: 40px;
            input {
                width: 580px;
                height: 40px;
            }
        }

        .prompt {
            margin-left: 14px;
        }

    }
}
</style>