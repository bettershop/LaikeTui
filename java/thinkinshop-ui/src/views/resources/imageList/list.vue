<template>
  <div class="container">
    <div class="Search">
      <div class="Search-condition">
        <div class="query-input">
          <el-input size="medium" v-model="mchName" @keyup.enter.native="demand" class="Search-input" placeholder="请输入店铺名称"></el-input>
        </div>

        <div class="btn-list">
          <el-button class="bgColor" type="primary" @click="demand">{{$t('DemoPage.tableExamplePage.demand')}}
          </el-button>
          <el-button class="fontColor" @click="reset">{{$t('DemoPage.tableExamplePage.reset')}}</el-button>
        </div>
      </div>
    </div>

    <div class="jump-list">
      <el-button style="
    width: 120px;
    height: 42px;
    color: #6a7076;
    background-color: #fff;
    border-radius: 4px;" @click="Del" icon="el-icon-delete">{{$t('DemoPage.tableExamplePage.Batch_delete')}}</el-button>
    </div>


    <div class="merchants-list" ref="tableFather">
      <el-table element-loading-text="拼命加载中..." v-loading="page.loading" :data="page.tableData" ref="table"
          class="el-table"
          style="width: 100%"
          :height="tableHeight" @selection-change="Chose">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column label="序号" width="200">
          <template slot-scope="scope">
            {{ scope.$index+1 }}
          </template>
        </el-table-column>
        <el-table-column prop="p_name" label="图像" width="200">
          <template slot-scope="scope">
            <img :src="scope.row.imgUrl" alt=""/>
          </template>
        </el-table-column>
        <el-table-column prop="image_name" label="图片名称" width="200">
        </el-table-column>
        <el-table-column prop="imgUrl" label="图片路径" width="328">
          <template slot-scope="scope">
            <a :href="scope.row.imgUrl" target="blank">{{ scope.row.imgUrl }}</a>
            <!-- <a :href="`${scope.row.imgUrl}?response-content-type=application/octet-stream`" download target="blank">{{ scope.row.imgUrl }}</a> -->
          </template>
        </el-table-column>
        <el-table-column prop="name" label="所属店铺" width="200">
        </el-table-column>
        <el-table-column label="上传时间">
          <template slot-scope="scope">
            {{ scope.row.add_time | dateFormat}}
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
import main from "@/webManage/js/resources/imageList/list";
export default main;
</script>


<style scoped lang="less">
@import "../../../common/commonStyle/form";
@import "../../../webManage/css/resources/imageList.less";
</style>

