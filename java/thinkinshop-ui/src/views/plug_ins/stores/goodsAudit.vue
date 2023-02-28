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
          <el-input v-model="inputInfo.goodsName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入商品名称"></el-input>
          <el-input v-model="inputInfo.storeName" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
          <!-- <el-button class="bgColor export" type="primary" @click="dialogShow">导出</el-button> -->
        </div>
      </div>
	  </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="id" label="商品ID" width="70">
        </el-table-column>
        <el-table-column prop="imgurl" label="商品图片">
          <template slot-scope="scope">
            <img :src="scope.row.imgurl" alt="">
          </template>
        </el-table-column>
        <el-table-column prop="goods_name" label="商品名称">
          <template slot-scope="scope">
            <div class="goods-info">
              <div class="item name">
                <span>{{ scope.row.product_title }}</span>
              </div>
              <div class="item label" v-if="scope.row.s_type">
                <span v-show="scope.row.s_type.includes('3')" class="recommend">推荐</span>
                <span v-show="scope.row.s_type.includes('2')" class="sell">热销</span>
                <span v-show="scope.row.s_type.includes('1')" class="new">新品</span>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="mch_id" label="店铺ID">
        </el-table-column>
        <el-table-column prop="name" label="店铺名称">
        </el-table-column>
        <el-table-column prop="pname" label="分类名称">
        </el-table-column>
        <el-table-column prop="num" label="库存">
        </el-table-column>
        <el-table-column prop="status_name" label="商品状态">
            <!-- <template slot-scope="scope">
              <span class="ststus active">{{ scope.row.mch_status == 1 ? '待审核' : '' }}</span>
            </template> -->
        </el-table-column>
        <el-table-column prop="add_date" label="发布时间">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
       <el-table-column prop="brand_name" label="商品品牌">
        </el-table-column>
        <el-table-column prop="price" class="price" label="价格">
          <template slot-scope="scope">
              <span>{{ scope.row.price.toFixed(2) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="show_adr" label="显示位置">
          <template slot-scope="scope">
            <span>{{ scope.row.showAdrNameList[0] == '0' ? '全部商品' : '购物车' }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-view" @click="View(scope.row)">查看</el-button>
                <el-button icon="el-icon-circle-check" @click="Through(scope.row)">通过</el-button>
                <el-button icon="el-icon-circle-close" @click="dialogShow2(scope.row)">拒绝</el-button>
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
        <el-form :model="ruleForm" ref="ruleForm" :rules="rules" label-width="100px" class="demo-ruleForm">
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
  </div>
</template>

<script>
import goodsAudit from '@/webManage/js/plug_ins/stores/goodsAudit'
export default goodsAudit
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/goodsAudit.less';
</style>