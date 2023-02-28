<template>
  <div class="container">
    <div class="dictionary-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column prop="id" label="序号" width="70">
          <template class="status" slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="itemid" label="商品链接ID" width="243">
        </el-table-column>
        <el-table-column prop="goodsName" show-overflow-tooltip label="商品名称" width="243">
        </el-table-column>
        <el-table-column prop="is_use_str" label="抓取状态" width="243">
          <template class="status" slot-scope="scope">
            <span :class="scope.row.status === -1 ? 'active1' : 'active2'">{{ scope.row.status === -1 ? '抓取失败' : '抓取成功' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="add_date" label="执行时间" width="442">
          <template slot-scope="scope">
            <span>{{ scope.row.creattime | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="msg" show-overflow-tooltip label="备注" width="243">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" v-if="scope.row.status === 2" @click="Edit(scope.row)">编辑</el-button>
                <el-button icon="el-icon-view" v-if="scope.row.status === 2" @click="$router.push('/goods/goodslist/physicalgoods')">查看商品</el-button>
                <el-button icon="el-icon-view" v-if="scope.row.status === -1" @click="viewLink(scope.row.link)">查看链接</el-button>
                <el-button icon="el-icon-edit-outline" v-if="scope.row.status === -1"  @click="draws(scope.row)">重抓</el-button>
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
import viewTask from '@/webManage/js/goods/taobaoAssistant/viewTask'
export default viewTask
</script>

<style scoped lang="less">
@import '../../../../webManage/css/goods/taobaoAssistant/viewTask.less';
</style>