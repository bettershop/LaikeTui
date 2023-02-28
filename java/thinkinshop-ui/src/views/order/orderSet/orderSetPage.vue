<template>
  <div class="container">
    <div class="formCenter">
      <el-form :model="mainData" class="picture-ruleForm" label-width="100%">
        <el-form-item label="多件包邮设置">
          <el-switch v-model="mainData.isMany"/>
          <div class="djby width" v-if="mainData.isMany">
            <div class="row">
              同件商品数量达到
              <div class="el-input">
                <input type="number" @blur="blurPiece" autocomplete="off" class="el-input__inner" v-model="mainData.same_piece"/>
              </div>
              件，包邮
            </div>
            <div class="row">
              同一订单中，商品数量达到
              <div class="el-input"><input type="number" @blur="blurOrder" autocomplete="off" class="el-input__inner" v-model="mainData.same_order"/></div>
              件，包邮
            </div>
          </div>
        </el-form-item>

        <el-form-item label="自动收货时间">
          <el-input placeholder="自动收货时间" v-model="mainData.auto_the_goods" class="width">
            <template slot="append">天</template>
          </el-input>
        </el-form-item>
        <el-form-item label="订单失效时间">
          <el-input placeholder="订单失效时间" v-model="mainData.order_failure" class="width">
            <template slot="append">小时</template>
          </el-input>
        </el-form-item>
        <el-form-item label="订单售后时间">
          <el-input placeholder="订单售后时间" v-model="mainData.order_after" class="width">
            <template slot="append">天</template>
          </el-input>
        </el-form-item>
        <el-form-item label="提醒限制">
          <div class="djby width">
            <div class="row">
              <div class="el-input">
                <input type="number" autocomplete="off" class="el-input__inner" v-model="mainData.remind_day"/>
              </div>天
              <div class="el-input"><input type="number" autocomplete="off" class="el-input__inner" v-model="mainData.remind_hour"/></div>小时
            </div><span>(店主查看发货提醒后，买家多久后能再次提醒。0.表示只能提醒一次)</span>
          </div>
        </el-form-item>
        <el-form-item label="自动评价设置">
          <el-input placeholder="自动评价设置" v-model="mainData.auto_good_comment_day" class="width">
            <template slot="append">天</template>
          </el-input>
        </el-form-item>

        <div class="form-footer">
          <el-form-item>
            <el-button class="bgColor" type="primary" @click="Save()">{{ $t('DemoPage.tableFromPage.save') }}
            </el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import main from "@/webManage/js/order/orderSet/orderSetPage";

export default main;
</script>

<style scoped lang="less">
  @import "../../../common/commonStyle/form";
  @import "../../../webManage/css/order/orderSet/orderSetPage";
</style>
