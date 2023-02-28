<template>
  <div class="container">
    <el-form ref="ruleForm" class="form-search" :model="ruleForm" label-width="130px">
      <div class="basic-info">
        <div class="header">
          <span>基本信息</span>
        </div>
        <div class="basic-block">
          <el-form-item label="商品标题：" required>
						<span>{{ goodsInfo.product_title }}</span>
					</el-form-item>
          <el-form-item label="副标题：">
						<span>{{ goodsInfo.subtitle }}</span>
					</el-form-item>
          <el-form-item label="关键词：" required>
						<span>{{ goodsInfo.keyword }}</span>
					</el-form-item>
          <el-form-item label="商品条形码：" required>
						<img :src="goodsInfo.scan" alt="">
					</el-form-item>
          <el-form-item label="商品类别：" required>
						<span>{{ goodsInfo.className }}</span>
					</el-form-item>
          <el-form-item label="商品品牌：" required>
						<span>{{ goodsInfo.brandName }}</span>
					</el-form-item>
          <el-form-item class="goosdImg" label="商品封面图：" required>
            <div class="goods-cover">
              <img :src="goodsInfo.cover_map" alt="">
            </div>
					</el-form-item>
          <el-form-item class="goosdImg" label="商品展示图：" required>
						<div class="goods-show">
              <img :src="goodsInfo.imgurl" alt="">
              <span>主图</span>
            </div>
					</el-form-item>
        </div>
      </div>

      <div class="goods-attribute">
        <div class="header">
          <span>商品属性</span>
        </div>
        <div class="attribute-block">
          <el-table :data="goodsDate" style="max-height: 350px;width: 90%" :header-cell-style="header">
            <el-table-column prop="颜色" align="center" label="颜色">
            </el-table-column>
            <el-table-column prop="costprice" align="center" label="成本价">
              <template slot-scope="scope">
                <span>{{ scope.row.costprice.toFixed(2) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="yprice" align="center" label="原价">
              <template slot-scope="scope">
                <span v-if="scope.row.yprice">{{ scope.row.yprice.toFixed(2) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="price" align="center" label="售价">
              <template slot-scope="scope">
                <span>{{ scope.row.price.toFixed(2) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="stockNum" align="center" label="库存">
            </el-table-column>
            <el-table-column prop="unit" align="center" label="单位">
            </el-table-column>
            <el-table-column prop="imgurl" align="center" label="上传图片">
              <template slot-scope="scope">
                <img :src="scope.row.imgurl" alt="">
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>

      <div class="goods-set">
        <div class="header">
          <span>商品设置</span>
        </div>
        <div class="set-block">
          <el-form-item class="inventory-warning" label="库存预警：" required>
            <div class="warning-info">
              当前库存量小于<span>{{ goodsInfo.min_inventory }}</span>时，商品库存报警
            </div>
          </el-form-item>
          <el-form-item class="freight-set" label="运费设置：" required>
            <span>{{ goodsInfo.freightName }}</span>
          </el-form-item>
          <el-form-item label="显示标签：">
						<el-checkbox-group v-model="ruleForm.checkedLabel">
              <el-checkbox v-for="label in labelList" :label="label.value" :key="label.value">{{label.name}}</el-checkbox>
            </el-checkbox-group>
					</el-form-item>
          <el-form-item class="activity-class" label="支持活动类型：" required>
            <el-radio-group v-model="ruleForm.checkedActivity">
              <el-radio disabled v-for="activity in activityList" :label="activity.value" :key="activity.value">{{activity.name}}</el-radio>
            </el-radio-group>
					</el-form-item>
          <el-form-item class="show-local" label="前端显示位置：">
						<el-checkbox v-model="ruleForm.checked" true-label="1" false-label="2">购物车</el-checkbox>
            <span class="show-font">如果不选，默认显示在全部商品里</span>
					</el-form-item>
        </div>
      </div>

      <div class="detailed-content">
        <div class="header">
          <span>商品设置</span>
        </div>
        <div class="detailed-block">
          <el-form-item class="inventory-warning" label="商品详情：">
            <div class="content">
              {{ ruleForm.content }}
            </div>
          </el-form-item>
        </div>
      </div>
      
      <div class="footer-button">
        <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
        <!-- <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button> -->
      </div>
    </el-form>
  </div>
</template>

<script>
import viewGoods from '@/webManage/js/plug_ins/stores/viewGoods'
export default viewGoods
</script>

<style scoped lang="less">
@import  '../../../webManage/css/plug_ins/stores/viewGoods.less';
</style>