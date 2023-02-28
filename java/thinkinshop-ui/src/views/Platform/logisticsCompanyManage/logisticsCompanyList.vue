<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input size="medium" v-model="page.inputInfo.name" @keyup.enter.native="demand" class="Search-input"
          placeholder="请输入物流公司名称/编码"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}
          </el-button>
        </div>
      </div>
    </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"
        @click="Save()">添加物流公司
      </el-button>
      <el-button class="bgColor fontColor" :disabled="is_disabled" icon="el-icon-delete" type="primary"
        @click="Delete()">批量删除
      </el-button>
    </div>

    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
        class="el-table"
        style="width: 100%"
        :height="tableHeight" @selection-change="handleSelectionChange" @sort-change="sortChange">
        <el-table-column type="selection">
        </el-table-column>
        <el-table-column prop="id" label="ID">
        </el-table-column>
        <el-table-column prop="kuaidi_name" label="物流公司名称">
        </el-table-column>
        <el-table-column prop="type" label="编码">
        </el-table-column>
        <el-table-column label="开关">
          <template slot-scope="scope">
            <el-switch v-model="switchFlag=scope.row.is_open===1" @change="Switch(scope.row.id)"/>
          </template>
        </el-table-column>
        <el-table-column label="排序" sortable='sequence'>
          <template slot-scope="scope">
            <el-input v-model="scope.row.sort" placeholder="请输入序号" @change="Sort(scope.row)"/>
          </template>
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间">
          <template slot-scope="scope" v-if="scope.row.add_date!=null">
            {{scope.row.add_date|dateFormat}}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" @click="Save(scope.row.id)">编辑</el-button>
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
          :page-sizes="pagesizes" :current-page="pagination.page"
          @current-change="handleCurrentChange"
          :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
import main from "@/webManage/js/platform/logisticsCompanyManage/logisticsCompanyList";
export default main
</script>


<style scoped lang="less">
  @import "../../../common/commonStyle/form";
  @import "../../../webManage/css/platform/logisticsCompanyManage/logisticsCompanyList";
</style>
