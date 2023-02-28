<template>
  <div class="container">
    <div class="goods-info">
      <div class="header">
        <span>选择商品</span>
      </div>
      <div class="goods-block">
          <div class="dictionary-list">
              <el-table :data="goodsTables" ref="table" class="el-table" @selection-change="handleSelectionChange" style="width: 100%">
                  <el-table-column type="selection" width="55">
                  </el-table-column>
                  <el-table-column prop="p_name" label="商品名称">
                  </el-table-column>
                  <el-table-column prop="size" label="商品图片">
                    <template slot-scope="scope">
                      <img :src="scope.row.imgurl" alt="">
                    </template>
                  </el-table-column>
                  <el-table-column prop="num" label="数量">
                  </el-table-column>
                  <el-table-column prop="p_price" label="商品价格">
                  </el-table-column>
                  <el-table-column prop="size" label="规格" width="200">
                  </el-table-column>
              </el-table>
          </div>
      </div>
    </div>
    <div class="footer-button">
      <div class="info">
        <!-- goodsNum : js赋值错误 -->
        共<span>{{goodsTables.length}}</span>件商品
      </div>
      <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">取消</el-button>
      <el-button type="primary" class="footer-save bgColor mgleft" @click="dialogShow">发货</el-button>
    </div>

    <div class="dialog-block">
      <!-- 弹框组件 -->
      <el-dialog
        title="发货"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
          <el-form-item label="快递公司" prop="kuaidi_name">
            <el-select class="select-input" v-model="ruleForm2.kuaidi_name" placeholder="请选择快递公司">
              <el-option v-for="item in courierList" :key="item.id" :label="item.kuaidi_name" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="快递单号" prop="kuaidi_no">
            <el-input v-model="ruleForm2.kuaidi_no"></el-input>
          </el-form-item>
          <div class="form-footer">
            <el-form-item>
              <el-button @click="handleClose">取 消</el-button>
              <el-button type="primary" @click="determine('ruleForm2')">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import goodsDelivery from '@/webManage/js/order/orderList/goodsDelivery'
export default goodsDelivery
</script>

<style scoped lang="less">
@import '../../../../webManage/css/order/orderList/goodsDelivery.less';
</style>