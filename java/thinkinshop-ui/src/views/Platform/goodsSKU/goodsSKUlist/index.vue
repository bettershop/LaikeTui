<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.code" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入数据编码"></el-input>
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入数据名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button class="bgColor" type="primary" icon="el-icon-circle-plus-outline" @click="$router.push('/Platform/goodsSKU/addSKU')">添加SKU</el-button>
      <el-button class="fontColor" @click="delAll" :disabled="is_disabled" icon="el-icon-delete" >批量删除</el-button>
    </div>
    <div class="dictionary-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" ref="table" class="el-table" style="width: 100%"
			:height="tableHeight">
        <el-table-column type="selection" width="48">
        </el-table-column>
        <el-table-column prop="code" label="数据编码" width="270">
        </el-table-column>
        <el-table-column prop="name" label="数据名称" width="238">
        </el-table-column>
        <el-table-column label="是否生效" width="238">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" @change="switchs(scope.row)" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="admin_name" label="添加人" width="194">
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="452">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="220">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button :disabled="scope.row.status == 1 ? true : false" icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                <el-button :disabled="scope.row.status == 1 ? true : false" icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
              </div>
              <div class="OP-button-bottom">
                <el-button icon="el-icon-view" @click="details(scope.row)">查看详情</el-button>
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
import { getSkuInfo, setSkuSwitch, delSku } from '@/api/Platform/goodsSku'
import { exports } from '@/api/export/index'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'goodsSKUlist',
  mixins: [mixinstest],
  data() {
    return {
      tableData: [],
      loading: true,
      is_disabled: true,
      idList: [],
      inputInfo: {
        code: '',
        name: '',
      },

      // table高度
      tableHeight: null,

      // 弹框数据
      dialogVisible: false,
    }
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.getSkuInfos()
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
    async getSkuInfos() {
      const res = await getSkuInfo({
        api: 'saas.dic.getSkuInfo',
        storeType: 8,
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        dataCode: this.inputInfo.code,
        dataName: this.inputInfo.name,
        sid: 0
      })
      console.log(res);
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false
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
      this.getSkuInfos().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    // 是否生效
    switchs(value) {
      console.log(value);
      setSkuSwitch({
        api: 'saas.dic.setSkuSwitch',
        id: value.id,
      }).then(res => {
        this.getSkuInfos()  
        console.log(res);
        if(value.status === 1) {
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

    //选择一页多少条
		handleSizeChange(e){
      this.loading = true
			console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.getSkuInfos().then(() => {
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
      this.getSkuInfos().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
      
		},

    Edit(value) {
      this.$router.push({
        path: '/Platform/goodsSKU/addSKU',
        query: {
          id: value.id,
          sname: value.name,
          name: 'editor',
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    Delete(value) {
      console.log(value);
      this.$confirm('删除此数据有可能导致某些商品报错，确定要删除此数据吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delSku({
          api: 'saas.dic.delSku',
          idList: value.id
        }).then(res => {
          if(res.data.code = '200') {
            console.log(res);
            this.getSkuInfos()
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

    details(value) {
      this.$router.push({
        path: '/Platform/goodsSKU/SKUdetails',
        query: {
          id: value.id
        }
      })
      // console.log(value);
    },

    // 选框改变
    handleSelectionChange(val) {
			if(val.length==0){
				this.is_disabled = true
			}else{
				this.is_disabled = false
			}
      console.log(val);
      this.idList = val.map(item => {
        return item.id
      })
      this.idList = this.idList.join(',')
    },

    // 批量删除
    delAll() {
      this.$confirm('删除此数据有可能导致某些商品报错，确定要删除此数据吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delSku({
          api: 'saas.dic.delSku',
          idList: this.idList
        }).then(res => {
          if(res.data.code == '200') {
            console.log(res);
            this.getSkuInfos()
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
        api: 'saas.dic.getSkuInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        sid: 0,
        exportType: 1,
      },'pagegoods')
    },

    async exportAll() {
      console.log(this.total);
      await exports({
        api: 'saas.dic.getSkuInfo',
        exportType: 1,
        pageNo: 1,
        pageSize: this.total,
        sid: 0
      },'allgoods')
    },

    async exportQuery() {
      await exports({
        api: 'saas.dic.getSkuInfo',
        exportType: 1,
        pageNo: this.dictionaryNum,
        pageSize: this.total,
        dataCode: this.inputInfo.code,
        dataName: this.inputInfo.name,
        sid: 0
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
          border: 1px solid #d5dbe8;
        }
        .export {
          position: absolute;
          right: 30px;
        }
      }
    }
    
  }

  .jump-list {
    width: 100%;
    display: flex;
    align-items: center;
    margin-bottom: 16px;
    .bgColor {
      width: 120px;
      height: 40px;
      background: #28B6FF;
      border-radius: 4px;
    }
    .fontColor {
      width: 120px;
      height: 42px;
      color: #6a7076;
      border-radius: 4px;
    }
  }

  .dictionary-list {
    // height: 603px;
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
        .OP-button-bottom {
          button {
            margin-right: 40px;
          }
        }
      }
    }
  }

}
</style>