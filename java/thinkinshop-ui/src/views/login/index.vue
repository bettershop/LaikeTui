<template>
  <div id="container">
    <div class="main">
      <div class="content">
        <div class="el-aside"> 
          <img src="../../assets/imgs/loginBg1.png" alt="">
        </div>
        <div class="right_form">
          <div class="form">
            <div class="laike">
              <img :src="logo_img" />
            </div>
            <div class="form_login">
              <el-tabs v-model="activeName">
                <el-tab-pane label="平台登录" name="first">
                  <el-form
                    :model="platformForm"
                    status-icon
                    :rules="rules1"
                    ref="platformForm"
                    label-width="100px"
                    class="demo-ruleForm"
                  >
                    <el-form-item prop="userName">
                      <el-input
                        placeholder="请输入管理员账号"
                        ref="user"
                        type="text"
                        v-model="platformForm.userName"
                        autocomplete="off"
                        @keyup.enter.native="platformLogin"
                        prefix-icon="el-icon-user"
                      ></el-input>
                    </el-form-item>
                    <el-form-item prop="pwd">
                      <el-input
                        placeholder="请输入密码"
                        ref="pass"
                        type="password"
                        v-model="platformForm.pwd"
                        autocomplete="off"
                        @keyup.enter.native="platformLogin"
                        prefix-icon="el-icon-lock"          
                      ></el-input>
                    </el-form-item>
                    <div>
                      <el-form-item>
                        <el-button type="primary" @click.native.prevent="platformLogin">登录</el-button>
                      </el-form-item>
                    </div>
                  </el-form>
                </el-tab-pane>
                <el-tab-pane label="商户登录" name="second">
                  <el-form
                    :model="merchantsForm"
                    status-icon
                    :rules="rules2"
                    ref="merchantsForm"
                    label-width="100px"
                    class="demo-ruleForm"
                  >
                      <el-form-item prop="customerNumber">
                        <el-input
                          placeholder="请输入商户编号"
                          ref="userNum"
                          type="text"
                          v-model="merchantsForm.customerNumber"
                          autocomplete="off"
                          prefix-icon="el-icon-date"
                        ></el-input>
                      </el-form-item>

                      <el-form-item prop="userName">
                        <el-input
                          placeholder="请输入管理员账号"
                          ref="userName"
                          type="text"
                          v-model="merchantsForm.userName"
                          autocomplete="off"
                          prefix-icon="el-icon-user"
                        ></el-input>
                      </el-form-item>
                      <el-form-item prop="pwd">
                        <el-input
                          placeholder="请输入管理员密码"
                          ref="pwd"
                          type="password"
                          v-model="merchantsForm.pwd"
                          autocomplete="off"
                          prefix-icon="el-icon-lock"
                        ></el-input>
                      </el-form-item>
                    <div>
                      <el-form-item>
                        <el-button :loading="loading" type="primary" @click.native.prevent="merchantsLogin('merchantsForm')">登录</el-button>
                      </el-form-item>
                    </div>
                  </el-form>
                </el-tab-pane>
              </el-tabs>
            </div>
          </div>
        </div>
      </div>
    
      <div class="footer">
        <div class="footer_box">
          <ul class="link-list">
            <li v-for="(item,index) in linkList" :key="index">
              <a :href="item.url" target="_blank" v-if="item.url">{{ item.name }}</a>
              <span v-else>{{ item.name }}</span>
            </li>
          </ul>
          <div class="introduce">
            <span>{{ copyright_information }}</span>
            <span class="hr">版权所有</span>
            <span>{{ record_information }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { validUsername } from '@/utils/validate'
import { index } from '@/api/Platform/system'
import { getShopInfo } from '@/api/Platform/merchants'
import axios from 'axios'
export default {
  data() {
    var validateUser = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入管理员账号"));
      } else {
        // if (this.ruleForm.user !== "") {
        //   this.$refs.ruleForm.validateField("username");
        // }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        // if (this.ruleForm.pass !== "") {
        //   this.$refs.ruleForm.validateField("password");
        // }
        callback();
      }
    };
    var validateUserNum = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入商户编号"));
      } else {
        // if (this.ruleForm.userNum !== "") {
        //   this.$refs.ruleForm.validateField("userNum");
        // }
        callback();
      }
    };
    var validateName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入管理员账号"));
      } else {
        // if (this.ruleForm.name !== "") {
        //   this.$refs.ruleForm.validateField("name");
        // }
        callback();
      }
    };
    return {
      platformForm: {
        userName: "",
        pwd: "",
      },
      merchantsForm: {
        customerNumber: "",
        userName: "",
        pwd: "",
      },
      rules1: {
        userName: [{ validator: validateUser, trigger: "blur" }],
        pwd: [{ validator: validatePass, trigger: "blur" }],
        
      },
      rules2: {
        customerNumber: [{ validator: validateUserNum, trigger: "blur" }],
        userName: [{ validator: validateName, trigger: "blur" }],
        pwd: [{ validator: validatePass, trigger: "blur" }],
      },
      activeName: "first",
      loading:false,

      linkList: [],
      baseUrl: '',
      copyright_information: '',
      record_information: '',
      logo_img: ''
    };
  },

  created() {
    if(process.env.NODE_ENV == 'development') {
      axios({
        method: 'post',
        url: 'api'+"/admin/system/getSetSystem",
        params: {
          api: 'admin.system.getSetSystem',
        }
      }).then(res => {
        if(res.data.code == '200') {
          let info = res.data.data.config
          this.linkList = JSON.parse(info.link_to_landing_page)
          this.copyright_information = info.copyright_information
          this.record_information = info.record_information
          this.logo_img = info.logo
        }
      })
    } else if (process.env.NODE_ENV == 'production') {
      axios({
        method: 'post',
        url: localStorage.getItem("api_url"),
        params: {
          api: 'admin.system.getSetSystem',
        }
      }).then(res => {
        if(res.data.code == '200') {
          console.log(res);
          console.log(JSON.parse(res.data.data.config.link_to_landing_page));
          let info = res.data.data.config
          this.linkList = JSON.parse(info.link_to_landing_page)
          this.copyright_information = info.copyright_information
          this.record_information = info.record_information
          this.logo_img = info.logo
        }
      })
    }
    
  },

  methods: {
    envSetFun() {
      this.baseUrl = localStorage.getItem("api_url")
    },
    platformLogin() {
      this.$refs.platformForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.platformForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    merchantsLogin(merchantsForm) {
      this.$refs['merchantsForm'].validate(valid => {
        if (valid) {
            this.loading = true
            this.$store.dispatch('user/login', this.merchantsForm).then(() => {
              this.$router.push({ path: this.redirect || '/' })
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
};
</script>

<style scoped lang="less">
#container {
  width: 100%;
  height: 100%;
}
.main {
  width: 100%;
  height: 100%;
  background-image: url(http://xiaochengxu.houjiemeishi.com/V3/images/icon1/loginBg.png);
  background-repeat: no-repeat;
  background-position: left top;
  background-size: auto 100%;
  /deep/ .el-form-item__error {
    margin-left: 40px;
  }

  /deep/ .el-button {
    width: 399px;
    height: 60px;
    border-radius: 20px;
    button {
      width: 399px;
      height: 60px;
      background: linear-gradient(90deg, #2890FF 0%, #2890FF 100%);
      box-shadow: 0px 6px 20px 0px rgba(40, 144, 255, 0.53);
      border-radius: 20px;
    }
  }
  /deep/ .el-tabs {
    position: relative;
    top: 60px;
  }
  /deep/ .el-tabs__header {
    margin-bottom: 50px;
  }
  /deep/ .el-tabs__nav-wrap::after {
    content: "";
    position: absolute;
    left: 0;
    bottom: 0;
    width: 420px;
    height: 2px;
    background-color: #e4e7ed;
    z-index: 1;
  }
  /deep/ #tab-first {
    width: 210px;
    text-align: center;
    height: 16px;
    // font-size: 16px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    // color: #999999;
  }
  /deep/ #tab-second {
    width: 200px;
    text-align: center;
  }


  /deep/ .content{
      display: flex;
      align-items: center;
      justify-content: center;
      box-sizing: border-box;
      width: 100%;
      height: 100%;
      z-index: 999;
  }

  .el-aside{
    width: 57%;
    img {
      width: 100%;
    }
  }

  /deep/ .image_box img{
      width: 1103px;
      height: 526px;
      border-style: none;
  }

  /deep/ .right_form {
    width: 800px;
    display: flex;
    box-sizing: border-box;
    flex: 1;
    flex-basis: auto;
  }

  /deep/ .form {
    margin-left: 50%;
    transform: translateX(-50%);
  }

  /deep/ .form img {
    margin-left: 50%;
    width: 151px; 
    height: 50px;
    transform: translateX(-50%);
  }

  /deep/ .form_login {
    width: 420px;
  }

  /deep/ .demo-ruleForm {
    width: 420px;
  }


  /deep/.el-form {
    .el-form-item {
      width: 400px;
      height: 60px;
      .el-form-item__content {
        margin-left: 0px !important;
        .el-input {
          width: 400px;
          height: 60px;
          input {
            width: 400px;
            height: 60px;
            border: 1px solid #CCCCCC;
            border-radius: 20px;
            padding-left: 42px;
          }
          .el-input__prefix {
            .el-input__icon {
              margin-top: 3px;
            }
            .el-input__icon:before {
              font-size: 16px;
            }
          }
        }
      }
    }
  }

  .footer{
    width: 100%;
    height: 94px;
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    bottom: 0px;
  }

  .footer .footer_box{
    text-align: center;
    width: 100%;
    height: 94px;
    font-size: 12px;
    font-family: Source Han Sans CN;
    font-weight: 400;
    color: #778BA1;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .footer_box {
    ul {
      display: flex;
      li {
        &:not(:last-child)::after {
          content: '|';
          margin: 0 5px;
        }
        a {
          color: #778ba1;
          &:hover {
            color: #2890ff;
          }
        }
      }
    }

    .introduce {
      margin-top: 10px;
      display: flex;
      align-items: center;
      justify-content: center;
      .hr {
        margin: 0 5px;
      }
    }
  }

  /deep/.el-input__inner {
		border: 1px solid #d5dbe8;
	}
	/deep/.el-input__inner:hover {
		border: 1px solid #b2bcd4;
	}
	/deep/.el-input__inner:focus {
		border-color: #409eff !important;
	}
	/deep/.el-input__inner::-webkit-input-placeholder {
		color: #97a0b4;
	}
}
</style>