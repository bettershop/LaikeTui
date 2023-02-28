<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="轮播图列表" @click.native.prevent="$router.push('/plug_ins/template/playlist')"></el-radio-button>
        <el-radio-button label="UI导航栏" @click.native.prevent="$router.push('/plug_ins/template/navigationBar')"></el-radio-button>
        <el-radio-button label="分类管理" @click.native.prevent="$router.push('/plug_ins/template/Classification')"></el-radio-button>
        <el-radio-button label="活动管理" @click.native.prevent="$router.push('/plug_ins/template/activity')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="dialogShow('',true)">添加UI导航栏</el-button>
    </div>
    
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column prop="" label="序号" width="70">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="image" label="图标" width="420">
            <template slot-scope="scope">
              <img :src="scope.row.image" alt="">
            </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" width="200">
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="400">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="isshow" label="是否显示" width="300">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.isshow" @change="switchs(scope.row)" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="180">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button @click="moveUp(scope.$index)">
                  <i v-if="scope.$index !== 0" class="el-icon-top"></i>
                  <i v-else class="el-icon-bottom"></i>
                  {{ scope.$index === 0 ? '下移' : '上移'}}
                </el-button>
                <el-button class="laiketui laiketui-zhiding" @click="placedTop(scope.row)" v-if="scope.$index !== 0">置顶</el-button>
              </div>
              <div class="OP-button-bottom">
                <el-button icon="el-icon-edit-outline" @click="dialogShow(scope.row,false)">编辑</el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
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
        :title="title"
        :visible.sync="dialogVisible"
        :before-close="handleClose"
      >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="名称：" required>
            <el-input v-model="ruleForm.name" placeholder="请输入导航名称"></el-input>
            <span>（最多不能超过四个字符）</span>
          </el-form-item>
          <el-form-item class="upload-img" label="图标：" required>
            <l-upload
              :limit="1"
              ref="upload"
              v-model="ruleForm.img"
              text="（建议上传82px*82px的图片）"
            >
            </l-upload>
          </el-form-item>
          <el-form-item label="跳转地址：">
            <el-select class="select-input" v-model="ruleForm.class1" placeholder="">
                <el-option v-for="(item,index) in classList1" :key="index" :label="item.label" :value="item.value">
                  <div @click="change(item)">{{ item.label }}</div>
                </el-option>
            </el-select>
            <el-select v-if="ruleForm.class1 !== 0" filterable class="select-input selects" v-model="ruleForm.url" placeholder="请输入关键字搜索">
              <el-option v-for="(item,index) in classList2" :key="index" :label="item.name" :value="item.parameter">
              </el-option>
            </el-select>
            <el-input v-else v-model="ruleForm.url"></el-input>
          </el-form-item>
          <el-form-item label="是否显示：" required>
            <el-switch v-model="ruleForm.is_display" :active-value="1" :inactive-value="0" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </el-form-item>
          <div class="form-footer">
            <el-form-item>
              <el-button class="bgColor" @click="handleClose()">取 消</el-button>
              <el-button class="bdColor" type="primary" @click="submitForm2('ruleForm')">确 定</el-button>
            </el-form-item>
          </div>
        </el-form>
      </el-dialog>
    </div>

  </div>
</template>

<script>
import navigationBar from '@/webManage/js/plug_ins/template/navigationBar'
export default navigationBar
</script>

<style scoped lang="less">
@import '../../../webManage/css/plug_ins/template/navigationBar.less';
</style>