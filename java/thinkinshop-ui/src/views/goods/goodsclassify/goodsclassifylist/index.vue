<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入分类名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="addClasss">新增分类</el-button>
    </div>
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column prop="cid" label="分类ID" width="88">
        </el-table-column>
        <el-table-column prop="img" label="分类图片" width="329">
            <template slot-scope="scope">
              <img :src="scope.row.img" alt="">
            </template>
        </el-table-column>
        <el-table-column prop="pname" label="分类名称" width="329">
        </el-table-column>
        <el-table-column prop="level" label="分类级别" width="329">
            <template>
                <span>一级</span>
            </template>
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="329">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="246">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button class="laiketui laiketui-zhiding" @click="placedTop(scope.row)" v-if="scope.$index !== 0">置顶</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
              </div>
              <div class="OP-button-bottom">
                <el-button icon="el-icon-view" @click="viewlower(scope.row)">查看下级</el-button>
                <el-button class="laiketui laiketui-add" @click="addclass(scope.row)">添加子类</el-button>
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

    <div class="dialog-export">
      <!-- 弹框组件 -->
      <el-dialog
        title="导出数据"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
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
import { getClassInfo, classSortTop, delClass } from '@/api/goods/goodsClass'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'goodsclassifylist',
  mixins: [mixinstest],
  data() {
    return {
      inputInfo: {
        name: ''
      },
      type: '',

      tableData: [],
      loading: true,

      // 弹框数据
      dialogVisible: false,
      // table高度
      tableHeight: null
    }
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.getClassInfos()
  },

  mounted() {
    this.$nextTick(function() {
      this.getHeight()
    })
    window.addEventListener('resize',this.getHeight(),false)
  },

  methods: {
    // 获取table高度
    getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
      // debugger
      console.log(this.$refs.tableFather.clientHeight);
		},

    async getClassInfos() {
      const res = await getClassInfo({
        api: 'admin.goods.getClassInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        className: this.inputInfo.name,
        level: 0
      })
      console.log(res);
      this.total = res.data.data.total
      this.tableData = res.data.data.classInfo
      this.loading = false
      if(this.total < this.current_num) {
        this.current_num = this.total
      }
    },

    reset() {
        this.inputInfo.name = ''
    },

    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getClassInfos().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    derive() {},

    viewlower(value) {
      console.log(value);
      this.$router.push({path:'/goods/goodsclassify/viewgoodsclasslower',query: {id: value.cid}})
    },

    Edit(value) {
      console.log(value);
      this.$router.push({
        name: 'editorgoodsclass1',
        params: value,
        query: {
          classlevel: 1,
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    addClasss() {
      this.$router.push({
        name: 'addgoodsclass1',
        query: {
          classlevel: 1
        }
      })
    },

    addclass(value) {
      this.$router.push({
        name: 'addgoodsclass1',
        params: value,
        query: {
          id: value.cid,
          classlevel: 2
        }
      })
    },

    Delete(value) {
      this.$confirm('此操作将永久删除该类别, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delClass({
          api: 'admin.goods.delClass',
          classId: value.cid
        }).then(res => {
          if(res.data.code == '200') {
            console.log(res);
            this.getClassInfos()
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

    // moveUp(value) {
    //     if(value !== 0) {
    //         moveMenuSort({
    //           api: 'saas.role.moveMenuSort',
    //           downId: this.tableData[value - 1].id,
    //           upId: this.tableData[value].id
    //         }).then(res => {
    //           this.getMenuLeveInfos(this.$route.query.id)
    //           console.log(res);
    //           this.$message({
    //             type: 'success',
    //             message: '操作成功!',
    //             offset: 100
    //           });
    //         })
    //     } else {
    //         moveMenuSort({
    //           api: 'saas.role.moveMenuSort',
    //           downId: this.tableData[value + 1].id,
    //           upId: this.tableData[value].id
    //         }).then(res => {
    //           this.getMenuLeveInfos(this.$route.query.id)
    //           console.log(res);
    //           this.$message({
    //             type: 'success',
    //             message: '操作成功!',
    //             offset: 100
    //           });
    //         })
    //     }
    // },

    placedTop(value) {
      classSortTop({
        api: 'admin.goods.classSortTop',
        classId: value.cid
      }).then(res => {
        console.log(res);
        this.getClassInfos()
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
      this.getClassInfos().then(() => {
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
      this.getClassInfos().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
      
	  },

    // 弹框方法
    dialogShow() {
      this.dialogVisible = true
    },

    handleClose(done) {
      this.dialogVisible = false
    },

    async exportPage() {
      await exports({
        api: 'admin.goods.getClassInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        exportType: 1,
      },'ClassInfo')
    },

    async exportAll() {
      console.log(this.total);
      await exports({
        api: 'admin.goods.getClassInfo',
        pageNo: 1,
        pageSize: this.total,
        exportType: 1
      },'allClassInfo')
    },

    async exportQuery() {
      await exports({
        api: 'admin.goods.getClassInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.total,
        exportType: 1,
        className: this.inputInfo.name,
      },'queryClassInfo')
    }

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
    margin-bottom: 16px;
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
    // height: 591px;
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
          display: flex;
          justify-content: start;
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