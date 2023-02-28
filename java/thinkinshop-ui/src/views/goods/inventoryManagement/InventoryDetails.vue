<template>
  <div class="container">
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="" label="序号" width="88">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="goods_img" label="商品名称" width="230">
          <template slot-scope="scope">
            <div class="goods-info">
              <img :src="scope.row.imgurl" alt="">
              <span>{{ scope.row.product_title }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="price" label="售价" width="230">
          <template slot-scope="scope">
            <span>{{ scope.row.price.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="specifications" label="规格" width="230">
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template slot-scope="scope">
            <span class="ststus" :class="[ scope.row.status == 2 ? 'active1' : scope.row.status == 3 ? 'active2' : 'active3' ]">{{ scope.row.status == 2 ? '已上架' : scope.row.status == 3 ? '已下架' : '待上架'}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="flowing_num" label="入库/出库" width="200">
          <template slot-scope="scope">
            <div class="num-Detailds" :class="[ scope.row.type === 0 ? 'active' : 'actives' ]">
              <span v-if="scope.row.type === 0">+</span>
              <span v-else>-</span>
              <span>{{ scope.row.flowing_num }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="add_date" label="上架时间" width="200">
          <template slot-scope="scope">
            <span v-if="!scope.row.add_date"></span>
            <span v-else>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="content" fixed="right" show-overflow-tooltip label="备注" width="300">
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
import InventoryDetails from '@/webManage/js/goods/inventoryManagement/InventoryDetails'
export default InventoryDetails
</script>

<style scoped lang="less">
@import '../../../webManage/css/goods/inventoryManagement/InventoryDetails.less';
</style>