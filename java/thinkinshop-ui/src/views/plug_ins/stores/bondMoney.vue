<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="店铺" @click.native.prevent="$router.push('/plug_ins/stores/store')"></el-radio-button>
        <el-radio-button label="审核列表" @click.native.prevent="$router.push('/plug_ins/stores/auditList')"></el-radio-button>
        <el-radio-button label="保证金记录" @click.native.prevent="$router.push('/plug_ins/stores/bondMoney')"></el-radio-button>
        <el-radio-button label="商品审核" @click.native.prevent="$router.push('/plug_ins/stores/goodsAudit')"></el-radio-button>
        <el-radio-button label="提现审核" @click.native.prevent="$router.push('/plug_ins/stores/withdrawalAudit')"></el-radio-button>
        <el-radio-button label="提现记录" @click.native.prevent="$router.push('/plug_ins/stores/withdrawalRecord')"></el-radio-button>
        <el-radio-button label="店铺设置" @click.native.prevent="$router.push('/plug_ins/stores/storeSet')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.keyName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称/用户ID"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="mch_id" label="店铺ID">
        </el-table-column>
        <el-table-column prop="store_info" label="店铺信息" fixed="left" width="300">
          <template slot-scope="scope">
            <div class="store-info">
              <div class="head-img">
                <img :src="scope.row.logo" alt="">
              </div>
              <div class="store_info">
                <div class="item name">
                  店铺名称：{{ scope.row.mchName }}
                </div>
                <div class="item grey center">
                  用户ID：{{ scope.row.user_id }}
                </div>
                <div class="item grey">
                  所属用户：{{ scope.row.userName }}
                </div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="realname" label="联系人">
        </el-table-column>
        <el-table-column prop="tel" label="联系电话">
        </el-table-column>
        <el-table-column prop="status" label="记录类型">
          <template slot-scope="scope">
            <div class="store-info">
              {{scope.row.status  == 1 ? "交纳":"退还"}}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="mobile" label="是否已退还">
          <template slot-scope="scope">
            <div class="store-info">
              {{scope.row.is_return_pay  == 1 ? "是":"否"}}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="add_date" label="交纳/申请退还时间">
          <template slot-scope="scope">
            <span v-if="scope.row.is_return_pay != 1">{{ scope.row.add_date | dateFormat }}</span>
            <span v-else>{{ scope.row.update_date | dateFormat }}</span>
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
import bondMoney from '@/webManage/js/plug_ins/stores/bondMoney'
export default bondMoney
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/withdrawalRecord.less';
</style>