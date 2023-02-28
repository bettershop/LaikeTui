<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="菜单列表" @click.native.prevent="$router.push('/Platform/permissions/menulist')"></el-radio-button>
        <el-radio-button label="角色列表" @click.native.prevent="$router.push('/Platform/permissions/rolelist')"></el-radio-button>
      </el-radio-group>
    </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/Platform/permissions/addrole')">添加角色</el-button>
    </div>
    <div class="role-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
			:height="tableHeight">
        <el-table-column prop="id" label="序号" width="88">
        </el-table-column>
        <el-table-column prop="name" label="角色" width="347">
        </el-table-column>
        <el-table-column prop="role_describe" label="描述" width="345">
        </el-table-column>
        <el-table-column prop="level" label="绑定商户" width="345">
          <template slot-scope="scope">
            <p v-for="(item,index) in scope.row.bindAdminList" :key="index">{{ item.name }}</p>
          </template>
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="345">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="180">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="view(scope.row)">查看</el-button>
                <el-button icon="el-icon-edit-outline" @click="permissionmodify(scope.row)">权限修改</el-button>   
              </div>
              <div class="OP-button-bottom">
                <el-button class="laiketui laiketui-bangding" @click="dialogShow(scope.row)">绑定商户</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
              </div>
            </div>
				  </template>
        </el-table-column>
		  </el-table>
      <div class="pageBox" ref="pageBox" v-if="showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页" @size-change="handleSizeChange"
          :page-sizes="pagesizes" :current-page="pagination.page" @current-change="handleCurrentChange" :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>
    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        title="绑定商户"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <div class="Search">
          <el-input v-model="merchants" size="medium" @keyup.enter.native="demand" placeholder="请输入商户名称"></el-input>
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
	      </div>
        <div class="merchant-list" ref="tableBindFather">
          <el-table v-loading="loading" :data="merchantLists" ref="table" class="merchant-table" style="width: 100%"
          :height="tableBindHeight">
            <el-table-column prop="name" label="商户名称">
              <template slot-scope="scope">
                <el-checkbox :style="{'--fill-color':'#2890ff'}" @change="change3(scope.row.id)"></el-checkbox>
                <span>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="tel" label="手机号">
            </el-table-column>
		      </el-table>
        </div>
        <div class="bind-merchant">
          <h3>已绑定商户</h3>
          <div class="merchant-block">
            <div class="item" v-for="(item,index) in merchantList" :key="index">
              <el-checkbox @change="change4(item.id)" checked :style="{'--fill-color':'#2890ff'}"></el-checkbox>
              <span>{{ item.name }}</span>
            </div>
          </div>
        </div>
        <div slot="footer" class="form-footer">
            <el-button class="fontColor" @click="cancel">取 消</el-button>
            <el-button class="bgColor" type="primary" @click="determine">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getRoleListInfo, delUserRoleMenu, getBindListInfo, bindRole, verificationBind } from '@/api/Platform/permissions'
import { mixinstest } from '@/mixins/index'
export default {
    name: 'rolelist',
    mixins: [mixinstest],
    data() {
      return {
        radio1:'角色列表',

        tableData: [],
        loading: true,

        // 弹框数据
        dialogVisible: false,
        merchants: '',
        merchantLists: [],
        merchantList: [],
        id: null,
        idList: [],
        // table高度
        tableHeight: null,
        tableBindHeight: null,

        bindId: [],
      }
    },

    created() {
      this.getRoleListInfos()
    },

     mounted() {
      this.$nextTick(function() {
        this.getHeight()
      })
      window.addEventListener('resize',this.getHeight(),false)

    },

    methods: {
      getHeight(){
        this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
      },
      async getRoleListInfos() {
        const res = await getRoleListInfo({
          api: 'saas.role.getRoleListInfo',
          pageNo: this.dictionaryNum,
          pageSize: this.pageSize,
        })
        this.total = res.data.data.total
        this.tableData = res.data.data.list
        this.loading = false
        if (res.data.data.total < 10) {
          this.current_num = this.total;
        }
        let bindList = []
        for(var i = 0; i<this.tableData.length; i++) {
          bindList.push(...this.tableData[i].bindAdminList)
        }
      },

      // 获取已绑定商户
      async getBindListInfoss() {
        const res = await getBindListInfo({
          api: 'saas.role.getBindListInfo',
          roleId: this.id,
          isBind: 0
        })
        console.log(res);
        this.merchantLists = res.data.data.bindAdminList
      },

      // 获取未绑定商户
      async getBindListInfos() {
        const res = await getBindListInfo({
          api: 'saas.role.getBindListInfo',
          roleId: this.id,
          isBind: 1
        })
        console.log(res);
        this.merchantList = res.data.data.bindAdminList
      },

      view(value) {
        this.$router.push({ name: 'viewrole', params: value })
      },

      permissionmodify(value) {
        this.$router.push({ name: 'rolepermission', params: value })
      },

      // binding(value) {
      //   console.log(value);
      // },

      Delete(value) {
        this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delUserRoleMenu({
            api: 'saas.role.delUserRoleMenu',
            id: value.id
          }).then(res => {
            if(res.data.code == '200') {
              console.log(res);
              this.getRoleListInfos()
              this.$message({
                type: 'success',
                message: '删除成功!',
                offset: 100
              });
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },

      //选择一页多少条
      handleSizeChange(e){
        this.loading = true
        console.log(e);
        // this.current_num = e
        this.pageSize = e
        this.getRoleListInfos().then(() => {
          this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
          this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
          this.loading = false
        })
      },
      //点击上一页，下一页
      handleCurrentChange(e){
        this.loading = true
        this.dictionaryNum = e
        this.currpage = ((e - 1) * this.pageSize) + 1
        this.getRoleListInfos().then(() => {
          this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
          this.loading = false
        })
        
      },

      // 弹框方法
      dialogShow(value) {
        this.dialogVisible = true
        console.log(value);
        this.id = value.id
        this.getBindListInfoss()
        this.getBindListInfos()
        this.$nextTick(function() {
          this.tableBindHeight = this.$refs.tableBindFather.clientHeight
        })
        
      },

      change(value) {
        this.idList.push(value)
      },

      change3(value) {
        console.log(value);
        if(this.idList.some(item => {
          return item == value
        })) {
          for(var i = 0; i < this.idList.length; i++) {
            if(this.idList[i] == value) {
              this.idList.splice(i, 1);
              this.bindId.splice(i, 1);
              break;
            }
          }
        } else {
          this.idList.push(value)
          this.bindId.push(value)
        }
      },

      change4(value) {
        if(this.idList.some(item => {
          return item == value
        })) {
          for(var i = 0; i < this.idList.length; i++) {
            if(this.idList[i] == value) {
              this.idList.splice(i, 1);
              break;
            }
          }
        } else {
          this.idList.push(value)
        }
      },

      cancel() {
        this.merchantLists = [],
        this.merchantList = [],
        this.idList = []
        this.dialogVisible = false
      },

      determine() {
        if(this.bindId.length !== 0) {
          verificationBind({
            api: 'saas.role.verificationBind',
            adminIds: this.bindId.join(',')
          }).then(res => {
            console.log(res);
            if(res.data.data) {
              this.$confirm('商户已被绑定，继续执行绑定操作将修改商户绑定角色，是否确认操作？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              })
              .then(() => {
                bindRole({
                  api: 'saas.role.bindRole',
                  roleId: this.id,
                  adminIds: this.idList.join()
                }).then(res => {
                  if(res.data.code == '200') {
                    console.log(res);
                    this.merchantLists = [],
                    this.merchantList = [],
                    this.idList = []
                    this.dialogVisible = false
                    this.getRoleListInfos()
                  }
                })
              })
              .catch(() => {
                  this.$message({
                    type: 'info',
                    message: '已取消'
                  });          
              })
            } else {
              bindRole({
                api: 'saas.role.bindRole',
                roleId: this.id,
                adminIds: this.idList.join()
              }).then(res => {
                if(res.data.code == '200') {
                  console.log(res);
                  this.merchantLists = [],
                  this.merchantList = [],
                  this.idList = []
                  this.dialogVisible = false
                  this.getRoleListInfos()
                }
              })
            }
          })
        } else {
          bindRole({
            api: 'saas.role.bindRole',
            roleId: this.id,
            adminIds: this.idList.join()
          }).then(res => {
            if(res.data.code == '200') {
              console.log(res);
              this.merchantLists = [],
              this.merchantList = [],
              this.idList = []
              this.dialogVisible = false
              this.getRoleListInfos()
            }
          })
        }
      },

      reset() {
        this.merchants = ''
      },

      demand() {
        getBindListInfo({
          api: 'saas.role.getBindListInfo',
          name: this.merchants,
          roleId: this.id,
          isBind: 0
        }).then(res => {
          console.log(res);
          this.merchantLists = res.data.data.bindAdminList
        })
      },
      
      handleClose(done) {
        this.dialogVisible = false
      },
    }
}
</script>

<style scoped lang="less">
.container {
  display: flex;
  flex-direction: column;
  /deep/.btn-nav {
    span {
      // width: 112px;
      height: 42px;
      font-size: 16px;
      border: none;
    }
  }
  /deep/.jump-list {
    width: 100%;
    display: flex;
    align-items: center;
    margin: 16px 0;
    .laiketui-add:before {
      font-size: 14px;
      margin-right: 8px;
    }
    button {
      width: 120px;
      height: 40px;
      background: #28B6FF;
      border-radius: 4px;
      padding: 0;
      border: none;
      span {
        font-size: 14px;
      }
    }
  }

  .role-list {
    // height: 623px;
    flex: 1;
    background: #FFFFFF;
    border-radius: 4px;
    /deep/.el-table__header {
      thead {
        tr {
          th{
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
          td{
            height: 92px;
            text-align: center;
            font-size: 14px;
            color: #414658;
            font-weight: 400;
          }
        }
      }
    }

    /deep/.el-table{
      .OP-button{
        .OP-button-top{
          margin-bottom: 8px;
          display: flex;
          justify-content: start;
          button {
            &:not(:first-child) {
              margin-left: 8px !important;
            }
          }
        }
        .OP-button-bottom {
          display: flex;
          justify-content: start;
          .laiketui-add:before {
            margin-right: 5px;
            font-size: 10px;
          }
          .laiketui-bangding:before {
            margin-right: 5px;
          }
        }
      }
    }
  }

  .dialog-block {
    // 弹框样式
    /deep/.el-dialog {
      width: 920px;
      height: 720px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%,-50%);
      margin: 0 !important;
      .el-dialog__header {
        width: 100%;
        height: 62px;
        line-height: 58px;
        font-size: 16px;
        margin-left: 19px;
        font-weight: bold;
        border-bottom: 1px solid #E9ECEF;
        box-sizing: border-box;
        margin: 0;
        padding: 0 0 0 19px;
        span {
          color: #414658;
          font-size: 16px;
        }
        .el-dialog__headerbtn {
          font-size: 18px;
          top: 0 !important;
        }
        .el-dialog__title {
          font-weight: normal;
        }
        
      }

      .el-dialog__body {
        width: 100%;
        height: 587px;
        padding: 40px 20px;
        border-bottom: 1px solid #E9ECEF;
        .Search{
          display: flex;
          align-items: center;
          justify-content: start;
          margin-bottom: 20px;
          .el-input {
            width: 280px;
            height: 40px;
            margin-right: 10px;
            .el-input__inner {
              width: 280px;
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
          
        }

        .merchant-list {
          width: 880px;
          height: 292px;
          border: 1px solid #E9ECEF;
          .el-table__header {
            thead {
              height: 50px;
              border-bottom: 1px solid #E9ECEF;
              tr {
                th {
                  height: 50px;
                  padding-left: 40px;
                }
              }
            }
          }
          .el-table__body {
            td {
              height: 60px !important;
            }
          }

          .el-checkbox {
            margin: 0 8px 0 21px;
          }

          .cell {
            color: #414658;
          }
          
        }

        .bind-merchant {
          margin-top: 30px;
          color: #414658;
          h3 {
            font-size: 14px;
          }
          .merchant-block {
            margin-top: 15px;
            width: 880px;
            height: 98px;
            background: #F4F7F9;
            border-radius: 4px;
            padding: 0 22px;
            display: flex;
            flex-wrap: wrap;
            overflow: hidden;
            overflow-y: auto;
            .item {
              margin: 20px 128px 0 0;
              .el-checkbox {
                margin-right: 10px;
              }
            }
          }
        }

        .el-checkbox__inner {
          border: 1px solid #b2bcd1;
        }
        .el-checkbox__label {
          color: var(--fill-color);
        }

        .el-checkbox__input.is-checked .el-checkbox__inner,
        .el-checkbox__input.is-indeterminate .el-checkbox__inner {
          background-color: var(--fill-color);
          border-color: var(--fill-color)
        }

        .el-checkbox__input.is-focus .el-checkbox__inner,
          .el-checkbox__inner:hover {
            border-color: var(--fill-color);
          }
      }

      .el-dialog__footer {
        padding: 0 20px 0 0;
        height: 72px;
        line-height: 72px;
        .form-footer {
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
            background-color: #fff;
            color: #2890FF;
            border: 1px solid #2890FF;
          }

          .el-button+.el-button {
            margin-left: 14px;
          }
        }
      }
    }
  }
}
</style>