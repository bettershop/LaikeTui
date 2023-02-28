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
          <el-input v-model="inputInfo.storeName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称"></el-input>
          <el-input v-model="inputInfo.phone" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入联系电话"></el-input>
          <div class="select-date">
            <el-date-picker v-model="inputInfo.date"
              type="datetimerange" range-separator="至" start-placeholder="开始日期"
              end-placeholder="结束日期" value-format="yyyy-MM-dd HH:mm:ss"
              :editable="false">
            </el-date-picker>
          </div>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button>
        </div>
      </div>
	  </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="id" label="ID" width="70">
        </el-table-column>
        <el-table-column prop="store_info" label="店铺信息" width="300">
          <template slot-scope="scope">
            <div class="store-info">
              <div class="head-img">
                <img :src="scope.row.logo" alt="">
              </div>
              <div class="store-info">
                <div class="item name">
                  店铺名称：{{ scope.row.mch_name }}
                </div>
                <div class="item grey center">
                  用户ID：{{ scope.row.user_id }}
                </div>
                <div class="item grey">
                  所属用户：{{ scope.row.name }}
                </div>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="mobile" label="联系电话" width="130">
        </el-table-column>
        <el-table-column prop="add_date" label="申请时间" width="200">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="money" label="提现金额" width="100">
          <template slot-scope="scope" v-if="scope.row.money">
            <span>{{ scope.row.money.toFixed(2) }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="s_charge" label="提现手续费" width="100">
          <template slot-scope="scope" v-if="scope.row.s_charge">
            <span>{{ scope.row.s_charge.toFixed(2) }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="审核状态" width="100">
            <template slot-scope="scope">
              <span class="ststus" :class="[[ scope.row.status == 0 ? 'active1' : scope.row.status == 1 ? 'active2' : 'active3' ]]">{{ scope.row.status == 0 ? '待审核' : scope.row.status == 1 ? '审核通过' : '已拒绝'}}</span>
            </template>
        </el-table-column>
        <el-table-column prop="Cardholder" label="持卡人姓名" width="130">
        </el-table-column>
        <el-table-column prop="Bank_name" label="银行名称" width="100">
        </el-table-column>
        <el-table-column prop="branch" label="支行名称" width="100">
        </el-table-column>
        <el-table-column prop="Bank_card_number" label="卡号" width="200">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-refresh-right" @click="Through(scope.row)">通过</el-button>
                <el-button icon="el-icon-warning-outline" @click="dialogShow2(scope.row)">拒绝</el-button>
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

    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        :title="'填写原因'"
        :visible.sync="dialogVisible2"
        :before-close="handleClose2"
      >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <div class="task-container">
            <el-form-item class="integral" label="拒绝理由：" prop="reason">
              <el-input type="textarea" v-model="ruleForm.reason" placeholder="请输入拒绝理由"></el-input>
            </el-form-item>
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" @click="handleClose2()">取 消</el-button>
              <el-button class="bdColor" type="primary" @click="submitForm2('ruleForm')">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

    <div class="dialog-export">
      <!-- 弹框组件 -->
      <el-dialog
        title="导出数据"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <div class="item" @click="exportPage">
          <i class="el-icon-document"></i>
          <span>导出本页</span>
        </div>
        <div class="item item-center" @click="exportAll">
          <i class="el-icon-document-copy"></i>
          <span>导出全部</span>
        </div>
        <div class="item" @click="exportQuery">
          <i class="el-icon-document"></i>
          <span>导出查询</span>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import withdrawalAudit from '@/webManage/js/plug_ins/stores/withdrawalAudit'
export default withdrawalAudit
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/withdrawalAudit.less';
</style>