<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.code" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入数据编码"></el-input>
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入数据名称"></el-input>
          <el-input v-model="inputInfo.attribute" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入数据属性"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list" ref="tableFather">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/Platform/numerical/adddictionary')">添加数据字典</el-button>
      <el-button class="bgColor" type="primary" icon="el-icon-remove-outline" @click="$router.push('/Platform/numerical/datanamemanagement')">数据名称管理</el-button>
    </div>
    <div class="dictionary-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
			:height="tableHeight">
        <el-table-column prop="code" label="数据编码" width="255">
        </el-table-column>
        <el-table-column prop="name" label="数据名称" width="206">
        </el-table-column>
        <el-table-column prop="text" label="值" width="175">
          
        </el-table-column>
        <el-table-column prop="s_name" label="所属属性名称" width="228">
        </el-table-column>
        <el-table-column label="是否生效" width="166">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" @change="switchs(scope.row)" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="admin_name" label="添加人" width="136">
          
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="326">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
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

  </div>
</template>

<script>
import { dictionaryList, deleteDictionary, addDictionaryTable, switchDictionaryDetail } from '@/api/Platform/numerical'
import { mixinstest } from '@/mixins/index'
export default {
  name: 'dictionarylist',
  mixins: [mixinstest],
  data() {
    return {
      tableData: [],
      loading: true,
      inputInfo: {
        code: '',
        name: '',
        attribute: ''
      },

      el_switch: true,
      // table高度
      tableHeight: null,
    }
  },

  created() {
    if(this.$route.params.pageSize) {
      this.pagination.page = this.$route.params.dictionaryNum
      this.dictionaryNum = this.$route.params.dictionaryNum
      this.pageSize = this.$route.params.pageSize
    }
    this.getDictionaryList()
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
    async getDictionaryList() {
      const res = await dictionaryList({
        api: 'saas.dic.getDictionaryInfo',
        storeType: 8,
        pageNo: this.dictionaryNum,
        pageSize: this.pageSize,
        dicNo: this.inputInfo.code,
        key: this.inputInfo.name,
        text: this.inputInfo.attribute,
      })
      this.total = res.data.data.total
      this.tableData = res.data.data.list
      this.loading = false
    },

    reset() {
      this.inputInfo.code = ''
      this.inputInfo.name = ''
      this.inputInfo.attribute = ''
    },

    demand() {
      this.currpage = 1
      this.current_num = 10
      this.showPagebox = false
      this.loading = true
      this.dictionaryNum = 1
      this.getDictionaryList().then(() => {
        this.loading = false
        if(this.tableData.length > 5) {
          this.showPagebox = true
        }
      })
    },

    //选择一页多少条
		handleSizeChange(e){
      this.loading = true
			console.log(e);
      // this.current_num = e
      this.pageSize = e
      this.getDictionaryList().then(() => {
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
      this.getDictionaryList().then(() => {
        this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
        this.loading = false
      })
      
		},

    Edit(value) {
      this.$router.push({
        name: 'compile',
        params: {
          ...value,
          searchInfo: this.inputInfo,
        },
        query: {
          dictionaryNum: this.dictionaryNum,
          pageSize: this.pageSize
        }
      })
    },

    switchs(value) {
      // console.log(value);
      switchDictionaryDetail({
        api: 'saas.dic.switchDictionaryDetail',
        id: value.id,
        token: this.$store.getters.token
      }).then(res => {
        this.getDictionaryList()  
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

    Delete(value) {
      console.log(value);
      this.$confirm('确认删除此数据嘛？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDictionary({
          api: 'saas.dic.delDictionaryDetailInfo',
          idList: value.id
        }).then(res => {
          if(res.data.code == '200') {
            this.getDictionaryList()
            dictionaryList({
              api: 'saas.dic.getDictionaryInfo',
              key: '分页',
            }).then(res => {
              if(res.data.data.list.length !== 0) {
                let pagesize = res.data.data.list.map(item => {
                    return parseInt(item.text)
                })
                pagesize = pagesize.sort(function(a, b){return a - b})
                console.log(pagesize);
                this.pagesizes = pagesize
              }
              
            })
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
      width: 150px;
      height: 40px;
      background: #28B6FF;
      border-radius: 4px;
      padding: 0;
      span {
        font-size: 14px;
      }
    }
  }

  .dictionary-list {
    // height: 605px;
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