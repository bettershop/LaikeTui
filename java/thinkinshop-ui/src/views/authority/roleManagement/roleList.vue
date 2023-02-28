<template>
  <div class="container">
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/authority/roleManagement/addRoles')">添加角色</el-button>
    </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="id" label="序号">
          <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="角色">
        </el-table-column>
        <el-table-column prop="role_describe" label="描述">
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="View(scope.row)">查看</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                <el-button class="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
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
import roleList from '@/webManage/js/authority/roleManagement/roleList'
export default roleList
</script>

<style scoped lang="less">
@import '../../../webManage/css/authority/roleManagement/roleList.less';
</style>