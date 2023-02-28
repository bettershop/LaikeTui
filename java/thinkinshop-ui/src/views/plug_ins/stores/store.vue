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
          <el-select class="select-input" v-model="inputInfo.status" placeholder="请选择营业状态">
            <el-option v-for="(item,index) in statusList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-select class="select-input" v-model="inputInfo.promiseStatus" placeholder="请选择交纳保证金状态" style="width:204px">
            <el-option label="未交纳" value="0"></el-option>
            <el-option label="已交纳" value="1"></el-option>
          </el-select>
          <el-input v-model="inputInfo.storeName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称或用户ID"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <!-- <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button> -->
        </div>
      </div>
	  </div>

    <!-- <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/plug_ins/stores/addStore')">添加自营店</el-button>
    </div> -->

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="id" label="店铺ID" width="70">
        </el-table-column>
        <el-table-column prop="store_info" label="店铺信息" width="410">
          <template slot-scope="scope">
            <div class="stores-info">
              <div class="head-img">
                <img :src="scope.row.logo" alt="">
              </div>
              <div class="store-info">
                <div class="item name">
                  店铺名称：{{ scope.row.name }}
                </div>
                <div class="item grey center">
                  用户ID：{{ scope.row.user_id }}
                </div>
                <div class="item grey">
                  所属用户：{{ scope.row.user_name }}
                </div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="realname" label="联系人" width="275">
        </el-table-column>
        <el-table-column prop="tel" label="联系电话" width="175">
        </el-table-column>
        <el-table-column prop="cashable_money" label="商户余额" width="130">
          <template slot-scope="scope">
            <span>{{ scope.row.cashable_money.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="promiseStatus" label="交纳保证金状态" width="130">
        </el-table-column>
        <el-table-column prop="add_time" label="入驻时间" width="250">
          <template slot-scope="scope">
            <span>{{ scope.row.add_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="is_open" label="店铺状态" width="130">
            <template slot-scope="scope">
              <span class="ststus" :class="[[ scope.row.is_open == 0 ? 'active2' : scope.row.is_open == 1 ? 'active1' : 'active3' ]]">{{ scope.row.is_open == 0 ? '未营业' : scope.row.is_open == 1 ? '营业中' : '已打烊' }}</span>
            </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200" class="hhh">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="View(scope.row)">查看</el-button>
                <el-button icon="el-icon-edit-outline" @click="Modify(scope.row)">修改</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
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
import store from '@/webManage/js/plug_ins/stores/store'
export default store
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/store.less';
</style>