<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="会员列表" @click.native.prevent="$router.push('/members/membersList')"></el-radio-button>
        <el-radio-button label="会员等级" @click.native.prevent="$router.push('/members/membersLevel')"></el-radio-button>
        <el-radio-button label="会员设置" @click.native.prevent="$router.push('/members/membersSet')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input v-model="inputInfo.uid" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入会员ID"></el-input>
          <el-input v-model="inputInfo.uname" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入会员名称"></el-input>
          <el-select class="select-input" v-model="inputInfo.grade" placeholder="请选择会员等级">
            <el-option v-for="(item,index) in membersGrade" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-select class="select-input" v-model="inputInfo.isOverdue" placeholder="是否过期">
            <el-option v-for="(item,index) in overdueList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-select class="select-input" v-model="inputInfo.source" placeholder="账号来源">
            <el-option v-for="(item,index) in sourceList" :key="index" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="inputInfo.tel" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入手机号码"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button>
        </div>
      </div>
	  </div>

    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="addMembers">添加会员</el-button>
    </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight" :cell-class-name="addClass">
        <el-table-column fixed="left" prop="user_id" label="会员ID" width="95">
        </el-table-column>
        <el-table-column prop="imgurl" label="会员头像" width="95">
          <template slot-scope="scope">
            <img :src="scope.row.headimgurl" alt="">
          </template>
        </el-table-column>
        <el-table-column prop="user_name" label="会员名称" width="95">
        </el-table-column>
        <el-table-column prop="zhanghao" label="会员账号" width="135">
        </el-table-column>
        <el-table-column prop="grade" label="会员级别" width="95">
        </el-table-column>
        <el-table-column prop="mobile" label="手机号码" width="125">
        </el-table-column>
        <el-table-column prop="money" label="账户余额" width="125">
            <template slot-scope="scope">
              <span>￥{{ scope.row.money }}</span>
            </template>
        </el-table-column>
        <el-table-column prop="score" label="积分余额" width="95">
        </el-table-column>
        <el-table-column prop="volume" label="账号来源" width="95">
          <template slot-scope="scope">
            <span>{{ getSource(scope.row.source) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="z_num" label="有效订单数" width="90">
        </el-table-column>
        <el-table-column prop="z_price" label="交易金额" width="76">
          <template slot-scope="scope">
            <span>￥{{ scope.row.z_price == null ? 0 : scope.row.z_price }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="show_adr" label="是否过期" width="76">
          <template slot-scope="scope">
            <span>{{ scope.row.is_out == 1 ? '是' : '否' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="grade_end" label="到期时间" width="185">
          <template slot-scope="scope">
            <span v-if="scope.row.grade_end == '暂无'">{{ scope.row.grade_end }}</span>
            <span v-else>{{ scope.row.grade_end | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="Register_data" label="注册时间" width="185">
          <template slot-scope="scope">
            <!-- <span v-if="!scope.row.grade_add">暂无</span> -->
            <span >{{ scope.row.Register_data | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200" class="hhh">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="View(scope.row)">查看</el-button>
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
              </div>
              <div class="OP-button-bottom">
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
                <div class="more" @click="tags(scope.row)">
                    更多<i class="el-icon-caret-bottom"></i>
                    <ul class="more-block" :class="[ tag == scope.row.user_id ? 'active' : '' ]">
                        <li @click="dialogShow2(scope.row,1)">余额充值</li>
                        <li @click="dialogShow2(scope.row,2)">积分充值</li>
                        <li @click="dialogShow2(scope.row,3)">等级修改</li>
                        <li @click="valetOrder(scope.row.user_id)">代客下单</li>
                    </ul>
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
        :title="toggle == 1 ? '金额充值' : toggle == 2 ? '积分充值' : '等级修改'"
        :visible.sync="dialogVisible2"
        :before-close="handleClose2"
      >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <div class="task-container">
            <el-form-item v-if="toggle === 1" class="balance" label="余额金额" prop="balance">
                <el-input v-model="ruleForm.balance" placeholder="请输充值金额"></el-input>
                <div class="explain">
                  <img src="../../../../assets/imgs/ts.png" alt="">
                  <span class="red">扣除请添加负号</span>
                </div>
            </el-form-item>
            <el-form-item v-if="toggle === 2" class="integral" label="积分金额" prop="integral">
                <el-input v-model="ruleForm.integral" placeholder="请输充值金额"></el-input>
                <div class="explain">
                  <img src="../../../../assets/imgs/ts.png" alt="">
                  <span class="red">扣除请添加负号</span>
                </div>
            </el-form-item>
            <el-form-item v-if="toggle === 3" class="level" label="会员等级" prop="level">
                <el-select class="select-input" v-model="ruleForm.level" placeholder="请选择会员等级">
                  <el-option v-for="(item,index) in membersGrade" :key="index" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
            </el-form-item>
            <el-form-item v-if="toggle === 3 && ruleForm.level !== 0" class="level" label="生效时间">
                <el-select class="select-input" v-model="ruleForm.effect_time" placeholder="请选择会员等级">
                  <el-option v-for="(item,index) in methodList" :key="index" :label="item.label" :value="item.value">
                  </el-option>
                </el-select>
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
import membersLists from '@/webManage/js/members/membersList/membersLists'
export default membersLists
</script>

<style scoped lang="less">
@import '../../../../webManage/css/members/membersList/membersList.less';
</style>