<template>
  <div class="container">
    <div class="btn-nav">
      <el-radio-group fill="#2890ff" text-color="#fff" v-model="radio1">
        <el-radio-button label="轮播图列表" @click.native.prevent="$router.push('/plug_ins/template/playlist')"></el-radio-button>
        <el-radio-button label="UI导航栏" @click.native.prevent="$router.push('/plug_ins/template/navigationBar')"></el-radio-button>
        <el-radio-button label="分类管理" @click.native.prevent="$router.push('/plug_ins/template/classification')"></el-radio-button>
        <el-radio-button label="活动管理" @click.native.prevent="$router.push('/plug_ins/template/activity')"></el-radio-button>
      </el-radio-group>
    </div>

    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column prop="" label="序号" width="70">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="pname" label="分类名称" width="500">
        </el-table-column>
        <el-table-column prop="is_display" label="是否显示" width="800">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.is_display" @change="switchs(scope.row)" :active-value="0" :inactive-value="1" active-color="#00ce6d" inactive-color="#d4dbe8">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
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
import classification from '@/webManage/js/plug_ins/template/classification'
export default classification
</script>

<style scoped lang="less">
@import '../../../webManage/css/plug_ins/template/classification.less';
</style>