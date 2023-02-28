<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入导览名称/菜单ID/菜单名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button type="primary"  @click="$router.push('/mall/functionnavigation/editornav')">
        <img src="@/assets/imgs/fhsj.png" alt="">
        返回上级
      </el-button>
    </div>
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column prop="id_id" label="序号" width="86">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="guide_name" label="导览名称" width="237">
        </el-table-column>
        <el-table-column prop="id_id" label="菜单ID" width="237">
        </el-table-column>
        <el-table-column prop="title" label="菜单名称" width="237">
        </el-table-column>
        <el-table-column prop="image" class="image" label="导览图标" width="237">
          <template slot-scope="scope">
              <img :src="scope.row.image" alt="">
          </template>
        </el-table-column>
        <el-table-column prop="" label="导览简介" width="237">
        </el-table-column>
        <el-table-column label="是否显示" width="237">
           <template slot-scope="scope">
            <el-switch v-model="scope.row.is_display" @change="switchs(scope.row)" :active-value="0" :inactive-value="1" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button @click="moveUp(scope.$index)">
                  <i v-if="scope.$index !== 0" class="el-icon-top"></i>
                  <i v-else class="el-icon-bottom"></i>
                  {{ scope.$index === 0 ? '下移' : '上移'}}
                </el-button>
                <el-button class="laiketui laiketui-zhiding" @click="placedTop(scope.row)"  v-if="scope.$index !== 0">置顶</el-button>
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
  name: 'viewnav',
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
      
    }
  },

  created() {
    this.functionLists(this.$route.query.id)
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
    async functionLists(id) {
      const res = await functionList({
        api: 'admin.overview.functionList',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        sid: id,
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
      this.functionLists(this.$route.query.id).then(() => {
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
        this.functionLists(this.$route.query.id)
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

    moveUp(value) {
        if(value !== 0) {
            move({
              api: 'admin.overview.move',
              id2: this.tableData[value - 1].id,
              id: this.tableData[value].id
            }).then(res => {
              this.functionLists(this.$route.query.id)
              console.log(res);
              this.$message({
                type: 'success',
                message: '操作成功!',
                offset: 100
              });
            })
        } else {
            move({
              api: 'admin.overview.move',
              id2: this.tableData[value + 1].id,
              id: this.tableData[value].id
            }).then(res => {
              this.functionLists(this.$route.query.id)
              console.log(res);
              this.$message({
                type: 'success',
                message: '操作成功!',
                offset: 100
              });
            })
        }
    },

    placedTop(value) {
      sortTop({
        api: 'admin.overview.sortTop',
        id: value.id,
        sid: value.s_id
      }).then(res => {
        console.log(res);
        this.functionLists(this.$route.query.id)
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
      this.functionLists(this.$route.query.id).then(() => {
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
      this.functionLists(this.$route.query.id).then(() => {
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

  /deep/.jump-list {
    width: 100%;
    display: flex;
    align-items: center;
    margin-bottom: 16px;
    button {
      width: 120px;
      height: 40px;
      background: #28B6FF;
      border-radius: 4px;
      padding: 0;
      img {
        position: relative;
        top: 1px;
      }
      span {
        font-size: 14px;
      }
    }
  }

  .menu-list {
    // height: 597px;
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
      .cell {
          img {
            width: 60px;
            height: 60px;
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