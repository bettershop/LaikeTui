<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input size="medium" v-model="search.adminName" class="Search-input" placeholder="管理员账号"></el-input>
        </div>
        <div class="query-input">
          <el-date-picker 
            v-model="search.time" 
            type="datetimerange" 
            range-separator="至" 
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </div>

        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="isExportBox=!isExportBox">导出</el-button>
        </div>
      </div>
    </div>

    <div class="jump-list">
      <el-button class="fontColor" @click="Del" icon="el-icon-delete">{{$t('DemoPage.tableExamplePage.Batch_delete')}}</el-button>
    </div>

    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
        class="el-table"
        style="width: 100%"
        :height="tableHeight" @selection-change="Chose">
        <el-table-column type="selection" label="全选">
        </el-table-column>
        <el-table-column prop="admin_name" label="管理员账号">
        </el-table-column>
        <el-table-column prop="event" label="事件">
        </el-table-column>
        <el-table-column label="时间">
          <template slot-scope="scope" v-if="scope.row.add_date!=null">
            {{ scope.row.add_date | dateFormat}}
          </template>
        </el-table-column>

      </el-table>

      <div class="pageBox" ref="pageBox" v-if="page.showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
          @size-change="handleSizeChange"
          :page-sizes="pagesizes" :current-page="pagination.page"
          @current-change="handleCurrentChange"
          :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

    <div class="dialog-export">
      <!-- 弹框组件 -->
      <el-dialog
        title="导出数据"
        :visible.sync="isExportBox"
        :before-close="isExportBoxClose"
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
import main from "@/webManage/js/authority/adminLoggerManage/adminLoggerList";
export default main;
</script>

<style scoped lang="less">
@import "../../../common/commonStyle/form";
@import "../../../webManage/css/authority/adminLoggerManage/adminLoggerList.less";
</style>
