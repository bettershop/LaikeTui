<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="会员列表" @click.native.prevent="$router.push('/members/membersList')"></el-radio-button>
        <el-radio-button label="会员等级" @click.native.prevent="$router.push('/members/membersLevel')"></el-radio-button>
        <el-radio-button label="会员设置" @click.native.prevent="$router.push('/members/membersSet')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/members/addLevel')">添加等级</el-button>
    </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column fixed="left" prop="id" label="序号" width="175">
        </el-table-column>
        <el-table-column prop="name" label="等级名称">
        </el-table-column>
        <el-table-column prop="" label="晋升条件">
          <template>
            <div class="condition">
              <span v-if="upgrade.includes('1')">购买会员服务</span>
              <span v-if="upgrade.includes('2')">补差额升级</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="rate" label="专属折扣">
          <template slot-scope="scope">
            <span>{{ scope.row.rate }}折</span>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" @click="Edit(scope.row)">编辑</el-button>
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
  </div>
</template>

<script>
import membersLevel from '@/webManage/js/members/membersList/membersLevel'
export default membersLevel
</script>

<style scoped lang="less">
@import '../../../../webManage/css/members/membersList/membersLevel.less';
</style>