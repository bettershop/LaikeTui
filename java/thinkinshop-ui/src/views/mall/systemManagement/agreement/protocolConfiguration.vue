<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="基础配置" @click.native.prevent="$router.push('/mall/basicConfiguration')"></el-radio-button>
        <el-radio-button label="协议配置" @click.native.prevent="$router.push('/mall/agreement/protocolConfiguration')"></el-radio-button>
        <el-radio-button label="常见问题" @click.native.prevent="$router.push('/mall/commonProblem')"></el-radio-button>
        <el-radio-button label="新手指南" @click.native.prevent="$router.push('/mall/newbieGuide')"></el-radio-button>
        <el-radio-button label="售后服务" @click.native.prevent="$router.push('/mall/afterSales')"></el-radio-button>
        <el-radio-button label="关于我们" @click.native.prevent="$router.push('/mall/aboutUs')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/mall/agreement/addAgreement')">添加协议</el-button>
    </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="id" label="序号">
        </el-table-column>
        <el-table-column prop="name" label="标题">
        </el-table-column>
        <el-table-column prop="" label="类型">
          <template slot-scope="scope">
            <span>{{ scope.row.type === 0 ? '注册' : scope.row.type === 1 ? '店铺申请' : '隐私协议'  }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="modify_date" label="发布时间">
          <template slot-scope="scope">
            <span>{{ scope.row.modify_date | dateFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
                <el-button class="el-icon-delete" @click="Delete(scope.row)">删除</el-button>
              </div>
            </div>
		      </template>
        </el-table-column>
	  </el-table>
      <div class="pageBox" v-if="showPagebox && tableData.length > 5">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页" @size-change="handleSizeChange"
          :page-sizes="pagesizes" :current-page="pagination.page" @current-change="handleCurrentChange" :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import protocolConfiguration from '@/webManage/js/mall/systemManagement/protocolConfiguration'
export default protocolConfiguration
</script>

<style scoped lang="less">
@import '../../../../webManage/css/mall/systemManagement/protocolConfiguration.less';
</style>