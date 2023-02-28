<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-select class="select-input" v-model="inputInfo.status" placeholder="请选择任务状态">
            <el-option v-for="item in Dictionary" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
          <el-input v-model="inputInfo.name" size="medium" @keyup.enter.native="demand" class="Search-input" placeholder="请输入任务ID/任务名称"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}</el-button>
        </div>
      </div>
	  </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary" @click="dialogShow">添加任务</el-button>
      <el-button class="fontColor" @click="performAll" :disabled="is_disabled" icon="el-icon-thumb" >批量执行</el-button>
      <el-button class="fontColor" @click="delAll" :disabled="is_disabled" icon="el-icon-delete" >批量删除</el-button>
    </div>
    <div class="dictionary-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" @selection-change="handleSelectionChange" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column type="selection" width="42">
        </el-table-column>
        <el-table-column prop="id" label="任务ID" width="70">
        </el-table-column>
        <el-table-column prop="title" label="任务名称" width="243">
        </el-table-column>
        <el-table-column prop="is_use_str" label="任务状态" width="243">
          <template class="status" slot-scope="scope">
            <span :class="scope.row.status === 0 ? 'active1' : 'active2'">{{ scope.row.status === 0 ? '待执行' : scope.row.status === 1 ? '执行中' : '执行完成' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="creattime" label="创建时间" width="442">
          <template slot-scope="scope">
            <span>{{ scope.row.creattime | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="add_date" label="执行时间" width="442">
          <template slot-scope="scope">
            <span v-if="scope.row.add_date">{{  scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="160">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" v-if="scope.row.status === 0" @click="Edit(scope.row)">编辑</el-button>
                <el-button icon="el-icon-view" v-if="scope.row.status === 0" @click="immediately(scope.row)">立即执行</el-button>
                <el-button icon="el-icon-view" v-if="scope.row.status !== 0" @click="details(scope.row)">查看详情</el-button>
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
        title="添加任务"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <div class="task-container">
            <div class="operation-process" :class="tag === true ? 'active' : ''">
              <div class="operation-block">
                <div class="operation-header">
                  <span>操作流程</span>
                  <i :class="tag === true ? 'el-icon-caret-top' : 'el-icon-caret-bottom'" @click="tag = !tag"></i>
                </div>
                <ul :class="tag === false ? 'active' : ''">
                  <li>
                    <div class="process-describe">
                      <span class="num">①</span>
                      <span class="fonts">创建抓取任务</span>
                    </div>
                  </li>
                  <li>
                    <i class="el-icon-arrow-right"></i>
                  </li>
                  <li>
                    <div class="process-describe">
                      <span class="num">②</span>
                      <span class="fonts">执行抓取任务</span>
                    </div>
                  </li>
                  <li>
                    <i class="el-icon-arrow-right"></i>
                  </li>
                  <li>
                    <div class="process-describe">
                      <span class="num">③</span>
                      <span class="fonts">查看抓取结果</span>
                    </div>
                  </li>
                  <li>
                    <i class="el-icon-arrow-right"></i>
                  </li>
                  <li>
                    <div class="process-describe">
                      <span class="num">④</span>
                      <span class="fonts">商品上架</span>
                    </div>
                  </li>
                  <li>
                    <i class="el-icon-arrow-right"></i>
                  </li>
                  <li>
                    <div class="process-describe">
                      <span class="num">⑤</span>
                      <span class="fonts">入库</span>
                    </div>
                  </li>
                </ul>
              </div>
            </div>
            <el-form-item class="task-name" label="任务名称" prop="taskName">
                <el-input v-model="ruleForm.taskName" placeholder="请输任务名称"></el-input>
            </el-form-item>
            <el-form-item label="商品分类" class="goods-class" prop="goodsClass">
                <el-cascader
                  v-model="ruleForm.goodsClass"
                  class="select-input"
                  ref="myCascader"
                  placeholder="请选择商品分类"
                  :options="classList"
                  :props="{ checkStrictly: true }"
                  @change="changeProvinceCity"
                clearable>
                </el-cascader>
            </el-form-item>
            <el-form-item class="goods-brand" label="商品品牌" prop="goodsBrand">
              <el-select class="select-input" v-model="ruleForm.goodsBrand" placeholder="请选择商品品牌">
                <el-option v-for="item in brandList" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item class="baby-link" label="宝贝链接" prop="linkId">
              <div class="link-container">
                <span>https://item.taobao.com/item.htm?id=</span>
                <el-input v-model="ruleForm.linkId" placeholder="请输宝贝链接ID"></el-input>
                <div class="add-reduction">
                    <i class="el-icon-remove-outline" @click="minus" v-show="attributeList.length !== 0"></i>
                    <i class="el-icon-circle-plus-outline" @click="addOne" v-show="attributeList.length === 0"></i>
                </div>
              </div>
            </el-form-item>
            <el-form-item v-if="attributeList.length !== 0" class="add-babyLink" label="" prop="code">
              <div class="link-container add-container" v-for="(item,index) in attributeList" :key="index">
                <span>https://item.taobao.com/item.htm?id=</span>
                <el-input v-model="attributeList[index]" placeholder="请输宝贝链接ID"></el-input>
                <div class="add-reduction">
                  <i class="el-icon-remove-outline" @click="minus"></i>
                  <i class="el-icon-circle-plus-outline" @click="addOnes" v-show="index === attributeList.length - 1"></i>
                </div>
              </div>
            </el-form-item>
            <div class="prompt">
              <div class="prompt-block">
                <h3>温馨提示：</h3>
                <ul>
                  <li>
                    <span class="num">①</span>
                    <span class="font">宝贝链接ID必须复制淘宝商品链接ID。(必须为淘宝商品，天猫商品不支持！)</span>
                  </li>
                  <li>
                    <span class="num">②</span>
                    <span class="font">任务创建成功后，须先排队再执行，整个过程将在3-5分种内自动完成，请耐心等待！</span>
                  </li>
                  <li>
                    <span class="num">③</span>
                    <span class="font">任务执行成功后，可在查看详情中查看执行结果。</span>
                  </li>
                  <li>
                    <span class="num">④</span>
                    <span class="font">抓取成功的商品将在商品列表中显示，默认状态为待上架。</span>
                  </li>
                  <li>
                    <span class="num">⑤</span>
                    <span class="font">商品上架成功后，将会自动按库存入库。</span>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" @click="handleClose()">取 消</el-button>
              <el-button class="bdColor" type="primary" @click="submitForm('ruleForm')">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import taskList from '@/webManage/js/goods/taobaoAssistant/taskList'
export default taskList
</script>

<style scoped lang="less">
@import '../../../../webManage/css/goods/taobaoAssistant/taskList.less';
</style>