<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="菜单列表" @click.native.prevent="$router.push('/Platform/permissions/menulist')"></el-radio-button>
        <el-radio-button label="角色列表" @click.native.prevent="$router.push('/Platform/permissions/rolelist')"></el-radio-button>
      </el-radio-group>
    </div>
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-select class="select-input" v-model="inputInfo.code" placeholder="菜单类型">
            <el-option v-for="item in Dictionary" :key="item.value" :label="item.label" :value="item.label">
            </el-option>
          </el-select>
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入菜单ID/菜单名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/Platform/permissions/addmenulevel')">添加菜单</el-button>
    </div>
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
			:height="tableHeight">
        <el-table-column prop="id_id" label="菜单ID" width="88">
        </el-table-column>
        <el-table-column prop="title" label="菜单名称" width="329">
        </el-table-column>
        <el-table-column prop="guide_name" label="菜单类型" width="329">
          <template slot-scope="scope">
            <span>{{ scope.row.type === 0 ? '控制台' : '商城' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="level" label="菜单等级" width="329">
        </el-table-column>
        <el-table-column prop="add_time" label="添加时间" width="329">
          <template slot-scope="scope">
            <span>{{ scope.row.add_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="246">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="viewlower(scope.row)">查看下级</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
              </div>
              <div class="OP-button-bottom">
                <el-button class="laiketui laiketui-add" @click="addmenu(scope.row)">添加菜单</el-button>
                <el-button @click="moveUp(scope.$index)">
                  <i v-if="scope.$index !== 0 || dictionaryNum !== 1" class="el-icon-top"></i>
                  <i v-else class="el-icon-bottom"></i>
                  {{ scope.$index === 0 && dictionaryNum == 1 ? '下移' : '上移'}}
                </el-button>
                <el-button class="laiketui laiketui-zhiding" @click="placedTop(scope.row)" v-if="scope.$index !== 0 || dictionaryNum !==1">置顶</el-button>
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
  </div>
</template>

<script>
import { getMenuLeveInfo, moveTopMenuSort, delMenu, moveMenuSort } from '@/api/Platform/permissions'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'menulist',
  mixins: [mixinstest],
  data() {
    return {
      radio1:'菜单列表',
      inputInfo: {
        code: '',
        name: '',
      },
      type: '',
      Dictionary: [
        { 
          value: '选项二',
          label: '控制台'
        },
        { 
          value: '选项一',
          label: '商城'
        }
      ],

      tableData: [],
      loading: true,

      // table高度
      tableHeight: null,

      ids: null
      
    }
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.getMenuLeveInfos()
    this.$store.commit('EMPTY_SUPERIOR')
  },

  mounted() {
    this.$nextTick(function() {
      this.getHeight()
    })
    window.addEventListener('resize',this.getHeight(),false)

  },

  watch: {
    'inputInfo.code':{
      handler:function() {
        if(this.inputInfo.code === '商城') {
          this.type = 1
        } else if(this.inputInfo.code === '控制台') {
          this.type = 0
        } else {
          this.type = ''
        }
      }
    }
  },

  methods: {
    getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
		},

    async getMenuLeveInfos() {
      const res = await getMenuLeveInfo({
        api: 'saas.role.getMenuLeveInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        name: this.inputInfo.name,
        type: this.type
      })
      console.log(res.data);
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false
      if (res.data.data.total < 10) {
        this.current_num = this.total;
      }
    },

    reset() {
      this.inputInfo.code = ''
      this.inputInfo.name = ''
    },

    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getMenuLeveInfos().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    viewlower(value) {
      console.log(value.id);
      this.$router.push({path:'/Platform/permissions/viewmenu',query: {id: value.id}})
    },

    Edit(value) {
      console.log(value);
      this.$router.push({
        name: 'editormenulevel',
        params: value,
        query: {
          menulevel: 1,
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    addmenu(value) {
      this.$router.push({
        name: 'addmenulevel',
        params: value,
        query: {
          menulevel: 2
        }
      })
    },

    Delete(value) {
      this.$confirm('此操作将永久删除该菜单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delMenu({
          api: 'saas.role.delMenu',
          menuId: value.id
        }).then(res => {
          if(res.data.code == '200') {
            console.log(res);
            this.getMenuLeveInfos()
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

    moveUp(value) {
      if(value == 0 && this.dictionaryNum == 1) {
          moveMenuSort({
            api: 'saas.role.moveMenuSort',
            downId: this.tableData[value + 1].id,
            upId: this.tableData[value].id
          }).then(res => {
            if(res.data.code == '200') {
              this.getMenuLeveInfos(this.$route.query.id)
              console.log(res);
              this.$message({
                type: 'success',
                message: '操作成功!',
                offset: 100
              });
            }
          })
      } else {
        moveMenuSort({
          api: 'saas.role.moveMenuSort',
          downId: this.tableData[value].id,
          upId: value == 0 ? this.ids : this.tableData[value - 1].id
        }).then(res => {
          if(res.data.code == '200') {
            this.getMenuLeveInfos(this.$route.query.id)
            console.log(res);
            this.$message({
              type: 'success',
              message: '操作成功!',
              offset: 100
            });
          }
        })
      }
    },

    placedTop(value) {
      console.log(value);
      moveTopMenuSort({
        api: 'saas.role.moveTopMenuSort',
        id: value.id,
        sid: 0
      }).then(res => {
        if(res.data.code == '200') {
          console.log(res);
          this.getMenuLeveInfos()
          this.$message({
            type: 'success',
            message: '置顶成功!',
            offset: 100
          });
        }
      })
    },

    //选择一页多少条
		handleSizeChange(e){
      this.loading = true
			console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.getMenuLeveInfos().then(() => {
        this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
        this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
        this.loading = false
      })
		},
		//点击上一页，下一页
		handleCurrentChange(e){
      if(e > 1) {
        this.ids = this.tableData[this.tableData.length-1].id
      }
      this.loading = true
      this.dictionaryNum = e
      this.currpage = ((e - 1) * this.pageSize) + 1
      this.getMenuLeveInfos().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
      
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

  /deep/.Search{
    display: flex;
    align-items: center;
    background: #ffffff;
    border-radius: 4px;
    padding: 10px;
    margin: 16px 0;
    .Search-condition{
      display: flex;
      align-items: center;
      .query-input {
        .Search-input{
          width: 280px;
          margin: 0 10px;
          
        }
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
          color: #2890FF;
          border: 1px solid #2890FF;
          background-color: #fff;
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
      background: #28B6FF;
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

  .menu-list {
    // height: 537px;
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
          .laiketui-zhiding:before {
            margin-right: 5px;
            color: #888f9e;
            font-weight: 600;
          }
        }
      }
    }

  }
}
</style>