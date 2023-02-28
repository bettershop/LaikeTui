<template>
  <div class="iframe-content">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="tbl">
        <el-radio-button label="新增会员报表" @click.native.prevent="$router.push('/Data/addvip-report')">
        </el-radio-button>
        <el-radio-button label="会员消费报表" @click.native.prevent="$router.push('/Data/vipconsumption-report')">
        </el-radio-button>
        <el-radio-button label="会员比例" @click.native.prevent="$router.push('/Data/vip-proportion')">
        </el-radio-button>
      </el-radio-group>
    </div>

    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <div class="select-date">
            <el-date-picker v-model="page.inputInfo.date"
              type="datetimerange" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期" value-format="yyyy-MM-dd"
              :editable="false">
            </el-date-picker>
          </div>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}
          </el-button>
        </div>
      </div>
    </div>
    <div class="listBox" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table" class="el-table" style="width: 100%"
        :height="tableHeight">
        <el-table-column fixed="user_id"  label="会员ID" width="150">
        </el-table-column>
        <el-table-column prop="store_info" label="会员昵称" width="150">
          <template slot-scope="scope">
            <div class="store-info">
              <div class="store-info">
                {{ scope.row.user_name }}
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="用户来源" width="230">
          <template slot-scope="scope">
            <span>{{ source.get(scope.row.source.toString())}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="订单数量" width="150">
        </el-table-column>
        <el-table-column prop="add_date" label="订单总金额" width="230">
          <template slot-scope="scope">
            <span>{{ scope.row.orderAmt.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="reback_num" label="退款数量" width="230">
        </el-table-column>

        <el-table-column prop="s_charge" label="退款总金额" width="230">
          <template slot-scope="scope">
            <span>{{ scope.row.reback_amt.toFixed(2) }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="yx_num" label="有效订单数量" width="230">
        </el-table-column>
        <el-table-column prop="Cardholder" label="有效订单总金额">
          <template slot-scope="scope">
            <span>{{ scope.row.yx_price.toFixed(2) }}元</span>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox" ref="pageBox" v-if="page.showPagebox">
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
import main from '@/webManage/js/data/vipconsumption-report/index'
export default main
</script>

<style scoped lang="less">
@import '../../../webManage/css/data/vipconsumption-report/index.less';
</style>
