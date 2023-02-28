<template>
  <div class="iframe-content">
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

    <div class="box">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="grid-content bg-purple">订单总数:<span class="left">{{orderCount.num}}</span></div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">订单总额:<span class="left">{{orderCount.z_price.toFixed(2)}}</span></div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">有效订单数:<span class="left">{{orderCount.yx_num}}</span></div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">有效总额:<span class="left">{{orderCount.yx_amt.toFixed(2)}}</span></div>
        </el-col>
      </el-row>
    </div>

    <div id="orderMap" class="chart" :style="{width: '100%', height: '500px'}"></div>
    <div id="priceMap" class="chart" :style="{width: '100%', height: '500px'}"></div>

  </div>
</template>

<script>
import main from '@/webManage/js/data/orde-report/index'

export default main
</script>

<style scoped lang="less">
  @import '../../../webManage/css/data/orde-report/index';
</style>
