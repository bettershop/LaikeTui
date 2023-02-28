<template>
  <div class="container">
    <el-form ref="ruleForm" class="form-search" :rules="rules" :model="ruleForm" label-width="100px">
      <div class="basic-info">
        <div class="header">
          <span>基础信息</span>
        </div>
        <div class="basic-block">
          <el-form-item label="商户名称" prop="storeName">
						<el-input v-model="ruleForm.storeName" placeholder="请输入商户名称"></el-input>
					</el-form-item>
          <el-form-item label="商户编号" prop="storeNo">
						<el-input v-model="ruleForm.storeNo" placeholder="请输入商户编号"></el-input>
					</el-form-item>
          <el-form-item label="公司名称" prop="company">
						<el-input v-model="ruleForm.company" placeholder="请输入公司名称"></el-input>
					</el-form-item>
          <el-form-item label="手机号" prop="mobile">
						<el-input v-model="ruleForm.mobile" placeholder="请输入手机号"></el-input>
					</el-form-item>
          <el-form-item label="价格" prop="price">
						<el-input class="composite" v-model="ruleForm.price" placeholder="请输入价格">
              <template slot="append">元</template>
            </el-input>
					</el-form-item>
          <el-form-item label="邮箱" prop="email">
						<el-input v-model="ruleForm.email" placeholder="请输入邮箱"></el-input>
					</el-form-item>
          <el-form-item label="管理员账号" prop="adminAccount">
						<el-input v-model="ruleForm.adminAccount" placeholder="请输入管理员账号"></el-input>
					</el-form-item>
          <el-form-item label="管理员密码" prop="adminPwd">
						<el-input show-password v-model="ruleForm.adminPwd" placeholder="请输入管理员密码"></el-input>
					</el-form-item>
          <el-form-item label="到期时间" prop="endDate">
						<el-date-picker
              v-model="ruleForm.endDate"
              type="datetime"
              value-format='yyyy-MM-dd HH:mm:ss'
              placeholder="选择日期时间">
            </el-date-picker>
					</el-form-item>
          <el-form-item label="是否启用" prop="isOpen">
            <el-switch v-model="ruleForm.isOpen" :active-value="0" :inactive-value="2" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
					</el-form-item>
        </div>
      </div>
      <div class="configuration-info">
        <div class="header">
          <span>配置信息</span>
        </div>
        <div class="configuration-block">
          <el-form-item label="商城根目录域名" prop="storeDomain">
						<el-input v-model="ruleForm.storeDomain" placeholder="请输入商城根目录域名"></el-input>
					</el-form-item>
          <el-form-item label="联系地址" prop="contactAddress">
						<el-input v-model="ruleForm.contactAddress" placeholder="请输入联系地址"></el-input>
					</el-form-item>
          <el-form-item label="联系电话" prop="contactNumber">
						<el-input v-model="ruleForm.contactNumber" placeholder="请输入联系电话"></el-input>
					</el-form-item>
          <el-form-item label="版权信息" prop="copyrightInformation">
						<el-input v-model="ruleForm.copyrightInformation" placeholder="请输入版权信息"></el-input>
					</el-form-item>
          <el-form-item label="备案信息" prop="recordInformation">
						<el-input v-model="ruleForm.recordInformation" placeholder="请输入备案信息">
            </el-input>
					</el-form-item>
          <el-form-item label="官方网站" prop="website">
						<el-input v-model="ruleForm.website" placeholder="请输入官方网站"></el-input>
					</el-form-item>
          <el-form-item required label="商户logo" prop="logoUrl">
            <l-upload
              :limit="1"
              v-model="ruleForm.logoUrl"
              text="建议尺寸：122*40px，白色"
            >
            </l-upload>
          </el-form-item>
        </div>
      </div>
      
      <div class="footer-button">
        <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
        <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
      </div>
    </el-form>
    <div class="role-authorization">
      <el-form :rules="rules2" :model="ruleForm2" label-width="100px">
        <div class="header">
          <span>角色权限</span>
        </div>
        <div class="role-block">
          <el-form-item label="绑定角色" prop="region">
						<el-select class="select-input" v-model="ruleForm2.region" placeholder="请选择角色">
              <el-option v-for="item in Dictionary" :key="item.id" :label="item.name" :value="item.name">
                <div @click="change(item.id)">{{ item.name }}</div>
              </el-option>
            </el-select>
            <span class="prompt">绑定角色和权限可以在权限管理->角色列表中修改</span>
					</el-form-item>
          <el-form-item label="绑定权限" prop="">
            <el-tree
              :data="treeData"
              :show-checkbox="false"
              node-key="id"
              :props="defaultProps">
            </el-tree>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { addStore, getUserRoleInfo } from '@/api/Platform/merchants'
import { getRoleListInfo } from '@/api/Platform/permissions'
export default {
  name: 'addmerchants',

  data() {
    return {
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'title',
        disabled: true
      },
      ruleForm: {
        // 基础信息
        storeName: '',
        storeNo: '',
        company: '',
        mobile: '',
        price: '',
        email: '',
        adminAccount: '',
        adminPwd: '',
        endDate: '',
        isOpen: 0,

        // 配置信息
        storeDomain: '',
        contactAddress: '',
        contactNumber: '',
        copyrightInformation: '',
        recordInformation: '',
        website: '',
        logoUrl: '',

      },

      Dictionary: [],
      id: '',

      rules: {
        storeName: [
          {required: true, message: '请输入商户名称', trigger: 'blur'}
        ],
        storeNo: [
          {required: true, message: '请输入商户编号', trigger: 'blur'}
        ],
        company: [
          {required: true, message: '请输入公司名称', trigger: 'blur'}
        ],
        mobile: [
          {required: true, message: '请输入手机号', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入价格', trigger: 'blur'}
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'}
        ],
        adminAccount: [
          {required: true, message: '请输入管理员账号', trigger: 'blur'}
        ],
        adminPwd: [
          {required: true, message: '请输入管理员密码', trigger: 'blur'}
        ],
        endDate: [
          {required: true, message: '请选择日期时间', trigger: 'change'}
        ],
        storeDomain: [
          {required: true, message: '请输入商城根目录域名', trigger: 'blur'}
        ],
        copyrightInformation: [
          {required: true, message: '请输入版权信息', trigger: 'blur'}
        ],
        recordInformation: [
          {required: true, message: '请输入备案信息', trigger: 'blur'}
        ],
        logoUrl: [
          {required: true, message: '请上传商户logo', trigger: 'change'}
        ]
      },

      ruleForm2: {
        // 角色权限
        region: ''
      },

      rules2: {
        region: [
          {required: true, message: '请选择角色', trigger: 'blur'}
        ],
      }
    }
  },

  created() {
    this.getRoleListInfos()
  },

  watch: {
    id() {
      this.getUserRoleInfos()
    }
  },

  methods: {
    disabledFn() {
      // if(this.operate === 'view') {
      //   return true
      // }
      return false
    },

    // 获取角色列表
    async getRoleListInfos() {
      const res = await getRoleListInfo({
        api: 'saas.role.getRoleListInfo',
      })
      console.log(res);
      this.Dictionary = res.data.data.list
    },

    // 根据角色获取权限
    async getUserRoleInfos() {
      const res = await getUserRoleInfo({
        api: 'admin.role.getUserRoleInfo',
        id: this.id
      })
      this.treeData = res.data.data.menuList.filter(item => {
        if(item.checked) {
          // this.filterChildren(item)
          return item
        }
      })
      console.log(res);
    },

    change(value) {
      this.id = value
    },

    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        console.log(this.ruleForm);
        if (valid) {
          try {
            addStore({
              api: 'saas.shop.addStore',
              storeId: null,
              storeName: this.ruleForm.storeName,
              storeNo: this.ruleForm.storeNo,
              company: this.ruleForm.company,
              mobile: this.ruleForm.mobile,
              price: this.ruleForm.price,
              email: this.ruleForm.email,
              adminAccount: this.ruleForm.adminAccount,
              adminPwd: this.ruleForm.adminPwd,
              endDate: this.ruleForm.endDate,
              isOpen: this.ruleForm.isOpen,
              storeDomain: this.ruleForm.storeDomain,
              logUrl: this.ruleForm.logoUrl,
              website: this.ruleForm.website,
              recordInformation: this.ruleForm.recordInformation,
              copyrightInformation: this.ruleForm.copyrightInformation,
              contactNumber: this.ruleForm.contactNumber,
              contactAddress: this.ruleForm.contactAddress,
              roleId: this.id
            }).then(res => {
              if(res.data.code == '200') {
                this.$message({
                  message: '添加成功',
                  type: 'success',
                  offset: 100
                })
                this.$router.go(-1)
              }
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

<style scoped  lang="less">
.container {
  width: 100%;
  /deep/.el-form {
    .header {
      width: 100%;
      height: 60px;
      line-height: 60px;
      border-bottom: 1px solid #E9ECEF;
      padding-left: 20px;
      span {
        font-weight: 400;
        font-size: 16px;
        color: #414658;
      }
    }
    .el-input {
      width: 400px;
      height: 40px;
      input {
        width: 400px;
        height: 40px;
      }
    }
    .composite {
      input {
        width: 345px !important;
      }
    }


    .basic-info {
      width: 100%;
      background-color: #fff;
      margin-bottom: 16px;
      .basic-block {
        margin-top: 40px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        padding: 0 20px 0 52px;
      }
    }

    .configuration-info {
      width: 100%;
      background-color: #fff;
      margin-bottom: 16px;
      .configuration-block {
        margin-top: 40px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        padding: 0 20px 0 23px;
        .el-form-item {
          display: flex;
          .el-form-item__label {
            width: 130px !important;
          }
          .el-form-item__content {
            margin-left: 0 !important;
          }
        }
        
      }
    }

    .footer-button {
      position: fixed;
      right: 0;
      bottom: 40px;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      padding: 15px 20px;
      border-top: 1px solid #E9ECEF;
      background: #FFFFFF;
      width: 300%;
      z-index: 10;
      button {
        width: 70px;
        height: 40px;
      }
      .bgColor {
        margin-left: 14px;
      }
      .bgColor:hover {
        opacity: 0.8;
      }

      .fontColor {
        color: #6a7076;
        border: 1px solid #d5dbe8;
        margin-left: 14px;
      }
      .fontColor:hover {
        color: #2890ff;
        border: 1px solid #2890ff;
      }
    }
    .el-form-item__label {
      font-weight: normal;
    }
  }
  /deep/.role-authorization {
    width: 100%;
    height: 633px;
    padding-bottom: 71px;
    background-color: #fff;
    .role-block {
      margin-top: 40px;
      padding-left: 50px;
      .el-tree {
        left: -10px;
        top: 5px; 
      }
    }

    .prompt {
      margin-left: 20px;
    }

    .el-checkbox__input.is-checked .el-checkbox__inner, .el-checkbox__input.is-indeterminate .el-checkbox__inner {
      background-color: #B2BCD1;
      border-color: #B2BCD1;
    }
    .el-form-item__label {
      font-weight: normal;
    }
    
  }
  /deep/.el-form-item__label {
    color: #414658;
  }
  /deep/.el-input__inner {
		border: 1px solid #d5dbe8;
	}
	/deep/.el-input__inner:hover {
		border: 1px solid #b2bcd4;
	}
	/deep/.el-input__inner:focus {
		border-color: #409eff;
	}
	/deep/.el-input__inner::-webkit-input-placeholder {
		color: #97a0b4;
	}
}
</style>