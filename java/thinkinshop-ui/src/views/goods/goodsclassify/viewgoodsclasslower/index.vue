<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="分类名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button type="primary"  @click="superiors">
        <img src="@/assets/imgs/fhsj.png" alt="">
        返回上级
      </el-button>
    </div>
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column prop="cid" label="分类ID" width="88">
        </el-table-column>
        <el-table-column prop="title" label="分类图片" width="328">
          <template slot-scope="scope">
              <img :src="scope.row.img" alt="">
            </template>
        </el-table-column>
        <el-table-column prop="pname" label="分类名称" width="328">
        </el-table-column>
        <el-table-column prop="level" label="分类级别" width="328">
          <template slot-scope="scope">
            <span>{{ getLevel(scope.row.level) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="328">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button class="laiketui laiketui-zhiding" @click="placedTop(scope.row)"  v-if="scope.$index !== 0">置顶</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
              </div>
              <div class="OP-button-bottom" v-if="scope.row.level !== 4">
                <el-button icon="el-icon-view" @click="viewlower(scope.row)">查看下级</el-button>
                <el-button class="laiketui laiketui-add" @click="addmenu(scope.row)">添加子类</el-button>
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
import { getClassInfo, classSortTop, delClass } from '@/api/goods/goodsClass'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'viewgoodsclasslower',
  mixins: [mixinstest],
  data() {
    return {
      inputInfo: {
        name: '',
      },

      tableData: [],
      loading: true,
      
      level: null,
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
    this.getClassInfos(this.$route.query.id)
    console.log(this.$store.state.superior.superiorList);
  },

  mounted() {
    this.$nextTick(function() {
      this.getHeight()
    })
    window.addEventListener('resize',this.getHeight(),false)
  },


  computed: {
    // superior() {
    //   return this.$store.state.superior.superiorList
    // },
    goodsList() {
      return this.$store.state.superior.goodsSuperiorList
    },
  },

  watch: {
    $route:{
      handler:function() {
        this.getClassInfos(this.$route.query.id)
      },
      deep: true
    }
  },

  methods: {
    // 获取table高度
    getHeight(){
			this.tableHeight = this.$refs.tableFather.clientHeight - this.$refs.pageBox.clientHeight
      // debugger
      console.log(this.$refs.tableFather.clientHeight);
		},

    async getClassInfos(id) {
      const res = await getClassInfo({
        api: 'admin.goods.getClassInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        classId: id,
        className: this.inputInfo.name,
        type: 1,
        level: this.level - 1
      })
      console.log(res);
      this.level = res.data.data.classInfo.length === 0 ? this.level + 1 : res.data.data.classInfo[0].level + 1
      this.total = res.data.data.total
      if(this.total < 10) {
        this.current_num = this.total
      }
      this.tableData = res.data.data.classInfo
      this.loading = false
    },


    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getClassInfos(this.$route.query.id).then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    superiors() {
        if(this.level === 2 || this.level === 1) {
          this.$router.push('/goods/goodsclassify/goodsclassifylist')
          this.$store.commit('EMPTY_GOODS')
        } else {
          let ids = this.$store.state.superior.goodsSuperiorList.pop()
          this.$router.push({path:'/goods/goodsclassify/viewgoodsclasslower',query: { id: ids }})
        }
    },

    viewlower(value) {
      this.$store.commit('ADD_GOODS',this.$route.query.id)
      this.$router.push({path:'/goods/goodsclassify/viewgoodsclasslower',query: {id: value.cid}})
    },

    Edit(value) {
      if(this.level === 2) {
        this.$router.push({
          name: 'editorgoodsclass1',
          params: value,
          query: {
            id: value.cid,
            classlevel: this.level,
            dictionaryNum: this.dictionaryNum,
            pageSize: this.pageSize
          }
        })
      } else if (this.level === 3) {
        this.$router.push({
          name: 'editorgoodsclass1',
          params: value,
          query: {
            id: value.cid,
            classlevel: this.level
          }
        })
      } else if (this.level === 4) {
        this.$router.push({
          name: 'editorgoodsclass1',
          params: value,
          query: {
            id: value.cid,
            classlevel: this.level
          }
        })
      } else if (this.level === 5) {
        this.$router.push({
          name: 'editorgoodsclass1',
          params: value,
          query: {
            id: value.cid,
            classlevel: this.level
          }
        })
      }
      
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
            this.getClassInfos(this.$route.query.id)
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

    addmenu(value) {
        console.log(value);
        if(this.level === 2) {
            this.$router.push({
              name: 'addgoodsclass1',
              query: {
                id: value.cid,
                classlevel: this.level + 1
              }
            })
        } else if (this.level === 3) {
            this.$router.push({
              name: 'addgoodsclass1',
              query: {
                id: value.cid,
                classlevel: this.level + 1
              }
            })
        } else if (this.level === 4) {
            this.$router.push({
              name: 'addgoodsclass1',
              query: {
                id: value.cid,
                classlevel: this.level + 1
              }
            })
        }
    },

    placedTop(value) {
      classSortTop({
        api: 'admin.goods.classSortTop',
        classId: value.cid
      }).then(res => {
        console.log(res);
        this.getClassInfos(this.$route.query.id)
        this.$message({
          type: 'success',
          message: '置顶成功!',
          offset: 100
        });
      })
    },

    getLevel(value) {
      if(value === 1) {
        return '二级'
      } else if(value === 2) {
        return '三级'
      } else if(value === 3) {
        return '四级'
      } else {
        return '五级'
      }
    },

    //选择一页多少条
    handleSizeChange(e){
      this.loading = true
      console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.getClassInfos(this.$route.query.id).then(() => {
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
      this.getClassInfos(this.$route.query.id).then(() => {
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
      border: none;
      &:hover {
        opacity: 0.8;
      }
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