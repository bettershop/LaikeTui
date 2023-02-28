<template>
  <div class="container">
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary" @click="Save()">添加管理员</el-button>
    </div>
    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
          class="el-table"
          style="width: 100%"
          :height="tableHeight">
        <el-table-column prop="id" label="账户ID" width="230">
        </el-table-column>
        <el-table-column prop="name" label="账号" width="230">
        </el-table-column>
        <el-table-column label="所属客户编号" width="230">
          <template>
            {{customerNumber}}
          </template>
        </el-table-column>
        <el-table-column prop="roleName" label="绑定角色" width="230">
        </el-table-column>
        <el-table-column prop="superName" label="添加人" width="230">
        </el-table-column>
        <el-table-column label="添加时间">
          <template slot-scope="scope" v-if="scope.row.add_date!=null">
            {{ scope.row.add_date | dateFormat}}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="250">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-circle-close" @click="Disable(scope.row.id)" v-if="scope.row.status===2">禁用</el-button>
                <el-button icon="el-icon-circle-check" @click="Disable(scope.row.id)" v-if="scope.row.status===1">启用</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row.id)">编辑</el-button>
                <el-button icon="el-icon-delete" @click="Del(scope.row.id)">删除</el-button>
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
import main from "@/webManage/js/authority/adminUserManage/adminUserList";
export default main;
</script>

<style scoped lang="less">
@import "../../../common/commonStyle/form";
@import "../../../webManage/css/authority/adminUserManage/adminUserList.less";
</style>
