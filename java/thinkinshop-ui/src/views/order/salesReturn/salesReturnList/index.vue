<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.orderNo" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入订单号"></el-input>
          <el-select class="select-input" v-model="inputInfo.state" placeholder="请选择状态">
            <el-option v-for="item in stateList" :key="item.brand_id" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
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
		  :height="tableHeight" >
        <el-table-column prop="" label="序号" width="95">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="user_id" label="会员ID" width="135">
        </el-table-column>
        <el-table-column prop="p_name" label="商品名称" width="95">
        </el-table-column>
        <el-table-column prop="mchName" label="店铺名称" width="125">
        </el-table-column>
        <el-table-column prop="p_price" label="商品价格" width="125">
          <template slot-scope="scope">
            <span>{{ scope.row.p_price.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="数量" width="125">
        </el-table-column>
        <el-table-column prop="sNo" label="订单号" width="125">
        </el-table-column>
        <el-table-column prop="" label="实退金额" width="125">
          <template slot-scope="scope">
            <span v-if="scope.row.re_money == '0.00' && scope.row.re_type != 3">未退款</span>
            <span v-else-if="scope.row.re_type == 3">-</span>
            <span v-else v-html="scope.row.re_money"></span>
					</template>
        </el-table-column>
        <el-table-column prop="re_time" label="申请时间" width="185">
          <template slot-scope="scope">
            <span>{{ scope.row.re_time | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="mobile" label="类型" width="125">
          <template slot-scope="scope">
            <span v-if="scope.row.re_type == 2">仅退款</span>
            <span v-else-if="scope.row.re_type == 1">退货退款</span>
            <span v-else>换货</span>
					</template>
        </el-table-column>
        <el-table-column prop="prompt" label="状态" width="125">
          <template slot-scope="scope">
            <span v-if="scope.row.r_type == 0">待审核</span>
            <span v-else-if="scope.row.r_type == 1 && scope.row.re_type == 1">退款中</span>
            <span v-else-if="scope.row.r_type == 3 && scope.row.re_type == 1">退款中</span>
            <span v-else-if="scope.row.r_type == 1 && scope.row.re_type == 3">换货中</span>
            <span v-else-if="scope.row.r_type == 3 && scope.row.re_type == 3">换货中</span>
            <span v-else-if="scope.row.r_type == 11 && scope.row.re_type == 3">换货中</span>
            <span v-else-if="scope.row.r_type == 5 && scope.row.re_type == 3" style="color: #ff2a1f;">换货失败</span>
            <span v-else-if="scope.row.r_type == 5 && scope.row.re_type != 3" style="color: #ff2a1f;">退款失败</span>
            <span v-else-if="scope.row.r_type == 4 || scope.row.r_type == 9" style="color: #7CCD7C;">退款成功</span>
            <span v-else-if="scope.row.r_type == 12" style="color: #7CCD7C;">换货成功</span>
            <span v-else-if="scope.row.r_type == 2" style="color: #ff2a1f;">退款失败</span>
            <span v-else-if="scope.row.r_type == 8 && scope.row.r_content == '已收货'" style="color: #7CCD7C;">退换成功</span>
            <span v-else-if="scope.row.r_type == 8 && scope.row.r_content != '已收货'" style="color: #ff2a1f;">退款失败</span>
            <span v-else-if="scope.row.r_type == 10" style="color: #ff2a1f;">换货失败</span>
					</template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200" class="hhh">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-bottom" v-if="scope.row.is_distribution == 0">
                <div class="item view" v-if="scope.row.is_distribution == 0">
                  <el-button icon="el-icon-view" @click="View(scope.row)">查看</el-button>
                </div>
                <div class="item" v-if="scope.row.r_type != 8 && (scope.row.re_type == 3 && scope.row.r_type == 11)">
                  <el-button icon="el-icon-view" @click="dialogShow2(scope.row,3)">回寄</el-button>
                </div>
                <div class="item" v-else-if="scope.row.r_type == 0">
                  <el-button icon="el-icon-circle-check" @click="dialogShow2(scope.row,1)">通过</el-button>
                  <el-button icon="el-icon-circle-close" @click="dialogShow2(scope.row,2)">拒绝</el-button>
                </div>
                <div class="item" v-else-if="scope.row.r_type == 3">
                  <el-button v-if="scope.row.re_type<3" icon="el-icon-circle-check" @click="dialogShow2(scope.row,1)">通过</el-button>
                  <el-button v-else icon="el-icon-view" @click="dialogShow2(scope.row,3)">回寄</el-button>
                  <el-button icon="el-icon-circle-close" @click="dialogShow2(scope.row,2)">拒绝</el-button>
                </div>
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
        :title="toggle == 1 ? '通过' : toggle == 3 ? '回寄' : '填写理由'"
        :visible.sync="dialogVisible2"
        :before-close="handleClose2"
      >
        <el-form :model="ruleForm" ref="ruleForm" label-width="170px" class="demo-ruleForm">
          <div class="task-container">
            <div class="through" v-if="toggle === 1 || toggle === 3">
              <h3 v-if="this.type !== 11">{{ content }}</h3>
              <div class="refund" v-if="this.type == 2 || this.type == 9 || this.type == 4">
                <div class="y-refund">
                  应退：<span>{{ ruleForm.y_refund }}</span>
                </div>
                <div class="s-refund">
                  <span>实退：</span>
                  <el-input type="number" min="0" v-model="ruleForm.s_refund"></el-input>
                </div>
              </div>
              <el-form-item class="select-input" v-if="toggle === 3" label="快递公司" prop="kuaidi_name">
                <el-select class="select-input" v-model="ruleForm.kuaidi_name" placeholder="请选择快递公司">
                  <el-option v-for="item in courierList" :key="item.id" :label="item.kuaidi_name" :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item class="select-input" v-if="toggle === 3" label="快递单号" prop="kuaidi_no">
                <el-input v-model="ruleForm.kuaidi_no"></el-input>
              </el-form-item>
            </div>
            <el-form-item v-if="toggle === 2" class="integral" label="拒绝理由：(限20个字符)">
              <el-input type="textarea" v-model="ruleForm.reason"></el-input>
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
import salesReturnList from '@/webManage/js/order/salesReturn/salesReturnList'
export default salesReturnList
</script>

<style scoped lang="less">
@import '../../../../webManage/css/order/salesReturn/salesReturnList.less';
</style>