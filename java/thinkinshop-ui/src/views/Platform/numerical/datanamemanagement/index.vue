<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="dataName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入数据名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button @click="dialogShow" class="bgColor laiketui laiketui-add" type="primary">添加数据名称</el-button>
    </div>
    <div class="dictionary-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading"  :data="tableData" ref="table" class="el-table" style="width: 100%"
			:height="tableHeight">
        <el-table-column prop="name" label="数据名称" width="378">
        </el-table-column>
        <el-table-column label="是否生效" width="306">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.status" @change="switchs(scope.row)" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="admin_name" label="添加人" width="249">
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="579">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" prop="operation" label="操作" width="140">
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

    <el-dialog
      title="添加数据名称"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="数据名称" prop="dataName">
          <el-input v-model="ruleForm.dataName"></el-input>
        </el-form-item>
        <el-form-item label="是否生效" prop="resource">
          <el-radio-group v-model="ruleForm.status">
            <el-radio v-model="radio" label="1">是</el-radio>
            <el-radio v-model="radio" label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <div class="form-footer">
          <el-form-item>
            <el-button @click="handleClose">取 消</el-button>
            <el-button type="primary" @click="determine('ruleForm')">确 定</el-button>
          </el-form-item>
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { dataName, addDictionaryInfo, delDictionary, switchDictionary } from '@/api/Platform/numerical'
import { mixinstest } from '@/mixins/index'

export default {
    name: 'datanamemanagement',
    mixins: [mixinstest],
    data() {
      return {
        loading: true,
        dataName: '',
        tableData: [],
        tableHeight:null,//表格高度

        // 弹框数据
        dialogVisible: false,
        ruleForm: {
          dataName: '',
          status: 1,
        },
        radio: '是',
        rules: {
          dataName: [
            { required: true, message: '请填写数据名称', trigger: 'blur' }
          ],
          statues: [
            { required: true, message: '是', trigger: 'change' }
          ],
        },

        // table高度
        tableHeight: null,
      }
    },

    created() {
      this.getDataName()
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
      async getDataName() {
        const res = await dataName({
          api: 'saas.dic.getDictionaryCatalogInfo',
          storeType: 8,
          pageNo: this.dictionaryNum,
          pageSize: this.pageSize,
          name: this.dataName
        })
        console.log(res);
        this.total = res.data.data.total
        this.tableData = res.data.data.list
        this.loading = false
      },

      reset() {
        this.dataName = ''
      },

      demand() {
        this.currpage = 1
        this.current_num = 10
        this.showPagebox = false
        this.loading = true
        this.dictionaryNum = 1
        this.getDataName().then(() => {
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
        this.getDataName().then(() => {
          this.currpage = ((this.dictionaryNum - 1) * this.pageSize) + 1
          this.current_num = this.tableData.length === this.pageSize ? this.dictionaryNum * this.pageSize : this.total
          this.loading = false
        })
      },
      //点击上一页，下一页
      handleCurrentChange(e){
        this.loading = true
        this.currpage = e
        this.dictionaryNum = e
        this.currpage = ((e - 1) * this.pageSize) + 1
        this.getDataName().then(() => {
          this.current_num = this.tableData.length === this.pageSize ? e * this.pageSize : this.total
          this.loading = false
        })
      },

      // 是否生效开关
      switchs(value) {
        console.log(value);
        switchDictionary({
          api: 'saas.dic.switchDictionary',
          id: value.id,
        }).then(res => {
          this.getDataName()
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

      // 编辑字典目录
      Edit(value) {
        this.dialogVisible = true
        console.log(value)
        this.ruleForm.dataName= value.name
        this.ruleForm.status= `${value.status}`
      },

      // 删除字典目录
      Delete(value) {
        console.log(value);
        this.$confirm('确认删除此数据嘛？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delDictionary({
            api: 'saas.dic.delDictionary',
            idList: value.id
          }).then(res => {
            if(res.data.code == '200') {
              console.log(res);
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getDataName()
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
        this.ruleForm.dataName= '',
        this.ruleForm.status= '1',
        this.dialogVisible = true
      },
    
      handleClose(done) {
        this.dialogVisible = false
        this.$refs['ruleForm'].clearValidate()
      },

      determine(formName) {
        this.$refs[formName].validate(async (valid) => {
          console.log(this.ruleForm);
          if (valid) {
            try {
              addDictionaryInfo({
                api: 'saas.dic.addDictionaryInfo',
                name: this.ruleForm.dataName,
                isOpen: parseInt(this.ruleForm.status),
              }).then(res => {
                if(res.data.code == '200') {
                  this.getDataName()
                  console.log(res);
                  this.$message({
                    message: '添加成功',
                    type: 'success',
                    offset: 100
                  })
                  this.dialogVisible = false
                }
              })
            } catch (error) {
              this.$message({
                message: '数据名称不能为空',
                type: 'error',
                offset: 100
              })
            }
          } else {
            this.$message({
              message: '数据名称不能为空',
              type: 'error',
              offset: 100
            })
            // return false;
          }
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


  // 弹框样式
  /deep/.el-dialog {
    width: 580px;
    height: 310px;
    padding: 10px 0 0;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    margin: 0 !important;
    .el-dialog__header {
      width: 100%;
      height: 58px;
      line-height: 58px;
      font-size: 16px;
      margin-left: 19px;
      font-weight: bold;
      border-bottom: 2px solid #E9ECEF;
      box-sizing: border-box;
      margin: 0;
      padding: 0 0 0 19px;
    }

    .el-dialog__body {
      padding: 41px 60px 0px 60px !important;

      .form-footer {
        width: 174px;
        height: 72px;
        position: absolute;
        bottom: 0;
        right: 0;
        .el-form-item {
          padding: 0 !important;
          height: 100%;
          .el-form-item__content {
            height: 100%;
            line-height: 72px;
            margin: 0 !important;
          }
        }
      }
    }
  }
}
</style>