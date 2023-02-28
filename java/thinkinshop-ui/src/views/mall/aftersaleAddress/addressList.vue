<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input size="medium" v-model="page.inputInfo.name" @keyup.enter.native="demand" class="Search-input"
          placeholder="请输入联系人"></el-input>
        </div>
        <div class="btn-list">
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}
          </el-button>
        </div>
      </div>
    </div>
    <div class="jump-list">
      <el-button class="bgColor laiketui laiketui-add" type="primary" @click="Save()">添加地址
      </el-button>
    </div>
    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
        class="el-table"
        style="width: 100%"
        :height="tableHeight">
        <el-table-column label="序号" width="88">
          <template slot-scope="scope">
            {{ scope.$index+1 }}
          </template>
        </el-table-column>
        <el-table-column prop="name" label="发货人" width="328">
        </el-table-column>
        <el-table-column prop="tel" label="联系电话" width="328">
        </el-table-column>
        <el-table-column prop="address_xq" label="详细地址" width="328">
        </el-table-column>
        <el-table-column prop="code" label="邮政编码" width="328">
        </el-table-column>
        <el-table-column label="是否默认" width="328">
          <template slot-scope="scope">
            {{ scope.row.is_default===1?'默认地址':'否' }}
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="250">
          <template slot-scope="scope">
            <div class="OP-button">
              <div class="OP-button-top">
                <el-button icon="el-icon-edit-outline" @click="Save(scope.row.id)">编辑</el-button>
                <el-button icon="el-icon-circle-check" @click="Default(scope.row.id)"
                           :disabled="scope.row.is_default===1">默认
                </el-button>
                <el-button icon="el-icon-delete" @click="Delete(scope.row.id)">删除</el-button>
              </div>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="pageBox" ref="pageBox" v-if="page.showPagebox">
        <div class="pageLeftText">显示</div>
        <el-pagination layout="sizes, slot, prev, pager, next" prev-text="上一页" next-text="下一页"
          @size-change="handleSizeChange"
          :page-sizes="pagesizes" :current-page="pagination.page"
          @current-change="handleCurrentChange"
          :total="total">
          <div class="pageRightText">当前显示{{currpage}}-{{current_num}}条，共 {{total}} 条记录</div>
        </el-pagination>
      </div>
    </div>

  </div>
</template>

<script>
import addressList from "@/webManage/js/mall/aftersaleAddress/addressList";
export default addressList
</script>

<style scoped lang="less">
  @import "../../../webManage/css/mall/aftersaleAddress/addressList.less";
</style>
