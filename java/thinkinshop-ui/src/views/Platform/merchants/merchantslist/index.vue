<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input
            v-model="inputInfo.code"
            size="medium"
            @keyup.enter.native="demand"
            class="Search-input"
            placeholder="请输入商户名称"
          ></el-input>
          <div class="select-date">
            <span>购买日期：</span>
            <el-date-picker
              v-model="inputInfo.range"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              :editable="false"
            ></el-date-picker>
          </div>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button
            class="bgColor"
            type="primary"
            @click="demand"
          >{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="dialogShow2">导出</el-button>
        </div>
      </div>
    </div>
    <div class="jump-list">
      <el-button
        class="bgColor laiketui laiketui-add"
        type="primary"
        @click="$router.push('/Platform/merchants/addmerchants')"
      >添加商户</el-button>
    </div>
    <div class="merchants-list" ref="tableFather">
      <el-table
        element-loading-text="拼命加载中..."
        v-loading="loading"
        :data="tableData"
        ref="table"
        class="el-table"
        style="width: 100%"
        :height="tableHeight"
      >
        <el-table-column prop="id" label="商户ID"></el-table-column>
        <el-table-column prop="name" label="商户名称"></el-table-column>
        <el-table-column prop="mobile" label="手机"></el-table-column>
        <el-table-column prop="price" label="价格"></el-table-column>
        <el-table-column prop="company" label="公司名称"></el-table-column>
        <el-table-column prop="add_date" label="购买时间" width="210px">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="end_date" label="到期时间" width="210px">
          <template slot-scope="scope">
            <span>{{ scope.row.end_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="roleName" label="角色"></el-table-column>
        <el-table-column prop="state" label="状态">
          <template slot-scope="scope">
            <span
              class="state"
              :class="scope.row.status === 0 ? 'active1' : 'active2' "
            >{{ scope.row.status === 0 ? '启用' : scope.row.status === 2 ? '锁定' : '到期' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="设为默认">
          <template slot-scope="scope">
            <el-switch
              v-if="scope.row.status === 0"
              v-model="scope.row.is_default"
              @change="switchs(scope.row)"
              :active-value="1"
              :inactive-value="0"
              active-color="#00ce6d"
              inactive-color="#d4dbe8"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="246">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button
                  icon="el-icon-lock"
                  @click="Lock(scope.row)"
                >{{ scope.row.status === 0 || scope.row.status === 1 ? '锁定' : '开启' }}</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
              </div>
              <div class="OP-button-bottom">
                <el-button icon="el-icon-edit-outline" @click="dialogShow(scope.row)">密码重置</el-button>
                <el-button class="laiketui laiketui-jinru" @click="enterSystem(scope.row)">进入系统</el-button>
              </div>
            </div>
		      </template>
        </el-table-column>
      </el-table>
      <div class="pageBox" ref="pageBox" v-if="showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination
          layout="sizes, slot, prev, pager, next"
          prev-text="上一页"
          next-text="下一页"
          @size-change="handleSizeChange"
          :page-sizes="pagesizes"
          :current-page="pagination.page"
          @current-change="handleCurrentChange"
          :total="total"
        >
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog :title="title" :visible.sync="dialogVisible" :before-close="handleClose">
        <el-form
          :model="ruleForm2"
          :rules="rules2"
          ref="ruleForm2"
          label-width="100px"
          class="demo-ruleForm"
        >
          <div class="pass-input">
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="ruleForm2.newPassword" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="ruleForm2.confirmPassword" show-password></el-input>
            </el-form-item>
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button @click="dialogVisible = false" class="qxcolor">取 消</el-button>
              <el-button type="primary" @click="determine('ruleForm2')" class="qdcolor">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <div class="dialog-export">
      <!-- 弹框组件 -->
      <el-dialog title="导出数据" :visible.sync="dialogVisible2" :before-close="handleClose2">
        <div class="item" @click="exportPage">
          <i class="el-icon-document"></i>
          <span>导出本页</span>
        </div>
        <div class="item item-center" @click="exportAll">
          <i class="el-icon-document-copy"></i>
          <span>导出全部</span>
        </div>
        <div class="item" @click="exportQuery">
          <i class="el-icon-document"></i>
          <span>导出查询</span>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  getShopInfo,
  resetAdminPwd,
  delStore,
  setStoreOpenSwitch,
  setStoreDefaultSwitch,
} from "@/api/Platform/merchants";
import { getRoleMenu } from "@/api/Platform/permissions";
import { setStorage, getStorage } from "@/utils/storage";
import { exports } from "@/api/export/index";
import { mixinstest } from "@/mixins/index";
import { setUserAdmin } from '@/api/Platform/merchants'
export default {
  name: "shoppinglist",

  mixins: [mixinstest],
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm2.confirmPassword !== "") {
          this.$refs.ruleForm2.validateField("confirmPassword");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm2.newPassword) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      tableData: [],
      loading: true,
      inputInfo: {
        code: "",
        range: "",
      },

      // 弹框数据
      dialogVisible: false,
      title: "",
      adminId: "",
      ruleForm2: {
        newPassword: "",
        confirmPassword: "",
      },
      rules2: {
        newPassword: [
          { required: true, message: "请填写密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "长度在 6 到 16 个字符",
            trigger: "blur",
          },
          { validator: validatePass, trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请确认密码", trigger: "blur" },
          {
            min: 6,
            max: 16,
            message: "长度在 6 到 16 个字符",
            trigger: "blur",
          },
          { validator: validatePass2, trigger: "blur", required: true },
        ],
      },

      // table高度
      tableHeight: null,

      // 导出弹框数据
      dialogVisible2: false,
    };
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.getShopInfos();
  },

  mounted() {
    this.$nextTick(function () {
      this.getHeight();
    });
    window.addEventListener("resize", this.getHeight(), false);
  },

  methods: {
    getHeight() {
      this.tableHeight =
        this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight;
    },

    // 获取商户列表
    async getShopInfos() {
      const res = await getShopInfo({
        api: "saas.shop.getShopInfo",
        storeType: 8,
        storeId: null,
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        storeName: this.inputInfo.code,
        startDate: this.inputInfo.range[0],
        endDate: this.inputInfo.range[1],
      });
      this.total = res.data.data.total;
      this.tableData = res.data.data.dataList;
      this.loading = false;
      if (res.data.data.total < 10) {
        this.current_num = this.total;
      }
    },

    // 重置
    reset() {
      this.inputInfo.code = "";
      this.inputInfo.range = "";
    },

    // 查询
    demand() {
      this.currpage = 1;
      this.current_num = 10;
      this.showPagebox = false;
      this.loading = true;
      this.dictionaryNum = 1;
      this.getShopInfos().then(() => {
        this.loading = false;
        if (this.tableData.length > 5) {
          this.showPagebox = true;
        }
      });
    },

    // 导出
    derive() {},

    //选择一页多少条
    handleSizeChange(e) {
      this.loading = true;
      console.log(e);
      // this.current_num = e;
      this.pageSize = e;
      this.getShopInfos().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.loading = false;
      });
    },

    //点击上一页，下一页
    handleCurrentChange(e) {
      this.loading = true;
      this.dictionaryNum = e;
      this.currpage = (e - 1) * this.pageSize + 1;
      this.getShopInfos().then(() => {
        this.current_num =
          this.tableData.length === this.pageSize
            ? e * this.pageSize
            : this.total;
        this.loading = false;
      });
    },

    // 锁定
    Lock(value) {
      if (value.status === 0) {
        this.$confirm("确定锁定此生效用户?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            setStoreOpenSwitch({
              api: "saas.shop.setStoreOpenSwitch",
              storeId: value.id,
            }).then((res) => {
              if (res.data.code == "200") {
                this.getShopInfos();
                this.$message({
                  type: "success",
                  message: "锁定成功!",
                  offset: 100,
                });
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消",
            });
          });
      } else if (value.status === 1) {
        this.$confirm("确定锁定此生效用户?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            setStoreOpenSwitch({
              api: "saas.shop.setStoreOpenSwitch",
              storeId: value.id,
            }).then((res) => {
              this.getShopInfos();
              this.$message({
                message: res.data.message,
                type: "error",
                offset: 100,
              });
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消",
            });
          });
      } else {
        this.$confirm("是否解除该商户锁定状态?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            setStoreOpenSwitch({
              api: "saas.shop.setStoreOpenSwitch",
              storeId: value.id,
            }).then((res) => {
              if (res.data.code == "200") {
                this.getShopInfos();
                this.$message({
                  type: "success",
                  message: "解除成功!",
                  offset: 100,
                });
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消",
            });
          });
      }
    },

    // 编辑
    Edit(value) {
      this.$router.push({
        name: "editormerchants",
        params: value,
        query: {
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      });
    },

    // 删除
    Delete(value) {
      console.log(value);
      this.$confirm("确定删除此商户?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          delStore({
            api: "saas.shop.delStore",
            storeId: value.id,
          }).then((res) => {
            if (res.data.code == "200") {
              this.getShopInfos();
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    // 进入系统
    enterSystem(value) {
      const laike_admin_uaerInfo = getStorage("laike_admin_uaerInfo");
      const rolesInfo = getStorage("rolesInfo");
      getShopInfo({
        api: "saas.shop.getShopInfo",
        storeType: 8,
        storeId: value.id,
      }).then((res) => {
        const info = res.data.data.dataList[0];
        console.log(info);
        laike_admin_uaerInfo.storeId = info.id;
        rolesInfo.storeId = info.id;
        setStorage("laike_admin_uaerInfo", laike_admin_uaerInfo);
        setStorage("rolesInfo", rolesInfo);
        this.$store.commit("user/SET_MERCHANTSLOGO", info.merchant_logo);
        setStorage("laike_head_img", info.merchant_logo);
        setUserAdmin({
          api: 'admin.saas.user.setUserAdmin',
        })
        .then(response => {
          const laike_admin_uaerInfo = getStorage('laike_admin_uaerInfo')
          laike_admin_uaerInfo.mchId = response.data.data.mchId
          setStorage('laike_admin_uaerInfo',laike_admin_uaerInfo)
          resolve(response)
        })
        this.$router.go(0);
      });
    },

    // 开关
    switchs(value) {
      setStoreDefaultSwitch({
        api: "saas.shop.setStoreDefaultSwitch",
        storeId: value.id,
      }).then((res) => {
        if (res.data.code == "200") {
          this.$message({
            message: "成功！",
            type: "success",
            offset: 100,
          });
        }
        this.getShopInfos();
      });
    },

    // 弹框方法
    dialogShow(value) {
      (this.ruleForm2.dataName = ""),
      (this.ruleForm2.status = "1"),
      (this.dialogVisible = true);
      this.title = value.company + "密码重置";
      this.adminId = value.admin_id;
    },

    handleClose(done) {
      this.dialogVisible = false;
    },

    // 修改密码
    determine(formName2) {
      this.$refs[formName2].validate(async (valid) => {
        if (valid) {
          try {
            resetAdminPwd({
              api: "saas.shop.resetAdminPwd",
              adminId: this.adminId,
              pwd: this.ruleForm2.newPassword,
            }).then((res) => {
              console.log(res);
              if (res.data.code == "200") {
                this.$message({
                  message: "修改成功",
                  type: "success",
                  offset: 100,
                });
                this.dialogVisible = false;
              }
            });
          } catch (error) {
            this.$message({
              message: "密码不能为空",
              type: "error",
              offset: 100,
            });
          }
        } else {
          this.$message({
            message: "确认密码不能为空",
            type: "error",
            offset: 100,
          });
          // return false;
        }
      });
    },

    // 弹框方法
    dialogShow2() {
      this.dialogVisible2 = true;
    },

    handleClose2(done) {
      this.dialogVisible2 = false;
    },

    async exportPage() {
      exports(
        {
          api: "saas.shop.getShopInfo",
          pageNo: this.dictionaryNum,
          pageSize: this.pageSize,
          exportType: 1,
          storeId: 0,
        },
        "userlist"
      );
    },

    async exportAll() {
      exports(
        {
          api: "saas.shop.getShopInfo",
          pageNo: 1,
          pageSize: this.total,
          exportType: 1,
          storeId: 0,
        },
        "userlist"
      );
    },

    async exportQuery() {
      exports(
        {
          api: "saas.shop.getShopInfo",
          pageNo: this.dictionaryNum,
          pageSize: this.total,
          exportType: 1,
          storeId: 0,
          storeName: this.inputInfo.code,
          startDate: this.inputInfo.range[0],
          endDate: this.inputInfo.range[1],
        },
        "userlist"
      );
    },
  },
};
</script>

<style scoped lang="less">
.container {
  display: flex;
  flex-direction: column;
  /deep/.Search {
    display: flex;
    align-items: center;
    background: #ffffff;
    border-radius: 4px;
    padding: 10px;
    margin-bottom: 16px;
    .Search-condition {
      display: flex;
      align-items: center;
      .query-input {
        display: flex;
        margin-right: 10px;
        .select-date {
          margin-left: 10px;
        }
        .Search-input {
          width: 280px;
          margin-right: 10px;
          .el-input__inner {
            height: 40px;
            line-height: 40px;
            border: 1px solid #d5dbe8;
          }
          .el-input__inner:hover {
            border: 1px solid #b2bcd4;
          }
          .el-input__inner:focus {
            border-color: #409eff;
          }
          .el-input__inner::-webkit-input-placeholder {
            color: #97a0b4;
          }
        }
      }

      .btn-list {
        .bgColor {
          background-color: #2890ff;
        }
        .bgColor:hover {
          background-color: #70aff3;
        }
        .fontColor {
          color: #6a7076;
          border: 1px solid #d5dbe8;
        }
        .fontColor:hover {
          color: #2890ff;
          border: 1px solid #2890ff;
          background-color: #fff;
        }
        .export {
          position: absolute;
          right: 30px;
        }
      }
    }
  }

  /deep/.jump-list {
    width: 100%;
    display: flex;
    align-items: center;
    margin-bottom: 16px;
    .laiketui-add:before {
      font-size: 14px;
      margin-right: 8px;
    }
    button {
      width: 120px;
      height: 40px;
      background: #28b6ff;
      border-radius: 4px;
      padding: 0;
      border: none;
      &:hover {
        opacity: 0.8;
      }
      span {
        font-size: 14px;
      }
    }
  }

  .merchants-list {
    // height: 595px;
    flex: 1;
    background: #ffffff;
    border-radius: 4px;
    /deep/.el-table__header {
      thead {
        tr {
          th {
            height: 61px;
            text-align: center;
            font-size: 14px;
            font-weight: bold;
            color: #414658;
          }
        }
      }
    }
    /deep/.el-table__body {
      tbody {
        tr {
          td {
            height: 92px;
            text-align: center;
            font-size: 14px;
            color: #414658;
            font-weight: 400;
          }
        }
      }
    }

    /deep/.el-table {
      .OP-button {
        .OP-button-top {
          margin-bottom: 8px;
          display: flex;
          justify-content: start;
        }

        .OP-button-bottom {
          display: flex;
          justify-content: start;
          .laiketui-jinru:before {
            margin-right: 6px;
          }
        }
      }
    }
    .active1 {
      color: #00ce6d;
    }
    .active2 {
      color: #ff4949;
    }
  }

  .dialog-block {
    // 弹框样式
    /deep/.el-dialog {
      width: 580px;
      height: 310px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      margin: 0 !important;
      .el-dialog__header {
        width: 100%;
        height: 58px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 1px solid #e9ecef;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
          font-size: 16px;
          color: #414658;
        }
      }

      .el-dialog__body {
        border-bottom: 1px solid #e9ecef;
        padding: 41px 60px 16px 60px !important;
        .pass-input {
          .el-form {
            width: 340px;
            .el-form-item {
              width: 340px;
              height: 40px;
              .el-form-item__content {
                .el-input {
                  width: 340px;
                  height: 40px;
                  input {
                    width: 340px;
                    height: 40px;
                  }
                }
              }
            }
          }
        }
        .form-footer {
          width: 174px;
          height: 72px;
          position: absolute;
          bottom: 0;
          right: 0;
          .el-form-item {
            padding: 0 !important;
            height: 100%;
            .el-form-item__content {
              height: 100%;
              line-height: 72px;
              margin: 0 !important;
            }
          }

          .qxcolor {
            color: #6a7076;
            border: 1px solid #d5dbc6;
          }
          .qdcolor {
            background-color: #2890ff;
          }
          .qdcolor {
            background-color: #2890ff;
          }
          .qdcolor:hover {
            opacity: 0.8;
          }
          .qxcolor {
            color: #6a7076;
            border: 1px solid #d5dbe8;
            // margin-left: 14px;
          }
          .qxcolor:hover {
            color: #2890ff;
            border: 1px solid #2890ff;
            background-color: #fff;
          }
        }
      }
    }
    /deep/.el-form-item__label {
      font-weight: normal;
      color: #414658;
    }
  }
}
</style>