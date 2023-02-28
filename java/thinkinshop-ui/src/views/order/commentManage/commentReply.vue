<template>
  <div class="container">
    <div class="commentAll iframe-content">
      <!--回复区域 begin-->
      <div class="comment-show">
        <div class="comment-show-con clearfix">
          <div class="comment-show-con-img pull-left">
            <img
              :src="mainData.headimgurl"
              alt="">
          </div>
          <div class="comment-show-con-list pull-left clearfix" style="margin-top: 0;">
            <div class="pl-text clearfix" style="margin-top: 0;">
              <span class="comment-size-name">{{mainData.add_time | dateFormat}}</span>
            </div>
            <div class="date-dz">
              <span class="date-dz-left pull-left comment-time"
                    style="padding-top: 0;">{{mainData.user_name}}:<span
                class="commentText">{{mainData.content}}</span> </span>
              <i class=" laiketui laiketui-haoping pull-right" v-if="mainData.CommentType>3"></i>
              <i class=" laiketui laiketui-chaping pull-right" v-else-if="mainData.CommentType<3"></i>
              <i class=" laiketui laiketui-zhongping pull-right" v-else></i>
            </div>
          </div>
          <div class="hf-list-con"></div>
        </div>
      </div>
    </div>
    <div class="date-dz">
      <!--回复区域 end-->

      <div class="xian_h"></div>
      <!--评论区域 begin-->
      <div class="reviewArea clearfix" style="margin-top: 30px;">
        <div>
          <textarea class="content comment-input" v-model="replyText" placeholder="请输入需要回复的内容"></textarea>
        </div>
      </div>
      <div class="reply">
        <el-button class="bgColor pull-right" type="primary" @click="Reply()">{{
            $t('DemoPage.tableFromPage.saveComment') }}
          </el-button>
      </div>
    </div>

  </div>
</template>

<script>
import main from "@/webManage/js/order/commentManage/commentReply";

export default main;
</script>


<style scoped lang="less">
  @import "../../../webManage/css/order/commentManage/commentReply";
</style>
