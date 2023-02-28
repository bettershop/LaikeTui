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

    <div id="myChart" class="chart" :style="{width: '100%', height: '800px'}"></div>
    <div class="hr"></div>
  </div>
</template>

<script>
import main from '@/webManage/js/data/vip-proportion/index'

export default main
</script>

<style scoped lang="less">
  @import '../../../webManage/css/data/addvip-report/index';
</style>
