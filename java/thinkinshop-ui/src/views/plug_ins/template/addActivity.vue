<template>
  <div class="container">
    <div class="header">
      <span>添加活动</span>
    </div>
    <el-form :model="ruleForm" label-position="right" ref="ruleForm" label-width="100px" class="form-search">
      <div class="notice">
        <el-form-item label="活动类型：">
          <el-radio-group v-model="ruleForm.activeType">
            <el-radio v-for="item in activeTypeList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <div class="activity-project" v-if="ruleForm.activeType === 0">
          <el-form-item label="标题：" required>
            <el-input v-model="ruleForm.activeTitle" size="medium" class="Search-input" placeholder="请输入标题"></el-input>
            <span>（标题，如好物优选）</span>
          </el-form-item>
          <el-form-item class="goos-block" label="展示商品：" required>
            <el-button plain @click="AddPro" class="cancel">添加商品</el-button>
            <div class="changeUser" style="height: 330px;">
              <el-table :data="ChangeProList" height="100%" :header-cell-style="header">
                <el-table-column prop="proName" align="center" label="商品信息">
                  <template slot-scope="scope">
                    <div class="Info">
                      <img :src="scope.row.imgurl" width="40" height="40" alt="">
                      <span>{{ scope.row.product_title  }}</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="price" align="center" label="价格">
                </el-table-column>
                <el-table-column prop="volume" align="center" label="库存">
                </el-table-column>
                <el-table-column prop="action" align="center" label="操作">
                    <template slot-scope="scope">
                        <el-button class="delete" @click="ChangeProdel(scope.$index,scope.row)" plain icon="el-icon-delete">删除</el-button>
                    </template>
                </el-table-column>
              </el-table>
            </div>
          </el-form-item>
        </div>
        <div class="plug-in" v-else>
          <el-form-item label="插件类型：" required>
            <el-select class="Search-select" v-model="ruleForm.plugType" placeholder="请选择插件类型">
              <el-option v-for="item in plugTypeList" :key="item.value" :label="item.name" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="标题：" required v-if="ruleForm.plugType !== 10">
            <el-input v-model="ruleForm.plugTitle" size="medium" class="Search-input" placeholder="请输入标题"></el-input>
          </el-form-item>
          <el-form-item label="设置活动：" required v-else>
            <el-select class="Search-select" multiple v-model="ruleForm.setActivity" placeholder="请选择插件类型">
              <el-option v-for="item in setActivityList" :key="item.id" :label="item.name" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="footer-button">
          <el-button type="primary" class="footer-save bgColor mgleft" @click="submitForm('ruleForm')">保存</el-button>
          <el-button plain class="footer-cancel fontColor" @click="$router.go(-1)">返回</el-button>
        </div>
      </div>
        
      
    </el-form>

    <el-dialog title="添加商品" :visible.sync="dialogVisible3" width="920px">
      <div class="">
        <div class="Search" style="margin-top: 20px;">
          <el-cascader
              v-model="goodsClass"
              class="select-input"
              ref="myCascader"
              placeholder="请选择商品分类"
              :options="classList"
              :props="{ checkStrictly: true }"
              @change="changeProvinceCity"
              clearable>
          </el-cascader>
          <el-select class="Search-select" v-model="brand" placeholder="请选择商品品牌">
              <el-option v-for="item in brandList" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
              </el-option>
          </el-select>
          <el-input class="Search-input" placeholder="请输入商品名称" v-model="Proname"></el-input>
          <el-button @click="Reset" plain>重置</el-button>
          <el-button @click="query" type="primary">查询</el-button>
        </div>
        <div class="Table">
          <el-table :data="ProList" :row-key="rowKeys" ref="multipleTable" style="width: 100%" @selection-change="handleSelectionChange2" height="350">
            <el-table-column :reserve-selection="true" align="center" type="selection" width="55"></el-table-column>
            <el-table-column prop="ProName" align="center" label="商品名称">
              <template slot-scope="scope">
                <div class="Info">
                  <img :src="scope.row.imgurl" width="40" height="40" alt="">
                  <span>{{scope.row.product_title}}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column prop="price" align="center" label="价格">
            </el-table-column>
            <el-table-column prop="volume" align="center" label="库存">
            </el-table-column>
          </el-table>
          <div class="pageBox">
            <div class="pageLeftText">显示</div>
            <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页" @size-change="handleSizeChange"
              :page-sizes="pagesizes" :current-page="pagination.page" @current-change="handleCurrentChange" :total="total">
              <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
            </el-pagination>
          </div>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
          <el-button type="primary" @click="AddProconfirm">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import addActivity from '@/webManage/js/plug_ins/template/addActivity'
export default addActivity
</script>

<style scoped lang="less">
@import '../../../webManage/css/plug_ins/template/addActivity.less';
</style>