<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入菜单ID/菜单名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column label="序号" width="88">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="title" label="导览名称" width="332">
        </el-table-column>
        <el-table-column prop="id_id" label="菜单ID" width="330">
        </el-table-column>
        <el-table-column prop="guide_name" label="菜单名称" width="330">
        </el-table-column>
        <el-table-column label="是否显示" width="330">
           <template slot-scope="scope">
            <el-switch v-model="scope.row.is_display" @change="switchs(scope.row)" :active-value="0" :inactive-value="1" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="240">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="viewlower(scope.row)">查看下级</el-button>
                <el-button @click="moveUp(scope.$index)" v-if="scope.row.is_display === 1">
                  <i v-if="scope.$index !== 0" class="el-icon-top"></i>
                  <i v-else class="el-icon-bottom"></i>
                  {{ scope.$index === 0 ? '下移' : '上移'}}
                </el-button>
                <el-button icon="el-icon-edit-outline" @click="placedTop(scope.row)"  v-if="scope.$index !== 0 && scope.row.is_display === 1">置顶</el-button>
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
import { functionList, isDisplaySwitch, move, sortTop } from '@/api/mall/functionNavigation'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'editornav',
  mixins: [mixinstest],
  data() {
    return {
      inputInfo: {
        name: '',
      },

      tableData: [],
      loading: true,
      
      // table高度
      tableHeight: null,
      
      level: null,
    }
  },

  created() {
    this.functionLists()
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
    async functionLists() {
      const res = await functionList({
        api: 'admin.overview.functionList',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        name: this.inputInfo.name,
      })
      console.log(res);
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false
    },


    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.functionLists().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    switchs(value) {
      isDisplaySwitch({
        api: 'admin.overview.isDisplaySwitch',
        id: value.id
      }).then(res => {
        this.functionLists()
        console.log(res);
        if(value.is_display === 1) {
          this.$message({
            message: '开启成功！',
            type: 'success',
            offset: 100
          })
        } else {
          this.$message({
            message: '关闭成功！',
            type: 'success',
            offset: 100
          })
        }
      })
    },

    viewlower(value) {
      this.$router.push({path:'/mall/functionnavigation/viewnav',query: {id: value.id}})
    },

    Edit(value) {
      // console.log(value);
      if(this.level === 2) {
        this.$router.push({
            name: 'editormenulevel2',
            params: value
        })
      } else if (this.level === 3) {
        this.$router.push({
            name: 'editormenulevel3',
            params: value
        })
      }
      
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
          console.log(res);
          this.$message({
            type: 'success',
            message: '删除成功!',
            offset: 100
          });
        })
        this.getMenuLeveInfos(this.$route.query.id)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },

    moveUp(value) {
      console.dir(value)
        if(value !== 0) {
            move({
              api: 'admin.overview.move',
              id2: this.tableData[value - 1].id,
              id: this.tableData[value].id
            }).then(res => {
              console.log(res);
              if(res.data.code == 200){
                  this.$message({
                  type: 'success',
                  message: '操作成功!',
                  offset: 100
                });
                setTimeout(() => {
                  this.functionLists()
                }, 100);
              }
            })
        } else {
            move({
              api: 'admin.overview.move',
              id2: this.tableData[value + 1].id,
              id: this.tableData[value].id
            }).then(res => {
              console.log(res);
              if(res.data.code == 200){
                  this.$message({
                  type: 'success',
                  message: '操作成功!',
                  offset: 100
                });
                setTimeout(() => {
                  this.functionLists()
                }, 100);
              }
            })
        }
    },

    placedTop(value) {
      console.log(value);
      sortTop({
        api: 'admin.overview.sortTop',
        id: value.id
      }).then(res => {
        console.log(res);
        this.functionLists()
        this.$message({
          type: 'success',
          message: '置顶成功!',
          offset: 100
        });
      })
    },

  //选择一页多少条
	handleSizeChange(e){
    this.loading = true
	  console.log(e);
    // this.current_num = e
    this.pageSize = e
    this.functionLists().then(() => {
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
    this.functionLists().then(() => {
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
      }
    }
    
  }

  .menu-list {
    // height: 653px;
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
        }
      }
    }
  }
}
</style>