<template>
  <div class="iframe-content">

    <div class="box">
      <el-row :gutter="20">
        <el-col :span="10">
          <div class="grid-content bg-purple">商家商品数:<span class="left">{{mainData.product_num}}</span></div>
        </el-col>
        <el-col :span="10">
          <div class="grid-content bg-purple">对接店铺数:<span class="left">{{mainData.customer_num}}</span></div>
        </el-col>
      </el-row>
    </div>
    <div id="goodsMap" class="chart" :style="{width: '100%', height: '800px', borderRadius: '4px'}"></div>
    <div id="stockMap" class="chart" :style="{width: '100%', height: '800px'}"></div>

    <div class="listBox">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
          class="el-table" style="width: 100%"
          :height="527">
        <el-table-column label="序号" width="150">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="id" label="商品编号" width="150">
        </el-table-column>
        <el-table-column prop="product_title" label="商品名称" width="150">
        </el-table-column>
        <el-table-column label="商品图片" width="230">
          <template slot-scope="scope">
            <img class="goodsImg" :src="scope.row.img" alt="">
          </template>
        </el-table-column>
        <el-table-column prop="attribute" label="商品规格" width="150">
        </el-table-column>
        <el-table-column prop="total_num" label="总库存" width="230">
        </el-table-column>
        <el-table-column prop="num" label="剩余库存" width="230">
        </el-table-column>

        <el-table-column label="预警时间" >
          <template slot-scope="scope">
            <span v-if="scope.row.add_date">{{ scope.row.add_date | dateFormat}}</span>
            <span v-else></span>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox" v-if="page.showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
            @size-change="handleSizeChange"
            :page-sizes="pagesizes" :current-page="pagination.page"
            @current-change="handleCurrentChange" :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
import main from '@/webManage/js/data/goods-report/index'

export default main
</script>

<style scoped lang="less">
  @import '../../../webManage/css/data/goods-report/index';
</style>
