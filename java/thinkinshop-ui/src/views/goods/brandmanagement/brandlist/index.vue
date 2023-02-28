<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入品牌名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/goods/brandmanagement/addbrand')">新增品牌</el-button>
    </div>
    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..."  v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		    :height="tableHeight">
        <el-table-column prop="brand_id" label="ID" width="88">
        </el-table-column>
        <el-table-column prop="brand_pic" class="brand_pic" label="品牌图片" width="328">
          <template slot-scope="scope">
              <img :src="scope.row.brand_pic" alt="">
          </template>
        </el-table-column>
         <el-table-column prop="brand_name" label="品牌名称" width="328">
        </el-table-column>
         <el-table-column prop="categories" label="所属分类" width="328">
           <template slot-scope="scope">
            <span>{{ getClass(scope.row.categories) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="brand_time" label="添加时间" width="328">
          <template slot-scope="scope">
            <span>{{ scope.row.brand_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button class="laiketui laiketui-zhiding" @click="placedTop(scope.row)" v-if="scope.$index !== 0 || currpage !== 1">置顶</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
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
import { getBrandInfo, addBrand, brandByTop, delBrand } from '@/api/goods/brandManagement'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'brandlist',
  mixins: [mixinstest],
  data() {
    return {
        tableData: [],
        loading: true,
        inputInfo: {
          name: '',
        },

        // 弹框数据
        dialogVisible: false,

        tableHeight: null
    }
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.getBrandInfos()
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
      console.log(this.$refs.tableFather.clientHeight);
		},
    // 获取品牌信息
    async getBrandInfos() {
      const res = await getBrandInfo({
        api: 'admin.goods.getBrandInfo',
        storeType: 8,
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        brandName: this.inputInfo.name
      })
      console.log(res);
      this.total = res.data.data.total
      this.tableData = res.data.data.brandInfoList
      this.loading = false
      if(res.data.data.total < 10) {
        this.current_num = this.total
      }
      if(this.total < this.current_num) {
        this.current_num = this.total
      }
    },

    // 重置
    reset() {
      this.inputInfo.name = ''
    },

    // 查询
    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getBrandInfos().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    getClass(value) {
      return value.join(',')
    },

    //选择一页多少条
    handleSizeChange(e){
      this.loading = true
      console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.getBrandInfos().then(() => {
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
      this.getBrandInfos().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
      
	  },

    // 置顶
    placedTop(value) {
      brandByTop({
        api: 'admin.goods.brandByTop',
        brandId: value.brand_id
      }).then(res => {
        console.log(res);
        this.getBrandInfos()
        this.$message({
          type: 'success',
          message: '置顶成功!',
          offset: 100
        });
      })
    },

    // 编辑
    Edit(value) {
      this.$router.push({
        name: 'editorbrand',
        params: value,
        query: {
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    // 删除
    Delete(value) {
      console.log(value);
      this.$confirm('确定删除此商品品牌吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delBrand({
          api: 'admin.goods.delBrand',
            brandId: value.brand_id
        }).then(res => {
          if(res.data.code == '200') {
            console.log(res);
            this.getBrandInfos()
            this.$message({
              type: 'success',
              message: '删除成功!'
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

    // 弹框方法
    dialogShow() {
      this.dialogVisible = true
    },
    
    handleClose(done) {
      this.dialogVisible = false
    },

    async exportPage() {
      await exports({
        api: 'admin.goods.getBrandInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        exportType: 1,
      },'pagegoods')
    },

    async exportAll() {
      console.log(this.total);
      await exports({
        api: 'admin.goods.getBrandInfo',
        pageNo: 1,
        pageSize: this.total,
        exportType: 1
      },'allgoods')
    },

    async exportQuery() {
      await exports({
        api: 'admin.goods.getBrandInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.total,
        exportType: 1,
        brandName: this.inputInfo.name
      },'querygoods')
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
        display: flex;
        margin-right: 10px;
        .Search-input{
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
          background-color: #fff;
          border: 1px solid #d5dbe8;
        }
        .fontColor:hover {
            background-color: #fff;
            color: #2890FF;
            border: 1px solid #2890FF;
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
      span {
        font-size: 14px;
      }
    }
  }

  .merchants-list {
    flex: 1;
    // height: 605px;
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
          .laiketui-zhiding:before {
            margin-right: 5px;
          }
        }
      }
    }
  }
}
</style>