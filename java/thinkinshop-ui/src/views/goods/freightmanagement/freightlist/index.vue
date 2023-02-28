<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-select class="select-input" v-model="inputInfo.status" placeholder="请选择模板状态">
            <el-option v-for="item in Dictionary" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入模板名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary" @click="$router.push('/goods/freightmanagement/addtemplate')">添加模板</el-button>
      <el-button class="fontColor" @click="delAll" :disabled="is_disabled" icon="el-icon-delete" >批量删除</el-button>
    </div>
    <div class="dictionary-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column type="selection" width="48">
        </el-table-column>
        <el-table-column prop="name" label="运费名称" width="448">
        </el-table-column>
        <el-table-column label="设为默认" width="273">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.is_default" @change="switchs(scope.row)" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="is_use" label="状态" width="187">
          <template slot-scope="scope">
            <span>{{ scope.row.is_use == 1 ? '已使用' : '未使用' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="add_time" label="添加时间" width="532">
          <template slot-scope="scope">
            <span>{{ scope.row.add_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" v-if="scope.row.is_use !== 1" @click="Edit(scope.row)">编辑</el-button>
                <!-- :disabled="scope.row.is_use === 1" -->
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button> 
                <el-button icon="el-icon-view" v-show="scope.row.is_use === 1" @click="details(scope.row)">查看</el-button>
              </div>
              <!-- <div class="OP-button-bottom">
                <el-button icon="el-icon-view" @click="details(scope.row)">查看</el-button>
              </div> -->
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
import { getFreightInfo, delFreight, freightSetDefault } from '@/api/goods/freightManagement'
import { getStorage } from '@/utils/storage'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'freightlist',
  mixins: [mixinstest],
  data() {
    return {
      tableData: [],
      loading: true,
      is_disabled: true,
      idList: [],
      inputInfo: {
        status: '',
        name: '',
      },
      Dictionary: [
        { 
          value: '0',
          label: '未使用'
        },
        { 
          value: '1',
          label: '已使用'
        }
      ],

      freight:null,
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
    this.getFreightInfos()
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

    async getFreightInfos() {
      const res = await getFreightInfo({
        api: 'admin.goods.getFreightInfo',
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        status: this.inputInfo.status,
        name: this.inputInfo.name,
        mchId: getStorage('laike_admin_uaerInfo').mchId,
      })
      console.log(res);
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false

      this.freight = res.data.data.list[0].freight
      console.log(this.freight);
      
    },

    reset() {
      this.inputInfo.status = ''
      this.inputInfo.name = ''
    },

    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getFreightInfos().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    // 设置默认
    switchs(value) {
      console.log(value);
      freightSetDefault({
        api: 'admin.goods.freightSetDefault',
        id: value.id,
      }).then(res => {
        if(res.data.code == '200') {
          this.getFreightInfos()
          console.log(res);
          this.$message({
            message: '设置默认成功！',
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
      this.getFreightInfos().then(() => {
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
      this.getFreightInfos().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })  
	  },

    Edit(value) {
      this.$router.push({
        name: 'editortemplate',
        query: {
          name: 'editor',
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        },
        params: value
      })
    },

    Delete(value) {
      console.log(value);
      this.$confirm('确定要删除此运费吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFreight({
          api: 'admin.goods.delFreight',
          idList: value.id
        }).then(res => {
          if(res.data.code == '200') {
            console.log(res);
            this.getFreightInfos()
            this.$message({
              type: 'success',
              message: '删除成功!',
              offset:100
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
        name: 'editortemplate',
        query: {
          name: 'view'
        },
        params: value
      })
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
      this.$confirm('确定要删除此运费吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delFreight({
          api: 'admin.goods.delFreight',
          freightIds: this.idList
        }).then(res => {
          if(res.data.code == '200') {
            console.log(res);
            this.getFreightInfos()
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
      }
    }
    
  }

  .jump-list {
    width: 100%;
    display: flex;
    align-items: center;
    margin-bottom: 16px;
    .laiketui-add:before {
      font-size: 14px;
      margin-right: 8px;
    }
    .bgColor {
      width: 120px;
      height: 40px;
      background: #28B6FF;
      border-radius: 4px;
      border: none;
      font-size: 14px;
    }
    .fontColor {
      width: 120px;
      height: 42px;
      color: #6a7076;
      border-radius: 4px;
    }
  }

  .dictionary-list {
    // height: 593px;
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