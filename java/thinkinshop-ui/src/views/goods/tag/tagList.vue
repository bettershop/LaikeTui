<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input size="medium" v-model="page.inputInfo.name" @keyup.enter.native="demand" placeholder="请输入商品标签"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}
          </el-button>
        </div>
      </div>
    </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"
          @click="dialogShow">新增标签
      </el-button>
    </div>

    <!-- 弹框组件 -->
    <div class="dialog-block">
      <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <el-form :model="dataForm" :rules="rules" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
          <div class="pass-input">
            <el-form-item label="标签名称" prop="name">
              <el-input v-model="dataForm.name"></el-input>
            </el-form-item>
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" @click="handleClose()">取 消</el-button>
              <el-button type="primary" @click="Save('ruleForm2')" class="qdcolor">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table" class="el-table" style="width: 100%"
      :height="tableHeight">
        <el-table-column prop="id" label="标签ID">
        </el-table-column>
        <el-table-column prop="name" label="商品标签">
        </el-table-column>
        <el-table-column  label="添加时间">
          <template slot-scope="scope">
            {{ scope.row.add_time | dateFormat}}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" @click="Show(scope.row.id)">编辑</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row.id)">删除</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox" ref="pageBox" v-if="page.showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
          @size-change="handleSizeChange"
          :page-sizes="page.pagesizes" :current-page="page.pagination.page"
          @current-change="handleCurrentChange"
          :total="page.total">
          <div class="pageRightText">当前显示{{page.currpage}}-{{page.current_num}}条，共 {{page.total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

  </div>
</template>


<script>
import tagList from "@/webManage/js/goods/tag/tagList";
export default tagList;
</script>

<style scoped lang="less">
  @import "../../../common/commonStyle/form";
  @import "../../../webManage/css/goods/tag/tagList";
</style>
