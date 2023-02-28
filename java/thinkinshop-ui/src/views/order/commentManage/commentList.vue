<template>
  <div class="container">
    <div class="Search">
      <el-select class="select-input" v-model="dataForm.CommentType" placeholder="评价类型">
        <el-option v-for="(item,index) in commentTypeList" :key="index" :label="item.name" :value="item.val">
          <div>{{ item.name }}</div>
        </el-option>
      </el-select>

      <div class="Search-condition">
        <div class="query-input">
          <el-input size="medium" v-model="dataForm.orderNo" @keyup.enter.native="demand" class="Search-input" placeholder="请输入订单编号"></el-input>
        </div>
        <div class="select-date">
          <span>评论日期：</span>
          <el-date-picker v-model="dataForm.choseDate"
            type="datetimerange" range-separator="至" start-placeholder="开始日期"
            end-placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss"
            :editable="false">
          </el-date-picker>
        </div>

        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}
          </el-button>
        </div>
      </div>
    </div>
    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
        class="el-table"
        style="width: 100%"
        :height="tableHeight">
        <el-table-column label="序号" width="88">
          <template slot-scope="scope">
            {{ scope.$index+page.currpage }}
          </template>
        </el-table-column>
        <el-table-column prop="p_name" label="商品名称" width="328">
        </el-table-column>
        <el-table-column prop="r_sNo" label="订单编号" width="328">
        </el-table-column>
        <el-table-column prop="shop_name" label="店铺名称" width="328">
        </el-table-column>
        <el-table-column prop="uid" label="用户id" width="328">
        </el-table-column>

        <el-table-column label="评价类型" width="328">
          <template slot-scope="scope">
            <i class=" laiketui laiketui-haoping" v-if="scope.row.CommentType>3"></i>
            <i class=" laiketui laiketui-chaping" v-else-if="scope.row.CommentType<3"></i>
            <i class=" laiketui laiketui-zhongping" v-else></i>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="评价内容" width="328">
        </el-table-column>
        <el-table-column label="评价时间" width="328">
          <template slot-scope="scope">
            {{ scope.row.add_time | dateFormat}}
          </template>
        </el-table-column>
        <el-table-column prop="p_price" label="订单金额" width="328">
        </el-table-column>

        <el-table-column fixed="right" label="操作" width="260">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-chat-dot-square" @click="Reply(scope.row.id)" v-if="scope.row.replyText==null">
                  回复
                </el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row.id)">修改</el-button>
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
import list from "@/webManage/js/order/commentManage/commentList";
export default list;
</script>


<style scoped lang="less">
  @import "../../../common/commonStyle/form";
  @import "../../../webManage/css/order/commentManage/commentList";
</style>
