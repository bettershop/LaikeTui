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
      <el-button class="bgColor laiketui laiketui-add" type="primary"  @click="$router.push('/plug_ins/template/addSlideShow')">添加轮播图</el-button>
    </div>
    <div class="menu-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="loading" :data="tableData" ref="table" class="el-table" style="width: 100%"
		  :height="tableHeight">
        <el-table-column prop="" label="序号" width="70">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="image" label="图片" width="670">
            <template slot-scope="scope">
              <img :src="scope.row.image" alt="">
            </template>
        </el-table-column>
        <el-table-column prop="url" label="链接" width="450">
        </el-table-column>
        <el-table-column prop="add_date" label="添加时间" width="280">
          <template slot-scope="scope">
            <span>{{ scope.row.add_date | dateFormat }}</span>
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
import playlist from '@/webManage/js/plug_ins/template/playlist'
export default playlist
</script>

<style scoped lang="less">
@import '../../../webManage/css/plug_ins/template/playlist.less';
</style>