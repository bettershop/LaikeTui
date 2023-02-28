<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="商户营业报表" @click.native.prevent="$router.push('/Platform/reportManagement/businessReport')"></el-radio-button>
        <el-radio-button label="商户新增用户报表" @click.native.prevent="$router.push('/Platform/reportManagement/addUsersReport')"></el-radio-button>
        <el-radio-button label="商户订单报表" @click.native.prevent="$router.push('/Platform/reportManagement/orderReport')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="iframe-content" id="app" style="height: 679px; background-color: #FFFFFF;border-radius: 4px;padding: 20px;">
      <div class="Ranking">
        <div class="Ranking-title">商户新增用户前十排行</div>
        <div class="Ranking-date">
          <div class="Ranking-tab">
            <span :class="tabIndex == 0 ? 'active' : ''" @click="Tab(0)">昨日</span>
            <span :class="tabIndex == 1 ? 'active' : ''" @click="Tab(1)">今日</span>
            <span :class="tabIndex == 2 ? 'active' : ''" @click="Tab(2)">最近7天</span>
            <span :class="tabIndex == 3 ? 'active' : ''" @click="Tab(3)">本月</span>
          </div>
          <el-date-picker
            v-model="value1"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetimerange"
            @change="change"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </div>
      </div>
      <div id="chart">
    
      </div>
      <div class="allData">
        <h2>全部数据</h2>
        <div class="allDataRight">
          <el-input v-model="SHinput" placeholder="请输入商户名称" ></el-input>
          <el-button type="primary" size="small" @click="demand">查询</el-button>
        </div>
      </div>
      <div class="">
        <el-table :data="tableData" @sort-change="sortChange" style="width: 100%;"  >
          <el-table-column  prop="mchId" label="商户ID" ></el-table-column>
          <el-table-column  prop="name" label="商户名称" ></el-table-column>
          <el-table-column  prop="total" sortable='custom' label="营业额"></el-table-column>
        </el-table>
        <div class="pageBox" v-if="showPagebox">
          <div class="pageLeftText">显示</div>
          <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页" @size-change="handleSizeChange"
            :page-sizes="pagesizes" :current-page="pagination.page" @current-change="handleCurrentChange" :total="total">
            <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
          </el-pagination>
        </div>
      </div>
	  </div>
  </div>
</template>

<script>
import addUsersReport from '@/webManage/js/platform/reportManagement/addUsersReport'
export default addUsersReport
</script>

<style scoped lang="less">
@import '../../../webManage/css/platform/reportManagement/addUsersReport.less';
</style>