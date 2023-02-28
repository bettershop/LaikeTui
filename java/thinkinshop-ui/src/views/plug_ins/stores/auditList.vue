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
          <el-select class="select-input" v-model="inputInfo.status" placeholder="请选择审核状态">
            <el-option v-for="(item,index) in statusList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="inputInfo.storeName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称或用户ID"></el-input>
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
        <el-table-column fixed="left" prop="id" label="店铺ID">
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
        <el-table-column prop="realname" label="联系人">
        </el-table-column>
        <el-table-column prop="tel" label="联系电话">
        </el-table-column>
        <el-table-column prop="add_time" label="申请/审核时间">
          <template slot-scope="scope">
            <span>{{ scope.row.add_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="review_status" label="审核状态">
            <template slot-scope="scope">
              <span class="ststus" :class="[[ scope.row.review_status == 0 ? 'active' : 'actives' ]]">{{ scope.row.review_status == 0 ? '待审核' : '审核不通过' }}</span>
            </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="View(scope.row)">查看</el-button>
                <el-button icon="el-icon-circle-check" v-if="scope.row.review_status == 0" @click="Through(scope.row)">通过</el-button>
                <el-button icon="el-icon-circle-close" v-if="scope.row.review_status == 0" @click="dialogShow2(scope.row)">拒绝</el-button>
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
import auditList from '@/webManage/js/plug_ins/stores/auditList'
export default auditList
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/auditList.less';
</style>