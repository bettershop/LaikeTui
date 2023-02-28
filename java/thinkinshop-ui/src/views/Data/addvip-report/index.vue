<template>
  <div class="iframe-content">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="tbl">
        <el-radio-button label="新增会员报表"
          @click.native.prevent="$router.push('/Data/addvip-report')"></el-radio-button>
        <el-radio-button label="会员消费报表"
          @click.native.prevent="$router.push('/Data/vipconsumption-report')"></el-radio-button>
        <el-radio-button label="会员比例"
          @click.native.prevent="$router.push('/Data/vip-proportion')"></el-radio-button>
      </el-radio-group>
    </div>
    <div class="page_h16"></div>
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
    <div class="page_h16"></div>
    <div id="myChart" class="chart" :style="{width: '100%', height: '500px'}"></div>

    <div class="listBox">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
                class="el-table" style="width: 100%"
                :height="527">
        <el-table-column prop="user_id" label="会员ID">
        </el-table-column>
        <el-table-column prop="user_name" label="用户昵称">
        </el-table-column>
        <el-table-column label="用户来源">
          <template slot-scope="scope">
            {{ source.get(scope.row.source.toString()) }}
          </template>
        </el-table-column>
        <el-table-column prop="Bank_card_number" label="注册时间">
          <template slot-scope="scope">
            <span>{{scope.row.Register_data | dateFormat}}</span>
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
import main from '@/webManage/js/data/addvip-report/index'
export default main
</script>

<style scoped lang="less">
  @import '../../../webManage/css/data/addvip-report/index';
</style>

